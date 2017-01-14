package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;

@FunctionalInterface
@SuppressWarnings("squid:S00119") // naming convention for type parameter
public interface FramePopulator<FRAME,OBJECT>
{
    /**
     * Populate frame for this rootItem using giving bean name
     *
     * @param rootItem
     *            SwingIntrospectorRootItem to populate
     * @param beanname
     *            bean name to use for identify value from OBJECT
     * @throws SwingIntrospectorException
     *             if any
     * @throws IntrospectionInvokeException
     *             if any
     */
    @SuppressWarnings("squid:S1160") // Exception are not of same kind at all.
    void populateFrame(
        SwingIntrospectorRootItem<FRAME>    rootItem,
        String                              beanname
        ) throws SwingIntrospectorException,
                 IntrospectionInvokeException;


}
