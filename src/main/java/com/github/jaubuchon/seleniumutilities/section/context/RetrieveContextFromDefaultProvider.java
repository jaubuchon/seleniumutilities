package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

/**
 * Retrieve the context of an {@link ApplicationSection} by searching into the context of an existent {@link ApplicationSection}.
 * Supports the {@link RetrieveContext} annotation.
 */
@Component
public class RetrieveContextFromDefaultProvider extends ContextRetriever {

  /**
   * Determines if the retriever support this class.
   */
  public static <T extends ApplicationSection> boolean canRetrieve(Class<T> sectionToBeRetrievedClass_) {

    return RetrieveContextFromDefaultProvider
        .getRetrieveContextAnnotation(sectionToBeRetrievedClass_) != null;
  }

  @Override
  public <T extends ApplicationSection> SectionContext retrieveContext(
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_) {

    RetrieveContext retrieveContextAnnotation =
        RetrieveContextFromDefaultProvider.getRetrieveContextAnnotation(sectionToBeRetrievedClass_);

    return this.retrieveContext(retrieveContextAnnotation, isReadOnlyModeActivated_);
  }


  /**
   * Get the {@link SectionContext} based on the specified {@link RetrieveContext} annotation.
   */
  public SectionContext retrieveContext(RetrieveContext retrieveContextAnnotation_,
      boolean isReadonlyModeActive_) {

    SectionContext context = null;

    By by = this.buildLocatingMechanism(retrieveContextAnnotation_);

    ApplicationSection from =
        this._sectionProvider
            .retrieveSection(retrieveContextAnnotation_.from(), isReadonlyModeActive_);

    if (by != null) {

      WebElement webElement = from.find(by);
      context = new WebElementSectionContext(webElement);
    } else {

      // if there was no locating mechanism specified, get the SectionContext of the "from" section.
      context = from.getSectionContext();
    }

    return context;
  }

  private By buildLocatingMechanism(RetrieveContext retrieveContextAnnotation_) {

    By locatingMechanism = null;

    if (!StringUtils.isEmpty(retrieveContextAnnotation_.id())) {
      locatingMechanism = By.id(retrieveContextAnnotation_.id());
    } else if (!StringUtils.isEmpty(retrieveContextAnnotation_.xpath())) {
      locatingMechanism = By.xpath(retrieveContextAnnotation_.xpath());
    }

    return locatingMechanism;
  }

  private static <T extends ApplicationSection> RetrieveContext getRetrieveContextAnnotation(
      Class<T> sectionToBeRetrievedClass_) {
    return sectionToBeRetrievedClass_.getAnnotation(RetrieveContext.class);
  }
}
