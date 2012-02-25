package com.googlecode.cchlib.swing.batchrunner;

/**
 * Use to detect user cancel action
 * 
 * @since 1.4.7
 */
public class BatchRunnerInterruptedException extends Exception
{
    private static final long serialVersionUID = 1L;

    public BatchRunnerInterruptedException( Throwable cause )
    {
        super( cause );
    }

    public BatchRunnerInterruptedException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
