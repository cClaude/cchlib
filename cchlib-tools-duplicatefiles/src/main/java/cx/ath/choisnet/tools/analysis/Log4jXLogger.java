package cx.ath.choisnet.tools.analysis;

import org.apache.log4j.Logger;

/**
 *
 *
 */
public class Log4jXLogger implements XLogger
{
    private final Logger logger;

    public Log4jXLogger( Class<?> clazz )
    {
        this.logger = Logger.getLogger( clazz );
    }

    @Override
    public void info( String message )
    {
        this.logger.info( message );
    }

    @Override
    public void error( String message, Exception e )
    {
        this.logger.error( message, e );
    }

}
