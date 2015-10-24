package com.github.jaubuchon.seleniumutilities.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provide utility methods to retrieve sections inside a {@link SeleniumClient}.
 */
@Component
public class SectionUtility {

  @Autowired
  private SeleniumClientManager _seleniumClientManager;

  /**
   * Retrieve a section from the main {@link SeleniumClient}.
   */
  public <T extends ApplicationSection> T retrieve(Class<T> sectionToBeRetrievedClass_) {

    return this.retrieve(sectionToBeRetrievedClass_, SeleniumClientManager.MAIN_CLIENT_KEY);
  }
  
  /**
   * Retrieve a section from a specific {@link SeleniumClient}.
   */
  public <T extends ApplicationSection> T retrieve(Class<T> sectionToBeRetrievedClass_,
      String seleniumClientKey_) {

    SectionProvider sectionProvider = this.getSectionProvider(seleniumClientKey_);

    return sectionProvider.retrieveSection(sectionToBeRetrievedClass_);
  }
  
  /**
   * Read a section from the main {@link SeleniumClient} (readonly mode).
   */
  public <T extends ApplicationSection> T read(Class<T> sectionToBeRetrievedClass_) {

    return this.read(sectionToBeRetrievedClass_, SeleniumClientManager.MAIN_CLIENT_KEY);
  }
  
  /**
   * Read a section from a specific {@link SeleniumClient} (readonly mode).
   */
  public <T extends ApplicationSection> T read(Class<T> sectionToBeRetrievedClass_,
      String seleniumClientKey_) {

    SectionProvider sectionProvider = this.getSectionProvider(seleniumClientKey_);

    return sectionProvider.retrieveSectionWithReadOnlyMode(sectionToBeRetrievedClass_);
  }
  
  /**
   * Invalidate a section from the cache.
   */
  public <T extends ApplicationSection> void invalidateSection(Class<T> section_) {

    this.invalidateSection(section_, SeleniumClientManager.MAIN_CLIENT_KEY);
  }
  
  /**
   * Invalidate a section from the cache.
   */
  public <T extends ApplicationSection> void invalidateSection(Class<T> section_,
      String seleniumClientKey_) {

    SectionProvider sectionProvider = this.getSectionProvider(seleniumClientKey_);

    sectionProvider.invalidateSection(section_);
  }

  private SectionProvider getSectionProvider(String clientKey_) {

    return this._seleniumClientManager.get(clientKey_).getSectionProvider();
  }
}
