package cx.ath.choisnet.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 *
 * @author Claude CHOISNET
 */
public interface SimpleZipEntry
{
    /**
     * 
     * @return
     */
    public abstract ZipEntry getZipEntry();

    /**
     * 
     * @return
     * @throws IOException 
     */
    public abstract InputStream getInputStream()
        throws IOException;
}
