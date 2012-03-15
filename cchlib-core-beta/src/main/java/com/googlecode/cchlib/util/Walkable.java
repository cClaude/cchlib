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
    public void walk(Visitor<T> visitor);
}
