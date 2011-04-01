package cx.ath.choisnet.util;

import cx.ath.choisnet.util.enumeration.EnumerationHelper;
import cx.ath.choisnet.util.iterator.IteratorHelper;
import cx.ath.choisnet.util.iterator.IteratorWrapper;

/**
 *
 * @author Claude CHOISNET
 * @param <S> source type
 * @param <R> result type
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration, Wrappable)
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration)
 * @see IteratorHelper
 * @see IteratorWrapper
 */
public interface Wrappable<S,R>
{
    /**
     * Wrap a object to an other one.
     * 
     * @param obj object to wrap
     * @return an other view for giving object
     */
    abstract R wrappe(S obj);
}
