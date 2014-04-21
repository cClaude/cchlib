// $codepro.audit.disable largeNumberOfFields, largeNumberOfMethods, constantNamingConvention
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.util.iterator.Iterators;
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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

@I18nName("duplicatefiles.JPanelResult")
public final class JPanelResult extends JPanelResultWB implements I18nAutoCoreUpdatable
{
    private static class Selected implements Iterable<KeyFileState>
    {
        private final List<KeyFileState> selectedList;

        Selected(
            final ListModel<KeyFileState> listModel,
            final int[]                   selectedIndices
            )
        {
            if( selectedIndices == null ) {
                throw new IllegalArgumentException( "selectedIndices is null - Illegal value" );
                }
            if( selectedIndices.length < 0 ) {
                throw new IllegalArgumentException( "Illegal value for selectedIndices: " + selectedIndices.length );
                }

            this.selectedList = new ArrayList<>( selectedIndices.length );

            for( final int index : selectedIndices ) {
                this.selectedList.add( listModel.getElementAt( index ) );
                }
        }

        @Override
        public Iterator<KeyFileState> iterator()
        {
            return Iterators.unmodifiableIterator( this.selectedList.iterator() );
        }

        public String getKey()
        {
            return this.selectedList.get( 0 ).getKey();
        }

//        public List<File> toFileList()
//        {
//
//            final List<File> list = new ArrayList<>();
//
//            for( final KeyFileState kf : this ) {
//                list.add( kf.getFile() );
//                }
//
//            return list;
//        }

        public List<File> toFileList()
        {
            return toFiles().collect( Collectors.toList() );
        }

        public Stream<File> toFiles()
        {
            return this.selectedList.stream().map( kf -> kf.getFile() );
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelResult.class );

    // TODO: Must be restore by parent !
    private transient AppToolKit dFToolKit;
    private final DuplicateSetListContextualMenu duplicateSetListContextualMenu;

    private ActionListener      actionListenerContextSubMenu;
    private static final String ACTION_OBJECT                            = "KeyFile";
    private static final String ACTION_COMMAND_DeleteDuplicateInDir      = "DeleteDuplicateInDir";
    private static final String ACTION_COMMAND_KeepNonDuplicateInDir     = "KeepNonDuplicateInDir";
    private static final String ACTION_COMMAND_KeepAllInDir              = "KeepAllInDir";
    private static final String ACTION_COMMAND_DeleteAllInDir            = "DeleteAllInDir";
    private static final String ACTION_COMMAND_DeleteTheseFiles          = "DeleteTheseFiles";
    private static final String ACTION_COMMAND_DeleteAllExceptTheseFiles = "DeleteAllExceptTheseFiles";
    private static final String ACTION_COMMAND_KeepTheseFiles            = "KeepTheseFiles";
    private static final String ACTION_COMMAND_KeepAllExceptTheseFiles   = "KeepAllExceptTheseFiles";

    @I18nString private final String txtCopy = "Copy";
    @I18nString private final String txtOpenFile = "Open (Handle by System)";
    @I18nString private final String txtOpenParentDirectory = "Open parent directory (Handle by System)";
    @I18nString private final String txtDeleteThisFile = "Delete this file";
    @I18nString private final String txtDeleteAllExceptThisFile  = "Delete all except this file";
    @I18nString private final String txtKeepThisFile = "Keep this file";
    @I18nString private final String txtKeepAllExceptThisFile = "Keep all except this file";
    @I18nString private final String txtDeleteDuplicateIn = "Delete duplicate in";
    @I18nString private final String txtKeepNonDuplicateIn = "Keep nonduplicate in";
    @I18nString private final String txtKeepAllInDir = "Keep all in dir";
    @I18nString private final String txtDeleteAllInDir = "Delete all in dir";
    @I18nString private final String txtHiddenFirstLetter = "H";
    //@I18nString private String txtCanExecuteFirstLetter = "E";
    @I18nString private final String txtCanWriteFirstLetter = "W";
    @I18nString private final String txtCanReadFirstLetter = "R";

    public JPanelResult()
    {
        super();

        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

        duplicateSetListContextualMenu = new DuplicateSetListContextualMenu( this );
        createPopupMenus();

        SwingUtilities.invokeLater( () -> setDividersLocation( dFToolKit.getPreferences().getJPaneResultDividerLocations() ) );
    }

