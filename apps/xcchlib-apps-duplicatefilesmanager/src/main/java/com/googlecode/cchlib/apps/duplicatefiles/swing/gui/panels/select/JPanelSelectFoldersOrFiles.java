package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.select;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.FileChooserEntryPoint;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Resources;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.dnd.SimpleFileDropListener;
import com.googlecode.cchlib.swing.textfield.XTextField;

/**
 * <pre>
 * - add comment if a folder is a child of an other
 *   one, say that it will be ignored, and ignore it !
 * - store a tree of folders list to find duplicate !
 *
 * Create classes TreeFile and TreeFile.Node
 *
 * TreeFile.Node = name;[File][TreeFile.Node]*
 * TreeFile = [TreeFile.Node (where TreeFile.Node.File.getParent() == null)]*
 * </pre>
 *
 */
@I18nName("duplicatefiles.JPanelSelectFoldersOrFiles")
@SuppressWarnings({
    "squid:S1199", // Generated code
    "squid:S00117" // Naming conventions
    })
public class JPanelSelectFoldersOrFiles extends JPanel
{
    private final class SelectedFilesOrFoldersTableModel extends AbstractTableModel
    {
        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount()
        {
            return JPanelSelectFoldersOrFiles.this.columnsHeaders.length;
        }

        @Override
        public String getColumnName(final int column)
        {
            return JPanelSelectFoldersOrFiles.this.columnsHeaders[column];
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex)
        {
            return String.class;
        }

        @Override
        public int getRowCount()
        {
            return JPanelSelectFoldersOrFiles.this.includeFileList.size();
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex)
        {
            // Build data
            final File file = JPanelSelectFoldersOrFiles.this.includeFileList.get( rowIndex );

            if( columnIndex == 0 ) {
                return file;
                }
            else if( columnIndex == 1 ) {
                return file.isFile() ?
                        JPanelSelectFoldersOrFiles.this.txtFile : JPanelSelectFoldersOrFiles.this.txtDirectory;
                }
            else {
                return file.isFile() ?
                    JPanelSelectFoldersOrFiles.this.txtIncludeFile : JPanelSelectFoldersOrFiles.this.txtIncludeDir;
                }
            }
    }

