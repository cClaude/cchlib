package alpha.com.googlecode.cchlib.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

/*not public*/
class MessageDialog extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final String ACTIONCMD_OK = "OK";
    private final JPanel contentPanel = new JPanel();
    private JButton jButtonOK;

//    public static void main( String[] args )
//    {
//        try {
//            MessageDialog dialog = new MessageDialog(
//                "mytest title",
//                "<HTML>HTML test<BR/>for dialog content</HTML>",
//                "MyOk"
//                );
//            dialog.addDefaultListeners();
//            dialog.setVisible( true );
//            }
//        catch( Exception e ) {
//            e.printStackTrace();
//            }
//    }

    /**
     * Create the dialog providing a title, a message content (could
     * be HTML) and a text for button.
     *
     * @param title
     * @param message
     * @param okButtonText
     *
     * @wbp.parser.constructor
     */
    public MessageDialog(
        final String title,
        final String message,
        final String okButtonText
        )
    {
        setTitle( title );
        setSize( 450, 300 ); // Provide a default size
        getContentPane().setLayout( new BorderLayout() );
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( contentPanel, BorderLayout.CENTER );
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JScrollPane scrollPane = new JScrollPane();
            contentPanel.add(scrollPane);
            {
                JPanel panel = new JPanel();
                scrollPane.setViewportView(panel);
                panel.setLayout(new BorderLayout(0, 0));
                {
                    JLabel lblNewLabel = new JLabel( message );
                    panel.add(lblNewLabel);
                }
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
            getContentPane().add( buttonPane, BorderLayout.SOUTH );
            {
                jButtonOK = new JButton( okButtonText );
                jButtonOK.setActionCommand( ACTIONCMD_OK );
                buttonPane.add( jButtonOK );
                getRootPane().setDefaultButton( jButtonOK );
            }
        }
    }

    public JButton getJButtonOK()
    {
        return jButtonOK;
    }

    public void setJButtonOK( final String okText)
    {
        jButtonOK.setText( okText );
    }

    /**
     *
     */
    public void addDefaultListeners()
    {
        MessageDialog.this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );

        jButtonOK.addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if( ACTIONCMD_OK.equals( e.getActionCommand() ) ) {
            MessageDialog.this.setVisible( false );
            MessageDialog.this.dispose();
            }
    }
}
