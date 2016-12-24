package com.googlecode.cchlib.swing.textfield;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.annotation.Nonnegative;
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
public class LimitedIntegerJTextField extends XTextField
    implements IntegerTextLimiter.Limits
{
    private static final class LimitedIntegerFocusListener implements FocusListener
    {
        private final Limits limits;

        private LimitedIntegerFocusListener( final Limits limits )
        {
            this.limits = limits;
        }

        @Override
        public void focusGained( final FocusEvent event )
        {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "focusGained( " + event + " )" );
            }
        }

        @Override
        public void focusLost( final FocusEvent event )
        {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "focusLost( " + event + " )" );
            }

            // Ensure this is an Integer...
            final int currentValue = this.limits.getValue();

            try {
                // Ensure limits are respected
                this.limits.setValue( currentValue );
            } catch( final IllegalArgumentException e ) {
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( e.getMessage(), e );
                } else {
                    LOGGER.info( e.getMessage() );
                }

                // Find best match...
                if( currentValue > this.limits.getMaximum() ) {
                    this.limits.setValue( this.limits.getMaximum() );
                }
                if( currentValue < this.limits.getMinimum() ) {
                    this.limits.setValue( this.limits.getMinimum() );
                }
            }
        }
    }

    private static final long serialVersionUID = 2L;

    private static final Logger LOGGER = Logger.getLogger( LimitedIntegerJTextField.class );

    /** @serial */
    private int minimumValue;
    /** @serial */
    private int maximumValue;
    /** @serial */
    private int radix;

    /**
     * TextField that is limited to integer values, range [0...Integer.MAX_VALUE],
     * with default radix set to 10
     */
    public LimitedIntegerJTextField()
    {
        this( 0, Integer.MAX_VALUE, 10 );
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
    public LimitedIntegerJTextField( @Nonnegative final int maxValue )
    {
        this( 0, maxValue, 10 );
    }

    /**
     * TextField that is limited to integer values, and limit this integer to positive
     * range : [{@code minValue}...{@code maxValue}]
     *
     * @param minValue
     *            {@code minValue} for current JTextField,
     *            range [{@code minValue}...{@code maxValue}],
     *            {@code minValue} should be zero or positive.
     * @param maxValue
     *            {@code maxValue} for current JTextField,
     *            range [{@code minValue}...{@code maxValue}],
     *            {@code maxValue} should be &gt;= {@code minValue}
     * @param radix
     *            The radix value for String to Integer conversion.
     * @throws IllegalArgumentException
     *             if {@code minValue} is negative, if
     *
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField(
        @Nonnegative final int minValue,
        @Nonnegative final int maxValue,
        @Nonnegative final int radix
        ) throws IllegalArgumentException
    {
        super();

        setMinimum( minValue );
        setMaximum( maxValue );
        setRadix( radix );

        final AbstractDocument doc = (AbstractDocument)getDocument();
        doc.setDocumentFilter( new IntegerTextLimiter( this ) );

        addFocusListener( new LimitedIntegerFocusListener( this ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnegative
    public int getMinimum()
    {
        return this.minimumValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnegative
    public int getMaximum()
    {
        return this.maximumValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRadix()
    {
        return this.radix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnegative
    public int getValue()
    {
        try {
            final int value = Integer.parseInt( super.getText(), this.radix );

            if( value < this.minimumValue  ) {
                setValue( this.minimumValue );

                return this.minimumValue;
            } else if( value > this.maximumValue  ) {
                setValue( this.maximumValue );

                return this.maximumValue;
            } else { /* (value >= this.minimumValue) && (value <= this.maximumValue) */
                return value;
            }
        }
        catch( final NumberFormatException ignore ) {
            // continue using minimum
            final String message = "Text: " + super.getText()
                    + " use minValue(" + this.minimumValue + ')';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, ignore );
            } else {
                LOGGER.warn( message );
            }

            setValue( this.minimumValue );
            return this.minimumValue;
        }
    }

    /**
     * Set the minimum value allowed
     *
     * @param minValue Minimum integer allowed by this component
     * @return this component for initialization chaining
     * @throws IllegalArgumentException if maxValue is negative
     *         or if current value is greater than this new maxValue
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField setMinimum( @Nonnegative final int minValue )
        throws IllegalArgumentException
    {
        if( minValue < 0 ) { // minValue should be greater than 0
            throw new IllegalArgumentException( "minValue should be greater than 0" );
        }

        this.minimumValue = minValue;

        // Align current value with constraints
        setValue( getValue() );

        return this;
    }

    /**
     * Set the maximum value allowed
     *
     * @param maxValue Maximum integer allowed by this component
     * @return this component for initialization chaining
     * @throws IllegalArgumentException if maxValue is negative
     *         or if current value is greater than this new maxValue
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField setMaximum( @Nonnegative final int maxValue )
        throws IllegalArgumentException
    {
        if( maxValue < this.minimumValue ) { // maxValue should be greater than minValue
            throw new IllegalArgumentException(
                "maxValue should be greater than minValue (" + this.minimumValue + ')'
                );
        }

        this.maximumValue = maxValue;

        // Align current value with constraints
        setValue( getValue() );

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public void setValue( final int value ) throws IllegalArgumentException
    {
        checkValue( value );

        super.setText( Integer.toString( value ) );
    }

    /**
     * Set the radix
     *
     * @param radix the radix
     * @return this component for initialization chaining
     * @throws IllegalArgumentException The radix is either smaller than
     *          @java.lang.Character.MIN_RADIX or larger than
     *          java.lang.Character.MAX_RADIX.
     * @see Character#digit(char, int)
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public LimitedIntegerJTextField setRadix( final int radix )
        throws IllegalArgumentException
    {
        if( radix < Character.MIN_RADIX ) {
            throw new IllegalArgumentException(
                "radix can not be smaller than " + Character.MIN_RADIX
                );
        }

        if( radix > Character.MAX_RADIX ) {
            throw new IllegalArgumentException(
                "radix can not be larger than " + Character.MAX_RADIX
                );
        }

        this.radix = radix;

        // Check value, and validate radix value.
        setValue( getValue() );

        return this;
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void checkValue( final int value ) throws IllegalArgumentException
    {
        if( value > this.maximumValue ) {
            throw new IllegalArgumentException(
                String.format(
                        "Value (%d) is larger than maximum (%d) ",
                        Integer.valueOf( value ),
                        Integer.valueOf( this.maximumValue )
                        )
                );
        }

        if( value < this.minimumValue ) {
            throw new IllegalArgumentException(
                String.format(
                        "Value (%d) smaller than minimum (%d) ",
                        Integer.valueOf( value ),
                        Integer.valueOf( this.minimumValue )
                        )
                );
        }
    }
}
