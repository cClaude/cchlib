package com.googlecode.cchlib.lang;

/**
 * TODOC
 *
 */
public final class ClassHelper
{
    private ClassHelper() {}

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

}
