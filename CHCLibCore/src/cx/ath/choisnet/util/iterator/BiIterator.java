package cx.ath.choisnet.util.iterator;

import java.util.Iterator;

/**
 * Build a new Iterator that consume first
 * Iterator and second Iterator for it's
 * results (Order is preserve).
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
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

    public BiIterator(Iterator<T> firstIter, Iterator<T> secondIter)
    {
        this.firstIter = firstIter;
        this.secondIter = secondIter;
    }

    public boolean hasNext()
    {
        if(firstIter.hasNext()) {
            return true;
        }
        else {
            return secondIter.hasNext();
        }
    }

    public T next()
        throws java.util.NoSuchElementException
    {
        if(firstIter.hasNext()) {
            return firstIter.next();
            }
        else {
            return secondIter.next();
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator()
    {
        return this;
    }
}
