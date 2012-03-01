package com.googlecode.cchlib.util.zip;

import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.iterator.IteratorWrapper;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.event.EventListenerList;

/**
 * TODOC
 *
 */
public class SimpleZip
    implements  Closeable
{
    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private ZipOutputStream zos;
    private byte[] buffer;

    /**
     * Create a {@link SimpleZip}
     *
     * @param output
     * @throws IOException if any I/O occur
     */
    public SimpleZip( final OutputStream output ) throws IOException
    {
        this( output, 4096 );
    }

    /**
     * Create a {@link SimpleZip} and define buffer size
     *
     * @param output
     * @param bufferSize
     * @throws IOException if any I/O occur
     */
    public SimpleZip(
            final OutputStream    output,
            final int             bufferSize
            )
        throws IOException
    {
        zos    = new ZipOutputStream(output);
        buffer = new byte[bufferSize];

        setMethod( ZipEntry.DEFLATED );
    }

    @Override
    public void close() throws IOException
    {
        zos.flush();
        zos.finish();
        zos.close();
    }

    /**
     * Sets the ZIP file comment.
     *
     * @param comment the comment string
     * @throws IllegalArgumentException if the length of the specified ZIP file comment is greater than 0xFFFF bytes
     */
    public void setComment( final String comment )
        throws IllegalArgumentException
    {
        zos.setComment( comment );
    }

    /**
     * Sets the compression level for subsequent
     * entries which are DEFLATED.
     *
     * @param level the compression level (0-9)
     * @throws IllegalArgumentException if the compression level is invalid
     * @see ZipOutputStream#setLevel(int)
     */
    public void setLevel( int level )
        throws IllegalArgumentException
    {
        zos.setLevel( level );
    }

    /**
     * Sets the default compression method for subsequent entries.
     * This default will be used whenever the compression method
     * is not specified for an individual ZIP file entry,
     * and is initially set to DEFLATED.
     *
     * @param method the default compression method
     * @throws IllegalArgumentException if the specified compression
     *         method is invalid
     * @see ZipEntry#DEFLATED
     * @see ZipEntry#STORED
     */
    public void setMethod( final int method )
        throws IllegalArgumentException
    {
        zos.setMethod(method);
    }

    /**
     * TODOC
     *
     * @param simpleZipEntry
     * @throws IOException if any I/O occur
     */
    public void add(
        final SimpleZipEntry sze
        ) throws IOException
    {
        this.fireEntryPostProcessing( sze );

        zos.putNextEntry( sze.getZipEntry() );

        if( !sze.getZipEntry().isDirectory() ) {
            BufferedInputStream bis = new BufferedInputStream( sze.createInputStream() );
            int len;

            try {
                while( (len = bis.read(buffer, 0, buffer.length)) != -1 ) {
                    zos.write(buffer, 0, len);
                    }
                }
            finally {
                bis.close();
                }
            }

        zos.flush();
        zos.closeEntry();

        this.fireEntryAdded( sze );
    }

    /**
     * TODOC
     *
     * @param c
     * @throws IOException if any I/O occur
     */
    public void addAll( Iterable<SimpleZipEntry> c )
        throws java.io.IOException
    {
        addAll( c.iterator() );
    }

    /**
     * TODOC
     *
     * @param iterator
     * @throws IOException if any I/O occur
     */
    public void addAll( Iterator<SimpleZipEntry> iterator )
        throws IOException
    {
        while( iterator.hasNext() ) {
            add( iterator.next() );
            }
    }

    /**
     * TODOC
     *
     * @param folderFile
     * @param wrapper
     * @throws IOException if any I/O occur
     */
    public void addFolder(
            File                            folderFile,
            Wrappable<File,SimpleZipEntry>  wrapper
            )
        throws IOException
    {
        addAll(
            new IteratorWrapper<File, SimpleZipEntry>(
                new FileIterator(folderFile),
                wrapper
                )
            );
    }

    /**
     * Adds a {@link ZipListener} to the {@link SimpleZip}'s listener list.
     *
     * @param l the {@link ZipListener} to add
     */
    public void addZipListener( ZipListener l )
    {
        listenerList.add( ZipListener.class, l );
    }

    /**
     * Removes a {@link ZipListener} from the {@link SimpleZip}'s listener list.
     *
     * @param l the {@link ZipListener} to remove
     */
    public void removeZipListener( ZipListener l )
    {
        listenerList.remove( ZipListener.class, l );
    }

    /**
     * Runs each {@link ZipListener}'s
     * {@link ZipListener#entryPostProcessing(ZipEntry)}
     * method.
     */
    protected void fireEntryPostProcessing(
        final SimpleZipEntry sze
        )
    {
        ZipEntry zipEntry = sze.getZipEntry();
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == ZipListener.class) {
                ((ZipListener)listeners[i + 1]).entryPostProcessing( zipEntry  );
                }
            }
    }

    /**
     * Runs each {@link ZipListener}'s
     * {@link ZipListener#entryAdded(ZipEntry)}
     * method.
     */
    protected void fireEntryAdded(
        final SimpleZipEntry sze
        )
    {
        ZipEntry zipEntry = sze.getZipEntry();
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == ZipListener.class) {
                ((ZipListener)listeners[i + 1]).entryAdded( zipEntry );
                }
            }
    }
}
