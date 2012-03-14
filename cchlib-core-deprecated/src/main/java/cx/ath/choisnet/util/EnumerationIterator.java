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

            public boolean hasNext()
            {
                return enumeration.hasMoreElements();
            }

            public T next() throws NoSuchElementException
            {
                return enumeration.nextElement();
            }

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

    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    public boolean hasMoreElements()
    {
        return iterator.hasNext();
    }

    public T next()
        throws java.util.NoSuchElementException
    {
        return iterator.next();
    }

    public T nextElement() throws NoSuchElementException
    {
        return iterator.next();
    }

    public void remove() throws UnsupportedOperationException, java.lang.IllegalStateException
    {
        iterator.remove();
    }

    public Iterator<T> iterator()
    {
        return iterator;
    }
}
