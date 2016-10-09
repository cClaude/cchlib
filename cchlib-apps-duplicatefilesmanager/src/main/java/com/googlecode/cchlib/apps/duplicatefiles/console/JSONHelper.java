package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper to JSON Serialization
 */
public class JSONHelper
{
    private JSONHelper()
    {
        // Empty - All static
    }

    static <T> String toJSON( //
        final T value
        ) throws JsonProcessingException
    {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString( value );
    }

    static <T> T fromJSON( //
        final String   jsonString,
        final Class<T> clazz
        ) throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue( jsonString, clazz );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     *
     * @param jsonFile
     * @param clazz
     * @return
     * @throws JSONHelperException
     */
    public static <T> T load( final File jsonFile, final Class<T> clazz )
        throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue( jsonFile, clazz );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     *
     * @param jsonFile
     * @param value
     * @throws JSONHelperException
     */
    public static <T> void toJSON( //
            final File jsonFile,
            final T    value
            ) throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue( jsonFile, value );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }
}
