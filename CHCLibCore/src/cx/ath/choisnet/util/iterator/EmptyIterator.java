package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Just a empty Iterator, could be use to deal
 * with limit cases.
 *
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface

 * @author Claude CHOISNET
 * @param <T> 
 */
public class EmptyIterator<T>
    implements Iterator<T>, Iterable<T>, java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    public EmptyIterator()
    {
    }

    public boolean hasNext()
    {
        return false;
    }

    public T next()
    {
        throw new NoSuchElementException();
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
