package cx.ath.choisnet.util.iterator;

import java.util.Iterator;

/**
 * TODO: Doc!
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T>
 */
public abstract class ComputableIterator<T>
    implements Iterator<T>, Iterable<T>//, IterableIterator<T>
{
    private T nextObject;

    public ComputableIterator()
    {
        nextObject = null;
    }

    public abstract T computeNext()
        throws java.util.NoSuchElementException;

    public boolean hasNext()
    {
        if(nextObject == null) {
            try {
                nextObject = computeNext();
            }
            catch(java.util.NoSuchElementException e) {
                return false;
            }
        }

        return true;
    }

    public T next()
        throws java.util.NoSuchElementException
    {
        if(nextObject == null) {
            nextObject = computeNext();
        }

        T returnObject = nextObject;

        try {
            nextObject = computeNext();
        }
        catch(java.util.NoSuchElementException e) {
            nextObject = null;
        }

        return returnObject;
    }

    public void remove()
        throws java.lang.UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator()
    {
        return this;
    }
}
