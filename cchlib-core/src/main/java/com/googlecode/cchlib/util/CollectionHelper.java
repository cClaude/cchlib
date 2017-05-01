package com.googlecode.cchlib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedTestCases;

/**
 * Give some missing feature in {@link Collections}
 *
 * @since 4.2
 */
public final class CollectionHelper
{
    private CollectionHelper() {}

    /**
     * Create an unmodifiable {@link List} from a {@link Collection}
     *
     * @param <T>
     *            the class of the objects in the list
     * @param collection
     *            the collection whose elements are to be placed into this list
     * @return an unmodifiable List
     * @since 4.2
     */
    @NeedTestCases
    @Nonnull
    public static <T> List<T> unmodifiableList(
        @Nullable final Collection<T> collection
        )
    {
        if( collection == null ) {
            return Collections.emptyList();
            }
        else {
            final List<T> list;

            if( collection instanceof List ) {
                list = (List<T>)collection;
                }
            else {
                list = new ArrayList<>( collection );
                }

            return Collections.unmodifiableList( list );
        }
     }

    /**
     * Convert result of an {@link Enumeration} to an unmodifiable {@link List}
     *
     * @param <T>
     *            the class of the objects in the list
     * @param enumeration
     *            the enumeration whose elements are to be placed into this list
     * @return an unmodifiable List
     * @since 4.2
     */
    @Nonnull
    public static <T> List<T> unmodifiableList(
        @Nullable final Enumeration<T> enumeration
        )
    {
        return Collections.unmodifiableList( newList( enumeration ) );
    }

    /**
     * Returns an unmodifiable view of the specified {@code mapSet}. This
     *  method allows modules to provide users with "read-only" access to
     * {@link Map}, but also to the value {@link Set}.
     *
     * @param <K>
     *            the class of the map keys
     * @param <V>
     *            the class of the set values
     * @param mapSet
     *            the {@link Map} of {@link Set} for which an unmodifiable view
     *            is to be returned
     * @return an unmodifiable view of the specified map.
     *
     * @see Collections#unmodifiableMap(Map)
     * @see Collections#unmodifiableSet(Set)
     * @since 4.2
     */
    @Nonnull
    public static <K,V> Map<K, Set<V>> unmodifiableMapSet(
        @Nullable final Map<K, Set<V>> mapSet
        )
    {
        if( mapSet == null ) {
            return Collections.emptyMap();
        }

        for( final Map.Entry<K, Set<V>> entry : mapSet.entrySet() ) {
            final K      key   = entry.getKey();
            final Set<V> value = entry.getValue();

            mapSet.replace( key, Collections.unmodifiableSet( value ) );
        }

        return Collections.unmodifiableMap( mapSet );
    }

    /**
     * Create a {@link List} from an {@link Enumeration}
     *
     * @param <T>
     *            the class of the objects in the list
     * @param enumeration
     *            the enumeration whose elements are to be placed into this list
     * @return a List
     * @since 4.2
     */
    @Nonnull
    public static <T> List<T> newList( @Nullable final Enumeration<T> enumeration )
    {
        final List<T> list = new ArrayList<>();

        if( enumeration != null ) {
            while( enumeration.hasMoreElements() ) {
                list.add( enumeration.nextElement() );
            }
        }

        return list;
     }

    /**
     * Create a {@link List} from an {@link Iterator}
     *
     * @param <T>
     *            the class of the objects in the list
     * @param iterator
     *            the iterator whose elements are to be placed into this list
     * @return a List
     * @since 4.2
     */
    public static <T> List<T> newList( @Nullable final Iterator<T> iterator )
    {
        final List<T> list = new ArrayList<>();

        if( iterator != null ) {
            while( iterator.hasNext() ) {
                list.add( iterator.next() );
            }
        }

        return list;
    }

