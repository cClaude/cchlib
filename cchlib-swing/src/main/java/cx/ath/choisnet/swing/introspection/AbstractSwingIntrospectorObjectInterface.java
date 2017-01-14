package cx.ath.choisnet.swing.introspection;

import java.util.Map;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 * This class provide a default implementation for {@link SwingIntrospectorObjectInterface}
 *
 * @param <FRAME> NEEDDOC
 * @param <OBJECT> NEEDDOC
 * @param <OBJECT_ENTRY> NEEDDOC
 *
 */
@SuppressWarnings("squid:S00119")
public abstract class AbstractSwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY extends IntrospectionItem<OBJECT>>
    implements SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>
{
    private static final Logger LOGGER = Logger.getLogger(AbstractSwingIntrospectorObjectInterface.class);

    private Class<FRAME> frameClass;
    private Introspection<OBJECT,OBJECT_ENTRY> introspection;
    private ComponentInitializer componentInitializer;

    public AbstractSwingIntrospectorObjectInterface(
        final Class<FRAME>                        frameClass,
        final Introspection<OBJECT,OBJECT_ENTRY>  introspection,
        final ComponentInitializer                componentInitializer
        )
    {
        this.frameClass             = frameClass;
        this.introspection          = introspection;
        this.componentInitializer   = componentInitializer;
    }

    public AbstractSwingIntrospectorObjectInterface(
        final Class<FRAME>                        frameClass,
        final Introspection<OBJECT,OBJECT_ENTRY>  introspection
        )
    {
        this(
            frameClass,
            introspection,
            new DefaultComponentInitializer<OBJECT,OBJECT_ENTRY>( introspection )
            );
    }

    /**
     * @return class object used by frame
     */
    @Override
    public Class<FRAME> getFrameClass()
    {
        return this.frameClass;
    }

    /**
     * Get a FramePopulator for giving values
     * @param frame NEEDDOC
     * @param object NEEDDOC
     * @return FramePopulator for these instances
     */
    @Override
    public FramePopulator<FRAME,OBJECT> getFramePopulator(
        final FRAME  frame,
        final OBJECT object
        )
    {
        // TODO: ?? store frame and object on instance
        // if they have not change, don't build a new object ???
        final FrameFieldPopulator<FRAME,OBJECT> ffp = getFrameFieldPopulator( frame, object );

        return ( rootItem, beanname ) -> populateFrame( ffp, rootItem, beanname );
    }

    private void populateFrame(
        final FrameFieldPopulator<FRAME,OBJECT> ffp ,
        final SwingIntrospectorRootItem<FRAME>  rootItem,
        final String                            beanName
        ) throws SwingIntrospectorException,
                 IntrospectionInvokeException
    {
        final IntrospectionItem<OBJECT> iItem = getObjectEntry( beanName );

        if( iItem != null ) {
            ffp.populateFields( rootItem, iItem );
        }
        else {
            // TODO some exception !!
            LOGGER.fatal( "*** Not InspectionItem for: " + beanName );
        }
    }
    /**
     * This method is use by {@link #getFramePopulator(Object, Object)}
     * provide by this implementation to populate Frame fields.
     *
     * @param frame  - Frame to populate
     * @param object - Object where data will be read
     * @return a valid FrameFieldPopulator for giving FRAME/OBJECT
     */
    public abstract FrameFieldPopulator<FRAME,OBJECT> getFrameFieldPopulator(FRAME frame, OBJECT object);

    /**
     *
     * @return a map with keys reflex beans names
     */
    @Override
    public Map<String,OBJECT_ENTRY> getObjectInfos()
    {
        return this.introspection.getMap();
    }

    /**
     * @return the Introspection object
     */
    protected Introspection<OBJECT, OBJECT_ENTRY> getIntrospection()
    {
        return this.introspection;
    }

    /**
     * @param beanname NEEDDOC
     * @return the OBJECT_ENTRY
     */
    protected OBJECT_ENTRY getObjectEntry(final String beanname)
    {
        return this.introspection.getItem( beanname );
    }

    @Override
    public ComponentInitializer getComponentInitializer()
    {
        return this.componentInitializer;
    }
}
