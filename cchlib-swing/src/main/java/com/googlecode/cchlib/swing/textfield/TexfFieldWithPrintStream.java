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
 * TODOC
 *
 * @author  Hanns Holger Rutz at contact@sciss.de visit http://www.sciss.de/jcollider
 */
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
     *  Constructs a new text area for logging messages.
     *  <p>
     *  The area is readonly and wraps lines as they exceed the right margin.
     *  <br/>
     *  Messages can be logged in a text file.
     *  </p>
     *
     *  @param rows     the number of rows >= 0
     *  @param columns  the number of columns >= 0
     *  @param logFile  this is the file into which the log is written. if <code>logFile</code> is
     *                  <code>null</code>, there is no log file
     */
    public TexfFieldWithPrintStream( int rows, int columns, final File logFile )
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
     *  Returns the stream used by this gadget to write data to.
     *
     *  <p>
     *  <b>warning</b><br/>
     *  Theoretically if you use this stream for <code>System.setErr</code>, 
     *  you will create a recursion deadlock if an exception is thrown
     *  within the <code>write</code> method of the stream. This case 
     *  has never been experienced however.
     *  </p>
     *  
     *  @return the <code>PrintStream</code>, useful
     *                      for redirecting system output to.
     *
     *  @see System#setOut( PrintStream )
     *  @see System#setErr( PrintStream )
     */
    public PrintStream getPrintStream()
    {
        return ps;
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
    public void append( String str )
    {
        super.append( str );

        totalLength += str.length();
        updateCaret();
    }

    private void updateCaret()
    {
        try {
            setCaretPosition( Math.max( 0, totalLength - 1 ));
            }
        catch( IllegalArgumentException ignore ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "updateCaret() ", ignore );
                }
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

        totalLength = (str == null) ? 0 : str.length();
    }

    public AbstractAction getClearAction()
    {
        if( actionClear == null ) {
            actionClear = new ActionClearClass();
            }

        return actionClear;
    }

//    /**
//     * surround by a JScrollPane
//     * @return
//     */
//    public JScrollPane surroundByJScrollPane()
//    {
//        return(
//            new JScrollPane(
//                this,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // aqua hin aqua her. VERTICAL_SCROLLBAR_ALWAYS
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
//                )
//            );
//    }

//    public void makeSystemOutput()
//    {
//        System.setOut( getLogStream() );
//        System.setErr( getLogStream() );
//    }

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

            append( str );

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
