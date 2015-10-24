package com.github.jaubuchon.seleniumutilities.utility.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebDriverUtils {

  public static WebDriverWait buildWebDriverWait(WebDriver webDriver_) {

    return WebDriverUtils.buildWebDriverWait(webDriver_, 10, 250);
  }

  public static WebDriverWait buildWebDriverWait(WebDriver webDriver_, int timeoutSeconds_,
      int pollingMiliSeconds_) {

    return new WebDriverWait(webDriver_, timeoutSeconds_, pollingMiliSeconds_);
  }

  public static boolean isHtmlUnitDriverInUse(WebDriver webDriver_) {
    return webDriver_.getClass().toString()
        .equalsIgnoreCase("class org.openqa.selenium.htmlunit.HtmlUnitDriver");
  }
}
