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
    private Iterator<T>           iterator;
    private Selectable<? super T> filter;

    /**
     * Create an IteratorFilter based on an iterator
     * and on a filter.
     *
     * @param iterator
     * @param filter
     */
    public IteratorFilter(
        Iterator<T>             iterator,
        Selectable<? super T>   filter
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

    /**
     * TODOC
     *
     * @param fileFilter
     * @return a wrapper for File use that use FileFilter
     */
    public static Selectable<File> wrap(final FileFilter fileFilter)
    {
        return new Selectable<File>() {
            @Override
            public boolean isSelected(File file)
            {
                return fileFilter.accept(file);
            }
        };
    }
}
