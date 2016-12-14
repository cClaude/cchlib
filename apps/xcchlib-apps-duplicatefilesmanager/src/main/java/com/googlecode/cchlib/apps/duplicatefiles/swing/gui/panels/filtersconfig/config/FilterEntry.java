package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.Serializable;

/**
 * @see FiltersConfig
 */
public class FilterEntry implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String description;
    private String data;

    public FilterEntry()
    {
        // Empty
    }

    public FilterEntry( final String description, final String data )
    {
        this.description = description;
        this.data        = data;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public String getData()
    {
        return this.data;
    }

    public void setData( final String data )
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "FilterEntry [description=" );
        builder.append( this.description );
        builder.append( ", data=" );
        builder.append( this.data );
        builder.append( "]" );

        return builder.toString();
    }
}
