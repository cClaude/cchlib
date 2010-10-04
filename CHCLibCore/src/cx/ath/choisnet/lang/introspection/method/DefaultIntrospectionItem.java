/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;

/**
 * <p>
 * Provide a default implementation for IntrospectionItem
 * </p>
 * <p>
 * This implementation use annotation to fill minValue, maxValue 
 * and defaultValue.
 * <br/>
 * Annotation MUST be set on setter's methods
 * <br/>
 * Current supported annotation are:
 * <pre>
 *  - IVInt
 *  - IVLong
 * </pre>
 * </p>
 * 
 * @author Claude CHOISNET
 * 
 * @param <O> 
 * @see IVInt
 * @see IVLong
 */
public class DefaultIntrospectionItem<O> 
    extends AbstractIntrospectionItem<O> 
{
    private Object minValue;
    private Object defaultValue;
    private Object maxValue;
    
    public DefaultIntrospectionItem( 
            final Method getter, 
            final Method setter
            )
    {
        super( getter, setter );
        
        IVInt  ivInt  = setter.getAnnotation( IVInt.class );
        IVLong ivLong = setter.getAnnotation( IVLong.class );

        if( ivInt != null && ivLong != null ) {
            // Exception ? or log ? nothing ?
        }
        
        if( ivInt != null ) {
            // Use protected setters ?
            this.minValue     = ivInt.minValue();
            this.defaultValue = ivInt.defaultValue();
            this.maxValue     = ivInt.maxValue();
        }
        else if( ivLong != null ) {
            // Use protected setters ?
            this.minValue     = ivLong.minValue();
            this.defaultValue = ivLong.defaultValue();
            this.maxValue     = ivLong.maxValue();
        }
        else {
            // Don't do anything, since this
            // class could be extended to implements
            // others cases !
        }
    }

    @Override
    public Object getMinValue()
    {
        return this.minValue;
    }

    @Override
    public Object getDefaultValue()
    {
        return this.defaultValue;
    }

    @Override
    public Object getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * @param minValue the minValue to set
     */
    protected void setMinValue( Object minValue )
    {
        this.minValue = minValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    protected void setDefaultValue( Object defaultValue )
    {
        this.defaultValue = defaultValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    protected void setMaxValue( Object maxValue )
    {
        this.maxValue = maxValue;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "DefaultIntrospectionItem [minValue=" );
        builder.append( minValue );
        builder.append( ", defaultValue=" );
        builder.append( defaultValue );
        builder.append( ", maxValue=" );
        builder.append( maxValue );
        builder.append( ", toString()=" );
        builder.append( super.toString() );
        builder.append( "]" );
        return builder.toString();
    }
}