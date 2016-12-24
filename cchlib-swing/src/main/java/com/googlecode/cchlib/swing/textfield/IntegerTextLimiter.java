package com.googlecode.cchlib.swing.textfield;

import java.awt.Toolkit;
import javax.annotation.Nonnegative;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import org.apache.log4j.Logger;

/**
 * Text limiter used to limit the value to integer and also the max integer
 * value Should be used this way :
 *
 * <pre>
 * AbstractDocument doc = (AbstractDocument)myTextComponent.getDocument();
 * doc.setDocumentFilter( new IntegerTextLimiter( maxLength ) );
 * </pre>
 */
class IntegerTextLimiter extends DocumentFilter
{
    public interface Limits
    {
        /**
         * Returns the Integer minimum allowed value
         *
         * @return the Integer max allowed value (value included)
         */
        @Nonnegative
        int getMinValue();

        /**
         * Returns the String to Integer conversion radix
         *
         * @return the String to Integer conversion radix
         */
        @Nonnegative
        int getRadix();

        /**
         * Returns the Integer max allowed value
         *
         * @return the Integer max allowed value (value included)
         */
        @Nonnegative
        int getMaxValue();

        /**
         * Returns text value as an integer
         * @return text value as an integer
         */
        @Nonnegative
        int getValue();

        /**
         * Sets the text of this TextComponent to the specified {@code value}
         *
         * @param value
         *            Value to set
         * @throws IllegalArgumentException
         *             if {@code value} is negative or greater than {@link #getMaxValue()}
         */
        void setValue( @Nonnegative int currentValue );
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
            final int                         offset,
            final String                      str,
            final AttributeSet                attrs
            )
        throws BadLocationException
    {
        replace( fb, offset, 0, str, attrs );
    }

    @Override
    @SuppressWarnings("squid:S1066")
    public void replace(
            final DocumentFilter.FilterBypass fb,
            final int                         offset,
            final int                         length,
            final String                      str,
            final AttributeSet                attrs
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

            if( intValue >= 0 ) {
                // Negative values are not allowed.
                if( intValue <= this.limits.getMaxValue() ) {
                    // Value is not in range, but positive integer,
                    // let user finish...
                    return;
                    }
            }
        // greater than maxValue or negative
        }
        catch( final NumberFormatException e ) {
            // No more an Integer or out of range !
            final String message = "Try to set invalid value [" + newValue
                    + "] - restaure previous value [" + savePrevValue + ']';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, e );
            } else {
                LOGGER.info( message );
            }
        }

        Toolkit.getDefaultToolkit().beep(); // TODO : Use a custom toolkit (allow to configure beep())

        // clean up
        rdoc.remove( 0, rdoc.getLength() );
        // set old string
        rdoc.insertString( 0, savePrevValue, attrs );
    }
}
