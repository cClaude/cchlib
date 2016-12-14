package com.googlecode.cchlib.util.iterable;

import java.util.Comparator;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 * e<B>X</B>tended {@link Iterable} interface. Allow to use chaining annotation like :
 *
 * <pre>
 *   List&lt;Integer&gt; result = XIterables.filter( collection, filter )
 *      .wrap( wrapper )
 *      .sort( comparator )
 *      .filter( filter2 )
 *      .wrap( wrapper2 )
 *      .sort( comparator2 )
 *      .toList();
 * </pre>
 *
 * @param <T> the type of elements returned by the iterator
 *
 * @since 1.4.8
 * @see XIterables
 * @see Wrappable
 * @see Selectable
 * @see Comparator
 */
@NeedDoc
public interface XIterable<T> extends Iterable<T>
{
    /**
     * Transform a {@link XIterable}
     *
     * @param <R> the type of elements returned by the wrapper
     * @param wrapper Wrapper to use
     * @return a new wrapped {@link XIterable}
     * @throws WrapperException if wrapping fail
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    <R> XIterable<R> wrap( Wrappable<? super T,? extends R> wrapper ) throws WrapperException;

    /**
     * Filter entries of a {@link XIterable}
     *
     * @param filter Filter to use
     * @return a new filtered {@link XIterable}
     */
    XIterable<T> filter(Selectable<? super T> filter);

    /**
     * Sort content using comparator
     * @param comparator the comparator to determine the order of the list.
     *            A null value indicates that the elements' natural ordering should be used.
     * @return a new sorted {@link XIterable}
     * @throws ClassCastException
     *            if the list contains elements that are not mutually comparable using
     *            the specified comparator.
     * @throws UnsupportedOperationException
     *            if the specified list's list-iterator does not support the set operation.
     */
    XIterable<T> sort( Comparator<? super T> comparator );

    /**
     * @return a new {@link List} with content of {@link Iterable} objects
     */
    List<T> toList();

    /**
     * Replace {@code list} content by element found on this iterable object.
     * <p>
     * Default implementation should be
     * <pre>
     *   list.clear();
     *   addToList( list );
     * </pre>
     *
     * @param list {@link List} where result will be copied.
     * @return value of {@code list} parameter for chaining.
     */
    List<T> setToList( List<T> list );

    /**
     * Add the content of the current {@link XIterable} into the
     * giving {@code list}
     *
     * @param list {@link List} where result will be appended.
     * @return value of {@code list} parameter for chaining.
     */
    List<T> addToList( List<T> list );

}
