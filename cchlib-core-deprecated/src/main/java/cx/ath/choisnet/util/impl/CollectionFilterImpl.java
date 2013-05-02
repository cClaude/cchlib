package cx.ath.choisnet.util.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of {@link cx.ath.choisnet.util.CollectionFilter} 
 * base on a {@link cx.ath.choisnet.util.Selectable} object.
 * @deprecated use {@link com.googlecode.cchlib.util.iterator.DefaultCollectionFilter} instead
 */
@Deprecated
public class CollectionFilterImpl<T> implements cx.ath.choisnet.util.CollectionFilter<T>
{
    private cx.ath.choisnet.util.Selectable<T> selector;

    /**
     * Create a {@link cx.ath.choisnet.util.CollectionFilter}
     * based on a {@link cx.ath.choisnet.util.Selectable} object
     *
     * @param selector {@link cx.ath.choisnet.util.Selectable} 
     * to use to filter entries.
     */
    public CollectionFilterImpl( final cx.ath.choisnet.util.Selectable<T> selector )
    {
        this.selector = selector;
    }

    /**
     * Basic implementation that create a new {@link Collection} according
     * to {@link cx.ath.choisnet.util.Selectable} given object when apply 
     * method is invoked.
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
