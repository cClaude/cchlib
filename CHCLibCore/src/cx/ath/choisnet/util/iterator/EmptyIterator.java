package cx.ath.choisnet.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cx.ath.choisnet.util.iterator.iterable.IterableIterator;

/**
 * Just a empty Iterator, could be use to deal
 * with limit cases.
 *
 * <BR/>
 * Note: This Iterator extends also {@link Iterable} interface

 * @author Claude CHOISNET
 * @param <T> 
 */
public class EmptyIterator<T>
    implements Iterator<T>, Iterable<T>, IterableIterator<T>, java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an empty Iterator
     */
    public EmptyIterator()
    {
    }

    /**
     * Always return false.
     * 
     * @return false
     */
    @Override
    public boolean hasNext()
    {
        return false;
    }

    /**
     * Always generate NoSuchElementException according
     * to Iterator specifications.
     * 
     * @throws NoSuchElementException
     */
    @Override
    public T next()
    {
        throw new NoSuchElementException();
    }

    /**
     * Always generate IllegalStateException according
     * to Iterator specifications.
     * 
     * @throws IllegalStateException
     */
    @Override
    public void remove()
    {
        throw new IllegalStateException();
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

//    @Override
//    public int size()
//    {
//        return 0;
//    }
}
