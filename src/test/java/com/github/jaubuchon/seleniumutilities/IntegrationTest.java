package com.github.jaubuchon.seleniumutilities;

import com.github.jaubuchon.seleniumutilities.section.AbstractSectionProviderFactory;
import com.github.jaubuchon.seleniumutilities.section.ApplicationContextProvider;
import com.github.jaubuchon.seleniumutilities.section.SectionProvider;
import com.github.jaubuchon.seleniumutilities.section.SectionUtility;
import com.github.jaubuchon.seleniumutilities.section.SeleniumClient;
import com.github.jaubuchon.seleniumutilities.section.SeleniumClientManager;
import com.github.jaubuchon.seleniumutilities.section.context.WebDriverSectionContext;
import com.github.jaubuchon.seleniumutilities.sections.ApplicationLoadEvaluator;
import com.github.jaubuchon.seleniumutilities.utility.webdriver.WebDriverPool;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class IntegrationTest {

  protected WebDriver _webDriver;

  @Autowired
  protected SectionUtility _sectionUtility;
  
  @Autowired
  SeleniumClientManager _seleniumClientManager;

  @Autowired
  private WebDriverPool _webDriverPool;

  @Rule
  public WireMockRule _wireMockRule = new WireMockRule(IntegrationTest.buildWireMockConfig());

  /**
   * Setup a test scenario.
   */
  @Before
  public void setUp() {

    DeploySystemUnderTest.deploy();
  }

  /**
   * Teardown a test scenario.
   */
  @After
  public void tearDown() throws Exception {

    this._seleniumClientManager.releaseAllClients();
  }

  private static WireMockConfiguration buildWireMockConfig() {

    WireMockConfiguration config = new WireMockConfiguration();

    return config.port(8089);
  }
  
  public static class SectionProviderFactoryFakeImpl extends AbstractSectionProviderFactory{

    @Override
    protected void init(SectionProvider sectionProvider_, SeleniumClient seleniumClient_) {
      
      ApplicationContextProvider rootProvider = new ApplicationContextProvider();
      rootProvider.setSectionContext(new WebDriverSectionContext(seleniumClient_.getWebDriver()));
      rootProvider.setApplicationLoadEvaluator(new ApplicationLoadEvaluator());
      rootProvider.setApplicationUrl("http://localhost:8089/login.htm");
      
      sectionProvider_.registerRootProvider(rootProvider);
    }
  }

}
