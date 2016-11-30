package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.zip.ZipEntryBuilder.Reference;

/**
 * Helper to create {@link Wrappable} object
 * that transform {@link File} into {@link SimpleZipEntry}
 *
 * @since 4.2
 */
public final class SimpleZipWrapperFactory
{
    private SimpleZipWrapperFactory()
    {
        // All static
    }

    /**
     * Create a wrapper based on directory {@link File} object
     *
     * @param rootFolderFile
     *            Directory {@link File}
     * @throws IOException
     *             If an I/O error occurs, which is possible because
     *             the construction of the canonical pathname may
     *             require filesystem queries
     * @throws SecurityException
     *             If a required system property value cannot be accessed,
     *             or if a security manager exists and its
     *             java.lang.SecurityManager.checkRead method denies read
     *             access to the file
     * @return a wrapper based on directory
     * @since 4.2
     */
    public static Wrappable<File,SimpleZipEntry> wrapperFromFolder(
        final File rootFolderFile
        ) throws IOException
    {
        final Reference reference = ZipEntryBuilder.computeReference( rootFolderFile );

        return file -> wrapper( reference, file );
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    private static SimpleZipEntry wrapper(
        final Reference reference,
        final File      file
        ) throws WrapperException
    {
        try {
            return new SimpleZipEntryImpl( reference, file );
        }
        catch( final IOException e ) {
            throw new WrapperException( e );
        }
    }
}
