package com.github.jaubuchon.seleniumutilities.list;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.github.jaubuchon.seleniumutilities.utility.iterable.IPredicate;

/**
 * An optimized version of the @see {@link SeleniumAgentIterable}.
 */
public abstract class OptimizedSeleniumAgentList<T> extends SeleniumAgentIterable<T> {
  
  /**
   * Get search expression that return the first item in the list that is matching the predicate.
   * @param predicate_ encapsulate the attribute(s) on which the search operate
   * @return 
   */
  protected abstract By getFirstItemSearchExpression(IPredicate<T> predicate_);

  /*
   * This is a performance optimization of the SeleniumAgentIterable#single(IPredicate<T>). It uses
   * the xPath optimized search engine of Selenium to search for the element that match the
   * predicate.
   * 
   * TODO: throw an IllegalStateException exception if there is more than one element that fit the
   * condition (for now we are only looking at the first element)
   */
  @Override
  public T single(IPredicate<T> predicate_) {

    this._logger.debug(String.format("Trying to get the single %s that match the predicate [%s]",
        this.getItemsName(), predicate_.toString()));

    WebElement listItemWebElement = null;

    try {
      listItemWebElement = this.first(predicate_);

    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(String.format(
          "No element in the sequence satisfies satisfies the condition of the predicate \"%s\"",
          predicate_));
    }

    this._logger.debug(String.format("Successfully found the single %s that match the predicate",
        this.getItemsName()));

    return this.getSeleniumListItemFactory().build(listItemWebElement);
  }

 
  /*
   * This is a performance optimization of the SeleniumAgentIterable#firstOrDefault(IPredicate<T>).
   * It uses the xPath optimized search engine of Selenium to search for the element that match the
   * predicate.
   */
  @Override
  public T firstOrDefault(IPredicate<T> predicate_) {
    T result;

    try {
      result = this.getSeleniumListItemFactory().build(this.first(predicate_));

    } catch (NoSuchElementException e) {
      result = null;
    }

    return result;
  }

  private WebElement first(IPredicate<T> predicate_) {
    return this.getListContainer().findElement(this.getFirstItemSearchExpression(predicate_));
  }
}
