package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.lang.StringHelper;

//NOT public
abstract class JPanelSearchingFilters extends JPanelSearchingLayoutWB // NOSONAR
{
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingFilters.class );
    private static final long serialVersionUID = 1L;

    private long    pass1BytesCount;
    private int     pass1FilesCount;

    protected JPanelSearchingFilters( final int nThreads )
    {
        super( nThreads );
    }

    private boolean acceptForDefaultFilesFileFilter( //
        final boolean skipHiddenFiles, //
        final boolean skipReadOnlyFiles, //
        final File    currentFile //
        )
    {
        if( currentFile.isFile() ) {

            // Hidden files
            if( skipHiddenFiles && currentFile.isHidden() ) {
                return false;
            }

            if( skipReadOnlyFiles&& !currentFile.canWrite() ) {
                return false;
            }

            this.pass1FilesCount++;
            this.pass1BytesCount += currentFile.length();

            return true;
        }

        return false;
    }

    private boolean acceptForDirectoriesFileFilter( final boolean skipHidden, final File currentFile )
    {
        // Hidden files
        if( skipHidden  && currentFile.isHidden() ) {
            return false;
        }

        setPass1DisplayFile( currentFile );

        return true;
    }

    private boolean acceptForDirectoriesFileFilter1( //
        final boolean  skipHidden, //
        final Pattern  regex, //
        final String[] dirNames, //
        final File     currentFile //
        )
    {
        // Hidden files
        if( skipHidden && currentFile.isHidden() ) {
            return false;
        }

        // RegEx
        if( (regex != null) && regex.matcher(currentFile.getName()).matches() ) {
            return false;
        }

        // Test names ?
        final String name = currentFile.getName().toLowerCase();

        for( int i=0; i<dirNames.length; i++ ) {
            if( name.equals( dirNames[i] ) ) {
                return false;
            }
        }

        setPass1DisplayFile( currentFile );

        return true;
    }

    private boolean acceptForDirectoriesFileFilter2( //
        final boolean  skipHiddenFolders, //
        final Pattern  regex, //
        final String[] dirNames, //
        final File     currentFile //
        )
    {
        // Hidden files
        if( skipHiddenFolders && currentFile.isHidden() ) {
            return false;
        }

        // RegEx
        if( (regex != null) && regex.matcher(currentFile.getName()).matches() ) {
            return false;
        }

        // Test names ?
        final String name = currentFile.getName().toLowerCase();

        for( int i=0; i<dirNames.length; i++ ) {
            if( name.equals( dirNames[i] ) ) {
                setPass1DisplayFile( currentFile );

                return true;
            }
        }

        return false;
    }

    private boolean acceptForExcludeFilesFileFilter( //
        final boolean  skipHiddenFiles, //
        final boolean  skipReadOnlyFiles, //
        final Pattern  regex, //
        final String[] fileExts, //
        final File     currentFile //
        )
    {
        if( currentFile.isFile() ) {

            // Hidden files
            if( skipHiddenFiles && currentFile.isHidden() ) {
                return false;
            }

            if( skipReadOnlyFiles && !currentFile.canWrite() ) {
                return false;
            }

            // RegEx
            if( (regex != null) && ! regex.matcher(currentFile.getName()).matches() ) {
                return false;
            }

            // Extensions
            final String name = currentFile.getName().toLowerCase();

            for( int i=0; i<fileExts.length; i++ ) {
                if( name.endsWith( fileExts[i] ) ) {
                    return false;
                }
            }

            this.pass1FilesCount++;
            this.pass1BytesCount += currentFile.length();

            return true;
        }
        return false;
    }

    private boolean acceptForIncludeFilesFileFilter( //
        final boolean  skipHiddenFiles, //
        final boolean  skipReadOnlyFiles, //
        final Pattern  regex, //
        final String[] fileExts, //
        final File     currentFile //
        )
    {
        if( currentFile.isFile() ) {

            // Hidden files
            if( skipHiddenFiles && currentFile.isHidden() ) {
                return false;
                }

            // ReadOnly files
            if( skipReadOnlyFiles && !currentFile.canWrite() ) {
                return false;
                }

            // RegEx
            if( (regex != null) && regex.matcher(currentFile.getName()).matches() ) {
                this.pass1FilesCount++;
                this.pass1BytesCount += currentFile.length();

                return true;
                }

            // Extensions
            final String name = currentFile.getName().toLowerCase();

            for( int i=0; i<fileExts.length; i++ ) {
                if( name.endsWith( fileExts[i] ) ) {
                    this.pass1FilesCount++;
                    this.pass1BytesCount += currentFile.length();

                    return true;
                    }
                }

            return false;
        }
        return false;
    }

    protected final long getPass1BytesCount()
    {
        return this.pass1BytesCount;
    }

    protected final int getPass1FilesCount()
    {
        return this.pass1FilesCount;
    }

    protected FileFilter newDirectoriesFileFilter(
        final FileFilterBuilders fileFilterBuilders
        )
    {
        final boolean skipHidden = fileFilterBuilders.isIgnoreHiddenDirs();

        final FileFilterBuilder excludeDirectoriesFileFilterBuilder = fileFilterBuilders.getExcludeDirs();

        if( excludeDirectoriesFileFilterBuilder != null ) {
            final Pattern regex = excludeDirectoriesFileFilterBuilder.getRegExp();

            //TODO: construire un automate pour tester
            //      une chaîne par rapport à un groupe de motif
            final String[] dirNames  = excludeDirectoriesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "newDirectoriesFileFilter 1: regex=" + regex );
                LOGGER.debug( "newDirectoriesFileFilter 1: skipHidden=" + skipHidden );
                LOGGER.debug( "newDirectoriesFileFilter 1: dirNames=" + Arrays.toString( dirNames ) );
            }

            return f -> acceptForDirectoriesFileFilter1( skipHidden, regex, dirNames, f );
        }
        else {
            final FileFilterBuilder includeDirectoriesFileFilterBuilder = fileFilterBuilders.getIncludeDirs();

            if( includeDirectoriesFileFilterBuilder != null ) {
                 final Pattern regex = includeDirectoriesFileFilterBuilder.getRegExp();

                //TODO: construire un automate pour tester
                //      une chaîne par rapport à un groupe de motif
                final String[] dirNames  = includeDirectoriesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "newDirectoriesFileFilter 2: regex=" + regex );
                    LOGGER.debug( "newDirectoriesFileFilter 2: skipHidden=" + skipHidden );
                    LOGGER.debug( "newDirectoriesFileFilter 2: dirNames=" + Arrays.toString( dirNames ) );
                }

                return f -> acceptForDirectoriesFileFilter2( skipHidden, regex, dirNames, f );
            }
            else {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "newDirectoriesFileFilter 3: skipHidden=" + skipHidden );
                }

                return f -> acceptForDirectoriesFileFilter( skipHidden, f );
            }
        }
    }

    protected FileFilter newFilesFileFilter(
        final FileFilterBuilders fbs
        )
    {
        final boolean skipHidden   = fbs.isIgnoreHiddenFiles();
        final boolean skipReadOnly = fbs.isIgnoreReadOnlyFiles();

        final FileFilterBuilder includeFilesFileFilterBuilder = fbs.getIncludeFiles();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "includeFilesFileFilterBuilder="+includeFilesFileFilterBuilder);
        }

        if( includeFilesFileFilterBuilder != null ) {
            final Pattern  regex    = includeFilesFileFilterBuilder.getRegExp();
            final String[] fileExts = includeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "newFilesFileFilter 1: regex=" + regex );
                LOGGER.debug( "newFilesFileFilter 1: skipHidden=" + skipHidden );
                LOGGER.debug( "newFilesFileFilter 1: skipReadOnly=" + skipReadOnly );
                LOGGER.debug( "newFilesFileFilter 1: fileExts=" + Arrays.toString( fileExts ) );
            }

            return f -> acceptForIncludeFilesFileFilter( skipHidden, skipReadOnly, regex, fileExts, f );
        }
        else {
            final FileFilterBuilder excludeFilesFileFilterBuilder = fbs.getExcludeFiles();

            if( excludeFilesFileFilterBuilder != null ) {
                final Pattern  regex    = excludeFilesFileFilterBuilder.getRegExp();
                final String[] fileExts = excludeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "newFilesFileFilter 2: regex=" + regex );
                    LOGGER.debug( "newFilesFileFilter 2: skipHidden=" + skipHidden );
                    LOGGER.debug( "newFilesFileFilter 2: skipReadOnly=" + skipReadOnly );
                    LOGGER.debug( "newFilesFileFilter 2: fileExts=" + Arrays.toString( fileExts ) );
                }

                return f -> acceptForExcludeFilesFileFilter( skipHidden, skipReadOnly, regex, fileExts, f );
            }
            else {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "newFilesFileFilter 3: skipHidden=" + skipHidden );
                    LOGGER.debug( "newFilesFileFilter 3: skipReadOnly=" + skipReadOnly );
                }

                // Minimum file filter
                return f -> acceptForDefaultFilesFileFilter( skipHidden, skipReadOnly, f );
            }
        }
    }

    protected final void setPass1BytesCount( final long pass1BytesCount )
    {
        this.pass1BytesCount = pass1BytesCount;
    }

    protected abstract void setPass1DisplayFile( final File file );

    protected final void setPass1FilesCount( final int pass1FilesCount )
    {
        this.pass1FilesCount = pass1FilesCount;
    }
}
