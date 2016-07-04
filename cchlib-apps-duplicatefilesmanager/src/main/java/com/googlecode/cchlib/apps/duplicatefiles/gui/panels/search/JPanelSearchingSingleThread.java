package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.io.FileIterable;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.InitialStatus;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.Status;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderEventListener;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderHelper;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/***
 * This class is use if number of Thread is equal to 1
 */
public class JPanelSearchingSingleThread extends JPanelSearching
{
    /** TODO try to use {@link GlobalDuplicateFileFinderListener} instead */
    private final static class MyDuplicateFileFinderEventListener implements DuplicateFileFinderEventListener {
        private static final long serialVersionUID = 1L;
        private static final Logger LOGGER = Logger.getLogger( JPanelSearchingSingleThread.class );
        private final JPanelSearchingSingleThread jPanelSearching;

        public MyDuplicateFileFinderEventListener( final JPanelSearchingSingleThread jPanelSearchingSingleThread )
        {
            this.jPanelSearching = jPanelSearchingSingleThread;
        }

        @Override
         public void analysisStart( @Nonnull final File file )
         {
            this.jPanelSearching.setPass1DisplayFile( file );

            this.jPanelSearching.pass2CountFile++;
         }

        @Override
         public void analysisStatus( @Nonnull final File file, final long length )
         {
            this.jPanelSearching.pass2BytesCount += length;
         }

        @Override
         public void analysisDone( @Nonnull final File file, @Nonnull final String hashString )
         {
             if( LOGGER.isTraceEnabled() ) {
                 LOGGER.trace( "Hash for " + file + " is " + hashString );
             }
         }

        @Override
         public void ioError( @Nonnull final File file, @Nonnull final IOException ioe )
         {
             LOGGER.warn( String.format( "IOException %s : %s\n", file,
                     ioe.getMessage() ) );

             this.jPanelSearching.getTableModelErrorList().addRow( file, ioe );
         }

        @Override
         public boolean isCancel()
         {
            if( this.jPanelSearching.dff != null ) {
                return this.jPanelSearching.dff.isCancelProcess();
               }

            return false;
         }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingSingleThread.class );
    private static final int THREAD_NUMBER = 0;
    private static final int NUMBER_OF_THREADS = 1;

    private DuplicateFileFinder dff;

    @I18nString private String txtDuplicateSetsFound;
    @I18nString private String txtDuplicateFilesFound;
    @I18nString private String txtNumberOfFilesProcessed;
    @I18nString private String txtOctectsToCheck;

    private int     pass2CountFile;
    private long    pass2BytesCount;

    private int     displayPass;
    private boolean displayRunning;
    private File    displayFile;

    private final DuplicateFileFinderEventListener eventListener = new MyDuplicateFileFinderEventListener( this );
    private long pass1currentTimeMillis;
    private long pass2currentTimeMillis;

    /**
     * Create the panel.
     */
    public JPanelSearchingSingleThread()
    {
        super( NUMBER_OF_THREADS );

        initI18N();
    }

    private void initI18N()
    {
        this.txtDuplicateSetsFound = "%,d";
        this.txtDuplicateFilesFound = "%,d";
        this.txtNumberOfFilesProcessed = "Number of files processed: %,d";
        this.txtOctectsToCheck = "Octects to check: %,d";
    }

    @Override
    protected void setPass1DisplayFile( final File file )
    {
        if( file != null ) {
            setCurrentFile( file.getAbsolutePath(), THREAD_NUMBER );
        } else {
            clearCurrentFile( THREAD_NUMBER );
        }
    }

    @Override
    public void startScan( final ScanParams scanParams )
    {
        try {
            final FileDigestFactory fileDigestFactory = new DefaultFileDigestFactory( //
                    scanParams.getMessageDigestAlgorithm(), //
                    scanParams.getMessageDigestBufferSize() );

            prepareScan( //
                    fileDigestFactory, //
                    scanParams.getMaxParallelFilesPerThread(),
                    scanParams.isIgnoreEmptyFiles() );
        }
        catch( final NoSuchAlgorithmException e ) {
            LOGGER.fatal( "Bad messageDigestAlgorithm: " + scanParams.getMessageDigestAlgorithm(), e );
            // This exception should not occur.
            throw new RuntimeException( e );
        }

        doScan( scanParams );
    }

