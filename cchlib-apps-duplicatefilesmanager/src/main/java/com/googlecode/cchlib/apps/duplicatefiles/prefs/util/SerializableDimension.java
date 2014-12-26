package com.googlecode.cchlib.apps.duplicatefiles.prefs.util;

import java.awt.Dimension;
import java.io.Serializable;

public class SerializableDimension implements Serializable
{
    private static final long serialVersionUID = 1L;
    private double width;
    private double height;

    public SerializableDimension()
    {
        this.width  = 0D;
        this.height = 0D;
    }

    /**
     *
     * @param values Values a has a String : <code>"width,height"</code>
     * @throws NumberFormatException if the string does not contain a parsable sequence of integers.
     */
    public SerializableDimension( final String strValues )
    {
        final String[] values = strValues.split(",");

        if( values.length == 2 ) {
            this.width  = Integer.parseInt( values[ 0 ] );
            this.height = Integer.parseInt( values[ 1 ] );
        } else {
            this.width  = 0D;
            this.height = 0D;
        }
    }

    public SerializableDimension( final Dimension dimension )
    {
        if( dimension != null ) {
            this.width    = dimension.getWidth();
            this.height   = dimension.getHeight();
        }
    }

    public double getWidth()
    {
        return this.width;
    }

    public void setWidth( final double width )
    {
        this.width = width;
    }

    public double getHeight()
    {
        return this.height;
    }

    public void setHeight( final double height )
    {
        this.height = height;
    }
}
