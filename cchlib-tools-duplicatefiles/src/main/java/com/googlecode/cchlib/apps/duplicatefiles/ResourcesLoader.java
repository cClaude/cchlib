package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Load resources for GUI
 */
public class ResourcesLoader
{
    private final static Logger logger = Logger.getLogger( ResourcesLoader.class );

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
        return ResourcesLoader.class.getResource( name );
    }

    /**
     * Find {@link InputStream} for resource, according to this 
     * class {@link ClassLoader}
     * 
     * @param name Resource name
     * @return {@link InputStream} for giving resource name
     * @see Class#getResourceAsStream(String)
     * @throws ResourcesLoaderException If resource is not found
     */
    public static InputStream getResourceAsStream( final String name )
    {
        final InputStream stream = ResourcesLoader.class.getResourceAsStream( name );

        if( stream == null ) {
            logger.error( "Can't find resource: " + name );
            }

        return stream;
    }

    /**
     * Build {@link Icon} for giving resource name
     * @param name Resource name
     * @return {@link Icon} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    public static Icon getImageIcon( final String name )
    {
        try {
            return new ImageIcon( getResource( name ) );
            }
        catch( Exception e ) {
            logger.error( "Can't find image: " + name );

            return null;
            }
    }

    /**
     * Build {@link Image} for giving resource name
     * @param name Resource name
     * @return {@link Image} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    public static Image getImage( final String name )
    {
        final URL url = getResource( name );

        if( url == null ) {
            logger.error(
                String.format( "Bad url for image : '%s'", name )
                );

            return null;
            }

        final Image image = Toolkit.getDefaultToolkit().getImage( url );

        if( image == null ) {
            logger.error(
                String.format( "No image for : '%s'", name )
                );
            }

        return image;
    }


    /**
     * Build {@link Properties} for giving resource name
     * @param name Resource name
     * @return {@link Properties} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    public static Properties getProperties( final String name )
    {
        final Properties prop = new Properties();

        try {
            InputStream is = ResourcesLoader.getResourceAsStream( name );

            if( is != null ) {
                prop.load( is );
                is.close();
                }
            }
        catch( IOException e ) {
            logger.error( "Can't load properties: " + name, e );
            }

        return prop;
    }

}
