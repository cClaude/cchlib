package com.googlecode.cchlib.util;

import java.util.Enumeration;

/**
 * TODOC
 *
 * @param <T>
 * @param <O>
 * @since 4.1.7
 */
public class WrapperHelper<T,O>
{
    private WrapperHelper()
    {//All static
    }

    /**
     * Create a Wrappable object using Object.toString()
     * @param <T> type to wrap
     * @return a wrapper
     */
    public static final <T> Wrappable<T,String> wrappeToString()
    {
        return new Wrappable<T,String>()
        {
            @Override
            public String wrap(T o)
            {
                return o.toString();
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
}
