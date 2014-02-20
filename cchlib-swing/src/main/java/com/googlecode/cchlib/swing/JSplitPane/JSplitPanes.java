package com.googlecode.cchlib.swing.JSplitPane;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.JSplitPane;

/**
 * Tools for {@link JSplitPane}
 */
public final class JSplitPanes
{
    private JSplitPanes() {}

    /**
     * Set JSplitPane proportional divider location
     *
     * @param jsplitpane JSplitPane to set
     * @param proportionalLocation double [0.0; 1.0]
     */
    public static void setJSplitPaneDividerLocation(
        final JSplitPane jsplitpane,
        final double     proportionalLocation
        )
    {
        if (jsplitpane.isShowing())
        {
            if (jsplitpane.getWidth() > 0 && jsplitpane.getHeight() > 0)
            {
                jsplitpane.setDividerLocation(proportionalLocation);
            }
            else
            {
                jsplitpane.addComponentListener(new ComponentAdapter()
                {
                    @Override
                    public void componentResized( final ComponentEvent event )
                    {
                        jsplitpane.removeComponentListener( this );
                        setJSplitPaneDividerLocation(jsplitpane, proportionalLocation);
                    }
                });
            }
        }
        else
        {
            jsplitpane.addHierarchyListener(new HierarchyListener()
            {
                @Override
                public void hierarchyChanged(final HierarchyEvent event)
                {
                    if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && jsplitpane.isShowing())
                    {
                        jsplitpane.removeHierarchyListener( this );
                        setJSplitPaneDividerLocation(jsplitpane, proportionalLocation);
                    }
                }
            });
        }
    }

}
