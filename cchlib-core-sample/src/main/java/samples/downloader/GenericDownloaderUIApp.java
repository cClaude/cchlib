package samples.downloader;

import java.awt.EventQueue;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import samples.downloader.GenericDownloader.AbstractLogger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class GenericDownloaderUIApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( GenericDownloaderUIApp.class );
    protected static final String APP_NAME = "Downloader";

    private ArrayList<GenericDownloaderAppInterface> downloadEntriesTypeList;
    private DefaultComboBoxModel<ProxyEntry> proxyComboBoxModel;
    private GenericDownloaderUIPanel[] downloaderUIPanels;
    private JButton startJButton;
    private JButton stopJButton;
    private JComboBox<ProxyEntry> proxyJComboBox;
    private JComboBox<String> siteJComboBox;
    private JLabel proxyJLabel;
    private JPanel cardsPanel;
    private JPanel contentPane;
    private JTextArea displayJTextArea;
    private StringBuilder _printDisplayStringBuilder = new StringBuilder();

    private final static int    DOWNLOAD_THREAD = 10;

    private class ProxyEntry
    {
        private Proxy proxy;
        private String displayString;

        public ProxyEntry( final Proxy proxy )
        {
            this( proxy, proxy.toString() );
        }

        public ProxyEntry( Proxy proxy, String displayString )
        {
            this.proxy = proxy;
            this.displayString = displayString;
        }

        @Override
        public String toString()
        {
            return displayString;
        }

        public Proxy getProxy()
        {
            return proxy;
        }
    }

    private final AbstractLogger loggerWrapper = new AbstractLogger()
    {
        @Override
        public void warn( String msg )
        {
            printDisplay( "*** WARN: ", msg );
            logger.warn( msg );
        }
        @Override
        public void info( String msg )
        {
            printDisplay( msg );
            logger.info( msg );
        }
        @Override
        public void error( URL url, Throwable cause )
        {
            printDisplay( "*** ERROR: ", url.toExternalForm() );
            logger .error( "Error while download: " + url, cause );
        }
    };
    private JSpinner downloadThreadNumberJSpinner;
    private JLabel downloadThreadNumberJLabel;
    private JScrollPane cardsPanel_JScrollPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                    }
                catch( ClassNotFoundException | InstantiationException
                        | IllegalAccessException
                        | UnsupportedLookAndFeelException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }

                try {
                    GenericDownloaderUIApp frame = new GenericDownloaderUIApp();
                    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
                            GenericDownloaderUIApp.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png"))
                            );
                    frame.setTitle( APP_NAME );
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
        downloadEntriesTypeList = new ArrayList<GenericDownloaderAppInterface>();

        downloadEntriesTypeList.add( new DownloaderSample_gifpal() );
        downloadEntriesTypeList.add( new DownloaderSample1() );
        downloadEntriesTypeList.add( new DownloaderSample2() );

        downloaderUIPanels = new GenericDownloaderUIPanel[ downloadEntriesTypeList.size() ];

        for( int i = 0; i < downloaderUIPanels.length; i++ ) {
            GenericDownloaderAppInterface entry = downloadEntriesTypeList.get( i );

            downloaderUIPanels[ i ] = new GenericDownloaderUIPanel(
                    entry.getSiteName(),
                    entry.getDefaultPageCount(),
                    entry.getMaxPageCount(),
                    entry.getNumberOfPicturesByPage()
                    );
            }

        proxyComboBoxModel = new DefaultComboBoxModel<ProxyEntry>();

        proxyComboBoxModel.addElement( new ProxyEntry( Proxy.NO_PROXY ) );
        proxyComboBoxModel.addElement( new ProxyEntry( new Proxy( Proxy.Type.HTTP, new InetSocketAddress("55.37.80.2", 3128) ) ) );
    }

    /**
     * Create the frame.
     */
    public GenericDownloaderUIApp()
    {
        init();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 600, 300 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 50, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 2.0, 2.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 2.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        {
            cardsPanel_JScrollPane = new JScrollPane();
            GridBagConstraints gbc_cardsPanel_JScrollPane = new GridBagConstraints();
            gbc_cardsPanel_JScrollPane.fill = GridBagConstraints.BOTH;
            gbc_cardsPanel_JScrollPane.gridwidth = 4;
            gbc_cardsPanel_JScrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_cardsPanel_JScrollPane.gridx = 0;
            gbc_cardsPanel_JScrollPane.gridy = 1;
            contentPane.add(cardsPanel_JScrollPane, gbc_cardsPanel_JScrollPane);
            cardsPanel = new JPanel();
            cardsPanel_JScrollPane.setViewportView(cardsPanel);
            cardsPanel.setLayout(new CardLayout(0, 0));
        }

        {

            for( int i = 0; i < downloaderUIPanels.length; i++ ) {
                cardsPanel.add( downloaderUIPanels[ i ], downloaderUIPanels[ i ].getSiteName() );
                }
        }
        {
            siteJComboBox = new JComboBox<String>();
            siteJComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event)
                {
                    CardLayout cl = (CardLayout)(cardsPanel.getLayout());
                    cl.show(cardsPanel, (String)event.getItem());
                }
            });
            for( GenericDownloaderUIPanel p : downloaderUIPanels ) {
                siteJComboBox.addItem( p.getSiteName() );
                }

            GridBagConstraints gbc_siteJComboBox = new GridBagConstraints();
            gbc_siteJComboBox.gridwidth = 4;
            gbc_siteJComboBox.insets = new Insets(0, 0, 5, 0);
            gbc_siteJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_siteJComboBox.gridx = 0;
            gbc_siteJComboBox.gridy = 0;
            contentPane.add(siteJComboBox, gbc_siteJComboBox);
        }
        {
            proxyJLabel = new JLabel("Proxy");
            GridBagConstraints gbc_proxyJLabel = new GridBagConstraints();
            gbc_proxyJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJLabel.anchor = GridBagConstraints.EAST;
            gbc_proxyJLabel.gridx = 0;
            gbc_proxyJLabel.gridy = 2;
            contentPane.add(proxyJLabel, gbc_proxyJLabel);
        }
        {
            proxyJComboBox = new JComboBox<>(proxyComboBoxModel);
            proxyJLabel.setLabelFor(proxyJComboBox);
            GridBagConstraints gbc_proxyJComboBox = new GridBagConstraints();
            gbc_proxyJComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_proxyJComboBox.gridx = 1;
            gbc_proxyJComboBox.gridy = 2;
            contentPane.add(proxyJComboBox, gbc_proxyJComboBox);
        }
        {
            downloadThreadNumberJLabel = new JLabel("Download thread number");
            downloadThreadNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gbc_downloadThreadNumberJLabel = new GridBagConstraints();
            gbc_downloadThreadNumberJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_downloadThreadNumberJLabel.gridx = 2;
            gbc_downloadThreadNumberJLabel.gridy = 2;
            contentPane.add(downloadThreadNumberJLabel, gbc_downloadThreadNumberJLabel);
        }
        {
            downloadThreadNumberJSpinner = new JSpinner();
            downloadThreadNumberJLabel.setLabelFor(downloadThreadNumberJSpinner);
            GridBagConstraints gbc_downloadThreadNumberJSpinner = new GridBagConstraints();
            gbc_downloadThreadNumberJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJSpinner.insets = new Insets(0, 0, 5, 0);
            gbc_downloadThreadNumberJSpinner.gridx = 3;
            gbc_downloadThreadNumberJSpinner.gridy = 2;
            contentPane.add(downloadThreadNumberJSpinner, gbc_downloadThreadNumberJSpinner);
        }
        {
            JScrollPane displayJTextArea_JScrollPane = new JScrollPane();
            GridBagConstraints gbc_displayJTextArea_JScrollPane = new GridBagConstraints();
            gbc_displayJTextArea_JScrollPane.fill = GridBagConstraints.BOTH;
            gbc_displayJTextArea_JScrollPane.gridwidth = 4;
            gbc_displayJTextArea_JScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_displayJTextArea_JScrollPane.gridx = 0;
            gbc_displayJTextArea_JScrollPane.gridy = 3;
            contentPane.add(displayJTextArea_JScrollPane, gbc_displayJTextArea_JScrollPane);
            {
                displayJTextArea = new JTextArea();
                displayJTextArea.setEditable(false);
                displayJTextArea_JScrollPane.setViewportView(displayJTextArea);
            }
        }
        {
            startJButton = new JButton("Start");
            startJButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event)
                {
                    if( startJButton.isEnabled() ) {
                        startJButton.setEnabled( false );
                        stopJButton.setEnabled( true );
                        siteJComboBox.setEnabled( false );

                        int index = siteJComboBox.getSelectedIndex();

                        startDownload( downloadEntriesTypeList.get( index ) );
                        }
                }
            });
            GridBagConstraints gbc_startJButton = new GridBagConstraints();
            gbc_startJButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_startJButton.insets = new Insets(0, 0, 0, 5);
            gbc_startJButton.gridx = 0;
            gbc_startJButton.gridy = 4;
            contentPane.add(startJButton, gbc_startJButton);
        }
        {
            stopJButton = new JButton("Stop");
            GridBagConstraints gbc_stopJButton = new GridBagConstraints();
            gbc_stopJButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_stopJButton.gridx = 3;
            gbc_stopJButton.gridy = 4;
            contentPane.add(stopJButton, gbc_stopJButton);
        }
    }

    private void startDownload( final GenericDownloaderAppInterface gdai )
    {
        displayJTextArea.setText( "" );

        final Proxy proxy = proxyComboBoxModel.getElementAt( proxyJComboBox.getSelectedIndex() ).getProxy();

        printDisplay( "Proxy: ", proxy.toString() );

        new Thread( new Runnable() {
            @Override
            public void run()
            {
                DownloaderAppCore instance = new DownloaderAppCore();

                GenericDownloaderAppUIResults gdauir = new GenericDownloaderAppUIResults()
                {
                    @Override
                    public int getDownloadThreadCount()
                    {
                        return DOWNLOAD_THREAD; // FIXME add gadget
                    }
                    @Override
                    public Proxy getProxy()
                    {
                        return proxy;
                    }
                    @Override
                    public AbstractLogger getAbstractLogger()
                    {
                        return loggerWrapper;
                    }
                };

                try {
                    instance.startDownload( gdai, gdauir );
                    }
                catch( NoSuchAlgorithmException e ) {
                    printDisplay( "*** FATAL: ", e.getMessage() );
                    logger.fatal( "fatal", e );
                    }
                catch( ClassNotFoundException e ) {
                    printDisplay( "*** FATAL: ", e.getMessage() );
                    logger.fatal( "fatal", e );
                    }
                catch( IOException e ) {
                    printDisplay( "*** FATAL: ", e.getMessage() );
                    logger.fatal( "fatal", e );
                    }

                startJButton.setEnabled( true );
                stopJButton.setEnabled( false );
                siteJComboBox.setEnabled( true );
            }
        }).start();
    }

    private void printDisplay( String...messages )
    {
        _printDisplayStringBuilder.setLength( 0 );
        _printDisplayStringBuilder.append( displayJTextArea.getText() );
        _printDisplayStringBuilder.append( '\n' );

        for( String str : messages ) {
            _printDisplayStringBuilder.append( str );
            }

        displayJTextArea.setText( _printDisplayStringBuilder.toString() );
        _printDisplayStringBuilder.setLength( 0 );
    }

}
