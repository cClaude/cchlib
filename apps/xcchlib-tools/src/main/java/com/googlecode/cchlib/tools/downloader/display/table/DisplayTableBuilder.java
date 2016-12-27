package com.googlecode.cchlib.tools.downloader.display.table;

import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;

public class DisplayTableBuilder
{
    // Non static
    private final class TablePopupMenu extends JPopupMenuForJTable {
        private static final long serialVersionUID = 1L;

        private TablePopupMenu( final JTable jTable )
        {
            super( jTable );
        }

        @Override
        protected JPopupMenu createContextMenu(
                final int rowIndex,
                final int columnIndex
                )
        {
            final JPopupMenu contextMenu = new JPopupMenu();

            final int                    rowModelIndex = super.convertRowIndexToModel( rowIndex );
            final DisplayTableModelEntry row           = DisplayTableBuilder.this.displayTableModel.getRow( rowModelIndex );
            final boolean isValidFile                  = (row.getFile() != null) && row.getFile().exists();

            if( columnIndex == 0 ) {
                addCopyMenuItem( contextMenu, DisplayTableBuilder.this.txtMenuCopy, rowIndex, columnIndex );
                }
            else if( (columnIndex == 2) && isValidFile ) {
                addCopyMenuItem( contextMenu, DisplayTableBuilder.this.txtMenuCopy, rowIndex, columnIndex );
                }

            addJMenuItem(
                contextMenu,
                DisplayTableBuilder.this.txtMenuOpenSourceUrl,
                DisplayTableBuilder.this.listener,
                ACTION_DO_OPEN_SOURCE_URL,      // actionCommand,
                CLIENT_PROPERTY_KEY,            // clientPropertyKey
                row                             // clientPropertyValue
                );

            if( row.getParentURL() != null ) {
                addJMenuItem(
                    contextMenu,
                    DisplayTableBuilder.this.txtMenuOpenParentUrl,
                    DisplayTableBuilder.this.listener,
                    ACTION_DO_OPEN_PARENT_URL,      // actionCommand,
                    CLIENT_PROPERTY_KEY,            // clientPropertyKey
                    row                             // clientPropertyValue
                    );
                }

            if( isValidFile ) {
                addJMenuItem(
                    contextMenu,
                    DisplayTableBuilder.this.txtMenuOpenLocalFile,
                    DisplayTableBuilder.this.listener,
                    ACTION_DO_OPEN_LOCAL_FILE,      // actionCommand,
                    CLIENT_PROPERTY_KEY,            // clientPropertyKey
                    row                             // clientPropertyValue
                    );
                }

            return contextMenu;
        }
    }

    private static final Logger LOGGER = Logger.getLogger( DisplayTableBuilder.class );

    private static final Object CLIENT_PROPERTY_KEY = DisplayTableModelEntry.class;
    private static final String ACTION_DO_OPEN_SOURCE_URL = "ACTION_DO_OPEN_SOURCE_URL";
    private static final String ACTION_DO_OPEN_LOCAL_FILE = "ACTION_DO_OPEN_LOCAL_FILE";
    private static final String ACTION_DO_OPEN_PARENT_URL = "ACTION_DO_OPEN_PARENT_URL";

    @I18nString private String txtMenuCopy;
    @I18nString private String txtMenuOpenSourceUrl;
    @I18nString private String txtMenuOpenParentUrl;
    @I18nString private String txtMenuOpenLocalFile;
    @I18nString private String txtOpenDesktopExceptionTitle;
    @I18nString private String txtBrowseDesktopExceptionTitle;

    private final DisplayTableModel displayTableModel;
    private final Window            mainWindow;
    private final ActionListener    listener;
    private Desktop                 desktop;
    private JTable                  jTable;

    public DisplayTableBuilder(
        final Window            mainWindow,
        final DisplayTableModel displayTableModel
        )
    {
        this.mainWindow        = mainWindow;
        this.displayTableModel = displayTableModel;
        this.listener          = this::actionPerformedActionListener;

        ensureI18nNonFinalStatic();
    }

    private void ensureI18nNonFinalStatic()
    {
        this.txtMenuCopy                    = "Copy cell content";
        this.txtMenuOpenSourceUrl           = "Open media from URL";
        this.txtMenuOpenParentUrl           = "Open parent URL";
        this.txtMenuOpenLocalFile           = "Open local file";
        this.txtOpenDesktopExceptionTitle   = "Error while open file";
        this.txtBrowseDesktopExceptionTitle = "Error while brosing";
    }

    private void actionPerformedActionListener( final ActionEvent event )
    {
        final String cmd = event.getActionCommand();

        if( ACTION_DO_OPEN_SOURCE_URL.equals( cmd ) ) {
            final DisplayTableModelEntry value = getDisplayTableModelEntry( event );
            browseDesktop( value.getURL() );
            }
        else if( ACTION_DO_OPEN_LOCAL_FILE.equals( cmd ) ) {
            final DisplayTableModelEntry value = getDisplayTableModelEntry( event );
            openDesktop( value.getFile() );
            }
        else if( ACTION_DO_OPEN_PARENT_URL.equals( cmd ) ) {
            final DisplayTableModelEntry value = getDisplayTableModelEntry( event );
            browseDesktop( value.getParentURL() );
            }

        else {
            LOGGER.error( "Action command not handle: " + cmd );
            }
   }

    public JTable getJTable()
    {
        if( this.jTable == null ) {
            this.jTable = new JTable( this.displayTableModel );
            this.displayTableModel.setJTable( this.jTable );

            final JPopupMenuForJTable popupMenu = new TablePopupMenu( this.jTable );

            popupMenu.addMenu();
            }
        return this.jTable;
    }


    private static DisplayTableModelEntry getDisplayTableModelEntry(
        final ActionEvent event
        )
    {
        return DisplayTableModelEntry.class.cast(
            JMenuItem.class.cast( event.getSource() ).getClientProperty( CLIENT_PROPERTY_KEY )
            );
    }

    private void openDesktop( final File file )
    {
        if( this.desktop == null ) {
            this.desktop = java.awt.Desktop.getDesktop();
            }

        Threads.start( () -> doOpenDesktopAsync( file ) );
    }

    private void doOpenDesktopAsync( final File file )
    {
        try {
            LOGGER.info( "trying to open: " + file );
            this.desktop.open( file );
            }
        catch( final Exception e ) {
            DialogHelper.showMessageExceptionDialog(
                    getMainWindow(),
                    this.txtOpenDesktopExceptionTitle,
                    e
                    );
            }
    }

    private void browseDesktop( final URL url )
    {
        if( this.desktop == null ) {
            this.desktop = java.awt.Desktop.getDesktop();
            }

        Threads.start( () -> doBrowseDesktopAsync( url ) );
    }

    protected void doBrowseDesktopAsync( final URL url )
    {
        try {
            LOGGER.info( "trying to open: " + url );

            this.desktop.browse( url.toURI() );
            }
        catch( final Exception e ) {
            DialogHelper.showMessageExceptionDialog(
                    getMainWindow(),
                    this.txtBrowseDesktopExceptionTitle,
                    e
                    );
            }
   }

    private Window getMainWindow()
    {
        return this.mainWindow;
    }
}
