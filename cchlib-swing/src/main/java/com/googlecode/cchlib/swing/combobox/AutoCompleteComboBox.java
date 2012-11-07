package com.googlecode.cchlib.swing.combobox;

import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import com.googlecode.cchlib.swing.AutoComplete;

/**
 * TODOC
 *
 */
public class AutoCompleteComboBox extends JComboBox<String> implements AutoComplete
{
    private static final long serialVersionUID = 1L;
    private class AutoCompleteFieldEditor extends BasicComboBoxEditor implements ComboBoxEditor
    {
        AutoCompleteFieldEditor( final List<String> list )
        {
            editor = new HiddenAutoCompleteTextField( list, AutoCompleteComboBox.this );
        }

        private HiddenAutoCompleteTextField getEditor()
        {
            return (HiddenAutoCompleteTextField) editor;
        }
     }

    private AutoCompleteFieldEditor autoTextFieldEditor;
    private boolean isFired;

    /**
     * TODOC
     *
     * @param list
     */
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

    @Override
    public boolean isCaseSensitive()
    {
        return autoTextFieldEditor.getEditor().isCaseSensitive();
    }

    @Override
    public void setCaseSensitive( boolean isCaseSensitive )
    {
        autoTextFieldEditor.getEditor().setCaseSensitive( isCaseSensitive );
    }

    @Override
    public boolean isStrict()
    {
        return autoTextFieldEditor.getEditor().isStrict();
    }

    @Override
    public void setStrict( final boolean isStrict )
    {
        autoTextFieldEditor.getEditor().setStrict( isStrict );
    }

    @Override
    public List<String> getDataList()
    {
        return autoTextFieldEditor.getEditor().getDataList();
    }

    @Override
    public void setDataList( final List<String> dataList )
    {
        autoTextFieldEditor.getEditor().setDataList( dataList );

        String[] array = dataList.toArray( new String[0] );

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

    @Override
    protected void fireActionEvent()
    {
        if( !isFired ) {
            super.fireActionEvent();
            }
    }
}
