package com.googlecode.cchlib.util;

/**
 * NEEDDOC
 *
 */
public interface Walkable<T>
{
    /**
     * NEEDDOC
     *
     * @param visitor
     */
    void walk(Visitor<T> visitor);
}
