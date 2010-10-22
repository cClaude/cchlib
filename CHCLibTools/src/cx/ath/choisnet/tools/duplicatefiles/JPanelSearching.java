package cx.ath.choisnet.tools.duplicatefiles;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
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
    private DefaultTableModel       tableModelErrorList;
    private int                     pass1CountFile;
    private long                    pass1BytesCount;
    private int                     pass2CountFile;
    private long                    pass2BytesCount;

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

    public JPanelSearching()
    {
        initComponents();
    }

    private void initComponents()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getJLabelSearchProcess());
        add(getJProgressBarFiles());
        add(getJProgressBarOctets());
        add(getJLabelCurrentFile());
        add(getJTextFieldCurrentFile());
        add(getJLabelDuplicateSetsFound());
        add(getJLabelDuplicateFilesFound());
        add(getJLabelBytesReadFromDisk());
        add(getJScrollPaneErrorList());
        add(getJPanelDuplicateBottom());
        setSize( 320, 240 );
    }

    public void initFixComponents()
    {
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
            jLabelDuplicateSetsFound.setText("DuplicateSetsFound");
        }
        return jLabelDuplicateSetsFound;
    }

    private JTextField getJTextFieldCurrentFile() {
        if (jTextFieldCurrentFile == null) {
            jTextFieldCurrentFile = new JTextField();
            jTextFieldCurrentFile.setEditable( false );
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
            //jProgressBarOctets.setString("0 %");
        }
        return jProgressBarOctets;
    }

    private JProgressBar getJProgressBarFiles() {
        if (jProgressBarFiles == null) {
            jProgressBarFiles = new JProgressBar();
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
            FileFilterBuilder   directoriesFileFilterBuilder,
            FileFilterBuilder   filesFileFilterBuilder
            )
    {
        displayPass = 1;
        slogger.info( "doScanPass1: init()" );

        // FileFilter and Listener for pass 1

//        //TO DO: construire un automate pour tester
//        //      une chaîne par rapport à un groupe de motif
//        final String[] dirNames  = directoriesFileFilterBuilder.getNamePart().toArray( new String[0] );
//        final int      dirNamesL = dirNames.length;
//
//        slogger.info( "dirNamesL=" + dirNamesL );
//        slogger.info( "dirNames=" + directoriesFileFilterBuilder.getNamePart() );
//        FileFilter dirFilter = new FileFilter() {
//            @Override
//            public boolean accept( File f )
//            {
//                if( dirNamesL > 0 ) {
//                    String name = f.getName().toLowerCase();
//
//                    for(int i=0;i<dirNamesL;i++) {
//                        if(name.equals( dirNames[i] )) {
//                            return false;
//                        }
//                    }
//                }
//
//                // slogger.info("dir:" + f);
//                //updateDisplaySearching( f, 1 );
//                displayFile = f;
//                return true;
//            }
//        };
        FileFilter dirFilter = createDirectoriesFileFilter( directoriesFileFilterBuilder );

//        final String[] fileExts  = filesFileFilterBuilder.getNamePart().toArray( new String[0] );
//        final int      fileExtsL = fileExts.length;
//
//        slogger.info( "fileExtsL=" + fileExtsL);
//        slogger.info( "fileExts=" + filesFileFilterBuilder.getNamePart() );
//
//        FileFilter fileFilter = new FileFilter() {
//            @Override
//            public boolean accept( File f )
//            {
//                if( f.isFile() ) {
//                    if( fileExtsL > 0 ) {
//                        String name = f.getName().toLowerCase();
//
//                        for(int i=0;i<fileExtsL;i++) {
//                            if(name.endsWith( fileExts[i] )) {
//                                pass1CountFile++;
//                                pass1BytesCount += f.length();
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//                    else {
//                        pass1CountFile++;
//                        pass1BytesCount += f.length();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        };
        FileFilter fileFilter = createFilesFileFilter(filesFileFilterBuilder);

        // Listener for pass 2
        duplicateFC.addDigestEventListener( new DigestEventListener() {
            @Override
            public void computeDigest( File file )
            {
                //updateDisplaySearching( file, 2 );
                displayFile = file;
                pass2CountFile++;
                pass2BytesCount += file.length();
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

        //pass1CountFile = duplicateFC.getDuplicateFilesCount();
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
        //jProgressBarFiles.setString( "0 %" );
        jProgressBarOctets.setMaximum( Math.round( pass1BytesCount/1024) );
        //jProgressBarOctets.setString( "0 %" );
        duplicateFC.pass2();

        displayFile = null;
        displayRunning = false;
        pass2CountFile = pass1CountFile;
        pass2BytesCount = pass1BytesCount;
        //updateDisplaySearching( null, 2);
        displayRunning = false;
    }

    private File displayFile;
    private int  displayPass;
    private boolean displayRunning;

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
                    "Duplicate sets found: %d",
                    duplicateFC.getDuplicateSetsCount()
                    )
                );
        jLabelDuplicateFilesFound.setText(
                String.format(
                    "Duplicate files found: %d",
                    duplicateFC.getDuplicateFilesCount()
                    )
                );

        if( displayPass == 1 ) {
            jProgressBarFiles.setString(
                    String.format(
                        "Number of files processed: %d",
                        pass1CountFile
                        )
                    );
            jProgressBarOctets.setString(
                String.format(
                    "Octects to check: %d",
                    pass1BytesCount
                    )
                );
        }
        else {
            jLabelBytesReadFromDisk.setText(
                String.format(
                    "Bytes read from disk: %d bytes",
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

        //jProgressBarFiles.setStringPainted( false );
        jProgressBarFiles.setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );
        jLabelCurrentFile.setText( "Current directory:" );
        jTextFieldCurrentFile.setText( "" );
        jButtonCancelScan.setEnabled( true );
        tableModelErrorList.setRowCount( 0 );

        duplicateFC = new DuplicateFileCollector(
                    messageDigestFile, //new MessageDigestFile( "MD5" ),
                    ignoreEmptyFiles//jPanel1Config.IsIgnoreEmptyFiles()
                    );

        //updateDisplaySearching( null, 0 );
        updateDisplayThread();
    }

    public void doScan(
            Iterable<File>                  directories,
            FileFilterBuilder               directoriesFileFilterBuilder,
            FileFilterBuilder               filesFileFilterBuilder,
            HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        slogger.info( "pass1" );
        duplicateFiles.clear();
        doScanPass1(
                directories,
                directoriesFileFilterBuilder,
                filesFileFilterBuilder
                );
        slogger.info( "pass1 done" );

        //jProgressBarFiles.setStringPainted( true );
        jProgressBarFiles.setIndeterminate( false );
        jProgressBarOctets.setIndeterminate( false );
        jLabelCurrentFile.setText( "Current File:" );
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
    }
    
    
    private FileFilter createFilesFileFilter(
            FileFilterBuilder   filesFileFilterBuilder
            )
    {
        final Pattern regex      = filesFileFilterBuilder.getRegExp();
        final boolean skipHidden = filesFileFilterBuilder.getIgnoreHidden();
        
        final String[] fileExts  = filesFileFilterBuilder.getNamePart().toArray( new String[0] );
        final int      fileExtsL = fileExts.length;

        slogger.info( "files regex=" + regex );
        slogger.info( "files skipHidden=" + skipHidden );
        slogger.info( "fileExtsL=" + fileExtsL);
        slogger.info( "fileExts=" + filesFileFilterBuilder.getNamePart() );

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
                    // RegEx
                    if( regex != null ) {
                        if( ! regex.matcher(f.getName()).matches() ) {
                            return false;
                        }
                    }
                    // Extensions
                    if( fileExtsL > 0 ) {
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
                    else {
                        pass1CountFile++;
                        pass1BytesCount += f.length();
                        return true;
                    }
                }
                return false;
            }
        };
    }
    
    private FileFilter createDirectoriesFileFilter(
            FileFilterBuilder directoriesFileFilterBuilder
            )
    {
        final Pattern regex      = directoriesFileFilterBuilder.getRegExp();
        final boolean skipHidden = directoriesFileFilterBuilder.getIgnoreHidden();
        
        //TODO: construire un automate pour tester
        //      une chaîne par rapport à un groupe de motif
        final String[] dirNames  = directoriesFileFilterBuilder.getNamePart().toArray( new String[0] );
        final int      dirNamesL = dirNames.length;

        slogger.info( "dirs regex=" + regex );
        slogger.info( "dirs skipHidden=" + skipHidden );
        slogger.info( "dirNamesL=" + dirNamesL );
        slogger.info( "dirNames=" + directoriesFileFilterBuilder.getNamePart() );

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
                if( dirNamesL > 0 ) {
                    String name = f.getName().toLowerCase();

                    for(int i=0;i<dirNamesL;i++) {
                        if(name.equals( dirNames[i] )) {
                            return false;
                        }
                    }
                }

                displayFile = f;
                return true;
            }
        };
    }
}
