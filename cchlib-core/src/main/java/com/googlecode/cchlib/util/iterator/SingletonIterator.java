package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.util.iterator.iterable.IterableIterator;

/**
 * Limit case, iteration on a singleton
 * <BR/>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @param <T> content type
 * @since 4.1.7
 */
public class SingletonIterator<T>
    implements Iterator<T>, 
               Iterable<T>, 
               IterableIterator<T>
{
    private boolean hasNext;
    private T item;

    /**
     * Singleton entry
     *
     * @param item unique item of this Iterator
     */
    public SingletonIterator(T item)
    {
        this.item = item;
        this.hasNext = true;
    }

    /**
     * Returns true if the iteration has more elements.
     * @return true if the iteration has more elements.
     */
    public boolean hasNext()
    {
        return hasNext;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    public T next() throws NoSuchElementException
    {
        if( hasNext ) {
            hasNext = false;

            return item;
        	}
        else {
            throw new NoSuchElementException();
        	}
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException
     * @throws IllegalStateException
     */
    public void remove()
        throws UnsupportedOperationException,
               IllegalStateException
    {
        if( hasNext ) {
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
    public Iterator<T> iterator()
    {
        return new SingletonIterator<T>( item );
    }
}
