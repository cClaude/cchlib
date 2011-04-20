/************************************************************************************
 *                                .                                                 *
 *                                .                                                 *
 ************************************************************************************/
package cx.ath.choisnet.swing;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author oma
 * @author cc
 */
public class LimitedTextField extends JTextField
{
    /**
    * Text limiter used to limit the number of characters of text fields
    * Should be used this way :
    *
    * AbstractDocument doc = (AbstractDocument) myTextComponent.getDocument();
    * doc.setDocumentFilter(new TextLimiter(maxLength));
    *
    * @author oma
    * @author cc
    *
    */
    private class TextLimiter extends DocumentFilter
    {
        public TextLimiter()
        {
            // empty
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr)
            throws BadLocationException
        {
            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
            throws BadLocationException
        {
            int newLength = fb.getDocument().getLength() - length + str.length();

            if (newLength <= maxLength) {
                fb.replace(offset, length, str, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    private static final long serialVersionUID = 1L;

    /** @serial */
    private int maxLength;

    /**
     * TextField that can be limited in size (max number of characters typed in = 100)
     */
    public LimitedTextField()
    {
        this( 100 );
    }

    /**
     * TextField that can be limited in size (max number of characters typed in)
     * @param maxLength maxLength for current JTextField
     * @throws IllegalArgumentException if maxValue is negative
     */
    public LimitedTextField( int maxLength ) throws IllegalArgumentException
    {
        super();

        setMaxLength( maxLength );

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new TextLimiter());
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength()
    {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     * @throws IllegalArgumentException if maxValue is negative
     */
    public void setMaxLength( int maxLength ) throws IllegalArgumentException
    {
        this.maxLength = maxLength;

        if( maxLength < 0 ) { // maxLength should be greater than 0
            throw new IllegalArgumentException( "maxLength should be greater than 0" );
        }

        // TODO: Check current size !
    }

    public void setText( String str )
    {
        if( str.length() > this.maxLength ) {
            throw new IllegalArgumentException( "giving String is too long, should be not bigger than: " + this.maxLength );
        }

        super.setText( str );
    }
}