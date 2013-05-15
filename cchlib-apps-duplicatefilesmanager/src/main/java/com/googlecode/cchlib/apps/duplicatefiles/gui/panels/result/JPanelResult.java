package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.iterator.Iterators;

/**
 *
 */
public class JPanelResult extends JPanelResultWB
{
    private class Selected implements Iterable<KeyFileState>
    {
        //private ListModel<KeyFileState> listModel;
        //private int[] selectedIndices;
        private List<KeyFileState> selectedList;

        public Selected(
            final ListModel<KeyFileState> listModel,
            final int[]                   selectedIndices
            )
        {
            if( selectedIndices == null ) {
                throw new IllegalArgumentException( "selectedIndices is null - Illegal value" );
                }
            if( selectedIndices.length <= 0 ) {
                throw new IllegalArgumentException( "Illegal value for selectedIndices: " + selectedIndices );
                }

            //this.listModel       = listModel;
            //this.selectedIndices = selectedIndices;
            this.selectedList    = new ArrayList<>();

            for( int index : selectedIndices ) {
                this.selectedList.add( listModel.getElementAt( index ) );
                }
        }

        @Override
        public Iterator<KeyFileState> iterator()
        {
            return Iterators.unmodifiableIterator( this.selectedList.iterator() );
//            return new Iterator<KeyFileState>() {
//                private int index = 0;
//
//                @Override
//                public boolean hasNext()
//                {
//                    return index < selectedIndices.length;
//                }
//
//                @Override
//                public KeyFileState next()
//                {
//                    final int pos = selectedIndices[ index++ ];
//
//                    return listModel.getElementAt( pos );
//                }
//
//                @Override
//                public void remove()
//                {
//                    throw new UnsupportedOperationException();
//                }
//            };
        }

        public String getKey()
        {
            //return listModel.getElementAt( selectedIndices[ 0 ] ).getKey();
            return this.selectedList.get( 0 ).getKey();
        }

        public List<File> toFileList()
        {
            List<File> list = new ArrayList<>();

            for( KeyFileState kf : this ) {
                list.add( kf.getFile() );
                }

            return list;
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelResult.class );

    // TODO: Must be restore by parent !
    private transient DFToolKit dFToolKit;

    private ActionListener      actionListenerContextSubMenu;
    private final static String ACTION_OBJECT                            = "KeyFile";
    //private final static String ACTION_COMMAND_DeleteThisFile            = "DeleteThisFile";
    //private final static String ACTION_COMMAND_KeepThisFile              = "KeepThisFile";
    //private final static String ACTION_COMMAND_DeleteAllExceptThisFile   = "DeleteAllExceptThisFile";
    //private final static String ACTION_COMMAND_KeepAllExceptThisFile     = "KeepAllExceptThisFile";
    private final static String ACTION_COMMAND_DeleteDuplicateInDir      = "DeleteDuplicateInDir";
    private final static String ACTION_COMMAND_KeepNonDuplicateInDir     = "KeepNonDuplicateInDir";
    private final static String ACTION_COMMAND_KeepAllInDir              = "KeepAllInDir";
    private final static String ACTION_COMMAND_DeleteAllInDir            = "DeleteAllInDir";
    private static final String ACTION_COMMAND_DeleteTheseFiles          = "DeleteTheseFiles";
    private static final String ACTION_COMMAND_DeleteAllExceptTheseFiles = "DeleteAllExceptTheseFiles";
    private static final String ACTION_COMMAND_KeepTheseFiles            = "KeepTheseFiles";
    private static final String ACTION_COMMAND_KeepAllExceptTheseFiles   = "KeepAllExceptTheseFiles";

    @I18nString private String txtCopy = "Copy";
    @I18nString private String txtOpenFile = "Open (Handle by System)";
    @I18nString private String txtOpenParentDirectory = "Open parent directory (Handle by System)";
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

