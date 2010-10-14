package cx.ath.choisnet.util;

import cx.ath.choisnet.util.enumeration.EnumerationHelper;
import cx.ath.choisnet.util.iterator.IteratorHelper;
import cx.ath.choisnet.util.iterator.IteratorWrapper;

/**
 *
 * @author Claude CHOISNET
 * @param <T> source type
 * @param <O> result type
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration, Wrappable)
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration)
 * @see IteratorHelper
 * @see IteratorWrapper
 */
public interface Wrappable<T,O>
{
    /**
     * Wrap a object to an other one.
     * 
     * @param obj object to wrap
     * @return an other view for giving object
     */
    abstract O wrappe(T obj);
}
