package com.googlecode.cchlib.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class helper to test your serialization using JSON
 *
 * @since 4.2
 * @see JSONHelper
 */
public class JSONTestHelper
{
    private JSONTestHelper()
    {
        // All static
    }

    /**
     * Clone a object using JSON (using default presentation)
     *
     * @param <I>
     *            Type of object to serialize
     * @param <T>
     *            Concrete type to build object
     * @param original
     *            Object to serialize
     * @param type
     *            Expected type
     * @return a cloned object throw JSON serialization
     * @throws IOException
     *             if any error occur during serialization
     * @throws JSONHelperException
     *             if any error occur during de-serialization
     */
    @SuppressWarnings({"null","squid:S1160"})
    @Nullable
    public static <I, T extends I> I serializationOverJSON(
        @Nonnull final I        original,
        @Nonnull final Class<T> type
        ) throws IOException, JSONHelperException
    {
        return serializationOverJSON( original, type, null, null );
    }


    /**
     * Clone a object using JSON
     *
     * @param <I>
     *            Type of object to serialize
     * @param <T>
     *            Concrete type to build object
     * @param original
     *            Object to serialize
     * @param type
     *            Expected type
     * @param printMode
     *            Define JSON presentation
     * @param serializationInclusionMode
     *            Fix setting default POJO property inclusion strategy for serialization
     *            (see {@link ObjectMapper#setSerializationInclusion(Include)}). If value
     *            is null, use default strategy.
     * @return a cloned object throw JSON serialization
     * @throws IOException
     *             if any error occur during serialization
     * @throws JSONHelperException
     *             if any error occur during de-serialization
     */
    @SuppressWarnings({"null","squid:S1160"})
    @Nullable
    public static <I, T extends I> I serializationOverJSON(
        @Nonnull final I        original,
        @Nonnull final Class<T> type,
        final Set<JSONPrintMode>    printMode,
        final Include           serializationInclusionMode
        ) throws IOException, JSONHelperException
    {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        JSONHelper.save( output, original, printMode, serializationInclusionMode );

        @SuppressWarnings("squid:S1488") // immediately returned (to show cast)
        final I result = reload( output.toByteArray(), type );

        return result;
    }

    @SuppressWarnings({ "squid:RedundantThrowsDeclarationCheck" })
    @Nullable
    private static <T> T reload(
        @Nonnull final byte[]   json,
        @Nonnull final Class<T> type
        ) throws IOException, JsonParseException, JsonMappingException
    {
        return new ObjectMapper().readValue( json, type );
    }
}
