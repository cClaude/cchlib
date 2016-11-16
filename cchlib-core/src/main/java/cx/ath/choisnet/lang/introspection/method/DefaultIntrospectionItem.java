package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;

/**
 * <p>
 * Provide a default implementation for IntrospectionItem
 * </p>
 * <br>
 * <br>
 * This implementation use annotation to fill minValue, maxValue
 * and defaultValue.
 * <br>
 * Annotation MUST be set on setter's methods
 * <br>
 * Current supported annotation are:
 * <pre>
 *  - IVInt
 *  - IVLong
 * </pre>
 *
 * @param <O> NEEDDOC
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

        final IVInt  ivInt  = setter.getAnnotation( IVInt.class );
        final IVLong ivLong = setter.getAnnotation( IVLong.class );

        if( (ivInt != null) && (ivLong != null) ) {
            // Exception ? or log ? nothing ?
            }

        if( ivInt != null ) {
            // Use protected setters ?
            this.minValue     = Integer.valueOf( ivInt.minValue() );
            this.defaultValue = Integer.valueOf( ivInt.defaultValue() );
            this.maxValue     = Integer.valueOf( ivInt.maxValue() );
            }
        else if( ivLong != null ) {
            // Use protected setters ?
            this.minValue     = Long.valueOf( ivLong.minValue() );
            this.defaultValue = Long.valueOf( ivLong.defaultValue() );
            this.maxValue     = Long.valueOf( ivLong.maxValue() );
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
    protected void setMinValue( final Object minValue )
    {
        this.minValue = minValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    protected void setDefaultValue( final Object defaultValue )
    {
        this.defaultValue = defaultValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    protected void setMaxValue( final Object maxValue )
    {
        this.maxValue = maxValue;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "DefaultIntrospectionItem [minValue=" );
        builder.append( this.minValue );
        builder.append( ", defaultValue=" );
        builder.append( this.defaultValue );
        builder.append( ", maxValue=" );
        builder.append( this.maxValue );
        builder.append( ", toString()=" );
        builder.append( super.toString() );
        builder.append( ']' );
        return builder.toString();
    }
}
