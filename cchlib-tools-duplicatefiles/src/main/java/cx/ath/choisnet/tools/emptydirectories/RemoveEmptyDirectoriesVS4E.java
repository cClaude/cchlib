/**
 *
 */
package cx.ath.choisnet.tools.emptydirectories;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class RemoveEmptyDirectoriesVS4E extends JFrame
{
    private static final long   serialVersionUID        = 1L;
    private JTree jTreeDir;
    private JScrollPane jScrollPaneDir;
    private JPanel jPanelTop;
    private JPanel jPanelRight;
    private JTextField jTextFieldRootDir;
    private JButton jButtonFind;
    private JButton jButton0X;
    private JProgressBar jProgressBarMain;

    //private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public RemoveEmptyDirectoriesVS4E()
    {
        initComponents();
    }

    private void initComponents()
    {
        add(getJPanelTop(), BorderLayout.NORTH);
        add(getJScrollPaneDir(), BorderLayout.CENTER);
        add(getJPanelRight(), BorderLayout.EAST);
        add(getJProgressBarMain(), BorderLayout.SOUTH);
        setSize(320, 240);
    }

    protected JProgressBar getJProgressBarMain()
    {
        if (jProgressBarMain == null) {
            jProgressBarMain = new JProgressBar();
            jProgressBarMain.setString( "Select folder" );
            }
        return jProgressBarMain;
    }

    private JButton getJButtonFind() {
        if (jButtonFind == null) {
            jButtonFind = new JButton();
            jButtonFind.setText("jButtonFind");
            jButtonFind.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent event) {
                    jButtonFindMouseMouseClicked(event);
                }
            });
        }
        return jButtonFind;
    }

    private JButton getJButton0X() {
        if (jButton0X == null) {
            jButton0X = new JButton();
            jButton0X.setText("jButton0X");
            jButton0X.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent event) {
                    jButton0XMouseMouseClicked(event);
                }
            });
        }
        return jButton0X;
    }

    protected JTextField getJTextFieldRootDir() {
        if (jTextFieldRootDir == null) {
            jTextFieldRootDir = new JTextField();
            jTextFieldRootDir.setText("jTextFieldRootDir");
        }
        return jTextFieldRootDir;
    }

    private JPanel getJPanelTop() {
        if (jPanelTop == null) {
            jPanelTop = new JPanel();
            jPanelTop.setLayout(new BorderLayout());
            jPanelTop.add(getJTextFieldRootDir(), BorderLayout.CENTER);
            jPanelTop.add(getJButtonFind(), BorderLayout.EAST);
        }
        return jPanelTop;
    }

    private JPanel getJPanelRight() {
        if (jPanelRight == null) {
            jPanelRight = new JPanel();
            jPanelRight.add(getJButton0X());
        }
        return jPanelRight;
    }

    protected JScrollPane getJScrollPaneDir()
    {
        if (jScrollPaneDir == null) {
            jScrollPaneDir = new JScrollPane();
            jScrollPaneDir.setViewportView( getJTreeDir() );
        }
        return jScrollPaneDir;
    }

    protected JTree getJTreeDir()
    {
        if (jTreeDir == null) {
            jTreeDir = new JTree();

            //EmptyDirectoriesTreeModel model = new EmptyDirectoriesTreeModel( fileTree );
            //jTreeDir.setModel(treeModel);
            jTreeDir.setModel(null);
        }
        return jTreeDir;
    }

//    private static void installLnF()
//    {
//        try {
//            String lnfClassname /*= PREFERRED_LOOK_AND_FEEL;
//            if( lnfClassname == null )
//                lnfClassname */= UIManager.getCrossPlatformLookAndFeelClassName();
//            UIManager.setLookAndFeel( lnfClassname );
//        }
//        catch( Exception e ) {
//            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
//                    + " on this platform:" + e.getMessage() );
//        }
//    }

//    /**
//     * Main entry of the class.
//     * Note: This class is only created so that you can easily preview the result at runtime.
//     * It is not expected to be managed by the designer.
//     * You can modify it as you like.
//     */
//    public static void main( String[] args )
//    {
//        installLnF();
//        SwingUtilities.invokeLater( new Runnable() {
//            @Override
//            public void run()
//            {
//                RemoveEmptyDirectoriesVS4E frame = new RemoveEmptyDirectoriesVS4E();
//                frame.setDefaultCloseOperation( RemoveEmptyDirectoriesVS4E.EXIT_ON_CLOSE );
//                frame.setTitle( "RemoveEmptyDirectoriesVS4E" );
//                frame.getContentPane().setPreferredSize( frame.getSize() );
//                frame.pack();
//                frame.setLocationRelativeTo( null );
//                frame.setVisible( true );
//            }
//        } );
//    }


    protected void jButtonFindMouseMouseClicked(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButton0XMouseMouseClicked(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

}
