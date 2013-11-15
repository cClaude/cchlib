package com.googlecode.cchlib.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * MapCollection provide an easy and efficient way
 * to store keys-values when you need to have
 * more than one value for an unique key.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 03a.34
 */
public interface MapCollection<K,V>
    extends Serializable, Iterable<V>, Cloneable
{
    /**
     * Add an couple of key value in MapCollection
     * <p>
     * If key already exist in MapCollection, add value to corresponding Collection.
     * If key does not exist create an new Collection initialized with this value,
     * and associates this Collection with the specified key in this MapCollection.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return true if value has been added (could depend of implementation)
     */
    boolean add(K key, V value);

    /**
     * Returns the Collection to which the specified key is mapped, or null
     * if this MapCollection contains no mapping for the key.
     *
     * @param key the key whose associated Collection is to be returned
     * @return the value to which the specified key is mapped, or null
     *         if this MapCollection contains no mapping for the key
     */
    Collection<V> getCollection(K key);

    /**
     * Add all values with same key in this MapCollection.
     *
     * @param key       key to use for all values
     * @param values    values to add
     * @return number of values add in MapCollection
     */
    int addAll(K key, Collection<V> values);

    /**
     * Add all couples of key value in MapCollection.
     *
     * @param m map to add
     * @return number of values added
     */
    int addAll(Map<K,V> m);

    /**
     * Returns true if MapCollection contains the specified element.
     * More formally, returns true if and only if at least one Collection
     * contains at least one element e such that
     * (value==null ? e==null : o.equals(e)).
     *
     * @param value element whose presence in MapCollection is to be tested
     * @return true if MapCollection contains the specified element.
     */
    boolean containsValue(V value);

    /**
     * Returns true if MapCollection contains all of the elements
     * in the specified collection.
     *
     * @param c collection to be checked for containment in MapCollection
     * @return true if MapCollection contains all of the elements
     *  in the specified collection
     */
    boolean containsAll(Collection<? extends V> c);

    /**
     * Returns true if MapCollection contains the specified key.
     * @param key key whose presence in MapCollection is to be tested
     * @return true if MapCollection contains the specified key.
     */
    boolean containsKey( K key );

    /**
     * Removes all of the mappings from this MapCollection.
     * The MapCollection will be empty after this call returns.
     */
    void clear();

    /**
     * Removes all of the mappings from this MapCollection,
     * but also perform a Collection.clear() on each set of values.
     * The MapCollection will be empty after this call returns.
     */
    void deepClear();

    /**
     * Returns an iterator over the values in this MapCollection.
     * @return an iterator over the values in this MapCollection.
     */
    @Override
    Iterator<V> iterator();

//    void   purge()
//             Remove key-Set<V> pair for null or empty Set<V> Invoke purge(1)
//    void   purge(int minSetSize)
//             Remove key-Set<V> pair for null or Set<V> like Set.size() < minSetSize purge(2) : remove all key-Set<V> pair that not contains more than 1 value.

    /**
     * Removes the specified key-value from this MapCollection if it is present.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return true if the specified key-value could be removed from MapCollection
     */
    boolean remove(K key, V value);

    /**
     * Removes the specified key from this MapCollection if it is present,
     * all values associate with this key will be removed.
     *
     * @param key   key with which the specified value is to be associated
     * @return previous Collection of values for this key, or null if
     * key could not be found in MapCollection
     */
    Collection<V> remove(K key);

    /**
     * Returns a Collection view of V according to MapCollection.
     * @return a Collection view of V according to MapCollection.
     */
    Collection<V> valuesCollection();

    /**
     * Returns the number of key in this MapCollection.
     * @return the number of key in this MapCollection.
     */
    int keySize();

    /**
     * Returns the number of value in this MapCollection.
     * @return the number of value in this MapCollection.
     */
    int valuesSize();

    /**
     * Returns true if this {@link MapCollection} is empty
     * @return true if this {@link MapCollection} is empty
     */
    boolean isEmpty();

    /**
     * Returns {@link Set} for this {@link MapCollection}
     * @return {@link Set} for this {@link MapCollection}
     */
    Set<Map.Entry<K,Collection<V>>> entrySet();
    //,*/ get, isEmpty, keySet, put, putAll, remove, size, values


    /**
     * Compares the specified object with this MapCollection for equality.
     * <p>
     * Returns true if the given object is also a map and the two maps
     * represent the same mappings. More formally, two maps m1 and m2 represent
     * the same mappings if m1.entrySet().equals(m2.entrySet()). This ensures
     * that the equals method works properly across different implementations
     * of the Map interface.
     * </p>
     * <p>
     * This implementation first checks if the specified object is this map;
     * if so it returns true. Then, it checks if the specified object is a
     * map whose size is identical to the size of this map; if not, it
     * returns false. If so, it iterates over this map's entrySet collection,
     * and checks that the specified map contains each mapping that this map
     * contains. If the specified map fails to contain such a mapping, false
     * is returned. If the iteration completes, true is returned.
     *
     * @param o object to be compared for equality with this MapCollection
     * @return true if the specified object is equal to this MapCollection
     */
    @Override
    boolean equals( Object o );

    /**
     * Returns a shallow copy of this AbstractMapCollection instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this AbstractMapCollection
     * @see Cloneable
     */
    Object clone();

    //hashCode,
    //toString
}
