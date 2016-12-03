package com.googlecode.cchlib.json;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
    /** NEEDDOC */
    public enum PrintMode {
        /** NEEDDOC */
        PRETTY,
        /** NEEDDOC */
        PRETTY_ARRAYS
    }

    /**
     * @see PrintMode#PRETTY
     */
    public static final Set<PrintMode> PRETTY_PRINT =
            Collections.unmodifiableSet( EnumSet.of( PrintMode.PRETTY ) );

    /**
     * @see PrintMode
     */
    public static final Set<PrintMode> COMPACT_PRINT =
            Collections.unmodifiableSet( EnumSet.noneOf( PrintMode.class ) );

    private JSONHelper()
    {
        // Empty - All static
    }

    /**
     * Convert object {@code value} into a JSON String
     *
     * @param <T>
     *            Type of object to convert
     * @param value
     *            Object to convert
     * @return a JSON String
     * @throws JsonProcessingException
     *             if any
     */
    public static <T> String toJSON( //
        final T value
        ) throws JsonProcessingException
    {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString( value );
    }

    /**
     * Load an object from a JSON String
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonString
     *            JSON String
     * @param type
     *            Expected type
     * @return an object of type <code>type<code>
     *
     * @throws JSONHelperException
     *             if any
     */
    public static <T> T fromJSON( //
        final String   jsonString,
        final Class<T> type
        ) throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue( jsonString, type );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     * Load an object from a JSON File
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonFile
     *            JSON File
     * @param type
     *            Expected type
     * @return an object of type <code>type<code>
     *
     * @throws JSONHelperException
     *             if any
     */
    public static <T> T load( final File jsonFile, final Class<T> type )
        throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue( jsonFile, type );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     * Load an object from a JSON Stream
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonStream
     *            JSON Stream
     * @param type
     *            Expected type
     * @return an object of type {@code type}
     *
     * @throws JSONHelperException
     *             if any
     */
    public static <T> T load(
        final InputStream jsonStream,
        final Class<T>    type
        )
        throws JSONHelperException
    {
        final ObjectMapper        mapper = new ObjectMapper();
        final BufferedInputStream in;

        if( jsonStream instanceof BufferedInputStream ) {
            in = (BufferedInputStream)jsonStream;
        } else {
            in = new BufferedInputStream( jsonStream );
        }

        try {
            return mapper.readValue( in, type );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    /**
     * Load an object from a JSON File
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonFile
     *            JSON File
     * @param type
     *            Type reference.
     * @return an object of type {@code type}
     *
     * @throws JSONHelperException
     *             if any
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
     * Store {@code value} in file {@code jsonFile}
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonFile
     *            File to use to store {@code value}
     * @param value
     *            Object to serialize
     * @param prettyJson
     *            If true format output
     * @throws JSONHelperException
     * @deprecated Use {@link #save(File, Object, Set)} instead
     */
    @Deprecated
    public static <T> void toJSON( //
            final File    jsonFile,
            final T       value,
            final boolean prettyJson
            ) throws JSONHelperException
    {
        if( prettyJson ) {
            save( jsonFile, value, EnumSet.of( PrintMode.PRETTY ) );
        } else {
            save( jsonFile, value, EnumSet.noneOf( PrintMode.class ) );
        }
    }

    /**
     * Store {@code value} in file {@code jsonFile}
     *
     * @param <T>
     *            Type of object to convert
     * @param jsonFile
     *            File to use to store {@code value}
     * @param value
     *            Object to serialize
     * @param printMode
     *            Define JSON presentation
     * @throws JSONHelperException
     * @see PrintMode
     * @see #COMPACT_PRINT
     * @see #PRETTY_PRINT
     */
    public static <T> void save( //
            final File           jsonFile,
            final T              value,
            final Set<PrintMode> printMode
            ) throws JSONHelperException
    {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion( Include.NON_NULL );

        try {
            if( isUseStandardPrettyPrint( printMode ) ) {
                prettyPrint( jsonFile, value, mapper );
            }
            else if( isUseArraysPrettyPrint( printMode ) ) {
                prettyPrintArray( jsonFile, value, mapper );
            } else {
                mapper.writeValue( jsonFile, value );
            }
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    private static <T> boolean isContaint( final Collection<T> printMode, final T mode )
    {
        if( printMode != null ) {
            return printMode.contains( mode );
        }

        return false;
    }

    private static boolean isUseArraysPrettyPrint( final Set<PrintMode> printMode )
    {
        if( isContaint( printMode, PrintMode.PRETTY_ARRAYS  ) ) {
            return true;
        }
        return isUseArraysPrettyPrint();
    }


    private static boolean isUseStandardPrettyPrint( final Set<PrintMode> printMode )
    {
        if( isContaint( printMode, PrintMode.PRETTY ) ) {
            return true;
        }

        return isUseStandardPrettyPrint() ;
    }

    private static boolean isUseStandardPrettyPrint()
    {
        // NEEDDOC
        return Boolean.getBoolean( "UseStandardPrettyPrint" );
    }

    private static boolean isUseArraysPrettyPrint()
    {
        // NEEDDOC
        return Boolean.getBoolean( "UseArraysPrettyPrint" );
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private static <T> void prettyPrint(
            final File         jsonFile,
            final T            value,
            final ObjectMapper mapper
            )
        throws
            IOException,
            JsonGenerationException,
            JsonMappingException
    {
        mapper.writerWithDefaultPrettyPrinter().writeValue( jsonFile, value );
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private static <T> void prettyPrintArray(
            final File         jsonFile,
            final T            value,
            final ObjectMapper mapper
            )
        throws
            IOException,
            JsonGenerationException,
            JsonMappingException
    {
        final DefaultPrettyPrinter pp = new DefaultPrettyPrinter();

        pp.indentArraysWith( DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );

        mapper.writer( pp ).writeValue( jsonFile, value );
    }
}
