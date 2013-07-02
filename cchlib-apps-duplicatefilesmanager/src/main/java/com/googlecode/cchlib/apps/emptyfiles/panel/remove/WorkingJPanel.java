package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.apps.emptyfiles.tasks.DeleteTask;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.apache.log4j.Logger;

@I18nName("emptyfiles.WorkingJPanel")
public class WorkingJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( WorkingJPanel.class );
    private JTable table;
    private JPanel panel;
    private JLabel messageLabel;
    private JProgressBar progressBar;
    private JButton deleteButton;
    private JButton selectAllButton;
    private JButton deselectAllButton;
    private WorkingTableModel tableModel;
    private JScrollPane scrollPane;
    private JButton restartButton;

    /**
     * Create the panel.
     */
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
            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[]{337, 0, 0};
            gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 25, 0};
            gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
            this.panel.setLayout(gbl_panel);
        }
        {
            this.messageLabel = new JLabel("Select files to delete");
            this.messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            GridBagConstraints gbc_messageLabel = new GridBagConstraints();
            gbc_messageLabel.gridwidth = 2;
            gbc_messageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_messageLabel.insets = new Insets(0, 0, 5, 0);
            gbc_messageLabel.gridx = 0;
            gbc_messageLabel.gridy = 0;
            this.panel.add(this.messageLabel, gbc_messageLabel);
        }
        {
            this.scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
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
                tableModel.addTableModelListener( new TableModelListener() {
                    @Override
                    public void tableChanged( TableModelEvent event )
                    {
                        if( logger.isTraceEnabled() ) {
                            logger.trace( "tableChanged :" + event.getSource() );
                            }

                        applySelectionState();
                    }} );
            }
        }
        {
            this.deselectAllButton = new JButton("Deselect All");
            this.deselectAllButton.setIcon( removeEmptyFilesJPanel.getResources().getDeselectAllIcon() );
            this.deselectAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableModel.doUnselectAll();
                }
            });

            GridBagConstraints gbc_deselectAllButton = new GridBagConstraints();
            gbc_deselectAllButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_deselectAllButton.insets = new Insets(0, 0, 5, 0);
            gbc_deselectAllButton.gridx = 1;
            gbc_deselectAllButton.gridy = 1;
            this.panel.add(this.deselectAllButton, gbc_deselectAllButton);
        }
        {
            this.selectAllButton = new JButton("Select All");
            this.selectAllButton.setIcon( removeEmptyFilesJPanel.getResources().getSelectAllIcon() );
            this.selectAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableModel.doSelectAll();
                }
            });
            GridBagConstraints gbc_selectAllButton = new GridBagConstraints();
            gbc_selectAllButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_selectAllButton.insets = new Insets(0, 0, 5, 0);
            gbc_selectAllButton.gridx = 1;
            gbc_selectAllButton.gridy = 2;
            this.panel.add(this.selectAllButton, gbc_selectAllButton);
        }
        {
            this.restartButton = new JButton("Restart");
            this.restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeEmptyFilesJPanel.restart();
                }
            });
            GridBagConstraints gbc_restartButton = new GridBagConstraints();
            gbc_restartButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_restartButton.insets = new Insets(0, 0, 5, 0);
            gbc_restartButton.gridx = 1;
            gbc_restartButton.gridy = 4;
            this.panel.add(this.restartButton, gbc_restartButton);
        }
        {
            this.progressBar = new JProgressBar();
            GridBagConstraints gbc_progressBar = new GridBagConstraints();
            gbc_progressBar.fill = GridBagConstraints.BOTH;
            gbc_progressBar.insets = new Insets(0, 0, 0, 5);
            gbc_progressBar.gridx = 0;
            gbc_progressBar.gridy = 5;
            this.panel.add(this.progressBar, gbc_progressBar);
        }
        {
            this.deleteButton = new JButton("Delete");
            this.deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doDelete();
                }
            });
            GridBagConstraints gbc_deleteButton = new GridBagConstraints();
            gbc_deleteButton.fill = GridBagConstraints.BOTH;
            gbc_deleteButton.gridx = 1;
            gbc_deleteButton.gridy = 5;
            this.panel.add(this.deleteButton, gbc_deleteButton);
        }
    }

    protected void applySelectionState()
    {
        switch( tableModel.getSelectionState() ) {
            case ALL_SELECTED:
                deleteButton.setEnabled( true );
                selectAllButton.setEnabled( false );
                deselectAllButton.setEnabled( true );
               break;
               
            case AT_LEAST_ONE_FILE_SELECTED:
                deleteButton.setEnabled( true );
                selectAllButton.setEnabled( true );
                deselectAllButton.setEnabled( true );
                break;
                
            case NONE_SELECTED:
                deleteButton.setEnabled( false );
                selectAllButton.setEnabled( true );
                deselectAllButton.setEnabled( false );
                break;
            }
    }

    private void doDelete()
    {
        setEnabledAll( false );
        this.progressBar.setMinimum( 0 );
        this.progressBar.setMaximum( this.tableModel.getSelectedRowCount() );
        this.progressBar.setEnabled( true );

        new Thread( new DeleteTask( this, tableModel, progressBar ) ).start();
    }

    private void setEnabledAll( boolean b )
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
