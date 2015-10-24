package com.github.jaubuchon.seleniumutilities.section.context;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;


/**
 * A {@link SectionContext} created from a {@link SearchContext} element.
 */
public class WebDriverSectionContext extends SectionContext {

  private WebDriver _webDriver;

  public WebDriverSectionContext(WebDriver webDriver_) {
    super(webDriver_);
    this._webDriver = webDriver_;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public boolean isVisible() {
    return true;
  }

  /**
   * Get the encapsulated {@link WebDriver}.
   */
  public WebDriver getWebDriver() {
    return this._webDriver;
  }
}