    private void doScanPass1( //
            final Iterable<File> rootFiles, //
            final FileFilter dirFilter, //
            final FileFilter fileFilter )
    {
        LOGGER.debug( "doScanPass1: begin" );
        for( final File rootFile : rootFiles ) {
            final Iterable<File> files;

            if( rootFile.isDirectory() ) {
                LOGGER.info( "doScanPass1: examin folder:" + rootFile );
                LOGGER.info( "doScanPass1: examin fileFilter:" + fileFilter );
                LOGGER.info( "doScanPass1: examin dirFilter:" + dirFilter );

                files = new FileIterable(
                        rootFile,
                        fileFilter,
                        dirFilter
                        );
                }
            else {
                LOGGER.info( "doScanPass1: examin file:" + rootFile );

                files = Collections.singleton( rootFile );
            }

            this.dff.addFiles( files );
        }

        LOGGER.debug( "doScanPass1: end" );
    }

    private void doScanPass2()
    {
        this.displayPass = 2;

        getjProgressBarFiles().setMaximum( getPass1FilesCount() );
        getjProgressBarOctets().setMaximum( (int)(getPass1BytesCount()/1024) );

        this.dff.find();

        setDisplayFile( null );

        this.displayRunning  = false;
        this.pass2CountFile  = getPass1FilesCount();
        this.pass2BytesCount = getPass1BytesCount();
        this.displayRunning  = false;
    }

