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
    private final Map<String,T> map = new HashMap<>();

    public void add( final String key, final T value )
    {
        map.put( key, value );
    }

    @Override
    public Iterator<Map.Entry<String,T>> iterator()
    {
        final Set<Map.Entry<String,T>>       entrySet = map.entrySet();
        final ArrayList<Map.Entry<String,T>> list     = new ArrayList<>( entrySet );

        Collections.sort( list, new Comparator<Map.Entry<String,T>>() {
            @Override
            public int compare( final Map.Entry<String,T> o1, final Map.Entry<String,T> o2 )
            {
                return o1.getKey().compareTo( o2.getKey() );
            }
        });

        return Collections.unmodifiableList( list ).iterator();
    }
}
