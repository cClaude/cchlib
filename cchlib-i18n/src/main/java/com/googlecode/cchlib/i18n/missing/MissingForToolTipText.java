package com.googlecode.cchlib.i18n.missing;

import java.io.Serializable;

/** 
 *
 */
public class MissingForToolTipText implements MissingInfo, Serializable
{
    private static final long serialVersionUID = 1L;
    //private String realKey;
    private String key;

    public MissingForToolTipText( final String realKey, final String key )
    {
        //this.realKey = realKey;
        this.key     = key;
    }

    @Override
    public MissingInfo.Type getType()
    {
        return MissingInfo.Type.JCOMPONENT_TOOLTIPTEXT;
    }

    @Override
    public String getKey()
    {
        return key;
    }
}
