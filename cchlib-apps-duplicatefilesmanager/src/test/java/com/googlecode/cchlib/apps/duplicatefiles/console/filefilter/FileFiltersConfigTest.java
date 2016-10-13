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
            + FileFilterConfigTestTools.JSON_FULL_OUT2
            + ",\"directories\":"
            + FileFilterConfigTestTools.JSON_FULL_OUT2
            + "}";
    @Test
    public void testSave() throws JsonProcessingException
    {
        final FileFiltersConfig config = new FileFiltersConfig();

        config.setFiles( FileFilterConfigTestTools.newFileFilterConfigTestTools() );
        config.setDirectories( FileFilterConfigTestTools.newFileFilterConfigTestTools() );

        final String jsonString = JSONHelper.toJSON( config );

        LOGGER.info( "jsonString = " + jsonString );

        Assertions.assertThat( jsonString )
            .isEqualTo( JSON_FULL_OUT );
    }

    @Test
    public void testLoad() throws IOException, JSONHelperException
    {
        final FileFiltersConfig config = JSONHelper.fromJSON( JSON_FULL_OUT, FileFiltersConfig.class );

        runTest( config.getFiles() );
        runTest( config.getDirectories() );
    }

    private void runTest( final CustomFileFilterConfig cffc )
    {
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

