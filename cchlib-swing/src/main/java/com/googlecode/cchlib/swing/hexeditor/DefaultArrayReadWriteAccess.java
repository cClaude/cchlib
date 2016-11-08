package com.googlecode.cchlib.swing.hexeditor;

import java.io.IOException;

/**
 * TODOC
 */
public final class DefaultArrayReadWriteAccess implements ArrayReadWriteAccess
{
    private final byte[] buffer;

    /**
     * TODOC
     * @param buffer
     */
    public DefaultArrayReadWriteAccess(byte[] buffer)
    {
        this.buffer = buffer;
    }

    @Override
    public int getLength()
    {
        return this.buffer.length;
    }

    @Override
    public byte getByte(int index)
    {
        return this.buffer[ index ];
    }

    @Override
    public char getChar(int index)
    {
        return (char)getByte( index );
    }

    @Override
    public void setByte(int index, byte b)
    {
        this.buffer[ index ] = b;
    }

    @Override
    public void close() throws IOException
    {
    }
}
