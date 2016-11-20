/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx\ath\choisnet\tools\servlets\InitServletAbstractTask.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.00 2004.12.01 Claude CHOISNET
 **  1.10 2005.10.17 Claude CHOISNET
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.tools.servlets.InitServletAbstractTask
 **
 */
package cx.ath.choisnet.tools.servlets;

import org.apache.log4j.Logger;

/**
 **
 **
 ** @author Claude CHOISNET
 ** @since 1.00
 ** @version 1.10
 */
public abstract class InitServletAbstractTask implements InitServletTaskInterface
{
    private final static Logger LOGGER = Logger.getLogger( InitServletAbstractTask.class );

    private final String              instanceName;
    private boolean             continueRunning;

    public InitServletAbstractTask() // ---------------------------------------
    {
        this( null );
    }

    public InitServletAbstractTask( final String instanceName ) // ------------------
    {
        this.instanceName = instanceName;
        this.continueRunning = true;
    }

    public String getInstanceName() // ----------------------------------------
    {
        return this.instanceName;
    }

    @Override
    public String getTaskName() // --------------------------------------------
    {
        final String name = this.getInstanceName();

        if( name == null ) {
            return this.getClass().getName();
        } else {
            return this.getClass().getName() + "." + name;
        }
    }

    public void stop() // -----------------------------------------------------
    {
        this.continueRunning = false;
    }

    @Override
    public boolean continueRunning() // ---------------------------------------
    {
        return this.continueRunning;
    }

    @Override
    public void log( final String message ) // --------------------------------------
    {
        LOGGER.trace( message );
    }

}
