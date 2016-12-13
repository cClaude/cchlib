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
     * @param entry NEEDDOC
     * @param rootItem NEEDDOC
     * @throws IntrospectionException if any
     * @throws SwingIntrospectorException if any
     */
    void populateObject(
        OBJECT_ENTRY                     entry,
        SwingIntrospectorRootItem<FRAME> rootItem
        ) throws IntrospectionException;
}
