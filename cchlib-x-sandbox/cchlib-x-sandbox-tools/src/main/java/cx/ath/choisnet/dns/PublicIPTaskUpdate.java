/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/dns/PublicIPTaskUpdate.java
** Description   :
**
** 1.00 2005.02.27 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.dns.PublicIPTaskUpdate
**
*/
package cx.ath.choisnet.dns;

import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public class PublicIPTaskUpdate
    implements
        cx.ath.choisnet.tools.servlets.InitServletTaskInterface

{
/**
** Gestion des traces
*/
final org.apache.commons.logging.Log logger
    = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/** */
private boolean continueRunning = true;

/** */
private PublicIP publicIP;

/** */
private DNSRequestInterface dnsRequest;

/**
**
*/
private static String removeQuotes( final String str ) // -----------------
{
 if( str.charAt( 0 ) == '"' ) {
    int end = str.length() - 1;

    if( str.charAt( end ) == '"' ) {
        return str.substring( 1, end );
        }
    }

 return str;
}
/**
**
*/
public void init( ServletConfig servletConfig ) // ------------------------
    throws ServletException
{
 logger.debug( " *****************************************" );
 logger.debug( " * " + this.getClass() /* + ".init[" + initParamValue + "]" */ );
 logger.debug( " *****************************************" );

 String system      = null;
 String login       = null;
 String password    = null;
 String hostname    = null;

 String          initParamValue = servletConfig.getInitParameter( "PublicIPTaskUpdate" );
 StringTokenizer coupleST       = new StringTokenizer( initParamValue, " \t\n" );

 while( coupleST.hasMoreTokens() ) {
    String  couple          = coupleST.nextToken();
    String  coupleToLower   = couple.toLowerCase();
    int     pos             = couple.indexOf( "=" );

    if( coupleToLower.startsWith( "system=" ) ) {
        system = removeQuotes( couple.substring( pos + 1 ) );
        }
    else if( coupleToLower.startsWith( "login=" ) ) {
        login = removeQuotes( couple.substring( pos + 1 ) );
        }
    else if( coupleToLower.startsWith( "password=" ) ) {
        password = removeQuotes( couple.substring( pos + 1 ) );
        }
    else if( coupleToLower.startsWith( "hostname=" ) ) {
        hostname = removeQuotes( couple.substring( pos + 1 ) );
        }
    }

 logger.debug( " *****************************************" );
 logger.debug( " * system   = [" + system + "]" );
 logger.debug( " * login    = [" + login + "]" );
 logger.debug( " * password = [" + password + "]" );
 logger.debug( " * hostname = [" + hostname + "]" );
 logger.debug( " *****************************************" );

 if( system == null ) {
    system = "dyndns";
    }

 try {
    this.publicIP = new PublicIP(
                            PublicIPReaderFactory.getDefaultPublicIPReader()
                            );
    PublicIP.setOnceGlobalPublicIP( this.publicIP );

    this.dnsRequest = new DynDNSRequest(
                            system,
                            hostname.toString(),
                            "ON",
                            login.toString(),
                            password.toString()
                            );
    }
 catch( PublicIPException e ) {
    throw new ServletException( e );
    }
 catch( NullPointerException e ) {
    throw new ServletException( "Bad init value for this task", e );
    }
}

/**
**
*/
public void run() // ------------------------------------------------------
{
 if( continueRunning ) {
    // System.out.println( " *****************************************" );
    // System.out.println( " * " + this.getClass() + ".run()" );
    // System.out.println( " *****************************************" );

    try {
        String currentIP    = this.publicIP.getCurrentPublicIP();
        String previousIP   = this.publicIP.getPreviousPublicIP();

        logger.debug( "IP previous: "
                                + previousIP
                                + " * current: "
                                + currentIP
                                );

        if( this.publicIP.hasChange() ) {
            // String currentIP    = this.publicIP.getCurrentPublicIP();
            // String previousIP   = this.publicIP.getPreviousPublicIP();

            log( " publicIP.hasChange{} "
                                + previousIP
                                + " => "
                                + currentIP
                                );

            // String request = this.dnsRequest.getHTTPRequest( ipAddress );
            // System.out.println( request );

            continueRunning = this.dnsRequest.updateIP( currentIP );

            logger.info( "updateIP{} OK ? continueRunning " + continueRunning );
            }
        else {
            logger.debug( " + Pas de changement d'@ IP Publique : " + this.publicIP.getCurrentPublicIP() );
            }
        }
    catch( PublicIPException e ) {
        logger.info( "No internet connection", e );
        }
    catch( java.io.IOException e ) {
        logger.error( "Can't update IP", e );
        }
    catch( NullPointerException e ) {
        final String msg = "Erreur innatendue";

        System.err.println( msg );

        e.printStackTrace( System.err );

        logger.error( msg, e );
        }
    }
}


/**
**
*/
public String getTaskName() // --------------------------------------------
{
 return "PublicIPTaskUpdate";
}

/**
**
*/
public boolean continueRunning() // ---------------------------------------
{
 return continueRunning;
}

/**
**
*/
public void log( String message ) // --------------------------------------
{
 logger.trace( message );
}

} // class

/**
** <p>PublicIPBackupManagerInterface</p>
**
** return une chaîne contenant l'adresse IP sauvegardée ou si elle n'existe
**        pas retourne la chaîne  "0.0.0.0"
public String load() // ---------------------------------------------------
    throws PublicIPException
{
 try {
    return PublicIP.getIP( new FileReader( ipFile ) );
    }
 catch( java.io.IOException e ) {
    logger.fatal( "load() : File \"" + ipFile + "\"", e );

    return "0.0.0.0";
    }

}
*/



/**
** PublicIPBackupManagerInterface
public void store( String ip ) // -----------------------------------------
    throws PublicIPException
{
 if( ip == null ) {
    throw new PublicIPException( "IP is NULL" );
    }
 else {
    try {
        BufferedWriter writer = new BufferedWriter( new FileWriter( ipFile ) );

        try {
            writer.write( ip + " " + new java.util.Date() );
            }
        finally {
            writer.close();
            }
        }
    catch( java.io.IOException e ) {
        logger.fatal( "store() : File \"" + ipFile + "\"", e );

        throw new PublicIPException( e );
        }
    }
}
*/


/*
public String getIP( String name )
{
 java.util.Hashtable env = new java.util.Hashtable();

 env.put(
        Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.dnc.DnsContextFactory"
        );
 env.put(
    Context.PROVIDER_URL,
    "localhost:1099"
    );
Context initialContext = new InitialContext(env);

 return "";
}

/*
public static void main(String[] args)
    {
    try {
    Hashtable env = new Hashtable();
    env.put("java.naming.factory.initial",
        "com.sun.jndi.dns.DnsContextFactory");
    env.put("java.naming.provider.url",
        "dns://saphir.lidil.univ-mrs.fr/univ-mrs.fr");

    Context ictx = new InitialContext(env);
    NamingEnumeration e = ictx.listBindings(args[0]);
    while (e.hasMore())
        {
        Binding b = (Binding) e.next();
        System.out.println("name: " + b.getName());
        System.out.println("object: " + b.getObject());
        }
    }
    catch (javax.naming.NamingException e) {
        System.err.println("Exception " + e);
        }
    }
*/
