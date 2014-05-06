package com.googlecode.cchlib.util.properties;

import java.util.Arrays;
import java.util.Properties;
import org.apache.log4j.Logger;

//NOT public
final class Tools
{
    private static final int CMP_ERR_A_STRING_FIELD = 1;
    private static final int CMP_ERR_A_INT_FIELD = 2;
    private static final int CMP_ERR_A_FLOAT_FIELD = 3;
    private static final int CMP_ERR_SOMEBOOL1_FIELD = 4;
    private static final int CMP_ERR_SOMEBOOL2_FIELD = 5;
    private static final int CMP_ERR_SOMEBOOL3_FIELD = 6;
    private static final int CMP_ERR_SOMEBOOL4_FIELD = 7;
    private static final int CMP_ERR_TEXEFIELD_TEXT = 8;
    private static final int CMP_ERR_CHECKBOX_IS_SELECTED = 9;
    private static final int CMP_ERR_COMBOBOX_SELECTED_INDEX = 10;

    private Tools()
    {
        // All static
    }

    public static void logProperties( final Logger logger, final Properties properties )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Properties" );
        for( final String s : properties.stringPropertyNames() ) {
            sb.append("\n\t").append(s).append('=').append(properties.getProperty( s )).append(';');
            }
        logger.info( sb.toString() );
    }

    public static int compare(
        final BeanAnnotation first,
        final BeanAnnotation other
        )
    {
        int res = first.getaString().compareTo( other.getaString() );

        if( res != 0 ) {
            return CMP_ERR_A_STRING_FIELD;
            }

        res = first.getaInt() - other.getaInt();
        if( res != 0 ) {
            return CMP_ERR_A_INT_FIELD;
            }

        res = Float.compare( first.getaFloat(), other.getaFloat() );
        if( res != 0 ) {
            return CMP_ERR_A_FLOAT_FIELD;
            }

        if(  first.getSomeBooleans() == null ) {
            if( other.getSomeBooleans() != null ) {
                return CMP_ERR_SOMEBOOL1_FIELD;
            }
        } else {
            if( other.getSomeBooleans() == null ) {
                return CMP_ERR_SOMEBOOL2_FIELD;
            }

            res = first.getSomeBooleans().length - other.getSomeBooleans().length;
            if( res != 0 ) {
                return CMP_ERR_SOMEBOOL3_FIELD;
                }

            for( int i = 0; i<first.getSomeBooleans().length; i++ ) {
                res = Boolean.valueOf( first.getSomeBooleans()[ i ] ).compareTo( Boolean.valueOf( other.getSomeBooleans()[ i ] ) );

                if( res != 0 ) {
                    return CMP_ERR_SOMEBOOL4_FIELD;
                    }
                }
            }

        res = first.getaJTextField().getText().compareTo( other.getaJTextField().getText() );
        if( res != 0 ) {
            return CMP_ERR_TEXEFIELD_TEXT;
            }

        if( ! first.getaJCheckBox().isSelected() == other.getaJCheckBox().isSelected() ) {
            return CMP_ERR_CHECKBOX_IS_SELECTED;
            }

        res = first.getaJComboBox().getSelectedIndex() - other.getaJComboBox().getSelectedIndex();
        if( res != 0 ) {
            return CMP_ERR_COMBOBOX_SELECTED_INDEX;
            }

        assert res == 0;

        return res;
    }

    public static void toString( final BeanAnnotation bean, final StringBuilder builder )
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

    public static String toString( final BeanAnnotation ppSimpleBeanAnnotation )
    {
        final StringBuilder builder = new StringBuilder();

        toString( ppSimpleBeanAnnotation, builder );

        return builder.toString();
    }

}
