package com.googlecode.cchlib.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * NEEDDOC
 *
 *@since 4.1.6
 */
public class FileRollingOutputStream
    extends OutputStream
{
    private static final Logger LOGGER = Logger.getLogger( FileRollingOutputStream.class );
    private static final String STREAM_CLOSED = "Stream closed";

    private final List<File>    fileList    = new ArrayList<>();;
    private final FileRoller    fileRoller;
    private final int           maxLength;

    private File            currentFile;
    private OutputStream    currentOutput;
    private int             currentLength;
    private long            prevLength;

    private boolean isClose;

    /**
     * Create a new FileRollingOutputStream
     *
     * @param fileRoller
     * @param maxLength
     * @throws FileNotFoundException if an error while
     *         creating first File
     * @throws IllegalArgumentException if maxLength < 1
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public FileRollingOutputStream(
            final FileRoller    fileRoller,
            final int            maxLength
            )
        throws  FileNotFoundException,
                IllegalArgumentException
    {
        if( maxLength < 1 ) {
            throw new IllegalArgumentException(
                "maxLength should be grater than 1"
                );
            }

        this.fileRoller    = fileRoller;
        this.maxLength     = maxLength;

        this.prevLength            = 0;

        openCurrentOutputStream();

        this.isClose = false;
    }

    private void openCurrentOutputStream() throws FileNotFoundException
    {
        this.currentFile     = this.fileRoller.createNewRollFile();
        this.currentOutput     = new BufferedOutputStream(
                new FileOutputStream( this.currentFile )
                );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Open new file: " + this.currentFile );
            }
    }

    private void closeCurrentOutputStream() throws IOException
    {
        this.currentOutput.flush();
        this.currentOutput.close();
        this.currentOutput = null;

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Close file: " + this.currentFile.length() );
            }

        this.fileList.add( this.currentFile );

        this.prevLength += this.currentLength;
        this.currentFile    = null;
        this.currentLength     = 0;
    }

    private void checkIfNeedToChangeFile( final int len )
        throws IOException
    {
        this.currentLength += len;

        if( (this.currentLength + len) > this.maxLength ) {
            roolToNewFile();
            }
    }

    private void roolToNewFile() throws IOException
    {
        closeCurrentOutputStream();
        openCurrentOutputStream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException
    {
        if( this.isClose ) {
            throw new IOException( "Already close." );
            }
        else {
            closeCurrentOutputStream();

            this.isClose = true;
            }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException
    {
        if( this.isClose ) {
            throw new IOException( STREAM_CLOSED );
            }

        this.currentOutput.flush();
    }

    /**
     * {@inheritDoc}
     * <br>
     * If len of giving buffer is greater than defined
     * maxLength, then a file of len will be generated
     * for this action.
     */
    @Override
    public void write( final byte[] b, final int off, final int len ) throws IOException
    {
        if( this.isClose ) {
            throw new IOException( STREAM_CLOSED );
            }

        checkIfNeedToChangeFile( len );
        this.currentOutput.write( b, off, len );
    }

    /**
     * {@inheritDoc}
     * <br>
     * If size of giving buffer is greater than defined
     * maxLength, then a file of b.length will be generated
     * for this action.
     */
    @Override
    public void write( final byte[] b ) throws IOException
    {
        if( this.isClose ) {
            throw new IOException( STREAM_CLOSED );
            }

        checkIfNeedToChangeFile( b.length );
        this.currentOutput.write( b );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write( final int b ) throws IOException
    {
        if( this.isClose ) {
            throw new IOException( STREAM_CLOSED );
            }

        checkIfNeedToChangeFile( 1 );
        this.currentOutput.write( b );
    }

    /**
     * Force to change current file now.
     *
     * @throws IOException if any
     */
    public void roolNow() throws IOException
    {
        if( this.isClose ) {
            throw new IOException( STREAM_CLOSED );
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
        return this.isClose;
    }
}
