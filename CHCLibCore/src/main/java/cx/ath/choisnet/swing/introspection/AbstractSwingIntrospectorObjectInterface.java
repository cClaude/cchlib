/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import java.util.Map;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 * This class provide a default implementation for {@link SwingIntrospectorObjectInterface}
 * </BR>
 * 
 * @author Claude
 * @param <FRAME> 
 * @param <OBJECT> 
 * @param <OBJECT_ENTRY> 
 *
 */
public abstract class AbstractSwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY extends IntrospectionItem<OBJECT>>
    implements SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>
{
    private static Logger slogger = Logger.getLogger(AbstractSwingIntrospectorObjectInterface.class);
    
    private Class<FRAME> frameClass;
    private Introspection<OBJECT,OBJECT_ENTRY> introspection;
    private ComponentInitializer componentInitializer;
    
    public AbstractSwingIntrospectorObjectInterface(
            Class<FRAME>                        frameClass,
            Introspection<OBJECT,OBJECT_ENTRY>  introspection,
            ComponentInitializer                componentInitializer
            )
    {
        this.frameClass             = frameClass;
        this.introspection          = introspection;
        this.componentInitializer   = componentInitializer;
    }

    public AbstractSwingIntrospectorObjectInterface(
            Class<FRAME>                        frameClass,
            Introspection<OBJECT,OBJECT_ENTRY>  introspection
            )
    {
        this(
                frameClass,
                introspection,
                new DefaultComponentInitializer<OBJECT,OBJECT_ENTRY>(introspection)
                );
    }    

    /**
     * @return class object used by frame
     */
    @Override
    final // TODO: remove this
    public Class<FRAME> getFrameClass()
    {
        return this.frameClass;
    }

//    /**
//     * @param componentToInit 
//     * @param beanname 
//     * @throws SwingIntrospectorException 
//     * @See {@link ComponentInitializer}
//     * @See {@link ComponentInitializer#initComponent(Object, cx.ath.choisnet.lang.introspection.method.IntrospectionItem, String)}
//     */
//    //@Override
//    final // TODO: remove this
//    public void initComponent(
//            Object  componentToInit,
//            String  beanname
//            ) 
//    throws SwingIntrospectorException
//    {
//        OBJECT_ENTRY iItem = introspection.getItem( beanname );
//
//        //componentInitializer.initComponent( componentToInit, iItem, beanname );
//        
//    }
    
    /**
     * Get a FramePopulator for giving values
     * @param frame
     * @param object
     * @return FramePopulator for these instances
     */
    @Override
    final // TODO: remove this
    public FramePopulator<FRAME,OBJECT> getFramePopulator( 
            final FRAME   frame, 
            final OBJECT  object 
            )
    {
        // TODO: ?? store frame and object on instance
        // if they have not change, don't build a new object ???
        final FrameFieldPopulator<FRAME,OBJECT> ffp = getFrameFieldPopulator( frame, object );
        
        return new FramePopulator<FRAME,OBJECT>()
        {
            @Override
            public void populateFrame( 
                    SwingIntrospectorRootItem<FRAME>    rootItem,
                    String                              beanname         
                    ) throws SwingIntrospectorException,
                             IntrospectionInvokeException
            {
                IntrospectionItem<OBJECT> iItem = getObjectEntry( beanname );

                if( iItem != null ) {
                    ffp.populateFields( rootItem, iItem );
                } 
                else {
                    // TODO some exception !!
                    slogger.fatal( "*** Not InspectionItem for: " + beanname );
                }
            }
            
        };
    }    
    
    /**
     * This method is use by {@link #getFramePopulator(Object, Object)}
     * provide by this implementation to populate Frame fields.
     * 
     * @param frame  - Frame to populate
     * @param object - Object where data will be read
     * @return a valid FrameFieldPopulator for giving FRAME/OBJECT
     */
    public abstract FrameFieldPopulator<FRAME,OBJECT> getFrameFieldPopulator(FRAME frame, OBJECT object);

//    /**
//     * Get a ObjectPopulator for giving values
//     * @param frame
//     * @param object
//     * @return ObjectPopulator for these instances
//     */
//    public ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY> getObjectPopulator( FRAME frame, OBJECT object );

    /**
     * 
     * @return a map with keys reflex beans names
     */
    @Override
    final // TODO: remove this
    public Map<String,OBJECT_ENTRY> getObjectInfos()
    {
        return this.introspection.getMap();
    }

    /**
     * @return the Introspection object
     */
    final // TODO: remove this
    protected Introspection<OBJECT, OBJECT_ENTRY> getIntrospection()
    {
        return introspection;
    }
    
    /**
     * @param beanname 
     * @return the OBJECT_ENTRY
     */
    final // TODO: remove this
    protected OBJECT_ENTRY getObjectEntry(String beanname)
    {
        return introspection.getItem( beanname );
    }

    @Override
    final // TODO: remove this
    public ComponentInitializer getComponentInitializer()
    {
        return componentInitializer;
    }
}  
