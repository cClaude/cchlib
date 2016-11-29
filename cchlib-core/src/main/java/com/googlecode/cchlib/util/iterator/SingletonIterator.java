package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Limit case, iteration on a singleton
 * <BR>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @param <T> content type
 * @since 4.1.7
 */
public class SingletonIterator<T>
    implements Iterator<T>,
               Iterable<T>
{
    private boolean hasNext;
    private final T item;

    /**
     * Singleton entry
     *
     * @param item unique item of this Iterator
     */
    public SingletonIterator(final T item)
    {
        this.item = item;
        this.hasNext = true;
    }

    /**
     * Returns true if the iteration has more elements.
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        return this.hasNext;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck") // By contract
    public T next() throws NoSuchElementException
    {
        if( this.hasNext ) {
            this.hasNext = false;

            return this.item;
            }
        else {
            throw new NoSuchElementException();
            }
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException if {@link #hasNext()} have been called
     * @throws IllegalStateException if {@link #hasNext()} have not been called
     */
    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck") // By contract
    public void remove()
        throws UnsupportedOperationException,
               IllegalStateException
    {
        if( this.hasNext ) {
            throw new IllegalStateException();
            }
        else {
            throw new UnsupportedOperationException();
            }
    }

    /**
     * Returns an iterator over a set of elements of type T.
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return new SingletonIterator<>( this.item );
    }
}
