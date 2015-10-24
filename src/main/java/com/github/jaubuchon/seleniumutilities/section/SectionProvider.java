package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.ContextRetriever;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@Component
@Scope("prototype")
public class SectionProvider {

  @Autowired
  private ApplicationContext _applicationContext;

  private final Logger _logger = Logger.getLogger(SectionProvider.class);

  @Autowired
  SectionCache _sectionsCache;

  @Autowired
  ContextRetrieverStrategy _contextRetrieverStrategy;

  private SeleniumClient _seleniumClient;

  /**
   * Set the {@link SeleniumClient} to which the {@link SectionProvider} is associated.
   */
  public void setSeleniumClient(SeleniumClient seleniumClient_){
    this._seleniumClient = seleniumClient_;
  }
  
  /**
   * Register the root provider.
   */
  public <T extends ApplicationSection> void registerRootProvider(T root_) {
    this._sectionsCache.add(root_);
  }

  public <T extends ApplicationSection> T retrieveSectionWithReadOnlyMode(
      Class<T> sectionToBeRetrievedClass_) {

    return this.retrieveSection(sectionToBeRetrievedClass_, true);
  }

  /**
   * Retrieve a section.
   */
  public <T extends ApplicationSection> T retrieveSection(Class<T> sectionToBeRetrievedClass_) {

    return this.retrieveSection(sectionToBeRetrievedClass_, false);
  }
  
  /**
   * Retrieve a section.
   * 
   * @param sectionToBeRetrievedClass_ the class representing the section to be retrieved
   * @param isReadOnlyModeActivated_ the readonly mode disable all non-readonly action on the page
   *        when retrieving a section (ie: click).
   */
  @SuppressWarnings("unchecked")
  public <T extends ApplicationSection> T retrieveSection(Class<T> sectionToBeRetrievedClass_,
      boolean isReadOnlyModeActivated_) {

    this._logger.debug("Trying to retrieve the section: " + sectionToBeRetrievedClass_.toString());

    ApplicationSection section = this._sectionsCache.sanitizeAndresolve(sectionToBeRetrievedClass_);

    if (section == null) {

      this._logger.debug("the section was not available in the cache, trying to build it.");

      // get a new instance of the section
      section = this.buildSection(sectionToBeRetrievedClass_, isReadOnlyModeActivated_);

      // cache it
      this._sectionsCache.add(section);
    }

    this._logger.info("Retrieved the section: " + sectionToBeRetrievedClass_.toString());

    return (T) section;
  }

  /**
   * Invalidate a section from the cache.
   */
  public <T extends ApplicationSection> void invalidateSection(Class<T> section_) {

    this._sectionsCache.invalidateFromCache(section_);
  }


  /**
   * Refresh the context of a section. Used when the context is not valid anymore but you know that
   * it can be read again without making any action to the page.
   */
  public <T extends ApplicationSection> void refreshContext(T sectionToRefreshContext_) {

    SectionContext refreshedSectionContext =
        this.retrieveSectionContext(sectionToRefreshContext_.getClass(), true);

    sectionToRefreshContext_.setSectionContext(refreshedSectionContext);
  }

  /**
   * Clean the cache of section and instantiate a new one. Usually used when starting a new test
   * scenario.
   * 
   * @deprecated a SectionProvider is now scoped to a test scenario and is now not reused between
   *             test scenarios.
   */
  @Deprecated
  public void cleanCache() {
    this._sectionsCache = this._applicationContext.getBean(SectionCache.class);
  }

  private <T extends ApplicationSection> ApplicationSection buildSection(
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_) {

    SectionContext sectionContext =
        this.retrieveSectionContext(sectionToBeRetrievedClass_, isReadOnlyModeActivated_);

    ApplicationSection section = SectionProvider.createInstance(sectionToBeRetrievedClass_);

    this._applicationContext.getAutowireCapableBeanFactory().autowireBean(section);

    section.setSectionContext(sectionContext);
    section.setSeleniumClient(this._seleniumClient);

    return section;
  }

  /**
   * Retrieve the section context of the sectionToBeRetrieved
   * 
   * @param sectionToBeRetrievedClass_ the class representing the section to be retrieved
   * @param isReadOnlyModeActivated_ the readonly mode disable all non-readonly action on the page
   *        when retrieving a section (ie: click).
   */
  private <T extends ApplicationSection> SectionContext retrieveSectionContext(
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_) {

    ContextRetriever contextRetriever =
        this._contextRetrieverStrategy.get(sectionToBeRetrievedClass_, this);

    SectionContext sectionContext =
        contextRetriever.retrieveContext(sectionToBeRetrievedClass_, isReadOnlyModeActivated_);

    if (!sectionContext.isValid()) {
      throw new IllegalStateException(String.format("The context retrieved from is invalid!"));
    }

    this._logger.debug("Context retrieved: " + sectionToBeRetrievedClass_.toString());

    return sectionContext;
  }

  private static <T extends ApplicationSection> T createInstance(Class<T> clazz_) {

    T instance = null;

    Constructor<T> ctor;

    // Java 1.6 is not supporting multi-catch exception.
    try {
      ctor = clazz_.getConstructor();
      instance = ctor.newInstance();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return instance;
  }
}
