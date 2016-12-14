package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingLocaleStringException;

/**
 * Builder for {@link DefaultBRLocaleResources} based on
 * a {@link ResourceBundle}
 *
 * @since 4.2
 */
public class DefaultBRLocaleResourcesBuilder
{

    private static final Logger LOGGER = Logger.getLogger( DefaultBRLocaleResourcesBuilder.class );

    /** Default class support for resources */
    public static final Class<DefaultBRLocaleResources> DEFAULT_CLASS = DefaultBRLocaleResources.class;
    /** Default resource bundle : {@value} */
    public static final String RESOURCE_BUNDLE = ".ResourceBundle";
    /** Default resource bundle : {@value} */
    public static final String DEFAULT_RESOURCE_BUNDLE = ".DefaultResourceBundle";

    private ResourceBundle rb;

    private final Class<?> resourceRefType;

    /**
     * Create DefaultBRLocaleResourcesBuilder with a {@link ResourceBundle}
     *
     * @param resourceRefType NEEDDOC
     * @param resourceBundle {@link ResourceBundle} to use, if
     *       {@code resourceBundle} is null create a {@link ResourceBundle}
     *       based on {@link DefaultBRLocaleResources} package name and
     *       {@link #DEFAULT_RESOURCE_BUNDLE}
     */
    public DefaultBRLocaleResourcesBuilder(
        final Class<?>       resourceRefType,
        @Nullable
        final ResourceBundle resourceBundle
        )
    {
        this.resourceRefType = resourceRefType;

        if( resourceBundle == null ) {
            LOGGER.info(
                "no ResourceBundle provided. Create Internal ResourceBundle based on " +
                        DEFAULT_CLASS + " and " + DEFAULT_RESOURCE_BUNDLE
                );

            this.rb = getResourceBundle( DEFAULT_CLASS, DEFAULT_RESOURCE_BUNDLE );
            }
        else {
            this.rb = resourceBundle;
            }
    }

    /**
     * Create DefaultBRLocaleResourcesBuilder using default
     * {@link ResourceBundle}
     *
     * a {@link ResourceBundle} is create based on
     * {@link DefaultBRLocaleResources} package name and
     * {@link #DEFAULT_RESOURCE_BUNDLE}
     */
    public DefaultBRLocaleResourcesBuilder()
    {
        this( DEFAULT_CLASS, (ResourceBundle)null );
    }

    /**
     * Create DefaultBRLocaleResourcesBuilder
     *
     * @param classForBundle {@link Class} to use to create
     *       a {@link ResourceBundle} based on package name and
     *       {@link #RESOURCE_BUNDLE},
     *       if {@code packageForBundle} is null create a {@link ResourceBundle}
     *       based on {@link DefaultBRLocaleResources} package name and
     *       {@link #DEFAULT_RESOURCE_BUNDLE}
     */
    public DefaultBRLocaleResourcesBuilder( @Nonnull final Class<?> classForBundle )
    {
        this(
            classForBundle,
            getResourceBundle( classForBundle, RESOURCE_BUNDLE )
            );
    }

    private static ResourceBundle getResourceBundle(
        @Nonnull final Class<?> classForBundle,
        @Nonnull final String   suffix
        )
    {
        final String baseName = classForBundle.getPackage().getName()
                + suffix;

        LOGGER.info(  "Create ResourceBundle based on " + baseName );

        return ResourceBundle.getBundle( baseName );
    }

    /**
     * Return the ResourceBundle
     * @return ResourceBundle
     */
    public ResourceBundle getResourceBundle()
    {
        return this.rb;
    }

    public Class<?> getResourceRefType()
    {
        return this.resourceRefType;
    }

    public  String getString( final String key )
        throws MissingLocaleStringException
    {
       try {
           return this.rb.getString( key );
        } catch( final MissingResourceException e ) {
            throw new MissingLocaleStringException( key, e );
        }
    }
}
