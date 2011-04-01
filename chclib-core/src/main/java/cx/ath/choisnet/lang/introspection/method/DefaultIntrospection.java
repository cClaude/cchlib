/**
 * 
 */
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * @author Claude
 * @param <O> 
 *
 */
public class DefaultIntrospection<O> 
    extends Introspection<O, DefaultIntrospectionItem<O>> 
{
    /**
     * 
     * @param inpectClass
     * @param attribSet
     */
    public DefaultIntrospection(
            Class<O>                        inpectClass,
            EnumSet<Introspection.Attrib>   attribSet 
            )
    {
        super( 
           inpectClass, 
           new IntrospectionItemFactory<IntrospectionItem<O>>()
           {
                @Override
                public IntrospectionItem<O> buildIntrospectionItem( 
                        Method getter,
                        Method setter 
                        )
                {
                    return new DefaultIntrospectionItem<O>( getter, setter );
                }
           },
           attribSet
           );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "DefaultIntrospection [getMap()=" );
        builder.append( getMap() );
        builder.append( ']' );
        return builder.toString();
    }

}
