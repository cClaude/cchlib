package com.googlecode.cchlib.util.iterator;

import java.util.Collection;

/**
 * A CollectionFilter is a object able to create an {@link Collection} from a other one.
 *
 * @param <T> type of collection.
 */
public interface CollectionFilter<T>
{
    /**
     * Apply filter on an original {@link Collection} to create a
     * new {@link Collection} with only some items of the original one. Original
     * {@link Collection} should not be modify by this method.
     *
     * @param collection Original {@link Collection}
     * @return new {@link Collection} with only some items of the original one.
     */
    Collection<T> apply( Collection<T> collection );
}
