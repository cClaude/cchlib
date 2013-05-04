package cx.ath.choisnet.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Create an Iterator using (and consuming) an Enumeration
 *
 * @param <T> type content
 * @deprecated use {@link com.googlecode.cchlib.util.iterator.EnumerationIterator} instead
 */
@Deprecated
public class EnumerationIterator<T>
    implements Iterator<T>
{
    private Enumeration<T> enumeration;

    /**
     * Create an Iterator using (and consuming) an Enumeration
     *
     * @param enumeration Enumeration to use
     */
    public EnumerationIterator(
            Enumeration<T> enumeration
            )
    {
        this.enumeration = enumeration;
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
        return enumeration.hasMoreElements();
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public T next()
    {
        return enumeration.nextElement();
    }

    /**
     * Unsupported Operation
     *
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
