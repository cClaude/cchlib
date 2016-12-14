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
     * new IndexKeys with {@code keyBase} and {@code size} possible values
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
    public String get( final int index )
    {
        if( index < 0 ) {
            throw new IndexOutOfBoundsException( "index=" + index + " must getter than 0" );
            }
        if( index >= this.size ) {
            throw new IndexOutOfBoundsException( "index=" + index + " less than " + this.size );
            }

        return  this.keyBase + '.' + index;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "IndexKeys [keyBase=" );
        builder.append( this.keyBase );
        builder.append( ", size=" );
        builder.append( this.size );
        builder.append( ']' );
        return builder.toString();
    }
}
