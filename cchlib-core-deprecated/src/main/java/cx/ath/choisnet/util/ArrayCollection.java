package cx.ath.choisnet.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Deprecated
public class ArrayCollection<T> extends AbstractCollection<T>
{
    private final List<T> list;

    public ArrayCollection()
    {
        list = new LinkedList<T>();
    }

    public ArrayCollection( T[] array )
    {
        if(array == null) {
            list = new LinkedList<T>();
        }
        else {
            list = new ArrayList<T>(array.length);
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; i$++) {
                T item = array[i$];
                add(item);
            }
        }
    }

    public ArrayCollection(T[] array, int offset, int len)
    {
        list = new ArrayList<T>(array.length);

        for(int i = offset; i < len; i++) {
            add(array[i]);
        }
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public Iterator<T> iterator()
    {
        return list.iterator();
    }

    public ArrayCollection<T> append(T element)
    {
        add(element);

        return this;
    }
}
