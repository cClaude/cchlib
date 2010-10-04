package cx.ath.choisnet.swing.introspection;

/**
 * 
 * @author Claude
 */
public interface ComponentInitializer 
{
    /**
     * @param componentToInit 
     * @param beanname 
     * @throws SwingIntrospectorException 
     * @See {@link DefaultComponentInitializer}
     * @See {@link DefaultComponentInitializer#initComponent(Object, cx.ath.choisnet.lang.introspection.method.IntrospectionItem, String)}
     */
    public void initComponent(
            Object  componentToInit,
            String  beanname
            ) throws SwingIntrospectorException;
}
