package cx.ath.choisnet.util.enumeration;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.EnumerationIterator;
import cx.ath.choisnet.util.ArrayHelper;

/**
 * Providing other view for Enumerations
 *
 * @see cx.ath.choisnet.util.iterator.IteratorHelper
 * @see ArrayHelper#toEnumeration(Object[])
 * @see ArrayHelper#toEnumeration(Object[], int, int)
 * @deprecated use {@link com.googlecode.cchlib.util.enumeration.Enumerations} instead
 */
@Deprecated
public class EnumerationHelper
{
    private EnumerationHelper()
    { // All static
    }

    /**
     * Create an empty Enumeration
     *
     * @param <T> type content
     * @return an empty Enumeration
     */
    public static <T> Enumeration<T> empty()
    {
        return new EmptyEnumeration<T>();
    }


    /**
     * Create an Enumeration using (and consuming) an other Enumeration,
     * and changing type.
     * @param <T> source type content
     * @param <O> result type
     * @param enumeration
     * @return an Enumeration view for this enumeration
     */
    public static <T extends Wrappable<T,O>,O> Enumeration<O> toEnumeration(
            final Enumeration<T> enumeration
            )
    {
        return new Enumeration<O>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            @Override
            public O nextElement()
                throws NoSuchElementException
            {
                T element = enumeration.nextElement();

                return element.wrap( element );
            }
        };
    }

    /**
     * Create an Enumeration using (and consuming) an other Enumeration,
     * and changing type using wrapper.
     * @param <T> source type content
     * @param <O> result type
     * @param enumeration
     * @param wrapper
     * @return an Enumeration view for this enumeration
     */
    public static <T,O> Enumeration<O> toEnumeration(
            final Enumeration<T> enumeration,
            final Wrappable<T,O> wrapper
            )
    {
        return new Enumeration<O>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            @Override
            public O nextElement()
                throws java.util.NoSuchElementException
            {
                return wrapper.wrap( enumeration.nextElement() );
            }
        };
    }

    /**
     * Wrap an enumeration to respond has an Iterator
     * @param enumeration enumeration to wrap
     * @param <T> type off enumeration and off iterator
     * @return an Iterator
     * @deprecated use EnumerationIterator
     * @see EnumerationIterator
     */
    @Deprecated
    public static <T> Iterator<T> toIterator(
            final Enumeration<T> enumeration
            )
    {
        return new EnumerationIterator<T>(enumeration);
    }
}
