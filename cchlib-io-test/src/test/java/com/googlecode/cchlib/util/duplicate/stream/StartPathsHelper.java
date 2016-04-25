package com.googlecode.cchlib.util.duplicate.stream;

import java.nio.file.Files;
import java.nio.file.Path;
import com.googlecode.cchlib.io.FileHelper;

public class StartPathsHelper {

    public static Path[] getStartPaths()
    {
        // TODO implements a better solution to avoid very long test !
        final Path path_home = FileHelper.getUserHomeDirFile().toPath();
        final Path path_for_tests;

        // Windows: AppData\Local
        Path path = path_home.resolve( "AppData" );

        if( Files.isDirectory( path ) ) {
            final Path pathLocal = path.resolve( "Local" );
            if( Files.isDirectory( pathLocal ) ) {
                path_for_tests = pathLocal;
            } else {
                path_for_tests = path;
            }
        } else {
            // Unix: .config
            path = path_home.resolve( ".config" );

            if( Files.isDirectory( path ) ) {
                path_for_tests = path;
            } else {
                path_for_tests  = path_home;
            }
        }

        return new Path[] { path_for_tests };
     }

}
