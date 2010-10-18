package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;
import cx.ath.choisnet.util.duplicate.DigestEventListener;
import cx.ath.choisnet.util.duplicate.DuplicateFileCollector;

/**
 *
 * @author Claude CHOISNET
 */
final public class DuplicateFilesFrame extends DuplicateFilesFrameVS4E {
    private static final long        serialVersionUID                         = 1L;
    private static final Logger      slogger                                  = Logger.getLogger( DuplicateFilesFrame.class );
    /* @serial */
    private JFileChooserInitializer  jFileChooserInitializer;
    /* @serial */
    private DefaultListModel         listModelDirectoryFiles                  = new DefaultListModel();
    /* @serial */
    private DefaultTableModel        tableModelErrorList;

    private DefaultListModel         /* <KeyFiles> */listModelDuplicatesFiles = new DefaultListModel();
    private DefaultListModel         /* <KeyFileState> */listModelKeptIntact = new DefaultListModel();
    private DefaultListModel         /* <KeyFileState> */listModelWillBeDeleted = new DefaultListModel();

    private HashMapSet<String,KeyFileState> duplicateFiles = new HashMapSet<String,KeyFileState>();

    private int                      state;
    private final static int         STATE_SELECT_DIRS                        = 0;
    private final static int         STATE_SEARCHING                          = 1;
    private final static int         STATE_RESULTS                            = 2;

    private ActionListener           actionListenerContextSubMenu;
    private final static String      ACTION_OBJECT                            = "KeyFile";
    private final static String      ACTION_COMMAND_DeleteThisFile            = "DeleteThisFile";
    private final static String      ACTION_COMMAND_KeepThisFile              = "KeepThisFile";
    private final static String      ACTION_COMMAND_DeleteAllExceptThisFile   = "DeleteAllExceptThisFile";
    private final static String      ACTION_COMMAND_KeepAllExceptThisFile     = "KeepAllExceptThisFile";
    private final static String      ACTION_COMMAND_DeleteDuplicateInDir      = "DeleteDuplicateInDir";
    private final static String      ACTION_COMMAND_KeepNonDuplicateInDir     = "KeepNonDuplicateInDir";
    private final static String      ACTION_COMMAND_KeepAllInDir              = "KeepAllInDir";
    private final static String      ACTION_COMMAND_DeleteAllInDir            = "DeleteAllInDir";

    private int                      countFile;
    DuplicateFileCollector           duplicateFC;
    private int                      fileCount;
    private int                      bytesCount;

    public DuplicateFilesFrame()
    {
        super();

        initFixComponents();
        slogger.info( "DuplicateFilesFrame() done." );
    }

