package com.googlecode.cchlib.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/* not public */
class CustomDialogWB extends JDialog
{
    private static final long serialVersionUID = 1L;
    private static final String ACTION_CMD_COMPONENT = "ACTION_CMD_COMPONENT";
    private static final String CLIENT_PROPERTY_INDEX = "CLIENT_PROPERTY_INDEX";
    private final JPanel contentPanel = new JPanel();
    private JLabel jLabelMessage;
    private JScrollPane scrollPane;
    private JPanel commandPanel;
    private ActionListener actionListener;
    private int selectedButtonIndex;

    /**
     * Create the dialog.
     *
     * @param parentWindow
     * @param addCancelButton
     */
    public CustomDialogWB(
        final Window            parentWindow,
        final AbstractButton[]  abstractButtons
        )
    {
        super( parentWindow );

        setSize( 450, 300);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 33, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        {
            scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            getContentPane().add(scrollPane, gbc_scrollPane);
            scrollPane.setViewportView(contentPanel);
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPanel.setLayout(new BorderLayout());
            {
                jLabelMessage = new JLabel();
                contentPanel.add(jLabelMessage);
            }
        }
        {
            commandPanel = new JPanel();
            final GridBagConstraints gbc_commandPanel = new GridBagConstraints();
            gbc_commandPanel.fill = GridBagConstraints.BOTH;
            gbc_commandPanel.gridx = 0;
            gbc_commandPanel.gridy = 1;
            getContentPane().add(commandPanel, gbc_commandPanel);
            commandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            for( int i = 0; i < abstractButtons.length; i++ ) {
                final AbstractButton b = abstractButtons[ i ];
                commandPanel.add( b );
                b.putClientProperty( CLIENT_PROPERTY_INDEX, Integer.valueOf( i ) );
                b.setActionCommand( ACTION_CMD_COMPONENT );
                b.addActionListener( getActionListener() );
                }
        }
    }

    /**
     *
     * @return
     */
    protected JLabel getJLabelMessage()
    {
        return jLabelMessage;
    }

    /**
     *
     * @return
     */
    protected int getSelectedButtonIndex()
    {
        return this.selectedButtonIndex;
    }

    private void closeDisposeDialog()
    {
        CustomDialogWB.this.setVisible( false );
        CustomDialogWB.this.dispose();
    }

    private ActionListener getActionListener()
    {
        if( this.actionListener == null ) {
            this.actionListener = e -> {
                if( ACTION_CMD_COMPONENT.equals( e.getActionCommand() ) ) {
                    final AbstractButton b = AbstractButton.class.cast( e.getSource() );
                    selectedButtonIndex = Integer.class.cast( b.getClientProperty( CLIENT_PROPERTY_INDEX ) ).intValue();

                    closeDisposeDialog();
                    }
            };
            }

        return this.actionListener;
    }
}
