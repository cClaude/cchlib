package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderEventListener;
import com.googlecode.cchlib.util.duplicate.DuplicateHelpers;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestListener;

@NeedDoc
public class DuplicateFileFinderUsingStream
{
    /* *
     * @ deprecated use {@link FileDigestFactory} instead
     * /
    @ Deprecated
    public interface MessageDigestFileBuilder {
        XMessageDigestFile newMessageDigestFile() throws NoSuchAlgorithmException;

        /** @see MessageDigest#getInstance * /
        public static MessageDigestFileBuilder newMessageDigestFileBuilder( final String algorithm, final int bufferSize ) throws NoSuchAlgorithmException {
            return ( ) -> new XMessageDigestFile( algorithm, bufferSize );
        }
    }

    @ Deprecated
    public interface DuplicateFileFinderListener extends DigestEventListener {
//        void computeDigest( long threadId, File file );
//
//        @Override
//        default void computeDigest( final File file ) {
//            final long threadId = Thread.currentThread().getId();
//
//            computeDigest( threadId, file );
//        }
    } */

    //private final MessageDigestFileBuilder messageDigestFileBuilder;

    private final FileDigestFactory                 fileDigestFactory;
    private final FileDigestListener                fileDigestListener;
    private final DuplicateFileFinderEventListener  listener;

    @NeedDoc
    public DuplicateFileFinderUsingStream(
        @Nonnull final FileDigestFactory                fileDigestFactory,
        @Nonnull final DuplicateFileFinderEventListener listener
        ) throws InvalidParameterException
    {
        if( fileDigestFactory == null ) {
            throw new InvalidParameterException( "fileDigestFactory is null" );
        }
        if( listener == null ) {
            throw new InvalidParameterException( "listener is null" );
        }

        this.fileDigestFactory  = fileDigestFactory;
        this.listener           = listener;
        this.fileDigestListener = new FileDigestListener() {

            @Override
            public boolean isCancel()
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void computeDigest( final File file, final int length )
            {
                // TODO Auto-generated method stub

            }
        };
    }

    @NeedDoc
    public synchronized Map<String, Set<File>> computeHash(@Nonnull final Map<Long, Set<File>> mapLengthFiles )
        throws IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        if( mapLengthFiles == null ) {
            throw new InvalidParameterException( "mapSet is null" );
        }
        if( this.fileDigestListener.isCancel() ) {
            return Collections.emptyMap();
        }

        final Map<String, Set<File>> mapHashFiles = new HashMap<>( computeMapSize( mapLengthFiles ) );
        //final XMessageDigestFile xMessageDigestFile = newMessageDigestFile();
        final FileDigest fileDigest = this.fileDigestFactory.newInstance();

        for( final Set<File> set : mapLengthFiles.values() ) {
            if( set.size() > 1 ) {
                final Map<String, Set<File>> hashForSet = computeHashForSet( fileDigest, set );

                addAll( mapHashFiles, hashForSet );
            }
        }

        if( this.fileDigestListener.isCancel() ) {
            return Collections.emptyMap();
        }
        DuplicateHelpers.removeNonDuplicate( mapHashFiles );

        return mapHashFiles;
    }

    protected FileDigest newMessageDigestFile() throws NoSuchAlgorithmException
    {
        return this.fileDigestFactory.newInstance();
    }

    protected static int computeMapSize( final Map<Long, Set<File>> mapLengthFiles )
    {
        final int size = mapLengthFiles.size();
        if( size <= 0 ) {
            return 0;
        } else if( size < 100 ) {
            return size;
        } else {
            return size / 10;
        }
    }

    private static void addAll( final Map<String, Set<File>> mapHashFiles, final Map<String, Set<File>> hashForSet )
    {
        for( final Entry<String, Set<File>> entry : hashForSet.entrySet() ) {
            assert ! mapHashFiles.containsKey( entry.getKey() );

            if( entry.getValue().size() > 1 ) {
                mapHashFiles.put( entry.getKey(), entry.getValue() );
            }
        }
    }

    private Map<String, Set<File>> computeHashForSet( //
            final FileDigest fileDigest,
            final Set<File> filesSet
            )
    {
        final Map<String, Set<File>> hashs = new HashMap<>();

        for( final File file : filesSet ) {
            try {
                final String hash = computeHashForFile( fileDigest, file );

                if( hash != null ) {
                    Set<File> set = hashs.get( hash );

                    if( set == null ) {
                        set = new HashSet<>();
                        hashs.put( hash, set );
                    }
                    set.add( file );

                } // else file can not be read
            }
            catch( final CancelRequestException e ) {
                assert this.fileDigestListener.isCancel();

                return Collections.emptyMap();
            }
        }

        return hashs;
    }

    protected String computeHashForFile(
        final FileDigest fileDigest,
        final File file
        ) throws CancelRequestException
    {
        this.listener.analysisStart( file );

        try {
            fileDigest.computeFile( file, this.fileDigestListener );

            final String hashString = fileDigest.digestString();

            this.listener.analysisDone( file, hashString );

            return hashString;
            }
        catch(final IOException ioe) {
            this.listener.ioError( file, ioe );
            return null;
            }
    }

    public DuplicateFileFinderEventListener getListener()
    {
        return this.listener;
    }
}
