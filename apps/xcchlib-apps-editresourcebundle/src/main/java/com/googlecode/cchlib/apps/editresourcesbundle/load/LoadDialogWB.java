package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import com.googlecode.cchlib.apps.editresourcesbundle.Resources;
import com.googlecode.cchlib.i18n.annotation.I18nString;

//NOT public
@SuppressWarnings({
    "squid:S00100", "squid:S00116", "squid:S00117", // naming convention
    "squid:MaximumInheritanceDepth" // Swing
    })
abstract class LoadDialogWB extends JDialog
{
    private static final long serialVersionUID = 4L;

    private final int numberOfFiles;

    private FilesPanel selectJPanel;
    @SuppressWarnings("squid:S1450") // For I18n
    private JButton jButton_Cancel;
    @SuppressWarnings("squid:S1450") // For I18n
    private JButton jButton_Ok;
    private JCheckBox checkBox_CUT_LINE_AFTER_HTML_BR;
    private JCheckBox checkBox_CUT_LINE_AFTER_HTML_END_P;
    private JCheckBox checkBox_CUT_LINE_AFTER_NEW_LINE;
    private JCheckBox checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P;
    private JCheckBox checkBox_CUT_LINE_BEFORE_HTML_BR;
    private JCheckBox checkBox_FormattedProperties;
    private JCheckBox checkBox_LeftReadOnly;
    private JCheckBox checkBox_Properties;
    private JCheckBox checkBox_RightUseLeftHasDefaults;
    private JCheckBox checkBox_ShowLineNumbers;
    private JCheckBox checkBox_ini;
    private JPanel jPanel_TabFMTProperties;
    private JPanel jPanel_TabProperties;
    private JPanel jPanel_TabSelect;
    private JScrollPane scrollPane;
    private JTabbedPane jTabbedPaneRoot;

    @I18nString private String msgStringLeft;
    @I18nString private String msgStringFmt;
    @I18nString private String msgButton;
    @I18nString protected String msgTitle;
    @I18nString protected String msgErrorWhileLoading;

    public LoadDialogWB( final Frame parent, final int numberOfFiles )
    {
        super( parent );
        beSureNotFinal();

        this.numberOfFiles = (numberOfFiles < 2) ? 2 : numberOfFiles;

        initComponents();
    }

    private void beSureNotFinal()
    {
        this.msgStringLeft        = "Left";
        this.msgStringFmt         = "File %d";
        this.msgButton            = "Select";
        this.msgTitle             = "Load...";
        this.msgErrorWhileLoading = "Error while loading files";
    }

    @SuppressWarnings("squid:S1199") // Generated code
    private void initComponents()
    {
        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 0, 100, 0};
        gridBagLayout.rowHeights = new int[]{180, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        {
            final GridBagConstraints gbc_jTabbedPaneRoot = new GridBagConstraints();
            gbc_jTabbedPaneRoot.gridwidth = 3;
            gbc_jTabbedPaneRoot.fill = GridBagConstraints.BOTH;
            gbc_jTabbedPaneRoot.insets = new Insets(0, 0, 5, 0);
            gbc_jTabbedPaneRoot.gridx = 0;
            gbc_jTabbedPaneRoot.gridy = 0;

            this.jTabbedPaneRoot = new JTabbedPane();
            this.jTabbedPaneRoot.addTab("Files", getJPanel_TabSelect());
            this.jTabbedPaneRoot.addTab("Properties", getJPanel_TabProperties());
            this.jTabbedPaneRoot.addTab("Formatted Properties", getJPanel_TabFMTProperties());

            getContentPane().add(this.jTabbedPaneRoot, gbc_jTabbedPaneRoot);
        }
        {
            final GridBagConstraints gbc_jButton_Ok = new GridBagConstraints();
            gbc_jButton_Ok.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButton_Ok.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_Ok.gridx = 0;
            gbc_jButton_Ok.gridy = 1;
            this.jButton_Ok = new JButton("OK");
            this.jButton_Ok.setIcon(new ImageIcon( Resources.class.getResource("ok.png")));
            this.jButton_Ok.setActionCommand( LoadDialogAction.ACTIONCMD_OK_BUTTON.name() );
            this.jButton_Ok.addActionListener( getActionListener() );
            getContentPane().add(this.jButton_Ok, gbc_jButton_Ok);
        }
        {
            final GridBagConstraints gbc_jButton_Cancel = new GridBagConstraints();
            gbc_jButton_Cancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButton_Cancel.gridx = 2;
            gbc_jButton_Cancel.gridy = 1;

            this.jButton_Cancel = new JButton("Cancel");
            this.jButton_Cancel.setIcon(new ImageIcon( Resources.class.getResource("cancel.png")));
            this.jButton_Cancel.setActionCommand( LoadDialogAction.ACTIONCMD_CANCEL_BUTTON.name() );
            this.jButton_Cancel.addActionListener( getActionListener() );
            getContentPane().add(this.jButton_Cancel, gbc_jButton_Cancel);
        }

        initButtonGroup_FileType();
        setSize(500, 290);
    }

