package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.XComboBoxPattern;
import cx.ath.choisnet.swing.list.JPopupMenuForJList;
import cx.ath.choisnet.util.HashMapSet;

/**
 * 
 * @author Claude CHOISNET
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelResult extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelResult.class );

    private JPanel jPanelResultsButtons;
    private JButton jButtonPrevSet;
    private JButton jButtonNextSet;
    private JTextField jTextFieldFileInfo;

    private JPanel jPanelExtraCmd;
    private JToggleButton jToggleButtonSelectByRegEx;
    private XComboBoxPattern xComboBoxPatternRegEx;
    private JCheckBox jCheckBoxKeepOne;
    private JButton jButtonRegExDelete;
    private JButton jButtonRegExKeep;

    private JSplitPane jSplitPaneResultMain;
    private JSplitPane jSplitPaneResultRight;
    private JScrollPane jScrollPaneDuplicatesFiles;
    private JScrollPane jScrollPaneWillBeDeleted;
    private JList jListWillBeDeleted;
    private JScrollPane jScrollPaneKeptIntact;
    private JList jListKeptIntact;
    private JList jListDuplicatesFiles;

    // TODO: Must be restore by parent !
    private transient DFToolKit dFToolKit; 

    private HashMapSet<String,KeyFileState>     duplicateFiles;// = new HashMapSet<String,KeyFileState>();
    private DefaultListModel/* <KeyFiles> */    listModelDuplicatesFiles = new DefaultListModel();
    private DefaultListModel/* <KeyFileState> */listModelKeptIntact = new DefaultListModel();
    private DefaultListModel/* <KeyFileState> */listModelWillBeDeleted = new DefaultListModel();

    private ActionListener      actionListenerContextSubMenu;
    private final static String ACTION_OBJECT                            = "KeyFile";
    private final static String ACTION_COMMAND_DeleteThisFile            = "DeleteThisFile";
    private final static String ACTION_COMMAND_KeepThisFile              = "KeepThisFile";
    private final static String ACTION_COMMAND_DeleteAllExceptThisFile   = "DeleteAllExceptThisFile";
    private final static String ACTION_COMMAND_KeepAllExceptThisFile     = "KeepAllExceptThisFile";
    private final static String ACTION_COMMAND_DeleteDuplicateInDir      = "DeleteDuplicateInDir";
    private final static String ACTION_COMMAND_KeepNonDuplicateInDir     = "KeepNonDuplicateInDir";
    private final static String ACTION_COMMAND_KeepAllInDir              = "KeepAllInDir";
    private final static String ACTION_COMMAND_DeleteAllInDir            = "DeleteAllInDir";

    @I18nString private String txtCopy = "Copy";
    @I18nString private String txtOpenFile = "Open (Handle by System)";
    @I18nString private String txtDeleteThisFile = "Delete this file";
    @I18nString private String txtDeleteAllExceptThisFile  = "Delete all except this file";
    @I18nString private String txtKeepThisFile = "Keep this file";
    @I18nString private String txtKeepAllExceptThisFile = "Keep all except this file";
    @I18nString private String txtDeleteDuplicateIn = "Delete duplicate in";
    @I18nString private String txtKeepNonDuplicateIn = "Keep nonduplicate in";
    @I18nString private String txtKeepAllInDir = "Keep all in dir";
    @I18nString private String txtDeleteAllInDir = "Delete all in dir";
    @I18nString private String txtHiddenFirstLetter = "H";
    //@I18nString private String txtCanExecuteFirstLetter = "E";
    @I18nString private String txtCanWriteFirstLetter = "W";
    @I18nString private String txtCanReadFirstLetter = "R";
    @I18nString private String txtPatternSyntaxExceptionTitle = "Not valid Regular Expression";

    public JPanelResult()
    {
        initComponents();
    }

    private void initComponents() {
    	setLayout(new BorderLayout());
    	add(getJSplitPaneResultMain(), BorderLayout.CENTER);
    	add(getJPanelResultsButtons(), BorderLayout.NORTH);
    	add(getJPanelExtraCmd(), BorderLayout.SOUTH);
    	setSize(488, 240);
    }

    private XComboBoxPattern getXComboBoxPatternRegEx() {
    	if (xComboBoxPatternRegEx == null) {
    	    xComboBoxPatternRegEx = new XComboBoxPattern();
    	}
    	return xComboBoxPatternRegEx;
    }

    private JButton getJButtonRegExDelete() {
    	if (jButtonRegExDelete == null) {
    		jButtonRegExDelete = new JButton();
    		jButtonRegExDelete.setText("Delete");
    		jButtonRegExDelete.addMouseListener(new MouseAdapter() {
    			public void mousePressed(MouseEvent event) {
    				jButtonRegExDeleteMouseMousePressed(event);
    			}
    		});
    	}
    	return jButtonRegExDelete;
    }

    private JButton getJButtonRegExKeep() {
    	if (jButtonRegExKeep == null) {
    		jButtonRegExKeep = new JButton();
    		jButtonRegExKeep.setText("Keep");
    		jButtonRegExKeep.addMouseListener(new MouseAdapter() {
    			public void mousePressed(MouseEvent event) {
    				jButtonRegExKeepMouseMousePressed(event);
    			}
    		});
    	}
    	return jButtonRegExKeep;
    }

    private JCheckBox getJCheckBoxKeepOne() {
    	if (jCheckBoxKeepOne == null) {
    		jCheckBoxKeepOne = new JCheckBox();
    		jCheckBoxKeepOne.setText("Keep one");
    		jCheckBoxKeepOne.setSelected( true );
    	}
    	return jCheckBoxKeepOne;
    }

    private JToggleButton getJToggleButtonSelectByRegEx() {
    	if (jToggleButtonSelectByRegEx == null) {
    		jToggleButtonSelectByRegEx = new JToggleButton();
    		jToggleButtonSelectByRegEx.setText("Select by RegEx");
    		jToggleButtonSelectByRegEx.addChangeListener(new ChangeListener() {
    			public void stateChanged(ChangeEvent event) {
    				jToggleButtonSelectByRegExChangeStateChanged(event);
    			}
    		});
    	}
    	return jToggleButtonSelectByRegEx;
    }

    private JPanel getJPanelExtraCmd() {
    	if (jPanelExtraCmd == null) {
    		jPanelExtraCmd = new JPanel();
    		jPanelExtraCmd.setLayout(new BoxLayout(jPanelExtraCmd, BoxLayout.X_AXIS));
    		jPanelExtraCmd.add(getJToggleButtonSelectByRegEx());
    		jPanelExtraCmd.add(getXComboBoxPatternRegEx());
    		jPanelExtraCmd.add(getJCheckBoxKeepOne());
    		jPanelExtraCmd.add(getJButtonRegExDelete());
    		jPanelExtraCmd.add(getJButtonRegExKeep());
    	}
    	return jPanelExtraCmd;
    }

    public void initFixComponents(
            HashMapSet<String,KeyFileState> duplicateFiles,
            DFToolKit                       dFToolKit
            )
    {
        this.duplicateFiles = duplicateFiles;
        this.dFToolKit      = dFToolKit;

        jListDuplicatesFiles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
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

        jListKeptIntact.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jListKeptIntact.setModel( listModelKeptIntact );
        jListKeptIntact.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if( e.getClickCount() > 0 ) {
                    jListWillBeDeleted.clearSelection();
                    int index = jListKeptIntact.locationToIndex( e.getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelKeptIntact.get( index );

                        displayFileInfo( kf );
                    }
                }
                if( e.getClickCount() == 2 ) { // Double-click
                    int index = jListKeptIntact.locationToIndex( e.getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelKeptIntact.remove( index );

                        DeleteThisFile( kf );
                    }
                }
            }
        } );

        jListWillBeDeleted.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jListWillBeDeleted.setModel( listModelWillBeDeleted );
        jListWillBeDeleted.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                if( e.getClickCount() > 0 ) {
                    jListKeptIntact.clearSelection();
                    int index = jListWillBeDeleted.locationToIndex( e.getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelWillBeDeleted.get( index );

                        displayFileInfo( kf );
                    }
                }
                if( e.getClickCount() == 2 ) { // Double-click
                    int index = jListWillBeDeleted.locationToIndex( e.getPoint() );

                    if( index >= 0 ) {
                        KeyFileState kf = (KeyFileState)listModelWillBeDeleted.remove( index );

                        KeptThisFile( kf );
                    }
                }
            }
        });
        createPopupMenus();

        Color errorColor = Color.RED;

        xComboBoxPatternRegEx.setErrorBackGroundColor( errorColor );
        xComboBoxPatternRegEx.setModel(
            new DefaultComboBoxModel(new Object[] { ".*\\.jpg", "item1", "item2", "item3" })
            );

        updateDisplay();
    }

    public void updateDisplayMode( ConfigMode mode )
    {
        updateDisplay();
    }

    public void initDisplay()
    {
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

    public void updateDisplay()
    {
        if( dFToolKit.getConfigMode() == ConfigMode.BEGINNER ) {
            jToggleButtonSelectByRegEx.setSelected( false );
            jToggleButtonSelectByRegEx.setEnabled( false );
        }
        else {
            jToggleButtonSelectByRegEx.setEnabled( true );
        }

        boolean useRegEx = jToggleButtonSelectByRegEx.isSelected();

        //jTextFieldRegEx.setVisible(useRegEx);
        xComboBoxPatternRegEx.setVisible(useRegEx);
        jCheckBoxKeepOne.setVisible(useRegEx);
        jButtonRegExDelete.setVisible(useRegEx);
        jButtonRegExKeep.setVisible(useRegEx);

        int index = jListDuplicatesFiles.getSelectedIndex();

        if( index >= 0 ) {
            KeyFiles kf = (KeyFiles)listModelDuplicatesFiles.getElementAt( index );
            
            updateDisplayKeptDelete( kf.getKey() );
        }
    }

    private JSplitPane getJSplitPaneResultMain() {
        if (jSplitPaneResultMain == null) {
            jSplitPaneResultMain = new JSplitPane();
            jSplitPaneResultMain.setDividerLocation(100);
            jSplitPaneResultMain.setLeftComponent(getJScrollPaneDuplicatesFiles());
            jSplitPaneResultMain.setRightComponent(getJSplitPaneResultRight());
        }
        return jSplitPaneResultMain;
    }

    private JPanel getJPanelResultsButtons() {
    	if (jPanelResultsButtons == null) {
    		jPanelResultsButtons = new JPanel();
    		jPanelResultsButtons.setLayout(new BoxLayout(jPanelResultsButtons, BoxLayout.X_AXIS));
    		jPanelResultsButtons.add(getJButtonPrevSet());
    		jPanelResultsButtons.add(getJButtonNextSet());
    		jPanelResultsButtons.add(getJTextFieldFileInfo());
    	}
    	return jPanelResultsButtons;
    }

    private JTextField getJTextFieldFileInfo() {
        if (jTextFieldFileInfo == null) {
            jTextFieldFileInfo = new JTextField();
            jTextFieldFileInfo.setEditable( false );
            jTextFieldFileInfo.setHorizontalAlignment( JTextField.CENTER );
        }
        return jTextFieldFileInfo;
    }

    private JButton getJButtonNextSet() {
        if (jButtonNextSet == null) {
            jButtonNextSet = new JButton();
            jButtonNextSet.setText(">>");
            jButtonNextSet.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    jButtonNextSetMouseMousePressed(event);
                }
            });
        }
        return jButtonNextSet;
    }

    private JButton getJButtonPrevSet() {
        if (jButtonPrevSet == null) {
            jButtonPrevSet = new JButton();
            jButtonPrevSet.setText("<<");
            jButtonPrevSet.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    jButtonPrevSetMouseMousePressed(event);
                }
            });
        }
        return jButtonPrevSet;
    }
    
    private JSplitPane getJSplitPaneResultRight() {
        if (jSplitPaneResultRight == null) {
            jSplitPaneResultRight = new JSplitPane();
            jSplitPaneResultRight.setDividerLocation(100);
            jSplitPaneResultRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
            jSplitPaneResultRight.setTopComponent(getJScrollPaneKeptIntact());
            jSplitPaneResultRight.setBottomComponent(getJScrollPaneWillBeDeleted());
        }
        return jSplitPaneResultRight;
    }

    private JScrollPane getJScrollPaneDuplicatesFiles() {
        if (jScrollPaneDuplicatesFiles == null) {
            jScrollPaneDuplicatesFiles = new JScrollPane();
            jScrollPaneDuplicatesFiles.setViewportView(getJListDuplicatesFiles());
        }
        return jScrollPaneDuplicatesFiles;
    }
    
    private JScrollPane getJScrollPaneWillBeDeleted() {
        if (jScrollPaneWillBeDeleted == null) {
            jScrollPaneWillBeDeleted = new JScrollPane();
            jScrollPaneWillBeDeleted.setViewportView(getJListWillBeDeleted());
        }
        return jScrollPaneWillBeDeleted;
    }

    private JList getJListWillBeDeleted() {
        if (jListWillBeDeleted == null) {
            jListWillBeDeleted = new JList();
        }
        return jListWillBeDeleted;
    }

    private JScrollPane getJScrollPaneKeptIntact() {
        if (jScrollPaneKeptIntact == null) {
            jScrollPaneKeptIntact = new JScrollPane();
            jScrollPaneKeptIntact.setViewportView(getJListKeptIntact());
        }
        return jScrollPaneKeptIntact;
    }

    private JList getJListKeptIntact() {
        if (jListKeptIntact == null) {
            jListKeptIntact = new JList();
        }
        return jListKeptIntact;
    }

    private JList getJListDuplicatesFiles() {
        if (jListDuplicatesFiles == null) {
            jListDuplicatesFiles = new JList();
        }
        return jListDuplicatesFiles;
    }

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
                    new KeyFileState( k, f ) 
                    );

            f = f.getParentFile();
        }
    }
    
    private void displayFileInfo( KeyFileState kf )
    {
        if( kf == null ) {
            jTextFieldFileInfo.setText( "" );
        }
        else {
            File f = kf.getFile();
            Locale locale = dFToolKit.getLocale();

            String date = DateFormat.getDateTimeInstance(
                    DateFormat.FULL,
                    DateFormat.SHORT, 
                    locale 
                    ).format(
                        new Date(f.lastModified())
                        );

            jTextFieldFileInfo.setText( 
                String.format( 
                        "%s - %d [%s%s%s] %s",
                        f.getName(),
                        f.length(),
                        //f.canExecute()?txtCanExecuteFirstLetter:"-",
                        f.canRead()?txtCanReadFirstLetter:"-",
                        f.canWrite()?txtCanWriteFirstLetter:"-",
                        f.isHidden()?txtHiddenFirstLetter:"-",
                        date
                        )
                );
        }
    }

    private void updateDisplayKeptDelete( String key )
    {
        listModelKeptIntact.clear();
        listModelWillBeDeleted.clear();
        displayFileInfo( null );

        Set<KeyFileState>       s  = duplicateFiles.get( key );
        SortedSet<KeyFileState> ss = new TreeSet<KeyFileState>();
        
        if( s != null ) {
            ss.addAll( s );

            for( KeyFileState sf : ss ) {
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
        ss.clear();
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

    private ActionListener createOpenFileActionListener( final File file )
    {
        return new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    dFToolKit.openDesktop( file );
                }
            };
    }

    private void createPopupMenus()
    {
        createPopupMenus( jListKeptIntact );
        createPopupMenus( jListWillBeDeleted );
    }

    private void createPopupMenus( final JList jList )
    {
        final JPopupMenuForJList m = new JPopupMenuForJList( jList ) 
        {
            @Override
            protected JPopupMenu createContextMenu( final int rowIndex )
            {
                JPopupMenu cm = super.createContextMenu( rowIndex );

                addCopyMenuItem( cm, new JMenuItem( txtCopy ), rowIndex );

                KeyFileState kf = (KeyFileState)getValueAt( rowIndex );

                add(
                    cm,
                    new JMenuItem( txtOpenFile ),
                    createOpenFileActionListener( kf.getFile() )
                    );
                cm.addSeparator();

                if( jList == jListKeptIntact ) {
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtDeleteThisFile),
                        ACTION_COMMAND_DeleteThisFile,
                        kf
                        );
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtDeleteAllExceptThisFile),
                        ACTION_COMMAND_DeleteAllExceptThisFile,
                        kf
                        );
                }
                else {
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtKeepThisFile ),
                        ACTION_COMMAND_KeepThisFile,
                        kf
                        );
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtKeepAllExceptThisFile),
                        ACTION_COMMAND_KeepAllExceptThisFile,
                        kf
                        );
                }

                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu(txtDeleteDuplicateIn),
                    ACTION_COMMAND_DeleteDuplicateInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu(txtKeepNonDuplicateIn),
                    ACTION_COMMAND_KeepNonDuplicateInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu(txtKeepAllInDir),
                    ACTION_COMMAND_KeepAllInDir,
                    kf
                    );
                addContextSubMenuActionCommandRec(
                    this,
                    cm,
                    new JMenu(txtDeleteAllInDir),
                    ACTION_COMMAND_DeleteAllInDir,
                    kf
                    );

                return cm;
            }
        };
        m.setMenu();
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
                        // Delete all files in this dir, but keep one (globally)
                        final String dirPath = kf.getFile().getPath();

                        //Look for all files in this dir !
                        for(Entry<String, Set<KeyFileState>> entry:duplicateFiles.entrySet()) {
                            //String              k = entry.getKey();
                            Set<KeyFileState>   s = entry.getValue();
                            int                 c = 0;

                            for(KeyFileState f:s) {
                                if( !f.isSelectedToDelete() ) {
                                   c++;
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
                        }

                        // Update display for current file
                        updateDisplayKeptDelete( kf.getKey() );
                    }
                    else if( ACTION_COMMAND_KeepNonDuplicateInDir.equals( cmd ) ) {
                        // Keep at least on file in this dir.
                        ////final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath();
                      //TODO
                      //TODO
                      //TODO
                      //TODO
                      //TODO
                        //Look for all files in this dir !
                        for(Entry<String, Set<KeyFileState>> entry:duplicateFiles.entrySet()) {
                            int               c = 0;
                            Set<KeyFileState> s = entry.getValue();

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
                        }
                       
                        // Update display for current file
                        updateDisplayKeptDelete( kf.getKey() );
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

    private void jToggleButtonSelectByRegExChangeStateChanged(ChangeEvent e) 
    {
        updateDisplay();
    }

    private Pattern getCurrentPattern()
    {
        try {
            return xComboBoxPatternRegEx.getSelectedPattern();
        }
        catch( java.util.regex.PatternSyntaxException e ) {
            // TODO display alert !
            JOptionPane.showMessageDialog(
                    this,
                    e.getLocalizedMessage(),
                    txtPatternSyntaxExceptionTitle ,
                    JOptionPane.ERROR_MESSAGE
                    );
            return null;
        }
    }
    
    private void jButtonRegExDeleteMouseMousePressed(MouseEvent event)
    {
        Pattern p = getCurrentPattern();
        if( p == null ) {
            return;
        }
        boolean keepOne = jCheckBoxKeepOne.isSelected();
        
        for(KeyFileState f:duplicateFiles) {
            if( !f.isSelectedToDelete() ) {
                slogger.info( p.matcher( f.getFile().getPath() ).matches() + "=" + f.getFile().getPath() );
                if( p.matcher( f.getFile().getPath() ).matches() ) {
                    if( keepOne ) {
                        String            k = f.getKey();
                        Set<KeyFileState> s = duplicateFiles.get( k );
                        int               c = 0;

                        for(KeyFileState fc:s) {
                            if( !fc.isSelectedToDelete() ) {
                                c++;
                            }
                        }
                        slogger.info( "count=" + c );
                        if( c > 1 ) {
                            f.setSelectedToDelete( true );
                        }
                    }
                    else {
                        f.setSelectedToDelete( true );
                    }
                }
            }
        }
        updateDisplay();
    }

    private void jButtonRegExKeepMouseMousePressed(MouseEvent event) 
    {
        Pattern p = getCurrentPattern();
        if( p == null ) {
            return;
        }
        
        for(KeyFileState f:duplicateFiles) {
            if( f.isSelectedToDelete() ) {
                if( p.matcher( f.getFile().getPath() ).matches() ) {
                    f.setSelectedToDelete( false );
                }
            }
        }
        updateDisplay();
    }
}
