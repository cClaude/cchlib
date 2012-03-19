package com.googlecode.cchlib.util;

/**
 * TODOC
 *
 * @param <S> source type
 * @param <R> result type
 * @see cx.ath.choisnet.util.enumeration.EnumerationHelper#toEnumeration(java.util.Enumeration, Wrappable)
 * @see cx.ath.choisnet.util.enumeration.EnumerationHelper#toEnumeration(java.util.Enumeration)
 * @see cx.ath.choisnet.util.iterator.IteratorHelper
 * @see cx.ath.choisnet.util.iterator.IteratorWrapper
 * @see CollectionWrapper
 * @see MapKeyWrapper
 * @see MapWrapper
 * @see SetWrapper
 * @see WrapperHelper
 * @since 4.1.7
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
