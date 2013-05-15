package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import com.googlecode.cchlib.apps.editresourcesbundle.Resources;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

/**
 *
 */
public abstract class LoadDialogWB extends JDialog
{
    private static final long serialVersionUID  = 4L;
    protected static final String ACTIONCMD_SELECT_PREFIX = "ACTIONCMD_SELECT_";
    protected static final String ACTIONCMD_OK_BUTTON = "ACTIONCMD_OK_BUTTON";
    protected static final String ACTIONCMD_CANCEL_BUTTON = "ACTIONCMD_CANCEL_BUTTON";

    private int numberOfFiles;

    private ButtonGroup buttonGroup_FileType;
    private JButton jButton_Cancel;
    private JButton jButton_Ok;
    private JPanel jPanel_TabSelect;
    private JPanel jPanel_TabFMTProperties;
    private JPanel jPanel_TabProperties;
    private JTabbedPane jTabbedPaneRoot;
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
    protected JCheckBox jCheckBox_ShowLineNumbers;
    private FilesPanel selectJPanel;
    private JScrollPane scrollPane;

    @I18nString private String msgStringLeft  = "Left";
    @I18nString private String msgStringFmt = "File %d";
    @I18nString private String msgButton = "Select";

    public LoadDialogWB( Frame parent, final int numberOfFiles )
    {
        super( parent );

        this.numberOfFiles = numberOfFiles < 2 ? 2 : numberOfFiles;

        initComponents();
    }

