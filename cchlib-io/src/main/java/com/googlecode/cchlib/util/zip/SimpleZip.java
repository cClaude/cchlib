package com.googlecode.cchlib.util.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.event.EventListenerList;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.util.Wrappable;

/**
 * {@link SimpleZip} is a fronted of {@link ZipOutputStream}
 * to add all content of a specified directory to an
 * {@link OutputStream} (see {@link #addFolder(File, Wrappable)})
 * but can also be use to create an archive with a only some files.
 */
public class SimpleZip implements Closeable
{
    // Not static
    private final class SimpleZipFileVisitor implements FileVisitor<Path>
    {
        private final Wrappable<File, SimpleZipEntry> wrapper;
        private final Path                            startPath;

        private SimpleZipFileVisitor(
            @Nonnull final Wrappable<File, SimpleZipEntry> wrapper,
            @Nullable final Path startPath
            )
        {
            this.wrapper   = wrapper;
            this.startPath = startPath;
        }

        private SimpleZipFileVisitor( final Wrappable<File, SimpleZipEntry> wrapper )
        {
            this( wrapper, (Path)null );
        }

        @Override
        public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs ) throws IOException
        {
            // Ignore startPath (only if define)
            if( ! dir.equals( this.startPath ) ) {
                add( this.wrapper.wrap( dir.toFile() ) );
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs ) throws IOException
        {
            add( this.wrapper.wrap( file.toFile() ) );
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed( final Path file, final IOException exc ) throws IOException
        {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory( final Path dir, final IOException exc ) throws IOException
        {
            return FileVisitResult.CONTINUE;
        }
    }

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
        this.zos    = new ZipOutputStream( output );
        this.buffer = new byte[ bufferSize ];

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
     * @param comment
     *            the comment string
     * @throws IllegalArgumentException
     *             if the length of the specified ZIP file comment is greater
     *             than 0xFFFF bytes
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
     * @param entry {@link SimpleZipEntry} to add
     * @throws IOException if any I/O occur
     */
    public void add( final SimpleZipEntry entry ) throws IOException
    {
        this.fireEntryPostProcessing( entry );

        this.zos.putNextEntry( entry.getZipEntry() );

        if( isNotADirectory( entry ) ) {
            copyFile( entry );
            }

        this.zos.flush();
        this.zos.closeEntry();
        this.fireEntryAdded( entry );
    }

    private void copyFile( final SimpleZipEntry entry ) throws IOException
    {
        try( final BufferedInputStream bis = newBufferedInputStream( entry ) ) {
            IOHelper.copy( bis, this.zos, this.buffer );
        }
    }

    private static BufferedInputStream newBufferedInputStream(
        final SimpleZipEntry entry
        ) throws IOException
    {
        final InputStream inputStream = entry.createInputStream();

        if( inputStream instanceof BufferedInputStream ) {
            return (BufferedInputStream)inputStream;
        }

        return new BufferedInputStream( entry.createInputStream() );
    }

    private static boolean isNotADirectory( final SimpleZipEntry entry )
        throws IOException
    {
        return ! entry.getZipEntry().isDirectory();
    }

    /**
     * Add all {@link SimpleZipEntry} of giving {@link Iterable}
     * object to {@link SimpleZip}
     *
     * @param zipCollection {@link Iterable} of {@link SimpleZipEntry} to add
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
     * @param folderFile
     *            Home directory that will be archived
     * @param wrapper
     *            {@link Wrappable} object able to transform any {@link File}
     *            found under giving directory to a {@link SimpleZipEntry}.
     * @throws IOException
     *             if any I/O occur
     * @see SimpleZipWrapperFactory
     */
    public void addFolder(
            final File                            folderFile,
            final Wrappable<File,SimpleZipEntry>  wrapper
            )
        throws IOException
    {
        final Set<FileVisitOption> options    = EnumSet.noneOf( FileVisitOption.class );
        final int                  maxDepth   = Integer.MAX_VALUE;
        final boolean              ignoreRoot = true;

        addFolder( folderFile, wrapper, options, maxDepth, ignoreRoot );
    }

    /**
     * Add all content of a specified directory to {@link SimpleZip}
     *
     * @param folderFile
     *            Home directory that will be archived
     * @param wrapper
     *            {@link Wrappable} object able to transform any {@link File}
     *            found under giving directory to a {@link SimpleZipEntry}.
     * @param options
     *            options to configure the traversal
     * @param maxDepth
     *            the maximum number of directory levels to visit
     * @param ignoreRoot
     *            if false root folder will be included in zip, if true
     *            root folder will not be included
     * @throws IOException
     *             if any I/O occur
     *
     * @see SimpleZipWrapperFactory
     * @see Files#walkFileTree(Path, Set, int, FileVisitor)
     */
    public void addFolder(
            final File                            folderFile,
            final Wrappable<File, SimpleZipEntry> wrapper,
            final Set<FileVisitOption>            options,
            final int                             maxDepth,
            final boolean                         ignoreRoot
            ) throws IOException
    {
        final Path                      start   = folderFile.toPath();
        final FileVisitor<? super Path> visitor;

        if( ignoreRoot ) {
            visitor = new SimpleZipFileVisitor( wrapper, start );
        } else {
            visitor = new SimpleZipFileVisitor( wrapper );
        }

        Files.walkFileTree( start, options, maxDepth, visitor );
    }

    /**
     * Add all content of a specified directory to {@link SimpleZip}
     * using default wrapper.
     *
     * @param sourceFolderFile Home directory that will be archived
     * @throws IOException if any I/O occur
     * @see SimpleZipWrapperFactory#wrapperFromFolder(File)
     */
    public void addFolder( final File sourceFolderFile ) throws IOException
    {
        addFolder(
            sourceFolderFile,
            SimpleZipWrapperFactory.wrapperFromFolder( sourceFolderFile )
            );
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
     * @throws IOException  if any I/O occur
     */
    protected void fireEntryPostProcessing(
        final SimpleZipEntry szipEntry
        ) throws IOException
    {
        final ZipEntry zipEntry  = szipEntry.getZipEntry();
        final Object[] listeners = this.listenerList.getListenerList();

        assert zipEntry != null;

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
     * @throws IOException  if any I/O occur
     */
    protected void fireEntryAdded(
        final SimpleZipEntry szipEntry
        ) throws IOException
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
