package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

/**
 * Build a Iterable<O> based on an other Iterable<T>,
 * each object is transformed before returning using 
 * giving wrapper.
 *
 * @param <S> Source type
 * @param <R> Result type
 */
/*public*/ class IterableWrapper<S,R>
    implements Iterable<R>
{
    private Iterable<S>                      iterable;
    private Wrappable<? super S,? extends R> wrapper;

    /**
     * Build a Iterator<O> based on an other Iterator<T>,
     * each object is transformed before returning using
     * giving wrapper.
     *
     * @param iterable Initial Iterable
     * @param wrapper  Wrapper to use to transform current
     *                 T Object to O Object.
     */
    public IterableWrapper(
        final Iterable<S>                      iterable,
        final Wrappable<? super S,? extends R> wrapper
        )
    {
        this.iterable = iterable;
        this.wrapper  = wrapper;
    }

    @Override
    public Iterator<R> iterator()
    {
        return new IteratorWrapper<S,R>( iterable.iterator(), wrapper );
    }
}
