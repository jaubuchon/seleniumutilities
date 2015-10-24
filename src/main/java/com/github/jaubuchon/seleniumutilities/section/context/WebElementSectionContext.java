package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ReadOnlyWebElement;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;


public class WebElementSectionContext extends SectionContext {

  private WebElement _webElement;

  /**
   * A {@link SectionContext} created from a {@link WebElement} element.
   */
  public WebElementSectionContext(WebElement webElement_) {
    super(WebElementSectionContext.getNonReadonlyElement(webElement_));

    this._webElement = WebElementSectionContext.getNonReadonlyElement(webElement_);
  }

  @Override
  public boolean isValid() {

    boolean isValid = false;
    try {
      this._webElement.isEnabled();
      isValid = true;
    } catch (StaleElementReferenceException e) {
      isValid = false;
    }

    return isValid;
  }

  @Override
  public boolean isVisible() {
    return this._webElement.isDisplayed();
  }

  /**
   * Since the context provider can return a {@link ReadOnlyWebElement} as a context, we need to
   * ensure that the given webElement is not a {@link ReadOnlyWebElement}.
   */
  private static WebElement getNonReadonlyElement(WebElement webElement_) {

    WebElement notReadonlyWebElement = null;

    if (ReadOnlySearchContext.isReadOnlyWebElement(webElement_)) {
      ReadOnlyWebElement readOnlyWebElement = (ReadOnlyWebElement) webElement_;
      notReadonlyWebElement = readOnlyWebElement.getOriginalWebElement();
    } else {
      notReadonlyWebElement = webElement_;
    }

    return notReadonlyWebElement;
  }
}
