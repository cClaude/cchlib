package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 *
 * @param <FRAME>
 * @param <OBJECT>
 */
public interface FrameFieldPopulator<FRAME,OBJECT>
{

    /**
     * Populate all Fields on frame for this rootItem
     *
     * @param rootItem - SwingIntrospectorRootItem<FRAME> describe
     *                   Fields associate to current bean
     * @param iItem - IntrospectionItem<OBJECT> describe witch entry
     *               on on the Object will be use to Populate Frame fields
     * @throws SwingIntrospectorIllegalAccessException
     * @throws SwingIntrospectorIllegalArgumentException
     * @throws IntrospectionInvokeException
     *
     */
    void populateFields(
        final SwingIntrospectorRootItem<FRAME> rootItem,
        final IntrospectionItem<OBJECT>        iItem
        ) throws SwingIntrospectorIllegalAccessException,
               SwingIntrospectorIllegalArgumentException,
               IntrospectionInvokeException;

}