     public void populate(
        final Map<String,Set<KeyFileState>> duplicateFiles
        )
    {
        final Preferences preferences = this.dFToolKit.getPreferences();

        final SortMode        sortMode        = preferences.getDefaultSortMode();
        final SelectFirstMode selectFirstMode = preferences.getDefaultSelectFirstMode();

        getListModelDuplicatesFiles().setDuplicateFiles( duplicateFiles );
        getListModelDuplicatesFiles().setSortMode( sortMode );
        getListModelDuplicatesFiles().setSelectFirstMode( selectFirstMode );
        getListModelDuplicatesFiles().updateCache();
        updateDisplay();
    }

    public void clear()
    {
        getListModelDuplicatesFiles().clearKeepDelete();
        getListModelDuplicatesFiles().updateCache();
    }

    @Override
    public void updateDisplay()
    {
        getSelectorsJPanel().updateDisplay();

        final int index = getJListDuplicatesFiles().getSelectedIndex();

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

    private void addContextSubMenuActionCommand(
        final JPopupMenuForJList<KeyFileState>    m,
        final JMenu                               parentMenu,
        final String                              actionCommand,
        final KeyFileState                        kf
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
        final JPopupMenuForJList<KeyFileState> m,
        final JPopupMenu                       parentMenu,
        final JMenuItem                        menu,
        final String                           actionCommand,
        final Selected                         selected
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
                    new KeyFileState( k, f ) // $codepro.audit.disable avoidInstantiationInLoops
                    );

            f = f.getParentFile();
        }
    }

