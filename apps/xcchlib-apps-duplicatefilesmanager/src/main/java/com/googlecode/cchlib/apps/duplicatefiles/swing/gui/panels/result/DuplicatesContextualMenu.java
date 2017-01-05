package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFiles;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;

@I18nName("duplicatefiles.JPanelResult.DuplicateSetContextualMenu")
//NOT public
final class DuplicatesContextualMenu implements Serializable
{
    private static final long serialVersionUID = 1L;

    @I18nString private String txtMenuIgnoreThisSetMen = "Ignore theses files";

    @I18nString private String txtMenuSortList;
    @I18nString private String txtMenuSortBySize;
    @I18nString private String txtMenuSortByName;
    @I18nString private String txtMenuSortByPath;
    @I18nString private String txtMenuSortByDepth;
    @I18nString private String txtMenuSortFirstFile;
    @I18nString private String txtMenuSortByNumberOfDuplicate;
    @I18nString private String txtMenuFirstFileRandom;
    @I18nString private String txtMenuFirstFileDepthAscendingOrder;
    @I18nString private String txtMenuFirstFileDepthDescendingOrder;

    private final JPanelResult jPanelResult;

    public DuplicatesContextualMenu( final JPanelResult jPanelResult )
    {
        beSurNonFinal();

        this.jPanelResult = jPanelResult;
    }

    private void beSurNonFinal()
    {
        this.txtMenuIgnoreThisSetMen              = "Ignore theses files";
        this.txtMenuSortList                      = "Sort by";
        this.txtMenuSortBySize                    = "Size";
        this.txtMenuSortByName                    = "Filename";
        this.txtMenuSortByPath                    = "File path";
        this.txtMenuSortByDepth                   = "File depth";
        this.txtMenuSortFirstFile                 = "Select first file";
        this.txtMenuSortByNumberOfDuplicate       = "Number of duplicate";
        this.txtMenuFirstFileRandom               = "Quick";
        this.txtMenuFirstFileDepthAscendingOrder  = "Depth Order Ascending";
        this.txtMenuFirstFileDepthDescendingOrder = "Depth Order Descending";
    }

    private class PopupMenu extends JPopupMenuForJList<KeyFiles>
    {
        private static final long serialVersionUID = 1L;

        private PopupMenu()
        {
            super( DuplicatesContextualMenu.this.jPanelResult.getJListDuplicatesFiles(), Attributs.MUST_BE_SELECTED );
        }

        @Override
        protected JPopupMenu createContextMenu( final int rowIndex )
        {
            final JPopupMenu cm = new JPopupMenu();

            createSortByMenu( cm );
            createSelectFirstMenu( cm );
            createIgnoreThisSetMenu( cm, rowIndex );

            return cm;
        }

        private void createIgnoreThisSetMenu( final JPopupMenu cm, final int rowIndex )
        {
            final JMenuItem ignoreThisSetMenuItem = addJMenuItem( cm, DuplicatesContextualMenu.this.txtMenuIgnoreThisSetMen );

            ignoreThisSetMenuItem.addActionListener( event -> {
                DuplicatesContextualMenu.this.jPanelResult.lockGUI( true );

                new Thread( ( ) -> {
                    try {
                        doIgnoreThisSetOfFiles( rowIndex );
                    }
                    finally {
                        DuplicatesContextualMenu.this.jPanelResult.lockGUI( false );
                    }
                }, "doIgnoreThisSetOfFiles" ).start();
            } );
        }

