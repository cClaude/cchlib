package com.googlecode.cchlib.tools.downloader;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
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
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.tools.downloader.common.DownloadStateEvent;
import com.googlecode.cchlib.tools.downloader.common.DownloaderData;
import com.googlecode.cchlib.tools.downloader.common.DownloaderHandler;
import com.googlecode.cchlib.tools.downloader.common.LoggerListener;
import com.googlecode.cchlib.tools.downloader.common.SiteConfig;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_senorgif;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_www_bloggif_com;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_www_epins_fr;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_www_gifgirl_org;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_www_gifmash_com;
import com.googlecode.cchlib.tools.downloader.connector.DownloadI_www_gifpal_com;
import com.googlecode.cchlib.tools.downloader.display.table.DisplayTableBuilder;
import com.googlecode.cchlib.tools.downloader.display.table.DisplayTableModel;
import com.googlecode.cchlib.tools.downloader.gdai.tumblr.GDAI_tumblr_com;
import com.googlecode.cchlib.tools.downloader.proxy.ProxyEntry;

/**
 * Application starting class
 */
@SuppressWarnings({
    "squid:MaximumInheritanceDepth", // Swing
    "squid:S00117", "squid:S00116" // Generated code
    })
public class GenericDownloaderUIApp extends JFrame
{
    private static final class MyDisplayTableModel extends DisplayTableModel
    {
        private static final long serialVersionUID = 1L;

        private final GenericDownloaderUIApp frame;

        MyDisplayTableModel( final GenericDownloaderUIApp frame )
        {
            this.frame = frame;
        }

        public JProgressBar getDisplayJProgressBar()
        {
            return this.frame.displayJProgressBar;
        }

        @Override
        public void error( final URL url, final File file, final Throwable cause )
        {
            LOGGER.error(  ("*** ERROR: " + "Error while download: ") + url + " to file: " + file, cause );
        }

        @Override
        public void downloadStateInit( final DownloadStateEvent event )
        {
            LOGGER.info( "downloadStateInit " + event );
            //TODO
            getDisplayJProgressBar().setMinimum( 0 );
            final int downloadListSize = event.getDownloadListSize();
            getDisplayJProgressBar().setMaximum( downloadListSize );
            getDisplayJProgressBar().setValue( 0 );
            getDisplayJProgressBar().setEnabled( true );
            getDisplayJProgressBar().setIndeterminate( false );
            getDisplayJProgressBar().setStringPainted( true );
            //TODO

            LOGGER.info( "setMaximum downloadListSize = " + downloadListSize );
        }

        @Override
        public void downloadStateChange( final DownloadStateEvent event )
        {
            LOGGER.info( "downloadStateChange " + event );

            //TODO
            getDisplayJProgressBar().setValue( event.getDownloadListSize() );
            getDisplayJProgressBar().setString( "loading " + event.getDownloadListSize() );
            //TODO

            LOGGER.info( "downloadStateChange setValue = " + event.getDownloadListSize() );
        }
    }

    private final class MyWindowListener extends WindowAdapter
    {
        @Override
        public void windowClosed(final WindowEvent event)
        {
            LOGGER.info("Window close event occur");
            GenericDownloaderUIApp.this.windowClosed();
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
        public void windowOpened(final WindowEvent event)
        {
            LOGGER.info("Window Opened");
        }
    }

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

    private transient WindowListener appWindowListener;

    private final DisplayTableModel                   displayTableModel;
    private final ArrayList<GenericDownloaderUIPanel> downloaderUIPanels
        = new ArrayList<>();

