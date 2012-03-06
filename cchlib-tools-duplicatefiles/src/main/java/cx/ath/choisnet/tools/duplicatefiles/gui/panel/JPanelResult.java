package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.i18n.I18nString;
import cx.ath.choisnet.swing.list.JPopupMenuForJList;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.duplicatefiles.KeyFiles;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.result.JPanelResultWB;
import cx.ath.choisnet.tools.duplicatefiles.gui.panel.result.SortMode;
import cx.ath.choisnet.util.HashMapSet;

/**
 *
 */
public class JPanelResult extends JPanelResultWB
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelResult.class );

    // TODO: Must be restore by parent !
    private transient DFToolKit dFToolKit;

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

        createPopupMenus();
    }

    public void populate(
            final HashMapSet<String,KeyFileState> duplicateFiles
            )
    {
        //this.duplicateFiles = duplicateFiles;

        SortMode sortMode = SortMode.FILESIZE; // FIXME

        getListModelDuplicatesFiles().updateCache( duplicateFiles, sortMode  );
        updateDisplay();
    }

    public void clear()
    {
        //listModelKeptIntact.clear();
        //listModelWillBeDeleted.clear();
        getListModelDuplicatesFiles().clearKeepDelete();
        getListModelDuplicatesFiles().updateCache();
//        if( listModelDuplicatesFiles != null ) {
//            getListModelDuplicatesFiles().clearKeepDelete();
//            }
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
//        if( index >= 0 ) {
//            KeyFiles kf = listModelDuplicatesFiles.getElementAt( index );
//
//            updateDisplayKeptDelete( kf.getKey() );
//            }
    }

    @Override
    protected void jButtonPrevSetMouseMousePressed( MouseEvent event )
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
    protected void jButtonNextSetMouseMousePressed( MouseEvent event )
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

    @Override
    protected void displayFileInfo( KeyFileState kf )
    {
        if( kf == null ) {
            getJTextFieldFileInfo().setText( "" );
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

    private void updateDisplayKeptDelete( String key )
    {
        logger.info( "updateDisplayKeptDelete: " + key/*, new Exception()*/ );
//        listModelKeptIntact.clear();
//        listModelWillBeDeleted.clear();
        displayFileInfo( null );
        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( key );
        if( s != null ) {
            getListModelDuplicatesFiles().setKeepDelete( s );
            }
        else {
            logger.error( "updateDisplayKeptDelete() * Missing key:" + key );
            }

////        Set<KeyFileState>       s  = duplicateFiles.get( key );
//        SortedSet<KeyFileState> ss = new TreeSet<KeyFileState>();
//
//        if( s != null ) {
//            ss.addAll( s );
//
//            for( KeyFileState sf : ss ) {
//                if( sf.isSelectedToDelete() ) {
//                    listModelWillBeDeleted.addElement( sf );
//                    }
//                else {
//                    listModelKeptIntact.addElement( sf );
//                    }
//                }
//            }
//        else {
//            slogger.error( "updateDisplayKeptDelete() * Missing key:" + key );
//            }
//        ss.clear();
    }

    @Override
    protected void DeleteThisFile( KeyFileState kf )
    {
        if( kf != null ) {
            kf.setSelectedToDelete( true );
            updateDisplayKeptDelete( kf.getKey() );
            }
    }

    @Override
    protected void KeptThisFile( KeyFileState kf )
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
        createPopupMenus( getJListKeptIntact() );
        createPopupMenus( getJListWillBeDeleted() );
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

                if( jList == getJListKeptIntact() ) {
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

                    logger.info( "cmd:" + cmd + " - " + kf );

                    if( ACTION_COMMAND_DeleteThisFile.equals( cmd ) ) {
                        DeleteThisFile(kf);
                        }
                    else if( ACTION_COMMAND_KeepThisFile.equals( cmd ) ) {
                        KeptThisFile(kf);
                        }
                    else if( ACTION_COMMAND_DeleteAllExceptThisFile.equals( cmd ) ) {
                        final String k    = kf.getKey();
                        final File   file = kf.getFile();

                        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );
                        //Set<KeyFileState> s = duplicateFiles.get( k );

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

                        Set<KeyFileState> s = getListModelDuplicatesFiles().getStateSet( k );
//                        Set<KeyFileState> s = duplicateFiles.get( k );

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
                        for( Entry<String,Set<KeyFileState>> entry : getListModelDuplicatesFiles().getStateEntrySet() ) {
//                        for(Entry<String, Set<KeyFileState>> entry:duplicateFiles.entrySet()) {
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
                    else if( ACTION_COMMAND_KeepNonDuplicateInDir.equals( cmd ) ) {
                        // Keep at least on file in this dir.
                        ////final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath() + File.separator;

                        //TODO need to be studies

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
                    else if( ACTION_COMMAND_KeepAllInDir.equals( cmd )) {
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath() + File.separator;

                        for( KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
                            if( f.isInDirectory( dirPath ) ) {
                                f.setSelectedToDelete( false );
                                }
                            }
                        updateDisplayKeptDelete( k );
                        }
                    else if( ACTION_COMMAND_DeleteAllInDir.equals( cmd ) ) {
                        final String k       = kf.getKey();
                        final String dirPath = kf.getFile().getPath() + File.separator;

                        for( KeyFileState f : getListModelDuplicatesFiles().getStateSet( k ) ) {
                            if( f.isInDirectory( dirPath ) ) {
                                f.setSelectedToDelete( true );
                                }
                            updateDisplayKeptDelete( k );
                            }
                        }
                    else {
                        logger.error("Don't known how to handle: " + cmd);
                        }
                    }
            };
        }
        return actionListenerContextSubMenu;
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
//                        Set<KeyFileState> s = duplicateFiles.get( k );
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
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
}
