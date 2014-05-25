package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
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
import com.googlecode.cchlib.util.duplicate.DigestEventListener;
import com.googlecode.cchlib.util.duplicate.DuplicateHelpers;
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;

@NeedDoc
public class DuplicateFileFinder
{
    @NeedDoc
    public interface MessageDigestFileBuilder {
        MessageDigestFile newMessageDigestFile() throws NoSuchAlgorithmException;

        /** @see MessageDigest#getInstance */
        public static MessageDigestFileBuilder newMessageDigestFileBuilder( final String algorithm, final int bufferSize ) throws NoSuchAlgorithmException {
            return ( ) -> new MessageDigestFile( algorithm, bufferSize );
        }
    }

    @NeedDoc
    public interface DuplicateFileFinderListener extends DigestEventListener {
//        void computeDigest( long threadId, File file );
//
//        @Override
//        default void computeDigest( final File file ) {
//            final long threadId = Thread.currentThread().getId();
//
//            computeDigest( threadId, file );
//        }
    }

    private final MessageDigestFileBuilder messageDigestFileBuilder;
    private final DuplicateFileFinderListener listener;

    @NeedDoc
    public DuplicateFileFinder(
        @Nonnull final MessageDigestFileBuilder      messageDigestFileBuilder,
        @Nonnull final DuplicateFileFinderListener   listener
        ) throws InvalidParameterException
    {
        if( messageDigestFileBuilder == null ) {
            throw new InvalidParameterException( "messageDigestFileBuilder is null" );
        }
        if( listener == null ) {
            throw new InvalidParameterException( "listener is null" );
        }

        this.messageDigestFileBuilder   = messageDigestFileBuilder;
        this.listener                   = listener;
    }

    @NeedDoc
    public synchronized Map<String, Set<File>> computeHash(@Nonnull final Map<Long, Set<File>> mapLengthFiles )
        throws IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        if( mapLengthFiles == null ) {
            throw new InvalidParameterException( "mapSet is null" );
        }
        if( listener.isCancel() ) {
            return Collections.emptyMap();
        }

        final Map<String, Set<File>> mapHashFiles = new HashMap<>( computeMapSize( mapLengthFiles ) );
        final MessageDigestFile messageDigestFile = newMessageDigestFile();

        for( final Set<File> set : mapLengthFiles.values() ) {
            if( set.size() > 1 ) {
                final Map<String, Set<File>> hashForSet = computeHashForSet( messageDigestFile, set );

                addAll( mapHashFiles, hashForSet );
            }
        }

        if( listener.isCancel() ) {
            return Collections.emptyMap();
        }
        DuplicateHelpers.removeNonDuplicate( mapHashFiles );

        return mapHashFiles;
    }

    protected MessageDigestFile newMessageDigestFile() throws NoSuchAlgorithmException
    {
        return messageDigestFileBuilder.newMessageDigestFile();
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
            final MessageDigestFile mdf,
            final Set<File> filesSet
            )
    {
        final Map<String, Set<File>> hashs = new HashMap<>();

        for( final File file : filesSet ) {
            try {
                final String hash = computeHashForFile( mdf, file );

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
                assert listener.isCancel();

                return Collections.emptyMap();
            }
        }

        return hashs;
    }

    protected String computeHashForFile(
        final MessageDigestFile mdf,
        final File file
        ) throws CancelRequestException
    {
        listener.computeDigest( file );

        try {
            mdf.compute( file, listener );

            final String hashString = mdf.digestString();

            listener.hashString( file, hashString );

            return hashString;
            }
        catch(final IOException e) {
            listener.ioError( e, file );
            return null;
            }
    }

    public DuplicateFileFinderListener getListener()
    {
        return listener;
    }
}
