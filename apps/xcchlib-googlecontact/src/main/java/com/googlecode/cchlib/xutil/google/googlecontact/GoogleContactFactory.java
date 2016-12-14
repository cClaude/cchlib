package com.googlecode.cchlib.xutil.google.googlecontact;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;

/**
 * Build {@link GoogleContact}, see {@link #newGoogleContact(String[])}
 */
public class GoogleContactFactory
{
    private static final Logger LOGGER = Logger.getLogger( GoogleContactFactory.class );

    private final GoogleContactHeader googleContactHeader;
    private final boolean             ignoreEmptyCels;

    @SuppressWarnings("squid:S3346") // assert usage
    public GoogleContactFactory(
            final String[] headers,
            final boolean  ignoreEmptyCels
            ) throws GoogleContactFactoryException
    {
        this.googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( headers );
        this.ignoreEmptyCels     = ignoreEmptyCels;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "Found " + headers.length + " columns." );
        }

        assert headers.length == this.googleContactHeader.getIndexMethodConteners().size();
    }

    /**
     * Create a new {@link GoogleContact}
     *
     * @param entry
     *            Array of strings with cell values, according to
     *            {@code headers} given in factory constructor
     * @return a non null GoogleContact
     * @throws GoogleContactFactoryException
     *             if any error occur
     */
    @Nonnull
    public GoogleContact newGoogleContact( @Nonnull final String[] entry )
            throws GoogleContactFactoryException
    {
        final GoogleContact googleContact = new GoogleContact();

        if( this.googleContactHeader.getIndexMethodConteners().size() != entry.length ) {
            throw new GoogleContactFactoryException(
                    "Bad number of entry " + entry.length
                    + " expected " + this.googleContactHeader.getIndexMethodConteners().size()
                    );
        }

      for( int index = 0; index < entry.length ; ) {
            final int inc = invoker( googleContact, entry, index );

            assert inc > 0;

            index += inc;
        }

        return googleContact;
    }

    @SuppressWarnings("squid:S3346") // assert usage
    private int invoker(
        final GoogleContact googleContact,
        final String[]      entry,
        final int           index
        ) throws GoogleContactFactoryException
    {
        final HeaderMethodContener methodContener = this.googleContactHeader.getIndexMethodConteners().get( Integer.valueOf( index ) );

        assert methodContener != null;
        assert methodContener.getMethod().getParameterTypes().length == 1;

        if( methodContener instanceof HeaderCustomTypeMethodContener ) {
            return invokeInner( (HeaderCustomTypeMethodContener)methodContener, entry, index, googleContact );
        } else {
            assert methodContener.getMethod().getParameterTypes().length == 1;
            assert String.class.isAssignableFrom( methodContener.getMethod().getParameterTypes()[0] ) : "Bad type expected String is " + methodContener.getMethod().getParameterTypes()[0];

            invoke( methodContener, entry[ index ], googleContact );

            return 1;
        }
    }

    private int invokeInner(
        final HeaderCustomTypeMethodContener methodContener,
        final String[]                       entry,
        final int                            index,
        final GoogleContactType              googleContactType
        ) throws GoogleContactFactoryException
    {
        final Class<?> parameterClass = methodContener.getMethod().getParameterTypes()[ 0 ];

        // Create bean
        final GoogleContactType element = GoogleContact.newGoogleContactType( parameterClass );

        final int size             = methodContener.size();
        boolean   needToAddElement = false;

        for( int i = 0; i<size; i++ ) {
            final HeaderMethodContener innerMethodContener = methodContener.get( i );
            final boolean              hasBeenSet           = invoke( innerMethodContener, entry[ index + i ], element );

            if( hasBeenSet ) {
                needToAddElement  = hasBeenSet;
            }
        }

        if( needToAddElement ) {
            addElement( googleContactType, methodContener, element );
        }

        return size;
    }

    /*
     * return true is value has been set
     */
    @SuppressWarnings("squid:S3346") // assert usage
    private boolean invoke(
        final HeaderMethodContener  methodContener,
        final String                cellContent,
        final GoogleContactType     googleContactType
        ) throws GoogleContactFactoryException
    {
        assert methodContener.getMethod().getParameterTypes().length == 1;
        assert String.class.isAssignableFrom( methodContener.getMethod().getParameterTypes()[0] ) : "Bad type expected String is " + methodContener.getMethod().getParameterTypes()[0];

        if( ! (this.ignoreEmptyCels && cellContent.isEmpty() ) ) {
            try {
                methodContener.getMethod().invoke( googleContactType, cellContent );
                }
            catch( IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException e ) {
                throw new GoogleContactFactoryException(
                    "Invocaktion error : " + methodContener.getMethod()
                    + " on class " + googleContactType.getClass(),
                    e );
                }
            return true;
        } else {
            return false;
        }
    }

    private void addElement(
        final GoogleContactType     googleContactType,
        final HeaderMethodContener  methodContener,
        final GoogleContactType     element
        ) throws GoogleContactFactoryException
    {
        try {
            methodContener.getMethod().invoke( googleContactType, element );
            }
        catch( IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e ) {
            throw new GoogleContactFactoryException( "Invocaktion error : " + methodContener.getMethod(), e );
            }
    }
}
