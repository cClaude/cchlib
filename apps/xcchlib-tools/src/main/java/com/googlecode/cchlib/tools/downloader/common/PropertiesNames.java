package com.googlecode.cchlib.tools.downloader.common;

public enum PropertiesNames
{
    PARENT_URL_PROPERTY("parent"),
    ;

    private String str;

    private PropertiesNames( final String str )
    {
        this.str = str;
    }

    @Override
    public String toString()
    {
        return this.str;
    }
}
