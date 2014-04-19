package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.util.HashMapSet;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@I18nName("duplicatefiles.JPanelResult.DuplicateSetContextualMenu")
//NOT public
final class DuplicateSetListContextualMenu implements Serializable
{
    private static final long serialVersionUID = 1L;

    @I18nString private final String txtMenuIgnoreThisSetMen = "Ignore theses files";

    @I18nString private final String txtMenuSortList  = "Sort by";
    @I18nString private final String txtMenuSortBySize = "Size";
    @I18nString private final String txtMenuSortByName = "Filename";
    @I18nString private final String txtMenuSortByPath = "File path";
    @I18nString private final String txtMenuSortByDepth = "File depth";
    @I18nString private final String txtMenuSortFirstFile = "Select first file";
    @I18nString private final String txtMenuSortByNumberOfDuplicate = "Number of duplicate";
    @I18nString private final String txtMenuFirstFileRandom = "Quick";
    @I18nString private final String txtMenuFirstFileDepthAscendingOrder = "Depth Order Ascending";
    @I18nString private final String txtMenuFirstFileDepthDescendingOrder = "Depth Order Descending";

    private final JPanelResult jPanelResult;

    public DuplicateSetListContextualMenu( final JPanelResult jPanelResult )
    {
        this.jPanelResult = jPanelResult;
    }

    private class PopupMenu extends JPopupMenuForJList<KeyFiles>
    {
        private static final long serialVersionUID = 1L;

        private PopupMenu()
        {
            super( jPanelResult.getJListDuplicatesFiles() );
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
            final JMenuItem ignoreThisSetMenuItem = addJMenuItem( cm, txtMenuIgnoreThisSetMen );

//            ignoreThisSetMenuItem.addActionListener( new ActionListener()
//            {
//                @Override
//                public void actionPerformed( final ActionEvent event )
//                {
//                    Runnable task = new Runnable() {
//
//                        @Override
//                        public void run()
//                        {
//                            try {
//                                doIgnoreThisSetOfFiles( rowIndex );
//                            } finally {
//                                jPanelResult.lockGUI( false );
//                            }
//                        }
//
//                    };
//
//                    jPanelResult.lockGUI( true );
//                    new Thread( task, "doIgnoreThisSetOfFiles" ).start();
//                 }
//
//            } );
            ignoreThisSetMenuItem.addActionListener( event -> {

                jPanelResult.lockGUI( true );
                new Thread( ( ) -> {
                    try {
                        doIgnoreThisSetOfFiles( rowIndex );
                    }
                    finally {
                        jPanelResult.lockGUI( false );
                    }
                }, "doIgnoreThisSetOfFiles" ).start();
            } );
        }

        /**
         * @param cm
         */
        private void createSelectFirstMenu( final JPopupMenu cm )
        {
            final JMenu sortFirstFileMenu = addJMenu( cm, txtMenuSortFirstFile );

//            final ActionListener sortByListener = new ActionListener()
//            {
//                @Override
//                public void actionPerformed( final ActionEvent event )
//                {
//                    final JMenuItem menu = JMenuItem.class.cast( event.getSource() );
//                    final SelectFirstMode selectFirstMode = SelectFirstMode.class.cast(
//                            menu.getClientProperty( SelectFirstMode.class )
//                            );
//                    jPanelResult.getListModelDuplicatesFiles().updateCache( selectFirstMode );
//                }
//            };
            final ActionListener sortByListener = event -> {
                    final JMenuItem menu = JMenuItem.class.cast( event.getSource() );
                    final SelectFirstMode selectFirstMode = SelectFirstMode.class.cast(
                            menu.getClientProperty( SelectFirstMode.class )
                            );
                    jPanelResult.getListModelDuplicatesFiles().setSelectFirstMode( selectFirstMode );
                    jPanelResult.getListModelDuplicatesFiles().updateCache();
            };
            final SelectFirstMode sortMode = jPanelResult.getListModelDuplicatesFiles().getSelectFirstMode();
            final ButtonGroup gb           = new ButtonGroup();

            addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileRandom, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.QUICK, sortMode );
            addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileDepthAscendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_ASCENDING_ORDER, sortMode );
            addJCheckBoxMenuItem( sortFirstFileMenu, txtMenuFirstFileDepthDescendingOrder, gb, sortByListener, SelectFirstMode.class, SelectFirstMode.FILEDEPTH_DESCENDING_ORDER, sortMode );
        }

        private void createSortByMenu( final JPopupMenu cm )
        {
            final JMenu sortListMenu = addJMenu( cm, txtMenuSortList );

//            final ActionListener sortByListener = new ActionListener()
//            {
//                @Override
//                public void actionPerformed( final ActionEvent event )
//                {
//                    final JMenuItem menu = JMenuItem.class.cast( event.getSource() );
//                    final SortMode sortMode = SortMode.class.cast(
//                            menu.getClientProperty( SortMode.class )
//                            );
//                    jPanelResult.getListModelDuplicatesFiles().updateCache( sortMode );
//                }
//            };
            final ActionListener sortByListener = event -> {
                jPanelResult.getListModelDuplicatesFiles().setSortMode(
                        SortMode.class.cast( JMenuItem.class.cast( event.getSource() ).getClientProperty( SortMode.class ) ) );
                jPanelResult.getListModelDuplicatesFiles().updateCache();
            };

            final SortMode    sortMode = jPanelResult.getListModelDuplicatesFiles().getSortMode();
            final ButtonGroup gb       = new ButtonGroup();

            addJCheckBoxMenuItem( sortListMenu, txtMenuSortBySize , gb, sortByListener, SortMode.class, SortMode.FILESIZE, sortMode );
            addJCheckBoxMenuItem( sortListMenu, txtMenuSortByName , gb, sortByListener, SortMode.class, SortMode.FIRST_FILENAME, sortMode );
            addJCheckBoxMenuItem( sortListMenu, txtMenuSortByPath , gb, sortByListener, SortMode.class, SortMode.FIRST_FILEPATH, sortMode );
            addJCheckBoxMenuItem( sortListMenu, txtMenuSortByDepth, gb, sortByListener, SortMode.class, SortMode.FIRST_FILEDEPTH, sortMode );
            addJCheckBoxMenuItem( sortListMenu, txtMenuSortByNumberOfDuplicate, gb, sortByListener, SortMode.class, SortMode.NUMBER_OF_DUPLICATE, sortMode );
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
    }

    private void doIgnoreThisSetOfFiles( final int rowIndex )
    {
        final KeyFiles element = jPanelResult.getJListDuplicatesFiles().getModel().getElementAt( rowIndex );
        final String   setKey  = element.getKey();
        final HashMapSet<String, KeyFileState> hashMap = jPanelResult.getListModelDuplicatesFiles().getDuplicateFiles();

        hashMap.remove( setKey );

        jPanelResult.getListModelDuplicatesFiles().updateCache();
    }

    public void setPopupMenu()
    {
        new PopupMenu().addMenu();
    }
}
