package com.github.jaubuchon.seleniumutilities.utility.iterable;

import java.util.List;

/**
 * An {@link Iterable} decorated with useful methods.
 */
public abstract class SmartIterable<T> implements Iterable<T> {

  /**
   * @see IterableUtils#first(Iterable).
   */
  public T first() {
    return IterableUtils.first(this);
  }

  /**
   * @see IterableUtils#firstOrDefault(Iterable, IPredicate).
   */
  public T firstOrDefault(IPredicate<T> predicate_) {
    return IterableUtils.firstOrDefault(this, predicate_);
  }

  /**
   * @see IterableUtils#single(Iterable).
   */
  public T single() {
    return IterableUtils.single(this);
  }

  /**
   * @see IterableUtils#single(Iterable).
   */
  public T single(IPredicate<T> predicate_) {
    return IterableUtils.single(this, predicate_);
  }

  /**
   * @see IterableUtils#any(Iterable, IPredicate).
   */
  public boolean any(IPredicate<T> predicate_) {
    return IterableUtils.any(this, predicate_);
  }

  /**
   * @see IterableUtils#where(Iterable, IPredicate).
   */
  public List<T> where(IPredicate<T> predicate_) {
    return IterableUtils.where(this, predicate_);
  }

  /**
   * @see IterableUtils#select(Iterable, ISelector).
   */
  public <ResultT> SmartIterable<ResultT> select(ISelector<T, ResultT> selector_) {
    List<ResultT> result = IterableUtils.select(this, selector_);

    return new SmartList<ResultT>(result);
  }

  /**
   * see {@link IterableUtils}{@link #selectMany(IManySelector)}.
   */
  public <ResultT> SmartIterable<ResultT> selectMany(IManySelector<T, ResultT> selector_) {
    List<ResultT> result = IterableUtils.selectMany(this, selector_);

    return new SmartList<ResultT>(result);
  }

  /**
   * @see IterableUtils#count(Iterable).
   */
  public int count() {
    return IterableUtils.count(this);
  }

  /**
   * @see IterableUtils#toList(Iterable).
   */
  public List<T> toList() {
    return IterableUtils.toList(this);
  }
}
