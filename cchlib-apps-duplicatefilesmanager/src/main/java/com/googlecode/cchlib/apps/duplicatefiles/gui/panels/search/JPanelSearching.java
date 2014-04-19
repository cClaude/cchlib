// $codepro.audit.disable largeNumberOfFields, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.io.FileIterable;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.duplicate.DigestEventListener;
import com.googlecode.cchlib.util.duplicate.DuplicateFileCollector;
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

@I18nName("duplicatefiles.JPanelSearching")
public class JPanelSearching extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearching.class );
    private transient AppToolKit dFToolKit; // Serialization !
    private DuplicateFileCollector  duplicateFC;
    private DefaultTableModel   tableModelErrorList;
    private int                 pass1CountFile;
    private long                pass1BytesCount;
    private int                 pass2CountFile;
    private long                pass2BytesCount;

    private File displayFile;
    private int  displayPass;
    private boolean displayRunning;

    @I18nString private final String txtDuplicateSetsFound = "Duplicate sets found: %,d";
    @I18nString private final String txtDuplicateFilesFound = "Duplicate files found: %,d";
    @I18nString private final String txtNumberOfFilesProcessed = "Number of files processed: %,d";
    @I18nString private final String txtOctectsToCheck = "Octects to check: %,d";
    //@I18nString private String txtBytesReadFromDisk = "Bytes read from disk: %d bytes";
    @I18nString private final String txtCurrentFile = "Current File :";
    @I18nString private final String txtCurrentDir = "Current directory :";

    private final JTable jTableErrorList;
    private final JProgressBar jProgressBarFiles;
    private final JProgressBar jProgressBarOctets;
    private final JLabel jLabelCurrentFile;
    private final JLabel jLabelBytesReadFromDisk;
    private final JLabel jLabelDuplicateSetsFound;
    private final JLabel jLabelDuplicateFilesFound;
    @I18nIgnore private final JLabel jTextFieldCurrentFile;
    @I18nIgnore private final JLabel jLabelDuplicateSetsFoundValue;
    @I18nIgnore private final JLabel jLabelDuplicateFilesFoundValue;

    /**
     * Create the panel.
     */
    public JPanelSearching()
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jLabelCurrentFile = new JLabel("Current File :");
        jLabelCurrentFile.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelCurrentFile = new GridBagConstraints();
        gbc_jLabelCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelCurrentFile.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelCurrentFile.gridx = 0;
        gbc_jLabelCurrentFile.gridy = 0;
        add(jLabelCurrentFile, gbc_jLabelCurrentFile);

        jTextFieldCurrentFile = new JLabel();
        final GridBagConstraints gbc_jTextFieldCurrentFile = new GridBagConstraints();
        gbc_jTextFieldCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldCurrentFile.insets = new Insets(0, 0, 5, 0);
        gbc_jTextFieldCurrentFile.gridx = 1;
        gbc_jTextFieldCurrentFile.gridy = 0;
        add(jTextFieldCurrentFile, gbc_jTextFieldCurrentFile);

        final JLabel lblFilesReads = new JLabel("Files reads :");
        lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
        gbc_lblFilesReads.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
        gbc_lblFilesReads.gridx = 0;
        gbc_lblFilesReads.gridy = 1;
        add(lblFilesReads, gbc_lblFilesReads);

        jProgressBarFiles = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
        gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarFiles.gridx = 1;
        gbc_jProgressBarFiles.gridy = 1;
        add(jProgressBarFiles, gbc_jProgressBarFiles);

        jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
        jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
        gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelBytesReadFromDisk.gridx = 0;
        gbc_jLabelBytesReadFromDisk.gridy = 2;
        add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);

        jProgressBarOctets = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
        gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarOctets.gridx = 1;
        gbc_jProgressBarOctets.gridy = 2;
        add(jProgressBarOctets, gbc_jProgressBarOctets);

        jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
        jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateSetsFound.gridx = 0;
        gbc_jLabelDuplicateSetsFound.gridy = 3;
        add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);

        jLabelDuplicateSetsFoundValue = new JLabel("");
        final GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
        gbc_jLabelDuplicateSetsFoundValue.gridy = 3;
        add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);

        jLabelDuplicateFilesFound = new JLabel();
        jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
        final GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateFilesFound.gridx = 0;
        gbc_jLabelDuplicateFilesFound.gridy = 4;
        add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);

        jLabelDuplicateFilesFoundValue = new JLabel();
        jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
        final GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
        gbc_jLabelDuplicateFilesFoundValue.gridy = 4;
        add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 5;
        add(scrollPane, gbc_scrollPane);

        jTableErrorList = new JTable();
        scrollPane.setViewportView(jTableErrorList);
    }

    public void initFixComponents()
    {
        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

         tableModelErrorList = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable( final int row, final int column )
            {
                return false;
            }
        };
        tableModelErrorList.addColumn( "Files" );
        tableModelErrorList.addColumn( "Errors" );
        jTableErrorList.setModel( tableModelErrorList );

        jProgressBarFiles.setStringPainted( true );
        jProgressBarOctets.setStringPainted( true );
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
                displayFile = file;
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
                tableModelErrorList.addRow( v );
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
        pass1CountFile = stats.getPass2Files();
        pass1BytesCount = stats.getPass2Bytes();

        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1CountFile = " + pass1CountFile );
        LOGGER.info( "doScanPass1Prepare + doScanPass1: pass1BytesCount = " + pass1BytesCount );

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

        jProgressBarFiles.setMaximum( pass1CountFile );
        //jProgressBarOctets.setMaximum( Math.round( pass1BytesCount/1024) );
        jProgressBarOctets.setMaximum( (int)(pass1BytesCount/1024) );
        duplicateFC.pass2();

        displayFile = null;
        displayRunning = false;
        pass2CountFile = pass1CountFile;
        pass2BytesCount = pass1BytesCount;
        //updateDisplaySearching( null, 2);
        displayRunning = false;
    }

    private void updateDisplayThread()
    {
        displayRunning  = true;
        displayPass     = 1;
        displayFile     = null;

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
        if( displayFile != null ) {
            jTextFieldCurrentFile.setText( displayFile.getAbsolutePath() );
            }
        else {
            jTextFieldCurrentFile.setText( "" );
        }
        final Locale locale = dFToolKit.getValidLocale();

        jLabelDuplicateSetsFoundValue.setText(
                String.format(
                    locale,
                    txtDuplicateSetsFound,
                    Integer.valueOf( duplicateFC.getDuplicateSetsCount() )
                    )
                );
        jLabelDuplicateFilesFoundValue.setText(
                String.format(
                    locale,
                    txtDuplicateFilesFound,
                    Integer.valueOf( duplicateFC.getDuplicateFilesCount() )
                    )
                );

        if( displayPass == 1 ) {
            jProgressBarFiles.setString(
                    String.format(
                        locale,
                        txtNumberOfFilesProcessed,
                        Integer.valueOf( pass1CountFile )
                        )
                    );
            jProgressBarOctets.setString(
                String.format(
                    locale,
                    txtOctectsToCheck,
                    Long.valueOf( pass1BytesCount )
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
            jProgressBarFiles.setValue( pass2CountFile );
            jProgressBarFiles.setString( String.format( locale, "%,d / %,d", Integer.valueOf( pass2CountFile ), Integer.valueOf( pass1CountFile ) ) );
            //jProgressBarOctets.setValue( Math.round( pass2BytesCount/1024) );
            jProgressBarOctets.setValue( (int)( pass2BytesCount/1024 ) );
            jProgressBarOctets.setString( String.format( locale, "%,d / %,d", Long.valueOf( pass2BytesCount), Long.valueOf( pass1BytesCount ) ) );
        }
    }


    public void clear()
    {
        if( duplicateFC!= null ) {
            duplicateFC.deepClear();
            }
        duplicateFC = null;

        pass1CountFile = 0;
        pass1BytesCount = 0;
        pass2CountFile = 0;
        pass2BytesCount = 0;

        while( tableModelErrorList.getRowCount() > 0 ) {
            tableModelErrorList.removeRow( 0 );
            }
    }

    public void prepareScan(
        final MessageDigestFile   messageDigestFile,
        final boolean             ignoreEmptyFiles
        )
    {
        clear();

        //jLabelStateWorking.setIcon( iconWorking );
        jProgressBarFiles.setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );
        jLabelCurrentFile.setText( txtCurrentDir );
        jTextFieldCurrentFile.setText( "" );
        //this.dFToolKit.getJButtonCancel().setEnabled( true );
        this.dFToolKit.setEnabledJButtonCancel( true );
        tableModelErrorList.setRowCount( 0 );

        duplicateFC = new DuplicateFileCollector(
                    messageDigestFile,
                    ignoreEmptyFiles
                    );

        updateDisplayThread();
    }

    public void doScan(
            final Iterable<File>                  entriesToScans,
            final Iterable<File>                  entriesToIgnore,
            final FileFilterBuilders              fileFilterBuilders,
            final HashMapSet<String,KeyFileState> duplicateFiles // $codepro.audit.disable declareAsInterface
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
        jProgressBarFiles.setIndeterminate( false );
        jProgressBarOctets.setIndeterminate( false );
        jLabelCurrentFile.setText( txtCurrentFile );
        jTextFieldCurrentFile.setText( "" );

        LOGGER.info( "pass2" );
        displayFile = null;
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
        displayFile = null;
        displayPass = 2;

        //Be sure to have a real ending display !
        updateDisplay();

        duplicateFC.deepClear();
        duplicateFC = null;

        this.dFToolKit.setEnabledJButtonCancel( false );

        // Populate next panel now !!
        dFToolKit.initComponentsJPanelConfirm();
    }


    private FileFilter createFilesFileFilter(
        final FileFilterBuilders   fbs
        )
    {
        final boolean skipHidden   = fbs.isIgnoreHiddenFiles();
        final boolean skipReadOnly = fbs.isIgnoreReadOnlyFiles();

        final FileFilterBuilder includeFilesFileFilterBuilder = fbs.getIncludeFiles();
        LOGGER.info( "includeFilesFileFilterBuilder="+includeFilesFileFilterBuilder);

        if( includeFilesFileFilterBuilder != null ) {
            final Pattern regex        = includeFilesFileFilterBuilder.getRegExp();
            final String[] fileExts     = includeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
            final int      fileExtsL    = fileExts.length;

            LOGGER.info( "createFilesFileFilter: case1");
            LOGGER.info( "files regex=" + regex );
            LOGGER.info( "files skipHidden=" + skipHidden );
            LOGGER.info( "files skipReadOnly=" + skipReadOnly );
            LOGGER.info( "fileExtsL=" + fileExtsL);
            LOGGER.info( "fileExts=" + includeFilesFileFilterBuilder.getNamePart() );

            final FileFilter ff = f -> {
                if( f.isFile() ) {

                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                            }
                        }

                    // ReadOnly files
                    if( skipReadOnly ) {
                        if( !f.canWrite() ) {
                            return false;
                            }
                        }

                    // RegEx
                    if( regex != null ) {
                        if( regex.matcher(f.getName()).matches() ) {
                            pass1CountFile++;
                            pass1BytesCount += f.length();
                            return true;
                            }
                        }

                    // Extensions
                    final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                    for(int i=0;i<fileExtsL;i++) {
                        if(name.endsWith( fileExts[i] )) {
                            pass1CountFile++;
                            pass1BytesCount += f.length();
                            return true;
                            }
                        }

                    return false;
                }
                return false;
            };

            return ff;
        }
        else {
            final FileFilterBuilder excludeFilesFileFilterBuilder = fbs.getExcludeFiles();

            if( excludeFilesFileFilterBuilder != null ) {
                final Pattern regex        = excludeFilesFileFilterBuilder.getRegExp();

                final String[] fileExts  = excludeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
                final int      fileExtsL = fileExts.length;

                LOGGER.info( "createFilesFileFilter: case2");
                LOGGER.info( "files regex=" + regex );
                LOGGER.info( "files skipHidden=" + skipHidden );
                LOGGER.info( "files skipReadOnly=" + skipReadOnly );
                LOGGER.info( "fileExtsL=" + fileExtsL);
                LOGGER.info( "fileExts=" + excludeFilesFileFilterBuilder.getNamePart() );

                return f -> {
                    if( f.isFile() ) {
                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                return false;
                            }
                        }
                        if( skipReadOnly ) {
                            if( !f.canWrite() ) {
                                return false;
                            }
                        }
                        // RegEx
                        if( regex != null ) {
                            if( ! regex.matcher(f.getName()).matches() ) {
                                return false;
                            }
                        }
                        // Extensions
                        final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                        for(int i=0;i<fileExtsL;i++) {
                            if(name.endsWith( fileExts[i] )) {
                                return false;
                            }
                        }
                        pass1CountFile++;
                        pass1BytesCount += f.length();
                        return true;
                    }
                    return false;
                };
            }
            else {
                LOGGER.info( "createFilesFileFilter: case3");
                // Minimum file filter
                return f -> {
                    if( f.isFile() ) {
                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                return false;
                            }
                        }
                        if( skipReadOnly ) {
                            if( !f.canWrite() ) {
                                return false;
                            }
                        }
                        pass1CountFile++;
                        pass1BytesCount += f.length();
                        return true;
                    }
                    return false;
                };
            }
        }
    }

    private FileFilter createDirectoriesFileFilter(
            final FileFilterBuilders fileFilterBuilders
            )
    {
        final boolean skipHidden = fileFilterBuilders.isIgnoreHiddenDirs();

        final FileFilterBuilder excludeDirectoriesFileFilterBuilder = fileFilterBuilders.getExcludeDirs();

        if( excludeDirectoriesFileFilterBuilder != null ) {
            final Pattern regex = excludeDirectoriesFileFilterBuilder.getRegExp();

            //TODO: construire un automate pour tester
            //      une chaîne par rapport à un groupe de motif
            final String[] dirNames  = excludeDirectoriesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
            final int      dirNamesL = dirNames.length;

            LOGGER.info( "dirs regex=" + regex );
            LOGGER.info( "dirs skipHidden=" + skipHidden );
            LOGGER.info( "dirNamesL=" + dirNamesL );
            LOGGER.info( "dirNames=" + excludeDirectoriesFileFilterBuilder.getNamePart() );

            return f -> {
                // Hidden files
                if( skipHidden ) {
                    if( f.isHidden() ) {
                        return false;
                    }
                }
                // RegEx
                if( regex != null ) {
                    if( regex.matcher(f.getName()).matches() ) {
                        return false;
                    }
                }
                // Test names ?
                final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                for(int i=0;i<dirNamesL;i++) {
                    if(name.equals( dirNames[i] )) {
                        return false;
                    }
                }

                displayFile = f;
                return true;
            };
        }
        else {
            final FileFilterBuilder includeDirectoriesFileFilterBuilder = fileFilterBuilders.getIncludeDirs();

            if( includeDirectoriesFileFilterBuilder != null ) {
                 final Pattern regex = includeDirectoriesFileFilterBuilder.getRegExp();

                //TODO: construire un automate pour tester
                //      une chaîne par rapport à un groupe de motif
                final String[] dirNames  = includeDirectoriesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
                final int      dirNamesL = dirNames.length;

                LOGGER.info( "dirs regex=" + regex );
                LOGGER.info( "dirs skipHidden=" + skipHidden );
                LOGGER.info( "dirNamesL=" + dirNamesL );
                LOGGER.info( "dirNames=" + includeDirectoriesFileFilterBuilder.getNamePart() );

                return f -> {
                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                        }
                    }
                    // RegEx
                    if( regex != null ) {
                        if( regex.matcher(f.getName()).matches() ) {
                            return false;
                        }
                    }
                    // Test names ?
                    final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                    for(int i=0;i<dirNamesL;i++) {
                        if(name.equals( dirNames[i] )) {
                            displayFile = f;
                            return true;
                        }
                    }
                    return false;
                };
            }
            else {
                LOGGER.info( "dirs skipHidden=" + skipHidden );

                return f -> {
                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                        }
                    }
                    displayFile = f;
                    return true;
                };
            }
        }
    }

    public void cancelProcess()
    {
        if( duplicateFC != null ) {
            duplicateFC.setCancelProcess( true );
            }
    }
}
