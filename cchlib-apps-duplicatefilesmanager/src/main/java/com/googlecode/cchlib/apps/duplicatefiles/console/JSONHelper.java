package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    /**
     *
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    public static <T> String toJSON( //
        final T value
        ) throws JsonProcessingException
    {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString( value );
    }

    /**
     *
     * @param jsonString
     * @param clazz
     * @return
     * @throws JSONHelperException
     */
    public static <T> T fromJSON( //
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
     * @param type
     * @return
     * @throws JSONHelperException
     */
    public static <T> T load( final File jsonFile, final TypeReference<T> type )
        throws JSONHelperException
    {
        try {
            return new ObjectMapper().readValue( jsonFile, type );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     * Store <code>value</code> in file <code>jsonFile</code>
     *
     * @param jsonFile File to use to store <code>value</code>
     * @param value Object to serialize
     * @param prettyJson If true format output
     * @throws JSONHelperException
     */
    public static <T> void toJSON( //
            final File    jsonFile,
            final T       value,
            final boolean prettyJson
            ) throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            if( prettyJson ) {
                if( isUseStandardPrettyPrint() ) {
                    prettyPrint( jsonFile, value, mapper );
                } else {
                    prettyPrintArray( jsonFile, value, mapper );
                }
            } else {
                mapper.writeValue( jsonFile, value );
            }
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    private static boolean isUseStandardPrettyPrint()
    {
        return Boolean.getBoolean( "UseStandardPrettyPrint" );
    }

    private static <T> void prettyPrint( final File jsonFile, final T value, final ObjectMapper mapper )
        throws
            IOException,
            JsonGenerationException, // NOSONAR
            JsonMappingException // NOSONAR
    {
        mapper.writerWithDefaultPrettyPrinter().writeValue( jsonFile, value );
    }

    private static <T> void prettyPrintArray( final File jsonFile, final T value, final ObjectMapper mapper )
        throws
            IOException,
            JsonGenerationException, // NOSONAR
            JsonMappingException // NOSONAR
    {
        final DefaultPrettyPrinter pp = new DefaultPrettyPrinter();

        pp.indentArraysWith( DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );

        mapper.writer(pp).writeValue( jsonFile, value );
    }


}
