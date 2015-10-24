package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.Credential;
import com.github.jaubuchon.seleniumutilities.section.Application;
import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContext;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;
import com.github.jaubuchon.seleniumutilities.section.context.WebElementSectionContext;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


@RetrieveContext(from = Application.class)
public class ApplicationSecurity extends ApplicationSection {

  /**
   * Determines if the user is authenticated.
   */
  public boolean isUserAuthenticated() {

    boolean isUserAuthenticated;
    try {
      WebElement logOutButtonElement = this.getLogoutButton();
      isUserAuthenticated = logOutButtonElement.isDisplayed();
    } catch (NoSuchElementException elementError) {
      isUserAuthenticated = false;
    }
    return isUserAuthenticated;
  }

  /**
   * Disconnect the user of the application.
   */
  public void disconnect() {
    WebElement logoutButton = this.getLogoutButton();
    logoutButton.click();
  }

  /**
   * Provide the context to the {@link AuthenticationPage}. It navigates to the Authentication page.
   */
  @ProvideContextTo(AuthenticationPage.class)
  public SectionContext getAuthenticationPageContext() {

    this.ensureDisconnected();

    WebElement body = this.find(By.tagName("body"));

    return new WebElementSectionContext(body);
  }

  /**
   * Provide the context the the {@link AuthenticatedApplication}.
   */
  @ProvideContextTo(AuthenticatedApplication.class)
  public SectionContext getAuthenticatedApplicationContext() {

    AuthenticationPage authenticationPage =
        this.getSectionProvider().retrieveSection(AuthenticationPage.class);

    authenticationPage.authenticate(new Credential("username", "password"));

    return new AuthenticatedApplicationContext(this);
  }

  /**
   * Verify that the client is disconnected. If not, disconnect the client.
   */
  public void ensureDisconnected() {
    if (this.isUserAuthenticated()) {
      this.disconnect();
    }
  }

  private WebElement getLogoutButton() {
    return this.getSectionContext().findElement(By.id("logout"));
  }

  public class AuthenticatedApplicationContext extends SectionContext {

    private ApplicationSecurity _applicationSecurityAgent;

    /**
     * This is building the context given to the {@link AuthenticatedApplication}. 
     * The context is valid until the user is connected.
     */
    public AuthenticatedApplicationContext(ApplicationSecurity applicationSecurityAgent_) {

      super(applicationSecurityAgent_.getSectionContext().getSearchContext());
      this._applicationSecurityAgent = applicationSecurityAgent_;
    }

    @Override
    public boolean isValid() {
      return this._applicationSecurityAgent.isUserAuthenticated();
    }

    @Override
    public boolean isVisible() {
      return this._applicationSecurityAgent.isVisible();
    }
  }
}
