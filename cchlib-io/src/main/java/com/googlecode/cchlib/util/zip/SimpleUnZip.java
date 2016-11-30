package com.googlecode.cchlib.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.IOHelper;

/**
 * {@link SimpleUnZip} is a fronted of {@link ZipInputStream}
 * to extract all content to a specified directory.
 */
public class SimpleUnZip implements Closeable
{
    private static final Logger LOGGER = Logger.getLogger( SimpleUnZip.class );

    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();

    private final byte[]         buffer;
    private final ZipInputStream zipIS;
    private final FileTime       lastAccessTime = FileTime.fromMillis( System.currentTimeMillis() );

    private long unZippedFilesCount;

    /**
     * Creates a new {@link SimpleUnZip} based on giving {@link InputStream} using
     * default buffer size (see {@link SimpleZip#DEFAULT_BUFFER_SIZE} ).
     *
     * @param input
     *            {@link InputStream} that content a zip stream (typically a file)
     * @throws IOException
     *             if any I/O occur
     */
    public SimpleUnZip( final InputStream input )
        throws IOException
    {
        this( input, SimpleZip.DEFAULT_BUFFER_SIZE );
    }

    /**
     * Creates a new {@link SimpleUnZip} based on giving {@link InputStream} <BR>
     * The UTF-8 charset is used to decode the entry names.
     *
     * @param input
     *            {@link InputStream} that content a zip stream (typically a file)
     * @param bufferSize
     *            size of buffer
     * @throws IOException
     *             if any I/O occur
     */
    public SimpleUnZip(
        final InputStream   input,
        final int           bufferSize
        ) throws IOException
    {
        this.zipIS              = newZipInputStream( input );
        this.buffer             = new byte[ bufferSize ];
        this.unZippedFilesCount = 0;
    }

    private static ZipInputStream newZipInputStream( final InputStream input )
    {
        if( input instanceof BufferedInputStream ) {
            return new ZipInputStream( input );
            }
        else {
            return new ZipInputStream(
                new BufferedInputStream( input )
                );
            }
    }

    @Override
    public void close() throws IOException
    {
        this.zipIS.close();
    }

    /**
     * Save next entry relative to giving folderFile
     *
     * @param folderFile Home directory {@link File} for next file or folder
     * @return File object for this new file, or null
     *         if no more entry in zip.
     * @throws IOException if any I/O occur
     */
    protected File saveNextEntry( final File folderFile )
        throws IOException
    {
        final ZipEntry zipEntry = this.zipIS.getNextEntry();

        if( zipEntry == null ) {
            return null;
            }

        final File file = new File( folderFile, zipEntry.getName() );
        this.fireEntryPostProcessing( zipEntry, file );

        final File parent = file.getParentFile();

        if( zipEntry.isDirectory() ) {
            file.mkdirs();
        } else { // Handle file
            if( !parent.isDirectory() ) {
                parent.mkdirs();
            }

            try( OutputStream output = new BufferedOutputStream( new FileOutputStream( file ) ) ) {
                IOHelper.copy( this.zipIS, output, this.buffer );
            }
        }

        setLastModified( file, zipEntry );

        this.unZippedFilesCount++;
        this.fireEntryAdded( zipEntry, file );

        return file;
    }

    @SuppressWarnings("squid:S1166") // Exception already log
    private void setLastModified( final File file, final ZipEntry zipEntry )
    {
        try {
            setLastModifiedJava8( file, zipEntry );
        }
        catch( final IOException ignore ) {
            setLastModifiedJava7( file, zipEntry );
        }
    }

    private void setLastModifiedJava7( final File file, final ZipEntry zipEntry )
    {
        final long time = zipEntry.getTime();

        if( time != -1 ) {
            final boolean result = file.setLastModified( time );

            if( !result ) {
                final String message = String.format(
                    "Can't setLastModified( %d ) on %s : isDir( %b )",
                    time,
                    file,
                    file.isDirectory()
                    );
                LOGGER.warn( message );
            }
        }
    }

