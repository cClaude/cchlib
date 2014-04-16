package cx.ath.choisnet.swing.introspection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.iterator.BiIterator;

@NeedDoc
public class SwingIntrospectorRootItem<FRAME>
    implements Serializable, Iterable<SwingIntrospectorItem<FRAME>>
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final /*Map*/TreeMap<Integer,SwingIntrospectorItem<FRAME>> rootItems = new TreeMap<Integer,SwingIntrospectorItem<FRAME>>();
    /** @serial */
    private final /*List*/ArrayList<SwingIntrospectorItem<FRAME>> items = new ArrayList<SwingIntrospectorItem<FRAME>>();

    /**
     * TODOC
     */
    public SwingIntrospectorRootItem()
    {
        // empty
    }

    @NeedDoc
    public void add( final SwingIntrospectorItem<FRAME> item )
    {
        if( item.isRoot() ) {
            final boolean alreadyExist;

            if( item.getIndex() >= 0 ) {
                alreadyExist = putOfRootItems( item.getIndex(), item );
                }
            else {
                alreadyExist = putOfRootItems( 0, item );
                }

            if( alreadyExist ) {
                // TODO: something better !
                throw new RuntimeException( "rootItem already exist: (" + item + " - " + alreadyExist );
                }
            }
        else {
            this.items.add( item );
            }
    }

    private boolean putOfRootItems( final int index, final SwingIntrospectorItem<FRAME> item )
    {
        final SwingIntrospectorItem<FRAME> previous = this.rootItems.put( Integer.valueOf( index ), item );

        return previous != null;
    }

    @NeedDoc
    public Map<Integer,SwingIntrospectorItem<FRAME>> getRootItemsMap()
    {
        return Collections.unmodifiableMap( rootItems );
    }

    @NeedDoc
    public Collection<SwingIntrospectorItem<FRAME>> getRootItemsCollection()
    {
        return Collections.unmodifiableCollection( rootItems.values() );
    }

    @NeedDoc
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

    @Override
    public String toString()
    {
        final int maxLen = 4;
        final StringBuilder builder = new StringBuilder();
        builder.append( "SwingIntrospectorRootItem [rootItems=" );
        builder.append( rootItems != null ? toString( rootItems.entrySet(),
                maxLen ) : null );
        builder.append( ", items=" );
        builder.append( items != null ? toString( items, maxLen ) : null );
        builder.append( ']' );
        return builder.toString();
    }

    private String toString( final Collection<?> collection, final int maxLen )
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( '[' );
        int i = 0;
        for( final Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++ ) {
            if( i > 0 ) {
                builder.append( ", " );
            }
            builder.append( iterator.next() );
        }
        builder.append( ']' );
        return builder.toString();
    }
}