    private DefaultComboBoxModel<ProxyEntry> proxyComboBoxModel;
    private JButton startJButton;
    private JButton stopJButton;
    private JComboBox<ProxyEntry> proxyJComboBox;
    private JComboBox<String>     siteJComboBox;
    private JLabel downloadThreadNumberJLabel;
    private JLabel proxyJLabel;
    private JMenu     mnFile;
    private JMenuBar  appMenuBar;
    private JMenuItem mntmQuit;
    private JPanel cardsPanel;
    private JPanel panel;
    private JProgressBar displayJProgressBar;
    private JScrollPane cardsPanel_JScrollPane;
    private JSpinner downloadThreadNumberJSpinner;
    private JTable displayJTable;
    private SpinnerNumberModel downloadThreadNumberSpinnerModel;
    private final JPanel contentPane;

    /**
     * Create the frame.
     */
    @SuppressWarnings("squid:S1199")
    public GenericDownloaderUIApp()
    {
        init();

        this.displayTableModel = new MyDisplayTableModel( this );

        //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 600, 500 );
        {
            this.appMenuBar = new JMenuBar();
            setJMenuBar( this.appMenuBar );
            {
                this.mnFile = new JMenu("File");
                this.appMenuBar.add(this.mnFile);
                {
                    this.mntmQuit = new JMenuItem("Quit");
                    this.mntmQuit.setActionCommand( ACTION_QUIT );
                    this.mntmQuit.addActionListener( getActionListener() );
                    this.mnFile.add(this.mntmQuit);
                }
            }
        }
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( this.contentPane );
        final GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 80, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 2.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 2.0, 0.0, 0.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);
        {
            this.cardsPanel_JScrollPane = new JScrollPane();
            final GridBagConstraints gbc_cardsPanel_JScrollPane = new GridBagConstraints();
            gbc_cardsPanel_JScrollPane.fill = GridBagConstraints.BOTH;
            gbc_cardsPanel_JScrollPane.gridwidth = 4;
            gbc_cardsPanel_JScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_cardsPanel_JScrollPane.gridx = 0;
            gbc_cardsPanel_JScrollPane.gridy = 1;
            this.contentPane.add(this.cardsPanel_JScrollPane, gbc_cardsPanel_JScrollPane);
            this.cardsPanel = new JPanel();
            this.cardsPanel_JScrollPane.setViewportView(this.cardsPanel);
            this.cardsPanel.setLayout(new CardLayout(0, 0));

            for( final GenericDownloaderUIPanel panel : this.downloaderUIPanels ) {
                this.cardsPanel.add(
                        panel,
                        panel.getDownloaderData().getSiteName()
                        );
            }
        }
        {
            this.siteJComboBox = new JComboBox<>();
            this.siteJComboBox.addItemListener(event -> {
                final CardLayout cl = (CardLayout)(this.cardsPanel.getLayout());
                final String panelName = String.class.cast( event.getItem() );
                cl.show(this.cardsPanel, panelName );

                for( final GenericDownloaderUIPanel panel : this.downloaderUIPanels ) {
                    final DownloaderData data = panel.getDownloaderData();

                    if( data.getSiteName().equals( panelName ) ) {
                        //xxxxx
                        break;
                        }
                    }
            });

            for( final GenericDownloaderUIPanel panel : this.downloaderUIPanels ) {
                this.siteJComboBox.addItem( panel.getDownloaderData().getSiteName() );
                }

            final GridBagConstraints gbc_siteJComboBox = new GridBagConstraints();
            gbc_siteJComboBox.gridwidth = 4;
            gbc_siteJComboBox.insets = new Insets(0, 0, 5, 0);
            gbc_siteJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_siteJComboBox.gridx = 0;
            gbc_siteJComboBox.gridy = 0;
            this.contentPane.add(this.siteJComboBox, gbc_siteJComboBox);
        }
        {
            this.proxyJLabel = new JLabel("Proxy");
            final GridBagConstraints gbc_proxyJLabel = new GridBagConstraints();
            gbc_proxyJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJLabel.anchor = GridBagConstraints.EAST;
            gbc_proxyJLabel.gridx = 0;
            gbc_proxyJLabel.gridy = 2;
            this.contentPane.add(this.proxyJLabel, gbc_proxyJLabel);
        }
        {
            this.proxyJComboBox = new JComboBox<>(this.proxyComboBoxModel);
            this.proxyJLabel.setLabelFor(this.proxyJComboBox);
            final GridBagConstraints gbc_proxyJComboBox = new GridBagConstraints();
            gbc_proxyJComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_proxyJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_proxyJComboBox.gridx = 1;
            gbc_proxyJComboBox.gridy = 2;
            this.contentPane.add(this.proxyJComboBox, gbc_proxyJComboBox);
        }
        {
            this.downloadThreadNumberJLabel = new JLabel("Download thread number");
            this.downloadThreadNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_downloadThreadNumberJLabel = new GridBagConstraints();
            gbc_downloadThreadNumberJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_downloadThreadNumberJLabel.gridx = 2;
            gbc_downloadThreadNumberJLabel.gridy = 2;
            this.contentPane.add(this.downloadThreadNumberJLabel, gbc_downloadThreadNumberJLabel);
        }
        {
            this.downloadThreadNumberSpinnerModel = new SpinnerNumberModel( DOWNLOAD_THREAD_NUMBER_DEFAULT, 1, DOWNLOAD_THREAD_NUMBER_MAX, 1 );
            this.downloadThreadNumberJSpinner = new JSpinner( new SpinnerNumberModel(5, 1, 50, 1) );
            this.downloadThreadNumberJLabel.setLabelFor(this.downloadThreadNumberJSpinner);
            final GridBagConstraints gbc_downloadThreadNumberJSpinner = new GridBagConstraints();
            gbc_downloadThreadNumberJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_downloadThreadNumberJSpinner.insets = new Insets(0, 0, 5, 0);
            gbc_downloadThreadNumberJSpinner.gridx = 3;
            gbc_downloadThreadNumberJSpinner.gridy = 2;
            this.contentPane.add(this.downloadThreadNumberJSpinner, gbc_downloadThreadNumberJSpinner);
        }

