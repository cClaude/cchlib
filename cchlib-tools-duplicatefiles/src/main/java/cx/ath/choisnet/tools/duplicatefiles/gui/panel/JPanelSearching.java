package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.i18n.I18nIgnore;
import com.googlecode.cchlib.i18n.I18nString;
import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.tools.duplicatefiles.FileFilterBuilder;
import cx.ath.choisnet.tools.duplicatefiles.FileFilterBuilders;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;
import cx.ath.choisnet.util.duplicate.DigestEventListener;
import cx.ath.choisnet.util.duplicate.DuplicateFileCollector;

/**
 *
 *
 */
public class JPanelSearching extends JPanel//SearchingWB
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelSearching.class );
    private transient DFToolKit dFToolKit; // Serialization !
    private DuplicateFileCollector  duplicateFC;
    private DefaultTableModel   tableModelErrorList;
    private int                 pass1CountFile;
    private long                pass1BytesCount;
    private int                 pass2CountFile;
    private long                pass2BytesCount;

    private File displayFile;
    private int  displayPass;
    private boolean displayRunning;

    @I18nString private String txtDuplicateSetsFound = "Duplicate sets found: %d";
    @I18nString private String txtDuplicateFilesFound = "Duplicate files found: %d";
    @I18nString private String txtNumberOfFilesProcessed = "Number of files processed: %d";
    @I18nString private String txtOctectsToCheck = "Octects to check: %d";
    //@I18nString private String txtBytesReadFromDisk = "Bytes read from disk: %d bytes";
    @I18nString private String txtCurrentFile = "Current File :";
    @I18nString private String txtCurrentDir = "Current directory :";

    private JTable jTableErrorList;
    private JProgressBar jProgressBarFiles;
    private JProgressBar jProgressBarOctets;
    private JLabel jLabelCurrentFile;
    private JLabel jLabelBytesReadFromDisk;
    private JLabel jLabelDuplicateSetsFound;
    private JLabel jLabelDuplicateFilesFound;
    @I18nIgnore private JLabel jTextFieldCurrentFile;
    @I18nIgnore private JLabel jLabelDuplicateSetsFoundValue;
    @I18nIgnore private JLabel jLabelDuplicateFilesFoundValue;

    /**
     * Create the panel.
     */
    public JPanelSearching()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jLabelCurrentFile = new JLabel("Current File :");
        jLabelCurrentFile.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelCurrentFile = new GridBagConstraints();
        gbc_jLabelCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelCurrentFile.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelCurrentFile.gridx = 0;
        gbc_jLabelCurrentFile.gridy = 0;
        add(jLabelCurrentFile, gbc_jLabelCurrentFile);

        jTextFieldCurrentFile = new JLabel();
        GridBagConstraints gbc_jTextFieldCurrentFile = new GridBagConstraints();
        gbc_jTextFieldCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldCurrentFile.insets = new Insets(0, 0, 5, 0);
        gbc_jTextFieldCurrentFile.gridx = 1;
        gbc_jTextFieldCurrentFile.gridy = 0;
        add(jTextFieldCurrentFile, gbc_jTextFieldCurrentFile);

        JLabel lblFilesReads = new JLabel("Files reads :");
        lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
        gbc_lblFilesReads.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
        gbc_lblFilesReads.gridx = 0;
        gbc_lblFilesReads.gridy = 1;
        add(lblFilesReads, gbc_lblFilesReads);

        jProgressBarFiles = new JProgressBar();
        GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
        gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarFiles.gridx = 1;
        gbc_jProgressBarFiles.gridy = 1;
        add(jProgressBarFiles, gbc_jProgressBarFiles);

        jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
        jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
        gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelBytesReadFromDisk.gridx = 0;
        gbc_jLabelBytesReadFromDisk.gridy = 2;
        add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);

        jProgressBarOctets = new JProgressBar();
        GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
        gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarOctets.gridx = 1;
        gbc_jProgressBarOctets.gridy = 2;
        add(jProgressBarOctets, gbc_jProgressBarOctets);

        jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
        jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateSetsFound.gridx = 0;
        gbc_jLabelDuplicateSetsFound.gridy = 3;
        add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);

        jLabelDuplicateSetsFoundValue = new JLabel("");
        GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
        gbc_jLabelDuplicateSetsFoundValue.gridy = 3;
        add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);

        jLabelDuplicateFilesFound = new JLabel();
        jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
        GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateFilesFound.gridx = 0;
        gbc_jLabelDuplicateFilesFound.gridy = 4;
        add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);

        jLabelDuplicateFilesFoundValue = new JLabel();
        jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
        gbc_jLabelDuplicateFilesFoundValue.gridy = 4;
        add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 5;
        add(scrollPane, gbc_scrollPane);

        jTableErrorList = new JTable();
        scrollPane.setViewportView(jTableErrorList);
    }

    public void initFixComponents( DFToolKit dFToolKit )
    {
        this.dFToolKit = dFToolKit;
//        this.dFToolKit.getJButtonCancel().addMouseListener(
//            new MouseAdapter()
//            {
//                @Override
//                public void mouseClicked( final MouseEvent e )
//                {
//                if( JPanelSearching.this.dFToolKit.isEnabledJButtonCancel() ) {
//                    JPanelSearching.this.dFToolKit.setEnabledJButtonCancel( false );
//
//                    if( duplicateFC != null ) {
//                        duplicateFC.setCancelProcess( true );
//                        }
//                    }
//                }
//            });

        //this.iconWorking = ResourcesLoader.getImageIcon( "working.gif" );
        tableModelErrorList = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable( int row, int column )
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

    private void doScanPass1(
            Iterable<File>      entriesToScans,
            Iterable<File>      entriesToIgnore,
            FileFilterBuilders  fileFilterBuilders
            )
    {
        displayPass = 1;
        slogger.info( "doScanPass1: init()" );

        // FileFilter and Listener for pass 1
        FileFilter dirFilter = createDirectoriesFileFilter(
                fileFilterBuilders
                );
        FileFilter fileFilter = createFilesFileFilter(
                fileFilterBuilders
                );

        // Listener for pass 2
        duplicateFC.addDigestEventListener( new DigestEventListener() {
            @Override
            public void computeDigest( File file )
            {
                displayFile = file;
                pass2CountFile++;
                //pass2BytesCount += file.length();
            }
            @Override
            public void ioError( IOException e, File file )
            {
                System.err.printf( "IOException %s : %s\n", file,
                        e.getMessage() );

                Vector<Object> v = new Vector<Object>();
                v.add( file );
                v.add( e.getLocalizedMessage() );
                tableModelErrorList.addRow( v );
            }
            @Override
            public void computeDigest( File file, long length )
            {
                pass2BytesCount += length;
            }
            @Override
            public boolean isCancel()
            {
                return duplicateFC.isCancelProcess();
            }
        } );

        //for(File f:directories) {
        for(File f : entriesToScans ) {

            if( f.isDirectory() ) {
                Iterable<File>  files = new FileIterator(
                        f,
                        fileFilter,
                        dirFilter
                        );
//                List<File> files = new ArrayList<File>();
//                Iterator<File> iter0 = files0.iterator();
//                while( iter0.hasNext()) {
//                    File f0 = iter0.next();
//                    files.add( f0 );
//                }
//                slogger.info( "files_:" + files.size() );

                slogger.info( "doScanPass1: examin:" + f );

                duplicateFC.pass1Add( files );
                }
            else {
                slogger.info( "doScanPass1: examin file:" + f );

                List<File> fContener = new ArrayList<File>();
                fContener.add( f );
                duplicateFC.pass1Add( fContener );
            }
        }

        DuplicateFileCollector.Stats stats = duplicateFC.getPass1Stats();
        pass1CountFile = stats.getPass2Files();
        pass1BytesCount = stats.getPass2Bytes();

        slogger.info( "pass1CountFile = " + pass1CountFile );
        slogger.info( "pass1BytesCount = " + pass1BytesCount );

        slogger.info( "doScanPass1: done" );
    }

    private void doScanPass2()
    {
        displayPass = 2;

        jProgressBarFiles.setMaximum( pass1CountFile );
        jProgressBarOctets.setMaximum( Math.round( pass1BytesCount/1024) );
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

        new Thread( new Runnable() {
            @Override
            public void run()
            {
                while(displayRunning) {
                    updateDisplay();

                    try {
                        Thread.sleep(300);
                    }
                    catch( InterruptedException e ) {
                        return;
                    }
                }
            }
        }).start();
    }

    private void updateDisplay()
    {
        if( displayFile != null ) {
            jTextFieldCurrentFile.setText( displayFile.getAbsolutePath() );
        }
        else {
            jTextFieldCurrentFile.setText( "" );
        }
        jLabelDuplicateSetsFoundValue.setText(
                String.format(
                    txtDuplicateSetsFound,
                    duplicateFC.getDuplicateSetsCount()
                    )
                );
        jLabelDuplicateFilesFoundValue.setText(
                String.format(
                    txtDuplicateFilesFound,
                    duplicateFC.getDuplicateFilesCount()
                    )
                );

        if( displayPass == 1 ) {
            jProgressBarFiles.setString(
                    String.format(
                        txtNumberOfFilesProcessed,
                        pass1CountFile
                        )
                    );
            jProgressBarOctets.setString(
                String.format(
                    txtOctectsToCheck,
                    pass1BytesCount
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
            jProgressBarFiles.setString( String.format( "%d / %d", pass2CountFile, pass1CountFile ) );
            jProgressBarOctets.setValue( Math.round( pass2BytesCount/1024) );
            jProgressBarOctets.setString( String.format( "%d / %d", pass2BytesCount, pass1BytesCount ) );
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
            MessageDigestFile   messageDigestFile,
            boolean             ignoreEmptyFiles
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
            Iterable<File>                  entriesToScans,
            Iterable<File>                  entriesToIgnore,
            FileFilterBuilders              fileFilterBuilders,
            HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        slogger.info( "pass1" );
        duplicateFiles.clear();
        doScanPass1(
                entriesToScans,
                entriesToIgnore,
                fileFilterBuilders
                );
        slogger.info( "pass1 done" );

        //jProgressBarFiles.setStringPainted( true );
        jProgressBarFiles.setIndeterminate( false );
        jProgressBarOctets.setIndeterminate( false );
        jLabelCurrentFile.setText( txtCurrentFile );
        jTextFieldCurrentFile.setText( "" );

        slogger.info( "pass2" );
        displayFile = null;
        displayPass = 2;
        doScanPass2();
        slogger.info( "pass2 done" );

        //
        // Populate duplicateFiles
        //
        Map<String,Set<File>> filesMap = duplicateFC.getFiles();

        for(Map.Entry<String,Set<File>> e:filesMap.entrySet()) {
            String              k    = e.getKey();
            Set<File>           sf   = e.getValue();
            Set<KeyFileState>   skfs = new HashSet<KeyFileState>();

            for(File f:sf) {
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
        //jButtonCancelScan.setEnabled( false );
        //this.dFToolKit.getJButtonCancel().setEnabled( false );
        this.dFToolKit.setEnabledJButtonCancel( false );
        //jLabelStateWorking.setIcon( null );

        // Populate next panel now !!
        dFToolKit.initComponentsJPanelConfirm();
    }


    private FileFilter createFilesFileFilter(
            FileFilterBuilders   fbs
            )
    {
        final boolean skipHidden   = fbs.isIgnoreHiddenFiles();
        final boolean skipReadOnly = fbs.isIgnoreReadOnlyFiles();

        FileFilterBuilder includeFilesFileFilterBuilder = fbs.getIncludeFiles();
        slogger.info( "includeFilesFileFilterBuilder="+includeFilesFileFilterBuilder);

        if( includeFilesFileFilterBuilder != null ) {
            final Pattern regex        = includeFilesFileFilterBuilder.getRegExp();
            final String[] fileExts     = includeFilesFileFilterBuilder.getNamePart().toArray( new String[0] );
            final int      fileExtsL    = fileExts.length;

            slogger.info( "createFilesFileFilter: case1");
            slogger.info( "files regex=" + regex );
            slogger.info( "files skipHidden=" + skipHidden );
            slogger.info( "files skipReadOnly=" + skipReadOnly );
            slogger.info( "fileExtsL=" + fileExtsL);
            slogger.info( "fileExts=" + includeFilesFileFilterBuilder.getNamePart() );

            FileFilter ff = new FileFilter() {
                @Override
                public boolean accept( File f )
                {
                    if( f.isFile() ) {

                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                //slogger.debug( "f:" + f + " -> false1" );
                                return false;
                                }
                            }

                        // ReadOnly files
                        if( skipReadOnly ) {
                            if( !f.canWrite() ) {
                                //slogger.debug( "f:" + f + " -> false2" );
                                return false;
                                }
                            }

                        // RegEx
                        if( regex != null ) {
                            if( regex.matcher(f.getName()).matches() ) {
                                pass1CountFile++;
                                pass1BytesCount += f.length();
                                //slogger.debug( "f:" + f + " -> (true regex match)" );
                                return true;
                                }
                            }

                        // Extensions
                        String name = f.getName().toLowerCase();

                        for(int i=0;i<fileExtsL;i++) {
                            if(name.endsWith( fileExts[i] )) {
                                pass1CountFile++;
                                pass1BytesCount += f.length();
                                //slogger.debug( "f:" + f + " -> (true endsWith match)" );
                                return true;
                                }
                            }

                        //slogger.debug( "f:" + f + " -> false4" );
                        return false;
                    }
                    //slogger.debug( "f:" + f + " -> false5 (not a file)" );
                    return false;
                }
            };
//            File f = new File( "C:\\toto.reg" );
//            slogger.info( "fileTst toto.reg=" + regex.matcher( "toto.reg" ).matches() );
//            slogger.info( "fileTst " + f.getName() + "=" + regex.matcher( f.getName() ).matches() );
//            slogger.info( "fileTst " + f + "=" + ff.accept( f ) );

            return ff;
        }
        else {
            FileFilterBuilder excludeFilesFileFilterBuilder = fbs.getExcludeFiles();

            if( excludeFilesFileFilterBuilder != null ) {
                final Pattern regex        = excludeFilesFileFilterBuilder.getRegExp();

                final String[] fileExts  = excludeFilesFileFilterBuilder.getNamePart().toArray( new String[0] );
                final int      fileExtsL = fileExts.length;

                slogger.info( "createFilesFileFilter: case2");
                slogger.info( "files regex=" + regex );
                slogger.info( "files skipHidden=" + skipHidden );
                slogger.info( "files skipReadOnly=" + skipReadOnly );
                slogger.info( "fileExtsL=" + fileExtsL);
                slogger.info( "fileExts=" + excludeFilesFileFilterBuilder.getNamePart() );

                return new FileFilter() {
                    @Override
                    public boolean accept( File f )
                    {
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
                            String name = f.getName().toLowerCase();

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
                    }
                };
            }
            else {
                slogger.info( "createFilesFileFilter: case3");
                // Minimum file filter
                return new FileFilter() {
                    @Override
                    public boolean accept( File f )
                    {
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
                    }
                };
            }
        }
    }

    private FileFilter createDirectoriesFileFilter(
            FileFilterBuilders fileFilterBuilders
            )
    {
        final boolean skipHidden = fileFilterBuilders.isIgnoreHiddenDirs();

        FileFilterBuilder excludeDirectoriesFileFilterBuilder = fileFilterBuilders.getExcludeDirs();

        if( excludeDirectoriesFileFilterBuilder != null ) {
            final Pattern regex = excludeDirectoriesFileFilterBuilder.getRegExp();

            //TODO: construire un automate pour tester
            //      une chaîne par rapport à un groupe de motif
            final String[] dirNames  = excludeDirectoriesFileFilterBuilder.getNamePart().toArray( new String[0] );
            final int      dirNamesL = dirNames.length;

            slogger.info( "dirs regex=" + regex );
            slogger.info( "dirs skipHidden=" + skipHidden );
            //slogger.info( "dirs skipReadOnly=" + skipReadOnly );
            slogger.info( "dirNamesL=" + dirNamesL );
            slogger.info( "dirNames=" + excludeDirectoriesFileFilterBuilder.getNamePart() );

            return new FileFilter() {
                @Override
                public boolean accept( File f )
                {
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
                    String name = f.getName().toLowerCase();

                    for(int i=0;i<dirNamesL;i++) {
                        if(name.equals( dirNames[i] )) {
                            return false;
                        }
                    }

                    displayFile = f;
                    return true;
                }
            };
        }
        else {
            FileFilterBuilder includeDirectoriesFileFilterBuilder = fileFilterBuilders.getIncludeDirs();

            if( includeDirectoriesFileFilterBuilder != null ) {
                 final Pattern regex = includeDirectoriesFileFilterBuilder.getRegExp();

                //TODO: construire un automate pour tester
                //      une chaîne par rapport à un groupe de motif
                final String[] dirNames  = includeDirectoriesFileFilterBuilder.getNamePart().toArray( new String[0] );
                final int      dirNamesL = dirNames.length;

                slogger.info( "dirs regex=" + regex );
                slogger.info( "dirs skipHidden=" + skipHidden );
                slogger.info( "dirNamesL=" + dirNamesL );
                slogger.info( "dirNames=" + includeDirectoriesFileFilterBuilder.getNamePart() );

                return new FileFilter() {
                    @Override
                    public boolean accept( File f )
                    {
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
                        String name = f.getName().toLowerCase();

                        for(int i=0;i<dirNamesL;i++) {
                            if(name.equals( dirNames[i] )) {
                                displayFile = f;
                                return true;
                            }
                        }
                        return false;
                    }
                };
            }
            else {
                slogger.info( "dirs skipHidden=" + skipHidden );

                return new FileFilter() {
                    @Override
                    public boolean accept( File f )
                    {
                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                return false;
                            }
                        }
                        displayFile = f;
                        return true;
                    }
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
