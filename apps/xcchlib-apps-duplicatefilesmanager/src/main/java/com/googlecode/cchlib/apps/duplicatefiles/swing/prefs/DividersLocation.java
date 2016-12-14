package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.Serializable;
import javax.annotation.Nullable;

/**
 * {@link DividersLocation} class able to be serialized.
 */
public final class DividersLocation implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer mainDividerLocation;
    private Integer rightDividerLocation;

    /**
     * Create {@link DividersLocation} with (0,0)
     */
    public DividersLocation()
    {
        this.mainDividerLocation  = Integer.valueOf( 0 );
        this.rightDividerLocation = Integer.valueOf( 0 );
    }

    /**
     * Create {@link DividersLocation} from a string
     *
     * @param strValues Values a has a String : {@code "mainDividerLocation,rightDividerLocation"}
     * @throws NumberFormatException if the string does not contain a parsable sequence of integers.
     */
    public DividersLocation( final String strValues )
    {
        final String[] values = strValues.split(",");

        if( values.length == 2 ) {
            this.mainDividerLocation  = Integer.parseInt( values[ 0 ] );
            this.rightDividerLocation = Integer.parseInt( values[ 1 ] );
        } else {
            this.mainDividerLocation  = Integer.valueOf( 0 );
            this.rightDividerLocation = Integer.valueOf( 0 );
        }
    }

    public Integer getMainDividerLocation()
    {
        return this.mainDividerLocation;
    }

    public void setMainDividerLocation( @Nullable final Integer mainDividerLocation )
    {
        this.mainDividerLocation = mainDividerLocation;
    }

    public Integer getRightDividerLocation()
    {
        return this.rightDividerLocation;
    }

    public void setRightDividerLocation( @Nullable final Integer rightDividerLocation )
    {
        this.rightDividerLocation = rightDividerLocation;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "DividersLocation [mainDividerLocation=" );
        builder.append( this.mainDividerLocation );
        builder.append( ", rightDividerLocation=" );
        builder.append( this.rightDividerLocation );
        builder.append( "]" );

        return builder.toString();
    }
}
