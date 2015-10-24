package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ReadOnlyWebElement;
import com.github.jaubuchon.seleniumutilities.utility.iterable.ISelector;
import com.github.jaubuchon.seleniumutilities.utility.iterable.IterableUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReadOnlySearchContext implements SearchContext {

  private SearchContext _searchContext;

  public ReadOnlySearchContext(SearchContext searchContext_) {
    this._searchContext = searchContext_;
  }

  @Override
  public List<WebElement> findElements(By by_) {

    List<WebElement> webElements = this._searchContext.findElements(by_);

    List<WebElement> readOnlyWebElements =
        IterableUtils.select(webElements, new ISelector<WebElement, WebElement>() {

          @Override
          public WebElement select(WebElement source_) {
            return ReadOnlySearchContext.buildReadOnlyWebElement(source_);
          }
        });

    return readOnlyWebElements;
  }

  @Override
  public WebElement findElement(By by_) {
    WebElement webElement = this._searchContext.findElement(by_);

    return ReadOnlySearchContext.buildReadOnlyWebElement(webElement);
  }

  /**
   * Determine if {@link Theme} given {@link SectionContext} is a {@link ReadOnlyWebElement}.
   */
  public static boolean isReadOnlyWebElement(SearchContext searchContext_) {
    return searchContext_ instanceof ReadOnlyWebElement;
  }

  /**
   * If the {@link WebElement} is not a {@link ReadOnlyWebElement}, creates one and returns it
   * otherwise returns the original {@link WebElement}.
   */
  private static ReadOnlyWebElement buildReadOnlyWebElement(WebElement webElement_) {

    ReadOnlyWebElement readOnlyWebElement;

    if (ReadOnlySearchContext.isReadOnlyWebElement(webElement_)) {
      readOnlyWebElement = (ReadOnlyWebElement) webElement_;
    } else {
      readOnlyWebElement = new ReadOnlyWebElement(webElement_);
    }

    return readOnlyWebElement;
  }
}
