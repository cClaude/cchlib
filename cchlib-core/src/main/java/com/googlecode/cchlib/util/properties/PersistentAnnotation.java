package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Field;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/* not public */
class PersistentAnnotation<E> implements PropertiesPopulatorAnnotation<E>
{
    private Persistent persistent;

    PersistentAnnotation( Persistent persistent )
    {
        this.persistent = persistent;
    }
    @Override
    public boolean isDefaultValueNull()
    {
        return false;
    }
    @Override
    public String defaultValue()
    {
        return persistent.defaultValue();
    }
    @Override
    public String toString( final Object o ) throws PropertiesPopulatorException
    {
        if( o instanceof JTextField ) {
            return JTextField.class.cast( o ).getText();
            }
        else if( o instanceof JCheckBox ) {
            return Boolean.toString( JCheckBox.class.cast( o ).isSelected() );
            }
        else if( o instanceof JComboBox ) {
            JComboBox jc    = JComboBox.class.cast( o );
            int       index = jc.getSelectedIndex(); // Store only selected index

            return Integer.toString( index );
            }
        else {
            throw new PersistentException( "@Persistent does not handle type " + o.getClass() );
            }
    }
    @Override
    public void setValue( final Field f, final E bean, final String strValue, final Class<?> type )
        throws IllegalArgumentException,
               IllegalAccessException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException
    {
        Object o = f.get( bean );

        if( o instanceof JTextField ) {
            JTextField.class.cast( o ).setText( strValue );
            }
        else if( o instanceof JCheckBox ) {
            JCheckBox.class.cast( o ).setSelected( Boolean.parseBoolean( strValue ) );
            }
        else if( o instanceof JComboBox ) {
            final JComboBox jc    = JComboBox.class.cast( o );
            final int       index = Integer.parseInt( strValue );

            jc.setSelectedIndex( index );
            }
        else {
            throw new PersistentException( "@Persistent does not handle type " + o.getClass() );
            }
    }
    @Override
    public void setArrayEntry( final Field f, final Object array, final int index, final String strValue, final Class<?> type)
        throws ArrayIndexOutOfBoundsException,
               IllegalArgumentException,
               ConvertCantNotHandleTypeException,
               PropertiesPopulatorException
    {
        throw new PersistentException( "@Persistent does not handle array" );
    }
}
