package cx.ath.choisnet.util.iterator;

import java.util.Collection;
import java.util.Iterator;
import cx.ath.choisnet.util.Wrappable;

/**
 *
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T>
 * @param <O>
 */
public class IteratorWrapper<T,O>
    implements Iterator<O>, Iterable<O>//,IterableIterator<O>
{
    private Iterator<T>    iterator;
    private Wrappable<T,O> wrapper;

    public IteratorWrapper(
            Iterator<T>     iterator,
            Wrappable<T,O>  wrapper
            )
    {
        this.iterator = iterator;
        this.wrapper  = wrapper;
    }

    public IteratorWrapper(
            Collection<T>   c, 
            Wrappable<T,O>  wrapper
            )
    {
        this(c.iterator(), wrapper);
    }

    @Override
    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    @Override
    public O next()
        throws java.util.NoSuchElementException
    {
        return wrapper.wrappe(iterator.next());
    }

    @Override
    public void remove()
        throws java.lang.UnsupportedOperationException
    {
        iterator.remove();
    }

    @Override
    public Iterator<O> iterator()
    {
        return this;
    }
}
