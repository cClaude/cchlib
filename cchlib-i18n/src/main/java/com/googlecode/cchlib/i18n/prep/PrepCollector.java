package com.googlecode.cchlib.i18n.prep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;

/**
 * @param <T> deprecated
 * @deprecated use {@link I18nResourceBuilderFactory} instead
 */
@Deprecated
public final class PrepCollector<T> implements Iterable<Map.Entry<String, T>> {
    private final Map<String, T> map = new HashMap<>();

    public void add( final String key, final T value )
    {
        this.map.put( key, value );
    }

    @Override
    public Iterator<Map.Entry<String, T>> iterator()
    {
        final Set<Map.Entry<String, T>> entrySet = this.map.entrySet();
        final ArrayList<Map.Entry<String, T>> list = new ArrayList<>( entrySet );

        Collections.sort( list, this::compare );

        return Collections.unmodifiableList( list ).iterator();
    }

    private int compare( final Map.Entry<String, T> o1, final Map.Entry<String, T> o2 )
    {
        return o1.getKey().compareTo( o2.getKey() );
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    public int size()
    {
        return this.map.size();
    }
}
