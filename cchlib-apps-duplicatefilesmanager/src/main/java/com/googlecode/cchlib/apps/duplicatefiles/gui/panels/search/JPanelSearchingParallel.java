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
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileBuilder;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.MessageDigestFileBuilder;
import com.googlecode.cchlib.util.duplicate.stream.ParallelDuplicateFileFinder;
import com.googlecode.cchlib.util.duplicate.stream.PrepareDuplicateFile;

@I18nName("duplicatefiles.JPanelSearching")
public class JPanelSearchingParallel extends JPanelSearching
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingParallel.class );

//    private DuplicateFileCollector  duplicateFC;

    @I18nString private final String txtDuplicateSetsFound = "Duplicate sets found: %,d";
    @I18nString private final String txtDuplicateFilesFound = "Duplicate files found: %,d";
    @I18nString private final String txtNumberOfFilesProcessed = "Number of files processed: %,d";
    @I18nString private final String txtOctectsToCheck = "Octects to check: %,d";

    private int     pass2CountFile;
    private long    pass2BytesCount;

    private int     displayPass;
    private boolean displayRunning;
    private boolean cancel;

    /**
     * Create the panel.
     */
    public JPanelSearchingParallel()
    {
        super();
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
        afterPass1();

        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1CountFile = " + getPass1CountFile() );
        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1BytesCount = " + getPass1BytesCount() );

        LOGGER.info( "doScanPass1Prepare + doScanPass1: done" );

        beforePass2();

        final Map<String, Set<File>> computePass2 = runPass2( messageDigestAlgorithm, messageDigestBufferSize, computePass1 );

        afterPass2();

        populateDuplicate(duplicateFiles,computePass2);

        afterPopulate();
    }

    private void beforePass1( final Map<String, Set<KeyFileState>> duplicateFiles )
    {
        displayPass = 1;

        setPass1CountFile( 0 );
        setPass1BytesCount( 0 );
        pass2CountFile = 0;
        pass2BytesCount = 0;

        super.clear();

        duplicateFiles.clear();

        super.prepareScan();
    }

    private void afterPass1()
    {
        //        setPass1CountFile( stats.getPass2Files() );
        //      setPass1BytesCount( stats.getPass2Bytes() );

        //jProgressBarFiles.setStringPainted( true );
        getjProgressBarFiles().setIndeterminate( false );
        getjProgressBarOctets().setIndeterminate( false );
        getjLabelCurrentFile().setText( txtCurrentFile );
        getjTextFieldCurrentFile().setText( "" );
    }

    private void beforePass2()
    {
        setDisplayFile( null );
        displayPass = 2;

        getjProgressBarFiles().setMaximum( getPass1CountFile() );
        getjProgressBarOctets().setMaximum( (int)(getPass1BytesCount()/1024) );
    }

    private void afterPass2()
    {
        setDisplayFile( null );
        displayRunning = false;
        pass2CountFile = getPass1CountFile();
        pass2BytesCount = getPass1BytesCount();
        displayRunning = false;
    }

    private void afterPopulate()
    {
        //Stop display !
        displayRunning = false;
        setDisplayFile( null );
        displayPass = 2;

        //Be sure to have a real ending display !
        updateDisplay();

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
            final DuplicateFileFinderListener listener                = newDuplicateFileFinderListener();

            final DuplicateFileFinder duplicateFileFinder = new ParallelDuplicateFileFinder(messageDigestFileBuilder, listener);

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
        return new DuplicateFileFinderListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void computeDigest( final File file )
            {
                setDisplayFile( file );
                pass2CountFile++;
            }

            @Override
            public void computeDigest( final File file, final long length )
            {
                pass2BytesCount += length;
            }

            @Override
            public void ioError( final IOException e, final File file )
            {
                LOGGER.warn(
                        String.format(
                            "IOException %s : %s\n",
                            file,
                            e.getMessage()
                            )
                        );

                    final Vector<Object> v = new Vector<>();
                    v.add( file );
                    v.add( e.getLocalizedMessage() );
                    getTableModelErrorList().addRow( v );
            }

            @Override
            public boolean isCancel()
            {
                return JPanelSearchingParallel.this.isCancel();
            }};
    }

    private boolean isCancel()
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
                    entriesToScans.stream().map( f -> f.toPath() ), //
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
                    if( fileFilter.accept( file.toFile() ) ) {
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
        displayPass     = 1;
        setDisplayFile( null );

        new Thread( ( ) -> {
            while(displayRunning) {
                updateDisplay();

                try {
                    Thread.sleep(300); // $codepro.audit.disable disallowSleepInsideWhile
                    }
                catch( final InterruptedException e ) {
                    return;
                    }
                }
        }, "updateDisplayThread()" ).start();
    }

    private void updateDisplay()
    {
        if( getDisplayFile() != null ) {
            getjTextFieldCurrentFile().setText( getDisplayFile().getAbsolutePath() );
            }
        else {
            getjTextFieldCurrentFile().setText( "" );
        }
        final Locale locale = getAppToolKit().getValidLocale();

        getjLabelDuplicateSetsFoundValue().setText(
                String.format(
                    locale,
                    txtDuplicateSetsFound,
                    Integer.valueOf( 0/*duplicateFC.getDuplicateSetsCount()*/ )
                    )
                );
        getjLabelDuplicateFilesFoundValue().setText(
                String.format(
                    locale,
                    txtDuplicateFilesFound,
                    Integer.valueOf( 1/*duplicateFC.getDuplicateFilesCount()*/ )
                    )
                );

        if( displayPass == 1 ) {
            getjProgressBarFiles().setString(
                    String.format(
                        locale,
                        txtNumberOfFilesProcessed,
                        Integer.valueOf( getPass1CountFile() )
                        )
                    );
            getjProgressBarOctets().setString(
                String.format(
                    locale,
                    txtOctectsToCheck,
                    Long.valueOf( getPass1BytesCount() )
                    )
                );
        }
        else {
//            jLabelBytesReadFromDisk.setText(
//                String.format(
//                    txtBytesReadFromDisk,
//                    pass2BytesCount
//                    )
//                );
            getjProgressBarFiles().setValue( pass2CountFile );
            getjProgressBarFiles().setString( String.format( locale, "%,d / %,d", Integer.valueOf( pass2CountFile ), Integer.valueOf( getPass1CountFile() ) ) );
            //jProgressBarOctets.setValue( Math.round( pass2BytesCount/1024) );
            getjProgressBarOctets().setValue( (int)( pass2BytesCount/1024 ) );
            getjProgressBarOctets().setString( String.format( locale, "%,d / %,d", Long.valueOf( pass2BytesCount), Long.valueOf( getPass1BytesCount() ) ) );
        }
    }

    @Override
    public void cancelProcess()
    {
        this.cancel = true;
    }
}
