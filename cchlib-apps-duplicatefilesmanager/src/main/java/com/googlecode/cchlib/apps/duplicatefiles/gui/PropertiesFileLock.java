package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 */
//NOT public
class PropertiesFileLock extends Properties
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PropertiesFileLock.class );

    private File file;
    private FileLock fileLock;

    public PropertiesFileLock( final File file ) throws IOException
    {
        this.file = file;

        init();
    }

    public PropertiesFileLock(
        final File          file,
        final Properties    defaults // $codepro.audit.disable declareAsInterface
        ) throws IOException
    {
        super(defaults);

        this.file = file;
        init();
    }

    private void init() throws IOException
    {
        try ( final RandomAccessFile raf = new RandomAccessFile( this.file, "rw" ) ) {
            // Get a file channel for the file
            @SuppressWarnings("resource")
            FileChannel channel = raf.getChannel();

            // Use the file channel to create a lock on the file.
            // This method blocks until it can retrieve the lock.
            this.fileLock = channel.lock();

            // Try acquiring the lock without blocking. This method returns
            // null or throws an exception if the file is already locked.
            try {
                this.fileLock = channel.tryLock();
                }
            catch( OverlappingFileLockException ignore ) {
                // File is already locked in this thread or virtual machine
                LOGGER.warn( "init()", ignore );
                }

            // Release the lock
            this.fileLock.release();
            }
        catch( Exception e ) {
            LOGGER.warn( "init()", e );
            }
    }


    /**
     * Load properties values from file.
     * @throws IOException if an error occurred when reading
     *         from the internal file.
     * @throws IllegalArgumentException if the file contains a malformed
     *             Unicode escape sequence.
     * @see #load(InputStream)
     */
    public void load() throws IOException
    {
        try( final InputStream is = new FileInputStream( this.file ) ) {
            super.load( is );
            }
    }

    /**
     * Writes this property list (key and element pairs) in this Properties
     * table to the file.
     *
     * @param comments a description of the property list.
     * @throws IOException if writing this property list to
     *             the specified output stream throws an IOException.
     * throws ClassCastException if this Properties object contains
     *          any keys or values that are not Strings.
     */
    public void store( final String comments ) throws IOException
    {
        try( final OutputStream os = new FileOutputStream( this.file ) ) {
            super.store( os , comments  );
            }
    }
}
