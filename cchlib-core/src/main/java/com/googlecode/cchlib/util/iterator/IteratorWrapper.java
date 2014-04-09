package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.util.Wrappable;

/**
 * Build a {@link Iterator}&lt;O&gt; based on an other {@link Iterator}&lt;T&gt;
 * or an other {@link Iterable}&lt;T&gt;, each object is transformed
 * before returning using giving wrapper.
 *
 * @param <T> Source type
 * @param <O> Result type
 */
public class IteratorWrapper<T,O>
    implements Iterator<O>
{
    private Iterator<T>                      iterator;
    private Wrappable<? super T,? extends O> wrapper;

    /**
     * Build a Iterator&lt;O&gt; based on an Iterator&lt;T&gt;,
     * each object is transformed before returning using
     * giving wrapper.
     *
     * @param iterator Initial Iterator
     * @param wrapper  Wrapper to use to transform current
     *                 T Object to O Object.
     */
    public IteratorWrapper(
            Iterator<T>                      iterator,
            Wrappable<? super T,? extends O> wrapper
            )
    {
        this.iterator = iterator;
        this.wrapper  = wrapper;
    }

    /**
     * Build a Iterator&lt;O&gt; based on a Collection&lt;T&gt;,
     * each object is transformed before returning using
     * giving wrapper.
     *
     * @param iterable Iterable of object T
     * @param wrapper  Wrapper to use to transform current
     *                 T Object to O Object.
     */
    public IteratorWrapper(
            Iterable<T>     iterable,
            Wrappable<T,O>  wrapper
            )
    {
        this( iterable.iterator(), wrapper );
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
        return wrapper.wrap(iterator.next());
    }

    /**
     * Removes from the underlying collection the last element
     * returned by the iterator.
     * <p>
     * remove() is supported if the provided iterator/iterable does.
     * </p>
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
}
