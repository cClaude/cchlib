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
    private static final Logger logger = Logger.getLogger( PropertiesFileLock.class );
    private File file;
    private FileLock fileLock;

    /**
     *
     * @param file
     * @throws IOException
     */
    public PropertiesFileLock(
        final File file
        ) throws IOException
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
        try {
            RandomAccessFile raf = new RandomAccessFile( this.file, "rw" );
            // Get a file channel for the file
            FileChannel channel = raf.getChannel();

            try {
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
                    logger.warn( "init()", ignore );
                    }

                // Release the lock
                this.fileLock.release();
                }
            finally {
                // Close the file
                raf.close();
                // Not needed: channel.close();
                channel.close();
                }
            }
        catch( Exception e ) {
            logger.warn( "init()", e );
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
        InputStream is = new FileInputStream( this.file );

        try {
            super.load( is  );
            }
        finally {
            is.close();
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
        OutputStream os = new FileOutputStream( this.file );

        try {
            super.store( os , comments  );
            }
        finally {
            os.close();
            }
    }
}
