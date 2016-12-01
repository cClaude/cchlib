package cx.ath.choisnet.swing.introspection;

import java.util.Map;

/**
 * NEEDDOC
 *
 * @param <FRAME> NEEDDOC
 * @param <OBJECT> NEEDDOC
 * @param <OBJECT_ENTRY> NEEDDOC
 */
@SuppressWarnings("squid:S00119")
public interface SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>
{
    /**
     *  NEEDDOC
     * @return class object used by frame
     */
    Class<FRAME> getFrameClass();

    /**
     * NEEDDOC
     * @return ComponentInitializer for this frame
     */
    ComponentInitializer getComponentInitializer();

    /**
     * Get a FramePopulator for giving values
     * @param frame NEEDDOC
     * @param object NEEDDOC
     * @return FramePopulator for these instances
     */
    FramePopulator<FRAME,OBJECT> getFramePopulator( FRAME frame, OBJECT object );

    /**
     * Get a ObjectPopulator for giving values
     * @param frame NEEDDOC
     * @param object NEEDDOC
     * @return ObjectPopulator for these instances
     */
    ObjectPopulator<FRAME,OBJECT_ENTRY> getObjectPopulator( FRAME frame, OBJECT object );

    /**
     * NEEDDOC
     * @return a map with keys reflex beans names
     */
    Map<String,OBJECT_ENTRY> getObjectInfos();
}
