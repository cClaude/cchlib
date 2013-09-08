package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;

/**
 * Contains a collection of search options displayed as tabbed panes and
 * at least one pane for displaying the search results. Each options tab
 * pane is a user interface for specifying the search criteria and a
 * factory for a FindFilter to implement the acceptance function. By making
 * the search option pane responsible for generating a FindFilter object,
 * the programmer can easily extend the search capabilities without
 * modifying the controlling search engine.
 */
class FindJTabbedPane extends JTabbedPane
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FindJTabbedPane.class );
    
    protected String            TAB_NAME = "Name";
    protected String            TAB_DATE = "Date";
    protected String            TAB_CONTENT = "Content";
    protected String            TAB_RESULTS = "Found";

    protected FindResultsJPanel   resultsPanel = null;
    protected JScrollPane   resultsScroller = null;


    /**
     * Construct a search tabbed pane with tab panels for search by
     * filename, search by date, search by content and search results.
     * */
    FindJTabbedPane( JFileChooserSelector jFileChooserSelector )
    {
        super();

        setForeground(Color.black);
        setFont(new Font("Helvetica",Font.BOLD,10));

        // Add search-by-name panel
        addTab(TAB_NAME,new FindByNameJPanel());

        // Add search-by-date panel
        addTab(TAB_DATE,new FindByDateJPanel());

        // Add search-by-content panel
        addTab(TAB_CONTENT,new FindByContentJPanel());

        // Add results panel
        resultsScroller = new JScrollPane(resultsPanel = new FindResultsJPanel(jFileChooserSelector));

        // so that updates will be smooth
        resultsPanel.setDoubleBuffered(true);
        resultsScroller.setDoubleBuffered(true);

        addTab(TAB_RESULTS,resultsScroller);
    }

    /**
        Adds the specified file to the results list.

        @param f file to add to results list
    */
    public void addFoundFile (File f)
    {
        if (resultsPanel != null) {
            resultsPanel.append(f);
        }
    }

    /**
        Bring the search results tab panel to the front.
    */
    public void showFindResults ()
    {
        if (resultsScroller != null) {
            setSelectedComponent(resultsScroller);
        }
    }

    /**
        Prepares the panel for a new search by clearing the results list,
        bringing the results tab panel to the front and generating an
        array of search filters for each search options pane that
        implements the FindFilterFactory interface.

        @return array of FindFilters to be used by the controlling
        search engine
    */
    public FindFilter[] newFind ()
    {
        // Clear the results display
        if (resultsPanel != null) {
            resultsPanel.clear();
        }

        // Fix the width of the scrolling results panel so the layout
        // managers don't try to make it too wide for JFileChooser
        Dimension dim = resultsScroller.getSize();
        resultsScroller.setMaximumSize(dim);
        resultsScroller.setPreferredSize(dim);

        // Return an array of FindFilters
        Vector<FindFilter> filters = new Vector<FindFilter>();
        for (int i=0; i<getTabCount(); i++) {
            try {
                FindFilterFactory fac = (FindFilterFactory)getComponentAt(i);
                FindFilter f = fac.createFindFilter();
                if (f != null) {
                    filters.addElement(f);
                }
            }
            catch (Throwable e) {
                // The FindResults pane does not implement FindFilterFactory
                logger.warn( "The FindResults pane does not implement FindFilterFactory", e );
            }
        }
        if( filters.isEmpty() ) {
            return null;
        }
        FindFilter[] filterArray = new FindFilter[filters.size()];
        for (int i=0; i<filterArray.length; i++) {
            filterArray[i] = filters.elementAt(i);
        }
        return filterArray;
    }
}
