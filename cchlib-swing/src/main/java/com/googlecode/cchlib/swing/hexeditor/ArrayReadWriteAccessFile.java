package com.googlecode.cchlib.swing.hexeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * NEEDDOC
 *
 */
public class ArrayReadWriteAccessFile
    extends ArrayReadAccessFile
        implements ArrayReadWriteAccess
{
    /**
     * NEEDDOC
     * @param file File
     * @throws FileNotFoundException if any
     */
    public ArrayReadWriteAccessFile( final File file ) throws FileNotFoundException
    {
        super( new RandomAccessFile( file, "rw" ) );
    }

    @Override
    public void setByte( final int index, final byte b )
    {
        try {
            this.raf.seek( index );
            this.raf.writeByte( b );
            }
        catch (final IOException e) {
            throw new RuntimeException( e );
            }
    }

}
