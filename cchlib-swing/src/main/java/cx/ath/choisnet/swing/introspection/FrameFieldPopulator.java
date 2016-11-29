package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 *
 * @param <FRAME> Swing Frame object
 * @param <OBJECT> Container to populate frame
 */
@SuppressWarnings({"squid:S00119"})
@FunctionalInterface
public interface FrameFieldPopulator<FRAME,OBJECT>
{

    /**
     * Populate all Fields on frame for this rootItem
     *
     * @param rootItem - SwingIntrospectorRootItem&lt;FRAME&gt; describe
     *                   Fields associate to current bean
     * @param iItem - IntrospectionItem&lt;OBJECT&gt; describe witch entry
     *               on on the Object will be use to Populate Frame fields
     * @throws SwingIntrospectorIllegalAccessException if any
     * @throws SwingIntrospectorIllegalArgumentException if any
     * @throws IntrospectionInvokeException if any
     *
     */
    @SuppressWarnings("squid:S1160")
    void populateFields(
        final SwingIntrospectorRootItem<FRAME> rootItem,
        final IntrospectionItem<OBJECT>        iItem
        ) throws SwingIntrospectorIllegalAccessException,
               SwingIntrospectorIllegalArgumentException,
               IntrospectionInvokeException;

}
