package cx.ath.choisnet.swing.introspection;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;

/**
 * @author CC
 * @param <FRAME>
 * @param <OBJECT>
 * @param <OBJECT_ENTRY>
 * @see Bean
 * @see FramePopulator
 * @see ObjectPopulator
 * @see ObjectPopulatorHelper
 * @see SwingIntrospectorItem
 * @see SwingIntrospectorObjectInterface
 */
@SuppressWarnings({"squid:S00119"})
public class SwingIntrospector<FRAME,OBJECT,OBJECT_ENTRY>
{
    private static final class Builder<FRAME>
    {
        private final Set<Attrib> attribs;

        Builder( final Set<Attrib> attribs )
        {
            this.attribs = attribs;
        }

        void buildSwingIntrospectorItemMap(
            final Map<String,List<SwingIntrospectorItem<FRAME>>> map,
            final Class<?>                                       clazz
            )
        {
            final Field[] fields = getFields( clazz/*, attribs*/ );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( clazz.getName() + " # fields = " + fields.length );
                }

            for( final Field f : fields ) {
                if( isSubClass( f.getType(), Component.class ) ) {
                    try {
                        final Bean bean = new Bean( f );

                        final List<SwingIntrospectorItem<FRAME>> list = getListForBean( map, bean );

                        // Add Field informations to the list
                        list.add(
                                new SwingIntrospectorItem<FRAME>( bean, f, this.attribs )
                                );
                        }
                    catch( final IllegalArgumentException ignoreBadFieldName ) {
                        // Ignore this field
                        if( LOGGER.isDebugEnabled() ) {
                            LOGGER.debug( "buildSwingIntrospectorItemMap - ignoreBadFieldName", ignoreBadFieldName );
                            }
                        }
                    }
                }
        }

        private Field[] getFields( final Class<?> clazz )
        {
            Field[] fields;

            if( this.attribs.contains( Attrib.ONLY_ACCESSIBLE_PUBLIC_FIELDS ) ) {
                // doOnlyAccessiblePublicFields
                fields = clazz.getFields();
                }
            else {
                fields = clazz.getDeclaredFields();
                }
            return fields;
        }

        private void buildSwingIntrospectorItemMapForParents(
            final Map<String, List<SwingIntrospectorItem<FRAME>>> map,
            final Class<FRAME>                                    clazz
            )
        {
            Class<?> superClass = clazz.getSuperclass();

            while( (superClass != null)
                    && !superClass.equals( Object.class )
                    && !superClass.equals( JFrame.class )
                    && !superClass.equals( JDialog.class )
                    ) {
                buildSwingIntrospectorItemMap( map,superClass/*, this.attribs*/ );
                superClass = superClass.getSuperclass();
                }
        }

        private List<SwingIntrospectorItem<FRAME>> getListForBean(
            final Map<String, List<SwingIntrospectorItem<FRAME>>> map,
            final Bean                                            bean
            )
        {
            List<SwingIntrospectorItem<FRAME>> list = map.get( bean.getBeanName() );

            if( list == null ) {
                list = new ArrayList<>();

                map.put( bean.getBeanName(), list );
                }
            return list;
        }

