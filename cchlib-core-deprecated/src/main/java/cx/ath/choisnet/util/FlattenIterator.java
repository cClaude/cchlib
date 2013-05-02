package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.googlecode.cchlib.util.iterator.ArrayIterator;
import com.googlecode.cchlib.util.iterator.ComputableIterator;
//import cx.ath.choisnet.util.iterator.ArrayIterator;
//import cx.ath.choisnet.util.iterator.ComputableIterator;
//import cx.ath.choisnet.util.iterator.SingletonIterator;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
@Deprecated
public class FlattenIterator<T> extends ComputableIterator<T>
{

    private final Iterator<? extends Iterable<T>> metaIterator;
    private Iterator<T> currentIterator;
    //private T nextObject;

    public FlattenIterator(Iterator<? extends Iterable<T>> iterator)
    {
        currentIterator = null;
        //nextObject = null;
        metaIterator = iterator;
    }

    public FlattenIterator(Collection<Iterable<T>> collection)
    {
        this(collection.iterator());
    }

    public FlattenIterator(Iterable<T>[] arrayOfIterable, int offset, int len)
    {
        currentIterator = null;
        //nextObject = null;
        metaIterator = new ArrayIterator<Iterable<T>>(arrayOfIterable, offset, len);
    }

    public FlattenIterator(Iterable<T>[] iterables)
    {
        this(iterables, 0, iterables.length);
    }

    public FlattenIterator(Iterable<T> iterable, T element)
    {
        this(
                (new ArrayCollection<Iterable<T>>())
                    .append(iterable)
                    .append(new SingletonIterator<T>(element))
                    );
    }

    public FlattenIterator(T element, Iterable<T> iterable)
    {
        this(
                (new ArrayCollection<Iterable<T>>())
                    .append(new SingletonIterator<T>(element))
                    .append(iterable)
                    );
    }

    @Override
    public T computeNext()
        throws java.util.NoSuchElementException
    {
        if(currentIterator == null) {
            currentIterator = metaIterator.next().iterator();
            }

        do {
            if(currentIterator.hasNext()) {
                return currentIterator.next();
                }

            if(metaIterator.hasNext()) {
                currentIterator = metaIterator.next().iterator();
                }
            else {
                throw new NoSuchElementException();
            }
        } while(true);
    }
}
