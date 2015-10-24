package com.github.jaubuchon.seleniumutilities.utility.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Inspired by the Enumerable Extensions Methods of the .NET Framework.
 */
public class IterableUtils {

  /**
   * Returns the first element of a sequence, or null if the sequence contains no elements.
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ The {@link Iterable} to return the first element of.
   * @return null if sequence is empty; otherwise, the first element in sequence.
   */
  public static <T> T first(Iterable<T> sequence_) {
    return IterableUtils.first(sequence_.iterator());
  }

  /**
   * Returns the first element of the sequence that satisfies a condition or null if no such element
   * is found.
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ An {@link Iterable} to return the single element of.
   * @param predicate_ A function to test each element for a condition.
   * @return null if source is empty or if no element passes the test specified by predicate;
   *         otherwise, the first element in source that passes the test specified by predicate.
   */
  public static <T> T firstOrDefault(Iterable<T> sequence_, IPredicate<T> predicate_) {
    T result = null;
    for (T entity : sequence_) {
      if (predicate_.test(entity)) {
        result = entity;
      }

      if (result != null) {
        break;
      }
    }

    return result;
  }

  /**
   * Returns the only element of a sequence, and throws an exception if there is not exactly one
   * element in the sequence.
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ An {@link Iterable} to return the single element of.
   * @return The single element of the input sequence.
   * 
   * @throws NoSuchElementException if the list has no element
   * @throws IllegalStateException: if there is more than one element in the list
   */
  public static <T> T single(Iterable<T> sequence_) {
    Iterator<T> iterator = sequence_.iterator();

    T singleElement = IterableUtils.first(iterator);

    if (iterator.hasNext()) {
      throw new IllegalStateException(
          String.format("There is more than one element available in the sequence"));
    }

    return singleElement;
  }

  /**
   * Returns the only element of the list that satisfies a specified condition and throws an
   * exception if more than one such element exists.
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ An {@link Iterable} to return a single element from.
   * @param predicate_ A function to test an element for a condition.
   * @return The single element of the input sequence that satisfies a condition.
   * 
   * @throws NoSuchElementException: No element satisfies the condition of the predicate.
   * @throw IllegalStateException: More than one element satisfies the condition of the predicate.
   */
  public static <T> T single(Iterable<T> sequence_, IPredicate<T> predicate_) {

    T result = null;

    boolean found = false;

    for (T entity : sequence_) {
      if (predicate_.test(entity)) {
        if (!found) {
          result = entity;
          found = true;
        } else {
          throw new IllegalStateException(
              String
                  .format(
                      "More than one element in the sequence satisfies the condition of the predicate \"%s\"",
                      predicate_));
        }
      }
    }

    if (!found) {
      throw new NoSuchElementException(String.format(
          "No element in the sequence satisfies satisfies the condition of the predicate \"%s\"",
          predicate_));
    }

    return result;
  }

  /**
   * Determines whether any element of a sequence satisfies a condition.
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ An {@link Iterable} whose elements to apply the predicate to.
   * @param predicate_ A function to test each element for a condition.
   * @return <code>true</code> if any elements in the source sequence pass the test in the specified
   *         predicate; otherwise, <code>false</code>.
   */
  public static <T> boolean any(Iterable<T> sequence_, IPredicate<T> predicate_) {
    return IterableUtils.firstOrDefault(sequence_, predicate_) != null;
  }

  /**
   * Filters a sequence of values based on a predicate.
   * 
   * @param sequence_ An {@link Iterable} to filter.
   * @param predicate_ A function to test each element for a condition.
   * @return An {@link Iterable} that contains elements from the input sequence that satisfy the
   *         condition.
   */
  public static <T> List<T> where(Iterable<T> sequence_, IPredicate<T> predicate_) {

    List<T> resultList = new ArrayList<T>();

    for (T entity : sequence_) {
      if (predicate_.test(entity)) {
        resultList.add(entity);
      }
    }

    return resultList;
  }

  /**
   * Projects each element of a sequence into a new form.
   * 
   * @param <SourceT> The type of the elements inside sequence.
   * @param <ResultT> The type of the value returned by selector.
   * 
   * @param sequence_ A sequence of values to invoke a transform function on.
   * @param selector_ A transform function to apply to each element.
   * @return A {@link List} whose elements are the result of invoking the transform function on each
   *         element of source.
   */
  public static <SourceT, ResultT> List<ResultT> select(Iterable<SourceT> sequence_,
      ISelector<SourceT, ResultT> selector_) {
    List<ResultT> resultList = new ArrayList<ResultT>();

    for (SourceT entity : sequence_) {
      resultList.add(selector_.select(entity));
    }

    return resultList;
  }

  /**
   * Projects each element of a sequence to an {@link Iterable} and flattens the resulting sequences into
   * one sequence.
   * 
   * @param <SourceT> The type of the elements inside sequence.
   * @param <ResultT> The type of the elements of the sequence returned by selector.
   * 
   * @param sequence_ A sequence of values to project.
   * @param selector_ A transform function to apply to each element.
   * @return An {@link Iterable} whose elements are the result of invoking the one-to-many transform
   *         function on each element of the input sequence.
   */
  public static <SourceT, ResultT> List<ResultT> selectMany(Iterable<SourceT> sequence_,
      IManySelector<SourceT, ResultT> selector_) {
    List<ResultT> result = new ArrayList<ResultT>();

    for (SourceT entity : sequence_) {
      for (ResultT selectedItem : selector_.selectMany(entity)) {
        result.add(selectedItem);
      }
    }

    return result;
  }

  /**
   * Returns the number of elements in a sequence.
   * 
   * @param sequence_ A sequence that contains elements to be counted.
   * @return he number of elements in the input sequence.
   */
  public static <SourceT> int count(Iterable<SourceT> sequence_) {
    int counter = 0;

    for (Iterator<SourceT> iterator = sequence_.iterator(); iterator.hasNext();) {
      iterator.next();
      counter++;
    }

    return counter;
  }

  /**
   * Creates a {@link List} from an {@link Iterable}
   * 
   * @param <T> The type of the elements inside sequence.
   * 
   * @param sequence_ The {@link Iterable} to create a {@link List} from.
   * @return A {@link List} that contains elements from the input sequence.
   */
  public static <T> List<T> toList(Iterable<T> sequence_) {
    List<T> result = new ArrayList<T>();

    for (T entity : sequence_) {
      result.add(entity);
    }

    return result;
  }
  
  private static <T> T first(Iterator<T> sequence_) {
    T first = null;
    try {
      first = sequence_.next();
    } catch (NoSuchElementException exception) {
      throw new NoSuchElementException(
          String.format("There is no available element in the sequence"));
    }

    return first;
  }
}
