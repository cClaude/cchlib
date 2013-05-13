package com.googlecode.cchlib.i18n;

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
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;

/**
 *
 */
public class FakePanel extends JPanel implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;

    @I18nIgnore private JLabel jLabelNoI18n;
    @I18n(id="JButtonID") private JButton jButton;
    @I18n(keyName="JLabelID")private JLabel jLabel;
    @I18nForce private JTextArea jTextArea;
    private JCheckBox jCheckBox;
    @I18nForce private JTextField jTextField;
    @I18nForce private JEditorPane jEditorPane;
    private JProgressBar jProgressBar;
    @I18n (method="I18nJPanelBorder") private JPanel jPanel;
    @I18nToolTipText private JButton buttonToolTipsButton;
    @I18nIgnore @I18nToolTipText private JButton buttonIgnoreButton;

    public void setI18nJPanelBorder( String str )
    {
        TitledBorder tb = TitledBorder.class.cast( getBorder() );

        tb.setTitle( str );
    }

    public String getI18nJPanelBorder()
    {
        TitledBorder tb = TitledBorder.class.cast( getBorder() );

        return tb.getTitle();
    }

    /**
     *
     */
    public FakePanel()
    {
        setBorder(new TitledBorder(null, "TitleBorder", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{80, 50, 0, 0};
        gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
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
            jTextArea = new JTextArea();
            jTextArea.setEditable(false);
            jTextArea.setToolTipText("<html>jTextArea<br/>ToolTipText to i18n</html>");
            jTextArea.setText("jTextArea l1\r\njTextArea l2");
            GridBagConstraints gbc_jTextArea = new GridBagConstraints();
            gbc_jTextArea.gridwidth = 2;
            gbc_jTextArea.insets = new Insets(0, 0, 5, 0);
            gbc_jTextArea.gridheight = 4;
            gbc_jTextArea.fill = GridBagConstraints.BOTH;
            gbc_jTextArea.gridx = 1;
            gbc_jTextArea.gridy = 0;
            add(jTextArea, gbc_jTextArea);
        }
        {
            jLabelNoI18n = new JLabel("jLabel_No_I18n");
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
            jProgressBar = new JProgressBar();
            jProgressBar.setIndeterminate(true);
            jProgressBar.setValue(25);
            jProgressBar.setToolTipText("<html>JProgressBar<br/>ToolTipText to i18n</html>");
            jProgressBar.setOrientation(SwingConstants.VERTICAL);
            GridBagConstraints gbc_jProgressBar = new GridBagConstraints();
            gbc_jProgressBar.fill = GridBagConstraints.VERTICAL;
            gbc_jProgressBar.insets = new Insets(0, 0, 5, 5);
            gbc_jProgressBar.gridx = 0;
            gbc_jProgressBar.gridy = 4;
            add(jProgressBar, gbc_jProgressBar);
        }
        {
            jPanel = new JPanel();
            jPanel.setBorder(new TitledBorder(null, "TitledBorder2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            GridBagConstraints gbc_jPanel = new GridBagConstraints();
            gbc_jPanel.gridwidth = 2;
            gbc_jPanel.insets = new Insets(0, 0, 5, 0);
            gbc_jPanel.fill = GridBagConstraints.BOTH;
            gbc_jPanel.gridx = 1;
            gbc_jPanel.gridy = 4;
            add(jPanel, gbc_jPanel);
            GridBagLayout gbl_jPanel = new GridBagLayout();
            gbl_jPanel.columnWidths = new int[]{100, 50, 0};
            gbl_jPanel.rowHeights = new int[]{20, 0, 0};
            gbl_jPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            jPanel.setLayout(gbl_jPanel);
        }
        {
            jTextField = new JTextField();
            jTextField.setEditable(false);
            jTextField.setToolTipText("<html>jTextField<br/>toolTipText to I18n</html>");
            jTextField.setText("jTextField");
            GridBagConstraints gbc_jTextField = new GridBagConstraints();
            gbc_jTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextField.anchor = GridBagConstraints.NORTH;
            gbc_jTextField.insets = new Insets(0, 0, 5, 5);
            gbc_jTextField.gridx = 0;
            gbc_jTextField.gridy = 0;
            jPanel.add(jTextField, gbc_jTextField);
            jTextField.setColumns(10);
        }
        {
            jEditorPane = new JEditorPane();
            jEditorPane.setEditable(false);
            jEditorPane.setToolTipText("<html>JEditorPane ligne1<br/>ToolTipText to I18n</html>");
            jEditorPane.setText("JEditorPane line1\r\nJEditorPane line2\r\nJEditorPane line3\r\n");
            GridBagConstraints gbc_jEditorPane = new GridBagConstraints();
            gbc_jEditorPane.fill = GridBagConstraints.HORIZONTAL;
            gbc_jEditorPane.gridheight = 2;
            gbc_jEditorPane.insets = new Insets(0, 0, 5, 0);
            gbc_jEditorPane.anchor = GridBagConstraints.NORTH;
            gbc_jEditorPane.gridx = 1;
            gbc_jEditorPane.gridy = 0;
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
            gbc_buttonIgnoreButton.gridx = 1;
            gbc_buttonIgnoreButton.gridy = 5;
            add(this.buttonIgnoreButton, gbc_buttonIgnoreButton);
        }
    }

    @Override
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
