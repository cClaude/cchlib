/**
 *
 */
package cx.ath.choisnet.swing.introspection;

import java.util.Map;

/**
 * @param <FRAME>
 * @param <OBJECT>
 * @param <OBJECT_ENTRY>
 */
public interface SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>
{
    /**
     * @return class object used by frame
     */
    Class<FRAME> getFrameClass();

    /**
     *
     * @return ComponentInitializer for this frame
     */
    ComponentInitializer getComponentInitializer();

    /**
     * Get a FramePopulator for giving values
     * @param frame
     * @param object
     * @return FramePopulator for these instances
     */
    FramePopulator<FRAME,OBJECT> getFramePopulator( FRAME frame, OBJECT object );

    /**
     * Get a ObjectPopulator for giving values
     * @param frame
     * @param object
     * @return ObjectPopulator for these instances
     */
    ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY> getObjectPopulator( FRAME frame, OBJECT object );

    /**
     *
     * @return a map with keys reflex beans names
     */
    Map<String,OBJECT_ENTRY> getObjectInfos();
}
