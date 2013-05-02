package com.googlecode.cchlib.swing.hexeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * TODOC
 *
 */
public class ArrayReadWriteAccessFile extends ArrayReadAccessFile implements ArrayReadWriteAccess
{
    /**
     * TODOC
     * @param f
     * @throws FileNotFoundException
     */
    @SuppressWarnings("resource")
    public ArrayReadWriteAccessFile( File f ) throws FileNotFoundException
    {
        super( new RandomAccessFile( f, "rw" ) );
    }

    @Override
    public void setByte(int index, byte b)
    {
        try {
            this.raf.seek( index );
            this.raf.writeByte( b );
            }
        catch (IOException e) {
            throw new RuntimeException( e );
            }
    }

}
