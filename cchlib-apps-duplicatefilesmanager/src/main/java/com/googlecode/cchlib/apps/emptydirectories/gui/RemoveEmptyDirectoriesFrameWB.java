package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
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

public abstract class RemoveEmptyDirectoriesFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectoriesFrameWB.class );
    private JPanel contentPane;
    private JButton btnStartScan;
    private JButton btnRemoveRootDirectory;
    private JButton btnAddRootDirectory;
    private JButton btnSelectAll;
    private JButton btnUnselectAll;
    private JButton btnStartDelete;
    private JTree jTreeEmptyDirectories;
    private JProgressBar progressBar;
    private JSeparator separator;
    private JButton btnCancel;
    private JList<File> jListRootDirectories;
    private JScrollPane scrollPaneJList;
    private JScrollPane scrollPane_1;
    private SimpleFileDrop scrollPaneJListSimpleFileDrop;

//    /**
//     * Launch the application (debug only).
//     */
//    public static void main( String[] args )
//    {
//        EventQueue.invokeLater( new Runnable() {
//            public void run()
//            {
//                try {
//                    RemoveEmptyDirectoriesFrameWB frame = new RemoveEmptyDirectoriesFrameWB()
//                    {
//                        private static final long serialVersionUID = 1L;
//                        @Override
//                        protected void btnAddRootDirectory_mouseClicked( MouseEvent e )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnRemoveRootDirectory_mouseClicked( MouseEvent e )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnStartScan_mouseClicked( MouseEvent event )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnCancel_mouseClicked( MouseEvent event )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnSelectAll_mouseClicked( MouseEvent event )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnUnselectAll_mouseClicked( MouseEvent event )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void btnStartDelete_mouseClicked( MouseEvent event )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                        @Override
//                        protected void addRootDirectory( List<File> files )
//                        {
//                            throw new UnsupportedOperationException();
//                        }
//                    };
//                    frame.setVisible( true );
//                    }
//                catch( Exception e ) {
//                    e.printStackTrace();
//                    logger.fatal( "Fatal error while creating frame", e );
//                    }
//            }
//        } );
//    }
    protected abstract void addRootDirectory( List<File> files );
    protected abstract void btnAddRootDirectory_mouseClicked( MouseEvent e );
    protected abstract void btnRemoveRootDirectory_mouseClicked( MouseEvent e );
    protected abstract void btnStartScan_mouseClicked(MouseEvent event);
    protected abstract void btnCancel_mouseClicked( MouseEvent event );
    protected abstract void btnSelectAll_mouseClicked( MouseEvent event );
    protected abstract void btnUnselectAll_mouseClicked( MouseEvent event );
    protected abstract void btnStartDelete_mouseClicked( MouseEvent event );

    /**
     * Create the frame.
     */
    public RemoveEmptyDirectoriesFrameWB()
    {
        //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //setBounds( 20, 20, 800, 400 );
        setSize( 800, 400 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0};
        contentPane.setLayout(gbl_contentPane);

//        jListRootDirectories = RemoveEmptyDirectoriesFrameWB.createJList();
//        GridBagConstraints gbc_jListRootDirectories = new GridBagConstraints();
//        gbc_jListRootDirectories.gridheight = 3;
//        gbc_jListRootDirectories.insets = new Insets(0, 0, 5, 5);
//        gbc_jListRootDirectories.fill = GridBagConstraints.BOTH;
//        gbc_jListRootDirectories.gridx = 0;
//        gbc_jListRootDirectories.gridy = 0;
//        contentPane.add(jListRootDirectories, gbc_jListRootDirectories);

        btnAddRootDirectory = new JButton("Add directory to scan");
        btnAddRootDirectory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnAddRootDirectory_mouseClicked( e );
            }
        });
        
        scrollPaneJList = new JScrollPane();
        GridBagConstraints gbc_scrollPaneJList = new GridBagConstraints();
        gbc_scrollPaneJList.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneJList.gridheight = 4;
        gbc_scrollPaneJList.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPaneJList.gridx = 0;
        gbc_scrollPaneJList.gridy = 0;
        contentPane.add(scrollPaneJList, gbc_scrollPaneJList);

//        try {
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
//                this.scrollPaneJListSimpleFileDrop.addDropTargetListener();
//            }
//        catch( HeadlessException /*| TooManyListenersException e */ ) {
//            logger.error( "Can not create Drop Listener", e );
//            }

        jListRootDirectories = createJList();
        scrollPaneJList.setViewportView(jListRootDirectories);

        GridBagConstraints gbc_btnAddRootDirectory = new GridBagConstraints();
        gbc_btnAddRootDirectory.anchor = GridBagConstraints.PAGE_START;
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
        btnRemoveRootDirectory.setEnabled(false);
        GridBagConstraints gbc_btnRemoveRootDirectory = new GridBagConstraints();
        gbc_btnRemoveRootDirectory.anchor = GridBagConstraints.NORTH;
        gbc_btnRemoveRootDirectory.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnRemoveRootDirectory.insets = new Insets(0, 0, 5, 0);
        gbc_btnRemoveRootDirectory.gridx = 1;
        gbc_btnRemoveRootDirectory.gridy = 1;
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
        gbc_btnStartScan.gridy = 3;
        contentPane.add(btnStartScan, gbc_btnStartScan);

        progressBar = new JProgressBar();
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets( 0, 0, 5, 5 );
        gbc_progressBar.gridx = 0;
        gbc_progressBar.gridy = 4;
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
        gbc_btnCancel.gridy = 4;
        contentPane.add( btnCancel, gbc_btnCancel );

        scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridheight = 4;
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 5;
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
        gbc_btnSelectAll.gridy = 5;
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
        gbc_btnUnselectAll.gridy = 6;
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
        gbc_separator.insets = new Insets( 0, 0, 5, 0 );
        gbc_separator.gridx = 1;
        gbc_separator.gridy = 7;
        contentPane.add( separator, gbc_separator );
        GridBagConstraints gbc_btnStartDelete = new GridBagConstraints();
        gbc_btnStartDelete.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnStartDelete.gridx = 1;
        gbc_btnStartDelete.gridy = 8;
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


    protected JButton getBtnSelectAll() {
        return btnSelectAll;
    }
    protected JButton getBtnUnselectAll() {
        return btnUnselectAll;
    }
    protected JTree getJTreeEmptyDirectories() {
        return jTreeEmptyDirectories;
    }
    protected JList<File> getJListRootDirectories() {
        return jListRootDirectories;
    }
    protected JButton getBtnStartDelete() {
        return btnStartDelete;
    }
    protected JProgressBar getProgressBar() {
        return progressBar;
    }
    protected JButton getBtnCancel() {
        return btnCancel;
    }
    
    protected boolean isButtonAddRootDirectoryEnabled()
    {
        return btnAddRootDirectory.isEnabled();
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
        btnRemoveRootDirectory.setEnabled( false );

        btnStartScan.setEnabled( true );
        btnCancel.setEnabled( false );
        
        btnStartDelete.setEnabled( true ); // FIXME: enable only when at least 1 file selected
        btnSelectAll.setEnabled( true );
        btnUnselectAll.setEnabled( true ); // FIXME: only if something selected
        jListRootDirectories.setEnabled( true );
    }
}
