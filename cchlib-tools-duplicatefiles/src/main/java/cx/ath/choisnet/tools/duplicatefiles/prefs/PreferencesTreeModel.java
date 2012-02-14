/**
 * 
 */
package cx.ath.choisnet.tools.duplicatefiles.prefs;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * @author Claude
 *
 */
public class PreferencesTreeModel extends DefaultTreeModel
{
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param arg0
     */
    public PreferencesTreeModel( TreeNode arg0 )
    {
        super( arg0 );
    }

    DefaultTreeModel treeModel = null;
    {
        DefaultMutableTreeNode node0 = new DefaultMutableTreeNode("DefaultPreferences");
        {
            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("colors");
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("blue");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("violet");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("red");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("yellow");
                node1.add(node2);
            }
            node0.add(node1);
        }
        {
            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("sports");
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("basketball");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("soccer");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("football");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("hockey");
                node1.add(node2);
            }
            node0.add(node1);
        }
        {
            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("food");
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("hot dogs");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("pizza");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("ravioli");
                node1.add(node2);
            }
            {
                DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("bananas");
                node1.add(node2);
            }
            node0.add(node1);
        }
        treeModel = new DefaultTreeModel(node0);
    }
}
