/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import java.util.EnumSet;
import java.util.Map;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospection;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.swing.introspection.AbstractSwingIntrospectorObjectInterface;
import cx.ath.choisnet.swing.introspection.FrameFieldPopulator;
import cx.ath.choisnet.swing.introspection.ObjectPopulator;
import cx.ath.choisnet.swing.introspection.ObjectPopulatorHelper;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorException;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorItem;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorRootItem;

/**
 * @author CC
 * @param <FRAME> 
 * @param <OBJECT> 
 *
 */
public class DefaultSwingIntrospectorObjectInterface<FRAME,OBJECT> 
    extends AbstractSwingIntrospectorObjectInterface<
                FRAME, 
                OBJECT, 
                DefaultIntrospectionItem<OBJECT>
                > 
{
    public DefaultSwingIntrospectorObjectInterface(
            Class<FRAME>                                            frameClass,
            Introspection<OBJECT,DefaultIntrospectionItem<OBJECT>>  introspection,
            ComponentInitializer                                    componentInitializer
            )
    {
        super( frameClass, introspection, componentInitializer );
    }

//    public DefaultSwingIntrospectorObjectInterface(
//            Class<FRAME>                                            frameClass,
//            Introspection<OBJECT,DefaultIntrospectionItem<OBJECT>>  introspection
//            )
//    {
//        super( frameClass, introspection );
//    }
    
    public DefaultSwingIntrospectorObjectInterface(
            Class<FRAME>    frameClass,
            Class<OBJECT>   objectClass
            )
    {
        super( 
                frameClass, 
                new DefaultIntrospection<OBJECT>(
                        objectClass,
                        EnumSet.of(
                                Introspection.Attrib.ONLY_PUBLIC, 
                                Introspection.Attrib.NO_DEPRECATED
                                )
                        )
                );
    }

    @Override
    public ObjectPopulator<FRAME,OBJECT,DefaultIntrospectionItem<OBJECT>> getObjectPopulator(
            final FRAME     frame, 
            final OBJECT    object 
            )
    {
        return new ObjectPopulator<FRAME,OBJECT,DefaultIntrospectionItem<OBJECT>>()
        {
            @Override
            public void populateObject(
                    DefaultIntrospectionItem<OBJECT>    iItem,
                    SwingIntrospectorRootItem<FRAME>    rootItem 
                    )
                    throws  SwingIntrospectorException, 
                            IntrospectionException
            {
                Map<Integer, SwingIntrospectorItem<FRAME>> map = rootItem.getRootItemsMap();
                
                if( map.size() == 1 ) {                    
                    Object value = ObjectPopulatorHelper.getFieldValue( map.get( 0 ).getFieldObject( frame ), iItem );
                    iItem.setObjectValue( object, value );
                }
                else {
                    throw new SwingIntrospectorException("Don't handle multiple rootItem");
                }
            }
        };
    }

    private FrameFieldPopulator<FRAME,OBJECT>   frameFieldPopulator;
    private FRAME                               frameFieldPopulatorFRAME;
    private OBJECT                              frameFieldPopulatorOBJECT;

    @Override
    public FrameFieldPopulator<FRAME,OBJECT> getFrameFieldPopulator(
            final FRAME     frame, 
            final OBJECT    object 
            )
    {
        // In cache ?
        if( frameFieldPopulator == null 
            || frameFieldPopulatorFRAME != frame 
            || frameFieldPopulatorOBJECT != object 
            ) {
            frameFieldPopulator = new DefaultFrameFieldPopulator<FRAME,OBJECT>(frame,object);
            frameFieldPopulatorFRAME = frame;
            frameFieldPopulatorOBJECT = object;
        }
        
        return frameFieldPopulator;
    }

}
