package cx.ath.choisnet.util;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;
import cx.ath.choisnet.util.iterator.ArrayIterator;

/**
 * Providing tools for Arrays, mainly other views for Arrays
 */
public final class ArrayHelper
{
    private ArrayHelper()
    {//All static
    }

    /**
     * Convert varargs in a Array.
     *
     * @param <T>       Type of array
     * @param entries   List of entry for the array
     * @return the varargs Array (documented as a new
     *         arrays in previous versions)
     * @since 4.1.5
     * @deprecated does not check entries content, and did
     *             not create a shadow copy given values.
     */
    //Java 1.7 @SafeVarargs
    public static <T> T[] createArray( T...entries )
    {
        return entries;
    }

    /**
     * Create an Array from giving values
     *
     * @param <T>       Type of array
     * @param entries   List of entry for the array
     * @return the new Array
     * @since 4.1.6
     */
    //Java 1.7 @SafeVarargs
    public static <T> T[] createArray( Class<T> clazz, T...entries )
    {
        //Workaround for: T[] array = new T[ entries.length ];
        @SuppressWarnings("unchecked")
        final T[] array = (T[])Array.newInstance( clazz, entries.length );

        for( int i = 0; i<entries.length; i++ ) {
            array[ i ] = entries[ i ];
            }

        return array;
    }

    /**
     * Wrap an array to get an {@link Enumeration}
     *
     * @param <T>    array content type
     * @param array  array to wrap
     * @param offset first offset for enumeration
     * @param len    number of item return by this Enumeration
     * @return an {@link Enumeration} for this array
     */
    public static <T> Enumeration<T> toEnumeration(
            final T[] array,
            final int offset,
            int       len
            )
    {
        final int enumLen = offset + len;

        return new Enumeration<T>()
        {
            private int index = offset;
            public boolean hasMoreElements()
            {
                return index < enumLen;
            }

            public T nextElement()
                throws java.util.NoSuchElementException
            {
                if( index < enumLen ) {
                    try {
                        return array[index++];
                    }
                    catch(java.lang.IndexOutOfBoundsException e) {
                    }
                }
                throw new NoSuchElementException((new StringBuilder()).append("index = ").append(index).toString());
            }
        };
    }

    /**
     * Wrap an array to get an {@link Enumeration}
     *
     * @param <T>    array content type
     * @param array  array to wrap
     * @return an {@link Enumeration} for this array
     */
    public static <T> Enumeration<T> toEnumeration(
            T[] array
            )
    {
        if( array == null ) {
            return EnumerationHelper.empty();
            }
        else {
            return toEnumeration(array, 0, array.length);
        }
    }

    /**
     * Create an {@link Iterator} based on array content.
     *
     * @param <T>    array content type
     * @param array  array to wrap
     * @return an {@link Iterator} for this array
     * @see ArrayIterator
     */
    public static <T> Iterator<T> toIterator( T[] array )
    {
        return new ArrayIterator<T>(array);
    }

    /**
     * Create an {@link Iterator} based on array content.
     *
     * @param <T>    array content type
     * @param array  array to wrap
     * @param offset First offset index to include in {@link Iterator}
     * @param len    Length of sub-array to consider
     * @return an {@link Iterator} for this array
     * @see ArrayIterator
     */
    public static <T> Iterator<T> toIterator(T[] array, int offset, int len)
    {
        return new ArrayIterator<T>(array,offset,len);
    }

}
