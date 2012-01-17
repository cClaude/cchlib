package cx.ath.choisnet.tools.analysis;

import org.apache.log4j.Logger;

/**
 *
 *
 */
public class Log4JXLogger implements XLogger
{
    private final Logger logger;

    public Log4JXLogger()
    {
        this( Log4JXLogger.class );
    }

    public Log4JXLogger( final Class<?> clazz )
    {
        this.logger = Logger.getLogger( clazz );
    }

    @Override
    public void info( final String message )
    {
        this.logger.info( message );
    }

    @Override
    public void error( final String message, final Exception e )
    {
        this.logger.error( message, e );
    }
}
