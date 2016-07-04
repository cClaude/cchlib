package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.filtersconfig;

import javax.swing.DefaultComboBoxModel;

public class EnumComboBoxModel<E extends Enum<E>>
    extends DefaultComboBoxModel<E>
{
    private static final long serialVersionUID = 1L;
    private final Class<E> enumClass;

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
