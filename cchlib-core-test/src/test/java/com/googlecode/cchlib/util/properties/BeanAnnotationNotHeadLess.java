package com.googlecode.cchlib.util.properties;

import java.util.Arrays;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
interface BeanAnnotationNotHeadLess extends Comparable<BeanAnnotationNotHeadLess>
{
    String getaString();
    void setaString( String aString );

    int getaInt();
    void setaInt( int aInt );

    float getaFloat();
    void setaFloat( float aFloat );

    boolean[] getSomeBooleans();
    void setSomeBooleans( boolean[] someBooleans );

    JTextField getaJTextField();

    JCheckBox getaJCheckBox();

    JComboBox<String> getaJComboBox();

    static ComboBoxModel<String> getComboBoxModel()
    {
        final String[] objects = {
            "Aa", "Bbb", "Ccc", "Dddd", "Eeeee",
            };

        return new DefaultComboBoxModel<String>( objects );
    }

    @Override
    default int compareTo( final BeanAnnotationNotHeadLess other )
    {
        return compare( this, other );
    }

    public static int compare(
        final BeanAnnotationNotHeadLess first,
        final BeanAnnotationNotHeadLess other
        )
    {
        int res = first.getaString().compareTo( other.getaString() );

        if( res != 0 ) {
            return CmpResult.CMP_ERR_A_STRING_FIELD.getValue();
            }

        res = first.getaInt() - other.getaInt();
        if( res != 0 ) {
            return CmpResult.CMP_ERR_A_INT_FIELD.getValue();
            }

        res = Float.compare( first.getaFloat(), other.getaFloat() );
        if( res != 0 ) {
            return CmpResult.CMP_ERR_A_FLOAT_FIELD.getValue();
            }

        if(  first.getSomeBooleans() == null ) {
            if( other.getSomeBooleans() != null ) {
                return CmpResult.CMP_ERR_SOMEBOOL1_FIELD.getValue();
            }
        } else {
            if( other.getSomeBooleans() == null ) {
                return CmpResult.CMP_ERR_SOMEBOOL2_FIELD.getValue();
            }

            res = first.getSomeBooleans().length - other.getSomeBooleans().length;
            if( res != 0 ) {
                return CmpResult.CMP_ERR_SOMEBOOL3_FIELD.getValue();
                }

            for( int i = 0; i<first.getSomeBooleans().length; i++ ) {
                res = Boolean.valueOf( first.getSomeBooleans()[ i ] ).compareTo( Boolean.valueOf( other.getSomeBooleans()[ i ] ) );

                if( res != 0 ) {
                    return CmpResult.CMP_ERR_SOMEBOOL4_FIELD.getValue();
                    }
                }
            }

        res = first.getaJTextField().getText().compareTo( other.getaJTextField().getText() );
        if( res != 0 ) {
            return CmpResult.CMP_ERR_TEXEFIELD_TEXT.getValue();
            }

        if( ! first.getaJCheckBox().isSelected() == other.getaJCheckBox().isSelected() ) {
            return CmpResult.CMP_ERR_CHECKBOX_IS_SELECTED.getValue();
            }

        res = first.getaJComboBox().getSelectedIndex() - other.getaJComboBox().getSelectedIndex();
        if( res != 0 ) {
            return CmpResult.CMP_ERR_COMBOBOX_SELECTED_INDEX.getValue();
            }

        assert res == 0;

        return res;
    }

    public static void toString( final BeanAnnotationNotHeadLess bean, final StringBuilder builder )
    {
        builder.append( bean.getClass().getSimpleName() );
        builder.append( "[ getaString()=" );
        builder.append( bean.getaString() );
        builder.append( ", getaInt()=" );
        builder.append( bean.getaInt() );
        builder.append( ", getaFloat()=" );
        builder.append( bean.getaFloat() );
        builder.append( ", getSomeBooleans()=" );
        builder.append( Arrays.toString( bean.getSomeBooleans() ) );
        builder.append(", getaJTextField().getText()=");
        builder.append( bean.getaJTextField().getText() );
        builder.append(", getaJCheckBox().isSelected()=");
        builder.append( bean.getaJCheckBox().isSelected() );
        builder.append(", getaJComboBox().getSelectedIndex()=");
        builder.append( bean.getaJComboBox().getSelectedIndex() );
        builder.append( ']' );
    }

    public static String toString( final BeanAnnotationNotHeadLess ppSimpleBeanAnnotation )
    {
        final StringBuilder builder = new StringBuilder();

        toString( ppSimpleBeanAnnotation, builder );

        return builder.toString();
    }
}
