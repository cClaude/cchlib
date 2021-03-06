package cx.ath.choisnet.dns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.util.base64.Base64Encoder;

/**
 *
 * @since 1.0
 */
public class DynDNSRequest implements DNSRequestInterface
{
    private static final Logger LOGGER = Logger.getLogger( DynDNSRequest.class );

    /** statdns|dyndns */
    private final String                     system;

    /** yourhost.ourdomain.ext,yourhost2.dyndns.org */
    private final String                     hostnameList;

    private final int                        hostCount;

    /** ON|OFF|NOCHG */
    private final String                     wildcard;

    /** Encode64( username:pass ) */
    private final String                     usernamePass;

    /** Keep log of IP in this file */
    private final File                       traceFile;

    private final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSSZ" );

    public DynDNSRequest(
        final File   traceFile,
        final String system,
        final String hostnameList,
        final String wildcard,
        final String username,
        final String password
        )
    {
        this.traceFile      = traceFile;
        this.system         = system;
        this.hostnameList   = hostnameList;
        this.wildcard       = wildcard;
        this.usernamePass   = encodeBase64( username + ":" + password );
        this.hostCount      = hostnameList.split( "," ).length;

        LOGGER.info( " **INIT***********************************" );
        LOGGER.info( " * traceFile      = [" + traceFile + "]" );
        LOGGER.info( " * system         = [" + system + "]" );
        LOGGER.info( " * hostnameList   = [" + hostnameList + "]" );
        LOGGER.info( " * hostCount      = [" + this.hostCount + "]" );
        LOGGER.info( " * wildcard       = [" + wildcard + "]" );
        LOGGER.info( " * username       = [" + username + "]" );
        LOGGER.info( " * password       = [" + password + "]" );
        LOGGER.info( " *****************************************" );
    }

    public DynDNSRequest(
        final String system,
        final String hostnameList,
        final String wildcard,
        final String username,
        final String password
        )
    {
        this(
            FileHelper.getUserHomeDirectoryFile( DynDNSRequest.class.getName() + ".log" ),
            system,
            hostnameList,
            wildcard,
            username,
            password
            );
    }

