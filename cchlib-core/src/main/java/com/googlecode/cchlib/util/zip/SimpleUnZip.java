package com.googlecode.cchlib.util.zip;

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
 * TODOC
 *
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
     * TODOC
     *
     * @param input
     * @throws IOException if any I/O occur
     */
    public SimpleUnZip( final InputStream input )
        throws IOException
    {
        this( input, 4096 );
    }

    /**
     * TODOC
     *
     * @param input
     * @param bufferSize
     * @throws IOException if any I/O occur
     */
    public SimpleUnZip(
        final InputStream   input,
        final int           bufferSize
        ) throws IOException
    {
        zis = new ZipInputStream(input);
        buffer = new byte[bufferSize];
        fileCount = 0;
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        zis.close();
    }

    @Override
    protected void finalize() throws Throwable
    {
        close();
        super.finalize();
    }

    /**
     * TODOC
     *
     * @param folderFile
     * @return File object for this new file, or null
     *         if no more entry in zip.
     * @throws IOException if any I/O occur
     */
    public File saveNextEntry( final File folderFile )
        throws IOException
    {
        ZipEntry zipEntry = zis.getNextEntry();

        if( zipEntry == null ) {
            return null;
            }

        this.fireEntryPostProcessing( zipEntry );

        final File file = new File(folderFile, zipEntry.getName());

        file.setLastModified(zipEntry.getTime());

        final File parent = file.getParentFile();

        if( zipEntry.isDirectory() ) {
            file.mkdirs();
            }
        else {
            if(!parent.isDirectory()) {
                parent.mkdirs();
                }
            OutputStream output = new BufferedOutputStream(
                        new FileOutputStream( file )
                        );
            int len;

            try {
                while((len = zis.read(buffer, 0, buffer.length)) != -1) {
                    output.write(buffer, 0, len);
                    }
                }
            finally {
                output.close();
                }
            }

        this.fileCount++;

        this.fireEntryAdded( file, zipEntry );

        return file;
    }

    /**
     * TODOC
     *
     * @param folderFile
     * @throws IOException if any I/O occur
     */
    public void saveAll( final File folderFile )
        throws IOException
    {
        while( saveNextEntry( folderFile ) != null );
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
     * {@link UnZipListener#entryPostProcessing(ZipEntry)}
     * method.
     */
    protected void fireEntryPostProcessing(
        final ZipEntry zipEntry
        )
    {
        UnZipEvent event = new UnZipEvent( zipEntry );
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == UnZipListener.class) {
                ((UnZipListener)listeners[i + 1]).entryPostProcessing( event );
                }
            }
    }

    /**
     * Runs each {@link UnZipListener}'s
     * {@link UnZipListener#entryAdded(ZipEntry)}
     * method.
     */
    protected void fireEntryAdded(
        final File     file,
        final ZipEntry zipEntry
        )
    {
        UnZipEvent event = new UnZipEvent( zipEntry );
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == UnZipListener.class) {
                ((UnZipListener)listeners[i + 1]).entryAdded( event );
                }
            }
    }
}
