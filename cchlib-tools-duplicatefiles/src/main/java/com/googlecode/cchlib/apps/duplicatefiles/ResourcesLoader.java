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
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
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
     *
     * @param name
     * @return
     */
    public static Properties getProperties( final String name )
    {
        final Properties prop = new Properties();

        try {
            InputStream is   = ResourcesLoader.getResourceAsStream(
                    "JPanelConfig.properties"
                    );

            if( is != null ) {
                prop.load( is );
                is.close();
                }
            }
        catch( IOException e ) {
            logger.error( "Can't load properties", e );
            }

        return prop;
    }

}
