package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.io.Serializable;

public final class DividersLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer resultsMainDividerLocation;
    private Integer resultsRightDividerLocation;

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