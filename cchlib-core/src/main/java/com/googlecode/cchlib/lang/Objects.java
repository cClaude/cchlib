package com.googlecode.cchlib.lang;

/**
 *
 */
public final class Objects
{
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private Objects()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] emptyArray()
    {
        return (T[])EMPTY_ARRAY;
    }
}
