package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;
import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
public class UniqKeys implements Serializable, Keys
{
    private static final long serialVersionUID = 1L;
    private final String  key;

    public UniqKeys( final String key )
    {
        this.key = key;
    }

    @Override
    public String get( final int index )
    {
        if( index != 0 ) {
            throw new IndexOutOfBoundsException( "index=" + index + " must be 0" );
            }
        return key;
    }

    @Override
    public int size()
    {
        return 1;
    }

    @Override
    public Iterator<String> iterator()
    {
        return new SingletonIterator<>( key );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "UniqKeys [key=" );
        builder.append( key );
        builder.append( ", size()=" );
        builder.append( size() );
        builder.append( ']' );
        return builder.toString();
    }
}
