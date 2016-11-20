package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * Find controls panel displays default action components for starting
 * and stopping a search. Also displays the search progress in the form of
 * a text display indicating the number of items found and the total number
 * of items encountered in the search.
 */
class FindControlsJPanel extends JPanel
{
    private final FindAccessoryImpl findAccessoryImpl;
    private static final long serialVersionUID = 1L;
    protected JLabel        searchDirectory = null;
    protected JLabel        progress = null;

    /**
     * Construct a simple search control panel with buttons for
     * starting and stopping a search and a simple display for
     * search progress.
     */
    FindControlsJPanel(
            FindAccessoryImpl findAccessoryImpl, ActionContener actionContener,
            FindAction find,
            FindAction stop,
            boolean recurse
            )
    {
        super();
        this.findAccessoryImpl = findAccessoryImpl;
        setLayout(new BorderLayout());

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        tools.add(this.findAccessoryImpl.actionStart = new FindAction(actionContener, FindAccessoryImpl.ACTION_START,null));
        tools.add(this.findAccessoryImpl.actionStop = new FindAction(actionContener, FindAccessoryImpl.ACTION_STOP,null));
        add(tools,BorderLayout.WEST);

        progress = new JLabel( StringHelper.EMPTY, SwingConstants.RIGHT );

        // So that frequent updates will appear smooth
        progress.setDoubleBuffered(true);

        progress.setForeground(Color.black);
        progress.setFont(new Font("Helvetica",Font.PLAIN,9));
        add(progress,BorderLayout.EAST);
    }

    /**
     * Display search progress as a text field
     * "no. of matches / total searched".
     *
     * @param matches number of items found
     * @param total number of items investigated
     */
    public void showProgress (int matches, int total)
    {
        if( progress == null ) {
            return;
            }
        progress.setText(String.valueOf(matches)+'/'+String.valueOf(total));
    }
}
