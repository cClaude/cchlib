package com.googlecode.cchlib.net.download.fis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
//NOT public
class CopyInputStream extends FilterInputStream
{
    private final ByteArrayOutputStream copy = new ByteArrayOutputStream();
    private boolean closed = false;

    public CopyInputStream( final InputStream is )
    {
        super( is );
    }

    @Override
    public int read() throws IOException
    {
        final int got = super.read();

        if( got >-1 ) {
            this.copy.write( (byte)got );
        }

        return got;
    }

    @Override
    public int read( final byte[] buff, final int off, final int len ) throws IOException
    {
        final int got = super.read( buff, off, len );

        if( got >-1 ) {
            this.copy.write( buff, off, got );
            }

        return got;
    }

    @Override
    public void close() throws IOException
    {
        try {
            super.close();
            }
        finally {
            this.closed  = true;
            }
    }

    /**
     * Returns a copy of InputStream
     *
     * @return an array of bytes
     * @throws IllegalStateException
     *             if original stream not yet closed.
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public byte[] toByteArray() throws IllegalStateException
    {
        if( this.closed ) {
            return this.copy.toByteArray();
            }

        throw new IllegalStateException( "InputStream not closed" );
    }

    /**
     * Returns a copy of InputStream
     *
     * @return a new InputStream
     * @throws IllegalStateException
     *             if original stream not yet closed.
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public InputStream toInputStream() throws IllegalStateException
    {
        return new ByteArrayInputStream( toByteArray() );
    }
}
