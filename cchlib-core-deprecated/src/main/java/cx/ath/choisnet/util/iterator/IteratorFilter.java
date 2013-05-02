package cx.ath.choisnet.util.iterator;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Apply a filter on an Iterator, only object
 * matches according to {@link cx.ath.choisnet.util.Selectable} are
 * in iterator result.
 *
 * @param <T> type iterator entries.
 * @deprecated use {@link com.googlecode.cchlib.util.iterator.IteratorFilter} instead
 */
@Deprecated
public class IteratorFilter<T>
    extends ComputableIterator<T>
        implements Iterator<T>
        //Note: ComputableIterator can't be Iterable<T>
{
    private Iterator<T> iterator;
    private cx.ath.choisnet.util.Selectable<T> filter;

    /**
     * Create an IteratorFilter based on an iterator
     * and on a filter.
     *
     * @param iterator
     * @param filter
     */
    public IteratorFilter(
            Iterator<T> iterator,
            cx.ath.choisnet.util.Selectable<T> filter
            )
    {
        this.iterator = iterator;
        this.filter   = filter;
    }

    @Override
    protected T computeNext() throws NoSuchElementException
    {
        while(iterator.hasNext()) {
            T currentObject = iterator.next();

            if( filter.isSelected( currentObject ) ) {
                return currentObject;
            }
        }

        throw new NoSuchElementException();
    }

//    /**
//     * Removes from the underlying collection the last element
//     * returned by the iterator.
//     *
//     * @throws UnsupportedOperationException if the remove
//     *         operation is not supported by parent Iterator.
//     * @throws IllegalStateException if the next method has
//     *         not yet been called, or the remove method has
//     *         already been called after the last call to the
//     *         next method.
//     */
//    @Override
//    public void remove()
//    {
//        this.iterator.remove();
//    }

//    /**
//     * Returns an iterator over a set of elements of type T.
//     * @return this Iterator
//     */
//    @Override
//    public Iterator<T> iterator()
//    {
//        return this;
//    }

    /**
     * 
     *
     * @param fileFilter
     * @return a wrapper for File use that use FileFilter
     */
    public static cx.ath.choisnet.util.Selectable<File> wrappe(final FileFilter fileFilter)
    {
        return new cx.ath.choisnet.util.Selectable<File>() {
            @Override
            public boolean isSelected(File file)
            {
                return fileFilter.accept(file);
            }
        };
    }

//    public static <T> String toString( Iterator<T> iterator, String separator)
//    {
//        StringBuilder sb = new StringBuilder();
//
//        if(iterator.hasNext()) {
//            sb.append(iterator.next().toString());
//        }
//
//        for(; iterator.hasNext(); sb.append(iterator.next().toString())) {
//            sb.append(separator);
//        }
//
//        return sb.toString();
//    }
}
