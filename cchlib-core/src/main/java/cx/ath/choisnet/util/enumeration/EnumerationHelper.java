package cx.ath.choisnet.util.enumeration;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cx.ath.choisnet.util.ArrayHelper;
import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.iterator.EnumerationIterator;
import cx.ath.choisnet.util.iterator.IteratorHelper;

/**
 * Providing other view for Enumerations
 *
 * @see IteratorHelper
 * @see ArrayHelper#toEnumeration(Object[])
 * @see ArrayHelper#toEnumeration(Object[], int, int)
 */
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
/*
        return new Enumeration<T>()
        {
            public boolean hasMoreElements()
            {
                return false;
            }
            public T nextElement()
                throws java.util.NoSuchElementException
            {
                throw new NoSuchElementException();
            }
        };
*/
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
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            public O nextElement()
                throws NoSuchElementException
            {
                T element = enumeration.nextElement();

                return element.wrappe( element );
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
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            public O nextElement()
                throws java.util.NoSuchElementException
            {
                return wrapper.wrappe( enumeration.nextElement() );
            }
        };
    }

//    /**
//     * @ Deprecated use wrapper !
//     * @param <T>
//     * @param enumeration
//     * @return
//     */
//    public static <T> Enumeration<String> toEnumerationString(
//            final Enumeration<T> enumeration
//            )
//    {
//        return new Enumeration<String>()
//        {
//            public boolean hasMoreElements()
//            {
//                return enumeration.hasMoreElements();
//            }
//
//            public String nextElement()
//                throws java.util.NoSuchElementException
//            {
//                return enumeration.nextElement().toString();
//            }
//        };
//    }

//    /**
//     * @ Deprecated use wrapper !
//     * @param <T>
//     * @param <O>
//     * @param enumeration
//     * @param clazz
//     * @return
//     */
//    public static <T,O> Enumeration<O> toEnumeration(
//            final Enumeration<T> enumeration,
//            final Class<O>       clazz
//            )
//    {
//        if(enumeration == null) {
//            return EnumerationHelper.toEnumeration();
//            }
//        else {
//            return new Enumeration<O>()
//            {
//                public boolean hasMoreElements()
//                {
//                    return enumeration.hasMoreElements();
//                }
//
//                public O nextElement()
//                    throws java.util.NoSuchElementException
//                {
//                    return clazz.cast( enumeration.nextElement() );
//                }
//            };
//        }
//    }

    /**
     * Wrap an enumeration to respond has an Iterator
     * @param enumeration enumeration to wrap
     * @param <T> type off enumeration and off iterator
     * @return an Iterator
     * @deprecated use EnumerationIterator
     * @see EnumerationIterator
     */
    public static <T> Iterator<T> toIterator(
            final Enumeration<T> enumeration
            )
    {
        return new EnumerationIterator<T>(enumeration);
/*
        return new Iterator<T>()
        {
            @Override
            public boolean hasNext()
            {
                return enumeration.hasMoreElements();
            }
            @Override
            public T next()
            {
                return enumeration.nextElement();
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
*/
    }
}
