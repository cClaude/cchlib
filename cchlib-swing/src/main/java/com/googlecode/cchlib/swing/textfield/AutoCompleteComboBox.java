package com.googlecode.cchlib.swing.textfield;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

class AutoCompleteComboBox extends JComboBox<String>
{
    private static final long serialVersionUID = 1L;
    private class AutoCompleteFieldEditor extends BasicComboBoxEditor implements ComboBoxEditor
    {
        AutoCompleteFieldEditor( final List<String> list )
        {
            editor = new AutoCompleteTextField( list, AutoCompleteComboBox.this );
        }

        private AutoCompleteTextField getEditor()
        {
            return (AutoCompleteTextField) editor;
        }
     }

    private AutoCompleteFieldEditor autoTextFieldEditor;
    private boolean isFired;

    public AutoCompleteComboBox( final List<String> list )
    {
        isFired = false;
        autoTextFieldEditor = new AutoCompleteFieldEditor( list );
        setEditable( true );

        String[] array = list.toArray( new String[ 0 ] );

        setModel( new DefaultComboBoxModel<String>( array )
        {
            private static final long serialVersionUID = 1L;

            protected void fireContentsChanged(Object obj, int i, int j)
            {
                if( !isFired ) {
                    super.fireContentsChanged(obj, i, j);
                    }
                }
            });

        super.setEditor( autoTextFieldEditor );
    }

    public boolean isCaseSensitive()
    {
        return autoTextFieldEditor.getEditor().isCaseSensitive();
    }

    public void setCaseSensitive( boolean flag )
    {
        autoTextFieldEditor.getEditor().setCaseSensitive( flag );
    }

    public boolean isStrict()
    {
        return autoTextFieldEditor.getEditor().isStrict();
    }

    public void setStrict( final boolean flag )
    {
        autoTextFieldEditor.getEditor().setStrict( flag );
    }

    public List<String> getDataList()
    {
        return autoTextFieldEditor.getEditor().getDataList();
    }

    public void setDataList( final List<String> list )
    {
        autoTextFieldEditor.getEditor().setDataList(list);

        String[] array = list.toArray( new String[0] );

        setModel( new DefaultComboBoxModel<String>( array ) );
    }

    void setSelectedValue( final String str )
    {
        if( isFired ) {
            return;
            }
        else {
            isFired = true;
            setSelectedItem( str );
            fireItemStateChanged (new ItemEvent(this, 701, selectedItemReminder, 1) );
            isFired = false;

            return;
            }
    }

    protected void fireActionEvent()
    {
        if( !isFired ) {
            super.fireActionEvent();
        	}
    }
}
