package com.github.jaubuchon.seleniumutilities.weblist;

import com.github.jaubuchon.seleniumutilities.list.ISeleniumListItemFactory;
import com.github.jaubuchon.seleniumutilities.list.SeleniumAgentIterable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class StatisticsWebList extends SeleniumAgentIterable<Statistic> {

  private WebElement _webElement;

  public StatisticsWebList(WebElement webElement_) {
    this._webElement = webElement_;
  }

  /**
   * Get the name (the English description) of the items contained inside this Iterable.
   */
  @Override
  protected String getItemsName() {

    return "Statistic";
  }

  /**
   * Get the @see {@link WebElement} container of the list to search on.
   */
  @Override
  protected WebElement getListContainer() {
    return this._webElement;
  }

  /**
   * Get a search expression that return all {@link WebElement} items of the list.
   */
  @Override
  protected By getAllItemsSearchExpression() {
    return By.xpath("./tbody/tr");
  }

  /**
   * Get The Factory that will be used to build a concrete  list element from a {@link WebElement} item.
   */
  @Override
  protected ISeleniumListItemFactory<Statistic> getSeleniumListItemFactory() {
    return new ISeleniumListItemFactory<Statistic>() {

      @Override
      public Statistic build(WebElement webElement_) {
        return new Statistic(webElement_);
      }
    };
  }

}
