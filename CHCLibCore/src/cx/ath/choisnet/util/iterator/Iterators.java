/**
 * 
 */
package cx.ath.choisnet.util.iterator;

import java.util.Iterator;

/**
 * This class consists exclusively of static methods
 * that operate on or return iterators.
 * 
 * @author Claude CHOISNET
 */
public class Iterators
{
    private Iterators()
    {//empty
    }

    /**
     * Returns an unmodifiable view of the specified 
     * Iterator. This method allows modules to provide
     * users with "read-only" access to Iterator.
     * Attempts to modify the returned iterator, 
     * result in an UnsupportedOperationException.
     * 
     * @param iterator the iterator for which an unmodifiable
     *        view is to be returned. 
     * @param <T> type of Iterator
     * @return an unmodifiable  view of the specified iterator
     * 
     */
    public final static <T> Iterator<T> unmodifiableIterator(
            final Iterator<T> iterator
            )
    {
        return new Iterator<T>()
        {
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }
            @Override
            public T next()
            {
                return iterator.next();
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    /**
     * Returns an unmodifiable view of the specified 
     * firstElement and Iterator.
     * <p>
     * Results Iterator return 'firstElement' and
     * then all element in Iterator.
     * </p>
     * <p>
     * This method allows modules to provide users 
     * with "read-only" access to Iterator.
     * Attempts to modify the returned iterator, 
     * result in an UnsupportedOperationException.
     * </p>
     * 
     * @param firstElement first element for the new iterator.
     * @param iterator the iterator for which an unmodifiable
     *        view is to be returned. 
     * @param <T> type of Iterator
     * @return an unmodifiable  view of the specified iterator
     * 
     */
    public final static <T> Iterator<T> unmodifiableIterator(
            final T             firstElement,
            final Iterator<T>   iterator
            )
    {
        return new Iterator<T>()
        {
            boolean firstDone = false;
            @Override
            public boolean hasNext()
            {
                if( firstDone ) {
                    return iterator.hasNext();
                }
                return true;
            }
            @Override
            public T next()
            {
                if( firstDone ) {
                    return iterator.next();
                }
                else {
                    firstDone = true;
                    return firstElement;
                }
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
