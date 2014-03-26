package com.googlecode.cchlib.tools.sortfiles.impl;

import com.googlecode.cchlib.tools.sortfiles.ParsedFilename;

public class DefaultParsedFilename implements ParsedFilename
{
    private String rawPhoneNumber;
    private String rawDate;

    @Override
    public ParsedFilename setRawPhoneNumber( final String rawPhoneNumber )
    {
        this.rawPhoneNumber = rawPhoneNumber;
        return this;
    }

    @Override
    public String getRawPhoneNumber()
    {
        return rawPhoneNumber;
    }

    @Override
    public ParsedFilename setRawDate( final String rawDate )
    {
        this.rawDate = rawDate;
        return this;
    }

    @Override
    public String getRawDate()
    {
        return rawDate;
    }
}
