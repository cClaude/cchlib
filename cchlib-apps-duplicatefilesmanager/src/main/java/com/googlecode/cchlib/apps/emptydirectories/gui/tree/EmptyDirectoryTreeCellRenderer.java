package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import com.googlecode.cchlib.apps.duplicatefiles.MyStaticResources;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeNode;

/**
 *
 */
public final
class EmptyDirectoryTreeCellRenderer
    extends DefaultTreeCellRenderer
        implements TreeCellRenderer
{
    private static final long serialVersionUID = 1L;

    private Icon nodeLeafIcon;
    private Icon nodeLeafSelectedIcon;
    private Icon nodeLeafSelectedByUserIcon;
    private Icon nodeLeafSelectedAndSelectedByUserIcon;

    private Icon nodeLeafEmptyIcon;
    private Icon nodeLeafEmptySelectedIcon;
    private Icon nodeLeafEmptySelectedByUserIcon;
    private Icon nodeLeafEmptySelectedAndSelectedByUserIcon;

    public EmptyDirectoryTreeCellRenderer( final FolderTreeModelable model )
    {
        this.nodeLeafIcon                          = MyStaticResources.getEmptyIcon();
        this.nodeLeafSelectedIcon                  = MyStaticResources.getEmptySelectedIcon();
        this.nodeLeafSelectedByUserIcon            = MyStaticResources.getEmptySelectedByUserIcon();
        this.nodeLeafSelectedAndSelectedByUserIcon = MyStaticResources.getEmptySelectedAndSelectedByUserIcon();

        this.nodeLeafEmptyIcon                          = MyStaticResources.getEmptyLeafIcon();
        this.nodeLeafEmptySelectedIcon                  = MyStaticResources.getEmptyLeafSelectedIcon();
        this.nodeLeafEmptySelectedByUserIcon            = MyStaticResources.getEmptyLeafSelectedByUserIcon();
        this.nodeLeafEmptySelectedAndSelectedByUserIcon = MyStaticResources.getEmptyLeafSelectedAndSelectedByUserIcon();
    }

    @Override
    public Component getTreeCellRendererComponent(
            final JTree   tree,
            final Object  objectValue,
            final boolean selectedByUser,
            final boolean expanded,
            final boolean leaf,
            final int     row,
            final boolean hasFocus
            )
    {
        final String    name            = computeText( objectValue );
        final Component cellEditorValue = super.getTreeCellRendererComponent(
                tree,
                (name == null) ? objectValue : name,
                selectedByUser,
                expanded,
                leaf,
                row,
                hasFocus
                );

        if( objectValue instanceof FolderTreeNode ) {
            customizeIcon(
                leaf,
                FolderTreeNode.class.cast( objectValue ),
                selectedByUser
                );
            }

        return cellEditorValue;
    }

    private void customizeIcon(
        final boolean        leaf,
        final FolderTreeNode nodeValue,
        final boolean        selectedByUser
        )
    {
        final Folder folder = nodeValue.getFolder();
        final File   file  = folder.getPath().toFile();

        super.setToolTipText( file.getPath() );

        if( folder instanceof EmptyFolder ) {
            final boolean selected = nodeValue.isSelected();

            if( leaf ) {
                super.setIcon( getNodeLeafEmptyRendererIcon( selected, selectedByUser ) );
                }
            else {
                super.setIcon( getNodeLeafIconSelected( selected, selectedByUser ) );
                }
            }
    }

    private Icon getNodeLeafIconSelected(
        final boolean selected,
        final boolean selectedByUser
        )
    {
        if( selectedByUser ) {
            return selected ?  nodeLeafSelectedAndSelectedByUserIcon : nodeLeafSelectedByUserIcon;
           }
        else {
            return selected ?  nodeLeafSelectedIcon : nodeLeafIcon;
            }
    }

    private Icon getNodeLeafEmptyRendererIcon(
        final boolean selected,
        final boolean selectedByUser
        )
    {
        if( selectedByUser ) {
            return selected ?  nodeLeafEmptySelectedAndSelectedByUserIcon : nodeLeafEmptySelectedByUserIcon;
            }
        else {
            return selected ?  nodeLeafEmptySelectedIcon : nodeLeafEmptyIcon;
            }
    }

    private String computeText( final Object objectValue )
    {
        final String name;

        if( objectValue instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( objectValue );
            Folder         folder    = nodeValue.getFolder();
            File           file      = folder.getPath().toFile();

            name = file.getName();
            }
        else {
            // handle hidden root node
            name = "ROOT";
            }

        return name;
    }
}