    @I18nString private String txtMenuSortList  = "Sort by";
    @I18nString private String txtMenuSortBySize = "Size";
    @I18nString private String txtMenuSortByName = "Filename";
    @I18nString private String txtMenuSortByPath = "File path";
    @I18nString private String txtMenuSortByDepth = "File depth";
    @I18nString private String txtMenuSortFirstFile = "Select first file";
    @I18nString private String txtMenuSortByNumberOfDuplicate = "Number of duplicate";
    @I18nString private String txtMenuFirstFileRandom = "Quick";
    @I18nString private String txtMenuFirstFileDepthAscendingOrder = "Depth Order Ascending";
    @I18nString private String txtMenuFirstFileDepthDescendingOrder = "Depth Order Descending";

    public JPanelResult(
        final DFToolKit dFToolKit
        )
    {
        super( dFToolKit.getResources() );

        this.dFToolKit = dFToolKit;

        createPopupMenus();
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                setDividersLocation( dFToolKit.getPreferences().getJPaneResultDividerLocations() ); 
            }
        });
    }

     public void populate(
            final HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        SortMode sortMode = SortMode.FILESIZE; // FIXME get default mode from prefs
        SelectFirstMode selectFirstMode = SelectFirstMode.QUICK; // FIXME get default mode from prefs

        getListModelDuplicatesFiles().updateCache( duplicateFiles, sortMode, selectFirstMode );
        updateDisplay();
    }

    public void clear()
    {
        getListModelDuplicatesFiles().clearKeepDelete();
        getListModelDuplicatesFiles().updateCache();
    }

    public void updateDisplay()
    {
        if( dFToolKit.getPreferences().getConfigMode() == ConfigMode.BEGINNER ) {
            getJToggleButtonSelectByRegEx().setSelected( false );
            getJToggleButtonSelectByRegEx().setEnabled( false );
            }
        else {
            getJToggleButtonSelectByRegEx().setEnabled( true );
            }

        boolean useRegEx = getJToggleButtonSelectByRegEx().isSelected();

        //jTextFieldRegEx.setVisible(useRegEx);
        getXComboBoxPatternRegEx().setVisible( useRegEx );
        getJCheckBoxKeepOne().setVisible( useRegEx );
        getJButtonRegExDelete().setVisible( useRegEx );
        getJButtonRegExKeep().setVisible( useRegEx );

        int index = getJListDuplicatesFiles().getSelectedIndex();

        updateDisplayKeptDelete( index );
    }

    @Override
    protected void onPrevSet()
    {
        final int size = getJListDuplicatesFiles().getModel().getSize();

        if( size > 0 ) {
            int i = getJListDuplicatesFiles().getSelectedIndex() - 1;

            if( i < 0 ) {
                i = size - 1;
                }
            getJListDuplicatesFiles().setSelectedIndex( i );
            }
    }

    @Override
    protected void onNextSet()
    {
        final int size = getJListDuplicatesFiles().getModel().getSize();

        if( size > 0 ) {
            int i = getJListDuplicatesFiles().getSelectedIndex() + 1;

            if( i >= size ) {
                i = 0;
                }
            getJListDuplicatesFiles().setSelectedIndex( i );
            }
    }

