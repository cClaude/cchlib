package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util;

import java.awt.Dimension;
import java.io.Serializable;
import javax.annotation.Nullable;

/**
 * {@link SerializableDimension} is a {@link Dimension} like class but
 * able to be serialized.
 */
public class SerializableDimension implements Serializable
{
    private static final long serialVersionUID = 1L;
    private double width;
    private double height;

    /**
     * Create {@link SerializableDimension} with (0,0)
     */
    public SerializableDimension()
    {
        this.width  = 0D;
        this.height = 0D;
    }

    /**
     * Create {@link SerializableDimension} from a string
     *
     * @param strValues Values a has a String : {@code "width,height"}
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

    /**
     * Create {@link SerializableDimension} from {@link Dimension}
     *
     * @param dimension {@link Dimension} to use, if null use (0,0)
     */
    public SerializableDimension( @Nullable final Dimension dimension )
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

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "SerializableDimension [width=" );
        builder.append( this.width );
        builder.append( ", height=" );
        builder.append( this.height );
        builder.append( "]" );

        return builder.toString();
    }
}
