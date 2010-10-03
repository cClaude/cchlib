package cx.ath.choisnet.util.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * TODO: Doc!
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T> 
 */
public class MultiIterator<T> extends ComputableIterator<T>
{
    private final Iterator<Iterator<T>> metaIterator;
    private Iterator<T>                 currentIterator;

    /**
     * 
     * @param iteratorOfIterator
     */
    public MultiIterator(Iterator<Iterator<T>> iteratorOfIterator)
    {
        currentIterator = null;
        metaIterator    = iteratorOfIterator;
    }

    /**
     * 
     * @param collectionOfIterator
     */
    public MultiIterator(Collection<Iterator<T>> collectionOfIterator)
    {
        this(collectionOfIterator.iterator());
    }

    /**
     * 
     * @param iter0
     * @param iter1
     */
    public MultiIterator(
            Iterator<T> iter0, 
            Iterator<T> iter1
            )
    {
        currentIterator = null;

        List<Iterator<T>> listOfIterator = new ArrayList<Iterator<T>>();
        listOfIterator.add(iter0);
        listOfIterator.add(iter1);
        metaIterator = listOfIterator.iterator();
    }

    /**
     * 
     * @param iter
     * @param element
     */
    public MultiIterator(Iterator<T> iter, T element)
    {
        currentIterator = null;

        List<Iterator<T>> listOfIterator = new LinkedList<Iterator<T>>();
        listOfIterator.add( iter );
        listOfIterator.add( new SingletonIterator<T>(element) );
        metaIterator = listOfIterator.iterator();

    }

    /**
     * 
     * @param element
     * @param iter
     */
    public MultiIterator(T element, Iterator<T> iter)
    {
        currentIterator = null;

        List<Iterator<T>> listOfIterator = new LinkedList<Iterator<T>>();
        listOfIterator.add(new SingletonIterator<T>(element));
        listOfIterator.add(iter);
        
        metaIterator = listOfIterator.iterator();
    }

    /**
     * 
     * @param arrayOfIterator
     */
    public MultiIterator(
            Iterator<T>...arrayOfIterator
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
            Iterator<T>[]   arrayOfIterator, 
            int             offset, 
            int             len
            )
    {
        currentIterator = null;
        metaIterator = new ArrayIterator<Iterator<T>>(arrayOfIterator, offset, len);
    }

    public T computeNext()
        throws java.util.NoSuchElementException
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