        private final boolean isSubClass( final Class<?> clazz, final Class<?> compareToClass )
        {
            final String    canonicalName   = compareToClass.getCanonicalName();
            Class<?>        current         = clazz;

            while( current != null ) {
                if( current.getCanonicalName().equals( canonicalName ) ) {
                    return true;
                    }

                current = current.getSuperclass();
                }

            return false;
        }
    }
    private static final Logger LOGGER = Logger.getLogger( SwingIntrospector.class );

    private static final EnumSet<Attrib> DEFAULT_CONFIG = EnumSet.of(
            Attrib.LOOK_IN_SUPER_CLASSES
            );

    private final Map<String,SwingIntrospectorRootItem<FRAME>> itemsMap = new TreeMap<>();
    private final SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY> objectInterface;

    /**
     * <p>
     * LOOK_IN_SUPER_CLASSES<BR>
     * Introspect current class and super classes (default)
     * In all case, does not look in classes (and super classes)
     * of {@link JFrame}, {@link JDialog}, {@link Object}.
     * </p>
     * <p>
     * ONLY_ACCESSIBLE_PUBLIC_FIELDS<BR>
     * Get only public fields, if not set introspect all fields,
     * and force then to be accessible.
     * </p>
     * @author CC
     *
     */
    public enum Attrib {
        LOOK_IN_SUPER_CLASSES,
        ONLY_ACCESSIBLE_PUBLIC_FIELDS, // == FORCE_FIELDS_ACCESSIBLE,
    }

    private EnumSet<Attrib> attribs;

    /**
     * @param objectInterface
     */
    public SwingIntrospector(
        final SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY> objectInterface
        )
    {
        this(objectInterface, null);
    }

    /**
     * @param objectInterface
     * @param attributes
     */
    public SwingIntrospector(
        final SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY> objectInterface,
        final Set<Attrib>                                                 attributes
        )
    {
        this.objectInterface = objectInterface;
        this.attribs         = getConfig( attributes );

        final Class<FRAME> clazz = objectInterface.getFrameClass();
        final Map<String, List<SwingIntrospectorItem<FRAME>>> map = new TreeMap<>();

        final Builder<FRAME> builder = new Builder<>( this.attribs );

        builder.buildSwingIntrospectorItemMap( map, clazz );

        if( this.attribs.contains( Attrib.LOOK_IN_SUPER_CLASSES )) {
            builder.buildSwingIntrospectorItemMapForParents( map, clazz );
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "bean found: " + map.size() + " for: " + clazz );
            }

        for( final Map.Entry<String,List<SwingIntrospectorItem<FRAME>>> entry : map.entrySet() ) {
            final String                              beanName = entry.getKey();
            final List<SwingIntrospectorItem<FRAME>>  items    = entry.getValue();
            final SwingIntrospectorRootItem<FRAME>    rootItem = new SwingIntrospectorRootItem<>();

            for( final SwingIntrospectorItem<FRAME> item : items ) {
                rootItem.add( item );
                }

            if( ! rootItem.getRootItemsCollection().isEmpty() ) {
                this.itemsMap.put( beanName, rootItem );
                }
            else {
                LOGGER.warn( "* Igore (no $root): " + rootItem.getItemsCollection() );
                }
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "$root found: " + this.itemsMap.size() + " for: " + clazz );
            }

        //TODO: if size == 0 : exception ?? or error on System.err ??
    }

    private static EnumSet<Attrib> getConfig( final Set<Attrib> attributes )
    {
        if( attributes != null ) {
            return EnumSet.copyOf( attributes );
            }
        else {
            return DEFAULT_CONFIG;
        }
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public Map<String,SwingIntrospectorRootItem<FRAME>> getItemMap()
    {
        return Collections.unmodifiableMap( this.itemsMap );
    }

    /**
     * NEEDDOC
     * @param beanName
     * @return NEEDDOC
     */
    public SwingIntrospectorRootItem<FRAME> getRootItem( final String beanName )
    {
        return this.itemsMap.get( beanName );
    }

    /**
     * NEEDDOC
     *
     * @param frame  FRAME object to populate
     * @param object OBJECT where data will be read
     * @throws IntrospectionInvokeException
     * @throws SwingIntrospectorException
     * @see #populateFrameWithoutException(Object, Object)
     */
    @SuppressWarnings({"squid:S1160"})
    public synchronized void populateFrameWithException(
            final FRAME     frame,
            final OBJECT    object
            )
        throws  IntrospectionInvokeException,
                SwingIntrospectorException
    {
        final FramePopulator<FRAME,OBJECT> fp = this.objectInterface.getFramePopulator( frame, object );

        for( final Entry<String, SwingIntrospectorRootItem<FRAME>> entry : this.itemsMap.entrySet() ) {
            final String                      beanName = entry.getKey();
            final SwingIntrospectorRootItem<FRAME>   rootItem = entry.getValue();

            if( rootItem != null ) {
                fp.populateFrame(rootItem, beanName);
            }
            else {
                throw new SwingIntrospectorNoRootItemException(beanName);
            }
        }
    }

    /**
     * Same has populateFrameWithException() but just log, when
     * an error occur.
     *
     * @param frame  FRAME object to populate
     * @param object OBJECT where data will be read
     * @see #populateFrameWithException(Object, Object)
     */
    public synchronized void populateFrameWithoutException(
        final FRAME     frame,
        final OBJECT    object
        )
    {
        final FramePopulator<FRAME,OBJECT> fp = this.objectInterface.getFramePopulator( frame, object );

        for( final Entry<String, SwingIntrospectorRootItem<FRAME>> entry : this.itemsMap.entrySet() ) {
            final String                      beanName = entry.getKey();
            final SwingIntrospectorRootItem<FRAME>   rootItem = entry.getValue();

            if( rootItem != null ) {
                try {
                    fp.populateFrame(rootItem, beanName);
                    }
                catch( final SwingIntrospectorException e ) {
                    LOGGER.warn( "*** SwingIntrospectorException for: " + beanName, e );
                    }
                catch( final IntrospectionInvokeException e ) {
                    LOGGER.warn( "*** IntrospectionInvokeException for: " + beanName, e );
                    }
                }
            else {
                LOGGER.fatal( "*** Not SwingIntrospectorRootItem for: " + beanName );
                }
            }
    }

    /**
     * NEEDDOC
     *
     * @param frame  FRAME where data will be read
     * @param object OBJECT object to populate
     * @throws IntrospectionException
     * @throws SwingIntrospectorException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public synchronized void populateObjectWithException(
            final FRAME  frame,
            final OBJECT object
            )
        throws  SwingIntrospectorException,
                IntrospectionException
    {
        final ObjectPopulator<FRAME,OBJECT_ENTRY> op = this.objectInterface.getObjectPopulator( frame, object );

        for( final Entry<String,OBJECT_ENTRY> item : this.objectInterface.getObjectInfos().entrySet()) {
            final String                    beanName = item.getKey();
            final SwingIntrospectorRootItem<FRAME> rootItem = this.getRootItem( beanName );

            if( rootItem == null ) {
                throw new SwingIntrospectorNoRootItemException( beanName );
                }

            op.populateObject( item.getValue(), rootItem );
            }
    }

    /**
     * Same has populateObjectWithException() but just log, when
     * an error occur.
     *
     * @param frame  FRAME where data will be read
     * @param object OBJECT object to populate
     * @see #populateObjectWithException(Object, Object)
     */
    public synchronized void populateObjectWithoutException(
            final FRAME  frame,
            final OBJECT object
            )
    { // without exception !
        final ObjectPopulator<FRAME,OBJECT_ENTRY> op = this.objectInterface.getObjectPopulator( frame, object );

        for( final Entry<String,OBJECT_ENTRY> item : this.objectInterface.getObjectInfos().entrySet()) {
            final String                    beanName = item.getKey();
            final SwingIntrospectorRootItem<FRAME> rootItem = this.getRootItem( beanName );

            if( rootItem == null ) {
                LOGGER.fatal( "NoRootItemException: " + beanName );
                }

            try {
                op.populateObject( item.getValue(), rootItem );
                }
            catch( final SwingIntrospectorException e ) {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "SwingIntrospectorException for: " + beanName,e  );
                    }
                else {
                    LOGGER.warn( "SwingIntrospectorException for: " + beanName );
                    }
                }
            catch( final IntrospectionException e ) {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "IntrospectionException for: " + beanName, e );
                    }
                else {
                    LOGGER.warn( "IntrospectionException for: " + beanName );
                    }
                }
        }
    }

    /**
     * Same has initComponentsWithException() but just log, when
     * an error occur.
     *
     * @param populateObject
     * @see #initComponentsWithException(Object)
     */
    public void initComponentsWithoutException( final FRAME populateObject )
    {
        for( final Entry<String,SwingIntrospectorRootItem<FRAME>> entry : this.getItemMap().entrySet()) {
            for( final SwingIntrospectorItem<FRAME> fd : entry.getValue() ) {

                try {
                    initComponents( populateObject, entry, fd );
                    }
                catch( final SwingIntrospectorException e ) {
                    LOGGER.warn( "initComponents()", e );
                    }
            }
        }
    }

    /**
     * NEEDDOC
     *
     * @param populateObject
     * @throws SwingIntrospectorIllegalAccessException
     * @throws SwingIntrospectorException
     * @throws SwingIntrospectorNoMaxValueException
     * @see #initComponentsWithoutException(Object)
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public void initComponentsWithException( final FRAME populateObject )
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        for( final Entry<String,SwingIntrospectorRootItem<FRAME>> entry : this.getItemMap().entrySet()) {
            for( final SwingIntrospectorItem<FRAME> fd : entry.getValue() ) {
                initComponents( populateObject, entry, fd );
            }
        }
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    private void initComponents(
            final FRAME populateObject,
            final Entry<String,SwingIntrospectorRootItem<FRAME>> entry,
            final SwingIntrospectorItem<FRAME> fd
            )
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        final Object obj = fd.getFieldObject( populateObject );

        this.objectInterface.getComponentInitializer().initComponent( obj, entry.getKey() );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "SwingIntrospector [itemsMap=" );
        builder.append( this.itemsMap );
        builder.append( ", objectInterface=" );
        builder.append( this.objectInterface );
        builder.append( ']' );

        return builder.toString();
    }

    /**
     * NEEDDOC
     *
     * @param <FRAME>
     * @param <OBJECT>
     * @param frameClass
     * @param objectClass
     * @return a SwingIntrospector based on {@link DefaultSwingIntrospectorObjectInterface}
     *
     */
    public static <FRAME,OBJECT> SwingIntrospector<FRAME,OBJECT,DefaultIntrospectionItem<OBJECT>> buildSwingIntrospector(
            final Class<FRAME>    frameClass,
            final Class<OBJECT>   objectClass
            )
    {
        return new SwingIntrospector<>(
                new DefaultSwingIntrospectorObjectInterface<>(
                        frameClass,
                        objectClass
                        )
                );
    }
}
