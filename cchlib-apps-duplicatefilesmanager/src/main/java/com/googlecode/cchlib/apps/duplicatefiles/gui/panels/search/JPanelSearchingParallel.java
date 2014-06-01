package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileBuilder;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.MessageDigestFileBuilder;
import com.googlecode.cchlib.util.duplicate.stream.ParallelDuplicateFileFinder;
import com.googlecode.cchlib.util.duplicate.stream.PrepareDuplicateFile;

public class JPanelSearchingParallel extends JPanelSearchingParallelUpdateCurrentFile
{
    private static final class Stats {
        private final int  filesCount;
        private final long bytesCount;
        private final int  setsCount;

        public Stats( final int filesCount, final long bytesCount, final int setsCount )
        {
            this.filesCount = filesCount;
            this.bytesCount = bytesCount;
            this.setsCount = setsCount;
        }

        public final int getFilesCount()
        {
            return filesCount;
        }

        public final long getBytesCount()
        {
            return bytesCount;
        }

        public final int getSetsCount()
        {
            return setsCount;
        }
    }

    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingParallel.class );
    private static final int MIN_SET_SIZE = 2;
    private final Object lockpass1StatsAdd = new Object();

    private final AtomicInteger pass2FilesCount = new AtomicInteger();
    private final AtomicLong    pass2BytesCount = new AtomicLong();
    private final AtomicInteger pass2SetsCount  = new AtomicInteger();

    private Pass    displayPass;
    private boolean displayRunning;
    private boolean cancel;

    /**
     * Create the panel.
     */
    public JPanelSearchingParallel()
    {
        super( AppToolKitService.getInstance().getAppToolKit().getPreferences().getNumberOfThreads() );
    }

    @Override
    public void startScan(
        final String                            messageDigestAlgorithm,
        final int                               messageDigestBufferSize,
        final boolean                           ignoreEmptyFiles,
        final Collection<File>                  entriesToScans,
        final Collection<File>                  entriesToIgnore,
        final FileFilterBuilders                fileFilterBuilders,
        final Map<String, Set<KeyFileState>>    duplicateFiles
        )
    {
        this.cancel = false;

        beforePass1( duplicateFiles );

        final FileVisitor<Path> fileVisitor = newFileVisitorPass1( entriesToIgnore, fileFilterBuilders );

        updateDisplayThread();

        LOGGER.info( "pass1" );
        final Map<Long, Set<File>> computePass1 = runPass1( ignoreEmptyFiles, entriesToScans, fileVisitor );
        LOGGER.info( "pass1 done" );

        LOGGER.info( "pass1: getPass1BytesCount() = " + super.getPass1BytesCount() );
        LOGGER.info( "pass1: getPass1FilesCount()  = " + super.getPass1FilesCount() );

        purgePass1AndUpdateStats( computePass1 ); // Only needed for display

        LOGGER.info( "after purge: getPass1BytesCount() = " + super.getPass1BytesCount() );
        LOGGER.info( "after purge: getPass1FilesCount()  = " + super.getPass1FilesCount() );

        afterPass1();

        LOGGER.info( "beforePass2: getPass1FilesCount() = " + getPass1FilesCount() );
        LOGGER.info( "beforePass2: getPass1BytesCount() = " + getPass1BytesCount() );
        LOGGER.info( "pass1 complete" );

        beforePass2();

        final Map<String, Set<File>> computePass2 = runPass2( messageDigestAlgorithm, messageDigestBufferSize, computePass1 );

        purgePass2AndUpdateStats( computePass2 );

        afterPass2();

        populateDuplicate(duplicateFiles,computePass2);

        afterPopulate();
    }

    private <T> Stats purge( final Map<T, Set<File>> mapSet )
    {
        final Iterator<Entry<T, Set<File>>> iterator = mapSet.entrySet().iterator();

        int  filesCount = 0;
        long bytesCount = 0L;
        int  setsCount  = 0;

        while( iterator.hasNext() ) {
            final Set<File> set = iterator.next().getValue();

            if( (set == null) || (set.size() < MIN_SET_SIZE) ) {
                iterator.remove();
            } else {
                filesCount += set.size();
                bytesCount += set.stream().mapToLong( f -> f.length() ).sum();
                setsCount++;
            }
        }

        return new Stats( filesCount, bytesCount, setsCount );
    }

    private void purgePass1AndUpdateStats( final Map<Long, Set<File>> computePass1 )
    {
        final Stats stats = purge( computePass1 );

        setPass1FilesCount( stats.getFilesCount() );
        setPass1BytesCount( stats.getBytesCount() );
    }

    private void purgePass2AndUpdateStats( final Map<String, Set<File>> computePass2 )
    {
        final Stats stats = purge( computePass2 );

        pass2FilesCount.set( stats.getFilesCount() );
        pass2BytesCount.set( stats.getBytesCount() );
        pass2SetsCount.set( stats.getSetsCount() );
    }


    private void beforePass1( final Map<String, Set<KeyFileState>> duplicateFiles )
    {
        displayPass = Pass.PASS1;

        clearDisplayFiles();
        setPass1FilesCount( 0 );
        setPass1BytesCount( 0 );
        pass2FilesCount.set( 0 );
        pass2BytesCount.set( 0 );
        pass2SetsCount.set( 0 );

        super.clearErrors();

        duplicateFiles.clear();

        super.prepareScan();
    }

    private void afterPass1()
    {
        getjProgressBarFiles().setIndeterminate( false );
        getjProgressBarOctets().setIndeterminate( false );
        setCurrentFileLabels( getTxtCurrentFile() );
        clearCurrentFiles();
    }

    private void beforePass2()
    {
        clearDisplayFiles();

        //setDisplayFile( null );
        clearCurrentFiles();
        displayPass = Pass.PASS2;

        getjProgressBarFiles().setMaximum( getPass1FilesCount() );
        getjProgressBarOctets().setMaximum( (int)(getPass1BytesCount()/1024) );
    }

    private void afterPass2()
    {
        //setDisplayFile( null );
        clearCurrentFiles();
        displayRunning = false;

        //Stop display !
        displayRunning = false;

        //Be sure to have a real ending display !
        updateDisplay();
    }

    private void afterPopulate()
    {
        //setDisplayFile( null );
        clearCurrentFiles();
        clearDisplayFiles();

        //Be sure to have a coherent ending display !
        //(Include ignored files) -> FIXME expecting sizes should be adapt instead of result
        pass2FilesCount.set( getPass1FilesCount() );
        pass2BytesCount.set( getPass1BytesCount() );
        updateDisplay();
        displayPass = null; // No more update !

        getAppToolKit().setEnabledJButtonCancel( false );

        // Populate next panel now !!
        getAppToolKit().initComponentsJPanelConfirm();
    }

    private Map<String, Set<File>> runPass2( //
            final String               messageDigestAlgorithm, //
            final int                  messageDigestBufferSize, //
            final Map<Long, Set<File>> computePass1 //
            )
    {
        try {
            final MessageDigestFileBuilder    messageDigestFileBuilder = newMessageDigestFileBuilder( messageDigestAlgorithm, messageDigestBufferSize );
            final DuplicateFileFinderListener listener                 = newDuplicateFileFinderListener();

            final DuplicateFileFinder duplicateFileFinder = new ParallelDuplicateFileFinder(messageDigestFileBuilder, listener, getNumberOfThreads() );

            return duplicateFileFinder.computeHash( computePass1 );
        }
        catch( IllegalStateException | NoSuchAlgorithmException | InterruptedException | ExecutionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return Collections.emptyMap();
        }
    }

    private DuplicateFileFinderListener newDuplicateFileFinderListener()
    {
        return new GlobalDuplicateFileFinderListener( this );
    }

    boolean isCancel()
    {
        return cancel;
    }

    private MessageDigestFileBuilder newMessageDigestFileBuilder( //
            final String messageDigestAlgorithm, //
            final int messageDigestBufferSize //
            ) throws NoSuchAlgorithmException
    {
        return MessageDigestFileBuilder.newMessageDigestFileBuilder( messageDigestAlgorithm, messageDigestBufferSize );
    }

    private Map<Long, Set<File>> runPass1( final boolean ignoreEmptyFiles, final Collection<File> entriesToScans, final FileVisitor<Path> fileVisitor )
    {
        Map<Long, Set<File>> computePass1;

        try {
            final PrepareDuplicateFile prepareDuplicateFile = DuplicateFileBuilder.createFromFileVisitor( //
                    entriesToScans.stream().map( f -> f.toPath() ), // FIXME use parallleStream() here !!!
                    fileVisitor, //
                    EnumSet.noneOf( FileVisitOption.class ), //
                    Integer.MAX_VALUE, //
                    ignoreEmptyFiles //
                    );
            computePass1 = prepareDuplicateFile.compute();
        }
        catch( final IOException e ) {
            LOGGER.error( "runPass1", e ); // TODO display error on GUI

            computePass1 = Collections.emptyMap();
        }
        return computePass1;
    }

    private FileVisitor<Path> newFileVisitorPass1( //
            final Collection<File>   entriesToIgnore, //
            final FileFilterBuilders fileFilterBuilders //
            )
    {
        // FileFilter and Listener for pass 1
        final FileFilter dirFilter = createDirectoriesFileFilter(
                fileFilterBuilders
                );
        final FileFilter fileFilter = createFilesFileFilter(
                fileFilterBuilders
                );

        return new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs ) throws IOException
            {
                if( dirFilter.accept( dir.toFile() ) ) {
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.SKIP_SUBTREE;
            }

            @Override
            public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs ) throws IOException
            {
                {
                    final File fileFile = file.toFile();

                    if( fileFilter.accept( fileFile ) ) {
                        pass1StatsAdd( fileFile );

                        return FileVisitResult.CONTINUE;
                    }
                    return FileVisitResult.SKIP_SUBTREE;
                 }
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

        };
    }

    private void pass1StatsAdd( final File fileFile )
    {
        synchronized( lockpass1StatsAdd  ) {
            super.setPass1FilesCount( super.getPass1FilesCount() + 1 );
            super.setPass1BytesCount( super.getPass1BytesCount() + fileFile.length() );
            setPass1DisplayFile( fileFile );
        }
    }

    private void populateDuplicate( final Map<String, Set<KeyFileState>> duplicateFiles, final Map<String, Set<File>> filesMap )
    {
        for(final Map.Entry<String,Set<File>> e:filesMap.entrySet()) {
            final String              k    = e.getKey();
            final Set<File>           sf   = e.getValue();
            final Set<KeyFileState>   skfs = new HashSet<>();

            for(final File f:sf) {
                skfs.add( new KeyFileState(k,f) );
            }

            duplicateFiles.put( k, skfs );
        }
    }

    private void updateDisplayThread()
    {
        displayRunning  = true;
        //setDisplayFile( null );
        clearCurrentFiles();

        new Thread( ( ) -> {
            while(displayRunning) {
                updateDisplay();

                try {
                    Thread.sleep(300);
                    }
                catch( final InterruptedException e ) {
                    return;
                    }
                }
        }, "updateDisplayThread()" ).start();
    }

    private void updateDisplay()
    {
        updateCurrentFilesDisplay();

        final Locale locale = getAppToolKit().getValidLocale();

        getjLabelDuplicateSetsFoundValue().setText(
                String.format(
                    locale,
                    getTxtDuplicateSetsFound(),
                    Integer.valueOf( pass2SetsCount.get() )
                    )
                );
        getjLabelDuplicateFilesFoundValue().setText(
                String.format(
                    locale,
                    getTxtDuplicateFilesFound(),
                    Integer.valueOf( pass2FilesCount.get() )
                    )
                );

        switch( displayPass ) {
            case PASS1:
                updateDisplayPass1( locale );
                break;
            case PASS2:
                updateDisplayPass2( locale );
                break;
        }
    }

    private void updateDisplayPass1(final Locale locale )
    {
        getjProgressBarFiles().setString(
                String.format(
                    locale,
                    getTxtNumberOfFilesProcessed(),
                    Integer.valueOf( getPass1FilesCount() )
                    )
                );
        getjProgressBarOctets().setString(
            String.format(
                locale,
                getTxtOctectsToCheck(),
                Long.valueOf( getPass1BytesCount() )
                )
            );
    }

    private void updateDisplayPass2( final Locale locale )
    {
        //jLabelBytesReadFromDisk.setText( String.format( txtBytesReadFromDisk, pass2BytesCount ) );
        getjProgressBarFiles().setValue( pass2FilesCount.get() );
        getjProgressBarFiles().setString( String.format( locale, "%,d / %,d", Integer.valueOf( pass2FilesCount.get() ), Integer.valueOf( getPass1FilesCount() ) ) );
        // jProgressBarOctets.setValue( Math.round( pass2BytesCount/1024) );
        getjProgressBarOctets().setValue( (int)(pass2BytesCount.get() / 1024) );
        getjProgressBarOctets().setString( String.format( locale, "%,d / %,d", Long.valueOf( pass2BytesCount.get() ), Long.valueOf( getPass1BytesCount() ) ) );
    }

    @Override
    public void cancelProcess()
    {
        this.cancel = true;
    }

    public void pass2BytesCountAdd( final long lengthToInc )
    {
        pass2BytesCount.addAndGet( lengthToInc );
    }

    public void pass2FilesCountInc()
    {
        pass2FilesCount.incrementAndGet();
    }
}
