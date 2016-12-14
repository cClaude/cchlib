package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;

public class TestResultsHelper
{
    public final static String TESTS_PATH = "testResults";

    public static final File getTestsPath()
    {
        final File file = new File( TESTS_PATH );

        file.mkdir();

        return file;
    }

    public static String getResultsPath( final String filename )
    {
        return TESTS_PATH + '/' + filename;
    }

    public static File newFile( final String filename )
    {
        return new File( getTestsPath(), filename );
    }
}
