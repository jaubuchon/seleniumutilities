package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Retrieve the context of an {@link ApplicationSection} from a {@link ProvideContextTo} annotated method.
 * Supports the {@link RetrieveContextFrom} annotation.
 */
@Component
public class RetrieveContextFromCustomProvider extends ContextRetriever {

  private final Logger _logger = Logger.getLogger(RetrieveContextFromCustomProvider.class);

  /**
   * Determines if the retriever support this class.
   */
  public static <T extends ApplicationSection>  boolean canRetrieve(Class<T> sectionToBeRetrievedClass_) {

    return RetrieveContextFromCustomProvider.getRetrieveContextFromAnnotation(sectionToBeRetrievedClass_) != null;
  }

  /**
   * Retrieve the section context of the sectionToBeRetrieved by using a custom section provider
   * 
   * @param sectionToBeRetrievedClass_ the class representing the section to be retrieved
   * @param isReadOnlyModeActivated_ the readonly mode disable all non-readonly action on the page
   *        when retrieving a section (ie: click).
   */
  public <T extends ApplicationSection> SectionContext retrieveContext(
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_) {

    Class<T> contextProviderClass = this.resolveContextProviderClass(sectionToBeRetrievedClass_);

    Method contextProviderMethod =
        this.resolveContextProviderMethod(contextProviderClass, sectionToBeRetrievedClass_);

    SectionContext sectionContext =
        this.retrieveSectionContextFromProvider(contextProviderClass, contextProviderMethod,
            sectionToBeRetrievedClass_, isReadOnlyModeActivated_);

    return sectionContext;
  }

  private <T extends ApplicationSection> SectionContext retrieveSectionContextFromProvider(
      Class<T> contextProviderClass_, Method contextProviderMethod_,
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_) {

    // Retrieve the context provider instance
    ApplicationSection contextProviderObject =
        this._sectionProvider.retrieveSection(contextProviderClass_, isReadOnlyModeActivated_);

    SectionContext sectionContext = null;

    // before resolving the context, set the read only mode
    contextProviderObject.getSectionContext().enableReadOnlyMode(isReadOnlyModeActivated_);

    // Java 1.6 is not supporting multi-catch exception.
    try {
      sectionContext = (SectionContext) contextProviderMethod_.invoke(contextProviderObject);
    } catch (IllegalAccessException e) {
      this.logError(e, sectionToBeRetrievedClass_, contextProviderObject);
    } catch (IllegalArgumentException e) {
      this.logError(e, sectionToBeRetrievedClass_, contextProviderObject);
    } catch (InvocationTargetException e) {
      this.logError(e, sectionToBeRetrievedClass_, contextProviderObject);
    }

    // once the context resolve, ensure the read only mode is deactivated.
    contextProviderObject.getSectionContext().enableReadOnlyMode(false);

    return sectionContext;
  }

  /**
   * Resolve the method that is providing the context to the sectionToBeRetrived.
   * 
   * @param contextProviderClass_ the class that is providing the context to the sectionToBeRetrived
   * @param sectionToBeRetrievedClass_ the class representing the section to be retrieved
   */
  private Method resolveContextProviderMethod(Class<?> contextProviderClass_,
      Class<?> sectionToBeRetrievedClass_) {
    // Resolve the method that is providing the context
    Method contextProviderMethod =
        RetrieveContextFromCustomProvider.getMethodsAnnotatedWithProvideContextTo(
            contextProviderClass_, sectionToBeRetrievedClass_);

    if (contextProviderMethod == null) {

      String exceptionMsg =
          String
              .format(
                  "The Context Provider '%s' must expose a method annotated with @ProvideContextTo(...)in "
                  + "order to Provide the context to '%s'.", 
                  contextProviderClass_, sectionToBeRetrievedClass_);

      throw new IllegalStateException(exceptionMsg);
    }

    this._logger.debug("Context provider method: " + contextProviderMethod.toString());

    return contextProviderMethod;
  }

  /**
   * Resolve the class of the ContextProvider that will provide the context to the
   * sectionToBeRetrieved.
   * 
   * @param sectionToBeRetrieved_ the class representing the section to be retrieved
   */
  private <T extends ApplicationSection> Class<T> resolveContextProviderClass(
      Class<T> sectionToBeRetrieved_) {

    // Resolve the context provider object type
    @SuppressWarnings("unchecked")
    Class<T> contextProviderType =
        (Class<T>) RetrieveContextFromCustomProvider.getRetrieveContextFromAnnotation(
            sectionToBeRetrieved_).value();

    if (contextProviderType == null) {

      String exceptionMsg =
          String.format(
              "The Application Section '%s' must be specify his Context Provider with the help of "
                  + "the @RetrieveContextFrom(...) annotation.", sectionToBeRetrieved_);

      throw new IllegalStateException(exceptionMsg);
    }

    this._logger.debug("Context provider: " + contextProviderType.toString());

    return contextProviderType;
  }

  private static <T extends ApplicationSection> RetrieveContextFrom getRetrieveContextFromAnnotation(
      Class<T> sectionToBeRetrievedClass_) {
    
    return sectionToBeRetrievedClass_.getAnnotation(RetrieveContextFrom.class);
  }

  private static Method getMethodsAnnotatedWithProvideContextTo(
      final Class<?> type_, Class<?> provideContextToClass_) {
    
    Method result = null;

    Class<?> klass = type_;
    while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve
                                    // methods from above the current instance
      // iterate though the list of methods declared in the class represented by klass variable, and
      // add those annotated with the specified annotation
      final List<Method> allMethods =
          new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
      for (final Method method : allMethods) {
        if (method.isAnnotationPresent(ProvideContextTo.class)) {
          ProvideContextTo annotInstance = method.getAnnotation(ProvideContextTo.class);

          if (annotInstance.value() == provideContextToClass_) {
            result = method;
            break;
          }
        }
      }
      // move to the upper class in the hierarchy in search for more methods
      klass = klass.getSuperclass();
    }
    return result;
  }

  private <T extends ApplicationSection> void logError(Exception exception_,
      Class<T> sectionToBeRetrievedClass_, ApplicationSection contextProviderObject_) {
    String errorMsg =
        String
            .format(
                "An error occured while trying to resolve the '%s' context from the Context Provider '%s'",
                sectionToBeRetrievedClass_, contextProviderObject_);

    this._logger.error(exception_.getCause().getMessage());

    throw new IllegalStateException(errorMsg, exception_.getCause());
  }
}
