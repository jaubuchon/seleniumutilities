package com.github.jaubuchon.seleniumutilities.list;

import org.openqa.selenium.WebElement;

import java.util.Iterator;

/**
 * Iterate over a list of @see {@link WebElement} and return the appropriate Agent for each items.
 * 
 * @param <T> The type of the Agent
 */
public class SeleniumAgentIterator<T> implements Iterator<T> {

  private Iterator<WebElement> _iterator;
  private ISeleniumListItemFactory<T> _agentFactory;

  public SeleniumAgentIterator(Iterator<WebElement> iterator_,
      ISeleniumListItemFactory<T> agentFactory_) {
    this._iterator = iterator_;
    this._agentFactory = agentFactory_;
  }

  @Override
  public boolean hasNext() {
    return this._iterator.hasNext();
  }

  @Override
  public T next() {
    return this._agentFactory.build(this._iterator.next());
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
