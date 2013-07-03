package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import com.googlecode.cchlib.util.Wrappable;

/**
 * Wrapper for an {@link Iterator}, you just need to implement
 * D {@link Wrappable#wrap(Object)} method to transform you O object into D object

 * @param <O> Original type
 * @param <D> Destination type
 */
public abstract class AbstractIteratorWrapper<O,D>
    implements Wrappable<O,D>, Iterator<D>
{
    private IteratorWrapper<O, D> iteratorWrapper;

    /**
     * Build a wrapper using and consuming giving {@link Iterator}
     * @param sourceIterator The {@link Iterator} to wrap
     */
    public AbstractIteratorWrapper( Iterator<O> sourceIterator )
    {
        this.iteratorWrapper = new IteratorWrapper<O,D>( sourceIterator, this );
    }

    @Override
    public boolean hasNext()
    {
        return iteratorWrapper.hasNext();
    }

    @Override
    public D next()
    {
        return iteratorWrapper.next();
    }

    @Override
    public void remove()
    {
        iteratorWrapper.remove();
    }
}
