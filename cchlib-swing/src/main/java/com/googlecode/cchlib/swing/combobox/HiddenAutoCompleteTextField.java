package com.googlecode.cchlib.swing.combobox;

import java.util.List;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.googlecode.cchlib.swing.AutoComplete;

/**
 * TODOC
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
        public void replace( int i, int j, String s, AttributeSet attributeset )
            throws BadLocationException
        {
            super.remove( i, j );

            insertString(i, s, attributeset);
        }

        @Override
        public void insertString( int i, String s, AttributeSet attributeset )
            throws BadLocationException
        {
            if( s == null || s.isEmpty() ) {
                return;
                }

            String s1 = getText(0, i);
            String s2 = getMatch(s1 + s);
            int    j  = (i + s.length()) - 1;

            if( isStrict && s2 == null ) {
                s2 = getMatch( s1 );
                j--;
                }
            else if( !isStrict && s2 == null ) {
                super.insertString(i, s, attributeset);

                return;
                }

            if( autoCompleteComboBox != null && s2 != null ) {
                autoCompleteComboBox.setSelectedValue(s2);
                }

            super.remove(0, getLength());
            super.insertString(0, s2, attributeset);

            setSelectionStart(j + 1);
            setSelectionEnd(getLength());
        }

        @Override
        public void remove( int i, int j ) throws BadLocationException
        {
            int k = getSelectionStart();

            if( k > 0 ) {
                k--;
                }

            String s = getMatch( getText( 0, k ) );

            if( !isStrict && s == null ) {
                super.remove(i, j);
                }
            else {
                super.remove(0, getLength());
                super.insertString(0, s, null);
                }

            if( autoCompleteComboBox != null && s != null ) {
                autoCompleteComboBox.setSelectedValue( s );
                }

            try {
                setSelectionStart(k);
                setSelectionEnd(getLength());
                }
            catch( Exception ignore ) { // $codepro.audit.disable logExceptions, emptyCatchClause
                }
            }
    }

    private List<String> dataList;
    private boolean isCaseSensitive;
    private boolean isStrict;
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

        if( isStrict && dataList.size() > 0 ) {
            setText( dataList.get(0).toString() );
            }
    }

    private String getMatch( final String s )
    {
        for( int i = 0; i < dataList.size(); i++ ) {
            String s1 = dataList.get( i );

            if( s1 != null ) {
                if( !isCaseSensitive && s1.toLowerCase().startsWith( s.toLowerCase() ) ) {
                    return s1;
                    }

                if( isCaseSensitive && s1.startsWith( s ) ) {
                    return s1;
                    }
                }
            }

        return null;
    }

    @Override
    public void replaceSelection( final String s )
    {
        AutoDocument _lb = (AutoDocument) getDocument();

        if( _lb != null ) {
            try {
                int i = Math.min( getCaret().getDot(), getCaret().getMark() );
                int j = Math.max( getCaret().getDot(), getCaret().getMark() );

                _lb.replace( i, j - i, s, null );
                }
            catch( Exception exception ) { // $codepro.audit.disable logExceptions, emptyCatchClause
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
        return dataList;
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
