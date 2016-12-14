package com.googlecode.cchlib.swing.textfield;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 * NEEDDOC
 *
 * @author  Hanns Holger Rutz at contact@sciss.de visit http://www.sciss.de/jcollider
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class TexfFieldWithPrintStream extends JTextArea
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( TexfFieldWithPrintStream.class );

    private final File          logFile;
    private final PrintStream   ps;
    private FileWriter          logFileWriter   = null;
    private int                 totalLength     = 0;
    private AbstractAction      actionClear     = null;

    /**
     * Constructs a new text area for logging messages.
     * <p>
     * The area is read only and wraps lines as they exceed the right margin. <BR>
     * Messages can be logged in a text file.
     *
     * @param rows
     *            the number of rows &gt;= 0
     * @param columns
     *            the number of columns &gt;= 0
     * @param logFile
     *            this is the file into which the log is written. if {@code logFile}
     *            is {@code null}, there is no log file
     */
    public TexfFieldWithPrintStream( final int rows, final int columns, final File logFile )
    {
        super( rows, columns );

        this.logFile= logFile;
        this.ps     = new PrintStream( new RedirectedStream() );

        setEditable( false );
        setLineWrap( true );
    }

    /**
     *  Constructs a new text area for logging messages.
     */
    public TexfFieldWithPrintStream()
    {
        this( 6, 40, null );
    }

    /**
     * Returns the stream used by this gadget to write data to.
     * <p>
     * <b>warning</b><BR>
     * Theoretically if you use this stream for {@link System#setErr(PrintStream)},
     * you will create a recursion deadlock if an exception is thrown within the
     * {@code write} method of the stream. This case has never been
     * experienced however.
     *
     * @return the {@link PrintStream}, useful for redirecting system output to.
     *
     * @see System#setOut(PrintStream )
     * @see System#setErr(PrintStream )
     */
    public PrintStream getPrintStream()
    {
        return this.ps;
    }

    /**
     *  This method is public because
     *  of the superclass method. Appending
     *  text using this method directly
     *  will not use the internal print
     *  stream and thus not appear in
     *  a log file.
     */
    @Override
    public void append( final String str )
    {
        super.append( str );

        this.totalLength += str.length();
        updateCaret();
    }

    private void updateCaret()
    {
        try {
            setCaretPosition( Math.max( 0, this.totalLength - 1 ));
            }
        catch( final IllegalArgumentException ignore ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "updateCaret() ", ignore );
                }
            }
    }

    /**
     * Replaces the gadget's text. This is useful for clearing the gadget.
     * This doesn't affect the {@link PrintStream} or the log file.
     *
     * @param str
     *            the new text to replace or {@code null} to clear the gadget.
     */
    @Override
    public void setText( final String str )
    {
        super.setText( str );

        this.totalLength = (str == null) ? 0 : str.length();
    }

    public AbstractAction getClearAction()
    {
        if( this.actionClear == null ) {
            this.actionClear = new ActionClearClass();
            }

        return this.actionClear;
    }

    // ---------------- internal classes ----------------

    private class RedirectedStream extends OutputStream
    {
        private final byte[] cheesy = new byte[1];

        private RedirectedStream()
        {
            super();
        }

        @Override
        public void write( final byte[] b ) throws IOException
        {
            this.write( b, 0, b.length );
        }

        @Override
        public void write( final byte[] b, final int off, final int len ) throws IOException
        {
            final String str = new String( b, off, len );

            append( str );

            if( TexfFieldWithPrintStream.this.logFile != null ) {
                if( TexfFieldWithPrintStream.this.logFileWriter == null ) {
                    TexfFieldWithPrintStream.this.logFileWriter = new FileWriter( TexfFieldWithPrintStream.this.logFile );
                    }

                TexfFieldWithPrintStream.this.logFileWriter.write( str );
                }
        }

        @Override
        public void flush() throws IOException
        {
            if( TexfFieldWithPrintStream.this.logFileWriter != null ) {
                TexfFieldWithPrintStream.this.logFileWriter.flush();
                }

            super.flush();
        }

        @Override
        public void close() throws IOException
        {
            if( TexfFieldWithPrintStream.this.logFileWriter != null ) {
                TexfFieldWithPrintStream.this.logFileWriter.close();
                TexfFieldWithPrintStream.this.logFileWriter = null;
                }

            super.close();
        }

        @Override
        public void write( final int b ) throws IOException
        {
            this.cheesy[0] = (byte) b;

            this.write( this.cheesy );
        }
    }

    private class ActionClearClass extends AbstractAction
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed( final ActionEvent e )
        {
            setText( null );
        }
    }
 }
