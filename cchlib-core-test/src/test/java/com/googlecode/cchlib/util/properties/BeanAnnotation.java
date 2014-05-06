package com.googlecode.cchlib.util.properties;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
interface BeanAnnotation extends Comparable<BeanAnnotation> {

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
    default int compareTo( final BeanAnnotation other )
    {
        return Tools.compare( this, other );
    }
    }
