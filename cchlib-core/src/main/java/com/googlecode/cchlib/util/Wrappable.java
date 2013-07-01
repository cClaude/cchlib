package com.googlecode.cchlib.util;

/**
 * A {@link Wrappable} object is a object able to
 * mute from a type to a other one.
 *
 * @param <S> source type
 * @param <R> result type
 * @see com.googlecode.cchlib.util.enumeration.Enumerations#toEnumeration(java.util.Enumeration)
 * @see com.googlecode.cchlib.util.enumeration.Enumerations#toEnumeration(java.util.Enumeration, Wrappable)
 * @see com.googlecode.cchlib.util.iterator.IteratorWrapper
 * @see com.googlecode.cchlib.util.iterator.Iterators
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
    abstract R wrap(S obj) throws WrappeException;
}
