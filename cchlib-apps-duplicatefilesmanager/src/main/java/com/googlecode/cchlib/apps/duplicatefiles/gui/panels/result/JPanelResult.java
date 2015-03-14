// $codepro.audit.disable largeNumberOfFields, largeNumberOfMethods, constantNamingConvention
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;
import com.googlecode.cchlib.util.iterator.Iterators;

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

        @Deprecated
        public List<File> _toFileList()
        {
            return toFiles().collect( Collectors.toList() );
        }

        @Deprecated
        public Stream<File> toFiles()
        {
            return this.selectedList.stream().map( kf -> kf.getFile() );
        }

        public Stream<KeyFileState> toKeyFileStateStream()
        {
            return this.selectedList.stream();
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelResult.class );

    private final AppToolKit dFToolKit;
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

    @I18nString private String txtCopy;
    @I18nString private String txtOpenFile;
    @I18nString private String txtOpenParentDirectory;
    @I18nString private String txtDeleteThisFile;
    @I18nString private String txtDeleteAllExceptThisFile;
    @I18nString private String txtKeepThisFile;
    @I18nString private String txtKeepAllExceptThisFile;
    @I18nString private String txtDeleteDuplicateIn;
    @I18nString private String txtKeepNonDuplicateIn;
    @I18nString private String txtKeepAllInDir;
    @I18nString private String txtDeleteAllInDir;
    @I18nString private String txtHiddenFirstLetter;
    //@I18nString private String txtCanExecuteFirstLetter = "E";
    @I18nString private String txtCanWriteFirstLetter;
    @I18nString private String txtCanReadFirstLetter;

    public JPanelResult()
    {
        super();

        beSurNonFinal();

        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

        this.duplicateSetListContextualMenu = new DuplicateSetListContextualMenu( this );
        createPopupMenus();

        SwingUtilities.invokeLater( () -> setDividersLocation( this.dFToolKit.getPreferences().getJPaneResultDividerLocations() ) );
    }

    private void beSurNonFinal()
    {
         this.txtCopy = "Copy";
         this.txtOpenFile = "Open (Handle by System)";
         this.txtOpenParentDirectory = "Open parent directory (Handle by System)";
         this.txtDeleteThisFile = "Delete this file";
         this.txtDeleteAllExceptThisFile  = "Delete all except this file";
         this.txtKeepThisFile = "Keep this file";
         this.txtKeepAllExceptThisFile = "Keep all except this file";
         this.txtDeleteDuplicateIn = "Delete duplicate in";
         this.txtKeepNonDuplicateIn = "Keep nonduplicate in";
         this.txtKeepAllInDir = "Keep all in dir";
         this.txtDeleteAllInDir = "Delete all in dir";
         this.txtHiddenFirstLetter = "H";
         //@I18nString private String txtCanExecuteFirstLetter = "E";
         this.txtCanWriteFirstLetter = "W";
         this.txtCanReadFirstLetter = "R";
    }

    public void populate(
        final Map<String,Set<KeyFileState>> duplicateFiles
        )
    {
        final PreferencesControler preferences = this.dFToolKit.getPreferences();

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
            new JMenuItem( kf.getPath() ),
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
        File         f = kf.getParentFile();

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
            final Locale  locale  = this.dFToolKit.getValidLocale();

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
                        f.canRead()?this.txtCanReadFirstLetter:"-",
                        f.canWrite()?this.txtCanWriteFirstLetter:"-",
                        f.isHidden()?this.txtHiddenFirstLetter:"-",
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
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "updateDisplayKeptDelete: " + key );
        }

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
        return e ->  this.dFToolKit.openDesktop( file );
    }

    private void createPopupMenus()
    {
        createKeyFileStatePopupMenu( getJListKeptIntact() );
        createKeyFileStatePopupMenu( getJListWillBeDeleted() );

        this.duplicateSetListContextualMenu.setPopupMenu();
   }

    private void createKeyFileStatePopupMenu( final JList<KeyFileState> jList_ )
    {
        final JPopupMenuForJList<KeyFileState> menu = new JPopupMenuForJList<KeyFileState>( jList_, AbstractJPopupMenuBuilder.Attributs.MUST_BE_SELECTED )
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

                    addCopyMenuItem( cm, new JMenuItem( JPanelResult.this.txtCopy ), rowIndex );

                    addJMenuItem(
                            cm,
                            JPanelResult.this.txtOpenFile,
                            createOpenFileActionListener( kf.getFile() )
                            );
                    addJMenuItem(
                            cm,
                            JPanelResult.this.txtOpenParentDirectory,
                            createOpenFileActionListener( kf.getParentFile() )
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
                        new JMenuItem(JPanelResult.this.txtDeleteThisFile),
                        ACTION_COMMAND_DeleteTheseFiles,
                        selected
                        );
                    // ONLY: jListKeptIntact
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(JPanelResult.this.txtDeleteAllExceptThisFile),
                        ACTION_COMMAND_DeleteAllExceptTheseFiles,
                        selected
                        );
                    }
                else {
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(JPanelResult.this.txtKeepThisFile ),
                        ACTION_COMMAND_KeepTheseFiles,
                        selected
                        );
                    // ONLY: jListWillBeDeleted
                    addContextSubMenuActionCommand(
                        this,
                        cm,
                        new JMenuItem(JPanelResult.this.txtKeepAllExceptThisFile),
                        ACTION_COMMAND_KeepAllExceptTheseFiles,
                        selected
                        );
                    }

                if( kf != null ) {
                    addContextSubMenuActionCommandRec(
                            this,
                            cm,
                            new JMenu(JPanelResult.this.txtDeleteDuplicateIn),
                            ACTION_COMMAND_DeleteDuplicateInDir,
                            kf
                            );
                    addContextSubMenuActionCommandRec(
                            this,
                            cm,
                            new JMenu(JPanelResult.this.txtKeepNonDuplicateIn),
                            ACTION_COMMAND_KeepNonDuplicateInDir,
                            kf
                            );
                    addContextSubMenuActionCommandRec(
                            this,
                            cm,
                            new JMenu(JPanelResult.this.txtKeepAllInDir),
                            ACTION_COMMAND_KeepAllInDir,
                            kf
                            );
                    addContextSubMenuActionCommandRec(
                            this,
                            cm,
                            new JMenu(JPanelResult.this.txtDeleteAllInDir),
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
        if( this.actionListenerContextSubMenu == null ) {
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
        return this.actionListenerContextSubMenu;
    }

    private void onDeleteAllInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        final String k       = kf.getKey();
        final String dirPath = kf.getPath() + File.separator;

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
        final String dirPath = kf.getPath() + File.separator;

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
       final String dirPath = kf.getPath() + File.separator;

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
        final String dirPath = kf.getPath() + File.separator;

        //Look for all files in this dir !
        // FIXME NEED CODE REVIEW !!!!
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
        final Selected             selected      = Selected.class.cast( actionObject );
        final String               key           = selected.getKey();
        final Stream<KeyFileState> selectedFiles = selected.toKeyFileStateStream();
        final Set<KeyFileState>    modelSet      = getListModelDuplicatesFiles().getStateSet(     key );
        //final Collection<File> selectedFiles = selected.toFileList();

//        if( modelSet != null ) {
//            for( final KeyFileState modelEntry : modelSet ) {
//                //if( selectedFiles.contains( modelEntry.getFile() ) ) {
//                if( selectedFiles.anyMatch( e -> e.getFile().equals( modelEntry.getFile() ) ) ) {
//                    modelEntry.setSelectedToDelete( true );
//                    }
//                else {
//                    modelEntry.setSelectedToDelete( false );
//                    }
//                }
//            }
        final Consumer<KeyFileState> actionTrue = modelEntry -> {
            modelEntry.setSelectedToDelete( true );
        };
        final Consumer<KeyFileState> actionFalse = modelEntry -> {
            modelEntry.setSelectedToDelete( false );
        };

        doActionOnAllExceptTheseFiles( modelSet, selectedFiles, actionTrue, actionFalse );

        updateDisplayKeptDelete( key );
    }

    private void onDeleteAllExceptTheseFiles( final Object actionObject )
    {
        final Selected             selected      = Selected.class.cast( actionObject );
        final String               key           = selected.getKey();
        final Stream<KeyFileState> selectedFiles = selected.toKeyFileStateStream();
        final Set<KeyFileState>    modelSet      = getListModelDuplicatesFiles().getStateSet( key );
        //final Collection<File> selectedFiles = selected.toFileList();

        final Consumer<KeyFileState> actionTrue = modelEntry -> {
            modelEntry.setSelectedToDelete( false );
        };
        final Consumer<KeyFileState> actionFalse = modelEntry -> {
            modelEntry.setSelectedToDelete( true );
        };

       doActionOnAllExceptTheseFiles( modelSet, selectedFiles, actionTrue, actionFalse );

       updateDisplayKeptDelete( key );
    }

    private void doActionOnAllExceptTheseFiles( //
        final Set<KeyFileState>      modelSet, //
        final Stream<KeyFileState>   selectedFiles, //
        final Consumer<KeyFileState> actionTrue,  //
        final Consumer<KeyFileState> actionFalse  //
        )
    {
        if( modelSet != null ) {
                modelSet.forEach( (Consumer<KeyFileState>)modelEntry -> {
                if( selectedFiles.anyMatch( e -> e.getFile().equals( modelEntry.getFile() ) ) ) {
                    actionTrue.accept( modelEntry );
                    }
                else {
                    actionFalse.accept( modelEntry );
                    }
            } );
//            for(final KeyFileState modelEntry : modelSet) {
//                if( selectedFiles.anyMatch( e -> e.getFile().equals( modelEntry.getFile() ) ) ) {
//                    modelEntry.setSelectedToDelete( false );
//                    }
//                else {
//                    modelEntry.setSelectedToDelete( true );
//                    }
//                }
                }
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

                this.dFToolKit.setEnabledJButtonCancel( true );
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
        autoI18n.performeI18n( this.duplicateSetListContextualMenu, DuplicateSetListContextualMenu.class );
        this.duplicateSetListContextualMenu.setPopupMenu();

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
