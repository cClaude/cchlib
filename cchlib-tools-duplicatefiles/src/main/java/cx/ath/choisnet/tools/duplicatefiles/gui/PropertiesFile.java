package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 *
 */
public class PropertiesFile extends Properties
{
    private static final long serialVersionUID = 1L;
    private File file;
    private FileLock fileLock;

    /**
     *
     * @param file
     * @throws IOException
     */
    public PropertiesFile(
        final File file
        ) throws IOException
    {
        this.file = file;

        init();
    }

    public PropertiesFile(
        final File 			file,
        final Properties 	defaults
        ) throws IOException
    {
        super(defaults);

        this.file = file;
        init();
    }

    private void init() throws IOException
    {
//        Path 			path 	= this.file.toPath();
//        OpenOption[]	options = {
//                StandardOpenOption.WRITE,
//                StandardOpenOption.READ
//                };
//        FileChannel fc = FileChannel.open( path, options );
//        this.fileLock  = fc.lock();

        ////
        try {
            // Get a file channel for the file
            FileChannel channel = new RandomAccessFile( this.file, "rw" )
                .getChannel();

            // Use the file channel to create a lock on the file.
            // This method blocks until it can retrieve the lock.
            this.fileLock = channel.lock();

            // Try acquiring the lock without blocking. This method returns
            // null or throws an exception if the file is already locked.
            try {
                this.fileLock = channel.tryLock();
                }
            catch( OverlappingFileLockException e ) {
                // File is already locked in this thread or virtual machine
                }

            // Release the lock
            this.fileLock.release();

            // Close the file
            channel.close();
            }
        catch( Exception e ) {
            }
        ///

        ////
//        FileInputStream in = new FileInputStream(file);
//
//        try {
//            java.nio.channels.FileLock lock = in.getChannel().lock();
//            try {
//                Charset charset = Charset.defaultCharset();
//				Reader reader = new InputStreamReader(in, charset );
//                ///...
//            	}
//            finally {
//                lock.release();
//            	}
//        	}
//        finally {
//            in.close();
//        	}
        //////
    }


    /**
     * Load properties values from file.
     * @throws IOException if an error occurred when reading
     *         from the internal file.
     * @throws IllegalArgumentException if the file contains a malformed
     * 			Unicode escape sequence.
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
     * 			the specified output stream throws an IOException.
     * throws ClassCastException if this Properties object contains
     *  		any keys or values that are not Strings.
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
