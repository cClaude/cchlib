package cx.ath.choisnet.util.iterator;

import java.util.Iterator;

/**
 * TODO: Doc!
 * 
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface

 * @author Claude CHOISNET
 * @param <T>
 */
public class CascadingIterator<T> extends ComputableIterator<T>
{
    private Iterator<Iterable<T>> mainIterator;
    private Iterator<T> currentIterator;

    public CascadingIterator(Iterator<Iterable<T>> iterator)
    {
        mainIterator    = iterator;
        currentIterator = null;
    }

    public T computeNext()
        throws java.util.NoSuchElementException
    {
        do {
            if(currentIterator != null) {
                try {
                    return currentIterator.next();
                }
                catch(java.util.NoSuchElementException ignore) {
                }
            }

            currentIterator = mainIterator.next().iterator();

        } while(true);
    }
}
