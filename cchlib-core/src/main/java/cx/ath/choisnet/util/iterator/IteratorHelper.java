package cx.ath.choisnet.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;
import cx.ath.choisnet.util.ArrayHelper;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;

/**
 * Providing other view for Iterators
 *
 * @see com.googlecode.cchlib.util.iterator.ArrayIterator
 * @see EnumerationHelper
 * @see ArrayHelper#toIterator(Object[])
 * @see ArrayHelper#toIterator(Object[], int, int)
 * @see com.googlecode.cchlib.io.IOHelper#toIterator(java.io.File[], java.io.FileFilter)
 */
public class IteratorHelper
{
    private IteratorHelper()
    {//All static
    }

    /**
     * Create an Enumeration using (and consuming) an Iterator
     *
     * @param <T> type content
     * @param iterator
     * @return an Enumeration view for this iterator
     */
    public static <T> Enumeration<T> toEnumeration(
            final Iterator<T> iterator
            )
    {
        return new Enumeration<T>()
        {
            public boolean hasMoreElements()
            {
                return iterator.hasNext();
            }
            public T nextElement()
                throws java.util.NoSuchElementException
            {
                return iterator.next();
            }
        };
    }
}
