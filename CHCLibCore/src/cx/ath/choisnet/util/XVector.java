/**
 * 
 */
package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Vector;

/**
 * Typical use:
 * 
 * <code>
 *  Vector<Vector<Object>> configValues = new XVector<Vector<Object>>()
 *          .xadd( new XVector<Object>( "ShoeBar"     , new Float(30) ))
 *          .xadd( new XVector<Object>( "CarBar"      , new Float(20) ))
 *          .xadd( new XVector<Object>( "TravelBar"   , new Float(50) ))
 *          .xadd( new XVector<Object>( "ComputerBar" , new Float(60) ))
 *          ;
 * </code>
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
