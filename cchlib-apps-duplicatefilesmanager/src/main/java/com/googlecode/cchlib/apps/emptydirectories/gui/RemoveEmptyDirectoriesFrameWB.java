package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.JTree;
import javax.swing.JProgressBar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.dnd.SimpleFileDropListener;
import java.awt.event.MouseAdapter;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;

/**
 * Handle layout
 */
public abstract class RemoveEmptyDirectoriesFrameWB extends JFrame
{
    private static final long serialVersionUID = 2L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectoriesFrameWB.class );
    protected static final String ACTION_IMPORT_DIRS = "ACTION_IMPORT_DIRS";
    protected static final String ACTION_ADD_DIRS = "ACTION_ADD_DIRS";
    private JButton btnAddRootDirectory;
    private JButton btnCancel;
    private JButton btnImportDirectories;
    private JButton btnRemoveRootDirectory;
    private JButton btnSelectAll;
    private JButton btnStartDelete;
    private JButton btnStartScan;
    private JButton btnUnselectAll;
    private JList<File> jListRootDirectories;
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JScrollPane scrollPaneJList;
    private JScrollPane scrollPane_1;
    private JSeparator separator;
    private JTree jTreeEmptyDirectories;
    private SimpleFileDrop scrollPaneJListSimpleFileDrop;

    protected abstract void addRootDirectory( List<File> files );
    protected abstract void btnRemoveRootDirectory_mouseClicked( MouseEvent e );
    protected abstract void btnStartScan_mouseClicked(MouseEvent event);
    protected abstract void btnCancel_mouseClicked( MouseEvent event );
    protected abstract void btnSelectAll_mouseClicked( MouseEvent event );
    protected abstract void btnUnselectAll_mouseClicked( MouseEvent event );
    protected abstract void btnStartDelete_mouseClicked( MouseEvent event );
    protected abstract ActionListener getActionListener();
    
    /**
     * Create the frame.
     */
    public RemoveEmptyDirectoriesFrameWB()
    {
        setSize( 800, 400 );
        contentPane = new JPanel();
        contentPane.setBorder( new CompoundBorder() );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0};
        contentPane.setLayout(gbl_contentPane);

        btnAddRootDirectory = new JButton("Add directory to scan");
        btnAddRootDirectory.setActionCommand( ACTION_ADD_DIRS );
        btnAddRootDirectory.addActionListener( getActionListener() );

        scrollPaneJList = new JScrollPane();
        GridBagConstraints gbc_scrollPaneJList = new GridBagConstraints();
        gbc_scrollPaneJList.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneJList.gridheight = 5;
        gbc_scrollPaneJList.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPaneJList.gridx = 0;
        gbc_scrollPaneJList.gridy = 0;
        contentPane.add(scrollPaneJList, gbc_scrollPaneJList);

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
                    }} ).start();
            }
        });
        
        jListRootDirectories = createJList();
        scrollPaneJList.setViewportView(jListRootDirectories);

        GridBagConstraints gbc_btnAddRootDirectory = new GridBagConstraints();
        gbc_btnAddRootDirectory.anchor = GridBagConstraints.NORTH;
        gbc_btnAddRootDirectory.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnAddRootDirectory.insets = new Insets(0, 0, 5, 0);
        gbc_btnAddRootDirectory.gridx = 1;
        gbc_btnAddRootDirectory.gridy = 0;
        contentPane.add(btnAddRootDirectory, gbc_btnAddRootDirectory);

        btnRemoveRootDirectory = new JButton("Remove");
        btnRemoveRootDirectory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnRemoveRootDirectory_mouseClicked( e );
            }
        });
        
        btnImportDirectories = new JButton("Import directories");
        btnImportDirectories.setActionCommand( ACTION_IMPORT_DIRS );
        btnImportDirectories.addActionListener( getActionListener() );
        GridBagConstraints gbc_btnImportDirectories = new GridBagConstraints();
        gbc_btnImportDirectories.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnImportDirectories.insets = new Insets(0, 0, 5, 0);
        gbc_btnImportDirectories.gridx = 1;
        gbc_btnImportDirectories.gridy = 1;
        contentPane.add(btnImportDirectories, gbc_btnImportDirectories);
        btnRemoveRootDirectory.setEnabled(false);
        GridBagConstraints gbc_btnRemoveRootDirectory = new GridBagConstraints();
        gbc_btnRemoveRootDirectory.anchor = GridBagConstraints.NORTH;
        gbc_btnRemoveRootDirectory.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnRemoveRootDirectory.insets = new Insets(0, 0, 5, 0);
        gbc_btnRemoveRootDirectory.gridx = 1;
        gbc_btnRemoveRootDirectory.gridy = 2;
        contentPane.add(btnRemoveRootDirectory, gbc_btnRemoveRootDirectory);

        btnStartScan = new JButton("Find empty directories");
        btnStartScan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnStartScan_mouseClicked( e );
            }
        });
        btnStartScan.setEnabled(false);

        GridBagConstraints gbc_btnStartScan = new GridBagConstraints();
        gbc_btnStartScan.anchor = GridBagConstraints.NORTH;
        gbc_btnStartScan.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnStartScan.insets = new Insets(0, 0, 5, 0);
        gbc_btnStartScan.gridx = 1;
        gbc_btnStartScan.gridy = 4;
        contentPane.add(btnStartScan, gbc_btnStartScan);

        progressBar = new JProgressBar();
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets( 0, 0, 5, 5 );
        gbc_progressBar.gridx = 0;
        gbc_progressBar.gridy = 5;
        contentPane.add( progressBar, gbc_progressBar );

        btnCancel = new JButton( "Cancel" );
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnCancel_mouseClicked( e );
            }
        });
        btnCancel.setEnabled( false );
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCancel.insets = new Insets( 0, 0, 5, 0 );
        gbc_btnCancel.gridx = 1;
        gbc_btnCancel.gridy = 5;
        contentPane.add( btnCancel, gbc_btnCancel );

        scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridheight = 4;
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 6;
        contentPane.add(scrollPane_1, gbc_scrollPane_1);

        jTreeEmptyDirectories = createJTree(new SoftBevelBorder( BevelBorder.LOWERED, null, null, null, null ));
        scrollPane_1.setViewportView(jTreeEmptyDirectories);

        btnSelectAll = new JButton( "Select All" );
        btnSelectAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnSelectAll_mouseClicked( e );
            }
        });
        GridBagConstraints gbc_btnSelectAll = new GridBagConstraints();
        gbc_btnSelectAll.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSelectAll.insets = new Insets( 0, 0, 5, 0 );
        gbc_btnSelectAll.gridx = 1;
        gbc_btnSelectAll.gridy = 6;
        contentPane.add( btnSelectAll, gbc_btnSelectAll );

        btnUnselectAll = new JButton( "Unselect All" );
        btnUnselectAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnUnselectAll_mouseClicked( e );
            }
        });
        GridBagConstraints gbc_btnUnselectAll = new GridBagConstraints();
        gbc_btnUnselectAll.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnUnselectAll.insets = new Insets( 0, 0, 5, 0 );
        gbc_btnUnselectAll.gridx = 1;
        gbc_btnUnselectAll.gridy = 7;
        contentPane.add( btnUnselectAll, gbc_btnUnselectAll );

        btnStartDelete = new JButton( "Delete selected" );
        btnStartDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnStartDelete_mouseClicked( e );
            }
        });

        separator = new JSeparator();
        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator.insets = new Insets( 0, 0, 5, 0 );
        gbc_separator.gridx = 1;
        gbc_separator.gridy = 8;
        contentPane.add( separator, gbc_separator );
        GridBagConstraints gbc_btnStartDelete = new GridBagConstraints();
        gbc_btnStartDelete.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnStartDelete.gridx = 1;
        gbc_btnStartDelete.gridy = 9;
        contentPane.add( btnStartDelete, gbc_btnStartDelete );
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

    protected JButton getBtnSelectAll()
    {
        return btnSelectAll;
    }
    protected JButton getBtnUnselectAll() 
    {
        return btnUnselectAll;
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
    
    public void enable_startDelete()
    {
        scrollPaneJListSimpleFileDrop.remove();
        btnAddRootDirectory.setEnabled( false );
        btnImportDirectories.setEnabled( false );
        btnRemoveRootDirectory.setEnabled( false );

        btnStartScan.setEnabled( false );
        btnCancel.setEnabled( true );

        btnSelectAll.setEnabled( false );
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

        btnStartScan.setEnabled( true );
        btnCancel.setEnabled( false );
        
        btnStartDelete.setEnabled( true ); // FIXME: enable only when at least 1 file selected
        btnSelectAll.setEnabled( true );
        btnUnselectAll.setEnabled( true ); // FIXME: only if something selected
        jListRootDirectories.setEnabled( true );
    }
}