    private void initComponents() {
        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 0, 100, 0};
        gridBagLayout.rowHeights = new int[]{180, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
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
        GridBagConstraints gbc_jButton_Ok = new GridBagConstraints();
        gbc_jButton_Ok.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButton_Ok.insets = new Insets(0, 0, 0, 5);
        gbc_jButton_Ok.gridx = 0;
        gbc_jButton_Ok.gridy = 1;
        jButton_Ok = new JButton("OK");
        jButton_Ok.setIcon(new ImageIcon( Resources.class.getResource("ok.png")));
        jButton_Ok.setActionCommand( ACTIONCMD_OK_BUTTON );
        jButton_Ok.addActionListener( getActionListener() );
        getContentPane().add(jButton_Ok, gbc_jButton_Ok);
        GridBagConstraints gbc_jButton_Cancel = new GridBagConstraints();
        gbc_jButton_Cancel.fill = GridBagConstraints.HORIZONTAL;
        gbc_jButton_Cancel.gridx = 2;
        gbc_jButton_Cancel.gridy = 1;

        jButton_Cancel = new JButton("Cancel");
        jButton_Cancel.setIcon(new ImageIcon( Resources.class.getResource("cancel.png")));
        jButton_Cancel.setActionCommand( ACTIONCMD_CANCEL_BUTTON );
        jButton_Cancel.addActionListener( getActionListener() );
        getContentPane().add(jButton_Cancel, gbc_jButton_Cancel);
        initButtonGroup_FileType();
        setSize(500, 290);
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
            GridBagLayout gbl_jPanel_TabFMTProperties = new GridBagLayout();
            gbl_jPanel_TabFMTProperties.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabFMTProperties.rowHeights = new int[]{23, 23, 23, 23, 23, 0};
            gbl_jPanel_TabFMTProperties.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gbl_jPanel_TabFMTProperties.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            jPanel_TabFMTProperties.setLayout(gbl_jPanel_TabFMTProperties);
            GridBagConstraints gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE = new GridBagConstraints();
            gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE.anchor = GridBagConstraints.WEST;
            gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE.gridx = 0;
            gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE.gridy = 0;
            jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_NEW_LINE(), gbc_jCheckBox_CUT_LINE_AFTER_NEW_LINE);
            GridBagConstraints gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR = new GridBagConstraints();
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR.anchor = GridBagConstraints.WEST;
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR.gridx = 0;
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR.gridy = 1;
            jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_HTML_BR(), gbc_jCheckBox_CUT_LINE_AFTER_HTML_BR);
            GridBagConstraints gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR = new GridBagConstraints();
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR.anchor = GridBagConstraints.WEST;
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR.gridx = 0;
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR.gridy = 2;
            jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_BEFORE_HTML_BR(), gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BR);
            GridBagConstraints gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P = new GridBagConstraints();
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P.anchor = GridBagConstraints.WEST;
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridx = 0;
            gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P.gridy = 3;
            jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P(), gbc_jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P);
            GridBagConstraints gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P = new GridBagConstraints();
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P.insets = new Insets(0, 0, 0, 5);
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P.anchor = GridBagConstraints.WEST;
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P.gridx = 0;
            gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P.gridy = 4;
            jPanel_TabFMTProperties.add(getJCheckBox_CUT_LINE_AFTER_HTML_END_P(), gbc_jCheckBox_CUT_LINE_AFTER_HTML_END_P);
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

    private JCheckBox getJCheckBox_LeftReadOnly()
    {
        if (jCheckBox_LeftReadOnly == null) {
            jCheckBox_LeftReadOnly = new JCheckBox();
            jCheckBox_LeftReadOnly.setText("Left file readonly");
            }
        return jCheckBox_LeftReadOnly;
    }

    private void initButtonGroup_FileType()
    {
        buttonGroup_FileType = new ButtonGroup();
        buttonGroup_FileType.add(getJCheckBox_Properties());
        buttonGroup_FileType.add(getJCheckBox_FormattedProperties());
        buttonGroup_FileType.add(getJCheckBox_ini());
    }

    private JCheckBox getJCheckBox_Properties()
    {
        if (jCheckBox_Properties == null) {
            jCheckBox_Properties = new JCheckBox();
            jCheckBox_Properties.setText("Properties");
            }
        return jCheckBox_Properties;
    }

    private JCheckBox getJCheckBox_ini()
    {
        if (jCheckBox_ini == null) {
            jCheckBox_ini = new JCheckBox();
            jCheckBox_ini.setText("*.ini files");
            jCheckBox_ini.setEnabled(false);
            }
        return jCheckBox_ini;
    }

    private JCheckBox getJCheckBox_FormattedProperties()
    {
        if (jCheckBox_FormattedProperties == null) {
            jCheckBox_FormattedProperties = new JCheckBox();
            jCheckBox_FormattedProperties.setText("Formatted Properties");
            }
        return jCheckBox_FormattedProperties;
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
            GridBagConstraints gbc_jCheckBox_RightUseLeftHasDefaults = new GridBagConstraints();
            gbc_jCheckBox_RightUseLeftHasDefaults.insets = new Insets(0, 0, 0, 5);
            gbc_jCheckBox_RightUseLeftHasDefaults.anchor = GridBagConstraints.NORTHWEST;
            gbc_jCheckBox_RightUseLeftHasDefaults.gridx = 0;
            gbc_jCheckBox_RightUseLeftHasDefaults.gridy = 0;
            jPanel_TabProperties.add(getJCheckBox_RightUseLeftHasDefaults(), gbc_jCheckBox_RightUseLeftHasDefaults);
            }
        return jPanel_TabProperties;
    }

    private JPanel getJPanel_TabSelect()
    {
        if (jPanel_TabSelect == null) {
            jPanel_TabSelect = new JPanel();
            GridBagLayout gbl_jPanel_TabSelect = new GridBagLayout();
            gbl_jPanel_TabSelect.columnWidths = new int[]{0, 0, 0};
            gbl_jPanel_TabSelect.rowHeights = new int[]{50, 0, 0, 0, 0};
            gbl_jPanel_TabSelect.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            gbl_jPanel_TabSelect.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            jPanel_TabSelect.setLayout(gbl_jPanel_TabSelect);
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            jPanel_TabSelect.add(getScrollPane(), gbc_scrollPane);

            {
                GridBagConstraints gbc_jCheckBox_Properties = new GridBagConstraints();
                gbc_jCheckBox_Properties.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_Properties.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_Properties.gridx = 0;
                gbc_jCheckBox_Properties.gridy = 1;
                jPanel_TabSelect.add(getJCheckBox_Properties(), gbc_jCheckBox_Properties);
            }
            {
                GridBagConstraints gbc_jCheckBox_LeftReadOnly = new GridBagConstraints();
                gbc_jCheckBox_LeftReadOnly.anchor = GridBagConstraints.WEST;
                gbc_jCheckBox_LeftReadOnly.insets = new Insets(0, 0, 5, 0);
                gbc_jCheckBox_LeftReadOnly.gridx = 1;
                gbc_jCheckBox_LeftReadOnly.gridy = 1;
                jPanel_TabSelect.add(getJCheckBox_LeftReadOnly(), gbc_jCheckBox_LeftReadOnly);
            }
            {
                GridBagConstraints gbc_jCheckBox_FormattedProperties = new GridBagConstraints();
                gbc_jCheckBox_FormattedProperties.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_FormattedProperties.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_FormattedProperties.gridx = 0;
                gbc_jCheckBox_FormattedProperties.gridy = 2;
                jPanel_TabSelect.add(getJCheckBox_FormattedProperties(), gbc_jCheckBox_FormattedProperties);
            }
            {
                GridBagConstraints gbc_jCheckBox_ShowLineNumbers = new GridBagConstraints();
                gbc_jCheckBox_ShowLineNumbers.anchor = GridBagConstraints.WEST;
                gbc_jCheckBox_ShowLineNumbers.insets = new Insets(0, 0, 5, 0);
                gbc_jCheckBox_ShowLineNumbers.gridx = 1;
                gbc_jCheckBox_ShowLineNumbers.gridy = 2;
                jPanel_TabSelect.add(getJCheckBox_ShowLineNumbers(), gbc_jCheckBox_ShowLineNumbers);
            }
            {
                GridBagConstraints gbc_jCheckBox_ini = new GridBagConstraints();
                gbc_jCheckBox_ini.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_ini.insets = new Insets(0, 0, 0, 5);
                gbc_jCheckBox_ini.gridx = 0;
                gbc_jCheckBox_ini.gridy = 3;
                jPanel_TabSelect.add(getJCheckBox_ini(), gbc_jCheckBox_ini);
            }
        }
        return jPanel_TabSelect;
    }

    protected JTabbedPane getJTabbedPaneRoot()
    {
        return jTabbedPaneRoot;
    }

    protected abstract ActionListener getActionListener();

    protected JTextField getJTextField( int index )
    {
        return getSelectJPanel().getPanelFile( index ).getJTextField();
    }

    private FilesPanel getSelectJPanel()
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
