package com.googlecode.cchlib.util;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.util.iterator.CascadingIterator;

/**
 * {@link HashMapSet} provide an easy and efficient way
 * to store keys-values when you need to have
 * more than one value for an unique key.
 * <br>
 * <br>
 * Concrete examples:
 * <p>
 * - Store File objects and grouping files with same
 *   size : {@link HashMapSet}&lt;{@link Long},{@link File}&gt;
 *   <br>
 *   Where keys are length of files, and values are
 *   File object.
 * </p>
 * <p>
 * - Store File objects and grouping files with
 *   content size : {@link HashMapSet}&lt;<i>any_hash_code</i>,{@link File}&gt;
 *   <br>
 *   Where keys are an hash code based on content (MD5,...),
 *   and values are File object.
 * </p>
 * <br>
 * <br>
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
 *
 * @param <KEY> the type of keys maintained by this map
 * @param <VALUE> the type of values
 */
@SuppressWarnings("squid:S00119")
public class HashMapSet<KEY,VALUE>
    extends HashMap<KEY,Set<VALUE>>
        implements  XMapSet<KEY,VALUE>,
                    Iterable<VALUE>,
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
    public HashMapSet( final int initialCapacity )
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
    public HashMapSet( final Map<? extends KEY,? extends Set<VALUE>> m )
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
    public HashMapSet( final int initialCapacity, final float loadFactor )
    {
        super( initialCapacity, loadFactor );
    }

    /**
     * Removes all of the mappings from this HashMapSet,
     * but also perform a {@link Set#clear()} on each
     * set of values.
     * <br>
     * The HashMapSet will be empty after this call returns.
     *
     * @see #clear()
     */
    public void deepClear()
    {
        for( final Set<VALUE> s : super.values() ) {
            s.clear();
            }

        super.clear();
    }

    /**
     * Returns the number of value in this {@link Map}
     * of {@link Set}. More formerly the sum of size of
     * each {@link Set} in the {@link Map}
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
        return MapSetHelper.size( this );
    }

    /*
     * CAN'T OVERWRITE containsValue() -> buggy (NOT SUPPORTED)
     * Returns true if this map maps
     * one or more keys to the specified value.
     * <p>
     * If value is a Set, looking for a Set&lt;V&gt; in
      HashMapSet.
     * <br>
     * If value is not a Set, looking throws all
     * Set&lt;V&gt; to find at least a matching value.
     *
     * @return true if this map maps contains the
     *         specified value
     */
//    @Override // Map
//    public boolean containsValue( Object value )
//    { -
//        "if_ value instance of Set _ {"
//            return super.containsValue( value ); -
//        } -
//        for_ Set<? extends V> s:super.values()_ { -
//            if_ s.contains( value )_ { -
//                return true; -
//            } -
//        } -
//        return false; -
//    } -

    /**
     * Not supported
     *
     * @throws UnsupportedOperationException Operation is not supported
     */
    @Override // Map
    public boolean containsValue( final Object value )
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add( final KEY key, final VALUE value ) // $codepro.audit.disable booleanMethodNamingConvention
    {
        Set<VALUE> s = get(key);

        if( s == null ) {
            s = new HashSet<>();

            super.put(key,s);
            }

        return s.add( value );
    }

    /**
     * Add all couples of key value in this HashMapSet.
     * Use {@link #add(Object, Object)} for each entries.
     *
     * @param m map to add to current HashMapSet
     * @return number of values add in HashMapSet.
     * @see #add(Object, Object) add(Object, Object) for more details
     */
    public int addAll( final Map<KEY,VALUE> m )
    {
        int addCount = 0;

        for( final Map.Entry<KEY,VALUE> e : m.entrySet() ) {
           if( add( e.getKey(), e.getValue() ) ) {
               addCount++;
               }
            }

       return addCount;
    }

    /**
     * Add all values with same key in this HashMapSet.
     * <p>
     * Tips:<br>
     * If you want to replace a Set&lt;V&gt; for a key, use {@link #put(Object, Object)}
     * </p>
     * @param key       key to use for all values
     * @param values    values to add
     *
     * @return number of values add in HashMapSet.
     */
    public int addAll( final KEY key, final Collection<VALUE> values)
    {
        int     addCount = 0;
        Set<VALUE>  set      = get(key);

        if( set == null ) {
            set = new HashSet<>();

            super.put(key,set);
            }

        for(final VALUE v:values) {
           if( set.add( v ) ) {
               addCount++;
               }
            }

       return addCount;
    }

    /**
     * Removes the specified key-value from this HashMapSet if it is present, same has
     * {@link #remove(Object, Object)} but this version use generics.
     *
     * @param key  key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return if this set HashMapSet the specified key-value and if the value was removed
     */
    @Override
    public boolean removeInSet( final KEY key, final VALUE value ) // $codepro.audit.disable booleanMethodNamingConvention
    {
        return remove( key, value );
    }

    /**
     * Removes the specified key-value from this HashMapSet if it is present.
     * <p><b>Change erasure of this method to be compliant with new Java 8 API</b></p>
     *
     * @param key  key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return if this set HashMapSet the specified key-value and if the value was removed
     * @see #removeInSet(Object, Object)
     */
    @Override
    public boolean remove( final Object key, final Object value )  // $codepro.audit.disable booleanMethodNamingConvention
    {
        final Set<VALUE> set = super.get( key );

        if( set != null ) {
            return set.remove( value );
            }

        return false;
    }
