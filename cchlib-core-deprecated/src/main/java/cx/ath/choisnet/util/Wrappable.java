package cx.ath.choisnet.util;

import cx.ath.choisnet.util.enumeration.EnumerationHelper;

/**
 * TODOC
 *
 * @param <S> source type
 * @param <R> result type
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration, Wrappable)
 * @see EnumerationHelper#toEnumeration(java.util.Enumeration)
 * @see cx.ath.choisnet.util.iterator.IteratorHelper
 * @see cx.ath.choisnet.util.iterator.IteratorWrapper
 * @deprecated use {@link com.googlecode.cchlib.util.Wrappable} instead
 */
public interface Wrappable<S,R>
{
    /**
     * Wrap a object to an other one.
     *
     * @param obj object to wrap
     * @return an other view for giving object
     * @throws WrappeException if any error occur while wrapping,
     *         use {@link WrappeException#getCause()} to have
     *         initial error.
     */
    abstract R wrappe(S obj) throws WrappeException;
}
