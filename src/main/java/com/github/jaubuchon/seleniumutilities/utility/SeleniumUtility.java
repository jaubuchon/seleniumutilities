package com.github.jaubuchon.seleniumutilities.utility;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class SeleniumUtility {

  /**
   * Verify if an element is visible in an {@link ApplicationSection}.
   */
  public static boolean isElementVisible(
      final ApplicationSection section_, final By searchExpression_) {
    
    return SeleniumUtility.isElementVisible(
        section_.getSectionContext().getSearchContext(), searchExpression_);
  }

  /**
   * Verify if an element is visible in a {@link SectionContext}.
   * 
   * @param sectionContext_ The {@link SectionContext} in which to search in
   * @param searchExpression_ The search expression to use
   * @return true if the element is visible, otherwise false.
   */
  public static boolean isElementVisible(final SectionContext sectionContext_,
      final By searchExpression_) {

    return SeleniumUtility.isElementVisible(sectionContext_.getSearchContext(), searchExpression_);
  }

  /**
   * Verify if an element is visible on the page.
   * 
   * @param searchContext_ The context in which to search in
   * @param searchExpression_ The search expression to use
   * @return true if the element is visible, otherwise false.
   */
  public static boolean isElementVisible(final SearchContext searchContext_,
      final By searchExpression_) {

    return SeleniumUtility.isElementVisible(new IElementAccessor() {

      @Override
      public WebElement findElement() {
        return searchContext_.findElement(searchExpression_);
      }
    });
  }

  /**
   * Verify if an element is visible on the page.
   * 
   * @param elementAccessor_ an element accessor that is using {@link WebElement#findElement(By)} to
   *        search the element
   * @return true if the element is visible, otherwise false.
   */
  public static boolean isElementVisible(IElementAccessor elementAccessor_) {
    
    boolean isDisplayed;
    try {
      WebElement element = elementAccessor_.findElement();
      isDisplayed = element.isDisplayed();
    } catch (NoSuchElementException e) {
      isDisplayed = false;
    }

    return isDisplayed;
  }
  
  /**
   * Verify if an element is available from an {@link ApplicationSection} (not necessarily visible).
   */
  public static boolean isElementAvailable(
      final ApplicationSection section_, final By searchExpression_) {
    
    return SeleniumUtility.isElementAvailable(new IElementAccessor() {

      @Override
      public WebElement findElement() {
        return section_.getSectionContext().findElement(searchExpression_);
      }
    });
  }
  
  /**
   * Determines if an element is available from an {@link IElementAccessor} (not necessarily visible).
   */
  public static boolean isElementAvailable(IElementAccessor elementAccessor_){
    
    boolean isAvailable;
    try {
      elementAccessor_.findElement();
      isAvailable = true;
    } catch (NoSuchElementException e) {
      isAvailable = false;
    }

    return isAvailable;
  }
  
  /**
   * Verify if an element is visible and enabled from an {@link ApplicationSection}.
   */
  public static boolean isElementDisplayedAndEnabled(
      final ApplicationSection section_, final By searchExpression_) {
    
    return SeleniumUtility.isElementDisplayedAndEnabled(new IElementAccessor() {

      @Override
      public WebElement findElement() {
        return section_.getSectionContext().findElement(searchExpression_);
      }
    });
  }
  
  /**
   * Verify if an element is visible and enabled from an {@link IElementAccessor}.
   */
  public static boolean isElementDisplayedAndEnabled(IElementAccessor elementAccessor_){
    
    boolean isVisibleAndEnabled;
    try {
      WebElement element = elementAccessor_.findElement();
      isVisibleAndEnabled = element.isDisplayed() && element.isEnabled();
    } catch (NoSuchElementException e) {
      isVisibleAndEnabled = false;
    }

    return isVisibleAndEnabled;
  }
  

  /**
   * Wait a SHORT period of time until the context is ready.
   */
  public static void waitUntilContextIsReady() {
    SeleniumUtility.waitUntilContextIsReady(WaitOption.MEDIUM);
  }

  /**
   * Wait an arbitrary period of time until the context is ready.
   */
  public static void waitUntilContextIsReady(WaitOption waitOption_) {

    int timeToWait;
    switch (waitOption_) {
      case EXTRA_LONG: 
        timeToWait = 12000;
        break;
      case LONG:
        timeToWait = 7000;
        break;
      case MEDIUM:
        timeToWait = 4000;
        break;
      case SHORT:
        timeToWait = 2000;
        break;
      case EXTRA_SHORT:
        timeToWait = 1000;
        break;
      default:
        throw new IllegalArgumentException();
    }

    try {
      Thread.sleep(timeToWait);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public interface IElementAccessor {
    public WebElement findElement();
  }

  public enum WaitOption {
    EXTRA_LONG, LONG, MEDIUM, SHORT, EXTRA_SHORT
  }
}
