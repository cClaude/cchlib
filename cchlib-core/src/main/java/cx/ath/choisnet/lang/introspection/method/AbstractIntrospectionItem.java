/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import cx.ath.choisnet.lang.introspection.IntrospectionClassCastException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;

/**
 * Provide a default implementation for IntrospectionItem
 *
 * @author CC
 * @param <O> Object to inspect
 *
 */
public abstract class AbstractIntrospectionItem<O>
    implements IntrospectionItem<O>
{
    private Method getterMethod;
    private Method setterMethod;

    public AbstractIntrospectionItem( final Method getterMethod, final Method setterMethod )
    {
        this.getterMethod = getterMethod;
        this.setterMethod = setterMethod;
    }


    /**
     * @return the getterMethod
     */
    @Override
    public Method getGetterMethod()
    {
        return getterMethod;
    }

    /**
     * @return the setterMethod
     */
    @Override
    public Method getSetterMethod()
    {
        return setterMethod;
    }

    @Override
    public abstract Object getMinValue();
    @Override
    public abstract Object getDefaultValue();
    @Override
    public abstract Object getMaxValue();

    /**
     * Get invocation raw result has an Object.
     *
     * @param object Object where method will be apply
     * @return result value from invocation
     * @see #setObjectValue(Object, Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    @Override
    final public Object getObjectValue( final O object )
            throws IntrospectionInvokeException
    {
        try {
            //workaround for bug:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
            this.getterMethod.setAccessible(true);

            return this.getterMethod.invoke( object, (Object[])null );
        }
        catch( IllegalArgumentException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod );
        }
        catch( IllegalAccessException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod );
        }
        catch( InvocationTargetException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod );
        }
    }

    public int getIntegerValue( final O object )
            throws IntrospectionInvokeException, IntrospectionClassCastException
    {
        try {
            return Integer.class.cast( getObjectValue( object ) ).intValue();
        }
        catch( ClassCastException e ) {
            throw new IntrospectionClassCastException( e, this.setterMethod );
            }
    }

    public boolean getBooleanValue( final O object )
            throws IntrospectionInvokeException, IntrospectionClassCastException
    {
        try {
            return Boolean.class.cast( getObjectValue( object ) ).booleanValue();
        }
        catch( ClassCastException e ) {
            throw new IntrospectionClassCastException( e, this.setterMethod );
            }
    }

    public String getStringValue( final O object )
            throws IntrospectionInvokeException, IntrospectionClassCastException
    {
        try {
            return String.class.cast( getObjectValue( object ) ).trim();
        }
        catch( ClassCastException e ) {
            throw new IntrospectionClassCastException( e, this.setterMethod );
            }
    }

    /**
     * Set invocation raw Object.
     *
     * @param object Object where method will be apply
     * @param value  value to be set
     * @see #getObjectValue(Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    public void setObjectValue( final O object, final Object value )
        throws IntrospectionInvokeException
    {
        final Object[] params = new Object[ 1 ];
        params[ 0 ] = value;

        try {
            //workaround for bug:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
            this.setterMethod.setAccessible(true);
            this.setterMethod.invoke( object, params );
        }
        catch( IllegalArgumentException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod, params.getClass() );
        }
        catch( IllegalAccessException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod, params.getClass() );
        }
        catch( InvocationTargetException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod, params.getClass() );
        }
    }

    /**
     * Just to be symmetric, for automation
     *
     * @param object Object where method will be apply
     * @param value  value to be set
     * @see #setObjectValue(Object, Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    public void setIntegerValue( final O object, final int value )
            throws IntrospectionInvokeException
    {
        setObjectValue( object, Integer.valueOf( value ) );
    }

    /**
     * Just to be symmetric, for automation
     *
     * @param object Object where method will be apply
     * @param value  value to be set
     * @see #setObjectValue(Object, Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    public void setBooleanValue( final O object, final boolean value )
            throws IntrospectionInvokeException
    {
        setObjectValue( object, Boolean.valueOf( value ) );
    }

    /**
     * Just to be symmetric, for automation
     *
     * @param object Object where method will be apply
     * @param value  value to be set
     * @see #setObjectValue(Object, Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    public void setStringValue( final O object, final String value )
            throws IntrospectionInvokeException
    {
        setObjectValue( object, value );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "IntrospectionItem [getterMethod=" );
        builder.append( getterMethod.getName() );
        builder.append( ", setterMethod=" );
        builder.append( setterMethod.getName() );
        builder.append( ']' );
        return builder.toString();
    }

}
