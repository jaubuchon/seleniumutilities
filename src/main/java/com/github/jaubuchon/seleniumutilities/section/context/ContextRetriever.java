package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.SectionProvider;

public abstract class ContextRetriever {

  SectionProvider _sectionProvider;
  
  /**
   * Retrieve the section context of the sectionToBeRetrieved
   * 
   * @param sectionToBeRetrievedClass_ the class representing the section to be retrieved
   * @param isReadOnlyModeActivated_ the readonly mode disable all non-readonly action on the page
   *        when retrieving a section (ie: click).
   */
  public abstract <T extends ApplicationSection> SectionContext retrieveContext(
      Class<T> sectionToBeRetrievedClass_, boolean isReadOnlyModeActivated_);
  
  /**
   * Configure the section provider to use when retrieving a context.
   */
  public void configureSectionProvider(SectionProvider sectionProvider_){
    this._sectionProvider = sectionProvider_;
  }

}
