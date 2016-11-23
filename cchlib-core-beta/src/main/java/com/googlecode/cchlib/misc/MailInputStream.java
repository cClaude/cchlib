package com.googlecode.cchlib.misc;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 *
 */
final public class MailInputStream extends InputStream
{
    public static final String BEGIN_OF_MAIL = "From -";
    private MailLineInputStream in;
    private byte[]              lastLine      = null;
    private int                 lastLineIndex = 0;
    private int                 emptyLineCount = 0;
    private boolean eom;
    private boolean eof;

    /**
     *
     * @param in
     */
    public MailInputStream( final MailLineInputStream in )
    {
        this.in = in;
    }

    // 2 lignes vides puis "From -"

    @Override
    public int read() throws IOException
    {
        if( this.eom ) {
            return -1; // End Of Mail
            }

        if( this.lastLine == null || this.lastLineIndex >= this.lastLine.length ) {
            this.lastLine      = in.getLine();
            this.lastLineIndex = 0;

            if( this.lastLine != null && this.lastLine.length == 2 ) {
                if( this.lastLine[ 0 ] == 0x0D /*\r*/ && this.lastLine[ 1 ] == 0x0A /* \n */ ) {
                    this.emptyLineCount++;

                    if( this.emptyLineCount > 1 ) {
                        this.eom = true;
                        return -1; // End Of Mail
                        }
                    }
                else {
                    this.emptyLineCount = 0;
                    }
                }
            else {
                this.emptyLineCount = 0;
                }
            }

        if( this.lastLine == null ) {
            this.eom = this.eof = true;
            return -1; // End Of File => End Of Mail
            }

        int c = 0x00FF & this.lastLine[ this.lastLineIndex++ ];

        return c;
    }

    public boolean isEOF()
    {
        return this.eof;
    }
}
