package com.googlecode.cchlib.util.populator.test;

import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.googlecode.cchlib.util.populator.Persistent;

//NOT public
class BeanAnnotationOnGettersNotHeadLess
    extends BeanAnnotationOnGettersHeadLess
        implements BeanAnnotationNotHeadLess
{
    /** default text ERROR */
    private final JTextField aJTextField;
    /** default state unselected */
    private final JCheckBox aJCheckBox;
    /** With a model, but non selected value */
    private final JComboBox<String>  aJComboBox;

    public BeanAnnotationOnGettersNotHeadLess()
    {
        super();

        this.aJTextField = new JTextField();
        this.aJTextField.setText( "ERROR" );

        this.aJCheckBox  = new JCheckBox();
        this.aJCheckBox.setSelected(false);

        this.aJComboBox  = new JComboBox<String>( BeanAnnotationNotHeadLess.getComboBoxModel() );
    }

    public BeanAnnotationOnGettersNotHeadLess(
        final String    aString,
        final int       aInt,
        final float     aFloat,
        final boolean[] booleans,
        final String    jTextFieldString,
        final boolean   jCheckBoxSelected,
        final int       jComboBoxSelectedIndex
        )
    {
        super( aString, aInt, aFloat, booleans );

        this.aJTextField = new JTextField();
        this.aJTextField.setText( jTextFieldString );

        this.aJCheckBox  = new JCheckBox();
        this.aJCheckBox.setSelected( jCheckBoxSelected );

        this.aJComboBox  = new JComboBox<String>( BeanAnnotationNotHeadLess.getComboBoxModel() );
        this.aJComboBox.setSelectedIndex( jComboBoxSelectedIndex );
    }

    @Override
    @Persistent
    public JTextField getaJTextField()
    {
        return this.aJTextField;
    }

    @Override
    @Persistent
    public JCheckBox getaJCheckBox()
    {
        return this.aJCheckBox;
    }

    @Override
    @Persistent
    public JComboBox<String> getaJComboBox()
    {
        return this.aJComboBox;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "BeanAnnotationOnGettersNotHeadLess [aJTextField=" );
        builder.append( this.aJTextField );
        builder.append( ", aJCheckBox=" );
        builder.append( this.aJCheckBox );
        builder.append( ", aJComboBox=" );
        builder.append( this.aJComboBox );
        builder.append( ", getaString()=" );
        builder.append( getaString() );
        builder.append( ", getaInt()=" );
        builder.append( getaInt() );
        builder.append( ", getaFloat()=" );
        builder.append( getaFloat() );
        builder.append( ", getSomeBooleans()=" );
        builder.append( Arrays.toString( getSomeBooleans() ) );
        builder.append( "]" );
        return builder.toString();
    }
}
