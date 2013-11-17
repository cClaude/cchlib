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
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.servlets.InitServletTaskInterface;

/**
 ** 
 ** @author Claude CHOISNET
 ** @version 1.0
 */
public class PublicIPTaskUpdate implements InitServletTaskInterface
{
    private final static Logger LOGGER = Logger.getLogger( PublicIPTaskUpdate.class );

    private boolean             continueRunning = true;
    private PublicIP            publicIP;
    private DNSRequestInterface dnsRequest;

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

    @Override
    public void init( ServletConfig servletConfig ) // ------------------------
            throws ServletException
    {
        LOGGER.debug( " *****************************************" );
        LOGGER.debug( " * " + this.getClass() /* + ".init[" + initParamValue +
                                               * "]" */);
        LOGGER.debug( " *****************************************" );

        String system = null;
        String login = null;
        String password = null;
        String hostname = null;

        String initParamValue = servletConfig
                .getInitParameter( "PublicIPTaskUpdate" );
        StringTokenizer coupleST = new StringTokenizer( initParamValue, " \t\n" );

        while( coupleST.hasMoreTokens() ) {
            String couple = coupleST.nextToken();
            String coupleToLower = couple.toLowerCase();
            int pos = couple.indexOf( "=" );

            if( coupleToLower.startsWith( "system=" ) ) {
                system = removeQuotes( couple.substring( pos + 1 ) );
            } else if( coupleToLower.startsWith( "login=" ) ) {
                login = removeQuotes( couple.substring( pos + 1 ) );
            } else if( coupleToLower.startsWith( "password=" ) ) {
                password = removeQuotes( couple.substring( pos + 1 ) );
            } else if( coupleToLower.startsWith( "hostname=" ) ) {
                hostname = removeQuotes( couple.substring( pos + 1 ) );
            }
        }

        LOGGER.debug( " *****************************************" );
        LOGGER.debug( " * system   = [" + system + "]" );
        LOGGER.debug( " * login    = [" + login + "]" );
        LOGGER.debug( " * password = [" + password + "]" );
        LOGGER.debug( " * hostname = [" + hostname + "]" );
        LOGGER.debug( " *****************************************" );

        if( system == null ) {
            system = "dyndns";
        }

        try {
            this.publicIP = new PublicIP(
                    PublicIPReaderFactory.getDefaultPublicIPReader() );
            PublicIP.setOnceGlobalPublicIP( this.publicIP );

            this.dnsRequest = new DynDNSRequest( system, hostname.toString(),
                    "ON", login.toString(), password.toString() );
        }
        catch( PublicIPException e ) {
            throw new ServletException( e );
        }
        catch( NullPointerException e ) {
            throw new ServletException( "Bad init value for this task", e );
        }
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        if( continueRunning ) {

            try {
                String currentIP = this.publicIP.getCurrentPublicIP();
                String previousIP = this.publicIP.getPreviousPublicIP();

                LOGGER.debug( "IP previous: " + previousIP + " * current: "
                        + currentIP );

                if( this.publicIP.hasChange() ) {
                    log( " publicIP.hasChange{} " + previousIP + " => "
                            + currentIP );

                    continueRunning = this.dnsRequest.updateIP( currentIP );

                    LOGGER.info( "updateIP{} OK ? continueRunning "
                            + continueRunning );
                } else {
                    LOGGER.debug( " + Pas de changement d'@ IP Publique : "
                            + this.publicIP.getCurrentPublicIP() );
                }
            }
            catch( PublicIPException e ) {
                LOGGER.info( "No internet connection", e );
            }
            catch( java.io.IOException e ) {
                LOGGER.error( "Can't update IP", e );
            }
            catch( NullPointerException e ) {
                final String msg = "Erreur innatendue";

                System.err.println( msg );

                e.printStackTrace( System.err );

                LOGGER.error( msg, e );
            }
        }
    }

    @Override
    public String getTaskName() // --------------------------------------------
    {
        return "PublicIPTaskUpdate";
    }

    @Override
    public boolean continueRunning() // ---------------------------------------
    {
        return continueRunning;
    }
    
    @Override
    public void log( String message ) // --------------------------------------
    {
        LOGGER.trace( message );
    }
}
