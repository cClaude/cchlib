package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import com.googlecode.cchlib.util.Wrappable;

/**
 * TODOC
 *
 * @param <O>
 * @param <D>
 */
public abstract class AbstractIteratorWrapper<O,D>
    implements Wrappable<O,D>, Iterator<D>
{
    private IteratorWrapper<O, D> iteratorWrapper;

    /**
     * TODOC
     * @param sourceIterator
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
