package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.Serializable;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 */
final class ResourcesUtils implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Class<?> clazz;

    /**
     * Build a ResourcesUtils according to clazz ClassPath
     *
     * @param clazz
     */
    public ResourcesUtils( final Class<?> clazz )
    {
        this.clazz = clazz;
    }

    /**
     * Unified {@link Class#getResource(String)}, that crash if resource not found
     *
     * @param resourceName resource to find
     * @return an URL for this resource, never null
     * @throws ResourcesUtilsException if resource is not found
     */
    public URL getResource( final String resourceName )
        throws ResourcesUtilsException
    {
        URL url = clazz.getResource( resourceName );

        if( url != null ) {
            return url;
            }
        else {
            throw new ResourcesUtilsException( "Can not find : [" + resourceName + "]" );
            }
    }

    /**
     *
     * @param resourceName
     * @return
     */
    public ImageIcon getImageIcon( final String resourceName )
    {
        try {
            return new ImageIcon(
                getResource( resourceName )
                );
            }
        catch( ResourcesUtilsException e ) {
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

    /**
     *
     * @param resourceName
     * @return
     */
    public JButton getJButton( final String resourceName )
    {
        try {
            return new JButton(
                    new ImageIcon(
                            getResource( resourceName )
                            )
                    );
            }
        catch( ResourcesUtilsException e ) {
            // Try to show something when resource is missing
            return new JButton( resourceName );
            }
    }

}