    /**
     * Returns an non null value based on the giving {@link List}
     *
     * @param list
     *            value to evaluate
     * @return the original value or an empty {@link List}
     * @since 4.2
     */
    public static <T> List<T> nonNullList( @Nullable final List<T> list )
    {
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * Returns an non null value based on the giving {@link Collection}
     *
     * @param collection
     *            value to evaluate
     * @return the original value or an empty {@link Collection}
     * @since 4.2
     */
    public static <T> Collection<T> nonNullCollection( @Nullable final Collection<T> collection )
    {
        return collection == null ? Collections.emptyList() : collection;
    }

    /**
     * Returns an non null value based on the giving {@link Set}
     *
     * @param set
     *            value to evaluate
     * @return the original value or an empty {@link Set}
     * @since 4.2
     */
    public static <T> Set<T> nonNullSet( @Nullable final Set<T> set )
    {
        return set == null ? Collections.emptySet() : set;
    }

    /**
     * Returns an non null value based on the giving {@link Map}
     *
     * @param map
     *            value to evaluate
     * @return the original value or an empty {@link Map}
     * @since 4.2
     */
    public static <K,V> Map<K,V> nonNullMap( @Nullable final Map<K,V> map )
    {
        return map == null ? Collections.emptyMap() : map;
    }

    /**
     * Test if a {@link Collection} is empty
     *
     * @param <T>
     *            the class of the objects in the list
     * @param collection
     *            Collection to test
     * @return true if collection is empty or null
     * @since 4.2
     */
    public static <T> boolean isEmpty( @Nullable final Collection<T> collection )
    {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * Returns a Map&lt;V,Set&lt;K&gt;&gt;
     * <p>
     * {@link Supplier} are based on {@link HashMap} and {@link HashSet}
     * <p>
     * Original map is not modify by this operation.
     *
     * @param <K>
     *            The type of the key of the initial map, the type of the value
     *            in result {@link Map}{@link Set}
     * @param <V>
     *            The type of the value of the initial map, the type of the key
     *            in result {@link Map}{@link Set}
     * @param map
     *            The map
     * @return a {@link Map} build using {@link HashMap} {@link HashSet}
     *
     * @see #reverseMap(Map, Supplier, Supplier)
     */
    public static <K,V> Map<V,Set<K>> reverseMap( final Map<K,V> map )
    {
        final Supplier<Map<V, Set<K>>> mapSupplier = HashMap::new;
        final Supplier<Set<K>>         setSupplier = HashSet::new;

        return reverseMap( map, mapSupplier, setSupplier );
    }


    /**
     * Returns a {@link Map}&lt;V,{@link Set}&lt;K&gt;&gt; build by exchange
     * key and value of original {@code map}. All couple (K,V) are kept by
     * this operation.
     * <p>
     * Original map is not modify by this operation.
     *
     * @param <K>
     *            The type of the key of the initial map, the type of the value
     *            in result {@link Map}{@link Set}
     * @param <V>
     *            The type of the value of the initial map, the type of the key
     *            in result {@link Map}{@link Set}
     * @param map
     *            The map
     * @param mapSupplier
     *            The {@link Map} {@link Supplier}
     * @param setSupplier
     *            The {@link Set} {@link Supplier}
     * @return a {@link Map} build using {@link HashMap} {@link Set}
     */
    public static <K,V> Map<V,Set<K>> reverseMap(
        @Nonnull final Map<K,V>                map,
        @Nonnull final Supplier<Map<V,Set<K>>> mapSupplier,
        @Nonnull final Supplier<Set<K>>        setSupplier
        )
    {
        final Map<V,Set<K>> reverseMap = mapSupplier.get();

        for( final Map.Entry<K, V> entry : map.entrySet() ) {
            final K      oldKey   = entry.getKey();
            final V      oldValue = entry.getValue();
            final Set<K> set;

            if( reverseMap.containsKey( oldValue ) ) {
                set = reverseMap.get( oldValue );
            } else {
                set = setSupplier.get();

                reverseMap.put( oldValue, set );
            }
            set.add( oldKey );
        }

        return reverseMap;
    }
}
