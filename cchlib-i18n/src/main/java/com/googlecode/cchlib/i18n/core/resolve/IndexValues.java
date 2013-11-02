package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;

/**
 *
 */
public class IndexValues extends IndexKV implements Values, Serializable
{
    private static final long serialVersionUID = 1L;
    private String[] values;

    /**
     *
     */
    public IndexValues( final String...values )
    {
        assert values.length > 0;

        this.values = values;
    }

    @Override
    public String get( int index )
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
}
