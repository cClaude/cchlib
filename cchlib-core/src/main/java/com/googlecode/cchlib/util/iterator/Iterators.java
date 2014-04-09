package com.googlecode.cchlib.util.iterator;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * This class consists exclusively of static methods
 * that operate on iterators or return iterators.
 */
public final class Iterators
{
    private Iterators()
    {//All static
    }

    /**
     * Returns an unmodifiable view of the specified
     * Iterator. This method allows modules to provide
     * users with "read-only" access to Iterator.
     * Attempts to modify the returned iterator,
     * result in an UnsupportedOperationException.
     *
     * @param iterator the iterator for which an unmodifiable
     *        view is to be returned.
     * @param <T> type of Iterator
     * @return an unmodifiable  view of the specified iterator
     *
     */
    public static <T> Iterator<T> unmodifiableIterator(
            final Iterator<T> iterator
            )
    {
        return new UnmodifiableIterator<T>( iterator );
    }

    /**
     * Returns an unmodifiable view of the specified
     * firstElement and Iterator.
     * <p>
     * Results Iterator return 'firstElement' and
     * then all element in Iterator.
     * </p>
     * <p>
     * This method allows modules to provide users
     * with "read-only" access to Iterator.
     * Attempts to modify the returned iterator,
     * result in an UnsupportedOperationException.
     * </p>
     *
     * @param firstElement first element for the new iterator.
     * @param iterator the iterator for which an unmodifiable
     *        view is to be returned.
     * @param <T> type of Iterator
     * @return an unmodifiable  view of the specified iterator
     *
     */
    public static <T> Iterator<T> unmodifiableIterator(
            final T             firstElement,
            final Iterator<T>   iterator
            )
    {
        return new UnmodifiableIterator2<T>( firstElement, iterator );
    }

    /**
     * Create an Enumeration using (and consuming) an Iterator
     *
     * @param <T> type content
     * @param iterator
     * @return an Enumeration view for this iterator
     */
    public static <T> Enumeration<T> toEnumeration(
            final Iterator<T> iterator
            )
    {
        return new IteratortoEnumeration<T>( iterator );
    }

    private static class UnmodifiableIterator<T> implements Iterator<T>
    {
        private Iterator<T> iterator;
        public UnmodifiableIterator( Iterator<T> iterator )
        {
            this.iterator = iterator;
        }
        @Override
        public boolean hasNext()
        {
            return iterator.hasNext();
        }
        @Override
        public T next()
        {
            return iterator.next();
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableIterator2<T> implements Iterator<T>
    {
        private T firstElement;
        private boolean firstDone = false;
        private Iterator<T> iterator;

        public UnmodifiableIterator2(
            final T             firstElement,
            final Iterator<T>   iterator
            )
        {
            this.firstElement = firstElement;
            this.iterator     = iterator;
        }
        @Override
        public boolean hasNext()
        {
            if( firstDone ) {
                return iterator.hasNext();
                }
            return true;
        }
        @Override
        public T next()
        {
            if( firstDone ) {
                return iterator.next();
                }
            else {
                firstDone = true;
                return firstElement;
                }
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }


    private static class IteratortoEnumeration<T> implements Enumeration<T>
    {
        private Iterator<T> iterator;

        public IteratortoEnumeration( Iterator<T> iterator )
        {
            this.iterator = iterator;
        }
        @Override
        public boolean hasMoreElements()
        {
            return iterator.hasNext();
        }
        @Override
        public T nextElement()
            throws java.util.NoSuchElementException
        {
            return iterator.next();
        }
    }

    public static <T> Iterator<T> create( final T[] array )
    {
        return new ArrayIterator<T>( array );
    }

    public static <T> Iterator<T> create( final T[] array, final int offset, final int len )
    {
        return new ArrayIterator<T>( array, offset, len );
    }

    public static <T> List<T> newList( final Iterator<T> iterator )
    {
        final List<T> list = new ArrayList<T>();

        while( iterator.hasNext() ) {
            list.add( iterator.next() );
        }

        return list;
    }
}
