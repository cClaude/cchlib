package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * Load resources for GUI
 */
public class ResourcesLoader
{
    private final static Logger logger = Logger.getLogger( ResourcesLoader.class );
    private ResourcesLoader()
    {
        // All static
    }

    /**
     *
     * @param name
     * @return
     */
    public static InputStream getResourceAsStream( final String name )
    {
        InputStream stream = ResourcesLoader.class.getResourceAsStream( name );

        if( stream == null ) {
            logger.error( "Can't find resource: " + name );
            }

        return stream;
    }

    public static Icon getImageIcon( final String name )
    {
        try {
            return new ImageIcon(
                    ResourcesLoader.class.getResource( name )
                    );
            }
        catch( Exception e ) {
            logger.error( "Can't find image: " + name );

            return null;
            }
    }

    public static Image getImage( final String name )
    {
        URL url = ResourcesLoader.getResource( name );

        if( url == null ) {
            logger.error(
                String.format( "Bad url for image : '%s'", name )
                );

            return null;
            }

        Image image = Toolkit.getDefaultToolkit().getImage( url );

        if( image == null ) {
            logger.error(
                String.format( "No image for : '%s'", name )
                );
        }

        return image;
    }

    public static URL getResource( final String name )
    {
        return ResourcesLoader.class.getResource( name );
    }
}
