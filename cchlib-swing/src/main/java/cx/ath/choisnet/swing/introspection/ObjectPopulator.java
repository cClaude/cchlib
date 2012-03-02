package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionException;

public interface ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY>
{
    /**
     * Populate object from frame
     * @param entry 
     * @param rootItem 
     * @throws IntrospectionException 
     * @throws SwingIntrospectorException 
     */
    public void populateObject(
            OBJECT_ENTRY                entry, 
            SwingIntrospectorRootItem<FRAME>   rootItem
            )
    throws  SwingIntrospectorException, 
            IntrospectionException;
}
