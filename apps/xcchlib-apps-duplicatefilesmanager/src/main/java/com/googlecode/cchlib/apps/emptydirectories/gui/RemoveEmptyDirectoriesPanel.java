package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.EmptyDirectoryTreeCellRenderer;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.FolderTreeCellEditor;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModel1;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable1;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeNode;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.filechooser.FileSelectionMode;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.LasyJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.list.LeftDotListCellRenderer;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;

@I18nName("RemoveEmptyDirectoriesPanel")
@SuppressWarnings({"squid:MaximumInheritanceDepth"})
public class RemoveEmptyDirectoriesPanel
    extends RemoveEmptyDirectoriesPanelWB
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( RemoveEmptyDirectoriesPanel.class );

    private transient ActionListener    actionListener;
    private transient FindDeleteAdapter findDeleteAdapter;

    private final AppToolKit dfToolKit;
    private final Window mainWindow;

    private FolderTreeModelable1 treeModel;
    //private FolderTreeModelable2 treeModel;

    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;

    @I18nString private String jFileChooserInitializerMessage;
    @I18nString private String jFileChooserInitializerTitle;
    @I18nString private String txtProgressBarComputing;
    @I18nString private String txtSelectDirToScan;
    @I18nString private String txtProgressBarScanCancel;
    @I18nString private String txtProgressBarSelectFileToDelete;
    @I18nString private String txtProgressBarDeleteSelectedFiles;

    public RemoveEmptyDirectoriesPanel(
        final Window mainWindow
        )
    {
        super( AppToolKitService.getInstance().getAppToolKit().getResources() );

        beSurNonFinal();

        this.dfToolKit  = AppToolKitService.getInstance().getAppToolKit();
        this.mainWindow = mainWindow;

        init();
    }

    private void beSurNonFinal()
    {
        this.jFileChooserInitializerMessage    = "Analyze disk structure";
        this.jFileChooserInitializerTitle      = "Waiting...";
        this.txtProgressBarComputing           = "Computing...";
        this.txtSelectDirToScan                = "Select directory to scan";
        this.txtProgressBarScanCancel          = "Scan canceled !";
        this.txtProgressBarSelectFileToDelete  = "Select file to delete";
        this.txtProgressBarDeleteSelectedFiles = "Delete selected files";
    }

    private void init()
    {
        initProgressBar();

        initTreeEmptyDirectories();

        enable_findTaskDone();

        initListRootDirectories();


        // Prepare (in background) JFileChooser (avoid waiting under windows)
        getWaitingJFileChooserInitializer();

        LOGGER.info( "init() done" );
    }

    private FindDeleteAdapter getFindDeleteAdapter()
    {
        if( this.findDeleteAdapter == null ) {
            // Init Adapter
            final FindDeleteListener findDeleteListener = this::findTaskDoneFindDeleteListener;

            this.findDeleteAdapter = newFindDeleteAdapter( findDeleteListener );
        }

        return this.findDeleteAdapter;
    }

    private FindDeleteAdapter newFindDeleteAdapter(
            final FindDeleteListener findDeleteListener
            )
    {
        return new FindDeleteAdapter(
                getJListRootDirectoriesModel(),
                this.treeModel,
                findDeleteListener
                );
    }

    protected void findTaskDoneFindDeleteListener( final boolean isCancel )
    {
        LOGGER.info( "find thread done" );
        // Bad workaround !!!!
        // TODO: find a better solution to expand tree
        // during build.

        expandAllRows();

        enable_findTaskDone();

        final JProgressBar pBar = getProgressBar();

        pBar.setIndeterminate( false );

        if( isCancel ) {
            pBar.setString( this.txtProgressBarScanCancel );
        }
        else {
            pBar.setString( this.txtProgressBarSelectFileToDelete );
        }
    }

    private void expandAllRows()
    {
        this.treeModel.expandAllRows();
    }

    private void initListRootDirectories()
    {
        final LeftDotListCellRenderer leftListCellRenderer = new LeftDotListCellRenderer( super.getJListRootDirectories(), true );
        super.getJListRootDirectories().setCellRenderer( leftListCellRenderer );

        super.getJListRootDirectories().addListSelectionListener(
            (final ListSelectionEvent e) -> {
                final int count = getJListRootDirectories().getSelectedValuesList().size();

                if( count > 0 ) {
                    setButtonRemoveRootDirectoryEnabled( true );
                }
                else {
                    // No selection
                    setButtonRemoveRootDirectoryEnabled( false );
                }
        });
    }

    private void initTreeEmptyDirectories()
    {
        // Create a JTree and tell it to display our model
        final JTree jTreeDir = super.getJTreeEmptyDirectories();
        this.treeModel = new FolderTreeModel1( jTreeDir );
        // this.treeModel = new FolderTreeModel2( jTreeDir );
        jTreeDir.setModel( this.treeModel );

        jTreeDir.addTreeSelectionListener(
            (final TreeSelectionEvent event) -> valueChangedTreeSelectionListener( jTreeDir, event )
            );

        final EmptyDirectoryTreeCellRenderer cellRenderer = new EmptyDirectoryTreeCellRenderer( this.treeModel );

        jTreeDir.setCellRenderer( cellRenderer );
        jTreeDir.setCellEditor( new FolderTreeCellEditor( this.treeModel, cellRenderer ) );
        jTreeDir.setEditable( true );
    }

    private void valueChangedTreeSelectionListener(
        final JTree              jTreeDir,
        @SuppressWarnings("squid:S1172") // could be use
        final TreeSelectionEvent event
        )
    {
        final Object currentSelectedNodeModel = jTreeDir.getLastSelectedPathComponent();

        if( currentSelectedNodeModel instanceof FolderTreeNode ) {
            final FolderTreeNode selectedNode = (FolderTreeNode)currentSelectedNodeModel;

            if( selectedNode.getFolder() instanceof EmptyFolder ) {
                this.treeModel.toggleSelected( selectedNode );
            } // else click on a none empty folder => ignore
        }
    }

    private void initProgressBar()
    {
        final JProgressBar pBar = super.getProgressBar();

        pBar.setStringPainted( true );
        pBar.setString( this.txtSelectDirToScan );
        pBar.setIndeterminate( false );
    }

    protected AppToolKit getDFToolKit()
    {
        return this.dfToolKit;
    }

    private void onRemoveRootDirectory()
    {
        LOGGER.info( "onRemoveRootDirectory()" );

        if( super.isButtonRemoveRootDirectoryEnabled() ) {
            final JList<File>             rootList        = super.getJListRootDirectories();
            final List<File>              selectedList    = rootList.getSelectedValuesList();
            final DefaultListModel<File>  model           = super.getJListRootDirectoriesModel();

            for( final File f : selectedList ) {
                model.removeElement( f );
                }

            rootList.clearSelection();
            setEnableFind( model.size() > 0 );
            }
    }

    private void onFindEmptyDirectories()
    {
        if( super.isButtonStartScanEnabled() ) {
            LOGGER.info( "onFindEmptyDirectories()" );

            findBegin(); // Launch a thread
            }
    }

    private void onCancel()
    {
        LOGGER.info( "onCancel()" );

        if( super.getBtnCancel().isEnabled() ) {
            getFindDeleteAdapter().cancel();
            LOGGER.info( "Cancel!" );
            }
    }

    private void onSelectAll( final boolean onlyLeaf )
    {
        if( super.isBtnSelectAllEnabled() ) {
            LOGGER.info( "onSelectAll() : onlyLeaf=" + onlyLeaf );

            this.treeModel.setSelectAll( onlyLeaf, true );
            LOGGER.info( "onSelectAll() : size=" + this.treeModel.getSelectedEmptyFoldersSize() );

            expandAllRows();
            }
    }

    private void onUnselectAll()
    {
        if( super.isBtnUnselectAllEnabled() ) {
            LOGGER.info( "onUnselectAll()" );

            this.treeModel.setSelectAll( false, false );
            expandAllRows();
            }
    }

    private void onStartDelete()
    {
        LOGGER.info( "onStartDelete()" );

        if( super.getBtnStartDelete().isEnabled() ) {
            // NOTE: do not use of SwingUtilities.invokeLater( task )
            Threads.start( "onStartDelete()", this::startDelete );
        }
    }
    @Override
    protected ActionListener getActionListener()
    {
        if( this.actionListener == null ) {
            this.actionListener = this::actionPerformedActionListener;
        }

        return this.actionListener;
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    private void actionPerformedActionListener( final ActionEvent event )
    {
        final String cmd = event.getActionCommand();

        if( cmd == null ) {
            return;
        }

        switch( cmd ) {
            case ACTION_IMPORT_DIRS:
                onImportDirectories();
                break;
            case ACTION_ADD_DIRS:
                onAddRootDirectory();
                break;
            case ACTION_REMOVE_DIR:
                onRemoveRootDirectory();
                break;
            case ACTION_FIND_EMPTY_DIRS:
                onFindEmptyDirectories();
                break;
            case ACTION_CANCEL:
                onCancel();
                break;
            case ACTION_SELECT_ALL_SELECTABLE_NODES:
                onSelectAll( false );
                break;
            case ACTION_SELECT_ALL_LEAFONLY:
                onSelectAll( true );
                break;
            case ACTION_UNSELECT_ALL:
                onUnselectAll();
                break;
            case ACTION_START_REMDIRS:
                onStartDelete();
                break;
            default:
                LOGGER.warn( "Action not handled : " + cmd );
                break;
        }
    }

    private void onAddRootDirectory()
    {
        LOGGER.info( "btnAddRootDirectory()" );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            final Runnable task = this::addRootDirectory;
           new Thread( task, "onAddRootDirectory()" ).start();
           LOGGER.info( "btnAddRootDirectory() done" );
        }
    }

    @Override
    protected void addRootDirectory( final List<File> files )
    {
        final Runnable task = () -> {
            final DefaultListModel<File> model = super.getJListRootDirectoriesModel();

            for( final File f: files ) {
                if( f.isDirectory() ) {
                    model.addElement( f );
                    LOGGER.info( "add drop dir:" + f );
                    }
                else {
                    LOGGER.warn( "Ignore drop : " + f );
                    }
                }

            setEnableFind( model.size() > 0 );
        };

        new Thread( task, "addRootDirectory" ).start();
    }

    private static <E> List<E> toList( final E[] files )
    {
        final List<E> filesList = new ArrayList<>( files.length );

        for( final E file : files ) {
            filesList.add( file  );

            LOGGER.info( "Add directory: " + file );
        }

        return filesList;
    }

    private void onImportDirectories()
    {
        LOGGER.info( "onImportDirectories() - " + super.isButtonImportDirectoriesEnabled() );

        if( super.isButtonImportDirectoriesEnabled() ) {
            final Runnable note = () -> {
                final List<File> dirs = getDFToolKit().getRootDirectoriesList();
                addRootDirectory( dirs );
                LOGGER.info( "onImportDirectories() done" );
            };
            new Thread( note, "onImportDirectories()" ).start();
            }
    }

    private void addRootDirectory()
    {
        LOGGER.info( "addRootDirectory() " + super.isButtonAddRootDirectoryEnabled() );

        if( super.isButtonAddRootDirectoryEnabled() ) {
            final JFileChooser jfc = getJFileChooser();

            jfc.setMultiSelectionEnabled( true );

            LOGGER.info( "getJFileChooser() done" );

            final int returnVal = jfc.showOpenDialog( this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                final File[] files = jfc.getSelectedFiles();

                addRootDirectory( toList( files ) );
                }

            LOGGER.info( "addRootDirectory() done" );
        }
    }

    private WaitingJFileChooserInitializer getWaitingJFileChooserInitializer()
    {
        if( this.waitingJFileChooserInitializer == null ) {
            final JFileChooserInitializerCustomize configurator
                = new LasyJFCCustomizer()
                    .setFileSelectionMode( FileSelectionMode.DIRECTORIES_ONLY );

            this.waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this.mainWindow,
                    this.jFileChooserInitializerTitle,
                    this.jFileChooserInitializerMessage
                    );

            }
        return this.waitingJFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        return getWaitingJFileChooserInitializer().getJFileChooser();
    }

    private void findBegin()
    {
        LOGGER.info( "find thread started" );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( this.txtProgressBarComputing );
        pBar.setIndeterminate( true );

        enable_findBegin();

        // KO Lock UI task.run()
        // KO Lock UI SwingUtilities.invokeAndWait( task )
        // KO Lock UI SwingUtilities.invokeLater( task )
        new Thread( getFindDeleteAdapter()::doFind, "findBegin()" ).start();
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // called in onStartDelete()
    private void startDelete()
    {
        LOGGER.info( "DELETE Thread started" );

        enable_startDelete();

        getJTreeEmptyDirectories().setEditable( false );

        final JProgressBar pBar = getProgressBar();
        pBar.setString( this.txtProgressBarDeleteSelectedFiles );
        pBar.setIndeterminate( true );

        new Thread( () -> runDelete( pBar ), "startDelete()").start();
    }

    private void runDelete( final JProgressBar pBar )
    {
        try {
            getFindDeleteAdapter().doDelete();
        }
        catch( final Exception e ) {
            LOGGER.warn( "doDelete()", e );
        }
        finally {
            getBtnStartDelete().setEnabled( true );

            getBtnCancel().setEnabled( false );
            getJTreeEmptyDirectories().setEditable( true );
            pBar.setIndeterminate( false );

            findBegin();
        }

        LOGGER.info( "DELETE Thread done" );
    }

    @Override//I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
