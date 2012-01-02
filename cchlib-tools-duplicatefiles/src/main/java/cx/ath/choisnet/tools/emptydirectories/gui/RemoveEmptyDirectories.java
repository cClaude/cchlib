package cx.ath.choisnet.tools.emptydirectories.gui;

import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializerEvent;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializerListener;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.tools.emptydirectories.EmptyDirectoriesFinder;
import cx.ath.choisnet.tools.emptydirectories.EmptyDirectoriesListener;
import cx.ath.choisnet.util.CancelRequestException;

/**
 *
 *
 */
public class RemoveEmptyDirectories
    extends RemoveEmptyDirectoriesFrameWB
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectories.class );
    //private File     rootFile;
    private JFileChooserInitializer jFileChooserInitializer;
    private FileTreeModelable treeModel;
    private boolean cancelCalled;

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

        final JTree jTreeDir = super.getJTreeEmptyDirectories();
        jTreeDir.setEditable( false );
        //jTreeDir.set

        this.cancelCalled = false;

        super.getBtnAddRootDirectory().setEnabled( true );
        super.getBtnRemoveRootDirectory().setEnabled( true );

        super.getBtnStartScan().setEnabled( true );
        super.getBtnCancel().setEnabled( false );

        super.getBtnStartDelete().setEnabled( false );
        super.getBtnSelectAll().setEnabled( false );
        super.getBtnUnselectAll().setEnabled( false );

        logger.info( "init() done" );
    }

    private void startFind()
    {
        logger.info( "find thread started" );

        this.cancelCalled = false;
        //getJButtonFind().setEnabled( false );
        super.getBtnStartScan().setEnabled( false );
        //getJButtonCancel().setEnabled( true );
        super.getBtnCancel().setEnabled( true );
        //getJButtonDeleteSelected().setEnabled( false );
        super.getBtnStartDelete().setEnabled( false );
        //setJButtonSelectAll().setEnabled( false );
        super.getBtnSelectAll().setEnabled( false );
        super.getBtnUnselectAll().setEnabled( false );
        super.getJListRootDirectories().setEnabled( false );
        super.getJListRootDirectories().setSelectedIndices( new int[0] );

        // Create a JTree and tell it to display our model
        final JTree                     jTreeDir        = getJTreeEmptyDirectories();
        final DefaultListModel<File>    jListRootModel  = super.getJListRootDirectoriesModel();
        final EmptyDirectoriesFinder    emptyDirs       = new EmptyDirectoriesFinder( jListRootModel.elements() );
        final UpdateJTreeListeners      listener        = new UpdateJTreeListeners();

        emptyDirs.addListener( listener );

        treeModel = new FileTreeModel2( jTreeDir );
        jTreeDir.setModel( treeModel );

        jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer( treeModel ) );
        jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( treeModel ) );
        //jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer() );
        //jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( jTreeDir ) );
        jTreeDir.setEditable( true );

        Runnable doRun = new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    emptyDirs.find();

                    logger.info( "treeModel.size(): " + treeModel.size() );
                    }
                catch( CancelRequestException e )  {
                    logger.info( "Cancel received" );

                    // Call done, to cleanup layout.
                    listener.findDone();
                    }
            }
        };
        // TODO
        // TODO
        // TODO
        // TODO
