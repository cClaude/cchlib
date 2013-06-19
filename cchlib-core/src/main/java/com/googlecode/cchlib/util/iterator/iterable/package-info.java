/**
 * Provide some extra Iterator, most based
 * on other Iterator. These Iterator are also {@link java.lang.Iterable}.
 * <br/>
 * An {@link com.googlecode.cchlib.util.iterator.iterable.IterableIterator}
 * is an Iterator that could be restart/reset using
 * {@link java.lang.Iterable#iterator()}
 * method.
 * <br/>
 * Theses Iterators are design to avoid copy of
 * Collections in memory and to generate result
 * in the flow.
 * 
 * <p>
 * <b>This package is deprecated</b> use classes based on 
 * {@link com.googlecode.cchlib.util.iterable.XIterable} instead or
 * methods from
 * {@link com.googlecode.cchlib.util.iterable.Iterables} 
 * {@link com.googlecode.cchlib.util.iterable.XIterables} 
 * </p>
 *
 * @see com.googlecode.cchlib.util.enumeration
 * @see com.googlecode.cchlib.util.iterator
 * @see com.googlecode.cchlib.util.iterator.iterable.IterableIterator
 * @since 4.1.7
 */
package com.googlecode.cchlib.util.iterator.iterable;
