// $codepro.audit.disable largeNumberOfFields, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.confirm;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import javax.swing.BoxLayout;
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
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;
import com.googlecode.cchlib.util.MapSetHelper;

@I18nName("duplicatefiles.JPanelConfirm")
public class JPanelConfirm extends JPanel
{
    private static final class Model extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final JPanelConfirm jPanelConfirm;
        private final Map<String, Set<KeyFileState>> duplicateFiles;

        private Model( //
            final JPanelConfirm                   jPanelConfirm, //
            final Map<String, Set<KeyFileState>>  duplicateFiles //
            )
        {
            this.jPanelConfirm  = jPanelConfirm;
            this.duplicateFiles = duplicateFiles;
        }

        @Override
        public int getColumnCount()
        {
            return this.jPanelConfirm.columnsHeaders.length;
        }

        @Override
        public String getColumnName(final int column)
        {
            return this.jPanelConfirm.columnsHeaders[column];
        }

        @Override
        public int getRowCount()
        {
            return this.jPanelConfirm.tableDataToDelete.size();
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
                return this.jPanelConfirm.tableDataToDelete.getFileLength( rowIndex );
                }
            else {
                final KeyFileState f = this.jPanelConfirm.tableDataToDelete.get( rowIndex );

                switch(columnIndex) {
                    case 0 : return f.getPath();
                    //case 1 : return f.getFile().length();
                    case 2 : return computeKept( this.duplicateFiles,f.getKey() );
                    case 3 : return computeDeleted( this.duplicateFiles,f.getKey() );
                }
                return null;
            }
        }
    }

    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private final class ConfirmTableCellRenderer extends DefaultTableCellRenderer
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
                final KeyFileState f = JPanelConfirm.this.tableDataToDelete.get( row );
                setText( f.getPath() );

                final Boolean b = JPanelConfirm.this.tableDataToDelete.getDeleted( row );

                if( b != null ) {
                    setHorizontalAlignment(SwingConstants.LEFT);

                    if( b.booleanValue() ) {
                        setIcon( JPanelConfirm.this.dfToolKit.getResources().getSmallOKIcon() );
                        }
                    else {
                        if( f.isFileExists() ) {
                            setIcon( JPanelConfirm.this.dfToolKit.getResources().getSmallKOIcon() );
                            setToolTipText( JPanelConfirm.this.txtIconKo );
                            }
                        else {
                            setIcon( JPanelConfirm.this.dfToolKit.getResources().getSmallOKButOKIcon() );
                            setToolTipText( JPanelConfirm.this.txtIconKoButDelete );
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
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(JPanelConfirm.class);
    public static final String ACTIONCMD_GENERATE_SCRIPT = "ACTIONCMD_GENERATE_SCRIPT";

    private final AppToolKit dfToolKit;
    private final JTable jTableFiles2Delete;
    private final JLabel jLabelTitle;
    private final JProgressBar jProgressBarDeleteProcess;
    private final JButton jButtonDoScript;

    private AbstractTableModel tableModel;
    private JPanelConfirmModel tableDataToDelete;

    @I18nString private String[] columnsHeaders;
    @I18nString private String txtWaiting;
    @I18nString private String txtTitle;
    @I18nString private String txtMsgDone;
    @I18nString private String txtCopy;
    @I18nString private String msgStr_doDeleteExceptiontitle;
    @I18nString private String txtIconKo;
    @I18nString private String txtIconKoButDelete;

    @SuppressWarnings({"squid:S00117","squid:S1199"})
    public JPanelConfirm()
    {
        beSurNonFinal();

        this.dfToolKit = AppToolKitService.getInstance().getAppToolKit();

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{26, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        {
            this.jLabelTitle = new JLabel("Files selected to be deleted");
            final GridBagConstraints gbc_jLabelTitle = new GridBagConstraints();
            gbc_jLabelTitle.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelTitle.gridx = 0;
            gbc_jLabelTitle.gridy = 0;
            add(this.jLabelTitle, gbc_jLabelTitle);
        }
        {
            final GridBagConstraints gbc_jScrollPaneFiles2Delete = new GridBagConstraints();
            gbc_jScrollPaneFiles2Delete.fill = GridBagConstraints.BOTH;
            gbc_jScrollPaneFiles2Delete.insets = new Insets(0, 0, 5, 0);
            gbc_jScrollPaneFiles2Delete.gridx = 0;
            gbc_jScrollPaneFiles2Delete.gridy = 1;
            final JScrollPane jScrollPaneFiles2Delete = new JScrollPane();
            this.jTableFiles2Delete = new JTable();
            jScrollPaneFiles2Delete.setViewportView( this.jTableFiles2Delete );
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
            this.jProgressBarDeleteProcess = new JProgressBar();
            add( this.jProgressBarDeleteProcess, gbc_jProgressBarDeleteProcess);
        }
        {
            final GridBagConstraints gbc_jButtonDoScript = new GridBagConstraints();
            gbc_jButtonDoScript.gridx = 0;
            gbc_jButtonDoScript.gridy = 3;
            this.jButtonDoScript = new JButton("Create script");
            this.jButtonDoScript.setActionCommand( ACTIONCMD_GENERATE_SCRIPT );
            this.jButtonDoScript.addActionListener((final ActionEvent e) -> {
                // TODO
            });
            add( this.jButtonDoScript, gbc_jButtonDoScript);

            // TODO: NOT YET IMPLEMENTED
            // $hide>>$
            this.jButtonDoScript.setVisible(false);
            // $hide<<$
        }

        setSize(320, 240);
    }

    private void beSurNonFinal()
    {
        this.txtWaiting = "Waitting for user...";
        this.txtTitle = "%d file(s) selected to be deleted";
        this.txtMsgDone = "Done";
        this.txtCopy = "Copy";
        this.msgStr_doDeleteExceptiontitle = "Error while deleting files";
        this.txtIconKo = "File already exist";
        this.txtIconKoButDelete = "File does not exist";
        this.columnsHeaders = new String[] {
                "File to delete",
                "Length",
                "Kept",
                "Deleted"
                };
    }

    public void populate(
            final Map<String, Set<KeyFileState>> duplicateFiles
            )
    {
        //clear();
        LOGGER.info( "populate: Duplicate count: " + duplicateFiles.size() );

        this.tableDataToDelete = new JPanelConfirmModel( duplicateFiles );

        LOGGER.info( "populate: Selected count: " + this.tableDataToDelete.size() );

        final DefaultTableCellRenderer render = newDefaultTableCellRenderer();

        this.jTableFiles2Delete.setDefaultRenderer(String.class, render);

        this.tableModel = newAbstractTableModel( duplicateFiles );

        this.jTableFiles2Delete.setModel(this.tableModel);

        final JPopupMenuForJTable popupMenu = newJPopupMenuForJTable();
        popupMenu.addMenu();

        this.jProgressBarDeleteProcess.setMinimum( 0 );
        this.jProgressBarDeleteProcess.setValue( 0 );
        this.jProgressBarDeleteProcess.setMaximum( this.tableDataToDelete.size() );
        this.jProgressBarDeleteProcess.setIndeterminate( false );
        this.jProgressBarDeleteProcess.setString( this.txtWaiting  );
        this.jProgressBarDeleteProcess.setStringPainted( true );

        this.jLabelTitle.setText( String.format( this.txtTitle, Integer.valueOf( this.tableDataToDelete.size() ) ) );
    }

    private JPopupMenuForJTable newJPopupMenuForJTable()
    {
        return new JPopupMenuForJTable(this.jTableFiles2Delete)
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
                    addCopyMenuItem(contextMenu, JPanelConfirm.this.txtCopy, rowIndex, columnIndex);
                }

                return contextMenu;
            }
        };
    }

    private AbstractTableModel newAbstractTableModel( final Map<String, Set<KeyFileState>> duplicateFiles )
    {
        return new Model( this, duplicateFiles );
    }

    private DefaultTableCellRenderer newDefaultTableCellRenderer()
    {
        return new ConfirmTableCellRenderer();
    }

    public void updateProgressBar(final int count, final String msg)
    {
        this.jProgressBarDeleteProcess.setValue( count );
        this.jProgressBarDeleteProcess.setString( msg );
    }

    private static Integer computeKept(
        final Map<String, Set<KeyFileState>>    duplicateFiles,
        final String                            k
        )
    {
        final Set<KeyFileState>                 set         = duplicateFiles.get( k );
        final Function<KeyFileState, Boolean>   function    = file -> Boolean.valueOf( !file.isSelectedToDelete() );

        return computeKeepDelete( set, function  );
    }

    public static Integer computeKeepDelete( //
        final Set<KeyFileState>                 set, //
        final Function<KeyFileState, Boolean>   function //
        )
    {
        final AtomicInteger count = new AtomicInteger();

        if( set != null ) {
            set.stream().forEach( file -> {
                if( function.apply( file ) ) {
                    count.incrementAndGet();
                }
            } );
        }

        return Integer.valueOf( count.get() );
    }

    private static Integer computeDeleted(
        final Map<String, Set<KeyFileState>> duplicateFiles,
        final String                         key
        )
    {
        final Set<KeyFileState>                 set         = duplicateFiles.get( key );
        final Function<KeyFileState, Boolean>   function    = file -> Boolean.valueOf( file.isSelectedToDelete() );

        return computeKeepDelete( set, function  );
    }

    public void doDelete( //
        final Map<String, Set<KeyFileState>> duplicateFiles //
        )
    {
        final Runnable task = () -> {
            try {
                // TODO disable gadgets

                private_doDelete( duplicateFiles );
            }
            catch( final Exception e ) {
                LOGGER.fatal( "*** Error catched while delete files", e );

                DialogHelper.showMessageExceptionDialog(
                        this.dfToolKit.getMainFrame(),
                        this.msgStr_doDeleteExceptiontitle,
                        e
                );
            }
            finally {
                // TODO enable gadgets
            }
        };

        new Thread( task, "doDelete()" ).start();
    }

    private void private_doDelete( //
        final Map<String, Set<KeyFileState>> duplicateFiles //
        )
    {
        int         deleteCount         = 0;
        final int   size                = this.tableDataToDelete.size();
        final long  deleteSleepDisplay  = //
            (size < this.dfToolKit.getPreferences().getDeleteSleepDisplayMaxEntries()) ?
                    this.dfToolKit.getPreferences().getDeleteSleepDisplay()
                    :
                    0;

        LOGGER.info( "private_doDelete: Selected count: " + this.tableDataToDelete.size() );

        for( int i=0; i<size; i++ ) {
            final KeyFileState  kf  = this.tableDataToDelete.get( i );
            final String        msg = kf.getPath();

            updateProgressBar(deleteCount,msg);

            // Delete file
            final boolean isDel = kf.delete();

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace("Delete=" + isDel + ':' + kf + " - i=" + i);
                }

            // Store result
            //tableDts_deleted[ i ] = new Boolean( isDel );
            this.tableDataToDelete.setDeleted( i, isDel );
            this.tableModel.fireTableCellUpdated( i, 0 );
            //.fireTableDataChanged();

            final int row = i;

            SwingUtilities.invokeLater( () -> {
                try {
                    this.jTableFiles2Delete.scrollRectToVisible(
                            this.jTableFiles2Delete.getCellRect(row, 0, true)
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

        updateProgressBar(deleteCount,this.txtMsgDone);

        LOGGER.info( "deleteCount= " + deleteCount  );
        LOGGER.info( "keepCount= " + keepCount );

        // Remove files that can't be found on file system
        final Iterator<KeyFileState> iter = MapSetHelper.values( duplicateFiles );

        while( iter.hasNext() ) {
            final KeyFileState f = iter.next();

            if( !f.isFileExists() ) {
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
