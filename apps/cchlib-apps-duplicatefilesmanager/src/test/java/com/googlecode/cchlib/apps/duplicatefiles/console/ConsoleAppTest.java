package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import com.googlecode.cchlib.apps.duplicatefiles.ConsoleApp;
import com.googlecode.cchlib.io.FileHelper;

public class ConsoleAppTest 
{
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String LOG_PATH      = ".logs/";

    @Test
    public void testJSONConfig1() throws NoSuchAlgorithmException
    {
        ConsoleApp.main( createArgs(  "shareCC-1.json", "json-filefilter-1.json" ) );
    }

    @Test
    public void testJSONConfig2() throws NoSuchAlgorithmException
    {
        ConsoleApp.main( createArgs(  "shareCC-2.json", "json-filefilter-2.json" ) );
    }

    @Test
    public void testJSONConfig3() throws NoSuchAlgorithmException
    {
        ConsoleApp.main( createArgs(  "shareCC-3.json", "json-filefilter-3.json" ) );
    }

    private String[] createArgs( final String outputJSonFilename, final String inputJSonFilename )
    {
        final String[] params = {
                "-directory",FileHelper.getTmpDirFile().getPath(),
                "-json", LOG_PATH + outputJSonFilename,
                "--files-filter-from-file", RESOURCE_PATH + inputJSonFilename,
        };
        return params;
    }
}
