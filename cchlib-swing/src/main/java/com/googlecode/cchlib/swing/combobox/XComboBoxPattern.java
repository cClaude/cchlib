package com.googlecode.cchlib.swing.combobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.text.PatternDocument;

/**
 * JComboBox for Regular Expressions
 *
 * @see PatternDocument
 * @see Pattern
 * @since 4.1.6
 */
public class XComboBoxPattern extends XComboBox<String>
{
    private static final long serialVersionUID = 1L;
    /** Default background to display errors : {@link Color#RED} */
    public static final Color DEFAULT_ERROR_COLOR = Color.RED;
    /** @serial */
    private JTextField editor = new JTextField();
    /** @serial */
    private PatternDocument documentEditor;

    /**
     * Create an empty XComboBoxPattern using default error Color.
     */
    public XComboBoxPattern()
    {
        this( DEFAULT_ERROR_COLOR );
    }

    /**
     * Create an empty XComboBoxPattern
     *
     * @param errorColor background color when an current text
     *                   is <B>not</B> a valid Regular Expression
     */
    public XComboBoxPattern( final Color errorColor )
    {
        super( String.class );

        this.setEditor( new ComboBoxEditorPattern( errorColor ) );
        this.editor.setBorder( null ); // already a border in XComboBox
    }

    /**
     * Create a XComboBoxPattern
     *
     * @param errorColor background color when an current text
     *                   is <B>not</B> a valid Regular Expression
     * @param regExps arrays of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern(
            final Color    errorColor,
            final String...regExps
            )
    {
        this( errorColor );

        for( String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Create a XComboBoxPattern using default color to display errors
     *
     * @param regExps arrays of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern( String...regExps )
    {
        this();

        for( String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Create a XComboBoxPattern
     *
     * @param errorColor background color when an current text
     *                   is <B>not</B> a valid Regular Expression
     * @param regExps Collection of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern(
            final Color                 errorColor,
            final Collection<String>    regExps
            )
    {
        this( errorColor );

        for( String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Create a XComboBoxPattern using default color to display errors
     *
     * @param regExps Collection of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern( Collection<String> regExps )
    {
        this();

        for( String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Returns the current selected item has Pattern object.
     * If the combo box is editable, then this value may not
     * have been added to the combo box with addItem,
     * insertItemAt or the data constructors.
     *
     * @return the current selected item has Pattern object,
     * or null if there is no selected item.
     *
     * @throws java.util.regex.PatternSyntaxException
     */
    public Pattern getSelectedPattern()
    {
        int selected = getSelectedIndex();

        if( selected == -1 ) {
            return null;
            }
        Object o = super.getSelectedItem();

        // java.util.regex.PatternSyntaxException
        return Pattern.compile( o.toString() );
    }


    /**
     * Define background color when an current text is
     * <B>not</B> a valid Regular Expression
     *
     * @param errorColor background color when an current text
     *                   is <B>not</B> a valid Regular Expression
     */
    public void setErrorBackGroundColor( Color errorColor )
    {
        documentEditor.setErrorBackgoundColor( errorColor );
    }

    /**
     * Returns background color when an current text is
     * <B>not</B> a valid Regular Expression
     *
     * @return background color when an current text
     *                    is <B>not</B> a valid Regular Expression
     */
    public Color getErrorBackgoundColor()
    {
        return documentEditor.getErrorBackgoundColor();
    }

    /**
     * Define background color when an current text is
     * a valid Regular Expression
     *
     * @param defaultColor background color when an current text
     *                     is a valid Regular Expression
     */
    public void setDefaultBackgoundColor( Color defaultColor )
    {
        documentEditor.setDefaultBackgoundColor( defaultColor );
    }

    /**
     * Returns background color when an current text is
     * a valid Regular Expression
     *
     * @return background color when an current text
     * is a valid Regular Expression
     */
    public Color getDefaultBackgoundColor()
    {
        return documentEditor.getDefaultBackgoundColor();
    }

    private class ComboBoxEditorPattern
        implements ComboBoxEditor
    {
        public ComboBoxEditorPattern( final Color errorColor )
        {
            documentEditor = new PatternDocument( editor, errorColor );
            editor.setDocument( documentEditor );
        }
        @Override
        public void addActionListener( final ActionListener l )
        {
            editor.addActionListener( l );
        }
        @Override
        public void removeActionListener( final ActionListener l )
        {
            editor.removeActionListener( l );
        }
        @Override
        public Component getEditorComponent()
        {
            return editor;
        }
        @Override
        public void selectAll()
        {
            editor.selectAll();
        }
        @Override
        public Object getItem()
        {
            return editor.getText();
        }
        @Override
        public void setItem( final Object anObject )
        {
            if ( anObject != null ) {
                editor.setText( anObject.toString() );
                }
            else {
                editor.setText( StringHelper.EMPTY );
                }
            }
    }
}
