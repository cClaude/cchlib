package cx.ath.choisnet.zip;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface SimpleZipEntry
{
    public abstract java.util.zip.ZipEntry getZipEntry();

    public abstract java.io.InputStream getInputStream()
        throws java.io.IOException;
}
