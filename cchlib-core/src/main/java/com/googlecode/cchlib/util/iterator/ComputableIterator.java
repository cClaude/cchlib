package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract class for create Iterator
 * @since 4.1.7
 */
public abstract class ComputableIterator<T>
    implements Iterator<T>
    //Note: Can't implement Iterable<T> here - super class could do this work
{
    private T nextObject;

    /** Create ComputableIterator */
    protected ComputableIterator()
    {
        nextObject = null;
    }

    /**
     * Compute next object for iterator, return next object if
     * exist, throwing an exception if not.
     * @return next object if exist
     * @throws NoSuchElementException if no more object
     */
    protected abstract T computeNext()
        throws NoSuchElementException;

    /**
     * Returns true if the iteration has more elements.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
     @Override
    public boolean hasNext()
    {
        if( nextObject == null ) {
            try {
                nextObject = computeNext();
                }
            catch( NoSuchElementException e ) { // $codepro.audit.disable logExceptions
                return false;
                }
            }

        return true;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public T next() throws NoSuchElementException
    {
        if(nextObject == null) {
            nextObject = computeNext();
            }

        T returnObject = nextObject;

        try {
            nextObject = computeNext();
            }
        catch(NoSuchElementException e) { // $codepro.audit.disable logExceptions
            nextObject = null;
            }

        return returnObject;
    }

    /**
     * Unsupported Operation.
     * <p>
     * Since next item is read before return current
     * item, it's not possible to support remove() operation
     * using ComputableIterator
     * </p>
     *
     * @throws UnsupportedOperationException
     * @throws IllegalStateException
     */
    @Override
    final public void remove()
        throws  UnsupportedOperationException,
                IllegalStateException
    {
        if(nextObject == null) {
            throw new IllegalStateException();
            }
        else {
            throw new UnsupportedOperationException();
            }
    }

}
