package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.DuplicateHelpers;
import com.googlecode.cchlib.util.duplicate.XMessageDigestFile;

public class ParallelDuplicateFileFinder extends DuplicateFileFinderUsingStream {

    private static class MapEntry implements Entry<String, File> {

        private final String key;
        private File file;

        public MapEntry( final String key, final File file )
        {
            this.key = key;
            this.file = file;
        }

        @Override
        public String getKey()
        {
            return key;
        }

        @Override
        public File getValue()
        {
            return this.file;
        }

        @Override
        public File setValue( final File file )
        {
            final File oldvalue = this.file;
            this.file = file;
            return oldvalue;
        }
    }

    private final int nThreads;

    public ParallelDuplicateFileFinder( //
        final MessageDigestFileBuilder messageDigestFileBuilder,
        final DuplicateFileFinderListener listener, //
        final int nThreads //
        )
            throws InvalidParameterException
    {
        super( messageDigestFileBuilder, listener );

        assert nThreads > 0;

        this.nThreads = nThreads;
    }

    @Override
    @NeedDoc
    public synchronized Map<String, Set<File>> computeHash( //
            @Nonnull final Map<Long, Set<File>> mapLengthFiles //
            ) throws IllegalStateException, NoSuchAlgorithmException, InterruptedException, ExecutionException
    {
        if( mapLengthFiles == null ) {
            throw new InvalidParameterException( "mapSet is null" );
        }
        if( getListener().isCancel() ) {
            return Collections.emptyMap();
        }

        final ExecutorService                      executor = Executors.newFixedThreadPool( nThreads );
        final List<Future<Entry<String,File>>>     results = new ArrayList<>();

        final Map<String, Set<File>> mapHashFiles = new HashMap<>( computeMapSize( mapLengthFiles ) );

        for( final Set<File> set : mapLengthFiles.values() ) {
            if( set.size() > 1 ) {
                for( final File file : set ) {
                    final XMessageDigestFile xMessageDigestFile = newMessageDigestFile();
                    final Future<Entry<String,File>> submit = executor.submit( ( ) -> computeHashForEntry( xMessageDigestFile, file ) );

                    results.add( submit );
                }
            }
        }

        for(final Future<Entry<String,File>> result : results ) {
            add( mapHashFiles, result.get() );
        }
        executor.shutdown();

        if( getListener().isCancel() ) {
            return Collections.emptyMap();
        }
        DuplicateHelpers.removeNonDuplicate( mapHashFiles );

        return mapHashFiles;
    }

    private Entry<String,File> computeHashForEntry( final XMessageDigestFile xMessageDigestFile, final File file )
        throws CancelRequestException
    {
        final String key = computeHashForFile( xMessageDigestFile, file );

        return new MapEntry( key, file );
    }

    private static void add( final Map<String, Set<File>> mapHashFiles, final Entry<String,File> hashFile )
    {
        final String key = hashFile.getKey();
        Set<File> set = mapHashFiles.get( key );

        if( set == null ) {
            set = new HashSet<>();
            mapHashFiles.put( key, set );
        }

        set.add( hashFile.getValue() );
   }

}
