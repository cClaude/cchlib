package com.googlecode.cchlib.swing;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/* not public */
class CustomDialogWB extends JDialog
{
    private static final long serialVersionUID = 1L;
    private static final String ACTION_CMD_OK = "OK";
    private static final String ACTION_CMD_CANCEL = "Cancel";
    private final JPanel contentPanel = new JPanel();
    private JLabel jLabelMessage;
    private JButton jButtonOk;
    private JButton jButtonCancel;
    private ActionListener actionListener;
    private JScrollPane scrollPane;

//    /**
//     * Test only
//     */
//    public static void main( String[] args )
//    {
//        try {
//            Frame           parentFrame = null;
//            String          title       = "<<title>>";
//            String          message     = "<html>&lt;&lt;<b>HTML</b> message&gt;&gt;</html>";
//            String          okText      = "<<ok>>";
//            String          cancelText  = "<<cancel>>";
//            CustomDialogWB  dialog      = new CustomDialogWB(
//                    parentFrame,
//                    title,
//                    message,
//                    okText,
//                    cancelText
//                    );
//            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            dialog.setVisible(true);
//            }
//        catch( Exception e ) {
//            e.printStackTrace();
//            }
//    }

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
     *
     * @param parentFrame
     * @param title
     * @param message
     * @param okText
     * @param cancelText
     * @wbp.parser.constructor
     */
    public CustomDialogWB(
        final Window      parentWindow,
        final String    title,
        final String    message,
        final String    okText,
        final String    cancelText

        )
    {
        this( parentWindow, true, title, message );

        this.setJButtonOk( okText );
        this.setJButtonCancel( cancelText );
    }

    /**
     * Create the dialog.
     *
     * @param parentWindow
     * @param addCancelButton
     * @param title
     * @param message
     */
    public CustomDialogWB(
        final Window      parentWindow,
        final boolean   addCancelButton,
        final String    title,
        final String    message
        )
    {
        this( parentWindow, addCancelButton );

        setTitle( title );
        this.jLabelMessage.setText( message );
    }

    /**
     * Create the dialog.
     *
     * @param parentWindow
     * @param addCancelButton
     */
    public CustomDialogWB(
        final Window      parentWindow,
        final boolean   addCancelButton
        )
    {
        super( parentWindow );

        setSize( 450, 300);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 33, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        {
            scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridwidth = 3;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
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

        // CANCEL
        // $hide>>$
        if( addCancelButton )
        // $hide<<$
        {
            jButtonCancel = new JButton();
            jButtonCancel.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonCancel.gridx = 2;
            gbc_jButtonCancel.gridy = 1;
            getContentPane().add(jButtonCancel, gbc_jButtonCancel);
            jButtonCancel.setActionCommand( ACTION_CMD_CANCEL );
            }

        // OK
        {
            jButtonOk = new JButton();
            jButtonOk.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButtonOk = new GridBagConstraints();
            gbc_jButtonOk.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonOk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonOk.gridx = 1;
            gbc_jButtonOk.gridy = 1;
            getContentPane().add(jButtonOk, gbc_jButtonOk);
            jButtonOk.setActionCommand( ACTION_CMD_OK );
            getRootPane().setDefaultButton(jButtonOk);
        }
    }

    public JLabel getJLabelMessage()
    {
        return jLabelMessage;
    }

    public JButton getJButtonOk()
    {
        return jButtonOk;
    }

    public void setJButtonOk( final String okText )
    {
        jButtonOk.setText( okText );
    }

    public JButton getJButtonCancel()
    {
        return jButtonCancel;
    }

    public void setJButtonCancel( final String cancelText )
    {
        jButtonCancel.setText( cancelText );
    }

}
