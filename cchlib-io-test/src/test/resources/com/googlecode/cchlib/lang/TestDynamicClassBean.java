package com.googlecode.cchlib.lang;

/**
 * Test class to be compile, then add in class path, and use
 */
public class TestDynamicClassBean
{
    private String value;

    public TestDynamicClassBean()
    {
        // empty
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public int length()
    {
        return value.length();
    }
}
