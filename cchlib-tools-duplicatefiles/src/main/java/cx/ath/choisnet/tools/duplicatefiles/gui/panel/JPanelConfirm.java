package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.table.JPopupMenuForJTable;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.duplicatefiles.ResourcesLoader;
import cx.ath.choisnet.util.HashMapSet;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelConfirm extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger slogger = Logger.getLogger(JPanelConfirm.class);

    private JTable jTableFiles2Delete;
    private JScrollPane jScrollPaneFiles2Delete;
    private JPanel jPanelHeader;
    private JLabel jLabelTitle;
    private JPanel jPanelBottom;
    private JProgressBar jProgressBarDeleteProcess;
    private JPanel jPanelScript;
    private JButton jButtonDoScript;

    private Icon iconOk;
    private Icon iconKo;
    private AbstractTableModel tableModel;
    private List<KeyFileState> tableDts_toDelete;
    private Boolean[] tableDts_deleted;
    private long[] tableDts_length;
//    private String[]  tableDts_deleteMsg;

    @I18nString private String[] columnsHeaders = {
        "File to delete",
        "Length",
        "Kept",
        "Deleted"
        };
    @I18nString private String txtWaiting = "Waitting for user...";
    @I18nString private String txtTitle = "%d file(s) selected to be deleted";
    @I18nString private String txtMsgDone = "Done";

    public JPanelConfirm()
    {
        initComponents();
        jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.Y_AXIS));

        // NOT YET IMPLEMENTED
        jButtonDoScript.setVisible(false);

        iconOk = ResourcesLoader.getImageIcon( "ok.12x12.png" );
        iconKo = ResourcesLoader.getImageIcon( "ko.12x12.png" );
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(getJPanelHeader(), BorderLayout.NORTH);
        add(getJScrollPaneFiles2Delete(), BorderLayout.CENTER);
        add(getJPanelBottom(), BorderLayout.SOUTH);
        setSize(320, 240);
    }

    private JPanel getJPanelScript() {
        if (jPanelScript == null) {
            jPanelScript = new JPanel();
            jPanelScript.add(getJButtonDoScript());
        }
        return jPanelScript;
    }

    private JButton getJButtonDoScript() {
        if (jButtonDoScript == null) {
            jButtonDoScript = new JButton();
            jButtonDoScript.setText("Create script");
            jButtonDoScript.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    jButtonDoScriptMouseMousePressed(event);
                }
            });
        }
        return jButtonDoScript;
    }

    private JPanel getJPanelBottom() {
        if (jPanelBottom == null) {
            jPanelBottom = new JPanel();
            jPanelBottom.setLayout(new BoxLayout(jPanelBottom, BoxLayout.Y_AXIS));
            jPanelBottom.add(getJProgressBarDeleteProcess());
            jPanelBottom.add(getJPanelScript());
        }
        return jPanelBottom;
    }

    private JProgressBar getJProgressBarDeleteProcess() {
        if (jProgressBarDeleteProcess == null) {
            jProgressBarDeleteProcess = new JProgressBar();
        }
        return jProgressBarDeleteProcess;
    }

    private JPanel getJPanelHeader() {
        if (jPanelHeader == null) {
            jPanelHeader = new JPanel();
            jPanelHeader.add(getJLabelTitle());
        }
        return jPanelHeader;
    }

    private JLabel getJLabelTitle() {
        if (jLabelTitle == null) {
            jLabelTitle = new JLabel();
            jLabelTitle.setText("Files selected to be deleted");
        }
        return jLabelTitle;
    }

    private JScrollPane getJScrollPaneFiles2Delete() {
        if (jScrollPaneFiles2Delete == null) {
            jScrollPaneFiles2Delete = new JScrollPane();
            jScrollPaneFiles2Delete.setViewportView(getJTableFiles2Delete());
        }
        return jScrollPaneFiles2Delete;
    }

    private JTable getJTableFiles2Delete() {
        if (jTableFiles2Delete == null) {
            jTableFiles2Delete = new JTable();
        }
        return jTableFiles2Delete;
    }

    public void initComponents(
            final HashMapSet<String,KeyFileState> dupFiles
            )
    {
        // final List<KeyFileState> toDelete = new ArrayList<KeyFileState>();
        tableDts_toDelete = new ArrayList<KeyFileState>();

        for(KeyFileState f:dupFiles) {
            if( f.isSelectedToDelete() ) {
                tableDts_toDelete.add( f );
            }
        }

        final int size = tableDts_toDelete.size();

        tableDts_deleted = new Boolean[size] ;
        tableDts_length = new long[size] ;

        for(int i=0;i<size;i++) {
            KeyFileState f = tableDts_toDelete.get( i );
            tableDts_length[i] = f.getFile().length();
        }

        DefaultTableCellRenderer render = new DefaultTableCellRenderer()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
                    )
            {
                if( row == 0 ) {
                    KeyFileState f = tableDts_toDelete.get( row );
                    setText( f.getFile().getPath() );

                    Boolean b = tableDts_deleted[row];

                    if( b != null ) {
                        setHorizontalAlignment(SwingConstants.LEFT);

                        if( b.booleanValue() ) {
                            setIcon( iconOk );
                        }
                        else {
                            setIcon( iconKo );
                        }
                    }
                }
                //setText("case " + row + ", " + column);
                //;
//                return this;
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
            public String getColumnName(int column)
            {
                return columnsHeaders[column];
            }
            @Override
            public int getRowCount()
            {
                return tableDts_toDelete.size();
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
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
            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if( columnIndex == 1 ) {
                    return tableDts_length[rowIndex];
                }
                else {
                    KeyFileState f = tableDts_toDelete.get( rowIndex );

                    switch(columnIndex) {
                        case 0 : return f.getFile().getPath();
                        //case 1 : return f.getFile().length();
                        case 2 : return computeKept(dupFiles,f.getKey());
                        case 3 : return computeDeleted(dupFiles,f.getKey());
                    }
                    return null;
                }
            }
         };

        jTableFiles2Delete.setModel(tableModel);

        JPopupMenuForJTable popupMenu = new JPopupMenuForJTable(jTableFiles2Delete)
        {
            @Override
            protected JPopupMenu createContextMenu(
                    int rowIndex,
                    int columnIndex
                    )
            {
                JPopupMenu contextMenu = new JPopupMenu();

                if( rowIndex == 0 ) {
                    addCopyMenuItem(contextMenu, rowIndex, columnIndex);
                }

                return contextMenu;
            }
        };
        popupMenu.setMenu();

        jProgressBarDeleteProcess.setMinimum( 0 );
        jProgressBarDeleteProcess.setValue( 0 );
        jProgressBarDeleteProcess.setMaximum( tableDts_toDelete.size() );
        jProgressBarDeleteProcess.setIndeterminate( false );
        jProgressBarDeleteProcess.setString( txtWaiting  );
        jProgressBarDeleteProcess.setStringPainted( true );

        jLabelTitle.setText( String.format( txtTitle, tableDts_toDelete.size() ) );
    }

    public void updateProgressBar(int count, String msg)
    {
        jProgressBarDeleteProcess.setValue( count );
        jProgressBarDeleteProcess.setString( msg );
    }

    private int computeKept(
            HashMapSet<String,KeyFileState> dupFiles,
            String                          k
            )
    {
        Set<KeyFileState> s = dupFiles.get( k );
        int               c = 0;

        if( s != null ) {
            for(KeyFileState f:s) {
                if(!f.isSelectedToDelete()) {
                    c++;
                }
            }
        }

        return c;
    }

    private int computeDeleted(
            HashMapSet<String,KeyFileState> dupFiles,
            String                          k
            )
    {
        Set<KeyFileState> s = dupFiles.get( k );
        int               c = 0;

        if( s != null ) {
            for(KeyFileState f:s) {
                if(f.isSelectedToDelete()) {
                    c++;
                }
            }
        }

        return c;
    }

    public void doDelete(
            final DFToolKit                         tk,
            final HashMapSet<String,KeyFileState>   duplicateFiles
            )
    {
        try {
            private_doDelete( tk, duplicateFiles );
            }
        catch( Exception e ) {
            slogger.fatal( "*** Error catched while delete files", e );
            }
    }

    private void private_doDelete(
            final DFToolKit                         tk,
            final HashMapSet<String,KeyFileState>   duplicateFiles
            )
    {
        //Iterator<KeyFileState>  iter = duplicateFiles.iterator();
        int                     deleteCount = 0;
        final int               size = tableDts_toDelete.size();
        final long deleteSleepDisplay =
            size < tk.getConfigData().getDeleteSleepDisplayMaxEntries() ?
                    tk.getConfigData().getDeleteSleepDisplay()
                    :
                    0;

        for(int i=0;i<size;i++) {
            KeyFileState kf = tableDts_toDelete.get( i );
            String msg = kf.getFile().getPath();

            updateProgressBar(deleteCount,msg);

            // Delete file
            boolean isDel = kf.getFile().delete();

            slogger.info("Delete=" + isDel + ':' + kf + " - i=" + i);

            // Store result
            tableDts_deleted[ i ] = new Boolean( isDel );
            tableModel.fireTableCellUpdated( i, 0 );
            //.fireTableDataChanged();

            jTableFiles2Delete.scrollRectToVisible(
                    jTableFiles2Delete.getCellRect(i, 0, true)
                    );

            tk.sleep( deleteSleepDisplay );

            deleteCount++;
            updateProgressBar(deleteCount,msg);
        }

        int keepCount = duplicateFiles.valuesSize();

        updateProgressBar(deleteCount,txtMsgDone);

        slogger.info( "deleteCount= " + deleteCount  );
        slogger.info( "keepCount= " + keepCount );

        // Remove files that can't be found on file system
        Iterator<KeyFileState> iter = duplicateFiles.iterator();

        while( iter.hasNext() ) {
            KeyFileState f = iter.next();

            if( !f.getFile().exists() ) {
                iter.remove();
            }
        }

        // Remove from list, files with no more
        // duplicate entry
        duplicateFiles.purge(2);

        int newDupCount = duplicateFiles.valuesSize();

        slogger.info( "newDupCount= " + newDupCount );
        //tk.sleep( tk.getConfigData().getDeleteFinalDisplay() );
    }

    private void jButtonDoScriptMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }
}
