package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class ApplicationSection {

  protected SeleniumClient _seleniumClient;

  private SectionContext _sectionContext;

  public void setSectionContext(SectionContext sectionContext_) {
    this._sectionContext = sectionContext_;
  }
  
  /**
   * Set the {@link SeleniumClient} to which the {@link ApplicationSection} is associated.
   */
  public void setSeleniumClient(SeleniumClient seleniumClient_) {
    this._seleniumClient = seleniumClient_;
  }

  public SectionContext getSectionContext() {
    return this._sectionContext;
  }

  /**
   * Determines if the section is visible on the page.
   */
  public boolean isVisible() {
    return this.getSectionContext().isValid() && this.getSectionContext().isVisible();
  }

  /**
   * Find an element inside the {@link ApplicationSection}.
   */
  public WebElement find(By by_) {
    return this.getSectionContext().findElement(by_);
  }

  /**
   * Refresh the context of the section.
   */
  protected void refreshSectionContext() {
    this.getSectionProvider().refreshContext(this);
  }
  
  /**
   * Get the {@link SectionProvider} associated to the current {@link SeleniumClient}.
   */
  protected SectionProvider getSectionProvider() {
    return this._seleniumClient.getSectionProvider();
  }
}
