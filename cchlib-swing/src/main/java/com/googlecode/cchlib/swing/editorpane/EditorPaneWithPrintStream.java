package com.googlecode.cchlib.swing.editorpane;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * NEEDDOC
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class EditorPaneWithPrintStream extends JEditorPane
{
    private static final long serialVersionUID = 1L;

    private final File          logFile;
    private final PrintStream   ps;
    private FileWriter          logFileWriter   = null;
    private int                 totalLength     = 0;
    private AbstractAction      actionClear     = null;

    /**
     *  Creates a new EditorPaneWithPrintStream. The document model is set to "text/html".
     *  <p>
     *  This EditorPane is readonly.
     *  <BR>
     *  Messages can be logged in a text file.
     *  </p>
     *
     *  @param logFile  this is the file into which the log is written. if <code>logFile</code> is
     *                  <code>null</code>, there is no log file
     */
    public EditorPaneWithPrintStream( final File logFile )
    {
        super();
        setContentType("text/html");

        this.logFile= logFile;
        this.ps     = new PrintStream( new RedirectedStream() );

        setEditable( false );
    }

    /**
     * Creates a new EditorPaneWithPrintStream. The document model is set to "text/html".
     */
    public EditorPaneWithPrintStream()
    {
        this( /*6, 40,*/ null );
    }

    /**
     *  Returns the stream used by this gadget to write data to.
     *
     *  <p>
     *  <b>warning</b><BR>
     *  Theoretically if you use this stream for <code>System.setErr</code>,
     *  you will create a recursion deadlock if an exception is thrown
     *  within the <code>write</code> method of the stream. This case
     *  has never been experienced however.
     *  </p>
     *
     *  @see System#setOut( PrintStream )
     *  @see System#setErr( PrintStream )
     */
    public PrintStream getPrintStream()
    {
        return this.ps;
    }

    private void append( final String str ) throws BadLocationException
    {
        final Document document = getDocument();
        document.insertString( document.getLength(), str, null );
        setCaretPosition(document.getLength());

        this.totalLength += str.length();
        updateCaret();
    }

    private void updateCaret()
    {
        try {
            setCaretPosition( Math.max( 0, this.totalLength - 1 ));
            }
        catch( final IllegalArgumentException ignore ) { // $codepro.audit.disable logExceptions, emptyCatchClause
            }
    }

    /**
     *  Replaces the gadget's text.
     *  This is useful for clearing
     *  the gadget. This doesn't
     *  affect the <code>PrintStream</code>
     *  or the log file.
     *
     *  @param  str        the new text to replace
     *                     the gadgets content or <code>null</code>to clear the gadget.
     */
    @Override
    public void setText( final String str )
    {
        super.setText( str );

        this.totalLength = str == null ? 0 : str.length();
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
        public void write( final byte b[] ) throws IOException
        {
            this.write( b, 0, b.length );
        }

        @Override
        public void write( final byte b[], final int off, final int len ) throws IOException
        {
            final String str = new String( b, off, len );

            try {
                append( str );
                }
            catch( final BadLocationException e ) {
                final IOException ioe = new IOException( e.getMessage() );

                ioe.initCause( e );

                throw ioe;
                }

            if( EditorPaneWithPrintStream.this.logFile != null ) {
                if( EditorPaneWithPrintStream.this.logFileWriter == null ) {
                    EditorPaneWithPrintStream.this.logFileWriter = new FileWriter( EditorPaneWithPrintStream.this.logFile );
                    }

                EditorPaneWithPrintStream.this.logFileWriter.write( str );
                }
        }

        @Override
        public void flush() throws IOException
        {
            if( EditorPaneWithPrintStream.this.logFileWriter != null ) {
                EditorPaneWithPrintStream.this.logFileWriter.flush();
                }

            super.flush();
        }

        @Override
        public void close() throws IOException
        {
            if( EditorPaneWithPrintStream.this.logFileWriter != null ) {
                EditorPaneWithPrintStream.this.logFileWriter.close();
                EditorPaneWithPrintStream.this.logFileWriter = null;
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
