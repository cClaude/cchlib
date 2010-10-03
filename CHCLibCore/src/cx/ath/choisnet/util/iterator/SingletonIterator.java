package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T>
 */
public class SingletonIterator<T>
    implements Iterator<T>, Iterable<T>, java.io.Serializable
{

    private static final long serialVersionUID = 1L;
    /** @serial */
    private boolean hasNext;
    /** @serial */
    private T item;

    public SingletonIterator(T item)
    {
        this.item = item;

        hasNext = true;
    }

    public boolean hasNext()
    {
        return hasNext;
    }

    public T next()
        throws java.util.NoSuchElementException
    {
        if(hasNext) {
            hasNext = false;

            return item;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public void remove()
        throws java.lang.UnsupportedOperationException, java.lang.IllegalStateException
    {
        if(hasNext) {
            hasNext = false;
        }
        else {
            throw new IllegalStateException();
        }
    }

    public Iterator<T> iterator()
    {
        return this;
    }
}
