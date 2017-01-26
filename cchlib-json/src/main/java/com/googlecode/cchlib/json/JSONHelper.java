package com.googlecode.cchlib.json;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Helper to JSON Serialization
 *
 * @since 4.2
 */
public class JSONHelper
{
    /**
     * @see JSONPrintMode#PRETTY
     */
    public static final Set<JSONPrintMode> PRETTY_PRINT =
            Collections.unmodifiableSet( EnumSet.of( JSONPrintMode.PRETTY ) );

    /**
     * @see JSONPrintMode
     */
    public static final Set<JSONPrintMode> COMPACT_PRINT =
            Collections.unmodifiableSet( EnumSet.noneOf( JSONPrintMode.class ) );

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
     * @throws JSONHelperException
     *             if any
     */
    public static <T> String toJSON( final T value )
        throws JSONHelperException
    {
        try {
            return new ObjectMapper().writeValueAsString( value );
        }
        catch( final JsonProcessingException e ) {
            throw new JSONHelperException( e );
        }
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
     * @return an object of type {@code T}
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
     * @return an object of type {@code T}
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
     * @param printMode
     *            Define JSON presentation
     * @param serializationInclusionMode
     *            Fix setting default POJO property inclusion strategy for serialization (see
     *            {@link ObjectMapper#setSerializationInclusion(Include)}).
     *            If value is null, use default strategy.
     * @throws JSONHelperException if any error occur
     * @see JSONPrintMode
     * @see #COMPACT_PRINT
     * @see #PRETTY_PRINT
     */
    public static <T> void save( //
        final File           jsonFile,
        final T              value,
        final Set<JSONPrintMode> printMode,
        @Nullable
        final Include        serializationInclusionMode
        ) throws JSONHelperException
    {
        try {
            createObjectWriter( printMode, serializationInclusionMode )
                .writeValue( jsonFile, value );
        }
        catch( final IOException e ) {
            throw new JSONHelperException( e );
        }
    }

    private static ObjectWriter createObjectWriter(
        final Set<JSONPrintMode> printMode,
        final Include        serializationInclusionMode
        )
    {
        final ObjectMapper mapper = new ObjectMapper();

        if( serializationInclusionMode == null ) {
            mapper.setSerializationInclusion( serializationInclusionMode );
        }

        final ObjectWriter writer;

        if( isUseArraysPrettyPrint( printMode ) ) {
            writer = JSONPrintMode.PRETTY_ARRAYS.getObjectWriter( mapper );
        }  else if( isUseStandardPrettyPrint( printMode ) ) {
            writer = JSONPrintMode.PRETTY.getObjectWriter( mapper );
        } else {
            writer = mapper.writer();
        }

        return writer;
    }

    /**
     * Store {@code value} in file {@code jsonFile}
     *
     * @param <T>
     *            Type of object to convert
     * @param out
     *            OutputStream to use to store {@code value}
     * @param value
     *            Object to serialize
     * @param printMode
     *            Define JSON presentation
     * @param serializationInclusionMode
     *            Fix setting default POJO property inclusion strategy for serialization (see
     *            {@link ObjectMapper#setSerializationInclusion(Include)}).
     *            If value is null, use default strategy.
     * @throws JSONHelperException if any error occur
     * @see JSONPrintMode
     * @see #COMPACT_PRINT
     * @see #PRETTY_PRINT
     */
    public static <T> void save(
        final OutputStream   out,
        final T              value,
        final Set<JSONPrintMode> printMode,
        final Include        serializationInclusionMode
        ) throws JSONHelperException
    {
        try {
            createObjectWriter( printMode, serializationInclusionMode )
                .writeValue( out, value );
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

    private static boolean isUseArraysPrettyPrint( final Set<JSONPrintMode> printMode )
    {
        if( isContaint( printMode, JSONPrintMode.PRETTY_ARRAYS  ) ) {
            return true;
        }
        return isUseArraysPrettyPrint();
    }

    private static boolean isUseStandardPrettyPrint( final Set<JSONPrintMode> printMode )
    {
        if( isContaint( printMode, JSONPrintMode.PRETTY ) ) {
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

    static ObjectWriter getPrettyPrintObjectWriter( final ObjectMapper mapper )
    {
        return mapper.writerWithDefaultPrettyPrinter();
    }

    static ObjectWriter getPrettyPrintArrayObjectWriter( final ObjectMapper mapper )
    {
        final PrettyPrinter        pp = mapper.getSerializationConfig().getDefaultPrettyPrinter();
        final DefaultPrettyPrinter dpp;

        if( pp instanceof DefaultPrettyPrinter ) {
            dpp = (DefaultPrettyPrinter)pp;
        } else {
            dpp = new DefaultPrettyPrinter();
        }

        dpp.indentArraysWith( DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );

        return mapper.writer( dpp );
    }
}
