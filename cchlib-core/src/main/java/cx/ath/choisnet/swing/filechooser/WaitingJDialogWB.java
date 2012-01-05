package cx.ath.choisnet.swing.filechooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//public
class WaitingJDialogWB
    extends JDialog
        implements WaitingDialog, ActionListener
{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JProgressBar jProgressBar;
    private JLabel jLabel;

    /* *
     * Launch the application.
     * /
    public static void main( String[] args )
    {
        try {
            WaitingJDialogWB dialog = new WaitingJDialogWB( null );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }*/

//    /**
//     * Create the dialog.
//     */
//    public WaitingJDialogWB()
//    {
//        this( null );
//    }

    /**
     * Create the dialog.
     */
    public WaitingJDialogWB( final Frame owner )
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
//            {
//                JButton okButton = new JButton( "OK" );
//                okButton.setActionCommand( "OK" );
//                buttonPane.add( okButton );
//                getRootPane().setDefaultButton( okButton );
//            }
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

    public void setText( final String text )
    {
        jLabel.setText( text );
    }

    protected JProgressBar getJProgressBar()
    {
        return jProgressBar;
    }

    public void setIndeterminate( final boolean newValue )
    {
        jProgressBar.setIndeterminate( newValue  );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if( "Cancel".equals( e.getActionCommand() ) ) {
            super.dispose();
            }
    }
}
