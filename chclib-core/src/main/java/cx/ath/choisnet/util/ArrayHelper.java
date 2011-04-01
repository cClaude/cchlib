/**
 * 
 */
package cx.ath.choisnet.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;
import cx.ath.choisnet.util.iterator.ArrayIterator;

/**
 * Providing other view for Arrays
 * 
 * @author Claude CHOISNET
 *
 */
public class ArrayHelper
{
    private ArrayHelper()
    {//All static
    }
    
    /**
     * Wrap an array to get an Enumeration
     * 
     * @param <T>    array content type
     * @param array  array to wrap
     * @param offset first offset for enumeration
     * @param len    number of item return by this Enumeration
     * @return an Enumeration for this array 
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
     * Wrap an array to get an Enumeration
     * 
     * @param <T>    array content type
     * @param array  array to wrap
     * @return an Enumeration for this array 
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
     * @param <T>
     * @param array
     * @return an Iterator
     * @see ArrayIterator
     */
    public static <T> Iterator<T> toIterator(T[] array)
    {
        return new ArrayIterator<T>(array);
    }
    
    /**
     * @param <T>
     * @param array
     * @param offset 
     * @param len 
     * @return an Iterator
     * @see ArrayIterator
     */
    public static <T> Iterator<T> toIterator(T[] array, int offset, int len)
    {
        return new ArrayIterator<T>(array,offset,len);
    }
}
