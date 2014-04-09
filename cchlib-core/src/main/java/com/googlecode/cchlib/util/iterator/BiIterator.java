package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Build a new Iterator that consume first
 * Iterator and second Iterator for it's
 * results (Order is preserve).
 *
 * @param <T> content type
 * @since 4.1.7
 */
public class BiIterator<T>
    implements Iterator<T>
{
    private Iterator<T> firstIter;
    private Iterator<T> secondIter;

    /**
     * Build a new Iterator that consume first
     * Iterator and second Iterator for it's
     * results (Order is preserve).
     *
     * @param firstIterator  first iterator
     * @param secondIterator second iterator
     * @throws NullPointerException if firstIterator
     * or secondIterator is null
     */
    public BiIterator(
            Iterator<T> firstIterator,
            Iterator<T> secondIterator
            )
    {
        if( firstIterator == null ) {
            throw new NullPointerException();
        	}
        if( secondIterator == null ) {
            throw new NullPointerException();
        	}
        this.firstIter = firstIterator;
        this.secondIter = secondIterator;
    }

    /**
     * Returns true if the iteration has more elements.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        if(firstIter.hasNext()) {
            return true;
        	}
        else {
            return secondIter.hasNext();
        	}
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public T next() throws NoSuchElementException
    {
        if(firstIter.hasNext()) {
            return firstIter.next();
            }
        else {
            return secondIter.next();
        	}
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
        if(firstIter.hasNext()) {
            firstIter.remove();
            }
        else {
            secondIter.remove();
        	}
    }
}
