package com.googlecode.cchlib.util.zip;

import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.WrappeException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * TODOC
 *
 */
public class SimpleZipEntryFactoryImpl
    implements Wrappable<File,SimpleZipEntry>
{
    private String refFolder;
    private int    refFolderLen;

    /**
     * TODOC
     *
     * @param refFolderFile
     * @throws IOException
     */
    public SimpleZipEntryFactoryImpl(
            final File refFolderFile
            )
        throws IOException
    {
        final String canonicalPath = refFolderFile.getCanonicalPath();

        refFolder = canonicalPath.replace('\\', '/') + '/';
        refFolderLen = refFolder.length();
    }

    /**
     *
     * @param refFolder
     * @throws IOException
     */
    public SimpleZipEntryFactoryImpl(
            final String refFolder
            )
        throws IOException
    {
        this(new File(refFolder));
    }

    @Override
    public SimpleZipEntry wrappe( File file ) throws WrappeException
    {
        try {
            return private_wrappe( file );
            }
        catch( IOException e ) {
            throw new WrappeException( e );
            }
    }

    private SimpleZipEntry private_wrappe(
        final File file
        ) throws IOException
    {
        String name = file.getCanonicalPath().replace('\\', '/');

        if( /*file.isAbsolute() &&*/ name.startsWith( refFolder ) ) {
            name = name.substring( refFolderLen );
            }

        if( file.isDirectory() && !name.endsWith("/") ) {
            name = name + '/';
            }

        final ZipEntry zipEntry = new ZipEntry(name);

        zipEntry.setTime(file.lastModified());
//        zipEntry.setComment( comment );
//        zipEntry.setCompressedSize( csize );
//        zipEntry.setCrc( crc );
//        zipEntry.setExtra( extra );
//        zipEntry.setMethod( method );
//        zipEntry.setSize( size );

        return new SimpleZipEntry()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public ZipEntry getZipEntry()
            {
                return zipEntry;
            }
            @Override
            public InputStream createInputStream()
                throws FileNotFoundException
            {
                return new BufferedInputStream(
                        new FileInputStream( file )
                        );
            }
        };
    }
}
