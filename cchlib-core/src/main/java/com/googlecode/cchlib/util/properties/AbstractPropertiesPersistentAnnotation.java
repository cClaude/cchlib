package com.googlecode.cchlib.util.properties;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
abstract class AbstractPropertiesPersistentAnnotation<E,METHOD_OR_FIELD> //
    implements PropertiesPersistentAnnotation<E,METHOD_OR_FIELD>
{
    private final Persistent persistent;

    AbstractPropertiesPersistentAnnotation( final Persistent persistent )
    {
        this.persistent = persistent;
    }

    @Override
    public final boolean isDefaultValueNull()
    {
        return false;
    }

    @Override
    public final String defaultValue()
    {
        return persistent.defaultValue();
    }

    @Override
    public final String toString( final Object swingObject ) throws PropertiesPopulatorRuntimeException
    {
        if( swingObject instanceof JTextField ) {
            return JTextField.class.cast( swingObject ).getText();
            }
        else if( swingObject instanceof JCheckBox ) {
            return Boolean.toString( JCheckBox.class.cast( swingObject ).isSelected() );
            }
        else if( swingObject instanceof JComboBox ) {
            final JComboBox<?> jc    = JComboBox.class.cast( swingObject );
            final int          index = jc.getSelectedIndex(); // Store only selected index

            return Integer.toString( index );
            }
        else {
            throw new PersistentException( "@Persistent does not handle type " + swingObject.getClass() );
            }
    }

    protected void setValue( final Object swingObject, final String strValue )
    {
        if( swingObject instanceof JTextField ) {
            JTextField.class.cast( swingObject ).setText( strValue );
            }
        else if( swingObject instanceof JCheckBox ) {
            JCheckBox.class.cast( swingObject ).setSelected( Boolean.parseBoolean( strValue ) );
            }
        else if( swingObject instanceof JComboBox ) {
            final JComboBox<?> jc    = JComboBox.class.cast( swingObject );
            final int          index = Integer.parseInt( strValue );

            jc.setSelectedIndex( index );
            }
        else {
            throw new PersistentException( "@Persistent does not handle type " + swingObject.getClass() );
            }
    }
}
