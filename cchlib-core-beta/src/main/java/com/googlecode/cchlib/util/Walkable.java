package com.googlecode.cchlib.util;

/**
 * TODOC
 *
 */
public interface Walkable<T>
{
    /**
     * TODOC
     *
     * @param visitor
     */
    void walk(Visitor<T> visitor);
}
