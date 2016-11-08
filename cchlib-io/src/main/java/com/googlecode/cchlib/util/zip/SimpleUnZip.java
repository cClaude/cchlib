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
public class SimpleUnZip
    implements Closeable
{
    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private ZipInputStream zis;
    private byte[] buffer;
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

    @Override
    protected void finalize() throws Throwable // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.avoidFinalizers.avoidFinalizers
    {
        close();
        super.finalize();
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
        final ZipEntry zipEntry = zis.getNextEntry();

        if( zipEntry == null ) {
            return null;
            }

        final File file = new File( folderFile, zipEntry.getName() );
        file.setLastModified( zipEntry.getTime() );

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

                while( (len = zis.read(buffer, 0, buffer.length)) != -1 ) {
                    output.write(buffer, 0, len);
                    }
                }
            }

        this.fileCount++;

        this.fireEntryAdded( zipEntry, file );

        return file;
    }

    /**
     * Extract all archive content to giving folder
     *
     * @param folderFile Home directory {@link File} for next file or folder
     * @throws IOException if any I/O occur
     */
    public void saveAll( final File folderFile )
        throws IOException
    {
        while( saveNextEntry( folderFile ) != null ); // $codepro.audit.disable emptyStatement, emptyWhileStatement
    }

    /**
     * Returns count files extracted
     * @return count files extracted
     */
    public int getFileCount()
    {
        return fileCount;
    }

    /**
     * Adds a {@link UnZipListener} to the
     * {@link SimpleUnZip}'s listener list.
     *
     * @param l the {@link UnZipListener} to add
     */
    public void addZipListener( UnZipListener l )
    {
        listenerList.add( UnZipListener.class, l );
    }

    /**
     * Removes a {@link UnZipListener} from the
     *  {@link SimpleUnZip}'s listener list.
     *
     * @param l the {@link UnZipListener} to remove
     */
    public void removeZipListener( UnZipListener l )
    {
        listenerList.remove( UnZipListener.class, l );
    }

    /**
     * Runs each {@link UnZipListener}'s
     * {@link UnZipListener#entryPostProcessing(UnZipEvent)}
     * method.
     */
    protected void fireEntryPostProcessing(
            final ZipEntry zipEntry,
            final File     file
        )
    {
        UnZipEvent event     = new UnZipEvent( zipEntry, file );
        Object[]   listeners = listenerList.getListenerList();

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
     */
    protected void fireEntryAdded(
        final ZipEntry zipEntry,
        final File     file
        )
    {
        UnZipEvent event     = new UnZipEvent( zipEntry, file );
        Object[]   listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) { // $codepro.audit.disable numericLiterals
            if( listeners[i] == UnZipListener.class ) {
                ((UnZipListener)listeners[i + 1]).entryAdded( event );
                }
            }
    }
}
