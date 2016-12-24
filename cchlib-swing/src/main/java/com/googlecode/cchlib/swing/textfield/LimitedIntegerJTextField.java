package com.googlecode.cchlib.swing.textfield;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.textfield.IntegerTextLimiter.Limits;

/**
 * {@link LimitedIntegerJTextField} is a {@link JTextField} with
 * copy/paste ({@link XTextField}) support and limit content text
 * to an positive integer.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class LimitedIntegerJTextField extends XTextField implements Limits
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( LimitedIntegerJTextField.class );

    /** @serial */
    private int maxValue;
    /** @serial */
    private int radix;

    /**
     * TextField that is limited to integer values, range [0...Integer.MAX_VALUE],
     * with default radix set to 10
     */
    public LimitedIntegerJTextField()
    {
        this( Integer.MAX_VALUE, 10 );
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an
     * maxValue, radix is set to 10.
     *
     * @param maxValue
     *            maxValue for current JTextField, range [0...maxValue]
     * @throws IllegalArgumentException
     *             if maxValue is negative
     */
    public LimitedIntegerJTextField( final int maxValue )
    {
        this( maxValue, 10 );
    }

    /**
     * TextField that is limited to integer values, and limit this integer to an maxValue
     *
     * @param maxValue
     *            maxValue for current JTextField, range [0...maxValue]
     * @param radix
     *            The radix value for String to Integer conversion.
     * @throws IllegalArgumentException
     *             if maxValue is negative
     *
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField( final int maxValue, final int radix )
        throws IllegalArgumentException
    {
        super();

        setRadix( radix );
        setMaxValue( maxValue );

        final AbstractDocument doc = (AbstractDocument)getDocument();
        doc.setDocumentFilter( new IntegerTextLimiter(this) );
    }

    /**
     * Returns the Integer max allowed value
     * @return the Integer max allowed value (value included)
     */
    @Override
    public int getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * Returns the String to Integer conversion radix
     * @return the String to Integer conversion radix
     */
    @Override
    public int getRadix()
    {
        return this.radix;
    }

    /**
     * Returns text value as an integer
     * @return text value as an integer
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
     * Set the maximum value allowed
     *
     * @param maxValue Maximum integer allowed by LimitedIntegerTextField
     * @throws IllegalArgumentException if maxValue is negative
     *         or if current value is greater than this new maxValue
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setMaxValue( final int maxValue )
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
     * Sets the text of this TextComponent to the specified {@code value}
     *
     * @param value
     *            Value to set
     * @throws IllegalArgumentException
     *             if {@code value} is negative or greater than {@link #getMaxValue()}
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
     * @param radix NEEDDOC
     * @throws IllegalArgumentException NEEDDOC
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setRadix( final int radix ) throws IllegalArgumentException
    {
        this.radix = radix;

        // Check value, and validate radix value.
        setValue( getValue() );
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void checkValue( final int value ) throws IllegalArgumentException
    {
        if( value > this.maxValue ) {
            throw new IllegalArgumentException(
                String.format(
                        "need a value(%d) less than %d ",
                        Integer.valueOf( value ),
                        Integer.valueOf( this.maxValue )
                        )
                );
            }

        if( value < 0 ) {
            throw new IllegalArgumentException(
                String.format(
                        "need a value(%d) greater than: 0 ",
                        Integer.valueOf( value )
                        )
                );
            }
    }
}
