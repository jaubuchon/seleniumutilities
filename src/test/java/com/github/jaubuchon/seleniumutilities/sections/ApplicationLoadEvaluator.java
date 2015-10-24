package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.section.IApplicationLoadedEvaluator;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;
import com.github.jaubuchon.seleniumutilities.utility.SeleniumUtility;

import org.openqa.selenium.By;

public class ApplicationLoadEvaluator implements IApplicationLoadedEvaluator {

  @Override
  public boolean isApplicationLoaded(SectionContext sectionContext_) {
    return SeleniumUtility.isElementVisible(sectionContext_, By.id("system-under-test"));
  }

}
