package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.ScanIOException; // $codepro.audit.disable unnecessaryImport
import com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 *
 */
public class FindDeleteAdapter
{
    private static final Logger logger = Logger.getLogger( FindDeleteAdapter.class );
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
                        "treeModel.size(): " + ((treeModel == null) ? null : Integer.valueOf( treeModel.size()) )
                        );
                    }
                catch( CancelRequestException | ScanIOException cancelRequestException )  { // $codepro.audit.disable logExceptions
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

            try {
                SwingUtilities.invokeAndWait(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                treeModel.clear();
                            }
                        });
                }
            catch( InvocationTargetException | InterruptedException e ) {
                logger.error( "findStarted() *** ERROR", e );
                }
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
        // Get selected list of paths
        List<Path> selectedPaths = new ArrayList<>();

        for( EmptyFolder ef : treeModel.getSelectedEmptyFolders() ) {
            selectedPaths.add( ef.getPath() );
            }

        logger.info( "doDelete() : selected files count = " + selectedPaths.size() );

        assert selectedPaths.size() > 0;

        // Add deepest paths at the beginning
        Collections.sort( selectedPaths, new Comparator<Path>(){
            @Override
            public int compare( Path p1, Path p2 )
            {
                return p2.getNameCount() - p1.getNameCount();
            }
        });

        // Delete deepest paths firsts
        for( Path path : selectedPaths ) {
            try {
                boolean res = Files.deleteIfExists( path );

                if( logger.isDebugEnabled() ) {
                    logger.debug( "DIR delete [" + path + "] => " + res );
                    }
                }
            catch( AccessDeniedException e ) { // $codepro.audit.disable logExceptions
                logger.warn( "delete AccessDeniedException  [" + path + "]" );
                }
            catch( DirectoryNotEmptyException e ) { // $codepro.audit.disable logExceptions
                logger.warn( "delete DirectoryNotEmptyException [" + path + "]" );

                String[] files = path.toFile().list();
                logger.warn( "cause content : [" + Arrays.toString( files ) + "]" );
                }
            catch( Exception e ) {
                logger.error( "delete [" + path + "]", e );
                }
            }
    }

}
