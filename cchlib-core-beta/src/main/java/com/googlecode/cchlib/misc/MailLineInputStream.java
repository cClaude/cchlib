package com.googlecode.cchlib.misc;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 *
 */
//not public
class MailLineInputStream extends InputStream
{
    private InputStream sourceInputStream;
    private boolean isEOF;
    private boolean prev_0D;
    private boolean prev_0D0A;
    private byte[] lineBuffer = new byte[ 1024 ];
    private int currentLineNumber; // TODO

    /**
     *
     * @param sourceInputStream
     */
    public MailLineInputStream( final InputStream sourceInputStream )
    {
        this.sourceInputStream = sourceInputStream;
    }

    public byte[] getLine() throws IOException
    {
        int length = 0;

        do {
            int c = read();

            if( c == -1 ) {
                break;
                }

            try {
                this.lineBuffer[ length++ ] = (byte)c;
                }
            catch( ArrayIndexOutOfBoundsException e ) {
                // TODO handle buffer over run FIXME
                throw e;
                }
            } while( !isEOL() );

        if( length == 0 ) {
            return null;
            }
        else { // shadow buffer
            byte[] shadow = new byte[ length ];

            System.arraycopy(lineBuffer, 0, shadow, 0, length);

            return shadow;
            }
    }

    @Override
    public int read() throws IOException
    {
        if( isEOF() ) {
            return -1;
            }
        int c = sourceInputStream.read();

        if( c == -1 ) {
            isEOF  = true;
            return -1;
            }

        if( c == 0x0D ) {
            prev_0D   = true;
            prev_0D0A = false;
            }
        else if( c == 0x0A ) {
            if( prev_0D ) {
                prev_0D0A = true;
                }
            else {
                prev_0D0A = false;
                }
            prev_0D = false;
            }
        else {
            prev_0D = prev_0D0A = false;
            }

        return c;
    }

    /**
     *
     * @return
     */
    boolean isEOF()
    {
        return this.isEOF;
    }

//    /**
//     *
//     */
//    protected void setEOF( boolean b )
//    {
//        this.isEOF = b;
//    }

    /**
     *
     */
    private boolean isEOL()
    {
        return this.prev_0D0A;
    }

    public int getLineNumber()
    {
        return this.currentLineNumber;
    }
}
