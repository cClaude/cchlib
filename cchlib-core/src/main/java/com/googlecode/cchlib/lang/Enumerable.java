package com.googlecode.cchlib.lang;

import java.util.Enumeration;

/**
 * This interface is to design to identify classes
 * able to be enumerated.
 *   
 * @see Iterable
 * @since 4.1.7
 */
public interface Enumerable<T>
{
    /**
     * Returns an enumeration over a set of elements 
     * of type T. 
     * @return an enumeration
     */
    public Enumeration<T> enumeration();
}