//        doRun.run();
        //
        try {
            SwingUtilities.invokeAndWait( doRun );
            }
        catch( InvocationTargetException e ) {
            logger.error( "InvocationTargetException during find", e );
            }
        catch( InterruptedException e ) {
            logger.error( "InterruptedException during find", e );
            }

        // Bad workaround !!!!
        // TODO: find a better solution to expand tree
        // during build.
        expandAllRows();

        cancelCalled = false;
        super.getBtnStartScan().setEnabled( true );
        super.getBtnCancel().setEnabled( false );
        super.getBtnStartDelete().setEnabled( true ); // FIXME: enable only when at least 1 file selected
        super.getBtnSelectAll().setEnabled( true );
        super.getBtnUnselectAll().setEnabled( true );
        super.getJListRootDirectories().setEnabled( true );

        logger.info( "find thread done" );
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

        this.cancelCalled = false;
        super.getBtnSelectAll().setEnabled( false );
        super.getBtnUnselectAll().setEnabled( false );
        super.getBtnCancel().setEnabled( true );
        getJTreeEmptyDirectories().setEditable( false );

        JProgressBar pBar = getProgressBar();
        pBar.setString( "Delete selected files" );
        pBar.setIndeterminate( true );

        for( File f : treeModel.selectedFiles() ) {
            try {
                boolean res = f.delete();
                logger.info( "delete [" + f + "] => " + res );
                }
            catch( Exception e ) {
                logger.warn( "delete [" + f + "]", e );
                }
            }

        pBar.setIndeterminate( false );

        startFind();

        super.getBtnCancel().setEnabled( false );
        getJTreeEmptyDirectories().setEditable( true );

        logger.info( "delete thread done" );
    }

    class UpdateJTreeListeners implements EmptyDirectoriesListener
    {
        @Override
        public boolean isCancel()
        {
            return cancelCalled;
        }
        @Override
        public void newEntry( File emptyDirectoryFile )
        {
            //Xboolean add = fileTree.add( emptyDirectoryFile );
            boolean add = treeModel.add( emptyDirectoryFile );

            //logger.info( "tree size:" + treeModel.size() );
//
//            if( add ) {
//                jTreeDir.fireTreeExpanded( path )
//                }
        }
        @Override
        public void findStarted()
        {
            logger.info( "findStarted()" );
            treeModel.clear();

            JProgressBar pBar = getProgressBar();
            pBar.setString( "Computing..." );
            pBar.setIndeterminate( true );
        }
        @Override
        public void findDone()
        {
            JProgressBar pBar = getProgressBar();
            pBar.setIndeterminate( false );

            if( cancelCalled ) {
                pBar.setString( "Scan canceled !" );
                }
            else {
                pBar.setString( "Select file to delete" );
                }

            logger.info( "findDone()" );
        }
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
                RemoveEmptyDirectories frame = new RemoveEmptyDirectories();
                //Done!frame.setDefaultCloseOperation( RemoveEmptyDirectories.EXIT_ON_CLOSE );
                frame.setTitle( "RemoveEmptyDirectories" );
                //Done?frame.getContentPane().setPreferredSize( frame.getSize() );
                //Done?frame.pack();
                //Done?frame.setLocationRelativeTo( null );
                frame.init();
                frame.setVisible( true );

                // Prepare JFileChooser
                frame.getJFileChooserInitializer();
            }
        } );

        logger.fatal( "Running in a thread" );
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator(
                        //JFileChooserInitializer.Attrib.DO_NOT_USE_SHELL_FOLDER
                        ) {
                    private static final long serialVersionUID = 1L;

                    public void perfomeConfig( JFileChooser jfc )
                    {
                        super.perfomeConfig( jfc );

                        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                        jfc.setMultiSelectionEnabled( true );
                        jfc.setAccessory( new TabbedAccessory()
                                .addTabbedAccessory( new BookmarksAccessory(
                                        jfc,
                                        new BookmarksAccessoryDefaultConfigurator() ) ) );
                    }
                } );

            JFileChooserInitializerListener l = new JFileChooserInitializerListener()
            {
                @Override
                public void jFileChooserIsReady(
                        JFileChooserInitializerEvent event
                        )
                {
                    logger.info( "jFileChooserIsReady: " + event.isJFileChooserReady() );
                }
                @Override
                public void jFileChooserInitializationError(
                        JFileChooserInitializerEvent event
                        )
                {
                    logger.error( "JFileChooser initialization error" );
                }
            };
            jFileChooserInitializer.addFooListener( l );
            }
        return jFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        JFileChooserInitializer jfci    = getJFileChooserInitializer();
        WaitingJDialogWB        dialog;

        if( ! jfci.isReady() ) {
            dialog              = new WaitingJDialogWB( RemoveEmptyDirectories.this );

            final WaitingJDialogWB d = dialog;

            Runnable doRun = new Runnable()
            {
                @Override
                public void run()
                {
                    d.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                    d.setVisible( true );
                }
            };

            try {
                SwingUtilities.invokeAndWait( doRun  );
                }
            catch( InvocationTargetException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
            catch( InterruptedException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }

            }
        return jfci.getJFileChooser();
    }

//    protected void jButtonTmpRefreshTreeMouseMouseClicked(MouseEvent event)
//    {
//        final JTree jTreeDir  = getJTree();
//
//        // TODO: remove this - just to fix !
//        jTreeDir.setModel( null );
//        jTreeDir.setModel( treeModel );
//        expandAllRows();
//    }
//
//    protected void jButtonTmpClearTreeMouseMouseClicked( MouseEvent event )
//    {
//        this.treeModel.clear();
//
//        logger.info( "jButtonTmpClearTreeMouseMouseClicked" );
//    }

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
                //JList<File>     rootJList   = super.getListRootDirectories();
                //ListModel<File> model       = rootJList.getModel();
                DefaultListModel<File> model = super.getJListRootDirectoriesModel();

//                rootFile = jfc.getSelectedFile();
//                logger.info( "selected dir:" + rootFile );

                File[] files = jfc.getSelectedFiles();
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
            }
    }

    @Override
    protected void btnStartScan_mouseClicked( MouseEvent event )
    {
        if( super.getBtnStartScan().isEnabled() ) {
            logger.info( "btnStartScan_mouseClicked" );

            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    startFind();
                }
            };
            new Thread( r ).start();
            // NOTE: do not use of SwingUtilities.invokeLater( r );
        }
    }

    @Override
    protected void btnCancel_mouseClicked( MouseEvent event )
    {
        logger.info( "btnCancel_mouseClicked" );
        if( super.getBtnCancel().isEnabled() ) {
            this.cancelCalled = true;

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

            treeModel.setSelectAllLeaf( true );
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
