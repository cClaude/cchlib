/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import java.util.Map;

/**
 * @author Claude
 * @param <FRAME> 
 * @param <OBJECT> 
 * @param <OBJECT_ENTRY> 
 *
 */
public interface SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>
{
    /**
     * @return class object used by frame
     */
    public Class<FRAME> getFrameClass();
    
//    /**
//     * @param componentToInit 
//     * @param beanname 
//     * @throws SwingIntrospectorException 
//     * @See {@link ComponentInitializer}
//     * @See {@link ComponentInitializer#initComponent(Object, cx.ath.choisnet.lang.introspection.method.IntrospectionItem, String)}
//     */
//    public void initComponent(
//            Object  componentToInit,
//            String  beanname
//            ) throws SwingIntrospectorException;

    /**
     * 
     * @return ComponentInitializer for this frame
     */
    public ComponentInitializer getComponentInitializer();
    
    /**
     * Get a FramePopulator for giving values
     * @param frame
     * @param object
     * @return FramePopulator for these instances
     */
    public FramePopulator<FRAME,OBJECT> getFramePopulator( FRAME frame, OBJECT object );
    
    /**
     * Get a ObjectPopulator for giving values
     * @param frame
     * @param object
     * @return ObjectPopulator for these instances
     */
    public ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY> getObjectPopulator( FRAME frame, OBJECT object );

    /**
     * 
     * @return a map with keys reflex beans names
     */
    public Map<String,OBJECT_ENTRY> getObjectInfos();
}  
