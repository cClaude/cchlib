package com.googlecode.cchlib.swing.textfield;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import org.apache.log4j.Logger;

/**
 * NEEDDOC
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class LimitedIntegerJTextField extends JTextField
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( LimitedIntegerJTextField.class );

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

        @Override
        public void insertString(
                final DocumentFilter.FilterBypass fb,
                final int offset,
                final String str,
                final AttributeSet attrs
                )
            throws BadLocationException
        {
            replace(fb, offset, 0, str, attrs);
        }

        @Override
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
            fb.replace(offset, length, str, attrs);

            // Get new value, check it
            final String   newValue = rdoc.getText( 0, rdoc.getLength() );
            int            intValue;

            try {
                intValue = Integer.parseInt( newValue, LimitedIntegerJTextField.this.radix );

                if( intValue <= LimitedIntegerJTextField.this.maxValue ) {
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
        this( Integer.MAX_VALUE, 10 );
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an maxValue,
     * radix is set to 10.
     * @param maxValue maxValue for current JTextField, range [0...maxValue]
     * @throws IllegalArgumentException if maxValue is negative
     */
    public LimitedIntegerJTextField( final int maxValue )
    {
        this( maxValue, 10 );
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an maxValue
     * @param maxValue maxValue for current JTextField, range [0...maxValue]
     * @param radix
     * @throws IllegalArgumentException if maxValue is negative
     *
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField( final int maxValue, final int radix )
        throws IllegalArgumentException
    {
        super();

        setRadix(radix);
        setMaxValue( maxValue );

        final AbstractDocument doc = (AbstractDocument)getDocument();
        doc.setDocumentFilter(new IntegerTextLimiter());
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public int getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public int getRadix()
    {
        return this.radix;
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public int getValue()
    {
        try {
            final int value = Integer.parseInt( super.getText(), this.radix );

            if( value >=0 ) {
                return value;
                }
            }
        catch( final NumberFormatException ignore ) {
            // continue
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "Text: " + super.getText(), ignore );
                }
            }

        setValue( 0 );
        return 0;
    }

    /**
     * NEEDDOC
     * @param maxValue Maximum integer allowed by LimitedIntegerTextField
     * @throws IllegalArgumentException if maxValue is negative
     *         or if current value is greater than this new maxValue
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setMaxValue(final int maxValue)
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
     * NEEDDOC
     * @param value
     * @throws IllegalArgumentException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setValue( final int value ) throws IllegalArgumentException
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
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setRadix( final int radix ) throws IllegalArgumentException
    {
        this.radix = radix;

        // Check value, and validate radix value.
        setValue( getValue() );
    }

    /**
     * NEEDDOC
     * @param value
     * @throws IllegalArgumentException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void checkValue( final int value ) throws IllegalArgumentException
    {
        if( value > this.maxValue ) {
            throw new IllegalArgumentException(
                String.format( "need a value(%d) less than %d ",Integer.valueOf( value ), Integer.valueOf( this.maxValue ) )
                );
            }

        if( value < 0 ) {
            throw new IllegalArgumentException(
                String.format( "need a value(%d) greater than: 0 ", Integer.valueOf( value ) )
                );
            }
    }
}
