package com.googlecode.cchlib.util;

import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of values
 *
 * @since 4.2
 * @see HashMapSet
 */
public interface XMapSet<K,V> extends Map<K,Set<V>>
{
    boolean containsValueInSet( V value );

    boolean removeInSet( K key, V value );

    /**
     * Add an couple of key value in this HashMapSet.
     * <p>
     * If key already exist in this HashMapSet, add value
     * to corresponding set. If key does not exist create
     * an new HashSet initialized with this value, and
     * Associates this set with the specified key in this
     * HashMapSet.
     * </p>
     *
     * @param key  key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return true if Set associated with the specified key
     *         did not already contain the specified value
     */
    boolean add( K key, V value );
}
