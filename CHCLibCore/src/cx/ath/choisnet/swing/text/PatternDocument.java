/**
 * 
 */
package cx.ath.choisnet.swing.text;

import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 * @see java.util.regex.Pattern
 */
public class PatternDocument extends PlainDocument
{
    private static final long serialVersionUID = 1L;
    private JTextField jtf;
    private Color errorColor ;
    private Color defaultColor;

    /**
     * TODO: Doc!
     * 
     * @param jTextField
     * @param errorColor background color when an current text
     *                   is not a valid Regular Expression
     */
    public PatternDocument(
            JTextField  jTextField,
            Color       errorColor
            )
    {
        this.jtf = jTextField;
        this.errorColor = errorColor;
        //TODO: add listener on LookAndField to invalidate field !
        // create a new JTextField and get color!
        this.defaultColor = jTextField.getBackground();
    }

    /**
     * Define background color when an current text is
     * <B>not</B> a valid Regular Expression
     * 
     * @param errorColor background color when an current text
     * is <B>not</B> a valid Regular Expression
     */
    public void setErrorBackgoundColor( Color errorColor )
    {
        this.errorColor = errorColor;
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
        return this.errorColor;
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
        this.defaultColor = defaultColor;
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
        return this.defaultColor;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) 
        throws BadLocationException 
    {
        super.insertString(offs, str, a);
        check();
    }

    @Override
    public void remove(int offs, int len) 
        throws BadLocationException 
    {
        super.remove(offs, len);
        check();
    }

    private void check()
    {
        try {
            Pattern.compile( jtf.getText() );
            jtf.setBackground( defaultColor );
        }
        catch(Exception e) {
            jtf.setBackground( errorColor );
        }
    }

}
