package com.googlecode.cchlib.util.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.util.Wrappable;

/**
 * Build a Iterator<O> based on an other Iterator<T>
 * or an other Collection<T>, each object is transformed
 * before returning using giving wrapper.
 *
 * @param <T> Source type
 * @param <O> Result type
 */
public class IteratorWrapper<T,O>
    implements Iterator<O>
{
    private Iterator<T>    iterator;
    private Wrappable<T,O> wrapper;

    /**
     * Build a Iterator<O> based on an other Iterator<T>,
     * each object is transformed before returning using
     * giving wrapper.
     *
     * @param iterator Initial Iterator
     * @param wrapper  Wrapper to use to transform current
     *                 T Object to O Object.
     */
    public IteratorWrapper(
            Iterator<T>     iterator,
            Wrappable<T,O>  wrapper
            )
    {
        this.iterator = iterator;
        this.wrapper  = wrapper;
    }

    /**
     * Build a Iterator<O> based on an other Collection<T>,
     * each object is transformed before returning using
     * giving wrapper.
     *
     * @param c        Collection of object T
     * @param wrapper  Wrapper to use to transform current
     *                 T Object to O Object.
     */
    public IteratorWrapper(
            Collection<T>   c,
            Wrappable<T,O>  wrapper
            )
    {
        this(c.iterator(), wrapper);
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
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public O next() throws NoSuchElementException
    {
        return wrapper.wrappe(iterator.next());
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
    public void remove() throws UnsupportedOperationException
    {
        iterator.remove();
    }

//    /**
//     * Returns an iterator over a set of elements of type O.
//     * @return this Iterator
//     */
//    @Override
//    public Iterator<O> iterator()
//    {
//        return this;
//    }
}
