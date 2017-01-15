package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.reflect.AccessibleRestorer;
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
    private final Method getterMethod;
    private final Method setterMethod;

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
        return this.getterMethod;
    }

    /**
     * @return the setterMethod
     */
    @Override
    public Method getSetterMethod()
    {
        return this.setterMethod;
    }

    /**
     * Get invocation raw result has an Object.
     *
     * @param object Object where method will be apply
     * @return result value from invocation
     * @see #setObjectValue(Object, Object)
     * @throws IntrospectionInvokeException if any problems occurs during method invocation
     */
    @Override
    public final Object getObjectValue( final O object )
        throws IntrospectionInvokeException
    {
        final AccessibleRestorer access = new AccessibleRestorer( this.getterMethod );

        try {
            return this.getterMethod.invoke( object, (Object[])null );
            }
        catch( IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod );
            }
        finally {
            access.restore();
        }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S1160" // 2 exceptions
        })
    public int getIntegerValue( final O object )
        throws IntrospectionInvokeException,
               IntrospectionClassCastException
    {
        try {
            return Integer.class.cast( getObjectValue( object ) ).intValue();
            }
        catch( final ClassCastException e ) {
            throw new IntrospectionClassCastException( e, this.setterMethod );
            }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S1160" // 2 exceptions
        })
    public boolean getBooleanValue( final O object )
        throws IntrospectionInvokeException,
               IntrospectionClassCastException
    {
        try {
            return Boolean.class.cast( getObjectValue( object ) ).booleanValue();
            }
        catch( final ClassCastException e ) {
            throw new IntrospectionClassCastException( e, this.setterMethod );
            }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S1160" // 2 exceptions
        })
    public String getStringValue( final O object )
        throws IntrospectionInvokeException,
               IntrospectionClassCastException
    {
        try {
            return String.class.cast( getObjectValue( object ) ).trim();
            }
        catch( final ClassCastException e ) {
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
        final AccessibleRestorer accessible = new AccessibleRestorer( this.setterMethod );
        final Object[]           params     = new Object[] { value };

        try {
            this.setterMethod.invoke( object, params );
            }
        catch( IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
            throw new IntrospectionInvokeException( e, this.setterMethod, params.getClass() );
            }
        finally {
            accessible.restore();
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
        final StringBuilder builder = new StringBuilder();
        builder.append( "IntrospectionItem [getterMethod=" );
        builder.append( this.getterMethod.getName() );
        builder.append( ", setterMethod=" );
        builder.append( this.setterMethod.getName() );
        builder.append( ']' );
        return builder.toString();
    }

}
