package samples.downloader.gdai;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Configuration panel for {@link GDAI_tumblr_com}
 */
// public 
abstract class GDAI_tumblr_com_ConfigJPanel extends JPanel 
{
    private static final long serialVersionUID = 1L;
    private static final String ACTIONCMD_ADD = "ACTIONCMD_ADD";
    private static final String ACTIONCMD_REMOVE = "ACTIONCMD_REMOVE";
    private JTable table;
    private ActionListener actionListener;
    private GDAI_tumblr_com_ConfigTableModel tableModel;
    
    /**
     * Create the panel.
     * @param config 
     */
    public GDAI_tumblr_com_ConfigJPanel(
        final GDAI_tumblr_com_Config config
        )
    {
        tableModel = new GDAI_tumblr_com_ConfigTableModel( config.toEntriesVector() );
        
        setBorder(new TitledBorder(null, "Configuration") );
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 30, 0};
        gridBagLayout.rowHeights = new int[]{22, 22, 0, 22, 22, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        {
            JScrollPane scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridheight = 5;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            add(scrollPane, gbc_scrollPane);
            
            table = new JTable();
            table.setModel( tableModel );
            scrollPane.setViewportView(table);
        }        
        {
            JButton btnAdd = new JButton( "Add" );
            btnAdd.setActionCommand( ACTIONCMD_ADD );
            btnAdd.addActionListener( getActionListener() );
            GridBagConstraints gbc_btnAdd = new GridBagConstraints();
            gbc_btnAdd.fill = GridBagConstraints.BOTH;
            gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
            gbc_btnAdd.gridx = 1;
            gbc_btnAdd.gridy = 0;
            add(btnAdd, gbc_btnAdd);
        }
        {
            JButton btnRemove = new JButton( "Remove" );
            btnRemove.setActionCommand( ACTIONCMD_REMOVE );
            btnRemove.addActionListener( getActionListener() );
            GridBagConstraints gbc_btnRemove = new GridBagConstraints();
            gbc_btnRemove.fill = GridBagConstraints.BOTH;
            gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
            gbc_btnRemove.gridx = 1;
            gbc_btnRemove.gridy = 1;
            add(btnRemove, gbc_btnRemove);
        }
        {
            JButton btnCancel = new JButton( "Cancel" );
             btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelClicked();
                }
            });
            GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.BOTH;
            gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
            gbc_btnCancel.gridx = 1;
            gbc_btnCancel.gridy = 3;
            add(btnCancel, gbc_btnCancel);
        }
        {
            JButton btnOk = new JButton( "OK" );
            btnOk.addActionListener(new ActionListener() {
                @SuppressWarnings("unchecked")
                public void actionPerformed(ActionEvent evt) {
                    config.setDataVector( tableModel.getDataVector() );
                    
                    okClicked();
                }
            });
            GridBagConstraints gbc_btnOk = new GridBagConstraints();
            gbc_btnOk.fill = GridBagConstraints.BOTH;
            gbc_btnOk.gridx = 1;
            gbc_btnOk.gridy = 4;
            add(btnOk, gbc_btnOk);
        }
    }

    abstract void cancelClicked();
    abstract void okClicked();

    private ActionListener getActionListener()
    {
        if( actionListener == null ) {
            actionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent event )
                {
                    final String cmd = event.getActionCommand();
                    
                    switch( cmd ) 
                    {
                        case ACTIONCMD_ADD :
                            addEntry();
                            break;
                        case ACTIONCMD_REMOVE : 
                            removeEntry();
                            break;
                    }
                }
            };
        }

        return actionListener;
    }

    protected void removeEntry()
    {
        int[] entries2Remove = this.table.getSelectedColumns();
        
        for( int row : entries2Remove ) {
            tableModel.removeRow( row );
            }
    }

    protected void addEntry()
    {
        // Add row
        tableModel.addRow( new String[] {"","" } );
        
        // Display row
        table.scrollRectToVisible(
            table.getCellRect(
                table.getRowCount() - 1,
                0, 
                true
                )
            );
        
        // focus on first component
        // TODO: set focus
    }

}
