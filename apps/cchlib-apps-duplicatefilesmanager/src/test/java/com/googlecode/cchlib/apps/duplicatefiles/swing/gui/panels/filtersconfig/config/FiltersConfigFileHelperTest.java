package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.File;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.apps.duplicatefiles.TestResultsHelper;
import com.googlecode.cchlib.apps.duplicatefiles.common.JSONHelperException;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.MyResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

public class FiltersConfigFileHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( FiltersConfigFileHelperTest.class );

    @Test
    public void test_LoadJSON() throws ResourcesLoaderException, JSONHelperException
    {
        final InputStream   jsonIn = MyResourcesLoader.getResourceAsStream( "JPanelConfig.json" );
        final FiltersConfig result = FiltersConfigFileHelper.load( jsonIn );

        LOGGER.info
        ( "test_LoadJSON: " + result );
    }

    @Test(expected=JSONHelperException.class)
    public void test_LoadJSON_crashConfig() throws ResourcesLoaderException, JSONHelperException
    {
        final InputStream   jsonIn = MyResourcesLoader.getResourceAsStream( "crashConfig.json" );
        final FiltersConfig result = FiltersConfigFileHelper.load( jsonIn );

        LOGGER.info( "test_LoadJSON_crashConfig: " + result );
    }

    @Test
    public void test_LoadJSON_testConfig() throws ResourcesLoaderException, JSONHelperException
    {
        final InputStream   jsonIn = MyResourcesLoader.getResourceAsStream( "testConfig.json" );
        final FiltersConfig result = FiltersConfigFileHelper.load( jsonIn );

        LOGGER.info( "test_LoadJSON_testConfig: " + result );
    }

    @Test
    public void test_SaveJSON() throws JSONHelperException
    {
        final FiltersConfig value    = new FiltersConfig();
        final File          jsonFile = TestResultsHelper.newFile(
                "FiltersConfigFileHelperTest.SaveJSON.json"
                );

        LOGGER.info( "test_SaveJSON: " + value );
        FiltersConfigFileHelper.save( jsonFile, value );
    }

    /*
     * This "test" was design to generate new configuration file
     */
    @Test
    public void test_LoadJSON_testConfig_SaveJSON() throws ResourcesLoaderException, JSONHelperException
    {
        final String sourceFilename     = "testConfig.json";
        final String destinatonFilename = "FiltersConfigFileHelperTest." + sourceFilename;

        final InputStream   jsonIn = MyResourcesLoader.getResourceAsStream( sourceFilename );
        final FiltersConfig config = FiltersConfigFileHelper.load( jsonIn );

        LOGGER.info( "test_LoadJSON_testConfig_SaveJSON: config = " + config );

        final File jsonFile = TestResultsHelper.newFile( destinatonFilename );

        LOGGER.info( "test_LoadJSON_testConfig_SaveJSON: " + destinatonFilename );
        FiltersConfigFileHelper.save( jsonFile, config );
    }
}
