package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;


/**
 * Generic class to find getters/setters observers using Reflection
 *
 * @param <O> Object to inspect
 * @param <I> Content objects for values, must extends IntropectionItem
 *
 */
public /*abstract WHY??*/ class Introspection<O,I extends IntrospectionItem<O>>
    implements Comparator<O>
{
    private static final Logger LOGGER = Logger.getLogger( Introspection.class );

    /** Getter/Setter Methods list */
    private Map<String,I> itemsMap = null;
    private final IntrospectionItemFactory<IntrospectionItem<O>> itemFactory;

    /**
     * @param inpectClass Class of object to analyze
     * @param itemFactory
     * @param parameters  Parameters
     *
     *  TIPS: Use EnumSet.of(Introspection.Attrib.ONLY_PUBLIC, Introspection.Attrib.NO_DEPRECATED) for parameter attribSet
     */
    public Introspection(
        final Class<O>                                       inpectClass,
        final IntrospectionItemFactory<IntrospectionItem<O>> itemFactory,
        final Set<IntrospectionParameters>                   parameters
        )
    {
        final EnumSet<IntrospectionParameters> safeParameters = IntrospectionBuilder.getSafeParameters( parameters );

        // get generic class !
        this.itemFactory    = itemFactory;
        this.itemsMap       = new TreeMap<>();

        final IntrospectionBuilder<O> builder = new IntrospectionBuilder<>(inpectClass, safeParameters);

        for( final Map.Entry<String,Method> entry : builder.getGetterMethodsMap().entrySet() ) {
            final String beanName = entry.getKey();
            final Method setter   = builder.getSetterMethodsMap().get( beanName );

            if( setter != null ) {
                //final I methodInfo =
                addMethod( beanName, entry.getValue(), setter );
                }
            }
    }

    /**
     *
     */
    private void addMethod( final String beanName, final Method getter, final Method setter )
    {
        @SuppressWarnings("unchecked")
        final I methodInfo = (I)this.itemFactory.buildIntrospectionItem( getter, setter );
        final I previous   = this.itemsMap.put( beanName, methodInfo );

        // Verify than method does not already exist in Map !
        if( previous != null ) {
            // Should not occur
            final StringBuilder msg = new StringBuilder();
            msg.append( "*** " );
            msg.append( getter.getName() );
            msg.append( " found twice ! " );
            msg.append( methodInfo );
            msg.append( " and " );
            msg.append( previous );

            throw new RuntimeException( msg.toString() );
        }
    }

    public I getItem( final String beanName )
    {
        return this.itemsMap.get( beanName );
    }

    public Map<String,I> getMap()
    {
        return Collections.unmodifiableMap( this.itemsMap );
    }

    @Override
    public int compare( final O o1, final O o2 )
    {
        try {
            return compareWithException( o1, o2 );
            }
        catch( final IntrospectionCompareException e ) {
            LOGGER.info( "Compare diff found using: " + e.getMethod(), e );

            return e.getCompareValue();
            }
        catch( final IntrospectionInvokeException e ) {
            LOGGER.warn( "Exception while compare: " + e.getMethod(), e );

            return Integer.MIN_VALUE - 1;
            }
    }

    /**
     * @param o1
     * @param o2
     * @return always 0, when something is different goes to a IntrospectionCompareException
     * @throws IntrospectionInvokeException
     * @throws IntrospectionCompareException
     */
    public int compareWithException( final O o1, final O o2 )
        throws IntrospectionInvokeException, IntrospectionCompareException
    {
        for( final I item : this.itemsMap.values() ) {
            final Object v1 = item.getObjectValue( o1 );
            final Object v2 = item.getObjectValue( o2 );

            compareWithExceptionObjects( v1, v2, item.getGetterMethod() );
            }

        return 0;
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    public void compareWithExceptionObjects(
            final Object v1,
            final Object v2,
            final Method m
            )
        throws IntrospectionCompareException
    {
        if( v1 == null ) {
            if( v2 == null ) {
                // continue
                }
            else {
                throw new IntrospectionCompareException( "v1 is null, but v2=" + v2, m, v1, v2, 1 );
                }
            }
        else if( v2 == null ) {
            throw new IntrospectionCompareException( "v2 is null, but v1=" + v1, m, v1, v2, -1 );
            }
        else if( v1 instanceof Comparable ) {
            @SuppressWarnings("unchecked")
            final int c = Comparable.class.cast( v1 ).compareTo( v2 );

            if( c != 0 ) {
                throw new IntrospectionCompareException( m, v1, v2, c );
                }
            else {
                // continue
                }
            }
        else if( v1.getClass().isArray() && v2.getClass().isArray() ) {
            compareWithExceptionArrays( v1, v2, m );
            }
        else if( ! v1.equals( v2 ) ) {
            throw new IntrospectionCompareException( m, v1, v2, Integer.MAX_VALUE );
            }
    }

    private void compareWithExceptionArrays(
        final Object a1,
        final Object a2,
        final Method m
        ) throws IntrospectionCompareException
    {
        final int len = Array.getLength( a1 );

        if( len != Array.getLength( a2 ) ) {
            throw new IntrospectionCompareException( "Arrays not same size", m, a1, a2, Integer.MAX_VALUE );
            }
        if( len == 0 ) {
            // continue
            }
        else {
            compareWithExceptionArrays( a1, a2, m, len );
            }
    }

    private void compareWithExceptionArrays(
        final Object a1,
        final Object a2,
        final Method m,
        final int    len
        ) throws IntrospectionCompareException
    {
        for( int i = 0; i<len; i++ ) {
            final Object av1 = Array.get( a1, 0 );
            final Object av2 = Array.get( a2, 0 );

            if( av1 == null ) {
                if( av2 == null ) {
                    // continue
                    }
                else {
                    throw new IntrospectionCompareException( "Arrays diff(1)", m, a1, a2, Integer.MAX_VALUE );
                    }
                }
            else {
                if( av2 == null ) {
                    throw new IntrospectionCompareException( "Arrays diff(2)", m, a1, a2, Integer.MAX_VALUE );
                    }
                else {
                    compareWithExceptionObjects( av1, av2, m );
                    }
                }
            }
    }
}

