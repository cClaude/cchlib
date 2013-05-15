package com.googlecode.cchlib.i18n.missing;

import java.io.Serializable;

public class MissingSimpleKey implements MissingInfo, Serializable
{
    private static final long serialVersionUID = 1L;
    private String key;

    public MissingSimpleKey( String key )
    {
        this.key = key;
    }

    @Override
    public MissingInfo.Type getType()
    {
        return MissingInfo.Type.SIMPLE_KEY;
    }

    @Override
    public String getKey()
    {
        return key;
    }

}
