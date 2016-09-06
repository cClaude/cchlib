package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/**
 * Abstract class for create Iterator
 *
 * @param <T> the type of elements returned by this iterator
 *
 * @since 4.1.7
 */
public abstract class ComputableIterator<T>
    implements Iterator<T>
    //Note: Can't implement Iterable<T> here - super class could do this work
{
    @Nullable private T nextObject;
    private Boolean noSuchElementException;

    /** Create ComputableIterator */
    protected ComputableIterator()
    {
        // noSuchElementException and nextObject are null
    }

    private static boolean isEnd( final Boolean noSuchElementException )
    {
        if( noSuchElementException != null ) {
            return noSuchElementException.booleanValue();
        }
        return false;
    }

    /**
     * Compute next object for iterator, return next object if exist, throwing an exception if not.
     *
     * @return next object if exist
     * @throws NoSuchElementException
     *             if no more object
     */
    @Nullable protected abstract T computeNext()
        throws NoSuchElementException;

    /**
     * Returns true if the iteration has more elements. (In other words, returns true if next would return an element
     * rather than throwing an exception.)
     *
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        if( isEnd( this.noSuchElementException ) ) {
            return false;
        }
        if( this.noSuchElementException == null ) {
            try {
                this.noSuchElementException = Boolean.FALSE;
                this.nextObject = computeNext();
                }
            catch( final NoSuchElementException e ) { // NOSONAR $codepro.audit.disable logExceptions
                return false;
                }
            }

        return true;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     * @throws NoSuchElementException
     *             iteration has no more elements.
     */
    @Override
    public T next() throws NoSuchElementException
    {
        if( isEnd( this.noSuchElementException ) ) {
            throw new NoSuchElementException();
        }

        if( this.noSuchElementException == null ) {
            this.noSuchElementException = Boolean.FALSE;
            this.nextObject = computeNext();
            }

        @Nullable final T returnObject = this.nextObject;

        try {
            this.nextObject = computeNext();
            }
        catch(final NoSuchElementException e) { // NOSONAR $codepro.audit.disable logExceptions
            this.nextObject = null;
            this.noSuchElementException = Boolean.TRUE;
            }

        return returnObject;
    }

    /**
     * Unsupported Operation.
     * <p>
     * Since next item is read before return current item, it's not possible to support remove() operation using
     * ComputableIterator
     * </p>
     *
     * @throws UnsupportedOperationException
     *             Always throw this exception.
     */
    @Override
    public final void remove()
        throws  UnsupportedOperationException,
                IllegalStateException
    {
        throw new UnsupportedOperationException();
    }
}

