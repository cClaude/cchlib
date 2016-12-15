package com.googlecode.cchlib.util.properties;

import java.util.Arrays;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

//NOT public
interface BeanAnnotationHeadLess extends Comparable<BeanAnnotationHeadLess>
{
    String getaString();
    void setaString( String aString );

    int getaInt();
    void setaInt( int aInt );

    float getaFloat();
    void setaFloat( float aFloat );

    boolean[] getSomeBooleans();
    void setSomeBooleans( boolean[] someBooleans );

    static ComboBoxModel<String> getComboBoxModel()
    {
        final String[] objects = {
            "Aa", "Bbb", "Ccc", "Dddd", "Eeeee",
            };

        return new DefaultComboBoxModel<String>( objects );
    }

    @Override
    default int compareTo( final BeanAnnotationHeadLess other )
    {
        return compare( this, other );
    }

    public static int compare(
        final BeanAnnotationHeadLess first,
        final BeanAnnotationHeadLess other
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

        assert res == 0;

        return res;
    }

    public static void toString( final BeanAnnotationHeadLess bean, final StringBuilder builder )
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
        builder.append( ']' );
    }

    public static String toString( final BeanAnnotationHeadLess ppSimpleBeanAnnotation )
    {
        final StringBuilder builder = new StringBuilder();

        toString( ppSimpleBeanAnnotation, builder );

        return builder.toString();
    }
}
