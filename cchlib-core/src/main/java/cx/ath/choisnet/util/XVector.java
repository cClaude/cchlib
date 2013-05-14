package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Vector;

/**
 * Extend {@link Vector} features, to provide simplest initializations.
 * <p>
 * Typical use:
 * </p>
 * <pre>
 *  Vector&lt;Vector&lt;Object&gt;&gt; configValues = new XVector&lt;Vector&lt;Object&gt;&gt;()
 *          .xadd( new XVector&lt;Object&gt;( "ShoeBar"     , new Float(30) ))
 *          .xadd( new XVector&lt;Object&gt;( "CarBar"      , new Float(20) ))
 *          .xadd( new XVector&lt;Object&gt;( "TravelBar"   , new Float(50) ))
 *          .xadd( new XVector&lt;Object&gt;( "ComputerBar" , new Float(60) ))
 *          ;
 * </pre>
 *
 * @param <E> entries type
 *
 */
public class XVector<E> extends Vector<E>
{
    private static final long serialVersionUID = 2L;

    /**
     * Create an empty XVector
     */
    public XVector()
    {
        super();
    }

    /**
     * Create an XVector populate with given collection items
     *
     * @param c {@link Collection} to use to initialize XVector
     */
    public XVector( Collection<? extends E> c )
    {
        super( c );
    }

    /**
     * Constructs an empty XVector with the specified initial capacity
     * and capacity increment.
     *
     * @param initialCapacity the initial capacity of the XVector
     * @param capacityIncrement the amount by which the capacity is increased when the vector overflows
     * @throws IllegalArgumentException if the specified initial capacity is
     *         negative
     */
    public XVector( int initialCapacity, int capacityIncrement )
    {
        super( initialCapacity, capacityIncrement );
    }

    /**
     * Constructs an empty XVector with the specified initial capacity
     * and capacity increment.
     *
     * @param initialCapacity the initial capacity of the XVector
     * @throws IllegalArgumentException if the specified initial capacity is
     *         negative
     */
    public XVector( int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * Create an XVector populate with given items
     *
     * @param elements Array of element to use to initialize XVector
     */
    //Java 1.7 @SafeVarargs
    public XVector( final E...elements )
    {
        super( elements.length );

        for( E element : elements ) {
            add( element );
            }
    }

    /**
     * Same has {@link #add(Object)}
     * @param element element to be inserted
     * @return this object for initialization chaining
     */
    public XVector<E> xadd( E element )
    {
        super.addElement( element );

        return this;
    }

    /**
     * Same has {@link #add(int, Object)}
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @return this object for initialization chaining
     */
    public XVector<E> xadd( int index, E element )
    {
        super.add( index, element );

        return this;
    }
}
