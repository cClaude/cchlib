package com.googlecode.cchlib.lang.reflect;


/**
 * An {@link Invoker} for static methods
 */
public class InvokerOfStaticMethod<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an InvokerOfStaticMethod
     * 
     * @param clazz Class to use
     */
    public InvokerOfStaticMethod(
        final Class<? extends T> clazz
        )
    {
        super( clazz, new StaticMethodFilter() );
    }

    @Override
    protected String formatMethodNameForException( String format )
    {
        return String.format(
                format,
                getClazz().getName() + ".{method static}"
                );
    }
}
