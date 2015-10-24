package com.github.jaubuchon.seleniumutilities.utility.webdriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
  private final Logger _logger = Logger.getLogger(WebDriverFactory.class);
  private static final String _IEXPLORER = "iexplorer";
  private static final String _FIREFOX = "firefox";
  private static final String _CHROME = "chrome";
  private DesiredCapabilities _cap = new DesiredCapabilities();
  private String _browser = "";
  private org.openqa.selenium.Proxy _proxy;
  private String _binaryPath;
  private String _screenSize;

  /**
   * Constructor with mandatory browser designation and optional proxy and binaryPath designation
   * 
   * @param browser_ one of "firefox", "iexplorer", "chrome" or "" for the Selenium built-in html web
   *        driver.
   * @param screenSize_ full or default ( "")
   */
  public WebDriverFactory(String browser_, String proxy_, String binaryPath_, String screenSize_) {
    this._browser = browser_.toLowerCase();

    if (!proxy_.isEmpty()) {
      setProxy(proxy_);
    }

    this._binaryPath = binaryPath_;
    _logger.info("Setting the proxy to : " + proxy_);

    this._screenSize = screenSize_;

    this.setupCapabilities();
  }

  /**
   * start the driver.
   */
  public WebDriver build() {
    WebDriver webDriver = null;

    _logger.info("Launching driver: " + _browser);
    if (this._browser.equals(_IEXPLORER)) {
      webDriver = new InternetExplorerDriver(_cap);

    } else if (this._browser.equals(_FIREFOX)) {
      
      /*
       * Need to use a already existent profile because we don't want to enter 
       * the proxy username/password each time we run
       * a test...
      FirefoxProfile firefoxProfile = new FirefoxProfile();
      
      firefoxProfile.setPreference("network.proxy.autoconfig_url", "http://www.desjardins.com/c0nf1g/1q2w3e/pr0xy/pr0xy_mcafee.js");
      firefoxProfile.setPreference("network.proxy.type", 2);
      firefoxProfile.setPreference("signon.autologin.proxy", true);
      
      webDriver = new FirefoxDriver(new FirefoxBinary(), firefoxProfile);
      */
      
      // TODO configure firefox driver to be configured wihtout loading the user's default (ie
      // inject proxy)
      ProfilesIni profileIni = new ProfilesIni();
      FirefoxProfile profile = profileIni.getProfile("default");
      
      //profile.setPreference("network.proxy.autoconfig_url", "http://www.desjardins.com/c0nf1g/1q2w3e/pr0xy/pr0xy_mcafee.js");
      //profile.setPreference("network.proxy.type", 2);
      //profile.setPreference("signon.autologin.proxy", true);
      
      //profile.setPreference("extensions.firebug.currentVersion","1.8.2");
      webDriver = new FirefoxDriver(new FirefoxBinary(), profile, _cap);
    } else if (this._browser.equals(_CHROME)) {

      if (!this._binaryPath.isEmpty()) {
        // ChromeOptions options = new ChromeOptions();

        // options.setBinary(this.binaryPath);
        System.setProperty("webdriver.chrome.driver", this._binaryPath);

        // cap.setCapability(ChromeOptions.CAPABILITY, options);
      }
      webDriver = new ChromeDriver(_cap);
    } else {

      HtmlUnitDriver dr = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
      dr.setProxySettings(this._proxy);
      dr.setJavascriptEnabled(true);
      webDriver = dr;
    }
    // webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    // webDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

    if (this._screenSize.equalsIgnoreCase("full")) {
      //don't know why, but the maximize button is disabled on centos
      //webDriver.manage().window().maximize();
      webDriver.manage().window().setSize(new Dimension(1288, 1032));
      webDriver.manage().window().setPosition(new Point(0, 0));
    }

    return webDriver;
  }

  public void setProxy(String proxy_) {
    this._proxy = new org.openqa.selenium.Proxy();
    this._proxy.setHttpProxy(proxy_).setFtpProxy(proxy_).setSslProxy(proxy_);
  }

  /**
   * set the driver to work through a proxy and firefox.
   */
  private void setupCapabilities() {
    if (this._proxy != null) {
      _cap.setCapability(CapabilityType.PROXY, this._proxy);
    }
  }

}
