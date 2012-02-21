package cx.ath.choisnet.tools.emptydirectories.gui;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.emptydirectories.DefaultEmptyDirectoriesLookup;
import cx.ath.choisnet.tools.emptydirectories.EmptyDirectoriesListener;
import cx.ath.choisnet.util.CancelRequestException;

/**
 *
 */
public class FindDeleteAdapter
{
    private final static Logger logger = Logger.getLogger( FindDeleteAdapter.class );
    private DefaultListModel<File> listModel;
    private FileTreeModelable treeModel;
    private FindDeleteListener listener;
    private boolean isCancel;

    /**
     *
     */
    public FindDeleteAdapter(
        final DefaultListModel<File>    listModel,
        final FileTreeModelable         treeModel,
        final FindDeleteListener        listener
        )
    {
        if( listModel == null ) {
            throw new IllegalArgumentException( "listModel" );
            }
        if( treeModel == null ) {
            throw new IllegalArgumentException( "treeModel" );
            }
        if( listener == null ) {
            throw new IllegalArgumentException( "listener" );
            }

        this.listModel = listModel;
        this.treeModel = treeModel;
        this.listener  = listener;
        this.isCancel  = false;
    }

    /**
     *
     */
    public void cancel()
    {
        this.isCancel = true;
    }

    /**
     *
     */
    public void doFind()
    {
        logger.info( "doFind() thread started" );

        this.isCancel = false;
//
//        super.getBtnAddRootDirectory().setEnabled( false );
//        super.getBtnStartScan().setEnabled( false );
//        super.getBtnCancel().setEnabled( true );
//        super.getBtnStartDelete().setEnabled( false );
//        super.getBtnSelectAll().setEnabled( false );
//        super.getBtnUnselectAll().setEnabled( false );
//        super.getJListRootDirectories().setEnabled( false );
//        super.getJListRootDirectories().clearSelection();
//        //super.getJListRootDirectories().setSelectedIndices( new int[0] );
//        super.getProgressBar().setIndeterminate( true );

        // Create a JTree and tell it to display our model
//        final JTree                     jTreeDir        = getJTreeEmptyDirectories();

//        final DefaultListModel<File>    jListRootModel  = super.getJListRootDirectoriesModel();
//        final EmptyDirectoriesFinder    emptyDirs       = new EmptyDirectoriesFinder( jListRootModel.elements() );
        final DefaultEmptyDirectoriesLookup emptyDirs   = new DefaultEmptyDirectoriesLookup( listModel.elements() );
        final UpdateJTreeListeners          listener    = new UpdateJTreeListeners();

        emptyDirs.addListener( listener );

//        treeModel = new FileTreeModel2( jTreeDir );
//        jTreeDir.setModel( treeModel );

//        jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer( treeModel ) );
//        jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( treeModel ) );
//        jTreeDir.setEditable( true );

        Runnable doRun = new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    emptyDirs.lookup();

                    logger.info(
                        "treeModel.size(): " + treeModel == null ? null : treeModel.size()
                        );
                    }
                catch( CancelRequestException e )  {
                    logger.info( "Cancel received" );

                    // Call done, to cleanup layout.
                    listener.findDone();
                    }
                findEnd();
            }
        };

        // KO Lock UI doRun.run();
        // KO Lock UI SwingUtilities.invokeAndWait( doRun );
        // KO Lock UI SwingUtilities.invokeLater( doRun );
        new Thread( doRun ).start();
    }

    private void findEnd()
    {
    }

    public class UpdateJTreeListeners implements EmptyDirectoriesListener
    {
        @Override
        public boolean isCancel()
        {
            return isCancel;
        }
        @Override
        public void newEntry( final File emptyDirectoryFile )
        {
            SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        treeModel.add( emptyDirectoryFile );
                        //
//                      if( add ) {
//                          jTreeDir.fireTreeExpanded( path )
//                          }
                    }
                });
        }
        @Override
        public void findStarted()
        {
            logger.info( "findStarted()" );

            SwingUtilities.invokeLater(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            treeModel.clear();
                        }
                    });
//            JProgressBar pBar = getProgressBar();
//            pBar.setString( "Computing..." );
//            pBar.setIndeterminate( true );
        }
        @Override
        public void findDone()
        {
//            JProgressBar pBar = getProgressBar();
//            pBar.setIndeterminate( false );
//
//            if( isCancel ) {
//                pBar.setString( "Scan canceled !" );
//                }
//            else {
//                pBar.setString( "Select file to delete" );
//                }

            listener.findTaskDone( isCancel );
            logger.info( "findDone()" );
        }
    }

    public void doDelete()
    {
        for( File f : treeModel.selectedFiles() ) {
            try {
                boolean res = f.delete();
                logger.info( "delete [" + f + "] => " + res );
                }
            catch( Exception e ) {
                logger.warn( "delete [" + f + "]", e );
                }
            }
    }

}
