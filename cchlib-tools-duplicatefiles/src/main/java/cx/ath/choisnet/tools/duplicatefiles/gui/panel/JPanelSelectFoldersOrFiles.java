package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
import cx.ath.choisnet.util.iterator.iterable.BiIterator;

/**
 * <pre>
 * TODO: replace JList by JTable
 * -> add comment if a folder is a child of an other
 * one, say that it will be ignored, and ignore it !
 * -> store a tree of folders list to find duplicate !
 *
 * Create classes TreeFile and TreeFile.Node
 *
 * TreeFile.Node = name;[File][TreeFile.Node]*
 * TreeFile = [TreeFile.Node (where TreeFile.Node.File.getParent() == null)]*
 * </pre>
 *
 * @author Claude CHOISNET
 */
public class JPanelSelectFoldersOrFiles
    extends JPanelSelectFoldersOrFilesWB
{
    private static final long serialVersionUID = 3L;
    private Logger slogger = Logger.getLogger( JPanelSelectFoldersOrFiles.class );
    // TODO:Must be restore by parent !
    private transient DFToolKit dFToolKit;
    /* @serial */
    private AbstractTableModel tableModelSelectedFoldersOrFiles;
    /* @serial */
    private List<File> includeFileList  = new ArrayList<File>();
    /* @serial */
    private List<File> ingoreFileList   = new ArrayList<File>();

    @I18nString private String[] columnsHeaders = {
            "Files or folders names",
            "Type",  // File or Directory
            "Action" // Scan recursive, Check File, Ignore recursive
            };
    @I18nString private String txtFile = "File";
    @I18nString private String txtDirectory = "Directory";
    @I18nString private String txtIgnoreContent = "Ignore content";
    @I18nString private String txtIncludeDir = "Include content";
    @I18nString private String txtIncludeFile = "Include file";

    public JPanelSelectFoldersOrFiles()
    {
        //initComponents();
    }

    public void initJTableSelectedFoldersOrFiles()
    {
        tableModelSelectedFoldersOrFiles = new AbstractTableModel()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public int getColumnCount()
            {
                return columnsHeaders.length;
            }
            @Override
            public String getColumnName(int column)
            {
                return columnsHeaders[column];
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return String.class;
            }
            @Override
            public int getRowCount()
            {
                //slogger.info( "getRowCount(): " + filesList.size() );
                return includeFileList.size() + ingoreFileList.size();
            }
            @Override
            public Object getValueAt(int rowIndex, int columnIndex)
            {
                // Build data
                final int   includeFileListSize = includeFileList.size();
                boolean     ignore;
                File        f;

                if( rowIndex < includeFileListSize ) {
                    f = includeFileList.get( rowIndex );
                    ignore = false;
                    }
                else {
                    f = ingoreFileList.get( rowIndex - includeFileListSize );
                    ignore = true;
                    }

                if( columnIndex == 0 ) {
                    return f;
                    }
                else if( columnIndex == 1 ) {
                    return f.isFile() ?
                            txtFile : txtDirectory;
                    }
                else {
                    if( ignore ) {
                        return txtIgnoreContent;
                        }
                    else {
                        return f.isFile() ?
                            txtIncludeFile : txtIncludeDir;
                        }
                    }
                }
         };

        jTableSelectedFoldersOrFiles.setModel( tableModelSelectedFoldersOrFiles );
    }

    public void initFixComponents( DFToolKit dFToolKit )
    {
        this.dFToolKit = dFToolKit;

        // Init JTable
        initJTableSelectedFoldersOrFiles();

        jButtonSelectFile.setIcon( dFToolKit.getIcon( "file.png" ) );
        jButtonSelectDir.setIcon( dFToolKit.getIcon( "folder.png" ) );
        jButtonAddDir.setIcon( dFToolKit.getIcon( "add.png" ) );
        jButtonRemEntry.setIcon( dFToolKit.getIcon( "remove.png" ) );
    }

    @Override
    protected void jButtonSelectDirMouseMousePressed( MouseEvent event )
    {
        Runnable doJob = new Runnable()
        {
            @Override
            public void run()
            {
                JFileChooser jfc = dFToolKit.getJFileChooser();
                jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for(File f:files) {
                        slogger.info( "selected dir:" + f );
                        addEntry( f, false );
                        }
                    //slogger.info( "selected dir:" + jfc.getSelectedFile() );
                    //addEntry( jfc.getSelectedFile(), false );
                }
            }
        };
        
        new Thread( doJob ).start();
    }

    @Override
    protected void jButtonSelectFileMouseMousePressed(MouseEvent event)
    {       
        Runnable doJob = new Runnable()
        {
            @Override
            public void run()
            {
                JFileChooser jfc = dFToolKit.getJFileChooser();
                jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
                int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for(File f:files) {
                        slogger.info( "selected file:" + f );
                        addEntry( f, false );
                        }
                    //slogger.info( "selected file:" + jfc.getSelectedFile() );
                    //addEntry( jfc.getSelectedFile(), false );
                }
            }
        };
        
        new Thread( doJob ).start();
   }

    @Override
    protected void jButtonAddDirMouseMousePressed( MouseEvent event )
    {
        File f = new File( jTextFieldCurrentDir.getText() );

        addEntry( f, false );
    }

    @Override
    protected void jButtonRemEntryMouseMousePressed( MouseEvent event )
    {
        final int[] selecteds = jTableSelectedFoldersOrFiles.getSelectedRows();

        for( int i = selecteds.length - 1; i>=0; i-- ) {
            int selected = selecteds[ i ];
            //listModelDirectoryFiles.remove( selected );
            removeEntry( selected );
        }
        tableModelSelectedFoldersOrFiles.fireTableDataChanged();
    }

    private void removeEntry( int index )
    {
        final int includeFileListSize = includeFileList.size();

        if( index < includeFileListSize ) {
            includeFileList.remove( index );
            }
        else {
            ingoreFileList.remove( index - includeFileListSize );
            }
    }

    private boolean addEntry( File f, boolean ignore )
    {
        if( f.exists() ) {
            File existingValue = null;

            for( File current : entries() ) {
                if( current.equals( f ) ) {
                    existingValue = f;
                    break;
                    }
                }

            if( existingValue == null ) {
                // Not found
                if( ignore ) {
                    ingoreFileList.add( f );
                    }
                else {
                    includeFileList.add( f );
                    }
//                filesList.add( new FileOrFolder( f, ignore ) );
                tableModelSelectedFoldersOrFiles.fireTableDataChanged();
                slogger.info( "add: " + f );
//                slogger.info( "size: " + filesList.size() );
                return true;
            }
            else {
                // TODO: Explain reason in a dialog
                slogger.warn( "Value already exist: " + f );
            }
        }

        dFToolKit.beep();
        return false;
    }

    public int getEntriesToScanSize()
    {
        return includeFileList.size();
    }

    private Iterable<File> entries()
    {
        return new BiIterator<File>( includeFileList, ingoreFileList );
    }

    public Iterable<File> entriesToScans()
    {
        return includeFileList;
    }

    public Iterable<File> entriesToIgnore()
    {
        return ingoreFileList;
    }
}
