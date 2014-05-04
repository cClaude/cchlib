package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;


public abstract class JPanelConfigWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final JComboBox<String> jComboBoxFilesFilters;
    private final JComboBox<String> jComboBoxDirsFilters;
    private final JCheckBox jCheckBoxFFIgnoreHidden;
    private final JCheckBox jCheckBoxFDIgnoreHidden;
    private final JCheckBox jCheckBoxIgnoreReadOnlyFiles;
    private final JCheckBox jCheckBoxIgnoreEmptyFiles;
    private final JPanel jPanelFilters;

    private final JPanel jPanelFilesFilers;
    private final TitledBorder jPanelFilesFilersTitledBorder = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Files filters", TitledBorder.LEADING, TitledBorder.TOP, null, null);

    private final JPanel jPanelDirectoryFilters;
    private final Border jPanelDirectoryFiltersTitledBorder = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Directories filters", TitledBorder.LEADING, TitledBorder.TOP, null, null);

    private final JPanel jPanelIgnore;
    private final TitledBorder jPanelIgnoreTitledBorder = new TitledBorder(null, "Ignore", TitledBorder.LEADING, TitledBorder.TOP, null, null);

    /**
     * Create the panel.
     */
    public JPanelConfigWB()
    {
        final PreferencesControler prefs = getAppToolKit().getPreferences();

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            jPanelFilesFilers = new JPanel();
            jPanelFilesFilers.setBorder( jPanelFilesFilersTitledBorder );
            final GridBagConstraints gbc_jPanelFilesFilers = new GridBagConstraints();
            gbc_jPanelFilesFilers.fill = GridBagConstraints.BOTH;
            gbc_jPanelFilesFilers.insets = new Insets(0, 0, 5, 5);
            gbc_jPanelFilesFilers.gridx = 0;
            gbc_jPanelFilesFilers.gridy = 0;
            add(jPanelFilesFilers, gbc_jPanelFilesFilers);
            final GridBagLayout gbl_jPanelFilesFilers = new GridBagLayout();
            gbl_jPanelFilesFilers.columnWidths = new int[]{0, 0};
            gbl_jPanelFilesFilers.rowHeights = new int[]{0, 0};
            gbl_jPanelFilesFilers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gbl_jPanelFilesFilers.rowWeights = new double[]{0.0, Double.MIN_VALUE};
            jPanelFilesFilers.setLayout(gbl_jPanelFilesFilers);
        }

        jComboBoxFilesFilters = createJComboBoxString();
        jComboBoxFilesFilters.addActionListener( getActionListener() );
        final GridBagConstraints gbc_jComboBoxFilesFilters = new GridBagConstraints();
        gbc_jComboBoxFilesFilters.fill = GridBagConstraints.HORIZONTAL;
        gbc_jComboBoxFilesFilters.gridx = 0;
        gbc_jComboBoxFilesFilters.gridy = 0;
        jPanelFilesFilers.add(jComboBoxFilesFilters, gbc_jComboBoxFilesFilters);

        jPanelIgnore = new JPanel();
        jPanelIgnore.setBorder( jPanelIgnoreTitledBorder  );
        final GridBagConstraints gbc_jPanelIgnore = new GridBagConstraints();
        gbc_jPanelIgnore.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelIgnore.gridheight = 2;
        gbc_jPanelIgnore.fill = GridBagConstraints.BOTH;
        gbc_jPanelIgnore.gridx = 1;
        gbc_jPanelIgnore.gridy = 0;
        add(jPanelIgnore, gbc_jPanelIgnore);
        final GridBagLayout gbl_jPanelIgnore = new GridBagLayout();
        gbl_jPanelIgnore.columnWidths = new int[]{0, 0, 0};
        gbl_jPanelIgnore.rowHeights = new int[]{0, 0, 0};
        gbl_jPanelIgnore.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_jPanelIgnore.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        jPanelIgnore.setLayout(gbl_jPanelIgnore);

        jCheckBoxFFIgnoreHidden = new JCheckBox("Hidden files");
        jCheckBoxFFIgnoreHidden.setSelected( prefs.isIgnoreHiddenFiles() );
        final GridBagConstraints gbc_jCheckBoxFFIgnoreHidden = new GridBagConstraints();
        gbc_jCheckBoxFFIgnoreHidden.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxFFIgnoreHidden.insets = new Insets(0, 0, 5, 5);
        gbc_jCheckBoxFFIgnoreHidden.gridx = 0;
        gbc_jCheckBoxFFIgnoreHidden.gridy = 0;
        jPanelIgnore.add(jCheckBoxFFIgnoreHidden, gbc_jCheckBoxFFIgnoreHidden);

        jCheckBoxIgnoreReadOnlyFiles = new JCheckBox("Read only files");
        jCheckBoxIgnoreReadOnlyFiles.setSelected( prefs.isIgnoreReadOnlyFiles() );
        final GridBagConstraints gbc_jCheckBoxIgnoreReadOnlyFiles = new GridBagConstraints();
        gbc_jCheckBoxIgnoreReadOnlyFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxIgnoreReadOnlyFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jCheckBoxIgnoreReadOnlyFiles.gridx = 1;
        gbc_jCheckBoxIgnoreReadOnlyFiles.gridy = 0;
        jPanelIgnore.add(jCheckBoxIgnoreReadOnlyFiles, gbc_jCheckBoxIgnoreReadOnlyFiles);

        jCheckBoxFDIgnoreHidden = new JCheckBox("Hidden directories");
        jCheckBoxFDIgnoreHidden.setSelected( prefs.isIgnoreHiddenDirectories() );
        final GridBagConstraints gbc_jCheckBoxFDIgnoreHidden = new GridBagConstraints();
        gbc_jCheckBoxFDIgnoreHidden.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxFDIgnoreHidden.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxFDIgnoreHidden.gridx = 0;
        gbc_jCheckBoxFDIgnoreHidden.gridy = 1;
        jPanelIgnore.add(jCheckBoxFDIgnoreHidden, gbc_jCheckBoxFDIgnoreHidden);

        jCheckBoxIgnoreEmptyFiles = new JCheckBox("Empty files");
        jCheckBoxIgnoreEmptyFiles.setSelected( prefs.isIgnoreEmptyFiles() );
        final GridBagConstraints gbc_jCheckBoxIgnoreEmptyFiles = new GridBagConstraints();
        gbc_jCheckBoxIgnoreEmptyFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxIgnoreEmptyFiles.gridx = 1;
        gbc_jCheckBoxIgnoreEmptyFiles.gridy = 1;
        jPanelIgnore.add(jCheckBoxIgnoreEmptyFiles, gbc_jCheckBoxIgnoreEmptyFiles);

        jPanelDirectoryFilters = new JPanel();
        jPanelDirectoryFilters.setBorder( jPanelDirectoryFiltersTitledBorder  );
        final GridBagConstraints gbc_jPanelDirectoryFilters = new GridBagConstraints();
        gbc_jPanelDirectoryFilters.fill = GridBagConstraints.BOTH;
        gbc_jPanelDirectoryFilters.insets = new Insets(0, 0, 5, 5);
        gbc_jPanelDirectoryFilters.gridx = 0;
        gbc_jPanelDirectoryFilters.gridy = 1;
        add(jPanelDirectoryFilters, gbc_jPanelDirectoryFilters);
        final GridBagLayout gbl_jPanelDirectoryFilters = new GridBagLayout();
        gbl_jPanelDirectoryFilters.columnWidths = new int[]{0, 0};
        gbl_jPanelDirectoryFilters.rowHeights = new int[]{0, 0};
        gbl_jPanelDirectoryFilters.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_jPanelDirectoryFilters.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        jPanelDirectoryFilters.setLayout(gbl_jPanelDirectoryFilters);

        jComboBoxDirsFilters = createJComboBoxString();
        jComboBoxDirsFilters.addActionListener( getActionListener() );
        final GridBagConstraints gbc_jComboBoxDirsFilters = new GridBagConstraints();
        gbc_jComboBoxDirsFilters.fill = GridBagConstraints.HORIZONTAL;
        gbc_jComboBoxDirsFilters.gridx = 0;
        gbc_jComboBoxDirsFilters.gridy = 0;
        jPanelDirectoryFilters.add(jComboBoxDirsFilters, gbc_jComboBoxDirsFilters);

        final JScrollPane jScrollPanelFilters = new JScrollPane();
        final GridBagConstraints gbc_jScrollPanelFilters = new GridBagConstraints();
        gbc_jScrollPanelFilters.fill = GridBagConstraints.BOTH;
        gbc_jScrollPanelFilters.gridx = 0;
        gbc_jScrollPanelFilters.gridy = 2;
        gbc_jScrollPanelFilters.gridwidth = 2;
        add(jScrollPanelFilters, gbc_jScrollPanelFilters);

        jPanelFilters = new JPanel();
        jScrollPanelFilters.setViewportView(jPanelFilters);
        jPanelFilters.setLayout(new BoxLayout(jPanelFilters, BoxLayout.Y_AXIS));
    }

    public final AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    protected abstract ActionListener getActionListener();

    /**
     * @wbp.factory
     */
    public static JComboBox<String> createJComboBoxString()
    {
        return new JComboBox<>();
    }

    protected final JComboBox<String> getJComboBoxFilesFilters()
    {
        return jComboBoxFilesFilters;
    }

    protected final JComboBox<String> getJComboBoxDirsFilters()
    {
        return jComboBoxDirsFilters;
    }

    protected final JPanel getJPanelFilters()
    {
        return jPanelFilters;
    }

    protected JPanel getJPanelFilesFilers()
    {
        return jPanelFilesFilers;
    }

    protected final JPanel getJPanelDirectoryFilters()
    {
        return jPanelDirectoryFilters;
    }

    protected final JPanel getJPanelIgnore()
    {
        return jPanelIgnore;
    }

    protected final JCheckBox getjCheckBoxFFIgnoreHidden()
    {
        return jCheckBoxFFIgnoreHidden;
    }

    protected final JCheckBox getjCheckBoxFDIgnoreHidden()
    {
        return jCheckBoxFDIgnoreHidden;
    }

    protected final JCheckBox getjCheckBoxIgnoreReadOnlyFiles()
    {
        return jCheckBoxIgnoreReadOnlyFiles;
    }

    protected final JCheckBox getjCheckBoxIgnoreEmptyFiles()
    {
        return jCheckBoxIgnoreEmptyFiles;
    }
}
