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
 */
public class PatternDocument extends PlainDocument
{
    private static final long serialVersionUID = 1L;
    private JTextField jtf;
    private Color errorColor ;
    private Color defaultColor;

    /**
     * 
     * @param jTextField
     * @param errorColor
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
