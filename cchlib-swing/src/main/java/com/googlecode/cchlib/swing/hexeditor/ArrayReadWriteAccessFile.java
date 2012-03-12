package com.googlecode.cchlib.swing.hexeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ArrayReadWriteAccessFile
    implements ArrayReadWriteAccess
{
    //private File file;
    private RandomAccessFile raf;

    public ArrayReadWriteAccessFile( File f ) throws FileNotFoundException
    {
        //this.file = f;
        this.raf = new RandomAccessFile( f, "rw" );
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
