package com.googlecode.cchlib.swing.combobox;

import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * TODOC
 *
 * @param <E>
 */
public class XEnumComboBox<E extends Enum<E>> extends JComboBox<String>
{
    private static class XEnumComboBoxModel implements ComboBoxModel<String>
    {
        private String   selectedItem;
        private String[] values;

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
            return values.length;
        }

        @Override
        public String getElementAt( int index )
        {
            return values[ index ];
        }

        @Override
        public void addListDataListener( ListDataListener l )
        {// No change on data model
        }

        @Override
        public void removeListDataListener( ListDataListener l )
        {// No change on data model
         }

        @Override
        public void setSelectedItem( Object anItem )
        {
            selectedItem = (String)anItem;
        }

        @Override
        public Object getSelectedItem()
        {
            return selectedItem;
        }
    }
    private static final long serialVersionUID = 1L;

    private Class<E> enumClass;

    /**
     * TODOC
     *
     * @param defaultValue
     * @param autoI18n
     * @param values
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
            final E                  enumValue = ((E)anObject);
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
