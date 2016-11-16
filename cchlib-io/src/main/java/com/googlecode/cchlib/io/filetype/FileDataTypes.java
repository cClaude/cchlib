// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.io.filetype;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic and quick file data type resolution.
 * @since 4.1.5
 */
public class FileDataTypes
{
    private static List<FileDataTypeMatch> fileDataTypeMatchList;
    private static final FileDataTypeDescription JPEG = new FDT_jpeg();
    private static final FileDataTypeMatch[] JPEG_MATCHERS = {
        //new DefaultFileDataTypeMatch( JPEG, new int[]{0xFF, 0xD8, 0xFF, 0xE1,   -1,   -1, 0x45/*'E'*/, 0x78/*'x'*/, 0x69/*'i'*/, 0x66/*'f'*/, 0x00, 0x00} ),
        new DefaultFileDataTypeMatch( JPEG, new int[]{0xFF, 0xD8, 0xFF, 0xE0, 0x00, 0x10, 0x4A/*'J'*/, 0x46/*'F'*/, 0x49/*'I'*/, 0x46/*'F'*/, 0x00, 0x01 } ),
        new DefaultFileDataTypeMatch( JPEG, new int[]{0xFF, 0xD8, 0xFF, 0xDB, 0x00, 0x43, 0x00 } ),
        new DefaultFileDataTypeMatch( JPEG, new int[]{0xFF, 0xD8, 0xFF, 0xE2 } ),
        new DefaultFileDataTypeMatch( JPEG, new int[]{0xFF, 0xD8, 0xFF, 0xE1 } ),
        };
    private static final FileDataTypeDescription GIF = new FDT_gif();
    private static final FileDataTypeMatch[] GIF_MATCHERS = {
        new DefaultFileDataTypeMatch( GIF, new int[]{'G', 'I', 'F', '8', '9' } ),
        new DefaultFileDataTypeMatch( GIF, new int[]{'G', 'I', 'F', '8', '7' } ),
        };

    private static final FileDataTypeDescription PNG = new FDT_png();
    private static final FileDataTypeMatch[] PNG_MATCHERS = {
        new DefaultFileDataTypeMatch( PNG,
                //new int[]{0x89, 0x50, 0x4E, 0x47}
                new int[]{0x89, 'P', 'N', 'G'}
                )
        };

    /**
     * Supported type
     */
    public enum Type
    {
        /** JPEG Image */
        JPEG,
        /** PNG Image */
        PNG,
        /** GIF Image */
        GIF
    }


    /**
     * Returns {@link FileDataTypeDescription} for giving file
     *
     * @param file {@link File} to examine
     * @return {@link FileDataTypeDescription} for giving file
     * @throws FileNotFoundException If file not exist
     * @throws IOException If any I/O error occur
     */
    public static FileDataTypeDescription findDataTypeDescription( final File file )
            throws FileNotFoundException, IOException
    {
        try (InputStream is = new FileInputStream( file )) {
            return findDataTypeDescription( is );
            }
    }

    /**
     * Returns {@link FileDataTypeDescription} for giving {@link InputStream}
     *
     * @param is {@link InputStream} to examine (must be closed)
     * @return {@link FileDataTypeDescription} for giving file
     * @throws IOException If any I/O error occur
     */
    public static FileDataTypeDescription findDataTypeDescription(
        final InputStream is
        )
        throws IOException
    {
        if( fileDataTypeMatchList == null ) {
            initFileDataTypeMatchList();
            }

        final byte[] buffer = new byte[ 256 ];
        final FileDataTypeDescription r;

        try( final FilterDataTypes filter = new FilterDataTypes( is, fileDataTypeMatchList ) ) {
            do {
                filter.read( buffer );
                } while( ! filter.isReady() );

            r = filter.getFileDataTypeDescription();
            }

        return r;
    }

    public static List<FileDataTypeMatch> createFileDataTypeMatchList()
    {
        final List<FileDataTypeMatch> fileDataTypeMatchList = new ArrayList<>();

        for( final FileDataTypeMatch m : JPEG_MATCHERS ) {
            fileDataTypeMatchList.add( m );
            }
        for( final FileDataTypeMatch m : GIF_MATCHERS ) {
            fileDataTypeMatchList.add( m );
            }
        for( final FileDataTypeMatch m : PNG_MATCHERS ) {
            fileDataTypeMatchList.add( m );
            }

        return fileDataTypeMatchList;
    }

    private static synchronized void initFileDataTypeMatchList()
    {
        if( fileDataTypeMatchList == null ) {
            fileDataTypeMatchList = createFileDataTypeMatchList();
            }
    }

    /**
     * NEEDDOC
     * @param file
     * @return NEEDDOC
     * @throws IOException
     */
    public static ExtendedFileDataTypeDescription findExtendedFileDataTypeDescription(
            final File file
            ) throws IOException
    {
        final FileDataTypeDescription desc = findDataTypeDescription( file );

        if( desc == null ) {
            return null;
            }

        final ImageIOFileData image;

        {
            try (InputStream is = new BufferedInputStream( new FileInputStream( file ) )) {
                image = new ImageIOFileData( is );
                }
        }

        return new DefaultExtendedFileDataTypeDescription( desc, image );
    }
}
