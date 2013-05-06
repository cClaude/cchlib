package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.HeadlessException;
import javax.swing.JPanel;
import javax.swing.ListModel;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException; // $codepro.audit.disable unnecessaryImport
import javax.swing.JTree;
import javax.swing.JProgressBar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.dnd.SimpleFileDropListener;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import java.awt.event.ActionListener;

/**
 * Handle layout
 */
public abstract class RemoveEmptyDirectoriesPanelWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectoriesPanelWB.class );

    protected static final String ACTION_IMPORT_DIRS = "ACTION_IMPORT_DIRS";
    protected static final String ACTION_ADD_DIRS = "ACTION_ADD_DIRS";
    protected static final String ACTION_REMOVE_DIR = "ACTION_REMOVE_DIR";
    protected static final String ACTION_FIND_EMPTY_DIRS = "ACTION_FIND_EMPTY_DIRS";
    protected static final String ACTION_CANCEL          = "ACTION_CANCEL";
    protected static final String ACTION_SELECT_ALL_LEAFONLY            = "ACTION_SELECT_ALL_LEAFONLY";
    protected static final String ACTION_SELECT_ALL_SELECTABLE_NODES    = "ACTION_SELECT_ALL_SELECTABLE_NODES";
    protected static final String ACTION_UNSELECT_ALL  = "ACTION_UNSELECT_ALL";
    protected static final String ACTION_START_REMDIRS = "ACTION_START_REMDIRS";

    private JButton btnAddRootDirectory;
    private JButton btnCancel;
    private JButton btnImportDirectories;
    private JButton btnRemoveRootDirectory;
    private JButton btnSelectAll;
    private JButton btnSelectAllLeaf;
    private JButton btnUnselectAll;
    private JButton btnStartDelete;
    private JButton btnStartScan;
    private JList<File> jListRootDirectories;
    private JProgressBar progressBar;
    private JSeparator separator;
    private JTree jTreeEmptyDirectories;
    private SimpleFileDrop scrollPaneJListSimpleFileDrop;

    protected abstract ActionListener getActionListener();
    protected abstract void addRootDirectory( List<File> files );

    /**
     * Create the frame.
     */
    public RemoveEmptyDirectoriesPanelWB()
    {
        setSize( 800, 400 );

        {
            GridBagLayout gbl_contentPane = new GridBagLayout();
            gbl_contentPane.columnWidths = new int[]{0, 0, 0};
            gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0};
            this.setLayout(gbl_contentPane);
        }
        {
            btnAddRootDirectory = new JButton("Add directory to scan");
            btnAddRootDirectory.setActionCommand( ACTION_ADD_DIRS );
            btnAddRootDirectory.addActionListener( getActionListener() );

            GridBagConstraints gbc_btnAddRootDirectory = new GridBagConstraints();
            gbc_btnAddRootDirectory.anchor = GridBagConstraints.NORTH;
            gbc_btnAddRootDirectory.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnAddRootDirectory.insets = new Insets(0, 0, 5, 0);
            gbc_btnAddRootDirectory.gridx = 1;
            gbc_btnAddRootDirectory.gridy = 0;
            this.add(btnAddRootDirectory, gbc_btnAddRootDirectory);
        }
        {
            JScrollPane scrollPaneJList = new JScrollPane();
            GridBagConstraints gbc_scrollPaneJList = new GridBagConstraints();
            gbc_scrollPaneJList.fill = GridBagConstraints.BOTH;
            gbc_scrollPaneJList.gridheight = 5;
            gbc_scrollPaneJList.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPaneJList.gridx = 0;
            gbc_scrollPaneJList.gridy = 0;
            this.add(scrollPaneJList, gbc_scrollPaneJList);

            this.scrollPaneJListSimpleFileDrop = new SimpleFileDrop( scrollPaneJList, new SimpleFileDropListener()
            {
                @Override
                public void filesDropped( final List<File> files )
                {
                    new Thread( new Runnable() {
                        @Override
                        public void run()
                        {
                            addRootDirectory( files );
                        }}, "filesDropped()" ).start();
                }
            });

            jListRootDirectories = createJList();
            scrollPaneJList.setViewportView(jListRootDirectories);
        }
        {
            btnImportDirectories = new JButton("Import directories");
            btnImportDirectories.setActionCommand( ACTION_IMPORT_DIRS );
            btnImportDirectories.addActionListener( getActionListener() );
            GridBagConstraints gbc_btnImportDirectories = new GridBagConstraints();
            gbc_btnImportDirectories.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnImportDirectories.insets = new Insets(0, 0, 5, 0);
            gbc_btnImportDirectories.gridx = 1;
            gbc_btnImportDirectories.gridy = 1;
            this.add(btnImportDirectories, gbc_btnImportDirectories);
        }
        {
            btnRemoveRootDirectory = new JButton("Remove");
            btnRemoveRootDirectory.setActionCommand( ACTION_REMOVE_DIR );
            btnRemoveRootDirectory.addActionListener( getActionListener() );
            btnRemoveRootDirectory.setEnabled(false);

            GridBagConstraints gbc_btnRemoveRootDirectory = new GridBagConstraints();
            gbc_btnRemoveRootDirectory.anchor = GridBagConstraints.NORTH;
            gbc_btnRemoveRootDirectory.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnRemoveRootDirectory.insets = new Insets(0, 0, 5, 0);
            gbc_btnRemoveRootDirectory.gridx = 1;
            gbc_btnRemoveRootDirectory.gridy = 2;
            this.add(btnRemoveRootDirectory, gbc_btnRemoveRootDirectory);
        }
        {
            btnStartScan = new JButton("Find empty directories");
            btnStartScan.setActionCommand( ACTION_FIND_EMPTY_DIRS );
            btnStartScan.addActionListener( getActionListener() );
            btnStartScan.setEnabled(false);

            GridBagConstraints gbc_btnStartScan = new GridBagConstraints();
            gbc_btnStartScan.anchor = GridBagConstraints.NORTH;
            gbc_btnStartScan.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnStartScan.insets = new Insets(0, 0, 5, 0);
            gbc_btnStartScan.gridx = 1;
            gbc_btnStartScan.gridy = 4;
            this.add(btnStartScan, gbc_btnStartScan);
        }
        {
            progressBar = new JProgressBar();

            GridBagConstraints gbc_progressBar = new GridBagConstraints();
            gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
            gbc_progressBar.insets = new Insets( 0, 0, 5, 5 );
            gbc_progressBar.gridx = 0;
            gbc_progressBar.gridy = 5;
            this.add( progressBar, gbc_progressBar );
        }
        {
            btnCancel = new JButton( "Cancel" );
            btnCancel.setActionCommand( ACTION_CANCEL );
            btnCancel.addActionListener( getActionListener() );
            btnCancel.setEnabled( false );

            GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnCancel.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnCancel.gridx = 1;
            gbc_btnCancel.gridy = 5;
            this.add( btnCancel, gbc_btnCancel );
        }
        {
            JScrollPane scrollPane_jTreeEmptyDirectories = new JScrollPane();
            GridBagConstraints gbc_scrollPane_jTreeEmptyDirectories = new GridBagConstraints();
            gbc_scrollPane_jTreeEmptyDirectories.fill = GridBagConstraints.BOTH;
            gbc_scrollPane_jTreeEmptyDirectories.gridheight = 5;
            gbc_scrollPane_jTreeEmptyDirectories.insets = new Insets(0, 0, 0, 5);
            gbc_scrollPane_jTreeEmptyDirectories.gridx = 0;
            gbc_scrollPane_jTreeEmptyDirectories.gridy = 6;
            this.add(scrollPane_jTreeEmptyDirectories, gbc_scrollPane_jTreeEmptyDirectories);

            jTreeEmptyDirectories = createJTree(new SoftBevelBorder( BevelBorder.LOWERED, null, null, null, null ));
            scrollPane_jTreeEmptyDirectories.setViewportView(jTreeEmptyDirectories);
        }
        {
            btnSelectAll = new JButton( "Select All" );
            this.btnSelectAll.setEnabled(false);
            btnSelectAll.setActionCommand( ACTION_SELECT_ALL_SELECTABLE_NODES );
            btnSelectAll.addActionListener( getActionListener() );

            GridBagConstraints gbc_btnSelectAll = new GridBagConstraints();
            gbc_btnSelectAll.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSelectAll.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnSelectAll.gridx = 1;
            gbc_btnSelectAll.gridy = 6;
            this.add( btnSelectAll, gbc_btnSelectAll );
        }
        {
            btnSelectAllLeaf = new JButton("Select All Leaf");
            this.btnSelectAllLeaf.setEnabled(false);
            btnSelectAllLeaf.setActionCommand( ACTION_SELECT_ALL_LEAFONLY );
            btnSelectAllLeaf.addActionListener( getActionListener() );

            GridBagConstraints gbc_btnSelectAllLeaf = new GridBagConstraints();
            gbc_btnSelectAllLeaf.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSelectAllLeaf.insets = new Insets(0, 0, 5, 0);
            gbc_btnSelectAllLeaf.gridx = 1;
            gbc_btnSelectAllLeaf.gridy = 7;
            add(btnSelectAllLeaf, gbc_btnSelectAllLeaf);
        }
        {
            btnUnselectAll = new JButton( "Unselect All" );
            this.btnUnselectAll.setEnabled(false);
            btnUnselectAll.setActionCommand( ACTION_UNSELECT_ALL );
            btnUnselectAll.addActionListener( getActionListener() );

            GridBagConstraints gbc_btnUnselectAll = new GridBagConstraints();
            gbc_btnUnselectAll.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnUnselectAll.insets = new Insets( 0, 0, 5, 0 );
            gbc_btnUnselectAll.gridx = 1;
            gbc_btnUnselectAll.gridy = 8;
            this.add( btnUnselectAll, gbc_btnUnselectAll );
        }
        {
            separator = new JSeparator();
            GridBagConstraints gbc_separator = new GridBagConstraints();
            gbc_separator.fill = GridBagConstraints.HORIZONTAL;
            gbc_separator.insets = new Insets( 0, 0, 5, 0 );
            gbc_separator.gridx = 1;
            gbc_separator.gridy = 9;
            this.add( separator, gbc_separator );
        }
        {
            btnStartDelete = new JButton( "Delete selected" );
            this.btnStartDelete.setEnabled(false);
            btnStartDelete.setActionCommand( ACTION_START_REMDIRS );
            btnStartDelete.addActionListener( getActionListener() );

            GridBagConstraints gbc_btnStartDelete = new GridBagConstraints();
            gbc_btnStartDelete.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnStartDelete.gridx = 1;
            gbc_btnStartDelete.gridy = 10;
            this.add( btnStartDelete, gbc_btnStartDelete );
        }
    }

    /**
     * @wbp.factory
     * @wbp.factory.parameter.source border new javax.swing.border.SoftBevelBorder( javax.swing.border.BevelBorder.LOWERED, null, null,
                null, null )
     */
    public static JTree createJTree(Border border)
    {
        JTree tree = new JTree();
        tree.setBorder( border );
        tree.setModel( null );
        return tree;
    }

    /**
     * @wbp.factory
     */
    public static JList<File> createJList()
    {
        MyDefaultListModel<File>    model   = new MyDefaultListModel<File>();
        JList<File>                 list    = new JList<File>( model );

        return list;
    }

    public MyDefaultListModel<File> getJListRootDirectoriesModel()
    {
        JList<File>     list    = getJListRootDirectories();
        ListModel<File> lmodel = list.getModel();

        @SuppressWarnings("unchecked")
        MyDefaultListModel<File> model = MyDefaultListModel.class.cast( lmodel );
        return model;
    }

    protected JTree getJTreeEmptyDirectories()
    {
        return jTreeEmptyDirectories;
    }
    protected JList<File> getJListRootDirectories()
    {
        return jListRootDirectories;
    }
    protected JButton getBtnStartDelete()
    {
        return btnStartDelete;
    }
    protected JProgressBar getProgressBar()
    {
        return progressBar;
    }
    protected JButton getBtnCancel()
    {
        return btnCancel;
    }

    protected boolean isButtonAddRootDirectoryEnabled()
    {
        return btnAddRootDirectory.isEnabled();
    }

    protected boolean isButtonImportDirectoriesEnabled()
    {
        return btnImportDirectories.isEnabled();
    }

    protected boolean isButtonStartScanEnabled()
    {
        return btnStartScan.isEnabled();
    }

    protected boolean isButtonRemoveRootDirectoryEnabled()
    {
        return btnRemoveRootDirectory.isEnabled();
    }

    protected void setButtonRemoveRootDirectoryEnabled( boolean b )
    {
        btnRemoveRootDirectory.setEnabled( b );
    }

    protected void setEnableFind( boolean b )
    {
        this.btnStartScan.setEnabled( b );
        this.btnSelectAll.setEnabled( b );
        this.btnSelectAllLeaf.setEnabled( b );
        this.btnUnselectAll.setEnabled( b );       
    }

    public void enable_startDelete()
    {
        scrollPaneJListSimpleFileDrop.remove();
        btnAddRootDirectory.setEnabled( false );
        btnImportDirectories.setEnabled( false );
        btnRemoveRootDirectory.setEnabled( false );

        btnStartScan.setEnabled( false );
        btnCancel.setEnabled( true );

        btnSelectAll.setEnabled( false );
        btnSelectAllLeaf.setEnabled( false );
        btnUnselectAll.setEnabled( false );

        btnStartDelete.setEnabled( false );
    }

    protected void enable_findBegin()
    {
        scrollPaneJListSimpleFileDrop.remove();

        btnAddRootDirectory.setEnabled( false );
        btnImportDirectories.setEnabled( false );
        btnRemoveRootDirectory.setEnabled( false );

        btnStartScan.setEnabled( false );
        btnCancel.setEnabled( true );

        btnSelectAll.setEnabled( false );
        btnSelectAllLeaf.setEnabled( false );
        btnUnselectAll.setEnabled( false );

        btnStartDelete.setEnabled( false );

        jListRootDirectories.setEnabled( false );
        jListRootDirectories.clearSelection();
    }

    protected void enable_findTaskDone()
    {
        try {
            scrollPaneJListSimpleFileDrop.addDropTargetListener();
            }
        catch( HeadlessException | TooManyListenersException e ) {
            logger.error( "Can not create Drop Listener", e );
            }

        btnAddRootDirectory.setEnabled( true );
        btnImportDirectories.setEnabled( true );
        btnRemoveRootDirectory.setEnabled( false );

        ListModel<File> jListModelRootDirectories = jListRootDirectories.getModel();
        boolean         hasRootDirs = jListModelRootDirectories.getSize() > 0;
        
        btnStartScan.setEnabled( hasRootDirs );
        btnCancel.setEnabled( false );

        btnStartDelete.setEnabled( hasRootDirs ); // FIXME: enable only when at least 1 file selected
        btnSelectAll.setEnabled( hasRootDirs );
        btnSelectAllLeaf.setEnabled( hasRootDirs );
        btnUnselectAll.setEnabled( hasRootDirs ); // FIXME: only if something selected
        
        jListRootDirectories.setEnabled( true );
    }
    
    protected boolean isBtnSelectAllEnabled()
    {
        return btnSelectAll.isEnabled();
    }
    
    protected boolean isBtnUnselectAllEnabled()
    {
        return btnUnselectAll.isEnabled();
    }
}
