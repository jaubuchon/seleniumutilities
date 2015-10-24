package com.github.jaubuchon.seleniumutilities.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;

/**
 * This is the Page Object that is encapsulating the
 * interactions with the User Profile tab.
 */
@RetrieveContextFrom(AuthenticatedApplication.class)
public class UserProfileTab extends ApplicationSection {
  
  /**
   * Update the email address of the user by entering it 
   * in the email textbox and clicking the submit button.
   */
  public void updateEmail(String email) {
    
    WebElement emailTextbox = this.find(By.id("email-textbox"));
    emailTextbox.sendKeys(email);
  }
  
  /**
   * Get the email address of the user displayed in the user profile section.
   */
  public String getDisplayedEmail() {
    
    WebElement emailField = this.find(By.id("email-field"));
    return emailField.getText();
  }
}
