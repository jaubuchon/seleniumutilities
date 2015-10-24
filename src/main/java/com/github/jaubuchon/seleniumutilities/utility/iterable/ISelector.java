package com.github.jaubuchon.seleniumutilities.utility.iterable;

/**
 * Project an object into a new form.
 * 
 * @param <TSource> the type on the object
 * @param <TResult> the type of the new form
 */
public interface ISelector<TSource, TResult> {
  TResult select(TSource source_);
}

