package com.googlecode.cchlib.apps.duplicatefiles.swing.tools;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Properties;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.Version;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ressources.RessourcesPath;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 * Load resources for GUI
 */
public final class MyResourcesLoader
{
    private static final class TheResources implements Resources {
        private final URI     siteURI;
        private final Version version;

        private TheResources( URI siteURI, Version version )
        {
            this.siteURI = siteURI;
            this.version = version;
        }

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
        public Properties getJPanelConfigProperties() // $codepro.audit.disable declareAsInterface
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

        @Override
        public SerializableIcon getContinueSerializableIcon()
        {
            return new MySerializableIcon( "continue.png" );
        }

        @Override
        public SerializableIcon getRestartSerializableIcon()
        {
            return new MySerializableIcon( "restart.png" );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( MyResourcesLoader.class );
    private static Resources RESOURCES;

    private MyResourcesLoader()
    {
        // All static
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
    private static InputStream getResourceAsStream( final String name ) throws ResourcesLoaderException
    {
        return ResourcesLoader.getResourceAsStream( RessourcesPath.class, name );
    }

    /**
     * Build {@link Icon} for giving resource name
     * @param name Resource name
     * @return {@link Icon} for giving resource name
     */
    public static Icon getImageIcon( final String name )
    {
        try {
            return ResourcesLoader.getImageIcon( RessourcesPath.class, name );
            }
        catch( final ResourcesLoaderException e ) {
            LOGGER.error( "Can't load Icon: " + name, e );

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
            return ResourcesLoader.getImage( RessourcesPath.class, name );
            }
        catch( final ResourcesLoaderException e ) {
            LOGGER.error( "Can't load Image: " + name, e );

            return null;
            }
    }

    /**
     * Build {@link Properties} for giving resource name
     * @param name Resource name
     * @return {@link Properties} for giving resource name
     * @throws ResourcesLoaderException If resource is not found
     */
    private static Properties getProperties( final String name ) // $codepro.audit.disable declareAsInterface
    {
        final Properties prop = new Properties();

        try( final InputStream is = MyResourcesLoader.getResourceAsStream( name ) ) {
            prop.load( is );
            }
        catch( IOException | ResourcesLoaderException e ) {
            LOGGER.error( "Can't load properties: " + name, e );
            }

        return prop;
    }

    public static Resources getResources()
    {
        if( RESOURCES == null ) {
            final URI siteURI;

            try {
                siteURI = new URI( "https://code.google.com/p/cchlib-apps/" );
                }
            catch( final URISyntaxException e ) {
                throw new RuntimeException( e );
                }

            final Version version = Version.getInstance();

            RESOURCES = newResources( siteURI, version );
            }
        return RESOURCES;
    }

    private static Resources newResources( final URI siteURI, final Version version )
    {
        return new TheResources( siteURI, version );
    }

}
