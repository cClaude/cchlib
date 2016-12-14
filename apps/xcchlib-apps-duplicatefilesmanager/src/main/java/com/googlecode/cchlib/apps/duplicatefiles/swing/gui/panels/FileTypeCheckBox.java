package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels;

import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JCheckBox;

/**
 * Create a {@link JCheckBox} for file types
 */
public class FileTypeCheckBox implements Serializable
{
    private static final long serialVersionUID = 1L;
    private JCheckBox jCB;
    private String description;
    private String data;

    /**
     * @param description Text description for check box (label of check box)
     * @param data        Custom data for check box (comma separate string with extension)
     */
    public FileTypeCheckBox(
            final String description,
            final String data
            )
    {
        this.description = description;
        this.data        = data;
    }

    /**
     * @param description Text description for check box (label of check box)
     * @param data        Custom data for check box (comma separate string with extension)
     * @param listener    Custom {@link ActionListener}
     */
    public FileTypeCheckBox(
            final String            description,
            final String            data,
            final ActionListener    listener
            )
    {
        this( description, data );

        getJCheckBox().addActionListener( listener );
    }

    /**
     * @return the {@link JCheckBox} for this object
     */
    public JCheckBox getJCheckBox()
    {
        if( jCB == null ) {
            jCB = new JCheckBox( getDescription() );
            }
        return jCB;
    }

    public final String getDescription()
    {
        return description;
    }

    public final String getData()
    {
        return data;
    }
}
