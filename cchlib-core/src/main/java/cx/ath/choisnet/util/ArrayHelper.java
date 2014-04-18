package cx.ath.choisnet.util;

import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.util.enumeration.EmptyEnumeration;
import com.googlecode.cchlib.util.iterator.ArrayIterator;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Providing tools for Arrays, mainly other views for Arrays
 */
public final class ArrayHelper
{
    private ArrayHelper()
    {//All static
    }

//    /**
//     * Convert varargs in a Array.
//     *
//     * @param <T>       Type of array
//     * @param entries   List of entry for the array
//     * @return the varargs Array (documented as a new
//     *         arrays in previous versions)
//     * @since 4.1.5
//     * @deprecated does not check entries content, and did
//     *             not create a shadow copy given values.
//     */
//    //Java 1.7 @SafeVarargs
//    @Deprecated
//    public static <T> T[] createArray( T...entries )
//    {
//        return entries;
//    }

    /**
     * Create an Array from giving values
     *
     * @param <T>       Type of array
     * @param entries   List of entry for the array
     * @return the new Array
     * @since 4.1.6
     */
    @SafeVarargs
    @NeedTestCases
    public static <T> T[] createArray( final Class<T> clazz, final T...entries )
    {
        //Workaround for: T[] array = new T[ entries.length ];
        return cloneArray( clazz, entries );
    }

    /**
     *
     * @param clazz
     * @param src     the source array
     * @param srcPos  starting position in the source array.
     * @param destPos starting position in the destination data.
     * @param length  the number of array elements to be copied.
     * @return a new array
     */
    @NeedTestCases
    public static <T> T[] cloneArray(
        final Class<T> clazz,
        final T[]      src,
        final int      srcPos,
        final int      destPos,
        final int      length
        )
    {
        @SuppressWarnings("unchecked")
        final T[] dest = (T[])Array.newInstance( clazz, length );

        System.arraycopy( src, srcPos, dest, destPos, length );

        return dest;
    }

    /**
    *
    * @param clazz
    * @param src     the source array
    * @return a new array
    */
    @NeedTestCases
    public static <T> T[] cloneArray(
        final Class<T> clazz,
        final T[]      src
        )
    {
        return cloneArray( clazz, src, 0, 0, src.length );
    }

    /**
     * Clone an array of bytes
     *
     * @param src
     * @param srcPos
     * @param destPos
     * @param length
     * @return TODOC
     */
    @NeedTestCases
    public static byte[] cloneArray( final byte[] src, final int srcPos, final int destPos, final int length )
    {
        final byte[] dest = new byte[ length ];

        System.arraycopy( src, srcPos, dest, destPos, length );

        return dest;
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
            final int       len
            )
    {
        final int enumLen = offset + len;

        return new Enumeration<T>()
        {
            private int index = offset;
            @Override
            public boolean hasMoreElements()
            {
                return index < enumLen;
            }

            @Override
            public T nextElement()
                throws java.util.NoSuchElementException
            {
                if( index < enumLen ) {
                    //try {
                        return array[index++];
                    //    }
                    //catch(IndexOutOfBoundsException ignore) {}
                }
                throw new NoSuchElementException( "index = " + index );
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
            final T[] array
            )
    {
        if( array == null ) {
            return new EmptyEnumeration<>();
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
    public static <T> Iterator<T> toIterator( final T[] array )
    {
        return new ArrayIterator<>(array);
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
    public static <T> Iterator<T> toIterator(final T[] array, final int offset, final int len)
    {
        return new ArrayIterator<>(array,offset,len);
    }

}
