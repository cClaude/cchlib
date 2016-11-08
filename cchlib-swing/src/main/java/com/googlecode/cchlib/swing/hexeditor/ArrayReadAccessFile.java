package com.googlecode.cchlib.swing.hexeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * TODOC
 *
 */
public class ArrayReadAccessFile implements ArrayReadAccess
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

    public ArrayReadAccessFile( final File f ) throws FileNotFoundException
    {
        this( new RandomAccessFile( f, "r" ) );
    }

    @Override
    public int getLength()
    {
        try {
            return (int)this.raf.length();
            }
        catch (final IOException e) {
            throw new RuntimeException( e );
            }
    }

    @Override
    public byte getByte(final int index)
    {
        try {
            this.raf.seek( index );

            return this.raf.readByte();
            }
        catch (final IOException e) {
            throw new RuntimeException( e );
            }
    }

    @Override
    public char getChar(final int index)
    {
        return (char)getByte( index );
    }

    @Override
    public void close() throws IOException
    {
        this.raf.close();
    }
}
