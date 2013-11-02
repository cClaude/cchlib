/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;


/**
 * Generic class to find getters/setters observers using Reflection
 *
 * @author CC
 * @param <O> Object to inspect
 * @param <I> Content objects for values, must extends IntropectionItem
 *
 */
public /*abstract WHY??*/ class Introspection<O,I extends IntrospectionItem<O>>
    implements Comparator<O>
{
    /** Some logs */
    private static final Logger sLog = Logger.getLogger(Introspection.class);

    /**
     *
     * @author CC
     */
    public enum Attrib{
        ONLY_PUBLIC,
        NO_DEPRECATED
        };

    /** Getter/Setter Methods list */
    private Map<String,I> itemsMap = null;
    private IntrospectionItemFactory<IntrospectionItem<O>> itemFactory;

    /**
     * @param inpectClass
     * @param itemFactory
     * @param attribSet
     *
     *  TIPS: Use EnumSet.of(Introspection.Attrib.ONLY_PUBLIC, Introspection.Attrib.NO_DEPRECATED) for parameter attribSet
     */
    public Introspection(
            Class<O>                                        inpectClass,
            IntrospectionItemFactory<IntrospectionItem<O>>  itemFactory,
            EnumSet<Attrib>                                 attribSet
            )
    {
        if( attribSet == null ) {
            attribSet = EnumSet.noneOf( Attrib.class );
            }

        // get generic class !
        this.itemFactory    = itemFactory;
        this.itemsMap       = new TreeMap<String,I>();

        IntrospectionBuilder<O> builder = new IntrospectionBuilder<O>(inpectClass, attribSet);

        for( Map.Entry<String,Method> entry : builder.getGetterMethodsMap().entrySet() ) {
            String beanName = entry.getKey();
            Method setter   = builder.getSetterMethodsMap().get( beanName );

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
            StringBuilder msg = new StringBuilder();
            msg.append( "*** " );
            msg.append( getter.getName() );
            msg.append( " found twice ! " );
            msg.append( methodInfo );
            msg.append( " and " );
            msg.append( previous );
            //sLog.error( msg.toString() );
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
        catch( IntrospectionCompareException e ) {
            sLog.info( "Compare diff found using: " + e.getMethod(), e );

            return e.getCompareValue();
            }
        catch( IntrospectionInvokeException e ) {
            sLog.warn( "Exception while compare: " + e.getMethod(), e );

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
        for( I item : this.itemsMap.values() ) {
            final Object v1 = item.getObjectValue( o1 );
            final Object v2 = item.getObjectValue( o2 );

            compareWithExceptionObjects( v1, v2, item.getGetterMethod() );
            }

        return 0;
    }

    public void compareWithExceptionObjects( final Object v1, final Object v2, final Method m )
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

    private void compareWithExceptionArrays( final Object a1, final Object a2, final Method m )
        throws IntrospectionCompareException
    {
        final int len = Array.getLength( a1 );

        if( len != Array.getLength( a2 ) ) {
            throw new IntrospectionCompareException( "Arrays not same size", m, a1, a2, Integer.MAX_VALUE );
            }
        if( len == 0 ) {
            // continue
            }
        else {
            for( int i = 0; i<len; i++ ) {
                Object av1 = Array.get( a1, 0 );
                Object av2 = Array.get( a2, 0 );

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
}

