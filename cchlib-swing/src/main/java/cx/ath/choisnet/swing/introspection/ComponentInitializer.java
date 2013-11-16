package cx.ath.choisnet.swing.introspection;

/**
 *
 */
public interface ComponentInitializer
{
    /**
     * @param componentToInit
     * @param beanname
     * @throws SwingIntrospectorException
     * @see DefaultComponentInitializer
     * @see DefaultComponentInitializer#initComponent(Object, cx.ath.choisnet.lang.introspection.method.IntrospectionItem, String)
     */
    void initComponent(
            Object  componentToInit,
            String  beanname
            ) throws SwingIntrospectorException;
}
