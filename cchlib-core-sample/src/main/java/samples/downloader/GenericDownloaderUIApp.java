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
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.net.download.DownloadIOException;
import com.googlecode.cchlib.net.download.DownloadURL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import java.awt.FlowLayout;

/**
 * Application starting class
 */
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
    private JLabel downloadThreadNumberJLabel;
    private JLabel proxyJLabel;
    private JMenu mnFile;
    private JMenuBar menuBar;
    private JMenuItem mntmQuit;
    private JPanel cardsPanel;
    private JPanel contentPane;
    private JPanel panel;
    private JProgressBar displayJProgressBar;
    private JScrollPane cardsPanel_JScrollPane;
    private JSpinner downloadThreadNumberJSpinner;
    private JTextArea displayJTextArea;
    private SpinnerNumberModel downloadThreadNumberSpinnerModel;
    private StringBuilder _printDisplayStringBuilder = new StringBuilder();

    private static final int DOWNLOAD_THREAD_NUMBER_DEFAULT = 10;
    private static final int DOWNLOAD_THREAD_NUMBER_MAX = 50;

    private class ProxyEntry
    {
        private Proxy proxy;
        private String displayString;

        public ProxyEntry( String hostname, int port )
        {
            this( new Proxy( Proxy.Type.HTTP, new InetSocketAddress( hostname, port ) ) );
        }

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

    private final LoggerListener loggerWrapper = new LoggerListener()
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
        public void error( URL url, File file, Throwable cause )
        {
            printDisplay( "*** ERROR: ", url.toExternalForm() );

            logger .error( "Error while download: " + url + " to file: " + file, cause );
        }
        @Override
        public void downloadStateInit( DownloadStateEvent event )
        {
            displayJProgressBar.setMinimum( 0 );
            displayJProgressBar.setMaximum( event.getDownloadListSize() );
            displayJProgressBar.setValue( 0 );
            displayJProgressBar.setEnabled( true );
            displayJProgressBar.setIndeterminate( false );
            displayJProgressBar.setStringPainted( true );

            logger.info( "init :" + event.getDownloadListSize() );
        }
        @Override
        public void downloadStateChange( DownloadStateEvent event )
        {
            displayJProgressBar.setValue( event.getDownloadListSize() );

            logger.info( "update :" + event.getDownloadListSize() );
        }
        @Override
        public void downloadFail( DownloadIOException e )
        {
            // TODO Auto-generated method stub

        }
        @Override
        public void downloadStart( DownloadURL url )
        {
            // TODO Auto-generated method stub

        }
        @Override
        public void downloadDone( DownloadURL url )
        {
            // TODO Auto-generated method stub

        }
        @Override
        public void downloadCantRename( DownloadURL dURL, File tmpFile,
                File expectedCacheFile )
        {
            // TODO Auto-generated method stub

        }
        @Override
        public void downloadStored( DownloadURL dURL )
        {
            // TODO Auto-generated method stub

        }
    };

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

            downloaderUIPanels[ i ] = new GenericDownloaderUIPanel( entry );
            }

        proxyComboBoxModel = new DefaultComboBoxModel<ProxyEntry>();

        proxyComboBoxModel.addElement( new ProxyEntry( Proxy.NO_PROXY ) );
        // FIXME: Provide a better way to store this !
        proxyComboBoxModel.addElement( new ProxyEntry( "55.37.80.2", 3128 ) );
    }

    /**
     * Create the frame.
     */
    public GenericDownloaderUIApp()
    {
        init();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 600, 300 );
        {
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            {
                mnFile = new JMenu("File");
                menuBar.add(mnFile);
                {
                    mntmQuit = new JMenuItem("Quit");
                    mnFile.add(mntmQuit);
                }
            }
        }
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 50, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 2.0, 2.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 2.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        {
            cardsPanel_JScrollPane = new JScrollPane();
            GridBagConstraints gbc_cardsPanel_JScrollPane = new GridBagConstraints();
            gbc_cardsPanel_JScrollPane.fill = GridBagConstraints.BOTH;
            gbc_cardsPanel_JScrollPane.gridwidth = 4;
            gbc_cardsPanel_JScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_cardsPanel_JScrollPane.gridx = 0;
            gbc_cardsPanel_JScrollPane.gridy = 1;
            contentPane.add(cardsPanel_JScrollPane, gbc_cardsPanel_JScrollPane);
            cardsPanel = new JPanel();
            cardsPanel_JScrollPane.setViewportView(cardsPanel);
            cardsPanel.setLayout(new CardLayout(0, 0));

            for( int i = 0; i < downloaderUIPanels.length; i++ ) {
                cardsPanel.add( downloaderUIPanels[ i ], downloaderUIPanels[ i ].getGenericDownloaderAppInterface().getSiteName() );
                }
        }
        {
            siteJComboBox = new JComboBox<String>();
            siteJComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event)
                {
                    CardLayout cl = (CardLayout)(cardsPanel.getLayout());
                    String panelName = String.class.cast( event.getItem() );
                    cl.show(cardsPanel, panelName );

                    for( int i = 0; i < downloaderUIPanels.length; i++ ) {
                        final GenericDownloaderAppInterface c = downloaderUIPanels[ i ].getGenericDownloaderAppInterface();
                        if( c.getSiteName().equals( panelName ) ) {
                            //xxxxx
                            break;
                            }
                        }
                }
            });
            for( GenericDownloaderUIPanel p : downloaderUIPanels ) {
                siteJComboBox.addItem( p.getGenericDownloaderAppInterface().getSiteName() );
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
            downloadThreadNumberSpinnerModel = new SpinnerNumberModel( DOWNLOAD_THREAD_NUMBER_DEFAULT, 1, DOWNLOAD_THREAD_NUMBER_MAX, 1 );
            downloadThreadNumberJSpinner = new JSpinner( downloadThreadNumberSpinnerModel );
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
            displayJProgressBar = new JProgressBar();
            GridBagConstraints gbc_displayJProgressBar = new GridBagConstraints();
            gbc_displayJProgressBar.fill = GridBagConstraints.HORIZONTAL;
            gbc_displayJProgressBar.gridwidth = 4;
            gbc_displayJProgressBar.insets = new Insets(0, 0, 5, 0);
            gbc_displayJProgressBar.gridx = 0;
            gbc_displayJProgressBar.gridy = 4;
            contentPane.add(displayJProgressBar, gbc_displayJProgressBar);
        }
        {
            panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 0, 5);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 5;
            contentPane.add(panel, gbc_panel);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            startJButton = new JButton("Start");
            panel.add(startJButton);
            startJButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event)
                {
                    if( startJButton.isEnabled() ) {
                        startDownload();
                        }
                }
            });
            {
                stopJButton = new JButton("Stop");
                stopJButton.setEnabled(false);
                panel.add(stopJButton);
            }
        }
    }

    private void startDownload()
    {
        // UI disable/enable
        startJButton.setEnabled( false );
        stopJButton.setEnabled( true );
        siteJComboBox.setEnabled( false );

        displayJTextArea.setText( "" );
        displayJProgressBar.setIndeterminate( true );
        displayJProgressBar.setEnabled( true );

        // UI get values
        final GenericDownloaderAppInterface gdai  = downloadEntriesTypeList.get( siteJComboBox.getSelectedIndex() );
        final GenericDownloaderUIPanel      panel = downloaderUIPanels[ siteJComboBox.getSelectedIndex() ];
        final Proxy proxy = proxyComboBoxModel.getElementAt( proxyJComboBox.getSelectedIndex() ).getProxy();
        final int downloadThreadNumber = this.downloadThreadNumberSpinnerModel.getNumber().intValue();

        logger.info( "proxy: " + proxy.toString() );
        logger.info( "downloadThreadNumber: " + downloadThreadNumber );
        logger.info( "CacheRelativeDirectoryCacheName: " + gdai.getCacheRelativeDirectoryCacheName() );
        logger.info( "PageScanCount: " + panel.getGenericDownloaderAppInterface().getPageCount() );

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
                        return downloadThreadNumber;
                    }
                    @Override
                    public Proxy getProxy()
                    {
                        return proxy;
                    }
                    @Override
                    public LoggerListener getAbstractLogger()
                    {
                        return loggerWrapper;
                    }
                };

                try {
                    instance.startDownload( gdai, gdauir );
                    }
                catch( Exception e ) {
                    printDisplay( "*** FATAL: ", e.getMessage() );
                    logger.fatal( "fatal", e );
                    }

                startJButton.setEnabled( true );
                stopJButton.setEnabled( false );
                siteJComboBox.setEnabled( true );

                displayJProgressBar.setIndeterminate( false );
                displayJProgressBar.setEnabled( false );
            }
        }).start();
    }


    private static final int MAX_DISPLAY_TEXT_LENGTH = 16 * 1024;
    private void printDisplay( String...messages )
    {
        _printDisplayStringBuilder.setLength( 0 );

        int size = displayJTextArea.getText().length();

        if( size > MAX_DISPLAY_TEXT_LENGTH ) {
            int beginIndex = size - MAX_DISPLAY_TEXT_LENGTH;

            beginIndex = displayJTextArea.getText().indexOf( '\n', beginIndex );

            _printDisplayStringBuilder.append( displayJTextArea.getText().substring( beginIndex ) );
            }
        else {
            _printDisplayStringBuilder.append( displayJTextArea.getText() );
            }

        _printDisplayStringBuilder.append( '\n' );

        for( String str : messages ) {
            _printDisplayStringBuilder.append( str );
            }

        displayJTextArea.setText( _printDisplayStringBuilder.toString() );
        _printDisplayStringBuilder.setLength( 0 );
    }

}
