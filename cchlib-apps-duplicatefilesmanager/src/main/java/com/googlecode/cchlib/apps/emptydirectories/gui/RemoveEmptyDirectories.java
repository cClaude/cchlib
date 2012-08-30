package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.DefaultDFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
//import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
//import com.googlecode.cchlib.apps.duplicatefiles.DefaultDFToolKit;
//import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.LasyJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.list.LeftDotListCellRenderer;

/**
 *
 *
 */
public class RemoveEmptyDirectories
    extends RemoveEmptyDirectoriesFrameWB
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectories.class );
    private FileTreeModelable treeModel;
    private FindDeleteAdapter findDeleteAdapter;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;
    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage    = "Analyze disk structure";
    private DFToolKit dfToolKit;

    /**
     *
     */
    public RemoveEmptyDirectories( final DFToolKit dfToolKit )
    {
        super();
        
        this.dfToolKit = dfToolKit;
        
        setIconImage( getDFToolKit().getResources().getAppIcon() );
    }

    protected DFToolKit getDFToolKit()
    {
        return dfToolKit;
    }

    private void init()
    {
        JProgressBar pBar = super.getProgressBar();//getJProgressBarMain();
        pBar.setStringPainted( true );
        pBar.setString( "Select directory to scan" );
        pBar.setIndeterminate( false );

        // Create a JTree and tell it to display our model
        final JTree jTreeDir = super.getJTreeEmptyDirectories();
        treeModel = new FileTreeModel2( jTreeDir );
        jTreeDir.setModel( treeModel );
        jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer( treeModel ) );
        jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( treeModel ) );
        jTreeDir.setEditable( true );

        enable_findTaskDone();
        
        final LeftDotListCellRenderer leftListCellRenderer
        = new LeftDotListCellRenderer( super.getJListRootDirectories(), true );
        super.getJListRootDirectories().setCellRenderer( leftListCellRenderer );

        super.getJListRootDirectories().addListSelectionListener(
            new ListSelectionListener()
            {
                @Override
                public void valueChanged( ListSelectionEvent e )
                {
                    final int count = getJListRootDirectories().getSelectedValuesList().size();

                    if( count > 0 ) {
                        setButtonRemoveRootDirectoryEnabled( true );
                        }
                    else {
                        // No selection
                        setButtonRemoveRootDirectoryEnabled( false );
                        }
                }
            });

        // Init Adapter
        FindDeleteListener findDeleteListener = new FindDeleteListener()
        {
            @Override
            public void findTaskDone( final boolean isCancel )
            {
                logger.info( "find thread done" );
                // Bad workaround !!!!
                // TODO: find a better solution to expand tree
                // during build.
                expandAllRows();

                enable_findTaskDone();
                
                JProgressBar pBar = getProgressBar();
                pBar.setIndeterminate( false );

                if( isCancel ) {
                    pBar.setString( "Scan canceled !" );
                    }
                else {
                    pBar.setString( "Select file to delete" );
                    }
            }
        };

        findDeleteAdapter = new FindDeleteAdapter(
                getJListRootDirectoriesModel(),
                treeModel,
                findDeleteListener
                );

        logger.info( "init() done" );
    }

    private void findBegin()
    {
        logger.info( "find thread started" );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( "Computing..." );
        pBar.setIndeterminate( true );

//        super.getBtnAddRootDirectory().setEnabled( false );
//        super.getBtnStartScan().setEnabled( false );
//        super.getBtnCancel().setEnabled( true );
//        super.getBtnStartDelete().setEnabled( false );
//        super.getBtnSelectAll().setEnabled( false );
//        super.getBtnUnselectAll().setEnabled( false );
//        super.getJListRootDirectories().setEnabled( false );
//        super.getJListRootDirectories().clearSelection();
        
        /*super.*/enable_findBegin();
        
        Runnable doRun = new Runnable()
        {
            @Override
            public void run()
            {
                findDeleteAdapter.doFind();
            }
        };

        // KO Lock UI doRun.run();
        // KO Lock UI SwingUtilities.invokeAndWait( doRun );
        // KO Lock UI SwingUtilities.invokeLater( doRun );
        new Thread( doRun ).start();
    }

    protected void expandAllRows()
    {
        try {
            final JTree jTreeDir = getJTreeEmptyDirectories();

            //Expend all nodes
            for (int i = 0; i < jTreeDir.getRowCount(); i++) {
                jTreeDir.expandRow(i);
                }
            }
        catch( Exception e ) {
            logger.error( "expandAllRows()", e );
            }
    }

    private void startDelete()
    {
        logger.info( "delete thread started" );

        /*super.*/enable_startDelete();
        
        getJTreeEmptyDirectories().setEditable( false );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( "Delete selected files" );
        pBar.setIndeterminate( true );

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    findDeleteAdapter.doDelete();
                    }
                catch( Exception e ) {
                    logger.warn( "doDelete()", e );
                    }
                finally {
                    getBtnStartDelete().setEnabled( true );

                    getBtnCancel().setEnabled( false );
                    getJTreeEmptyDirectories().setEditable( true );
                    pBar.setIndeterminate( false );

                    findBegin();
                    }

                logger.info( "delete thread done" );
            }
        }).start();
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        final Preferences   preferences = Preferences.createPreferences();
        final DFToolKit     dfToolKit   = new DefaultDFToolKit( preferences );
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                RemoveEmptyDirectories frame = RemoveEmptyDirectories.start(dfToolKit);
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            }
        } );

        logger.fatal( "Running in a thread" );
    }

    /**
     * A WindowHandler should be add on frame.
     * @return Main Window
     */
    public static RemoveEmptyDirectories start( final DFToolKit dfToolKit )
    {
        RemoveEmptyDirectories frame = new RemoveEmptyDirectories( dfToolKit );
        //Done!frame.setDefaultCloseOperation( RemoveEmptyDirectories.EXIT_ON_CLOSE );
        frame.setTitle( "RemoveEmptyDirectories" );
        //Done?frame.getContentPane().setPreferredSize( frame.getSize() );
        //Done?frame.pack();
        //Done?frame.setLocationRelativeTo( null );
        frame.init();
        frame.setVisible( true );

        // Prepare JFileChooser
        frame.getWaitingJFileChooserInitializer();

        return frame;
    }

    private WaitingJFileChooserInitializer getWaitingJFileChooserInitializer()
    {
        if( waitingJFileChooserInitializer == null ) {
            JFileChooserInitializerCustomize configurator
                //= WaitingJFileChooserInitializer.getDefaultConfigurator();
                = new LasyJFCCustomizer()
                    .setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

            waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );

            }
        return waitingJFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        //return getWaitingJFileChooserInitializer().getJFileChooser();
        JFileChooser jfc = getWaitingJFileChooserInitializer().getJFileChooser();

        logger.info( "FileSelectionMode = " + jfc.getFileSelectionMode() );
        logger.info( "JFileChooser.FILES_ONLY:" + JFileChooser.FILES_ONLY );
        logger.info( "JFileChooser.DIRECTORIES_ONLY:" + JFileChooser.DIRECTORIES_ONLY );
        logger.info( "JFileChooser.FILES_AND_DIRECTORIES:" + JFileChooser.FILES_AND_DIRECTORIES );
         
        return jfc;
    }

    @Override
    protected void btnAddRootDirectory_mouseClicked( MouseEvent e )
    {
        logger.info( "btnAddRootDirectory_mouseClicked" );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    addRootDirectory();
                }
            };
           new Thread( r ).start();
           logger.info( "btnAddRootDirectory_mouseClicked done" );
        }
    }

    private void addRootDirectory()
    {
        logger.info( "addRootDirectory()" );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            JFileChooser jfc = getJFileChooser();

            logger.info( "getJFileChooser() done" );

            jfc.setMultiSelectionEnabled( true );
            int returnVal = jfc.showOpenDialog( this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                DefaultListModel<File>     model = super.getJListRootDirectoriesModel();
                File[]                     files = jfc.getSelectedFiles();

                //logger.info( "model:" + model );
                //logger.info( "model.getClass():" + model.getClass() );

                for( File f:files ) {
                    //model.
                    model.addElement( f );
                    logger.info( "selected dir:" + f );
                    }
                }
            logger.info( "addRootDirectory() done" );
        }
    }

    @Override
    protected void addRootDirectory( final List<File> files )
    {
        DefaultListModel<File> model = super.getJListRootDirectoriesModel();

        for( File f:files ) {
            if( f.isDirectory() ) {
                model.addElement( f );
                logger.info( "add drop dir:" + f );
                }
            else {
                logger.warn( "Ignore drop : " + f );
                }
            }
    }
    @Override
    protected void btnRemoveRootDirectory_mouseClicked( MouseEvent e )
    {
        logger.info( "btnRemoveRootDirectory_mouseClicked" );

        if( super.isButtonRemoveRootDirectoryEnabled() ) {
            JList<File>             rootList        = super.getJListRootDirectories();
            List<File>              selectedList    = rootList.getSelectedValuesList();
            DefaultListModel<File>  model           = super.getJListRootDirectoriesModel();

            for( File f : selectedList ) {
                model.removeElement( f );
                }

            rootList.clearSelection();
            }
    }

    @Override
    protected void btnStartScan_mouseClicked( MouseEvent event )
    {
        if( super.isButtonStartScanEnabled() ) {
            logger.info( "btnStartScan_mouseClicked" );

            findBegin(); // Launch a thread
            }
    }

    @Override
    protected void btnCancel_mouseClicked( MouseEvent event )
    {
        logger.info( "btnCancel_mouseClicked" );
        if( super.getBtnCancel().isEnabled() ) {
            findDeleteAdapter.cancel();
            logger.info( "Cancel!" );
            }
    }

    @Override
    protected void btnSelectAll_mouseClicked( MouseEvent event )
    {
        if( super.getBtnSelectAll().isEnabled() ) {
            logger.info( "btnSelectAll_mouseClicked" );

            treeModel.setSelectAllLeaf( true );
            expandAllRows();
            }
    }

    @Override
    protected void btnUnselectAll_mouseClicked( MouseEvent event )
    {
        if( super.getBtnUnselectAll().isEnabled() ) {
            logger.info( "btnSelectAll_mouseClicked" );

            treeModel.setSelectAllLeaf( false );
            expandAllRows();
            }
    }

    @Override
    protected void btnStartDelete_mouseClicked( MouseEvent event )
    {
        logger.info( "btnStartDelete_mouseClicked" );

        if( super.getBtnStartDelete().isEnabled() ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    startDelete();
                }
            };
            new Thread( r ).start();
            // NOTE: do not use of SwingUtilities.invokeLater( r );
        }
    }

}
