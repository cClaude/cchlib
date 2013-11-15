package com.googlecode.cchlib.util.iterator;

/**
 * Identify if an Object should be selected or not.
 *
 * @param <T> content type
 * @see com.googlecode.cchlib.util.iterator.IteratorFilter
 * @see DefaultCollectionFilter
 *
 */
public interface Selectable<T>
{
    /**
     * Identify if giving object should be select
     *
     * @param obj Object to test
     * @return true if object should be selected,
     *         false otherwise
     */
    boolean isSelected(T obj);
}