        {
            final JScrollPane displayJScrollPane = new JScrollPane();
            final GridBagConstraints gbc_displayJScrollPane = new GridBagConstraints();
            gbc_displayJScrollPane.fill = GridBagConstraints.BOTH;
            gbc_displayJScrollPane.gridwidth = 4;
            gbc_displayJScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_displayJScrollPane.gridx = 0;
            gbc_displayJScrollPane.gridy = 3;
            this.contentPane.add(displayJScrollPane, gbc_displayJScrollPane);
            {
                this.displayJTable = createJTable();
                this.displayTableModel.setJTable( this.displayJTable );
                displayJScrollPane.setViewportView( this.displayJTable );
            }
        }
        {
            this.displayJProgressBar = new JProgressBar();
            final GridBagConstraints gbc_displayJProgressBar = new GridBagConstraints();
            gbc_displayJProgressBar.fill = GridBagConstraints.HORIZONTAL;
            gbc_displayJProgressBar.gridwidth = 4;
            gbc_displayJProgressBar.insets = new Insets(0, 0, 5, 0);
            gbc_displayJProgressBar.gridx = 0;
            gbc_displayJProgressBar.gridy = 4;
            this.contentPane.add(this.displayJProgressBar, gbc_displayJProgressBar);
        }
        {
            this.panel = new JPanel();
            final GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 0, 5);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 5;
            this.contentPane.add(this.panel, gbc_panel);
            this.panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            this.startJButton = new JButton("Start");
            this.panel.add(this.startJButton);
            this.startJButton.setActionCommand( ACTION_START );
            this.startJButton.addActionListener( getActionListener() );
            {
                this.stopJButton = new JButton("Stop");
                this.stopJButton.setActionCommand( ACTION_STOP );
                this.stopJButton.addActionListener( getActionListener() );
                this.stopJButton.setEnabled(false);
                this.panel.add(this.stopJButton);
            }
        }
    }

    private void downloaderUIPanelsInt()
    {
        final ArrayList<SiteConfig> downloadEntriesTypeList = new ArrayList<>();

        try {
            downloadEntriesTypeList.add( GDAI_tumblr_com.createAllEntries( this ) );
        }
        catch( final JSONHelperException e ) {
            LOGGER.error( "GDAI_tumblr_com Fail", e );
        }

        downloadEntriesTypeList.add( new DownloadI_senorgif() );
        downloadEntriesTypeList.add( new DownloadI_www_bloggif_com() );
        downloadEntriesTypeList.add( new DownloadI_www_gifgirl_org() );
        downloadEntriesTypeList.add( new DownloadI_www_gifmash_com() );
        downloadEntriesTypeList.add( new DownloadI_www_gifpal_com() );
        downloadEntriesTypeList.add( new DownloadI_www_epins_fr() );

        for( final SiteConfig entry : downloadEntriesTypeList ) {
            this.downloaderUIPanels.add( new GenericDownloaderUIPanel( entry, entry ) );
        }

        LOGGER.info( "Load " + this.downloaderUIPanels.size() + " sites" );
    }

    private void init()
    {
        downloaderUIPanelsInt();

        this.proxyComboBoxModel = new DefaultComboBoxModel<>();
        this.proxyComboBoxModel.addElement( new ProxyEntry( Proxy.NO_PROXY ) );

        for( final ProxyEntry entry : createProxyList() ) {
            this.proxyComboBoxModel.addElement( entry );
            }

        //
        // Init closing
        //
        this.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
        this.setVisible( true );
        this.addWindowListener( getAppWindowListener() );
    }

    private ActionListener getActionListener()
    {
        return this::actionPerformedActionListener;
    }

    private WindowListener getAppWindowListener()
    {
        if( this.appWindowListener == null ) {
            this.appWindowListener = new MyWindowListener();
        }
        return this.appWindowListener;
    }

    protected boolean windowClosing()
    {
        synchronized( this.genericDownloaderLock ) {
            if( this.genericDownloader_useLock != null ) {
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

    protected void stopDownload()
    {
        new Thread( ( ) -> {
            synchronized( this.genericDownloaderLock ) {
                if( this.genericDownloader_useLock != null ) {
                    this.genericDownloader_useLock.onClickStopDownload();
                    }
                }
        }).start();
    }

    private void startDownload()
    {
        // UI disable/enable
        this.startJButton.setEnabled( false );
        this.stopJButton.setEnabled( true );
        this.siteJComboBox.setEnabled( false );
        this.proxyJComboBox.setEnabled( false );
        this.downloadThreadNumberJSpinner.setEnabled( false );
        this.siteJComboBox.setEnabled( false );

        for( final GenericDownloaderUIPanel pannel : this.downloaderUIPanels ) {
            pannel.setEnabledAllComponents( false );
            }

        synchronized( this.genericDownloaderLock ) {
            if( this.genericDownloader_useLock != null ) {
                LOGGER.error( "Already running !" );
                return;
                }
            }

        this.displayJProgressBar.setIndeterminate( true );
        this.displayJProgressBar.setEnabled( true );
        this.displayJProgressBar.setString( "searching URLs to download" );
        this.displayJProgressBar.setStringPainted( true );

        this.displayTableModel.clear();

        // UI get values
        //final GenericDownloaderAppInterface gdai__  = this.downloadEntriesTypeList.get( this.siteJComboBox.getSelectedIndex() );
        final GenericDownloaderUIPanel panel    = this.downloaderUIPanels.get( this.siteJComboBox.getSelectedIndex() );
        final DownloaderData           data     = panel.getDownloaderData();
        final DownloaderHandler        handler  = panel.getDownloaderHandler();
        final Proxy proxy = this.proxyComboBoxModel.getElementAt( this.proxyJComboBox.getSelectedIndex() ).getProxy();
        final int downloadThreadNumber = this.downloadThreadNumberSpinnerModel.getNumber().intValue();

        LOGGER.info( "proxy: " + proxy.toString() );
        LOGGER.info( "downloadThreadNumber: " + downloadThreadNumber );
        LOGGER.info( "CacheRelativeDirectoryCacheName: " + data.getCacheRelativeDirectoryCacheName() );
        LOGGER.info( "PageScanCount: " + data.getPageCount() );
        LOGGER.info( "data: " + data );
        LOGGER.info( "handler: " + handler );

        data.setProxy( proxy );
        // gdai.setPageCount( pageCount ); set in GenericDownloaderUIPanel

        new Thread( () -> startDownload( data, handler, downloadThreadNumber ) ).start();
    }

    private void startDownload(
        final DownloaderData    data,
        final DownloaderHandler handler,
        final int               downloadThreadNumber
        )
    {
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
                return GenericDownloaderUIApp.this.displayTableModel;
            }
        };

        try {
            //GenericDownloader
            synchronized( this.genericDownloaderLock ) {
                this.genericDownloader_useLock =
                        new GenericDownloader( data, handler, gdauir);
                }

            this.genericDownloader_useLock.onClickStartDownload();

             LOGGER.info( "done" );
            }
        catch( final Exception e ) {
            LOGGER.fatal( "fatal", e );
            }
        finally {
            synchronized( this.genericDownloaderLock ) {
                this.genericDownloader_useLock = null;
                }

            this.startJButton.setEnabled( true );
            this.stopJButton.setEnabled( false );
            this.siteJComboBox.setEnabled( true );
            this.proxyJComboBox.setEnabled( true );
            this.downloadThreadNumberJSpinner.setEnabled( true );
            this.siteJComboBox.setEnabled( true );

            for( final GenericDownloaderUIPanel pannel : this.downloaderUIPanels ) {
                pannel.setEnabledAllComponents( true );
                }

            this.displayJProgressBar.setIndeterminate( false );
            this.displayJProgressBar.setEnabled( false );
            this.displayJProgressBar.setString( "Ready." );
        }
    }

    private static List<ProxyEntry> createProxyList()
    {
        final List<ProxyEntry> l = new ArrayList<>();

        //l.add(  new ProxyEntry( "xxx.yyy.zzz.2", 3128 ) );

        return l;
    }

    /*
     * @wbp.factory
     * @wbp.factory.parameter.source displayTableModel displayTableModel
     */
    public JTable createJTable()
    {
        final DisplayTableBuilder builder = new DisplayTableBuilder( this, this.displayTableModel );

        return builder.getJTable();
    }

    /**
     * Launch the application.
     *
     * @param args CLI parameters (ignored)
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( GenericDownloaderUIApp::run );
    }

    private static void run()
    {
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
            frame.setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                    GenericDownloaderUIApp.class.getResource(
                            "/javax/swing/plaf/basic/icons/image-delayed.png")
                            )
                    );
            frame.setTitle( APP_NAME );
            }
        catch( final Exception e ) {
            LOGGER.fatal( "main() exception", e );

            final String title = "GenericDownloaderUIApp";

            DialogHelper.showMessageExceptionDialog( title , e );
            }
    }

    private final void actionPerformedActionListener( final ActionEvent event )
    {
        final String cmd = event.getActionCommand();

        if( ACTION_QUIT.equals( cmd ) ) {
            // Simulate call to windows close !

            if( GenericDownloaderUIApp.this.windowClosing() ) {
                GenericDownloaderUIApp.this.windowClosed();
                }
            }
        else if( ACTION_STOP.equals( cmd ) ) {
            if( GenericDownloaderUIApp.this.stopJButton.isEnabled() ) {
                GenericDownloaderUIApp.this.stopDownload();
                }
            }
        else if( ACTION_START.equals( cmd ) ) {
            if( GenericDownloaderUIApp.this.startJButton.isEnabled() ) {
                GenericDownloaderUIApp.this.startDownload();
                }
            }
        else {
            LOGGER.warn( "Unknown ActionCommand " + cmd );
            }
    }
}

