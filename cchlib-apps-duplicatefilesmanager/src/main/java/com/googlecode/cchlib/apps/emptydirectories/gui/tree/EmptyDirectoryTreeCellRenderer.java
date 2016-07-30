package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import com.googlecode.cchlib.apps.duplicatefiles.IconResources;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeNode;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.Folder;

/**
 *
 */
public final
class EmptyDirectoryTreeCellRenderer
    extends DefaultTreeCellRenderer
        implements TreeCellRenderer
{
    private static final long serialVersionUID = 1L;

    private final IconResources iconResources = IconResources.getInstance();
//    private Icon nodeLeafIcon;
//    private Icon nodeLeafSelectedIcon;
//    private Icon nodeLeafSelectedByUserIcon;
//    private Icon nodeLeafSelectedAndSelectedByUserIcon;

//    private Icon nodeLeafEmptyIcon;
//    private Icon nodeLeafEmptySelectedIcon;
//    private Icon nodeLeafEmptySelectedByUserIcon;
//    private Icon nodeLeafEmptySelectedAndSelectedByUserIcon;

    public EmptyDirectoryTreeCellRenderer( final FolderTreeModelable model )
    {
//        this.nodeLeafIcon                          = IconResources.getEmptyIcon();
//        this.nodeLeafSelectedIcon                  = IconResources.getEmptySelectedIcon();
//        this.nodeLeafSelectedByUserIcon            = IconResources.getEmptySelectedByUserIcon();
//        this.nodeLeafSelectedAndSelectedByUserIcon = IconResources.getEmptySelectedAndSelectedByUserIcon();

//        this.nodeLeafEmptyIcon                          = IconResources.getEmptyLeafIcon();
//        this.nodeLeafEmptySelectedIcon                  = IconResources.getEmptyLeafSelectedIcon();
//        this.nodeLeafEmptySelectedByUserIcon            = IconResources.getEmptyLeafSelectedByUserIcon();
//        this.nodeLeafEmptySelectedAndSelectedByUserIcon = IconResources.getEmptyLeafSelectedAndSelectedByUserIcon();
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

    @SuppressWarnings("deprecation") // FIXME A finir
    private Icon getNodeLeafIconSelected(
        final boolean selected,
        final boolean selectedByUser
        )
    {
        if( selectedByUser ) {
            //return selected ?  nodeLeafSelectedAndSelectedByUserIcon : nodeLeafSelectedByUserIcon;
            return selected ?  iconResources.getEmptySelectedAndSelectedByUserIcon() : iconResources.getEmptySelectedByUserIcon();
           }
        else {
            //return selected ?  nodeLeafSelectedIcon : nodeLeafIcon;
            return selected ?  iconResources.getEmptySelectedIcon() : iconResources.getEmptyIcon();
            }
    }

    @SuppressWarnings("deprecation") // FIXME A finir
    private Icon getNodeLeafEmptyRendererIcon(
        final boolean selected,
        final boolean selectedByUser
        )
    {
        if( selectedByUser ) {
            //return selected ?  nodeLeafEmptySelectedAndSelectedByUserIcon : nodeLeafEmptySelectedByUserIcon;
            return selected ?  iconResources.getEmptyLeafSelectedAndSelectedByUserIcon() : iconResources.getEmptyLeafSelectedByUserIcon();
            }
        else {
            //return selected ?  nodeLeafEmptySelectedIcon : nodeLeafEmptyIcon;
            return selected ?  iconResources.getEmptyLeafSelectedIcon() : iconResources.getEmptyLeafIcon();
            }
    }

    private String computeText( final Object objectValue )
    {
        final String name;

        if( objectValue instanceof FolderTreeNode ) {
            final FolderTreeNode nodeValue = FolderTreeNode.class.cast( objectValue );
            final Folder         folder    = nodeValue.getFolder();
            final File           file      = folder.getPath().toFile();

            name = file.getName();
            }
        else {
            // handle hidden root node
            name = "ROOT";
            }

        return name;
    }
}
