package com.github.jaubuchon.seleniumutilities.section.context;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class SectionContext {

  protected SearchContext _searchContext;

  private Logger _logger = Logger.getLogger(this.getClass());
  private boolean _isReadOnlyModeEnabled = false;

  public SectionContext(SearchContext searchContext_) {
    
    this._searchContext = searchContext_;
  }

  /**
   * Determines if the read only mode is enabled on the context.
   */
  public boolean isReadOnlyModeEnabled() {
    return this._isReadOnlyModeEnabled;
  }

  /**
   * Enable or disable the read only mode on the context.
   */
  public void enableReadOnlyMode(boolean isEnable_) {
    this._logger.debug("The read mode is set to: " + isEnable_);
    this._isReadOnlyModeEnabled = isEnable_;
  }


  /**
   * Find an element inside the context.
   */
  public WebElement findElement(By by_) {
    return this.getSearchContext().findElement(by_);
  }

  /**
   * Find a list of elements inside the context.
   */
  public List<WebElement> findElements(By by_) {
    return this.getSearchContext().findElements(by_);
  }

  /**
   * Get the search context. If the read only mode is activated a {@link ReadOnlySearchContext} is
   * returned.
   */
  public SearchContext getSearchContext() {
    SearchContext searchContext = null;

    if (this.isReadOnlyModeEnabled()) {
      searchContext = new ReadOnlySearchContext(this._searchContext);
    } else {
      searchContext = this._searchContext;
    }

    return searchContext;
  }

  /**
   * Determines if the context is still valid.
   */
  public abstract boolean isValid();

  /**
   * Determines if the context is visible.
   */
  public abstract boolean isVisible();
}
