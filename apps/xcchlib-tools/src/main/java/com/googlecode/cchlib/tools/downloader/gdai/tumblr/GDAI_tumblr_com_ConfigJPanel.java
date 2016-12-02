package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * Configuration panel for {@link GDAI_tumblr_com}
 */
// Not public
abstract class GDAI_tumblr_com_ConfigJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private static final String ACTIONCMD_ADD    = "ACTIONCMD_ADD";
    private static final String ACTIONCMD_REMOVE = "ACTIONCMD_REMOVE";

    private JTable table;
    private final GDAI_tumblr_com_ConfigTableModel tableModel;

    /**
     * Create the panel.
     * @param config
     */
    @SuppressWarnings({"squid:S00117","squid:S1199"})
    public GDAI_tumblr_com_ConfigJPanel(
        final Config config
        )
    {
        this.tableModel = new GDAI_tumblr_com_ConfigTableModel( config.toVector() );

        setBorder(new TitledBorder(null, "Configuration") );
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 30, 0};
        gridBagLayout.rowHeights = new int[]{22, 22, 0, 22, 22, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        {
            final JScrollPane scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridheight = 5;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            add(scrollPane, gbc_scrollPane);

            this.table = new JTable();
            this.table.setModel( this.tableModel );
            scrollPane.setViewportView(this.table);
        }
        {
            final JButton btnAdd = new JButton( "Add" );
            btnAdd.setActionCommand( ACTIONCMD_ADD );
            btnAdd.addActionListener( this::actionPerformed );
            final GridBagConstraints gbc_btnAdd = new GridBagConstraints();
            gbc_btnAdd.fill = GridBagConstraints.BOTH;
            gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
            gbc_btnAdd.gridx = 1;
            gbc_btnAdd.gridy = 0;
            add(btnAdd, gbc_btnAdd);
        }
        {
            final JButton btnRemove = new JButton( "Remove" );
            btnRemove.setActionCommand( ACTIONCMD_REMOVE );
            btnRemove.addActionListener( this::actionPerformed );
            final GridBagConstraints gbc_btnRemove = new GridBagConstraints();
            gbc_btnRemove.fill = GridBagConstraints.BOTH;
            gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
            gbc_btnRemove.gridx = 1;
            gbc_btnRemove.gridy = 1;
            add(btnRemove, gbc_btnRemove);
        }
        {
            final JButton btnCancel = new JButton( "Cancel" );
             btnCancel.addActionListener(evt -> cancelClicked());
            final GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.BOTH;
            gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
            gbc_btnCancel.gridx = 1;
            gbc_btnCancel.gridy = 3;
            add(btnCancel, gbc_btnCancel);
        }
        {
            final JButton btnOk = new JButton( "OK" );
            btnOk.addActionListener( evt -> onOK( config ) );
            final GridBagConstraints gbc_btnOk = new GridBagConstraints();
            gbc_btnOk.fill = GridBagConstraints.BOTH;
            gbc_btnOk.gridx = 1;
            gbc_btnOk.gridy = 4;
            add(btnOk, gbc_btnOk);
        }
    }

    private void onOK( final Config config )
    {
        config.setDataFromVector(
                this.tableModel.getDataVector()
                );

        okClicked();
    }

    abstract void cancelClicked();
    abstract void okClicked();

    // ActionListener
    private void actionPerformed( final ActionEvent event  )
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

    protected void removeEntry()
    {
        final int[] entries2Remove = this.table.getSelectedColumns();

        for( final int row : entries2Remove ) {
            this.tableModel.removeRow( row );
            }
    }

    protected void addEntry()
    {
        // Add row
        this.tableModel.addRow( new String[] { StringHelper.EMPTY, StringHelper.EMPTY } );

        // Display row
        this.table.scrollRectToVisible(
            this.table.getCellRect(
                this.table.getRowCount() - 1,
                0,
                true
                )
            );

        // focus on first component
        // TODO: set focus
    }

}
