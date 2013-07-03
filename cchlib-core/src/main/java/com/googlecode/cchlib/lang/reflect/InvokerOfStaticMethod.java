package com.googlecode.cchlib.lang.reflect;


/**
 *
 */
public class InvokerOfStaticMethod<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param clazz
     */
    public InvokerOfStaticMethod(
        Class<? extends T> clazz
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
