package com.googlecode.cchlib.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.event.EventListenerList;

/**
 * {@link SimpleUnZip} is a fronted of {@link ZipInputStream}
 * to extract all content to a specified directory.
 */
public class SimpleUnZip implements Closeable
{
    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private ZipInputStream zis;
    private final byte[] buffer;
    private int fileCount;

    /**
     * Creates a new {@link SimpleUnZip} based on giving {@link InputStream}
     * using default buffer size (4096).
     * @param input {@link InputStream} that content a zip stream (typically a file)
     * @throws IOException if any I/O occur
     */
    public SimpleUnZip( final InputStream input )
        throws IOException
    {
        this( input, 4096 );
    }

    /**
     * Creates a new {@link SimpleUnZip} based on giving {@link InputStream}
     * <BR>
     * The UTF-8 charset is used to decode the entry names.
     *
     * @param input {@link InputStream} that content a zip stream (typically a file)
     * @param bufferSize size of buffer
     * @throws IOException if any I/O occur
     */
    public SimpleUnZip(
        final InputStream   input,
        final int           bufferSize
        ) throws IOException
    {
        if( input instanceof BufferedInputStream ) {
            this.zis = new ZipInputStream( input );
            }
        else {
            this.zis = new ZipInputStream(
                new BufferedInputStream( input )
                );
            }

        this.buffer    = new byte[ bufferSize ];
        this.fileCount = 0;
    }

    @Override
    public void close() throws IOException
    {
        this.zis.close();
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
        final ZipEntry zipEntry = this.zis.getNextEntry();

        if( zipEntry == null ) {
            return null;
            }

        final File file = new File( folderFile, zipEntry.getName() );
        setLastModified( file, zipEntry );
        this.fireEntryPostProcessing( zipEntry, file );

        final File parent = file.getParentFile();

        if( zipEntry.isDirectory() ) {
            file.mkdirs();
            }
        else {
            if( !parent.isDirectory() ) {
                parent.mkdirs();
                }
            try (OutputStream output = new BufferedOutputStream(
                    new FileOutputStream( file )
            )) {
                int len;

                while( (len = this.zis.read(this.buffer, 0, this.buffer.length)) != -1 ) {
                    output.write(this.buffer, 0, len);
                    }
                }
            }

        this.fileCount++;

        this.fireEntryAdded( zipEntry, file );

        return file;
    }

    private void setLastModified( final File file, final ZipEntry zipEntry )
    {
        final long    time   = zipEntry.getTime();
        @SuppressWarnings("squid:S1854")
        final boolean result = file.setLastModified( time );

        assert result : "Can't setLastModified on " + file + " (" + time + ")";
    }

    /**
     * Extract all archive content to giving folder
     *
     * @param folderFile Home directory {@link File} for next file or folder
     * @throws IOException if any I/O occur
     */
    public void saveAll( final File folderFile ) throws IOException
    {
        while( saveNextEntry( folderFile ) != null ) {
            // Empty
        }
    }

    /**
     * Returns count files extracted
     * @return count files extracted
     */
    public int getFileCount()
    {
        return this.fileCount;
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

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) { // $codepro.audit.disable numericLiterals
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

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) { // $codepro.audit.disable numericLiterals
            if( listeners[i] == UnZipListener.class ) {
                ((UnZipListener)listeners[i + 1]).entryAdded( event );
                }
            }
    }
}
