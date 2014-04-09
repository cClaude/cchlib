// $codepro.audit.disable largeNumberOfFields, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.dnd.SimpleFileDropListener;
import com.googlecode.cchlib.swing.textfield.XTextField;
import com.googlecode.cchlib.util.iterable.CascadingIterable;

/**
 * <pre>
 * TODO: replace JList by JTable
 *
 * -> add comment if a folder is a child of an other
 *    one, say that it will be ignored, and ignore it !
 * -> store a tree of folders list to find duplicate !
 *
 * Create classes TreeFile and TreeFile.Node
 *
 * TreeFile.Node = name;[File][TreeFile.Node]*
 * TreeFile = [TreeFile.Node (where TreeFile.Node.File.getParent() == null)]*
 * </pre>
 *
 */
@I18nName("duplicatefiles.JPanelSelectFoldersOrFiles")
public class JPanelSelectFoldersOrFiles extends JPanel
{
    private static final long serialVersionUID = 4L;
    private static Logger LOGGER = Logger.getLogger( JPanelSelectFoldersOrFiles.class );

    private JTable jTableSelectedFoldersOrFiles;
    private XTextField jTextFieldCurrentDir;
    private JButton jButtonSelectDir;
    private JButton jButtonSelectFile;
    private JButton jButtonAddEntry;
    private JButton jButtonRemEntry;

