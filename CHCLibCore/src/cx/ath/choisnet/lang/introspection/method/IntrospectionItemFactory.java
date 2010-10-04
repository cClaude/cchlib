/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;

/**
 * @author CC
 * @param <T> 
 */
public abstract class IntrospectionItemFactory<T extends IntrospectionItem<?>>
{

    public IntrospectionItemFactory()
    {
        // empty
    }

    /**
     * @param getter 
     * @param setter 
     * @return an IntropectionItem
     */
    public abstract T buildIntrospectionItem( final Method getter,  final Method setter );
}
