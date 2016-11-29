package com.googlecode.cchlib.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Just a empty Iterator, could be use to deal
 * with limit cases.
 *
 * <BR>
 * Note: This Iterator extends also {@link Iterable} interface
 * @since 4.1.7
 */
public class EmptyIterator<T>
    implements Iterator<T>,
               Iterable<T>,
               Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an empty Iterator
     */
    public EmptyIterator()
    {
        // Yep, it's empty :)
    }

    /**
     * Always return false.
     *
     * @return false
     */
    @Override
    public boolean hasNext()
    {
        return false;
    }

    /**
     * Always generate NoSuchElementException according
     * to Iterator specifications.
     *
     * @throws NoSuchElementException always
     */
    @Override
    public T next()
    {
        throw new NoSuchElementException();
    }

    /**
     * Always generate IllegalStateException according
     * to Iterator specifications.
     *
     * @throws IllegalStateException always
     */
    @Override
    public void remove()
    {
        throw new IllegalStateException();
    }

    /**
     * Returns an iterator over a set of elements of type T.
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
}
