package cx.ath.choisnet.zip.impl;

import cx.ath.choisnet.zip.SimpleZipEntry;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleZipEntryBasicImpl
    implements SimpleZipEntry
{

    private InputStream inputStream;
    private ZipEntry zipEntry;

    public SimpleZipEntryBasicImpl(InputStream inputStream, ZipEntry zipEntry)
    {
        this.inputStream = inputStream;

        this.zipEntry = zipEntry;
    }

    public SimpleZipEntryBasicImpl(InputStream inputStream, String zipEntryName)
    {
        this(inputStream, new ZipEntry(zipEntryName));
    }

    public java.util.zip.ZipEntry getZipEntry()
    {
        return zipEntry;

    }

    public java.io.InputStream getInputStream()
    {
        return inputStream;

    }
}
