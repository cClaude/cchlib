package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;

/**
 * NEEDDOC
 *
 * @author CC
 * @param <T> NEEDDOC
 */
@FunctionalInterface
public interface IntrospectionItemFactory<T extends IntrospectionItem<?>>
{
    /**
     * @param getter NEEDDOC
     * @param setter NEEDDOC
     * @return an IntropectionItem NEEDDOC
     */
    T buildIntrospectionItem( final Method getter,  final Method setter );
}
