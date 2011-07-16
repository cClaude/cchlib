package cx.ath.choisnet.util.iterator;

import java.util.Enumeration;
import java.util.Iterator;
import cx.ath.choisnet.util.ArrayHelper;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;

/**
 * Providing other view for Iterators
 *
 * @see ArrayIterator
 * @see EnumerationHelper
 * @see ArrayHelper#toIterator(Object[])
 * @see ArrayHelper#toIterator(Object[], int, int)
 * @see com.googlecode.cchlib.io.IOHelper#toIterator(java.io.File[], java.io.FileFilter)
 */
// deprecated
// * @see cx.ath.choisnet.io.FileHelper#toIterator(java.io.File[], java.io.FileFilter)
public class IteratorHelper
{
    private IteratorHelper()
    {//All static
    }

//    /**
//     *
//     * @param <T>
//     * @param iteratorArray
//     * @return
//     */
//    public static <T> Iterator<T> toIterator(Iterator<T>[] iteratorArray)
//    {
//        return IteratorHelper.toIterator(iteratorArray, 0, iteratorArray.length);
//    }

//    public static <T> Iterator<T> toIterator(
//            final Iterator<T>[] array,
//            final int           offset,
//            int                 len
//            )
//    {
//        final int endIndex = offset + len;
//
//        return new Iterator<T>() {
//
//            int index = offset;
//
//            T nextObject;
//
//            public boolean hasNext()
//            {
//                if(nextObject == null) {
//                    try {
//                        nextObject = computeNext();
//                    }
//                    catch(java.util.NoSuchElementException e) {
//                        return false;
//                    }
//                }
//
//                return true;
//            }
//
//            public T next()
//                throws java.util.NoSuchElementException
//            {
//                if(nextObject == null) {
//                    nextObject = computeNext();
//                }
//
//                T returnObject = nextObject;
//
//                try {
//                    nextObject = computeNext();
//                }
//                catch(java.util.NoSuchElementException e) {
//                    nextObject = null;
//                }
//
//                return returnObject;
//            }
//
//            public void remove()
//            {
//                throw new UnsupportedOperationException();
//            }
//
//            private T computeNext()
//                throws java.util.NoSuchElementException
//            {
//                for(; index < endIndex; index++) {
//                    if(array[index].hasNext()) {
//                        return array[index].next();
//                    }
//                }
//
//                throw new NoSuchElementException();
//            }
//        };
//
//    }

//    public static <T> String toString(Iterator<T> iterator, String separator)
//    {
//        StringBuilder sb = new StringBuilder();
//
//        if(iterator.hasNext()) {
//            sb.append(iterator.next().toString());
//        }
//
//        for(; iterator.hasNext(); ) {
//            sb.append(separator);
//            sb.append(iterator.next().toString());
//        }
//
//        return sb.toString();
//
//    }
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
