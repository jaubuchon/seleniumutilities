package com.github.jaubuchon.seleniumutilities.list;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jaubuchon.seleniumutilities.utility.iterable.IPredicate;
import com.github.jaubuchon.seleniumutilities.utility.iterable.SmartIterable;

import java.util.Iterator;
import java.util.List;

/**
 * Iterates over a list of @see {@link WebElement} and return a list of Selenium Agents.
 * 
 * @param <T>
 */
public abstract class SeleniumAgentIterable<T> extends SmartIterable<T> {

  protected final Logger _logger = Logger.getLogger(this.getClass());

  @Override
  public T first() {
    this._logger
        .debug(String.format("Trying to get the first %s of the list", this.getItemsName()));

    T result = super.first();

    this._logger.debug(String.format("Successfully got the first %s of the list (%s)",
        this.getItemsName(), result.toString()));

    return result;
  }

  @Override
  public T single() {
    this._logger
        .debug(String.format("Trying to get the single %s of the list", this.getItemsName()));

    T result = super.single();

    this._logger.debug(String.format("Successfully found the single %s of the list (%s)",
        this.getItemsName(), result.toString()));

    return result;
  }

  @Override
  public T single(IPredicate<T> predicate_) {
    this._logger.debug(String.format(
        "Trying to get the single %s that match the predicate %s in the list", this.getItemsName(),
        predicate_.toString()));

    T result = super.single(predicate_);

    this._logger.debug(String.format("Successfully got the single %s of the list (%s)",
        this.getItemsName(), result.toString()));

    return result;
  }

  /**
   * Get the name (the English description) of the items contained inside this sIterable.
   * 
   * @return a name (an English description)
   */
  protected abstract String getItemsName();

  /**
   * @return the @see {@link WebElement} container of the list to search on.
   */
  protected abstract WebElement getListContainer();

  /**
   * @return a search expression that return all {@link WebElement} items of the list.
   */
  protected abstract By getAllItemsSearchExpression();

  /**
   * @return The Factory that will be used to build a Selenium Agent from a {@link WebElement} item.
   */
  protected abstract ISeleniumListItemFactory<T> getSeleniumListItemFactory();

  @Override
  public Iterator<T> iterator() {
    List<WebElement> items =
        this.getListContainer().findElements(this.getAllItemsSearchExpression());

    this._logger.debug(String.format(
        "Preparing to iterate over the list of %s (size of the list = %s)", this.getItemsName(),
        items.size()));

    // TODO: use the APIAgentIterable instead
    return new SeleniumAgentIterator<T>(items.iterator(), this.getSeleniumListItemFactory());
  }
}
