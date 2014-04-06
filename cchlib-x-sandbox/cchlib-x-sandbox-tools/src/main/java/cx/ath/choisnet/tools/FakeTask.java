/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/tools/FakeTask.java
 ** Description   :
 **
 ** 1.00 2005.08.21 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.tools.FakeTask
 **
 */
package cx.ath.choisnet.tools;

import javax.servlet.ServletConfig;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.servlets.InitServletTask;

/**
 ** 
 ** @author Claude CHOISNET
 ** @version 1.0
 */
public class FakeTask extends InitServletTask 
{
    private static final Logger LOGGER = Logger.getLogger( FakeTask.class );

    @Override
    public void init( // ------------------------------------------------------
            ServletConfig servletConfig )
    {
        log( " *****************************************" );
        log( " * " + this.hashCode() + ".init(" + servletConfig + ")" );
        log( " *****************************************" );
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        log( " *****************************************" );
        log( " * " + this.hashCode() + ".run()" );
        log( " *****************************************" );
    }

    @Override
    public void log( String message ) // --------------------------------------
    {
        LOGGER.trace( message );
    }

}