    private static final long serialVersionUID = 5L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSelectFoldersOrFiles.class );

    private final JTable jTableSelectedFoldersOrFiles;
    private final XTextField jTextFieldCurrentDir;
    private final JButton jButtonSelectDir;
    private final JButton jButtonSelectFile;
    private final JButton jButtonAddEntry;
    private final JButton jButtonRemEntry;

    private AbstractTableModel tableModelSelectedFoldersOrFiles;
    private final List<File> includeFileList  = new ArrayList<>();

    @I18nString private String[] columnsHeaders;
    @I18nString private String   txtFile;
    @I18nString private String   txtDirectory;
    @I18nString private String   txtIncludeDir;
    @I18nString private String   txtIncludeFile;

    public JPanelSelectFoldersOrFiles() throws TooManyListenersException
    {
        beSurNonFinal();

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{23, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.jButtonRemEntry = new JButton("Remove");
            this.jButtonRemEntry.setHorizontalAlignment(SwingConstants.LEFT);
            this.jButtonRemEntry.setIcon( getResources().getFolderRemoveIcon() );
            this.jButtonRemEntry.addActionListener(e -> onRemoveEntries());
        }
        {
            this.jButtonAddEntry = new JButton("Append");
            this.jButtonAddEntry.setHorizontalAlignment(SwingConstants.LEFT);
            this.jButtonAddEntry.setIcon( getResources().getAddIcon() );
            this.jButtonAddEntry.addActionListener(e -> onAddEntry());

            final GridBagConstraints gbc_jButtonAddEntry = new GridBagConstraints();
            gbc_jButtonAddEntry.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonAddEntry.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonAddEntry.gridx = 1;
            gbc_jButtonAddEntry.gridy = 0;
            add(this.jButtonAddEntry, gbc_jButtonAddEntry);
        }
        {
            final Icon   image      = getResources().getLogoIcon();
            final JLabel jLabelDeco = new JLabel( image );

            final GridBagConstraints gbc_jLabelDeco = new GridBagConstraints();
            gbc_jLabelDeco.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeco.gridx = 0;
            gbc_jLabelDeco.gridy = 0;
            add(jLabelDeco, gbc_jLabelDeco);
        }
        {
            this.jButtonSelectFile = new JButton("Select File");
            this.jButtonSelectFile.setHorizontalAlignment(SwingConstants.LEFT);
            this.jButtonSelectFile.setIcon( getResources().getFileIcon() );
            this.jButtonSelectFile.addActionListener(e -> onJButtonSelectFile());

            final GridBagConstraints gbc_jButtonSelectFile = new GridBagConstraints();
            gbc_jButtonSelectFile.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonSelectFile.insets = new Insets(0, 0, 5, 0);
            gbc_jButtonSelectFile.anchor = GridBagConstraints.NORTH;
            gbc_jButtonSelectFile.gridx = 2;
            gbc_jButtonSelectFile.gridy = 0;
            add(this.jButtonSelectFile, gbc_jButtonSelectFile);
        }
        {
            this.jTextFieldCurrentDir = createXTextField();
            this.jTextFieldCurrentDir.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate( final DocumentEvent event )
                {
                    jButtonAddEntryFix();
                }
                @Override
                public void removeUpdate( final DocumentEvent event )
                {
                    jButtonAddEntryFix();
                }
                @Override
                public void changedUpdate( final DocumentEvent event )
                {
                    jButtonAddEntryFix();
                }});

            final GridBagConstraints gbc_jTextFieldCurrentDir = new GridBagConstraints();
            gbc_jTextFieldCurrentDir.insets = new Insets(0, 0, 5, 5);
            gbc_jTextFieldCurrentDir.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextFieldCurrentDir.gridx = 0;
            gbc_jTextFieldCurrentDir.gridy = 1;
            add(this.jTextFieldCurrentDir, gbc_jTextFieldCurrentDir);
        }
        {
            final GridBagConstraints gbc_jButtonRemEntry = new GridBagConstraints();
            gbc_jButtonRemEntry.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonRemEntry.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonRemEntry.gridx = 1;
            gbc_jButtonRemEntry.gridy = 1;
            add(this.jButtonRemEntry, gbc_jButtonRemEntry);
        }
        {
            this.jButtonSelectDir = new JButton("Select Folder");
            this.jButtonSelectDir.setHorizontalAlignment(SwingConstants.LEFT);
            this.jButtonSelectDir.setIcon( getResources().getFolderSelectIcon() );
            this.jButtonSelectDir.addActionListener(e -> onJButtonSelectDir());

            final GridBagConstraints gbc_jButtonSelectDir = new GridBagConstraints();
            gbc_jButtonSelectDir.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonSelectDir.insets = new Insets(0, 0, 5, 0);
            gbc_jButtonSelectDir.gridx = 2;
            gbc_jButtonSelectDir.gridy = 1;
            add(this.jButtonSelectDir, gbc_jButtonSelectDir);
        }
        {
            final JScrollPane scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridwidth = 3;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 2;
            add(scrollPane, gbc_scrollPane);

            this.jTableSelectedFoldersOrFiles = new JTable();
            scrollPane.setViewportView(this.jTableSelectedFoldersOrFiles);
        }

        final SimpleFileDropListener dropListener = (final List<File> files) -> {
            for( final File f:files ) {
                LOGGER.info( "add drop file:" + f );
                addEntry( f );
            }
        };

        new SimpleFileDrop( this, dropListener ).addDropTargetListener();
    }

    private Resources getResources()
    {
        return getAppToolKit().getResources();
    }

    private AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    private void beSurNonFinal()
    {
        final String[] headers = {
                "Files or folders names",
                "Type",  // File or Directory
                "Action" // Scan recursive, Check File
                };
        this.columnsHeaders   = headers;
        this.txtFile          = "File";
        this.txtDirectory     = "Directory";
        this.txtIncludeDir    = "Include content";
        this.txtIncludeFile   = "Include file";
    }

    /**
     * @wbp.factory
     *
     * @return a new {@link XTextField}
     */
    public static XTextField createXTextField()
    {
        return new XTextField();
    }

    public void initFixComponents()
    {
        // Init JTable
        this.tableModelSelectedFoldersOrFiles = new SelectedFilesOrFoldersTableModel();

        this.jTableSelectedFoldersOrFiles.setModel( this.tableModelSelectedFoldersOrFiles );
        this.jTableSelectedFoldersOrFiles.getSelectionModel().addListSelectionListener( e -> jButtonRemEntryFix() );

        jButtonAddEntryFix();
        jButtonRemEntryFix();
    }

    private void jButtonAddEntryFix()
    {
        this.jButtonAddEntry.setEnabled( ! this.jTextFieldCurrentDir.getText().isEmpty() );
    }

    private void jButtonRemEntryFix()
    {
        this.jButtonRemEntry.setEnabled( this.jTableSelectedFoldersOrFiles.getSelectedRows().length > 0 );
    }

    private void onJButtonSelectDir()
    {
        final Runnable doJob = () -> {
            final JFileChooser jfc = getJFileChooser();
            jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            final int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                final File[] files = jfc.getSelectedFiles();

                for(final File f:files) {
                    LOGGER.info( "selected dir:" + f );
                    addEntry( f );
                }
            }
        };

        new Thread( doJob, "onJButtonSelectDir()" ).start();
    }

    private JFileChooser getJFileChooser()
    {
        final AppToolKit appToolKit = getAppToolKit();

        return appToolKit.getJFileChooser(
                appToolKit.getMainFrame(),
                FileChooserEntryPoint.DUPLICATES
                );
    }

    private void onJButtonSelectFile()
    {
        new Thread( this::selectFile, "onJButtonSelectFile()" ).start();
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Yes, this is use !
    private void selectFile()
    {
        final JFileChooser jfc = getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );

        final int returnVal = jfc.showOpenDialog( JPanelSelectFoldersOrFiles.this );

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            final File[] files = jfc.getSelectedFiles();

            for(final File f:files) {
                LOGGER.info( "selected file:" + f );
                addEntry( f );
            }
        }
    }

    private void onAddEntry()
    {
        final File f = new File( this.jTextFieldCurrentDir.getText() );

        addEntry( f );
    }

    private void onRemoveEntries()
    {
        final int[] selecteds = this.jTableSelectedFoldersOrFiles.getSelectedRows();

        for( int i = selecteds.length - 1; i>=0; i-- ) {
            final int selected = selecteds[ i ];

            removeEntry( selected );
        }

        this.tableModelSelectedFoldersOrFiles.fireTableDataChanged();
    }

    private void removeEntry( final int index )
    {
        this.includeFileList.remove( index );

        if( this.includeFileList.isEmpty() ) {
            this.jButtonRemEntry.setEnabled( false );
            }
    }

    public boolean addEntry( final File file )
    {
        if( file.exists() ) {
            File existingValue = null;

            for( final File current : entries() ) {
                if( current.equals( file ) ) {
                    existingValue = file;
                    break;
                    }
                }

            if( existingValue == null ) {
                // Not found
                this.includeFileList.add( file );
                this.tableModelSelectedFoldersOrFiles.fireTableDataChanged();
                LOGGER.info( "add: " + file );

                this.jButtonRemEntry.setEnabled( true );
                return true;
                }
            else {
                // TODO: Explain reason in a dialog
                LOGGER.warn( "Value already exist: " + file );
                }
            }
        else {
            // TODO: Explain reason in a dialog
            LOGGER.warn( "Value does not exist: " + file );
        }

        getAppToolKit().beep();

        return false;
    }

    public int getEntriesToScanSize()
    {
        return this.includeFileList.size();
    }

    private Iterable<File> entries()
    {
       return this.includeFileList;
    }

    public Collection<File> entriesToScans()
    {
        return Collections.unmodifiableCollection( this.includeFileList );
    }
}
