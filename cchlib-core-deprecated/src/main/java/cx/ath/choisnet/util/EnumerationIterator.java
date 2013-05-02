package cx.ath.choisnet.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
@Deprecated
public class EnumerationIterator<T>
    implements Enumeration<T>,
               Iterator<T>,
               Iterable<T>
{
    private Iterator<T> iterator;

    public EnumerationIterator(
            final Enumeration<T> enumeration
            )
    {
        iterator = new Iterator<T>() {

            @Override
            public boolean hasNext()
            {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() throws NoSuchElementException
            {
                return enumeration.nextElement();
            }

            @Override
            public void remove() throws UnsupportedOperationException
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    public EnumerationIterator(Iterator<T> iterator)
    {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    @Override
    public boolean hasMoreElements()
    {
        return iterator.hasNext();
    }

    @Override
    public T next()
        throws java.util.NoSuchElementException
    {
        return iterator.next();
    }

    @Override
    public T nextElement() throws NoSuchElementException
    {
        return iterator.next();
    }

    @Override
    public void remove() throws UnsupportedOperationException, java.lang.IllegalStateException
    {
        iterator.remove();
    }

    @Override
    public Iterator<T> iterator()
    {
        return iterator;
    }
}
