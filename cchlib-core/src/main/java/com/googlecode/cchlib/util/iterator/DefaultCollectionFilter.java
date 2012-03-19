package com.googlecode.cchlib.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of {@link CollectionFilter} base on a {@link Selectable} object.
 */
public class DefaultCollectionFilter<T> implements CollectionFilter<T>
{
    private Selectable<T> selector;

    /**
     * Create a {@link CollectionFilter} based on a {@link Selectable} object
     *
     * @param selector {@link Selectable} to use to filter entries.
     */
    public DefaultCollectionFilter( final Selectable<T> selector )
    {
        this.selector = selector;
    }

    /**
     * Basic implementation that create a new {@link Collection} according
     * to {@link Selectable} given object when apply method is invoked.
     */
    @Override
    public Collection<T> apply( final Collection<T> elements )
    {
        ArrayList<T> list = new ArrayList<T>();
        Iterator<T>  iter = elements.iterator();

        do {
            if( !iter.hasNext() ) {
                break;
                }

            T o = iter.next();

            if( selector.isSelected(o) ) {
                list.add( o );
                }
            } while(true);

        return list;
    }
}
