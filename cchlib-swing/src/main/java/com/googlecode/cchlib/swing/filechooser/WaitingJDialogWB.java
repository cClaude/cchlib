package com.googlecode.cchlib.swing.filechooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Not public
class WaitingJDialogWB
    extends JDialog
        implements ActionListener
{
    private static final long serialVersionUID = 3L;
    private final JPanel contentPanel = new JPanel();
    private JProgressBar jProgressBar;
    private JLabel jLabel;

    /**
     * Create the dialog.
     *
     * @param owner Parent {@link Window} for this dialog.
     */
    public WaitingJDialogWB(
            final Window owner
            )
    {
        super( owner );

        setBounds( 100, 100, 283, 131 );
        getContentPane().setLayout( new BorderLayout() );
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( contentPanel, BorderLayout.CENTER );
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            jLabel = new JLabel();
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(jLabel);
        }
        {
            jProgressBar = new JProgressBar();
            jProgressBar.setIndeterminate(true);
            contentPanel.add(jProgressBar, BorderLayout.SOUTH);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
            getContentPane().add( buttonPane, BorderLayout.SOUTH );
            {
                JButton cancelButton = new JButton( "Cancel" );
                cancelButton.addActionListener( this );
                cancelButton.setActionCommand( "Cancel" );
                buttonPane.add( cancelButton );
            }
        }
    }

    protected JLabel getJLabel()
    {
        return jLabel;
    }

    protected void setText( final String text )
    {
        jLabel.setText( text );
    }

    protected JProgressBar getJProgressBar()
    {
        return jProgressBar;
    }

    protected void setIndeterminate( final boolean newValue )
    {
        jProgressBar.setIndeterminate( newValue );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if( "Cancel".equals( e.getActionCommand() ) ) {
            super.dispose();
            }
    }
}
