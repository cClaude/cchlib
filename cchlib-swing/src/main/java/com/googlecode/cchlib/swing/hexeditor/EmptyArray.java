package com.googlecode.cchlib.swing.hexeditor;

import java.io.IOException;

/**
 * TODOC
 */
public class EmptyArray implements ArrayReadWriteAccess
{
    /**
     * TODOC
     */
    public EmptyArray()
    {
    }

    @Override
    public int getLength()
    {
        return 0;
    }

    @Override
    public byte getByte(int index)
    {
        return -1;
    }

    @Override
    public char getChar( int index )
    {
        return (char)getByte( index );
    }

    @Override
    public void setByte(int index, byte b)
    {
    }

    @Override
    public void close() throws IOException
    {
    }
}
