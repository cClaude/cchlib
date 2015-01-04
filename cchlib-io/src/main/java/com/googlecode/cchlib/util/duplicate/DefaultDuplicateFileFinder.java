package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.HashMapSet;

public class DefaultDuplicateFileFinder implements DuplicateFileFinder {

    private final class PrivateFileDigestListener implements FileDigestListener {

        @Override
        public boolean isCancel()
        {
            return DefaultDuplicateFileFinder.this.cancelProcess;
        }

        @Override
        public void computeDigest( final File file, final int length )
        {
            DefaultDuplicateFileFinder.this.notify_analysisStatus( file, length );
        }
    }

    private final class PrivateStatus implements Status, Serializable {

        private static final long serialVersionUID = 1L;
        private final long bytes2Read;
        private final int filesCount;

        public PrivateStatus( final long bytes2Read, final int filesCount )
        {
            this.bytes2Read = bytes2Read;
            this.filesCount = filesCount;
        }

        @Override
        public long getPass2Bytes()
        {
            return this.bytes2Read;
        }

        @Override
        public int getPass2Files()
        {
            return this.filesCount;
        }
    }

    private enum State { PASS1, PASS2, DONE };
    private final boolean ignoreEmptyFile;
    private boolean cancelProcess;
    private State state;
    private final ArrayList<DuplicateFileFinderEventListener> listeners;
    private final HashMapSet<String,File> mapHashFiles;
    private final HashMapSet<Long,File> mapLengthFiles;
    private final FileDigest[] fileDigests;
    private int duplicateSetsCount;
    private int duplicateFilesCount;
    private final FileDigestListener fileDigestListener = new PrivateFileDigestListener();

    public DefaultDuplicateFileFinder( //
        final FileDigestFactory   fileDigestFactory, //
        final int                 maxParalleleFiles, //
        final boolean             ignoreEmptyFile //
        ) throws NoSuchAlgorithmException
    {
        if( maxParalleleFiles < 1 ) {
            throw new IllegalArgumentException( "maxParalleleFiles must be >= 1 : current value = " + maxParalleleFiles );
        }

        this.fileDigests = new FileDigest[ maxParalleleFiles ];

        for( int i = 0; i<maxParalleleFiles; i++ ) {
            this.fileDigests[ i ] = fileDigestFactory.newInstance();
        }

        this.mapLengthFiles = new HashMapSet<>();
        this.mapHashFiles = new HashMapSet<>();
        this.listeners = new ArrayList<>();
        this.ignoreEmptyFile = ignoreEmptyFile;
        this.cancelProcess = false;
        this.state = State.PASS1;
    }

    @Override
    public void addEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
        if( eventListener == null ) {
            throw new NullPointerException( "DuplicateFileFinderEventListener is null" );
        }

