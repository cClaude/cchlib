/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/tools/layout/WakeOnLanLayout.java
** Description   :
** Encodage      : ANSI
**
**  1.00.000 yyyy.mm.dd Claude CHOISNET  - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.layout.WakeOnLanLayout
**
*/
package cx.ath.choisnet.tools.layout;

// import javax.servlet.ServletException;
//import cx.ath.choisnet.net.WakeOnLan;
import java.io.Writer;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
// import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import cx.ath.choisnet.net.WakeOnLan;

/**
**
** @author Claude CHOISNET
** @version 1.0
** @since   1.00
*/
public class WakeOnLanLayout
{
/** */
private final String macAddressValue;

/** */
private final String macAddress;

/** */
private final ArrayList<Computer> macAddressInfos = new  ArrayList<Computer>();

/** */
private final String uri;

/** */
private Writer out;

/**
**
*/
public WakeOnLanLayout( // ------------------------------------------------
    final ServletContext     servletContext,
    final HttpServletRequest request
    )
{
 String macAddress  = request.getParameter( "MAC_ADDRESS" );
 String macAddressValue;

 if( macAddress != null ) {
    macAddress        = macAddress.trim();
    macAddressValue   = macAddress;

    if( macAddress.length() == 0 ) {
        macAddress = null;
        }
    }
 else {
    macAddressValue  = "";
    }

 this.uri               = request.getRequestURI();
 this.macAddressValue   = macAddressValue;
 this.macAddress        = macAddress;
// this.macAddressInfos   = macAddress;

 final String   servletContextInfos = servletContext.getInitParameter( "macAddress" ).trim();
 int            from                = servletContextInfos.indexOf( '{' );

 while( from != -1 ) {
    int         to          = servletContextInfos.indexOf( '}', ++from );
    String      item        = servletContextInfos.substring( from, to );
    String[]    itemParts   = item.split( "[,;]" );

    this.macAddressInfos.add(
        new Computer( itemParts[ 0 ], itemParts[ 1 ] )
        );

    from = servletContextInfos.indexOf( '{', from );
    }

}

/**
**
*/
public void doDisplay( final Writer output ) // ---------------------------
    throws java.io.IOException
{
 this.out = output;

 if( this.macAddress != null ) {
    WakeOnLan wakeOnLan = new WakeOnLan();

    wakeOnLan.notify( this.macAddress );
    }

 displayOnce( /*this.uri,*/ null, null );

 for( Computer computer : this.macAddressInfos ) {
    displayOnce( /*this.uri,*/ computer.getHostName(), computer.getHostMacAddress() );
    }

 if( this.macAddress != null ) {
    out.write( "<br />\n" );
    out.write( "<div class=\"h2\">\n" );
    out.write( "Requete envoyee vers\n" );
    out.write( "<br />\n" );
    out.write( this.macAddress );
    out.write( "</div>\n" );
    out.write( "<br />\n" );
    }

 out.flush();
}

/**
**
*/
public void displayOnce( // -----------------------------------------------
//    final String    formURL,
    final String    hostName,
    final String    hostMacAddress
    )
    throws java.io.IOException
{
 out.write( "<form action=\"" + this.uri + "\">\n" );
 out.write( "<table width=\"90%\" summary=\"braodcast menu\">\n" );

 out.write( "<tr>\n" );
 out.write( "<td width=\"10%\">MAC&nbsp;Address</td>\n" );
 out.write( "<td width=\"40%\">\n" );

 String submitValue;

 if( (hostName == null) || (hostMacAddress == null) ) {
    out.write( "<input type=\"TEXT\" name=\"MAC_ADDRESS\" value=\"" + this.macAddressValue + "\" size=\"17\" maxlength=\"17\" />" );
    out.write( "(ex: 00:0B:DB:3D:DE:CB)\n" );

    submitValue = "Reveiller";
    }
 else {
    out.write( "<input type=\"HIDDEN\" name=\"MAC_ADDRESS\" value=\"" + hostMacAddress + "\" />\n" );

    submitValue = "Reveiller: " + hostMacAddress;
    }

 out.write( "</td>\n" );

 out.write( "<td width=\"25%\">" );
 out.write( "<input type=\"SUBMIT\" value=\"" + submitValue + "\" />" );
 out.write( "</td>\n" )
 ;
 out.write( "<td width=\"25%\">" );
 out.write( (hostName == null) ? "machine non identifiee" : hostName);
 out.write( "</td>\n" );

 out.write( "</tr>\n" );

 out.write( "</table>\n" );

 out.write( "</form>\n" );
 out.flush();
}

/**
**
*/
private class Computer
{
    String hostName;
    String hostMacAddress;

    public Computer( // ---------------------------------------------------
        String hostName,
        String hostMacAddress
        )
    {
        this.hostName       = hostName;
        this.hostMacAddress = hostMacAddress;
    }

    public String getHostName() // ----------------------------------------
    {
        return this.hostName;
    }

    public String getHostMacAddress() // ----------------------------------
    {
        return this.hostMacAddress;
    }
}

} // class
