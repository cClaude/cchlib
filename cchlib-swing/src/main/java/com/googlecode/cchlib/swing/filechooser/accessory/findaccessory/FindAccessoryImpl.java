package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.io.File;

//TODO: Localization, optimizations (non-recursing)

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
 *
 * @author Ken Klinner, kklinner@opiom.com
 * @author Claude CHOISNET
 */
public class FindAccessoryImpl
    extends JPanel
        implements  Runnable,
                    PropertyChangeListener,
                    ActionListener,
                    FindProgressCallback,
                    ActionContener,
                    JFileChooserSelector
{
    private static final long serialVersionUID = 1L;

    /**
    Label for this accessory.
    */
    static public final String  ACCESSORY_NAME = " Find ";

    /**
    Default max number of found items. Prevents overloading results list.
    */
    static public final int     DEFAULT_MAX_SEARCH_HITS = 500;

    /**
    Find start action name
    */
    static public final String  ACTION_START = "Start";

    /**
    Find stop action name
    */
    static public final String  ACTION_STOP = "Stop";

    /**
    Parent JFileChooser component
    @serial
    */
    protected JFileChooser      chooser = null;

    /** @serial */
    /*protected*/ FindAction        actionStart = null;
    /** @serial */
    /*protected*/ FindAction        actionStop = null;

    /**
     * This version of FindAccesory supports only one active search thread
     * @serial
     */
    protected transient Thread  searchThread = null;

    /**
     * Set to true to stop current search
     * @serial
     */
    protected boolean           killFind = false;

    /**
     * Displays full path of search base
     * @serial
     */
    private/*protected*/ FindFolderJPanel        pathPanel = null;

    /**
     * Find options with results list
     * @serial
     */
    private/*protected*/ FindJTabbedPane      searchTabs = null;

    /**
     * Find controls with progress display
     * @serial
     */
    private/*protected*/ FindControlsJPanel  controlPanel = null;

    /**
     * Number of items inspected by current/last search
     * @serial
     */
    protected int total = 0;

    /**
     * Number of items found by current/last search
     * @serial
     */
    protected int matches = 0;

    /**
     * Max number of found items to prevent overloading the results list.
     * @serial
     */
    protected int maxMatches = DEFAULT_MAX_SEARCH_HITS;

    /**
     * Construct a search panel with start and stop actions, option
     * panes and a results list pane that can display up to
     * DEFAULT_MAX_SEARCH_HITS items.
     */
    //public TODO: add some extra controls to restore this
    // this constructor to public, since we need parent
    // JFileChooser object before calling ....
    // openDialog()
    private FindAccessoryImpl()
    {
        super();

        setBorder(new TitledBorder(ACCESSORY_NAME));
        setLayout(new BorderLayout());

        actionStart = new FindAction(this, ACTION_START,null);
        actionStop = new FindAction(this, ACTION_STOP,null);

        add(pathPanel = new FindFolderJPanel(),BorderLayout.NORTH);
        add(searchTabs = new FindJTabbedPane( this ),BorderLayout.CENTER);
        add(controlPanel = new FindControlsJPanel(this, this, actionStart,actionStop,true),
                            BorderLayout.SOUTH);

        updateFindDirectory();
    }

    /**
     * Construct a search panel with start and stop actions and "attach" it
     * to the specified JFileChooser component. Calls register() to
     * establish FindAccessory as a PropertyChangeListener of JFileChooser.
     *
     * @param parent JFileChooser containing this accessory
     */
    public FindAccessoryImpl( JFileChooser parent )
    {
        this();

        chooser = parent;
        register(chooser);
    }

    /**
     * Construct a search panel with start and stop actions and "attach" it
     * to the specified JFileChooser component. Calls register() to establish
     * FindAccessory as a PropertyChangeListener of JFileChooser. Sets
     * maximum number of found items to limit the load in the results
     * list.
     *
     * @param parent JFileChooser containing this accessory
     * @param max Max number of items for results list. Find stops when max
     *          number of items found.
     */
    public FindAccessoryImpl( JFileChooser parent, int max )
    {
        this(parent);

        setMaxFindHits(max);
    }

    /**
     * Sets maximum capacity of the results list.
     * Find stops when max number of items found.
     *
     * @param max Max capacity of results list.
     */
    public void setMaxFindHits( int max )
    {
        maxMatches = max;
    }

    /**
     * Returns maximum capacity of results list.
     *
     * @return Max capacity of results list.
     */
    public int getMaxFindHits()
    {
        return maxMatches;
    }

    /**
     * Called by JFileChooser when a property changes. FindAccessory
     * listens for DIRECTORY_CHANGED_PROPERTY and updates the path
     * component to display the full path of the current JFileChooser
     * directory. When a search is in progress the path component
     * is <b>not</b> updated - the path component will display the
     * starting point of the current search.
     *
     * @param e PropertyChangeEvent from parent JFileChooser.
     */
    @Override
    public void propertyChange( PropertyChangeEvent e  )
    {
        String prop = e.getPropertyName();
        if (prop.equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY))
        {
            updateFindDirectory();
        }
    }

    /**
     * Called by JFileChooser when the user provokes an action like
     * "cancel" or "open". Listens for APPROVE_SELECTION and
     * CANCEL_SELECTION action and stops the current search, if there
     * is one.
     *
     * @param e ActionEvent from parent JFileChooser.
     */
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand();
        if( command == null ) {
            return; // Can this happen? Probably not. Call me paranoid.
            }
        if( command.equals( JFileChooser.APPROVE_SELECTION ) ) {
            quit();
            }
        else if( command.equals( JFileChooser.CANCEL_SELECTION ) ) {
            quit();
            }
    }


    /**
     * Displays the absolute path to the parent's current directory if
     * and only if there is no active search.
     */
    public void updateFindDirectory()
    {
        if( isRunning() ) {
            return;
            }
        if( chooser == null ) {
            return;
            }
        if( pathPanel == null ) {
            return;
            }
        File f = chooser.getCurrentDirectory();
        pathPanel.setFindDirectory(f);
    }

    /**
     * Set parent's current directory to the parent folder of the
     * specified file and select the specified file. This method is
     * invoked when the user double clicks on an item in the results
     * list.
     *
     * @param f File to select in parent JFileChooser
     */
    @Override//JFileChooserSelector
    public void goTo( File f )
    {
        if( f == null ) {
            return;
            }
        if( !f.exists() ) {
            return;
            }
        if( chooser == null ) {
            return;
            }

        // Make sure that files and directories can be displayed
        chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );

        // Make sure that parent file chooser will show the type of file
        // specified
        javax.swing.filechooser.FileFilter filter = chooser.getFileFilter();
        if (filter != null) {
            if (!filter.accept(f)) {
                // The current filter will not display the specified file.
                // Set the file filter to the built-in accept-all filter (*.*)
                javax.swing.filechooser.FileFilter all =
                    chooser.getAcceptAllFileFilter();
                chooser.setFileFilter(all);
            }
        }

        // Tell parent file chooser to display contents of parentFolder.
        // Prior to Java 1.2.2 setSelectedFile() did not set the current
        // directory the folder containing the file to be selected.
        File parentFolder = f.getParentFile();
        if (parentFolder != null) {
            chooser.setCurrentDirectory(parentFolder);
        }

        // Nullify the current selection, if any.
        // Why is this necessary?
        // Emperical evidence suggests that JFileChooser gets "sticky" (i.e. it
        // does not always relinquish the current selection). Nullifying the
        // current selection seems to yield better results.
        chooser.setSelectedFile( null );

        // Select the file
        chooser.setSelectedFile( f );

        // Refresh file chooser display.
        // Is this really necessary? Testing on a variety of systems with
        // Java 1.2.2 suggests that this helps. Sometimes it doesn't work,
        // but it doesn't do any harm.
        chooser.invalidate();
        chooser.repaint();
    }

    /**
     * Start a search. The path display will show the starting folder
     * of the search.
     * <br/>
     * Finds are recursive and will span the entire folder hierarchy
     * below the base folder. The user may continue to browse with
     * JFileChooser.
     */
    public synchronized void start()
    {
        if( searchTabs != null ) {
            searchTabs.showFindResults();
            }
        updateFindDirectory();
        killFind = false;
        if( searchThread == null ) {
            searchThread = new Thread(this);
            }
        if( searchThread != null ) {
            searchThread.start();
            }
    }

    /**
     * Stop the active search.
     */
    public synchronized void stop()
    {
        killFind = true;
    }

    /**
     * @return true if a search is currently running
     */
    public boolean isRunning()
    {
        if( searchThread == null ) {
            return false;
            }
        return searchThread.isAlive();
    }

    /**
     * Find thread
     */
    @Override
    public void run()
    {
        if (searchThread == null) {
            return;
            }
        if (Thread.currentThread() != searchThread) {
            return;
            }
        try {
            actionStart.setEnabled(false);
            actionStop.setEnabled(true);
            runFind(chooser.getCurrentDirectory(),newFind());
            }
        catch (InterruptedException e) { // $codepro.audit.disable emptyCatchClause
            }
        finally {
            actionStart.setEnabled(true);
            actionStop.setEnabled(false);
            searchThread = null;
            }
    }

    /**
     * Recursive search beginning at folder <b>base</b> for files and
     * folders matching each filter in the <b>filters</b> array. To
     * interrupt set <b>killFind</b> to true. Also stops when number
     * of search hits (matches)equals <b>maxMatches</b>.
     * <P>
     * <b>Note TODO:</b> Convert this to a non-recursive search algorithm
     * on systems where stack space might be limited and/or the search
     * hierarchy might be very deep.
     *
     * @param base starting folder of search
     * @param filters matches must pass each filters in array
     * @exception InterruptedException if thread is interrupted
     */
    private/*protected*/ void runFind( File base, FindFilter[] filters )
                    throws InterruptedException
    {
        if (base == null) {
            return;
            }
        if (!base.exists()) {
            return; // Not likely to happen
            }
        if (filters == null) {
            return;
            }

        if (killFind) {
            return;
            }

        File folder = null;

        if (base.isDirectory()) {
            folder = base;
            }
        else {
            folder = base.getParentFile();
            }

        File[] files = folder.listFiles();

        for (int i=0; i<files.length; i++) {
            total++;

            if (accept(files[i],filters)) {
                matches++;
                searchTabs.addFoundFile(files[i]);
                }
            updateProgress();

            if (killFind) {
                return;
                }
            //Thread.currentThread().sleep(0);
            //Thread.currentThread();
            Thread.sleep(0);

            if (files[i].isDirectory()) {
                runFind(files[i],filters);
                }

            if ((maxMatches > 0) && (matches >= maxMatches)) {
                return;// stopgap measure so that we don't overload
                }
        }
    }

    /**
     * @param file file to pass to each filter's accept method
     * @param filters array of selection criteria
     *
     * @return true if specified file matches each filter's selection criteria
     */
    private/*protected*/ boolean accept( File file, FindFilter[] filters )
    {
        if (file == null) return false;
        if (filters == null) return false;

        for (int i=0; i<filters.length; i++) {
            if( !filters[i].accept(file,this) ) {
                return false;
                }
            }

        return true;
    }

    /**
        Called by FindFilter to report progress of a search. Purely
        a voluntary report. This really should be implemented as a
        property change listener.
        Percentage completion = (current/total)*100.

        @param filter FindFilter reporting progress
        @param file file being searched
        @param current current "location" of search
        @param total expected maximum value of current

        @return true to continue search, false to abort
     */
    @Override
    public boolean reportProgress(
            FindFilter  filter,
            File        file,
            long        current,
            long        total
            )
    {
        return !killFind;
    }

    /**
        Begins a new search by resetting the <b>total</b> and <b>matches</b>
        progress variables and retrieves the search filter array from the
        options panel.
        Each tab in the options panel is responsible for generating a
        FindFilter based on its current settings.

        @return Array of search filters from the options panel.
     */
    private/*protected*/ FindFilter[] newFind()
    {
        total = matches = 0;
        updateProgress();

        if (searchTabs != null) {
            return searchTabs.newFind();
            }
        return null;
    }

    /**
        Display progress of running search.
     */
    protected void updateProgress()
    {
        controlPanel.showProgress(matches,total);
    }


    /**
        Add this component to the specified JFileChooser's list of property
        change listeners and action listeners.

        @param c parent JFileChooser
     */
    protected void register(JFileChooser c)
    {
        if (c == null) {
            return;
            }
        c.addPropertyChangeListener(this);
        c.addActionListener(this);
    }

    /**
        Remove this component from the specified JFileChooser's list of property
        change listeners and action listeners.

        @param c parent JFileChooser
     */
    protected void unregister(JFileChooser c)
    {
        if (c == null) {
            return;
            }
        c.removeActionListener(this);
        c.removePropertyChangeListener(this);
    }

    /**
        Stop the current search and unregister in preparation for parent
        shutdown.
     */
    public void quit()
    {
        stop();
        unregister(chooser);
    }

    /**
        Invoked by FindAction objects to start and stop searches.
     * @param command
     */
    @Override//ActionContener
    public void action(String command)
    {
        if( command == null ) {
            return;
            }
        if( command.equals(ACTION_START) ) {
            start();
            }
        else if( command.equals(ACTION_STOP) ) {
            stop();
            }
    }
}


