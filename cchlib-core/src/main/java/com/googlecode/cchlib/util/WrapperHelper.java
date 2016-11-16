package com.googlecode.cchlib.util;

import java.util.Collection;
import java.util.Enumeration;

/**
 * Tools for {@link Wrappable}
 *
 * @since 4.1.7
 */
public final class WrapperHelper
{
    private WrapperHelper()
    {//All static
    }

    /**
     * Create a Wrappable object using Object.toString()
     *
     * Typical code :
     *    return new Wrappable<T,String>()
     *    {
     *        public String wrap(T o)
     *        {
     *            return o.toString();
     *        }
     *    };
     * </code>
     *
     * @param <T> type to wrap
     * @return a wrapper
     */
    public static final <T> Wrappable<T,String> wrapToString()
    {
        return o -> o.toString();
    }

    /**
     * Return a {@link Wrappable} object able to transform String
     * to Integer.
     * @return a wrapper
     */
    public static final Wrappable<String,Integer> wrapStringToInteger()
    {
        return str -> {
            try {
                return Integer.parseInt( str );
            } catch( final NumberFormatException e ) {
                throw new WrapperException( e );
            }
        };
    }

    /**
     * Wrap an Enumeration.
     *
     * @param <E> Type off the giving Enumeration
     * @param <O> Type off the returning Enumeration
     * @param enumeration Enumeration to wrap
     * @param wrapper     Wrapper to use
     * @return an new Enumeration consuming the giving while running
     */
    public static final <E,O> Enumeration<O> wrappeEnumeration(
            final Enumeration<E>    enumeration,
            final Wrappable<E,O>    wrapper
            )
    {
        return new Enumeration<O>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return enumeration.hasMoreElements();
            }
            @Override
            public O nextElement()
            {
                 return wrapper.wrap( enumeration.nextElement() );
            }
        };
    }

    /**
     * Give a view of a collection of type <code>S</code> by returning
     * a collection of type <code>S</code>.
     *
     * @param collection Collection to wrap
     * @param wrapper Method to transform object of type <code>S</code>
     *                into type <code>R</code>
     * @param unwrapper Reflexive method of <code>wrapper</code>
     * @return a collection of type <code>S</code>
     */
    public static <S,R> Collection<R> toCollection(
        final Collection<S>   collection,
        final Wrappable<S,R>  wrapper,
        final Wrappable<R,S>  unwrapper
        )
    {
        return new CollectionWrapper<>( collection, wrapper, unwrapper );
    }
}
