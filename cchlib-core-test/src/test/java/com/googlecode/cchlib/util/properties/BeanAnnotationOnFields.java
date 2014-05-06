package com.googlecode.cchlib.util.properties;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
class BeanAnnotationOnFields implements BeanAnnotation
{
    @Populator protected String    aString;
    @Populator private   int       aInt;
    @Populator private   float     aFloat;
    @Populator public    boolean[] someBooleans;

    /** default text ERROR */
    @Persistent private final JTextField aJTextField;
    /** default state unselected */
    @Persistent private final JCheckBox aJCheckBox;
    /** With a model, but non selected value */
    @Persistent private final JComboBox<String>  aJComboBox;

    public BeanAnnotationOnFields()
    {
        this.aJTextField = new JTextField();
        this.aJTextField.setText( "ERROR" );

        this.aJCheckBox  = new JCheckBox();
        this.aJCheckBox.setSelected(false);

        this.aJComboBox  = new JComboBox<String>( BeanAnnotation.getComboBoxModel() );
    }

    public BeanAnnotationOnFields(
        final String    aString,
        final int       aInt,
        final float     aFloat,
        final boolean[] booleans,
        final String    jTextFieldString,
        final boolean   jCheckBoxSelected,
        final int       jComboBoxSelectedIndex
        )
    {
        this();

        this.aString        = aString;
        this.aInt           = aInt;
        this.aFloat         = aFloat;
        this.someBooleans   = booleans;

        this.aJTextField.setText( jTextFieldString );
        this.aJCheckBox.setSelected( jCheckBoxSelected );
        this.aJComboBox.setSelectedIndex( jComboBoxSelectedIndex );
    }

    @Override
    public String toString()
    {
        return Tools.toString( this );
    }

    @Override
    public final String getaString()
    {
        return aString;
    }
    @Override
    public final void setaString(final String aString)
    {
        this.aString = aString;
    }

    @Override
    public final int getaInt()
    {
        return aInt;
    }
    @Override
    public final void setaInt(final int aInt)
    {
        this.aInt = aInt;
    }

    @Override
    public final float getaFloat()
    {
        return aFloat;
    }
    @Override
    public final void setaFloat(final float aFloat)
    {
        this.aFloat = aFloat;
    }

    @Override
    public final boolean[] getSomeBooleans()
    {
        return someBooleans;
    }
    @Override
    public final void setSomeBooleans(final boolean[] someBooleans)
    {
        this.someBooleans = someBooleans;
    }

    @Override
    public JTextField getaJTextField()
    {
        return aJTextField;
    }

    @Override
    public JCheckBox getaJCheckBox()
    {
        return aJCheckBox;
    }

    @Override
    public JComboBox<String> getaJComboBox()
    {
        return aJComboBox;
    }
}