    private void initFixComponents()
    {
        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource( "icon.png" )
                )
            );
        this.state = STATE_SELECT_DIRS;

        // panel 0
        jListSelectedDirs.setModel( listModelDirectoryFiles );

        // panel 1
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

        // panel 2
        jListDuplicatesFiles.setModel( listModelDuplicatesFiles );
        jListDuplicatesFiles.addListSelectionListener(
            new ListSelectionListener() {
                @Override
                public void valueChanged( ListSelectionEvent e )
                {
                    int i = jListDuplicatesFiles.getSelectedIndex();

                    if( i >= 0 ) {
                        KeyFiles kf = (KeyFiles)listModelDuplicatesFiles.get( i );
                        String   k = kf.getKey();

                        updateDisplayKeptDelete( k );
                    }
                }
            } );

        jListKeptIntact.setModel( listModelKeptIntact );
        jListKeptIntact.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if( e.getClickCount() == 2 ) { // Double-click
                    int index = jListKeptIntact.locationToIndex( e.getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelKeptIntact.remove( index );

                        DeleteThisFile( kf );
                    }
                }
            }
        } );

        jListWillBeDeleted.setModel( listModelWillBeDeleted );
        jListWillBeDeleted.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if( e.getClickCount() == 2 ) { // Double-click
                    int index = jListWillBeDeleted.locationToIndex( e
                            .getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelWillBeDeleted.remove( index );

                        KeptThisFile( kf );
                    }
                }
            }
        } );

        jTabbedPaneMain.setEnabled( false );

        updateDisplayAccordState();

        // build menu (VS4E does not support Box!)
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add( getJMenuLookAndFeel() );

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );

        createPopupMenus();
    }

    protected void updateDisplayAccordState()
    {
        slogger.info( "updateDisplayAccordState: " + state );
        jTabbedPaneMain.setSelectedIndex( state );
        jButtonNextStep.setText( "Continue" );

        if( state == STATE_SELECT_DIRS ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( true );
        }
        else if( state == STATE_SEARCHING ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( false );
            jMenuLookAndFeel.setEnabled( false );
            jProgressBarFiles.setIndeterminate( true );
            jProgressBarOctets.setIndeterminate( true );
            jLabelCurrentFile.setText( "Current directory:" );
            jTextFieldCurrentFile.setText( "" );
            jButtonCancelScan.setEnabled( true );
            tableModelErrorList.setRowCount( 0 );

            try {
                duplicateFC = new DuplicateFileCollector(
                        new MessageDigestFile( "MD5" ),
                        true // ignore empty files TODO parameter !
                        );
            }
            catch( NoSuchAlgorithmException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            updateDisplaySearching( new File( "" ), 0 );
            countFile = 0;
            // cancelScan = false;
            // instance.setCancelProcess(false);

            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    slogger.info( "pass1" );
                    doScanPass1();
                    slogger.info( "pass1 done" );

                    jProgressBarFiles.setIndeterminate( false );
                    jProgressBarOctets.setIndeterminate( false );
                    jLabelCurrentFile.setText( "Current File:" );
                    jTextFieldCurrentFile.setText( "" );

                    slogger.info( "pass2" );
                    doScanPass2();
                    slogger.info( "pass2 done" );

                    //
                    // Populate duplicateFiles
                    //
                    duplicateFiles.clear();
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
                    duplicateFC.deepClear();
                    duplicateFC = null;

                    jButtonNextStep.setEnabled( true );
                    jMenuLookAndFeel.setEnabled( true );
                    jButtonRestart.setEnabled( true );
                    jButtonCancelScan.setEnabled( false );
                }
            } ).start();
        }
        else if( state == STATE_RESULTS ) {
            jButtonRestart.setEnabled( true );
            jButtonNextStep.setText( "Remove (does not delete yet)" );

            listModelDuplicatesFiles.clear();
            listModelKeptIntact.clear();
            listModelWillBeDeleted.clear();

            for(Map.Entry<String,Set<KeyFileState>> e:duplicateFiles.entrySet()) {
                String              k    = e.getKey();
                Set<KeyFileState>   sf   = e.getValue();

                listModelDuplicatesFiles.addElement(
                        new KeyFiles( k, sf )
                        );
            }
        }
    }

    private void updateDisplaySearching( File f, int pass )
    {
        jTextFieldCurrentFile.setText( f.getAbsolutePath() );
        jLabelDuplicateSetsFound
                .setText( String.format( "Duplicate sets found: %d",
                        duplicateFC.getDuplicateSetsCount() ) );
        jLabelDuplicateFilesFound.setText( String.format(
                "Duplicate files found: %d",
                duplicateFC.getDuplicateFilesCount() ) );
        if( pass == 1 ) {
            jLabelBytesReadFromDisk.setText( String.format(
                    "Number of files processed: %d", countFile ) );
        } else {
            jLabelBytesReadFromDisk.setText( String.format(
                    "Bytes read from disk: %d bytes", bytesCount ) );
        }

        // jProgressBarFiles.setString( "kkkkk" );
        // jProgressBarOctets.setIndeterminate( true );
        // jLabelDuplicateSetsFound.setText("Duplicate sets found:");
        // jLabelDuplicateFilesFound.setText("Duplicate files found:");
        // jLabelBytesReadFromDisk.setText("Bytes read from disk:");
    }

    private void updateDisplayKeptDelete( String key )
    {
        listModelKeptIntact.clear();
        listModelWillBeDeleted.clear();

        Set<KeyFileState> s = duplicateFiles.get( key );

        if( s != null ) {
            for( KeyFileState sf : s ) {
                if( sf.isSelectedToDelete() ) {
                    listModelWillBeDeleted.addElement( sf );
                }
                else {
                    listModelKeptIntact.addElement( sf );
                }
            }
        }
        else {
            slogger.error( "Missing key:" + key );
        }
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator() {
                    private static final long serialVersionUID = 1L;

                    public void perfomeConfig( JFileChooser jfc )
                    {
                        super.perfomeConfig( jfc );

                        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                        jfc.setAccessory( new TabbedAccessory()
                                .addTabbedAccessory( new BookmarksAccessory(
                                        jfc,
                                        new BookmarksAccessoryDefaultConfigurator() ) ) );
                    }
                } );
        }
        return jFileChooserInitializer;
    }

    private boolean addDirectoryFile( File f )
    {
        if( f.isDirectory() ) {
            boolean isPresent = false;
            final int len = listModelDirectoryFiles.size();

            for( int i = 0; i < len; i++ ) {
                File aFile = (File)listModelDirectoryFiles.getElementAt( i );

                if( aFile.equals( f ) ) {
                    isPresent = true;
                    break;
                }
            }

            if( !isPresent ) {
                listModelDirectoryFiles.addElement( f );
                return true;
            }
        }

        Toolkit.getDefaultToolkit().beep();
        return false;
    }

    public JFileChooser getJFileChooser()
    {
        return getJFileChooserInitializer().getJFileChooser();
    }

    public static void main( String[] args )
    {
        slogger.info( "starting..." );
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                DuplicateFilesFrame frame = new DuplicateFilesFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "Duplicate Files Manager (ALPHA)" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.getJFileChooserInitializer();
            }
        } );
    }

    @Override
    protected void jButtonSelectDirMouseMousePressed( MouseEvent event )
    {
        JFileChooser jfc = getJFileChooser();
        int returnVal = jfc.showOpenDialog( this );

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            // File[] files = jfc.getSelectedFiles();
            //
            // for(File f:files) {
            // slogger.info( "selected(s):" + f );
            // listModelDirectoryFiles.addElement( f );
            // }
            slogger.info( "selected:" + jfc.getSelectedFile() );
            addDirectoryFile( jfc.getSelectedFile() );
        }
    }

    @Override
    protected void jButtonAddDirMouseMousePressed( MouseEvent event )
    {
        File f = new File( jTextFieldCurrentDir.getText() );
        addDirectoryFile( f );
    }

    @Override
    protected void jButtonRemDirMouseMousePressed( MouseEvent event )
    {
        final int[] selecteds = jListSelectedDirs.getSelectedIndices();

        for( int selected : selecteds ) {
            listModelDirectoryFiles.remove( selected );
        }
    }

    @Override
    protected void jButtonRestartMouseMousePressed( MouseEvent event )
    {
        if( jButtonRestart.isEnabled() ) {
            state = STATE_SELECT_DIRS;
            countFile = 0;
            duplicateFC = null;

            updateDisplayAccordState();
        }
    }

    @Override
    protected void jButtonNextStepMouseMousePressed( MouseEvent event )
    {
        slogger.info( "Next: " + state );

        if( state == STATE_SELECT_DIRS ) {
            if( listModelDirectoryFiles.size() > 0 ) {
                state = STATE_SEARCHING;
            }
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        else if( state == STATE_SEARCHING ) {
            state = STATE_RESULTS;
        }
        else if( state == STATE_RESULTS ) {
            throw new UnsupportedOperationException();
        }

        updateDisplayAccordState();
    }

    @Override
    protected void jButtonCancelScanMouseMousePressed( MouseEvent event )
    {
        jButtonCancelScan.setEnabled( false );
        duplicateFC.setCancelProcess( true );
    }

    @Override
    protected void jButtonPrevSetMouseMousePressed( MouseEvent event )
    {
        final int size = jListDuplicatesFiles.getModel().getSize();

        if( size > 0 ) {
            int i = jListDuplicatesFiles.getSelectedIndex() - 1;

            if( i < 0 ) {
                i = size - 1;
            }
            jListDuplicatesFiles.setSelectedIndex( i );
        }
    }

    @Override
    protected void jButtonNextSetMouseMousePressed( MouseEvent event )
    {
        final int size = jListDuplicatesFiles.getModel().getSize();

        if( size > 0 ) {
            int i = jListDuplicatesFiles.getSelectedIndex() + 1;

            if( i >= size ) {
                i = 0;
            }
            jListDuplicatesFiles.setSelectedIndex( i );
        }
    }

    private void doScanPass1()
    {
        slogger.info( "doScanPass1: 1" );

        // FileFilter and Listener for pass 1
        FileFilter dirFilter = new FileFilter() {
            @Override
            public boolean accept( File f )
            {
                // slogger.info("dir:" + f);
                updateDisplaySearching( f, 1 );
                return true;
            }
        };

        slogger.info( "doScanPass1: 2" );

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept( File f )
            {
                if( f.isFile() ) {
                    countFile++;
                    return true;
                }
                return false;
            }
        };

        slogger.info( "doScanPass1: 3" );

        // Listener for pass 2
        duplicateFC.addDigestEventListener( new DigestEventListener() {
            @Override
            public void computeDigest( File file )
            {
                // slogger.info("computeDigest:" + file);
                // jTextFieldCurrentFile.setText( file.getAbsolutePath() );
                updateDisplaySearching( file, 2 );
                fileCount++;
                bytesCount += file.length();
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

        final int len = listModelDirectoryFiles.size();

        for( int i = 0; i < len; i++ ) {
            File root = (File)listModelDirectoryFiles.getElementAt( i );
            Iterable<File> files = new FileIterator( root, fileFilter,
                    dirFilter );
            slogger.info( "doScanPass1: 4:" + root );

            duplicateFC.pass1Add( files );
        }

        slogger.info( "doScanPass1: done" );
    }

    private void doScanPass2()
    {
        fileCount = 0;
        bytesCount = 0;
        duplicateFC.pass2();
    }

    private void DeleteThisFile( KeyFileState kf )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( true );
            updateDisplayKeptDelete( kf.getKey() );
        }
    }

    private void KeptThisFile( KeyFileState kf )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( false );
            updateDisplayKeptDelete( kf.getKey() );
        }
    }

    private void createPopupMenus()
    {
        createPopupMenus( jListKeptIntact );
        createPopupMenus( jListWillBeDeleted );
    }

    private void createPopupMenus( final JList jList )
    {

        final JPopupMenuForJList m = new JPopupMenuForJList( jList ) {
            protected JPopupMenu createContextMenu( final int rowIndex )
            {
                JPopupMenu cm = super.createContextMenu( rowIndex );

                addCopyMenuItem( cm, new JMenuItem( "Copy" ), rowIndex );

                KeyFileState kf = (KeyFileState)getValueAt( rowIndex );

                add(
                    cm,
                    new JMenuItem( "Open (Handle by System)" ),
                    openFileActionListener( kf.getFile() )
                    );
                cm.addSeparator();

                if( jList == jListKeptIntact ) {
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem("Delete this file" ),
                        ACTION_COMMAND_DeleteThisFile,
                        kf
                        );
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem("Delete all except this file" ),
                        ACTION_COMMAND_DeleteAllExceptThisFile,
                        kf
                        );
                } else {
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem("Keep this file" ),
                        ACTION_COMMAND_KeepThisFile,
                        kf
                        );
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem("Keep all except this file" ),
                        ACTION_COMMAND_KeepAllExceptThisFile,
                        kf
                        );
                }

                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu("Delete duplicate in" ),
                    ACTION_COMMAND_DeleteDuplicateInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu("Keep nonduplicate in" ),
                    ACTION_COMMAND_KeepNonDuplicateInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu("Keep all in dir" ),
                    ACTION_COMMAND_KeepAllInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu("Delete all in dir" ),
                    ACTION_COMMAND_DeleteAllInDir,
                    kf
                    );

                return cm;
            }
        };
        m.setMenu();
    }

    private ActionListener openFileActionListener( final File file )
    {
        return new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                openDesktop( file );
            }
        };
    }

    private void addContextSubMenuActionCommand(
            JPopupMenuForJList  m,
            JPopupMenu          parentMenu,
            JMenuItem           menu,
            String              actionCommand,
            KeyFileState        kf
            )
    {
        m.add(
            parentMenu,
            menu,
            getActionListenerContextSubMenu(),
            actionCommand,
            ACTION_OBJECT,
            kf
            );
    }

    private void addContextSubMenuActionCommand(
            JPopupMenuForJList  m,
            JMenu               parentMenu,
            String              actionCommand,
            KeyFileState        kf
            )
    {
        m.add(
                parentMenu,
                new JMenuItem( kf.getFile().getPath() ),
                getActionListenerContextSubMenu(),
                actionCommand,
                ACTION_OBJECT,
                kf
                );
    }

    private void addContextSubMenuActionCommandRec(
            JPopupMenuForJList  m,
            JPopupMenu          parentMenu,
            JMenu               menu,
            String              actionCommand,
            KeyFileState        kf
            )
    {
        m.add( parentMenu, menu );

        final String k = kf.getKey();
        File         f = kf.getFile().getParentFile();

        while( f != null ) {
            addContextSubMenuActionCommand(
                    m,
                    menu,
                    actionCommand,
                    new KeyFileState( k, f ) );

            f = f.getParentFile();
        }
    }

    private ActionListener getActionListenerContextSubMenu()
    {
        if( actionListenerContextSubMenu == null ) {
            this.actionListenerContextSubMenu = new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    final JMenuItem    sourceItem = (JMenuItem) e.getSource();
                    final KeyFileState kf         = (KeyFileState)sourceItem.getClientProperty(ACTION_OBJECT);
                    final String       cmd        = sourceItem.getActionCommand();

                    slogger.info( "cmd:" + cmd + " - " + kf );

                    if( ACTION_COMMAND_DeleteThisFile.equals( cmd ) ) {
                        DeleteThisFile(kf);
                    }
                    else if( ACTION_COMMAND_KeepThisFile.equals( cmd ) ) {
                        KeptThisFile(kf);
                    }
                    else if( ACTION_COMMAND_DeleteAllExceptThisFile.equals( cmd ) ) {
                        final String k    = kf.getKey();
                        final File   file = kf.getFile();

                        Set<KeyFileState> s = duplicateFiles.get( k );

                        if( s != null ) {
                            for(KeyFileState f:s) {
                                if( file.equals( f.getFile() ) ) {
                                    f.setSelectedToDelete( false );
                                }
                                else {
                                    f.setSelectedToDelete( true );
                                }
                            }
                        }
                        updateDisplayKeptDelete( k );
                    }
                    else if( ACTION_COMMAND_KeepAllExceptThisFile.equals( cmd ) ) {
                        final String k    = kf.getKey();
                        final File   file = kf.getFile();

                        Set<KeyFileState> s = duplicateFiles.get( k );

                        if( s != null ) {
                            for(KeyFileState f:s) {
                                if( file.equals( f.getFile() ) ) {
                                    f.setSelectedToDelete( true );
                                }
                                else {
                                    f.setSelectedToDelete( false );
                                }
                            }
                        }
                        updateDisplayKeptDelete( k );
                    }
                    else if( ACTION_COMMAND_DeleteDuplicateInDir.equals( cmd ) ) {
                        // Delete all files (dir/key), but keep one
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath();

                        //Look for all file in this dir !
                        Set<KeyFileState> s = duplicateFiles.get( k );
                        int               c = 0;

                        for(KeyFileState f:s) {
                            if( !f.isSelectedToDelete() ) {
                                if( f.isInDirectory( dirPath ) ) {
                                   c++;
                                }
                            }
                        }

                        // Keep one file !
                        int maxDel = c - 1;
                        c = 0;

                        for(KeyFileState f:s) {
                            if( !f.isSelectedToDelete() ) {
                                if( f.isInDirectory( dirPath ) ) {
                                    if( c < maxDel ) {
                                        f.setSelectedToDelete( true );
                                        c++;
                                    }
                                }
                            }
                        }

                        updateDisplayKeptDelete( k );
                    }
                    else if( ACTION_COMMAND_KeepNonDuplicateInDir.equals( cmd ) ) {
                        // Keep at least on file in this dir/key.
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath();

                        //Look for all file in this dir !
                        Set<KeyFileState> s = duplicateFiles.get( k );
                        int               c = 0;

                        for(KeyFileState f:s) {
                            if( !f.isSelectedToDelete() ) {
                                if( f.isInDirectory( dirPath ) ) {
                                   c++;
                                }
                            }
                        }

                        Iterator<KeyFileState> iter = s.iterator();

                        while( c== 0 && iter.hasNext() ) {
                            KeyFileState f = iter.next();

                            if( f.isSelectedToDelete() ) {
                                if( f.isInDirectory( dirPath ) ) {
                                    f.setSelectedToDelete( false );
                                    c++;
                                }
                            }
                        }

                        updateDisplayKeptDelete( k );
                    }
                    else if( ACTION_COMMAND_KeepAllInDir.equals( cmd )) {
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath();

                        for(KeyFileState f:duplicateFiles.get(k)) {
                            if( f.isInDirectory( dirPath ) ) {
                                f.setSelectedToDelete( false );
                            }
                        }
                        updateDisplayKeptDelete( k );
                    }
                    else if( ACTION_COMMAND_DeleteAllInDir.equals( cmd ) ) {
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath();

                        for(KeyFileState f:duplicateFiles.get(k)) {
                            if( f.isInDirectory( dirPath ) ) {
                                f.setSelectedToDelete( true );
                            }
                            updateDisplayKeptDelete( k );
                        }
                    }
                    else {
                        slogger.error("Don't known how to handle: " + cmd);
                    }

                }
            };
        }
        return actionListenerContextSubMenu;
    }

    public final static void openDesktop( File file )
    {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            slogger.info( "trying to open: " + file );
            desktop.open( file );
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
