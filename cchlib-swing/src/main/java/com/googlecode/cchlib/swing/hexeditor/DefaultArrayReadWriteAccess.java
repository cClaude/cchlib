package com.googlecode.cchlib.swing.hexeditor;

import java.io.IOException;

/**
 * NEEDDOC
 */
public final class DefaultArrayReadWriteAccess implements ArrayReadWriteAccess
{
    private final byte[] buffer;

    /**
     * NEEDDOC
     * @param buffer
     */
    public DefaultArrayReadWriteAccess(final byte[] buffer)
    {
        this.buffer = buffer;
    }

    @Override
    public int getLength()
    {
        return this.buffer.length;
    }

    @Override
    public byte getByte(final int index)
    {
        return this.buffer[ index ];
    }

    @Override
    public char getChar(final int index)
    {
        return (char)getByte( index );
    }

    @Override
    public void setByte(final int index, final byte b)
    {
        this.buffer[ index ] = b;
    }

    @Override
    public void close() throws IOException
    {
        // In memory
    }
}
