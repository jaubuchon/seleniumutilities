package com.github.jaubuchon.seleniumutilities.utility.iterable;

public interface IManySelector<TSource, TResult> {
  Iterable<TResult> selectMany(TSource source_);
}

