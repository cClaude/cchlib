package com.googlecode.cchlib.apps.duplicatefiles.console;

/**
 *
 */
public class CommandTaskException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param cause
     */
    public CommandTaskException( final ReflectiveOperationException cause )
    {
        super( cause );
    }

    /**
     *
     * @param message
     * @param cause
     */
    public CommandTaskException( final String message, final Exception cause )
    {
        super( message, cause );
    }
}
