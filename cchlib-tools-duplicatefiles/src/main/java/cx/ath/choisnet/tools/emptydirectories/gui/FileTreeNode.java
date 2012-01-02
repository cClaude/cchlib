package cx.ath.choisnet.tools.emptydirectories.gui;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;

/**
 *
 */
public class FileTreeNode
    extends DefaultMutableTreeNode
        implements  Iterable<FileTreeNode>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FileTreeNode.class );
    private final File file;
    //private boolean selected;

    /**
     * Create a root FileTreeNode
     *
     * @param file {@link File} object for this root FileTreeNode
     */
    public FileTreeNode( final File file, final boolean selected )
    {
        super( file, true /*allowsChildren*/ );

        this.file = file;
        //this.selected = true;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param file {@link File} object for this root FileTreeNode
     */
    public FileTreeNode( final File file )
    {
        this( file, true );
    }

    /**
     *
     * @return
     */
    final // TODO: remove this line
    public File getFile()
    {
        return this.file;
    }

//    /**
//     *
//     * @return
//     */
//    final// TODO: remove this line
//    public boolean isSelected()
//    {
//        return this.selected;
//    }

//    /**
//     *
//     * @return
//     */
//    final// TODO: remove this line
//    public void setSelected( boolean selected )
//    {
//        this.selected = selected;
//    }

    /**
     *
     * @return
     */
    final // TODO: remove this line
    public File getData_()
    {
        Object userObject = super.getUserObject();

        if( userObject instanceof File ) {
            return File.class.cast( userObject );
            }
        else if( userObject instanceof JCheckBox ) {
            JCheckBox   jCheckBox       = JCheckBox.class.cast( userObject );
            Object      clientProperty  = jCheckBox.getClientProperty( File.class );

            return File.class.cast( clientProperty );
            }
        else {
            // Unknown user object type
            logger.fatal( "Unknown user object type: " + userObject );

            if( userObject != null ) {
                logger.fatal( "Unknown user object class: " + userObject.getClass() );
                }

            return null;
            }
    }

    @Override
    final // TODO: remove this line
    public String toString()
    {
        return super.toString() + " depth(" + super.getDepth() + ") DFR(" + getDepthFromRoot() + ")";
    }

    /**
     *
     * @return
     */
    final // TODO: remove this line
    public int getDepthFromRoot()
    {
        TreeNode    parent  = super.getParent();
        int         depth   = 0;

        while( parent != null ) {
            parent = parent.getParent();
            depth++;
            }

        return depth;
    }

    /**
     * Create a child node on this node.
     *
     * @param file
     * @return
     */
    final // TODO: remove this line
    public FileTreeNode add( final File file )
    {
        if( ! file.getParentFile().equals( getFile() ) ) {
            throw new IllegalArgumentException(
                file + " is not a direct child of " + getFile()
                );
            }
        //logger.info( "add " + file + " on " + this.getData() );

        FileTreeNode newNode = new FileTreeNode( file );

        super.add( newNode );

        //logger.info( "add .getChildCount()=" + this.getChildCount() );

        return newNode;
    }

    /**
     *
     */
    @Override
    final // TODO: remove this line
    public Iterator<FileTreeNode> iterator()
    {
        final Enumeration<?> children = super.children();

        return new Iterator<FileTreeNode>()
        {
            @Override
            public boolean hasNext()
            {
                return children.hasMoreElements();
            }
            @Override
            public FileTreeNode next()
            {
                return FileTreeNode.class.cast( children.nextElement() );
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