    @Override
    protected void displayFileInfo( final KeyFileState kf )
    {
        if( kf == null ) {
            getJTextFieldFileInfo().setText( StringHelper.EMPTY );
            }
        else {
            final File    f       = kf.getFile();
            final Locale  locale  = dFToolKit.getValidLocale();

            final String date = DateFormat.getDateTimeInstance(
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
                        Long.valueOf( f.length() ),
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
    protected void updateDisplayKeptDelete( final int index )
    {
        LOGGER.info( "updateDisplayKeptDelete: index = " + index );

        if( index < 0 ) {
            return;
            }

        updateDisplayKeptDelete( getListModelDuplicatesFiles().getElementAt( index ).getKey() );
    }

    private void updateDisplayKeptDelete( final String key )
    {
        LOGGER.info( "updateDisplayKeptDelete: " + key );

        displayFileInfo( null );
        final Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( key );
        if( s != null ) {
            getListModelDuplicatesFiles().setKeepDelete( key, s );
            }
        else {
            LOGGER.error( "updateDisplayKeptDelete() * Missing key:" + key );
            }
    }

    @Override
    protected void onDeleteThisFile( final KeyFileState kf, final boolean updateDisplay )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( true );
            updateDisplayKeptDelete( kf.getKey() );
            }
    }

    @Override
    protected void onKeepThisFile( final KeyFileState kf, final boolean updateDisplay )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( false );
            updateDisplayKeptDelete( kf.getKey() );
            }
    }

    private ActionListener createOpenFileActionListener( final File file )
    {
        return e ->  dFToolKit.openDesktop( file );
    }

    private void createPopupMenus()
    {
        createKeyFileStatePopupMenu( getJListKeptIntact() );
        createKeyFileStatePopupMenu( getJListWillBeDeleted() );

        duplicateSetListContextualMenu.setPopupMenu();
   }

    private void createKeyFileStatePopupMenu( final JList<KeyFileState> jList_ )
    {
        final JPopupMenuForJList<KeyFileState> menu = new JPopupMenuForJList<KeyFileState>( jList_ )
        {
            private static final long serialVersionUID = 1L;
            @Override
            protected JPopupMenu createContextMenu( final int rowIndex )
            {
                final JPopupMenu cm = new JPopupMenu();

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

                if( getJList() == getJListKeptIntact() ) { // $codepro.audit.disable useEquals
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
        menu.addMenu();
    }


    private ActionListener getActionListenerContextSubMenu()
    {
        if( actionListenerContextSubMenu == null ) {
            this.actionListenerContextSubMenu = (final ActionEvent e) -> {
                final JMenuItem    sourceItem   = (JMenuItem) e.getSource();
                final Object       actionObject = sourceItem.getClientProperty(ACTION_OBJECT);
                final String       cmd        = sourceItem.getActionCommand();
                LOGGER.info( "cmd:" + cmd + " - " + actionObject );
                if (null != cmd) {
                    switch (cmd) {
                        case ACTION_COMMAND_DeleteTheseFiles:
                            onDeleteTheseFiles( actionObject );
                            break;
                        case ACTION_COMMAND_KeepTheseFiles:
                            onKeepTheseFiles( actionObject );
                            break;
                        case ACTION_COMMAND_DeleteAllExceptTheseFiles:
                            onDeleteAllExceptTheseFiles( actionObject );
                            break;
                        case ACTION_COMMAND_KeepAllExceptTheseFiles:
                            onKeepAllExceptTheseFiles( actionObject );
                            break;
                        case ACTION_COMMAND_DeleteDuplicateInDir:
                            onDeleteDuplicateInDir( actionObject );
                            break;
                        case ACTION_COMMAND_KeepNonDuplicateInDir:
                            onKeepNonDuplicateInDir( actionObject );
                            break;
                        case ACTION_COMMAND_KeepAllInDir:
                            onKeepAllInDir( actionObject );
                            break;
                        case ACTION_COMMAND_DeleteAllInDir:
                            onDeleteAllInDir( actionObject );
                            break;
                        default:
                            LOGGER.error("Don't known how to handle: " + cmd);
                            break;
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

        for( final KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
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

        for( final KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
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
        for( final Entry<String,Set<KeyFileState>> entry : getListModelDuplicatesFiles().getStateEntrySet() ) {
            int               c = 0;
            final Set<KeyFileState> s = entry.getValue();

            for(final KeyFileState f:s) {
                if( !f.isSelectedToDelete() ) {
                    if( f.isInDirectory( dirPath ) ) {
                       c++;
                        }
                    }
                }

            final Iterator<KeyFileState> iter = s.iterator();

            while( (c== 0) && iter.hasNext() ) {
                final KeyFileState f = iter.next();

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
        for( final Entry<String,Set<KeyFileState>> entry : getListModelDuplicatesFiles().getStateEntrySet() ) {
            final Set<KeyFileState>   s = entry.getValue();
            int                 c = 0;

            for( final KeyFileState f : s ) {
                if( !f.isSelectedToDelete() ) {
                   c++;
                    }
                }

            // Keep one file !
            final int maxDel = c - 1;
            c = 0;

            for(final KeyFileState f:s) {
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
        final Selected         selected      = Selected.class.cast( actionObject );
        final String           k             = selected.getKey();
        final Collection<File> selectedFiles = selected.toFileList();

        final Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );

        if( s != null ) {
            for(final KeyFileState f:s) {
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

        final Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );
        //Set<KeyFileState> s = duplicateFiles.get( k );

        if( s != null ) {
            for(final KeyFileState f:s) {
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

        for( final KeyFileState kf : selected ) {
            onKeepThisFile( kf, false );
            }

        updateDisplayKeptDelete( selected.getKey() );
    }

    private void onDeleteTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );

        for( final KeyFileState kf : selected ) {
            onDeleteThisFile( kf, false );
            }

        updateDisplayKeptDelete( selected.getKey() );
    }

    public void clearSelected()
    {
        disableAllWidgets();

        new Thread( ( ) -> {
            try {
                getListModelDuplicatesFiles().clearSelected();
            }
            catch( final Exception e ) {
                LOGGER.error( "clearSelected()", e );
            }
            finally {
                enableAllWidgets();

                dFToolKit.setEnabledJButtonCancel( true );
            }
        }, "clearSelected()" ).start();
    }

    private void disableAllWidgets()
    {
        // TODO : disable all widgets
    }

    private void enableAllWidgets()
    {
        // TODO : enable all widgets
    }

    @Override
    protected void onRefresh()
    {
        LOGGER.info( "onRefresh() - start" );

        disableAllWidgets();

        new Thread( ( ) -> {
            try {
                getListModelDuplicatesFiles().refreshList();
            }
            catch( final Exception e ) {
                LOGGER.error( "onRefresh()", e );
            }
            finally {
                enableAllWidgets();

                LOGGER.info( "onRefresh() - done" );
            }
        }, "onRefresh()" ).start();
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, getClass() );
        autoI18n.performeI18n( duplicateSetListContextualMenu, DuplicateSetListContextualMenu.class );
        duplicateSetListContextualMenu.setPopupMenu();

        super.getSelectorsJPanel().performeI18n( autoI18n );
    }

    public void lockGUI( final boolean lock )
    {
        if( lock ) {
            disableAllWidgets();
        } else {
            enableAllWidgets();
        }
    }
}
