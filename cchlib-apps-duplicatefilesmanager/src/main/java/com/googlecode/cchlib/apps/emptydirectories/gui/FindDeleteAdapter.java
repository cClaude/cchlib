package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.ScanIOException;
import com.googlecode.cchlib.apps.emptydirectories.file.lookup.DefaultEmptyDirectoriesLookup;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable1;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 *
 */
public class FindDeleteAdapter
{
    private static final Logger LOGGER = Logger.getLogger( FindDeleteAdapter.class );

    private final FileListModel        listModel;
    private final FolderTreeModelable1 treeModel;
    private final FindDeleteListener   listener;
    private boolean isCancel;

    /**
     *
     */
    public FindDeleteAdapter(
        final FileListModel         listModel,
        final FolderTreeModelable1  treeModel,
        final FindDeleteListener    listener
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
        LOGGER.info( "doFind() thread started" );

        this.isCancel = false;

        final EmptyDirectoriesLookup<FileFilter>    emptyDirs   = new DefaultEmptyDirectoriesLookup( this.listModel );
        final UpdateJTreeListeners                  listener    = new UpdateJTreeListeners();

        emptyDirs.addListener( listener );

        final Runnable doRun = () -> {
            try {
                emptyDirs.lookup();

                LOGGER.info(
                        "treeModel.size(): " + ((this.treeModel == null) ? null : Integer.valueOf( this.treeModel.size()) )
                );
            }
            catch( CancelRequestException | ScanIOException cancelRequestException )  { // $codepro.audit.disable logExceptions
                LOGGER.info( "Cancel received" );

                // Call done, to cleanup layout.
                listener.findDone();
            }
            findEnd();
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
            return FindDeleteAdapter.this.isCancel;
        }
        @Override
        public void newEntry( final EmptyFolder folder )
        {
            SwingUtilities.invokeLater(
                () -> {
                    FindDeleteAdapter.this.treeModel.add( folder );
            });
        }
        @Override
        public void findStarted()
        {
            LOGGER.info( "findStarted()" );

            try {
                SwingUtilities.invokeAndWait(
                        FindDeleteAdapter.this.treeModel::clear);
                }
            catch( InvocationTargetException | InterruptedException e ) {
                LOGGER.error( "findStarted() *** ERROR", e );
                }
        }
        @Override
        public void findDone()
        {
            FindDeleteAdapter.this.listener.findTaskDone( FindDeleteAdapter.this.isCancel );

            LOGGER.info( "findDone()" );
        }
    }

    public void doDelete()
    {
        // Get selected list of paths
        final List<Path> selectedPaths = new ArrayList<>();

        for( final EmptyFolder ef : this.treeModel.getSelectedEmptyFolders() ) {
            selectedPaths.add( ef.getPath() );
            }

        LOGGER.info( "doDelete() : selected files count = " + selectedPaths.size() );

        assert selectedPaths.size() > 0;

        // Add deepest paths at the beginning
        Collections.sort( selectedPaths, (final Path p1, final Path p2) -> p2.getNameCount() - p1.getNameCount());

        // Delete deepest paths firsts
        for( final Path path : selectedPaths ) {
            try {
                final boolean res = Files.deleteIfExists( path );

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "DIR delete [" + path + "] => " + res );
                    }
                }
            catch( final AccessDeniedException e ) { // $codepro.audit.disable logExceptions
                LOGGER.warn( "delete AccessDeniedException  [" + path + "]" );
                }
            catch( final DirectoryNotEmptyException e ) { // $codepro.audit.disable logExceptions
                LOGGER.warn( "delete DirectoryNotEmptyException [" + path + "]" );

                final String[] files = path.toFile().list();
                LOGGER.warn( "cause content : [" + Arrays.toString( files ) + "]" );
                }
            catch( final Exception e ) {
                LOGGER.error( "delete [" + path + "]", e );
                }
            }
    }

}
