package com.googlecode.cchlib.util.zip;

import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * {@link Wrappable} object to transform {@link File} into {@link SimpleZipEntry}
 */
public class DefaultSimpleZipWrapper
    implements Wrappable<File,SimpleZipEntry>
{
    private String refFolder;
    private int    refFolderLen;

    /**
     * Create a DefaultSimpleZipWrapper based on directory {@link File} object
     *
     * @param refFolderFile Directory {@link File}
     * @throws IOException If an I/O error occurs, which is
     *         possible because the construction of the canonical
     *         pathname may require filesystem queries
     * @throws SecurityException If a required system property value
     *         cannot be accessed, or if a security manager exists
     *         and its java.lang.SecurityManager.checkRead method
     *         denies read access to the file
     */
    public DefaultSimpleZipWrapper(
            final File refFolderFile
            )
        throws IOException
    {
        final String canonicalPath = refFolderFile.getCanonicalPath();

        refFolder = canonicalPath.replace('\\', '/') + '/';
        refFolderLen = refFolder.length();
    }

    /**
     * Create a DefaultSimpleZipWrapper based on directory file name
     *
     * @param refFolderName Directory file name
     * @throws IOException If an I/O error occurs, which is
     *         possible because the construction of the canonical
     *         pathname may require filesystem queries
     * @throws SecurityException If a required system property value
     *         cannot be accessed, or if a security manager exists
     *         and its java.lang.SecurityManager.checkRead method
     *         denies read access to the file
     */
    public DefaultSimpleZipWrapper(
            final String refFolderName
            )
        throws IOException
    {
        this(new File( refFolderName ));
    }

    @Override
    public SimpleZipEntry wrap( File file ) throws WrapperException
    {
        try {
            return wrapper( file );
            }
        catch( IOException e ) {
            throw new WrapperException( e );
            }
    }

    private SimpleZipEntry wrapper( final File file ) throws IOException
    {
        String name = file.getCanonicalPath().replace('\\', '/');

        if( /*file.isAbsolute() &&*/ name.startsWith( refFolder ) ) {
            name = name.substring( refFolderLen );
            }

        if( file.isDirectory() && !name.endsWith("/") ) {
            name = name + '/';
            }

        final ZipEntry zipEntry = new ZipEntry(name);

        zipEntry.setTime( file.lastModified() );
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
