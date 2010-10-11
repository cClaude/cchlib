package cx.ath.choisnet.swing.filechooser.accessory;

import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;

/**
 *  ImagePreview
 */
public class ImagePreviewAccessory
    extends JComponent
        implements  PropertyChangeListener,
                    TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private ImageIcon thumbnail = null;
    /** @serial */
    private File file = null;
    /** @serial */
    private int width;
    /** @serial */
    private int widthPicture;

    public ImagePreviewAccessory(JFileChooser fc)
    {
        width = 200;
        setPreferredSize(new Dimension(width, 100));
        fc.addPropertyChangeListener(this);
        widthPicture = width - 10;
    }

    public void loadImage()
    {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());

        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > widthPicture) {
                thumbnail = new ImageIcon(
                        tmpIcon.getImage()
                            .getScaledInstance(
                                    widthPicture,
                                    -1,
                                    Image.SCALE_DEFAULT
                                    )
                            );
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }

    @Override // PropertyChangeListener
    public void propertyChange(PropertyChangeEvent e)
    {
        boolean update = false;
        String prop = e.getPropertyName();

        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            //If the directory changed, don't show an image.
            file = null;
            update = true;
        }
        else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            //If a file became selected, find out which one.
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;

            if( isShowing() ) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g)
    {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
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

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null;
    }

    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return new ImageIcon(
                getClass().getResource( "picture.png" )
                );
    }

    @Override // TabbedAccessoryInterface
    public Component getComponent()
    {
        return this;
    }

    @Override // TabbedAccessoryInterface
    public void register()
    {
        // TODO Auto-generated method stub
    }

    @Override // TabbedAccessoryInterface
    public void unregister()
    {
        // TODO Auto-generated method stub
    }

}
