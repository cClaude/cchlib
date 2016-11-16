package com.googlecode.cchlib.swing.combobox;

import java.util.List;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import com.googlecode.cchlib.swing.AutoComplete;

/**
 * NEEDDOC
 *
 */
public class HiddenAutoCompleteTextField
    extends JTextField
        implements AutoComplete
{
    private static final long serialVersionUID = 1L;

    class AutoDocument extends PlainDocument
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void replace( final int i, final int j, final String s, final AttributeSet attributeset )
            throws BadLocationException
        {
            super.remove( i, j );

            insertString(i, s, attributeset);
        }

        @Override
        public void insertString( final int i, final String s, final AttributeSet attributeset )
            throws BadLocationException
        {
            if( (s == null) || s.isEmpty() ) {
                return;
                }

            final String s1 = getText(0, i);
            String s2 = getMatch(s1 + s);
            int    j  = (i + s.length()) - 1;

            if( HiddenAutoCompleteTextField.this.isStrict && (s2 == null) ) {
                s2 = getMatch( s1 );
                j--;
                }
            else if( !HiddenAutoCompleteTextField.this.isStrict && (s2 == null) ) {
                super.insertString(i, s, attributeset);

                return;
                }

            if( (HiddenAutoCompleteTextField.this.autoCompleteComboBox != null) && (s2 != null) ) {
                HiddenAutoCompleteTextField.this.autoCompleteComboBox.setSelectedValue(s2);
                }

            super.remove(0, getLength());
            super.insertString(0, s2, attributeset);

            setSelectionStart(j + 1);
            setSelectionEnd(getLength());
        }

        @Override
        public void remove( final int i, final int j ) throws BadLocationException
        {
            int k = getSelectionStart();

            if( k > 0 ) {
                k--;
                }

            final String s = getMatch( getText( 0, k ) );

            if( !HiddenAutoCompleteTextField.this.isStrict && (s == null) ) {
                super.remove(i, j);
                }
            else {
                super.remove(0, getLength());
                super.insertString(0, s, null);
                }

            if( (HiddenAutoCompleteTextField.this.autoCompleteComboBox != null) && (s != null) ) {
                HiddenAutoCompleteTextField.this.autoCompleteComboBox.setSelectedValue( s );
                }

            try {
                setSelectionStart( k );
                setSelectionEnd( getLength() );
                }
            catch( final Exception ignore ) { // $codepro.audit.disable logExceptions, emptyCatchClause
                }
            }

        private String getMatch( final String s )
        {
            for( int i = 0; i < HiddenAutoCompleteTextField.this.dataList.size(); i++ ) {
                final String s1 = HiddenAutoCompleteTextField.this.dataList.get( i );

                if( s1 != null ) {
                    if( !HiddenAutoCompleteTextField.this.isCaseSensitive && s1.toLowerCase().startsWith( s.toLowerCase() ) ) {
                        return s1;
                        }

                    if( HiddenAutoCompleteTextField.this.isCaseSensitive && s1.startsWith( s ) ) {
                        return s1;
                        }
                    }
                }

            return null;
        }
    }

    private List<String>         dataList;
    private boolean              isCaseSensitive;
    private boolean              isStrict;
    private AutoCompleteComboBox autoCompleteComboBox;

    protected HiddenAutoCompleteTextField( final List<String> valuesList )
    {
        this( valuesList, null );
    }

    HiddenAutoCompleteTextField(
        final List<String>         valuesList,
        final AutoCompleteComboBox autoCompleteComboBox
        )
    {
        this.isCaseSensitive    = false;
        this.isStrict           = true;

        if( valuesList == null ) {
            throw new IllegalArgumentException( "valuesList can not be null" );
            }
        else {
            this.dataList             = valuesList;
            this.autoCompleteComboBox = autoCompleteComboBox;

            init();
            }
    }

    private void init()
    {
        setDocument( new AutoDocument() );

        if( this.isStrict && (! this.dataList.isEmpty()) ) {
            setText( this.dataList.get(0) );
            }
    }

    @Override
    public void replaceSelection( final String s )
    {
        final AutoDocument lb = (AutoDocument) getDocument();

        if( lb != null ) {
            try {
                final int i = Math.min( getCaret().getDot(), getCaret().getMark() );
                final int j = Math.max( getCaret().getDot(), getCaret().getMark() );

                lb.replace( i, j - i, s, null );
                }
            catch( final Exception exception ) { // $codepro.audit.disable logExceptions, emptyCatchClause
                }
            }
    }

    @Override // AutoComplete
    public boolean isCaseSensitive()
    {
        return this.isCaseSensitive;
    }

    @Override // AutoComplete
    public void setCaseSensitive( final boolean isCaseSensitive )
    {
        this.isCaseSensitive = isCaseSensitive;
    }

    @Override // AutoComplete
    public boolean isStrict()
    {
        return this.isStrict;
    }

    @Override // AutoComplete
    public void setStrict( final boolean isStrict )
    {
        this.isStrict = isStrict;
    }

    @Override // AutoComplete
    public List<String> getDataList()
    {
        return this.dataList;
    }

    @Override // AutoComplete
    public void setDataList( final List<String> dataList )
    {
        if( dataList == null ) {
            throw new IllegalArgumentException( "valuesList can not be null" );
            }
        else {
            this.dataList = dataList;
            }
    }
}
