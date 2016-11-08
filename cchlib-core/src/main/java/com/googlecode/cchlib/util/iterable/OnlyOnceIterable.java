package com.googlecode.cchlib.util.iterable;

import java.util.Iterator;
import com.googlecode.cchlib.NeedDoc;

/**
 * TODOC
 * @since 4.1.8
 */
@NeedDoc
class OnlyOnceIterable<T> implements Iterable<T>
{
    private Iterator<T> iterator;

    /**
     *
     * @param iterator
     */
    public OnlyOnceIterable( final Iterator<T> iterator )
    {
        this.iterator = iterator;
    }

    /**
     * @throws IllegalStateException if iterator() already called
     */
    @Override
    public Iterator<T> iterator()
    {
        if( iterator == null ) {
            throw new IllegalStateException( "iterator() already called" );
            }

        final Iterator<T> cpy = iterator;

        iterator = null;

        return cpy;
    }

}
