package com.googlecode.cchlib.i18n.prep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PrepCollector<T> implements Iterable<Map.Entry<String,T>>
{
    private Map<String,T> map = new HashMap<String,T>();

    public void add( String key, T value )
    {
        map.put( key, value );
    }

    @Override
    public Iterator<Map.Entry<String,T>> iterator()
    {
        Set<Map.Entry<String,T>>       entrySet = map.entrySet();
        ArrayList<Map.Entry<String,T>> list     = new ArrayList<Map.Entry<String,T>>( entrySet );

        Collections.sort( list, new Comparator<Map.Entry<String,T>>() {
            @Override
            public int compare( Map.Entry<String,T> o1, Map.Entry<String,T> o2 )
            {
                return o1.getKey().compareTo( o2.getKey() );
            }
        });

        return list.iterator();
    }
}
