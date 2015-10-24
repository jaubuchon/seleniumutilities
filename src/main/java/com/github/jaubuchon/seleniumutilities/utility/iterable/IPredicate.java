package com.github.jaubuchon.seleniumutilities.utility.iterable;

/**
 * Exposed by the @see {@link IterableUtils} to let his consumers search for a specific item.
 * Concretely, this interface encapsulate a boolean condition.
 */
public interface IPredicate<T> {
  public boolean test(T element_);
}
