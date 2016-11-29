package com.googlecode.cchlib.util.properties;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * <p>PropertiesPopulator is a simple way to store values in a properties
 * with a minimum work.</p>
 * <p>
 * PropertiesPopulator is able to set or get {@link Field} values
 * annotates with {@link Populator} or {@link Persistent}.
 * </p>
 * <p>
 * <i>Warn:</i>Only primitive type are supported (and standard derived object) by
 * {@link Populator}.<br>
 * See {@link PopulatorContener} if you need to support extra type.
 * </p>
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
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulator.class );

    private static final int IS_LENGTH = 2;
    private static final int GET_SET_LENGTH = 3;

    private transient Class<? extends E> clazz;
    private final Map<Field ,PropertiesPopulatorAnnotation<E,Field>>  fieldsMap      = new HashMap<>();
    private final Map<String,PropertiesPopulatorAnnotation<E,Method>> getterSetterMap = new HashMap<>();

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param clazz {@link Class} to use to create this {@link PropertiesPopulator}.
     */
    public PropertiesPopulator( final Class<? extends E> clazz )
    {
        this.clazz = clazz;

        init();
    }

    private void init()
    {
        buildFieldMap( this.clazz.getDeclaredFields() );
        buildMethodMaps( this.clazz.getDeclaredMethods() );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Found " + this.fieldsMap.size() + " fields on " + this.clazz );

            for( final Entry<Field, PropertiesPopulatorAnnotation<E, Field>> entry : this.fieldsMap.entrySet() ) {
                LOGGER.trace( "Key[" + entry.getKey() + "]=" + entry.getValue() );
                }

            for( final Entry<String, PropertiesPopulatorAnnotation<E, Method>> entry : this.getterSetterMap.entrySet() ) {
                LOGGER.trace( "Method[" + entry.getKey() + "]=" + entry.getValue() );
                }
            }
    }

    //Serializable
    private void writeObject(final ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();
    }

    //Serializable
    private void readObject(final ObjectInputStream in)
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

                this.getterSetterMap.put( attributeName, new PropertiesPersistentAnnotationForMethodImpl<>( persistent, method, attributeName )  );
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

    private void tryToBuildMethodPopulator( final Method[] methods, final Method method, final Populator populator )
    {
        Method getter;
        Method setter;
        String attributName;

        if( method.getParameterCount() == 0 ) {
            final String methodName = method.getName();

            if( methodName.startsWith( "get" ) ) {
                getter = method;
                attributName = methodName.substring( GET_SET_LENGTH );
                setter = findSetter( attributName, methods, method.getReturnType() );
            } else if( methodName.startsWith( "is" ) ) {
                getter = method;
                attributName = methodName.substring( IS_LENGTH );
                setter = findSetter( attributName, methods, method.getReturnType() );
            } else {
                getter = null;
                setter = null;
                attributName = null;
            }

        } else  if( method.getParameterCount() == 1 ) {
            final String methodName = method.getName();

            if( methodName.startsWith( "set" ) ) {
                setter = method;
                attributName = methodName.substring( GET_SET_LENGTH );

                final Class<?> returnType = method.getReturnType();
                getter = findMethod( "get" + attributName, methods, returnType , 0 );

                if( getter == null ) {
                    getter = findMethod( "is" + attributName, methods, returnType, 0 );
                }
            } else {
                getter = null;
                setter = null;
                attributName = null;
            }
        } else {
            getter = null;
            setter = null;
            attributName = null;
        }

        if( (getter != null) && (setter != null) && (attributName != null) ) {
            this.getterSetterMap.put( //
                    attributName, //
                    new PropertiesPopulatorAnnotationForMethodImpl<>( populator, getter, setter, attributName ) //
                    );
        } else {
            LOGGER.warn( "Populator warning : can not handle Method " + method + " getter=" + getter + " / setter=" + setter + " / attributName=[" + attributName + ']' );
        }
    }

    private Method findSetter(
            final String   attributName,
            final Method[] methods,
            final Class<?> type
            )
    {
        return findMethod( "set" + attributName, methods, type, 1 );
    }

    private Method findMethod(
            final String   methodName,
            final Method[] methods,
            final Class<?> type,
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
                this.fieldsMap.put( field, new PropertiesPopulatorAnnotationForFieldImpl<>( populator, field ) );
                }

            final Persistent persistent = field.getAnnotation( Persistent.class );

            if( persistent != null ) {
                this.fieldsMap.put( field, new PropertiesPersistentAnnotationForFieldImpl<E>( persistent, field ) );
                }
            }
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties
     *
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @throws PropertiesPopulatorException if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorException
    {
        populateProperties( null, bean, properties );
    }

    /**
     * Store fields annotate with {@link Populator} from bean
     * to properties.<br>
     * Null entries are not stored in properties, but null entries
     * from arrays are stored.
     *
     * @param propertiesPrefix Prefix for properties names, if null or empty ignored.
     * @param bean       Object to use to get values
     * @param properties {@link Properties} to use to store values
     * @throws PropertiesPopulatorException if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        final String     propertiesPrefix,
        final E          bean,
        final Properties properties
        ) throws PropertiesPopulatorException
    {
        final BeanToProperties<E> loader //
        = new BeanToProperties<>( bean, properties, propertiesPrefix );

        loader.loadForField( this.fieldsMap );
        loader.loadForMethod( this.getterSetterMap );
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param bean       Object to use to store values
     * @param properties {@link Properties} to use to get values
     * @throws PropertiesPopulatorException if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorException
    {
        populateBean( null, properties, bean );
    }

    /**
     * Set fields annotate with {@link Populator} from properties
     * on bean
     *
     * @param propertiesPrefix Prefix for properties names, if null or empty ignored.
     * @param bean       Object to use to store values
     * @param properties {@link Properties} to use to get values
     * @throws PropertiesPopulatorException if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final String     propertiesPrefix,
        final Properties properties,
        final E          bean
        ) throws PropertiesPopulatorException
    {
        final PropertiesToBean<E> propertiesToBean = new PropertiesToBean<>( propertiesPrefix, properties, bean);

        propertiesToBean.populateFields( this.fieldsMap );
        propertiesToBean.populateMethods( this.getterSetterMap );
    }

    /**
     * Initialize a bean from a properties file.
     *
     * @param <E> type of the bean
     * @param propertiesFile    File to load
     * @param bean              Bean initialize
     * @param clazz             Class of bean
     * @return giving bean for initialization chaining.
     * @throws IOException if any I/O occur
     * @throws PropertiesPopulatorException if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> E loadProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorException
    {
        final Properties properties = PropertiesHelper.loadProperties( propertiesFile );
        new PropertiesPopulator<>( clazz ).populateBean( properties, bean );
        return bean;
    }

    /**
     * Save a bean to a properties file.
     *
     * @param <E> type of the bean
     * @param propertiesFile File to create
     * @param bean           Bean to save
     * @param clazz          Class of bean
     * @throws IOException if any I/O occur
     * @throws PropertiesPopulatorException if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> void saveProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PropertiesPopulatorException
    {
        final Properties properties = new Properties();
        new PropertiesPopulator<>( clazz ).populateProperties( bean, properties );
        PropertiesHelper.saveProperties( propertiesFile, properties );
    }
}
