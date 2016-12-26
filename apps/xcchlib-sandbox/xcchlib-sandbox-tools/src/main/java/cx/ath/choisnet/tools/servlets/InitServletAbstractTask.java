package cx.ath.choisnet.tools.servlets;

import org.apache.log4j.Logger;

/**
 *
 *
 * @since 1.00
 */
public abstract class InitServletAbstractTask implements InitServletTaskInterface
{
    private static final Logger LOGGER = Logger.getLogger( InitServletAbstractTask.class );

    private final String    instanceName;
    private boolean         continueRunning;

    public InitServletAbstractTask()
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