        synchronized( this.listeners ) {
            if( ! this.listeners.contains( eventListener ) ) {
                this.listeners.add( eventListener );
            }
        }
    }

    @Override
    public void removeEventListener( @Nonnull final DuplicateFileFinderEventListener eventListener )
    {
        if( eventListener == null ) {
            throw new NullPointerException( "DuplicateFileFinderEventListener is null" );
        }

        synchronized( this.listeners ) {
            final int index = this.listeners.indexOf( eventListener );

            if( index >= 0 ) {
                this.listeners.remove( index );
            }
        }
    }

    @Override
    public void addFile( final File file )
    {
        checkState( State.PASS1 );

        final long size = file.length();

        if( this.ignoreEmptyFile && (size == 0) ) {
            return;
        }

        this.mapLengthFiles.add( Long.valueOf( size ), file );

        if( this.cancelProcess ) {
            this.mapLengthFiles.clear();
            return;
        }
    }

    @Override
    public void addFiles( final Iterable<File> files )
    {
        if( ! this.cancelProcess ) {
            for(final File file : files) {
                addFile( file );
            }
        }
    }

    @Override
    public void cancelProcess()
    {
        this.cancelProcess = true;
    }

    @Override
    public void clear()
    {
        checkState( State.DONE );

        this.mapLengthFiles.clear();
        this.mapHashFiles.clear();
        this.duplicateSetsCount = 0;
        this.duplicateFilesCount = 0;
        this.cancelProcess = false;
        this.state = State.PASS1;
    }

    @Override
    public int getDuplicateFilesCount()
    {
        return this.duplicateFilesCount;
    }

    @Override
    public int getDuplicateSetsCount()
    {
        return this.duplicateSetsCount;
    }

    @Override
    public Map<String, Set<File>> getFiles()
    {
        checkState( State.DONE );

        return this.mapHashFiles;
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
    public Status getInitialStatus()
    {
        checkState( State.PASS1 );

        int  filesCount = 0;
        long bytes2Read = 0;

        final Iterator<Entry<Long, Set<File>>> entryIterator = this.mapLengthFiles.entrySet().iterator();

        while( entryIterator.hasNext() ) {
            final Map.Entry<Long, Set<File>> entry = entryIterator.next();
            final Set<File> set = entry.getValue();

            if( set.size() > 1 ) {
                final long length    = entry.getKey().longValue();
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

        return new PrivateStatus( bytes2Read, filesCount );
    }

    @Override
    public boolean isCancelProcess()
    {
        return this.cancelProcess;
    }

    @Override
    public void find()
    {
        checkState( State.PASS1 );

        this.state = State.PASS2;

        try {
            handlePass2();
        } finally {
            this.state = State.DONE;

            pass2CleanupForCancelProcess();
        }
    }

    private void handlePass2()
    {
        if( this.cancelProcess ) {
            return;
        }

        final Iterator<Set<File>> iter = this.mapLengthFiles.values().iterator();

        while( iter.hasNext() ) {
            final Set<File> set = iter.next();

            if( set.size() > 1 ) {
                handlePass2( set );
            }

            if( this.cancelProcess ) {
                return;
            }

            iter.remove();
        }

        assert this.mapLengthFiles.size() == 0;

        pass2RemoveNoneDuplicate();
    }

    private void pass2CleanupForCancelProcess()
    {
        this.mapLengthFiles.clear();

        pass2RemoveNoneDuplicate();
    }

    private void pass2RemoveNoneDuplicate()
    {
        final Iterator<Set<File>> iter  = this.mapHashFiles.values().iterator();

        while( iter.hasNext() ) {
            final Set<File> s = iter.next();

            if( s.size() < 2 ) {
                iter.remove();
            }
        }
    }

    private void handlePass2( final Set<File> set )
    {
        if( set.size() < this.fileDigests.length ) {
            handlePass2ForASetOfFiles( set );
        } else {
            handlePass2ForASingleFile( set );
        }
    }

    private void handlePass2ForASetOfFiles( final Set<File> set )
    {
        for( final File file : set ) {
            // TODO

            // Done with current file (or cancel)
            // Check cancel state
            if( this.cancelProcess ) {
                return;
            }
        }
    }

    private void handlePass2ForASingleFile( final Set<File> setForThisLength )
    {
        final HashMapSet<String,File> mapSetForThisLength = new HashMapSet<>();

        for( final File file : setForThisLength ) {
            final String hashString = handlePass2ForASingleFile( file );

            Set<File> setForThisHash = mapSetForThisLength.get( hashString );

            if( setForThisHash == null ) {
                setForThisHash = new HashSet<>();
                mapSetForThisLength.put( hashString, setForThisHash );
            }
            setForThisHash.add( file );

            // Done with current file (or cancel)
            // Check cancel state
            if( this.cancelProcess ) {
                break;
            }
        }

        // Check if we have new duplicates.
        final Iterator<Map.Entry<String, Set<File>>> entryIterator = mapSetForThisLength.entrySet().iterator();

        while( entryIterator.hasNext() ) {
            final Map.Entry<String, Set<File>> entry = entryIterator.next();
            final Set<File> set = entry.getValue();

            if( set.size() > 1 ) {
                final String hashString = entry.getKey();

                assert ! this.mapHashFiles.containsKey( hashString ) : "Error already found hash for a other size !";

                this.mapHashFiles.put( hashString, set );
                this.duplicateSetsCount++;
                this.duplicateFilesCount += set.size();
            } else {
                set.clear();
            }
            entryIterator.remove();
        }
     }

    /** handle pass 2 for a single file
     * @return */
    private String handlePass2ForASingleFile( final File file )
    {
        notify_analysisStart( file );

        String hashString = null;

        try {
            final FileDigest fd = this.fileDigests[ 0 ];

            fd.compute( file, this.fileDigestListener  );

            hashString = fd.digestString();
            }
        catch( final IOException ioe ) {
            notify_ioError( file, ioe );
            }
        catch( final CancelRequestException e ) { // $codepro.audit.disable logExceptions
            this.cancelProcess = true;
            }
        finally {
            notify_analysisDone( file, hashString );
        }
        return hashString;
    }

    private void notify_analysisStart( final File file )
    {
        for(final DuplicateFileFinderEventListener l:this.listeners) {
            l.analysisStart( file );
        }
    }

    private void notify_ioError( final File file, final IOException ioe )
    {
        for(final DuplicateFileFinderEventListener l:this.listeners) {
            l.ioError( file, ioe );
        }
    }

    private void notify_analysisStatus( final File file, final int length )
    {
        for(final DuplicateFileFinderEventListener l:this.listeners) {
            l.analysisStatus( file, length );
        }
    }

    private void notify_analysisDone( final File file, final String hashString )
    {
        for(final DuplicateFileFinderEventListener l:this.listeners) {
            l.analysisDone( file, hashString );
        }
    }

    private void checkState( final State expectedState )
    {
        if( this.state != expectedState ) {
            throw new IllegalStateException( "Current State:" + this.state + " excepting:" + expectedState );
        }
    }
}
