package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionException;

/**
 * NEEDDOC
 *
 * @param <FRAME> NEEDDOC
 * @param <OBJECT_ENTRY> NEEDDOC
 */
@SuppressWarnings("squid:S00119")
@FunctionalInterface
public interface ObjectPopulator<FRAME,OBJECT_ENTRY>
{
    /**
     * Populate object from frame
     * @param entry
     * @param rootItem
     * @throws IntrospectionException
     * @throws SwingIntrospectorException
     */
    void populateObject(
        OBJECT_ENTRY                     entry,
        SwingIntrospectorRootItem<FRAME> rootItem
        ) throws IntrospectionException;
}
