package com.googlecode.cchlib.util.mappable;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 * Code review in progress.
 * <p>
 * NEEDDOC
 */
public class MappableHelper
{
    private static final String XML_CLASS_NAME = "<class name=\"";

    private MappableHelper()
    {
        // all static
    }

    /**
     * Build Map using default factory.
     *
     * @param <T> type of the object to map
     * @param object object to map
     * @return a Map view of the {@code object}
     *
     * @see MappableBuilder#toMap(Object)
     */
    public static <T> Map<String,String> toMap( @Nonnull final T object )
    {
        @SuppressWarnings("unchecked")
        final Class<T> type = (Class<T>)object.getClass();

        return toMap( object, type );
    }

    /**
     * Build Map using default factory.
     *
     * @param <T> Type of the {@code object} to examine.
     * @param <C> Type to use to examine.
     * @param object Object to examine.
     * @param type  Type to use to examine {@code object}
     * @return a Map view of the {@code object}
     * @since 4.2
     *
     * @see MappableBuilder#toMap(Object, Class)
     * @see MappableBuilder#createMappableBuilder()
     */
    public static <T extends C,C> Map<String,String> toMap(
        @Nullable final T       object,
        @Nonnull final Class<C> type
        )
    {
        return toMap(
            MappableBuilder.createMappableBuilder(),
            object,
            type
            );
    }

    /**
     * Build Map using default factory.
     *
     * @param <T> Type of the {@code object} to examine.
     * @param <C> Type to use to examine.
     * @param mappableBuilder Configured {@link MappableBuilder}
     * @param object Object to examine.
     * @param type  Type to use to examine {@code object}
     * @return a Map view of the {@code object}
     * @since 4.2
     *
     * @see MappableBuilder#toMap(Object, Class)
     * @see MappableBuilder
     * @see MappableBuilder#createMappableBuilder()
     */
    public static <T extends C,C> Map<String,String> toMap(
        @Nonnull final MappableBuilder mappableBuilder,
        @Nullable final T              object,
        @Nonnull final Class<C>        type
        )
    {
        return mappableBuilder.toMap( object, type );
    }

    /**
     * Build a XML view of the object
     *
     * @param out   Output for result
     * @param clazz Class to use to analyze {@code mappableObject}
     * @param map   Map of value
     * @throws IOException if any
     */
    @NeedDoc
    public static void toXML(
        final Appendable            out,
        final Class<?>              clazz,
        final Map<String,String>    map
        ) throws IOException
    {
        if( map == null ) {
            out.append( XML_CLASS_NAME )
               .append( clazz.getName() )
               .append( "\" /><!-- NULL OBJECT -->\n" );
            }
        else if( map.size() == 0 ) {
            out.append( XML_CLASS_NAME )
               .append( clazz.getName() )
               .append( "\" /><!-- EMPTY -->\n" );
            }
        else {
            out.append( XML_CLASS_NAME )
               .append( clazz.getName() )
               .append( "\">\n" );

            for( final Entry<String, String> entry : map.entrySet() ) {
                out.append("  <value name=\"" )
                   .append( entry.getKey() ).append("\">" )
                   .append( entry.getValue() )
                   .append( "</value>\n" );
                }

            out.append("</class>\n");
            }
    }

    /**
     * Convert a {@code mappableObject} to an XML view
     *
     * @param out
     *      Output for result
     * @param clazz
     *      Class to use to analyze {@code mappableObject}
     * @param mappableObject
     *      Object to analyze
     * @throws IOException if any
     */
    public static void toXML(
        final Appendable out,
        final Class<?>   clazz,
        final Mappable   mappableObject
        ) throws IOException
    {
        MappableHelper.toXML(
            out,
            clazz,
            (mappableObject != null) ? mappableObject.toMap() : null
            );
    }

    /**
     * Convert a {@code mappableObject} to an XML view
     *
     * @param out
     *      Output for result
     * @param mappableObject
     *      Object to analyze
     * @throws IOException if any
     */
    public static void toXML(
        @Nonnull final Appendable out,
        @Nonnull final Mappable   mappableObject
        ) throws IOException
    {
        MappableHelper.toXML( out, mappableObject.getClass(), mappableObject );
    }

    /**
     * Returns {@link Mappable} object as XML
     *
     * @param clazz
     *      Class to use to analyze {@code mappableObject}
     * @param mappableObject
     *      Object to analyze
     * @return {@link Mappable} object as XML
     */
    public static String toXML( final Class<?> clazz, final Mappable mappableObject )
    {
        final StringBuilder sb = new StringBuilder();

        try {
            MappableHelper.toXML( sb, clazz, mappableObject );
            }
        catch( final IOException improbable ) {
            throw new StringBuilderIOException( improbable );
            }

        return sb.toString();
    }

    /**
     * Returns {@link Mappable} object as XML
     *
     * @param mappableObject
     *      Object to analyze
     * @return {@link Mappable} object as XML
     */
    public static String toXML( final Mappable mappableObject )
    {
        return MappableHelper.toXML( mappableObject.getClass(), mappableObject );
    }
}
