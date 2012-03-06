package cx.ath.choisnet.tools.emptydirectories.gui;

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
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import cx.ath.choisnet.swing.list.LeftDotListCellRenderer;

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

    /**
     *
     */
    public RemoveEmptyDirectories()
    {
        super();
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

        super.getBtnAddRootDirectory().setEnabled( true );
        super.getBtnRemoveRootDirectory().setEnabled( false );

        super.getBtnStartScan().setEnabled( true );
        super.getBtnCancel().setEnabled( false );

        super.getBtnStartDelete().setEnabled( false );
        super.getBtnSelectAll().setEnabled( false );
        super.getBtnUnselectAll().setEnabled( false );

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
                        getBtnRemoveRootDirectory().setEnabled( true );
                        }
                    else {
                        // No selection
                        getBtnRemoveRootDirectory().setEnabled( false );
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

                getBtnStartScan().setEnabled( true );
                getBtnCancel().setEnabled( false );
                getBtnStartDelete().setEnabled( true ); // FIXME: enable only when at least 1 file selected
                getBtnSelectAll().setEnabled( true );
                getBtnUnselectAll().setEnabled( true );
                getJListRootDirectories().setEnabled( true );
                getBtnAddRootDirectory().setEnabled( true );

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

        super.getBtnAddRootDirectory().setEnabled( false );
        super.getBtnStartScan().setEnabled( false );
        super.getBtnCancel().setEnabled( true );
        super.getBtnStartDelete().setEnabled( false );
        super.getBtnSelectAll().setEnabled( false );
        super.getBtnUnselectAll().setEnabled( false );
        super.getJListRootDirectories().setEnabled( false );
        super.getJListRootDirectories().clearSelection();

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

        getBtnAddRootDirectory().setEnabled( false );
        getBtnRemoveRootDirectory().setEnabled( false );
        getBtnStartScan().setEnabled( false );
        getBtnCancel().setEnabled( true );
        getBtnSelectAll().setEnabled( false );
        getBtnUnselectAll().setEnabled( false );
        getBtnStartDelete().setEnabled( false );

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
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                RemoveEmptyDirectories frame = RemoveEmptyDirectories.start();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            }
        } );

        logger.fatal( "Running in a thread" );
    }

    /**
     * A WindowHandler should be add on frame.
     * @return Main Window
     */
    public static RemoveEmptyDirectories start()
    {
        RemoveEmptyDirectories frame = new RemoveEmptyDirectories();
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
                = WaitingJFileChooserInitializer.getDefaultConfigurator();

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
        return getWaitingJFileChooserInitializer().getJFileChooser();
    }

    @Override
    protected void btnAddRootDirectory_mouseClicked( MouseEvent e )
    {
        logger.info( "btnAddRootDirectory_mouseClicked" );

        if( super.getBtnAddRootDirectory().isEnabled() ) {
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

        if( super.getBtnAddRootDirectory().isEnabled() ) {
            JFileChooser jfc = getJFileChooser();

            logger.info( "getJFileChooser() done" );

            jfc.setMultiSelectionEnabled( true );
            int returnVal = jfc.showOpenDialog( this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                DefaultListModel<File>     model = super.getJListRootDirectoriesModel();
                File[]                     files = jfc.getSelectedFiles();

                logger.info( "model:" + model );
                logger.info( "model.getClass():" + model.getClass() );

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
    protected void btnRemoveRootDirectory_mouseClicked( MouseEvent e )
    {
        logger.info( "btnRemoveRootDirectory_mouseClicked" );

        if( super.getBtnRemoveRootDirectory().isEnabled() ) {
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
        if( super.getBtnStartScan().isEnabled() ) {
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
