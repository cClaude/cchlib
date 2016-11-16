package com.googlecode.cchlib.util.iterator;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Apply a filter on an Iterator, only object
 * matches according to {@link Selectable} are
 * in iterator result.
 *
 * @param <T> type iterator entries.
 * @since 4.1.7
 */
public class IteratorFilter<T>
    extends ComputableIterator<T>
{
    private final Iterator<T>           iterator;
    private final Selectable<? super T> filter;

    /**
     * Create an IteratorFilter based on an iterator
     * and on a filter.
     *
     * @param iterator
     * @param filter
     */
    public IteratorFilter(
        final Iterator<T>             iterator,
        final Selectable<? super T>   filter
        )
    {
        this.iterator = iterator;
        this.filter   = filter;
    }

    @Override
    protected T computeNext() throws NoSuchElementException
    {
        while(this.iterator.hasNext()) {
            final T currentObject = this.iterator.next();

            if( this.filter.isSelected( currentObject ) ) {
                return currentObject;
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * NEEDDOC
     *
     * @param fileFilter
     * @return a wrapper for File use that use FileFilter
     */
    public static Selectable<File> wrap(final FileFilter fileFilter)
    {
        return fileFilter::accept;
    }
}
