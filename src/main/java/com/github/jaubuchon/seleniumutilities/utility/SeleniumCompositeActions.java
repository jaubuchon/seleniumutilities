package com.github.jaubuchon.seleniumutilities.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

/*
 * @author asforza Methods based on Selenium to reduce workload for recurring Selenium actions
 */
public class SeleniumCompositeActions {

  private static String lengthOfMessage = "80";
  private static final Logger logger = Logger.getLogger(SeleniumCompositeActions.class);

  @SuppressWarnings("unused")
  private static void setLengthOfMessage(String lom_) {
    lengthOfMessage = lom_;
  }

  /**
   * Ensure the webElement visibility.
   * @param elementTagType_ : the tagName of all the elements to focus visibility on
   * @param tagName_ : the tagName of the element to find to focus on
   * @param tagValue_ : the value of the parameter tagName
   * @param targetedElement_ : the web element that contain all sub elements to find and focus
   */
  public static void ensureWebElementVisibility(String elementTagType_, String tagName_,
      String tagValue_, WebElement targetedElement_) {
    List<WebElement> webElements = getAllChildsByTagname(targetedElement_, elementTagType_);
    for (WebElement we : webElements) {
      if (we.getAttribute(tagName_).equalsIgnoreCase(tagValue_)) {
        if (!targetedElement_.isDisplayed()) {
          clickElementNoWait(we);
        }
        break;
      }
    }
  }

  /**
   * Cycle through a List of WebElement and click them in sequence until the targeted element is
   * visible.
   * 
   * @param webElements_ a list of clickable webElement
   * @param targetedElement_ the element wanted to be visible
   */
  public static void ensureWebElementVisibility(List<WebElement> webElements_,
      WebElement targetedElement_) {
    for (WebElement we : webElements_) {
      if (ensureWebElementVisibility(we, targetedElement_)) {
        break;
      }
    }
  }


  /**
   * To get a web element into the status visible..
   * 
   * @param toggleElement_ a clickable web element that makes another element visible
   * @param targetedElement_ the element wanted to be visible
   * @return true if the targeted element is visible
   */
  public static boolean ensureWebElementVisibility(WebElement toggleElement_,
      WebElement targetedElement_) {
    if (!targetedElement_.isDisplayed()) {
      clickElementNoWait(toggleElement_);
    }
    if (targetedElement_.isDisplayed()) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * From a given element, will extract only the web elements matching the tagname provided.
   * 
   * @param parent_
   * @param child_
   * @return ArrayList of webElement of the requested tagName
   */
  public static List<WebElement> getAllChildsByTagname(WebElement parent_, String child_) {
    return parent_.findElements(By.tagName(child_));
  }

  /**
   * Parse a string into BigDecimal.
   * 
   * @param str_
   * @return null if not a number, otherwise the value
   */
  public static BigDecimal parseStringToBigDecimal(String str_) {
    BigDecimal bd = null;
    try {
      str_ = str_.replaceAll("[^\\d.-]+", "");
      bd = BigDecimal.valueOf(Double.parseDouble(str_));
    } catch (NumberFormatException nfe) {
      SeleniumCompositeActions.logger.error(nfe);
    }
    return bd;
  }


  /**
   * Simulate a user click on a web element and wait maximum 5 seconds for the element visibility.
   * 
   * @param element_
   */
  public static void clickElementWaitIsDisplayed(WebElement element_) {
    element_.click();
    // wait for the web element to turn on (in case of ajax delay)
    long maxTime = 5 * 1000; // time in milliseconds
    long timeSlice = 250;
    long elapsedTime = 0;
    try {
      boolean result = element_.isDisplayed();
      while (!result && elapsedTime < maxTime) {
        Thread.sleep(timeSlice);
        elapsedTime += timeSlice;
        result = element_.isDisplayed();
      }
    } catch (Exception e) {
      throw new NoSuchElementException("The web element was not found or cannot be displayed");
    }
  }


  /**
   * Simply simulate a user click without waiting for the element to be visible.
   * 
   * @param element_ a clickable web element
   */
  public static void clickElementNoWait(WebElement element_) {
    element_.click();
  }


  /**
   * Log a webElement with variable length.
   * 
   * @param webElement_ the element to be inspected
   * @param fromClass_ if the reference to that class logger
   * @param trigger_ the value used to find the web element
   */
  public static String webElementLogger(WebElement webElement_, Logger fromClass_, String trigger_) {
    int end = Integer.valueOf(lengthOfMessage);
    logger.info("InnerHTML characters length showed is limited to (set by a bean) : " + end);
    String innerHtml = webElement_.getAttribute("innerHTML");
    fromClass_.info("Found WebElement <" + webElement_.getTagName() + "> by trigger : " + trigger_);
    if (!innerHtml.isEmpty()) {
      end = end < innerHtml.length() ? end : innerHtml.length();
      innerHtml = innerHtml.substring(0, end - 1).replaceAll("[\n\r]", "").trim();
    }
    fromClass_.info("WebElement innerHTML is : " + innerHtml);
    return innerHtml;
  }
}
