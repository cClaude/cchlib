package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger( HexPreviewAccessory.class );
    private static final EmptyArray emptyArray = new EmptyArray();

    /**
     * Create a new HexPreviewAccessory
     * @param fc {@link JFileChooser} for this accessory
     */
    public HexPreviewAccessory( JFileChooser fc )
    {
        super( new DefaultHexEditorModel() );

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
                    LOGGER.error( "FileNotFound", e );
                    }
                }
            else {
                model.setArrayAccess( emptyArray );
                }
            update = true;
            }

        //Update the preview accordingly.
        if( update ) {
            if( isShowing() ) {
                // nothing in this case
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
