package com.googlecode.cchlib.tools.downloader;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException; // $codepro.audit.disable unnecessaryImport
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.tools.downloader.display.table.DisplayTableBuilder;
import com.googlecode.cchlib.tools.downloader.display.table.DisplayTableModel;
import com.googlecode.cchlib.tools.downloader.gdai.tumblr.GDAI_tumblr_com;

/**
 * Application starting class
 */
public class GenericDownloaderUIApp extends JFrame
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( GenericDownloaderUIApp.class );

    protected static final String APP_NAME = "Downloader";

    private static final int DOWNLOAD_THREAD_NUMBER_DEFAULT = 10;
    private static final int DOWNLOAD_THREAD_NUMBER_MAX = 50;
    private static final String ACTION_QUIT = "ACTION_QUIT";
    private static final String ACTION_STOP = "ACTION_STOP";
    private static final String ACTION_START = "ACTION_START";

    private GenericDownloader genericDownloader_useLock;
    /** lock for genericDownloader access */
    private final Object genericDownloaderLock = new Object();
    private ActionListener actionListener;
    private WindowListener windowListener;

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
    private final JPanel contentPane;
    private JPanel panel;
    private JProgressBar displayJProgressBar;
    private JScrollPane cardsPanel_JScrollPane;
    private JSpinner downloadThreadNumberJSpinner;
    private SpinnerNumberModel downloadThreadNumberSpinnerModel;
    private JTable displayJTable;

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( ( ) -> {
            try {
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                }
            catch( ClassNotFoundException | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException e ) {

                LOGGER.fatal( "Can not set LookAndFeel", e );
                }

            try {
                final GenericDownloaderUIApp frame = new GenericDownloaderUIApp();
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
                        GenericDownloaderUIApp.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png"))
                        );
                frame.setTitle( APP_NAME );
                }
            catch( final Exception e ) {
                LOGGER.fatal( "main() exception", e );

                final String title = "GenericDownloaderUIApp";

                DialogHelper.showMessageExceptionDialog( title , e );
                }
        } );
    }


    private void init()
    {
        downloadEntriesTypeList = new ArrayList<GenericDownloaderAppInterface>();

        //downloadEntriesTypeList.add( DownloadI_tumblr_com.createAllEntriesInOnce() );
        downloadEntriesTypeList.add( GDAI_tumblr_com.createAllEntries( this ) );
        //downloadEntriesTypeList.add( DownloadI_tumblr_com.createForHost( "milfgalore" ) );

        downloadEntriesTypeList.add( new DownloadI_senorgif() );
        downloadEntriesTypeList.add( new DownloadI_www_bloggif_com() );
        downloadEntriesTypeList.add( new DownloadI_www_gifgirl_org() );
        downloadEntriesTypeList.add( new DownloadI_www_gifmash_com() );
        downloadEntriesTypeList.add( new DownloadI_www_gifpal_com() );

        downloadEntriesTypeList.add( new DownloadI_www_epins_fr() );

        downloaderUIPanels = new GenericDownloaderUIPanel[ downloadEntriesTypeList.size() ];

        for( int i = 0; i < downloaderUIPanels.length; i++ ) {
            final GenericDownloaderAppInterface entry = downloadEntriesTypeList.get( i );

            downloaderUIPanels[ i ] = new GenericDownloaderUIPanel( entry );
            }

        proxyComboBoxModel = new DefaultComboBoxModel<ProxyEntry>();

        proxyComboBoxModel.addElement( new ProxyEntry( Proxy.NO_PROXY ) );

        for( final ProxyEntry entry : createProxyList() ) {
            proxyComboBoxModel.addElement( entry );
            }

        //
        // Init closing
        //
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible( true );
        this.addWindowListener( getWindowListener() );
    }

    private ActionListener getActionListener()
    {
        if( actionListener == null ) {
            actionListener = event -> {
                final String cmd = event.getActionCommand();

                if( ACTION_QUIT.equals( cmd ) ) {
                    // Simulate call to windows close !

                    if( windowClosing() ) {
                        windowClosed();
                        }
                    }
                else if( ACTION_STOP.equals( cmd ) ) {
                    if( stopJButton.isEnabled() ) {
                        stopDownload();
                        }
                    }
                else if( ACTION_START.equals( cmd ) ) {
                    if( startJButton.isEnabled() ) {
                        startDownload();
                        }
                    }
                else {
                    LOGGER.warn( "Unknown ActionCommand " + cmd );
                    }
            };
            }

        return actionListener;
    }

    private WindowListener getWindowListener()
    {
        if( windowListener == null ) {
            windowListener = new WindowListener()
            {
                @Override
                public void windowClosed(final WindowEvent event)
                {
                    LOGGER.info("Window close event occur");
                    GenericDownloaderUIApp.this.windowClosed();
                }
                @Override
                public void windowActivated(final WindowEvent event)
                {
                }
                @Override
                public void windowClosing(final WindowEvent event)
                {
                    //Called in response to a user request for the listened-to window
                    //to be closed. To actually close the window,
                    //the listener should invoke the window's dispose
                    //or setVisible(false) method
                    LOGGER.info("Window Closing");
                    GenericDownloaderUIApp.this.windowClosing();
                }
                @Override
                public void windowDeactivated(final WindowEvent event)
                {
                }
                @Override
                public void windowDeiconified(final WindowEvent event)
                {
                }
                @Override
                public void windowIconified(final WindowEvent event)
                {
                }
                @Override
                public void windowOpened(final WindowEvent event)
                {
                    LOGGER.info("Window Opened");
                }
            };
        }
        return windowListener;
    }

    protected boolean windowClosing()
    {
        synchronized( genericDownloaderLock ) {
            if( genericDownloader_useLock != null ) {
                LOGGER.error( "windowClosing() - Already running !" );

                //FIXME ASK USER !
                return false;
                }
            }
        //genericDownloader_useLock
        //FIXME: if( could_be_close )
        //try to cancel launched tasks
        //wait for a while (running tasks)
        //save cache
        super.dispose();
        return true;
    }

    protected void windowClosed()
    {
        //genericDownloader_useLock
        System.exit( 0 ); // FIXME: Kill all
    }

    /**
     * Create the frame.
     */
    public GenericDownloaderUIApp()
    {
        init();

        //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 600, 500 );
        {
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            {
                mnFile = new JMenu("File");
                menuBar.add(mnFile);
                {
                    mntmQuit = new JMenuItem("Quit");
                    mntmQuit.setActionCommand( ACTION_QUIT );
                    mntmQuit.addActionListener( getActionListener() );
                    mnFile.add(mntmQuit);
                }
            }
        }
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        final GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 80, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 2.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 2.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        {
            cardsPanel_JScrollPane = new JScrollPane();
            final GridBagConstraints gbc_cardsPanel_JScrollPane = new GridBagConstraints();
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
            siteJComboBox.addItemListener(event -> {
                final CardLayout cl = (CardLayout)(cardsPanel.getLayout());
                final String panelName = String.class.cast( event.getItem() );
                cl.show(cardsPanel, panelName );

                for( int i = 0; i < downloaderUIPanels.length; i++ ) {
                    final GenericDownloaderAppInterface c = downloaderUIPanels[ i ].getGenericDownloaderAppInterface();
                    if( c.getSiteName().equals( panelName ) ) {
                        //xxxxx
                        break;
                        }
                    }
            });
            for( final GenericDownloaderUIPanel p : downloaderUIPanels ) {
                siteJComboBox.addItem( p.getGenericDownloaderAppInterface().getSiteName() );
                }

            final GridBagConstraints gbc_siteJComboBox = new GridBagConstraints();
            gbc_siteJComboBox.gridwidth = 4;
            gbc_siteJComboBox.insets = new Insets(0, 0, 5, 0);
            gbc_siteJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_siteJComboBox.gridx = 0;
            gbc_siteJComboBox.gridy = 0;
            contentPane.add(siteJComboBox, gbc_siteJComboBox);
        }
        {
            proxyJLabel = new JLabel("Proxy");
            final GridBagConstraints gbc_proxyJLabel = new GridBagConstraints();
            gbc_proxyJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJLabel.anchor = GridBagConstraints.EAST;
            gbc_proxyJLabel.gridx = 0;
            gbc_proxyJLabel.gridy = 2;
            contentPane.add(proxyJLabel, gbc_proxyJLabel);
        }
        {
            proxyJComboBox = new JComboBox<>(proxyComboBoxModel);
            proxyJLabel.setLabelFor(proxyJComboBox);
            final GridBagConstraints gbc_proxyJComboBox = new GridBagConstraints();
            gbc_proxyJComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_proxyJComboBox.gridx = 1;
            gbc_proxyJComboBox.gridy = 2;
            contentPane.add(proxyJComboBox, gbc_proxyJComboBox);
        }
        {
            downloadThreadNumberJLabel = new JLabel("Download thread number");
            downloadThreadNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_downloadThreadNumberJLabel = new GridBagConstraints();
            gbc_downloadThreadNumberJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_downloadThreadNumberJLabel.gridx = 2;
            gbc_downloadThreadNumberJLabel.gridy = 2;
            contentPane.add(downloadThreadNumberJLabel, gbc_downloadThreadNumberJLabel);
        }
        {
            downloadThreadNumberSpinnerModel = new SpinnerNumberModel( DOWNLOAD_THREAD_NUMBER_DEFAULT, 1, DOWNLOAD_THREAD_NUMBER_MAX, 1 );
            downloadThreadNumberJSpinner = new JSpinner( new SpinnerNumberModel(5, 1, 50, 1) );
            downloadThreadNumberJLabel.setLabelFor(downloadThreadNumberJSpinner);
            final GridBagConstraints gbc_downloadThreadNumberJSpinner = new GridBagConstraints();
            gbc_downloadThreadNumberJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJSpinner.insets = new Insets(0, 0, 5, 0);
            gbc_downloadThreadNumberJSpinner.gridx = 3;
            gbc_downloadThreadNumberJSpinner.gridy = 2;
            contentPane.add(downloadThreadNumberJSpinner, gbc_downloadThreadNumberJSpinner);
        }
        {
            final JScrollPane displayJScrollPane = new JScrollPane();
            final GridBagConstraints gbc_displayJScrollPane = new GridBagConstraints();
            gbc_displayJScrollPane.fill = GridBagConstraints.BOTH;
            gbc_displayJScrollPane.gridwidth = 4;
            gbc_displayJScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_displayJScrollPane.gridx = 0;
            gbc_displayJScrollPane.gridy = 3;
            contentPane.add(displayJScrollPane, gbc_displayJScrollPane);
            {
                displayJTable = createJTable();
                displayTableModel.setJTable( displayJTable );
                displayJScrollPane.setViewportView( displayJTable );
            }
        }
        {
            displayJProgressBar = new JProgressBar();
            final GridBagConstraints gbc_displayJProgressBar = new GridBagConstraints();
            gbc_displayJProgressBar.fill = GridBagConstraints.HORIZONTAL;
            gbc_displayJProgressBar.gridwidth = 4;
            gbc_displayJProgressBar.insets = new Insets(0, 0, 5, 0);
            gbc_displayJProgressBar.gridx = 0;
            gbc_displayJProgressBar.gridy = 4;
            contentPane.add(displayJProgressBar, gbc_displayJProgressBar);
        }
        {
            panel = new JPanel();
            final GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 0, 5);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 5;
            contentPane.add(panel, gbc_panel);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            startJButton = new JButton("Start");
            panel.add(startJButton);
            startJButton.setActionCommand( ACTION_START );
            startJButton.addActionListener( getActionListener() );
            {
                stopJButton = new JButton("Stop");
                stopJButton.setActionCommand( ACTION_STOP );
                stopJButton.addActionListener( getActionListener() );
                stopJButton.setEnabled(false);
                panel.add(stopJButton);
            }
        }
    }


    protected void stopDownload()
    {
        new Thread( ( ) -> {
            synchronized( genericDownloaderLock ) {
                if( genericDownloader_useLock != null ) {
                    genericDownloader_useLock.onClickStopDownload();
                    }
                }
        }).start();
    }

    private void startDownload()
    {
        // UI disable/enable
        startJButton.setEnabled( false );
        stopJButton.setEnabled( true );
        siteJComboBox.setEnabled( false );
        proxyJComboBox.setEnabled( false );
        downloadThreadNumberJSpinner.setEnabled( false );
        siteJComboBox.setEnabled( false );

        for( final GenericDownloaderUIPanel pannel : downloaderUIPanels ) {
            pannel.setEnabledAllComponents( false );
            }

        synchronized( genericDownloaderLock ) {
            if( genericDownloader_useLock != null ) {
                LOGGER.error( "Already running !" );
                return;
                }
            }

        displayJProgressBar.setIndeterminate( true );
        displayJProgressBar.setEnabled( true );
        displayJProgressBar.setString( "searching URLs to download" );
        displayJProgressBar.setStringPainted( true );

        displayTableModel.clear();

        // UI get values
        final GenericDownloaderAppInterface gdai  = downloadEntriesTypeList.get( siteJComboBox.getSelectedIndex() );
        final GenericDownloaderUIPanel      panel = downloaderUIPanels[ siteJComboBox.getSelectedIndex() ];
        final Proxy proxy = proxyComboBoxModel.getElementAt( proxyJComboBox.getSelectedIndex() ).getProxy();
        final int downloadThreadNumber = this.downloadThreadNumberSpinnerModel.getNumber().intValue();

        LOGGER.info( "proxy: " + proxy.toString() );
        LOGGER.info( "downloadThreadNumber: " + downloadThreadNumber );
        LOGGER.info( "CacheRelativeDirectoryCacheName: " + gdai.getCacheRelativeDirectoryCacheName() );
        LOGGER.info( "PageScanCount: " + panel.getGenericDownloaderAppInterface().getPageCount() );
        LOGGER.info( "GenericDownloaderAppInterface: " + gdai );

        gdai.setProxy( proxy );
        // gdai.setPageCount( pageCount ); set in GenericDownloaderUIPanel

        new Thread( ( ) -> {
             final GenericDownloaderAppUIResults gdauir = new GenericDownloaderAppUIResults()
            {
                @Override
                public int getDownloadThreadCount()
                {
                    return downloadThreadNumber;
                }
                @Override
                public LoggerListener getAbstractLogger()
                {
                    return displayTableModel;
                }
            };

            try {
                //GenericDownloader
                synchronized( genericDownloaderLock ) {
                    genericDownloader_useLock = new GenericDownloader(gdai, gdauir);
                    }

                genericDownloader_useLock.onClickStartDownload();

                synchronized( genericDownloaderLock ) {
                    genericDownloader_useLock = null;
                    }
                LOGGER.info( "done" );
                }
            catch( final Exception e ) {
                LOGGER.fatal( "fatal", e );
                }

            startJButton.setEnabled( true );
            stopJButton.setEnabled( false );
            siteJComboBox.setEnabled( true );
            proxyJComboBox.setEnabled( true );
            downloadThreadNumberJSpinner.setEnabled( true );
            siteJComboBox.setEnabled( true );

            for( final GenericDownloaderUIPanel pannel : downloaderUIPanels ) {
                pannel.setEnabledAllComponents( true );
                }

            displayJProgressBar.setIndeterminate( false );
            displayJProgressBar.setEnabled( false );
            displayJProgressBar.setString( "Ready." );
        }).start();
    }

    private static List<ProxyEntry> createProxyList()
    {
        final List<ProxyEntry> l = new ArrayList<ProxyEntry>();

        //l.add(  new ProxyEntry( "xxx.yyy.zzz.2", 3128 ) );

        return l;
    }

    private final DisplayTableModel displayTableModel = new DisplayTableModel()
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void error( final URL url, final File file, final Throwable cause )
        {
            LOGGER.error(  ("*** ERROR: " + "Error while download: ") + url + " to file: " + file, cause );
        }
        @Override
        public void downloadStateInit( final DownloadStateEvent event )
        {
            //TODO
            displayJProgressBar.setMinimum( 0 );
            displayJProgressBar.setMaximum( event.getDownloadListSize() );
            displayJProgressBar.setValue( 0 );
            displayJProgressBar.setEnabled( true );
            displayJProgressBar.setIndeterminate( false );
            displayJProgressBar.setStringPainted( true );
            //TODO

            LOGGER.info( "init :" + event.getDownloadListSize() );
        }
        @Override
        public void downloadStateChange( final DownloadStateEvent event )
        {
            //TODO
            displayJProgressBar.setValue( event.getDownloadListSize() );
            displayJProgressBar.setString( "loading " + event.getDownloadListSize() );
            //TODO

            LOGGER.info( "downloadStateChange :" + event.getDownloadListSize() );
        }
    };

    /**
     * @wbp.factory
     * @wbp.factory.parameter.source displayTableModel displayTableModel
     */
    //Needed for WindowsBuilder: static
    public JTable createJTable()
    {
        final DisplayTableBuilder builder = new DisplayTableBuilder( this, this.displayTableModel );

        return builder.getJTable();
    }
}

