package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import cx.ath.choisnet.i18n.I18nIgnore;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;
import cx.ath.choisnet.util.duplicate.DigestEventListener;
import cx.ath.choisnet.util.duplicate.DuplicateFileCollector;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelSearching extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelSearching.class );

    private DuplicateFileCollector  duplicateFC;
    /* @serial */
    private DefaultTableModel   tableModelErrorList;
    private int                 pass1CountFile;
    private long                pass1BytesCount;
    private int                 pass2CountFile;
    private long                pass2BytesCount;

    private JLabel jLabelSearchProcess;
    private JProgressBar jProgressBarFiles;
    private JProgressBar jProgressBarOctets;
    private JLabel jLabelCurrentFile;
    private JTextField jTextFieldCurrentFile;
    private JLabel jLabelDuplicateSetsFound;
    private JLabel jLabelDuplicateFilesFound;
    private JLabel jLabelBytesReadFromDisk;
    private JScrollPane jScrollPaneErrorList;
    private JPanel jPanelDuplicateBottom;
    private JTable jTableErrorList;
    private JButton jButtonCancelScan;
    private File displayFile;
    private int  displayPass;
    private boolean displayRunning;
    private JPanel jPanelState;
    private JPanel jPanelStateText;
    private JPanel jPanelStateGfx;
    @I18nIgnore private JLabel jLabelStateWorking;
    private Icon   iconWorking;

    @I18nString private String txtDuplicateSetsFound = "Duplicate sets found: %d";
    @I18nString private String txtDuplicateFilesFound = "Duplicate files found: %d";
    @I18nString private String txtNumberOfFilesProcessed = "Number of files processed: %d";
    @I18nString private String txtOctectsToCheck = "Octects to check: %d";
    @I18nString private String txtBytesReadFromDisk = "Bytes read from disk: %d bytes";
    @I18nString private String txtCurrentFile = "Current File:";
    @I18nString private String txtCurrentDir = "Current directory:";

    public JPanelSearching()
    {
        initComponents();

        // Fix for VS4E
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jPanelStateText.setLayout(new BoxLayout(jPanelStateText, BoxLayout.Y_AXIS));
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(getJLabelSearchProcess());
        add(getJProgressBarFiles());
        add(getJProgressBarOctets());
        add(getJLabelCurrentFile());
        add(getJTextFieldCurrentFile());
        add(getJPanelState());
        add(getJScrollPaneErrorList());
        add(getJPanelDuplicateBottom());
        setSize(320, 240);
    }

    private JLabel getJLabelStateWorking() {
        if (jLabelStateWorking == null) {
            jLabelStateWorking = new JLabel();
         }
        return jLabelStateWorking;
    }

    private JPanel getJPanelStateGfx() {
        if (jPanelStateGfx == null) {
            jPanelStateGfx = new JPanel();
            jPanelStateGfx.add(getJLabelStateWorking());
        }
        return jPanelStateGfx;
    }

    private JPanel getJPanelStateText() {
        if (jPanelStateText == null) {
            jPanelStateText = new JPanel();
            jPanelStateText.setLayout(new BoxLayout(jPanelStateText, BoxLayout.X_AXIS));
            jPanelStateText.add(getJLabelDuplicateSetsFound());
            jPanelStateText.add(getJLabelDuplicateFilesFound());
            jPanelStateText.add(getJLabelBytesReadFromDisk());
        }
        return jPanelStateText;
    }

    private JPanel getJPanelState() {
        if (jPanelState == null) {
            jPanelState = new JPanel();
            jPanelState.setLayout(new BorderLayout());
            jPanelState.add(getJPanelStateText(), BorderLayout.CENTER);
            jPanelState.add(getJPanelStateGfx(), BorderLayout.EAST);
        }
        return jPanelState;
    }

    public void initFixComponents()
    {
        this.iconWorking = new ImageIcon(
                getClass().getResource( "working.gif" )
                );

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

    private JLabel getJLabelBytesReadFromDisk() {
        if (jLabelBytesReadFromDisk == null) {
            jLabelBytesReadFromDisk = new JLabel();
            jLabelBytesReadFromDisk.setText("BytesReadFromDisk");
        }
        return jLabelBytesReadFromDisk;
    }

    private JLabel getJLabelDuplicateFilesFound() {
        if (jLabelDuplicateFilesFound == null) {
            jLabelDuplicateFilesFound = new JLabel();
            jLabelDuplicateFilesFound.setText("DuplicateFilesFound");
        }
        return jLabelDuplicateFilesFound;
    }

    private JLabel getJLabelDuplicateSetsFound() {
        if (jLabelDuplicateSetsFound == null) {
            jLabelDuplicateSetsFound = new JLabel();
            jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.LEFT);
            jLabelDuplicateSetsFound.setText("DuplicateSetsFound");
        }
        return jLabelDuplicateSetsFound;
    }

    private JTextField getJTextFieldCurrentFile() {
        if (jTextFieldCurrentFile == null) {
            jTextFieldCurrentFile = new JTextField();
            jTextFieldCurrentFile.setEditable(false);
            jTextFieldCurrentFile.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        }
        return jTextFieldCurrentFile;
    }

    private JLabel getJLabelCurrentFile() {
        if (jLabelCurrentFile == null) {
            jLabelCurrentFile = new JLabel();
            jLabelCurrentFile.setText("CurrentFile");
        }
        return jLabelCurrentFile;
    }

    private JProgressBar getJProgressBarOctets() {
        if (jProgressBarOctets == null) {
            jProgressBarOctets = new JProgressBar();
            jProgressBarOctets.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        }
        return jProgressBarOctets;
    }

    private JProgressBar getJProgressBarFiles() {
        if (jProgressBarFiles == null) {
            jProgressBarFiles = new JProgressBar();
            jProgressBarFiles.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
            //jProgressBarFiles.setString("0 %");
        }
        return jProgressBarFiles;
    }

    private JLabel getJLabelSearchProcess() {
        if (jLabelSearchProcess == null) {
            jLabelSearchProcess = new JLabel();
            jLabelSearchProcess.setText("Search progress...");
        }
        return jLabelSearchProcess;
    }

    private JScrollPane getJScrollPaneErrorList() {
        if (jScrollPaneErrorList == null) {
            jScrollPaneErrorList = new JScrollPane();
            jScrollPaneErrorList.setViewportView(getJTableErrorList());
        }
        return jScrollPaneErrorList;
    }

    private JTable getJTableErrorList() {
        if (jTableErrorList == null) {
            jTableErrorList = new JTable();
        }
        return jTableErrorList;
    }

    private JPanel getJPanelDuplicateBottom() {
        if (jPanelDuplicateBottom == null) {
            jPanelDuplicateBottom = new JPanel();
             jPanelDuplicateBottom.add(getJButtonCancelScan());
        }
        return jPanelDuplicateBottom;
    }

    private JButton getJButtonCancelScan() {
        if (jButtonCancelScan == null) {
            jButtonCancelScan = new JButton();
            jButtonCancelScan.setText("Cancel");
            jButtonCancelScan.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonCancelScanMouseMousePressed(event);
                }
            });
        }
        return jButtonCancelScan;
    }

    protected void jButtonCancelScanMouseMousePressed( MouseEvent event )
    {
        jButtonCancelScan.setEnabled( false );
        duplicateFC.setCancelProcess( true );
    }

    private void doScanPass1(
            Iterable<File>      directories,
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
        } );

        for(File f:directories) {
            Iterable<File> files = new FileIterator(
                    f,
                    fileFilter,
                    dirFilter
                    );
            slogger.info( "doScanPass1: examin:" + f );

            duplicateFC.pass1Add( files );
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
        displayRunning = true;
        displayPass = 1;
        displayFile = null;

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
        jLabelDuplicateSetsFound.setText(
                String.format(
                    txtDuplicateSetsFound,
                    duplicateFC.getDuplicateSetsCount()
                    )
                );
        jLabelDuplicateFilesFound.setText(
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
            jLabelBytesReadFromDisk.setText(
                String.format(
                    txtBytesReadFromDisk,
                    pass2BytesCount
                    )
                );
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

        jLabelStateWorking.setIcon( iconWorking );
        jProgressBarFiles.setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );
        jLabelCurrentFile.setText( txtCurrentDir );
        jTextFieldCurrentFile.setText( "" );
        jButtonCancelScan.setEnabled( true );
        tableModelErrorList.setRowCount( 0 );

        duplicateFC = new DuplicateFileCollector(
                    messageDigestFile,
                    ignoreEmptyFiles
                    );

        updateDisplayThread();
    }

    public void doScan(
            Iterable<File>                  directories,
            FileFilterBuilders              fileFilterBuilders,
            HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        slogger.info( "pass1" );
        duplicateFiles.clear();
        doScanPass1(
                directories,
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
        jButtonCancelScan.setEnabled( false );
        jLabelStateWorking.setIcon( null );
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

            final String[] fileExts  = includeFilesFileFilterBuilder.getNamePart().toArray( new String[0] );
            final int      fileExtsL = fileExts.length;

            slogger.info( "createFilesFileFilter: case1");
            slogger.info( "files regex=" + regex );
            slogger.info( "files skipHidden=" + skipHidden );
            slogger.info( "files skipReadOnly=" + skipReadOnly );
            slogger.info( "fileExtsL=" + fileExtsL);
            slogger.info( "fileExts=" + includeFilesFileFilterBuilder.getNamePart() );

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
                                pass1CountFile++;
                                pass1BytesCount += f.length();
                                return true;
                            }
                        }
                        return false;
                    }
                    return false;
                }
            };
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
}
