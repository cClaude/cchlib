package com.googlecode.cchlib.util;

import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @param <KEY> the type of keys maintained by this map
 * @param <VALUE> the type of values
 *
 * @since 4.2
 */
public interface MapSet<KEY, VALUE> extends Map<KEY, Set<VALUE>> {

    boolean containsValueInSet( VALUE value );

    boolean removeInSet( KEY key, VALUE value );

}
