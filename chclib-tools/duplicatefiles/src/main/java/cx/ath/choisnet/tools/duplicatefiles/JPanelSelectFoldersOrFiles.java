package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18nString;
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
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelSelectFoldersOrFiles
    extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Logger slogger = Logger.getLogger( JPanelSelectFoldersOrFiles.class );
    // TODO:Must be restore by parent !
    private transient DFToolKit dFToolKit;
    /* @serial */
    private AbstractTableModel tableModelSelectedFoldersOrFiles;
    /* @serial */
    private JScrollPane jScrollPaneSelectedFoldersOrFiles;
    /* @serial */
    private JTable jTableSelectedFoldersOrFiles;
    //private List<FileOrFolder> filesList  = new ArrayList<FileOrFolder>();
    /* @serial */
    private List<File> includeFileList  = new ArrayList<File>();
    /* @serial */
    private List<File> ingoreFileList   = new ArrayList<File>();
    /* @serial */
    private JPanel jPanelCommands;
    /* @serial */
    private JPanel jPanelDirButtons;
    /* @serial */
    private JTextField jTextFieldCurrentDir;
    /* @serial */
    private JPanel jPanelAddEntry;
    /* @serial */
    private JButton jButtonRemEntry;
    /* @serial */
    private JButton jButtonAddDir;
    /* @serial */
    private JButton jButtonSelectDir;
    /* @serial */
    private JButton jButtonSelectFile;

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
        initComponents();
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

        jPanelCommands.setLayout(new BoxLayout(jPanelCommands, BoxLayout.Y_AXIS));

        // Init JTable
        initJTableSelectedFoldersOrFiles();

        jButtonSelectFile.setIcon( dFToolKit.getIcon( "file.png" ) );
        jButtonSelectDir.setIcon( dFToolKit.getIcon( "folder.png" ) );
        jButtonAddDir.setIcon( dFToolKit.getIcon( "add.png" ) );
        jButtonRemEntry.setIcon( dFToolKit.getIcon( "remove.png" ) );
    }

    private void initComponents() 
    {
    	setLayout(new BorderLayout());
    	add(getJPanelCommands(), BorderLayout.NORTH);
    	add(getJScrollPaneSelectedFoldersOrFiles(), BorderLayout.CENTER);
    	setSize(444, 108);
    }

    private JPanel getJPanelAddEntry()
    {
        if (jPanelAddEntry == null) {
            jPanelAddEntry = new JPanel();
            jPanelAddEntry.setLayout(new BoxLayout(jPanelAddEntry, BoxLayout.X_AXIS));
            jPanelAddEntry.add(getJTextFieldCurrentDir());
            jPanelAddEntry.add(getJButtonAddDir());
        }
        return jPanelAddEntry;
    }

    private JScrollPane getJScrollPaneSelectedFoldersOrFiles() 
    {
        if (jScrollPaneSelectedFoldersOrFiles == null) {
            jScrollPaneSelectedFoldersOrFiles = new JScrollPane();
            jScrollPaneSelectedFoldersOrFiles.setViewportView(getJTableSelectedFoldersOrFiles());
        }
        return jScrollPaneSelectedFoldersOrFiles;
    }

    private JTable getJTableSelectedFoldersOrFiles()
    {
        if (jTableSelectedFoldersOrFiles == null) {
            jTableSelectedFoldersOrFiles = new JTable();
        }
        return jTableSelectedFoldersOrFiles;
    }

    private JPanel getJPanelCommands() 
    {
        if (jPanelCommands == null) {
            jPanelCommands = new JPanel();
            jPanelCommands.setLayout(new BoxLayout(jPanelCommands, BoxLayout.Y_AXIS));
            jPanelCommands.add(getJPanelAddEntry());
            jPanelCommands.add(getJPanelDirButtons());
        }
        return jPanelCommands;
    }

//    private JScrollPane getJScrollPaneSelectedDirs() {
//        if (jScrollPaneSelectedDirs == null) {
//            jScrollPaneSelectedDirs = new JScrollPane();
//            jScrollPaneSelectedDirs.setViewportView(getJListSelectedDirs());
//        }
//        return jScrollPaneSelectedDirs;
//    }

