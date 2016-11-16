package com.googlecode.cchlib.swing.hexeditor;

import java.io.IOException;

/**
 * NEEDDOC
 */
public class EmptyArray implements ArrayReadWriteAccess
{
    @Override
    public int getLength()
    {
        return 0;
    }

    @Override
    public byte getByte(final int index)
    {
        return -1;
    }

    @Override
    public char getChar( final int index )
    {
        return (char)getByte( index );
    }

    @Override
    public void setByte(final int index, final byte b)
    {
    }

    @Override
    public void close() throws IOException
    {
        // In Memory
    }
}