    private transient AppToolKit dFToolKit; // Serialization !!
    private AbstractTableModel tableModelSelectedFoldersOrFiles;
    private List<File> includeFileList  = new ArrayList<File>();
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
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public JPanelSelectFoldersOrFiles()
        throws HeadlessException, TooManyListenersException
    {
        this.dFToolKit = AppToolKitService.getInstance().getAppToolKit();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{23, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            jButtonRemEntry = new JButton("Remove");
            this.jButtonRemEntry.setHorizontalAlignment(SwingConstants.LEFT);
            jButtonRemEntry.setIcon( dFToolKit.getResources().getFolderRemoveIcon() );
            jButtonRemEntry.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    jButtonRemEntryMouseMousePressed( e );
                    }
                });
        }
        {
            jButtonAddEntry = new JButton("Append");
            this.jButtonAddEntry.setHorizontalAlignment(SwingConstants.LEFT);
            jButtonAddEntry.setIcon( dFToolKit.getResources().getAddIcon() );
            jButtonAddEntry.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onAddEntry();
                    }
                });

            GridBagConstraints gbc_jButtonAddEntry = new GridBagConstraints();
            gbc_jButtonAddEntry.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonAddEntry.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonAddEntry.gridx = 1;
            gbc_jButtonAddEntry.gridy = 0;
            add(jButtonAddEntry, gbc_jButtonAddEntry);
        }
        {
            Icon image = dFToolKit.getResources().getLogoIcon();
            JLabel jLabelDeco = new JLabel( image );
            GridBagConstraints gbc_jLabelDeco = new GridBagConstraints();
            gbc_jLabelDeco.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeco.gridx = 0;
            gbc_jLabelDeco.gridy = 0;
            add(jLabelDeco, gbc_jLabelDeco);
        }
        {
            jButtonSelectFile = new JButton("Select File");
            this.jButtonSelectFile.setHorizontalAlignment(SwingConstants.LEFT);
            jButtonSelectFile.setIcon( dFToolKit.getResources().getFileIcon() );
            jButtonSelectFile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onJButtonSelectFile();
                }
            });
            GridBagConstraints gbc_jButtonSelectFile = new GridBagConstraints();
            gbc_jButtonSelectFile.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonSelectFile.insets = new Insets(0, 0, 5, 0);
            gbc_jButtonSelectFile.anchor = GridBagConstraints.NORTH;
            gbc_jButtonSelectFile.gridx = 2;
            gbc_jButtonSelectFile.gridy = 0;
            add(jButtonSelectFile, gbc_jButtonSelectFile);
        }
        {
            jTextFieldCurrentDir = createXTextField();
            jTextFieldCurrentDir.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate( DocumentEvent event )
                {
                    fix_jButtonAddEntry();
                }
                @Override
                public void removeUpdate( DocumentEvent event )
                {
                    fix_jButtonAddEntry();
                }
                @Override
                public void changedUpdate( DocumentEvent event )
                {
                    fix_jButtonAddEntry();
                }});

            GridBagConstraints gbc_jTextFieldCurrentDir = new GridBagConstraints();
            gbc_jTextFieldCurrentDir.insets = new Insets(0, 0, 5, 5);
            gbc_jTextFieldCurrentDir.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextFieldCurrentDir.gridx = 0;
            gbc_jTextFieldCurrentDir.gridy = 1;
            add(jTextFieldCurrentDir, gbc_jTextFieldCurrentDir);
        }
        {
            GridBagConstraints gbc_jButtonRemEntry = new GridBagConstraints();
            gbc_jButtonRemEntry.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonRemEntry.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonRemEntry.gridx = 1;
            gbc_jButtonRemEntry.gridy = 1;
            add(jButtonRemEntry, gbc_jButtonRemEntry);
        }
        {
            jButtonSelectDir = new JButton("Select Folder");
            this.jButtonSelectDir.setHorizontalAlignment(SwingConstants.LEFT);
            jButtonSelectDir.setIcon( dFToolKit.getResources().getFolderSelectIcon() );
            jButtonSelectDir.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onJButtonSelectDir();
                }
            });
            GridBagConstraints gbc_jButtonSelectDir = new GridBagConstraints();
            gbc_jButtonSelectDir.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonSelectDir.insets = new Insets(0, 0, 5, 0);
            gbc_jButtonSelectDir.gridx = 2;
            gbc_jButtonSelectDir.gridy = 1;
            add(jButtonSelectDir, gbc_jButtonSelectDir);
        }
        {
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

        SimpleFileDropListener dropListener = new SimpleFileDropListener()
        {
            @Override
            public void filesDropped( List<File> files )
            {
                for( File f:files ) {
                    LOGGER.info( "add drop file:" + f );
                    addEntry( f, false );
                    }
            }
        };

        new SimpleFileDrop( this, dropListener ).addDropTargetListener();
    }

    /**
     * @wbp.factory
     */
    public static XTextField createXTextField()
    {
        return new XTextField();
    }

    public void initFixComponents()
    {
        // Init JTable
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
        fix_jButtonAddEntry();
        fix_jButtonRemEntry();
    }

    private void fix_jButtonAddEntry()
    {
        jButtonAddEntry.setEnabled( ! jTextFieldCurrentDir.getText().isEmpty() );
    }

    private void fix_jButtonRemEntry()
    {
        jButtonRemEntry.setEnabled( jTableSelectedFoldersOrFiles.getSelectedRows().length > 0 );
    }

    private void onJButtonSelectDir()
    {
        Runnable doJob = new Runnable()
        {
            @Override
            public void run()
            {
                JFileChooser jfc = dFToolKit.getJFileChooser( dFToolKit.getMainFrame(), JPanelSelectFoldersOrFiles.this );
                jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for(File f:files) {
                        LOGGER.info( "selected dir:" + f );
                        addEntry( f, false );
                        }
                    }
            }
        };

        new Thread( doJob, "onJButtonSelectDir()" ).start();
    }

    private void onJButtonSelectFile()
    {
        Runnable doJob = new Runnable()
        {
            @Override
            public void run()
            {
                JFileChooser jfc = dFToolKit.getJFileChooser( dFToolKit.getMainFrame(), JPanelSelectFoldersOrFiles.this );
                jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
                int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for(File f:files) {
                        LOGGER.info( "selected file:" + f );
                        addEntry( f, false );
                        }
                    }
            }
        };

        new Thread( doJob, "onJButtonSelectFile()" ).start();
   }

    private void onAddEntry()
    {
        final File f = new File( jTextFieldCurrentDir.getText() ); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.pathManipulation

        addEntry( f, false );
    }

    private void jButtonRemEntryMouseMousePressed( MouseEvent event )
    {
        final int[] selecteds = jTableSelectedFoldersOrFiles.getSelectedRows();

        for( int i = selecteds.length - 1; i>=0; i-- ) {
            int selected = selecteds[ i ];

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

        if( ingoreFileList.isEmpty() ) {
            this.jButtonRemEntry.setEnabled( false );
            }
    }

    private boolean addEntry( File f, boolean ignore ) // $codepro.audit.disable booleanMethodNamingConvention
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
                tableModelSelectedFoldersOrFiles.fireTableDataChanged();
                LOGGER.info( "add: " + f );

                jButtonRemEntry.setEnabled( true );
                return true;
                }
            else {
                // TODO: Explain reason in a dialog
                LOGGER.warn( "Value already exist: " + f );
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
       @SuppressWarnings("unchecked") Iterable<? extends File>[] array = new Iterable[ 2 ];
       array[ 0 ] = includeFileList;
       array[ 1 ] = ingoreFileList;

       return new CascadingIterable<File>( array );
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
