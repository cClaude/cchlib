package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.VisibleForTesting;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.MapSet;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestHelper;

/**
 * @since 4.2
 */
public class DFFPass2WithMultiThreadSupportImpl
    extends DFFPass2Impl
        implements DFFPass2
{
    private final class FileDigestCollection
    {
        private List<Set<FileDigest>> nextList;
        private List<Set<FileDigest>> currentList;

        FileDigestCollection()
        {
            this.nextList    = new ArrayList<>();
            this.currentList = new ArrayList<>();
        }

        Iterator<Set<FileDigest>> getSameBeginFileDigestCollectionIterator()
        {
            // Swap buffers, prepare to iterate on currentList
            swapBuffers();

            return this.currentList.iterator();
        }

        @SuppressWarnings("squid:S3346") // assert usage
        private void swapBuffers()
        {
            // Clear currentList (no more needed)
            assert this.currentList.isEmpty(); // already done ???

            this.currentList.clear();

            // Save empty list to be reuse by next list
            final List<Set<FileDigest>> swapBuffer = this.currentList;

            // swap
            this.currentList = this.nextList;

            // nextList is empty
            this.nextList = swapBuffer;
        }

        @SuppressWarnings("squid:S3346") // assert usage
        void prepareNext( final MapSet<String, FileDigest> currentSubHashs )
        {
            assert currentSubHashs.size() > 0;

            // FileDigest must be splits only if
            // currentSubHashs.size() == numberOfSubHashForThisPart
            for( final Iterator<Set<FileDigest>> iterator = currentSubHashs.values().iterator(); iterator.hasNext(); ) {
                final Set<FileDigest> set = iterator.next();

                this.nextList.add( set );
            }
        }

        public boolean isEmpty()
        {
            return this.nextList.isEmpty() && this.currentList.isEmpty();
        }

        public void clear()
        {
            clear( this.nextList );
            clear( this.currentList );
        }

        private void clear( final List<Set<FileDigest>> fileDigestSetList )
        {
            for( final Iterator<Set<FileDigest>> iterator1 = fileDigestSetList.iterator(); iterator1.hasNext(); ) {
                final Set<FileDigest> set = iterator1.next();

                for( final Iterator<FileDigest> iterator2 = set.iterator(); iterator2.hasNext(); ) {
                    final FileDigest fileDigest = iterator2.next();

                    if( fileDigest.isOpen() ) {
                        close( fileDigest );
                    }
                    iterator2.remove();
                }
                iterator1.remove();
            }
        }
        private void close( final FileDigest fileDigest )
        {
            final File file = fileDigest.getFile();

            try {
                fileDigest.reset();
            }
            catch( final IOException ioe ) {
                LOGGER.error( "Can not close : " + file, ioe );
            }
        }
        @SuppressWarnings("squid:S3346") // assert usage
        public void addOnOpen( final FileDigest fd )
        {
            // currentList is empty (nothing start yet)
            assert this.currentList.isEmpty();

            final Set<FileDigest> initialSet = getInitialSet();

            initialSet.add( fd );
        }

        @SuppressWarnings("squid:S3346") // assert usage
        private Set<FileDigest> getInitialSet()
        {
            if( this.nextList.isEmpty() ) {
                final HashSet<FileDigest> initialSet = new HashSet<>();

                this.nextList.add( initialSet );

                return initialSet;
            } else {
                // should exist only one set in nextList.
                assert this.nextList.size() == 1;

                return this.nextList.get( 0 );
            }
        }

        public List<Set<FileDigest>> getCurrentList()
        {
            return this.currentList;
        }
    }

    private static final Logger LOGGER = Logger.getLogger( DFFPass2WithMultiThreadSupportImpl.class );

    private final MessageDigest messageDigest;
    private final StringBuilder hashStringBuilder;
    private final FileDigestCollection fileDigestCollection;

    public DFFPass2WithMultiThreadSupportImpl( final DFFConfigWithMultiThreadSupport config )
    {
        super( config );

        try {
            this.messageDigest = MessageDigest.getInstance( getConfig().getFileDigest().getAlgorithm() );
        }
        catch( final NoSuchAlgorithmException e ) {
            throw new RuntimeException( e );
        }

        this.hashStringBuilder    = new StringBuilder();
        this.fileDigestCollection = new FileDigestCollection();
    }

    protected DFFConfigWithMultiThreadSupport getDFFConfigWithMultiThreadSupport()
    {
        return (DFFConfigWithMultiThreadSupport)getConfig();
    }

    @Override
    @SuppressWarnings("squid:S3346") // assert usage
    protected void find( final Map.Entry<Long, Set<File>> entry )
    {
        final Set<File> set = entry.getValue();

        assert this.fileDigestCollection.isEmpty();

        if( set.size() < getDFFConfigWithMultiThreadSupport().getFileDigestsCount() ) {
            try {
                handlePass2ForSetAtOnce( entry );
            }
            finally {
                this.fileDigestCollection.clear();
            }
        } else {
            // too much entries, use basic solution.
            super.find( entry );
        }
    }



    private void handlePass2ForSetAtOnce( final Map.Entry<Long, Set<File>> entryForThisLength )
    {
        openFiles( entryForThisLength );

        try {
            final long length = entryForThisLength.getKey().longValue();

            readFiles( length );
            }
        catch( final CancelRequestException e ) {
            getConfig().setCancelProcess( true );
        }
        finally {
            this.fileDigestCollection.clear();
        }
    }

    /**
     * Open all files with the same length.
     *
     * @param entryForThisLength
     *            Entry with length and the set of files to open.
     */
    private void openFiles( final Entry<Long, Set<File>> entryForThisLength )
    {
        int index = 0;

        final Set<File> setForThisLength    = entryForThisLength.getValue();
        final long      length              = entryForThisLength.getKey().longValue();

        for( final Iterator<File> iterator = setForThisLength.iterator(); iterator.hasNext(); ) {
            final File file = iterator.next();

            if( file.length() == length ) {
                final FileDigest fd = getDFFConfigWithMultiThreadSupport().getFileDigest( index );

                try {
                    fd.setFile( file, getFileDigestListener() );

                    this.fileDigestCollection.addOnOpen( fd );
                }
                catch( final FileNotFoundException ioe ) {
                    // File not found, ignore if
                    notify_ioError( file, ioe );
                    iterator.remove();
                }
                index++;
            } else {
                // file has been modify, ignore it
                notify_ioError( file, new FileHasChangeException( file ) );
                iterator.remove();
            }
        }
    }

    /**
     * Read all files at the same time.
     *
     * @param expectedLength expected size of file.
     * @throws CancelRequestException
     */
    private void readFiles( final long expectedLength ) throws CancelRequestException
    {
        while( hasNext( expectedLength ) ) {
            final MapSet<String, FileDigest> currentSubHashs = computeNext();

            this.fileDigestCollection.prepareNext( currentSubHashs );
        }
    }

    private boolean hasNext( final long expectedLength )
    {
        Boolean hasNext = null;

        for( final Iterator<Set<FileDigest>> fileDigestsIterator = this.fileDigestCollection.getCurrentList().iterator(); fileDigestsIterator.hasNext(); ) {
            final Set<FileDigest> sameBegin = fileDigestsIterator.next();

            for( final Iterator<FileDigest> sameBeginIterator = sameBegin.iterator(); sameBeginIterator.hasNext(); ) {
                final FileDigest fd = sameBeginIterator.next();

                hasNext = hasNext( sameBeginIterator, fd, hasNext, expectedLength );
            }
         }

        return (hasNext == null) ? false : hasNext.booleanValue();
    }

    private Boolean hasNext(
            final Iterator<FileDigest> sameBeginningIterator,
            final FileDigest           fileDigest,
            final Boolean              hasNextStatus,
            final long                 expectedLength
            )
    {
        if( hasNextStatus == null ) {
            Boolean hasNext = null;

            if( fileDigest.getFile().length() == expectedLength ) {
                try {
                    hasNext = Boolean.valueOf( fileDigest.hasNext() );
                }
                catch( final IOException ioe ) {
                    ignoreFile( sameBeginningIterator, fileDigest, ioe );
                }
            } else {
                // Ignore file
                ignoreFile(
                        sameBeginningIterator,
                        fileDigest,
                        new FileLengthHasChangeException( fileDigest, expectedLength )
                        );
            }

            return hasNext;
        } else {
            try {
                if( hasNextStatus.booleanValue() != fileDigest.hasNext() ) {
                    // Ignore file
                    ignoreFile(
                            sameBeginningIterator,
                            fileDigest,
                            new FileHasChangeException( fileDigest ) /* TODO improve this : what is the cause ??? */
                            );
                }
            }
            catch( final IOException cause ) {
                ignoreFile( sameBeginningIterator, fileDigest, cause );
            }

            return hasNextStatus;
        }
    }

    private void ignoreFile( final Iterator<FileDigest> fileDigestsIterator, final FileDigest fd, final IOException ioe  )
    {
        // notify
        final File file = fd.getFile();

        notify_ioError( file, ioe );

        // close file
        closeFile( fd );

        // remove from list
        fileDigestsIterator.remove();
    }

    /**
     * Compute subDigest for a same previous list of subDigest
     * @param fileDigests {@link Collection} of a {@link FileDigest} having
     *      same previous list of subDigest
     * @param length expected length of file
     * @return
     * @throws CancelRequestException if any
     */
    private MapSet<String,FileDigest> computeNext() throws CancelRequestException
    {
        final MapSet<String,FileDigest> subDigest = new HashMapSet<>();

        for( final Iterator<? extends Collection<FileDigest>> sameBeginIterator = this.fileDigestCollection.getSameBeginFileDigestCollectionIterator(); sameBeginIterator.hasNext(); ) {
            final Collection<FileDigest> fileDigests = sameBeginIterator.next();

            computeNext( subDigest, fileDigests );
        }
        return subDigest;
    }

    private void computeNext( final MapSet<String, FileDigest> subDigest, final Collection<FileDigest> fileDigests ) throws CancelRequestException
    {
        for( final Iterator<FileDigest> iterator = fileDigests.iterator(); iterator.hasNext(); ) {
            final FileDigest fileDigest = iterator.next();
            final byte[]     hashBytes  = fileDigest.computeNext( true );

            final String hash = computeHash(this.messageDigest, this.hashStringBuilder, hashBytes);

            subDigest.add( hash, fileDigest );
        }
    }

    private void closeFile( final FileDigest fd )
    {
        // save file to be handle logging.
        final File file = fd.getFile();

        try {
            fd.reset();
        }
        catch( final Exception ignore ) {
            LOGGER.warn( "Close error: " + file, ignore );
        }
    }

    //Very special usage, need re-factoring to be documented
    //not public
    @VisibleForTesting
    public static String computeHash( final MessageDigest messageDigest, final StringBuilder sb, final byte[] currentBuffer )
    {
        messageDigest.reset();
        messageDigest.update( currentBuffer );
        final byte[] digest = messageDigest.digest();

        sb.setLength( 0 );
        FileDigestHelper.computeDigestKeyString( sb, digest );
        return sb.toString();
    }
}