    private void updateDisplayThread()
    {
        this.displayRunning  = true;
        this.displayPass     = 1;

        setDisplayFile( null );

        new Thread( ( ) -> {
            while(this.displayRunning) {
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

    private void setDisplayFile( final File displayFile )
    {
        this.displayFile = displayFile;
    }

    private File getDisplayFile()
    {
        return this.displayFile;
    }

    private void updateDisplay()
    {
        setPass1DisplayFile( getDisplayFile() );

        final Locale locale = getAppToolKit().getValidLocale();

        final Status status = this.dff.getStatus();
        getjLabelDuplicateSetsFoundValue().setText(
                String.format(
                    locale,
                    this.txtDuplicateSetsFound,
                    Integer.valueOf( status.getSets() )
                    )
                );
        getjLabelDuplicateFilesFoundValue().setText(
                String.format(
                    locale,
                    this.txtDuplicateFilesFound,
                    Integer.valueOf( status.getFiles() )
                    )
                );

        // TODO : status.getBytes()

        if( this.displayPass == 1 ) {
            getjProgressBarFiles().setString(
                    String.format(
                        locale,
                        this.txtNumberOfFilesProcessed,
                        Integer.valueOf( getPass1FilesCount() )
                        )
                    );
            getjProgressBarOctets().setString(
                String.format(
                    locale,
                    this.txtOctectsToCheck,
                    Long.valueOf( getPass1BytesCount() )
                    )
                );
        }
        else {
            getjProgressBarFiles().setValue( this.pass2CountFile );
            getjProgressBarFiles().setString( String.format( locale, "%,d / %,d", Integer.valueOf( this.pass2CountFile ), Integer.valueOf( getPass1FilesCount() ) ) );
            getjProgressBarOctets().setValue( (int)( this.pass2BytesCount/1024 ) );
            getjProgressBarOctets().setString( String.format( locale, "%,d / %,d", Long.valueOf( this.pass2BytesCount), Long.valueOf( getPass1BytesCount() ) ) );
        }
    }

    @Override
    public void clear()
    {
        if( this.dff != null ) {
            this.dff.clear();
            }
        this.dff = null;

        setPass1FilesCount( 0 );
        setPass1BytesCount( 0 );
        this.pass2CountFile = 0;
        this.pass2BytesCount = 0L;
        this.pass1currentTimeMillis = 0L;
        this.pass2currentTimeMillis = 0L;

        super.clearErrors();
    }

    private void prepareScan(
        final FileDigestFactory fileDigestFactory,
        final int               maxParallelFiles,
        final boolean           ignoreEmptyFiles
        ) throws NoSuchAlgorithmException
    {
        clear();

        super.prepareScan();

        this.dff = DuplicateFileFinderHelper.newDuplicateFileFinderAlgo2( ignoreEmptyFiles, fileDigestFactory, maxParallelFiles );

        updateDisplayThread();
    }

    private void doScan( final ScanParams scanParams )
    {
//        // TODO Auto-generated method stub
//
//    }
//
//    private void doScan(
//            final Iterable<File>                  entriesToScans,
//            final Iterable<File>                  entriesToIgnore,
//            final FileFilterBuilders              fileFilterBuilders,
//            final Map<String,Set<KeyFileState>>   duplicateFiles
//            )
//    {
        this.pass1currentTimeMillis = System.currentTimeMillis();
        LOGGER.info( "pass1 : " + this.pass1currentTimeMillis );

        scanParams.getDuplicateFiles().clear();
        doScanPass1Prepare( scanParams );
        LOGGER.info( "pass1 done" );

        //jProgressBarFiles.setStringPainted( true );
        getjProgressBarFiles().setIndeterminate( false );
        getjProgressBarOctets().setIndeterminate( false );

        //clearCurrentFileLabels();
        setCurrentFileLabels( getTxtCurrentFile() );
        clearCurrentFiles();

        this.pass2currentTimeMillis = System.currentTimeMillis();
        LOGGER.info( "pass2 : " + this.pass2currentTimeMillis );

        setDisplayFile( null );

        this.displayPass = 2;
        doScanPass2();
        LOGGER.info( "pass2 done" );

        //
        // Populate duplicateFiles
        //
        final Map<String,Set<File>> filesMap = this.dff.getFiles();

        for(final Map.Entry<String,Set<File>> e:filesMap.entrySet()) {
            final String              k    = e.getKey();
            final Set<File>           sf   = e.getValue();
            final Set<KeyFileState>   skfs = new HashSet<>();

            for(final File f:sf) {
                skfs.add( new KeyFileState(k,f) );
            }

            scanParams.getDuplicateFiles().put( k, skfs );
        }

        //Stop display !
        this.displayRunning = false;
        setDisplayFile( null );
        this.displayPass = 2;

        //Be sure to have a real ending display !
        updateDisplay();

//        duplicateFC.deepClear();
//        duplicateFC = null;
        this.dff.clear();
        this.dff = null;

        getAppToolKit().setEnabledJButtonCancel( false );

        // Populate next panel now !!
        getAppToolKit().initComponentsJPanelConfirm();
    }

    private void doScanPass1Prepare( final ScanParams scanParams )
    {
        this.displayPass = 1;

        LOGGER.info( "doScanPass1Prepare: begin" );

        // FileFilter and Listener for pass 1
        final FileFilter dirFilter = newDirectoriesFileFilter(
                scanParams.getFileFilterBuilders()
                );
        final FileFilter fileFilter = newFilesFileFilter(
                scanParams.getFileFilterBuilders()
                );

        // Listener for pass 2
        this.dff.addEventListener( this.eventListener );

        doScanPass1( scanParams.getEntriesToScans(), dirFilter, fileFilter );

        final InitialStatus initialStatus = this.dff.getInitialStatus();
        setPass1FilesCount( initialStatus.getFiles() );
        setPass1BytesCount( initialStatus.getBytes() );

        if( LOGGER.isInfoEnabled() ) {
            LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1CountFile = " + getPass1FilesCount() );
            LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1BytesCount = " + getPass1BytesCount() );

            LOGGER.info( "doScanPass1Prepare + doScanPass1: done" );
        }
    }

    @Override
    public void cancelProcess()
    {
        if( this.dff != null ) {
            this.dff.cancelProcess();
            }
    }
}
