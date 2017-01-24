package com.googlecode.cchlib.lang.reflect;

public class ExtendsMyTestByName extends MyTestByName
{
    public ExtendsMyTestByName()
    {
        // Empty
    }

    @Override
    public String invokeByNameTest()
    {
        return "ExtendsMyTestByName";
    }

    public int invokeByNameTest( final int a, final int b )
    {
        return a + b;
    }

    public int add( final Integer a, final Integer b )
    {
        return invokeByNameTest( a.intValue(), b.intValue() );
    }
}
