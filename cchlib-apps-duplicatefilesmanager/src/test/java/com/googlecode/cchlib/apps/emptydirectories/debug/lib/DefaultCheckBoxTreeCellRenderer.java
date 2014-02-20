package com.googlecode.cchlib.apps.emptydirectories.debug.lib;

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

public class DefaultCheckBoxTreeCellRenderer extends CheckBoxTreeCellRenderer
{
    private static final long serialVersionUID = 1L;

    private Icon defaultClosedIcon;
    private Icon defaultOpenIcon;
    private Icon defaultLeafIcon;

    private Icon defaultCheckBoxUnSelectedIcon;

    public DefaultCheckBoxTreeCellRenderer()
    {
        updateUI();
    }

    public Icon getDefaultIcon( final boolean expanded, final boolean isLeaf )
    {
        if( isLeaf ) {
            return getDefaultLeafIcon();
            }

        if( expanded ) {
            return getDefaultOpenIcon();
            }

        return getDefaultClosedIcon();
    }

    @Override
    public void updateUI()
    {
        super.updateUI();

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

        defaultClosedIcon = renderer.getDefaultClosedIcon();
        defaultLeafIcon   = renderer.getDefaultLeafIcon();
        defaultOpenIcon   = renderer.getDefaultOpenIcon();

//        JCheckBox cb = new JCheckBox(StringHelper.EMPTY,true);
//       defaultSelectedIcon   = super.getSelectedIcon();
//        cb.updateUI();
//        defaultSelectedIcon = cb.getIcon();
        defaultCheckBoxUnSelectedIcon = UIManager.getIcon("CheckBox.icon");
//        Object xxxx;
//        x = UIManager.get
        assert defaultCheckBoxUnSelectedIcon != null;

////        defaultUnSelectedIcon = super.getIcon();
////        cb.setSelected( false );
////        defaultUnSelectedIcon = cb.getIcon();
//        defaultUnSelectedIcon = defaultSelectedIcon;
////        defaultUnSelectedIcon = UIManager.getLookAndFeel().get(new JCheckBox(), defaultSelectedIcon);
//        assert defaultUnSelectedIcon != null;
}

    protected Icon getDefaultClosedIcon()
    {
        assert defaultClosedIcon != null;
        return defaultClosedIcon;
    }

    protected Icon getDefaultOpenIcon()
    {
        assert defaultOpenIcon != null;
        return defaultOpenIcon;
    }

    protected Icon getDefaultLeafIcon()
    {
        assert defaultLeafIcon != null;
        return defaultLeafIcon;
    }

    protected Icon getDefaultCheckBoxUnSelectedIcon()
    {
        assert defaultCheckBoxUnSelectedIcon != null;
        return defaultCheckBoxUnSelectedIcon;
    }
}
