package com.googlecode.cchlib.apps.emptydirectories.debug;

import java.io.File;
import java.nio.file.Path;
import javax.swing.tree.DefaultMutableTreeNode;

public class Folder3TreeNodeImpl extends DefaultMutableTreeNode implements Folder3TreeNode {

    private static final long serialVersionUID = 1L;
    private final File file;
    private final Folder3TreeImpl tree;
    private boolean marked;

    /**
     * Create root object (hidden)
     */
    private Folder3TreeNodeImpl( final String str )
    {
        super( str );

        this.tree = null;
        this.file = null;
    }

    public Folder3TreeNodeImpl( final Folder3TreeImpl tree, final File file )
    {
        super( file.getName() );

        assert tree != null;

        this.tree = tree;
        this.file = file;
    }

    public Folder3TreeNodeImpl( final Folder3TreeImpl tree, final Path path )
    {
        this( tree, path.toFile() );
    }

    @Override
    public void add( final Folder3TreeNode node )
    {
        super.add( node );
    }

    public static Folder3TreeNode createRoot()
    {
        return new Folder3TreeNodeImpl( "R00T" );
    }

    @Override
    public boolean isSelected()
    {
        if( this.tree != null ) {
            return this.tree.getSelectedNodes().contains( this );
            }
        return false;
    }

    @Override
    public void setSelected( final boolean selected )
    {
        assert this.tree != null;

        this.tree.addSelectedNode( this, selected );
    }

    @Override
    public File getFile()
    {
        return this.file;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Folder3TreeNodeImpl [this.file=" );
        builder.append( this.file );
        builder.append( ", isSelected()=" );
        builder.append( isSelected() );
        builder.append( ", toString()=" );
        builder.append( super.toString() );
        builder.append( ']' );
        return builder.toString();
    }

    @Override
    public boolean isMarked()
    {
        return this.marked;
    }

    @Override
    public void setMarked( final boolean marked )
    {
        this.marked = marked;
    }

    @Override
    public String getText()
    {
        return this.file != null ? this.file.getName() : null;
    }

}
