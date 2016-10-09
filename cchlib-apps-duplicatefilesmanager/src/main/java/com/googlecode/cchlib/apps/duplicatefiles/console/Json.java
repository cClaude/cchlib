package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json
{
    private Json()
    {
        // Empty - All static
    }

    private static <I,T extends I> I serializationOf( final I original, final Class<T> clazz )
            throws JsonParseException, JsonMappingException, IOException
    {
        final String jsonInString = toJSON( original );

        final ObjectMapper mapper = new ObjectMapper();

        //final String jsonInString = mapper.writeValueAsString( original );
        return mapper.readValue( jsonInString, clazz );
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
        ) throws IOException
    {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue( jsonString, clazz );
    }

    /**
     *
     * @param jsonFile
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T load( final File jsonFile, final Class<T> clazz )
            throws JsonParseException, JsonMappingException, IOException
    {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue( jsonFile, clazz );
    }

    /**
     *
     * @param jsonFile
     * @param value
     * @throws IOException
     */
    public static <T> void toJSON( //
            final File jsonFile,
            final T    value
            ) throws IOException
    {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue( jsonFile, value );;
    }

    /**
     *
     * @param jsonFile
     * @param value
     */
    public static <T> void toJSONSafe( final File jsonFile, final T value )
    {
        try {
            toJSON( jsonFile, value );
        }
        catch( final IOException e ) {
            Console.printError( "Can not save JSON file", jsonFile, e );
        }

    }
}
