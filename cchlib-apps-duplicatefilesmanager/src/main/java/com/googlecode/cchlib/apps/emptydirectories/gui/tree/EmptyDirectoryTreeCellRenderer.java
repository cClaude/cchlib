package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.MyStaticResources;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeNode;

/**
 *
 */
final public
class EmptyDirectoryTreeCellRenderer
    extends DefaultTreeCellRenderer
        implements TreeCellRenderer
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( EmptyDirectoryTreeCellRenderer.class );
    final private FolderTreeModelable model;

    //private JLabel checkBox = new JLabel();
    private Icon nodeLeafRendererIcon;
    private Icon nodeLeafRendererIconSelected;
    private Icon nodeLeafEmptyRendererIcon;
    private Icon nodeLeafEmptyRendererIconSelected;

    public EmptyDirectoryTreeCellRenderer( final FolderTreeModelable model )
    {
        this.model=model;

        this.nodeLeafRendererIcon              = MyStaticResources.getEmptyIcon();
        this.nodeLeafRendererIconSelected      = MyStaticResources.getEmptySelectedIcon();
        this.nodeLeafEmptyRendererIcon         = MyStaticResources.getEmptyLeafIcon();
        this.nodeLeafEmptyRendererIconSelected = MyStaticResources.getEmptyLeafSelectedIcon();
    }
/*
    @Override
    public Component getTreeCellRendererComponent(
            final JTree   tree,
            final Object  _value_,
            final boolean selected,
            final boolean expanded,
            final boolean leaf,
            final int     row,
            final boolean hasFocus
            )
    {
        String name;

        if( _value_ instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( _value_ );
            Folder        folder    = nodeValue.getFolder();
            File          nodeFile  = folder.getPath().toFile();

            name = nodeFile.getName();

            if( name.isEmpty() ) {
                // Root file
                name = nodeFile.getPath();
                }

            if( folder instanceof EmptyFolder ) {
                EmptyFolder ef = EmptyFolder.class.cast( folder );

                if( leaf ) {
                    if( ! ef.isEmpty() ) {
                        logger.error( "leaf EmptyFolder.isEmpty() = " + ef.isEmpty() );
                        }

                    // Build display
                    String stringValue = tree.convertValueToText(
                            name,
                            selected,
                            expanded,
                            leaf,
                            row,
                            false
                            );
                    this.nodeLeafRenderer.setText( stringValue );
//                    this.nodeLeafRenderer.setSelected( false );
                    this.nodeLeafRenderer.setEnabled( tree.isEnabled() );

//                    if (selected) {
//                        this.nodeLeafRenderer.setForeground( this.selectionForeground );
//                        this.nodeLeafRenderer.setBackground( this.selectionBackground );
//                        }
//                    else {
//                        this.nodeLeafRenderer.setForeground( this.textForeground );
//                        this.nodeLeafRenderer.setBackground( this.textBackground );
//                        }

                    this.currentValue = nodeValue;
//                    this.nodeLeafRenderer.setSelected( this.model.isSelected( nodeValue ) );
                    this.nodeLeafRenderer.setIcon( this.model.isSelected( nodeValue ) ? this.nodeLeafRendererIconSelected : this.nodeLeafRendererIcon );
//                    this.nodeLeafRenderer.setEnabled( nodeValue.isLeaf() );


                    this.cellEditorValue = this.nodeLeafRenderer;
                    return this.nodeLeafRenderer;
                    }
                else {
                    if( ef.isEmpty() ) {
                        logger.error( "not a leaf EmptyFolder.isEmpty() = " + ef.isEmpty() );
                        }

                    // Build display
                    String stringValue = tree.convertValueToText(
                            name,
                            selected,
                            expanded,
                            leaf,
                            row,
                            false
                            );
                    this.nonLeafEmptyRenderer.setText( stringValue );
                    //this.nonLeafEmptyRenderer.setSelected( false );
//                    this.nonLeafEmptyRenderer.setEnabled( tree.isEnabled() );

//                    if (selected) {
//                        this.nonLeafEmptyRenderer.setForeground( this.selectionForeground );
//                        this.nonLeafEmptyRenderer.setBackground( this.selectionBackground );
//                        }
//                    else {
//                        this.nonLeafEmptyRenderer.setForeground( this.textForeground );
//                        this.nonLeafEmptyRenderer.setBackground( this.textBackground );
//                        }

                    this.currentValue = nodeValue;
                    //this.nonLeafEmptyRenderer.setSelected( this.model.isSelected( nodeValue ) );
                    this.nonLeafEmptyRenderer.setIcon( this.model.isSelected( nodeValue ) ? this.nodeLeafEmptyRendererIconSelected : this.nodeLeafEmptyRendererIcon );
//                    this.nonLeafEmptyRenderer.setEnabled( !nodeValue.isLeaf() );

                    this.cellEditorValue = this.nonLeafEmptyRenderer;
                    return this.nonLeafEmptyRenderer;
                    }
                }
            else { // !(folder instanceof EmptyFolder)
                //logger.info( "node (not EmptyFolder) of type: " + folder );
                if( leaf ) {
                    throw new IllegalStateException( "Found leaf on a no empty folder: " + folder );
                    }
                }
            }
        else {
            name = null;
            }

        Object defValue = name == null ? _value_ : name;

        // use default display
        this.cellEditorValue = this.nonLeafNotEmptyRenderer.getTreeCellRendererComponent(
                    tree,
                    defValue /* name or _value* /,
                    selected,
                    expanded,
                    leaf,
                    row,
                    hasFocus
                    );
        return this.cellEditorValue;
    }
*/

    @Override
    public Component getTreeCellRendererComponent(
            final JTree   tree,
            final Object  _value_,
            final boolean _selected_,
            final boolean expanded,
            final boolean leaf,
            final int     row,
            final boolean hasFocus
            )
    {
        final String name;

        if( _value_ instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( _value_ );
            Folder         folder    = nodeValue.getFolder();
            File           file      = folder.getPath().toFile();

            name = file.getName();
            }
        else {
            // handle hidden root node
            name = null;
            }

        Component cellEditorValue = super.getTreeCellRendererComponent(
                tree,
                name == null ? _value_ : name,
                _selected_,
                expanded,
                leaf,
                row,
                hasFocus
                );

        if( _value_ instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( _value_ );
            Folder         folder    = nodeValue.getFolder();
            File           file      = folder.getPath().toFile();
            
            super.setToolTipText( file.getPath() );

            if( folder instanceof EmptyFolder ) {
                boolean selected = nodeValue.isSelected();
                //EmptyFolder ef = EmptyFolder.class.cast( folder );


                //checkBox.setText( file.getName() );
                //checkBox.setSelected( selected );

                if( leaf ) {
                    //super.set
                    super.setIcon( selected ?  nodeLeafEmptyRendererIconSelected : nodeLeafEmptyRendererIcon );
                    }
                else {
                    super.setIcon( selected ?  nodeLeafRendererIconSelected : nodeLeafRendererIcon );
                    }

                //value = checkBox;
                }
            }
        
        return cellEditorValue;
    }

}
