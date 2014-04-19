package com.googlecode.cchlib.apps.emptydirectories;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class DemoJTree
{
    private static final class TestTreeCellRenderer extends DefaultTreeCellRenderer
    {
        private static final long serialVersionUID = 1L;
        private final Icon loadIcon = UIManager.getIcon("OptionPane.errorIcon");
        private final Icon saveIcon = UIManager.getIcon("OptionPane.informationIcon");

        @Override
        public Component getTreeCellRendererComponent(//
                final JTree tree,
                final Object value, //
                final boolean selected, //
                final boolean expanded,
                final boolean isLeaf, //
                final int row, //
                final  boolean focused//
                ) {
            final Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);

            if (selected) {
                setIcon(loadIcon);
                }
            else {
                setIcon(saveIcon);
                }
            return c;
        }
    }

    private static void createAndShowUI() {
        final JFrame frame = new JFrame();
        final JTree tree = new JTree( buildDemoModel() );

        tree.setCellRenderer( new TestTreeCellRenderer() );
        tree.setVisibleRowCount(10);
        frame.add(new JScrollPane(tree));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    private static DefaultTreeModel buildDemoModel() {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        root.add(new DefaultMutableTreeNode("A"));
        root.add(new DefaultMutableTreeNode("B"));
        root.add(new DefaultMutableTreeNode("C"));

        return new DefaultTreeModel(root);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(DemoJTree::createAndShowUI);
    }
}
