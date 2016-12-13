package cx.ath.choisnet.swing.introspection;

import java.util.EnumSet;
import java.util.Map;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospection;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.lang.introspection.method.IntrospectionParameters;

/**
 * @author CC
 * @param <FRAME> NEEDDOC
 * @param <OBJECT> NEEDDOC
 */
@SuppressWarnings("squid:S00119")
public class DefaultSwingIntrospectorObjectInterface<FRAME,OBJECT>
    extends AbstractSwingIntrospectorObjectInterface<
                FRAME,
                OBJECT,
                DefaultIntrospectionItem<OBJECT>
                >
{
    private static final Integer ZERO = Integer.valueOf( 0 );

    private FrameFieldPopulator<FRAME,OBJECT>   frameFieldPopulator;
    private FRAME                               frameFieldPopulatorFRAME;
    private OBJECT                              frameFieldPopulatorOBJECT;

    public DefaultSwingIntrospectorObjectInterface(
            final Class<FRAME>                                            frameClass,
            final Introspection<OBJECT,DefaultIntrospectionItem<OBJECT>>  introspection,
            final ComponentInitializer                                    componentInitializer
            )
    {
        super( frameClass, introspection, componentInitializer );
    }

    public DefaultSwingIntrospectorObjectInterface(
            final Class<FRAME>    frameClass,
            final Class<OBJECT>   objectClass
            )
    {
        super(
                frameClass,
                new DefaultIntrospection<OBJECT>(
                        objectClass,
                        EnumSet.of(
                                IntrospectionParameters.ONLY_PUBLIC,
                                IntrospectionParameters.NO_DEPRECATED
                                )
                        )
                );
    }

    @Override
    public ObjectPopulator<FRAME,DefaultIntrospectionItem<OBJECT>> getObjectPopulator(
            final FRAME     frame,
            final OBJECT    object
            )
    {
        return ( iItem, rootItem ) -> {
            final Map<Integer, SwingIntrospectorItem<FRAME>> map = rootItem.getRootItemsMap();

            if( map.size() == 1 ) {
                final Object value = ObjectPopulatorHelper.getFieldValue( map.get( ZERO ).getFieldObject( frame ), iItem );
                iItem.setObjectValue( object, value );
            }
            else {
                throw new SwingIntrospectorException("Don't handle multiple rootItem");
            }
        };
    }

    @Override
    public FrameFieldPopulator<FRAME,OBJECT> getFrameFieldPopulator(
            final FRAME     frame,
            final OBJECT    object
            )
    {
        // In cache ?
        if( (this.frameFieldPopulator == null)
            || (this.frameFieldPopulatorFRAME != frame)
            || (this.frameFieldPopulatorOBJECT != object)
            ) {
            this.frameFieldPopulator = new DefaultFrameFieldPopulator<>(frame,object);
            this.frameFieldPopulatorFRAME = frame;
            this.frameFieldPopulatorOBJECT = object;
        }

        return this.frameFieldPopulator;
    }

}
