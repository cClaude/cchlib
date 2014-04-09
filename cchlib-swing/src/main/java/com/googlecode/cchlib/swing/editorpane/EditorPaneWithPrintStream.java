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
 * TODOC
 */
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
        //setLineWrap( true );
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
        return ps;
    }

    private void append( String str ) throws BadLocationException
    {
        Document document = getDocument();
        document.insertString( document.getLength(), str, null );
        setCaretPosition(document.getLength());

        totalLength += str.length();
        updateCaret();
    }

    private void updateCaret()
    {
        try {
            setCaretPosition( Math.max( 0, totalLength - 1 ));
            }
        catch( IllegalArgumentException ignore ) { // $codepro.audit.disable logExceptions, emptyCatchClause
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
    public void setText( String str )
    {
        super.setText( str );

        totalLength = str == null ? 0 : str.length();
    }

    public AbstractAction getClearAction()
    {
        if( actionClear == null ) {
            actionClear = new ActionClearClass();
            }

        return actionClear;
    }

    // ---------------- internal classes ----------------

    private class RedirectedStream extends OutputStream
    {
        private byte[] cheesy = new byte[1];

        private RedirectedStream()
        {
            super();
        }

        @Override
        public void write( byte b[] ) throws IOException
        {
            this.write( b, 0, b.length );
        }

        @Override
        public void write( byte b[], int off, int len ) throws IOException
        {
            final String str = new String( b, off, len );

            try {
                append( str );
                }
            catch( BadLocationException e ) {
                IOException ioe = new IOException( e.getMessage() );

                ioe.initCause( e );

                throw ioe;
                }

            if( logFile != null ) {
                if( logFileWriter == null ) {
                    logFileWriter = new FileWriter( logFile );
                    }

                logFileWriter.write( str );
                }
        }

        @Override
        public void flush() throws IOException
        {
            if( logFileWriter != null ) {
                logFileWriter.flush();
                }

            super.flush();
        }

        @Override
        public void close() throws IOException
        {
            if( logFileWriter != null ) {
                logFileWriter.close();
                logFileWriter = null;
                }

            super.close();
        }

        @Override
        public void write( int b ) throws IOException
        {
            cheesy[0] = (byte) b;

            this.write( cheesy );
        }
    }

    private class ActionClearClass extends AbstractAction
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed( ActionEvent e )
        {
            setText( null );
        }
    }
 }
