package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * Default implementation for {@link DuplicateFileFinder}
 *
 * @since 4.2
 */
public class DefaultDuplicateFileFinder implements DuplicateFileFinder {

    private enum State {
        PASS1,
        PASS2,
        DONE
        }
    private final DFFConfig dffConfig;

    private final DFFPass1 pass1Delegator;
    private final DFFPass2 pass2Delegator;

    private State state;

    private final Status status = new Status() {

        @Override
        public long getBytes()
        {
            return DefaultDuplicateFileFinder.this.dffConfig.getDuplicateBytesCount();
        }

        @Override
        public int getFiles()
        {
            return DefaultDuplicateFileFinder.this.dffConfig.getDuplicateFilesCount();
        }

        @Override
        public int getSets()
        {
            return DefaultDuplicateFileFinder.this.dffConfig.getDuplicateSetsCount();
        }
    };

    public DefaultDuplicateFileFinder( //
        final DFFConfig dffConfig, //
        final DFFPass1  dffPass1, //
        final DFFPass2  dffPass2 //
        )
    {
        this.state          = State.PASS1;
        this.dffConfig      = dffConfig;
        this.pass1Delegator = dffPass1;
        this.pass2Delegator = dffPass2;
    }

    @Override
    public void addEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
       this.pass2Delegator.addEventListener( eventListener );
    }

    @Override
    public void addFile( final File file )
    {
        checkState( State.PASS1 );

        this.pass1Delegator.addFile( file );
    }

    @Override
    public void addFiles( final Iterable<File> files )
    {
        checkState( State.PASS1 );

        this.pass1Delegator.addFiles( files );
    }

    @Override
    public void cancelProcess()
    {
        this.dffConfig.setCancelProcess( true );
    }

    private void checkState( final State expectedState )
    {
        if( this.state != expectedState ) {
            throw new IllegalStateException( "Current State:" + this.state + " excepting:" + expectedState );
        }
    }

    @Override
    public void clear()
    {
        checkState( State.DONE );

        this.dffConfig.clear();
        this.state = State.PASS1;
    }

    @Override
    public void find()
    {
        checkState( State.PASS1 );

        this.state = State.PASS2;

        try {
            this.pass2Delegator.find();
        } finally {
            this.state = State.DONE;

            pass2CleanupForCancelProcess();
        }
    }

    @Override
    public Map<String, Set<File>> getFiles()
    {
        checkState( State.DONE );

        return this.dffConfig.getMapHashFiles();
    }

    /**
     * Returns intermediate informations, statistics
     * informations on files that should be examined
     * by {@link #find()}.
     * <p>
     * Warning:<BR>
     * This value is only valid after adding all files
     * ({@link #addFile(File)}, {@link #addFiles(Iterable)} and
     * before calling {@link #find()}.
     * </p>
     *
     * @return Statistics informations on files that
     *         should be examined by {@link #find()}.
     */
    @Override
    public InitialStatus getInitialStatus()
    {
        checkState( State.PASS1 );

        int  filesCount = 0;
        long bytes2Read = 0;

        final Iterator<Entry<Long, Set<File>>> entryIterator = this.dffConfig.getMapLengthFiles().entrySet().iterator();

        while( entryIterator.hasNext() ) {
            final Map.Entry<Long, Set<File>> entry = entryIterator.next();
            final Set<File> set = entry.getValue();

            if( set.size() > 1 ) {
                final long length = entry.getKey().longValue();
                long bytes2Add = 0;

                // Check if files haven't change.
                final Iterator<File> fileIterator = set.iterator();

                while( fileIterator.hasNext() ) {
                    final File file = fileIterator.next();
                    final long filelength = file.length();

                    if( filelength == length ) {
                        bytes2Add += length;
                    } else {
                        fileIterator.remove();
                    }
                }

                if( set.size() > 1 ) {
                    bytes2Read += bytes2Add;
                    filesCount += set.size();
                }
            }

            // Remove not useful set.
            if( set.size() < 2 ) {
                entryIterator.remove();
            }
        }

        return new DFFStatus( bytes2Read, filesCount );
    }

    @Override
    public Status getStatus()
    {
        return this.status;
    }

    @Override
    public boolean isCancelProcess()
    {
        return this.dffConfig.isCancelProcess();
    }

    private void pass2CleanupForCancelProcess()
    {
        this.dffConfig.getMapLengthFiles().clear();

        pass2RemoveNoneDuplicate();
    }

    private void pass2RemoveNoneDuplicate()
    {
        final Iterator<Set<File>> iter  = this.dffConfig.getMapHashFiles().values().iterator();

        while( iter.hasNext() ) {
            final Set<File> s = iter.next();

            if( s.size() < 2 ) {
                iter.remove();
            }
        }
    }

    @Override
    public void removeEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
        this.pass2Delegator.removeEventListener( eventListener );
    }
}
