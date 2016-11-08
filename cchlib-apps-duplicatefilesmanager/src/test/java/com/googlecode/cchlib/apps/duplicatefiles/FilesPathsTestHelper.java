package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import com.googlecode.cchlib.io.FileHelper;

public class FilesPathsTestHelper
{
    public static Path[] getStartPaths()
    {
        return new Path[] { getStartPath() };
     }

    public static Path getStartPath()
    {
        // TODO implements a better solution to avoid very long test !
        final Path path_home = FileHelper.getUserHomeDirFile().toPath();
        final Path path_for_tests;

        // Unix: .config folder (could be exist if running cygwin)
        Path path = path_home.resolve( ".config" );

        if( Files.isDirectory( path ) ) {
            path_for_tests = path;
        } else {
            // Windows: AppData\LocalLow
            path = path_home.resolve( "AppData" );

            if( Files.isDirectory( path ) ) {
                final Path pathLocal = path.resolve( "LocalLow" );

                if( Files.isDirectory( pathLocal ) ) {
                    path_for_tests = pathLocal;
                } else {
                    path_for_tests = path;
                }
            } else {
                path_for_tests = path;
            }
        }

        return path_for_tests;
    }

    public static File[] getStartFiles()
    {
        return new File[] { getStartPath().toFile() };
    }
}
