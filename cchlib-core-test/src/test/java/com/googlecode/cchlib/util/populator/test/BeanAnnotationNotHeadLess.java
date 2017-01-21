package com.googlecode.cchlib.util.populator.test;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
interface BeanAnnotationNotHeadLess extends BeanAnnotationHeadLess
{
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
}
