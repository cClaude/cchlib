/**
 *
 */
package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *  Convenience class for adding action objects to the control panel.
*/
class FindAction extends AbstractAction
{
    private static final long serialVersionUID = 1L;
    private ActionContener actionContener;

    /**
     * Construct a search control action currently implements
     * FindAccesory.ACTION_START and FindAccessory.ACTION_STOP.
     *
     * @param text command
     * @param icon button icon
     */
    FindAction( ActionContener actionContener, String text, Icon icon)
    {
        super(text,icon);

        this.actionContener = actionContener;
    }

    /**
     * Invoke FindAction's action() method.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        this.actionContener.action( e.getActionCommand() );
    }
}
