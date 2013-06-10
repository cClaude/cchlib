package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;

/**
 *
 */
public class IndexKeys extends IndexKV implements Keys, Serializable
{
    private static final long serialVersionUID = 1L;
    private String keyBase;
    private int    size;

    public IndexKeys( final String key, final int size )
    {
        assert size > 0;

        this.keyBase    = key;
        this.size       = size;
    }

    @Override
    public String get( int index )
    {
        if( index < 0 ) {
            throw new IndexOutOfBoundsException( "index=" + index + " must getter than 0" );
            }
        if( index >= size ) {
            throw new IndexOutOfBoundsException( "index=" + index + " less than " + size );
            }

        return  this.keyBase + '.' + index;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "IndexKeys [keyBase=" );
        builder.append( keyBase );
        builder.append( ", size=" );
        builder.append( size );
        builder.append( ']' );
        return builder.toString();
    }
}
