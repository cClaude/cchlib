package com.googlecode.cchlib.apps.emptydirectories.debug;

import com.googlecode.cchlib.apps.emptydirectories.debug.lib.DefaultCheckBoxTreeCellRenderer;
import com.googlecode.cchlib.apps.emptydirectories.debug.lib.DualIcon;
import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

final class Folder3TreeCellRenderer extends DefaultCheckBoxTreeCellRenderer implements TreeCellRenderer {
    private static final long serialVersionUID = 1L;

    //private IconResources iconResources;

//    private Icon defaultClosedIcon;
//    private Icon defaultOpenIcon;
//    private Icon defaultLeafIcon;

    private Icon defaultClosedUnselectedIcon;
    private Icon defaultLeafUnselectedIcon;
    private Icon defaultOpenUnselectedIcon;

//    private Icon defaultClosedSelectedIcon;
//    private Icon defaultLeafSelectedIcon;
//    private Icon defaultOpenSelectedIcon;

    public Folder3TreeCellRenderer()
    {
    }

    @Override
    public Component getTreeCellRendererComponent(
        final JTree tree,
        final Object value,
        final boolean currentSelected,
        final boolean expanded,
        final boolean isLeaf,
        final int row,
        final boolean focused //
        )
    {
        final Component c = super.getTreeCellRendererComponent(tree, value, currentSelected, expanded, isLeaf, row, focused);

        final Folder3TreeNode node = Folder3TreeNode.class.cast( value );
        final File            file = node.getFile();

        if( file != null ) {
//            if( isLeaf ) {
//                if( node.isSelected() ) {
//                    setIcon( emptyLeafSelectedIcon );
//                } else {
//                    setIcon( iconResources.getEmptyLeafIcon() );
//                }
//            } else {
//                if( node.isSelected() ) {
//                    setIcon( iconResources.getEmptySelectedIcon() );
//                } else {
//                    setIcon( iconResources.getEmptyIcon() );
//                }
//            }

            super.setIcon( getDefaultIcon( node.isSelected(), expanded, isLeaf ) );
            super.setToolTipText( file.getAbsolutePath() );
        }

        return c;
    }

//    @Override
//    public Color getBorderSelectionColor()
//    {
//        return Colors.red.toColor();
//    }

    public Icon getDefaultIcon( final boolean selected, final boolean expanded, final boolean isLeaf )
    {
        if( isLeaf ) {
//            if( selected ) {
//                return getDefaultLeafSelectedIcon();
//                }
            return getDefaultLeafUnselectedIcon();
            }

        if( expanded ) {
//            if( selected ) {
//                return getDefaultOpenSelectedIcon();
//                }
            return getDefaultOpenUnselectedIcon();
            }

//        if( selected ) {
//            return getDefaultClosedSelectedIcon();
//            }
       return getDefaultClosedUnselectedIcon();
    }

    @Override
    public void updateUI()
    {
        super.updateUI();

        defaultClosedUnselectedIcon = new DualIcon( getDefaultCheckBoxUnSelectedIcon(), super.getDefaultClosedIcon() );
        defaultLeafUnselectedIcon   = new DualIcon( getDefaultCheckBoxUnSelectedIcon(), super.getDefaultLeafIcon()   );
        defaultOpenUnselectedIcon   = new DualIcon( getDefaultCheckBoxUnSelectedIcon(), super.getDefaultOpenIcon()   );
//
//        defaultClosedSelectedIcon = new DualIcon( getDefaultSelectedIcon(), super.getDefaultClosedIcon() );
//        defaultLeafSelectedIcon   = new DualIcon( getDefaultSelectedIcon(), super.getDefaultLeafIcon()   );
//        defaultOpenSelectedIcon   = new DualIcon( getDefaultSelectedIcon(), super.getDefaultOpenIcon()   );
    }

    protected Icon getDefaultClosedUnselectedIcon()
    {
        return defaultClosedUnselectedIcon;
    }

    protected Icon getDefaultLeafUnselectedIcon()
    {
        return defaultLeafUnselectedIcon;
    }

    protected Icon getDefaultOpenUnselectedIcon()
    {
        return defaultOpenUnselectedIcon;
    }

//    protected Icon getDefaultClosedSelectedIcon()
//    {
//        return defaultClosedSelectedIcon;
//    }
//
//    protected Icon getDefaultLeafSelectedIcon()
//    {
//        return defaultLeafSelectedIcon;
//    }
//
//    protected Icon getDefaultOpenSelectedIcon()
//    {
//        return defaultOpenSelectedIcon;
//    }

//    private IconResources getIconResources()
//    {
//        if( iconResources == null ) {
//            iconResources = IconResources.getInstance();
//            }
//        return iconResources;
//    }

//    @Override
//    protected Icon getDefaultClosedIcon()
//    {
//        return defaultClosedIcon;
//    }
//
//    @Override
//    protected Icon getDefaultOpenIcon()
//    {
//        return defaultOpenIcon;
//    }
//
//    @Override
//    protected Icon getDefaultLeafIcon()
//    {
//        return defaultLeafIcon;
//    }

}
