package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import cx.ath.choisnet.util.CancelRequestException;

/**
 *
 *
 */
public class VirtualTree2
{
    public static void main(String[] args)
    {
/*
        FileTree fileTree = new FileTree();
        {
            FileIterator iter = new FileIterator( new File( "C:\\Temps" ) );

            while( iter.hasNext() ) {
                fileTree.add( iter.next() );
                }
        }
*/
        //Xfinal FileTree fileTree = new FileTree();
        final EmptyDirectoriesTreeModel model = new EmptyDirectoriesTreeModel();

        {
            File[] rootDirs = { new File( "T:/Data" ) };

            EmptyDirectoriesFinder emptyDirs = new EmptyDirectoriesFinder( rootDirs );
            EmptyDirectoriesListener listener = new EmptyDirectoriesListener()
            {
                @Override
                public boolean isCancel()
                {
                    // TODO Auto-generated method stub
                    return false;
                }
                @Override
                public void newEntry( File emptyDirectoryFile )
                {
                    model.add( emptyDirectoryFile );
                }
                @Override
                public void findStarted()
                {
                    // TODO Auto-generated method stub
                }
                @Override
                public void findDone()
                {
                    // TODO Auto-generated method stub
                }
            };

            emptyDirs.addListener( listener );

            try {
                emptyDirs.find();
                }
            catch( CancelRequestException e ) {
                e.printStackTrace();
                }
        }

        //XEmptyDirectoriesTreeModel model = new EmptyDirectoriesTreeModel( fileTree );

        // Create a JTree and tell it to display our model
        JTree tree = new JTree(model);
        tree.setCellRenderer(new EmptyDirectoryCheckBoxNodeRenderer(model));
        tree.setCellEditor(new EmptyDirectoryCheckBoxNodeEditor(model));
        tree.setEditable(true);

        // The JTree can get big, so allow it to scroll
        JScrollPane scrollpane = new JScrollPane(tree);

        // Display it all in a window and make the window appear
        JFrame frame = new JFrame("Delete Empty Directory Tree Demo");
        frame.getContentPane().add(scrollpane, "Center");
        frame.setSize(400,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
