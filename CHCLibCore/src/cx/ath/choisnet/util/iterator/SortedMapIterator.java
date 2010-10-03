package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;

/**
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <K> 
 * @param <V> 
 */
public class SortedMapIterator<K,V>
    implements Iterator<V>, Iterable<V>
{
    private SortedMap<K,V> initialSortedMap;
    private Iterator<K> iter;
    private K prevKey;

    public SortedMapIterator(
            final SortedMap<K,V> sortedMap
            )
    {
        initialSortedMap = sortedMap;
        prevKey = null;
        iter = new ComputableIterator<K>() {

            private SortedMap<K,V> currentSortedMap = sortedMap;

            public K computeNext()
                throws java.util.NoSuchElementException
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

    public boolean hasNext()
    {
        return iter.hasNext();
    }

    public V next()
        throws java.util.NoSuchElementException
    {
        try {
            prevKey = iter.next();

            return initialSortedMap.get(prevKey);
        }
        catch(java.util.NoSuchElementException e) {
            prevKey = null;
            throw e;
        }
    }

    public K getLastKey()
        throws java.util.NoSuchElementException
    {
        if(prevKey == null) {
            throw new NoSuchElementException();
        }
        else {
            return prevKey;
        }
    }

    public void remove()
        throws java.lang.IllegalStateException
    {
        if(prevKey == null) {
            throw new IllegalStateException();
        } 
        else {
            initialSortedMap.remove(prevKey);
        }
    }

    public Iterator<V> iterator()
    {
        return this;
    }
}
