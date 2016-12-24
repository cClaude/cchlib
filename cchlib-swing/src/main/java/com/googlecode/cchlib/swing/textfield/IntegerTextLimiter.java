package com.googlecode.cchlib.swing.textfield;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import org.apache.log4j.Logger;

/**
 * Text limiter used to limit the value to integer and also the max integer value
 * Should be used this way :
 * <pre>
 * AbstractDocument doc = (AbstractDocument) myTextComponent.getDocument();
 * doc.setDocumentFilter(new IntegerTextLimiter(maxLength));
 * </pre>
 *
 * @author CC
 */
class IntegerTextLimiter extends DocumentFilter
{
    public interface Limits
    {
        /**
         * Returns the String to Integer conversion radix
         * @return the String to Integer conversion radix
         */
        int getRadix();

        /**
         * Returns the Integer max allowed value
         * @return the Integer max allowed value (value included)
         */
        int getMaxValue();
    }

    private static final Logger LOGGER = Logger.getLogger( IntegerTextLimiter.class );

    private final Limits limits;

    public IntegerTextLimiter( final Limits limits )
    {
        this.limits = limits;
    }

    @Override
    public void insertString(
            final DocumentFilter.FilterBypass fb,
            final int offset,
            final String str,
            final AttributeSet attrs
            )
        throws BadLocationException
    {
        replace( fb, offset, 0, str, attrs );
    }

    @Override
    @SuppressWarnings("squid:S1066")
    public void replace(
            final DocumentFilter.FilterBypass fb,
            final int offset,
            final int length,
            final String str,
            final AttributeSet attrs
            )
        throws BadLocationException
    {
        // Store previous value! (should be valid)
        final Document rdoc          = fb.getDocument().getDefaultRootElement().getDocument();
        final String   savePrevValue = rdoc.getText( 0, rdoc.getLength() );

        // Put new value
        fb.replace( offset, length, str, attrs );

        // Get new value, check it
        final String   newValue = rdoc.getText( 0, rdoc.getLength() );
        int            intValue;

        try {
            intValue = Integer.parseInt( newValue, this.limits.getRadix() );

            if( intValue <= this.limits.getMaxValue() ) {
                if( intValue >=0 ) {
                    // All OK, bye, bye
                    return;
                }
            }
        // greater than maxValue or negative
        }
        catch( final NumberFormatException e ) {
            LOGGER.warn( "set new value error - restore to last valid value", e );
            // No more and Integer, out of range !
        }

        Toolkit.getDefaultToolkit().beep(); // TODO : Use a custom toolkit (allow to configure beep())

        // clean up
        rdoc.remove( 0, rdoc.getLength() );
        // set old string
        rdoc.insertString( 0, savePrevValue, attrs );
    }
}
