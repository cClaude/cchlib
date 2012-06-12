package com.googlecode.cchlib.util.iterator.iterable;

import java.util.Iterator;

/**
 * Build a new Iterator that consume first
 * Iterator and second Iterator for it's
 * results (Order is preserve).
 * <br/>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @param <T> content type
 * @see com.googlecode.cchlib.util.iterator.BiIterator
 * @since 4.1.7
 */
public class BiIterator<T>
    extends com.googlecode.cchlib.util.iterator.BiIterator<T>
        implements Iterable<T>,IterableIterator<T>
{
    private Iterable<T> firstIterable;
    private Iterable<T> secondIterable;

    /**
     * Build a new Iterator that consume first
     * Iterator and second Iterator for it's
     * results (Order is preserve).
     *
     * @param firstIterable  first Iterable object
     * @param secondIterable second Iterable object
     * @throws NullPointerException if firstIterable
     * or secondIterable is null
     */
    public BiIterator(
            final Iterable<T> firstIterable,
            final Iterable<T> secondIterable
            )
    {
        super( firstIterable.iterator(), secondIterable.iterator() );

        this.firstIterable  = firstIterable;
        this.secondIterable = secondIterable;
    }

    /**
     * Returns an iterator over a set of elements of type T.
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return new BiIterator<T>( firstIterable, secondIterable );
    }

}