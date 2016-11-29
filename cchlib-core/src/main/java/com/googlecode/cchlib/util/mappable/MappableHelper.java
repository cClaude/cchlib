package com.googlecode.cchlib.util.mappable;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
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
 */
@NeedDoc
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
     * @param object object to map
     * @return NEEDDOC
     *
     * @see MappableBuilder#toMap(Object)
     */
    public static <T> Map<String,String> toMap( final T object )
    {
        final MappableBuilder mb = MappableBuilder.createMappableBuilder();

        return mb.toMap( object );
    }

    /**
     *  NEEDDOC
     *
     * @param out   Output for result
     * @param clazz Class to use to analyze <code>mappableObject</code>
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
     * Convert a <code>mappableObject</code> to an XML view
     *
     * @param out
     *      Output for result
     * @param clazz
     *      Class to use to analyze <code>mappableObject</code>
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
     * Convert a <code>mappableObject</code> to an XML view
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
     *      Class to use to analyze <code>mappableObject</code>
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
