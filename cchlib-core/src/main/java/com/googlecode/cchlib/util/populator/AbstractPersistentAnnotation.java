package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Member;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
@SuppressWarnings("squid:S00119") // naming convention
abstract class AbstractPersistentAnnotation<E,METHOD_OR_FIELD extends Member>
    implements PersistentAnnotation<E,METHOD_OR_FIELD>
{
    private final Persistent persistent;

    AbstractPersistentAnnotation( final Persistent persistent )
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
        return this.persistent.defaultValue();
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public final String toString( final Object swingObject )
        throws PopulatorRuntimeException
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
            throw new PersistentException(
                    "@Persistent does not handle type " + swingObject.getClass()
                    );
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
            throw new PersistentException(
                    "@Persistent does not handle type " + swingObject.getClass()
                    );
        }
    }
}
