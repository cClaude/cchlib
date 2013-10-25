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

import java.io.InputStream;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletConfig;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public class HTTPPingTask
    extends cx.ath.choisnet.tools.servlets.InitServletAbstractTask

{
/**
** Gestion des traces
*/
private org.apache.commons.logging.Log logger
      = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/** */
private Proxy proxy = Proxy.NO_PROXY;

/** */
private URL[] urls;

/**
**
*/
public HTTPPingTask() // --------------------------------------------------
{
 super();
}

/**
**
*/
public HTTPPingTask( String instanceName ) // -----------------------------
{
 super( instanceName );
}

/**
**
*/
public void init( ServletConfig servletConfig ) // ------------------------
    throws javax.servlet.ServletException
{
 String     paramName = getTaskName();
 String[]   values;

 try {
    String proxyParam = paramName + ".Proxy";
    //
    // http://213.228.0.12:3128/
    //
    values = servletConfig.getInitParameter( proxyParam ).split( "[:/]+" );

    //String  type = values[ 0 ];
    String  host = values[ 1 ];
    int     port = Integer.parseInt( values[ 2 ] );

    this.proxy = new Proxy( Proxy.Type.HTTP, new InetSocketAddress( host, port ) );

    logger.info( paramName + ".Proxy = " + this.proxy );
    }
 catch( NullPointerException e ) {
    logger.info( paramName + ".Proxy = null" );
    }
 catch( Exception e ) {
    logger.info( paramName + ".Proxy = null", e );
    }

 try {
    values = servletConfig.getInitParameter( paramName ).split( "[ ,\n\t]+" );
    }
 catch( Exception e ) {
    logger.error( "paramName = " + paramName, e );

    throw new javax.servlet.ServletException(
                    "Parameter '" + paramName + "' not valid.",
                    e
                    );
    }

 this.urls = new URL[ values.length ];

 for( int i = 0; i < values.length; i++ ) {
    try {
        this.urls[ i ] = new URL( values[ i ] );

        logger.info( paramName + " = urls [" + urls[ i ] + "] " + urls.length );
        }
    catch( java.net.MalformedURLException e ) {
        throw new javax.servlet.ServletException(
                    "Parameter '" + paramName + "' not valid : " + values[ i ],
                    e
                    );
        }
    }
}

/**
**
*/
public void run() // ------------------------------------------------------
{
 for( URL anURL : urls ) {
    httpPing( anURL );
    }
}

/**
**
*/
public void httpPing( URL anURL ) // --------------------------------------
{
 try {
    URLConnection   connect = anURL.openConnection( this.proxy );
    InputStream     stream  = connect.getInputStream();
    long            count   = 0;
    int             c;

    try {
        while( (c = stream.read()) != -1 ) {
            //
            // compte le nombre d'octects dans le flux
            //
            count++;
            }
        }
    finally {
        stream.close();
        }

    if( c == -1 ) {
        logger.info( getTaskName() + ": " + anURL + " - OK (" + count + ")" );
        }
    else {
        logger.warn( getTaskName() + ": " + anURL + " - FAIL " );
        }
    }
 catch( java.io.IOException e ) {
    logger.warn( getTaskName() + ": " + anURL + " - FAIL : " + e.getMessage() );
    }
}

} // class

