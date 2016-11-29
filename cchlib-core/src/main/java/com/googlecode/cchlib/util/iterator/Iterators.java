package com.googlecode.cchlib.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.util.CollectionHelper;

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
        return new UnmodifiableIterator<>( iterator );
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
        return new UnmodifiableIterator2<>( firstElement, iterator );
    }

    /**
     * Create an Enumeration using (and consuming) an Iterator
     *
     * @param <T> type content
     * @param iterator to transform to an {@link Enumeration}
     * @return an {@link Enumeration} view for this {@code iterator}
     */
    public static <T> Enumeration<T> toEnumeration(
            final Iterator<T> iterator
            )
    {
        return new IteratortoEnumeration<>( iterator );
    }

    private static class UnmodifiableIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;
        public UnmodifiableIterator( final Iterator<T> iterator )
        {
            this.iterator = iterator;
        }
        @Override
        public boolean hasNext()
        {
            return this.iterator.hasNext();
        }
        @Override
        public T next()
        {
            return this.iterator.next();
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableIterator2<T> implements Iterator<T>
    {
        private final T firstElement;
        private boolean firstDone = false;
        private final Iterator<T> iterator;

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
            if( this.firstDone ) {
                return this.iterator.hasNext();
                }
            return true;
        }
        @Override
        public T next()
        {
            if( this.firstDone ) {
                return this.iterator.next();
                }
            else {
                this.firstDone = true;
                return this.firstElement;
                }
        }
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    @SuppressWarnings("squid:S1150") // An Enumeration is expected here
    private static class IteratortoEnumeration<T> implements Enumeration<T>
    {
        private final Iterator<T> iterator;

        public IteratortoEnumeration( final Iterator<T> iterator )
        {
            this.iterator = iterator;
        }
        @Override
        public boolean hasMoreElements()
        {
            return this.iterator.hasNext();
        }
        @Override
        public T nextElement()
        {
            return this.iterator.next();
        }
    }

    /**
     * Create an {@link Iterator} from an array
     *
     * @param <T> the type of the array
     * @param array source
     * @return an {@link Iterator}
     */
    public static <T> Iterator<T> create( final T[] array )
    {
        return new ArrayIterator<>( array );
    }

    /**
     * Create an {@link Iterator} from an array
     *
     * @param <T> the type of the array
     * @param array source
     * @param offset first entry in the array
     * @param len number of elements in the {@link Iterator}
     * @return an {@link Iterator}
     */
    public static <T> Iterator<T> create( final T[] array, final int offset, final int len )
    {
        return new ArrayIterator<>( array, offset, len );
    }

    /**
     * @param <T> deprecated
     * @param iterator deprecated
     * @return deprecated
     * @deprecated use {@link CollectionHelper#newList(Iterator)} instead
     */
    @Deprecated
    public static <T> List<T> newList( final Iterator<T> iterator )
    {
        return CollectionHelper.newList( iterator );
    }
}
