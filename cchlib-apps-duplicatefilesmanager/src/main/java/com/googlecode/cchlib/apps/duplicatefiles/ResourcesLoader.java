package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException; // $codepro.audit.disable unnecessaryImport
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.Version;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Load resources for GUI
 */
public class ResourcesLoader
{
    private final static Logger logger = Logger.getLogger( ResourcesLoader.class );
    private static Resources resources;

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
    private static URL getResource( final String name )
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
    private static InputStream getResourceAsStream( final String name )
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
    private static Icon getImageIcon( final String name )
    {
        try {
            return new ImageIcon( getResource( name ) );
            }
        catch( Exception e ) {
            logger.error( "Can't find image: " + name, e );

            return null;
            }
    }

    /**
     * Build {@link Image} for giving resource name
     * @param name Resource name
     * @return {@link Image} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    private static Image getImage( final String name )
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
    private static Properties getProperties( final String name )
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

    public static Resources getResources()
    {
        if( resources == null ) {
            final URI siteURI;
            
            try {
                siteURI = new URI( "https://code.google.com/p/cchlib-apps/" );
                }
            catch( URISyntaxException e ) {
                throw new RuntimeException( e );
                }
            
            final Version version;
            
            try {
                version = new Version();
                }
            catch( IOException | ParseException e ) {
                throw new RuntimeException( e );
                }
            
            resources = new Resources()
            {
                @Override
                public Icon getAppIcon()
                {
                    return getImageIcon( "icon.png" );
                }
                @Override
                public Image getAppImage()
                {
                    return getImage( "icon.png" );
                }
                @Override
                public Icon getContinueIcon()
                {
                    return getImageIcon( "continue.png" );
                }
                @Override
                public Icon getRestartIcon()
                {
                    return getImageIcon( "restart.png" );
                }
                @Override
                public Icon getRemoveIcon()
                {
                    return getImageIcon( "remove.png" );
                }
                @Override
                public Icon getAddIcon()
                {
                    return getImageIcon( "add.png" );
                }
                @Override
                public Icon getLogoIcon()
                {
                    return getImageIcon( "logo.png" );
                }
                @Override
                public Icon getFileIcon()
                {
                    return getImageIcon( "file.png" );
                }
                @Override
                public Icon getFolderIcon()
                {
                    return getImageIcon( "folder.png" );
                }
                @Override
                public Properties getJPanelConfigProperties()
                {
                    return getProperties( "JPanelConfig.properties" );
                }
                @Override
                public Icon getSmallOKIcon()
                {
                    return getImageIcon( "ok.12x12.png" );
                }
                @Override
                public Icon getSmallKOIcon()
                {
                    return getImageIcon( "ko.12x12.png" );
                }
                @Override
                public Icon getSmallOKButOKIcon()
                {
                    return getImageIcon( "ko_but_ok.12x12.png" );
                }
                @Override
                public String getAuthorName()
                {
                    return "Claude CHOISNET";
                }
                @Override
                public String getAboutVersion()
                {
                    return version.getVersion();
                }
                @Override
                public String getAboutVersionDate()
                {
                    return DateFormat.getDateInstance().format( version.getDate() );
                }
                @Override
                public URI getSiteURI()
                {
                    return siteURI;
                }
            };
            }
        return resources;
    }

}
