package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 *  ImagePreview
 */
//NOT public 
class ImagePreviewComponent
    extends JComponent
        implements PropertyChangeListener
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private ImageIcon thumbnail = null;
    /** @serial */
    private File file = null;

    public ImagePreviewComponent( final Dimension dimension )
    {
        setPreferredSize( dimension );
    }

    private int getWidthPicture()
    {
        return getSize().width - 20;
    }
    
    private void loadImage()
    {
        if( file == null ) {
            thumbnail = null;
            return;
            }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        final ImageIcon tmpIcon = new ImageIcon( file.getPath() );

        if( tmpIcon != null ) {
            if( tmpIcon.getIconWidth() > getWidthPicture() ) {
                thumbnail = new ImageIcon(
                    tmpIcon.getImage()
                        .getScaledInstance(
                            getWidthPicture(),
                            -1,
                            Image.SCALE_DEFAULT
                            )
                    );
                } 
            else { //no need to miniaturize
                thumbnail = tmpIcon;
                }
            }
    }

    @Override // PropertyChangeListener
    public void propertyChange( final PropertyChangeEvent event )
    {
        boolean update = false;
        String  prop   = event.getPropertyName();

        if( JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals( prop ) ) {
            //If the directory changed, don't show an image.
            this.file = null;
            update    = true;
            }
        else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            //If a file became selected, find out which one.
            this.file = File.class.cast( event.getNewValue() );
            update    = true;
            }

        //Update the preview accordingly.
        if( update ) {
            thumbnail = null;

            if( isShowing() ) {
                loadImage();
                repaint();
                }
            }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if( thumbnail == null ) {
            loadImage();
            }
        
        if( thumbnail != null ) {
            int x = getWidth()/2  - thumbnail.getIconWidth() /2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
                }

            if (x < 5) {
                x = 5;
                }
            
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
