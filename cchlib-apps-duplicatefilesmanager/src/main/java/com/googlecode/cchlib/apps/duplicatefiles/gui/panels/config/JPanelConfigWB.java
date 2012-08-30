package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import com.googlecode.cchlib.i18n.I18n;
import com.googlecode.cchlib.i18n.I18nSwingHelper;

/**
 *
 *
 */
public abstract class JPanelConfigWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JComboBox<String> jComboBoxFilesFilters;
    private JComboBox<String> jComboBoxDirsFilters;
    protected JCheckBox jCheckBoxFFIgnoreHidden;
    protected JCheckBox jCheckBoxFDIgnoreHidden;
    protected JCheckBox jCheckBoxIgnoreReadOnlyFiles;
    protected JCheckBox jCheckBoxIgnoreEmptyFiles;
    private JPanel jPanelFilters;

    @I18n(methodSuffixName="I18nTileUseFilesFilters")
    private JPanel jPanelFilesFilers;

    @I18n(methodSuffixName="I18nTileUseDirsFilters")
    private JPanel jPanelDirectoryFilters;

    @I18n(methodSuffixName="I18nTileIgnore")
    private JPanel jPanelIgnore;

    /**
     * Create the panel.
     */
    public JPanelConfigWB()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jPanelFilesFilers = new JPanel();
        jPanelFilesFilers.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Files filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_jPanelFilesFilers = new GridBagConstraints();
        gbc_jPanelFilesFilers.fill = GridBagConstraints.BOTH;
        gbc_jPanelFilesFilers.insets = new Insets(0, 0, 5, 5);
        gbc_jPanelFilesFilers.gridx = 0;
        gbc_jPanelFilesFilers.gridy = 0;
        add(jPanelFilesFilers, gbc_jPanelFilesFilers);
        GridBagLayout gbl_jPanelFilesFilers = new GridBagLayout();
        gbl_jPanelFilesFilers.columnWidths = new int[]{0, 0};
        gbl_jPanelFilesFilers.rowHeights = new int[]{0, 0};
        gbl_jPanelFilesFilers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelFilesFilers.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        jPanelFilesFilers.setLayout(gbl_jPanelFilesFilers);

        jComboBoxFilesFilters = createJComboBoxString();
        jComboBoxFilesFilters.addActionListener( getActionListener() );
        GridBagConstraints gbc_jComboBoxFilesFilters = new GridBagConstraints();
        gbc_jComboBoxFilesFilters.fill = GridBagConstraints.HORIZONTAL;
        gbc_jComboBoxFilesFilters.gridx = 0;
        gbc_jComboBoxFilesFilters.gridy = 0;
        jPanelFilesFilers.add(jComboBoxFilesFilters, gbc_jComboBoxFilesFilters);

        jPanelIgnore = new JPanel();
        jPanelIgnore.setBorder(new TitledBorder(null, "Ignore", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_jPanelIgnore = new GridBagConstraints();
        gbc_jPanelIgnore.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelIgnore.gridheight = 2;
        gbc_jPanelIgnore.fill = GridBagConstraints.BOTH;
        gbc_jPanelIgnore.gridx = 1;
        gbc_jPanelIgnore.gridy = 0;
        add(jPanelIgnore, gbc_jPanelIgnore);
        GridBagLayout gbl_jPanelIgnore = new GridBagLayout();
        gbl_jPanelIgnore.columnWidths = new int[]{0, 0, 0};
        gbl_jPanelIgnore.rowHeights = new int[]{0, 0, 0};
        gbl_jPanelIgnore.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_jPanelIgnore.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        jPanelIgnore.setLayout(gbl_jPanelIgnore);

        jCheckBoxFFIgnoreHidden = new JCheckBox("Hidden files");
        jCheckBoxFFIgnoreHidden.setSelected( true ); // TODO prefs
        GridBagConstraints gbc_jCheckBoxFFIgnoreHidden = new GridBagConstraints();
        gbc_jCheckBoxFFIgnoreHidden.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxFFIgnoreHidden.insets = new Insets(0, 0, 5, 5);
        gbc_jCheckBoxFFIgnoreHidden.gridx = 0;
        gbc_jCheckBoxFFIgnoreHidden.gridy = 0;
        jPanelIgnore.add(jCheckBoxFFIgnoreHidden, gbc_jCheckBoxFFIgnoreHidden);

        jCheckBoxIgnoreReadOnlyFiles = new JCheckBox("Read only files");
        jCheckBoxIgnoreReadOnlyFiles.setSelected( true ); // TODO prefs
        GridBagConstraints gbc_jCheckBoxIgnoreReadOnlyFiles = new GridBagConstraints();
        gbc_jCheckBoxIgnoreReadOnlyFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxIgnoreReadOnlyFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jCheckBoxIgnoreReadOnlyFiles.gridx = 1;
        gbc_jCheckBoxIgnoreReadOnlyFiles.gridy = 0;
        jPanelIgnore.add(jCheckBoxIgnoreReadOnlyFiles, gbc_jCheckBoxIgnoreReadOnlyFiles);

        jCheckBoxFDIgnoreHidden = new JCheckBox("Hidden directories");
        jCheckBoxFDIgnoreHidden.setSelected( true ); // TODO prefs
        GridBagConstraints gbc_jCheckBoxFDIgnoreHidden = new GridBagConstraints();
        gbc_jCheckBoxFDIgnoreHidden.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxFDIgnoreHidden.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxFDIgnoreHidden.gridx = 0;
        gbc_jCheckBoxFDIgnoreHidden.gridy = 1;
        jPanelIgnore.add(jCheckBoxFDIgnoreHidden, gbc_jCheckBoxFDIgnoreHidden);

        jCheckBoxIgnoreEmptyFiles = new JCheckBox("Empty files");
        jCheckBoxIgnoreEmptyFiles.setSelected( true ); // TODO prefs
        GridBagConstraints gbc_jCheckBoxIgnoreEmptyFiles = new GridBagConstraints();
        gbc_jCheckBoxIgnoreEmptyFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxIgnoreEmptyFiles.gridx = 1;
        gbc_jCheckBoxIgnoreEmptyFiles.gridy = 1;
        jPanelIgnore.add(jCheckBoxIgnoreEmptyFiles, gbc_jCheckBoxIgnoreEmptyFiles);

        jPanelDirectoryFilters = new JPanel();
        jPanelDirectoryFilters.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Directories filters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_jPanelDirectoryFilters = new GridBagConstraints();
        gbc_jPanelDirectoryFilters.fill = GridBagConstraints.BOTH;
        gbc_jPanelDirectoryFilters.insets = new Insets(0, 0, 5, 5);
        gbc_jPanelDirectoryFilters.gridx = 0;
        gbc_jPanelDirectoryFilters.gridy = 1;
        add(jPanelDirectoryFilters, gbc_jPanelDirectoryFilters);
        GridBagLayout gbl_jPanelDirectoryFilters = new GridBagLayout();
        gbl_jPanelDirectoryFilters.columnWidths = new int[]{0, 0};
        gbl_jPanelDirectoryFilters.rowHeights = new int[]{0, 0};
        gbl_jPanelDirectoryFilters.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelDirectoryFilters.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        jPanelDirectoryFilters.setLayout(gbl_jPanelDirectoryFilters);

        jComboBoxDirsFilters = createJComboBoxString();
        jComboBoxDirsFilters.addActionListener( getActionListener() );
        GridBagConstraints gbc_jComboBoxDirsFilters = new GridBagConstraints();
        gbc_jComboBoxDirsFilters.fill = GridBagConstraints.HORIZONTAL;
        gbc_jComboBoxDirsFilters.gridx = 0;
        gbc_jComboBoxDirsFilters.gridy = 0;
        jPanelDirectoryFilters.add(jComboBoxDirsFilters, gbc_jComboBoxDirsFilters);

        JScrollPane jScrollPanelFilters = new JScrollPane();
        GridBagConstraints gbc_jScrollPanelFilters = new GridBagConstraints();
        gbc_jScrollPanelFilters.fill = GridBagConstraints.BOTH;
        gbc_jScrollPanelFilters.gridx = 0;
        gbc_jScrollPanelFilters.gridy = 2;
        gbc_jScrollPanelFilters.gridwidth = 2;
        add(jScrollPanelFilters, gbc_jScrollPanelFilters);

        jPanelFilters = new JPanel();
        jScrollPanelFilters.setViewportView(jPanelFilters);
        jPanelFilters.setLayout(new BoxLayout(jPanelFilters, BoxLayout.Y_AXIS));
    }

    protected abstract ActionListener getActionListener();

    public void setI18nTileUseFilesFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelFilesFilers(), localText );
    }

    public String getI18nTileUseFilesFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelFilesFilers() );
    }

    public void setI18nTileUseDirsFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelDirectoryFilters(), localText );
    }

    public String getI18nTileUseDirsFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelDirectoryFilters() );
    }

    public void setI18nTileIgnore(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelIgnore(), localText );
    }

    public String getI18nTileIgnore()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelIgnore() );
    }

    /**
     * @wbp.factory
     */
    public static JComboBox<String> createJComboBoxString()
    {
        JComboBox<String> comboBox = new JComboBox<String>();
        return comboBox;
    }

    protected JComboBox<String> getJComboBoxFilesFilters()
    {
        return jComboBoxFilesFilters;
    }

    protected JComboBox<String> getJComboBoxDirsFilters()
    {
        return jComboBoxDirsFilters;
    }

    protected JPanel getJPanelFilters()
    {
        return jPanelFilters;
    }

    protected JPanel getJPanelFilesFilers()
    {
        return jPanelFilesFilers;
    }

    protected JPanel getJPanelDirectoryFilters()
    {
        return jPanelDirectoryFilters;
    }

    protected JPanel getJPanelIgnore()
    {
        return jPanelIgnore;
    }
}
