// $codepro.audit.disable avoidInstantiationInLoops, returnValue
package com.googlecode.cchlib.apps.emptydirectories.debug.sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.apache.log4j.Logger;

public class FileTreeViewer extends JFrame
{
    private static final long                  serialVersionUID    = 1L;
    private static final Logger                LOGGER              = Logger.getLogger( FileTreeViewer.class );

    public static final ImageIcon              ICON_COMPUTER       = new ImageIcon( "" );
    public static final ImageIcon              ICON_DISK           = new ImageIcon( "defaults1.png" );
    public static final ImageIcon              ICON_FOLDER         = new ImageIcon( "fol_orig.png" );
    public static final ImageIcon              ICON_EXPANDEDFOLDER = new ImageIcon( "folder_open.png" );

    protected JTree                            m_tree;
    protected DefaultTreeModel                 m_model;

    AddCheckBoxToTree                          addCh               = new AddCheckBoxToTree();

    private AddCheckBoxToTree.CheckTreeManager checkTreeManager;

    protected TreePath                         m_clickedPath;

    public FileTreeViewer()
    {
        super( "Demo tree check box" );

        setSize( 400, 300 ); // $codepro.audit.disable numericLiterals

        final DefaultMutableTreeNode top = new DefaultMutableTreeNode(
                new IconData( ICON_COMPUTER, null, "Computer" )
                );

        DefaultMutableTreeNode node;

        final File[] roots = File.listRoots();

        for (final File root : roots) {
            node = new DefaultMutableTreeNode(new IconData(ICON_DISK, null, new FileNode(root)));
            top.add( node );
            node.add( new DefaultMutableTreeNode( new Boolean( true ) ) );
        }

        this.m_model = new DefaultTreeModel( top );

        this.m_tree = new JTree( this.m_model ) {
            private static final long serialVersionUID = 1L;

            @Override
            public String getToolTipText( final MouseEvent ev )
            {
                if( ev == null ) {
                    return null;
                }
                final TreePath path = FileTreeViewer.this.m_tree
                        .getPathForLocation( ev.getX(), ev.getY() );
                if( path != null ) {
                    final FileNode fnode = getFileNode( getTreeNode( path ) );
                    if( fnode == null ) {
                        return null;
                    }
                    final File f = fnode.getFile();
                    return (f == null) ? null : f.getPath();
                }
                return null;
            }
        };

        ToolTipManager.sharedInstance().registerComponent( this.m_tree );

        this.m_tree.putClientProperty( "JTree.lineStyle", "Angled" );

        final TreeCellRenderer renderer = new IconCellRenderer();
        this.m_tree.setCellRenderer( renderer );

        this.m_tree.addTreeExpansionListener( new DirExpansionListener() );

        this.m_tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION );
        this.m_tree.setShowsRootHandles( true );
        this.m_tree.setEditable( false );

        this.checkTreeManager = this.addCh.new CheckTreeManager( this.m_tree, null );

        final JScrollPane s = new JScrollPane();
        s.getViewport().add( this.m_tree );
        getContentPane().add( s, BorderLayout.CENTER );

        final WindowListener wndCloser = new WindowAdapter() {
            @Override
            public void windowClosing( final WindowEvent e )
            {
                System.exit( 0 );
            }
        };

        addWindowListener( wndCloser );

        setVisible( true );
    }

    DefaultMutableTreeNode getTreeNode( final TreePath path )
    {
        return (DefaultMutableTreeNode)(path.getLastPathComponent());
    }

    FileNode getFileNode( final DefaultMutableTreeNode node )
    {
        if( node == null ) {
            return null;
        }
        Object obj = node.getUserObject();
        if( obj instanceof IconData ) {
            obj = ((IconData)obj).getObject();
        }
        if( obj instanceof FileNode ) {
            return (FileNode)obj;
        } else {
            return null;
        }
    }

    public AddCheckBoxToTree.CheckTreeManager getCheckTreeManager()
    {
        return this.checkTreeManager;
    }

    // Make sure expansion is threaded and updating the tree model
    // only occurs within the event dispatching thread.
    class DirExpansionListener implements TreeExpansionListener {
        @Override
        public void treeExpanded( final TreeExpansionEvent event )
        {
            final DefaultMutableTreeNode node = getTreeNode( event.getPath() );
            final FileNode fnode = getFileNode( node );

            final Thread runner = new Thread() {
                @Override
                public void run()
                {
                    if( (fnode != null) && fnode.expand( node ) ) {
                        final Runnable runnable = () -> {
                            FileTreeViewer.this.m_model.reload( node );
                        };
                        SwingUtilities.invokeLater( runnable );
                    }
                }
            };
            runner.start();
        }

        @Override
        public void treeCollapsed( final TreeExpansionEvent event )
        {
            // Not use
        }
    }

    public static void main( final String[] argv )
    {
        try {
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
        }
        catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
            LOGGER.warn( "UIManager.setLookAndFeel", e );
        }

        new FileTreeViewer();
    }
}

class IconCellRenderer extends JLabel implements TreeCellRenderer {
    private static final long serialVersionUID = 1L;
    protected Color           m_textSelectionColor;
    protected Color           m_textNonSelectionColor;
    protected Color           m_bkSelectionColor;
    protected Color           m_bkNonSelectionColor;
    protected Color           m_borderSelectionColor;

    protected boolean         m_selected;

