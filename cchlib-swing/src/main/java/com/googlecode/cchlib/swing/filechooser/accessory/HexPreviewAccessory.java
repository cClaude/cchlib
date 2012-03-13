package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.swing.hexeditor.ArrayReadAccessFile;
import com.googlecode.cchlib.swing.hexeditor.EmptyArray;
import com.googlecode.cchlib.swing.hexeditor.DefaultHexEditorModel;
import com.googlecode.cchlib.swing.hexeditor.JHexEditor;

/**
 *  Hex/ASCII Preview
 */
public class HexPreviewAccessory
    extends JHexEditor
        implements  PropertyChangeListener,
                    TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;
    //private JHexEditor hexEditor = null;
    //private File file = null;
    //private int width;
    private final static EmptyArray emptyArray = new EmptyArray();

    /**
     * TODOC
     * @param fc
     * @param new HexEditorModel()
     */
    public HexPreviewAccessory( JFileChooser fc )
    {
        super( new DefaultHexEditorModel() );

        //this.width = 200;
        //setPreferredSize(new Dimension(width, 100));
        setPreferredSize(getMinimumSize() );
        fc.addPropertyChangeListener(this);
    }

    @Override // PropertyChangeListener
    public void propertyChange(PropertyChangeEvent event )
    {
        boolean update = false;
        String prop = event.getPropertyName();

        if( JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals( prop ) ) {
            //If the directory changed,
            //remove previous editor.
            DefaultHexEditorModel model = (DefaultHexEditorModel)getModel();

            model.setArrayAccess( emptyArray );
            //file   = null;
            update = true;
            }
        else if( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals( prop ) ) {
            //If a file became selected,
            //find out which one.
            File file = (File)event.getNewValue();
            DefaultHexEditorModel model = (DefaultHexEditorModel)getModel();

            if( file != null && file.isFile() ) {
                try {
                    model.setArrayAccess( new ArrayReadAccessFile( file ) );
                    }
                catch( FileNotFoundException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                }
            else {
                model.setArrayAccess( emptyArray );
                }
            update = true;
            }

        //Update the preview accordingly.
        if( update ) {
//            if( this.hexEditor != null ) {
//                super.remove( hexEditor );
//                }
//            if( file != null ) {
//                try {
//                    HexEditorModel model = new DefaultHexEditorModel();
//                    this.hexEditor = new JHexEditor( model );
//                    }
//                catch( FileNotFoundException e1 ) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                }
//            else {
//                this.hexEditor = null;
//                }

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
