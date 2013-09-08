package com.googlecode.cchlib.swing.batchrunner;

/**
 * Use to detect user cancel action
 *
 * @since 1.4.7
 */
@Deprecated
public class BatchRunnerInterruptedException extends Exception
{
    private static final long serialVersionUID = 2L;
    private Object customObject;

    public BatchRunnerInterruptedException( Throwable cause )
    {
        super( cause );
    }

    public BatchRunnerInterruptedException( String message )
    {
        super( message );
    }

    public BatchRunnerInterruptedException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Could be use by client to store extra information.
     *
     * @param customObject Any custom object
     */
    public void setCustomObject( final Object customObject )
    {
        this.customObject = customObject;
    }

    /**
     * Returns customObject if has been set, null otherwise
     * @return customObject if has been set, null otherwise
     */
    public Object getCustomObject()
    {
        return this.customObject;
    }
}
