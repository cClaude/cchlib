package com.googlecode.cchlib.i18n.resources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.i18n.api.I18nResource;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nResource}
 *
 * @see I18nResourceFactory
 */
public class I18nResourceBundle implements I18nResource, Serializable
{
    private static final long serialVersionUID = 4L;

    /** @serial */
    private Locale locale;

    /**
    * This field is use to restore {@link #resourceBundle} during
    * serialization process.
    * @serial
    */
    private String resourceBundleFullBaseName;

    /**
     *  ResourceBundle does not implement {@link java.io.Serializable}
     *  need a {@link #resourceBundleFullBaseName} information
     *  to restore it during serialization process.
     */
    private transient ResourceBundle resourceBundle;

    /**
     * Build I18nResourceBundle for locale
     *
     * @param resourceBundleFullBaseName
     *            base name for this resource bundle
     * @param locale
     *            {@link Locale} to use
     * @throws IllegalArgumentException
     *             if {@code resourceBundleFullBaseName} is null
     */
    I18nResourceBundle( //
        @Nonnull final String resourceBundleFullBaseName,
        @Nonnull final Locale locale
        )
    {
        if( resourceBundleFullBaseName == null ) {
            throw new IllegalArgumentException( "resourceBundleFullBaseName is null" );
            }

        if( locale == null ) {
            throw new IllegalArgumentException( "locale is null" );
            }

        setResourceBundle( resourceBundleFullBaseName, locale );
    }

    @Override // I18nResource
    public String getString( final String key ) throws MissingResourceException
    {
        try {
            return this.resourceBundle.getString( key );
            }
        catch( final java.util.MissingResourceException e ) {
            throw new MissingResourceException( e );
            }
    }

    /**
     * Returns the {@link ResourceBundle}
     * @return the {@link ResourceBundle}
     */
    public ResourceBundle getResourceBundle()
    {
        return this.resourceBundle;
    }

    /**
     * {@code ResourceBundle} is not serializable so this serializes the
     * base bundle name and the locale with the hopes that this will be
     * enough to look up the message again when this instance is deserialized.
     * This assumes the new place where this object was deserialized has the
     * resource bundle available. If it does not, the original message
     * will be reused.
     *
     * @param out
     *            where to write the serialized stream
     * @throws IOException
     *             if any
     */
    private void writeObject( final ObjectOutputStream out ) throws IOException
    {
        // Default serialization process (store baseName for this resourceBundle)
        out.defaultWriteObject();
    }

    private void readObject( final ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        // Default serialization process  (restore baseName for this resourceBundle)
        in.defaultReadObject();

        // Build a new ResourceBundle
        this.resourceBundle = newResourceBundle();
    }

    private ResourceBundle newResourceBundle()
    {
        return ResourceBundle.getBundle( this.resourceBundleFullBaseName, this.locale );
    }

    protected void setResourceBundle(
        final String resourceBundleFullBaseName,
        final Locale locale
        )
    {
        this.resourceBundleFullBaseName = resourceBundleFullBaseName;
        this.locale                     = locale;
        this.resourceBundle             = newResourceBundle();
    }

    public String getResourceBundleFullBaseName()
    {
        return this.resourceBundleFullBaseName;
    }

    public Locale getLocale()
    {
        return this.locale;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( getClass().getSimpleName() ).append( " [locale=" );
        builder.append( this.locale );
        builder.append( ", resourceBundleFullBaseName=" );
        builder.append( this.resourceBundleFullBaseName );
        builder.append( ", getResourceBundle()=" );
        builder.append( getResourceBundle() );
        builder.append( ']' );

        return builder.toString();
    }
}
