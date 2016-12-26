package cx.ath.choisnet.dns;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 *
 * @since 1.0
 */
public interface DNSRequestInterface
{
    @SuppressWarnings({"squid:S1160","squid:RedundantThrowsDeclarationCheck"})
    public InputStream getInputStream( String ip )
        throws MalformedURLException, IOException;

    public boolean updateIP( String ip )
            throws IOException;
}
