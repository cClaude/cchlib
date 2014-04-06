package com.googlecode.cchlib.lang.reflect;

public class MyTestByName
{
    public int myTest()
    {
        return 0;
    }

    public String myTest( int value )
    {
        return Integer.toString( value );
    }

    public String myTest( Integer value1, int value2 )
    {
        return Integer.toString( value1.intValue() + value2 );
    }

    public String myTest( int...values )
    {
        int som = 0;

        for( int v : values ) {
            som += v;
        }

        return Integer.toString( som );
    }

}