    private void setLastModifiedJava8( final File file, final ZipEntry zipEntry )
        throws IOException
    {
        final FileTime createTime       = zipEntry.getCreationTime();
        final FileTime lastModifiedTime = zipEntry.getLastModifiedTime();
        final Path     path             = file.toPath();

        final BasicFileAttributeView fileAttributeView =
                Files.getFileAttributeView( path, BasicFileAttributeView.class );

        try {
            fileAttributeView.setTimes(
                lastModifiedTime,
                this.lastAccessTime,
                createTime
                );
        }
        catch( final IOException cause ) {
            final String message = String.format(
                    "Can't setTimes( %s, %s, %s ) on %s",
                    lastModifiedTime,
                    this.lastAccessTime,
                    createTime,
                    file
                    );
            LOGGER.warn( message, cause );

            throw cause;
        }
     }

    /**
     * Extract all archive content to giving folder
     *
     * @param folderFile
     *            Home directory {@link File} for next file or folder
     * @throws IOException
     *             if any I/O occur
     */
    public void saveAll( final File folderFile ) throws IOException
    {
        while( saveNextEntry( folderFile ) != null ) {
            // Empty
        }
    }

    /**
     * @return deprecated
     * @deprecated use {@link #getUnZippedFilesCount()} instead
     */
    @Deprecated
    public int getFileCount()
    {
        return (int)getUnZippedFilesCount();
    }

    /**
     * Returns count files extracted
     * @return count files extracted
     */
    public long getUnZippedFilesCount()
    {
        return this.unZippedFilesCount;
    }

    /**
     * Adds a {@link UnZipListener} to the
     * {@link SimpleUnZip}'s listener list.
     *
     * @param listener the {@link UnZipListener} to add
     */
    public void addZipListener( final UnZipListener listener )
    {
        this.listenerList.add( UnZipListener.class, listener );
    }

    /**
     * Removes a {@link UnZipListener} from the
     *  {@link SimpleUnZip}'s listener list.
     *
     * @param listener the {@link UnZipListener} to remove
     */
    public void removeZipListener( final UnZipListener listener )
    {
        this.listenerList.remove( UnZipListener.class, listener );
    }

    /**
     * Runs each {@link UnZipListener}'s
     * {@link UnZipListener#entryPostProcessing(UnZipEvent)}
     * method.
     *
     * @param zipEntry Current {@link ZipEntry}
     * @param file destination {@link File}
     */
    protected void fireEntryPostProcessing(
        final ZipEntry zipEntry,
        final File     file
        )
    {
        final UnZipEvent event     = new UnZipEvent( zipEntry, file );
        final Object[]   listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == UnZipListener.class ) {
                ((UnZipListener)listeners[i + 1]).entryPostProcessing( event );
                }
            }
    }

    /**
     * Runs each {@link UnZipListener}'s
     * {@link UnZipListener#entryAdded(UnZipEvent)}
     * method.
     *
     * @param zipEntry Current {@link ZipEntry}
     * @param file destination {@link File}
     */
    protected void fireEntryAdded(
        final ZipEntry zipEntry,
        final File     file
        )
    {
        final UnZipEvent event     = new UnZipEvent( zipEntry, file );
        final Object[]   listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == UnZipListener.class ) {
                ((UnZipListener)listeners[i + 1]).entryAdded( event );
                }
            }
    }

    /**
     * Return number of files in {@code zipFile}
     * @param zipFile ZIP {@link File} to explore
     * @return number of files
     * @throws IOException if any I/O occur
     */
    public static long computeFilesCount( final File zipFile )
        throws IOException
    {
        try( final FileInputStream in = new FileInputStream( zipFile ) ) {
            return computeFilesCount( in );
        }
    }

    /**
     * Return number of files in {@code in}
     * @param in ZIP {@link InputStream} to explore
     * @return number of files
     * @throws IOException if any I/O occur
     */
    public static long computeFilesCount( final InputStream in )
        throws IOException
    {
        long count = 0;

        try( final ZipInputStream zis = newZipInputStream( in ) ) {
            while( zis.getNextEntry() != null ) {
                count++;
            }
        }

        return count;
    }
}
