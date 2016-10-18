package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Directories related tools
 */
public class DirectoriesHelper
{
    private DirectoriesHelper()
    {
        // All static
    }

    /**
     * Create parents directories of a file
     *
     * @param file File to create
     * @throws CLIParametersException
     */
    public static void createParentDirsOf( final File file )
        throws CLIParametersException
    {
        final File parentDirFile = file.getParentFile();

        if( ! parentDirFile.exists() ) {
            final Path dir = parentDirFile.toPath();
            try {
                Files.createDirectories( dir );
            }
            catch( final IOException e ) {
                throw new CLIParametersException(
                        "none (can not create parent dir)",
                        parentDirFile.getPath(),
                        e
                        );
            }
        }
   }
}
