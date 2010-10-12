package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/**
 * TODO: Doc!
 * <BR/>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <K> Type of key
 * @param <V> Type of value
 */
public class SortedMapIterator<K,V>
    implements Iterator<V>, Iterable<V>
{
    private SortedMap<K,V>  initialSortedMap;
    private Iterator<K>     iter;
    private K               prevKey;

    /**
     * TODO: Doc!
     * 
     * @param sortedMap
     */
    public SortedMapIterator(
            final SortedMap<K,V> sortedMap
            )
    {
        initialSortedMap = sortedMap;
        prevKey = null;
        iter = new ComputableIterator<K>() 
            {
                private SortedMap<K,V> currentSortedMap = sortedMap;

                public K computeNext() throws NoSuchElementException
                {
                    K key = currentSortedMap.lastKey();
                
                    if(key == null) {
                        throw new NoSuchElementException();
                    } 
                    else {
                        currentSortedMap = currentSortedMap.headMap(key);
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
    public boolean hasNext()
    {
        return iter.hasNext();
    }

    /** 
     * Returns the next element in the iteration. 
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    public V next() throws NoSuchElementException
    {
        try {
            prevKey = iter.next();

            return initialSortedMap.get(prevKey);
        }
        catch(NoSuchElementException e) {
            prevKey = null;
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
        if(prevKey == null) {
            throw new NoSuchElementException();
        }
        else {
            return prevKey;
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
    public void remove() throws IllegalStateException
    {
        if(prevKey == null) {
            throw new IllegalStateException();
        } 
        else {
            initialSortedMap.remove(prevKey);
        }
    }

    /**
     * Returns an iterator over a set of elements of type T. 
     * @return this Iterator
     */
    public Iterator<V> iterator()
    {
        return this;
    }
}
