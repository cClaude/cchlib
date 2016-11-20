package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Accessory container based on a {@link JTabbedPane} able
 * to support many accessory.
 *
 * @see javax.swing.JFileChooser#setAccessory(javax.swing.JComponent)
 */
public class TabbedAccessory
    extends JTabbedPane
        implements ChangeListener
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final ArrayList<TabbedAccessoryInterface> tabbedAccessoryInterfaceList;
    /** @serial */
    private boolean first = true;

    /**
     * Build Accessory with default Dimension (320,240)
     */
    public TabbedAccessory()
    {
        this( new Dimension(320, 240) );
    }

    /**
     * Build Accessory with giving Dimension
     *
     * @param dimension Preferred size for this accessory
     */
    public TabbedAccessory(
        final Dimension dimension
        )
    {
        this.tabbedAccessoryInterfaceList = new ArrayList<>();

        setPreferredSize(dimension);
        addChangeListener( this );
    }

    /**
     * Add a tab
     *
     * @param tai Accessory to add
     * @return current Object for chaining initialization.
     */
    public TabbedAccessory addTabbedAccessory(
        final TabbedAccessoryInterface tai
        )
    {
        super.addTab(
            tai.getTabName(),
            tai.getTabIcon(),
            tai.getComponent()
            );

        this.tabbedAccessoryInterfaceList.add( tai );

        tai.unregister();

        if( this.first ) {
            this.first = false;
            tai.register();
            }

        return this;
    }

    @Override // ChangeListener
    public void stateChanged( final ChangeEvent event )
    {
        final int         sel = getSelectedIndex();
        final Component   c   = super.getComponentAt( sel );

        for(final TabbedAccessoryInterface tai:this.tabbedAccessoryInterfaceList) {
            if( c == tai.getComponent() ) {
                tai.register();
                }
            else {
                tai.unregister();
                }
            }
    }
}

