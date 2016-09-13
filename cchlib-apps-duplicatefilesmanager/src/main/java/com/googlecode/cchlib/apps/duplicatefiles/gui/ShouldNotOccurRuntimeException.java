package com.googlecode.cchlib.apps.duplicatefiles.gui;

/**
 * Exceptions than should not occur
 */
public class ShouldNotOccurRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ShouldNotOccurRuntimeException (runtime exception) with the
     * specified cause
     * @param cause the cause
     */
    public ShouldNotOccurRuntimeException( final Exception cause )
    {
        super( cause );
    }
}