    protected static String encodeBase64( final String str ) // ---------------------
    {
        try {
            return Base64Encoder.encode( str );
        }
        catch( final UnsupportedEncodingException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override
    @SuppressWarnings({"squid:S1160","squid:RedundantThrowsDeclarationCheck"})
    public InputStream getInputStream( final String ip ) // -------------------------
            throws MalformedURLException, IOException
    {
        final java.net.URL url;
        final StringBuilder sb = new StringBuilder();

        try {
            sb.append( "http://members.dyndns.org/nic/update?" );
            sb.append( "system=" + this.system );
            sb.append( '&' );
            sb.append( "hostname=" + this.hostnameList );
            sb.append( '&' );
            sb.append( "myip=" + ip );
            sb.append( '&' );
            sb.append( "wildcard=" + this.wildcard );
            // sb.append( "&" );
            // sb.append( "mx=mail.exchanger.ext&" );
            // sb.append( "&" );
            // sb.append( "backmx=NO&" );
            // sb.append( "&" );
            // sb.append( "offline=NO" );

            LOGGER.debug( " URL = [" + sb + "]" );

            url = new URL( sb.toString() );

            final java.net.URLConnection conn = url.openConnection();

            conn.setDoInput( true );
            conn.setRequestProperty( "Authorization", "Basic " + this.usernamePass );
            conn.setRequestProperty( "User-Agent", this.getClass().getName() + "/0.10 " );
            conn.connect();

            return conn.getInputStream();
        }
        catch( final MalformedURLException e ) {
            LOGGER.error( "MalformedURLException [" + sb + "]" );

            throw e;
        }
    }

    @Override
    public boolean updateIP( final String ip ) // -----------------------------------
    {
        try {
            final String[] results = privateUpdateIP( getInputStream( ip ) );

            logUpdateIP( results );

            final boolean result = checkReturnCode( results, this.hostCount );

            if( this.traceFile != null ) {
                updateTraceFile( this.traceFile, ip, result );
            }

            return result;
        }
        catch( final Exception e ) {
            LOGGER.fatal( "Error while update IP to : " + ip, e );

            return false;
        }
    }

    private void updateTraceFile( final File traceFile, final String ip , final boolean result  )
    {

        final String traceMsg = "[" + result + "]"
                + ip + " - "
                + this.dateFormat.format( new java.util.Date() );

        try( final Writer writer = new FileWriter( traceFile, true ) ) {
            writer.write( traceMsg + "\n" );
            writer.flush();
        }
        catch( final IOException e ) {
            LOGGER.warn( "Can not update : " + traceFile + " : " + traceMsg, e );
        }
    }

    private void logUpdateIP( final String[] results )
    {
        LOGGER.info( "----- updateIP result -----" );

        for( int i = 0; i < results.length; i++ ) {
            LOGGER.info( "(" + i + "):" + results[ i ] );
        }

        LOGGER.info( "------------------" );
    }

    /*
     ** http://resolute.ucsd.edu/~diwaker/articles/java-proxy.html
     */
    public String[] privateUpdateIP( final InputStream inputStream ) // ------------
            throws IOException
    {
        final LinkedList<String> list = new LinkedList<>();

        try (final BufferedReader br = new BufferedReader( new InputStreamReader( inputStream ) )) {
            String line;

            while( (line = br.readLine()) != null ) {
                list.add( line );
            }
        }

        final String[] results = new String[list.size()];

        return list.toArray( results );
    }

    /*
     * Update Syntax Errors:
     *
     * The codes below should never be returned except when developing a client.
     * They indicate a serious problem with the syntax of the update sent by the
     * client.
     *
     * badsys
     *      The sycodestem parameter given is not valid. Valid system parameters
     *      are dyndns, statdns and custom.
     *
     * badagent
     *      The user agent that was sent has been blocked for not following these
     *      specifications or no user agent was specified.
     *
     * Account-Related Errors:
     *
     * The codes below indicate that the client is not configured currently for
     * the user's account. These return codes are given just once.
     *
     * badauth
     *      The username or password specified are incorrect.
     *
     * !donator
     *      An option available only to credited users (such as offline URL) was
     *      specified, but the user is not a credited user. If multiple hosts
     *      were specified, only a single !donator will be returned.
     *
     * Update Complete:
     *
     * The codes below indicate that the update of a hostname was completed
     * successfully.
     *
     * good
     *      The update was successful, and the hostname is now updated.
     *
     * nochg
     *      The update changed no settings, and is considered abusive.
     *      Additional nochg updates will cause the hostname to become
     *      blocked.
     *
     * Note that, for confirmation purposes, good and nochg messages will be
     * followed by the IP address that the hostname was updated to. This value
     * will be separated from the return code by a space.
     *
     * Hostname-Related Errors:
     *
     * The codes below indicate a problem with a specific hostname. The client
     * should stop updating that hostname until the user confirms that the
     * problem has been resolved.
     *
     * notfqdn
     *      The hostname specified is not a fully-qualified domain name (not
     *      in the form hostname.dyndns.org or domain.com).
     *
     * nohost
     *      The hostname specified does not exist (or is not in the service
     *      specified in the system parameter)
     *
     * !yours
     *      The hostname specified exists, but not under the username specified.
     *
     * abuse
     *      The hostname specified is blocked for update abuse.
     *
     * Note that, if no hostnames were specified, notfqdn will be returned once.
     * Server Error Conditions
     *
     * The codes below indicate server errors that will have to be investigated.
     * The client should stop and ask the user to contact support.
     *
     * numhost
     *      Too many or too few hosts found
     *
     * dnserr
     *      DNS error encountered
     *      The return dnserr will be followed by a numeric packet ID which should
     *      be reported to the support department along with the error. Emergency
     *      Conditions
     *
     * 911
     *      There is a serious problem on our side, such as a database or DNS
     *      server failure. The client should stop updating until notified via
     *      the status page that the service is back up.
     *
     */
    public static boolean checkReturnCode( // ---------------------------------
            final String[] results,
            final int      hostCount
            )
    {
        if( results.length == 0 ) {
            return false;
        } else {
            int goodCount = 0;

            for( int i = 0; i < results.length; i++ ) {
                final String str = results[ i ];

                if( str.startsWith( "good " ) ) {
                    // ok
                    goodCount++;
                }
            }

            return goodCount == hostCount;
        }
    }
}
