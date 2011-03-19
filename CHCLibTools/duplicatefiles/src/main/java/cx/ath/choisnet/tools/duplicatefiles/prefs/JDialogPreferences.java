/**
 * 
 */
package cx.ath.choisnet.tools.duplicatefiles.prefs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Logger;

//VS4E -- DO NOT REMOVE THIS LINE!
public class JDialogPreferences extends JDialog 
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JDialogPreferences.class );
    private JSplitPane jSplitPaneRoot;
    private JScrollPane jScrollPaneMenu;
    private JTree jTreeMenu;
    private JPanel jPanelButtons;
    private JButton jButtonApply;
    private JButton jButtonCancel;
    private JPanel jPanelEmpty;

    public JDialogPreferences()
    {
        initComponents();
    }

    public JDialogPreferences( Frame parent )
    {
        super( parent );
        initComponents();
    }

    public JDialogPreferences( Dialog parent )
    {
        super( parent );
        initComponents();
    }

    private void initComponents() {
    	setFont(new Font("Dialog", Font.PLAIN, 12));
    	setBackground(Color.white);
    	setForeground(Color.black);
    	add(getJPanelButtons(), BorderLayout.SOUTH);
    	add(getJSplitPaneRoot(), BorderLayout.CENTER);
    	setSize(320, 240);
    }

    private JPanel getJPanelEmpty() {
    	if (jPanelEmpty == null) {
    		jPanelEmpty = new JPanel();
    	}
    	return jPanelEmpty;
    }

    private JButton getJButtonCancel() {
    	if (jButtonCancel == null) {
    		jButtonCancel = new JButton();
    		jButtonCancel.setText("Cancel");
    	}
    	return jButtonCancel;
    }

    private JPanel getJPanelButtons() {
    	if (jPanelButtons == null) {
    		jPanelButtons = new JPanel();
    		jPanelButtons.add(getJButtonApply());
    		jPanelButtons.add(getJButtonCancel());
    	}
    	return jPanelButtons;
    }

    private JButton getJButtonApply() {
    	if (jButtonApply == null) {
    		jButtonApply = new JButton();
    		jButtonApply.setText("Apply");
    	}
    	return jButtonApply;
    }

    private JSplitPane getJSplitPaneRoot() {
    	if (jSplitPaneRoot == null) {
    		jSplitPaneRoot = new JSplitPane();
    		jSplitPaneRoot.setDividerLocation(82);
    		jSplitPaneRoot.setLeftComponent(getJScrollPaneMenu());
    		jSplitPaneRoot.setRightComponent(getJPanelEmpty());
    	}
    	return jSplitPaneRoot;
    }

    private JScrollPane getJScrollPaneMenu() {
    	if (jScrollPaneMenu == null) {
    		jScrollPaneMenu = new JScrollPane();
    		jScrollPaneMenu.setViewportView(getJTreeMenu());
    	}
    	return jScrollPaneMenu;
    }

    private JTree getJTreeMenu() {
    	if (jTreeMenu == null) {
    		jTreeMenu = new JTree();
    		DefaultTreeModel treeModel = null;
    		{
    			DefaultMutableTreeNode node0 = new DefaultMutableTreeNode("Preferences");
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
    		jTreeMenu.setModel(treeModel);
    		jTreeMenu.addTreeSelectionListener(new TreeSelectionListener() {
    
    			public void valueChanged(TreeSelectionEvent event) {
    				jTreeMenuTreeSelectionValueChanged(event);
    			}
    		});
    	}
    	return jTreeMenu;
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                JDialogPreferences dialog = new JDialogPreferences();
                dialog.setDefaultCloseOperation( JDialogPreferences.DISPOSE_ON_CLOSE );
                dialog.setTitle( "JDialogPreferences" );
                dialog.setLocationRelativeTo( null );
                dialog.getContentPane().setPreferredSize( dialog.getSize() );
                dialog.pack();
                dialog.setVisible( true );
            }
        } );
    }

    private void jTreeMenuTreeSelectionValueChanged(TreeSelectionEvent event) 
    {
        slogger.info( "TreeSelectionValueChanged:" + event );
    }

}
