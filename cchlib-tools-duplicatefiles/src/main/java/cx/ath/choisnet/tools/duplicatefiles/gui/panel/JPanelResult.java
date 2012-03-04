package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

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
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
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
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.XComboBoxPattern;
import cx.ath.choisnet.swing.list.JPopupMenuForJList;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.duplicatefiles.KeyFiles;
import cx.ath.choisnet.util.HashMapSet;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 */
public class JPanelResult extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelResult.class );
    private JTextField jTextFieldFileInfo;
    private JToggleButton jToggleButtonSelectByRegEx;
    private XComboBoxPattern xComboBoxPatternRegEx;
    private JCheckBox jCheckBoxKeepOne;
    private JButton jButtonRegExDelete;
    private JButton jButtonRegExKeep;

    private JSplitPane jSplitPaneResultMain;
    private JSplitPane jSplitPaneResultRight;
    private JList<KeyFiles>     jListDuplicatesFiles;
    private JList<KeyFileState> jListKeptIntact;
    private JList<KeyFileState> jListWillBeDeleted;

    // TODO: Must be restore by parent !
    private transient DFToolKit dFToolKit;

    private HashMapSet<String,KeyFileState>     duplicateFiles;// = new HashMapSet<String,KeyFileState>();
    //private DefaultListModel<KeyFiles>          listModelDuplicatesFiles    = new DefaultListModel<KeyFiles>();
    private JPanelResultDefaultListModel        listModelDuplicatesFiles;//    = new DefaultListModel<KeyFiles>();
    private DefaultListModel<KeyFileState>      listModelKeptIntact         = new DefaultListModel<KeyFileState>();
    private DefaultListModel<KeyFileState>      listModelWillBeDeleted      = new DefaultListModel<KeyFileState>();

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

    public JPanelResult(
        final DFToolKit dFToolKit
        )
    {
        this.dFToolKit = dFToolKit;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{25, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        GridBagConstraints gbc_jButtonPrevSet = new GridBagConstraints();
        gbc_jButtonPrevSet.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonPrevSet.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonPrevSet.gridx = 0;
        gbc_jButtonPrevSet.gridy = 0;

        JButton jButtonPrevSet = new JButton( "<<" );
        jButtonPrevSet.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButtonPrevSetMouseMousePressed(event);
                }
            });
        add(jButtonPrevSet, gbc_jButtonPrevSet);
        GridBagConstraints gbc_jTextFieldFileInfo = new GridBagConstraints();
        gbc_jTextFieldFileInfo.gridwidth = 4;
        gbc_jTextFieldFileInfo.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldFileInfo.insets = new Insets(0, 0, 5, 5);
        gbc_jTextFieldFileInfo.gridx = 1;
        gbc_jTextFieldFileInfo.gridy = 0;

        jTextFieldFileInfo = new JTextField();
        jTextFieldFileInfo.setEditable( false );
        jTextFieldFileInfo.setHorizontalAlignment( JTextField.CENTER );
        add(jTextFieldFileInfo, gbc_jTextFieldFileInfo);
        GridBagConstraints gbc_jButtonNextSet = new GridBagConstraints();
        gbc_jButtonNextSet.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonNextSet.insets = new Insets(0, 0, 5, 0);
        gbc_jButtonNextSet.gridx = 5;
        gbc_jButtonNextSet.gridy = 0;

        JButton jButtonNextSet = new JButton( ">>" );
        jButtonNextSet.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButtonNextSetMouseMousePressed(event);
                }
            });
        add(jButtonNextSet, gbc_jButtonNextSet);
        GridBagConstraints gbc_jSplitPaneResultMain = new GridBagConstraints();
        gbc_jSplitPaneResultMain.gridwidth = 6;
        gbc_jSplitPaneResultMain.fill = GridBagConstraints.BOTH;
        gbc_jSplitPaneResultMain.insets = new Insets(0, 0, 5, 0);
        gbc_jSplitPaneResultMain.gridx = 0;
        gbc_jSplitPaneResultMain.gridy = 1;
        add(getJSplitPaneResultMain(), gbc_jSplitPaneResultMain);
        setSize(488, 240);
        GridBagConstraints gbc_jToggleButtonSelectByRegEx = new GridBagConstraints();
        gbc_jToggleButtonSelectByRegEx.fill = GridBagConstraints.HORIZONTAL;
        gbc_jToggleButtonSelectByRegEx.gridwidth = 2;
        gbc_jToggleButtonSelectByRegEx.insets = new Insets(0, 0, 0, 5);
        gbc_jToggleButtonSelectByRegEx.gridx = 0;
        gbc_jToggleButtonSelectByRegEx.gridy = 2;

        jToggleButtonSelectByRegEx = new JToggleButton( "Select by RegEx" );
        jToggleButtonSelectByRegEx.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                jToggleButtonSelectByRegExChangeStateChanged(event);
                }
            });
        add( jToggleButtonSelectByRegEx, gbc_jToggleButtonSelectByRegEx );
        GridBagConstraints gbc_xComboBoxPatternRegEx = new GridBagConstraints();
        gbc_xComboBoxPatternRegEx.fill = GridBagConstraints.HORIZONTAL;
        gbc_xComboBoxPatternRegEx.insets = new Insets(0, 0, 0, 5);
        gbc_xComboBoxPatternRegEx.gridx = 2;
        gbc_xComboBoxPatternRegEx.gridy = 2;

        xComboBoxPatternRegEx = new XComboBoxPattern();
        add( xComboBoxPatternRegEx, gbc_xComboBoxPatternRegEx );
        GridBagConstraints gbc_jCheckBoxKeepOne = new GridBagConstraints();
        gbc_jCheckBoxKeepOne.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxKeepOne.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxKeepOne.gridx = 3;
        gbc_jCheckBoxKeepOne.gridy = 2;

        jCheckBoxKeepOne = new JCheckBox( "Keep one" );
        jCheckBoxKeepOne.setSelected( true );
        add( jCheckBoxKeepOne, gbc_jCheckBoxKeepOne );
        GridBagConstraints gbc_jButtonRegExDelete = new GridBagConstraints();
        gbc_jButtonRegExDelete.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonRegExDelete.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonRegExDelete.gridx = 4;
        gbc_jButtonRegExDelete.gridy = 2;

        jButtonRegExDelete = new JButton( "Delete" );
        jButtonRegExDelete.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButtonRegExDeleteMouseMousePressed();
                }
            });
        add(jButtonRegExDelete, gbc_jButtonRegExDelete);
        GridBagConstraints gbc_jButtonRegExKeep = new GridBagConstraints();
        gbc_jButtonRegExKeep.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonRegExKeep.gridx = 5;
        gbc_jButtonRegExKeep.gridy = 2;

        jButtonRegExKeep = new JButton( "Keep" );
        jButtonRegExKeep.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                jButtonRegExKeepMouseMousePressed();
                }
            });
        add( jButtonRegExKeep, gbc_jButtonRegExKeep );
    }

    public void populate(
            final HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        this.duplicateFiles = duplicateFiles;

        listModelDuplicatesFiles = new JPanelResultDefaultListModel( duplicateFiles );
        jListDuplicatesFiles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jListDuplicatesFiles.setModel( listModelDuplicatesFiles );
        jListDuplicatesFiles.addListSelectionListener(
            new ListSelectionListener() {
                @Override
                public void valueChanged( ListSelectionEvent e )
                {
                    int i = jListDuplicatesFiles.getSelectedIndex();

                    if( i >= 0 ) {
                        //KeyFiles kf = (KeyFiles)listModelDuplicatesFiles.get( i );
                        KeyFiles kf = listModelDuplicatesFiles.getElementAt( i );
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
            new DefaultComboBoxModel<String>(new String[] { ".*\\.jpg", ".*\\.gif", ".*\\.tmp" })
            );

        updateDisplay();
    }

    public void clear()
    {
        //listModelDuplicatesFiles.clear();
        listModelKeptIntact.clear();
        listModelWillBeDeleted.clear();
    }

//    public void initDisplay()
//    {
//        clear();
//
//        for(Map.Entry<String,Set<KeyFileState>> e:duplicateFiles.entrySet()) {
//            String              k    = e.getKey();
//            Set<KeyFileState>   sf   = e.getValue();
//
//            listModelDuplicatesFiles.addElement(
//                    new KeyFiles( k, sf )
//                    );
//        }
//    }

    public void updateDisplay()
    {
        if( dFToolKit.getPreferences().getConfigMode() == ConfigMode.BEGINNER ) {
            jToggleButtonSelectByRegEx.setSelected( false );
            jToggleButtonSelectByRegEx.setEnabled( false );
            }
        else {
            jToggleButtonSelectByRegEx.setEnabled( true );
            }

        boolean useRegEx = jToggleButtonSelectByRegEx.isSelected();

        //jTextFieldRegEx.setVisible(useRegEx);
        xComboBoxPatternRegEx.setVisible( useRegEx );
        jCheckBoxKeepOne.setVisible( useRegEx );
        jButtonRegExDelete.setVisible( useRegEx );
        jButtonRegExKeep.setVisible( useRegEx );

        int index = jListDuplicatesFiles.getSelectedIndex();

        if( index >= 0 ) {
            //KeyFiles kf = (KeyFiles)listModelDuplicatesFiles.getElementAt( index );
            KeyFiles kf = listModelDuplicatesFiles.getElementAt( index );

            updateDisplayKeptDelete( kf.getKey() );
            }
    }

    private JSplitPane getJSplitPaneResultMain()
    {
        if (jSplitPaneResultMain == null) {
            jSplitPaneResultMain = new JSplitPane();
            jSplitPaneResultMain.setDividerLocation(100);

            JScrollPane jScrollPaneDuplicatesFiles = new JScrollPane();
            jScrollPaneDuplicatesFiles.setViewportView(getJListDuplicatesFiles());

            jSplitPaneResultMain.setLeftComponent( jScrollPaneDuplicatesFiles );
            jSplitPaneResultMain.setRightComponent(getJSplitPaneResultRight());
            }
        return jSplitPaneResultMain;
    }

    private JSplitPane getJSplitPaneResultRight()
    {
        if (jSplitPaneResultRight == null) {
            jSplitPaneResultRight = new JSplitPane();
            jSplitPaneResultRight.setDividerLocation(100);
            jSplitPaneResultRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
            JScrollPane jScrollPaneKeptIntact = new JScrollPane();
            jListKeptIntact = new JList<KeyFileState>();

            jScrollPaneKeptIntact.setViewportView( jListKeptIntact );
            jSplitPaneResultRight.setTopComponent( jScrollPaneKeptIntact );

            JScrollPane jScrollPaneWillBeDeleted = new JScrollPane();
            jListWillBeDeleted = new JList<KeyFileState>();
            jScrollPaneWillBeDeleted.setViewportView( jListWillBeDeleted );

            jSplitPaneResultRight.setBottomComponent( jScrollPaneWillBeDeleted );
        }
        return jSplitPaneResultRight;
    }

    private JList<KeyFiles> getJListDuplicatesFiles()
    {
        if (jListDuplicatesFiles == null) {
            jListDuplicatesFiles = new JList<KeyFiles>();
            jListDuplicatesFiles.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if( e.getClickCount() == 2 ) {
                        // TODO: move current file to other list !
                        }
                    }
                });
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
            JPopupMenuForJList<KeyFileState>    m,
            JPopupMenu                          parentMenu,
            JMenuItem                           menu,
            String                              actionCommand,
            KeyFileState                        kf
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
            JPopupMenuForJList<KeyFileState>    m,
            JMenu                               parentMenu,
            String                              actionCommand,
            KeyFileState                        kf
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
            JPopupMenuForJList<KeyFileState>      m,
            JPopupMenu                          parentMenu,
            JMenu                               menu,
            String                              actionCommand,
            KeyFileState                        kf
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
            File    f       = kf.getFile();
            Locale  locale  = dFToolKit.getValidLocale();

            String date = DateFormat.getDateTimeInstance(
                    DateFormat.FULL,
                    DateFormat.SHORT,
                    locale
                    ).format(
                        new Date(f.lastModified())
                        );

            jTextFieldFileInfo.setText(
                String.format(
                        "%s - %d [%s%s%s] %s (%s)",
                        f.getName(),
                        f.length(),
                        //f.canExecute()?txtCanExecuteFirstLetter:"-",
                        f.canRead()?txtCanReadFirstLetter:"-",
                        f.canWrite()?txtCanWriteFirstLetter:"-",
                        f.isHidden()?txtHiddenFirstLetter:"-",
                        date,
                        kf.getKey()
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
            slogger.error( "updateDisplayKeptDelete() * Missing key:" + key );
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

    private void createPopupMenus( final JList<KeyFileState> jList )
    {
        final JPopupMenuForJList<KeyFileState> m = new JPopupMenuForJList<KeyFileState>( jList )
        {
            @Override
            protected JPopupMenu createContextMenu( final int rowIndex )
            {
                //JPopupMenu cm = super.createContextMenu( rowIndex );
                JPopupMenu cm = new JPopupMenu();

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
                        final String dirPath = kf.getFile().getPath() + File.separator;

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
                        final String dirPath = kf.getFile().getPath() + File.separator;

                        //TODO need to be studies

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
                        final String dirPath = kf.getFile().getPath() + File.separator;

                        for(KeyFileState f:duplicateFiles.get(k)) {
                            if( f.isInDirectory( dirPath ) ) {
                                f.setSelectedToDelete( false );
                                }
                            }
                        updateDisplayKeptDelete( k );
                        }
                    else if( ACTION_COMMAND_DeleteAllInDir.equals( cmd ) ) {
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath() + File.separator;

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

    private void jButtonRegExDeleteMouseMousePressed()
    {
        Pattern p = getCurrentPattern();
        if( p == null ) {
            return;
            }
        boolean keepOne = jCheckBoxKeepOne.isSelected();

        for( KeyFileState f:duplicateFiles ) {
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

    private void jButtonRegExKeepMouseMousePressed()
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
