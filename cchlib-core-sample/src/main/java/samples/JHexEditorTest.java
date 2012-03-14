package samples;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerCustomize;
import com.googlecode.cchlib.swing.filechooser.LasyJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.hexeditor.ArrayReadAccess;
import com.googlecode.cchlib.swing.hexeditor.ArrayReadAccessFile;
import com.googlecode.cchlib.swing.hexeditor.ArrayReadWriteAccess;
import com.googlecode.cchlib.swing.hexeditor.ArrayReadWriteAccessFile;
import com.googlecode.cchlib.swing.hexeditor.DefaultArrayReadWriteAccess;
import com.googlecode.cchlib.swing.hexeditor.DefaultHexEditorModel;
import com.googlecode.cchlib.swing.hexeditor.JHexEditor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;

/**
 *
 */
public class JHexEditorTest extends WindowAdapter
{
    private JFrame win;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;

    public JHexEditorTest() throws IOException
    {
        ArrayReadWriteAccess arrayAccess;

        // Build a test entry
        {
            byte[] ar = new byte[16 * 24];
            Arrays.fill(ar,(byte)0);

            for( int b = 0; b<256; b++ ) {
                ar[ b ] = (byte) b;
                }

            arrayAccess = new DefaultArrayReadWriteAccess( ar );
        }

        final DefaultHexEditorModel model = new DefaultHexEditorModel();
        model.setArrayAccess( arrayAccess );

        win = new JFrame();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        win.getContentPane().setLayout(gridBagLayout);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        win.getContentPane().add(panel, gbc_panel);

        {
            JButton btnSelectFileRWDirect = new JButton("Select a file (direct Read/Write)");
            btnSelectFileRWDirect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event)
                {
                    File f = getFile();

                    if( f != null ) {
                        try {
                            ArrayReadWriteAccess arrayAccess = new ArrayReadWriteAccessFile( f );

                            model.setArrayAccess( arrayAccess );
                            }
                        catch( FileNotFoundException e ) {
                            e.printStackTrace();
                            }
                        }
                }
            });
            panel.add(btnSelectFileRWDirect);
        }
        {
            JButton btnSelectFileRO = new JButton("Select a file (Read Only)");
            btnSelectFileRO.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event)
                {
                    File f = getFile();

                    if( f != null ) {
                        try {
                            ArrayReadAccess arrayAccess = new ArrayReadAccessFile( f );

                            model.setArrayAccess( arrayAccess );
                            }
                        catch( FileNotFoundException e ) {
                            e.printStackTrace();
                            }
                        }
                    }
            });
            panel.add(btnSelectFileRO);
        }


        JHexEditor editor = new JHexEditor( model );
        GridBagConstraints gbc_editor = new GridBagConstraints();
        gbc_editor.gridwidth = 3;
        gbc_editor.fill = GridBagConstraints.BOTH;
        gbc_editor.gridx = 0;
        gbc_editor.gridy = 1;
        win.getContentPane().add( editor, gbc_editor );
        win.addWindowListener(this);
        win.pack();
        win.setVisible(true);

        // Init JFileChooser
        getJFileChooserInitializer();
    }

    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    private JFileChooserInitializerCustomize createJFileChooserInitializerConfigurator()
    {
        return new LasyJFCCustomizer();
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( waitingJFileChooserInitializer == null ) {
            JFileChooserInitializerCustomize     config    = createJFileChooserInitializerConfigurator();
            String         waitTitle    = "waitTitle";
            String         waitMessage    = "waitMessage";
            waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(config, win, waitTitle, waitMessage );
            }

        return waitingJFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        return getJFileChooserInitializer().getJFileChooser();
    }

    private File getFile()
    {
        JFileChooser jfc = getJFileChooser();

        int returnVal = jfc.showOpenDialog(win);

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            File f = jfc.getSelectedFile();

            System.out.printf( "You chose to open this file: %s\n", f);
            return f;
            }
        else {
            return null;
            }
    }

    public static void main(String arg[]) throws IOException
    {
        new JHexEditorTest();
    }
}
