package cx.ath.choisnet.util.iterator;

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
 * @author Claude CHOISNET
 * @param <T> 
 * @see CascadingIterator
 */
public class MultiIterator<T> extends ComputableIterator<T>
{
    private final Iterator<? extends Iterator<? extends T>> metaIterator;
    private Iterator<? extends T>                           currentIterator;

    /**
     * 
     * @param iteratorOfIterator
     */
    public MultiIterator(Iterator<? extends Iterator<? extends T>> iteratorOfIterator)
    {
        currentIterator = null;
        metaIterator    = iteratorOfIterator;
    }

    /**
     * 
     * @param collectionOfIterator
     */
    public MultiIterator(Collection<? extends Iterator<? extends T>> collectionOfIterator)
    {
        this(collectionOfIterator.iterator());
    }

    /**
     * 
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
     * 
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
     * 
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
     * 
     * @param arrayOfIterator
     */
    @SafeVarargs
	public MultiIterator(
            Iterator<? extends T>...arrayOfIterator
            )
    {
        this(arrayOfIterator, 0, arrayOfIterator.length);
    }

    /**
     * 
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

//    /**
//     * Removes from the underlying collection the last element
//     * returned by the iterator. 
//     * 
//     * @throws UnsupportedOperationException if the remove
//     *         operation is not supported by current Iterator. 
//     * @throws IllegalStateException if the next method has
//     *         not yet been called, or the remove method has
//     *         already been called after the last call to the
//     *         next method.
//     */
//    @Override
//    public void remove()
//    {
//        if(currentIterator != null) {
//            currentIterator.remove();
//        }
//        else {
//            throw new IllegalStateException();
//        }
//    }
}
