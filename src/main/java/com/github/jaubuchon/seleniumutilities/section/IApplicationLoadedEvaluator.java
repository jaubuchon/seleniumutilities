package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

public interface IApplicationLoadedEvaluator {

  /**
   * Determines if the application is loaded.
   */
  public boolean isApplicationLoaded(SectionContext sectionContext_);
}
