package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;

final class FilesContextualMenu extends JPopupMenuForJList<KeyFileState>
{
    @SuppressWarnings("squid:S00115")
    private enum MenuAction
    {
        ACTION_COMMAND_DeleteAllExceptTheseFiles(
                (t,actionObject) -> t.onDeleteAllExceptTheseFiles( actionObject )
            ),
        ACTION_COMMAND_DeleteAllInDir(
                (t,actionObject) -> t.onDeleteAllInDir( actionObject )
            ),
        ACTION_COMMAND_DeleteDuplicateInDir(
                (t,actionObject) -> t.onDeleteDuplicateInDir( actionObject )
            ),
        ACTION_COMMAND_DeleteTheseFiles(
                (t,actionObject) -> t.onDeleteTheseFiles( actionObject )
            ),
        ACTION_COMMAND_KeepAllExceptTheseFiles(
                (t,actionObject) -> t.onKeepAllExceptTheseFiles( actionObject )
            ),
        ACTION_COMMAND_KeepAllInDir(
                (t,actionObject) -> t.onKeepAllInDir( actionObject )
            ),
        ACTION_COMMAND_KeepNonDuplicateInDir(
                (t,actionObject) -> t.onKeepNonDuplicateInDir( actionObject )
            ),
        ACTION_COMMAND_KeepTheseFiles( (t,actionObject) -> t.onKeepTheseFiles( actionObject ) ),
        ;
        private BiConsumer<FilesContextualMenu,Object> action;

        private MenuAction( final BiConsumer<FilesContextualMenu,Object> action )
        {
            this.action = action;
        }

        public void doAction( final FilesContextualMenu filesContextualMenu, final Object actionObject  )
        {
            this.action.accept( filesContextualMenu, actionObject );
        }
    }
    private static final String ACTION_OBJECT                            = "KeyFile";

    private static final Logger LOGGER                                   = Logger.getLogger( FilesContextualMenu.class );
    private static final long   serialVersionUID                         = 1L;

    private transient ActionListener    actionListenerContextSubMenu;
    private final AppToolKit            dFToolKit;
    private final JPanelResult          jPanelResult;

    @I18nString
    private String  txtCopy;
    @I18nString
    private String  txtDeleteAllExceptThisFile;
    @I18nString
    private String  txtDeleteAllInDir;
    @I18nString
    private String  txtDeleteDuplicateIn;
    @I18nString
    private String  txtDeleteThisFile;
    @I18nString
    private String  txtKeepAllExceptThisFile;
    @I18nString
    private String  txtKeepAllInDir;
    @I18nString
    private String  txtKeepNonDuplicateIn;
    @I18nString
    private String  txtKeepThisFile;
    @I18nString
    private String  txtOpenFile;
    @I18nString
    private String  txtOpenParentDirectory;

    FilesContextualMenu(
        final JPanelResult          jPanelResult,
        final JList<KeyFileState>   jList,
        final Attributs             first,
        final Attributs[]           rest
        )
    {
        super( jList, first, rest );

        this.jPanelResult = jPanelResult;
        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

        beSurNonFinal();
    }

    private void addContextSubMenuActionCommand(
        final JPopupMenuForJList<KeyFileState> m,
        final JMenu                            parentMenu,
        final MenuAction                       menuAction,
        final KeyFileState                     kf
        )
    {
        m.addJMenuItem(
            parentMenu,
            new JMenuItem( kf.getPath() ),
            getActionListenerContextSubMenu(),
            menuAction.name(),
            ACTION_OBJECT,
            kf
            );
    }

    private void addContextSubMenuActionCommand(
            final JPopupMenuForJList<KeyFileState> m,
            final JPopupMenu                       parentMenu,
            final JMenuItem                        menu,
            final MenuAction                       menuAction,
            final Selected                         selected
            )
    {
        m.addJMenuItem(
                parentMenu,
                menu,
                getActionListenerContextSubMenu(),
                menuAction.name(),
                ACTION_OBJECT,
                selected
                );
    }

    private void addContextSubMenuActionCommandRec(
        final JPopupMenuForJList<KeyFileState> m,
        final JPopupMenu                       parentMenu,
        final JMenu                            menu,
        final MenuAction                       menuAction,
        final KeyFileState                     kf
        )
    {
        m.addJMenuItem( parentMenu, menu );

        final String key = kf.getKey();
        File filedir = kf.getParentFile();

        while( filedir != null ) {
            addContextSubMenuActionCommand( m, menu, menuAction, new KeyFileState( key, filedir ) );  // $codepro.audit.disable avoidInstantiationInLoops
            filedir = filedir.getParentFile();
        }
    }

