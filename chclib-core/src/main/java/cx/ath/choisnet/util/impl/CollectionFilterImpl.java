package cx.ath.choisnet.util.impl;

import cx.ath.choisnet.util.CollectionFilter;
import cx.ath.choisnet.util.Selectable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Claude CHOISNET
 * @param <T> 
 *
 */
public class CollectionFilterImpl<T> implements CollectionFilter<T>
{

    private Selectable<T> selector;

    public CollectionFilterImpl(Selectable<T> selector)
    {
        this.selector = selector;
    }

    public Collection<T> apply(Collection<T> elements)
    {
        LinkedList<T> list = new LinkedList<T>();
        Iterator<T> iter = elements.iterator();

        do {
            if( !iter.hasNext() ) {
                break;
            }

            T o = iter.next();

            if(selector.isSelected(o)) {
                list.add(o);
            }

        } while(true);

        return list;
    }
}
