package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import cx.ath.choisnet.i18n.I18nIgnore;

/**
 *
 *
 */
public class JPanelSearchingWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTable jTableErrorList;
    private JProgressBar jProgressBarFiles;
    private JProgressBar jProgressBarOctets;
    @I18nIgnore protected JLabel jTextFieldCurrentFile;
    protected JLabel jLabelCurrentFile;
    protected JLabel jLabelBytesReadFromDisk;
    protected JLabel jLabelDuplicateSetsFound;
    @I18nIgnore protected JLabel jLabelDuplicateSetsFoundValue;
    protected JLabel jLabelDuplicateFilesFound;
    @I18nIgnore protected JLabel jLabelDuplicateFilesFoundValue;

    /**
     * Create the panel.
     */
    public JPanelSearchingWB()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jLabelCurrentFile = new JLabel("Current File :");
        jLabelCurrentFile.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelCurrentFile = new GridBagConstraints();
        gbc_jLabelCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelCurrentFile.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelCurrentFile.gridx = 0;
        gbc_jLabelCurrentFile.gridy = 0;
        add(jLabelCurrentFile, gbc_jLabelCurrentFile);

        jTextFieldCurrentFile = new JLabel();
        GridBagConstraints gbc_jTextFieldCurrentFile = new GridBagConstraints();
        gbc_jTextFieldCurrentFile.fill = GridBagConstraints.HORIZONTAL;
        gbc_jTextFieldCurrentFile.insets = new Insets(0, 0, 5, 0);
        gbc_jTextFieldCurrentFile.gridx = 1;
        gbc_jTextFieldCurrentFile.gridy = 0;
        add(jTextFieldCurrentFile, gbc_jTextFieldCurrentFile);

        JLabel lblFilesReads = new JLabel("Files reads :");
        lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
        gbc_lblFilesReads.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
        gbc_lblFilesReads.gridx = 0;
        gbc_lblFilesReads.gridy = 1;
        add(lblFilesReads, gbc_lblFilesReads);

        jProgressBarFiles = new JProgressBar();
        GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
        gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarFiles.gridx = 1;
        gbc_jProgressBarFiles.gridy = 1;
        add(jProgressBarFiles, gbc_jProgressBarFiles);

        jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
        jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
        gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelBytesReadFromDisk.gridx = 0;
        gbc_jLabelBytesReadFromDisk.gridy = 2;
        add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);

        jProgressBarOctets = new JProgressBar();
        GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
        gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarOctets.gridx = 1;
        gbc_jProgressBarOctets.gridy = 2;
        add(jProgressBarOctets, gbc_jProgressBarOctets);

        jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
        jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateSetsFound.gridx = 0;
        gbc_jLabelDuplicateSetsFound.gridy = 3;
        add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);

        jLabelDuplicateSetsFoundValue = new JLabel("");
        GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
        gbc_jLabelDuplicateSetsFoundValue.gridy = 3;
        add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);

        jLabelDuplicateFilesFound = new JLabel();
        jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
        GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFound.gridx = 0;
        gbc_jLabelDuplicateFilesFound.gridy = 4;
        add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);

        jLabelDuplicateFilesFoundValue = new JLabel();
        jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
        gbc_jLabelDuplicateFilesFoundValue.gridy = 4;
        add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 5;
        add(scrollPane, gbc_scrollPane);

        jTableErrorList = new JTable();
        scrollPane.setViewportView(jTableErrorList);

    }

    protected JProgressBar getJProgressBarFiles() {
        return jProgressBarFiles;
    }
    protected JProgressBar getJProgressBarOctets() {
        return jProgressBarOctets;
    }
    protected JTable getJTableErrorList() {
        return jTableErrorList;
    }
}
