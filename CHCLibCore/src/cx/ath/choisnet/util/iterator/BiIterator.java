package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Build a new Iterator that consume first
 * Iterator and second Iterator for it's
 * results (Order is preserve).
 * <BR/>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T> 
 *
 */
public class BiIterator<T>
    implements Iterator<T>, Iterable<T>
{
    private Iterator<T> firstIter;
    private Iterator<T> secondIter;

    /**
     * Build a new Iterator that consume first
     * Iterator and second Iterator for it's
     * results (Order is preserve).
     * 
     * @param firstIter
     * @param secondIter
     */
    public BiIterator(
            Iterator<T> firstIter,
            Iterator<T> secondIter
            )
    {
        this.firstIter = firstIter;
        this.secondIter = secondIter;
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
        if(firstIter.hasNext()) {
            return true;
        }
        else {
            return secondIter.hasNext();
        }
    }

    /** 
     * Returns the next element in the iteration. 
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public T next() throws NoSuchElementException
    {
        if(firstIter.hasNext()) {
            return firstIter.next();
            }
        else {
            return secondIter.next();
        }
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
        if(firstIter.hasNext()) {
            firstIter.remove();
            }
        else {
            secondIter.remove();
        }
    }

    /**
     * Returns an iterator over a set of elements of type T. 
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
}
