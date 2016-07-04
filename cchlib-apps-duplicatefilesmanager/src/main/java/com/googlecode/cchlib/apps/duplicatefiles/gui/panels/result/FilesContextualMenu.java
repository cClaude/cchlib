package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;

final class FilesContextualMenu extends JPopupMenuForJList<KeyFileState> {
    private static final String ACTION_COMMAND_DeleteAllExceptTheseFiles = "DeleteAllExceptTheseFiles";
    private static final String ACTION_COMMAND_DeleteAllInDir            = "DeleteAllInDir";
    private static final String ACTION_COMMAND_DeleteDuplicateInDir      = "DeleteDuplicateInDir";
    private static final String ACTION_COMMAND_DeleteTheseFiles          = "DeleteTheseFiles";
    private static final String ACTION_COMMAND_KeepAllExceptTheseFiles   = "KeepAllExceptTheseFiles";
    private static final String ACTION_COMMAND_KeepAllInDir              = "KeepAllInDir";
    private static final String ACTION_COMMAND_KeepNonDuplicateInDir     = "KeepNonDuplicateInDir";
    private static final String ACTION_COMMAND_KeepTheseFiles            = "KeepTheseFiles";
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

    FilesContextualMenu( final JPanelResult jPanelResult, final JList<KeyFileState> jList, final Attributs first, final Attributs[] rest )
    {
        super( jList, first, rest );

        this.jPanelResult = jPanelResult;
        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

        beSurNonFinal();
    }

    private void addContextSubMenuActionCommand( final JPopupMenuForJList<KeyFileState> m, final JMenu parentMenu, final String actionCommand,
            final KeyFileState kf )
    {
        m.addJMenuItem( parentMenu, new JMenuItem( kf.getPath() ), getActionListenerContextSubMenu(), actionCommand, ACTION_OBJECT, kf );
    }

    private void addContextSubMenuActionCommand( final JPopupMenuForJList<KeyFileState> m, final JPopupMenu parentMenu, final JMenuItem menu,
            final String actionCommand, final Selected selected )
    {
        m.addJMenuItem( parentMenu, menu, getActionListenerContextSubMenu(), actionCommand, ACTION_OBJECT, selected );
    }

    private void addContextSubMenuActionCommandRec( final JPopupMenuForJList<KeyFileState> m, final JPopupMenu parentMenu, final JMenu menu,
            final String actionCommand, final KeyFileState kf )
    {
        m.addJMenuItem( parentMenu, menu );

        final String key = kf.getKey();
        File filedir = kf.getParentFile();

        while( filedir != null ) {
            addContextSubMenuActionCommand( m, menu, actionCommand, new KeyFileState( key, filedir ) );  // $codepro.audit.disable avoidInstantiationInLoops
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
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtDeleteThisFile ), ACTION_COMMAND_DeleteTheseFiles, selected );
            // ONLY: jListKeptIntact
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtDeleteAllExceptThisFile ), ACTION_COMMAND_DeleteAllExceptTheseFiles, selected );
        } else {
            // ONLY: jListWillBeDeleted
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtKeepThisFile ), ACTION_COMMAND_KeepTheseFiles, selected );
            // ONLY: jListWillBeDeleted
            addContextSubMenuActionCommand( this, cm, new JMenuItem( this.txtKeepAllExceptThisFile ), ACTION_COMMAND_KeepAllExceptTheseFiles, selected );
        }

        if( kf != null ) {
            if( inKeepList ) {
                addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtDeleteDuplicateIn ), ACTION_COMMAND_DeleteDuplicateInDir, kf );
            } else {
                addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtKeepNonDuplicateIn ), ACTION_COMMAND_KeepNonDuplicateInDir, kf );
            }
            cm.addSeparator();
            addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtKeepAllInDir ), ACTION_COMMAND_KeepAllInDir, kf );
            addContextSubMenuActionCommandRec( this, cm, new JMenu( this.txtDeleteAllInDir ), ACTION_COMMAND_DeleteAllInDir, kf );
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
                //if( selectedFiles.anyMatch( e -> e.getFile().equals( modelEntry.getFile() ) ) ) {

                if( isSelected( selectedFiles, modelEntry ) ) {
                    actionTrue.accept( modelEntry );
                } else {
                    actionFalse.accept( modelEntry );
                }
            } );
        }
    }

    private boolean isSelected( final Iterable<KeyFileState> selectedFiles, final KeyFileState modelEntry )
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
            this.actionListenerContextSubMenu = ( final ActionEvent e ) -> actionPerformedContextSubMenu( e );
        }

        return this.actionListenerContextSubMenu;
    }

    private void actionPerformedContextSubMenu( final ActionEvent event )
    {
        final JMenuItem sourceItem = (JMenuItem)event.getSource();
        final Object actionObject = sourceItem.getClientProperty( ACTION_OBJECT );
        final String cmd = sourceItem.getActionCommand();

        LOGGER.info( "cmd:" + cmd + " - " + actionObject );

        if( cmd != null ) {
            switch( cmd ) {
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
                    LOGGER.error( "Don't known how to handle: " + cmd );
                    break;
            }
        }
    }

    private void onDeleteAllExceptTheseFiles( final Object actionObject )
    {
        final Selected selected = Selected.class.cast( actionObject );
        final String key = selected.getKey();
        //final Stream<KeyFileState> selectedFiles = selected.toKeyFileStateStream();
        final Set<KeyFileState> modelSet = this.jPanelResult.getListModelDuplicatesFiles().getStateSet( key );
        //
        final Collection<KeyFileState> selectedFiles = selected.getSelectedList();

        final Consumer<KeyFileState> actionTrue = modelEntry -> {
            modelEntry.setSelectedToDelete( false );
        };
        final Consumer<KeyFileState> actionFalse = modelEntry -> {
            modelEntry.setSelectedToDelete( true );
        };

        doActionOnAllExceptTheseFiles( modelSet, selectedFiles, actionTrue, actionFalse );

        this.jPanelResult.updateDisplayKeptDelete( key );
    }

    private void onDeleteAllInDir( final Object actionObject )
    {
        final KeyFileState kf = (KeyFileState)actionObject;

        final String k = kf.getKey();
        final String dirPath = kf.getPath() + File.separator;

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

    private void onDeleteDuplicateInDir( final String dirPath, final Entry<String, Set<KeyFileState>> entry )
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