    private void beSurNonFinal()
    {
        this.txtCopy                    = "Copy";
        this.txtOpenFile                = "Open (Handle by System)";
        this.txtOpenParentDirectory     = "Open parent directory (Handle by System)";
        this.txtDeleteThisFile          = "Delete this file";
        this.txtDeleteAllExceptThisFile = "Delete all except this file";
        this.txtKeepThisFile            = "Keep this file";
        this.txtKeepAllExceptThisFile   = "Keep all except this file";
        this.txtDeleteDuplicateIn       = "Delete duplicate in";
        this.txtKeepNonDuplicateIn      = "Keep nonduplicate in";
        this.txtKeepAllInDir            = "Keep all in dir";
        this.txtDeleteAllInDir          = "Delete all in dir";
    }

    @Override
    protected JPopupMenu createContextMenu( final int rowIndex )
    {
        final JPopupMenu    cm              = new JPopupMenu();
        final int[]         selectedIndices = getSelectedIndices();
        final Selected      selected        = new Selected( getListModel(), selectedIndices );
        final KeyFileState  kf;

        if( selectedIndices.length == 1 ) {
            // Only one file selected...
            kf = getListModel().getElementAt( selectedIndices[ 0 ] );

            addCopyMenuItem( cm, new JMenuItem( this.txtCopy ), rowIndex );

            addJMenuItem( cm, this.txtOpenFile, createOpenFileActionListener( kf.getCurrentFile() ) );
            addJMenuItem( cm, this.txtOpenParentDirectory, createOpenFileActionListener( kf.getParentFile() ) );
            cm.addSeparator();
        } else {
            kf = null;
        }

        final boolean inKeepList = getJList() == this.jPanelResult.getJListKeptIntact();

        if( inKeepList ) {
            // ONLY: jListKeptIntact
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtDeleteThisFile ), MenuAction.ACTION_COMMAND_DeleteTheseFiles, selected );
            // ONLY: jListKeptIntact
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtDeleteAllExceptThisFile ), MenuAction.ACTION_COMMAND_DeleteAllExceptTheseFiles, selected );
        } else {
            // ONLY: jListWillBeDeleted
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtKeepThisFile ), MenuAction.ACTION_COMMAND_KeepTheseFiles, selected );
            // ONLY: jListWillBeDeleted
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtKeepAllExceptThisFile ), MenuAction.ACTION_COMMAND_KeepAllExceptTheseFiles, selected );
        }

        if( kf != null ) {
            if( inKeepList ) {
                addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtDeleteDuplicateIn ), MenuAction.ACTION_COMMAND_DeleteDuplicateInDir, kf );
            } else {
                addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtKeepNonDuplicateIn ), MenuAction.ACTION_COMMAND_KeepNonDuplicateInDir, kf );
            }
            cm.addSeparator();
            addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtKeepAllInDir ), MenuAction.ACTION_COMMAND_KeepAllInDir, kf );
            addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtDeleteAllInDir ), MenuAction.ACTION_COMMAND_DeleteAllInDir, kf );
        }

        return cm;
    }

    private ActionListener createOpenFileActionListener( final File file )
    {
        return e -> this.dFToolKit.openDesktop( file );
    }

    private void doActionOnAllExceptTheseFiles( //
        final Set<KeyFileState>         modelSet, //
        final Iterable<KeyFileState>    selectedFiles, //
        final Consumer<KeyFileState>    actionTrue, //
        final Consumer<KeyFileState>    actionFalse //
        )
    {
        if( modelSet != null ) {

            modelSet.forEach( (Consumer<KeyFileState>)modelEntry -> {
                if( isSelected( selectedFiles, modelEntry ) ) {
                    actionTrue.accept( modelEntry );
                } else {
                    actionFalse.accept( modelEntry );
                }
            } );
        }
    }

    private boolean isSelected(
        final Iterable<KeyFileState> selectedFiles,
        final KeyFileState           modelEntry
        )
    {
        final File currentFile = modelEntry.getFile();

        for( final KeyFileState keyFileState : selectedFiles ) {
            if( currentFile.equals( keyFileState.getFile() ) ) {
                return true;
            }
        }

        return false;
    }

    private ActionListener getActionListenerContextSubMenu()
    {
        if( this.actionListenerContextSubMenu == null ) {
            this.actionListenerContextSubMenu = this::actionPerformedContextSubMenu;
        }

        return this.actionListenerContextSubMenu;
    }

    private void actionPerformedContextSubMenu( final ActionEvent event )
    {
        final JMenuItem sourceItem   = (JMenuItem)event.getSource();
        final Object    actionObject = sourceItem.getClientProperty( ACTION_OBJECT );
        final String    cmd          = sourceItem.getActionCommand();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "cmd:" + cmd + " - " + actionObject );
        }

        if( cmd != null ) {
            final MenuAction menuAction = MenuAction.valueOf( cmd );

            if( menuAction != null ) {
                menuAction.doAction( this, actionObject );
            } else {
                LOGGER.fatal( "Can not transform \"" + cmd + "\" to an " + MenuAction.class );
            }
        }
    }

    private void onDeleteAllExceptTheseFiles( final Object actionObject )
    {
        final Selected          selected = Selected.class.cast( actionObject );
        final String            key      = selected.getKey();
        final Set<KeyFileState> modelSet = this.jPanelResult.getListModelDuplicatesFiles().getStateSet( key );
        //
        final Collection<KeyFileState> selectedFiles = selected.getSelectedList();

        final Consumer<KeyFileState> actionTrue  = modelEntry -> modelEntry.setSelectedToDelete( false );
        final Consumer<KeyFileState> actionFalse = modelEntry -> modelEntry.setSelectedToDelete( true );

        doActionOnAllExceptTheseFiles( modelSet, selectedFiles, actionTrue, actionFalse );

        this.jPanelResult.updateDisplayKeptDelete( key );
    }

    private void onDeleteAllInDir( final Object actionObject )
    {
        final KeyFileState kf      = (KeyFileState)actionObject;
        final String       k       = kf.getKey();
        final String       dirPath = kf.getPath() + File.separator;

        for( final KeyFileState f : this.jPanelResult.getListModelDuplicatesFiles().getStateSet( k ) ) {
            if( f.isInDirectory( dirPath ) ) {
                f.setSelectedToDelete( true );
            }

            this.jPanelResult.updateDisplayKeptDelete( k );
        }
    }

    private void onDeleteDuplicateInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        // Delete all files in this dir, but keep one (globally)
        // TODO should keep first one OR last one according to current sort order !
        final String dirPath = kf.getPath() + File.separator;

        // Look for all files in this dir !
        // FIXME NEED CODE REVIEW !!!!
        for( final Entry<String, Set<KeyFileState>> entry : this.jPanelResult.getListModelDuplicatesFiles().getStateEntrySet() ) {
            onDeleteDuplicateInDir( dirPath, entry );
        }

        // Update display for current file
        this.jPanelResult.updateDisplayKeptDelete( kf.getKey() );
    }

    private void onDeleteDuplicateInDir(
        final String                               dirPath,
        final Map.Entry<String, Set<KeyFileState>> entry
        )
    {
        final Set<KeyFileState> s = entry.getValue();
        int c = 0;

        for( final KeyFileState f : s ) {
            if( !f.isSelectedToDelete() ) {
                c++;
            }
        }

        // Keep one file !
        final int maxDel = c - 1;
        c = 0;

        for( final KeyFileState f : s ) {
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

    private void onDeleteTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );

        for( final KeyFileState kf : selected ) {
            this.jPanelResult.onDeleteThisFile( kf, false );
        }

        this.jPanelResult.updateDisplayKeptDelete( selected.getKey() );
    }

    private void onKeepAllExceptTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );
        final String key = selected.getKey();
        //final Stream<KeyFileState> selectedFiles = selected.toKeyFileStateStream();
        final Collection<KeyFileState> selectedFiles = selected.getSelectedList();
        final Set<KeyFileState> modelSet = this.jPanelResult.getListModelDuplicatesFiles().getStateSet( key );

        final Consumer<KeyFileState> actionTrue = modelEntry -> {
            modelEntry.setSelectedToDelete( true );
        };
        final Consumer<KeyFileState> actionFalse = modelEntry -> {
            modelEntry.setSelectedToDelete( false );
        };

        doActionOnAllExceptTheseFiles( modelSet, selectedFiles, actionTrue, actionFalse );

        this.jPanelResult.updateDisplayKeptDelete( key );
    }

    private void onKeepAllInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        final String k = kf.getKey();
        final String dirPath = kf.getPath() + File.separator;

        for( final KeyFileState f : this.jPanelResult.getListModelDuplicatesFiles().getStateSet( k ) ) {
            if( f.isInDirectory( dirPath ) ) {
                f.setSelectedToDelete( false );
            }
        }
        this.jPanelResult.updateDisplayKeptDelete( k );
    }

    private void onKeepNonDuplicateInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        // Keep at least on file in this dir.
        // TODO should keep first one OR last one according to current sort order !
        final String dirPath = kf.getPath() + File.separator;

        // Look for all files in this dir !
        for( final Entry<String, Set<KeyFileState>> entry : this.jPanelResult.getListModelDuplicatesFiles().getStateEntrySet() ) {
            int c = 0;
            final Set<KeyFileState> s = entry.getValue();

            for( final KeyFileState f : s ) {
                if( !f.isSelectedToDelete() ) {
                    if( f.isInDirectory( dirPath ) ) {
                        c++;
                    }
                }
            }

            final Iterator<KeyFileState> iter = s.iterator();

            while( (c == 0) && iter.hasNext() ) {
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
        this.jPanelResult.updateDisplayKeptDelete( kf.getKey() );
    }

    private void onKeepTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );

        for( final KeyFileState kf : selected ) {
            this.jPanelResult.onKeepThisFile( kf, false );
        }

        this.jPanelResult.updateDisplayKeptDelete( selected.getKey() );
    }
}
