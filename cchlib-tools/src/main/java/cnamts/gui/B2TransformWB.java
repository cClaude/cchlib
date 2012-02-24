package cnamts.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class B2TransformWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    public static final String ACTIONCMD_SELECT_SOURCE = "ACTIONCMD_SELECT_SOURCE";
    public static final String ACTIONCMD_CONVERT_FILE = "ACTIONCMD_CONVERT_FILE";
    private JPanel contentPane;
    private JLabel jLabelSource;
    private JLabel jLabelDestination;
    private JLabel jLabelCurrentState;

    /**
     * Create the frame.
     */
    public B2TransformWB()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 121);
        //setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JButton jButtonSource = new JButton( "Source" );
        jButtonSource.setActionCommand( ACTIONCMD_SELECT_SOURCE );
        jButtonSource.addActionListener( getActionListener() );
        GridBagConstraints gbc_jButtonSource = new GridBagConstraints();
        gbc_jButtonSource.fill = GridBagConstraints.BOTH;
        gbc_jButtonSource.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonSource.gridx = 0;
        gbc_jButtonSource.gridy = 0;
        contentPane.add( jButtonSource, gbc_jButtonSource );

        jLabelSource = new JLabel("<< choisir un fichier B2 source >>");
        GridBagConstraints gbc_jLabelSource = new GridBagConstraints();
        jLabelSource.putClientProperty( File.class, null );
        gbc_jLabelSource.gridwidth = 2;
        gbc_jLabelSource.anchor = GridBagConstraints.EAST;
        gbc_jLabelSource.fill = GridBagConstraints.VERTICAL;
        gbc_jLabelSource.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelSource.gridx = 1;
        gbc_jLabelSource.gridy = 0;
        contentPane.add( jLabelSource, gbc_jLabelSource);

        JButton jButtonDestination = new JButton("Destination");
        GridBagConstraints gbc_jButtonDestination = new GridBagConstraints();
        gbc_jButtonDestination.fill = GridBagConstraints.BOTH;
        gbc_jButtonDestination.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonDestination.gridx = 0;
        gbc_jButtonDestination.gridy = 1;
        contentPane.add(jButtonDestination, gbc_jButtonDestination);

        jLabelDestination = new JLabel("<< choisir un fichier destination>>");
        GridBagConstraints gbc_jLabelDestination = new GridBagConstraints();
        gbc_jLabelDestination.gridwidth = 2;
        gbc_jLabelDestination.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDestination.anchor = GridBagConstraints.EAST;
        gbc_jLabelDestination.fill = GridBagConstraints.VERTICAL;
        gbc_jLabelDestination.gridx = 1;
        gbc_jLabelDestination.gridy = 1;
        contentPane.add(jLabelDestination, gbc_jLabelDestination);

        JButton jButtonConvert = new JButton("Convert");
        jButtonConvert.setActionCommand( ACTIONCMD_CONVERT_FILE );
        jButtonConvert.addActionListener( getActionListener() );

        jLabelCurrentState = new JLabel();
        GridBagConstraints gbc_jLabelCurrentState = new GridBagConstraints();
        gbc_jLabelCurrentState.gridwidth = 2;
        gbc_jLabelCurrentState.insets = new Insets(0, 0, 0, 5);
        gbc_jLabelCurrentState.gridx = 0;
        gbc_jLabelCurrentState.gridy = 2;
        contentPane.add(jLabelCurrentState, gbc_jLabelCurrentState);
        GridBagConstraints gbc_jButtonConvert = new GridBagConstraints();
        gbc_jButtonConvert.gridx = 2;
        gbc_jButtonConvert.gridy = 2;
        contentPane.add(jButtonConvert, gbc_jButtonConvert);
    }

    protected abstract ActionListener getActionListener();

    protected void setSourceFile( final File file )
    {
        jLabelSource.putClientProperty( File.class, file );
        jLabelSource.setText( file.getPath() );
    }

    protected File getSourceFile()
    {
        return File.class.cast( jLabelSource.getClientProperty( File.class ) );
    }

    protected void setCurrentState( final String message )
    {
        jLabelCurrentState.setText( message );
    }
}
