package com.googlecode.cchlib.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.annotation.Nonnull;
import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

//NOT public
@SuppressWarnings({
    "squid:MaximumInheritanceDepth" // Because swing
    })
class CustomDialogWB extends JDialog
{
    private static final long serialVersionUID = 2L;

    private static final String ACTION_CMD_COMPONENT  = "actionCmdComponent";
    private static final String CLIENT_PROPERTY_INDEX = "clientPropertyIndex";

    private final JPanel contentPanel = new JPanel();

    private JLabel         jLabelMessage; // TODO We should be able to copy this in clipboard.
    private JScrollPane    scrollPane;
    private JPanel         commandPanel;
    private int            selectedButtonIndex;

    private transient ActionListener actionListener;

    /**
     * Create the dialog.
     *
     * @param parentWindow Parent Window, or null if none
     * @param buttons      Custom buttons to add
     */
    @SuppressWarnings({
        "squid:S1199", "squid:S00117" // Generated code
        })
    public CustomDialogWB(
        final Window            parentWindow,
        @Nonnull
        final AbstractButton[]  buttons
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
            this.scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            getContentPane().add(this.scrollPane, gbc_scrollPane);
            this.scrollPane.setViewportView(this.contentPanel);
            this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.contentPanel.setLayout(new BorderLayout());
            {
                this.jLabelMessage = new JLabel();
                this.contentPanel.add(this.jLabelMessage);
            }
        }
        {
            this.commandPanel = new JPanel();
            final GridBagConstraints gbc_commandPanel = new GridBagConstraints();
            gbc_commandPanel.fill = GridBagConstraints.BOTH;
            gbc_commandPanel.gridx = 0;
            gbc_commandPanel.gridy = 1;
            getContentPane().add(this.commandPanel, gbc_commandPanel);
            this.commandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            for( int i = 0; i < buttons.length; i++ ) {
                final AbstractButton b = buttons[ i ];
                this.commandPanel.add( b );
                setClientPropertyIndex( b, i );
                b.setActionCommand( ACTION_CMD_COMPONENT );
                b.addActionListener( getActionListener() );
                }
        }
    }

    protected JLabel getJLabelMessage()
    {
        return this.jLabelMessage;
    }

    protected void setMessage( final String message )
    {
        getJLabelMessage().setText( message );
    }

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
            this.actionListener = this::actionPerformedActionListener;
        }

        return this.actionListener;
    }

    private void actionPerformedActionListener( final ActionEvent event )
    {
        if( ACTION_CMD_COMPONENT.equals( event.getActionCommand() ) ) {
            final AbstractButton button = AbstractButton.class.cast( event.getSource() );

            this.selectedButtonIndex = getClientPropertyIndex( button );

            closeDisposeDialog();
            }
    }

    private static void setClientPropertyIndex( final AbstractButton button, final int index )
    {
        button.putClientProperty( CLIENT_PROPERTY_INDEX, Integer.valueOf( index ) );
    }

    private static int getClientPropertyIndex( final AbstractButton button )
    {
        return Integer.class.cast(
                button.getClientProperty( CLIENT_PROPERTY_INDEX )
                ).intValue();
    }
}
