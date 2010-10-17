package cx.ath.choisnet.util;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import cx.ath.choisnet.util.iterator.CascadingIterator;

/**
 * HashMapSet provide an easy and efficient way
 * to store keys-values when you need to have
 * more than one value for an unique key.
 * <br/>
 * <br/>
 * Concrete examples:
 * <p>
 * - Store File objects and grouping files with same
 *   size : HashMapSet&lt;Long,File&gt;
 *   <br/>
 *   Where keys are length of files, and values are
 *   File object. 
 * </p>
 * <p>
 * - Store File objects and grouping files with
 *   content size : HashMapSet&lt;<i>any_hash_code</i>,File&gt;
 *   <br/>
 *   Where keys are an hash code based on content (MD5,...), 
 *   and values are File object. 
 * </p>
 * <p>
 * <b>Starting with this class:</b>
 * <pre>
 *  HashMapSet&lt;Long,File&gt; hashMapSet = HashMapSet&lt;Long,File&gt;()
 *  
 *  for(...) {
 *    File file = ...
 *    hashMapSet.add( new Long(file.length), file);
 *    }
 *    
 *  
 * </pre>
 * </p>
 * 
 * @author Claude CHOISNET
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMapSet<K,V> 
    extends HashMap<K,Set<V>>
        implements  Iterable<V>,
                    Serializable

{
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs an empty HashMapSet with the default 
     * initial capacity (16) and the default load
     * factor (0.75). 
     */
    public HashMapSet()
    {
        super();
    }

    /**
     * Constructs an empty HashMapSet with the specified
     * initial capacity and the default load factor (0.75). 
     * @param initialCapacity the initial capacity. 
     */
    public HashMapSet(int initialCapacity)
    {
        super(initialCapacity);
    }

    /**
     * Constructs a new HashMapSet with the same mappings
     * as the specified Map. The HashMapSet is created with
     * default load factor (0.75) and an initial
     * capacity sufficient to hold the mappings in the
     * specified Map.
     * 
     * @param m the map whose mappings are to be 
     *          placed in this map 
     */
    public HashMapSet( Map<? extends K,? extends Set<V>> m )
    {
        super(m);
    }

    /**
     * Constructs an empty HashMapSet with the specified 
     * initial capacity and load factor. 
     * 
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor 
     */
    public HashMapSet( int initialCapacity, float loadFactor )
    {
        super(initialCapacity,loadFactor);
    }

    
    /**
     * Returns the number of value in this map 
     * of Set.
     * <p>
     * Computing valuesSize() is slow process comparing to
     * {@link #size()}, so you must consider to cache 
     * this value.
     * </p>
     * 
     * @return the number of value mappings in this
     *         map of set.
     */
    public int valuesSize()
    {
        int size = 0;
        
        for(Set<? extends V> s:super.values()) {
            size += s.size();
        }
        
        return size;
    }

