package cx.ath.choisnet.lang.reflect;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.mappable.MappableItem} instead 
 */
@Deprecated
public class MappableHelper
{
    /**
     * Build Map using default factory.
     *
     * @see MappableBuilder#toMap(Object)
     */
    public static Map<String,String> toMap(final Object object)
    {
        MappableBuilder mb = MappableBuilder.createMappableBuilder();

        return mb.toMap( object );
    }

    /**
     */
    public static void toXML( Appendable out, Class<?> clazz, Map<String,String> map )
        throws IOException
    {
        if( map == null ) {
            out.append( "<class name=\"" )
               .append( clazz.getName() )
               .append( "\" /><!-- NULL OBJECT -->\n" );
            }
        else if( map.size() == 0 ) {
            out.append( "<class name=\"" )
               .append( clazz.getName() )
               .append( "\" /><!-- EMPTY -->\n" );
            }
        else {
            out.append( "<class name=\"" )
               .append( clazz.getName() )
               .append( "\">\n" );

            for(
                    Iterator<String> i$ = map.keySet().iterator();
                    i$.hasNext();
                    ) {
                String name = i$.next();
                out.append("  <value name=\"" )
                   .append( name ).append("\">" )
                   .append( map.get( name ) )
                   .append( "</value>\n" );
                }

            out.append("</class>\n");
        }
    }

    /**
     */
    public static void toXML( Appendable out, Class<?> clazz, Mappable aMappableObject )
        throws IOException
    {
        MappableHelper.toXML(out, clazz, (aMappableObject != null) ? aMappableObject.toMap() : null);
    }

    /**
     */
    public static void toXML( Appendable out, Mappable aMappableObject )
        throws IOException
    {
        MappableHelper.toXML( out, aMappableObject.getClass(), aMappableObject );
    }

    /**
     */
    public static String toXML( Class<?> clazz, Mappable aMappableObject )
    {
        StringBuilder sb = new StringBuilder();

        try {
            MappableHelper.toXML( sb, clazz, aMappableObject);
            }
        catch( IOException improbable ) {
            throw new RuntimeException(improbable);
            }

        return sb.toString();
    }

    /**
     */
    public static String toXML( Mappable aMappableObject )
    {
        return MappableHelper.toXML( aMappableObject.getClass(), aMappableObject );
    }
}
