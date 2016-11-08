package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

/**
 * LinkedHashMapList provide an easy and efficient way
 * to store keys-values when you need to have
 * more than one value for an unique key.
 * <BR>
 * <BR>
 * Concrete examples:
 * <p>
 * - Store File CustomObject and grouping then by date
 * </p>
 * <p>
 * <b>Starting with this class:</b>
 * <pre>
 *  LinkedHashMapList&lt;Date,CustomObject&gt; hashMapSet = HashMapSet&lt;Date,CustomObject&gt;()
 *
 *  for(...) {
 *    CustomObject myObject = ...
 *
 *    hashMapSet.add( myObject.getDate(), myObject);
 *    }
 * </pre>
 * </p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 03a.34
 */
@NeedDoc
@NeedTestCases
public class LinkedHashMapList<K,V>
    //extends LinkedHashMap<K,List<V>>
        extends AbstractMapCollection<K,V>
        //implements  Iterable<V>,
        //            Serializable
{
    private static final long serialVersionUID = 2L;
//    private LinkedHashMap<K,List<V>> content;
    /**
     * Constructs an empty LinkedHashMapList with the default
     * initial capacity (16) and the default load
     * factor (0.75).
     */
    public LinkedHashMapList()
    {
        super();
        //content = new LinkedHashMap<K,List<V>>();
    }

    /**
     * Constructs an empty LinkedHashMapList with the specified
     * initial capacity and the default load factor (0.75).
     * @param initialCapacity the initial capacity.
     */
    public LinkedHashMapList(int initialCapacity)
    {
        super(initialCapacity);
        //content = new LinkedHashMap<K,List<V>>(initialCapacity);
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
    public LinkedHashMapList( Map<? extends K,? extends List<V>> m )
    {
        super(m);
        //content = new LinkedHashMap<K,List<V>>( m );
    }

    /**
     * Constructs an empty LinkedHashMapList with the specified
     * initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    public LinkedHashMapList( int initialCapacity, float loadFactor )
    {
        super(initialCapacity,loadFactor);
        //content = new LinkedHashMap<K,List<V>>(initialCapacity,loadFactor);
    }

    @Override
    public Collection<V> newCollection()
    {
        return new ArrayList<V>();
    }
}

