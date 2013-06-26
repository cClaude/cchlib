package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException; // $codepro.audit.disable unnecessaryImport
import java.util.Properties;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.Version;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Load resources for GUI
 */
public class MyResourcesLoader
{
    private final static Logger logger = Logger.getLogger( MyResourcesLoader.class );
    private static Resources resources;

    // static methods
    private MyResourcesLoader()
    {
        // All static
    }

//    /**
//     * Find {@link URL} for resource, according to this 
//     * class {@link ClassLoader}
//     * 
//     * @param name Resource name
//     * @return {@link URL} for giving resource name
//     * @see Class#getResource(String)
//     */
//    private static URL getResource( final String name )
//    {
//        return ResourcesLoader.getResource( MyResourcesLoader.class, name );
//    }

    /**
     * Find {@link InputStream} for resource, according to this 
     * class {@link ClassLoader}
     * 
     * @param name Resource name
     * @return {@link InputStream} for giving resource name
     * @see Class#getResourceAsStream(String)
     * @throws ResourcesLoaderException If resource is not found
     */
    private static InputStream getResourceAsStream( final String name ) throws ResourcesLoaderException
    {
        return ResourcesLoader.getResourceAsStream( MyResourcesLoader.class, name );
    }

    /**
     * Build {@link Icon} for giving resource name
     * @param name Resource name
     * @return {@link Icon} for giving resource name
     */
    static Icon getImageIcon( final String name ) 
    {
        try {
            return ResourcesLoader.getImageIcon( MyResourcesLoader.class, name );
            }
        catch( ResourcesLoaderException e ) {
            logger.error( "Can't load Icon: " + name, e );
            
            return null;
            }
    }

    /**
     * Build {@link Image} for giving resource name
     * @param name Resource name
     * @return {@link Image} for giving resource name
     */
    private static Image getImage( final String name )
    {
        try {
            return ResourcesLoader.getImage( MyResourcesLoader.class, name );
            }
        catch( ResourcesLoaderException e ) {
            logger.error( "Can't load Image: " + name, e );
            
            return null;
            }
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
            InputStream is = MyResourcesLoader.getResourceAsStream( name );

            if( is != null ) {
                prop.load( is );
                is.close();
                }
            }
        catch( IOException | ResourcesLoaderException e ) {
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
                public Icon getFolderRemoveIcon()
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
                public Icon getFolderSelectIcon()
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
                @Override
                public Icon getPrevIcon()
                {
                    return getImageIcon( "prev.16x16.png" );
                }
                @Override
                public Icon getNextIcon()
                {
                    return getImageIcon( "next.16x16.png" );
                }
                @Override
                public Icon getRefreshIcon()
                {
                    return getImageIcon( "refresh.16x16.png" );
                }
                @Override
                public Icon getFolderImportIcon()
                {
                    return getImageIcon( "import.png" );
                }
                @Override
                public Icon getDeselectAllIcon()
                {
                    return getImageIcon( "deselectAll.png" );
                }
                @Override
                public Icon getSelectAllIcon()
                {
                    return getImageIcon( "selectAll.png" );
                }
            };
            }
        return resources;
    }

}
