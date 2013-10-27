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
import cx.ath.choisnet.tools.servlets.InitServletAbstractTask;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public class PingTask
    extends InitServletAbstractTask

{
/**
** Gestion des traces
*/
final org.apache.commons.logging.Log logger
    = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/** */
private String[] hosts;

/**
**
*/
public PingTask() // ------------------------------------------------------
{
 super();
}
/**
**
*/
public PingTask( String instanceName ) // ---------------------------------
{
 super( instanceName );
}

/**
**
*/
@Override
public void init( ServletConfig servletConfig ) // ------------------------
    throws javax.servlet.ServletException
{
 String paramName = getTaskName();

 try {
//
//    String      value   = servletConfig.getInitParameter( paramName );
//    String[]    split   = value.split( "[ ,\n\t]+" );
//    int         len     = 0;
//
//    for( int i = 0; i < split.length; i++ ) {
//        if( split[ i ].length() > 0 ) {
//            len++;
//            }
//        }
//
//    this.hosts  = new String[ len ];
//    len         = 0;
//
//    for( int i = 0; i < split.length; i++ ) {
//        if( split[ i ].length() > 0 ) {
//            this.hosts[ len++ ] = split[ i ];
//            }
//        }
//
//    logger.info( "len " +  split.length + " - " + this.hosts.length );
//
    this.hosts = servletConfig.getInitParameter( paramName ).split( "[ ,\n\t]+" );
    }
 catch( Exception e ) {
    logger.error( "paramName = " + paramName, e );

    throw new javax.servlet.ServletException(
                    "Parameter '" + paramName + "' not valid."
                    );
    }

 for( int i = 0; i < hosts.length; i++ ) {
    logger.info( paramName + " = host [" + hosts[ i ] + "] " + hosts.length );
    }
}

/**
**
*/
@Override
public void run() // ------------------------------------------------------
{
 for( String host : hosts ) {
    ping( host );
    }
}

/**
**
*/
public void ping( String host ) // ----------------------------------------
{
 try {
    InetAddress inetAddress = InetAddress.getByName( host );

    try {
        boolean isReachable = inetAddress.isReachable( 5000 );

        if( isReachable ) {
            logger.info( getTaskName() + ": " + host + " - OK " );
            }
        else {
            logger.warn( "ping " + host + " - WARN - " + inetAddress );
            }
        }
    catch( java.io.IOException e ) {
        logger.warn( "hostname=" + host + " - " + e.getMessage() );
        }

    }
 catch( java.net.UnknownHostException e ) {
    logger.warn( "hostname=" + host + " - " + e.getMessage() );
    }
}

} // class

