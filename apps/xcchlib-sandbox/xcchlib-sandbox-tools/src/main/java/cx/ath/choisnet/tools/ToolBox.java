/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/tools/ToolBox.java
** Description   :
**
**  1.03.001 2006.09.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.ToolBox
**
*/
package cx.ath.choisnet.tools;

// import javax.servlet.ServletConfig;
// import javax.servlet.ServletException;
import cx.ath.choisnet.net.URLHelper;
import java.io.Writer;

/**
**
** @author  Claude CHOISNET
** @version 1.03.001
** @since   1.03.001
*/
public class ToolBox
{
/** ALL static */
private ToolBox() {}

/**
** Utilisé par: /private/index.jsp
*/
public static final void addProxyView( // ---------------------------------
    final Writer output,
    final String urlStr
    )
  throws java.io.IOException
{
 final String proxyHost = "proxy.free.fr";
 final int    proxyPort = 3128;

 output.write( "<div class=\"item-full\">" );

 try {
    final java.net.URL anUrl = new java.net.URL(
            "http"    , // protocol,
            proxyHost , // host name or IP of proxy server to use
            proxyPort , // proxy port or -1 to indicate the default port for the protocol
            urlStr
            );

    URLHelper.copy( anUrl, output );
    }
 catch( java.io.IOException e ) {
    output.write( "Erreur: " + e.getMessage() + "<br />URL=" + urlStr );
    }

 output.write( "</div>" );
 output.flush();
}

} // class

