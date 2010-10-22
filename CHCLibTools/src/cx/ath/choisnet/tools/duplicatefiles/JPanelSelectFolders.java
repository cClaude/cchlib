package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

/**
 * TODO: replace JList by JTable
 * -> add comment if a folder is a child of an other
 * one, say that it will be ignored, and ignore it !
 * -> store a tree of folders list to find duplicate !
 *
 * Create classes TreeFile and TreeFile.Node
 *
 * TreeFile.Node = name;[File][TreeFile.Node]*
 * TreeFile = [TreeFile.Node (where TreeFile.Node.File.getParent() == null)]*
 *
 * @author Claude CHOISNET
 *
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelSelectFolders
    extends JPanel
        implements Iterable<File>
{
    private static final long serialVersionUID = 1L;
    private Logger slogger = Logger.getLogger( JPanelSelectFolders.class );
    // TODO:Must be restore by parent !
    private transient MyToolKit myToolKit;
    /* @serial */
    private DefaultListModel listModelDirectoryFiles = new DefaultListModel();
    /* @serial */
    private JScrollPane jScrollPaneSelectedDirs;
    /* @serial */
    private JList jListSelectedDirs;
    /* @serial */
    private JPanel jPanelDirButtons;
    /* @serial */
    private JButton jButtonAddDir;
    /* @serial */
    private JButton jButtonRemDir;
    /* @serial */
    private JTextField jTextFieldCurrentDir;
    /* @serial */
    private JButton jButtonSelectDir;

    public JPanelSelectFolders()
    {
        initComponents();
    }

    public void initFixComponents( MyToolKit myToolKit )
    {
        this.myToolKit = myToolKit;
        jListSelectedDirs.setModel( listModelDirectoryFiles );
    }

    private void initComponents()
    {
        setLayout(new BorderLayout());
        add(getJScrollPaneSelectedDirs(), BorderLayout.CENTER);
        add(getJPanelDirButtons(), BorderLayout.NORTH);

        setSize( 320, 240 );
    }

    private JScrollPane getJScrollPaneSelectedDirs() {
        if (jScrollPaneSelectedDirs == null) {
            jScrollPaneSelectedDirs = new JScrollPane();
            jScrollPaneSelectedDirs.setViewportView(getJListSelectedDirs());
        }
        return jScrollPaneSelectedDirs;
    }

    private JList getJListSelectedDirs() {
        if (jListSelectedDirs == null) {
            jListSelectedDirs = new JList();
        }
        return jListSelectedDirs;
    }

    private JPanel getJPanelDirButtons() {
        if (jPanelDirButtons == null) {
            jPanelDirButtons = new JPanel();
            jPanelDirButtons.setLayout(new BoxLayout(jPanelDirButtons, BoxLayout.X_AXIS));
            jPanelDirButtons.add(getJTextFieldCurrentDir());
            jPanelDirButtons.add(getJButtonSelectDir());
            jPanelDirButtons.add(getJButtonAddDir());
            jPanelDirButtons.add(getJButtonRemDir());
        }
        return jPanelDirButtons;
    }

    private JButton getJButtonAddDir() {
        if (jButtonAddDir == null) {
            jButtonAddDir = new JButton();
            jButtonAddDir.setText("Add");
            jButtonAddDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonAddDirMouseMousePressed(event);
                }
            });
        }
        return jButtonAddDir;
    }

    private JButton getJButtonSelectDir() {
        if (jButtonSelectDir == null) {
            jButtonSelectDir = new JButton();
            jButtonSelectDir.setText("...");
            jButtonSelectDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonSelectDirMouseMousePressed(event);
                }
            });
        }
        return jButtonSelectDir;
    }

    private JTextField getJTextFieldCurrentDir() {
        if (jTextFieldCurrentDir == null) {
            jTextFieldCurrentDir = new JTextField();
        }
        return jTextFieldCurrentDir;
    }

    private JButton getJButtonRemDir() {
        if (jButtonRemDir == null) {
            jButtonRemDir = new JButton();
            jButtonRemDir.setText("Rem");
            jButtonRemDir.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonRemDirMouseMousePressed(event);
                }
            });
        }
        return jButtonRemDir;
    }

    private void jButtonSelectDirMouseMousePressed( MouseEvent event )
    {
        JFileChooser jfc = myToolKit.getJFileChooser();
        int returnVal = jfc.showOpenDialog( this );

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            // File[] files = jfc.getSelectedFiles();
            //
            // for(File f:files) {
            // slogger.info( "selected(s):" + f );
            // listModelDirectoryFiles.addElement( f );
            // }
            slogger.info( "selected:" + jfc.getSelectedFile() );
            addDirectoryFile( jfc.getSelectedFile() );
        }
    }

    private void jButtonAddDirMouseMousePressed( MouseEvent event )
    {
        File f = new File( jTextFieldCurrentDir.getText() );
        addDirectoryFile( f );
    }

    private void jButtonRemDirMouseMousePressed( MouseEvent event )
    {
        final int[] selecteds = jListSelectedDirs.getSelectedIndices();

        for( int selected : selecteds ) {
            listModelDirectoryFiles.remove( selected );
        }
    }

    private boolean addDirectoryFile( File f )
    {
        if( f.isDirectory() ) {
            boolean isPresent = false;
            final int len = listModelDirectoryFiles.size();

            for( int i = 0; i < len; i++ ) {
                File aFile = (File)listModelDirectoryFiles.getElementAt( i );

                if( aFile.equals( f ) ) {
                    isPresent = true;
                    break;
                }
            }

            if( !isPresent ) {
                listModelDirectoryFiles.addElement( f );
                return true;
            }
        }

        myToolKit.beep();
        return false;
    }

    public int directoriesSize()
    {
        return listModelDirectoryFiles.size();
    }

    @Override
    public Iterator<File> iterator()
    {
        final int len = listModelDirectoryFiles.size();
        return new Iterator<File>()
        {
            int i = 0;
            @Override
            public boolean hasNext()
            {
                return i<len;
            }
            @Override
            public File next()
            {
                return (File)listModelDirectoryFiles.getElementAt( i++ );
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
