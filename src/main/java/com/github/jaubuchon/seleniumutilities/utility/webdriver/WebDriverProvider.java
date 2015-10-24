//package com.github.jaubuchon.seleniumutilities.utility.webdriver;
//
//import com.vmd.selenium.section.SeleniumClientManager;
//import com.vmd.selenium.webdriver.WebDriverUtils;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class WebDriverProvider {
//  
//  @Autowired
//  SeleniumClientManager _seleniumClientManager;
//
//  /**
//   * Get the webDriver of the current test session in a static context. Useful when you are in a
//   * class that his instantiation is not managed by spring
//   */
//  public static WebDriver getWebDriver() {
//    return WebAppTestSession.getInstance().getSeleniumClientManager().get().getWebDriver();
//  }
//  
//  public static WebDriverWait getWebDriverWait() {
//
//    return WebDriverUtils.buildWebDriverWait(WebDriverProvider.getWebDriver(), 10, 250);
//  }
//
//  /**
//   * Get a {@link WebDriverWait}.
//   */
//  public static WebDriverWait getWebDriverWait(int timeoutSeconds_, int pollingMiliSeconds_) {
//
//    return WebDriverUtils.buildWebDriverWait(WebDriverProvider.getWebDriver(), timeoutSeconds_,
//        pollingMiliSeconds_);
//  }
//}
