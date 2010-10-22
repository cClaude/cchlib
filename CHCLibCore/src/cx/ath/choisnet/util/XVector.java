/**
 * 
 */
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
 * @author Claude CHOISNET
 * @param <E> entries type
 *
 */
public class XVector<E> extends Vector<E> 
{
    private static final long serialVersionUID = 1L;
     
    public XVector()
    {
        super();
    }

    public XVector( Collection<? extends E> c )
    {
        super( c );
    }

    public XVector( int initialCapacity, int capacityIncrement )
    {
        super( initialCapacity, capacityIncrement );
    }

    public XVector( int initialCapacity )
    {
        super( initialCapacity );
    }

    public XVector(E...elements)
    {
        for(E element:elements) {
            add(element);
        }
    }
    
    /**
     * Same has {@link #add(Object)}
     * @param element
     * @return this object
     */
    public XVector<E> xadd( E element )
    {
        super.addElement( element );
        return this;
    }

    /**
     * Same has {@link #add(int, Object)}
     * @param index 
     * @param element 
     * @return this object
     */
    public XVector<E> xadd( int index, E element )
    {
        super.add( index, element );
        return this;
    }
    
}
