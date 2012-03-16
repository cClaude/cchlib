package com.googlecode.cchlib.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Build an Iterator based on an Iterator of Iterator.
 * This new Iterator that consume all sub-Iterator in
 * order of main Iterator for it's
 * results (Order is preserve).
 *
 * <BR/>
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

    /**
     * TODOC
     * @param iteratorOfIterator
     */
    public MultiIterator(Iterator<? extends Iterator<? extends T>> iteratorOfIterator)
    {
        currentIterator = null;
        metaIterator    = iteratorOfIterator;
    }

    /**
     * TODOC
     * @param collectionOfIterator
     */
    public MultiIterator(Collection<? extends Iterator<? extends T>> collectionOfIterator)
    {
        this(collectionOfIterator.iterator());
    }

    /**
     * TODOC
     * @param iter0
     * @param iter1
     */
    public MultiIterator(
            Iterator<? extends T> iter0,
            Iterator<? extends T> iter1
            )
    {
        currentIterator = null;

        List<Iterator<? extends T>> listOfIterator = new ArrayList<Iterator<? extends T>>();
        listOfIterator.add(iter0);
        listOfIterator.add(iter1);
        metaIterator = listOfIterator.iterator();
    }

    /**
     * TODOC
     * @param iter
     * @param element
     */
    public MultiIterator(Iterator<? extends T> iter, T element)
    {
        currentIterator = null;

        List<Iterator<? extends T>> listOfIterator = new LinkedList<Iterator<? extends T>>();
        listOfIterator.add( iter );
        listOfIterator.add( new SingletonIterator<T>(element) );

        metaIterator = listOfIterator.iterator();
    }

    /**
     * TODOC
     * @param element
     * @param iter
     */
    public MultiIterator(T element, Iterator<? extends T> iter)
    {
        currentIterator = null;

        List<Iterator<? extends T>> listOfIterator = new LinkedList<Iterator<? extends T>>();
        listOfIterator.add(new SingletonIterator<T>(element));
        listOfIterator.add(iter);

        metaIterator = listOfIterator.iterator();
    }

    /**
     * TODOC
     * @param arrayOfIterator
     */
    //Java 1.7 @SafeVarargs
    public MultiIterator(
            Iterator<? extends T>...arrayOfIterator
            )
    {
        this(arrayOfIterator, 0, arrayOfIterator.length);
    }

    /**
     * TODOC
     * @param arrayOfIterator
     * @param offset
     * @param len
     */
    public MultiIterator(
            Iterator<? extends T>[] arrayOfIterator,
            int                     offset,
            int                     len
            )
    {
        this(
            new ArrayIterator<Iterator<? extends T>>(arrayOfIterator, offset, len)
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
