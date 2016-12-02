package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Allow to create XML writer
 *
 * @since 3.02
 */
public final class XMLWriter extends Writer
{
    private final StringBuilder sbuffer = new StringBuilder();
    private final Writer writer;

    /**
     * Create a new {@link XMLWriter}
     *
     * @param writer Related {@link Writer}
     */
    public XMLWriter( final Writer writer )
    {
        this.writer = writer;
    }

    @Override
    public void close() throws IOException
    {
        this.writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.writer.flush();
    }

    /**
     * Basic XML encoding
     */
    @Override
    public void write( final char[] cbuf, final int off, final int len )
        throws IOException
    {
        synchronized( super.lock ) {
            this.sbuffer.setLength( 0 );

            for( int i = off; i < len; i++ ) {
                final char c = cbuf[ i ];

                switch( c ) {

                case 62 : // '>'
                    this.sbuffer.append( "&gt;" );
                    break;

                case 60 : // '<'
                    this.sbuffer.append( "&lt;" );
                    break;

                case 38 : // '&'
                    this.sbuffer.append( "&amp;" );
                    break;

                default:
                    this.sbuffer.append( c );
                    break;
                }
            }

            this.writer.write( this.sbuffer.toString() );
        }
    }


    /**
     * Store a stack trace into writer
     *
     * @param anException
     *            The {@link Throwable} object to include
     * @throws IOException
     *             if any I/O occur
     */
    public void write( final Throwable anException )
        throws IOException
    {
        final StringWriter sw = new StringWriter();

        anException.printStackTrace( new PrintWriter( sw ) );

        write( sw.toString() );
    }

    /**
     * Add a string into {@link XMLWriter} stream without any conversion.
     *
     * @param str
     *            {@link String} to add
     * @throws IOException
     *             if any I/O occur
     */
    public void rawWrite( final String str ) throws IOException
    {
        this.writer.write( str );
    }

    /**
     * Add an array of char into {@link XMLWriter} stream without any conversion.
     *
     * @param chars
     *            Array of char to add
     * @throws IOException
     *             if any I/O occur
    */
    public void rawWrite( final char[] chars ) throws IOException
    {
        this.writer.write( chars );
    }

    /**
     * Add an array of char into {@link XMLWriter} stream without any conversion.
     *
     * @param chars
     *            Array of char to add
     * @param off
     *            First offset to add
     * @param len
     *            Number of char to add
     * @throws IOException
     *             if any I/O occur
     */
    public void rawWrite( final char[] chars, final int off, final int len )
        throws IOException
    {
        this.writer.write( chars, off, len );
    }
}
