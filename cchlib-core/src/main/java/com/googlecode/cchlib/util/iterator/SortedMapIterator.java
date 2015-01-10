package com.googlecode.cchlib.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/**
 * TODOC
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 4.1.7
 */
public class SortedMapIterator<K,V>
    implements Iterator<V>
{
    private SortedMap<K,V>      initialSortedMap;
    private final Iterator<K>   iter;
    private K                   prevKey;

    /**
     * Create a SortedMapIterator
     *
     * @param sortedMap {@link SortedMap} to use
     */
    public SortedMapIterator(
        final SortedMap<K,V> sortedMap
        )
    {
        this.initialSortedMap = sortedMap;
        this.prevKey = null;
        this.iter = new ComputableIterator<K>() {
                private SortedMap<K,V> currentSortedMap = sortedMap;

                @Override
                public K computeNext() throws NoSuchElementException
                {
                    final K key = this.currentSortedMap.lastKey();

                    if(key == null) {
                        throw new NoSuchElementException();
                        }
                    else {
                        this.currentSortedMap = this.currentSortedMap.headMap(key);
                        return key;
                        }
                }
            };
    }

    /**
     * Returns true if the iteration has more elements.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.)
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        return this.iter.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public V next() throws NoSuchElementException
    {
        try {
            this.prevKey = this.iter.next();

            return this.initialSortedMap.get(this.prevKey);
            }
        catch( final NoSuchElementException e ) {
            this.prevKey = null;
            throw e;
            }
    }

    /**
     * Return last key
     * @return last key
     * @throws NoSuchElementException
     */
    public K getLastKey() throws NoSuchElementException
    {
        if(this.prevKey == null) {
            throw new NoSuchElementException();
            }
        else {
            return this.prevKey;
            }
    }

    /**
     * Removes from the underlying Map the last element
     * returned by the iterator.
     *
     * @throws UnsupportedOperationException if the remove
     *         operation is not supported by current Iterator.
     * @throws IllegalStateException if the next method has
     *         not yet been called, or the remove method has
     *         already been called after the last call to the
     *         next method.
     */
    @Override
    public void remove() throws IllegalStateException
    {
        if(this.prevKey == null) {
            throw new IllegalStateException();
            }
        else {
            this.initialSortedMap.remove(this.prevKey);
            }
    }
}
