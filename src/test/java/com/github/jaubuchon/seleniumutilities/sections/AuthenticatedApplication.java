package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;
import com.github.jaubuchon.seleniumutilities.section.context.WebElementSectionContext;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RetrieveContextFrom(ApplicationSecurity.class)
public class AuthenticatedApplication extends ApplicationSection {

  /**
   * Provide the context to the {@link UserProfileTab}.
   */
  @ProvideContextTo(UserProfileTab.class)
  public SectionContext getUserProfileContext() {

    // select the user profile tab and retrieve the DOM element 
    // encapsulating the tab content
    WebElement tabContent = this.ensureTabIsSelected("user-profile");

    // return the DOM Element encapsulating the tab content (aka the context)
    return new WebElementSectionContext(tabContent);
  }

  /**
   * Provide the context to the {@link AddPlayerOrMatchTab}.
   */
  @ProvideContextTo(AddPlayerOrMatchTab.class)
  public SectionContext getAddPlayerOrMatchTabContext() {

    return this.getTabContext("add");
  }

  /**
   * Provide the context to the {@link StatisticTab}.
   */
  @ProvideContextTo(StatisticTab.class)
  public SectionContext getStatisticTabContext() {

    return this.getTabContext("stats");
  }

  private SectionContext getTabContext(String tabId_) {
    WebElement tabContent = this.ensureTabIsSelected(tabId_);

    return new WebElementSectionContext(tabContent);
  }

  /**
   * Select the specified tab and return his content.
   */
  private WebElement ensureTabIsSelected(String tabId_) {

    this.getTabButton(tabId_).click();

    return this.find(By.id(tabId_));
  }

  private WebElement getTabButton(String tabId_) {

    String xpath = String.format(".//*[@data-type='menu']//a[@href='#%s']", tabId_);
    return this.find(By.xpath(xpath));
  }

}
