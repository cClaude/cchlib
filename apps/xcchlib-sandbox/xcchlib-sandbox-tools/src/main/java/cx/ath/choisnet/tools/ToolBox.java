package cx.ath.choisnet.tools;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import cx.ath.choisnet.net.URLHelper;

/**
 *
 * @since 1.03
 */
public class ToolBox
{
    private ToolBox()
    {
        // All static
    }

    /*
     * Utilisé par: /private/index.jsp
     */
    public static final void addProxyView(
        final Writer output,
        final String urlStr
        ) throws IOException
    {
        final String proxyHost = "proxy.free.fr";
        final int    proxyPort = 3128;

        output.write( "<div class=\"item-full\">" );

        try {
            final URL anUrl = new URL(
                "http",    // protocol,
                proxyHost, // host name or IP of proxy server to use
                proxyPort, // proxy port or -1 to indicate the default port for the protocol
                urlStr
                );

            URLHelper.copy( anUrl, output );
        }
        catch( final IOException e ) {
            output.write( "Erreur: " + e.getMessage() + "<br />URL=" + urlStr );
        }

        output.write( "</div>" );
        output.flush();
    }
}

