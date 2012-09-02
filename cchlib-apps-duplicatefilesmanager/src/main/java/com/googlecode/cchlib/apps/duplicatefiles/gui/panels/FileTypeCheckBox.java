package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JCheckBox;

/**
 * TODOC
 */
public class FileTypeCheckBox implements Serializable
{
    private static final long serialVersionUID = 1L;
    private JCheckBox jCB;
    private String description;
    private String data;

    /**
     * TODOC
     * @param description TODOC
     * @param data TODOC
     */
    public FileTypeCheckBox(
            final String description,
            final String data
            )
    {
        this.description = description;
        this.data = data;
    }

    /**
     * TODOC
     * @param description TODOC
     * @param data TODOC
     * @param listener TODOC
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
     * TODOC
     * @return TODOC
     */
    public JCheckBox getJCheckBox()
    {
        if( jCB == null ) {
            jCB = new JCheckBox( getDescription() );
            }
        return jCB;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public final String getDescription()
    {
        return description;
    }

    /**
     * TODOC
     * @return TODOC
     */
    public final String getData()
    {
        return data;
    }
}
