package com.googlecode.cchlib.util.populator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.util.properties.PropertiesHelper;

/**
 * {@link PropertiesPopulator} is a simple way to store values in a properties
 * with a minimum effort. This implementation of {@link MapPopulator}
 * for {@link Properties}.
 *
 * @param <E> the type of the element to populate
 */
public class PropertiesPopulator<E> extends MapPopulator<E>
{
    private static final long serialVersionUID = 3L;

    /**
     * Create a {@link PropertiesPopulator} object for giving class using
     * default configuration : {@link PopulatorConfig#getDefaultConfig()}
     *
     * @param type
     *            {@link Class} to use to create this {@link PropertiesPopulator}.
     *
     * @see PopulatorConfig#getDefaultConfig()
     */
    public PropertiesPopulator( @Nonnull final Class<? extends E> type )
    {
        // Remark :
        // Compatibility of default configuration is break, here
        // Previously it was :
        // buildFieldMap( this.beanType.getDeclaredFields() ) - for fields
        // buildMethodMaps( this.beanType.getDeclaredMethods() ) - for methods
        super( type );
    }

    /**
     * Create a {@link PropertiesPopulator} object for giving class.
     *
     * @param type
     *            {@link Class} to use to create this {@link PropertiesPopulator}.
     * @param populatorConfig
     *            Configuration
     *
     * @see PopulatorConfig#getDefaultConfig()
    */
    public PropertiesPopulator(
        @Nonnull final Class<? extends E> type,
        @Nonnull final PopulatorConfig    populatorConfig
        )
    {
        super( type, populatorConfig );
    }

    /**
     * Store fields annotate with {@link Populator} from bean to properties
     *
     * @param bean
     *            Object to use to get values
     * @param properties
     *            {@link Properties} to use to store values
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        final E          bean,
        final Properties properties
        ) throws PopulatorRuntimeException
    {
        populateProperties( null, bean, properties );
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
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateProperties(
        @Nullable final String     propertiesPrefix,
        final E          bean,
        final Properties properties
        ) throws PopulatorRuntimeException
    {
        final Map<String,String> map = new HashMap<>();

        populateMap( propertiesPrefix, bean, map );

        properties.putAll( map );
    }

    /**
     * Set fields annotate with {@link Populator} from properties on bean
     *
     * @param bean
     *            Object to use to store values
     * @param properties
     *            {@link Properties} to use to get values
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final Properties properties,
        final E          bean
        ) throws PopulatorRuntimeException
    {
        populateBean( null, properties, bean );
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
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void populateBean(
        final String     propertiesPrefix,
        final Properties properties,
        final E          bean
        ) throws PopulatorRuntimeException
    {
        populateBeanFromMapOrProperties( propertiesPrefix, properties, bean );
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
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> E loadProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PopulatorRuntimeException
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
     * @throws PopulatorRuntimeException
     *             if there is a mapping error
     * @since 4.1.7
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public static <E> void saveProperties(
        final File      propertiesFile,
        final E         bean,
        final Class<E>  clazz
        ) throws IOException, PopulatorRuntimeException
    {
        final Properties             properties = new Properties();
        final PropertiesPopulator<E> populator  = new PropertiesPopulator<>( clazz );

        populator.populateProperties( bean, properties );

        PropertiesHelper.saveProperties( propertiesFile, properties );
    }
}
