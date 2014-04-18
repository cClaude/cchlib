package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.iterable.ArrayIterable;

/**
 * Build an Iterator based on an {@link Iterator} of {@link Iterable}.
 * This new Iterator that consume all sub-Iterator in
 * order of main Iterator for it's
 * results (Order is preserve).
 *
 * @param <T> content type
 * @see MultiIterator
 */
public class CascadingIterator<T>
    implements Iterator<T>
{
    private Iterator<? extends Iterable<? extends T>> mainIterator;
    private Iterator<? extends T> currentIterator;

    /**
     * Build an Iterator based on an Iterator of Iterator
     *
     * @param iterator an Iterator of Iterator
     */
    public CascadingIterator(final Iterator<? extends Iterable<? extends T>> iterator)
    {
        mainIterator    = iterator;
        currentIterator = null;

        // Init currentIterator with a valid value
        hasNext();
    }

    /**
     *
     * @param iterators
     * @since 4.1.8
     */
    @SafeVarargs
    @NeedDoc
    public CascadingIterator( final Iterable<? extends T>...iterators )
    {
        this( newIterator( iterators ) );
    }

    private static <T> Iterator<? extends Iterable<? extends T>> newIterator( final Iterable<? extends T>[] iterators )
    {
        return new ArrayIterable<>( iterators ).iterator();
    }

    @Override
    public boolean hasNext()
    {
        if( currentIterator == null ) {
            if( mainIterator.hasNext() ) {
                currentIterator = mainIterator.next().iterator();
                }
            else {
                return false;
                }
            }

        for(;;) {
            if( currentIterator.hasNext() ) {
                return true;
            }
            if( mainIterator.hasNext() ) {
                currentIterator = mainIterator.next().iterator();
            }
            else {
                return false;
            }
        }
    }

    @Override
    public T next()
    {
        return currentIterator.next();
    }

    /**
     * Removes from the underlying collection the last element
     * returned by the iterator.
     *
     * @throws UnsupportedOperationException if the remove
     *         operation is not supported by current Iterator.
     * @throws IllegalStateException if the next method has
     *         not yet been called, or the remove method has
     *         already been called after the last call to the
     *         next method.
     */
    @Override
    public void remove()
    {
        currentIterator.remove();
    }
}
