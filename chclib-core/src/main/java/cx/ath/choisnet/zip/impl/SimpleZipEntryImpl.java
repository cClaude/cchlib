package cx.ath.choisnet.zip.impl;

import cx.ath.choisnet.zip.SimpleZipEntry;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleZipEntryImpl
    implements SimpleZipEntry
{
    private File file;
    private ZipEntry zipEntry;

    /**
     * 
     * @param file
     * @param zipEntry
     */
    public SimpleZipEntryImpl(
            File        file, 
            ZipEntry    zipEntry
            )
    {
        this.file     = file;
        this.zipEntry = zipEntry;
    }

    @Override
    public ZipEntry getZipEntry()
    {
        return zipEntry;
    }

    @Override
    public InputStream getInputStream()
        throws FileNotFoundException
    {
        return new BufferedInputStream(
                new FileInputStream(file)
                );
    }
}
