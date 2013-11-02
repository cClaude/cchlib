package com.googlecode.cchlib.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * TODOC
 *
 *@since 4.1.6
 */
public class FileRollingWriter
    extends Writer
{
    private static final Logger logger = Logger.getLogger( FileRollingWriter.class );

    private final FileRoller    fileRoller;
    private final List<File>    fileList    = new ArrayList<File>();;
    private final int           maxLength;
    private final Charset       charset;

    private File    currentFile;
    private Writer  currentOutput;
    private int     currentLength;
    private long    prevLength;

    /**
     * Create a new FileRollingOutputStream
     *
     * @param fileRoller
     * @param maxLength
     * @throws IllegalArgumentException if maxLength < 1
     * @throws IOException
     */
    public FileRollingWriter(
            final FileRoller    fileRoller,
            final int           maxLength,
            final Charset       charset
            )
        throws  IllegalArgumentException, IOException
    {
        if( maxLength < 1 ) {
            throw new IllegalArgumentException(
                "maxLength should be grater than 1"
                );
            }

        this.fileRoller = fileRoller;
        this.maxLength  = maxLength;
        this.prevLength = 0;
        this.charset    = charset;

        openCurrentOutput();
    }

    private void openCurrentOutput() throws IOException
    {
        this.currentFile    = this.fileRoller.createNewRollFile();
        this.currentOutput  = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( this.currentFile ),
                    this.charset
                    )
                );

        if( logger.isTraceEnabled() ) {
            logger.trace( "Open new file: " + this.currentFile );
            }
    }

    private void closeCurrentOutput() throws IOException
    {
        this.currentOutput.flush();
        this.currentOutput.close();
        this.currentOutput = null;

        if( logger.isTraceEnabled() ) {
            logger.trace( "Close file: " + this.currentFile.length() );
            }

        this.fileList.add( this.currentFile );

        this.prevLength += this.currentLength;
        this.currentFile    = null;
        this.currentLength     = 0;
    }

    private void checkIfNeedToChangeFile( int len )
        throws IOException
    {
        this.currentLength += len;

        if( (this.currentLength + len) > this.maxLength ) {
            roolToNewFile();
            }
    }

    private void roolToNewFile() throws IOException
    {
        closeCurrentOutput();
        openCurrentOutput();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Already close." );
            }
        else {
            closeCurrentOutput();
            }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Writer closed" );
            }

        currentOutput.flush();
    }

    /**
     * {@inheritDoc}
     * <br>
     * If len of giving buffer is greater than defined
     * maxLength, then a file of len will be generated
     * for this action.
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Writer closed" );
            }

        checkIfNeedToChangeFile( len );
        currentOutput.write( cbuf, off, len );
    }

    /**
     * {@inheritDoc}
     * <br>
     * If size of giving buffer is greater than defined
     * maxLength, then a file of b.length will be generated
     * for this action.
     */
    @Override
    public void write( char[] b ) throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Writer closed" );
            }

        checkIfNeedToChangeFile( b.length );
        currentOutput.write( b );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write( int b ) throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Writer closed" );
            }

        checkIfNeedToChangeFile( 1 );
        currentOutput.write( b );
    }

    /**
     * Force to change current file now.
     *
     * @throws IOException if any
     */
    public void roolNow() throws IOException
    {
        if( this.currentOutput == null ) {
            throw new IOException( "Writer closed" );
            }

        roolToNewFile();
    }

    /**
     * Returns expected maxLength for generated files.
     * @return expected maxLength for generated files.
     */
    public int getMaxLength()
    {
        return this.maxLength;
    }

    /**
     * Returns length of all files create by this stream.
     * If stream is not closed, return current length store
     * in files (buffered values are not stored).
     * @return length of all files create by this stream
     */
    public long length()
    {
        return this.currentLength + this.prevLength;
    }

    /**
     * Returns List of all files create by this stream, if
     * stream is not close, this list could be invalid while
     * reading.
     * @return an unmodifiable {@link List} of all files
     *         create by this stream.
     */
    public List<File> getFileList()
    {
        return Collections.unmodifiableList( this.fileList );
    }

    /**
     * Returns true if stream is closed
     * @return true if stream is closed
     */
    public boolean isClose()
    {
        return currentOutput == null;
    }
}
