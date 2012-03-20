package com.googlecode.cchlib.util.enumeration;

import java.util.Enumeration;

/**
 * Build a new Enumeration that consume first
 * Enumeration and second Enumeration for it's
 * results (Order is preserve)
 *
 * @param <T> content type
 * @since 4.1.7
 */
public class BiEnumeration<T>
    implements Enumeration<T>
{
    private Enumeration<T> firstEnum;
    private Enumeration<T> secondEnum;

    /**
     * Create a {@link Enumeration} based on 2 {@link Enumeration}s
     * @param firstEnum  First {@link Enumeration}
     * @param secondEnum Second {@link Enumeration}
     */
    public BiEnumeration(Enumeration<T> firstEnum, Enumeration<T> secondEnum)
    {
        this.firstEnum = firstEnum;
        this.secondEnum = secondEnum;
    }

    @Override
    public boolean hasMoreElements()
    {
        if( firstEnum.hasMoreElements() ) {
            return true;
            }
        else {
            return secondEnum.hasMoreElements();
            }
    }

    @Override
    public T nextElement()
            throws java.util.NoSuchElementException
    {
        if(firstEnum.hasMoreElements()) {
            return firstEnum.nextElement();
        }
        else {
            return secondEnum.nextElement();
        }
    }
}
