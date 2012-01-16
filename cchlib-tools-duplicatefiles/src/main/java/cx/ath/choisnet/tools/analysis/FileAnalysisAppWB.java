package cx.ath.choisnet.tools.analysis;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;

/**
 *
 *
 */
public abstract class FileAnalysisAppWB
{
    private JFrame frame;
    private JTextField jTextField_CurrentRootDirectory;
    private JProgressBar jProgressBar;
    private JTextArea jTextArea_Logs;
    private JButton jButton_Cancel;

    /**
     * Create the application.
     */
    public FileAnalysisAppWB()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds( 100, 100, 450, 300 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel jLabel_CurrentRootDirectory = new JLabel("Current root directory :");
        jLabel_CurrentRootDirectory.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabel_CurrentRootDirectory = new GridBagConstraints();
        gbc_jLabel_CurrentRootDirectory.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabel_CurrentRootDirectory.insets = new Insets(0, 0, 5, 5);
        gbc_jLabel_CurrentRootDirectory.gridx = 0;
        gbc_jLabel_CurrentRootDirectory.gridy = 0;
        frame.getContentPane().add(jLabel_CurrentRootDirectory, gbc_jLabel_CurrentRootDirectory);

        jTextField_CurrentRootDirectory = new JTextField();
        jTextField_CurrentRootDirectory.setEditable(false);
        jLabel_CurrentRootDirectory.setLabelFor(jTextField_CurrentRootDirectory);
        GridBagConstraints gbc_jTextField_CurrentRootDirectory = new GridBagConstraints();
        gbc_jTextField_CurrentRootDirectory.insets = new Insets(0, 0, 5, 0);
        gbc_jTextField_CurrentRootDirectory.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextField_CurrentRootDirectory.gridx = 1;
        gbc_jTextField_CurrentRootDirectory.gridy = 0;
        frame.getContentPane().add(jTextField_CurrentRootDirectory, gbc_jTextField_CurrentRootDirectory);
        jTextField_CurrentRootDirectory.setColumns(10);

        jTextArea_Logs = new JTextArea();
        GridBagConstraints gbc_jTextArea_Logs = new GridBagConstraints();
        gbc_jTextArea_Logs.insets = new Insets(0, 0, 5, 0);
        gbc_jTextArea_Logs.gridwidth = 2;
        gbc_jTextArea_Logs.fill = GridBagConstraints.BOTH;
        gbc_jTextArea_Logs.gridx = 0;
        gbc_jTextArea_Logs.gridy = 1;
        frame.getContentPane().add(jTextArea_Logs, gbc_jTextArea_Logs);

        jButton_Cancel = new JButton("Cancel");
        jButton_Cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                jButton_Cancel_mouseClicked( event );
            }
        });
        GridBagConstraints gbc_jButton_Cancel = new GridBagConstraints();
        gbc_jButton_Cancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButton_Cancel.gridx = 0;
        gbc_jButton_Cancel.gridy = 2;
        frame.getContentPane().add(jButton_Cancel, gbc_jButton_Cancel);
        
        jProgressBar = new JProgressBar();
        GridBagConstraints gbc_jProgressBar = new GridBagConstraints();
        gbc_jProgressBar.gridx = 1;
        gbc_jProgressBar.gridy = 2;
        frame.getContentPane().add(jProgressBar, gbc_jProgressBar);
    }

    protected abstract void jButton_Cancel_mouseClicked( MouseEvent event );

    protected JFrame getJFrame()
    {
        return frame;
    }

    protected JTextField getJTextField_CurrentRootDirectory()
    {
        return jTextField_CurrentRootDirectory;
    }
    
    protected JProgressBar getJProgressBar() 
    {
        return jProgressBar;
    }
    
    protected JTextArea getJTextArea_Logs() 
    {
        return jTextArea_Logs;
    }
    
    protected JButton getJButton_Cancel() 
    {
        return jButton_Cancel;
    }
}
