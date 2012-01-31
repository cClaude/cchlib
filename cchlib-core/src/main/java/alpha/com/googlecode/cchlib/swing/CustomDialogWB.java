package alpha.com.googlecode.cchlib.swing;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomDialogWB extends JDialog
{
    private static final long serialVersionUID = 1L;
    private static final String ACTION_CMD_OK = "OK";
    private static final String ACTION_CMD_CANCEL = "Cancel";
    private final JPanel contentPanel = new JPanel();
    private JLabel jLabelMessage;
    private JButton jButtonOk;
    private JButton jButtonCancel;
    private ActionListener actionListener;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            CustomDialogWB dialog = new CustomDialogWB(
                "message test"
                );
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CustomActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if( ACTION_CMD_OK.equals( e.getActionCommand() ) ) {
                closeDisposeDialog();
                }
            else if( ACTION_CMD_CANCEL.equals( e.getActionCommand() ) ) {
                closeDisposeDialog();
            }
        }

        /**
         *
         */
        public void closeDisposeDialog()
        {
            CustomDialogWB.this.setVisible( false );
            CustomDialogWB.this.dispose();
        }
    }

    private ActionListener getActionListener()
    {
        if( this.actionListener == null ) {
            this.actionListener = new CustomActionListener();
            }
        return this.actionListener;
    }

    /**
     * Create the dialog.
     */
    public CustomDialogWB(
        final String message
        )
    {
        this();

        this.jLabelMessage.setText( message );
    }

    /**
     * Create the dialog.
     */
    public CustomDialogWB()
    {
        //setBounds(100, 100, 450, 300);
        setSize( 450, 300);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{240, 33, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc_contentPanel = new GridBagConstraints();
        gbc_contentPanel.gridwidth = 3;
        gbc_contentPanel.fill = GridBagConstraints.BOTH;
        gbc_contentPanel.insets = new Insets(0, 0, 5, 0);
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 0;
        getContentPane().add(contentPanel, gbc_contentPanel);
        contentPanel.setLayout(new BorderLayout());
        {
            jLabelMessage = new JLabel();
            contentPanel.add(jLabelMessage);
        }
        {
            jButtonCancel = new JButton("Cancel");
            GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonCancel.gridx = 2;
            gbc_jButtonCancel.gridy = 1;
            getContentPane().add(jButtonCancel, gbc_jButtonCancel);
            jButtonCancel.setActionCommand( ACTION_CMD_CANCEL );
        }
        {
            jButtonOk = new JButton("OK");
            jButtonOk.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButtonOk = new GridBagConstraints();
            gbc_jButtonOk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonOk.gridx = 1;
            gbc_jButtonOk.gridy = 1;
            getContentPane().add(jButtonOk, gbc_jButtonOk);
            jButtonOk.setActionCommand( ACTION_CMD_OK );
            getRootPane().setDefaultButton(jButtonOk);
        }
    }
    public JLabel getJLabelMessage() {
        return jLabelMessage;
    }
    public JButton getJButtonOk() {
        return jButtonOk;
    }
    public JButton getJButtonCancel() {
        return jButtonCancel;
    }
}
