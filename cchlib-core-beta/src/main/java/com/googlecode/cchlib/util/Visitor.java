package com.googlecode.cchlib.util;

/**
 * TODOC
 */
public interface Visitor<T>
{
    /**
     * Invoked for each entry in the contender.
     *
     * @param entry current entry
     * @return the visit result
     */
    VisitResult visite(T entry);
}
