// $codepro.audit.disable largeNumberOfFields, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.confirm;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;
import com.googlecode.cchlib.util.MapSetHelper;

public class JPanelConfirm extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(JPanelConfirm.class);
    public static final String ACTIONCMD_GENERATE_SCRIPT = "ACTIONCMD_GENERATE_SCRIPT";

    private final AppToolKit dfToolKit;
    private final JTable jTableFiles2Delete;
    private final JLabel jLabelTitle;
    private final JProgressBar jProgressBarDeleteProcess;
    private final JButton jButtonDoScript;

    private final Icon iconOk;
    private final Icon iconKo;
    private final Icon iconKoButDelete;
    private AbstractTableModel tableModel;
    private JPanelConfirmModel tableDts_toDelete;

    @I18nString private final String[] columnsHeaders = {
        "File to delete",
        "Length",
        "Kept",
        "Deleted"
        };
    @I18nString private final String txtWaiting = "Waitting for user...";
    @I18nString private final String txtTitle = "%d file(s) selected to be deleted";
    @I18nString private final String txtMsgDone = "Done";
    @I18nString private final String txtCopy = "Copy";
    @I18nString private final String msgStr_doDeleteExceptiontitle = "Error while deleting files";
    @I18nString private final String txtIconKo = "File already exist";
    @I18nString private final String txtIconKoButDelete = "File does not exist";

    public JPanelConfirm()
    {
        this.dfToolKit = AppToolKitService.getInstance().getAppToolKit();

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{26, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        {
            jLabelTitle = new JLabel("Files selected to be deleted");
            final GridBagConstraints gbc_jLabelTitle = new GridBagConstraints();
            gbc_jLabelTitle.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelTitle.gridx = 0;
            gbc_jLabelTitle.gridy = 0;
            add(jLabelTitle, gbc_jLabelTitle);
        }
        {
            final GridBagConstraints gbc_jScrollPaneFiles2Delete = new GridBagConstraints();
            gbc_jScrollPaneFiles2Delete.fill = GridBagConstraints.BOTH;
            gbc_jScrollPaneFiles2Delete.insets = new Insets(0, 0, 5, 0);
            gbc_jScrollPaneFiles2Delete.gridx = 0;
            gbc_jScrollPaneFiles2Delete.gridy = 1;
            final JScrollPane jScrollPaneFiles2Delete = new JScrollPane();
            jTableFiles2Delete = new JTable();
            jScrollPaneFiles2Delete.setViewportView( jTableFiles2Delete );
            add( jScrollPaneFiles2Delete, gbc_jScrollPaneFiles2Delete );
        }
        {
            final JPanel jPanelBottom = new JPanel();
            jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.Y_AXIS));
            final GridBagConstraints gbc_jProgressBarDeleteProcess = new GridBagConstraints();
            gbc_jProgressBarDeleteProcess.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarDeleteProcess.insets = new Insets(0, 0, 5, 0);
            gbc_jProgressBarDeleteProcess.gridx = 0;
            gbc_jProgressBarDeleteProcess.gridy = 2;
            jProgressBarDeleteProcess = new JProgressBar();
            add( jProgressBarDeleteProcess, gbc_jProgressBarDeleteProcess);
        }
        {
            final GridBagConstraints gbc_jButtonDoScript = new GridBagConstraints();
            gbc_jButtonDoScript.gridx = 0;
            gbc_jButtonDoScript.gridy = 3;
            jButtonDoScript = new JButton("Create script");
            jButtonDoScript.setActionCommand( ACTIONCMD_GENERATE_SCRIPT );
            jButtonDoScript.addActionListener((final ActionEvent e) -> {
            });
            add( jButtonDoScript, gbc_jButtonDoScript);

            // TODO: NOT YET IMPLEMENTED
            // $hide>>$
            jButtonDoScript.setVisible(false);
            // $hide<<$
        }

        iconOk          = dfToolKit.getResources().getSmallOKIcon();
        iconKo          = dfToolKit.getResources().getSmallKOIcon();
        iconKoButDelete = dfToolKit.getResources().getSmallOKButOKIcon();

        setSize(320, 240);
    }

    public void populate(
            final Map<String, Set<KeyFileState>> duplicateFiles
            )
    {
        //clear();
        LOGGER.info( "populate: Duplicate count: " + duplicateFiles.size() );

        tableDts_toDelete = new JPanelConfirmModel( duplicateFiles );

        LOGGER.info( "populate: Selected count: " + tableDts_toDelete.size() );

        final DefaultTableCellRenderer render = new DefaultTableCellRenderer()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(
                    final JTable table,
                    final Object value,
                    final boolean isSelected,
                    final boolean hasFocus,
                    final int row,
                    final int column
                    )
            {
                if( row == 0 ) {
                    final KeyFileState f = tableDts_toDelete.get( row );
                    setText( f.getFile().getPath() );

                    final Boolean b = tableDts_toDelete.getDeleted( row );

                    if( b != null ) {
                        setHorizontalAlignment(SwingConstants.LEFT);

                        if( b.booleanValue() ) {
                            setIcon( iconOk );
                            }
                        else {
                            if( f.getFile().exists() ) {
                                setIcon( iconKo );
                                setToolTipText( txtIconKo );
                                }
                            else {
                                setIcon( iconKoButDelete );
                                setToolTipText( txtIconKoButDelete );
                                }
                            }
                        }
                    } // else no change !

                return super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                        );
            }
        };

        jTableFiles2Delete.setDefaultRenderer(String.class, render);

        tableModel = new AbstractTableModel()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public int getColumnCount()
            {
                return columnsHeaders.length;
            }
            @Override
            public String getColumnName(final int column)
            {
                return columnsHeaders[column];
            }
            @Override
            public int getRowCount()
            {
                return tableDts_toDelete.size();
            }
            @Override
            public Class<?> getColumnClass(final int columnIndex)
            {
                switch(columnIndex) {
                    case 1:
                        return Long.class;
                    case 2:
                    case 3:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex)
            {
                if( columnIndex == 1 ) {
                    //return tableDts_length[rowIndex];
                    return tableDts_toDelete.getFileLength( rowIndex );
                    }
                else {
                    final KeyFileState f = tableDts_toDelete.get( rowIndex );

                    switch(columnIndex) {
                        case 0 : return f.getFile().getPath();
                        //case 1 : return f.getFile().length();
                        case 2 : return computeKept( duplicateFiles,f.getKey() );
                        case 3 : return computeDeleted( duplicateFiles,f.getKey() );
                    }
                    return null;
                }
            }
         };

        jTableFiles2Delete.setModel(tableModel);

        final JPopupMenuForJTable popupMenu = new JPopupMenuForJTable(jTableFiles2Delete)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected JPopupMenu createContextMenu(
                    final int rowIndex,
                    final int columnIndex
                    )
            {
                final JPopupMenu contextMenu = new JPopupMenu();

                if( rowIndex == 0 ) {
                    addCopyMenuItem(contextMenu, txtCopy, rowIndex, columnIndex);
                }

                return contextMenu;
            }
        };
        popupMenu.addMenu();

        jProgressBarDeleteProcess.setMinimum( 0 );
        jProgressBarDeleteProcess.setValue( 0 );
        jProgressBarDeleteProcess.setMaximum( tableDts_toDelete.size() );
        jProgressBarDeleteProcess.setIndeterminate( false );
        jProgressBarDeleteProcess.setString( txtWaiting  );
        jProgressBarDeleteProcess.setStringPainted( true );

        jLabelTitle.setText( String.format( txtTitle, Integer.valueOf( tableDts_toDelete.size() ) ) );
    }

    public void updateProgressBar(final int count, final String msg)
    {
        jProgressBarDeleteProcess.setValue( count );
        jProgressBarDeleteProcess.setString( msg );
    }

    private Integer computeKept(
        final Map<String, Set<KeyFileState>>    duplicateFiles,
        final String                            k
        )
    {
        final Set<KeyFileState> s = duplicateFiles.get( k );
        int               c = 0;

        if( s != null ) {
            for( final KeyFileState f:s ) {
                if(!f.isSelectedToDelete()) {
                    c++;
                }
            }
        }

        return Integer.valueOf( c );
    }

    private Integer computeDeleted(
        final Map<String, Set<KeyFileState>> duplicateFiles,
        final String                         k
        )
    {
        final Set<KeyFileState> s = duplicateFiles.get( k );
        int               c = 0;

        if( s != null ) {
            for(final KeyFileState f:s) {
                if(f.isSelectedToDelete()) {
                    c++;
                }
            }
        }

        return Integer.valueOf( c );
    }

    public void doDelete(
        final Map<String, Set<KeyFileState>> duplicateFiles
        )
    {
        final Runnable task = () -> {
            try {
                private_doDelete( duplicateFiles );
            }
            catch( final Exception e ) {
                LOGGER.fatal( "*** Error catched while delete files", e );
                DialogHelper.showMessageExceptionDialog(
                        dfToolKit.getMainFrame(), //.getMainWindow(),
                        msgStr_doDeleteExceptiontitle,
                        e
                );
            }
        };

        new Thread( task, "doDelete()" ).start();
    }

    private void private_doDelete(
            final Map<String, Set<KeyFileState>> duplicateFiles
            )
    {
        int                     deleteCount = 0;
        final int               size = tableDts_toDelete.size();
        final long deleteSleepDisplay =
            (size < this.dfToolKit.getPreferences().getDeleteSleepDisplayMaxEntries()) ?
                    this.dfToolKit.getPreferences().getDeleteSleepDisplay()
                    :
                    0;

        LOGGER.info( "private_doDelete: Selected count: " + tableDts_toDelete.size() );

        for( int i=0; i<size; i++ ) {
            final KeyFileState kf = tableDts_toDelete.get( i );
            final String msg = kf.getFile().getPath();

            updateProgressBar(deleteCount,msg);

            // Delete file
            final boolean isDel = kf.getFile().delete();

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace("Delete=" + isDel + ':' + kf + " - i=" + i);
                }

            // Store result
            //tableDts_deleted[ i ] = new Boolean( isDel );
            tableDts_toDelete.setDeleted( i, isDel );
            tableModel.fireTableCellUpdated( i, 0 );
            //.fireTableDataChanged();

            final int row = i;

            SwingUtilities.invokeLater( () -> {
                try {
                    jTableFiles2Delete.scrollRectToVisible(
                            jTableFiles2Delete.getCellRect(row, 0, true)
                    );
                }
                catch( final Exception e ) {
                    LOGGER.warn( "Swing error:", e );
                }
            });

            if( deleteSleepDisplay > 0 ) {
                this.dfToolKit.sleep( deleteSleepDisplay );
                }

            deleteCount++;
            updateProgressBar(deleteCount,msg);
        }

        final int keepCount = MapSetHelper.size( duplicateFiles );

        updateProgressBar(deleteCount,txtMsgDone);

        LOGGER.info( "deleteCount= " + deleteCount  );
        LOGGER.info( "keepCount= " + keepCount );

        // Remove files that can't be found on file system
        final Iterator<KeyFileState> iter = MapSetHelper.values( duplicateFiles );

        while( iter.hasNext() ) {
            final KeyFileState f = iter.next();

            if( !f.getFile().exists() ) {
                iter.remove();
                }
            }

        // Remove from list, files with no more
        // duplicate entry
        MapSetHelper.purge( duplicateFiles, 2);

        final int newDupCount = MapSetHelper.size( duplicateFiles );

        LOGGER.info( "newDupCount= " + newDupCount );
        }
}
