package cx.ath.choisnet.tools.emptydirectories.gui;

import java.awt.EventQueue;
import javax.swing.DefaultListModel;
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
import javax.swing.JTree;
import javax.swing.JProgressBar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import org.apache.log4j.Logger;
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
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    RemoveEmptyDirectoriesFrameWB frame = new RemoveEmptyDirectoriesFrameWB()
                    {
                        private static final long serialVersionUID = 1L;
                        @Override
                        protected void btnAddRootDirectory_mouseClicked( MouseEvent e )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnRemoveRootDirectory_mouseClicked( MouseEvent e )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnStartScan_mouseClicked( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnCancel_mouseClicked( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnSelectAll_mouseClicked( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnUnselectAll_mouseClicked( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                        @Override
                        protected void btnStartDelete_mouseClicked( MouseEvent event )
                        {
                            throw new UnsupportedOperationException();
                        }
                    };
                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    logger.fatal( "Fatal error while creating frame", e );
                    }
            }
        } );
    }
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
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 20, 20, 800, 400 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 3.0, 0.0};
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

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridheight = 3;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        contentPane.add(scrollPane, gbc_scrollPane);

        jListRootDirectories = createJList();
        scrollPane.setViewportView(jListRootDirectories);

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
        gbc_btnStartScan.gridy = 2;
        contentPane.add(btnStartScan, gbc_btnStartScan);

        progressBar = new JProgressBar();
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets( 0, 0, 5, 5 );
        gbc_progressBar.gridx = 0;
        gbc_progressBar.gridy = 3;
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
        gbc_btnCancel.gridy = 3;
        contentPane.add( btnCancel, gbc_btnCancel );
        
        scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridheight = 4;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 4;
        contentPane.add(scrollPane_1, gbc_scrollPane_1);

        jTreeEmptyDirectories = createJTree(new SoftBevelBorder( BevelBorder.LOWERED, null, null,
                        null, null ));
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
        gbc_btnSelectAll.gridy = 4;
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
        gbc_btnUnselectAll.gridy = 5;
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
        gbc_separator.gridy = 6;
        contentPane.add( separator, gbc_separator );
        GridBagConstraints gbc_btnStartDelete = new GridBagConstraints();
        gbc_btnStartDelete.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnStartDelete.gridx = 1;
        gbc_btnStartDelete.gridy = 7;
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
        DefaultListModel<File>  model = new DefaultListModel<File>();
        JList<File>             list = new JList<File>( model );

        return list;
    }

    public DefaultListModel<File> getJListRootDirectoriesModel()
    {
        JList<File>     list    = getJListRootDirectories();
        ListModel<File> lmodel = list.getModel();

        @SuppressWarnings("unchecked")
        DefaultListModel<File> model = DefaultListModel.class.cast( lmodel );
        return model;
    }

    protected JButton getBtnStartScan() {
        return btnStartScan;
    }
    protected JButton getBtnRemoveRootDirectory() {
        return btnRemoveRootDirectory;
    }
    protected JButton getBtnAddRootDirectory() {
        return btnAddRootDirectory;
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

}
