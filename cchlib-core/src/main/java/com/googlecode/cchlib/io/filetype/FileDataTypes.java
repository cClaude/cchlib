package com.googlecode.cchlib.io.filetype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Basic file data type resolution.
 * @since 4.1.5
 */
public class FileDataTypes
{
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
        FileInputStream     fis = new FileInputStream( file );
        byte[]              b   = new byte[ 16 ];

        int len = fis.read( b );
        fis.close();

        if( len > 0 ) {
            if( b[ 6 ] == 0x4A && b[ 7 ] == 0x46 && b[ 8 ] == 0x49 && b[ 9 ] == 0x46 ) {
                return new FileDataTypeDescription()
                {
                    @Override
                    public String getExtension()
                    {
                        return ".jpeg";
                    }
                    @Override
                    public String getShortExtension()
                    {
                        return ".jpg";
                    }
                    @Override
                    public Type getType()
                    {
                        return Type.JPEG;
                    }
                };
            }
            if( b[ 1 ] == 0x50 && b[ 2 ] == 0x4E && b[ 3] == 0x47 ) {
                return new FileDataTypeDescription()
                {
                    @Override
                    public String getExtension()
                    {
                        return ".png";
                    }
                    @Override
                    public String getShortExtension()
                    {
                        return getExtension();
                    }
                    @Override
                    public Type getType()
                    {
                        return Type.PNG;
                    }
                };
            }
            if( b[ 0 ] == 0x47 && b[ 1 ] == 0x49 && b[ 2 ] == 0x46 ) {
                return new FileDataTypeDescription()
                {
                    @Override
                    public String getExtension()
                    {
                        return ".gif";
                    }
                    @Override
                    public String getShortExtension()
                    {
                        return getExtension();
                    }
                    @Override
                    public Type getType()
                    {
                        return Type.GIF;
                    }
                };
            }
        }

        return null;
    }
}