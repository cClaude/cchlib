/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;

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
    public Method getGetterMethod();

    /**
     * @return the setterMethod
     */
    public Method getSetterMethod();

    public Object getMinValue();
    public Object getDefaultValue();
    public Object getMaxValue();
    
    /**
     * Get invocation raw result has an Object.
     * 
     * @param object Object where method will be apply
     * @return result value from invocation

     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    public Object getObjectValue( final O object )
            throws IntrospectionInvokeException;
}
