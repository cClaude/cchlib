package cx.ath.choisnet.util;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.util.enumeration.EmptyEnumeration;
import com.googlecode.cchlib.util.iterator.ArrayIterator;


/**
 * Providing tools for Arrays, mainly other views for Arrays
 */
public final class ArrayHelper
{
    private ArrayHelper()
    {//All static
    }

    /**
     * Create an Array from giving values
     *
     * @param <T>       Type of array
     * @param clazz     Class of the array
     * @param entries   List of entry for the array
     * @return the new Array
     * @since 4.1.6
     */
    @SafeVarargs
    @NeedTestCases
    public static <T> T[] createArray( final Class<T> clazz, final T...entries )
    {
        // Workaround for: " T[] array = new T[ entries.length ]; "
        return cloneArray( clazz, entries );
    }

    /**
     * Clone an array (did not clone in depth, only array content)
     *
     * @param <T>     Type of array
     * @param clazz   Class of the array
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
     * Clone an array (did not clone in depth, only array content)
     *
     * @param <T>     Type of array
     * @param clazz   Class of the array
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
     * @param src     the source array
     * @param srcPos  starting position in the source array.
     * @param destPos starting position in the destination data.
     * @param length  the number of array elements to be copied.
     * @return a new array
     */
    @NeedTestCases
    public static byte[] cloneArray(
        final byte[] src,
        final int    srcPos,
        final int    destPos,
        final int    length
        )
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
                return this.index < enumLen;
            }

            @Override
            @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
            public T nextElement() throws NoSuchElementException
            {
                if( this.index < enumLen ) {
                    return array[this.index++];
                }
                throw new NoSuchElementException( "index = " + this.index );
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
