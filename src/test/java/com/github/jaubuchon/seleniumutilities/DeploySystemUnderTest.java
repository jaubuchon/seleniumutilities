package com.github.jaubuchon.seleniumutilities;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class DeploySystemUnderTest {

  /**
   * Stub the system under test with Wiremock in order to make it accessible from the browser.
   */
  public static void deploy() {
    DeploySystemUnderTest.deploy("login.htm", "text/html");
    DeploySystemUnderTest.deploy("application.htm", "text/html");

    DeploySystemUnderTest.deploy("js/bootstrap.min.js", "text/javascript");
    DeploySystemUnderTest.deploy("js/jquery-2.1.4.min.js", "text/javascript");
    DeploySystemUnderTest.deploy("js/knockout-3.3.0.js", "text/javascript");
    DeploySystemUnderTest.deploy("css/bootstrap.min.css", "text/css");

  }

  private static void deploy(String fileName_, String contentType_) {

    String bodyFile = String.format("system-under-test/%s", fileName_);

    stubFor(get(urlEqualTo("/" + fileName_)).willReturn(
        aResponse().withStatus(200).withHeader("Content-Type", contentType_).withBodyFile(bodyFile)));
  }
}
