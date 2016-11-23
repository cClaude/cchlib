/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/dns/PublicIPReaderFactory.java
 ** Description   :
 **
 **  1.00 2005.09.25 Claude CHOISNET - Version initiale
 **  1.02 2006.04.06 Claude CHOISNET
 **                  Reprise de la gestion des logs
 **                  Ajout de getCurrentPublicIP(PublicIPReader)
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.dns.PublicIPReaderFactory
 **
 */
package cx.ath.choisnet.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 **
 ** @author Claude CHOISNET
 ** @version 1.02
 */
public class PublicIPReaderFactory
{
    /** Service par defaut : http://myip.dtdns.com/ */
    public static final URL  PUBLIC_DEFAULT_IP_LOCATOR_URL = buildURL( "http://myip.dtdns.com/" );

    /** Sauvegarde par defaut : C:/Tomcat-Tools.PublicIP */
    public static final File PUBLIC_DEFAULT_IP_FILE        = new File( "C:/PreviousPublicIP" );

    private PublicIPReaderFactory()
    {
        // All static
    }

    public static PublicIPReader getDefaultPublicIPReader() // ----------------
    {
        return new PublicIPReader() {
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            /** serialVersionUID */
            private static final long serialVersionUID = 2L;

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            @Override
            public String getPreviousPublicIP() // - - - - - - - - - - - - - - -
                    throws PublicIPException
            {
                try {
                    return getIP( new FileReader( PUBLIC_DEFAULT_IP_FILE ) );
                }
                catch( final java.io.IOException e ) {
                    getLogger().warn( "Can't read from " + PUBLIC_DEFAULT_IP_FILE );
                }

                try {
                    store( "0.0.0.0" );

                    return getIP( new FileReader( PUBLIC_DEFAULT_IP_FILE ) );
                }
                catch( final java.io.IOException e ) {
                    final String msg = "Can't read from " + PUBLIC_DEFAULT_IP_FILE;

                    getLogger().fatal( msg );

                    throw new PublicIPException( msg, e );
                }

            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            @Override
            public String getCurrentPublicIP() // - - - - - - - - - - - - - - -
                    throws PublicIPException
            {
                String ip;

                try {
                    ip = getIP( new InputStreamReader( PUBLIC_DEFAULT_IP_LOCATOR_URL.openStream() ) );
                }
                catch( final ConnectException e ) {
                    //
                    // Pas de connexion internet ?
                    //
                    final String msg = "Can't read from " + PUBLIC_DEFAULT_IP_LOCATOR_URL + " (no connection)";

                    getLogger().warn( msg );

                    throw new PublicIPException( msg, e );
                }
                catch( final IOException e ) {
                    final String msg = "Can't read from " + PUBLIC_DEFAULT_IP_LOCATOR_URL;

                    getLogger().warn( msg );

                    throw new PublicIPException( msg, e );
                }

                return ip;
            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            String getIP( final Reader reader ) // - - - - - - - - - - - - - - -
                    throws IOException
            {
                final StringBuilder sb = new StringBuilder();

                try (final BufferedReader br = new BufferedReader( reader )) {
                    String line;

                    while( (line = br.readLine()) != null ) {
                        sb.append( line + "\n" );
                    }
                }

                final String[] parts = sb.toString().split( "(\n|\t| |:)" );

                return parts[ 0 ];
            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            @Override
            public void storePublicIP() // - - - - - - - - - - - - - - - - - - -
                    throws PublicIPException
            {
                final String currentIP = getCurrentPublicIP();

                try {
                    store( currentIP );
                }
                catch( final java.io.IOException e ) {
                    getLogger().warn( "store( " + currentIP + " )", e );

                    throw new PublicIPException( "store( " + currentIP + " )", e );
                }
            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            private void store( final String ip ) // - - - - - - - - - - - - - - - - -
                    throws IOException
            {
                try (final Writer writer = new FileWriter( PUBLIC_DEFAULT_IP_FILE )) {
                    writer.write( ip + " " + new java.util.Date() );
                    writer.flush();
                }
            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            private org.apache.commons.logging.Log getLogger() // - - - - - - -
            {
                return PublicIPReaderFactory.getLogger( this.getClass() );
            }
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        };
    }

    /**
     ** @return l'@ IP publique ou null si elle n'a pas pu etre determinee
     **
     ** @since 1.02
     */
    public static String getCurrentPublicIP( // -------------------------------
            final PublicIPReader publicIPReader )
    {
        try {
            return publicIPReader.getCurrentPublicIP();
        }
        catch( final PublicIPException e ) {
            return null;
        }
        catch( final Exception e ) {
            getLogger( PublicIPReaderFactory.class ).fatal( "getDefaultPublicIPReader(" + publicIPReader + ")", e );

            return null;
        }
    }

    /**
     ** @return l'@ IP publique ou un message precisant l'erreur si elle n'a pas pu etre determinee
     **
     ** @since 1.02
     */
    public static String getCurrentPublicIPAsMessage( // ----------------------
            final PublicIPReader publicIPReader )
    {
        try {
            return publicIPReader.getCurrentPublicIP();
        }
        catch( final PublicIPException e ) {
            return e.getMessage();
        }
        catch( final Exception e ) {
            getLogger( PublicIPReaderFactory.class ).fatal( "getDefaultPublicIPReader(" + publicIPReader + ")", e );

            return e.toString();
        }
    }

    protected static final URL buildURL( final String url ) // ----------------
    {
        try {
            return new URL( url );
        }
        catch( final MalformedURLException e ) {
            getLogger( PublicIPReaderFactory.class ).fatal( "buildURLFromString(" + url + ")", e );

            return null;
        }
    }

    public static org.apache.commons.logging.Log getLogger( // ----------------
            final Class<?> clazz )
    {
        return org.apache.commons.logging.LogFactory.getLog( clazz );
    }
}

