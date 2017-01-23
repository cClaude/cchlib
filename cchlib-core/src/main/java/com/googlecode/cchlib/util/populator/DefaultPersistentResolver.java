package com.googlecode.cchlib.util.populator;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Default implementation for {@link PersistentResolver}
 *
 * @see PersistentResolverFactories#newDefaultPersistentResolverFactory()
 *
 *  @since 4.2
 */
public class DefaultPersistentResolver implements PersistentResolver
{
    /**
     * Create default {@link PersistentResolver}, could be extended.
     */
    protected DefaultPersistentResolver()
    {
        // Empty
    }

    /**
     * Handle JTextField
     *
     * @param textField
     *            The JTextField object
     * @return corresponding String
     *
     * @see #setValue(JTextField, String)
     */
    public String toString( final JTextField textField )
    {
        return textField.getText();
    }

    /**
     * Set {@code strValue} on {@code textField}
     *
     * @param textField
     *            The JTextField object
     * @param strValue
     *            The expected value
     *
     * @see #toString(JTextField)
     */
    public void setValue( final JTextField textField, final String strValue )
    {
        textField.setText( strValue );
    }

    /**
     * Handle JCheckBox
     *
     * @param checkBox
     *            The JCheckBox object
     * @return corresponding String
     *
     * @see #setValue(JCheckBox, String)
     * @see JCheckBox#isSelected()
     */
    public String toString( final JCheckBox checkBox )
    {
        return Boolean.toString( checkBox.isSelected() );
    }

    /**
     * Set {@code strValue} on {@code checkBox}
     *
     * @param checkBox
     *            The JCheckBox object
     * @param strValue
     *            The expected value (must a parsable has a boolean)
     *
     * @see #toString(JCheckBox)
     * @see JCheckBox#setSelected(boolean)
     */
    public void setValue( final JCheckBox checkBox, final String strValue )
    {
        checkBox.setSelected( Boolean.parseBoolean( strValue ) );
    }

    /**
     * Handle JComboBox
     *
     * @param comboBox
     *            The JComboBox object
     * @return corresponding String
     * @see #setValue(JComboBox, String)
     * @see JComboBox#getSelectedIndex()
     */
    public String toString( final JComboBox<?> comboBox )
    {
        final int index = comboBox.getSelectedIndex(); // Store only selected index

        return Integer.toString( index );
    }

    /**
     * Set {@code strValue} on {@code comboBox}
     *
     * @param comboBox
     *            The JComboBox object
     * @param strValue
     *            The expected value (must a parsable has an integer)
     *
     * @see #toString(JComboBox)
     * @see JComboBox#setSelectedIndex(int)
     */
    public void setValue( final JComboBox<?> comboBox, final String strValue )
    {
        final int index = Integer.parseInt( strValue );

        comboBox.setSelectedIndex( index );
    }
}
