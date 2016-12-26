package cx.ath.choisnet.tools;

import javax.servlet.ServletConfig;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.servlets.InitServletTask;

/**
 *
 *
 * @since 1.00
 */
public class FakeTask extends InitServletTask
{
    private static final Logger LOGGER = Logger.getLogger( FakeTask.class );

    private static final String MESSAGE_SEPARATOR = " *****************************************";

    @Override
    public void init( final ServletConfig servletConfig )
    {
        log( MESSAGE_SEPARATOR );
        log( " * " + this.hashCode() + ".init(" + servletConfig + ")" );
        log( MESSAGE_SEPARATOR );
    }

    @Override
    public void run()
    {
        log( MESSAGE_SEPARATOR );
        log( " * " + this.hashCode() + ".run()" );
        log( MESSAGE_SEPARATOR );
    }

    @Override
    public void log( final String message )
    {
        LOGGER.trace( message );
    }
}
