package com.googlecode.cchlib.swing.hexeditor;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * TODOC
 *
 */
public class ArrayReadAccessFile
    implements ArrayReadAccess,
               Closeable
{
    protected final RandomAccessFile raf;

    /**
     * TODOC
     * @param raf
     */
    protected ArrayReadAccessFile(
        final RandomAccessFile raf
        )
    {
        this.raf = raf;
    }

    /**
     * TODOC
     * @param f
     * @throws FileNotFoundException
     */
    public ArrayReadAccessFile( File f ) throws FileNotFoundException
    {
        this( new RandomAccessFile( f, "r" ) );
    }

    @Override
    public int getLength()
    {
        try {
            return (int)raf.length();
            }
        catch (IOException e) {
            throw new RuntimeException( e );
            }
    }

    @Override
    public byte getByte(int index)
    {
        try {
            this.raf.seek( index );

            return this.raf.readByte();
            }
        catch (IOException e) {
            throw new RuntimeException( e );
            }
    }

    @Override
    public char getChar(int index)
    {
        return (char)getByte( index );
    }

    @Override
    public void close() throws IOException
    {
        this.raf.close();
    }
}
