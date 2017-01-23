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
}
