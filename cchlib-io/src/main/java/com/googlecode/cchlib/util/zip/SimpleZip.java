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
public class SimpleZip implements Closeable
{
    /** Default buffer size : {@value} */
    protected static final int DEFAULT_BUFFER_SIZE = 4096;

    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();

    private final ZipOutputStream zos;
    private final byte[]          buffer;

    /**
     * Create a {@link SimpleZip} using {@link #DEFAULT_BUFFER_SIZE}
     *
     * @param output {@link OutputStream} for zip
     * @throws IOException if any I/O occur
     */
    public SimpleZip( final OutputStream output ) throws IOException
    {
        this( output, DEFAULT_BUFFER_SIZE );
    }

    /**
     * Create a {@link SimpleZip} and define buffer size
     *
     * @param output {@link OutputStream} for zip
     * @param bufferSize size of buffer
     * @throws IOException if any I/O occur
     */
    public SimpleZip(
        final OutputStream    output,
        final int             bufferSize
        ) throws IOException
    {
        this.zos    = new ZipOutputStream(output);
        this.buffer = new byte[bufferSize];

        setMethod( ZipEntry.DEFLATED );
    }

    @Override
    public void close() throws IOException
    {
        try {
            this.zos.flush();
            this.zos.finish();
            }
        finally {
            this.zos.close();
            }
    }

    /**
     * Sets the ZIP file comment.
     *
     * @param comment the comment string
     * @throws IllegalArgumentException if the length of the specified ZIP file comment is greater than 0xFFFF bytes
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void setComment( final String comment ) throws IllegalArgumentException
    {
        this.zos.setComment( comment );
    }

    /**
     * Sets the compression level for subsequent
     * entries which are DEFLATED.
     *
     * @param level the compression level (0-9)
     * @throws IllegalArgumentException if the compression level is invalid
     * @see ZipOutputStream#setLevel(int)
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void setLevel( final int level ) throws IllegalArgumentException
    {
        this.zos.setLevel( level );
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
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void setMethod( final int method ) throws IllegalArgumentException
    {
        this.zos.setMethod( method );
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

        this.zos.putNextEntry( sze.getZipEntry() );

        if( !sze.getZipEntry().isDirectory() ) {
            try (BufferedInputStream bis = new BufferedInputStream(
                    sze.createInputStream()
            )) {
                int len;

                while( (len = bis.read(this.buffer, 0, this.buffer.length)) != -1 ) {
                    this.zos.write(this.buffer, 0, len);
                    }
                }
            }

        this.zos.flush();
        this.zos.closeEntry();
        this.fireEntryAdded( sze );
    }

    /**
     * Add all {@link SimpleZipEntry} of giving {@link Iterable}
     * object to {@link SimpleZip}
     *
     * @param c {@link Iterable} of {@link SimpleZipEntry} to add
     * @throws IOException if any I/O occur
     */
    public void addAll( final Iterable<SimpleZipEntry> zipCollection )
        throws IOException
    {
        addAll( zipCollection.iterator() );
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
     * @param wrapper    {@link Wrappable} object able to transform
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
        final Iterator<File> iterator = new FileIterator( folderFile );

        addAll( new IteratorWrapper<>( iterator, wrapper ) );
    }

    /**
     * Adds a {@link ZipListener} to the {@link SimpleZip}'s listener list.
     *
     * @param listener the {@link ZipListener} to add
     */
    public void addZipListener( final ZipListener listener )
    {
        this.listenerList.add( ZipListener.class, listener );
    }

    /**
     * Removes a {@link ZipListener} from the {@link SimpleZip}'s listener list.
     *
     * @param listener the {@link ZipListener} to remove
     */
    public void removeZipListener( final ZipListener listener )
    {
        this.listenerList.remove( ZipListener.class, listener );
    }

    /**
     * Runs each {@link ZipListener}'s
     * {@link ZipListener#entryPostProcessing(ZipEntry)}
     * method.
     *
     * @param szipEntry current {@link SimpleZipEntry}
     */
    protected void fireEntryPostProcessing(
        final SimpleZipEntry szipEntry
        )
    {
        final ZipEntry zipEntry  = szipEntry.getZipEntry();
        final Object[] listeners = this.listenerList.getListenerList();

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
     *
     * @param szipEntry current {@link SimpleZipEntry}
     */
    protected void fireEntryAdded(
        final SimpleZipEntry szipEntry
        )
    {
        final ZipEntry zipEntry  = szipEntry.getZipEntry();
        final Object[] listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == ZipListener.class) {
                ((ZipListener)listeners[i + 1]).entryAdded( zipEntry );
                }
            }
    }
}
