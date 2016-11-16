package com.googlecode.cchlib.swing.combobox;

import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import com.googlecode.cchlib.swing.AutoComplete;

/**
 * NEEDDOC
 *
 */
public class AutoCompleteComboBox extends JComboBox<String> implements AutoComplete
{
    private static final long serialVersionUID = 1L;
    private class AutoCompleteFieldEditor extends BasicComboBoxEditor implements ComboBoxEditor
    {
        AutoCompleteFieldEditor( final List<String> list )
        {
            this.editor = new HiddenAutoCompleteTextField( list, AutoCompleteComboBox.this );
        }

        private HiddenAutoCompleteTextField getEditor()
        {
            return (HiddenAutoCompleteTextField) this.editor;
        }
     }

    private final AutoCompleteFieldEditor autoTextFieldEditor;
    private boolean isFired;

    /**
     * NEEDDOC
     *
     * @param list
     */
    public AutoCompleteComboBox( final List<String> list )
    {
        this.isFired = false;
        this.autoTextFieldEditor = new AutoCompleteFieldEditor( list );
        setEditable( true );

        final String[] array = list.toArray( new String[ 0 ] );

        setModel( new DefaultComboBoxModel<String>( array )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void fireContentsChanged(final Object obj, final int i, final int j)
            {
                if( !AutoCompleteComboBox.this.isFired ) {
                    super.fireContentsChanged(obj, i, j);
                    }
                }
            });

        super.setEditor( this.autoTextFieldEditor );
    }

    @Override
    public boolean isCaseSensitive()
    {
        return this.autoTextFieldEditor.getEditor().isCaseSensitive();
    }

    @Override
    public void setCaseSensitive( final boolean isCaseSensitive )
    {
        this.autoTextFieldEditor.getEditor().setCaseSensitive( isCaseSensitive );
    }

    @Override
    public boolean isStrict()
    {
        return this.autoTextFieldEditor.getEditor().isStrict();
    }

    @Override
    public void setStrict( final boolean isStrict )
    {
        this.autoTextFieldEditor.getEditor().setStrict( isStrict );
    }

    @Override
    public List<String> getDataList()
    {
        return this.autoTextFieldEditor.getEditor().getDataList();
    }

    @Override
    public void setDataList( final List<String> dataList )
    {
        this.autoTextFieldEditor.getEditor().setDataList( dataList );

        final String[] array = dataList.toArray( new String[ dataList.size() ] );

        setModel( new DefaultComboBoxModel<String>( array ) );
    }

    void setSelectedValue( final String str )
    {
        if( this.isFired ) {
            return;
            }
        else {
            this.isFired = true;
            setSelectedItem( str );
            fireItemStateChanged (new ItemEvent(this, 701, this.selectedItemReminder, 1) );
            this.isFired = false;

            return;
            }
    }

    @Override
    protected void fireActionEvent()
    {
        if( !this.isFired ) {
            super.fireActionEvent();
            }
    }
}
