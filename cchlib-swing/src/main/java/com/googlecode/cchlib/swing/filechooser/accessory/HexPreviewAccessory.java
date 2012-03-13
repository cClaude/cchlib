package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import com.googlecode.cchlib.swing.hexeditor.JHexEditor;

/**
 *  Hex/ASCII Preview
 */
public class HexPreviewAccessory
    extends JPanel
        implements  PropertyChangeListener,
                    TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;
    private JHexEditor hexEditor = null;
    private File file = null;
    private int width;

    /**
     * TODOC
     * @param fc
     */
    public HexPreviewAccessory( JFileChooser fc )
    {
        this.width = 200;
        setPreferredSize(new Dimension(width, 100));
        fc.addPropertyChangeListener(this);
        //widthPicture = width - 10;
    }

    @Override // PropertyChangeListener
    public void propertyChange(PropertyChangeEvent e)
    {
        boolean update = false;
        String prop = e.getPropertyName();

        if( JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals( prop ) ) {
            //If the directory changed,
            //remove previous editor.
            file   = null;
            update = true;
            }
        else if( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals( prop ) ) {
            //If a file became selected,
            //find out which one.
            file = (File) e.getNewValue();
            update = true;
            }

        //Update the preview accordingly.
        if( update ) {
            if( this.hexEditor != null ) {
                super.remove( hexEditor );
                }
            if( file != null ) {
                try {
                    this.hexEditor = new JHexEditor( file );
                }
                catch( FileNotFoundException e1 ) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                }
            else {
                this.hexEditor = null;
                }

            if( isShowing() ) {
//                loadImage();
//                repaint();
            }
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
                getClass().getResource( "hexeditor.png" )
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
        // empty
    }

    @Override // TabbedAccessoryInterface
    public void unregister()
    {
        // empty
    }

}
