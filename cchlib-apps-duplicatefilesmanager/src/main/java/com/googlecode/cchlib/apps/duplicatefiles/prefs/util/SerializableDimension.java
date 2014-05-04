package com.googlecode.cchlib.apps.duplicatefiles.prefs.util;

import java.awt.Dimension;
import java.io.Serializable;

public class SerializableDimension implements Serializable
{
    private static final long serialVersionUID = 1L;
    private double width;
    private double heigth;

    public SerializableDimension()
    {
        this.width  = 0D;
        this.heigth = 0D;
    }

    public SerializableDimension( final Dimension dimension )
    {
        if( dimension != null ) {
            this.width    = dimension.getWidth();
            this.heigth   = dimension.getHeight();
        }
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth( final double width )
    {
        this.width = width;
    }

    public double getHeigth()
    {
        return heigth;
    }

    public void setHeigth( final double heigth )
    {
        this.heigth = heigth;
    }
}
