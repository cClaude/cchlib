package com.googlecode.cchlib.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.util.iterator.CascadingIterator;

/**
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 03a.34
 */
public abstract class AbstractMapCollection<K,V>
    implements MapCollection<K,V>
{
    // TODO: cache sizeValue !
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<K,Collection<V>> map;

    /**
     * Constructs an empty LinkedHashMapList with the default
     * initial capacity (16) and the default load
     * factor (0.75).
     */
    public AbstractMapCollection()
    {
        map = new LinkedHashMap<K,Collection<V>>();
    }

    /**
     * Constructs an empty LinkedHashMapList with the specified
     * initial capacity and the default load factor (0.75).
     * @param initialCapacity the initial capacity.
     */
    public AbstractMapCollection( final int initialCapacity )
    {
        map = new LinkedHashMap<K,Collection<V>>(initialCapacity);
    }

    /**
     * Constructs a new LinkedHashMapList with the same mappings
     * as the specified Map. The LinkedHashMapList is created with
     * default load factor (0.75) and an initial
     * capacity sufficient to hold the mappings in the
     * specified Map.
     *
     * @param m the map whose mappings are to be
     *          placed in this map
     */
    public AbstractMapCollection( final Map<? extends K,? extends Collection<V>> m )
    {
        map = new LinkedHashMap<K,Collection<V>>( m );
    }

    /**
     * Constructs an empty LinkedHashMapList with the specified
     * initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    public AbstractMapCollection( final int initialCapacity, final float loadFactor )
    {
        map = new LinkedHashMap<K,Collection<V>>(initialCapacity,loadFactor);
    }

    /**
     * Returns a new empty Collection
     * @return a new empty Collection
     */
    public abstract Collection<V> newCollection();

    @Override
    public boolean add( final K key, final V value)
    {
        Collection<V> collection = getCollection( key );

        if( collection == null ) {
            collection = newCollection();
            map.put( key, collection );
            }

        return collection.add( value );
    }

    @Override
    public int addAll( final K key, final Collection<V> values)
    {
        int             result     = 0;
        Collection<V>   collection = map.get(key);

        if( collection == null ) {
            collection = newCollection();

            map.put(key,collection);
            }

        for( final V value : values ) {
           if( collection.add( value ) ) {
               result++;
               }
            }

       return result;
   }

    @Override
    public int addAll( final Map<K,V> map )
    {
        int result = 0;

        for( final Map.Entry<K,V> e:map .entrySet() ) {
           if( add( e.getKey(), e.getValue() ) ) {
               result++;
               }
            }

       return result;
   }

    @Override
    public boolean containsValue( final V value )
    {
        for( final Collection<? extends V> c : map.values() ) {
            if( c.contains( value ) ) {
                return true;
                }
            }

        return false;
    }

    @Override
    public boolean containsAll( final Collection<? extends V> colletion )
    {
        for( V value : colletion ) {
            if( !containsValue( value ) ) {
                return false;
                }
            }

        return true;
    }

    @Override
    public boolean containsKey( final K key )
    {
        return map.containsKey( key );
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public void deepClear()
    {
        for( final Collection<V> c : map.values() ) {
            c.clear();
            }

        clear();
    }

    @Override
    public Iterator<V> iterator()
    {
        return new CascadingIterator<V>(
                map.values().iterator()
                );
    }

    @Override
    public boolean remove( final K key, final V value)
    {
        final Collection<V> c = map.get( key );

        if( c != null ) {
            return c.remove( value );
            }

        return false;
    }

    @Override
    public Collection<V> remove( final K key )
    {
        return map.remove( key );
    }

    @Override
    public Collection<V> valuesCollection()
    {
        return new AbstractCollection<V>()
        {
            @Override
            public Iterator<V> iterator()
            {
                return AbstractMapCollection.this.iterator();
            }
            @Override
            public int size()
            {
                return valuesSize();
            }
        };
    }

    @Override
    public int keySize()
    {
        return map.size();
    }

    @Override
   public int valuesSize()
    {
        int size = 0;

        for(Collection<? extends V> c:map.values()) {
            size += c.size();
            }

        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public Set<Map.Entry<K,Collection<V>>> entrySet()
    {
        return map.entrySet();
    }

    @Override
    public Collection<V> getCollection( K key )
    {
        return map.get( key );
    }

    /**
     * Returns a shallow copy of this AbstractMapCollection instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this AbstractMapCollection
     * @see Cloneable
     */
    @Override
    public Object clone()
    {
        AbstractMapCollection<K,V> clone = new AbstractMapCollection<K,V>( map.size() )
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Collection<V> newCollection()
            {
                return AbstractMapCollection.this.newCollection();
            }
        };

        for( Map.Entry<K,Collection<V>> e: map.entrySet() ) {
            Collection<V> c = newCollection();

            for( V v: e.getValue() ) {
                c.add( v );
                }

            clone.map.put( e.getKey(), c );
        }

        return clone;
    }

    @Override
    public boolean equals( Object o )
    {
        if( o == this ) {
            return true;
            }

        if( o instanceof MapCollection ) {
            @SuppressWarnings("unchecked")
            MapCollection<K,V> omap = (MapCollection<K,V>)o;

            if( omap.keySize() != keySize() ) {
                return false;
                }

            for( Map.Entry<K,Collection<V>> e : map.entrySet() ) {
                Collection<V> oc = omap.getCollection( e.getKey() );

                if( oc == null ) {
                    return false;
                    }

                Collection<V> c = e.getValue();

                if( ! c.equals( oc ) ) {
                    return false;
                    }
                }
            }

        return false;
    }

    @Override
    public int hashCode()
    {
        return map.hashCode();
    }

    //TODO: toString

//  void   purge()
//  Remove key-Set<V> pair for null or empty Set<V> Invoke purge(1)
//void   purge(int minSetSize)
//  Remove key-Set<V> pair for null or Set<V> like Set.size() < minSetSize purge(2) : remove all key-Set<V> pair that not contains more than 1 value.

//  /**
//   * Get all values on Iterable object, compute their keys add add(key,values) in this HashMapSet.
//   * @param iterable
//   */
//  void   addAll(HashMapSet.ComputeKeyIterable<K,V> iterable)
//  void   addAll(HashMapSet.ComputeKeyIterator<K,V> iterator)
//           Add all key-value from ComputeKeyIterator iterator

    //,*/ get keySet, put, putAll, remove, values


}
