package com.googlecode.cchlib.swing.combobox;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;

/**
 * NEEDDOC
 *
 * @param <E> NEEDDOC
 */
public class XEnumComboBox<E extends Enum<E>> extends JComboBox<String>
{
    private static class XEnumComboBoxModel implements ComboBoxModel<String>
    {
        private String   selectedItem;
        private final String[] values;

        public XEnumComboBoxModel( final Enum<?>[] enumConstants )
        {
            this.values = new String[ enumConstants.length ];

            for( int i = 0; i<enumConstants.length; i++ ) {
                this.values[ i ] = enumConstants[ i ].name();
                }
       }

        @Override
        public int getSize()
        {
            return this.values.length;
        }

        @Override
        public String getElementAt( final int index )
        {
            return this.values[ index ];
        }

        @Override
        public void addListDataListener( final ListDataListener l )
        {// No change on data model
        }

        @Override
        public void removeListDataListener( final ListDataListener l )
        {// No change on data model
         }

        @Override
        public void setSelectedItem( final Object anItem )
        {
            this.selectedItem = (String)anItem;
        }

        @Override
        public Object getSelectedItem()
        {
            return this.selectedItem;
        }
    }
    private static final long serialVersionUID = 1L;

    private final Class<E> enumClass;

    /**
     * NEEDDOC
     *
     * @param defaultValue NEEDDOC
     * @param enumClass NEEDDOC
     */
    public XEnumComboBox(
        final E         defaultValue,
        final Class<E>  enumClass
        )
    {
        super( new XEnumComboBoxModel( enumClass.getEnumConstants() ) );

        this.enumClass = enumClass;

        setSelectedItem( defaultValue );
    }

    @Override
    public void setModel( final ComboBoxModel<String> model )
    {
        if( model instanceof XEnumComboBox.XEnumComboBoxModel ) {
            super.setModel( model );
        } else {
            throw new IllegalArgumentException( model.getClass().getName() );
        }
    }

    @Override
    public void setSelectedItem( final Object anObject )
    {
        if( anObject.getClass().equals( this.enumClass ) ) {
            @SuppressWarnings("unchecked")
            final E                  enumValue = (E)anObject;
            final XEnumComboBoxModel model     = (XEnumComboBoxModel)super.getModel();

            super.setSelectedItem( model.values[ enumValue.ordinal() ] );
        } else {
            super.setSelectedItem( anObject );
        }
    }

    public E getEnumAt( final int selectedIndex )
    {
        return this.enumClass.getEnumConstants()[ selectedIndex ];
    }
}