//    /** CAN'T OVERWRITE containsValue() -> buggy
//     * Returns true if this map maps
//     * one or more keys to the specified value.
//     * <p>
//     * If value is a Set, looking for a Set&lt;V&gt;
//     * in HashMapSet.
//     * <br/>
//     * If value is not a Set, looking throws all
//     * Set&lt;V&gt; to find at least a matching value.
//     * 
//     * @return true if this map maps contains the
//     *         specified value
//     */
//    @Override // Map
//    public boolean containsValue( Object value )
//    {
//        if( value instanceof Set ) {
//            return super.containsValue( value );
//        }
//        for(Set<? extends V> s:super.values()) {
//            if( s.contains( value )) {
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * Add an couple of key value in this HashMapSet.
     * <p>
     * If key already exist in this HashMapSet, add value
     * to corresponding set. If key does not exist create
     * an new HashSet initialized with this value, and 
     * Associates this set with the specified key in this 
     * HashMapSet.
     * </p>
     * 
     * @param key  key with which the specified value is to be associated
     * @param value value to be associated with the specified key 
     * @return true if Set associated with the specified key 
     *         did not already contain the specified value 
     */
    public boolean add( K key, V value )
    {
        Set<V> s = get(key);
        
        if( s == null ) {
            s = new HashSet<V>();
            
            super.put(key,s);
        }
        return s.add( value );
    }
    
    /**
     * Add all couples of key value in this HashMapSet.
     * Use {@link #add(Object, Object)} for each entries.
     * 
     * @param m map to add to current HashMapSet
     * @return if at least one value has been added to
     *         HashMapSet.
     * @see #add(Object, Object) add(Object, Object) for more details
     */
    public boolean addAll(Map<K,V> m)
    {
        boolean r = false;
        
        for(Map.Entry<K,V> e:m.entrySet()) {
           r= add( e.getKey(), e.getValue() );
        }
       
       return r;
    }

    /**
     * TODO: Doc!
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean remove(K key, V value)
    {
        Set<V> s = super.get( key );
        
        if( s != null ) {
            return s.remove( value );
        }
        return false;
    }
    
    /**
     * Returns true if this HashMapSet contains the specified
     * element. More formally, returns true if and only if
     * at least one Set contains at least one element e such
     * that (value==null ? e==null : o.equals(e)). 
     *
     * @param value element whose presence in this MapOfSet is 
     *              to be tested 
     *              
     * @return true if this HashMapSet contains the specified element 
     */
    public boolean contains( V value )
    {// from Collection<V>
        for(Set<? extends V> s:super.values()) {
            if( s.contains( value )) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this HashMapSet contains all 
     * of the elements in the specified collection.
     * 
     * @param c collection to be checked for 
     *          containment in this HashMapSet
     * @return true if this HashMapSet contains all 
     *         of the elements in the specified collection 
     */
    public boolean containsAll( Collection<? extends V> c )
    {// from Collection<V>
        for(V v:c) {
            if( !contains(v) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an iterator over the values in this
     * HashMapSet.
     * 
     * @return an Iterator over the values in 
     *         this HashMapSet
     */
    @Override //Iterable
    public Iterator<V> iterator()
    {
        return new CascadingIterator<V>(
                super.values().iterator()
                );
    }

    /**
     * Returns an <b>unmodifiable</b> Collection view
     * of V according to HashMapSet.
     * 
     * @return an unmodifiable Collection view
     *         of V
     */
    public Collection<V> valuesCollection()
    {
        return new AbstractCollection<V>()
        {
            @Override
            public Iterator<V> iterator()
            {
                return iterator();
            }

            @Override
            public int size()
            {
                return valuesSize();
            }
        };
    }

    /**
     * Get all values on Iterable object, compute
     * their keys add add(key,values) in this HashMapSet.
     * <p>
     * <b>Example of use:</b><br/>
     * This is probably most efficient way to use
     * {@link ComputeKeyInterface}, and easy to implements.
     * <pre>
     * <u>final</u> Iterable&lt;TVALUE&gt; iterableFinal = <i>any_iterable_object_like_collections</i>; 
     * 
     * hashMapSet.addAll(
     *       <u>new ComputeKeyIterable&lt;TKEY,TVALUE&gt;()</u>
     *       {
     *           {@code @Override}
     *           <u>public Iterator&lt;TVALUE&gt; iterator()</u>
     *           {
     *               return iterableFinal.iterator();
     *           }
     *           {@code @Override}
     *           <u>public TKEY computeKey( TVALUE value )</u>
     *           {
     *               return <i>computerKeyFromValue(</i>value<i>)</i>;
     *           }
     *       });
     * </pre>
     * </p>
     * 
     * @param iterable iterable object of values, that able to
     *                 compute key for each value.
     * @see ComputeKeyInterface
     */
    public void addAll( ComputeKeyIterable<K,V> iterable )
    {
        Iterator<V> i = iterable.iterator();
        
        while(i.hasNext()) {
            V v = i.next();
            K k = iterable.computeKey( v );
            add(k,v);
        }
    }
    
    /**
     * TODO: Doc!
     * ComputeKeyIterator<K,V> iterator
     * 
     * @param iterator
     */
    public void addAll( ComputeKeyIterator<K,V> iterator )
    {
        while(iterator.hasNext()) {
            V v = iterator.next();
            K k = iterator.computeKey( v );
            add(k,v);
        }
    }

    /**
     * Compute key from value
     * 
     * @author Claude CHOISNET
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyIterable
     */
    public interface ComputeKeyInterface<K,V>
    {
        /**
         * Compute key from value.
         * 
         * @param value
         * @return key for current value.
         */
        public K computeKey(V value);
    }
    
    /**
     * Define an Iterable object able to compute key
     * from values.
     * <p>
     * This is probably the most efficient way to
     * use {@link HashMapSet.ComputeKeyInterface}
     * </p>
     * 
     * @author Claude CHOISNET
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterator
     */
    public interface ComputeKeyIterable<K,V> 
        extends Iterable<V>, ComputeKeyInterface<K,V>
    {
    }
    
    /**
     * Define an Iterator object able to compute key
     * from values.
     * 
     * @author Claude CHOISNET
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterable
     */
    public interface ComputeKeyIterator<K,V>
        extends Iterator<V>, ComputeKeyInterface<K,V>
    {
        
    }
    
    /**
     * Default Iterator object able to compute key
     * from values.
     * 
     * @author Claude CHOISNET
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterable
     */
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
    }
/*
    public void bidon()
    {
        HashMapSet<String,File>     hms = new HashMapSet<String,File>();
        final java.util.List<File>  lf = new java.util.ArrayList<File>();
        final Set<File>             sf = new HashSet<File>(); 
       
        Iterable<File> iterable;
        iterable = lf;
        iterable = sf;
        
        Iterator<File> iterator;
        iterator = lf.iterator();
        iterator = sf.iterator();
        
        final Iterable<File> iterableFinal = iterable;

        hms.addAll(
            new ComputeKeyIterable<String, File>()
            {
                @Override
                public Iterator<File> iterator()
                {
                    return iterableFinal.iterator();
                }
                @Override
                public String computeKey( File value )
                {
                    return value.getName();
                }
            });

        hms.addAll(
            new AbstractComputeKeyIterator<String,File>(lf)
            {

                @Override
                public String computeKey( File value )
                {
                    // TO DO Auto-generated method stub
                    return null;
                }
            }
        );
    }
*/
}
