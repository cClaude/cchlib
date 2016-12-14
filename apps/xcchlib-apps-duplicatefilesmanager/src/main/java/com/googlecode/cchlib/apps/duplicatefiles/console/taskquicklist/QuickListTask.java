package com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskfilter.FilterTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskhash.FileComputeTaskListener;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model.AbstractFileInfo;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model.CanonicalFileInfo;
import com.googlecode.cchlib.apps.duplicatefiles.console.taskquicklist.model.PathFileInfo;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.CommandTask;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.TaskCommon;
import com.googlecode.cchlib.apps.duplicatefiles.console.tasks.tools.CollectFileInternalTask;

/**
 * Create a filter JSON list of files (without hash)
 */
class QuickListTask
    extends TaskCommon<AbstractFileInfo>
        implements CommandTask<AbstractFileInfo>, CollectFileInternalTask.Config
{
    private final File                    directoryFile;
    private final FileComputeTaskListener listener;
    private final boolean                 isCanonicalPath;

    /**
     * Create a {@link FilterTask} based on {@code cli}
     *
     * @param cli Parameters from CLI
     * @throws CLIParametersException if any
     */
    QuickListTask( final CLIParameters cli ) throws CLIParametersException
    {
        super( cli );

        this.directoryFile   = cli.getDirectory();
        this.isCanonicalPath = cli.isCanonicalPath();
        this.listener        = cli.getFileComputeListener();
   }

    @Override // CollectFileInternalTask.Config
    public FileFilter getDirectoriesFileFilter()
    {
        return super.getDirectoriesFileFilter();
    }

    @Override // CollectFileInternalTask.Config
    public FileFilter getFilesFileFilter()
    {
        return super.getFilesFileFilter();
    }

    @Override // CollectFileInternalTask.Config
    public File getDirectoryFile()
    {
        return this.directoryFile;
    }

    @Override // CommandTask
    public List<AbstractFileInfo> doTask()
    {
        final CollectFileInternalTask<AbstractFileInfo> task
        = new CollectFileInternalTask<>( this, this::handleFile );

        return task.runDirectories();
    }

    private AbstractFileInfo handleFile( final File file )
    {
        return newFileInfo( file );
    }

    private AbstractFileInfo newFileInfo( final File file )
    {
        try {
            final AbstractFileInfo result = newAbstractFileInfo( file );

            this.listener.printCurrentFile( result, file );

            return result;
        }
        catch( final IOException e ) {
            CLIHelper.printError( "Info access error", file, e );

            return null;
        }
    }

    private AbstractFileInfo newAbstractFileInfo( final File file )
        throws IOException
    {
        if( this.isCanonicalPath ) {
            return new CanonicalFileInfo( file );
        } else {
            return new PathFileInfo( file );
        }
    }
}
