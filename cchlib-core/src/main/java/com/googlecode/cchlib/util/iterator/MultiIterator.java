package com.googlecode.cchlib.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.NeedDoc;

/**
 * Build an Iterator based on an Iterator of Iterator.
 * This new Iterator that consume all sub-Iterator in
 * order of main Iterator for it's
 * results (Order is preserve).
 *
 * <BR>
 * Note: This Iterator extends also {@link Iterable} interface
 *
 * @param <T> content type
 * @see CascadingIterator
 * @since 4.1.7
 */
public class MultiIterator<T> extends ComputableIterator<T>
{
    private final Iterator<? extends Iterator<? extends T>> metaIterator;
    private Iterator<? extends T>                           currentIterator;

    @NeedDoc
    public MultiIterator(final Iterator<? extends Iterator<? extends T>> iteratorOfIterator)
    {
        currentIterator = null;
        metaIterator    = iteratorOfIterator;
    }

    @NeedDoc
    public MultiIterator(final Collection<? extends Iterator<? extends T>> collectionOfIterator)
    {
        this(collectionOfIterator.iterator());
    }

    @NeedDoc
    public MultiIterator(
            final Iterator<? extends T> iter0,
            final Iterator<? extends T> iter1
            )
    {
        currentIterator = null;

        final List<Iterator<? extends T>> listOfIterator = new ArrayList<>();
        listOfIterator.add(iter0);
        listOfIterator.add(iter1);
        metaIterator = listOfIterator.iterator();
    }

    @NeedDoc
    public MultiIterator(final Iterator<? extends T> iter, final T element)
    {
        currentIterator = null;

        final List<Iterator<? extends T>> listOfIterator = new LinkedList<>();
        listOfIterator.add( iter );
        listOfIterator.add( new SingletonIterator<>(element) );

        metaIterator = listOfIterator.iterator();
    }

    @NeedDoc
    public MultiIterator(final T element, final Iterator<? extends T> iter)
    {
        currentIterator = null;

        final List<Iterator<? extends T>> listOfIterator = new LinkedList<>();
        listOfIterator.add(new SingletonIterator<>(element));
        listOfIterator.add(iter);

        metaIterator = listOfIterator.iterator();
    }

    @NeedDoc
    @SafeVarargs
    public MultiIterator(
            final Iterator<? extends T>...arrayOfIterator
            )
    {
        this(arrayOfIterator, 0, arrayOfIterator.length);
    }

    @NeedDoc
    public MultiIterator(
            final Iterator<? extends T>[] arrayOfIterator,
            final int                     offset,
            final int                     len
            )
    {
        this(
            new ArrayIterator<>(arrayOfIterator, offset, len)
            );
    }

    @Override
    protected T computeNext()throws NoSuchElementException
    {
        if(currentIterator == null) {
            currentIterator = metaIterator.next();
            }

        do {
            if(currentIterator.hasNext()) {
                return currentIterator.next();
                }

            if(metaIterator.hasNext()) {
                currentIterator = metaIterator.next();
                }
            else {
                throw new NoSuchElementException();
                }
            } while(true);
    }
}
