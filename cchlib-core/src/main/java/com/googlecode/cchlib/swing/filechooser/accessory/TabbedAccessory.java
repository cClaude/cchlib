/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public class TabbedAccessory
    extends JTabbedPane
        implements ChangeListener
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private ArrayList<TabbedAccessoryInterface> tabbedAccessoryInterfaceList;
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
     * @param dimension
     */
    public TabbedAccessory(
        final Dimension dimension
        )
    {
        tabbedAccessoryInterfaceList = new ArrayList<TabbedAccessoryInterface>();

        setPreferredSize(dimension);
        addChangeListener( this );
    }

    /**
     * TODOC
     * @param tai
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

        tabbedAccessoryInterfaceList.add( tai );

        tai.unregister();

        if( first ) {
            first = false;
            tai.register();
            }

        return this;
    }

    @Override // ChangeListener
    public void stateChanged( ChangeEvent event )
    {
        int         sel = getSelectedIndex();
        Component   c   = super.getComponentAt( sel );

        for(TabbedAccessoryInterface tai:tabbedAccessoryInterfaceList) {
            if( c == tai.getComponent() ) {
                tai.register();
                }
            else {
                tai.unregister();
                }
            }
    }
}