//    private JList getJListSelectedDirs() {
//        if (jListSelectedDirs == null) {
//            jListSelectedDirs = new JList();
//        }
//        return jListSelectedDirs;
//    }

    private JPanel getJPanelDirButtons() 
    {
        if (jPanelDirButtons == null) {
            jPanelDirButtons = new JPanel();
            jPanelDirButtons.setLayout(new BoxLayout(jPanelDirButtons, BoxLayout.X_AXIS));
            jPanelDirButtons.add(getJButtonSelectFile());
            jPanelDirButtons.add(getJButtonSelectDir());
            jPanelDirButtons.add(getJButtonRemEntry());
        }
        return jPanelDirButtons;
    }

    private JButton getJButtonAddDir() 
    {
        if (jButtonAddDir == null) {
            jButtonAddDir = new JButton();
            jButtonAddDir.setText("Add");
            jButtonAddDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonAddDirMouseMousePressed(event);
                }
            });
        }
        return jButtonAddDir;
    }

    private JButton getJButtonSelectDir()
    {
        if (jButtonSelectDir == null) {
            jButtonSelectDir = new JButton();
            jButtonSelectDir.setText("Add folder");
            jButtonSelectDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonSelectDirMouseMousePressed(event);
                }
            });
        }
        return jButtonSelectDir;
    }
    
    private JButton getJButtonSelectFile()
    {
    	if (jButtonSelectFile == null) {
    		jButtonSelectFile = new JButton();
    		jButtonSelectFile.setText("Add File");
    		jButtonSelectFile.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jButtonSelectFileMouseMousePressed(event);
    			}
    		});
    	}
    	return jButtonSelectFile;
    }

    private JTextField getJTextFieldCurrentDir() 
    {
        if (jTextFieldCurrentDir == null) {
            jTextFieldCurrentDir = new JTextField();
        }
        return jTextFieldCurrentDir;
    }

    private JButton getJButtonRemEntry() 
    {
        if (jButtonRemEntry == null) {
            jButtonRemEntry = new JButton();
            jButtonRemEntry.setText("Remove entry");
            jButtonRemEntry.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonRemEntryMouseMousePressed(event);
                }
            });
        }
        return jButtonRemEntry;
    }

    private void jButtonSelectDirMouseMousePressed( MouseEvent event )
    {
        JFileChooser jfc = dFToolKit.getJFileChooser();
        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        int returnVal = jfc.showOpenDialog( this );

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
    
    private void jButtonSelectFileMouseMousePressed(MouseEvent event) 
    {
        JFileChooser jfc = dFToolKit.getJFileChooser();
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        int returnVal = jfc.showOpenDialog( this );

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

    private void jButtonAddDirMouseMousePressed( MouseEvent event )
    {
        File f = new File( jTextFieldCurrentDir.getText() );

        addEntry( f, false );
    }

    private void jButtonRemEntryMouseMousePressed( MouseEvent event )
    {
        //final int[] selecteds = jListSelectedDirs.getSelectedIndices();
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
        /*
        int count = 0;

        for( FileOrFolder ff : this.filesList ) {
            if( ! ff.isIgnoreContent() ) {
                count++;
                }
            }

        return count;
        */
    }
//    public int directoriesSize()
//    {
//        return listModelDirectoryFiles.size();
//    }

//    @Override
//    public Iterator<File> iterator()
//    {
//        final int len = listModelDirectoryFiles.size();
//        return new Iterator<File>()
//        {
//            int i = 0;
//            @Override
//            public boolean hasNext()
//            {
//                return i<len;
//            }
//            @Override
//            public File next()
//            {
//                return (File)listModelDirectoryFiles.getElementAt( i++ );
//            }
//            @Override
//            public void remove()
//            {
//                throw new UnsupportedOperationException();
//            }
//        };
//    }

    private Iterable<File> entries()
    {
        return new BiIterator<File>( includeFileList, ingoreFileList );
    }
    
    public Iterable<File> entriesToScans()
    {
        return includeFileList;
        /*        
        return new ComputableIterator<File>()
        {
            final Iterator<FileOrFolder> iter = filesList.iterator();
            @Override
            protected File computeNext() throws NoSuchElementException
            {
                while( iter.hasNext() ) {
                    FileOrFolder ff = iter.next();

                    if( !ff.isIgnoreContent() ) {
                        return ff.getFile();
                    }
                }
                throw new NoSuchElementException();
            }
        };
        */
    }

    public Iterable<File> entriesToIgnore()
    {
        return ingoreFileList;
        /*
        return new ComputableIterator<File>()
        {
            final Iterator<FileOrFolder> iter = filesList.iterator();
            @Override
            protected File computeNext() throws NoSuchElementException
            {
                while( iter.hasNext() ) {
                    FileOrFolder ff = iter.next();

                    if( !ff.isIgnoreContent() ) {
                        return ff.getFile();
                    }
                }
                throw new NoSuchElementException();            
            }
        };
        */
    }

//    private static class FileOrFolder
//    {
//        private File file;
//        private boolean includeContent;
//        public FileOrFolder( File file, boolean includeContent )
//        {
//            this.file = file;
//            this.includeContent = includeContent;
//        }
//        public File getFile()
//        {
//            return file;
//        }
//        public boolean isIgnoreContent()
//        {
//            return this.includeContent;
//        }
//    }
}
