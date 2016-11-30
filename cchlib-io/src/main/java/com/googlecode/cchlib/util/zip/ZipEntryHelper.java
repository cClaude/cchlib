package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import org.apache.log4j.Logger;

public class ZipEntryHelper
{
    private static final Logger LOGGER = Logger.getLogger( ZipEntryHelper.class );

    private ZipEntryHelper()
    {
        // All static
    }

    /**
     * Copy file attribute from {@code fromFile} to {@code toZipEntry}
     *
     * @param fromFile Original file
     * @param toZipEntry Zip entry
     */
    public static void copyAttributes( final File fromFile, final ZipEntry toZipEntry )
    {
        // pre-java 8 - for non regression
        toZipEntry.setTime( fromFile.lastModified() );

        // Missign attributs :
        //
        //  zipEntry.setComment( comment )
        //  zipEntry.setCompressedSize( csize )
        //  zipEntry.setCrc( crc )
        //  zipEntry.setExtra( extra )
        //  zipEntry.setMethod( method )
        //  zipEntry.setSize( size )
        try {
            copyAttributes( fromFile.toPath(), toZipEntry );
        }
        catch( final IOException cause ) {
            LOGGER.warn(
                "Can not setAttributes on " + toZipEntry + " from " + fromFile,
                cause
                );
        }
    }

    private static void copyAttributes( final Path fromPath, final ZipEntry toZipEntry )
        throws IOException
    {
        final BasicFileAttributeView fileAttributeView =
                Files.getFileAttributeView( fromPath, BasicFileAttributeView.class );

        final BasicFileAttributes attributes = fileAttributeView.readAttributes();

        toZipEntry.setLastAccessTime( attributes.lastAccessTime() );
        toZipEntry.setCreationTime( attributes.creationTime() );
    }

}
