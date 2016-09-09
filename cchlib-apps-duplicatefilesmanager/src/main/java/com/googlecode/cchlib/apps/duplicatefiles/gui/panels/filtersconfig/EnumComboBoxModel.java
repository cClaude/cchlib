package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.filtersconfig;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 * {@link ComboBoxModel} for {@link Enum}
 *
 * @param <E> Type of the enumeration
 */
public class EnumComboBoxModel<E extends Enum<E>>
    extends DefaultComboBoxModel<E>
{
    private static final long serialVersionUID = 1L;
    private final Class<E> enumClass;

    /**
     * Initialize model base on {@link Enum} and some values
     *
     * @param enumClass Enum class
     * @param values    Values to add in the combo
     */
    public EnumComboBoxModel( final Class<E> enumClass, final E[] values )
    {
        super( values );

        this.enumClass = enumClass;
    }

    @Override
    public E getSelectedItem()
    {
        return this.enumClass.cast( super.getSelectedItem() );
    }
}
