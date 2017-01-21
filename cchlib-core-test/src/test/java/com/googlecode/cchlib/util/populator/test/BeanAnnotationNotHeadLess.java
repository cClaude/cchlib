package com.googlecode.cchlib.util.populator.test;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

//NOT public
interface BeanAnnotationNotHeadLess extends BeanAnnotationHeadLess
{
    JTextField        getJTextField();
    JCheckBox         getJCheckBox();
    JComboBox<String> getJComboBox();

    /**
     * @return value to test for {@link #getJTextField()}
     */
    default String getTestValueJTextFieldText()
    {
        return getJTextField().getText();
    }

    /**
     * @return value to test for {@link #getJCheckBox()}
     */
    default boolean getTestValueJCheckBoxSelected()
    {
        return getJCheckBox().isSelected();
    }

    /**
     * @return value to test for {@link #getJComboBox()}
     */
    default int getTestValueJComboBoxSelectedIndex()
    {
        return getJComboBox().getSelectedIndex();
    }

    static ComboBoxModel<String> getComboBoxModel()
    {
        final String[] objects = {
            "Aa", "Bbb", "Ccc", "Dddd", "Eeeee",
            };

        return new DefaultComboBoxModel<String>( objects );
    }
}
