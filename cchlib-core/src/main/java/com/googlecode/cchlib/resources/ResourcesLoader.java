package com.googlecode.cchlib.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Load resources for GUI
 */
public class ResourcesLoader
{
    public static final String OK_ICON_16x16 = "ok_16x16.png";
    public static final String OK_ICON_32x32 = "ok_32x32.png";
    public static final String OK_ICON_48x48 = "ok_48x48.png";

    // static methods
    private ResourcesLoader()
    {
        // All static
    }

    /**
     *
     * @param name
     * @return
     */
    public static URL getResource( final String name )
    {
        return ResourcesLoader.class.getResource( name );
    }

    /**
     *
     * @param name
     * @return
     * @throws ResourcesLoaderException
     */
    public static InputStream getResourceAsStream( final String name )
        throws ResourcesLoaderException
    {
        final InputStream stream = ResourcesLoader.class.getResourceAsStream( name );

        if( stream == null ) {
            throw new ResourcesLoaderException( "Can't find resource: " + name );
            }

        return stream;
    }

    /**
     *
     * @param name
     * @return
     * @throws ResourcesLoaderException
     */
    public static Icon getImageIcon( final String name )
        throws ResourcesLoaderException
    {
        try {
            return new ImageIcon( getResource( name ) );
            }
        catch( Exception e ) {
            throw new ResourcesLoaderException( "Can't find image: " + name );
            }
    }

    /**
     *
     * @param name
     * @return
     * @throws ResourcesLoaderException
     */
    public static Image getImage( final String name )
        throws ResourcesLoaderException
    {
        final URL url = getResource( name );

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
