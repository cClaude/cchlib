package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * NEEDDOC
 *
 * @param <O> NEEDDOC
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
            final Class<O>                         inpectClass,
            final EnumSet<IntrospectionParameters> attribSet
            )
    {
        super(
           inpectClass,
           new IntrospectionItemFactory<IntrospectionItem<O>>()
           {
                @Override
                public IntrospectionItem<O> buildIntrospectionItem(
                        final Method getter,
                        final Method setter
                        )
                {
                    return new DefaultIntrospectionItem<>( getter, setter );
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
        final StringBuilder builder = new StringBuilder();
        builder.append( "DefaultIntrospection [getMap()=" );
        builder.append( getMap() );
        builder.append( ']' );
        return builder.toString();
    }

}
