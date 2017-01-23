package com.googlecode.cchlib.lang.reflect;

public class MyTestByName
{
    public MyTestByName()
    {
        // Empty
    }

    public int myTest()
    {
        return 0;
    }

    public String myTest( final int value )
    {
        return Integer.toString( value );
    }

    public String myTest( final Integer value1, final int value2 )
    {
        return Integer.toString( value1.intValue() + value2 );
    }

    public String myTest( final int...values )
    {
        int som = 0;

        for( final int v : values ) {
            som += v;
        }

        return Integer.toString( som );
    }

    public String invokeByNameTest()
    {
        return "MyTestByName";
    }
}
