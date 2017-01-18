package com.googlecode.cchlib.util.mappable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MBTestObject implements Iterable<String>
{
    private final boolean      bool;
    private final int          integer;
    private final List<String> listOfString  = new ArrayList<>();
    private final List<String> listOfString2 = new ArrayList<>();
    private final String       nullString;
    private final String       string;

    MBTestObject()
    {
        this.bool         = true;
        this.integer      = -10;
        this.nullString   = null;
        this.string       = "string";

        this.listOfString.add( "Str1" );
        this.listOfString.add( "Str2" );

        this.listOfString2.add( "2Str1" );
        this.listOfString2.add( "2Str2" );
    }

    public int getInteger()
    {
        return getPrivateInteger();
    }

    private int getPrivateInteger()
    {
        return this.integer;
    }

    protected int getProtectedInteger()
    {
        return getPrivateInteger();
    }

    int getPackageInteger()
    {
        return getPrivateInteger();
    }

    public List<String> getListOfString()
    {
        return this.listOfString;
    }

    public Iterable<String> getListOfString2()
    {
        return this.listOfString2;
    }

    public String getNullString()
    {
        return this.nullString;
    }

    public String getString()
    {
        return this.string;
    }

    public boolean isBool()
    {
        return this.bool;
    }

    @Override
    public Iterator<String> iterator()
    {
        return this.listOfString.iterator();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (this.bool ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final MBTestObject other = (MBTestObject)obj;
        if( this.bool != other.bool ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "MBTestObject [bool=" );
        builder.append( this.bool );
        builder.append( "]" );
        return builder.toString();
    }
}
