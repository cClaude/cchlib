/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.EnumSet;

/**
 * This object is created by {@link SwingIntrospector}
 *
 * TODO: Some doc !
 * 
 * @author CC
 * @param <FRAME> 
 */
public class SwingIntrospectorItem<FRAME> 
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private Field   field;
    /** @serial */
    private int     index;
    /** @serial */
    private boolean isRoot;

    /**
     * Create a SwingIntrospectorItem
     * @param bean
     * @param f
     * @param attribs
     */
    public SwingIntrospectorItem( 
            final Bean                              bean, 
            final Field                             f,
            final EnumSet<SwingIntrospector.Attrib> attribs
            )
    {
        this.field = f;

        if( ! attribs.contains( SwingIntrospector.Attrib.ONLY_ACCESSIBLE_PUBLIC_FIELDS )) {
            this.field.setAccessible( true );
        }

        if( bean.isIndexed() ) {
            this.index = bean.getIndex();
        }
        else {
            this.index = -1;
        }
        
        this.isRoot = bean.isRoot();
    }

    /**
     ** Note: to get real object, use getFieldObject(Object)
     * @return Field object
     ** @see #getFieldObject(Object)
     */
    public Field getField()
    {
        return this.field;
    }
    
    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @return the isRoot
     */
    public boolean isRoot()
    {
        return isRoot;
    }

    /**
     * @param objectToInspect
     * @return Object to populate corresponding to current Field
     * @throws SwingIntrospectorIllegalAccessException
     *
     */
    public Object getFieldObject( final FRAME objectToInspect )
        throws SwingIntrospectorIllegalAccessException
    {
        try {
            return this.getField().get( objectToInspect );
        }
        catch( IllegalArgumentException e ) {
            throw new SwingIntrospectorIllegalAccessException( e );
        }
        catch( IllegalAccessException e ) {
            throw new SwingIntrospectorIllegalAccessException( e );
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append( "SwingIntrospectorItem [field=" );
        builder.append( field.getName() );
        builder.append( ", index=" );
        builder.append( index );
        builder.append( ", isRoot=" );
        builder.append( isRoot );
        builder.append( "]" );
        
        return builder.toString();
    }
}

