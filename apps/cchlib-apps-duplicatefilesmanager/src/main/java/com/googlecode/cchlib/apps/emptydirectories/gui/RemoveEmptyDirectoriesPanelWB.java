// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets; // $codepro.audit.disable unnecessaryImport
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Resources;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;

/**
 * Handle layout
 */
public abstract class RemoveEmptyDirectoriesPanelWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( RemoveEmptyDirectoriesPanelWB.class );

    protected static final String ACTION_IMPORT_DIRS     = "ACTION_IMPORT_DIRS";
    protected static final String ACTION_ADD_DIRS        = "ACTION_ADD_DIRS";
    protected static final String ACTION_REMOVE_DIR      = "ACTION_REMOVE_DIR";
    protected static final String ACTION_FIND_EMPTY_DIRS = "ACTION_FIND_EMPTY_DIRS";
    protected static final String ACTION_CANCEL          = "ACTION_CANCEL";
    protected static final String ACTION_SELECT_ALL_LEAFONLY            = "ACTION_SELECT_ALL_LEAFONLY";
    protected static final String ACTION_SELECT_ALL_SELECTABLE_NODES    = "ACTION_SELECT_ALL_SELECTABLE_NODES";
    protected static final String ACTION_UNSELECT_ALL    = "ACTION_UNSELECT_ALL";
    protected static final String ACTION_START_REMDIRS   = "ACTION_START_REMDIRS";

    private JScrollPane scrollPaneJList;
    @I18nToolTipText private final JButton btnImportDirectories;
    private final JButton btnAddRootDirectory;
    private final JButton btnCancel;
    private final JButton btnDeselectAll;
    private final JButton btnRemoveRootDirectory;
    private final JButton btnSelectAll;
    private final JButton btnSelectAllLeaf;
    private final JButton btnStartDelete;
    private final JButton btnStartScan;
    private final JList<File> jListRootDirectories;
    private final JProgressBar progressBar;
    private final JSeparator separator;
    private final JTree jTreeEmptyDirectories;

    private transient SimpleFileDrop scrollPaneJListSimpleFileDrop;

    /**
     * Create the frame.
     * @param resources
     */
    @SuppressWarnings({"squid:S00117","squid:S1199"}) // Generated code
    public RemoveEmptyDirectoriesPanelWB( final Resources resources )
    {
        setSize( 800, 400 );

        {
            final GridBagLayout gbl_contentPane = new GridBagLayout();
            gbl_contentPane.columnWidths = new int[]{0, 0, 0};
            gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0};
            this.setLayout(gbl_contentPane);
        }
        {
            this.btnAddRootDirectory = new JButton("Select directory");
            this.btnAddRootDirectory.setIcon( resources.getFolderSelectIcon() );
            this.btnAddRootDirectory.setActionCommand( ACTION_ADD_DIRS );
            this.btnAddRootDirectory.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnAddRootDirectory = new GridBagConstraints();
            gbc_btnAddRootDirectory.anchor = GridBagConstraints.NORTH;
            gbc_btnAddRootDirectory.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnAddRootDirectory.insets = new Insets(0, 0, 5, 0);
            gbc_btnAddRootDirectory.gridx = 1;
            gbc_btnAddRootDirectory.gridy = 0;
            this.add(this.btnAddRootDirectory, gbc_btnAddRootDirectory);
        }
        {
            this.scrollPaneJList = new JScrollPane();
            final GridBagConstraints gbc_scrollPaneJList = new GridBagConstraints();
            gbc_scrollPaneJList.fill = GridBagConstraints.BOTH;
            gbc_scrollPaneJList.gridheight = 5;
            gbc_scrollPaneJList.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPaneJList.gridx = 0;
            gbc_scrollPaneJList.gridy = 0;
            this.add(this.scrollPaneJList, gbc_scrollPaneJList);

            initScrollPaneFileDrop( this.scrollPaneJList );

            this.jListRootDirectories = createJList();
            this.scrollPaneJList.setViewportView(this.jListRootDirectories);
        }
        {
            this.btnImportDirectories = new JButton("Import directories");
            this.btnImportDirectories.setToolTipText("Import directories from duplicate tab");
            this.btnImportDirectories.setIcon( resources.getFolderImportIcon() );
            this.btnImportDirectories.setActionCommand( ACTION_IMPORT_DIRS );
            this.btnImportDirectories.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnImportDirectories = new GridBagConstraints();
            gbc_btnImportDirectories.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnImportDirectories.insets = new Insets(0, 0, 5, 0);
            gbc_btnImportDirectories.gridx = 1;
            gbc_btnImportDirectories.gridy = 1;
            this.add(this.btnImportDirectories, gbc_btnImportDirectories);
        }
        {
            this.btnRemoveRootDirectory = new JButton("Remove");
            this.btnRemoveRootDirectory.setIcon( resources.getFolderRemoveIcon() );
            this.btnRemoveRootDirectory.setActionCommand( ACTION_REMOVE_DIR );
            this.btnRemoveRootDirectory.addActionListener( getActionListener() );
            this.btnRemoveRootDirectory.setEnabled(false);

            final GridBagConstraints gbc_btnRemoveRootDirectory = new GridBagConstraints();
            gbc_btnRemoveRootDirectory.anchor = GridBagConstraints.NORTH;
            gbc_btnRemoveRootDirectory.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnRemoveRootDirectory.insets = new Insets(0, 0, 5, 0);
            gbc_btnRemoveRootDirectory.gridx = 1;
            gbc_btnRemoveRootDirectory.gridy = 2;
            this.add(this.btnRemoveRootDirectory, gbc_btnRemoveRootDirectory);
        }
        {
            this.btnStartScan = new JButton("Find empty directories");
            this.btnStartScan.setActionCommand( ACTION_FIND_EMPTY_DIRS );
            this.btnStartScan.addActionListener( getActionListener() );
            this.btnStartScan.setEnabled(false);

            final GridBagConstraints gbc_btnStartScan = new GridBagConstraints();
            gbc_btnStartScan.anchor = GridBagConstraints.NORTH;
            gbc_btnStartScan.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnStartScan.insets = new Insets(0, 0, 5, 0);
            gbc_btnStartScan.gridx = 1;
            gbc_btnStartScan.gridy = 4;
            this.add(this.btnStartScan, gbc_btnStartScan);
        }
        {
            this.progressBar = new JProgressBar();

            final GridBagConstraints gbc_progressBar = new GridBagConstraints();
            gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
            gbc_progressBar.insets = new Insets( 0, 0, 5, 5 );
            gbc_progressBar.gridx = 0;
            gbc_progressBar.gridy = 5;
            this.add( this.progressBar, gbc_progressBar );
        }
        {
            this.btnCancel = new JButton( "Cancel" );
            this.btnCancel.setActionCommand( ACTION_CANCEL );
            this.btnCancel.addActionListener( getActionListener() );
            this.btnCancel.setEnabled( false );

            final GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnCancel.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnCancel.gridx = 1;
            gbc_btnCancel.gridy = 5;
            this.add( this.btnCancel, gbc_btnCancel );
        }
        {
            final JScrollPane scrollPane_jTreeEmptyDirectories = new JScrollPane();
            final GridBagConstraints gbc_scrollPane_jTreeEmptyDirectories = new GridBagConstraints();
            gbc_scrollPane_jTreeEmptyDirectories.fill = GridBagConstraints.BOTH;
            gbc_scrollPane_jTreeEmptyDirectories.gridheight = 5;
            gbc_scrollPane_jTreeEmptyDirectories.insets = new Insets(0, 0, 0, 5);
            gbc_scrollPane_jTreeEmptyDirectories.gridx = 0;
            gbc_scrollPane_jTreeEmptyDirectories.gridy = 6;
            this.add(scrollPane_jTreeEmptyDirectories, gbc_scrollPane_jTreeEmptyDirectories);

            this.jTreeEmptyDirectories = createJTree(new SoftBevelBorder( BevelBorder.LOWERED, null, null, null, null ));
            scrollPane_jTreeEmptyDirectories.setViewportView(this.jTreeEmptyDirectories);
        }
        {
            this.btnSelectAll = new JButton( "Select All" );
            this.btnSelectAll.setIcon( resources.getSelectAllIcon() );
            this.btnSelectAll.setEnabled(false);
            this.btnSelectAll.setActionCommand( ACTION_SELECT_ALL_SELECTABLE_NODES );
            this.btnSelectAll.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnSelectAll = new GridBagConstraints();
            gbc_btnSelectAll.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSelectAll.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnSelectAll.gridx = 1;
            gbc_btnSelectAll.gridy = 6;
            this.add( this.btnSelectAll, gbc_btnSelectAll );
        }
        {
            this.btnSelectAllLeaf = new JButton("Select All Leaf");
            this.btnSelectAllLeaf.setEnabled(false);
            this.btnSelectAllLeaf.setActionCommand( ACTION_SELECT_ALL_LEAFONLY );
            this.btnSelectAllLeaf.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnSelectAllLeaf = new GridBagConstraints();
            gbc_btnSelectAllLeaf.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSelectAllLeaf.insets = new Insets(0, 0, 5, 0);
            gbc_btnSelectAllLeaf.gridx = 1;
            gbc_btnSelectAllLeaf.gridy = 7;
            add(this.btnSelectAllLeaf, gbc_btnSelectAllLeaf);
        }
        {
            this.btnDeselectAll = new JButton( "Deselect All" );
            this.btnDeselectAll.setIcon( resources.getDeselectAllIcon() );
            this.btnDeselectAll.setEnabled(false);
            this.btnDeselectAll.setActionCommand( ACTION_UNSELECT_ALL );
            this.btnDeselectAll.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnDeselectAll = new GridBagConstraints();
            gbc_btnDeselectAll.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnDeselectAll.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnDeselectAll.gridx = 1;
            gbc_btnDeselectAll.gridy = 8;
            this.add( this.btnDeselectAll, gbc_btnDeselectAll );
        }
        {
            this.separator = new JSeparator();
            final GridBagConstraints gbc_separator = new GridBagConstraints();
            gbc_separator.fill = GridBagConstraints.HORIZONTAL;
            gbc_separator.insets = new Insets( 0, 0, 5, 0 );
            gbc_separator.gridx = 1;
            gbc_separator.gridy = 9;
            this.add( this.separator, gbc_separator );
        }
        {
            this.btnStartDelete = new JButton( "Delete selected" );
            this.btnStartDelete.setEnabled(false);
            this.btnStartDelete.setActionCommand( ACTION_START_REMDIRS );
            this.btnStartDelete.addActionListener( getActionListener() );

            final GridBagConstraints gbc_btnStartDelete = new GridBagConstraints();
            gbc_btnStartDelete.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnStartDelete.gridx = 1;
            gbc_btnStartDelete.gridy = 10;
            this.add( this.btnStartDelete, gbc_btnStartDelete );
        }
    }

    protected abstract ActionListener getActionListener();
    protected abstract void addRootDirectory( List<File> files );

    /**
     * @wbp.factory
     * @wbp.factory.parameter.source border new javax.swing.border.SoftBevelBorder( javax.swing.border.BevelBorder.LOWERED, null, null,
                null, null )
     */
    public static JTree createJTree(final Border border)
    {
        final JTree tree = new JTree();
        tree.setBorder( border );
        tree.setModel( null );
        return tree;
    }

    /**
     * @wbp.factory
     */
    public static JList<File> createJList()
    {
        return new JList<>( new FileListModel() );
    }

    private void initScrollPaneFileDrop( final JScrollPane scrollPaneJList )
    {
        this.scrollPaneJListSimpleFileDrop =
                new SimpleFileDrop(
                    scrollPaneJList,
                    (final List<File> files) ->
                       Threads.start(
                           "scrollPaneJListSimpleFileDrop",
                           () -> addRootDirectory( files )
                           )
                    );
    }

    private SimpleFileDrop getScrollPaneFileDrop()
    {
        if( this.scrollPaneJListSimpleFileDrop == null ) {
            initScrollPaneFileDrop( this.scrollPaneJList );
        }
        return this.scrollPaneJListSimpleFileDrop;
    }

    public FileListModel getJListRootDirectoriesModel()
    {
        final JList<File>     list  = getJListRootDirectories();
        final ListModel<File> model = list.getModel();

        return (FileListModel)model;
    }

    protected JTree getJTreeEmptyDirectories()
    {
        return this.jTreeEmptyDirectories;
    }

    protected JList<File> getJListRootDirectories()
    {
        return this.jListRootDirectories;
    }

    protected JButton getBtnStartDelete()
    {
        return this.btnStartDelete;
    }

    protected JProgressBar getProgressBar()
    {
        return this.progressBar;
    }

    protected JButton getBtnCancel()
    {
        return this.btnCancel;
    }

    protected boolean isButtonAddRootDirectoryEnabled()
    {
        return this.btnAddRootDirectory.isEnabled();
    }

    protected boolean isButtonImportDirectoriesEnabled()
    {
        return this.btnImportDirectories.isEnabled();
    }

    protected boolean isButtonStartScanEnabled()
    {
        return this.btnStartScan.isEnabled();
    }

    protected boolean isButtonRemoveRootDirectoryEnabled()
    {
        return this.btnRemoveRootDirectory.isEnabled();
    }

    protected void setButtonRemoveRootDirectoryEnabled( final boolean b )
    {
        this.btnRemoveRootDirectory.setEnabled( b );
    }

    protected void setEnableFind( final boolean b )
    {
        this.btnStartScan.setEnabled( b );
        this.btnSelectAll.setEnabled( b );
        this.btnSelectAllLeaf.setEnabled( b );
        this.btnDeselectAll.setEnabled( b );
    }

    public void enable_startDelete()
    {
        getScrollPaneFileDrop().remove();

        this.btnAddRootDirectory.setEnabled( false );
        this.btnImportDirectories.setEnabled( false );
        this.btnRemoveRootDirectory.setEnabled( false );

        this.btnStartScan.setEnabled( false );
        this.btnCancel.setEnabled( true );

        this.btnSelectAll.setEnabled( false );
        this.btnSelectAllLeaf.setEnabled( false );
        this.btnDeselectAll.setEnabled( false );

        this.btnStartDelete.setEnabled( false );
    }

    protected void enable_findBegin()
    {
        getScrollPaneFileDrop().remove();

        this.btnAddRootDirectory.setEnabled( false );
        this.btnImportDirectories.setEnabled( false );
        this.btnRemoveRootDirectory.setEnabled( false );

        this.btnStartScan.setEnabled( false );
        this.btnCancel.setEnabled( true );

        this.btnSelectAll.setEnabled( false );
        this.btnSelectAllLeaf.setEnabled( false );
        this.btnDeselectAll.setEnabled( false );

        this.btnStartDelete.setEnabled( false );

        this.jListRootDirectories.setEnabled( false );
        this.jListRootDirectories.clearSelection();
    }

    protected void enable_findTaskDone()
    {
        try {
            getScrollPaneFileDrop().addDropTargetListener();
            }
        catch( HeadlessException | TooManyListenersException e ) {
            LOGGER.error( "Can not create Drop Listener", e );
            }

        this.btnAddRootDirectory.setEnabled( true );
        this.btnImportDirectories.setEnabled( true );
        this.btnRemoveRootDirectory.setEnabled( false );

        final ListModel<File> jListModelRootDirectories = this.jListRootDirectories.getModel();
        final boolean         hasRootDirs = jListModelRootDirectories.getSize() > 0;

        this.btnStartScan.setEnabled( hasRootDirs );
        this.btnCancel.setEnabled( false );

        this.btnStartDelete.setEnabled( hasRootDirs ); // FIXME: enable only when at least 1 file selected
        this.btnSelectAll.setEnabled( hasRootDirs );
        this.btnSelectAllLeaf.setEnabled( hasRootDirs );
        this.btnDeselectAll.setEnabled( hasRootDirs ); // FIXME: only if something selected

        this.jListRootDirectories.setEnabled( true );
    }

    protected boolean isBtnSelectAllEnabled()
    {
        return this.btnSelectAll.isEnabled();
    }

    protected boolean isBtnUnselectAllEnabled()
    {
        return this.btnDeselectAll.isEnabled();
    }
}
