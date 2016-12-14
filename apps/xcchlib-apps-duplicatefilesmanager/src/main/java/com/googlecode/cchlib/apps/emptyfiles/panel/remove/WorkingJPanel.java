package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.apps.emptyfiles.tasks.DeleteTask;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.swing.table.JTableColumnsAutoSizer;

@I18nName("emptyfiles.WorkingJPanel")
public class WorkingJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( WorkingJPanel.class );

    private final JTable table;
    private final JTableColumnsAutoSizer autoSizer;
    private final JPanel panel;
    private final JLabel messageLabel;
    private final JProgressBar progressBar;
    private final JButton deleteButton;
    private final JButton selectAllButton;
    private final JButton deselectAllButton;
    private final WorkingTableModel tableModel;
    private final JScrollPane scrollPane;
    private final JButton restartButton;

    @SuppressWarnings({"squid:S00117","squid:S1199"})
    public WorkingJPanel(
        final RemoveEmptyFilesJPanel removeEmptyFilesJPanel,
        final WorkingTableModel      tableModel
        )
    {
        this.tableModel = tableModel;

        setLayout(new BorderLayout(0, 0));
        this.panel = new JPanel();
        add(this.panel);

        {
            final GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[]{337, 0, 0};
            gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 25, 0};
            gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
            this.panel.setLayout(gbl_panel);
        }
        {
            this.messageLabel = new JLabel("Select files to delete");
            this.messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            final GridBagConstraints gbc_messageLabel = new GridBagConstraints();
            gbc_messageLabel.gridwidth = 2;
            gbc_messageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_messageLabel.insets = new Insets(0, 0, 5, 0);
            gbc_messageLabel.gridx = 0;
            gbc_messageLabel.gridy = 0;
            this.panel.add(this.messageLabel, gbc_messageLabel);
        }
        {
            this.scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridheight = 4;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 1;
            this.panel.add(this.scrollPane, gbc_scrollPane);
            {
                this.table = new JTable();
                this.scrollPane.setViewportView(this.table);
                this.table.setModel( tableModel  );

                // Detect change in model.
                tableModel.addTableModelListener( (final TableModelEvent event) -> {
                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "tableChanged :" + event.getSource() );
                    }

                    //fixDisplay();
                    //autoSizer.apply();
                    applySelectionState();
                });

                this.autoSizer = new JTableColumnsAutoSizer( this.table, tableModel );
                tableModel.addTableModelListener( this.autoSizer );
                this.table.addComponentListener( this.autoSizer );
            }
        }
        {
            this.deselectAllButton = new JButton("Deselect All");
            this.deselectAllButton.setIcon( removeEmptyFilesJPanel.getResources().getDeselectAllIcon() );
            this.deselectAllButton.addActionListener(
                (final ActionEvent e) -> tableModel.doUnselectAll()
                );

            final GridBagConstraints gbc_deselectAllButton = new GridBagConstraints();
            gbc_deselectAllButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_deselectAllButton.insets = new Insets(0, 0, 5, 0);
            gbc_deselectAllButton.gridx = 1;
            gbc_deselectAllButton.gridy = 1;
            this.panel.add(this.deselectAllButton, gbc_deselectAllButton);
        }
        {
            this.selectAllButton = new JButton("Select All");
            this.selectAllButton.setIcon( removeEmptyFilesJPanel.getResources().getSelectAllIcon() );
            this.selectAllButton.addActionListener(
                (final ActionEvent e) -> tableModel.doSelectAll()
                );

            final GridBagConstraints gbc_selectAllButton = new GridBagConstraints();
            gbc_selectAllButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_selectAllButton.insets = new Insets(0, 0, 5, 0);
            gbc_selectAllButton.gridx = 1;
            gbc_selectAllButton.gridy = 2;
            this.panel.add(this.selectAllButton, gbc_selectAllButton);
        }
        {
            this.restartButton = new JButton("Restart");
            this.restartButton.addActionListener(
                (final ActionEvent e) -> removeEmptyFilesJPanel.restart()
                );

            final GridBagConstraints gbc_restartButton = new GridBagConstraints();
            gbc_restartButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_restartButton.insets = new Insets(0, 0, 5, 0);
            gbc_restartButton.gridx = 1;
            gbc_restartButton.gridy = 4;
            this.panel.add(this.restartButton, gbc_restartButton);
        }
        {
            this.progressBar = new JProgressBar();
            final GridBagConstraints gbc_progressBar = new GridBagConstraints();
            gbc_progressBar.fill = GridBagConstraints.BOTH;
            gbc_progressBar.insets = new Insets(0, 0, 0, 5);
            gbc_progressBar.gridx = 0;
            gbc_progressBar.gridy = 5;
            this.panel.add(this.progressBar, gbc_progressBar);
        }
        {
            this.deleteButton = new JButton("Delete");
            this.deleteButton.addActionListener(
                (final ActionEvent e) -> doDelete()
                );

            final GridBagConstraints gbc_deleteButton = new GridBagConstraints();
            gbc_deleteButton.fill = GridBagConstraints.BOTH;
            gbc_deleteButton.gridx = 1;
            gbc_deleteButton.gridy = 5;
            this.panel.add(this.deleteButton, gbc_deleteButton);
        }
    }

    private void applySelectionState()
    {
        switch( this.tableModel.getSelectionState() ) {
            case ALL_SELECTED:
                this.deleteButton.setEnabled( true );
                this.selectAllButton.setEnabled( false );
                this.deselectAllButton.setEnabled( true );
               break;

            case AT_LEAST_ONE_FILE_SELECTED:
                this.deleteButton.setEnabled( true );
                this.selectAllButton.setEnabled( true );
                this.deselectAllButton.setEnabled( true );
                break;

            case NONE_SELECTED:
                this.deleteButton.setEnabled( false );
                this.selectAllButton.setEnabled( true );
                this.deselectAllButton.setEnabled( false );
                break;
            }
    }

    private void doDelete()
    {
        setEnabledAll( false );
        this.progressBar.setMinimum( 0 );
        this.progressBar.setMaximum( this.tableModel.getSelectedRowCount() );
        this.progressBar.setEnabled( true );

        new Thread( new DeleteTask( this, this.tableModel, this.progressBar ), "doDelete()" ).start();
    }

    private void setEnabledAll( final boolean b )
    {
        this.restartButton.setEnabled( b );

        if( b ) {
            applySelectionState();
            }
        else {
            this.deleteButton.setEnabled( false );
            this.selectAllButton.setEnabled( false );
            this.deselectAllButton.setEnabled( false );
            }
    }

    public void deleteDone()
    {
        this.progressBar.setEnabled( false );

        setEnabledAll( true );
    }
}
