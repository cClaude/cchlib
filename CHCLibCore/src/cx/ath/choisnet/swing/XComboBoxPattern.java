package cx.ath.choisnet.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import cx.ath.choisnet.swing.text.PatternDocument;

/**
 * JComboBox for Regular Expressions
 *
 * @author Claude CHOISNET
 * @see PatternDocument
 */
public class XComboBoxPattern extends XComboBox
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private JTextField editor = new JTextField();
    /** @serial */
    private PatternDocument documentEditor;

    /**
     * Create a XComboBoxPattern using red error Color.
     */
    public XComboBoxPattern()
    {
        this(Color.RED);
    }

    /**
     * Create a XComboBoxPattern
     *
     * @param errorColor background color when an current text
     * is <B>not</B> a valid Regular Expression
     */
    public XComboBoxPattern( Color errorColor )
    {
        super();

        this.setEditor( new ComboBoxEditorPattern( errorColor ) );
    }

    /**
     * Create a XComboBoxPattern
     *
     * @param regExps arrays of String (must be valid regular
     * expression)
     */
    public XComboBoxPattern( String[] regExps )
    {
        this();

        for(String s:regExps) {
            addItem(s);
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
     * is <B>not</B> a valid Regular Expression
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
     * is <B>not</B> a valid Regular Expression
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
     * is a valid Regular Expression
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
        public ComboBoxEditorPattern( Color errorColor )
        {
            documentEditor = new PatternDocument( editor, errorColor );
            editor.setDocument( documentEditor );
        }
        @Override
        public void addActionListener( ActionListener l )
        {
            editor.addActionListener( l );
        }
        @Override
        public void removeActionListener( ActionListener l )
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
        public void setItem( Object anObject )
        {
            if ( anObject != null ) {
                editor.setText( anObject.toString() );
                }
            else {
                editor.setText( "" );
                }
            }
    }
}
