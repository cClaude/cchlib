package com.googlecode.cchlib.util.mappable;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
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
     * @param object
     * @return
     *
     * @see MappableBuilder#toMap(Object)
     */
    public static <T> Map<String,String> toMap( final T object )
    {
        final MappableBuilder mb = MappableBuilder.createMappableBuilder();

        return mb.toMap( object );
    }

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
     * TODOC
     *
     * @param out
     * @param clazz
     * @param aMappableObject
     * @throws IOException if any
     */
    public static void toXML(
        final Appendable out,
        final Class<?>   clazz,
        final Mappable   aMappableObject
        ) throws IOException
    {
        MappableHelper.toXML(
            out,
            clazz,
            (aMappableObject != null) ? aMappableObject.toMap() : null
            );
    }

    /**
     * TODOC
     *
     * @param out
     * @param aMappableObject
     * @throws IOException if any
     */
    public static void toXML( final Appendable out, final Mappable aMappableObject )
        throws IOException
    {
        MappableHelper.toXML( out, aMappableObject.getClass(), aMappableObject );
    }

    /**
     * Returns {@link Mappable} object as XML
     *
     * @param clazz TODOC
     * @param aMappableObject TODOC
     * @return {@link Mappable} object as XML
     */
    public static String toXML( final Class<?> clazz, final Mappable aMappableObject )
    {
        final StringBuilder sb = new StringBuilder();

        try {
            MappableHelper.toXML( sb, clazz, aMappableObject);
            }
        catch( final IOException improbable ) {
            throw new StringBuilderIOException( improbable );
            }

        return sb.toString();
    }

    /**
     * Returns {@link Mappable} object as XML
     *
     * @param aMappableObject TODOC
     * @return {@link Mappable} object as XML
     */
    public static String toXML( final Mappable aMappableObject )
    {
        return MappableHelper.toXML( aMappableObject.getClass(), aMappableObject );
    }
}
