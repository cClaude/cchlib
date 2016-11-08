package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;
import java.util.Arrays;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public class IndexValues extends IndexKV implements Values, Serializable
{
    private static final long serialVersionUID = 1L;
    private final String[] values;

    @NeedDoc
    public IndexValues( final String...values )
    {
        assert values.length > 0;

        this.values = values;
    }

    @Override
    public String get( final int index )
    {
        return values[ index ];
    }

    @Override
    public int size()
    {
        return values.length;
    }

    @Override
    public String[] toArray()
    {
        return values.clone();
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "IndexValues [values=" );
        builder.append( Arrays.toString( values ) );
        builder.append( ']' );
        return builder.toString();
    }
}
