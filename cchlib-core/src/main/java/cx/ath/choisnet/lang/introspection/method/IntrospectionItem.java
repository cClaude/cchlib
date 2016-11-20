/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import java.lang.reflect.Method;

/**
 * Container base for Introspection
 *
 * @author CC
 * @param <O> Object to inspect
 *
 */
public interface IntrospectionItem<O>
{
    /**
     * @return the getterMethod
     */
    Method getGetterMethod();

    /**
     * @return the setterMethod
     */
    Method getSetterMethod();

    Object getMinValue();
    Object getDefaultValue();
    Object getMaxValue();

    /**
     * Get invocation raw result has an Object.
     *
     * @param object Object where method will be apply
     * @return result value from invocation

     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    Object getObjectValue( final O object )
            throws IntrospectionInvokeException;
}
