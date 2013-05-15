package com.googlecode.cchlib.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provide a default implementation based on {@link ResourceBundle}
 * for {@link I18nInterface}
 */
public class I18nResourceBundle implements I18nInterface
{
    private static final long serialVersionUID = 2L;

    /**
     *  ResourceBundle does not implement {@link java.io.Serializable}
     *  need a {@link #resourceBundleBaseName} information
     *  to restore it during serialization process.
     */
    protected transient ResourceBundle resourceBundle;

    /**
    * This field is use to restore {@link #resourceBundle} during
    *  serialization process.
    */
    protected String resourceBundleBaseName;

    /**
     * Provide a non initialized object for inherit class
     * that must initialize ResourceBundle object ({@link #resourceBundle}
     * and {@link #resourceBundleBaseName}).
     */
    protected I18nResourceBundle()
    { //Empty
    }

    /**
     *
     * @param resourceBundleBaseName
     * @throws IllegalArgumentException if resourceBundleBaseName is null
     */
    protected I18nResourceBundle( final String resourceBundleBaseName )
    {
        this.resourceBundleBaseName = resourceBundleBaseName;
        
        if( resourceBundleBaseName == null ) {
            throw new IllegalArgumentException(
                new NullPointerException( "resourceBundleBaseName is null" )
                );
            }
    }

    /**
     * Create I18nResourceBundle using giving
     * resource bundle and resource bundle baseName
     *
     * @param resourceBundle            ResourceBundle to use
     * @param resourceBundleBaseName    base name for this resource bundle
     */
    public I18nResourceBundle(
            final ResourceBundle    resourceBundle,
            final String            resourceBundleBaseName
            )
    {
        this( resourceBundleBaseName );
        this.resourceBundle = resourceBundle;
    }

    @Override // I18nInterface
    public String getString(String key)
        throws java.util.MissingResourceException
    {
        return resourceBundle.getString( key );
    }

    /**
     * @return current ResourceBundle
     */
    public ResourceBundle getResourceBundle()
    {
        return this.resourceBundle;
    }

    /**
     * <code>ResourceBundle</code> is not serializable so this serializes
     * the base bundle name and the locale with the hopes that this
     * will be enough to look up the message again when this instance
     * is deserialized. This assumes the new place where this object
     * was deserialized has the resource bundle available. If it does
     * not, the original message will be reused.
     *
     * @param out where to write the serialized stream
     * @throws IOException if any
     */
    private void writeObject( java.io.ObjectOutputStream out )
        throws IOException
    {
        // Default serialization process (store baseName for this resourceBundle)
        out.defaultWriteObject();

        // store Locale for this resourceBundle
        out.writeObject( resourceBundle.getLocale() );
    }

    private void readObject( java.io.ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        // Default serialization process  (restore baseName for this resourceBundle)
        in.defaultReadObject();

        // restore Locale for this resourceBundle
        Locale locale = Locale.class.cast( in.readObject() );

        // Build a new ResourceBundle
        resourceBundle = ResourceBundle.getBundle( resourceBundleBaseName, locale );
    }
}
