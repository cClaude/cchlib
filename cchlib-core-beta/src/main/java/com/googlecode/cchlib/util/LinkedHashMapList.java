package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import cx.ath.choisnet.ToDo;

/**
 * LinkedHashMapList provide an easy and efficient way
 * to store keys-values when you need to have
 * more than one value for an unique key.
 * <br/>
 * <br/>
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
@ToDo
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

//    /**
//     * Removes all of the mappings from this LinkedHashMapList,
//     * but also perform a {@link Set#clear()} on each
//     * set of values.
//     * <br>
//     * The LinkedHashMapList will be empty after this call returns.
//     *
//     * @see #clear()
//     */
//    public void deepClear()
//    {
////        for( List<V> s:super.values() ) {
////            s.clear();
////            }
////
////        super.clear();
//        for( List<V> l:content.values() ) {
//            l.clear();
//            }
//
//        content.clear();
//    }

//    /**
//     * Returns the number of value in this LinkedHashMap
//     * <p>
//     * Computing valuesSize() is slow process comparing to
//     * {@link #keySize()}, so you must consider to cache
//     * this value.
//     * </p>
//     *
//     * @return the number of value mappings in this LinkedHashMap
//     */
//    public int valuesSize()
//    {
//        int size = 0;
//
//        //for(List<? extends V> s:super.values()) {
//        for(List<? extends V> l:content.values()) {
//            size += l.size();
//            }
//
//        return size;
//    }

//    /**
//     * Returns the number of key in this LinkedHashMap
//     *
//     * @return the number of key mappings in this LinkedHashMap
//     */
//    public int keySize()
//    {
//        return content.size();
//    }

//    /**
//     * TO DO: Doc!
//     *
//     * @throws UnsupportedOperationException
//     */
//    //@Override // Map
//    @ToDo
//    public boolean containsValue( final V value )
//    {
//        for(List<? extends V> l:content.values()) {
//            if( l.contains( value ) ) {
//                return true;
//                }
//            }
//
//        return false;
//    }

//    /**
//     * Add an couple of key value in this LinkedHashMapList.
//     * <p>
//     * If key already exist in this LinkedHashMapList, add value
//     * to corresponding set. If key does not exist create
//     * an new {@link ArrayList} initialized with this value, and
//     * Associates this set with the specified key in this
//     * HashMapSet.
//     * </p>
//     *
//     * @param key  key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     * @return true if Set associated with the specified key
//     *         did not already contain the specified value
//     */
//    public boolean add( final K key, final V value )
//    {
//        List<V> l = content.get(key);
//
//        if( l == null ) {
//            l = new ArrayList<V>();
//
//            content.put(key,l);
//            }
//
//        return l.add( value );
//    }

//    /**
//     * Add all couples of key value in this LinkedHashMapList.
//     * Use {@link #add(Object, Object)} for each entries.
//     *
//     * @param m map to add to current LinkedHashMapList
//     * @return number of values add in LinkedHashMapList.
//     * @see #add(Object, Object) add(Object, Object) for more details
//     */
//    public int addAll( final Map<K,V> m )
//    {
//        int r = 0;
//
//        for( Map.Entry<K,V> e:m.entrySet() ) {
//           if( add( e.getKey(), e.getValue() ) ) {
//               r++;
//               }
//            }
//
//       return r;
//    }

//    /**
//     * Add all values to the same key in this LinkedHashMapList.
//     * <p>
//     * Tips:<br/>
//     * If you want to replace a List&lt;V&gt; for a key, use {@link #put(Object, Object)}
//     * </p>
//     * @param key       key to use for all values
//     * @param values    values to add
//     *
//     * @return number of values add in HashMapSet.
//     */
//    public int addAll( final K key, final Collection<? extends V> values)
//    {
//        int     r = 0;
//        List<V> l = content.get(key);
//
//        if( l == null ) {
//            l = new ArrayList<V>();
//
//            content.put(key,l);
//            }
//
//        for(V v:values) {
//           if( l.add( v ) ) {
//               r++;
//               }
//            }
//
//       return r;
//    }

//    /**
//     * Removes the specified key-value from this LinkedHashMapList
//     * if it is present.
//     *
//     * @param key  key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     *
//     * @return if this set LinkedHashMapList the specified key-value
//     */
//    public boolean remove(final K key, final V value)
//    {
//        final List<V> l = content.get( key );
//
//        if( l != null ) {
//            return l.remove( value );
//            }
//
//        return false;
//    }

//    /**
//     * Returns true if this LinkedHashMapList contains the specified
//     * element. More formally, returns true if and only if
//     * at least one Set contains at least one element e such
//     * that (value==null ? e==null : o.equals(e)).
//     *
//     * @param value element whose presence in this MapOfSet is
//     *              to be tested
//     *
//     * @return true if this LinkedHashMapList contains the specified element
//     */
//    public boolean contains( final V value )
//    {// from Collection<V>
//        for(List<? extends V> l:content.values()) {
//            if( l.contains( value )) {
//                return true;
//                }
//            }
//        return false;
//    }

//    /**
//     * Returns true if this LinkedHashMapList contains all
//     * of the elements in the specified collection.
//     *
//     * @param c collection to be checked for
//     *          containment in this LinkedHashMapList
//     * @return true if this LinkedHashMapList contains all
//     *         of the elements in the specified collection
//     */
//    public boolean containsAll( Collection<? extends V> c )
//    {// from Collection<V>
//        for(V v:c) {
//            if( !contains(v) ) {
//                return false;
//                }
//            }
//        return true;
//    }

