package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.ZipEntry;
import javax.annotation.Nonnull;

/**
 * Allow to create {@link ZipEntry} without serialization issues
 */
public final class ZipEntryBuilder implements Serializable
{
    /**
     * Root folder reference
     */
    public static class Reference implements Serializable
    {
        private static final long serialVersionUID = 1L;

        private final String ref;

        /**
         * Create root reference
         *
         * @param rootFolderFile root folder
         * @throws IOException if any I/O occur
         * @see ZipEntryBuilder#computeReference(File)
         */
        Reference( @Nonnull final File rootFolderFile ) throws IOException
        {
            final String canonicalPath = rootFolderFile.getCanonicalPath();

            this.ref = canonicalPath.replace('\\', '/') + '/';
        }

        String getReference()
        {
            return this.ref;
        }
    }

    private static final long serialVersionUID = 1L;

    private final Reference rootReference;

    /**
     * Create ZipEntryBuilder
     *
     * @param rootReference root reference for this zip
     * @see ZipEntryBuilder#computeReference(File)
     */
    public ZipEntryBuilder( final Reference rootReference )
    {
        this.rootReference = rootReference;
    }

    /**
     * Create ZipEntryBuilder
     *
     * @param rootFolderFile root folder
     * @throws IOException if any I/O occur
     */
    public ZipEntryBuilder( @Nonnull final File rootFolderFile )
        throws IOException
    {
        this( new Reference( rootFolderFile ) );
    }

    /**
     * Create a {@link ZipEntry} from a given {@code file}
     *
     * @param file reference file
     * @return a {@link ZipEntry} for this {@code file}
     * @throws IOException if any I/O occur
     */
    public ZipEntry newZipEntry( final File file ) throws IOException
    {
        return new ZipEntry( getName( file ) );
    }

    private String getName( final File file ) throws IOException
    {
        String name = file.getCanonicalPath().replace('\\', '/');

        if( /*file.isAbsolute() &&*/ name.startsWith( this.rootReference.getReference() ) ) {
            name = name.substring( this.rootReference.getReference().length() );
            }

        if( file.isDirectory() && !name.endsWith("/") ) {
            name = name + '/';
            }

        return name;
    }

    /**
     * Create a {@link Reference} from {@code rootFolderFile}
     *
     * @param rootFolderFile root folder for the zip file
     * @return a {@link Reference}
     * @throws IOException if any I/O occur
     */
    public static Reference computeReference( final File rootFolderFile )
        throws IOException
    {
        return new Reference( rootFolderFile );
    }
}
