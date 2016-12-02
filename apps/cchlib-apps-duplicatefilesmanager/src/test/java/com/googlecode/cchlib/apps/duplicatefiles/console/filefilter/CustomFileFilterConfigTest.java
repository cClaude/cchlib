package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.IOException;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

public class CustomFileFilterConfigTest
{
    private static final Logger LOGGER = Logger.getLogger( CustomFileFilterConfigTest.class );

    @Test
    public void testSaveExcludeNames() throws JsonProcessingException
    {
        final CustomFileFilterConfig test = new CustomFileFilterConfig();

        FileFilterConfigTestTools.populateNames( test );
        LOGGER.info( "testSaveExcludeNames() test = " + test );

        final String jsonString = JSONHelper.toJSON( test );
        LOGGER.info( "testSaveExcludeNames() jsonString = " + jsonString );

        final String expected = FileFilterConfigTestTools.JSON_FULL_OUT1;
        LOGGER.info( "testSaveExcludeNames() expected   = " + expected );

        Assertions.assertThat( jsonString ).isEqualTo( expected );
    }

    @Test
    public void testLoadExcludeNames() throws IOException, JSONHelperException
    {
        final CustomFileFilterConfig value = JSONHelper.fromJSON( FileFilterConfigTestTools.JSON_SIMPLE_IN1, CustomFileFilterConfig.class );

        final Collection<String> excludeNames = value.getExcludeNames();
        Assertions.assertThat( excludeNames ).contains( FileFilterConfigTestTools.STR1 ).contains( FileFilterConfigTestTools.STR2 ).hasSize( 2 );

        final Collection<String> excludePaths = value.getExcludePaths();
        Assertions.assertThat( excludePaths ).isNull();
    }

    @Test
    public void testLoadExcludeNames2() throws IOException, JSONHelperException
    {
        final CustomFileFilterConfig value = JSONHelper.fromJSON(
                FileFilterConfigTestTools.JSON_FULL_OUT1,
                CustomFileFilterConfig.class
                );

        Assertions.assertThat( value.getExcludeNames() )
            .contains( FileFilterConfigTestTools.STR1 )
            .contains( FileFilterConfigTestTools.STR2 )
            .hasSize( 2 );

        Assertions.assertThat( value.getExcludePaths() ).isNull();
    }

    @Test
    public void testSave() throws JsonProcessingException
    {
        final CustomFileFilterConfig test = new CustomFileFilterConfig();

        FileFilterConfigTestTools.populateNames( test );
        FileFilterConfigTestTools.populatePaths( test );

        LOGGER.info( "testSave() test = " + test );
        final String jsonString = JSONHelper.toJSON( test );

        LOGGER.info( "testSave() jsonString = " + jsonString );
        LOGGER.info( "testSave() expected   = " + FileFilterConfigTestTools.JSON_FULL_OUT3 );

        Assertions.assertThat( jsonString )
            .isEqualTo( FileFilterConfigTestTools.JSON_FULL_OUT3 );
    }

    @Test
    public void testLoad() throws IOException, JSONHelperException
    {
        final CustomFileFilterConfig value = JSONHelper.fromJSON(
                FileFilterConfigTestTools.JSON_FULL_OUT3,
                CustomFileFilterConfig.class
                );

        Assertions.assertThat( value.getExcludeNames() )
            .contains( FileFilterConfigTestTools.STR1 )
            .contains( FileFilterConfigTestTools.STR2 )
            .hasSize( 2 );

        Assertions.assertThat( value.getExcludePaths() )
            .contains( FileFilterConfigTestTools.PATH1 )
            .contains( FileFilterConfigTestTools.PATH2 )
            .hasSize( 2 );
    }
}

