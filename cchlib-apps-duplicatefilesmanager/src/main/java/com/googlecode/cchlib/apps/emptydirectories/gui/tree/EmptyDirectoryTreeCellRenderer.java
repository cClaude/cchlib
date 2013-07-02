package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.MyStaticResources;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;

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
            final boolean selected,
            final boolean expanded,
            final boolean leaf,
            final int     row,
            final boolean hasFocus
            )
    {
        final String name;
        final Icon   icon;

        name = null;
        icon = null;
        /*
        if( _value_ instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( _value_ );
            Folder         folder    = nodeValue.getFolder();
            File           nodeFile  = folder.getPath().toFile();

            name = nodeFile.getName();

            // DOES NOT WORK : super.setToolTipText( folder.getFile().toString() );

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

                    icon = this.model.isSelected( nodeValue ) ? this.nodeLeafRendererIconSelected : this.nodeLeafRendererIcon;
                    }
                else {
                    if( ef.isEmpty() ) {
                        logger.error( "not a leaf EmptyFolder.isEmpty() = " + ef.isEmpty() );
                        }

                    icon = this.model.isSelected( nodeValue ) ? this.nodeLeafEmptyRendererIconSelected : this.nodeLeafEmptyRendererIcon;
                    }
                }
            else { // !(folder instanceof EmptyFolder)
                icon = null; // Default icon

                if( leaf ) {
                    throw new IllegalStateException( "Found leaf on a no empty folder: " + folder );
                    }
                }
            }
        else {
            name = null;
            icon = null; // Default icon
            }
*/
        Component cellEditorValue = super.getTreeCellRendererComponent(
                tree,
                name == null ? _value_ : name /* name or _value_*/,
                selected,
                expanded,
                leaf,
                row,
                hasFocus
                );

        // use default display
//        this.cellEditorValue = this.nonLeafNotEmptyRenderer.getTreeCellRendererComponent(

        //assert this.renderer == cellEditorValue;

        if( icon != null ) {
            super.setIcon( icon );
            logger.info( "setIcon() => " + icon );
            }

        return cellEditorValue;
    }

//    public FolderTreeNode getCurrentValue()
//    {
//        return this.currentValue;
//    }

//    DefaultTreeCellRenderer getCellEditorValue()
//    {
//        return this.renderer;
//    }


//    public JCheckBox getLeafRenderer()
//    {
//        return this.nodeLeafRenderer;
//    }

}