package com.googlecode.cchlib.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import com.googlecode.cchlib.lang.UnsupportedSystemException;

/**
 * GetServiceByName lookup port numbers in the /etc/services file
 *
 * @author Jeffrey M. Hunter (jhunter@idevelopment.info)
 * @author http://www.idevelopment.info/
 * @author Claude CHOISNET
 * @since 4.1.7
 */
public class GetServiceByName
{
    private final File servicesFile;

    /**
     * Create an GetServiceByName using giving servicesFile
     * @param servicesFile File to use: /etc/services
     * @throws UnsupportedSystemException if servicesFile is not found
     */
    protected GetServiceByName( final File servicesFile )
        throws UnsupportedSystemException
    {
        if( servicesFile.isFile() ) {
            this.servicesFile = servicesFile;
            }
        else {
            throw new UnsupportedSystemException( "Service file not found: " + servicesFile );
            }
    }

    /**
     * Create an GetServiceByName
     *
     * @param services {@link Services} support
     * @throws UnsupportedSystemException if services system is not support
     */
    public GetServiceByName( final Services services ) throws UnsupportedSystemException
    {
       this( services.getFile() );
    }

    /**
     * Create an GetServiceByName
     *
     * @throws UnsupportedSystemException if servicesFile is not found
     */
    public GetServiceByName() throws UnsupportedSystemException
    {
       this( ServicesFactory.newServices() );
    }

    /**
     * The {@code parseServicesLine()} method is called by
     * {@code getPortNumberForTcpIpService()} to parse a non-comment line
     * in the <tt>/etc/services</tt> file and save the values.
     *
     * @param line
     *            A line to compare from the <tt>/etc/services</tt> file.
     *
     * @param tcpipService
     *            The name of a TCP/IP "well-known" service found in the
     *            <tt>/etc/services</tt> file
     *
     * @param tcpipClass
     *            Either "tcp" or "udp", depending on the TCP/IP service
     *            desired.
     *
     * @return A port number for a TCP or UDP service (depending on tcpipClass).
     *         Return -1 on error.
     */
    private static int parseServicesLine(
            final String line,
            final String tcpipService,
            final String tcpipClass
            )
    {
        // Parse line
        final StringTokenizer st = new StringTokenizer( line, " \t/#" );

        // First get the name on the line (parameter 1):
        if( !st.hasMoreTokens() ) {
            return -1; // error
            }
        final String name = st.nextToken().trim();

        // Next get the service name on the line (parameter 2):
        if( !st.hasMoreTokens() ) {
            return -1; // error
            }
        final String portValue = st.nextToken().trim();

        // Finally get the class on the line (parameter 3):
        if( !st.hasMoreTokens() ) {
            return -1; // error
            }
        final String classValue = st.nextToken().trim();

        // Class doesn't match--reject:
        if( !classValue.equals( tcpipClass ) ) {
            return -1; // error
            }

        // Return port number, if name on this line matches:
        if( name.equals( tcpipService ) ) {
            try { // Convert the port number string to integer
                return Integer.parseInt( portValue );
                }
            catch( final NumberFormatException nfe ) {
                // Ignore corrupt /etc/services lines:
                return -1; // error
                }
            }
        else {
            return -1; // no match
            }
    }

    /**
     * The {@code getServiceByName()} method Search the /etc/services file
     * for a service name and class. Return the port number.
     *
     * @param ipService
     *            The name of a TCP/IP "well-known" service found in the
     *            <tt>/etc/services</tt> file
     *
     * @param ipClassName
     *            Either "tcp" or "udp", depending on the TCP/IP service
     *            desired.
     *
     * @return A port number for a TCP or UDP service (depending on tcpipClass).
     *         Return -1 on error.
     * @throws GetServiceByNameException if any Exception occur while
     *         reading /etc/services file (typically limited access)
     * @throws IllegalArgumentException if ipClassName is not valid
     * @see #getServiceByName(String, Protocole)
     */
    public int getServiceByName(
        final String ipService,
        final String ipClassName
        )
        throws GetServiceByNameException
    {
        return getServiceByName( ipService, Protocole.valueOf( ipClassName ) );
    }

    /**
     * The {@code getServiceByName()} method Search the /etc/services file
     * for a service name and class. Return the port number.
     * <p>
     * For example, given this line in <tt>/etc/services</tt>,
     *
     * <pre>
     * httpd        80/tcp
     * </pre>
     *
     * In this example, a search for service "httpd" and class "tcp" will
     * return 80.
     *
     * @param ipService The name of a TCP/IP "well-known" service found
     *                  in the <tt>/etc/services</tt> file
     *
     * @param ipClass An {@link Protocole} either tcp or udp, depending
     *                on the TCP/IP service desired.
     * @return A port number for a TCP or UDP service (depending on tcpipClass).
     *         Return -1 on error.
     * @throws GetServiceByNameException if any Exception occur while
     *         reading /etc/services file (typically limited access)
      */
    public int getServiceByName(
            final String    ipService,
            final Protocole ipClass
            )
            throws GetServiceByNameException
    {
        try( final InputStream    fis = new FileInputStream( this.servicesFile );
             final BufferedReader br  = newBufferedReader( fis )
             ) {
            int port = -1;

            // Read /etc/services file.
            // Skip comments and empty lines.
            String line;

            while( ((line = br.readLine()) != null) && (port == -1) ) {
                line = line.trim();

                if( (line.length() != 0) && (line.charAt( 0 ) != '#') ) {
                    port = parseServicesLine( line, ipService, ipClass.name() );
                    }
                }

            return port;
            }
        catch( final Exception e ) {
            throw new GetServiceByNameException( e );
            }
    }

    private BufferedReader newBufferedReader( final InputStream inStream )
    {
        return new BufferedReader( new InputStreamReader( inStream ) );
    }
}
