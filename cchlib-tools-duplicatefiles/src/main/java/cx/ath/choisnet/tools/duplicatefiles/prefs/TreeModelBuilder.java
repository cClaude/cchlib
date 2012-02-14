/**
 * 
 */
package cx.ath.choisnet.tools.duplicatefiles.prefs;

import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import alpha.cx.ath.choisnet.utils.VisitResult;
import alpha.cx.ath.choisnet.utils.Visitor;
import alpha.cx.ath.choisnet.utils.tree.BadRootNameException;
import alpha.cx.ath.choisnet.utils.tree.NamedTree;
import alpha.cx.ath.choisnet.utils.tree.NamedTreeNode;

/**
 * @author Claude
 *
 */
public class TreeModelBuilder implements Serializable
{
    private static final long serialVersionUID = 1L;

    //private TreeModel treeModel;
    //private DefaultMutableTreeNode currentMutableTreeNode;
    private NamedTree<JPanel> tree = new NamedTree<JPanel>();
    /**
     * 
     */
    public TreeModelBuilder()
    {
        //currentMutableTreeNode = null;
        //treeModel = null;
    }

    public TreeModelBuilder addNode(String path, JPanel panel) throws BadRootNameException
    {
        String[] pathParts = path.split( "\\." );
        
        tree.put( panel, pathParts );
        
        return this;
    }
    private class Builder implements Visitor<NamedTreeNode<JPanel>>
    {
        DefaultMutableTreeNode node = null;
        @Override
        public VisitResult visite( NamedTreeNode<JPanel> entry )
        {
//            DefaultMutableTreeNode node = new DefaultMutableTreeNode( entry.getData() );
            // TODO Auto-generated method stub
            return VisitResult.CONTINUE;
        }
    }

    public TreeModel createTreeModel()
    {
        Builder b = new Builder();
        
        tree.walkDepthFirst( b );
        
        return new DefaultTreeModel( b.node );
    }

    
    
    /**
     * 
     * @param node
     * @param jpanel
     * /
    private void addNode(DefaultMutableTreeNode node,JPanel jpanel)
    {
        if( currentMutableTreeNode != null ) {
            currentMutableTreeNode.add( node );
        }
        else {
            currentMutableTreeNode = node;
            
            if( treeModel == null ) {
                treeModel = new DefaultTreeModel( node );
            }
        }
        
        //return this;
    }*/

//    /**
//     * 
//     * @param name
//     * @param jpanel
//     * @return
//     */
//    public TreeModelBuilder addNode(String name,JPanel jpanel)
//    {
//        return addNode(new DefaultMutableTreeNode(name),jpanel);
//    }

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
        //treeModel = new DefaultTreeModel(node0);
    }
}
