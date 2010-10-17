package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Build an Iterator based on an {@link Iterator} of {@link Iterable}.
 * This new Iterator that consume all sub-Iterator in
 * order of main Iterator for it's
 * results (Order is preserve).
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable} interface

 * @author Claude CHOISNET
 * @param <T>
 * @see MultiIterator
 */
public class CascadingIterator<T> extends ComputableIterator<T>
{
    private Iterator<? extends Iterable<? extends T>> mainIterator;
    private Iterator<? extends T> currentIterator;

    /**
     * Build an Iterator based on an Iterator of Iterator
     * 
     * @param iterator an Iterator of Iterator
     */
    public CascadingIterator(Iterator<? extends Iterable<? extends T>> iterator)
    {
        mainIterator    = iterator;
        currentIterator = null;
    }

    @Override
    protected T computeNext() throws NoSuchElementException
    {
        do {
            if(currentIterator != null) {
                try {
                    return currentIterator.next();
                }
                catch(NoSuchElementException ignore) {
                }
            }

            currentIterator = mainIterator.next().iterator();
        } while(true);
    }
    
    /**
     * Removes from the underlying collection the last element
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
    public void remove()
    {
        if(currentIterator != null) {
            currentIterator.remove();
        }
        else {
            throw new IllegalStateException();
        }
    }
}
