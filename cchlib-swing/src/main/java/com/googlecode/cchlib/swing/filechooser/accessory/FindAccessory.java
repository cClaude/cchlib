package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.swing.filechooser.accessory.findaccessory.FindAccessoryImpl;

/**
 * A threaded file search accessory for JFileChooser.
 * <P>
 * Presents JFileChooser users with a tabbed panel interface for
 * specifying file search criteria including (1) search by name,
 * (2) search by date of modification, and (3) search by file content.
 * Finds are performed "in the background" with found files displayed
 * dynamically as they are found. Only one search can be active at
 * a time. FindResults are displayed in a scrolling list within a results
 * tab panel.
 * <P>
 * Finds are performed asynchronously so the user can continue
 * browsing the file system. The user may stop the search at any time.
 * Accepting or canceling the file chooser or closing the dialog window
 * will automatically stop a search in progress.
 * <P>
 * The starting folder of the search (the search base) is displayed
 * at the top of the accessory panel. The search base display will
 * not change while a search is running. These search base display
 * will change to reflect the current directory of JFileChooser
 * when a search is not running.
 * <P>
 * Changing the search options does not affect a search in progress.
 */
public class FindAccessory
    extends FindAccessoryImpl
        implements TabbedAccessoryInterface
{
    private static final long serialVersionUID = 1L;

    /**
     * NEEDDOC
     * @param parent
     * @param max
     */
    public FindAccessory(final JFileChooser parent, final int max)
    {
        super( parent, max );
    }

    /**
     * NEEDDOC
     * @param parent
     */
    public FindAccessory(final JFileChooser parent)
    {
        super( parent );
    }

    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null; //"Find"; // TODO Localization
    }

    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return new ImageIcon(
                getClass().getResource( "search.png" )
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
    }

    @Override //TabbedAccessoryInterface
    public void unregister()
    {
        stop();
    }
}
