/**
 *
 */
package cx.ath.choisnet.tools.emptydirectories;

import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.util.CancelRequestException;

/**
 *
 *
 */
public class RemoveEmptyDirectories extends RemoveEmptyDirectoriesVS4E
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectories.class );
    //Xprivate FileTree fileTree = new FileTree();
    private File     rootFile;
    private JFileChooserInitializer jFileChooserInitializer;
    private EmptyDirectoriesTreeModel treeModel;
    private boolean cancelCalled;

    /**
     *
     */
    public RemoveEmptyDirectories()
    {
        super();
    }

    private void init()
    {
        JProgressBar pBar = getJProgressBarMain();
        pBar.setStringPainted( true );
        pBar.setString( "Select directory to scan" );
        pBar.setIndeterminate( false );

        this.cancelCalled = false;
    }

    private void startFind()
    {
        this.cancelCalled = false;

        // Create a JTree and tell it to display our model
        final JTree                     jTreeDir    = getJTreeDir();
        final EmptyDirectoriesFinder    emptyDirs   = new EmptyDirectoriesFinder( rootFile );
        final UpdateJTreeListeners      listener    = new UpdateJTreeListeners();

        emptyDirs.addListener( listener );

        //XtreeModel = new EmptyDirectoriesTreeModel( fileTree );
        treeModel = new EmptyDirectoriesTreeModel();
        jTreeDir.setModel( treeModel );

        jTreeDir.setCellRenderer( new EmptyDirectoryCheckBoxNodeRenderer( treeModel ) );
        jTreeDir.setCellEditor( new EmptyDirectoryCheckBoxNodeEditor( treeModel ) );
        jTreeDir.setEditable( true );

        //SwingUtilities.invokeLater( );
        new Thread( new Runnable() {
            @Override
            public void run()
            {
                try {
                    emptyDirs.find();

                    // Bad workaround !!!! TODO: find the solution !
                    //XtreeModel = new EmptyDirectoriesTreeModel( fileTree );
                    jTreeDir.setModel( null );
                    jTreeDir.setModel( treeModel );

                    logger.info( "setModel size:" + treeModel.size()  );
                    }
                catch( CancelRequestException e )  {
                    logger.info( "Cancel received" );

                    // Call done, to cleanup layout.
                    listener.findDone();
                    }
                cancelCalled = false;
            }
            }).start();
    }

    class UpdateJTreeListeners implements EmptyDirectoriesListener
    {
        @Override
        public boolean isCancel()
        {
            return cancelCalled;
        }
        @Override
        public void newEntry( File emptyDirectoryFile )
        {
            //Xboolean add = fileTree.add( emptyDirectoryFile );
            boolean add = treeModel.add( emptyDirectoryFile );

            logger.info( "tree size:" + treeModel.size() );
//
//            if( add ) {
//                jTreeDir.fireTreeExpanded( path )
//                }
        }
        @Override
        public void findStarted()
        {
            logger.info( "findStarted()" );
            //XfileTree.clear();
            treeModel.clear();

            JProgressBar pBar = getJProgressBarMain();
            pBar.setString( "Computing..." );
            pBar.setIndeterminate( true );
        }
        @Override
        public void findDone()
        {
            JProgressBar pBar = getJProgressBarMain();
            pBar.setIndeterminate( false );

            if( cancelCalled ) {
                pBar.setString( "Scan canceled !" );
                }
            else {
                pBar.setString( "Select file to delete" );
                }

            logger.info( "findDone()" );
        }
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                RemoveEmptyDirectories frame = new RemoveEmptyDirectories();
                frame.setDefaultCloseOperation( RemoveEmptyDirectories.EXIT_ON_CLOSE );
                frame.setTitle( "RemoveEmptyDirectories" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.init();
                frame.getJFileChooserInitializer();
            }
        } );
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator() {
                    private static final long serialVersionUID = 1L;

                    public void perfomeConfig( JFileChooser jfc )
                    {
                        super.perfomeConfig( jfc );

                        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                        jfc.setMultiSelectionEnabled( true );
                        jfc.setAccessory( new TabbedAccessory()
                                .addTabbedAccessory( new BookmarksAccessory(
                                        jfc,
                                        new BookmarksAccessoryDefaultConfigurator() ) ) );
                    }
                } );
        }
        return jFileChooserInitializer;
    }

    protected void jButtonFindMouseMouseClicked(MouseEvent event)
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();
        jfc.setMultiSelectionEnabled( false ); // TODO: allow this ?
        int returnVal = jfc.showOpenDialog( this );

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            rootFile = jfc.getSelectedFile();
            logger.info( "selected dir:" + rootFile );

//            File[] files = jfc.getSelectedFiles();
//
//            for( File f:files ) {
//                //addEntry( f, false );
//                rootFile.add( f );
//                logger.info( "selected dir:" + rootFile );
//                }
            getJTextFieldRootDir().setText( rootFile.getPath() );
            startFind();
            }
    }

    protected void jButtonCancelMouseMouseClicked( MouseEvent event )
    {
        this.cancelCalled = true;

        logger.info( "jButtonCancelMouseMouseClicked" );
    }

}
