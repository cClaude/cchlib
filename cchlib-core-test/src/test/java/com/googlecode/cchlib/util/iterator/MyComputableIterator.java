package com.googlecode.cchlib.util.iterator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class MyComputableIterator<T> extends ComputableIterator<T> {
    private final Iterator<T> internalIterator;

    public MyComputableIterator( final Collection<T> collection )
    {
        this.internalIterator = Collections.unmodifiableCollection( collection ).iterator();
    }

    @Override
    protected T computeNext() throws NoSuchElementException
    {
        if( this.internalIterator.hasNext() ) {
            try {
                return this.internalIterator.next();
            }
            catch( final Exception cause ) {
                throw new BadException( cause );
            }
        }
        throw new NoSuchElementException();
    }
}