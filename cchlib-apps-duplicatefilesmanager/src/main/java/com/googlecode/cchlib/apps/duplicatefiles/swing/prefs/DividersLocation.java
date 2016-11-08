package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.Serializable;

public final class DividersLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer resultsMainDividerLocation;
    private Integer resultsRightDividerLocation;

    public DividersLocation()
    {
        this.resultsMainDividerLocation  = Integer.valueOf( 0 );
        this.resultsRightDividerLocation = Integer.valueOf( 0 );
    }

    public DividersLocation( final String strValues )
    {
        final String[] values = strValues.split(",");

        if( values.length == 2 ) {
            this.resultsMainDividerLocation  = Integer.parseInt( values[ 0 ] );
            this.resultsRightDividerLocation = Integer.parseInt( values[ 1 ] );
        } else {
            this.resultsMainDividerLocation  = Integer.valueOf( 0 );
            this.resultsRightDividerLocation = Integer.valueOf( 0 );
        }
    }

    public Integer getMainDividerLocation()
    {
        return this.resultsMainDividerLocation;
    }

    public void setMainDividerLocation( final Integer mainDividerLocation )
    {
        this.resultsMainDividerLocation = mainDividerLocation;
    }

    public Integer getRightDividerLocation()
    {
        return this.resultsRightDividerLocation;
    }

    public void setRightDividerLocation( final Integer rightDividerLocation )
    {
        this.resultsRightDividerLocation = rightDividerLocation;
    }
}