/**
 * 
 */
package cx.ath.choisnet.util.enumeration;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * TODOC
 * 
 * @author Claude CHOISNET
 * @param <E> 
 */
public class EmptyEnumeration<E>
    implements Enumeration<E> 
{
    /**
     * TODOC
     * 
     */
    public EmptyEnumeration()
    {
    }

    /**
     * Tests if this enumeration contains more elements. 
     * 
     * @return true if and only if this enumeration
     *         object contains at least one more element
     *         to provide; false otherwise.
     */
    @Override
    public boolean hasMoreElements()
    {
        return false;
    }

    /**
     * Always generate NoSuchElementException according
     * to Enumeration specifications.
     * 
     * @throws NoSuchElementException
     */
    @Override
    public E nextElement() throws NoSuchElementException
    {
        throw new NoSuchElementException();
    }
}