        private void createSelectFirstMenu( final JPopupMenu cm )
        {
            final JMenu sortFirstFileMenu = addJMenu( cm, DuplicatesContextualMenu.this.txtMenuSortFirstFile );

            final ActionListener sortByListener = event -> { // ActionListener.actionPerformed( final ActionEvent event )
                    final JMenuItem       menu            = JMenuItem.class.cast( event.getSource() );
                    final SelectFirstMode selectFirstMode = SelectFirstMode.class.cast(
                            menu.getClientProperty( SelectFirstMode.class )
                            );
                    DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().setSelectFirstMode( selectFirstMode );
                    DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().updateCache();
            };
            final SelectFirstMode sortMode = DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().getSelectFirstMode();
            final ButtonGroup gb           = new ButtonGroup();

            addJCheckBoxMenuItem( sortFirstFileMenu, DuplicatesContextualMenu.this.txtMenuFirstFileRandom, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.QUICK, sortMode );
            addJCheckBoxMenuItem( sortFirstFileMenu, DuplicatesContextualMenu.this.txtMenuFirstFileDepthAscendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_ASCENDING_ORDER, sortMode );
            addJCheckBoxMenuItem( sortFirstFileMenu, DuplicatesContextualMenu.this.txtMenuFirstFileDepthDescendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_DESCENDING_ORDER, sortMode );
        }

        private void createSortByMenu( final JPopupMenu cm )
        {
            final JMenu sortListMenu = addJMenu( cm, DuplicatesContextualMenu.this.txtMenuSortList );
            final ActionListener sortByListener = event -> { // ActionListener.actionPerformed( final ActionEvent event )
                DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().setSortMode(
                        SortMode.class.cast( JMenuItem.class.cast( event.getSource() ).getClientProperty( SortMode.class ) )
                        );
                DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().updateCache();
            };

            final SortMode    sortMode = DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().getSortMode();
            final ButtonGroup gb       = new ButtonGroup();

            addJCheckBoxMenuItem( sortListMenu, DuplicatesContextualMenu.this.txtMenuSortBySize , gb, sortByListener, SortMode.class, SortMode.FILESIZE, sortMode );
            addJCheckBoxMenuItem( sortListMenu, DuplicatesContextualMenu.this.txtMenuSortByName , gb, sortByListener, SortMode.class, SortMode.FIRST_FILENAME, sortMode );
            addJCheckBoxMenuItem( sortListMenu, DuplicatesContextualMenu.this.txtMenuSortByPath , gb, sortByListener, SortMode.class, SortMode.FIRST_FILEPATH, sortMode );
            addJCheckBoxMenuItem( sortListMenu, DuplicatesContextualMenu.this.txtMenuSortByDepth, gb, sortByListener, SortMode.class, SortMode.FIRST_FILEDEPTH, sortMode );
            addJCheckBoxMenuItem( sortListMenu, DuplicatesContextualMenu.this.txtMenuSortByNumberOfDuplicate, gb, sortByListener, SortMode.class, SortMode.NUMBER_OF_DUPLICATE, sortMode );
        }

        private <E> void addJCheckBoxMenuItem( // $codepro.audit.disable largeNumberOfParameters
            final JMenu          sortMenu,
            final String         txt,
            final ButtonGroup    gb,
            final ActionListener listener,
            final Class<E>       clientPropertyKey,
            final E              clientPropertyValue,
            final E              currentValue
            )
        {
            final JCheckBoxMenuItem jcbmiMenuSortBySize = new JCheckBoxMenuItem( txt );
            jcbmiMenuSortBySize.setSelected( currentValue.equals( clientPropertyValue ) );
            gb.add( jcbmiMenuSortBySize );
            addJMenuItem( sortMenu, jcbmiMenuSortBySize, listener, clientPropertyKey, clientPropertyValue );
        }

        private void doIgnoreThisSetOfFiles( final int rowIndex )
        {
            final KeyFiles                       element = DuplicatesContextualMenu.this.jPanelResult.getJListDuplicatesFiles().getModel().getElementAt( rowIndex );
            final String                         setKey  = element.getKey();
            final Map<String, Set<KeyFileState>> hashMap = DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().getDuplicateFiles();

            hashMap.remove( setKey );

            DuplicatesContextualMenu.this.jPanelResult.getListModelDuplicatesFiles().updateCache();
        }
    }

    public void setPopupMenu()
    {
        new PopupMenu().addMenu();
    }
}
