package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
abstract /*not public*/ class IndexKV implements Serializable, Iterable<String>
{
    private static final long serialVersionUID = 1L;

    public IndexKV()
    {
    }

    public abstract String get( int index );
    public abstract int size();
  
    
    @Override
    public Iterator<String> iterator()
    {
        return new Iterator<String>() {
            private int index;

            @Override
            public boolean hasNext()
            {
                return this.index < size();
            }

            @Override
            public String next()
            {
                if( this.index < size() ) {
                    return get( this.index++ );
                    }
                else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
