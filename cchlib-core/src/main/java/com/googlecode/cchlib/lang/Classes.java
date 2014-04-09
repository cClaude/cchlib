package com.googlecode.cchlib.lang;

import javax.annotation.Nullable;

/**
 * Tools for {@link Class}
 */
public final class Classes
{
    private static final Class<?>[] EMPTY_ARRAY = new Class<?>[0];

    private Classes() {}

    /**
     * Safely return the class of an object
     *
     * @param <E> Type of the object
     * @param anObject Object to get the class
     * @return Class of the object or null if <code>anObject</code> is null.
     */
    public static <E> Class<? extends E> getClass( @Nullable final E anObject )
    {
        @SuppressWarnings("unchecked")
        final Class<E> result = (Class<E>)((anObject == null) ? null : anObject.getClass()); // $codepro.audit.disable unnecessaryCast
        return result;
    }

    /**
     * @return an empty array of classes
     */
    public static Class<?>[] emptyArray()
    {
        return EMPTY_ARRAY;
    }
}
