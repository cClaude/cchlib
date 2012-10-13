package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.EmptyDirectoryCheckBoxNodeEditor;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.EmptyDirectoryCheckBoxNodeRenderer;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.FileTreeModel;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.FileTreeModelable;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.LasyJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.list.LeftDotListCellRenderer;

/**
 *
 */
public class RemoveEmptyDirectoriesPanel 
    extends RemoveEmptyDirectoriesPanelWB
        implements I18nPrepAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectoriesPanel.class );
    
    private ActionListener actionListener;
    private DFToolKit dfToolKit;
    private Window mainWindow;
    private FileTreeModelable treeModel;
    private FindDeleteAdapter findDeleteAdapter;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;
    
    @I18nString private String jFileChooserInitializerMessage    = "Analyze disk structure";
    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String txtProgressBarComputing = "Computing...";
    @I18nString private String txtSelectDirToScan = "Select directory to scan";
    @I18nString private String txtProgressBarScanCancel = "Scan canceled !";
    @I18nString private String txtProgressBarSelectFileToDelete = "Select file to delete";
    @I18nString private String txtProgressBarDeleteSelectedFiles = "Delete selected files";

    /**
     * 
     */
    public RemoveEmptyDirectoriesPanel(
        final DFToolKit dfToolKit,
        final Window    mainWindow
        )
    {
        super();

        this.dfToolKit  = dfToolKit;
        this.mainWindow = mainWindow;
        
        init();
    }
    
    private void init()
    {
        JProgressBar pBar = super.getProgressBar();
        pBar.setStringPainted( true );
        pBar.setString( txtSelectDirToScan );
        pBar.setIndeterminate( false );

        // Create a JTree and tell it to display our model
        final JTree jTreeDir = super.getJTreeEmptyDirectories();
        treeModel = new FileTreeModel( jTreeDir );
        jTreeDir.setModel( treeModel );
        jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer( treeModel ) );
        jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( treeModel ) );
        jTreeDir.setEditable( true );

        enable_findTaskDone();
        
        final LeftDotListCellRenderer leftListCellRenderer
        = new LeftDotListCellRenderer( super.getJListRootDirectories(), true );
        super.getJListRootDirectories().setCellRenderer( leftListCellRenderer );

        super.getJListRootDirectories().addListSelectionListener(
            new ListSelectionListener()
            {
                @Override
                public void valueChanged( ListSelectionEvent e )
                {
                    final int count = getJListRootDirectories().getSelectedValuesList().size();

                    if( count > 0 ) {
                        setButtonRemoveRootDirectoryEnabled( true );
                        }
                    else {
                        // No selection
                        setButtonRemoveRootDirectoryEnabled( false );
                        }
                }
            });

        // Init Adapter
        FindDeleteListener findDeleteListener = new FindDeleteListener()
        {
            @Override
            public void findTaskDone( final boolean isCancel )
            {
                logger.info( "find thread done" );
                // Bad workaround !!!!
                // TODO: find a better solution to expand tree
                // during build.
                expandAllRows();

                enable_findTaskDone();
                
                JProgressBar pBar = getProgressBar();
                pBar.setIndeterminate( false );

                if( isCancel ) {
                    pBar.setString( txtProgressBarScanCancel );
                    }
                else {
                    pBar.setString( txtProgressBarSelectFileToDelete );
                    }
            }
        };

        findDeleteAdapter = new FindDeleteAdapter(
                getJListRootDirectoriesModel(),
                treeModel,
                findDeleteListener
                );

        // Prepare JFileChooser
        /*frame.*/getWaitingJFileChooserInitializer();
        
        logger.info( "init() done" );
    }
    
    protected DFToolKit getDFToolKit()
    {
        return dfToolKit;
    }

    @Override
    protected void addRootDirectory( final List<File> files )
    {
        DefaultListModel<File> model = super.getJListRootDirectoriesModel();

        for( File f:files ) {
            if( f.isDirectory() ) {
                model.addElement( f );
                logger.info( "add drop dir:" + f );
                }
            else {
                logger.warn( "Ignore drop : " + f );
                }
            }
    }

    private void btnRemoveRootDirectory()
    {
        logger.info( "btnRemoveRootDirectory_mouseClicked" );

        if( super.isButtonRemoveRootDirectoryEnabled() ) {
            JList<File>             rootList        = super.getJListRootDirectories();
            List<File>              selectedList    = rootList.getSelectedValuesList();
            DefaultListModel<File>  model           = super.getJListRootDirectoriesModel();

            for( File f : selectedList ) {
                model.removeElement( f );
                }

            rootList.clearSelection();
            }
    }

    private void btnStartScan()
    {
        if( super.isButtonStartScanEnabled() ) {
            logger.info( "btnStartScan_mouseClicked" );

            findBegin(); // Launch a thread
            }
    }

    private void btnCancel()
    {
        logger.info( "btnCancel_mouseClicked" );
        if( super.getBtnCancel().isEnabled() ) {
            findDeleteAdapter.cancel();
            logger.info( "Cancel!" );
            }
    }

    private void btnSelectAll()
    {
        if( super.getBtnSelectAll().isEnabled() ) {
            logger.info( "btnSelectAll_mouseClicked" );

            treeModel.setSelectAllLeaf( true );
            expandAllRows();
            }
    }

    private void btnUnselectAll()
    {
        if( super.getBtnUnselectAll().isEnabled() ) {
            logger.info( "btnSelectAll_mouseClicked" );

            treeModel.setSelectAllLeaf( false );
            expandAllRows();
            }
    }

    private void onStartDelete()
    {
        logger.info( "onStartDelete()" );

        if( super.getBtnStartDelete().isEnabled() ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    startDelete();
                }
            };
            new Thread( r, "onStartDelete()" ).start();
            // NOTE: do not use of SwingUtilities.invokeLater( r );
        }
    }
    @Override
    protected ActionListener getActionListener()
    {
        if( actionListener == null ) {
            actionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( final ActionEvent event )
                {
                    final String cmd = event.getActionCommand();
                            
                    if( ACTION_IMPORT_DIRS.equals( cmd ) ) {
                    	onImportDirectories();
                        }
                    else if( ACTION_ADD_DIRS.equals( cmd ) ) {
                    	onAddRootDirectory();
                        }
                    else if( ACTION_REMOVE_DIR.equals( cmd ) ) {
                        btnRemoveRootDirectory();
                        }
                    else if( ACTION_FIND_EMPTY_DIRS.equals( cmd ) ) {
                        btnStartScan();
                        }
                    else if( ACTION_CANCEL.equals( cmd ) ) {
                        btnCancel();
                        }
                    else if( ACTION_SELECT_ALL.equals( cmd ) ) {
                        btnSelectAll();
                        }
                    else if( ACTION_UNSELECT_ALL.equals( cmd ) ) {
                        btnUnselectAll();
                        }
                    else if( ACTION_START_REMDIRS.equals( cmd ) ) {
                    	onStartDelete();
                        }
                    else {
                        logger.warn( "Action not handled : " + cmd );
                        }
                }
            };
        }
        return actionListener;
    }
    
    private void onAddRootDirectory()
    {
        logger.info( "btnAddRootDirectory()" );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    addRootDirectory();
                }
            };
           new Thread( r, "onAddRootDirectory()" ).start();
           logger.info( "btnAddRootDirectory() done" );
        }
    }

    private void onImportDirectories()
    {
        logger.info( "btnImportDirectories()" );
        
        if( super.isButtonImportDirectoriesEnabled() ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    List<File> dirs = getDFToolKit().getRootDirectoriesList();
                    addRootDirectory( dirs );
                    logger.info( "btnImportDirectories() done" );
                }
            };
            new Thread( r, "onImportDirectories()" ).start();
            }
    }
    
    private void addRootDirectory()
    {
        logger.info( "addRootDirectory()" );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            JFileChooser jfc = getJFileChooser();

            logger.info( "getJFileChooser() done" );

            jfc.setMultiSelectionEnabled( true );
            int returnVal = jfc.showOpenDialog( this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                DefaultListModel<File>     model = super.getJListRootDirectoriesModel();
                File[]                     files = jfc.getSelectedFiles();

                //logger.info( "model:" + model );
                //logger.info( "model.getClass():" + model.getClass() );

                for( File f:files ) {
                    //model.
                    model.addElement( f );
                    logger.info( "selected dir:" + f );
                    }
                }
            logger.info( "addRootDirectory() done" );
        }
    }

    private WaitingJFileChooserInitializer getWaitingJFileChooserInitializer()
    {
        if( waitingJFileChooserInitializer == null ) {
            JFileChooserInitializerCustomize configurator
                //= WaitingJFileChooserInitializer.getDefaultConfigurator();
                = new LasyJFCCustomizer()
                    .setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

            waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    mainWindow,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );

            }
        return waitingJFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        JFileChooser jfc = getWaitingJFileChooserInitializer().getJFileChooser();
         
        return jfc;
    }
    
    private void findBegin()
    {
        logger.info( "find thread started" );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( txtProgressBarComputing );
        pBar.setIndeterminate( true );

        enable_findBegin();
        
        Runnable doRun = new Runnable()
        {
            @Override
            public void run()
            {
                findDeleteAdapter.doFind();
            }
        };

        // KO Lock UI doRun.run();
        // KO Lock UI SwingUtilities.invokeAndWait( doRun );
        // KO Lock UI SwingUtilities.invokeLater( doRun );
        new Thread( doRun, "findBegin()" ).start();
    }
    
    protected void expandAllRows()
    {
        try {
            final JTree jTreeDir = getJTreeEmptyDirectories();

            //Expend all nodes
            for (int i = 0; i < jTreeDir.getRowCount(); i++) {
                jTreeDir.expandRow(i);
                }
            }
        catch( Exception e ) {
            logger.error( "expandAllRows()", e );
            }
    }
    
    private void startDelete()
    {
        logger.info( "delete thread started" );

        enable_startDelete();
        
        getJTreeEmptyDirectories().setEditable( false );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( txtProgressBarDeleteSelectedFiles );
        pBar.setIndeterminate( true );

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    findDeleteAdapter.doDelete();
                    }
                catch( Exception e ) {
                    logger.warn( "doDelete()", e );
                    }
                finally {
                    getBtnStartDelete().setEnabled( true );

                    getBtnCancel().setEnabled( false );
                    getJTreeEmptyDirectories().setEditable( true );
                    pBar.setIndeterminate( false );

                    findBegin();
                    }

                logger.info( "delete thread done" );
            }
        }, "startDelete()").start();
    }

    @Override
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public String getMessagesBundle()
    {
        return getDFToolKit().getMessagesBundle();
    }
    
}
