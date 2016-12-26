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
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;

/**
 *
 * @since 1.02
 */
public class PublicIPReaderFactory
{
    private static final class PublicIPReaderImpl implements PublicIPReader
    {
        private static final long serialVersionUID = 2L;

        private final File ipFile;

        public PublicIPReaderImpl( final File ipFile )
        {
            this.ipFile = ipFile;
        }

        @Override
        public String getPreviousPublicIP() throws PublicIPException
        {
            try {
                return getIP( new FileReader( this.ipFile ) );
            }
            catch( final IOException e ) {
               final String message = getMessage( this.ipFile );

               if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( message, e );
                } else {
                    LOGGER.warn( message );
                }
            }

            try {
                store( "0.0.0.0" );

                return getIP( new FileReader( this.ipFile ) );
            }
            catch( final java.io.IOException e ) {
                final String message = getMessage( this.ipFile );

                LOGGER.fatal( message );

                throw new PublicIPException( message, e );
            }

        }

        private static String getMessage( final File file )
        {
            return "Can't read from " + file;
        }

        @Override
        public String getCurrentPublicIP() throws PublicIPException
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

                LOGGER.warn( msg );

                throw new PublicIPException( msg, e );
            }
            catch( final IOException e ) {
                final String msg = "Can't read from " + PUBLIC_DEFAULT_IP_LOCATOR_URL;

                LOGGER.warn( msg );

                throw new PublicIPException( msg, e );
            }

            return ip;
        }

        private String getIP( final Reader reader ) throws IOException
        {
            final StringBuilder sb = new StringBuilder();

            try( final BufferedReader br = new BufferedReader( reader ) ) {
                String line;

                while( (line = br.readLine()) != null ) {
                    sb.append( line + "\n" );
                }
            }

            final String[] parts = sb.toString().split( "(\n|\t| |:)" );

            return parts[ 0 ];
        }

        @Override
        public void storePublicIP() throws PublicIPException
        {
            final String currentIP = getCurrentPublicIP();

            try {
                store( currentIP );
            }
            catch( final IOException e ) {
                LOGGER.warn( "store( " + currentIP + " )", e );

                throw new PublicIPException( "store( " + currentIP + " )", e );
            }
        }

        private void store( final String ip ) throws IOException
        {
            try( final Writer writer = new FileWriter( this.ipFile ) ) {
                writer.write( ip + " " + new java.util.Date() );
                writer.flush();
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger( PublicIPReaderFactory.class );

    /** Service par defaut : http://myip.dtdns.com/ */
    public static final URL  PUBLIC_DEFAULT_IP_LOCATOR_URL = buildURL( "http://myip.dtdns.com/" );

    public static final File PUBLIC_DEFAULT_IP_FILE
            = FileHelper.getUserConfigDirectoryFile( "PreviousPublicIP" );

    private PublicIPReaderFactory()
    {
        // All static
    }

    public static PublicIPReader getDefaultPublicIPReader()
    {
        return new PublicIPReaderImpl( PUBLIC_DEFAULT_IP_FILE );
    }

    /*
     * @return l'@ IP publique ou null si elle n'a pas pu etre determinee
     */
    public static String getCurrentPublicIP(
        final PublicIPReader publicIPReader
        )
    {
        try {
            return publicIPReader.getCurrentPublicIP();
        }
        catch( final PublicIPException e ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( publicIPReader, e );
            }
            return null;
        }
        catch( final Exception e ) {
            LOGGER.fatal( "getDefaultPublicIPReader(" + publicIPReader + ")", e );

            return null;
        }
    }

    /*
     * @return l'@ IP publique ou un message precisant l'erreur si elle n'a pas pu etre determinee
     */
    public static String getCurrentPublicIPAsMessage(
        final PublicIPReader publicIPReader
        )
    {
        try {
            return publicIPReader.getCurrentPublicIP();
        }
        catch( final PublicIPException e ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( publicIPReader, e );
            }
            return e.getMessage();
        }
        catch( final Exception e ) {
            LOGGER.fatal( "getDefaultPublicIPReader(" + publicIPReader + ")", e );

            return e.toString();
        }
    }

    protected static final URL buildURL( final String url )
    {
        try {
            return new URL( url );
        }
        catch( final MalformedURLException e ) {
            LOGGER.fatal( "buildURLFromString(" + url + ")", e );

            return null;
        }
    }
}

