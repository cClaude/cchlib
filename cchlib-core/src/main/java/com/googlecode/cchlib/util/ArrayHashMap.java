package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.Beta;

/**
 * TODO: Improve this code
 */
@Beta
public class ArrayHashMap<K,V> implements Map<K,V>, Serializable
{
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<K,V> delegator; // TODO: Change this !

    /**
     *
     */
    public ArrayHashMap()
    {
        delegator = new LinkedHashMap<K,V>();
    }

    public ArrayHashMap( int initialCapacity )
    {
        delegator = new LinkedHashMap<K,V>( initialCapacity );
    }

    public ArrayHashMap( Map<? extends K, ? extends V> m )
    {
        delegator = new LinkedHashMap<K,V>( m );
    }

    public ArrayHashMap( int initialCapacity, float loadFactor )
    {
        delegator = new LinkedHashMap<K,V>( initialCapacity, loadFactor );
    }

    @Override
    public int size()
    {
        return delegator.size();
    }

    @Override
    public boolean isEmpty()
    {
        return delegator.isEmpty();
    }

    @Override
    public boolean containsKey( Object key )
    {
        return delegator.containsKey( key );
    }

    @Override
    public boolean containsValue( Object value )
    {
        return delegator.containsValue( value );
    }

    @Override
    public V get( Object key )
    {
        return delegator.get( key );
    }

    @Override
    public V put( K key, V value )
    {
        return delegator.put( key, value );
    }

    @Override
    public V remove( Object key )
    {
        return delegator.remove( key );
    }

    @Override
    public void putAll( Map<? extends K, ? extends V> m )
    {
        delegator.putAll( m );
    }

    @Override
    public void clear()
    {
        delegator.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return delegator.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return Collections.unmodifiableCollection( delegator.values() );
    }

    @Override
    public Set<Map.Entry<K,V>> entrySet()
    {
        return delegator.entrySet();
    }

    public List<Map.Entry<K,V>> entryList()
    {
        return new AbstractList<Map.Entry<K,V>>()
        {
            @Override
            public Map.Entry<K, V> get( final int index )
            {
                return ArrayHashMap.this.get( index );
            }
            @Override
            public int size()
            {
                return ArrayHashMap.this.size();
            }
        };
    }

    public Map.Entry<K,V> get( final int index )
    {
        // TODO
        Iterator<Map.Entry<K,V>> iter  = entrySet().iterator();
        int                      count = 0;

        while( iter.hasNext() ) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
            Map.Entry<K,V> entry = iter.next();

            if( count == index ) {
                return entry;
                }
            count++;
            }
        throw new ArrayIndexOutOfBoundsException( index );
    }

    public K getKey( final int index )
    {
        return get( index ).getKey();
    }

    public V getValue( final int index )
    {
        return get( index ).getValue();
    }
}
