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
    private ByteArrayOutputStream copy = new ByteArrayOutputStream();
    private boolean closed = false;

    public CopyInputStream( InputStream is )
    {
        super( is );
    }

    @Override
    public int read() throws IOException 
    {
        int got = super.read();
        
        if( got >-1 ) {
            copy.write( (byte)got );
        }
        
        return got;
    }

    @Override
    public int read( byte[] buff, int off, int len ) throws IOException 
    {
        int got = super.read( buff, off, len );
        
        if( got >-1 ) {
            copy.write( buff, off, got );
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
     * @return an array of bytes
     * @throws IllegalStateException if original stream not
     * yet closed.
     */
    public byte[] toByteArray() throws IllegalStateException
    {
        if( closed ) {
            return copy.toByteArray();
            }
        
        throw new IllegalStateException();
    }
    /**
     * Returns a copy of InputStream 
     * @return a new InputStream
     * @throws IllegalStateException if original stream not
     * yet closed.
     */
    public InputStream toInputStream() throws IllegalStateException
    {
        return new ByteArrayInputStream( toByteArray() );
    }
}
