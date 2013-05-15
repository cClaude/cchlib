package com.googlecode.cchlib.i18n.sample;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18n;
import com.googlecode.cchlib.i18n.I18nForce;
import com.googlecode.cchlib.i18n.I18nIgnore;
import com.googlecode.cchlib.i18n.I18nToolTipText;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelperAutoUpdatable;

/**
 *
 */
public class FakePanel extends JPanel implements I18nAutoUpdatable, I18nPrepHelperAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FakePanel.class );

    @I18nIgnore private JLabel jLabelNoI18n; // No I18n
    private JProgressBar jProgressBar; // No I18n

    private JCheckBox jCheckBox; // I18n default process

    @I18n(id="JButtonID")            private JButton jButton;
    @I18n(keyName="JLabelID")        private JLabel jLabel;
    @I18n(method="I18nJPanelBorder") private Object dummy; // ex: declare field to apply a custom method

    @I18nForce private JTextArea   jTextArea;
    @I18nForce private JTextField  myJTextField;
    @I18nForce private JEditorPane jEditorPane;
    @I18nForce(id="myJTextFieldDefineWithId") private JTextField myJTextFieldDefineWithId; // use specific Id TODO
    @I18nForce private JProgressBar jProgressBarToI18n; // TODO

    @I18nToolTipText private JButton buttonToolTipsButton; // TODO
    @I18nIgnore @I18nToolTipText private JButton buttonIgnoreButton; // TODO

    private JPanel jPanel; // TODO @I18nTitledBorder private JPanel jPanel;

    public void setI18nJPanelBorder( String str )
    {
        logger.debug( "setI18nJPanelBorder: [" + str + ']' );
        TitledBorder tb = TitledBorder.class.cast( getBorder() );

        tb.setTitle( str );
    }

    public String getI18nJPanelBorder()
    {
        logger.debug( "getI18nJPanelBorder()" );
        TitledBorder tb = TitledBorder.class.cast( getBorder() );

        String str = tb.getTitle();
        logger.debug( "getI18nJPanelBorder(): [" + str + ']' );
        return str;
    }

    /**
     *
     */
    public FakePanel()
    {
        setBorder(new TitledBorder(null, "TitleBorder", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{80, 0, 0, 50, 0, 0};
        gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            jLabel = new JLabel("jLabel");
            jLabel.setToolTipText("<html>jLabel<br/>toolTipText to I18n</html>");
            GridBagConstraints gbc_jLabel = new GridBagConstraints();
            gbc_jLabel.fill = GridBagConstraints.VERTICAL;
            gbc_jLabel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel.anchor = GridBagConstraints.EAST;
            gbc_jLabel.gridx = 0;
            gbc_jLabel.gridy = 0;
            add(jLabel, gbc_jLabel);
        }
        {
            jProgressBar = new JProgressBar();
            this.jProgressBar.setStringPainted(true);
            jProgressBar.setValue(50);
            jProgressBar.setString( "My JProgressBar" );
            jProgressBar.setToolTipText("<html>JProgressBar<br/>ToolTipText to i18n</html>");
            jProgressBar.setOrientation(SwingConstants.VERTICAL);
            GridBagConstraints gbc_jProgressBar = new GridBagConstraints();
            gbc_jProgressBar.gridheight = 5;
            gbc_jProgressBar.fill = GridBagConstraints.VERTICAL;
            gbc_jProgressBar.insets = new Insets(0, 0, 5, 5);
            gbc_jProgressBar.gridx = 1;
            gbc_jProgressBar.gridy = 0;
            add(jProgressBar, gbc_jProgressBar);
        }
        {
            this.jProgressBarToI18n = new JProgressBar();
            this.jProgressBarToI18n.setValue(50);
            this.jProgressBarToI18n.setToolTipText("<html>JProgressBar to I18n<br/>ToolTipText to i18n</html>");
            this.jProgressBarToI18n.setStringPainted(true);
            this.jProgressBarToI18n.setString("My JProgressBar to I18n");
            this.jProgressBarToI18n.setOrientation(SwingConstants.VERTICAL);
            GridBagConstraints gbc_jProgressBarToI18n = new GridBagConstraints();
            gbc_jProgressBarToI18n.fill = GridBagConstraints.VERTICAL;
            gbc_jProgressBarToI18n.gridheight = 5;
            gbc_jProgressBarToI18n.insets = new Insets(0, 0, 5, 5);
            gbc_jProgressBarToI18n.gridx = 2;
            gbc_jProgressBarToI18n.gridy = 0;
            add(this.jProgressBarToI18n, gbc_jProgressBarToI18n);
        }
        {
            jTextArea = new JTextArea();
            jTextArea.setEditable(false);
            jTextArea.setToolTipText("<html>jTextArea<br/>ToolTipText to i18n</html>");
            jTextArea.setText("jTextArea l1\r\njTextArea l2");
            GridBagConstraints gbc_jTextArea = new GridBagConstraints();
            gbc_jTextArea.gridwidth = 2;
            gbc_jTextArea.insets = new Insets(0, 0, 5, 0);
            gbc_jTextArea.gridheight = 4;
            gbc_jTextArea.fill = GridBagConstraints.BOTH;
            gbc_jTextArea.gridx = 3;
            gbc_jTextArea.gridy = 0;
            add(jTextArea, gbc_jTextArea);
        }
        {
            jLabelNoI18n = new JLabel("jLabel (No_I18n)");
            GridBagConstraints gbc_jLabelNoI18n = new GridBagConstraints();
            gbc_jLabelNoI18n.anchor = GridBagConstraints.EAST;
            gbc_jLabelNoI18n.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelNoI18n.gridx = 0;
            gbc_jLabelNoI18n.gridy = 1;
            add(jLabelNoI18n, gbc_jLabelNoI18n);
        }
        {
            jButton = new JButton("JButton ");
            jButton.setToolTipText("<html>JButton<br/>toolTipText to I18n</html>");
            GridBagConstraints gbc_jButton = new GridBagConstraints();
            gbc_jButton.anchor = GridBagConstraints.EAST;
            gbc_jButton.insets = new Insets(0, 0, 5, 5);
            gbc_jButton.gridx = 0;
            gbc_jButton.gridy = 2;
            add(jButton, gbc_jButton);
        }
        {
            jCheckBox = new JCheckBox("jCheckBox");
            jCheckBox.setToolTipText("<html>JCheckBox<br/>toolTipText to I18n</html>");
            GridBagConstraints gbc_jCheckBox = new GridBagConstraints();
            gbc_jCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox.gridx = 0;
            gbc_jCheckBox.gridy = 3;
            add(jCheckBox, gbc_jCheckBox);
        }
        {
            jPanel = new JPanel();
            jPanel.setBorder(new TitledBorder(null, "My TitledBorder 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            GridBagConstraints gbc_jPanel = new GridBagConstraints();
            gbc_jPanel.gridwidth = 2;
            gbc_jPanel.insets = new Insets(0, 0, 5, 0);
            gbc_jPanel.fill = GridBagConstraints.BOTH;
            gbc_jPanel.gridx = 3;
            gbc_jPanel.gridy = 4;
            add(jPanel, gbc_jPanel);
        }
        GridBagLayout gbl_jPanel = new GridBagLayout();
        gbl_jPanel.columnWidths = new int[]{0, 0, 0};
        gbl_jPanel.rowHeights = new int[]{0, 0, 0};
        gbl_jPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_jPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.jPanel.setLayout(gbl_jPanel);
        {
            myJTextField = new JTextField();
            myJTextField.setEditable(false);
            myJTextField.setToolTipText("<html>jTextField<br/>toolTipText to I18n</html>");
            myJTextField.setText("My JTextField");
            GridBagConstraints gbc_myJTextField = new GridBagConstraints();
            gbc_myJTextField.fill = GridBagConstraints.BOTH;
            gbc_myJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_myJTextField.gridx = 0;
            gbc_myJTextField.gridy = 0;
            jPanel.add(myJTextField, gbc_myJTextField);
            myJTextField.setColumns(10);
        }
        {
            this.myJTextFieldDefineWithId = new JTextField();
            this.myJTextFieldDefineWithId.setText("My JTextField define with ID");
            GridBagConstraints gbc_myJTextFieldDefineWithId = new GridBagConstraints();
            gbc_myJTextFieldDefineWithId.insets = new Insets(0, 0, 5, 0);
            gbc_myJTextFieldDefineWithId.fill = GridBagConstraints.BOTH;
            gbc_myJTextFieldDefineWithId.gridx = 1;
            gbc_myJTextFieldDefineWithId.gridy = 0;
            this.jPanel.add(this.myJTextFieldDefineWithId, gbc_myJTextFieldDefineWithId);
            this.myJTextFieldDefineWithId.setColumns(10);
        }
        {
            jEditorPane = new JEditorPane();
            jEditorPane.setEditable(false);
            jEditorPane.setToolTipText("<html>JEditorPane ligne1<br/>ToolTipText to I18n</html>");
            jEditorPane.setText("JEditorPane line1\r\nJEditorPane line2\r\nJEditorPane line3\r\n");
            GridBagConstraints gbc_jEditorPane = new GridBagConstraints();
            gbc_jEditorPane.gridwidth = 2;
            gbc_jEditorPane.fill = GridBagConstraints.BOTH;
            gbc_jEditorPane.gridx = 0;
            gbc_jEditorPane.gridy = 1;
            jPanel.add(jEditorPane, gbc_jEditorPane);
        }
        {
            this.buttonToolTipsButton = new JButton("button tool tips");
            this.buttonToolTipsButton.setToolTipText("my tool tips");
            GridBagConstraints gbc_buttonToolTipsButton = new GridBagConstraints();
            gbc_buttonToolTipsButton.insets = new Insets(0, 0, 0, 5);
            gbc_buttonToolTipsButton.gridx = 0;
            gbc_buttonToolTipsButton.gridy = 5;
            add(this.buttonToolTipsButton, gbc_buttonToolTipsButton);
        }
        {
            this.buttonIgnoreButton = new JButton("button ignore - but not tool tips");
            this.buttonIgnoreButton.setToolTipText("<html>my tool<br/>tips 2</html>");
            GridBagConstraints gbc_buttonIgnoreButton = new GridBagConstraints();
            gbc_buttonIgnoreButton.insets = new Insets(0, 0, 0, 5);
            gbc_buttonIgnoreButton.gridx = 3;
            gbc_buttonIgnoreButton.gridy = 5;
            add(this.buttonIgnoreButton, gbc_buttonIgnoreButton);
        }
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // I18nPrepHelperAutoUpdatable
    public String getMessagesBundleForI18nPrepHelper()
    {
        return this.getClass().getPackage().getName() + ".MessagesBundle"; // Default name
    }
}
