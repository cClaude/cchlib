/**
 *
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * Class for VS4E only !
 *
 * @author Claude CHOISNET
 *
 */
//VS4E -- DO NOT REMOVE THIS LINE!
public class LoadDialogVS4E extends JDialog
{
    private static final long   serialVersionUID        = 1L;
    private ButtonGroup buttonGroup_FileType;
    private JButton jButton_Cancel;
    private JButton jButton_Left;
    private JButton jButton_Ok;
    private JButton jButton_Right;
    private JLabel jLabel_Fake1;
    //private JLabel jLabel_Fake2;
    private JPanel jPanel_CancelOk;
    private JPanel jPanel_Left;
    private JPanel jPanel_Params;
    private JPanel jPanel_Right;
    private JPanel jPanel_TabSelect;
    protected JCheckBox jCheckBox_CUT_LINE_AFTER_HTML_BR;
    protected JCheckBox jCheckBox_CUT_LINE_AFTER_HTML_END_P;
    protected JCheckBox jCheckBox_CUT_LINE_AFTER_NEW_LINE;
    protected JCheckBox jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P;
    protected JCheckBox jCheckBox_CUT_LINE_BEFORE_HTML_BR;
    protected JCheckBox jCheckBox_FormattedProperties;
    protected JCheckBox jCheckBox_LeftReadOnly;
    protected JCheckBox jCheckBox_Properties;
    protected JCheckBox jCheckBox_RightUseLeftHasDefaults;
    protected JCheckBox jCheckBox_ini;
    protected JPanel jPanel_TabFMTProperties;
    protected JPanel jPanel_TabProperties;
    protected JTabbedPane jTabbedPaneRoot;
    protected JTextField jTextField_Left;
    protected JTextField jTextField_Right;
    protected JCheckBox jCheckBox_ShowLineNumbers;
//    private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public LoadDialogVS4E()
    {
        initComponents();
    }

    public LoadDialogVS4E( Frame parent )
    {
        super( parent );
        initComponents();
    }

    private void initComponents() {
    	setFont(new Font("Dialog", Font.PLAIN, 12));
    	setBackground(Color.white);
    	setForeground(Color.black);
    	add(getJTabbedRoot(), BorderLayout.CENTER);
    	add(getJPanel_CancelOk(), BorderLayout.SOUTH);
    	initButtonGroup_FileType();
    	setSize(500, 240);
    }

    private JCheckBox getJCheckBox_ShowLineNumbers() {
    	if (jCheckBox_ShowLineNumbers == null) {
    	    jCheckBox_ShowLineNumbers = new JCheckBox();
    	    jCheckBox_ShowLineNumbers.setText("Show line numbers (if possible)");
    	}
    	return jCheckBox_ShowLineNumbers;
    }

    private JCheckBox getJCheckBox_CUT_LINE_AFTER_HTML_END_P() {
        if (jCheckBox_CUT_LINE_AFTER_HTML_END_P == null) {
            jCheckBox_CUT_LINE_AFTER_HTML_END_P = new JCheckBox();
            jCheckBox_CUT_LINE_AFTER_HTML_END_P.setText("CUT_LINE_AFTER_HTML_END_P");
        }
        return jCheckBox_CUT_LINE_AFTER_HTML_END_P;
    }

    private JCheckBox getJCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P() {
        if (jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P == null) {
            jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new JCheckBox();
            jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P.setText("CUT_LINE_BEFORE_HTML_BEGIN_P");
        }
        return jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P;
    }

    private JCheckBox getJCheckBox_CUT_LINE_BEFORE_HTML_BR() {
        if (jCheckBox_CUT_LINE_BEFORE_HTML_BR == null) {
            jCheckBox_CUT_LINE_BEFORE_HTML_BR = new JCheckBox();
            jCheckBox_CUT_LINE_BEFORE_HTML_BR.setText("CUT_LINE_BEFORE_HTML_BR");
        }
        return jCheckBox_CUT_LINE_BEFORE_HTML_BR;
    }

    private JCheckBox getJCheckBox_CUT_LINE_AFTER_HTML_BR() {
        if (jCheckBox_CUT_LINE_AFTER_HTML_BR == null) {
            jCheckBox_CUT_LINE_AFTER_HTML_BR = new JCheckBox();
            jCheckBox_CUT_LINE_AFTER_HTML_BR.setText("CUT_LINE_AFTER_HTML_BR");
        }
        return jCheckBox_CUT_LINE_AFTER_HTML_BR;
    }