//    /**
//     * Returns an iterator over the values in this
//     * LinkedHashMapList.
//     * <p>
//     * If you use {@link Iterator#remove()} you must
//     * consider to {@link #purge()} LinkedHashMapList.
//     * </p>
//     * @return an Iterator over the values in
//     *         this LinkedHashMapList
//     */
//    @Override //Iterable
//    public Iterator<V> iterator()
//    {
//        return new CascadingIterator<V>(
//                content.values().iterator()
//                );
//    }

//    /**
//     * Remove key-Set&lt;V&gt; pair for null or
//     * Set&lt;V&gt; like {@link Set#size()} {@code <} minSetSize
//     *
//     * <p>
//     * purge(2) : remove all key-Set&lt;V&gt; pair that
//     * not contains more than 1 value.
//     * </p>
//     * @param minSetSize minimum size for Sets to be
//     *        keep in LinkedHashMapList
//     */
//    public void purge(int minSetSize)
//    {
//        Iterator<Map.Entry<K,List<V>>> iter = super.entrySet().iterator();
//
//        while(iter.hasNext()) {
//           Map.Entry<K,List<V>> e = iter.next();
//           List<V>              s = e.getValue();
//
//           if( (s==null) || (s.size()<minSetSize) ) {
//               iter.remove();
//               }
//            }
//    }

//    /**
//     * Remove key-Set&lt;V&gt; pair for null or empty
//     * Set&lt;V&gt;
//     * <p>
//     * Invoke {@link #purge(int) purge(1)}
//     * </p>
//     */
//    public void purge()
//    {
//        purge(1);
//    }

//    /**
//     * Returns an <b>unmodifiable</b> Collection view
//     * of V according to LinkedHashMapList.
//     *
//     * @return an unmodifiable Collection view
//     *         of V
//     */
//    public Collection<V> valuesCollection()
//    {
//        return new AbstractCollection<V>()
//        {
//            @Override
//            public Iterator<V> iterator()
//            {
//                return LinkedHashMapList.this.iterator();
//            }
//
//            @Override
//            public int size()
//            {
//                return valuesSize();
//            }
//        };
//    }

//    /**
//     * Get all values on Iterable object, compute
//     * their keys add add(key,values) in this LinkedHashMapList.
//     * <p>
//     * <b>Example of use:</b><br/>
//     * This is probably most efficient way to use
//     * {@link ComputeKeyInterface}, and easy to implements.
//     * <pre>
//     * <u>final</u> Iterable&lt;TVALUE&gt; iterableFinal = <i>any_iterable_object_like_collections</i>;
//     *
//     * linkedHashMapList.addAll(
//     *       <u>new ComputeKeyIterable&lt;TKEY,TVALUE&gt;()</u>
//     *       {
//     *           {@code @Override}
//     *           <u>public Iterator&lt;TVALUE&gt; iterator()</u>
//     *           {
//     *               return iterableFinal.iterator();
//     *           }
//     *           {@code @Override}
//     *           <u>public TKEY computeKey( TVALUE value )</u>
//     *           {
//     *               return <i>computerKeyFromValue(</i>value<i>)</i>;
//     *           }
//     *       });
//     * </pre>
//     * </p>
//     *
//     * @param iterable iterable object of values, that able to
//     *                 compute key for each value.
//     * @see ComputeKeyInterface
//     */
//    public void addAll( HashMapSet.ComputeKeyIterable<K,V> iterable )
//    {
//        Iterator<V> i = iterable.iterator();
//
//        while( i.hasNext() ) {
//            V v = i.next();
//            K k = iterable.computeKey( v );
//            add(k,v);
//            }
//    }
//
//    /**
//     * Add all key-value from ComputeKeyIterator<K,V> iterator
//     *
//     * @param iterator iterator used to get values and compute
//     *        theirs keys.
//     */
//    public void addAll( HashMapSet.ComputeKeyIterator<K,V> iterator )
//    {
//        while(iterator.hasNext()) {
//            V v = iterator.next();
//            K k = iterator.computeKey( v );
//            add(k,v);
//            }
//    }

    /* *
     * Compute key from value
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see LinkedHashMapList.ComputeKeyIterable
     * /
    public interface ComputeKeyInterface<K,V>
    {
        /**
         * Compute key from value.
         *
         * @param value
         * @return key for current value.
         * /
        public K computeKey(V value);
    }*/

    /* *
     * Define an Iterable object able to compute key
     * from values.
     * <p>
     * This is probably the most efficient way to
     * use {@link LinkedHashMapList.ComputeKeyInterface}
     * </p>
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see LinkedHashMapList.ComputeKeyInterface
     * @see LinkedHashMapList.ComputeKeyIterator
     * /
    public interface ComputeKeyIterable<K,V>
        extends Iterable<V>, ComputeKeyInterface<K,V>
    {
    }*/

    /* *
     * Define an Iterator object able to compute key
     * from values.
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see LinkedHashMapList.ComputeKeyInterface
     * @see LinkedHashMapList.ComputeKeyIterable
     * /
    public interface ComputeKeyIterator<K,V>
        extends Iterator<V>, ComputeKeyInterface<K,V>
    {

    }*/

    /* *
     * Default Iterator object able to compute key
     * from values.
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see LinkedHashMapList.ComputeKeyInterface
     * @see LinkedHashMapList.ComputeKeyIterable
     * /
    public static abstract class AbstractComputeKeyIterator<K,V>
        implements ComputeKeyIterator<K,V>
    {
        private Iterator<V> iterator;

        public AbstractComputeKeyIterator(Iterator<V> iterator)
        {
            this.iterator = iterator;
        }
        public AbstractComputeKeyIterator(Iterable<V> iterable)
        {
            this.iterator = iterable.iterator();
        }
        @Override
        public boolean hasNext()
        {
            return iterator.hasNext();
        }
        @Override
        public V next()
        {
            return iterator.next();
        }
        @Override
        public void remove()
        {
            iterator.remove();
        }
    }*/
