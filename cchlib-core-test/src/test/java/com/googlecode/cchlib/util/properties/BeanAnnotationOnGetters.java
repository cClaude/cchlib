package com.googlecode.cchlib.util.properties;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
class BeanAnnotationOnGetters implements BeanAnnotation
{
    protected String    aString;
    private   int       aInt;
    private   float     aFloat;
    private boolean[]   someBooleans;

    /** default text ERROR */
    private final JTextField aJTextField;
    /** default state unselected */
    private final JCheckBox aJCheckBox;
    /** With a model, but non selected value */
    private final JComboBox<String>  aJComboBox;

    public BeanAnnotationOnGetters()
    {
        this.aJTextField = new JTextField();
        this.aJTextField.setText( "ERROR" );

        this.aJCheckBox  = new JCheckBox();
        this.aJCheckBox.setSelected(false);

        this.aJComboBox  = new JComboBox<String>( BeanAnnotation.getComboBoxModel() );
    }

    public BeanAnnotationOnGetters(
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
    @Populator
    public final String getaString()
    {
        return aString;
    }

    @Override
    public
    final void setaString(final String aString)
    {
        this.aString = aString;
    }

    @Override
    @Populator
    public final int getaInt()
    {
        return aInt;
    }

    @Override
    public
    final void setaInt(final int aInt)
    {
        this.aInt = aInt;
    }

    @Override
    @Populator
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
    @Populator
    public final boolean[] getSomeBooleans()
    {
        return this.someBooleans;
    }

    @Override
    public final void setSomeBooleans( final boolean[] someBooleans )
    {
        this.someBooleans = someBooleans;
    }

    @Override
    @Persistent
    public JTextField getaJTextField()
    {
        return aJTextField;
    }

    @Override
    @Persistent
    public JCheckBox getaJCheckBox()
    {
        return aJCheckBox;
    }

    @Override
    @Persistent
    public JComboBox<String> getaJComboBox()
    {
        return aJComboBox;
    }
}