    private JCheckBox getJCheckBox_CUT_LINE_AFTER_NEW_LINE() {
        if (jCheckBox_CUT_LINE_AFTER_NEW_LINE == null) {
            jCheckBox_CUT_LINE_AFTER_NEW_LINE = new JCheckBox();
            jCheckBox_CUT_LINE_AFTER_NEW_LINE.setText("CUT_LINE_AFTER_NEW_LINE");
        }
        return jCheckBox_CUT_LINE_AFTER_NEW_LINE;
    }

    private JPanel getJPanel_TabFMTProperties() {
    	if (jPanel_TabFMTProperties == null) {
    		jPanel_TabFMTProperties = new JPanel();
    		jPanel_TabFMTProperties.setLayout(new BoxLayout(jPanel_TabFMTProperties, BoxLayout.Y_AXIS));
    		jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_NEW_LINE());
    		jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_HTML_BR());
    		jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_BEFORE_HTML_BR());
    		jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P());
    		jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_HTML_END_P());
    	}
    	return jPanel_TabFMTProperties;
    }

    private JCheckBox getJCheckBox_RightUseLeftHasDefaults() {
        if (jCheckBox_RightUseLeftHasDefaults == null) {
            jCheckBox_RightUseLeftHasDefaults = new JCheckBox();
            jCheckBox_RightUseLeftHasDefaults.setText("Right file use Left file has defaults");
        }
        return jCheckBox_RightUseLeftHasDefaults;
    }

    private JLabel getJLabel_Fake1() {
        if (jLabel_Fake1 == null) {
            jLabel_Fake1 = new JLabel();
            //jLabel_Fake1.setText("jLabel1");
        }
        return jLabel_Fake1;
    }

    private JCheckBox getJCheckBox_LeftReadOnly() {
        if (jCheckBox_LeftReadOnly == null) {
            jCheckBox_LeftReadOnly = new JCheckBox();
            jCheckBox_LeftReadOnly.setText("Left file readonly");
        }
        return jCheckBox_LeftReadOnly;
    }

    private void initButtonGroup_FileType() {
    	buttonGroup_FileType = new ButtonGroup();
    	buttonGroup_FileType.add(getJCheckBox_Properties());
    	buttonGroup_FileType.add(getJCheckBox_FormattedProperties());
    	buttonGroup_FileType.add(getJCheckBox_ini());
    }

    private JPanel getJPanel_Params() {
    	if (jPanel_Params == null) {
    		jPanel_Params = new JPanel();
    		jPanel_Params.setLayout(new GridLayout(3, 2));
    		jPanel_Params.add(getJCheckBox_Properties());
    		jPanel_Params.add(getJCheckBox_LeftReadOnly());
    		jPanel_Params.add(getJCheckBox_FormattedProperties());
    		jPanel_Params.add(getJCheckBox_ShowLineNumbers());
    		jPanel_Params.add(getJCheckBox_ini());
    		jPanel_Params.add(getJLabel_Fake1());
    	}
    	return jPanel_Params;
    }

    private JCheckBox getJCheckBox_Properties() {
        if (jCheckBox_Properties == null) {
            jCheckBox_Properties = new JCheckBox();
            jCheckBox_Properties.setText("Properties");
        }
        return jCheckBox_Properties;
    }

    private JCheckBox getJCheckBox_ini() {
        if (jCheckBox_ini == null) {
            jCheckBox_ini = new JCheckBox();
            jCheckBox_ini.setText("*.ini files");
            jCheckBox_ini.setEnabled(false);
        }
        return jCheckBox_ini;
    }

    private JCheckBox getJCheckBox_FormattedProperties() {
        if (jCheckBox_FormattedProperties == null) {
            jCheckBox_FormattedProperties = new JCheckBox();
            jCheckBox_FormattedProperties.setText("Formatted Properties");
        }
        return jCheckBox_FormattedProperties;
    }

    private JPanel getJPanel_CancelOk() {
        if (jPanel_CancelOk == null) {
            jPanel_CancelOk = new JPanel();
            jPanel_CancelOk.add(getJButton_Ok());
            jPanel_CancelOk.add(getJButton_Cancel());
        }
        return jPanel_CancelOk;
    }

    private JButton getJButton_Ok() {
        if (jButton_Ok == null) {
            jButton_Ok = new JButton();
            jButton_Ok.setText("OK");
            jButton_Ok.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_OkMouseMousePressed(event);
                }
            });
        }
        return jButton_Ok;
    }

    private JButton getJButton_Cancel() {
        if (jButton_Cancel == null) {
            jButton_Cancel = new JButton();
            jButton_Cancel.setText("Cancel");
            jButton_Cancel.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_CancelMouseMousePressed(event);
                }
            });
        }
        return jButton_Cancel;
    }

    private JTabbedPane getJTabbedRoot() {
    	if (jTabbedPaneRoot == null) {
    		jTabbedPaneRoot = new JTabbedPane();
    		jTabbedPaneRoot.addTab("Files", getJPanel_TabSelect());
    		jTabbedPaneRoot.addTab("Properties", getJPanel_TabProperties());
    		jTabbedPaneRoot.addTab("Formatted Properties", getJPanel_TabFMTProperties());
    	}
    	return jTabbedPaneRoot;
    }

    private JPanel getJPanel_TabProperties() {
        if (jPanel_TabProperties == null) {
            jPanel_TabProperties = new JPanel();
            jPanel_TabProperties.add(getJCheckBox_RightUseLeftHasDefaults());
        }
        return jPanel_TabProperties;
    }

    private JPanel getJPanel_TabSelect() {
    	if (jPanel_TabSelect == null) {
    		jPanel_TabSelect = new JPanel();
    		jPanel_TabSelect.setLayout(new BoxLayout(jPanel_TabSelect, BoxLayout.Y_AXIS));
    		jPanel_TabSelect.add(getJPanel_Left());
    		jPanel_TabSelect.add(getJPanel_Right());
    		jPanel_TabSelect.add(getJPanel_Params());
    	}
    	return jPanel_TabSelect;
    }

    private JPanel getJPanel_Left() {
        if (jPanel_Left == null) {
            jPanel_Left = new JPanel();
            jPanel_Left.setBorder(BorderFactory.createTitledBorder(null, "Left", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD,
                    12), new Color(51, 51, 51)));
            jPanel_Left.setLayout(new BorderLayout());
            jPanel_Left.add(getJButton_Left(), BorderLayout.EAST);
            jPanel_Left.add(getJTextField_Left(), BorderLayout.CENTER);
        }
        return jPanel_Left;
    }

    private JPanel getJPanel_Right() {
        if (jPanel_Right == null) {
            jPanel_Right = new JPanel();
            jPanel_Right.setBorder(BorderFactory.createTitledBorder(null, "Right", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog",
                    Font.BOLD, 12), new Color(51, 51, 51)));
            jPanel_Right.setLayout(new BorderLayout());
            jPanel_Right.add(getJButton_Right(), BorderLayout.EAST);
            jPanel_Right.add(getJTextField_Right(), BorderLayout.CENTER);
        }
        return jPanel_Right;
    }

    private JButton getJButton_Left() {
        if (jButton_Left == null) {
            jButton_Left = new JButton();
            jButton_Left.setText("select");
            jButton_Left.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_LeftMouseMousePressed(event);
                }
            });
        }
        return jButton_Left;
    }

    private JButton getJButton_Right() {
        if (jButton_Right == null) {
            jButton_Right = new JButton();
            jButton_Right.setText("select");
            jButton_Right.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButton_RightMouseMousePressed(event);
                }
            });
        }
        return jButton_Right;
    }

    private JTextField getJTextField_Left() {
        if (jTextField_Left == null) {
            jTextField_Left = new JTextField();
        }
        return jTextField_Left;
    }

    private JTextField getJTextField_Right() {
        if (jTextField_Right == null) {
            jTextField_Right = new JTextField();
          }
        return jTextField_Right;
    }

//    @SuppressWarnings("unused")
//    private static void installLnF()
//    {
//        try {
//            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
//            if( lnfClassname == null )
//                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
//            UIManager.setLookAndFeel( lnfClassname );
//        }
//        catch( Exception e ) {
//            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
//                    + " on this platform:" + e.getMessage() );
//        }
//    }

    public static void main( String[] args )
    {
//        installLnF();
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                LoadDialogVS4E dialog = new LoadDialogVS4E();
                dialog.setDefaultCloseOperation( LoadDialogVS4E.DISPOSE_ON_CLOSE );
                dialog.setTitle( "Load Dialog (Debug)" );
                dialog.setLocationRelativeTo( null );
                dialog.getContentPane().setPreferredSize( dialog.getSize() );
                dialog.pack();
                dialog.setVisible( true );
            }
        } );
    }

    protected void jButton_LeftMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButton_RightMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButton_CancelMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }

    protected void jButton_OkMouseMousePressed(MouseEvent event)
    {
        throw new UnsupportedOperationException();
    }
}
