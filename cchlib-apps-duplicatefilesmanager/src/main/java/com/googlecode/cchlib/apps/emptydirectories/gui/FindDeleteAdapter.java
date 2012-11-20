package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.io.File;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.DefaultEmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.FolderTreeModelable;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 *
 */
public class FindDeleteAdapter
{
    private final static Logger logger = Logger.getLogger( FindDeleteAdapter.class );
    private MyDefaultListModel<File> listModel;
    private FolderTreeModelable treeModel;
    private FindDeleteListener listener;
    private boolean isCancel;

    /**
     *
     */
    public FindDeleteAdapter(
        final MyDefaultListModel<File>  listModel,
        final FolderTreeModelable       treeModel,
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

        final DefaultEmptyDirectoriesLookup emptyDirs   = new DefaultEmptyDirectoriesLookup( listModel );
        final UpdateJTreeListeners          listener    = new UpdateJTreeListeners();

        emptyDirs.addListener( listener );

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
                catch( CancelRequestException cancelRequestException )  { // $codepro.audit.disable logExceptions
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
        new Thread( doRun, "doFind()" ).start();
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
        public void newEntry( final EmptyFolder folder )
        {
            SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        treeModel.add( folder );
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
        }
        @Override
        public void findDone()
        {
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
