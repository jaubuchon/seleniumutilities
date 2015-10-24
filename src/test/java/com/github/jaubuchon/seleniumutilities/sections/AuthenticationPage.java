package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.Credential;
import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


@RetrieveContextFrom(ApplicationSecurity.class)
public class AuthenticationPage extends ApplicationSection {

  /**
   * Authenticate the user with the specified credential.
   */
  public void authenticate(Credential credential_) {

    this.getFirstNameField().sendKeys(credential_.getUsername());
    this.getPasswordField().sendKeys(credential_.getPassword());
    this.getSubmitButton().click();
  }

  private WebElement getFirstNameField() {

    return this.find(By.id("firstname"));
  }

  private WebElement getPasswordField() {

    return this.find(By.id("lastname"));
  }

  private WebElement getSubmitButton() {

    return this.find(By.xpath(".//button[@type='submit']"));
  }
}
