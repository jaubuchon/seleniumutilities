package com.github.jaubuchon.seleniumutilities.utility.iterable;

import java.util.List;

/**
 * Decorate a {@link List} with the {@link SmartIterable} features.
 * 
 * @param <T> type of list items
 */
public class SmartList<T> extends SmartCollection<T> {

  private List<T> _list;

  public SmartList(List<T> list_) {
    super(list_);
    this._list = list_;
  }

  @Override
  public List<T> toList() {
    return this._list;
  }
}
