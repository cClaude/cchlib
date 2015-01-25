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
            final int end = str.length() - 1;

            if( str.charAt( end ) == '"' ) {
                return str.substring( 1, end );
            }
        }

        return str;
    }

    @Override
    public void init( final ServletConfig servletConfig ) // ------------------------
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

        final String initParamValue = servletConfig
                .getInitParameter( "PublicIPTaskUpdate" );
        final StringTokenizer coupleST = new StringTokenizer( initParamValue, " \t\n" );

        while( coupleST.hasMoreTokens() ) {
            final String couple = coupleST.nextToken();
            final String coupleToLower = couple.toLowerCase();
            final int pos = couple.indexOf( "=" );

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

        if( hostname == null ) {
            throw new ServletException( "Invalid value for hostname : null" );
        }
        if( login == null ) {
            throw new ServletException( "Invalid value for login : null" );
        }
        if( password == null ) {
            throw new ServletException( "Invalid value for password : null" );
        }

        try {
            this.publicIP = new PublicIP(
                    PublicIPReaderFactory.getDefaultPublicIPReader() );
            PublicIP.setOnceGlobalPublicIP( this.publicIP );

            this.dnsRequest = new DynDNSRequest( system, hostname, "ON", login, password );
        }
        catch( final PublicIPException e ) {
            throw new ServletException( e );
        }
        catch( final NullPointerException e ) {
            throw new ServletException( "Bad init value for this task", e );
        }
    }

    @Override
    public void run() // ------------------------------------------------------
    {
        if( this.continueRunning ) {

            try {
                final String currentIP = this.publicIP.getCurrentPublicIP();
                final String previousIP = this.publicIP.getPreviousPublicIP();

                LOGGER.debug( "IP previous: " + previousIP + " * current: "
                        + currentIP );

                if( this.publicIP.hasChange() ) {
                    log( " publicIP.hasChange{} " + previousIP + " => "
                            + currentIP );

                    this.continueRunning = this.dnsRequest.updateIP( currentIP );

                    LOGGER.info( "updateIP{} OK ? continueRunning "
                            + this.continueRunning );
                } else {
                    LOGGER.debug( " + Pas de changement d'@ IP Publique : "
                            + this.publicIP.getCurrentPublicIP() );
                }
            }
            catch( final PublicIPException e ) {
                LOGGER.info( "No internet connection", e );
            }
            catch( final java.io.IOException e ) {
                LOGGER.error( "Can't update IP", e );
            }
            catch( final NullPointerException e ) {
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
        return this.continueRunning;
    }

    @Override
    public void log( final String message ) // --------------------------------------
    {
        LOGGER.trace( message );
    }
}
