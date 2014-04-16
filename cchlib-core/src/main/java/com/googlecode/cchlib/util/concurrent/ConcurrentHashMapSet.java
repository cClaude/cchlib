package com.googlecode.cchlib.util.concurrent;
//
//import java.io.Serializable;
//import java.util.AbstractCollection;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import com.googlecode.cchlib.util.iterator.CascadingIterator;
//
///**
// * ConcurrentHashMapSet provide an easy and efficient way
// * to store keys-values when you need to have
// * more than one value for an unique key.
// * <br>
// * <br>
// * Concrete examples:
// * <p>
// * - Store File objects and grouping files with same
// *   size : ConcurrentHashMapSet&lt;Long,File&gt;
// *   <br>
// *   Where keys are length of files, and values are
// *   File object.
// * </p>
// * <p>
// * - Store File objects and grouping files with
// *   content size : ConcurrentHashMapSet&lt;<i>any_hash_code</i>,File&gt;
// *   <br>
// *   Where keys are an hash code based on content (MD5,...),
// *   and values are File object.
// * </p>
// * <br>
// * <br>
// * <b>Starting with this class:</b>
// * <pre>
// *  ConcurrentHashMapSet&lt;Long,File&gt; ConcurrentHashMapSet = ConcurrentHashMapSet&lt;Long,File&gt;()
// *
// *  for(...) {
// *    File file = ...
// *    ConcurrentHashMapSet.add( new Long(file.length), file);
// *    }
// *
// *
// * </pre>
// *
// * @param <K> the type of keys maintained by this map
// * @param <V> the type of values
// */
//public class ConcurrentHashMapSet<K,V>
//    extends ConcurrentHashMap<K,Set<V>>
//        implements  ConcurrentMapSet<K,V>,
//                    Iterable<V>,
//                    Serializable
//
//{
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * Constructs an empty ConcurrentHashMapSet with the default
//     * initial capacity (16) and the default load
//     * factor (0.75).
//     */
//    public ConcurrentHashMapSet()
//    {
//        super();
//    }
//
//    /**
//     * Constructs an empty ConcurrentHashMapSet with the specified
//     * initial capacity and the default load factor (0.75).
//     * @param initialCapacity the initial capacity.
//     */
//    public ConcurrentHashMapSet( final int initialCapacity )
//    {
//        super(initialCapacity);
//    }
//
//    /**
//     * Constructs a new ConcurrentHashMapSet with the same mappings
//     * as the specified Map. The ConcurrentHashMapSet is created with
//     * default load factor (0.75) and an initial
//     * capacity sufficient to hold the mappings in the
//     * specified Map.
//     *
//     * @param m the map whose mappings are to be
//     *          placed in this map
//     */
//    public ConcurrentHashMapSet( final Map<? extends K,? extends Set<V>> m )
//    {
//        super(m);
//    }
//
//    /**
//     * Constructs an empty ConcurrentHashMapSet with the specified
//     * initial capacity and load factor.
//     *
//     * @param initialCapacity the initial capacity
//     * @param loadFactor the load factor
//     */
//    public ConcurrentHashMapSet( final int initialCapacity, final float loadFactor )
//    {
//        super( initialCapacity, loadFactor );
//    }
//
//    /**
//     * Removes all of the mappings from this ConcurrentHashMapSet,
//     * but also perform a {@link Set#clear()} on each
//     * set of values.
//     * <br>
//     * The ConcurrentHashMapSet will be empty after this call returns.
//     *
//     * @see #clear()
//     */
//    public void deepClear()
//    {
//        final List<Set<V>> listOfSets = new ArrayList<Set<V>>( size() );
//        listOfSets.addAll( values() );
//
//        super.clear();
//
//        for( final Set<V> s : listOfSets ) {
//            s.clear();
//            }
//    }
//
//    /**
//     * Returns the number of value in this map
//     * of Set.
//     * <p>
//     * Computing valuesSize() is slow process comparing to
//     * {@link #size()}, so you must consider to cache
//     * this value.
//     * </p>
//     *
//     * @return the number of value mappings in this
//     *         map of set.
//     */
//    public int valuesSize()
//    {
//        int size = 0;
//
//        for( final Set<? extends V> s:super.values() ) {
//            size += s.size();
//            }
//
//        return size;
//    }
//
////    /** CAN'T OVERWRITE containsValue() -> buggy (NOT SUPPORTED)
////     * Returns true if this map maps
////     * one or more keys to the specified value.
////     * <p>
////     * If value is a Set, looking for a Set&lt;V&gt;
////     * in ConcurrentHashMapSet.
////     * <br>
////     * If value is not a Set, looking throws all
////     * Set&lt;V&gt; to find at least a matching value.
////     *
////     * @return true if this map maps contains the
////     *         specified value
////     */
////    @Override // Map
////    public boolean containsValue( Object value )
////    {
////        if( value instanceof Set ) {
////            return super.containsValue( value );
////        }
////        for(Set<? extends V> s:super.values()) {
////            if( s.contains( value )) {
////                return true;
////            }
////        }
////        return false;
////    }
//
//    /**
//     * Not supported
//     *
//     * @throws UnsupportedOperationException Operation is not supported
//     */
//    @Override // Map
//    public boolean containsValue( final Object value )
//    {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Add an couple of key value in this ConcurrentHashMapSet.
//     * <p>
//     * If key already exist in this ConcurrentHashMapSet, add value
//     * to corresponding set. If key does not exist create
//     * an new HashSet initialized with this value, and
//     * Associates this set with the specified key in this
//     * ConcurrentHashMapSet.
//     * </p>
//     *
//     * @param key  key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     * @return true if Set associated with the specified key
//     *         did not already contain the specified value
//     */
//    public boolean add( final K key, final V value ) // $codepro.audit.disable booleanMethodNamingConvention
//    {
//        Set<V> s = get(key);
//
//        if( s == null ) {
//            s = new HashSet<V>();
//
//            super.put(key,s);
//            }
//
//        return s.add( value );
//    }
//
//    /**
//     * Add all couples of key value in this ConcurrentHashMapSet.
//     * Use {@link #add(Object, Object)} for each entries.
//     *
//     * @param m map to add to current ConcurrentHashMapSet
//     * @return number of values add in ConcurrentHashMapSet.
//     * @see #add(Object, Object) add(Object, Object) for more details
//     */
//    public int addAll( final Map<K,V> m )
//    {
//        int addCount = 0;
//
//        for( Map.Entry<K,V> e : m.entrySet() ) {
//           if( add( e.getKey(), e.getValue() ) ) {
//               addCount++;
//               }
//            }
//
//       return addCount;
//    }
//
//    /**
//     * Add all values with same key in this ConcurrentHashMapSet.
//     * <p>
//     * Tips:<br>
//     * If you want to replace a Set&lt;V&gt; for a key, use {@link #put(Object, Object)}
//     * </p>
//     * @param key       key to use for all values
//     * @param values    values to add
//     *
//     * @return number of values add in ConcurrentHashMapSet.
//     */
//    public int addAll( final K key, final Collection<V> values)
//    {
//        int     addCount = 0;
//        Set<V>  set      = get(key);
//
//        if( set == null ) {
//            set = new HashSet<V>();
//
//            super.put(key,set);
//            }
//
//        for(V v:values) {
//           if( set.add( v ) ) {
//               addCount++;
//               }
//            }
//
//       return addCount;
//    }
//
//    /**
//     * Removes the specified key-value from this ConcurrentHashMapSet if it is present, same has
//     * {@link #remove(Object, Object)} but this version use generics.
//     *
//     * @param key  key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     *
//     * @return if this set ConcurrentHashMapSet the specified key-value and if the value was removed
//     */
//    @Override
//    public boolean removeInSet( final K key, final V value ) // $codepro.audit.disable booleanMethodNamingConvention
//    {
//        return remove( key, value );
//    }
//
//    /**
//     * Removes the specified key-value from this ConcurrentHashMapSet if it is present.
//     * <p><b>Change erasure of this method to be compliant with new Java 8 API</b></p>
//     *
//     * @param key  key with which the specified value is to be associated
//     * @param value value to be associated with the specified key
//     *
//     * @return if this set ConcurrentHashMapSet the specified key-value and if the value was removed
//     * @see #removeInSet(Object, Object)
//     */
//    @Override
//    public boolean remove( final Object key, final Object value )  // $codepro.audit.disable booleanMethodNamingConvention
//    {
//        final Set<V> set = super.get( key );
//
//        if( set != null ) {
//            return set.remove( value );
//            }
//
//        return false;
//    }
////Description copied from interface: Map
////Removes the entry for the specified key only if it is currently mapped to the specified value.
////Specified by:
////remove in interface Map<K,V>
////Parameters:
////key - key with which the specified value is associated
////value - value expected to be associated with the specified key
////Returns:
////true if the value was removed
//    /**
//     * Returns true if this ConcurrentHashMapSet contains the specified
//     * element. More formally, returns true if and only if
//     * at least one Set contains at least one element e such
//     * that (value==null ? e==null : o.equals(e)).
//     *
//     * @param value element whose presence in this MapOfSet is
//     *              to be tested
//     *
//     * @return true if this ConcurrentHashMapSet contains the specified element
//     */
//    @Override
//    public boolean containsValueInSet( final V value )
//    {// from Collection<V>
//        for( final Set<? extends V> s : super.values() ) {
//            if( s.contains( value )) {
//                return true;
//                }
//            }
//
//        return false;
//    }
//
//    @Override
//    public boolean contains( final Object value )
//    {// from Collection<V>
//        //FIXME
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Returns true if this ConcurrentHashMapSet contains all
//     * of the elements in the specified collection.
//     *
//     * @param c collection to be checked for
//     *          containment in this ConcurrentHashMapSet
//     * @return true if this ConcurrentHashMapSet contains all
//     *         of the elements in the specified collection
//     */
//    public boolean containsAll( final Collection<? extends V> c )
//    {// from Collection<V>
//        for( final V v : c) {
//            if( !contains(v) ) {
//                return false;
//                }
//            }
//        return true;
//    }
//
//    /**
//     * Returns an iterator over the values in this
//     * ConcurrentHashMapSet.
//     * <p>
//     * If you use {@link Iterator#remove()} you must
//     * consider to {@link #purge()} ConcurrentHashMapSet.
//     * </p>
//     * @return an Iterator over the values in
//     *         this ConcurrentHashMapSet
//     */
//    @Override //Iterable
//    public Iterator<V> iterator()
//    {
//        return new CascadingIterator<V>(
//                super.values().iterator()
//                );
//    }
//
//    /**
//     * Remove key-Set&lt;V&gt; pair for null or
//     * Set&lt;V&gt; like {@link Set#size()} {@code <} minSetSize
//     *
//     * <p>
//     * purge(2) : remove all key-Set&lt;V&gt; pair that
//     * not contains more than 1 value.
//     * </p>
//     * @param minSetSize minimum size for Sets to be
//     *        keep in ConcurrentHashMapSet
//     */
//    public void purge( final int minSetSize )
//    {
//        final Iterator<Map.Entry<K, Set<V>>> iter = super.entrySet().iterator();
//
//        while(iter.hasNext()) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
//            final Map.Entry<K, Set<V>> e = iter.next();
//            final Set<V>               s = e.getValue();
//
//            if( (s==null) || (s.size()<minSetSize) ) {
//                iter.remove();
//                }
//            }
//    }
//
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
//
//    /**
//     * Returns an <b>unmodifiable</b> Collection view
//     * of V according to ConcurrentHashMapSet.
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
//                return ConcurrentHashMapSet.this.iterator();
//            }
//            @Override
//            public int size()
//            {
//                return valuesSize();
//            }
//        };
//    }
//
//    /**
//     * Get all values on Iterable object, compute
//     * their keys add add(key,values) in this ConcurrentHashMapSet.
//     * <br>
//     * <br>
//     * <b>Example of use:</b><br>
//     * This is probably most efficient way to use
//     * {@link ComputeKeyInterface}, and easy to implements.
//     * <pre>
//     * <u>final</u> Iterable&lt;TVALUE&gt; iterableFinal = <i>any_iterable_object_like_collections</i>;
//     *
//     * ConcurrentHashMapSet.addAll(
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
//     *
//     * @param iterable iterable object of values, that able to
//     *                 compute key for each value.
//     * @see ComputeKeyInterface
//     */
//    public void addAll( final ComputeKeyIterable<K,V> iterable )
//    {
//        final Iterator<V> i = iterable.iterator();
//
//        while(i.hasNext()) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
//            V v = i.next();
//            K k = iterable.computeKey( v );
//            add(k,v);
//            }
//    }
//
//    /**
//     * Add all key-value from ComputeKeyIterator&lt;K,V&gt; iterator
//     *
//     * @param iterator iterator used to get values and compute
//     *        theirs keys.
//     */
//    public void addAll( final ComputeKeyIterator<K,V> iterator )
//    {
//        while( iterator.hasNext() ) {
//            V v = iterator.next();
//            K k = iterator.computeKey( v );
//            add(k,v);
//            }
//    }
//
//    /**
//     * Compute key from value
//     *
//     * @param <K> the type of keys computed from current value
//     * @param <V> the type of values
//     * @see ConcurrentHashMapSet.ComputeKeyIterable
//     */
//    public interface ComputeKeyInterface<K,V>
//    {
//        /**
//         * Compute key from value.
//         *
//         * @param value Value
//         * @return key for current value.
//         */
//        K computeKey(V value);
//    }
//
//    /**
//     * Define an Iterable object able to compute key
//     * from values.
//     * <p>
//     * This is probably the most efficient way to
//     * use {@link ConcurrentHashMapSet.ComputeKeyInterface}
//     * </p>
//     *
//     * @param <K> the type of keys computed from current value
//     * @param <V> the type of values
//     * @see ConcurrentHashMapSet.ComputeKeyInterface
//     * @see ConcurrentHashMapSet.ComputeKeyIterator
//     */
//    public interface ComputeKeyIterable<K,V>
//        extends Iterable<V>, ComputeKeyInterface<K,V>
//    {
//    }
//
//    /**
//     * Define an Iterator object able to compute key
//     * from values.
//     *
//     * @param <K> the type of keys computed from current value
//     * @param <V> the type of values
//     * @see ConcurrentHashMapSet.ComputeKeyInterface
//     * @see ConcurrentHashMapSet.ComputeKeyIterable
//     */
//    public interface ComputeKeyIterator<K,V>
//        extends Iterator<V>, ComputeKeyInterface<K,V>
//    {
//
//    }
//
//    /**
//     * Default Iterator object able to compute key
//     * from values.
//     *
//     * @param <K> the type of keys computed from current value
//     * @param <V> the type of values
//     * @see ConcurrentHashMapSet.ComputeKeyInterface
//     * @see ConcurrentHashMapSet.ComputeKeyIterable
//     */
//    public abstract static class AbstractComputeKeyIterator<K,V>
//        implements ComputeKeyIterator<K,V>
//    {
//        private Iterator<V> iterator;
//
//        public AbstractComputeKeyIterator(final Iterator<V> iterator)
//        {
//            this.iterator = iterator;
//        }
//        public AbstractComputeKeyIterator(final Iterable<V> iterable)
//        {
//            this.iterator = iterable.iterator();
//        }
//        @Override
//        public boolean hasNext()
//        {
//            return iterator.hasNext();
//        }
//        @Override
//        public V next()
//        {
//            return iterator.next();
//        }
//        @Override
//        public void remove()
//        {
//            iterator.remove();
//        }
//    }
//}
//ConcurrentMap<Person.Sex, List<Person>> byGender =
//roster
//    .parallelStream()
//    .collect(
//        Collectors.groupingByConcurrent(Person::getGender));