    protected JCheckBox getCheckBox_ShowLineNumbers() {
        if (this.checkBox_ShowLineNumbers == null) {
            this.checkBox_ShowLineNumbers = new JCheckBox();
            this.checkBox_ShowLineNumbers.setText("Show line numbers (if possible)");
            }

        return this.checkBox_ShowLineNumbers;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_HTML_END_P() {
        if (this.checkBox_CUT_LINE_AFTER_HTML_END_P == null) {
            this.checkBox_CUT_LINE_AFTER_HTML_END_P = new JCheckBox();
            this.checkBox_CUT_LINE_AFTER_HTML_END_P.setText("CUT_LINE_AFTER_HTML_END_P");
            }

        return this.checkBox_CUT_LINE_AFTER_HTML_END_P;
    }

    protected JCheckBox getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P() {
        if (this.checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P == null) {
            this.checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new JCheckBox();
            this.checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.setText("CUT_LINE_BEFORE_HTML_BEGIN_P");
            }

        return this.checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P;
    }

    protected JCheckBox getCheckBox_CUT_LINE_BEFORE_HTML_BR() {
        if (this.checkBox_CUT_LINE_BEFORE_HTML_BR == null) {
            this.checkBox_CUT_LINE_BEFORE_HTML_BR = new JCheckBox();
            this.checkBox_CUT_LINE_BEFORE_HTML_BR.setText("CUT_LINE_BEFORE_HTML_BR");
            }

        return this.checkBox_CUT_LINE_BEFORE_HTML_BR;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_HTML_BR() {
        if (this.checkBox_CUT_LINE_AFTER_HTML_BR == null) {
            this.checkBox_CUT_LINE_AFTER_HTML_BR = new JCheckBox();
            this.checkBox_CUT_LINE_AFTER_HTML_BR.setText("CUT_LINE_AFTER_HTML_BR");
            }
        return this.checkBox_CUT_LINE_AFTER_HTML_BR;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_NEW_LINE() {
        if (this.checkBox_CUT_LINE_AFTER_NEW_LINE == null) {
            this.checkBox_CUT_LINE_AFTER_NEW_LINE = new JCheckBox();
            this.checkBox_CUT_LINE_AFTER_NEW_LINE.setText("CUT_LINE_AFTER_NEW_LINE");
            }

        return this.checkBox_CUT_LINE_AFTER_NEW_LINE;
    }

    @SuppressWarnings("squid:S1199") // Generated code
    private JPanel getJPanel_TabFMTProperties()
    {
        if (this.jPanel_TabFMTProperties == null) {
            this.jPanel_TabFMTProperties = new JPanel();

            final GridBagLayout gbl_jPanel_TabFMTProperties = new GridBagLayout();
            gbl_jPanel_TabFMTProperties.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabFMTProperties.rowHeights = new int[]{23, 23, 23, 23, 23, 0};
            gbl_jPanel_TabFMTProperties.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel_TabFMTProperties.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            this.jPanel_TabFMTProperties.setLayout(gbl_jPanel_TabFMTProperties);
            {
                final GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_NEW_LINE = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.gridy = 0;
                this.jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_NEW_LINE(), gbc_checkBox_CUT_LINE_AFTER_NEW_LINE);
            }
            {
                final GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_HTML_BR = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.gridy = 1;
                this.jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_HTML_BR(), gbc_checkBox_CUT_LINE_AFTER_HTML_BR);
            }
            {
                final GridBagConstraints gbc_checkBox_CUT_LINE_BEFORE_HTML_BR = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.gridx = 0;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.gridy = 2;
                this.jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_BEFORE_HTML_BR(), gbc_checkBox_CUT_LINE_BEFORE_HTML_BR);
            }
            {
                final GridBagConstraints gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridx = 0;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridy = 3;
                this.jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P(), gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P);
            }
            {
                final GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_HTML_END_P = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.insets = new Insets(0, 0, 0, 5);
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.gridy = 4;
                this.jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_HTML_END_P(), gbc_checkBox_CUT_LINE_AFTER_HTML_END_P);
            }
            }
        return this.jPanel_TabFMTProperties;
    }

    protected JCheckBox getCheckBox_RightUseLeftHasDefaults() {
        if (this.checkBox_RightUseLeftHasDefaults == null) {
            this.checkBox_RightUseLeftHasDefaults = new JCheckBox();
            this.checkBox_RightUseLeftHasDefaults.setText("Right file use Left file has defaults");
            }

        return this.checkBox_RightUseLeftHasDefaults;
    }

    protected JCheckBox getCheckBox_LeftReadOnly()
    {
        if (this.checkBox_LeftReadOnly == null) {
            this.checkBox_LeftReadOnly = new JCheckBox();
            this.checkBox_LeftReadOnly.setText("Left file readonly");
            }

        return this.checkBox_LeftReadOnly;
    }

    private void initButtonGroup_FileType()
    {
        final ButtonGroup buttonGroup_FileType = new ButtonGroup();

        buttonGroup_FileType.add(getCheckBox_Properties());
        buttonGroup_FileType.add(getCheckBox_FormattedProperties());
        buttonGroup_FileType.add(getCheckBox_ini());
    }

    protected JCheckBox getCheckBox_Properties()
    {
        if (this.checkBox_Properties == null) {
            this.checkBox_Properties = new JCheckBox();
            this.checkBox_Properties.setText("Properties");
            }

        return this.checkBox_Properties;
    }

    protected JCheckBox getCheckBox_ini()
    {
        if (this.checkBox_ini == null) {
            this.checkBox_ini = new JCheckBox();
            this.checkBox_ini.setText("*.ini files");
            this.checkBox_ini.setEnabled(false);
            }

        return this.checkBox_ini;
    }

    protected JCheckBox getCheckBox_FormattedProperties()
    {
        if (this.checkBox_FormattedProperties == null) {
            this.checkBox_FormattedProperties = new JCheckBox();
            this.checkBox_FormattedProperties.setText("Formatted Properties");
            }
        return this.checkBox_FormattedProperties;
    }

    private JPanel getJPanel_TabProperties()
    {
        if (this.jPanel_TabProperties == null) {
            this.jPanel_TabProperties = new JPanel();
            final GridBagLayout gbl_jPanel_TabProperties = new GridBagLayout();
            gbl_jPanel_TabProperties.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabProperties.rowHeights = new int[]{23, 0};
            gbl_jPanel_TabProperties.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel_TabProperties.rowWeights = new double[]{0.0, Double.MIN_VALUE};
            this.jPanel_TabProperties.setLayout(gbl_jPanel_TabProperties);

            final GridBagConstraints gbc_checkBox_RightUseLeftHasDefaults = new GridBagConstraints();
            gbc_checkBox_RightUseLeftHasDefaults.insets = new Insets(0, 0, 0, 5);
            gbc_checkBox_RightUseLeftHasDefaults.anchor = GridBagConstraints.NORTHWEST;
            gbc_checkBox_RightUseLeftHasDefaults.gridx = 0;
            gbc_checkBox_RightUseLeftHasDefaults.gridy = 0;
            this.jPanel_TabProperties.add(getCheckBox_RightUseLeftHasDefaults(), gbc_checkBox_RightUseLeftHasDefaults);
            }

        return this.jPanel_TabProperties;
    }

    @SuppressWarnings("squid:S1199") // Generated code
    private JPanel getJPanel_TabSelect()
    {
        if (this.jPanel_TabSelect == null) {
            {
                this.jPanel_TabSelect = new JPanel();
                final GridBagLayout gbl_jPanel_TabSelect = new GridBagLayout();
                gbl_jPanel_TabSelect.columnWidths = new int[]{0, 0, 0};
                gbl_jPanel_TabSelect.rowHeights = new int[]{50, 0, 0, 0, 0};
                gbl_jPanel_TabSelect.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
                gbl_jPanel_TabSelect.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                this.jPanel_TabSelect.setLayout(gbl_jPanel_TabSelect);
            }
            {
                final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                gbc_scrollPane.fill = GridBagConstraints.BOTH;
                gbc_scrollPane.gridwidth = 2;
                gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
                gbc_scrollPane.gridx = 0;
                gbc_scrollPane.gridy = 0;
                this.jPanel_TabSelect.add(getScrollPane(), gbc_scrollPane);
            }
            {
                final GridBagConstraints gbc_checkBox_Properties = new GridBagConstraints();
                gbc_checkBox_Properties.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_Properties.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_Properties.gridx = 0;
                gbc_checkBox_Properties.gridy = 1;
                this.jPanel_TabSelect.add(getCheckBox_Properties(), gbc_checkBox_Properties);
            }
            {
                final GridBagConstraints gbc_checkBox_LeftReadOnly = new GridBagConstraints();
                gbc_checkBox_LeftReadOnly.anchor = GridBagConstraints.WEST;
                gbc_checkBox_LeftReadOnly.insets = new Insets(0, 0, 5, 0);
                gbc_checkBox_LeftReadOnly.gridx = 1;
                gbc_checkBox_LeftReadOnly.gridy = 1;
                this.jPanel_TabSelect.add(getCheckBox_LeftReadOnly(), gbc_checkBox_LeftReadOnly);
            }
            {
                final GridBagConstraints gbc_checkBox_FormattedProperties = new GridBagConstraints();
                gbc_checkBox_FormattedProperties.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_FormattedProperties.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_FormattedProperties.gridx = 0;
                gbc_checkBox_FormattedProperties.gridy = 2;
                this.jPanel_TabSelect.add(getCheckBox_FormattedProperties(), gbc_checkBox_FormattedProperties);
            }
            {
                final GridBagConstraints gbc_checkBox_ShowLineNumbers = new GridBagConstraints();
                gbc_checkBox_ShowLineNumbers.anchor = GridBagConstraints.WEST;
                gbc_checkBox_ShowLineNumbers.insets = new Insets(0, 0, 5, 0);
                gbc_checkBox_ShowLineNumbers.gridx = 1;
                gbc_checkBox_ShowLineNumbers.gridy = 2;
                this.jPanel_TabSelect.add(getCheckBox_ShowLineNumbers(), gbc_checkBox_ShowLineNumbers);
            }
            {
                final GridBagConstraints gbc_checkBox_ini = new GridBagConstraints();
                gbc_checkBox_ini.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_ini.insets = new Insets(0, 0, 0, 5);
                gbc_checkBox_ini.gridx = 0;
                gbc_checkBox_ini.gridy = 3;
                this.jPanel_TabSelect.add(getCheckBox_ini(), gbc_checkBox_ini);
            }
        }

        return this.jPanel_TabSelect;
    }

    protected JTabbedPane getJTabbedPaneRoot()
    {
        return this.jTabbedPaneRoot;
    }

    protected abstract ActionListener getActionListener();

    protected JTextField getJTextField( final int index )
    {
        return getSelectJPanel().getPanelFile( index ).getJTextField();
    }

    protected FilesPanel getSelectJPanel()
    {
        if( this.selectJPanel == null ) {
            this.selectJPanel = new FilesPanel( this.numberOfFiles, this.msgStringLeft, this.msgStringFmt, this.msgButton, getActionListener() );
            }

        return this.selectJPanel;
    }

    private JScrollPane getScrollPane()
    {
        if (this.scrollPane == null) {
            this.scrollPane = new JScrollPane();
            this.scrollPane.setViewportView(getSelectJPanel());
            }

        return this.scrollPane;
    }
}
