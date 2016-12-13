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
        final FindAccessoryImpl findAccessoryImpl,
        final ActionContener    actionContener,
        final FindAction        find,
        final FindAction        stop,
        final boolean           recurse
        )
    {
        this.findAccessoryImpl = findAccessoryImpl;

        setLayout(new BorderLayout());

        final JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        this.findAccessoryImpl.actionStart = new FindAction( actionContener, FindAccessoryImpl.ACTION_START, null );
        this.findAccessoryImpl.actionStop  = new FindAction( actionContener, FindAccessoryImpl.ACTION_STOP , null );

        tools.add( this.findAccessoryImpl.actionStart );
        tools.add( this.findAccessoryImpl.actionStop  );

        add(tools,BorderLayout.WEST);

        this.progress = new JLabel( StringHelper.EMPTY, SwingConstants.RIGHT );

        // So that frequent updates will appear smooth
        this.progress.setDoubleBuffered(true);
        this.progress.setForeground(Color.black);
        this.progress.setFont(new Font("Helvetica",Font.PLAIN,9));
        add(this.progress,BorderLayout.EAST);
    }

    /**
     * Display search progress as a text field
     * "no. of matches / total searched".
     *
     * @param matches number of items found
     * @param total number of items investigated
     */
    public void showProgress (final int matches, final int total)
    {
        if( this.progress == null ) {
            return;
            }
        this.progress.setText(String.valueOf(matches)+'/'+String.valueOf(total));
    }
}
