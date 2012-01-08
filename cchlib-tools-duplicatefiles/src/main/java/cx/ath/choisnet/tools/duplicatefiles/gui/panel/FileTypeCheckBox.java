package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JCheckBox;

/**
 *
 * @author Claude
 *
 */
public class FileTypeCheckBox implements Serializable
{
    private static final long serialVersionUID = 1L;
    private JCheckBox jCB;
    private String description;
    private String data;

    /**
     *
     * @param description
     * @param data
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
     *
     * @param description
     * @param data
     * @param listener
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
     *
     * @return
     */
    public JCheckBox getJCheckBox()
    {
        if( jCB == null ) {
            jCB = new JCheckBox( getDescription() );
            }
        return jCB;
    }

    /**
     *
     * @return
     */
    public final String getDescription()
    {
        return description;
    }

    /**
     *
     * @return
     */
    public final String getData()
    {
        return data;
    }
}
