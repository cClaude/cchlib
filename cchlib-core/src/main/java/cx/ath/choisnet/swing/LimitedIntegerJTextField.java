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
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * @author CC
 *
 */
public class LimitedIntegerJTextField extends JTextField
{
//    private static org.apache.log4j.Logger slogger = org.apache.log4j.Logger.getLogger(LimitedIntegerTextField.class);
    private static final long serialVersionUID = 1L;
    /** @serial */
    private int maxValue;
    /** @serial */
    private int radix;

    /**
    * Text limiter used to limit the value to integer and also the max integer value
    * Should be used this way :
    *
    * AbstractDocument doc = (AbstractDocument) myTextComponent.getDocument();
    * doc.setDocumentFilter(new IntegerTextLimiter(maxLength));
    *
    * @author CC
    *
    */
    private class IntegerTextLimiter extends DocumentFilter
    {
        public IntegerTextLimiter()
        {
            // Empty
        }

//        public void remove(
//                DocumentFilter.FilterBypass fb,
//                int offset,
//                int length
//                )
//            throws BadLocationException
//        {
//            slogger.info( "remove: offset=" + offset + ", length=" + length );
//            Document doc  = fb.getDocument();
//            Document rdoc = doc.getDefaultRootElement().getDocument();
//            slogger.info( "remove before rdoc: [" + rdoc.getText( 0, rdoc.getLength() ) + "]" );
//            slogger.info( "remove before doc: [" + doc.getText( 0, doc.getLength() ) + "]" );
//            super.remove( fb, offset, length );
//            slogger.info( "remove after rdoc: [" + rdoc.getText( 0, rdoc.getLength() ) + "]" );
//            slogger.info( "remove after doc: [" + doc.getText( 0, doc.getLength() ) + "]" );
//        }

        public void insertString(
                DocumentFilter.FilterBypass fb,
                int offset,
                String str,
                AttributeSet attrs
                )
            throws BadLocationException
        {
            replace(fb, offset, 0, str, attrs);
        }

        public void replace(
                DocumentFilter.FilterBypass fb,
                int offset,
                int length,
                String str,
                AttributeSet attrs
                )
            throws BadLocationException
        {
            // Store previous value! (should be valid)
            Document rdoc          = fb.getDocument().getDefaultRootElement().getDocument();
            String   savePrevValue = rdoc.getText( 0, rdoc.getLength() );

            // Put new value
            fb.replace(offset, length, str, attrs);

            // Get new value, check it
            String   newValue = rdoc.getText( 0, rdoc.getLength() );
            int      intValue;
            try {
                intValue = Integer.parseInt( newValue, radix );

                if( intValue <= maxValue ) {
                    if( intValue >=0 ) {
                        // All OK, bye, bye
                        return;
                    }
                }
            // greater than maxValue or negative
            }
            catch( NumberFormatException e ) {
                //slogger.warn( "set new value error - restore to:" + lastValidValue, e );
                // No more and Integer, out of range !
            }

            Toolkit.getDefaultToolkit().beep();

            // clean up
            rdoc.remove( 0, rdoc.getLength() );
            // set old string
            rdoc.insertString( 0, savePrevValue, attrs );
        }
    }

    /**
     * TextField that is limited to integer values, range [0...Integer.MAX_VALUE],
     * with default radix set to 10
     */
    public LimitedIntegerJTextField()
    {
        this(Integer.MAX_VALUE,10);
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an maxValue,
     * radix is set to 10.
     * @param maxValue maxValue for current JTextField, range [0...maxValue]
     * @throws IllegalArgumentException if maxValue is negative
     */
    public LimitedIntegerJTextField(int maxValue)
    {
        this(Integer.MAX_VALUE,10);
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an maxValue
     * @param maxValue maxValue for current JTextField, range [0...maxValue]
     * @param radix
     * @throws IllegalArgumentException if maxValue is negative
     *
     */
    public LimitedIntegerJTextField(int maxValue,int radix)
        throws IllegalArgumentException
    {
        super();

        setRadix(radix);
        setMaxValue( maxValue );

        AbstractDocument doc = (AbstractDocument)getDocument();
        doc.setDocumentFilter(new IntegerTextLimiter());
    }

    public int getMaxValue()
    {
        return this.maxValue;
    }

    public int getRadix()
    {
        return radix;
    }

    public int getValue()
    {
        try {
            int value = Integer.parseInt( super.getText(), radix );

            if( value >=0 ) {
                return value;
            }
        }
        catch( NumberFormatException ignore ) {
            // continue
        }

        setValue( 0 );
        return 0;
    }

    /**
     *
     * @param maxValue Maximum integer allowed by LimitedIntegerTextField
     * @throws IllegalArgumentException if maxValue is negative
     *         or if current value is greater than this new maxValue
     */
    public void setMaxValue(int maxValue)
        throws IllegalArgumentException
    {
        if( maxValue < 0 ) { // maxValue should be greater than 0
            throw new IllegalArgumentException( "maxValue should be greater than 0" );
        }

        this.maxValue = maxValue;

        // Just to check current value
        setValue( getValue() );
    }

    /**
     *
     * @param value
     * @throws IllegalArgumentException
     */
    public void setValue( int value ) throws IllegalArgumentException
    {
        checkValue( value );
        super.setText( Integer.toString( value ) );
    }

    /**
     * Set radix
     *
     * @param radix
     * @throws IllegalArgumentException
     */
    public void setRadix(int radix)  throws IllegalArgumentException
    {
        this.radix = radix;

        // Check value, and validate radix value.
        setValue(getValue());
    }

    /**
    *
    * @param value
    * @throws IllegalArgumentException
    */
   private void checkValue( int value ) throws IllegalArgumentException
   {
       if( value > this.maxValue ) {
           //slogger.warn( "Checking: " + value );
           throw new IllegalArgumentException( String.format( "need a value(%d) less than %d ",value, this.maxValue) );
       }
       if( value < 0 ) {
           //slogger.warn( "Checking: " + value );
           throw new IllegalArgumentException( String.format( "need a value(%d) greater than: 0 ", value ) );
       }
   }
}
