package cx.ath.choisnet.lang.introspection.method;

import java.util.Set;

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
     * @param inpectClass NEEDDOC
     * @param attribSet NEEDDOC
     */
    public DefaultIntrospection(
        final Class<O>                     inpectClass,
        final Set<IntrospectionParameters> attribSet
        )
    {
        super(
           inpectClass,
           DefaultIntrospectionItem::new,
           attribSet
           );
    }

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
