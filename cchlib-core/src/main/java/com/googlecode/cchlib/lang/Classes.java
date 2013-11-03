package com.googlecode.cchlib.lang;

/**
 * TODOC
 *
 */
public final class Classes
{
    private static final Class<?>[] EMPTY_ARRAY = new Class<?>[0];

    private Classes() {}

    /**
     * TODOC
     * @param anObject
     * @return TODOC
     */
    public static <E> Class<E> getClass( final E anObject )
    {
        @SuppressWarnings("unchecked")
        final Class<E> result = (Class<E>)((anObject == null) ? null : anObject.getClass()); // $codepro.audit.disable unnecessaryCast
        return result;
    }
    
    public static Class<?>[] emptyArray()
    {
        return EMPTY_ARRAY;
    }
}
