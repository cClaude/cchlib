// $codepro.audit.disable numericLiterals
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

/**
 *
 */
/*public*/
abstract class LoadDialogWB extends JDialog // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID  = 4L;

    private int numberOfFiles;

    private ButtonGroup buttonGroup_FileType;
    private FilesPanel selectJPanel;
    private JButton jButton_Cancel;
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

    @I18nString private String msgStringLeft  = "Left";
    @I18nString private String msgStringFmt = "File %d";
    @I18nString private String msgButton = "Select";

    public LoadDialogWB( final Frame parent, final int numberOfFiles )
    {
        super( parent );

        this.numberOfFiles = (numberOfFiles < 2) ? 2 : numberOfFiles;

        initComponents();
    }

    private void initComponents()
    {
        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 0, 100, 0};
        gridBagLayout.rowHeights = new int[]{180, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        {
            GridBagConstraints gbc_jTabbedPaneRoot = new GridBagConstraints();
            gbc_jTabbedPaneRoot.gridwidth = 3;
            gbc_jTabbedPaneRoot.fill = GridBagConstraints.BOTH;
            gbc_jTabbedPaneRoot.insets = new Insets(0, 0, 5, 0);
            gbc_jTabbedPaneRoot.gridx = 0;
            gbc_jTabbedPaneRoot.gridy = 0;

            jTabbedPaneRoot = new JTabbedPane();
            jTabbedPaneRoot.addTab("Files", getJPanel_TabSelect());
            jTabbedPaneRoot.addTab("Properties", getJPanel_TabProperties());
            jTabbedPaneRoot.addTab("Formatted Properties", getJPanel_TabFMTProperties());

            getContentPane().add(jTabbedPaneRoot, gbc_jTabbedPaneRoot);
        }
        {
            GridBagConstraints gbc_jButton_Ok = new GridBagConstraints();
            gbc_jButton_Ok.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButton_Ok.insets = new Insets(0, 0, 0, 5);
            gbc_jButton_Ok.gridx = 0;
            gbc_jButton_Ok.gridy = 1;
            jButton_Ok = new JButton("OK");
            jButton_Ok.setIcon(new ImageIcon( Resources.class.getResource("ok.png")));
            jButton_Ok.setActionCommand( LoadDialogAction.ACTIONCMD_OK_BUTTON.name() );
            jButton_Ok.addActionListener( getActionListener() );
            getContentPane().add(jButton_Ok, gbc_jButton_Ok);
        }
        {
            GridBagConstraints gbc_jButton_Cancel = new GridBagConstraints();
            gbc_jButton_Cancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButton_Cancel.gridx = 2;
            gbc_jButton_Cancel.gridy = 1;

            jButton_Cancel = new JButton("Cancel");
            jButton_Cancel.setIcon(new ImageIcon( Resources.class.getResource("cancel.png")));
            jButton_Cancel.setActionCommand( LoadDialogAction.ACTIONCMD_CANCEL_BUTTON.name() );
            jButton_Cancel.addActionListener( getActionListener() );
            getContentPane().add(jButton_Cancel, gbc_jButton_Cancel);
        }

        initButtonGroup_FileType();
        setSize(500, 290);
    }

    protected JCheckBox getCheckBox_ShowLineNumbers() {
        if (checkBox_ShowLineNumbers == null) {
            checkBox_ShowLineNumbers = new JCheckBox();
            checkBox_ShowLineNumbers.setText("Show line numbers (if possible)");
            }

        return checkBox_ShowLineNumbers;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_HTML_END_P() {
        if (checkBox_CUT_LINE_AFTER_HTML_END_P == null) {
            checkBox_CUT_LINE_AFTER_HTML_END_P = new JCheckBox();
            checkBox_CUT_LINE_AFTER_HTML_END_P.setText("CUT_LINE_AFTER_HTML_END_P");
            }

        return checkBox_CUT_LINE_AFTER_HTML_END_P;
    }

    protected JCheckBox getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P() {
        if (checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P == null) {
            checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new JCheckBox();
            checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.setText("CUT_LINE_BEFORE_HTML_BEGIN_P");
            }

        return checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P;
    }

    protected JCheckBox getCheckBox_CUT_LINE_BEFORE_HTML_BR() {
        if (checkBox_CUT_LINE_BEFORE_HTML_BR == null) {
            checkBox_CUT_LINE_BEFORE_HTML_BR = new JCheckBox();
            checkBox_CUT_LINE_BEFORE_HTML_BR.setText("CUT_LINE_BEFORE_HTML_BR");
            }

        return checkBox_CUT_LINE_BEFORE_HTML_BR;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_HTML_BR() {
        if (checkBox_CUT_LINE_AFTER_HTML_BR == null) {
            checkBox_CUT_LINE_AFTER_HTML_BR = new JCheckBox();
            checkBox_CUT_LINE_AFTER_HTML_BR.setText("CUT_LINE_AFTER_HTML_BR");
            }
        return checkBox_CUT_LINE_AFTER_HTML_BR;
    }

    protected JCheckBox getCheckBox_CUT_LINE_AFTER_NEW_LINE() {
        if (checkBox_CUT_LINE_AFTER_NEW_LINE == null) {
            checkBox_CUT_LINE_AFTER_NEW_LINE = new JCheckBox();
            checkBox_CUT_LINE_AFTER_NEW_LINE.setText("CUT_LINE_AFTER_NEW_LINE");
            }

        return checkBox_CUT_LINE_AFTER_NEW_LINE;
    }

    private JPanel getJPanel_TabFMTProperties() {
        if (jPanel_TabFMTProperties == null) {
            jPanel_TabFMTProperties = new JPanel();

            GridBagLayout gbl_jPanel_TabFMTProperties = new GridBagLayout();
            gbl_jPanel_TabFMTProperties.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabFMTProperties.rowHeights = new int[]{23, 23, 23, 23, 23, 0};
            gbl_jPanel_TabFMTProperties.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel_TabFMTProperties.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            jPanel_TabFMTProperties.setLayout(gbl_jPanel_TabFMTProperties);
            {
                GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_NEW_LINE = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_NEW_LINE.gridy = 0;
                jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_NEW_LINE(), gbc_checkBox_CUT_LINE_AFTER_NEW_LINE);
            }
            {
                GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_HTML_BR = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_HTML_BR.gridy = 1;
                jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_HTML_BR(), gbc_checkBox_CUT_LINE_AFTER_HTML_BR);
            }
            {
                GridBagConstraints gbc_checkBox_CUT_LINE_BEFORE_HTML_BR = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.gridx = 0;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BR.gridy = 2;
                jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_BEFORE_HTML_BR(), gbc_checkBox_CUT_LINE_BEFORE_HTML_BR);
            }
            {
                GridBagConstraints gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridx = 0;
                gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridy = 3;
                jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P(), gbc_checkBox_CUT_LINE_BEFORE_HTML_BEGIN_P);
            }
            {
                GridBagConstraints gbc_checkBox_CUT_LINE_AFTER_HTML_END_P = new GridBagConstraints();
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.insets = new Insets(0, 0, 0, 5);
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.anchor = GridBagConstraints.WEST;
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.gridx = 0;
                gbc_checkBox_CUT_LINE_AFTER_HTML_END_P.gridy = 4;
                jPanel_TabFMTProperties.add(getCheckBox_CUT_LINE_AFTER_HTML_END_P(), gbc_checkBox_CUT_LINE_AFTER_HTML_END_P);
            }
            }
        return jPanel_TabFMTProperties;
    }

    protected JCheckBox getCheckBox_RightUseLeftHasDefaults() {
        if (checkBox_RightUseLeftHasDefaults == null) {
            checkBox_RightUseLeftHasDefaults = new JCheckBox();
            checkBox_RightUseLeftHasDefaults.setText("Right file use Left file has defaults");
            }

        return checkBox_RightUseLeftHasDefaults;
    }

    protected JCheckBox getCheckBox_LeftReadOnly()
    {
        if (checkBox_LeftReadOnly == null) {
            checkBox_LeftReadOnly = new JCheckBox();
            checkBox_LeftReadOnly.setText("Left file readonly");
            }

        return checkBox_LeftReadOnly;
    }

    private void initButtonGroup_FileType()
    {
        buttonGroup_FileType = new ButtonGroup();
        buttonGroup_FileType.add(getCheckBox_Properties());
        buttonGroup_FileType.add(getCheckBox_FormattedProperties());
        buttonGroup_FileType.add(getCheckBox_ini());
    }

    protected JCheckBox getCheckBox_Properties()
    {
        if (checkBox_Properties == null) {
            checkBox_Properties = new JCheckBox();
            checkBox_Properties.setText("Properties");
            }

        return checkBox_Properties;
    }

    protected JCheckBox getCheckBox_ini()
    {
        if (checkBox_ini == null) {
            checkBox_ini = new JCheckBox();
            checkBox_ini.setText("*.ini files");
            checkBox_ini.setEnabled(false);
            }

        return checkBox_ini;
    }

    protected JCheckBox getCheckBox_FormattedProperties()
    {
        if (checkBox_FormattedProperties == null) {
            checkBox_FormattedProperties = new JCheckBox();
            checkBox_FormattedProperties.setText("Formatted Properties");
            }
        return checkBox_FormattedProperties;
    }

    private JPanel getJPanel_TabProperties()
    {
        if (jPanel_TabProperties == null) {
            jPanel_TabProperties = new JPanel();
            GridBagLayout gbl_jPanel_TabProperties = new GridBagLayout();
            gbl_jPanel_TabProperties.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabProperties.rowHeights = new int[]{23, 0};
            gbl_jPanel_TabProperties.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel_TabProperties.rowWeights = new double[]{0.0, Double.MIN_VALUE};
            jPanel_TabProperties.setLayout(gbl_jPanel_TabProperties);

            GridBagConstraints gbc_checkBox_RightUseLeftHasDefaults = new GridBagConstraints();
            gbc_checkBox_RightUseLeftHasDefaults.insets = new Insets(0, 0, 0, 5);
            gbc_checkBox_RightUseLeftHasDefaults.anchor = GridBagConstraints.NORTHWEST;
            gbc_checkBox_RightUseLeftHasDefaults.gridx = 0;
            gbc_checkBox_RightUseLeftHasDefaults.gridy = 0;
            jPanel_TabProperties.add(getCheckBox_RightUseLeftHasDefaults(), gbc_checkBox_RightUseLeftHasDefaults);
            }

        return jPanel_TabProperties;
    }

    private JPanel getJPanel_TabSelect()
    {
        if (jPanel_TabSelect == null) {
            {
                jPanel_TabSelect = new JPanel();
                GridBagLayout gbl_jPanel_TabSelect = new GridBagLayout();
                gbl_jPanel_TabSelect.columnWidths = new int[]{0, 0, 0};
                gbl_jPanel_TabSelect.rowHeights = new int[]{50, 0, 0, 0, 0};
                gbl_jPanel_TabSelect.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
                gbl_jPanel_TabSelect.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                jPanel_TabSelect.setLayout(gbl_jPanel_TabSelect);
            }
            {
                GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                gbc_scrollPane.fill = GridBagConstraints.BOTH;
                gbc_scrollPane.gridwidth = 2;
                gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
                gbc_scrollPane.gridx = 0;
                gbc_scrollPane.gridy = 0;
                jPanel_TabSelect.add(getScrollPane(), gbc_scrollPane);
            }
            {
                GridBagConstraints gbc_checkBox_Properties = new GridBagConstraints();
                gbc_checkBox_Properties.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_Properties.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_Properties.gridx = 0;
                gbc_checkBox_Properties.gridy = 1;
                jPanel_TabSelect.add(getCheckBox_Properties(), gbc_checkBox_Properties);
            }
            {
                GridBagConstraints gbc_checkBox_LeftReadOnly = new GridBagConstraints();
                gbc_checkBox_LeftReadOnly.anchor = GridBagConstraints.WEST;
                gbc_checkBox_LeftReadOnly.insets = new Insets(0, 0, 5, 0);
                gbc_checkBox_LeftReadOnly.gridx = 1;
                gbc_checkBox_LeftReadOnly.gridy = 1;
                jPanel_TabSelect.add(getCheckBox_LeftReadOnly(), gbc_checkBox_LeftReadOnly);
            }
            {
                GridBagConstraints gbc_checkBox_FormattedProperties = new GridBagConstraints();
                gbc_checkBox_FormattedProperties.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_FormattedProperties.insets = new Insets(0, 0, 5, 5);
                gbc_checkBox_FormattedProperties.gridx = 0;
                gbc_checkBox_FormattedProperties.gridy = 2;
                jPanel_TabSelect.add(getCheckBox_FormattedProperties(), gbc_checkBox_FormattedProperties);
            }
            {
                GridBagConstraints gbc_checkBox_ShowLineNumbers = new GridBagConstraints();
                gbc_checkBox_ShowLineNumbers.anchor = GridBagConstraints.WEST;
                gbc_checkBox_ShowLineNumbers.insets = new Insets(0, 0, 5, 0);
                gbc_checkBox_ShowLineNumbers.gridx = 1;
                gbc_checkBox_ShowLineNumbers.gridy = 2;
                jPanel_TabSelect.add(getCheckBox_ShowLineNumbers(), gbc_checkBox_ShowLineNumbers);
            }
            {
                GridBagConstraints gbc_checkBox_ini = new GridBagConstraints();
                gbc_checkBox_ini.fill = GridBagConstraints.HORIZONTAL;
                gbc_checkBox_ini.insets = new Insets(0, 0, 0, 5);
                gbc_checkBox_ini.gridx = 0;
                gbc_checkBox_ini.gridy = 3;
                jPanel_TabSelect.add(getCheckBox_ini(), gbc_checkBox_ini);
            }
        }

        return jPanel_TabSelect;
    }

    protected JTabbedPane getJTabbedPaneRoot()
    {
        return jTabbedPaneRoot;
    }

    protected abstract ActionListener getActionListener();

    protected JTextField getJTextField( final int index )
    {
        return getSelectJPanel().getPanelFile( index ).getJTextField();
    }

    protected FilesPanel getSelectJPanel()
    {
        if( selectJPanel == null ) {
            selectJPanel = new FilesPanel( numberOfFiles, msgStringLeft, msgStringFmt, msgButton, getActionListener() );
            }

        return selectJPanel;
    }

    private JScrollPane getScrollPane()
    {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setViewportView(getSelectJPanel());
            }

        return scrollPane;
    }
}
