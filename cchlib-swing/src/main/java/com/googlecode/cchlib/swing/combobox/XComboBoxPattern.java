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
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class XComboBoxPattern extends XComboBox<String>
{
    private static final long serialVersionUID = 1L;

    /** Default background to display errors : {@link Color#RED} */
    public static final Color DEFAULT_ERROR_COLOR = Color.RED;

    /** @serial */
    private final JTextField textEditor = new JTextField();
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
        this.textEditor.setBorder( null ); // already a border in XComboBox
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

        for( final String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Create a XComboBoxPattern using default color to display errors
     *
     * @param regExps arrays of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern( final String...regExps )
    {
        this();

        for( final String s : regExps ) {
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

        for( final String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Create a XComboBoxPattern using default color to display errors
     *
     * @param regExps Collection of String (must be valid regular
     *                expression)
     */
    public XComboBoxPattern( final Collection<String> regExps )
    {
        this();

        for( final String s : regExps ) {
            addItem( s );
            }
    }

    /**
     * Returns the current selected item has Pattern object. If the
     * combo box is editable, then this value may not have been added
     * to the combo box with addItem, insertItemAt or the data
     * constructors.
     *
     * @return the current selected item has Pattern object, or null
     *         if there is no selected item.
     *
     * @throws java.util.regex.PatternSyntaxException
     *             if {@link Pattern} could not be created
     */
    public Pattern getSelectedPattern()
    {
        final int selected = getSelectedIndex();

        if( selected == -1 ) {
            return null;
            }
        final Object o = super.getSelectedItem();

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
    public void setErrorBackGroundColor( final Color errorColor )
    {
        this.documentEditor.setErrorBackgoundColor( errorColor );
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
        return this.documentEditor.getErrorBackgoundColor();
    }

    /**
     * Define background color when an current text is
     * a valid Regular Expression
     *
     * @param defaultColor background color when an current text
     *                     is a valid Regular Expression
     */
    public void setDefaultBackgoundColor( final Color defaultColor )
    {
        this.documentEditor.setDefaultBackgoundColor( defaultColor );
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
        return this.documentEditor.getDefaultBackgoundColor();
    }

    private class ComboBoxEditorPattern
        implements ComboBoxEditor
    {
        public ComboBoxEditorPattern( final Color errorColor )
        {
            XComboBoxPattern.this.documentEditor = new PatternDocument( XComboBoxPattern.this.textEditor, errorColor );
            XComboBoxPattern.this.textEditor.setDocument( XComboBoxPattern.this.documentEditor );
        }
        @Override
        public void addActionListener( final ActionListener l )
        {
            XComboBoxPattern.this.textEditor.addActionListener( l );
        }
        @Override
        public void removeActionListener( final ActionListener l )
        {
            XComboBoxPattern.this.textEditor.removeActionListener( l );
        }
        @Override
        public Component getEditorComponent()
        {
            return XComboBoxPattern.this.textEditor;
        }
        @Override
        public void selectAll()
        {
            XComboBoxPattern.this.textEditor.selectAll();
        }
        @Override
        public Object getItem()
        {
            return XComboBoxPattern.this.textEditor.getText();
        }
        @Override
        public void setItem( final Object anObject )
        {
            if ( anObject != null ) {
                XComboBoxPattern.this.textEditor.setText( anObject.toString() );
                }
            else {
                XComboBoxPattern.this.textEditor.setText( StringHelper.EMPTY );
                }
            }
    }
}
