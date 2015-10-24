package com.github.jaubuchon.seleniumutilities.section;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("prototype")
public class SectionCache {

  private final Logger _logger = Logger.getLogger(SectionCache.class);

  HashMap<Class<? extends ApplicationSection>, ApplicationSection> _dictionnary =
      new HashMap<Class<? extends ApplicationSection>, ApplicationSection>();

  /**
   * Add to the cache the specified section.
   */
  public <T extends ApplicationSection> void add(T section_) {
    this._dictionnary.put(section_.getClass(), section_);
  }

  /**
   * Sanitize and resolve from the cache the specified section.
   */
  public ApplicationSection sanitizeAndresolve(Class<? extends ApplicationSection> section_) {

    this.sanitize(section_);

    ApplicationSection result = this._dictionnary.get(section_);

    if (result != null) {
      this._logger.debug("The section was cached and his context still valid");
    }

    return result;
  }

  /**
   * Sanitize the cache for the specified {@link ApplicationSection}: If the context of the
   * {@link ApplicationSection} is not valid anymore, it invalidate the section from the cache.
   */
  public void sanitize(Class<? extends ApplicationSection> section_) {

    ApplicationSection sectionInstance = this._dictionnary.get(section_);

    if (sectionInstance != null) {

      if (!sectionInstance.getSectionContext().isValid()
          || !sectionInstance.getSectionContext().isVisible()) {
        this.invalidateFromCache(section_);
      }
    }
  }

  /**
   * Invalidate a section from the cache.
   */
  public void invalidateFromCache(Class<? extends ApplicationSection> section_) {
    this._dictionnary.remove(section_);
    this._dictionnary.get("The section was cleared from the cache");
  }
}
