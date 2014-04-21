package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.lang.StringHelper;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

//NOT public
abstract class JPanelSearchingFilters extends JPanelSearchingDisplay
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingFilters.class );

    private int     _pass1FilesCount;
    private long    _pass1BytesCount;
    private File    displayFile;

    protected FileFilter createFilesFileFilter(
        final FileFilterBuilders   fbs
        )
    {
        final boolean skipHidden   = fbs.isIgnoreHiddenFiles();
        final boolean skipReadOnly = fbs.isIgnoreReadOnlyFiles();

        final FileFilterBuilder includeFilesFileFilterBuilder = fbs.getIncludeFiles();
        LOGGER.info( "includeFilesFileFilterBuilder="+includeFilesFileFilterBuilder);

        if( includeFilesFileFilterBuilder != null ) {
            final Pattern regex        = includeFilesFileFilterBuilder.getRegExp();
            final String[] fileExts     = includeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
            final int      fileExtsL    = fileExts.length;

            LOGGER.info( "createFilesFileFilter: case1");
            LOGGER.info( "files regex=" + regex );
            LOGGER.info( "files skipHidden=" + skipHidden );
            LOGGER.info( "files skipReadOnly=" + skipReadOnly );
            LOGGER.info( "fileExtsL=" + fileExtsL);
            LOGGER.info( "fileExts=" + includeFilesFileFilterBuilder.getNamePart() );

            final FileFilter ff = f -> {
                if( f.isFile() ) {

                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                            }
                        }

                    // ReadOnly files
                    if( skipReadOnly ) {
                        if( !f.canWrite() ) {
                            return false;
                            }
                        }

                    // RegEx
                    if( regex != null ) {
                        if( regex.matcher(f.getName()).matches() ) {
                            _pass1FilesCount++;
                            _pass1BytesCount += f.length();
                            return true;
                            }
                        }

                    // Extensions
                    final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                    for(int i=0;i<fileExtsL;i++) {
                        if(name.endsWith( fileExts[i] )) {
                            _pass1FilesCount++;
                            _pass1BytesCount += f.length();
                            return true;
                            }
                        }

                    return false;
                }
                return false;
            };

            return ff;
        }
        else {
            final FileFilterBuilder excludeFilesFileFilterBuilder = fbs.getExcludeFiles();

            if( excludeFilesFileFilterBuilder != null ) {
                final Pattern regex        = excludeFilesFileFilterBuilder.getRegExp();

                final String[] fileExts  = excludeFilesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
                final int      fileExtsL = fileExts.length;

                LOGGER.info( "createFilesFileFilter: case2");
                LOGGER.info( "files regex=" + regex );
                LOGGER.info( "files skipHidden=" + skipHidden );
                LOGGER.info( "files skipReadOnly=" + skipReadOnly );
                LOGGER.info( "fileExtsL=" + fileExtsL);
                LOGGER.info( "fileExts=" + excludeFilesFileFilterBuilder.getNamePart() );

                return f -> {
                    if( f.isFile() ) {
                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                return false;
                            }
                        }
                        if( skipReadOnly ) {
                            if( !f.canWrite() ) {
                                return false;
                            }
                        }
                        // RegEx
                        if( regex != null ) {
                            if( ! regex.matcher(f.getName()).matches() ) {
                                return false;
                            }
                        }
                        // Extensions
                        final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                        for(int i=0;i<fileExtsL;i++) {
                            if(name.endsWith( fileExts[i] )) {
                                return false;
                            }
                        }
                        _pass1FilesCount++;
                        _pass1BytesCount += f.length();
                        return true;
                    }
                    return false;
                };
            }
            else {
                LOGGER.info( "createFilesFileFilter: case3");
                // Minimum file filter
                return f -> {
                    if( f.isFile() ) {
                        // Hidden files
                        if( skipHidden ) {
                            if( f.isHidden() ) {
                                return false;
                            }
                        }
                        if( skipReadOnly ) {
                            if( !f.canWrite() ) {
                                return false;
                            }
                        }
                        _pass1FilesCount++;
                        _pass1BytesCount += f.length();
                        return true;
                    }
                    return false;
                };
            }
        }
    }

    protected FileFilter createDirectoriesFileFilter(
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
            final int      dirNamesL = dirNames.length;

            LOGGER.info( "dirs regex=" + regex );
            LOGGER.info( "dirs skipHidden=" + skipHidden );
            LOGGER.info( "dirNamesL=" + dirNamesL );
            LOGGER.info( "dirNames=" + excludeDirectoriesFileFilterBuilder.getNamePart() );

            return f -> {
                // Hidden files
                if( skipHidden ) {
                    if( f.isHidden() ) {
                        return false;
                    }
                }
                // RegEx
                if( regex != null ) {
                    if( regex.matcher(f.getName()).matches() ) {
                        return false;
                    }
                }
                // Test names ?
                final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                for(int i=0;i<dirNamesL;i++) {
                    if(name.equals( dirNames[i] )) {
                        return false;
                    }
                }

                displayFile = f;
                return true;
            };
        }
        else {
            final FileFilterBuilder includeDirectoriesFileFilterBuilder = fileFilterBuilders.getIncludeDirs();

            if( includeDirectoriesFileFilterBuilder != null ) {
                 final Pattern regex = includeDirectoriesFileFilterBuilder.getRegExp();

                //TODO: construire un automate pour tester
                //      une chaîne par rapport à un groupe de motif
                final String[] dirNames  = includeDirectoriesFileFilterBuilder.getNamePart().toArray( StringHelper.emptyArray() );
                final int      dirNamesL = dirNames.length;

                LOGGER.info( "dirs regex=" + regex );
                LOGGER.info( "dirs skipHidden=" + skipHidden );
                LOGGER.info( "dirNamesL=" + dirNamesL );
                LOGGER.info( "dirNames=" + includeDirectoriesFileFilterBuilder.getNamePart() );

                return f -> {
                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                        }
                    }
                    // RegEx
                    if( regex != null ) {
                        if( regex.matcher(f.getName()).matches() ) {
                            return false;
                        }
                    }
                    // Test names ?
                    final String name = f.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods

                    for(int i=0;i<dirNamesL;i++) {
                        if(name.equals( dirNames[i] )) {
                            displayFile = f;
                            return true;
                        }
                    }
                    return false;
                };
            }
            else {
                LOGGER.info( "dirs skipHidden=" + skipHidden );

                return f -> {
                    // Hidden files
                    if( skipHidden ) {
                        if( f.isHidden() ) {
                            return false;
                        }
                    }
                    displayFile = f;
                    return true;
                };
            }
        }
    }

    protected final int getPass1FilesCount()
    {
        return _pass1FilesCount;
    }

    protected final void setPass1FilesCount( final int pass1FilesCount )
    {
        this._pass1FilesCount = pass1FilesCount;
    }

    protected final long getPass1BytesCount()
    {
        return _pass1BytesCount;
    }

    protected final void setPass1BytesCount( final long pass1BytesCount )
    {
        this._pass1BytesCount = pass1BytesCount;
    }

    protected final File getDisplayFile()
    {
        return displayFile;
    }

    protected final void setDisplayFile( final File displayFile )
    {
        this.displayFile = displayFile;
    }
}
