package com.github.jaubuchon.seleniumutilities.list;

import org.openqa.selenium.WebElement;

/**
 * Factory for building an Agent from a @see {@link WebElement}.
 * 
 * @param <T> The type of the agent to build
 */
public interface ISeleniumListItemFactory<T> {
  
  /**
   * Build an agent from a @see {@link WebElement}.
   * 
   * @param webElement_ the webElement on which the agent operate
   * @return the built Agent
   */
  T build(WebElement webElement_);
}
