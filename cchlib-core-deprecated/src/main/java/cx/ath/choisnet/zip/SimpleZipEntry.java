package cx.ath.choisnet.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * This class is used to represent a ZIP file entry.
 * 
 * @deprecated use {@link com.googlecode.cchlib.util.zip.SimpleZipEntry} instead
 */
@Deprecated
public interface SimpleZipEntry
{
    /**
     * Returns ZipEntry for current file
     * @return ZipEntry for current file
     */
    public ZipEntry getZipEntry();

    /**
     * Returns InputStream for current file
     * @return InputStream for current file
     * @throws IOException if any I/O error occur.
     */
    public InputStream getInputStream()
        throws IOException;
}
