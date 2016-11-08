package com.googlecode.cchlib.util.enumeration;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * {@link EmptyEnumeration} is an {@link Enumeration} with no element.
 * @param <E>  content type
 * @since 4.1.7
 */
public class EmptyEnumeration<E>
    implements Enumeration<E>
{
    /**
     * Create a {@link EmptyEnumeration}
     */
    public EmptyEnumeration()
    {
    }

    /**
     * Tests if this enumeration contains more elements.
     *
     * @return always false
     */
    @Override
    public boolean hasMoreElements()
    {
        return false;
    }

    /**
     * Always generate NoSuchElementException according
     * to Enumeration specifications.
     *
     * @throws NoSuchElementException if no more elements exist.
     */
    @Override
    public E nextElement() throws NoSuchElementException
    {
        throw new NoSuchElementException();
    }
}
