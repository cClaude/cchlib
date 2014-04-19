package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.io.FileIterable;
import com.googlecode.cchlib.util.duplicate.DigestEventListener;
import com.googlecode.cchlib.util.duplicate.DuplicateFileCollector;
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;

@I18nName("duplicatefiles.JPanelSearching")
public class JPanelSearching extends JPanelSearchingFilters
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearching.class );

    private DuplicateFileCollector  duplicateFC;

    @I18nString private final String txtDuplicateSetsFound = "Duplicate sets found: %,d";
    @I18nString private final String txtDuplicateFilesFound = "Duplicate files found: %,d";
    @I18nString private final String txtNumberOfFilesProcessed = "Number of files processed: %,d";
    @I18nString private final String txtOctectsToCheck = "Octects to check: %,d";

    private int     pass2CountFile;
    private long    pass2BytesCount;

    private int     displayPass;
    private boolean displayRunning;

    /**
     * Create the panel.
     */
    public JPanelSearching()
    {
        super();
    }

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
        try {
            final MessageDigestFile messageDigestFile = new MessageDigestFile( messageDigestAlgorithm, messageDigestBufferSize );

            prepareScan( messageDigestFile , ignoreEmptyFiles );
       }
        catch( final NoSuchAlgorithmException e ) {
            LOGGER.fatal( "Bad messageDigestAlgorithm: " + messageDigestAlgorithm, e );
            // This exception should not occur.
            throw new RuntimeException( e );
        }

        doScan(entriesToScans, entriesToIgnore, fileFilterBuilders, duplicateFiles);
    }

    private void doScanPass1Prepare(
        final Iterable<File>      entriesToScans,
        final Iterable<File>      entriesToIgnore,
        final FileFilterBuilders  fileFilterBuilders
        )
    {
        displayPass = 1;
        LOGGER.info( "doScanPass1Prepare: begin" );

        // FileFilter and Listener for pass 1
        final FileFilter dirFilter = createDirectoriesFileFilter(
                fileFilterBuilders
                );
        final FileFilter fileFilter = createFilesFileFilter(
                fileFilterBuilders
                );

        // Listener for pass 2
        duplicateFC.addDigestEventListener( new DigestEventListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void computeDigest( final File file )
            {
                setDisplayFile( file );
                pass2CountFile++;
                //pass2BytesCount += file.length();
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
            public void computeDigest( final File file, final long length )
            {
                pass2BytesCount += length;
            }
            @Override
            public boolean isCancel()
            {
                return duplicateFC.isCancelProcess();
            }
        } );

        doScanPass1( entriesToScans, dirFilter, fileFilter );

        final DuplicateFileCollector.Stats stats = duplicateFC.getPass1Stats();
        setPass1CountFile( stats.getPass2Files() );
        setPass1BytesCount( stats.getPass2Bytes() );

        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1CountFile = " + getPass1CountFile() );
        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1BytesCount = " + getPass1BytesCount() );

        LOGGER.info( "doScanPass1Prepare + doScanPass1: done" );
    }

    private void doScanPass1( final Iterable<File> rootFiles, final FileFilter dirFilter, final FileFilter fileFilter )
    {
        for( final File rootFile : rootFiles ) {
            final Iterable<File> files;

            if( rootFile.isDirectory() ) {
                LOGGER.info( "doScanPass1: examin folder:" + rootFile );

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
            duplicateFC.pass1Add( files );
       }
    }

    private void doScanPass2()
    {
        displayPass = 2;

        getjProgressBarFiles().setMaximum( getPass1CountFile() );
        getjProgressBarOctets().setMaximum( (int)(getPass1BytesCount()/1024) );
        duplicateFC.pass2();

        setDisplayFile( null );
        displayRunning = false;
        pass2CountFile = getPass1CountFile();
        pass2BytesCount = getPass1BytesCount();
        displayRunning = false;
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
                    Integer.valueOf( duplicateFC.getDuplicateSetsCount() )
                    )
                );
        getjLabelDuplicateFilesFoundValue().setText(
                String.format(
                    locale,
                    txtDuplicateFilesFound,
                    Integer.valueOf( duplicateFC.getDuplicateFilesCount() )
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
    public void clear()
    {
        if( duplicateFC!= null ) {
            duplicateFC.deepClear();
            }
        duplicateFC = null;

        setPass1CountFile( 0 );
        setPass1BytesCount( 0 );
        pass2CountFile = 0;
        pass2BytesCount = 0;

        super.clear();
    }

    private void prepareScan(
        final MessageDigestFile   messageDigestFile,
        final boolean             ignoreEmptyFiles
        )
    {
        clear();

        super.prepareScan();

        duplicateFC = new DuplicateFileCollector(
                    messageDigestFile,
                    ignoreEmptyFiles
                    );

        updateDisplayThread();
    }

    private void doScan(
            final Iterable<File>                  entriesToScans,
            final Iterable<File>                  entriesToIgnore,
            final FileFilterBuilders              fileFilterBuilders,
            final Map<String,Set<KeyFileState>>   duplicateFiles
            )
    {
        LOGGER.info( "pass1" );
        duplicateFiles.clear();
        doScanPass1Prepare(
                entriesToScans,
                entriesToIgnore,
                fileFilterBuilders
                );
        LOGGER.info( "pass1 done" );

        //jProgressBarFiles.setStringPainted( true );
        getjProgressBarFiles().setIndeterminate( false );
        getjProgressBarOctets().setIndeterminate( false );
        getjLabelCurrentFile().setText( txtCurrentFile );
        getjTextFieldCurrentFile().setText( "" );

        LOGGER.info( "pass2" );
        setDisplayFile( null );
        displayPass = 2;
        doScanPass2();
        LOGGER.info( "pass2 done" );

        //
        // Populate duplicateFiles
        //
        final Map<String,Set<File>> filesMap = duplicateFC.getFiles();

        for(final Map.Entry<String,Set<File>> e:filesMap.entrySet()) {
            final String              k    = e.getKey();
            final Set<File>           sf   = e.getValue();
            final Set<KeyFileState>   skfs = new HashSet<>();

            for(final File f:sf) {
                skfs.add( new KeyFileState(k,f) );
            }

            duplicateFiles.put( k, skfs );
        }

        //Stop display !
        displayRunning = false;
        setDisplayFile( null );
        displayPass = 2;

        //Be sure to have a real ending display !
        updateDisplay();

        duplicateFC.deepClear();
        duplicateFC = null;

        getAppToolKit().setEnabledJButtonCancel( false );

        // Populate next panel now !!
        getAppToolKit().initComponentsJPanelConfirm();
    }

    public void cancelProcess()
    {
        if( duplicateFC != null ) {
            duplicateFC.setCancelProcess( true );
            }
    }
}
