package com.googlecode.cchlib.lang;

import java.lang.reflect.Array;

/**
 *
 */
public final class Objects
{
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private Objects()
    {
    }

    public static Object[] emptyArray()
    {
        return EMPTY_ARRAY;
    }

    public static <T> T[] emptyArray( final Class<T> clazz )
    {
        @SuppressWarnings({ "squid:S1488", "unchecked" })
        final T[] array = (T[])Array.newInstance(clazz,0);
        return array;
    }
}
