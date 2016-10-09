package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomFileFilterConfigTest
{
    private static final String STR2 = "Str2";
    private static final String STR1 = "Str1";
    private static final String JSON_STRING = "{\"excludeNames\":[\"Str2\",\"Str1\"]}";

    @Test
    public void testSave() throws JsonProcessingException
    {
        final CustomFileFilterConfig test = new CustomFileFilterConfig();

        final Set<String> values = new HashSet<>();
        values.add( STR1 );
        values.add( STR2 );

        test.setExcludeNames( values );

        final String jsonString = Json.toJSON( test );

        Assertions.assertThat( jsonString ).isEqualTo( JSON_STRING );
    }

    @Test
    public void testLoad() throws IOException
    {
        final CustomFileFilterConfig value = Json.fromJSON( JSON_STRING, CustomFileFilterConfig.class );

        final Collection<String> excludeNames = value.getExcludeNames();
        Assertions.assertThat( excludeNames ).contains( STR1 ).contains( STR2 ).hasSize( 2 );
    }
}