//Description copied from interface: Map
//Removes the entry for the specified key only if it is currently mapped to the specified value.
//Specified by:
//remove in interface Map<K,V>
//Parameters:
//key - key with which the specified value is associated
//value - value expected to be associated with the specified key
//Returns:
//true if the value was removed
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
    @Override
    public boolean containsValueInSet( final VALUE value )
    {// from Collection<V>
        for( final Set<? extends VALUE> s : super.values() ) {
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
    public boolean containsAll( final Collection<? extends VALUE> c )
    {// from Collection<V>
        for( final VALUE v : c) {
            if( !containsValueInSet( v ) ) {
                return false;
                }
            }
        return true;
    }

    /**
     * Returns an iterator over the values in this
     * HashMapSet.
     * <p>
     * If you use {@link Iterator#remove()} you must
     * consider to {@link #purge()} HashMapSet.
     * </p>
     * @return an Iterator over the values in
     *         this HashMapSet
     */
    @Override //Iterable
    public Iterator<VALUE> iterator()
    {
        return new CascadingIterator<>(
                super.values().iterator()
                );
    }

    /**
     * Remove key-Set&lt;V&gt; pair for null or
     * Set&lt;V&gt; like {@link Set#size()} {@code <} minSetSize
     *
     * <p>
     * purge(2) : remove all key-Set&lt;V&gt; pair that
     * not contains more than 1 value.
     * </p>
     * @param minSetSize minimum size for Sets to be
     *        keep in HashMapSet
     */
    public void purge( final int minSetSize )
    {
        MapSetHelper.purge( this, minSetSize );
    }

    /**
     * Remove key-Set&lt;V&gt; pair for null or empty
     * Set&lt;V&gt;
     * <p>
     * Invoke {@link #purge(int) purge(1)}
     * </p>
     */
    public void purge()
    {
        purge(1);
    }


    /**
     * Returns an <b>unmodifiable</b> Collection view
     * of V according to HashMapSet.
     *
     * @return an unmodifiable Collection view
     *         of V
     */
    public Collection<VALUE> valuesCollection()
    {
        return new AbstractCollection<VALUE>()
        {
            @Override
            public Iterator<VALUE> iterator()
            {
                return HashMapSet.this.iterator();
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
     * <br>
     * <br>
     * <b>Example of use:</b><br>
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
     *
     * @param iterable iterable object of values, that able to
     *                 compute key for each value.
     * @see ComputeKeyInterface
     */
    public void addAll( final ComputeKeyIterable<KEY,VALUE> iterable )
    {
        final Iterator<VALUE> i = iterable.iterator();

        while(i.hasNext()) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
            final VALUE v = i.next();
            final KEY k = iterable.computeKey( v );
            add(k,v);
            }
    }

    /**
     * Add all key-value from ComputeKeyIterator&lt;K,V&gt; iterator
     *
     * @param iterator iterator used to get values and compute
     *        theirs keys.
     */
    public void addAll( final ComputeKeyIterator<KEY,VALUE> iterator )
    {
        while( iterator.hasNext() ) {
            final VALUE v = iterator.next();
            final KEY k = iterator.computeKey( v );
            add(k,v);
            }
    }

    /**
     * Compute key from value
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyIterable
     */
    @FunctionalInterface
    public interface ComputeKeyInterface<K,V>
    {
        /**
         * Compute key from value.
         *
         * @param value Value
         * @return key for current value.
         */
        K computeKey(V value);
    }

    /**
     * Define an Iterable object able to compute key
     * from values.
     * <p>
     * This is probably the most efficient way to
     * use {@link HashMapSet.ComputeKeyInterface}
     * </p>
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterator
     */
    public interface ComputeKeyIterable<K,V>
        extends Iterable<V>, ComputeKeyInterface<K,V>
    {
        // Just join two interfaces
    }

    /**
     * Define an Iterator object able to compute key
     * from values.
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterable
     */
    public interface ComputeKeyIterator<K,V>
        extends Iterator<V>, ComputeKeyInterface<K,V>
    {
        // Just join two interfaces
    }

    /**
     * Default Iterator object able to compute key
     * from values.
     *
     * @param <K> the type of keys computed from current value
     * @param <V> the type of values
     * @see HashMapSet.ComputeKeyInterface
     * @see HashMapSet.ComputeKeyIterable
     */
    public abstract static class AbstractComputeKeyIterator<K,V>
        implements ComputeKeyIterator<K,V>
    {
        private final Iterator<V> iterator;

        public AbstractComputeKeyIterator(final Iterator<V> iterator)
        {
            this.iterator = iterator;
        }
        public AbstractComputeKeyIterator(final Iterable<V> iterable)
        {
            this.iterator = iterable.iterator();
        }
        @Override
        public boolean hasNext()
        {
            return this.iterator.hasNext();
        }
        @Override
        public V next()
        {
            return this.iterator.next();
        }
        @Override
        public void remove()
        {
            this.iterator.remove();
        }
    }
}
