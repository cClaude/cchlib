package com.googlecode.cchlib.apps.emptydirectories.debug;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.io.FileHelper;

public class Debug3JTree
{
    private File rootFile;
    private Folder3TreeImpl tree;

    private void createAndShowUI() {
        final JFrame frame = new JFrame();

        this.tree  = new Folder3TreeImpl();
        this.tree.setModel( buildDemoModel() );

        tree.setCellRenderer( new Folder3TreeCellRenderer() );
        tree.addTreeSelectionListener( new Folder3TreeSelectionListener( tree ) );
        tree.setVisibleRowCount(10);
        frame.add(new JScrollPane(tree));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    private Folder3TreeModel buildDemoModel()
    {
        final Folder3TreeModel model = Folder3TreeModelImpl.getInstance();

        final Folder3TreeNode root = model.getRootNode();

        root.add( newTreeNode() );

        root.add( newTreeNode( "A" ) );
        root.add( newTreeNode( "B" ) );
        root.add( newTreeNode( "C" ) );

        final Folder3TreeNode nodeAA = newTreeNode( "AA" );
        root.add( nodeAA );
        nodeAA.add( newTreeNode( "AA/AA" ) );
        nodeAA.add( newTreeNode( "AA/BB" ) );
        nodeAA.add( newTreeNode( "AA/CC" ) );

        return model;
    }

    private Folder3TreeNode newTreeNode()
    {
        return newTreeNode( getRoot() );
    }

    private Folder3TreeNode newTreeNode( final String str )
    {
        final File root = getRoot();

        return newTreeNode( new File( root, str ) );
    }

    private Folder3TreeNode newTreeNode( final File file )
    {
        assert this.tree != null;

        return new Folder3TreeNodeImpl( this.tree, file );
    }

    private File getRoot()
    {
        if( rootFile == null ) {
            rootFile = FileHelper.createTempDir();
            }
        return rootFile;
    }

    public static void main(final String[] args) {

        SwingUtilities.invokeLater(new Debug3JTree()::createAndShowUI);
    }
}
