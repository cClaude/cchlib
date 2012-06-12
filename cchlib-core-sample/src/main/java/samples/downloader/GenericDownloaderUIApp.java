package samples.downloader;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.util.ArrayList;

public class GenericDownloaderUIApp extends JFrame
{
    private static final long serialVersionUID = 1L;

    //private String[]                   sitesName = { "fake" };
    private GenericDownloaderUIPanel[] downloaderUIPanels;

    private JPanel contentPane;
    private JComboBox<String> siteComboBox;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    GenericDownloaderUIApp frame = new GenericDownloaderUIApp();
                    frame.setVisible( true );
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    private void init()
    {
        ArrayList<GenericDownloaderAppInterface> entries = new ArrayList<GenericDownloaderAppInterface>();

        entries.add( new DownloaderSample_gifpal() );
        entries.add( new DownloaderSample1() );
        entries.add( new DownloaderSample2() );

        //sitesName          = new String[ entries.size() ];
        downloaderUIPanels = new GenericDownloaderUIPanel[ entries.size() ];

        for( int i = 0; i < downloaderUIPanels.length; i++ ) {
            GenericDownloaderAppInterface entry = entries.get( i );

            //sitesName[ i ] = entry.getSiteName();
            downloaderUIPanels[ i ] = new GenericDownloaderUIPanel(
                    entry.getSiteName(),
                    entry.getDefaultPageCount(),
                    entry.getMaxPageCount(),
                    entry.getNumberOfPicturesByPage()
                    );
            }
    }

    /**
     * Create the frame.
     */
    public GenericDownloaderUIApp()
    {
        init();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        siteComboBox = new JComboBox<String>();
        for( GenericDownloaderUIPanel p : downloaderUIPanels ) {
            siteComboBox.addItem( p.getSiteName() );
            }

        GridBagConstraints gbc_siteComboBox = new GridBagConstraints();
        gbc_siteComboBox.insets = new Insets(0, 0, 5, 0);
        gbc_siteComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_siteComboBox.gridx = 0;
        gbc_siteComboBox.gridy = 0;
        contentPane.add(siteComboBox, gbc_siteComboBox);

        JPanel cardsPanel = new JPanel();
        GridBagConstraints gbc_cardsPanel = new GridBagConstraints();
        gbc_cardsPanel.fill = GridBagConstraints.BOTH;
        gbc_cardsPanel.gridx = 0;
        gbc_cardsPanel.gridy = 1;
        contentPane.add(cardsPanel, gbc_cardsPanel);
        cardsPanel.setLayout(new CardLayout(0, 0));

        for( int i = 0; i < downloaderUIPanels.length; i++ ) {
            cardsPanel.add( downloaderUIPanels[ i ] );
            }
    }

}
