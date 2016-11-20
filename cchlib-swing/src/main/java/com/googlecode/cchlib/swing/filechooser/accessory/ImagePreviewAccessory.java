package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

/**
 * {@link JFileChooser} accessory able to show a preview
 * of your pictures.
 * 
 * @see javax.swing.JFileChooser#setAccessory(javax.swing.JComponent)
 * @see TabbedAccessory
 */
public class ImagePreviewAccessory
    extends JComponent
        implements TabbedAccessoryInterface
{
    private static final long serialVersionUID = 2L;
    private static ResourcesUtils resourcesUtils = new ResourcesUtils();
    private ImagePreviewComponent image;

    /**
     * Create a ImagePreviewAccessory for giving JFileChooser
     * 
     * @param jfc JFileChooser to use
     */
    public ImagePreviewAccessory( final JFileChooser jfc )
    {
        final int width = 200;
        
        Dimension dimension = new Dimension( width, 100 );
        setPreferredSize( dimension );
        setLayout(new BorderLayout(0, 0));
        
        JPanel jPanel_image = new JPanel();
        jPanel_image.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        add( jPanel_image );
        jPanel_image.setLayout(new BorderLayout(0, 0));
        
        // Needed for Windows Builder image = new JPanel();
        image = new ImagePreviewComponent( dimension );
        jPanel_image.add( image );
        
        jfc.addPropertyChangeListener( image );
    }

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null;
    }

    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return resourcesUtils.getImageIcon( ResourcesUtils.ID.IMAGE_ICON );
    }

    @Override // TabbedAccessoryInterface
    public Component getComponent()
    {
        return this;
    }

    @Override // TabbedAccessoryInterface
    public void register()
    {
        // empty
    }

    @Override // TabbedAccessoryInterface
    public void unregister()
    {
        // empty
    }
}
