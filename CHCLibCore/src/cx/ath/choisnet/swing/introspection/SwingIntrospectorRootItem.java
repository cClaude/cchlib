/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import cx.ath.choisnet.util.iterator.BiIterator;

/**
 * @author CC
 * @param <FRAME>
 */
public class SwingIntrospectorRootItem<FRAME>
    implements Serializable, Iterable<SwingIntrospectorItem<FRAME>>
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private Map<Integer,SwingIntrospectorItem<FRAME>> rootItems = new TreeMap<Integer,SwingIntrospectorItem<FRAME>>();
    /** @serial */
    private List<SwingIntrospectorItem<FRAME>> items = new ArrayList<SwingIntrospectorItem<FRAME>>();

    public SwingIntrospectorRootItem()
    {
        // empty
    }

    public void add( SwingIntrospectorItem<FRAME> item )
    {
        if( item.isRoot() ) {
            SwingIntrospectorItem<FRAME> alreadyExist;

            if( item.getIndex() >= 0 ) {
                alreadyExist = this.rootItems.put( item.getIndex(), item );
            }
            else {
                alreadyExist = this.rootItems.put( 0, item );
            }

            if( alreadyExist != null ) {
                // TODO: something better !
                throw new RuntimeException( "rootItem already exist: (" + item + " - " + alreadyExist );
            }
        }
        else {
            this.items.add( item );
        }
    }

    public Map<Integer,SwingIntrospectorItem<FRAME>> getRootItemsMap()
    {
        return Collections.unmodifiableMap( rootItems );
    }

    public Collection<SwingIntrospectorItem<FRAME>> getRootItemsCollection()
    {
        return Collections.unmodifiableCollection( rootItems.values() );
    }

    public Collection<SwingIntrospectorItem<FRAME>> getItemsCollection()
    {
        return Collections.unmodifiableList( items );
    }

    @Override
    public Iterator<SwingIntrospectorItem<FRAME>> iterator()
    {
        return new BiIterator<SwingIntrospectorItem<FRAME>>(
                rootItems.values().iterator(),
                items.iterator()
                );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final int maxLen = 4;
        StringBuilder builder = new StringBuilder();
        builder.append( "SwingIntrospectorRootItem [rootItems=" );
        builder.append( rootItems != null ? toString( rootItems.entrySet(),
                maxLen ) : null );
        builder.append( ", items=" );
        builder.append( items != null ? toString( items, maxLen ) : null );
        builder.append( "]" );
        return builder.toString();
    }

    private String toString( Collection<?> collection, int maxLen )
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "[" );
        int i = 0;
        for( Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++ ) {
            if( i > 0 ) builder.append( ", " );
            builder.append( iterator.next() );
        }
        builder.append( "]" );
        return builder.toString();
    }
}