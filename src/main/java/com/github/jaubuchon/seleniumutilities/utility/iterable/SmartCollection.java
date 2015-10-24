package com.github.jaubuchon.seleniumutilities.utility.iterable;

import java.util.Collection;
import java.util.Iterator;

/**
 * Decorate a {@link Collection} with the {@link SmartIterable} features.
 */
public class SmartCollection<T> extends SmartIterable<T> {

  private Collection<T> _collection;

  public SmartCollection(Collection<T> collection_) {
    this._collection = collection_;
  }

  @Override
  public Iterator<T> iterator() {
    return this._collection.iterator();
  }
}
