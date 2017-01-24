package com.googlecode.cchlib.util.populator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.annotation.Annotations;

/**
 * {@link MapPopulator} is a simple way to store values in a properties
 * with a minimum effort.
 * <p>
 * {@link MapPopulator} is able to set or get {@link Field} values
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
public class MapPopulator<E> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( MapPopulator.class );

    private static final int IS_LENGTH      = 2;
    private static final int GET_SET_LENGTH = 3;

    private transient Class<? extends E> beanType;
    private PopulatorConfig              config;
    private final PersistentResolver     persistentResolver;

    private final Map<Field ,PopulatorAnnotation<E,Field>>  fieldsMap       = new HashMap<>();
    private final Map<String,PopulatorAnnotation<E,Method>> getterSetterMap = new HashMap<>();

    /**
     * Create a {@link MapPopulator} object for giving class using
     * default configuration : {@link PopulatorConfig#getDefaultConfig()}
     *
     * @param type
     *            {@link Class} to use to create this {@link MapPopulator}.
     *
     * @see PopulatorConfig#getDefaultConfig()
     */
    public MapPopulator( @Nonnull final Class<? extends E> type )
    {
        this( type, PopulatorConfig.getDefaultConfig() );
        // Remark :
        // Compatibility of default configuration is break, here
        // Previously it was :
        // buildFieldMap( this.beanType.getDeclaredFields() ) - for fields
        // buildMethodMaps( this.beanType.getDeclaredMethods() ) - for methods
    }

    /**
     * Create a {@link MapPopulator} object for giving class.
     *
     * @param type
     *            {@link Class} to use to create this {@link MapPopulator}.
     * @param populatorConfig
     *            Configuration
     *
     * @see PopulatorConfig#getDefaultConfig()
    */
    public MapPopulator(
        @Nonnull final Class<? extends E> type,
        @Nonnull final PopulatorConfig    populatorConfig
        )
    {
        this.beanType           = type;
        this.config             = populatorConfig;
        this.persistentResolver = this.config.getPersistentResolverFactory().newPersistentResolver();

        init();
    }

    private void init()
    {
        buildFieldMap( this.config.getFieldsConfig().getFields( this.beanType ) );
        buildMethodMaps( this.config.getMethodsConfig().getMethods( this.beanType ) );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Found " + this.fieldsMap.size() + " fields on " + this.beanType );

            for( final Map.Entry<Field, PopulatorAnnotation<E, Field>> entry : this.fieldsMap.entrySet() ) {
                LOGGER.trace( "Key[" + entry.getKey() + "]=" + entry.getValue() );
                }

            LOGGER.trace( "Found " + this.fieldsMap.size() + " methods on " + this.beanType );

            for( final Map.Entry<String, PopulatorAnnotation<E, Method>> entry : this.getterSetterMap.entrySet() ) {
                LOGGER.trace( "Method[" + entry.getKey() + "]=" + entry.getValue() );
                }
            }
    }

    // Serializable
    private void writeObject( final ObjectOutputStream out ) throws IOException
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
            final Populator populator = Annotations.findAnnotation(
                                            method,
                                            Populator.class,
                                            this.config.getAnnotationLookup()
                                            );

            if( LOGGER.isDebugEnabled() ) {
                logBuildMapTraceMessage( populator, method );
            }

            if( populator != null ) {
                tryToBuildMethodPopulator( methods, method, populator );
                }

            final Persistent persistent = method.getAnnotation( Persistent.class );

            if( persistent != null ) {
                final String attributeName = getAttributeNameForGetter( method );

                this.getterSetterMap.put(
                        attributeName,
                        new PersistentAnnotationForMethodImpl<>(
                                persistent,
                                this.persistentResolver,
                                method,
                                attributeName
                                )
                        );
                }
        }
    }

    private static  void logBuildMapTraceMessage(
        final Populator        populator,
        final AnnotatedElement methodOrFields
        )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( buildMapTraceMessage( populator, methodOrFields ) );
        } else if( populator != null ) {
            LOGGER.debug( buildMapTraceMessage( populator, methodOrFields ) );
        }
    }

    private static String buildMapTraceMessage(
        final Populator        populator,
        final AnnotatedElement methodOrFields
        )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( populator == null ? "no " : "found " )
          .append( Populator.class.getSimpleName() )
          .append( " on " ).append( methodOrFields );

        if( populator != null ) {
            sb.append( "find " )
              .append( " : " )
              .append( populator );
        }

        return sb.toString();
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
            addPopulatorMethods( populator, attributName, getter, setter );
        } else {
            LOGGER.warn( buildCanNotHandleMethodMessage( populator, method, attributName, getter, setter ) );
        }
    }

    private void addPopulatorMethods(
        final Populator populator,
        final String    attributName,
        final Method    getter,
        final Method    setter
        )
    {
        if( LOGGER.isDebugEnabled() ) {
            final StringBuilder sb = new StringBuilder();

            sb.append( "Found getter and setter for \"" ).append( attributName ).append( '\"' );

            if( LOGGER.isTraceEnabled() ) {
                sb.append( " : " ).append( setter ).append( " / " ).append( getter );

                LOGGER.trace( sb.toString() );
            } else {
                LOGGER.debug( sb.toString() );
            }
        }

        this.getterSetterMap.put(
            attributName,
            new PopulatorAnnotationForMethodImpl<>( populator, getter, setter, attributName )
            );
    }

    private static String buildCanNotHandleMethodMessage(
        final Populator populator,
        final Method    method,
        final String    attributName,
        final Method    getter,
        final Method   setter
        )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Populator warning : can not handle Method " ).append( method )
          .append( " with annonation " ).append( populator );

        if( attributName == null ) {
            sb.append( " because this not a getter nor a setter :" );
        } else {
            sb.append( " for attribut \"" ).append( attributName )
              .append(  " \" because can not find" );
        }

        sb.append( " (getter, setter)=(" ).append( getter ).append( ',' ).append( setter ).append( ')' );

        return sb.toString();
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

            if( LOGGER.isDebugEnabled() ) {
                logBuildMapTraceMessage( populator, field );
            }

            if( populator != null ) {
                this.fieldsMap.put(
                    field,
                    new PopulatorAnnotationForFieldImpl<>( populator, field )
                    );
                }

            final Persistent persistent = field.getAnnotation( Persistent.class );

            if( persistent != null ) {
                this.fieldsMap.put(
                    field,
                    new PersistentAnnotationForFieldImpl<E>( persistent, this.persistentResolver, field )
                    );
                }
            }
    }

    /**
     * Store fields annotate with {@link Populator} from bean to a {@link Map}
     *
     * @param bean
     *            Object to use to get values
     * @param map
     *            {@link Map} to use to store values
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateMap(
        final E                  bean,
        final Map<String,String> map
        ) throws PopulatorRuntimeException
    {
        populateMap( null, bean, map );
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
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateMap(
        final String             propertiesPrefix,
        final E                  bean,
        final Map<String,String> map
        ) throws PopulatorRuntimeException
    {
        final BeanToMap<E> loader = new BeanToMap<>( bean, map, propertiesPrefix );

        loader.loadForField( this.fieldsMap );
        loader.loadForMethod( this.getterSetterMap );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param bean
     *            Object to use to store values
     * @param map
     *            {@link Map} to use to get values
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public final void populateBean(
        final Map<String,String> map,
        final E                  bean
        ) throws PopulatorRuntimeException
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
     * @param map
     *            {@link Map} to use to get values
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.2
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public final void populateBean(
        final String             propertiesPrefix,
        final Map<String,String> map,
        final E                  bean
        ) throws PopulatorRuntimeException
    {
        populateBeanFromMapOrProperties( propertiesPrefix, map, bean );
    }

    protected void populateBeanFromMapOrProperties(
        final String   propertiesPrefix,
        final Map<?,?> map,
        final E        bean
        )
    {
        final MapToBean<E> mapToBean = new MapToBean<>( propertiesPrefix, map, bean );

        if( ! this.fieldsMap.isEmpty() ) {
            mapToBean.populateFields( this.fieldsMap );
        }

        if( ! this.getterSetterMap.isEmpty() ) {
            mapToBean.populateMethods( this.getterSetterMap );
        }
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     * <p>
     * The method require {@link MapPopulator} to be initialized
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
     * @see #newMapForBean(Supplier)
     * @see #newMapForBean(String, Supplier)
     * @see #newMapForBean(String, Supplier, Supplier)
     *
     * @since 4.2
     */
    @SuppressWarnings("squid:S1160")
    public final Map<String, String> newMapForBean()
        throws InstantiationException, IllegalAccessException
    {
        final E instance = this.beanType.newInstance();

        return newMapForBean( (String)null, () -> instance );
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     *
     * @param instanceSupplier
     *            A {@link Supplier} able to create an "empty" concrete instance.
     * @return a {@link Map} that corresponding to the bean with default values.
     *
     * @see #newMapForBean()
     * @see #newMapForBean(String, Supplier)
     * @see #newMapForBean(String, Supplier, Supplier)
     *
     * @since 4.2
     */
    public final Map<String, String> newMapForBean(
        final Supplier<E> instanceSupplier
        )
    {
        return newMapForBean( (String)null, instanceSupplier );
    }

    /**
     * Create a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param instanceSupplier
     *            A {@link Supplier} able to create an "empty" concrete instance.
     * @return a {@link Map} that corresponding to the bean with default values.
     *
     * @see #newMapForBean()
     * @see #newMapForBean(Supplier)
     * @see #newMapForBean(String, Supplier)
     *
     * @since 4.2
     */
    public final Map<String, String> newMapForBean(
        final String      propertiesPrefix,
        final Supplier<E> instanceSupplier
        )
    {
        return newMapForBean(
                propertiesPrefix,
                instanceSupplier,
                HashMap::new
                );
    }

    /**
     * Fill a {@link Map} with empty values corresponding to a none
     * initialized instance of the bean.
     *
     * @param propertiesPrefix
     *            Prefix for properties names, if null or empty ignored.
     * @param instanceSupplier
     *            A {@link Supplier} able to create an "empty" concrete instance.
     * @param mapSupplier
     *            A {@link Supplier} able to create a {@link Map} with
     *            any required specifications.
     * @return a {@link Map} that corresponding to the bean with default values.
     *
     * @see #newMapForBean()
     * @see #newMapForBean(Supplier)
     * @see #newMapForBean(String, Supplier, Supplier)
     *
     * @since 4.2
     */
    public final Map<String, String> newMapForBean(
        final String                       propertiesPrefix,
        final Supplier<E>                  instanceSupplier,
        final Supplier<Map<String,String>> mapSupplier
        )
    {
        final E                  bean = instanceSupplier.get();
        final Map<String,String> map  = mapSupplier.get();

        populateBean( propertiesPrefix, map, bean );
        populateMap( propertiesPrefix, bean, map );

        return map;
    }

    /**
     * Returns an unmodifiable view of {@link Populator} fields
     *
     * @return an unmodifiable view of {@link Populator} fields
     */
    protected Map<Field ,PopulatorAnnotation<E,Field>> getPopulatorFields()
    {
        return Collections.unmodifiableMap( this.fieldsMap );
    }


    /**
     * Returns an unmodifiable view of {@link Populator} methods
     *
     * @return an unmodifiable view of {@link Populator} methods
     */
    protected Map<String, PopulatorAnnotation<E, Method>> getPopulatorMethods()
    {
        return Collections.unmodifiableMap( this.getterSetterMap );
    }
}
