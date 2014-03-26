package com.googlecode.cchlib.xutil.google.googlecontact;

import java.lang.reflect.InvocationTargetException;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContacAnalyserException;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;

public class GoogleContactFactory {
    //private final static Logger LOGGER = Logger.getLogger( GoogleContactFactory.class );

    private final GoogleContactHeader googleContactHeader;
    private final boolean ignoreEmptyCels;

    public GoogleContactFactory( final String[] headers, final boolean ignoreEmptyCels ) throws GoogleContactFactoryException
    {
        this.googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( headers );
        this.ignoreEmptyCels     = ignoreEmptyCels;
    }

    public GoogleContact newGoogleContact( final String[] entry ) throws GoogleContacAnalyserException
    {
        final GoogleContact googleContact = new GoogleContact();

        if( googleContactHeader.getIndexMethodConteners().size() != entry.length ) {
            throw new GoogleContacAnalyserException(
                    "Bad number of entry " + entry.length
                    + " expected " + googleContactHeader.getIndexMethodConteners().size()
                    );
        }

      for( int index = 0; index < entry.length ; ) {
            final int inc = invoker( googleContact, entry, index );

            assert inc > 0;

            index += inc;
        }
        return googleContact;
    }

    private int invoker(
            final GoogleContact googleContact,
            final String[]      entry,
            final int           index
            ) throws GoogleContacAnalyserException
    {
        final HeaderMethodContener methodContener = googleContactHeader.getIndexMethodConteners().get( Integer.valueOf( index ) );

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

    /**
     * @return true is value has been set
     */
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
            )
            throws GoogleContactFactoryException
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