    public IconCellRenderer()
    {
        super();
        this.m_textSelectionColor = UIManager.getColor( "Tree.selectionForeground" );
        this.m_textNonSelectionColor = UIManager.getColor( "Tree.textForeground" );
        this.m_bkSelectionColor = UIManager.getColor( "Tree.selectionBackground" );
        this.m_bkNonSelectionColor = UIManager.getColor( "Tree.textBackground" );
        this.m_borderSelectionColor = UIManager
                .getColor( "Tree.selectionBorderColor" );
        setOpaque( false );
    }

    @Override
    public Component getTreeCellRendererComponent( final JTree tree, final Object value,
            final boolean sel, final boolean expanded, final boolean leaf, final int row,
            final boolean hasFocus )

    {
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        final Object obj = node.getUserObject();
        setText( obj.toString() );

        if( obj instanceof Boolean ) {
            setText( "Retrieving data..." );
        }

        if( obj instanceof IconData ) {
            final IconData idata = (IconData)obj;
            if( expanded ) {
                setIcon( idata.getExpandedIcon() );
            } else {
                setIcon( idata.getIcon() );
            }
        } else {
            setIcon( null );
        }

        setFont( tree.getFont() );
        setForeground( sel ? this.m_textSelectionColor : this.m_textNonSelectionColor );
        setBackground( sel ? this.m_bkSelectionColor : this.m_bkNonSelectionColor );
        this.m_selected = sel;
        return this;
    }

    @Override
    public void paintComponent( final Graphics g )
    {
        final Color bColor = getBackground();
        final Icon icon = getIcon();

        g.setColor( bColor );
        int offset = 0;
        if( (icon != null) && (getText() != null) ) {
            offset = (icon.getIconWidth() + getIconTextGap());
        }
        g.fillRect( offset, 0, getWidth() - 1 - offset, getHeight() - 1 );

        if( this.m_selected ) {
            g.setColor( this.m_borderSelectionColor );
            g.drawRect( offset, 0, getWidth() - 1 - offset, getHeight() - 1 );
        }

        super.paintComponent( g );
    }
}

class IconData {
    protected Icon   m_icon;
    protected Icon   m_expandedIcon;
    protected Object m_data;

    public IconData( final Icon icon, final Object data )
    {
        this.m_icon = icon;
        this.m_expandedIcon = null;
        this.m_data = data;
    }

    public IconData( final Icon icon, final Icon expandedIcon, final Object data )
    {
        this.m_icon = icon;
        this.m_expandedIcon = expandedIcon;
        this.m_data = data;
    }

    public Icon getIcon()
    {
        return this.m_icon;
    }

    public Icon getExpandedIcon()
    {
        return (this.m_expandedIcon != null) ? this.m_expandedIcon : this.m_icon;
    }

    public Object getObject()
    {
        return this.m_data;
    }

    @Override
    public String toString()
    {
        return this.m_data.toString();
    }
}

class FileNode {
    protected File m_file;

    public FileNode( final File file )
    {
        this.m_file = file;
    }

    public File getFile()
    {
        return this.m_file;
    }

    @Override
    public String toString()
    {
        return (this.m_file.getName().length() > 0) ? this.m_file.getName() : this.m_file
                .getPath();
    }

    public boolean expand( final DefaultMutableTreeNode parent )
    {
        final DefaultMutableTreeNode flag = (DefaultMutableTreeNode)parent
                .getFirstChild();
        if( flag == null ) {
            return false;
        }
        final Object obj = flag.getUserObject();
        if( !(obj instanceof Boolean) )
         {
            return false; // Already expanded
        }

        parent.removeAllChildren(); // Remove Flag

        final File[] files = listFiles();
        if( files == null ) {
            return true;
        }

        final Vector<FileNode> v = new Vector<>();

        for (final File f : files) {
            if( !(f.isDirectory()) ) {
                continue;
            }

            final FileNode newNode = new FileNode( f );

            boolean isAdded = false;
            for( int i = 0; i < v.size(); i++ ) {
                final FileNode nd = v.elementAt( i );
                if( newNode.compareTo( nd ) < 0 ) {
                    v.insertElementAt( newNode, i );
                    isAdded = true;
                    break;
                }
            }
            if( !isAdded ) {
                v.addElement( newNode );
            }
        }

        for( int i = 0; i < v.size(); i++ ) {
            final FileNode nd = v.elementAt( i );
            final IconData idata = new IconData( FileTreeViewer.ICON_FOLDER,
                    FileTreeViewer.ICON_EXPANDEDFOLDER, nd );
            final DefaultMutableTreeNode node = new DefaultMutableTreeNode( idata );
            parent.add( node );

            if( nd.hasSubDirs() ) {
                node.add( new DefaultMutableTreeNode( new Boolean( true ) ) );
            }
        }

        return true;
    }

    public boolean hasSubDirs()
    {
        final File[] files = listFiles();
        if( files == null ) {
            return false;
        }
        for (final File file : files) {
            if (file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    public int compareTo( final FileNode toCompare )
    {
        return this.m_file.getName()
                .compareToIgnoreCase( toCompare.m_file.getName() );
    }

    protected File[] listFiles()
    {
        if( !this.m_file.isDirectory() ) {
            return null;
        }
        try {
            return this.m_file.listFiles();
        }
        catch( final Exception ex ) {
            JOptionPane.showMessageDialog( null, "Error reading directory "
                    + this.m_file.getAbsolutePath(), "Warning",
                    JOptionPane.WARNING_MESSAGE );
            return null;
        }
    }
}
