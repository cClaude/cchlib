package cx.ath.choisnet.tools.layout;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.googlecode.cchlib.net.WakeOnLan;

/**
 *
 * @since 1.00
 */
public class WakeOnLanLayout
{
    private static final class Computer
    {
        String hostName;
        String hostMacAddress;

        public Computer(
            final String hostName,
            final String hostMacAddress
            )
        {
            this.hostName = hostName;
            this.hostMacAddress = hostMacAddress;
        }

        public String getHostName()
        {
            return this.hostName;
        }

        public String getHostMacAddress()
        {
            return this.hostMacAddress;
        }
    }

    private final String              macAddressValue;
    private final String              macAddress;
    private final ArrayList<Computer> macAddressInfos = new ArrayList<>();
    private final String              uri;
    private Writer                    out;

    public WakeOnLanLayout(
        final ServletContext     servletContext,
        final HttpServletRequest request
        )
    {
        String requestMacAddress = request.getParameter( "MAC_ADDRESS" );
        String requestMacAddressValue;

        if( requestMacAddress != null ) {
            requestMacAddress      = requestMacAddress.trim();
            requestMacAddressValue = requestMacAddress;

            if( requestMacAddress.length() == 0 ) {
                requestMacAddress = null;
            }
        } else {
            requestMacAddressValue = "";
        }

        this.uri             = request.getRequestURI();
        this.macAddressValue = requestMacAddressValue;
        this.macAddress      = requestMacAddress;

        final String servletContextInfos = servletContext.getInitParameter( "macAddress" ).trim();
        int from = servletContextInfos.indexOf( '{' );

        while( from != -1 ) {
            final int to = servletContextInfos.indexOf( '}', ++from );
            final String item = servletContextInfos.substring( from, to );
            final String[] itemParts = item.split( "[,;]" );

            this.macAddressInfos.add( new Computer( itemParts[ 0 ], itemParts[ 1 ] ) );

            from = servletContextInfos.indexOf( '{', from );
        }
    }

    public void doDisplay( final Writer output ) throws IOException
    {
        this.out = output;

        if( this.macAddress != null ) {
            final WakeOnLan wakeOnLan = new WakeOnLan();

            wakeOnLan.notify( this.macAddress );
        }

        displayOnce( /* this.uri, */null, null );

        for( final Computer computer : this.macAddressInfos ) {
            displayOnce( /* this.uri, */computer.getHostName(), computer.getHostMacAddress() );
        }

        if( this.macAddress != null ) {
            this.out.write(
                "<br /><div class=\"h2\">\n" +
                "Requete envoyee vers\n" +
                "<br />\n" +
                this.macAddress + "</div><br />\n"
                );
        }

        this.out.flush();
    }

    private void displayOnce( final String hostName, final String hostMacAddress ) throws IOException
    {
        this.out.write(
            "<form action=\"" + this.uri + "\">\n" +
            "<table width=\"90%\" summary=\"braodcast menu\">\n" +

            "<tr>\n" +
            "<td width=\"10%\">MAC&nbsp;Address</td>\n" +
            "<td width=\"40%\">\n"
            );

        String submitValue;

        if( (hostName == null) || (hostMacAddress == null) ) {
            this.out.write(
                "<input type=\"TEXT\" name=\"MAC_ADDRESS\" value=\"" +
                        this.macAddressValue +
                        "\" size=\"17\" maxlength=\"17\" />(ie. 00:0B:DB:3D:DE:CB)\n"
                );

            submitValue = "Wake up";
        } else {
            this.out.write(
                "<input type=\"HIDDEN\" name=\"MAC_ADDRESS\" value=\"" +
                        hostMacAddress + "\" />\n"
                );

            submitValue = "Wake up: " + hostMacAddress;
        }

        this.out.write( "</td>" );

        this.out.write(
            "<td width=\"25%\">" +
            "<input type=\"SUBMIT\" value=\"" + submitValue + "\" />" +
            "</td>\n" +
            "<td width=\"25%\">" +
            ((hostName == null) ? "machine non identifiee" : hostName) +
            "</td>\n" +

            "</tr>\n" +
            "</table>\n" +

            "</form>\n"
            );
        this.out.flush();
    }
}
