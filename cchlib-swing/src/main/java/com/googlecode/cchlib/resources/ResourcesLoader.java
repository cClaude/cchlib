package com.googlecode.cchlib.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Load resources for GUI
 */
public class ResourcesLoader
{
    @SuppressWarnings("squid:S00115")
    public static final String OK_ICON_16x16 = "ok_16x16.png";
    @SuppressWarnings("squid:S00115")
    public static final String OK_ICON_32x32 = "ok_32x32.png";
    @SuppressWarnings("squid:S00115")
    public static final String OK_ICON_48x48 = "ok_48x48.png";

    // static methods
    private ResourcesLoader()
    {
        // All static
    }

    /**
     * Find {@link URL} for resource, according to this
     * class {@link ClassLoader}
     *
     * @param name Resource name
     * @return {@link URL} for giving resource name
     * @see Class#getResource(String)
     */
    public static URL getResource( final String name )
    {
        return getResource( ResourcesLoader.class, name );
    }

    public static URL getResource( final Class<?> clazz, final String name )
    {
        return clazz.getResource( name );
    }

    /**
     * Find {@link InputStream} for resource, according to this
     * class {@link ClassLoader} (never return null)
     *
     * @param name Resource name
     * @return {@link InputStream} for giving resource name
     * @see Class#getResourceAsStream(String)
     * @throws ResourcesLoaderException If resource is not found
     */
    public static InputStream getResourceAsStream( final String name )
        throws ResourcesLoaderException
    {
        return getResourceAsStream( ResourcesLoader.class, name );
    }

    /**
     * Return an {@link InputStream} using {@link Class#getResourceAsStream(String)}
     * but never return null.
     * @param clazz class to use
     * @param name  name of the desired resource
     * @return a valid {@link InputStream}
     * @throws ResourcesLoaderException if resource can not be open.
     */
    public static InputStream getResourceAsStream(
        @Nonnull final Class<?> clazz,
        @Nonnull final String   name
        ) throws ResourcesLoaderException
    {
        final InputStream stream = clazz.getResourceAsStream( name );

        if( stream == null ) {
            throw new ResourcesLoaderException( "Can't find resource: " + name );
            }

        return stream;
    }

    /**
     * Build {@link Icon} for giving resource name
     * @param name Resource name
     * @return {@link Icon} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    public static Icon getImageIcon( final String name ) throws ResourcesLoaderException
    {
        return getImageIcon( ResourcesLoader.class, name );
    }

    public static Icon getImageIcon( final Class<?> clazz, final String name )
        throws ResourcesLoaderException
    {
        try {
            return new ImageIcon( getResource( clazz, name ) );
            }
        catch( final Exception e ) {
            throw new ResourcesLoaderException( "Can't find image: " + name, e );
            }
    }
    /**
     * Build {@link Image} for giving resource name
     * @param name Resource name
     * @return {@link Image} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    public static Image getImage( final String name ) throws ResourcesLoaderException
    {
        return getImage( ResourcesLoader.class, name );
    }

    public static Image getImage( final Class<?> clazz, final String name )
        throws ResourcesLoaderException
    {
        final URL url = getResource( clazz, name );

        if( url == null ) {
            throw new ResourcesLoaderException(
                String.format( "Bad url for image : '%s'", name )
                );
            }

        final Image image = Toolkit.getDefaultToolkit().getImage( url );

        if( image == null ) {
            throw new ResourcesLoaderException(
                    String.format( "No image for : '%s'", name )
                    );
            }

        return image;
    }
}
