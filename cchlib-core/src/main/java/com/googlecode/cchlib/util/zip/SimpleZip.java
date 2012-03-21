package com.googlecode.cchlib.util.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.event.EventListenerList;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

/**
 * {@link SimpleZip} is a fronted of {@link ZipOutputStream}
 * to add all content of a specified directory to an
 * {@link OutputStream} (see {@link #addFolder(File, Wrappable)})
 * but can also be use to create an archive with a only some files.
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
        zos.setMethod( method );
    }

    /**
     * Add {@link SimpleZipEntry} to {@link SimpleZip}
     *
     * @param sze {@link SimpleZipEntry} to add
     * @throws IOException if any I/O occur
     */
    public void add( final SimpleZipEntry sze ) throws IOException
    {
        this.fireEntryPostProcessing( sze );

        zos.putNextEntry( sze.getZipEntry() );

        if( !sze.getZipEntry().isDirectory() ) {
            BufferedInputStream bis = new BufferedInputStream(
                    sze.createInputStream()
                    );
            try {
                int len;

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
     * Add all {@link SimpleZipEntry} of giving {@link Iterable}
     * object to {@link SimpleZip}
     *
     * @param c {@link Iterable} of {@link SimpleZipEntry} to add
     * @throws IOException if any I/O occur
     */
    public void addAll( Iterable<SimpleZipEntry> c )
        throws java.io.IOException
    {
        addAll( c.iterator() );
    }

    /**
     * Add all {@link SimpleZipEntry} of giving {@link Iterator}
     * object to {@link SimpleZip}
     *
     * @param iterator {@link Iterator} of {@link SimpleZipEntry} to add
     * @throws IOException if any I/O occur
     */
    protected void addAll( final Iterator<SimpleZipEntry> iterator )
        throws IOException
    {
        while( iterator.hasNext() ) {
            add( iterator.next() );
            }
    }

    /**
     * Add all content of a specified directory to {@link SimpleZip}
     *
     * @param folderFile Home directory that will be archived
     * @param wrapper	 {@link Wrappable} object able to transform
     *                   any {@link File} found under giving directory
     *                   to a {@link SimpleZipEntry}.
     * @throws IOException if any I/O occur
     * @see DefaultSimpleZipWrapper
     */
    public void addFolder(
            final File                            folderFile,
            final Wrappable<File,SimpleZipEntry>  wrapper
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
