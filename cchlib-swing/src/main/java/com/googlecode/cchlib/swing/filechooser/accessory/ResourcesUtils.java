package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.Serializable;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.apache.log4j.Logger;

/**
 *
 */
final class ResourcesUtils implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( ResourcesUtils.class );
    private final Locale locale;
    private ResourceBundle resourceBundle;;

    public enum ID {
        BOOKMARK_REMOVE,
        BOOKMARK_ADD, 
        BOOKMARK_UPDATE,
        COPY,
        IMAGE_ICON
        }

    /**
     * Create a ResourcesUtils using default {@link Locale}
     */
    public ResourcesUtils()
    {
        this( null );
    }
    
    
    /**
     * Create a ResourcesUtils
     * 
     * @param locale Locale to use for this ResourcesUtils
     */
    public ResourcesUtils( final Locale locale )
    {
        if( locale == null ) {
            this.locale = Locale.getDefault();
            }
        else {
            this.locale = locale;
            }
    }

    /**
     * Unified {@link Class#getResource(String)}, that crash if resource not found
     *
     * @param resourceName resource to find
     * @return an URL for this resource, never null
     * @throws ResourcesUtilsException if resource is not found
     */
    //public
    private URL getResource( final String resourceName )
        throws ResourcesUtilsException
    {
        URL url = getClass().getResource( resourceName );

        if( url != null ) {
            return url;
            }
        else {
            throw new ResourcesUtilsException( "Can not find : [" + resourceName + "]" );
            }
    }
    
    private URL getResource( final ResourcesUtils.ID resourceId )
        throws ResourcesUtilsException
    {
        switch( resourceId ) {
            case BOOKMARK_REMOVE :
                return getResource( "bookmark-remove.gif" );
            case BOOKMARK_ADD:
                return getResource( "bookmark-add.png" );
            case BOOKMARK_UPDATE:
                return getResource( "reload.gif" );
            case COPY:
                throw new ResourcesUtilsException( "ResourcesUtils.ID not not handle : [" + resourceId + "]" );
            case IMAGE_ICON: 
                return getResource( "picture.png" );
            }
        throw new ResourcesUtilsException( "ResourcesUtils.ID not not handle : [" + resourceId + "]" );
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    public ImageIcon getImageIcon( final ResourcesUtils.ID resourceId )
    {
        try {
            return new ImageIcon(
                getResource( resourceId )
                );
            }
        catch( ResourcesUtilsException e ) {
            return createOnePixelImageIcon();
            }
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    public JButton getJButton( final ResourcesUtils.ID resourceId )
    {
        try {
            return new JButton(
                new ImageIcon(
                    getResource( resourceId )
                    )
                );
            }
        catch( ResourcesUtilsException e ) {
            // Try to show something when resource is missing
            return new JButton( resourceId.name() );
            }
    }

    private ResourceBundle getResourceBundle() 
        throws ResourcesUtilsException
    {
        if( resourceBundle == null ) {
            try {
                final String baseName = getClass().getPackage().getName()
                        + ".MessagesBundle";

                resourceBundle = ResourceBundle.getBundle( baseName, locale );
                }
            catch( MissingResourceException e ) {
                throw new ResourcesUtilsException( e.getMessage() );
                }
            }
        return resourceBundle;
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    public String getText( final ResourcesUtils.ID resourceId )
    {
        try {
            final ResourceBundle rb = getResourceBundle();
            
            return rb.getString( resourceId.name() );
            }
        catch( MissingResourceException e ) {
            logger.error( "Text resource not found for " + resourceId );
 
            return resourceId.name(); 
            }
        catch( ResourcesUtilsException e ) {
            logger.error( "Can not load resources for " + resourceId, e );
            
            return resourceId.name(); 
        }
    }
    
    /**
     *
     * @param resourceName
     * @return
     */
    public static ImageIcon createOnePixelImageIcon()
   {
        final byte[] gif1px = {
                0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00,
                0x01, 0x00, (byte)0x80, 0x00, 0x00, (byte)0xF8, (byte)0xFC, (byte)0xFF,
                0x00, 0x00, 0x00, 0x2C, 0x00, 0x00, 0x00, 0x00,
                0x01, 0x00, 0x01, 0x00, 0x00, 0x02, 0x02, 0x44,
                0x01, 0x00, 0x3B
                };

        return new ImageIcon( gif1px );
   }
}
