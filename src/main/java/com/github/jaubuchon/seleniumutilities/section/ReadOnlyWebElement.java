package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.ReadOnlySearchContext;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * A {@link WebElement} that is read only.
 * All the methods that are changing the state of the page are deactivated (ie: click, sendKeys and
 * submit)
 */
public class ReadOnlyWebElement implements WebElement {

  private WebElement _webElement;
  private ReadOnlySearchContext _readOnlySearchContext;

  private final Logger _logger = Logger.getLogger(ReadOnlyWebElement.class);

  public ReadOnlyWebElement(WebElement webElement_) {
    this._webElement = webElement_;
    this._readOnlySearchContext = new ReadOnlySearchContext(webElement_);
  }

  /**
   * Get the original {@link WebElement} that is not read only.
   */
  public WebElement getOriginalWebElement() {
    return this._webElement;
  }

  @Override
  public void click() {

    this._logger.debug("Tried to click on the webElement, but was in readonly mode. "
        + this._webElement.toString());
  }

  @Override
  public void submit() {

    this._logger.debug("Tried to submit on the webElement, but was in readonly mode. "
        + this._webElement.toString());
  }

  @Override
  public void sendKeys(CharSequence... keysToSend_) {

    this._logger.debug("Tried to sendKeys on the webElement, but was in readonly mode. "
        + this._webElement.toString());
  }

  @Override
  public void clear() {
    // read only, do nothing
  }

  @Override
  public String getTagName() {
    return this._webElement.getTagName();
  }

  @Override
  public String getAttribute(String name_) {
    return this._webElement.getAttribute(name_);
  }

  @Override
  public boolean isSelected() {
    return this._webElement.isSelected();
  }

  @Override
  public boolean isEnabled() {
    return this._webElement.isEnabled();
  }

  @Override
  public String getText() {
    return this._webElement.getText();
  }

  @Override
  public List<WebElement> findElements(By by_) {

    return this._readOnlySearchContext.findElements(by_);
  }

  @Override
  public WebElement findElement(By by_) {

    return this._readOnlySearchContext.findElement(by_);
  }

  @Override
  public boolean isDisplayed() {
    return this._webElement.isDisplayed();
  }

  @Override
  public Point getLocation() {
    return this._webElement.getLocation();
  }

  @Override
  public Dimension getSize() {
    return this._webElement.getSize();
  }

  @Override
  public String getCssValue(String propertyName_) {
    return this._webElement.getCssValue(propertyName_);
  }
}