//    private void addContextSubMenuActionCommand(
//            JPopupMenuForJList<KeyFileState>    m,
//            JPopupMenu                          parentMenu,
//            JMenuItem                           menu,
//            String                              actionCommand,
//            KeyFileState                        kf
//            )
//    {
//        m.addJMenuItem(
//            parentMenu,
//            menu,
//            getActionListenerContextSubMenu(),
//            actionCommand,
//            ACTION_OBJECT,
//            kf
//            );
//    }

    private void addContextSubMenuActionCommand(
            JPopupMenuForJList<KeyFileState>    m,
            JMenu                               parentMenu,
            String                              actionCommand,
            KeyFileState                        kf
            )
    {
        m.addJMenuItem(
            parentMenu,
            new JMenuItem( kf.getFile().getPath() ),
            getActionListenerContextSubMenu(),
            actionCommand,
            ACTION_OBJECT,
            kf
            );
    }

    private void addContextSubMenuActionCommand(
            JPopupMenuForJList<KeyFileState> m,
            JPopupMenu                       parentMenu,
            JMenuItem                        menu,
            String                           actionCommand,
            Selected                         selected
            )
    {
        m.addJMenuItem(
                parentMenu,
                menu,
                getActionListenerContextSubMenu(),
                actionCommand,
                ACTION_OBJECT,
                selected
                );
    }

    private void addContextSubMenuActionCommandRec(
       final JPopupMenuForJList<KeyFileState>   m,
       final JPopupMenu                         parentMenu,
       final JMenu                              menu,
       final String                             actionCommand,
       final KeyFileState                       kf
       )
    {
        m.addJMenuItem( parentMenu, menu );

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

    @Override
    protected void displayFileInfo( KeyFileState kf )
    {
        if( kf == null ) {
            getJTextFieldFileInfo().setText( StringHelper.EMPTY );
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

            getJTextFieldFileInfo().setText(
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

    @Override
    protected void updateDisplayKeptDelete( int index )
    {
        logger.info( "updateDisplayKeptDelete: index = " + index );

        if( index < 0 ) {
            return;
            }

        final KeyFiles kf  = getListModelDuplicatesFiles().getElementAt( index );
        final String   key = kf.getKey();

        updateDisplayKeptDelete( key );
    }

    private void updateDisplayKeptDelete( final String key )
    {
        logger.info( "updateDisplayKeptDelete: " + key/*, new Exception()*/ );

        displayFileInfo( null );
        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( key );
        if( s != null ) {
            getListModelDuplicatesFiles().setKeepDelete( key, s );
            }
        else {
            logger.error( "updateDisplayKeptDelete() * Missing key:" + key );
            }
    }

    @Override
    protected void onDeleteThisFile( KeyFileState kf, boolean updateDisplay )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( true );
            updateDisplayKeptDelete( kf.getKey() );
            }
    }

    @Override
    protected void onKeepThisFile( KeyFileState kf, boolean updateDisplay )
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
        createKeyFileStatePopupMenu( getJListKeptIntact() );
        createKeyFileStatePopupMenu( getJListWillBeDeleted() );

        new JPopupMenuForJList<KeyFiles>( getJListDuplicatesFiles() )
            {
                private static final long serialVersionUID = 1L;
                @Override
                protected JPopupMenu createContextMenu( final int rowIndex )
                {
                    JPopupMenu cm = new JPopupMenu();

                    {
                        JMenu sortListMenu = addJMenu( cm, txtMenuSortList );

                        ActionListener sortByListener = new ActionListener()
                        {
                            @Override
                            public void actionPerformed( ActionEvent event )
                            {
                                JMenuItem menu = JMenuItem.class.cast( event.getSource() );
                                SortMode sortMode = SortMode.class.cast(
                                        menu.getClientProperty( SortMode.class )
                                        );
                                getListModelDuplicatesFiles().updateCache( sortMode );
                            }
                        };
                        SortMode    sortMode = getListModelDuplicatesFiles().getSortMode();
                        ButtonGroup gb       = new ButtonGroup();

                        addJCheckBoxMenuItem( sortListMenu, txtMenuSortBySize , gb, sortByListener, SortMode.class, SortMode.FILESIZE, sortMode );
                        addJCheckBoxMenuItem( sortListMenu, txtMenuSortByName , gb, sortByListener, SortMode.class, SortMode.FIRST_FILENAME, sortMode );
                        addJCheckBoxMenuItem( sortListMenu, txtMenuSortByPath , gb, sortByListener, SortMode.class, SortMode.FIRST_FILEPATH, sortMode );
                        addJCheckBoxMenuItem( sortListMenu, txtMenuSortByDepth, gb, sortByListener, SortMode.class, SortMode.FIRST_FILEDEPTH, sortMode );
                        addJCheckBoxMenuItem( sortListMenu, txtMenuSortByNumberOfDuplicate, gb, sortByListener, SortMode.class, SortMode.NUMBER_OF_DUPLICATE, sortMode );
                    }
                    {
                        JMenu sortFirstFileMenu = addJMenu( cm, txtMenuSortFirstFile );

                        ActionListener sortByListener = new ActionListener()
                        {
                            @Override
                            public void actionPerformed( ActionEvent event )
                            {
                                JMenuItem menu = JMenuItem.class.cast( event.getSource() );
                                SelectFirstMode selectFirstMode = SelectFirstMode.class.cast(
                                        menu.getClientProperty( SelectFirstMode.class )
                                        );
                                getListModelDuplicatesFiles().updateCache( selectFirstMode );
                            }
                        };
                        SelectFirstMode sortMode = getListModelDuplicatesFiles().getSelectFirstMode();
                        ButtonGroup gb           = new ButtonGroup();

                        addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileRandom, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.QUICK, sortMode );
                        addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileDepthAscendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_ASCENDING_ORDER, sortMode );
                        addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileDepthDescendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_DESCENDING_ORDER, sortMode );
                    }
                    return cm;
                }

                private <E> void addJCheckBoxMenuItem(
                    JMenu          sortMenu,
                    String         txt,
                    ButtonGroup    gb,
                    ActionListener listener,
                    Class<E>       clientPropertyKey,
                    E              clientPropertyValue,
                    E              currentValue
                    )
                {
                    JCheckBoxMenuItem jcbmiMenuSortBySize = new JCheckBoxMenuItem( txt );
                    jcbmiMenuSortBySize.setSelected( currentValue.equals( clientPropertyValue ) );
                    gb.add( jcbmiMenuSortBySize );
                    addJMenuItem( sortMenu, jcbmiMenuSortBySize, listener, clientPropertyKey, clientPropertyValue );
                }
            }.setMenu();
   }

    private void createKeyFileStatePopupMenu( final JList<KeyFileState> jList_ )
    {
        final JPopupMenuForJList<KeyFileState> m = new JPopupMenuForJList<KeyFileState>( jList_ )
        {
            private static final long serialVersionUID = 1L;
            @Override
            protected JPopupMenu createContextMenu( final int rowIndex )
            {
                JPopupMenu cm = new JPopupMenu();

                //KeyFileState kf = getValueAt( rowIndex );
                final int[]        selectedIndices = getSelectedIndices();
                final Selected     selected        = new Selected( getListModel(), selectedIndices );
                final KeyFileState kf;

                if( selectedIndices.length == 1 ) {
                    // Only one file selected...
                    kf = getListModel().getElementAt( selectedIndices[ 0 ] );

                    addCopyMenuItem( cm, new JMenuItem( txtCopy ), rowIndex );

                    addJMenuItem(
                            cm,
                            txtOpenFile,
                            createOpenFileActionListener( kf.getFile() )
                            );
                        addJMenuItem(
                                cm,
                                txtOpenParentDirectory,
                                createOpenFileActionListener( kf.getFile().getParentFile() )
                                );
                        cm.addSeparator();
                    }
                else {
                    kf = null;
                    }

                if( getJList() == getJListKeptIntact() ) {
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtDeleteThisFile),
                        ACTION_COMMAND_DeleteTheseFiles,
                        selected
                        );
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtDeleteAllExceptThisFile),
                        ACTION_COMMAND_DeleteAllExceptTheseFiles,
                        selected
                        );
                    }
                else {
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtKeepThisFile ),
                        ACTION_COMMAND_KeepTheseFiles,
                        selected
                        );
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(txtKeepAllExceptThisFile),
                        ACTION_COMMAND_KeepAllExceptTheseFiles,
                        selected
                        );
                    }

                if( kf != null ) {
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
                    }

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
                    final JMenuItem    sourceItem   = (JMenuItem) e.getSource();
                    final Object       actionObject = sourceItem.getClientProperty(ACTION_OBJECT);
                    //final KeyFileState kf         = (KeyFileState)sourceItem.getClientProperty(ACTION_OBJECT);
                    final String       cmd        = sourceItem.getActionCommand();

                    logger.info( "cmd:" + cmd + " - " + actionObject );

                    if( ACTION_COMMAND_DeleteTheseFiles.equals( cmd ) ) {
                        onDeleteTheseFiles( actionObject );
                        }
                    else if( ACTION_COMMAND_KeepTheseFiles.equals( cmd ) ) {
                        onKeepTheseFiles( actionObject );
                        }
                    else if( ACTION_COMMAND_DeleteAllExceptTheseFiles.equals( cmd ) ) {
                        onDeleteAllExceptTheseFiles( actionObject );
                        }
                    else if( ACTION_COMMAND_KeepAllExceptTheseFiles.equals( cmd ) ) {
                        onKeepAllExceptTheseFiles( actionObject );
                        }
                    else if( ACTION_COMMAND_DeleteDuplicateInDir.equals( cmd ) ) {
                        onDeleteDuplicateInDir( actionObject );
                        }
                    else if( ACTION_COMMAND_KeepNonDuplicateInDir.equals( cmd ) ) {
                        onKeepNonDuplicateInDir( actionObject );
                        }
                    else if( ACTION_COMMAND_KeepAllInDir.equals( cmd ) ) {
                        onKeepAllInDir( actionObject );
                        }
                    else if( ACTION_COMMAND_DeleteAllInDir.equals( cmd ) ) {
                        onDeleteAllInDir( actionObject );
                        }
                    else {
                        logger.error("Don't known how to handle: " + cmd);
                        }
                    }
            };
        }
        return actionListenerContextSubMenu;
    }

    private void onDeleteAllInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        final String k       = kf.getKey();
        final String dirPath = kf.getFile().getPath() + File.separator;

        for( KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
            if( f.isInDirectory( dirPath ) ) {
                f.setSelectedToDelete( true );
                }
            updateDisplayKeptDelete( k );
            }
    }

    private void onKeepAllInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        final String k       = kf.getKey();
        final String dirPath = kf.getFile().getPath() + File.separator;

        for( KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
            if( f.isInDirectory( dirPath ) ) {
                f.setSelectedToDelete( false );
                }
            }
        updateDisplayKeptDelete( k );
    }

    private void onKeepNonDuplicateInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        // Keep at least on file in this dir.
        // TODO should keep first one OR last one according to current sort order !
       final String dirPath = kf.getFile().getPath() + File.separator;

        //Look for all files in this dir !
        for( Entry<String,Set<KeyFileState>> entry : getListModelDuplicatesFiles().getStateEntrySet() ) {
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

    private void onDeleteDuplicateInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        // Delete all files in this dir, but keep one (globally)
        // TODO should keep first one OR last one according to current sort order !
        final String dirPath = kf.getFile().getPath() + File.separator;

        //Look for all files in this dir !
        for( Entry<String,Set<KeyFileState>> entry : getListModelDuplicatesFiles().getStateEntrySet() ) {
//        for(Entry<String, Set<KeyFileState>> entry:duplicateFiles.entrySet()) {
            //String              k = entry.getKey();
            Set<KeyFileState>   s = entry.getValue();
            int                 c = 0;

            for( KeyFileState f : s ) {
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

    private void onKeepAllExceptTheseFiles( final Object actionObject )
    {
        //final String k    = kf.getKey();
        //final File   file = kf.getFile();
        final Selected         selected      = Selected.class.cast( actionObject );
        final String           k             = selected.getKey();
        final Collection<File> selectedFiles = selected.toFileList();

        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );

        if( s != null ) {
            for(KeyFileState f:s) {
                //if( file.equals( f.getFile() ) ) {
                if( selectedFiles.contains( f.getFile() ) ) {
                    f.setSelectedToDelete( true );
                    }
                else {
                    f.setSelectedToDelete( false );
                    }
                }
            }
        updateDisplayKeptDelete( k );
    }

    private void onDeleteAllExceptTheseFiles( final Object actionObject )
    {
        //final String k    = kf.getKey();
        //final File   file = kf.getFile();
        final Selected         selected      = Selected.class.cast( actionObject );
        final String           k             = selected.getKey();
        final Collection<File> selectedFiles = selected.toFileList();

        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );
        //Set<KeyFileState> s = duplicateFiles.get( k );

        if( s != null ) {
            for(KeyFileState f:s) {
                //if( file.equals( f.getFile() ) ) {
                if( selectedFiles.contains( f.getFile() ) ) {
                    f.setSelectedToDelete( false );
                    }
                else {
                    f.setSelectedToDelete( true );
                    }
                }
            }
        updateDisplayKeptDelete( k );
    }

    private void onKeepTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );

        for( KeyFileState kf : selected ) {
            onKeepThisFile( kf, false );
            }

        updateDisplayKeptDelete( selected.getKey() );
    }

    private void onDeleteTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );

        for( KeyFileState kf : selected ) {
            onDeleteThisFile( kf, false );
            }

        updateDisplayKeptDelete( selected.getKey() );
    }

    @Override
    protected void jToggleButtonSelectByRegExChangeStateChanged(ChangeEvent e)
    {
        updateDisplay();
    }

    private Pattern getCurrentPattern()
    {
        try {
            return getXComboBoxPatternRegEx().getSelectedPattern();
            }
        catch( java.util.regex.PatternSyntaxException e ) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getLocalizedMessage(),
                    txtPatternSyntaxExceptionTitle ,
                    JOptionPane.ERROR_MESSAGE
                    );
            return null;
            }
    }

    @Override
    protected void jButtonRegExDeleteMouseMousePressed()
    {
        Pattern p = getCurrentPattern();
        if( p == null ) {
            return;
            }
        boolean keepOne = getJCheckBoxKeepOne().isSelected();

        for( KeyFileState f : getListModelDuplicatesFiles().iter() ) {
            if( !f.isSelectedToDelete() ) {
                logger.info( p.matcher( f.getFile().getPath() ).matches() + "=" + f.getFile().getPath() );
                if( p.matcher( f.getFile().getPath() ).matches() ) {
                    if( keepOne ) {
                        String            k = f.getKey();
                        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );
                        int               c = 0;

                        for(KeyFileState fc:s) {
                            if( !fc.isSelectedToDelete() ) {
                                c++;
                                }
                            }
                        logger.info( "count=" + c );
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

    @Override
    protected void jButtonRegExKeepMouseMousePressed()
    {
        Pattern p = getCurrentPattern();

        if( p == null ) {
            return;
            }

        for( KeyFileState f : getListModelDuplicatesFiles().iter() ) {
            if( f.isSelectedToDelete() ) {
                if( p.matcher( f.getFile().getPath() ).matches() ) {
                    f.setSelectedToDelete( false );
                    }
                }
            }
        updateDisplay();
    }

    public void clearSelected()
    {
        // TODO : disable all widgets
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    getListModelDuplicatesFiles().clearSelected();
                    }
                catch( Exception e ) {
                    logger.error( "clearSelected()", e );
                    }
                finally {
                    // TODO : enable all widgets
                    dFToolKit.setEnabledJButtonCancel( true );
                    }
            }
        }, "clearSelected()" ).start();
    }

    @Override
    protected void onRefresh()
    {
        logger.info( "onRefresh() - start" );

        // TODO : disable all widgets
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    getListModelDuplicatesFiles().refreshList();
                    }
                catch( Exception e ) {
                    logger.error( "onRefresh()", e );
                    }
                finally {
                    // TODO : enable all widgets
                    logger.info( "onRefresh() - done" );
                    }
            }
        }, "onRefresh()" ).start();
    }
}
