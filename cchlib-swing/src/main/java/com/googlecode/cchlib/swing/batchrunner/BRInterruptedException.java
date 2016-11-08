package com.googlecode.cchlib.swing.batchrunner;

import java.io.Serializable;

/**
 * Use to detect user cancel action
 *
 * @since 1.4.8
 */
public class BRInterruptedException extends Exception
{
    private static final long serialVersionUID = 1L;
    private Serializable customObject;
    private boolean hide;

    public BRInterruptedException( Throwable cause )
    {
        super( cause );
        
        setHide( cause instanceof BRUserCancelException );
    }

    public BRInterruptedException( String message )
    {
        super( message );
    }

    public BRInterruptedException( String message, Throwable cause )
    {
        super( message, cause );
        
        setHide( cause instanceof BRUserCancelException );
    }

    /**
     * Could be use by client to store extra information.
     *
     * @param customObject Any {@link Serializable} custom object
     */
    public void setCustomObject( final Serializable customObject )
    {
        this.customObject = customObject;
    }

    /**
     * Returns customObject if has been set, null otherwise
     * @return customObject if has been set, null otherwise
     */
    public Serializable getCustomObject()
    {
        return this.customObject;
    }

    /**
     * Returns true if exception should not be display to user, false otherwise
     * @return true if exception should not be display to user, false otherwise
     */
    public boolean isHide()
    {
        return hide;
    }

    /**
     * Set <code>hide</code> attribute.
     * 
     * @param hide <code>hide</code> attribute to set.
     */
    public void setHide( boolean hide )
    {
        this.hide = hide;
    }
}
