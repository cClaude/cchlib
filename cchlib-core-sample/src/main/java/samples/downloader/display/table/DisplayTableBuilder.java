package samples.downloader.display.table;

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
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;

/**
 *
 */
public class DisplayTableBuilder
{
    private static final Logger logger = Logger.getLogger( DisplayTableBuilder.class );
    private static final Object CLIENT_PROPERTY_KEY = DisplayTableModelEntry.class;
    private static final String ACTION_DO_OPEN_SOURCE_URL = "ACTION_DO_OPEN_SOURCE_URL";
    private static final String ACTION_DO_OPEN_LOCAL_FILE = "ACTION_DO_OPEN_LOCAL_FILE";
    private static final String ACTION_DO_OPEN_PARENT_URL = "ACTION_DO_OPEN_PARENT_URL";

    @I18nString private String txtMenuCopy                    = "Copy cell content";
    @I18nString private String txtMenuOpenSourceUrl           = "Open media from URL";
    @I18nString private String txtMenuOpenParentUrl           = "Open parent URL";
    @I18nString private String txtMenuOpenLocalFile           = "Open local file";
    @I18nString private String txtOpenDesktopExceptionTitle   = "Error while open file";
    @I18nString private String txtBrowseDesktopExceptionTitle = "Error while brosing";

    private DisplayTableModel displayTableModel;
    private ActionListener listener;
    private Desktop desktop;
    private JTable jTable;
    private Window mainWindow;

    public DisplayTableBuilder(
        final Window            mainWindow,
        final DisplayTableModel displayTableModel
        )
    {
        this.mainWindow        = mainWindow;
        this.displayTableModel = displayTableModel;

        this.listener = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent event )
            {
                final String cmd = event.getActionCommand();

                if( ACTION_DO_OPEN_SOURCE_URL.equals( cmd ) ) {
                    DisplayTableModelEntry value = getDisplayTableModelEntry( event );
                    browseDesktop( value.getURL() );
                    }
                else if( ACTION_DO_OPEN_LOCAL_FILE.equals( cmd ) ) {
                    DisplayTableModelEntry value = getDisplayTableModelEntry( event );
                    openDesktop( value.getFile() );
                    }
                else if( ACTION_DO_OPEN_PARENT_URL.equals( cmd ) ) {
                    DisplayTableModelEntry value = getDisplayTableModelEntry( event );
                    browseDesktop( value.getParentURL() );
                    }

                else {
                    logger.error( "Action command not handle: " + cmd );
                    }
            }
        };
    }

    public JTable getJTable()
    {
        if( jTable == null ) {
            jTable = new JTable( displayTableModel );
            displayTableModel.setJTable( jTable );

            JPopupMenuForJTable popupMenu = new JPopupMenuForJTable( jTable )
            {
                private static final long serialVersionUID = 1L;

                @Override
                protected JPopupMenu createContextMenu(
                        int rowIndex,
                        int columnIndex
                        )
                {
                    JPopupMenu contextMenu = new JPopupMenu();

                    final int                    rowModelIndex = super.convertRowIndexToModel( rowIndex );
                    final DisplayTableModelEntry row           = displayTableModel.getRow( rowModelIndex );
                    final boolean isValidFile                  = row.getFile() != null && row.getFile().exists();

                    if( columnIndex == 0 ) {
                        addCopyMenuItem( contextMenu, txtMenuCopy, rowIndex, columnIndex );
                        }
                    else if( columnIndex == 2 && isValidFile ) {
                        addCopyMenuItem( contextMenu, txtMenuCopy, rowIndex, columnIndex );
                        }

                    addJMenuItem(
                        contextMenu,
                        txtMenuOpenSourceUrl,
                        listener,
                        ACTION_DO_OPEN_SOURCE_URL,      // actionCommand,
                        CLIENT_PROPERTY_KEY,            // clientPropertyKey
                        row                             // clientPropertyValue
                        );

                    if( row.getParentURL() != null ) {
                        addJMenuItem(
                            contextMenu,
                            txtMenuOpenParentUrl,
                            listener,
                            ACTION_DO_OPEN_PARENT_URL,      // actionCommand,
                            CLIENT_PROPERTY_KEY,            // clientPropertyKey
                            row                             // clientPropertyValue
                            );
                        }

                    if( isValidFile ) {
                        addJMenuItem(
                            contextMenu,
                            txtMenuOpenLocalFile,
                            listener,
                            ACTION_DO_OPEN_LOCAL_FILE,      // actionCommand,
                            CLIENT_PROPERTY_KEY,            // clientPropertyKey
                            row                             // clientPropertyValue
                            );
                        }

                    return contextMenu;
                }
            };

            popupMenu.setMenu();
            }
        return jTable;
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
        if( desktop == null ) {
            desktop = java.awt.Desktop.getDesktop();
            }

        new Thread( new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        logger .info( "trying to open: " + file );
                        desktop.open( file );
                        }
                    catch( Exception e ) {
                        DialogHelper.showMessageExceptionDialog(
                                getMainWindow(),
                                txtOpenDesktopExceptionTitle,
                                e
                                );
                        }
                }
            }).start();
    }

    private void browseDesktop( final URL url )
    {
        if( desktop == null ) {
            desktop = java.awt.Desktop.getDesktop();
            }

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    logger .info( "trying to open: " + url );
                    desktop.browse( url.toURI() );
                    }
                catch( Exception e ) {
                    DialogHelper.showMessageExceptionDialog(
                            getMainWindow(),
                            txtBrowseDesktopExceptionTitle,
                            e
                            );
                    }
            }
        }).start();
    }

    private Window getMainWindow()
    {
        return this.mainWindow;
    }

}
