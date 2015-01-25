package com.googlecode.cchlib.samples.swing;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
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

/**
 *
 */
public class JHexEditorTest extends WindowAdapter
{
    private final JFrame win;
    private WaitingJFileChooserInitializer waitingJFileChooserInitializer;
    private ArrayReadWriteAccess mainArrayAccess;

    public JHexEditorTest() throws IOException
    {
        // Build a test entry
        {
            final byte[] ar = new byte[16 * 24];
            Arrays.fill(ar,(byte)0);

            for( int b = 0; b<256; b++ ) {
                ar[ b ] = (byte) b;
                }

            this.mainArrayAccess = new DefaultArrayReadWriteAccess( ar );
        }

        final DefaultHexEditorModel model = new DefaultHexEditorModel();

        model.setArrayAccess( this.mainArrayAccess );

        this.win = new JFrame();
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        this.win.getContentPane().setLayout(gridBagLayout);

        final JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        final GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        this.win.getContentPane().add(panel, gbc_panel);

        {
            final JButton btnSelectFileRWDirect = new JButton("Select a file (direct Read/Write)");
            btnSelectFileRWDirect.addActionListener(event -> {
                final File f = getFile();

                if( f != null ) {
                    try( final ArrayReadWriteAccess arrayAccess = new ArrayReadWriteAccessFile( f ) ) {
                        model.setArrayAccess( arrayAccess );
                        }
                    catch( final IOException e ) {
                        e.printStackTrace();
                        }
                    }
            });
            panel.add(btnSelectFileRWDirect);
        }
        {
            final JButton btnSelectFileRO = new JButton("Select a file (Read Only)");
            btnSelectFileRO.addActionListener(event -> {
                final File f = getFile();

                if( f != null ) {
                    try( final ArrayReadAccess arrayAccess = new ArrayReadAccessFile( f ) ) {
                        model.setArrayAccess( arrayAccess );
                        }
                    catch( final IOException e ) {
                        e.printStackTrace();
                        }
                    }
                });
            panel.add(btnSelectFileRO);
        }


        final JHexEditor editor = new JHexEditor( model );
        final GridBagConstraints gbc_editor = new GridBagConstraints();
        gbc_editor.gridwidth = 3;
        gbc_editor.fill = GridBagConstraints.BOTH;
        gbc_editor.gridx = 0;
        gbc_editor.gridy = 1;
        this.win.getContentPane().add( editor, gbc_editor );
        this.win.addWindowListener(this);
        this.win.pack();
        this.win.setVisible(true);

        // Init JFileChooser
        getJFileChooserInitializer();
    }

    @Override
    public void windowClosing(final WindowEvent e)
    {
        System.exit(0);
    }

    private JFileChooserInitializerCustomize createJFileChooserInitializerConfigurator()
    {
        return new LasyJFCCustomizer();
    }

    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( this.waitingJFileChooserInitializer == null ) {
            final JFileChooserInitializerCustomize     config    = createJFileChooserInitializerConfigurator();
            final String         waitTitle    = "waitTitle";
            final String         waitMessage    = "waitMessage";
            this.waitingJFileChooserInitializer = new WaitingJFileChooserInitializer(config, this.win, waitTitle, waitMessage );
            }

        return this.waitingJFileChooserInitializer;
    }

    private JFileChooser getJFileChooser()
    {
        return getJFileChooserInitializer().getJFileChooser();
    }

    private File getFile()
    {
        final JFileChooser jfc = getJFileChooser();

        final int returnVal = jfc.showOpenDialog(this.win);

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
            final File f = jfc.getSelectedFile();

            System.out.printf( "You chose to open this file: %s\n", f);
            return f;
            }
        else {
            return null;
            }
    }

    public static void main(final String[] args) throws IOException
    {
        new JHexEditorTest();
    }
}
