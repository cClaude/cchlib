package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelperException;

public class FileFiltersConfigTest
{
    private static final Logger LOGGER = Logger.getLogger( FileFiltersConfigTest.class );

    private static final String JSON_FULL_OUT = "{\"files\":"
            + FileFilterConfigTestTools.JSON_FULL_OUT3
            + ",\"directories\":"
            + FileFilterConfigTestTools.JSON_FULL_OUT3
            + "}";

    @Test
    public void testSave() throws JsonProcessingException
    {
        final FileFiltersConfig config = new FileFiltersConfig();

        config.setFiles( FileFilterConfigTestTools.newFileFilterConfigTestTools() );
        config.setDirectories( FileFilterConfigTestTools.newFileFilterConfigTestTools() );

        LOGGER.info( "testSave() config = " + config );

        final String jsonString = JSONHelper.toJSON( config );

        LOGGER.info( "testSave() jsonString = " + jsonString );
        LOGGER.info( "testSave() expected   = " + JSON_FULL_OUT );

        Assertions.assertThat( jsonString )
            .isEqualTo( JSON_FULL_OUT );
    }

    @Test
    public void testLoad() throws IOException, JSONHelperException
    {
        final String jsonString = JSON_FULL_OUT;
        LOGGER.info( "testLoad() jsonString = " + jsonString );

        final FileFiltersConfig config = JSONHelper.fromJSON( jsonString, FileFiltersConfig.class );

        LOGGER.info( "testLoad() config = " + config );

        runTest( config.getFiles() );
        runTest( config.getDirectories() );
    }

    private void runTest( final CustomFileFilterConfig cffc )
    {
      Assertions.assertThat( cffc ).isNotNull();

      Assertions.assertThat( cffc.getExcludeNames() )
          .contains( FileFilterConfigTestTools.STR1 )
          .contains( FileFilterConfigTestTools.STR2 )
          .hasSize( 2 );

      Assertions.assertThat( cffc.getExcludePaths() )
          .contains( FileFilterConfigTestTools.PATH1 )
          .contains( FileFilterConfigTestTools.PATH2 )
          .hasSize( 2 );
    }
}

