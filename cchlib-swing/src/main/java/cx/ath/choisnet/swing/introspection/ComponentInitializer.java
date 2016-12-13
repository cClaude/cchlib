package cx.ath.choisnet.swing.introspection;

/**
 * NEEDDOC
 */
@SuppressWarnings("squid:S1609") // Probably not a functional interface
public interface ComponentInitializer
{
    /**
     * NEEDDOC
     * @param componentToInit NEEDDOC
     * @param beanname NEEDDOC
     * @throws SwingIntrospectorException if any
     *
     * @see DefaultComponentInitializer
     * @see DefaultComponentInitializer#initComponent(Object, cx.ath.choisnet.lang.introspection.method.IntrospectionItem, String)
     */
    void initComponent(
            Object  componentToInit,
            String  beanname
            ) throws SwingIntrospectorException;
}
