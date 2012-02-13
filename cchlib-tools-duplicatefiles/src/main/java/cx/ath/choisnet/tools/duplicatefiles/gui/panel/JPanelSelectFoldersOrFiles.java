package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nString;
import cx.ath.choisnet.swing.XTextField;
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
public class JPanelSelectFoldersOrFiles extends JPanel
{
    private static final long serialVersionUID = 4L;
    private Logger slogger = Logger.getLogger( JPanelSelectFoldersOrFiles.class );

    private JTable jTableSelectedFoldersOrFiles;
    private XTextField jTextFieldCurrentDir;
    private JButton jButtonSelectDir;
    private JButton jButtonSelectFile;
    private JButton jButtonAddDir;
    private JButton jButtonRemEntry;

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

    /**
     * Create the panel.
     */
    public JPanelSelectFoldersOrFiles()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{23, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jButtonRemEntry = new JButton("Remove");
        jButtonRemEntry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonRemEntryMouseMousePressed( e );
                }
            });

        jButtonAddDir = new JButton("Append");
        jButtonAddDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonAddDirMouseMousePressed( e );
                }
            });
        GridBagConstraints gbc_jButtonAddDir = new GridBagConstraints();
        gbc_jButtonAddDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonAddDir.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonAddDir.gridx = 1;
        gbc_jButtonAddDir.gridy = 0;
        add(jButtonAddDir, gbc_jButtonAddDir);

        jButtonSelectFile = new JButton("Select File");
        jButtonSelectFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonSelectFileMouseMousePressed(e);
            }
        });
        GridBagConstraints gbc_jButtonSelectFile = new GridBagConstraints();
        gbc_jButtonSelectFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonSelectFile.insets = new Insets(0, 0, 5, 0);
        gbc_jButtonSelectFile.anchor = GridBagConstraints.NORTH;
        gbc_jButtonSelectFile.gridx = 2;
        gbc_jButtonSelectFile.gridy = 0;
        add(jButtonSelectFile, gbc_jButtonSelectFile);

        jTextFieldCurrentDir = createXTextField();
        GridBagConstraints gbc_jTextFieldCurrentDir = new GridBagConstraints();
        gbc_jTextFieldCurrentDir.insets = new Insets(0, 0, 5, 5);
        gbc_jTextFieldCurrentDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldCurrentDir.gridx = 0;
        gbc_jTextFieldCurrentDir.gridy = 1;
        add(jTextFieldCurrentDir, gbc_jTextFieldCurrentDir);

        GridBagConstraints gbc_jButtonRemEntry = new GridBagConstraints();
        gbc_jButtonRemEntry.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonRemEntry.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonRemEntry.gridx = 1;
        gbc_jButtonRemEntry.gridy = 1;
        add(jButtonRemEntry, gbc_jButtonRemEntry);

        jButtonSelectDir = new JButton("Select Folder");
        jButtonSelectDir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jButtonSelectDirMouseMousePressed( e );
            }
        });
        GridBagConstraints gbc_jButtonSelectDir = new GridBagConstraints();
        gbc_jButtonSelectDir.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButtonSelectDir.insets = new Insets(0, 0, 5, 0);
        gbc_jButtonSelectDir.gridx = 2;
        gbc_jButtonSelectDir.gridy = 1;
        add(jButtonSelectDir, gbc_jButtonSelectDir);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 2;
        add(scrollPane, gbc_scrollPane);

        jTableSelectedFoldersOrFiles = new JTable();
        scrollPane.setViewportView(jTableSelectedFoldersOrFiles);
    }

    /**
     * @wbp.factory
     */
    public static XTextField createXTextField() {
        return new XTextField();
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

    private void jButtonSelectDirMouseMousePressed( MouseEvent event )
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
                    }
            }
        };

        new Thread( doJob ).start();
    }

    private void jButtonSelectFileMouseMousePressed(MouseEvent event)
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
                    }
            }
        };

        new Thread( doJob ).start();
   }

    private void jButtonAddDirMouseMousePressed( MouseEvent event )
    {
        File f = new File( jTextFieldCurrentDir.getText() );

        addEntry( f, false );
    }

    private void jButtonRemEntryMouseMousePressed( MouseEvent event )
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
