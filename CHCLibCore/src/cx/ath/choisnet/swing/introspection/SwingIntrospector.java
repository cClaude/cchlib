/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class SwingIntrospector<FRAME,OBJECT,OBJECT_ENTRY>
{
    /** Some logs */
    private static Logger slogger = Logger.getLogger(SwingIntrospector.class);

    private final Map<String,SwingIntrospectorRootItem<FRAME>> itemsMap = new TreeMap<String,SwingIntrospectorRootItem<FRAME>>();
    private final SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY> objectInterface;

    /**
     * <p>
     * LOOK_IN_SUPER_CLASSES<br/>
     * Introspect current class and super classes (default)
     * In all case, does not look in classes (and super classes)
     * of {@link JFrame}, {@link JDialog}, {@link Object}. 
     * </p>
     * <p>
     * ONLY_ACCESSIBLE_PUBLIC_FIELDS<br/>
     * Get only public fields, if not set introspect all fields,
     * and force then to be accessible.
     * </p>
     * @author CC
     *
     */
    public enum Attrib {
        LOOK_IN_SUPER_CLASSES,
        ONLY_ACCESSIBLE_PUBLIC_FIELDS, // == FORCE_FIELDS_ACCESSIBLE,
    };
    // TODO: not yet implemented !
    private EnumSet<Attrib> attribs = EnumSet.of(
                Attrib.LOOK_IN_SUPER_CLASSES
                );

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
            final SwingIntrospectorObjectInterface<FRAME,OBJECT,OBJECT_ENTRY>   objectInterface,
            EnumSet<Attrib>                                                     attributes
            )
    {
        this.objectInterface = objectInterface;

        if( attributes != null ) {
            this.attribs = EnumSet.copyOf( attributes );
        }

        Class<FRAME> clazz = objectInterface.getFrameClass();
        Map<String, List<SwingIntrospectorItem<FRAME>>> map = new TreeMap<String, List<SwingIntrospectorItem<FRAME>>>();

        buildSwingIntrospectorItemMap(map,clazz,attribs);

        if( this.attribs.contains( Attrib.LOOK_IN_SUPER_CLASSES )) {
            Class<?> superClass = clazz.getSuperclass();

            while( superClass != null 
                    && !superClass.equals( Object.class ) 
                    && !superClass.equals( JFrame.class ) 
                    && !superClass.equals( JDialog.class ) 
                    ) {
                buildSwingIntrospectorItemMap(map,superClass,attribs);
                superClass = superClass.getSuperclass();
            }
        }

        slogger.info( "bean found: " + map.size() + " for: " + clazz );

        for( Map.Entry<String,List<SwingIntrospectorItem<FRAME>>> entry : map.entrySet() ) {
            String                              beanName = entry.getKey();
            List<SwingIntrospectorItem<FRAME>>  items    = entry.getValue();
            SwingIntrospectorRootItem<FRAME>    rootItem = new SwingIntrospectorRootItem<FRAME>();

            for( SwingIntrospectorItem<FRAME> item : items ) {
                rootItem.add( item );
            }

            if( rootItem.getRootItemsCollection().size() > 0 ) {
                this.itemsMap.put( beanName, rootItem );
            }
            else {
                slogger.warn( "* Igore (no $root): " + rootItem.getItemsCollection() );
            }
        }
        
        slogger.info( "$root found: " + this.itemsMap.size() + " for: " + clazz );
        //TODO: if size == 0 : exception ?? or error on System.err ??
    }

    private static <FRAME> void buildSwingIntrospectorItemMap(
            Map<String,List<SwingIntrospectorItem<FRAME>>> map,
            final Class<?>                          clazz,
            final EnumSet<Attrib>                   attribs
            )
    {
        Field[] fields;

        if( attribs.contains( Attrib.ONLY_ACCESSIBLE_PUBLIC_FIELDS ) ) {
            // doOnlyAccessiblePublicFields
            fields = clazz.getFields();
        }
        else {
            fields = clazz.getDeclaredFields();
        }

        //
        slogger.info( clazz.getName() + " # fields = " + fields.length );

        for( Field f : fields ) {
            // slogger.info( "  > f = " + f + " : " + f.getType() );

            if( isSubClass( f.getType(), Component.class ) ) {
                try {
                    final Bean bean = new Bean( f );

                    List<SwingIntrospectorItem<FRAME>> list = map.get( bean.getBeanName() );

                    if( list == null ) {
                        list = new ArrayList<SwingIntrospectorItem<FRAME>>();

                        map.put( bean.getBeanName(), list );
                        }

                    // Add Field informations to the list
                    list.add(
                            new SwingIntrospectorItem<FRAME>( bean, f, attribs )
                            );
                    }
                catch( IllegalArgumentException ignoreBadFieldName ) {
                    // Ignore this field
                    }
                }
            }
    }

    /**
     *
     * @param clazz
     * @param compareToClass
     * @return
     */
    private static final boolean isSubClass( final Class<?> clazz, final Class<?> compareToClass )
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

    /**
     *
     * @return TODO: doc
     */
    public Map<String,SwingIntrospectorRootItem<FRAME>> getItemMap()
    {
        return Collections.unmodifiableMap( this.itemsMap );
    }

    /**
     *
     * @param beanName
     * @return TODO: doc
     */
    public SwingIntrospectorRootItem<FRAME> getRootItem( final String beanName )
    {
        return this.itemsMap.get( beanName );
    }

    /**
     * TODO: doc!
     *
     * @param frame  FRAME object to populate
     * @param object OBJECT where data will be read
     * @throws IntrospectionInvokeException
     * @throws SwingIntrospectorException
     * @see #populateFrameWithoutException(Object, Object)
     */
    public synchronized void populateFrameWithException(
            final FRAME     frame,
            final OBJECT    object
            )
        throws  IntrospectionInvokeException,
                SwingIntrospectorException
    {
        FramePopulator<FRAME,OBJECT> fp = objectInterface.getFramePopulator( frame, object );

        for( Entry<String, SwingIntrospectorRootItem<FRAME>> entry : this.itemsMap.entrySet() ) {
            String                      beanName = entry.getKey();
            SwingIntrospectorRootItem<FRAME>   rootItem = entry.getValue();

            if( rootItem != null ) {
                fp.populateFrame(rootItem, beanName);
            }
            else {
                //slogger.fatal( "*** Not SwingIntrospectorRootItem for: " + beanName );
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
        FramePopulator<FRAME,OBJECT> fp = objectInterface.getFramePopulator( frame, object );

        for( Entry<String, SwingIntrospectorRootItem<FRAME>> entry : this.itemsMap.entrySet() ) {
            String                      beanName = entry.getKey();
            SwingIntrospectorRootItem<FRAME>   rootItem = entry.getValue();

            if( rootItem != null ) {
                try {
                    fp.populateFrame(rootItem, beanName);
                }
                catch( SwingIntrospectorException e ) {
                    slogger.warn( "*** SwingIntrospectorException for: " + beanName, e );
                }
                catch( IntrospectionInvokeException e ) {
                    slogger.warn( "*** IntrospectionInvokeException for: " + beanName, e );
                }
            }
            else {
                slogger.fatal( "*** Not SwingIntrospectorRootItem for: " + beanName );
            }
        }
    }

    /**
     * TODO: doc
     *
     * @param frame  FRAME where data will be read
     * @param object OBJECT object to populate
     * @throws IntrospectionException
     * @throws SwingIntrospectorException
     */
    public synchronized void populateObjectWithException(
            final FRAME  frame,
            final OBJECT object
            )
        throws  SwingIntrospectorException,
                IntrospectionException
    {
        ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY> op = objectInterface.getObjectPopulator( frame, object );

        for( Entry<String,OBJECT_ENTRY> item : objectInterface.getObjectInfos().entrySet()) {
            String                    beanName = item.getKey();
            SwingIntrospectorRootItem<FRAME> rootItem = this.getRootItem( beanName );

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
        ObjectPopulator<FRAME,OBJECT,OBJECT_ENTRY> op = objectInterface.getObjectPopulator( frame, object );

        for( Entry<String,OBJECT_ENTRY> item : objectInterface.getObjectInfos().entrySet()) {
            String                    beanName = item.getKey();
            SwingIntrospectorRootItem<FRAME> rootItem = this.getRootItem( beanName );

            if( rootItem == null ) {
                slogger.fatal( "NoRootItemException: " + beanName );
                }

            try {
                op.populateObject( item.getValue(), rootItem );
            }
            catch( SwingIntrospectorException e ) {
                slogger.warn( "SwingIntrospectorException for: " + beanName );
            }
            catch( IntrospectionException e ) {
                slogger.warn( "SwingIntrospectorException for: " + beanName );
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
        for( Entry<String,SwingIntrospectorRootItem<FRAME>> entry : this.getItemMap().entrySet()) {
            for( SwingIntrospectorItem<FRAME> fd : entry.getValue() ) {

                try {
                    initComponents( populateObject, entry, fd );
                }
                catch( SwingIntrospectorException e ) {
                    slogger.warn( "initComponents()", e );
                }
            }
        }
    }

    /**
     * TODO: doc!
     *
     * @param populateObject
     * @throws SwingIntrospectorIllegalAccessException
     * @throws SwingIntrospectorException
     * @throws SwingIntrospectorNoMaxValueException
     * @see #initComponentsWithoutException(Object)
     */
    public void initComponentsWithException( final FRAME populateObject )
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        for( Entry<String,SwingIntrospectorRootItem<FRAME>> entry : this.getItemMap().entrySet()) {
            for( SwingIntrospectorItem<FRAME> fd : entry.getValue() ) {
                initComponents( populateObject, entry, fd );
            }
        }
    }

    private void initComponents(
            final FRAME populateObject,
            final Entry<String,SwingIntrospectorRootItem<FRAME>> entry,
            final SwingIntrospectorItem<FRAME> fd
            )
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        Object obj = fd.getFieldObject( populateObject );

        //objectInterface.initComponent( obj, entry.getKey() );
        objectInterface.getComponentInitializer().initComponent( obj, entry.getKey() );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append( "SwingIntrospector [itemsMap=" );
        builder.append( itemsMap );
        builder.append( ", objectInterface=" );
        builder.append( objectInterface );
        builder.append( ']' );

        return builder.toString();
    }

    /**
     * @param <FRAME> 
     * @param <OBJECT> 
     * @param frameClass 
     * @param objectClass 
     * @return a SwingIntrospector based on {@link DefaultSwingIntrospectorObjectInterface}
     * 
     */
    public static <FRAME,OBJECT> SwingIntrospector<FRAME,OBJECT,DefaultIntrospectionItem<OBJECT>> buildSwingIntrospector(
            Class<FRAME>    frameClass,
            Class<OBJECT>   objectClass
            )
    {
        return new SwingIntrospector<FRAME,OBJECT,DefaultIntrospectionItem<OBJECT>>( 
                new DefaultSwingIntrospectorObjectInterface<FRAME,OBJECT>(
                        frameClass,
                        objectClass
                        ) 
                );
    }
    
}
