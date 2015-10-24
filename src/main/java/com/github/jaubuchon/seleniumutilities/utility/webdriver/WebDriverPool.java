package com.github.jaubuchon.seleniumutilities.utility.webdriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebDriverPool {

  private final Logger _logger = Logger.getLogger(WebDriverPool.class);

  @Autowired
  private WebDriverFactory _webDriverFactory;

  /**
   * Get one {@link WebDriver} from the pool.
   */
  public WebDriver getOne() {

    WebDriver webDriver = this._webDriverFactory.build();
    return webDriver;
  }

  /**
   * Release a {@link WebDriver} to the pool.
   */
  public void release(WebDriver webDriver_) {

    try {
      // webDriver.close();
      webDriver_.quit();
    } catch (Exception exception) {
      this._logger.error("unable to close or quit the driver. Got an exception: "
          + exception.getMessage());
      exception.printStackTrace();
    }
  }
}
