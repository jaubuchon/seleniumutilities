package com.github.jaubuchon.seleniumutilities.section;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.jaubuchon.seleniumutilities.utility.webdriver.WebDriverPool;

import javax.annotation.PostConstruct;

@Lazy
@Component
@Scope("prototype")
public class SeleniumClient {
  
  private final Logger _logger = Logger.getLogger(SeleniumClient.class);
  
  @Autowired
  private WebDriverPool _webDriverPool;
  
  @Autowired
  private AbstractSectionProviderFactory _sectionProviderFactory;
  
  private WebDriver _webDriver;
  
  private SectionProvider _sectionProvider;
  
  public WebDriver getWebDriver() {
    return this._webDriver;
  }

  public SectionProvider getSectionProvider() {
    return this._sectionProvider;
  }
  
  /**
   * Dispose this {@link SeleniumClient}.
   */
  public void dispose() {
    this._logger.info("dispose seleniumClient");
    this._webDriverPool.release(this._webDriver);
  }
  
  @PostConstruct
  private void init() {
    this._webDriver = this._webDriverPool.getOne();
    this._sectionProvider = this._sectionProviderFactory.build(this);
  }
  

}
