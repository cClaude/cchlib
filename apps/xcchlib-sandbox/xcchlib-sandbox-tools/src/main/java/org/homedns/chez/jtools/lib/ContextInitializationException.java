package org.homedns.chez.jtools.lib;

/**
 *
 * @since 1.01
 */
public class ContextInitializationException extends Exception
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public ContextInitializationException(
        final String    name,
        final Throwable cause
        )
    {
        super( name, cause );
    }

    public ContextInitializationException( final String name )
    {
        super( name );
    }
}
