package com.googlecode.cchlib.i18n.sample.full;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.EnumSet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

class FakePanel
    extends JPanel
        implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FakePanel.class );

    static final String JTEXT_AREA_NO_I18N_INIT_TEXT = "jTextArea l1\r\njTextArea l2 (no I18n)";

    private final TitledBorder panelTitleBorder;
    private final JPanel jPanel; // TODO @I18nTitledBorder private JPanel jPanel;

    @I18nIgnore private final JLabel jLabelNoI18n; // No I18n
    @I18nToolTipText private final JProgressBar jProgressBarNoI18n; // No I18n, but tool tip text
    @I18nToolTipText private final JProgressBar jProgressBarToI18n; // TODO I18n JProgressBar and tool tip text

    private final JCheckBox jCheckBox; // I18n default process

    @I18n(id="JButtonID") private final JButton jButtonToI18n;
    @I18n @I18nToolTipText private final JLabel  jLabelToI18n; // annotation not needed
    //TODO @I18n(method="myI18n") private Object dummy = new Object(); // ex: declare field to apply a custom method

    @I18n(id="myJTextFieldDefineWithId")
    private final JTextField myJTextFieldDefineWithId; // use specific Id TODO

    @I18nToolTipText private final JTextArea jTextAreaNoI18n; // No I18n, but tool tip text
    @I18n private final JTextArea jTextAreaToI18n;  //I18n, but not tool tip text

    private final JTextField  myJTextField;
    private final JEditorPane jEditorPane;

    // Default process I18n on JButton and special I18n on tool tip text
    @I18nToolTipText private final JButton buttonToolTipsButton;
    // No I18n on JButton and special I18n on tool tip text
    @I18nIgnore @I18nToolTipText private final JButton buttonIgnoreButton;

    @I18nIgnore private final JButton refreshButton;
    @I18nString private final String refreshButtonText = "Refresh (default String)";

    private enum Test {A,B,C};
    @SuppressWarnings("unused") private Test test;
    @SuppressWarnings("unused") private EnumSet<Test> testSet;

    private final JMenuBar menuBar;
    private final JMenu menu1;
    private final JMenu menu2;
    private final JMenu menu1_1;
    private final JMenuItem rootMenuItem;
    private final JMenuItem menuItem1_1_1;
    private final JMenuItem menuItem1_1_2;

    public FakePanel()
    {
        this.panelTitleBorder = new TitledBorder(null, "TitleBorderNoI18nYet", TitledBorder.LEADING, TitledBorder.TOP, null, null);

        setBorder( this.panelTitleBorder );
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{80, 0, 0, 50, 0, 0};
        gridBagLayout.rowHeights = new int[]{25, 14, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.jLabelToI18n = new JLabel("JLabelNoI18nYet");
            this.jLabelToI18n.setToolTipText("<html>jLabel<br/>toolTipText to I18n</html>");
            final GridBagConstraints gbc_jLabel = new GridBagConstraints();
            gbc_jLabel.fill = GridBagConstraints.VERTICAL;
            gbc_jLabel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabel.anchor = GridBagConstraints.EAST;
            gbc_jLabel.gridx = 0;
            gbc_jLabel.gridy = 1;
            add(this.jLabelToI18n, gbc_jLabel);
        }
        {
            this.jProgressBarNoI18n = new JProgressBar();
            this.jProgressBarNoI18n.setStringPainted(true);
            this.jProgressBarNoI18n.setValue(50);
            this.jProgressBarNoI18n.setString( "JProgressBar (No_I18n)" );
            this.jProgressBarNoI18n.setToolTipText("<html>JProgressBar<br/>ToolTipText to i18n</html>");
            this.jProgressBarNoI18n.setOrientation(SwingConstants.VERTICAL);
            final GridBagConstraints gbc_jProgressBar = new GridBagConstraints();
            gbc_jProgressBar.gridheight = 5;
            gbc_jProgressBar.fill = GridBagConstraints.VERTICAL;
            gbc_jProgressBar.insets = new Insets(0, 0, 5, 5);
            gbc_jProgressBar.gridx = 1;
            gbc_jProgressBar.gridy = 1;
            add(this.jProgressBarNoI18n, gbc_jProgressBar);
        }
        {
            this.jProgressBarToI18n = new JProgressBar();
            this.jProgressBarToI18n.setValue(50);
            this.jProgressBarToI18n.setToolTipText("<html>JProgressBar to I18n<br/>ToolTipText to i18n</html>");
            this.jProgressBarToI18n.setStringPainted(true);
            this.jProgressBarToI18n.setString("JProgressBarNoI18nYet");
            this.jProgressBarToI18n.setOrientation(SwingConstants.VERTICAL);
            final GridBagConstraints gbc_jProgressBarToI18n = new GridBagConstraints();
            gbc_jProgressBarToI18n.fill = GridBagConstraints.VERTICAL;
            gbc_jProgressBarToI18n.gridheight = 5;
            gbc_jProgressBarToI18n.insets = new Insets(0, 0, 5, 5);
            gbc_jProgressBarToI18n.gridx = 2;
            gbc_jProgressBarToI18n.gridy = 1;
            add(this.jProgressBarToI18n, gbc_jProgressBarToI18n);
        }
        {
            this.jTextAreaNoI18n = new JTextArea();
            this.jTextAreaNoI18n.setEditable(false);
            this.jTextAreaNoI18n.setToolTipText("<html>jTextAreaNoI18n<br/>ToolTipText to i18n</html>");
            this.jTextAreaNoI18n.setText( JTEXT_AREA_NO_I18N_INIT_TEXT );
            final GridBagConstraints gbc_jTextAreaNoI18n = new GridBagConstraints();
            gbc_jTextAreaNoI18n.insets = new Insets(0, 0, 5, 0);
            gbc_jTextAreaNoI18n.gridheight = 4;
            gbc_jTextAreaNoI18n.fill = GridBagConstraints.BOTH;
            gbc_jTextAreaNoI18n.gridx = 3;
            gbc_jTextAreaNoI18n.gridy = 1;
            add(this.jTextAreaNoI18n, gbc_jTextAreaNoI18n);
        }
        {
            this.jTextAreaToI18n = new JTextArea();
            this.jTextAreaToI18n.setToolTipText("<html>jTextAreaToI18n to I18n<br>but not ToolTipText</html>");
            this.jTextAreaToI18n.setText( "jTextAreaToI18n" );
            this.jTextAreaToI18n.setEditable(false);
            final GridBagConstraints gbc_jTextArea = new GridBagConstraints();
            gbc_jTextArea.gridheight = 4;
            gbc_jTextArea.insets = new Insets(0, 0, 5, 0);
            gbc_jTextArea.fill = GridBagConstraints.BOTH;
            gbc_jTextArea.gridx = 4;
            gbc_jTextArea.gridy = 1;
            add(this.jTextAreaToI18n, gbc_jTextArea);
        }
        {
            this.jLabelNoI18n = new JLabel("JLabel (No_I18n)");
            final GridBagConstraints gbc_jLabelNoI18n = new GridBagConstraints();
            gbc_jLabelNoI18n.anchor = GridBagConstraints.EAST;
            gbc_jLabelNoI18n.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelNoI18n.gridx = 0;
            gbc_jLabelNoI18n.gridy = 2;
            add(this.jLabelNoI18n, gbc_jLabelNoI18n);
        }
        {
            this.jButtonToI18n = new JButton("JButtonNoI18nYet");
            this.jButtonToI18n.setToolTipText("<html>JButton<br/>toolTipText to I18n</html>");
            final GridBagConstraints gbc_jButton = new GridBagConstraints();
            gbc_jButton.anchor = GridBagConstraints.EAST;
            gbc_jButton.insets = new Insets(0, 0, 5, 5);
            gbc_jButton.gridx = 0;
            gbc_jButton.gridy = 3;
            add(this.jButtonToI18n, gbc_jButton);
        }
        {
            this.jCheckBox = new JCheckBox("JCheckBox");
            this.jCheckBox.setToolTipText("<html>JCheckBox<br/>toolTipText to I18n</html>");
            final GridBagConstraints gbc_jCheckBox = new GridBagConstraints();
            gbc_jCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox.gridx = 0;
            gbc_jCheckBox.gridy = 4;
            add(this.jCheckBox, gbc_jCheckBox);
        }
        {
            this.jPanel = new JPanel();
            this.jPanel.setBorder(new TitledBorder(null, "My TitledBorder 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            final GridBagConstraints gbc_jPanel = new GridBagConstraints();
            gbc_jPanel.gridwidth = 2;
            gbc_jPanel.insets = new Insets(0, 0, 5, 0);
            gbc_jPanel.fill = GridBagConstraints.BOTH;
            gbc_jPanel.gridx = 3;
            gbc_jPanel.gridy = 5;
            add(this.jPanel, gbc_jPanel);

            final GridBagLayout gbl_jPanel = new GridBagLayout();
            gbl_jPanel.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel.rowHeights = new int[]{0, 0, 0};
            gbl_jPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
            this.jPanel.setLayout(gbl_jPanel);
        }
        {
            this.myJTextField = new JTextField();
            this.myJTextField.setEditable(false);
            this.myJTextField.setToolTipText("<html>jTextField<br/>toolTipText to I18n</html>");
            this.myJTextField.setText("My JTextField");
            final GridBagConstraints gbc_myJTextField = new GridBagConstraints();
            gbc_myJTextField.fill = GridBagConstraints.BOTH;
            gbc_myJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_myJTextField.gridx = 0;
            gbc_myJTextField.gridy = 0;
            this.jPanel.add(this.myJTextField, gbc_myJTextField);
            this.myJTextField.setColumns(10);
        }
        {
            this.myJTextFieldDefineWithId = new JTextField();
            this.myJTextFieldDefineWithId.setText("My JTextField define with ID");
            final GridBagConstraints gbc_myJTextFieldDefineWithId = new GridBagConstraints();
            gbc_myJTextFieldDefineWithId.insets = new Insets(0, 0, 5, 0);
            gbc_myJTextFieldDefineWithId.fill = GridBagConstraints.BOTH;
            gbc_myJTextFieldDefineWithId.gridx = 1;
            gbc_myJTextFieldDefineWithId.gridy = 0;
            this.jPanel.add(this.myJTextFieldDefineWithId, gbc_myJTextFieldDefineWithId);
            this.myJTextFieldDefineWithId.setColumns(10);
        }
        {
            this.jEditorPane = new JEditorPane();
            this.jEditorPane.setEditable(false);
            this.jEditorPane.setToolTipText("<html>JEditorPane ligne1<br/>ToolTipText to I18n</html>");
            this.jEditorPane.setText("JEditorPane line1\r\nJEditorPane line2\r\nJEditorPane line3\r\n");
            final GridBagConstraints gbc_jEditorPane = new GridBagConstraints();
            gbc_jEditorPane.gridwidth = 2;
            gbc_jEditorPane.fill = GridBagConstraints.BOTH;
            gbc_jEditorPane.gridx = 0;
            gbc_jEditorPane.gridy = 1;
            this.jPanel.add(this.jEditorPane, gbc_jEditorPane);
        }
        {
            this.buttonToolTipsButton = new JButton("button tool tips");
            this.buttonToolTipsButton.setToolTipText("my tool tips");
            final GridBagConstraints gbc_buttonToolTipsButton = new GridBagConstraints();
            gbc_buttonToolTipsButton.insets = new Insets(0, 0, 5, 5);
            gbc_buttonToolTipsButton.gridx = 0;
            gbc_buttonToolTipsButton.gridy = 6;
            add(this.buttonToolTipsButton, gbc_buttonToolTipsButton);
        }
        {
            this.buttonIgnoreButton = new JButton("button ignore - but not tool tips");
            this.buttonIgnoreButton.setToolTipText("<html>my tool<br/>tips 2</html>");
            final GridBagConstraints gbc_buttonIgnoreButton = new GridBagConstraints();
            gbc_buttonIgnoreButton.insets = new Insets(0, 0, 5, 5);
            gbc_buttonIgnoreButton.gridx = 3;
            gbc_buttonIgnoreButton.gridy = 6;
            add(this.buttonIgnoreButton, gbc_buttonIgnoreButton);
        }
        {
            this.refreshButton = new JButton("Refresh (click here)");
            this.refreshButton.addActionListener((final ActionEvent e) -> {
                doRefresh();
            });
            {
                this.menuBar = new JMenuBar();
                final GridBagConstraints gbc_menuBar = new GridBagConstraints();
                gbc_menuBar.fill = GridBagConstraints.BOTH;
                gbc_menuBar.gridwidth = 5;
                gbc_menuBar.insets = new Insets(0, 0, 5, 0);
                gbc_menuBar.gridx = 0;
                gbc_menuBar.gridy = 0;
                add(this.menuBar, gbc_menuBar);
                {
                    this.menu1 = new JMenu("Menu 1");
                    this.menuBar.add(this.menu1);
                    {
                        this.menu1_1 = new JMenu("Menu 1 - 1st sub menu");
                        this.menu1.add(this.menu1_1);
                        {
                            this.menuItem1_1_1 = new JMenuItem("Menu 1 - 1st sub menu - menu item 1");
                            this.menu1_1.add(this.menuItem1_1_1);
                        }
                        {
                            this.menuItem1_1_2 = new JMenuItem("Menu 1 - 1st sub menu - menu item 2");
                            this.menu1_1.add(this.menuItem1_1_2);
                        }
                    }
                }
                {
                    this.menu2 = new JMenu("Menu 2");
                    this.menuBar.add(this.menu2);
                }
                {
                    this.rootMenuItem = new JMenuItem("New menu item");
                    this.menuBar.add(this.rootMenuItem);
                }
            }
            {
                final GridBagConstraints gbc_refreshButton = new GridBagConstraints();
                gbc_refreshButton.insets = new Insets(0, 0, 0, 5);
                gbc_refreshButton.gridx = 0;
                gbc_refreshButton.gridy = 7;
                add(this.refreshButton, gbc_refreshButton);
            }
        }
    }

    private void doRefresh()
    {
        this.refreshButton.setText( this.refreshButtonText );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        LOGGER.info( "performeI18n" );
        autoI18n.performeI18n( this, this.getClass() );
    }

    public String getJButtonText()
    {
        return this.jButtonToI18n.getText();
    }

    public String getJLabelToI18nText()
    {
        return this.jLabelToI18n.getText();
    }

    public String getJLabelToI18nToolTipText()
    {
        return this.jLabelToI18n.getToolTipText();
    }

    public String getPanelBorderTitle()
    {
        return this.panelTitleBorder.getTitle();
    }

    public String getProgressBarNoI18nText()
    {
        return this.jProgressBarNoI18n.getString();
    }

    public String getProgressBarNoI18nToolTipText()
    {
        return this.jProgressBarNoI18n.getToolTipText();
    }

    public String getProgressBarToI18nText()
    {
        return this.jProgressBarToI18n.getString();
    }

    public String getProgressBarToI18nToolTipText()
    {
        return this.jProgressBarToI18n.getToolTipText();
    }

    public String getJTextAreaNoI18nText()
    {
        return this.jTextAreaNoI18n.getText();
    }

    public String getJTextAreaNoI18nToolTipText()
    {
        return this.jTextAreaNoI18n.getToolTipText();
    }

    public String getJTextAreaToI18nText()
    {
        return this.jTextAreaToI18n.getText();
    }

    public String getJTextAreaToI18nToolTipText()
    {
        return this.jTextAreaToI18n.getToolTipText();
    }


}
