package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;

/**
 *
 */
public class IndexKeys extends IndexKV implements Keys, Serializable
{
    private static final long serialVersionUID = 1L;
    private final String keyBase;
    private final int    size;

    /**
     * new IndexKeys with <code>keyBase</code> and <code>size</code> possible values
     * @param keyBase base name ('.' will be added between keyBase and key index)
     * @param size number of name (and values) for this key
     */
    public IndexKeys( final String keyBase, final int size )
    {
        assert size > 0;

        this.keyBase    = keyBase;
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
