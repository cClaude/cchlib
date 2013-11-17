/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/net/PingTask.java
 ** Description   :
 **
 ** 1.00 2005.09.19 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.net.PingTask
 **
 */
package cx.ath.choisnet.net;

import java.net.InetAddress;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.servlets.InitServletAbstractTask;

/**
 ** 
 ** @author Claude CHOISNET
 ** @version 1.0
 */
public class PingTask extends InitServletAbstractTask
{
    private static final Logger LOGGER = Logger.getLogger( PingTask.class );

    private String[] hosts;

    public PingTask() // ------------------------------------------------------
    {
        super();
    }

    public PingTask( final String instanceName ) // ---------------------------------
    {
        super( instanceName );
    }

    @Override
    public void init( final ServletConfig servletConfig ) // ------------------------
        throws ServletException
    {
        String paramName = getTaskName();

        try {
            this.hosts = servletConfig.getInitParameter( paramName ).split( "[ ,\n\t]+" );
            }
        catch( Exception e ) {
            LOGGER.error( "paramName = " + paramName, e );

            throw new javax.servlet.ServletException( "Parameter '" + paramName
                    + "' not valid." );
            }

        for( int i = 0; i < hosts.length; i++ ) {
            LOGGER.info( paramName + " = host [" + hosts[ i ] + "] "
                    + hosts.length );
            }
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        for( String host : hosts ) {
            ping( host );
        }
    }

    public void ping( String host ) // ----------------------------------------
    {
        try {
            InetAddress inetAddress = InetAddress.getByName( host );

            try {
                boolean isReachable = inetAddress.isReachable( 5000 );

                if( isReachable ) {
                    LOGGER.info( getTaskName() + ": " + host + " - OK " );
                } else {
                    LOGGER.warn( "ping " + host + " - WARN - " + inetAddress );
                }
            }
            catch( java.io.IOException e ) {
                LOGGER.warn( "hostname=" + host + " - " + e.getMessage() );
            }

        }
        catch( java.net.UnknownHostException e ) {
            LOGGER.warn( "hostname=" + host + " - " + e.getMessage() );
        }
    }
}

