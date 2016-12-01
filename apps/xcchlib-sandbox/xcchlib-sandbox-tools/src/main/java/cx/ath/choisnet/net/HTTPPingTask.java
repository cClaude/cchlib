/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/net/HTTPPingTask.java
 ** Description   :
 **
 ** 1.00 2005.09.19 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.net.HTTPPingTask
 **
 */
package cx.ath.choisnet.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.servlets.InitServletAbstractTask;

/**
 **
 ** @author Claude CHOISNET
 ** @version 1.0
 */
public class HTTPPingTask extends InitServletAbstractTask
{
    private static final Logger LOGGER = Logger.getLogger( HTTPPingTask.class );

    private Proxy  proxy  = Proxy.NO_PROXY;
    private URL[]  urls;

    public HTTPPingTask()
    {
        super();
    }

    public HTTPPingTask( final String instanceName )
    {
        super( instanceName );
    }

    @Override
    public void init( final ServletConfig servletConfig )
        throws ServletException
    {
        final String paramName = getTaskName();
        String[] values;

        try {
            final String proxyParam = paramName + ".Proxy";
            //
            // http://213.228.0.12:3128/
            //
            values = servletConfig.getInitParameter( proxyParam ).split(
                    "[:/]+" );

            // String type = values[ 0 ];
            final String host = values[ 1 ];
            final int port = Integer.parseInt( values[ 2 ] );

            this.proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress(
                    host, port ) );

            LOGGER.info( paramName + ".Proxy = " + this.proxy );
        }
        catch( final NullPointerException e ) {
            LOGGER.info( paramName + ".Proxy = null" );
        }
        catch( final Exception e ) {
            LOGGER.info( paramName + ".Proxy = null", e );
        }

        try {
            values = servletConfig.getInitParameter( paramName ).split(
                    "[ ,\n\t]+" );
        }
        catch( final Exception e ) {
            LOGGER.error( "paramName = " + paramName, e );

            throw new ServletException( "Parameter '" + paramName
                    + "' not valid.", e );
        }

        this.urls = new URL[values.length];

        for( int i = 0; i < values.length; i++ ) {
            try {
                this.urls[ i ] = new URL( values[ i ] );

                LOGGER.info( paramName + " = urls [" + this.urls[ i ] + "] "
                        + this.urls.length );
            }
            catch( final MalformedURLException e ) {
                throw new ServletException( "Parameter '"
                        + paramName + "' not valid : " + values[ i ], e );
            }
        }
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        for( final URL anURL : this.urls ) {
            httpPing( anURL );
        }
    }

    public void httpPing( final URL anURL )
    {
        try {
            final URLConnection connect = anURL.openConnection( this.proxy );
            long count = 0;
            int c;

            try( final InputStream stream = connect.getInputStream() ) {
                while( (c = stream.read()) != -1 ) {
                    //
                    // compte le nombre d'octects dans le flux
                    //
                    count++;
                }
            }

            if( c == -1 ) {
                LOGGER.info( getTaskName() + ": " + anURL + " - OK (" + count
                        + ")" );
            } else {
                LOGGER.warn( getTaskName() + ": " + anURL + " - FAIL " );
            }
        }
        catch( final IOException e ) {
            LOGGER.warn( getTaskName() + ": " + anURL + " - FAIL : "
                    + e.getMessage() );
        }
    }
}

