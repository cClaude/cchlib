package com.googlecode.cchlib.util.populator;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.properties.PropertiesHelper;

/**
 * PropertiesPopulator is a simple way to store values in a properties
 * with a minimum work.
 * <p>
 * PropertiesPopulator is able to set or get {@link Field} values
 * annotates with {@link Populator} or {@link Persistent}.
 * <p>
 * <i>Warn:</i>Only primitive type are supported (and standard derived object) by
 * {@link Populator}.<br>
 * See {@link PopulatorContener} if you need to support extra type.
 * <br>
 * Swing components supported by {@link Persistent}
 * <ul>
 *   <li>{@link javax.swing.JTextField JTextField}</li>
 *   <li>{@link javax.swing.JCheckBox JCheckBox}</li>
 *   <li>{@link javax.swing.JComboBox JComboBox} (Store only selected index)</li>
 * </ul>
 *
 * @param <E> the type of the element to populate
 */
public class PropertiesPopulator<E> implements Serializable
{
    private static final long serialVersionUID = 2L;

    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulator.class );

    private static final int IS_LENGTH      = 2;
    private static final int GET_SET_LENGTH = 3;

    private transient Class<? extends E> beanType;
    private PopulatorConfig              config;

    private final Map<Field ,PropertiesPopulatorAnnotation<E,Field>>  fieldsMap       = new HashMap<>();
    private final Map<String,PropertiesPopulatorAnnotation<E,Method>> getterSetterMap = new HashMap<>();

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param type {@link Class} to use to create this {@link PropertiesPopulator}.
     */
    public PropertiesPopulator( @Nonnull final Class<? extends E> type )
    {
        this( type, PopulatorConfig.getDefaultConfig() );
        // Remark :
        // Compatibility of default configuration is break, here
        // Previously it was :
        // buildFieldMap( this.beanType.getDeclaredFields() ) - for fields
        // buildMethodMaps( this.beanType.getDeclaredMethods() ) - for methods
    }

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param type {@link Class} to use to create this {@link PropertiesPopulator}.
     * @param populatorConfig Configuration
     */
    public PropertiesPopulator(
        @Nonnull final Class<? extends E> type,
        @Nonnull final PopulatorConfig    populatorConfig
        )
    {
        this.beanType = type;
        this.config   = populatorConfig;

        init();
    }

    private void init()
    {
        buildFieldMap( this.config.getFieldsConfig().getFields( this.beanType ) );
        buildMethodMaps( this.config.getMethodsConfig().getMethods( this.beanType ) );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Found " + this.fieldsMap.size() + " fields on " + this.beanType );

            for( final Map.Entry<Field, PropertiesPopulatorAnnotation<E, Field>> entry : this.fieldsMap.entrySet() ) {
                LOGGER.trace( "Key[" + entry.getKey() + "]=" + entry.getValue() );
                }

            for( final Map.Entry<String, PropertiesPopulatorAnnotation<E, Method>> entry : this.getterSetterMap.entrySet() ) {
                LOGGER.trace( "Method[" + entry.getKey() + "]=" + entry.getValue() );
                }
            }
    }

    // Serializable
    private void writeObject( final ObjectOutputStream out )
        throws IOException
    {
        out.defaultWriteObject();
    }

    // Serializable
    private void readObject( final ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        init();
    }

    private void buildMethodMaps( final Method[] methods )
    {
        for( final Method method : methods ) {
            final Populator populator = method.getAnnotation( Populator.class );

            if( populator != null ) {
                tryToBuildMethodPopulator( methods, method, populator );
                }

            final Persistent persistent = method.getAnnotation( Persistent.class );

            if( persistent != null ) {
                final String attributeName = getAttributeNameForGetter( method );

                this.getterSetterMap.put(
                        attributeName,
                        new PropertiesPersistentAnnotationForMethodImpl<>( persistent, method, attributeName )
                        );
                }
        }
    }

    private static String getAttributeNameForGetter( final Method method )
    {
        final String name = method.getName();

        if( name.startsWith( "get" ) ) {
            return name.substring( GET_SET_LENGTH );
        }
        return null;
    }

    private void tryToBuildMethodPopulator(
        final Method[]  methods,
        final Method    method,
        final Populator populator
        )
    {
        final Method getter;
        final Method setter;
        final String attributName;

        if( method.getParameterCount() == 0 ) {
            final String methodName = method.getName();

            if( methodName.startsWith( "get" ) ) {
                getter       = method;
                attributName = methodName.substring( GET_SET_LENGTH );
                setter       = findSetter( attributName, methods, method.getReturnType() );
            } else if( methodName.startsWith( "is" ) ) {
                getter       = method;
                attributName = methodName.substring( IS_LENGTH );
                setter       = findSetter( attributName, methods, method.getReturnType() );
            } else {
                getter       = null;
                setter       = null;
                attributName = null;
            }
        } else if( method.getParameterCount() == 1 ) {
            final String methodName = method.getName();

            if( methodName.startsWith( "set" ) ) {
                setter       = method;
                attributName = methodName.substring( GET_SET_LENGTH );

                final Class<?> returnType = method.getReturnType();
                final Method   getgetter  = findMethod( "get" + attributName, methods, returnType , 0 );

                if( getgetter == null ) {
                    getter = findMethod( "is" + attributName, methods, returnType, 0 );
                } else {
                    getter = getgetter;
                }
            } else {
                getter       = null;
                setter       = null;
                attributName = null;
            }
        } else {
            getter       = null;
            setter       = null;
            attributName = null;
        }

        if( (getter != null) && (setter != null) && (attributName != null) ) {
            this.getterSetterMap.put( //
                    attributName, //
                    new PropertiesPopulatorAnnotationForMethodImpl<>( populator, getter, setter, attributName ) //
                    );
        } else {
            LOGGER.warn(
                "Populator warning : can not handle Method " + method
                    + " getter=" + getter
                    + " / setter=" + setter
                    + " / attributName=[" + attributName
                    + ']'
                );
        }
    }

    private Method findSetter(
        final String   attributName,
        final Method[] methods,
        final Class<?> returnType
        )
    {
        return findMethod( "set" + attributName, methods, returnType, 1 );
    }

    private Method findMethod(
            final String   methodName,
            final Method[] methods,
            final Class<?> returnType,
            final int      parameterCount
            )
    {
        for( final Method method : methods ) {
            if( method.getName().equals( methodName )  && (method.getParameterCount() == parameterCount) ) {

                // TODO return type should be also checked...
                return method;
            }
        }

        return null;
    }

    private void buildFieldMap( final Field[] fields )
    {
        for( final Field field : fields ) {
            final Populator populator = field.getAnnotation( Populator.class );

            if( populator != null ) {
                this.fieldsMap.put(
                    field,
                    new PropertiesPopulatorAnnotationForFieldImpl<>( populator, field )
                    );
                }

            final Persistent persistent = field.getAnnotation( Persistent.class );

            if( persistent != null ) {
                this.fieldsMap.put(
                    field,
                    new PropertiesPersistentAnnotationForFieldImpl<E>( persistent, field )
                    );
                }
            }
    }

    /**
     * Store fields annotate with {@link Populator} from bean to properties
     *
     * @param bean
     *            Object to use to get values
     * @param properties
     *            {@link Properties} to use to store values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorRuntimeException
    {
        populateProperties( null, bean, properties );
    }

    /**
     * Store fields annotate with {@link Populator} from bean to a {@link Map}
     *
     * @param bean
     *            Object to use to get values
     * @param map
     *            {@link Map} to use to store values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateMap(
        final E                  bean,
        final Map<String,String> map
        ) throws PropertiesPopulatorRuntimeException
    {
        populateMap( null, bean, map );
    }

    /**
     * Store fields annotate with {@link Populator} from bean to properties.
     * <p>
     * Null entries are not stored in properties, but null entries from
     * arrays are stored.
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param bean
     *            Object to use to get values
     * @param properties
     *            {@link Properties} to use to store values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        final String     propertiesPrefix,
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorRuntimeException
    {
        final Map<String,String> map = new HashMap<>();

        populateMap( propertiesPrefix, bean, map );

        properties.putAll( map );
    }

    /**
     * Store fields annotate with {@link Populator} from bean to a {@link Map}.
     * <p>
     * Null entries are not stored in properties, but null entries from
     * arrays are stored.
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param bean
     *            Object to use to get values
     * @param map
     *            {@link Map} to use to store values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateMap(
        final String             propertiesPrefix,
        final E                  bean,
        final Map<String,String> map
        ) throws PropertiesPopulatorRuntimeException
    {
        final BeanToProperties<E> loader
            = new BeanToProperties<>( bean, map, propertiesPrefix );

        loader.loadForField( this.fieldsMap );
        loader.loadForMethod( this.getterSetterMap );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param bean
     *            Object to use to store values
     * @param properties
     *            {@link Properties} to use to get values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorRuntimeException
    {
        populateBean( null, properties, bean );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param bean
     *            Object to use to store values
     * @param map
     *            {@link Map} to use to get values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final Map<String,String> map,
        final E                  bean
        ) throws PropertiesPopulatorRuntimeException
    {
        populateBean( null, map, bean );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param bean
     *            Object to use to store values
     * @param properties
     *            {@link Properties} to use to get values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final String     propertiesPrefix,
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorRuntimeException
    {
        final PropertiesToBean<E> propertiesToBean
            = new PropertiesToBean<>( propertiesPrefix, properties, bean );

        propertiesToBean.populateFields( this.fieldsMap );
        propertiesToBean.populateMethods( this.getterSetterMap );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param bean
     *            Object to use to store values
     * @param map
     *            {@link Map} to use to get values
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.2
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final String             propertiesPrefix,
        final Map<String,String> map,
        final E                  bean
        ) throws PropertiesPopulatorRuntimeException
    {
        final PropertiesToBean<E> propertiesToBean
            = new PropertiesToBean<>( propertiesPrefix, map, bean );

        propertiesToBean.populateFields( this.fieldsMap );
        propertiesToBean.populateMethods( this.getterSetterMap );
    }

    /**
     * Initialize a bean from a properties file.
     *
     * @param <E>
     *            type of the bean
     * @param propertiesFile
     *            File to load
     * @param bean
     *            Bean initialize
     * @param clazz
     *            Class of bean
     * @return giving bean for initialization chaining.
     * @throws IOException
     *             if any I/O occur
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> E loadProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorRuntimeException
    {
        final Properties             properties = PropertiesHelper.loadProperties( propertiesFile );
        final PropertiesPopulator<E> populator  = new PropertiesPopulator<>( clazz );

        populator.populateBean( properties, bean );

        return bean;
    }

    /**
     * Save a bean to a properties file.
     *
     * @param <E>
     *            type of the bean
     * @param propertiesFile
     *            File to create
     * @param bean
     *            Bean to save
     * @param clazz
     *            Class of bean
     * @throws IOException
     *             if any I/O occur
     * @throws PropertiesPopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> void saveProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorRuntimeException
    {
        final Properties             properties = new Properties();
        final PropertiesPopulator<E> populator  = new PropertiesPopulator<>( clazz );

        populator.populateProperties( bean, properties );

        PropertiesHelper.saveProperties( propertiesFile, properties );
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     * <p>
     * The method require {@link PropertiesPopulator} to be initialized
     * with a concrete object and having a default constructor - see
     * {@link Class#newInstance()} for more details.
     *
     * @return a {@link Map} that corresponding to the bean with default values.
     * @throws InstantiationException
     *             if this Class represents an abstract class, an interface, an
     *             array class, a primitive type, or void; or if the class has no
     *             nullary constructor; or if the instantiation fails for some
     *             other reason.
     * @throws IllegalAccessException
     *             if the class or its nullary constructor is not accessible.
     *
     * @see #newMapForBean(Class)
     * @see #newMapForBean(String, Class)
     * @since 4.2
     */
    @SuppressWarnings("squid:S1160")
    public Map<String, String> newMapForBean()
        throws InstantiationException, IllegalAccessException
    {
        return newMapForBean( (String)null, this.beanType );
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     *
     * @param concreteBeanInstance
     *            A concrete type for this {@link PropertiesPopulator} having
     *            a default constructor - see {@link Class#newInstance()} for
     *            more details.
     * @return a {@link Map} that corresponding to the bean with default values.
     * @throws InstantiationException
     *             if this Class represents an abstract class, an interface, an
     *             array class, a primitive type, or void; or if the class has no
     *             nullary constructor; or if the instantiation fails for some
     *             other reason.
     * @throws IllegalAccessException
     *             if the class or its nullary constructor is not accessible.
     *
     * @see #newMapForBean()
     * @see #newMapForBean(String, Class)
     * @since 4.2
     */
    @SuppressWarnings("squid:S1160")
    public Map<String, String> newMapForBean(
        final Class<? extends E> concreteBeanInstance
        ) throws InstantiationException, IllegalAccessException
    {
        return newMapForBean( (String)null, concreteBeanInstance );
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param concreteBeanInstance
     *            A concrete type for this {@link PropertiesPopulator} having
     *            a default constructor - see {@link Class#newInstance()} for
     *            more details.
     * @return a {@link Map} that corresponding to the bean with default values.
     * @throws InstantiationException
     *             if this Class represents an abstract class, an interface, an
     *             array class, a primitive type, or void; or if the class has no
     *             nullary constructor; or if the instantiation fails for some
     *             other reason.
     * @throws IllegalAccessException
     *             if the class or its nullary constructor is not accessible.
     *
     * @see Class#newInstance()
     * @since 4.2
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    public Map<String, String> newMapForBean(
        final String             propertiesPrefix,
        final Class<? extends E> concreteBeanInstance
        ) throws InstantiationException, IllegalAccessException
    {
        final Map<String,String> map  = new HashMap<>();
        final E                  bean = concreteBeanInstance.newInstance();

        populateBean( propertiesPrefix, map, bean );
        populateMap( propertiesPrefix, bean, map );

        return map;
    }
}
