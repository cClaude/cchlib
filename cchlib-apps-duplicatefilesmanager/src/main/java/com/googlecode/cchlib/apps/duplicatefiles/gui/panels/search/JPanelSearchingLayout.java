package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.errors.ErrorTableModel;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files.CurrentFiles;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files.CurrentFilesJTable;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;

//NOT public
abstract class JPanelSearchingLayout extends JPanelSearchingDisplayI18n
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingLayout.class );

    private final ErrorTableModel tableModelErrorList = new ErrorTableModel(); // TODO NEED I18N call !!!

    private final JTable jTableErrorList;
    private final JProgressBar jProgressBarFiles;
    private final JProgressBar jProgressBarOctets;
    private final JLabel jLabelBytesReadFromDisk;
    private final JLabel jLabelDuplicateSetsFound;
    private final JLabel jLabelDuplicateFilesFound;

    @I18nIgnore private final JLabel jLabelDuplicateSetsFoundValue;
    @I18nIgnore private final JLabel jLabelDuplicateFilesFoundValue;

    private final JPanel panelCurrentFiles;
    private final CurrentFilesJTable tableCurrentFiles;

    /**
     * @wbp.parser.constructor
     */
    public JPanelSearchingLayout()
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 60, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 2.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        final JLabel lblFilesReads = new JLabel("Files reads :");
        lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
        gbc_lblFilesReads.anchor = GridBagConstraints.EAST;
        gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
        gbc_lblFilesReads.gridx = 0;
        gbc_lblFilesReads.gridy = 0;
        add(lblFilesReads, gbc_lblFilesReads);

        jProgressBarFiles = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
        gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarFiles.gridx = 1;
        gbc_jProgressBarFiles.gridy = 0;
        add(getjProgressBarFiles(), gbc_jProgressBarFiles);

        jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
        jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
        gbc_jLabelBytesReadFromDisk.anchor = GridBagConstraints.EAST;
        gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelBytesReadFromDisk.gridx = 0;
        gbc_jLabelBytesReadFromDisk.gridy = 1;
        add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);

        jProgressBarOctets = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
        gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarOctets.gridx = 1;
        gbc_jProgressBarOctets.gridy = 1;
        add(jProgressBarOctets, gbc_jProgressBarOctets);

        jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
        jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFound.anchor = GridBagConstraints.EAST;
        gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateSetsFound.gridx = 0;
        gbc_jLabelDuplicateSetsFound.gridy = 2;
        add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);

        jLabelDuplicateSetsFoundValue = new JLabel("");
        final GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
        gbc_jLabelDuplicateSetsFoundValue.gridy = 2;
        add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);

        jLabelDuplicateFilesFound = new JLabel();
        jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
        final GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFound.anchor = GridBagConstraints.EAST;
        gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateFilesFound.gridx = 0;
        gbc_jLabelDuplicateFilesFound.gridy = 3;
        add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);

        jLabelDuplicateFilesFoundValue = new JLabel();
        jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
        final GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
        gbc_jLabelDuplicateFilesFoundValue.gridy = 3;
        add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);

        this.panelCurrentFiles = new JPanel();
        final GridBagConstraints gbc_panelCurrentFile = new GridBagConstraints();
        gbc_panelCurrentFile.gridwidth = 2;
        gbc_panelCurrentFile.insets = new Insets(0, 0, 5, 5);
        gbc_panelCurrentFile.fill = GridBagConstraints.BOTH;
        gbc_panelCurrentFile.gridx = 0;
        gbc_panelCurrentFile.gridy = 4;
        add(this.panelCurrentFiles, gbc_panelCurrentFile);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 5;
        add(scrollPane, gbc_scrollPane);

        this.tableCurrentFiles = new CurrentFilesJTable();
        this.panelCurrentFiles.add( tableCurrentFiles );

        jTableErrorList = new JTable();
        scrollPane.setViewportView(jTableErrorList);
    }

    protected final AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    public final void initFixComponents()
    {
        jTableErrorList.setModel( tableModelErrorList );

        getjProgressBarFiles().setStringPainted( true );
        jProgressBarOctets.setStringPainted( true );
    }

    protected final void prepareScan()
    {
        getjProgressBarFiles().setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );

        clearCurrentFiles();
        setCurrentDirLabel(  getTxtCurrentDir() );

        getAppToolKit().setEnabledJButtonCancel( true );
        tableModelErrorList.clear();
    }

    protected final CurrentFiles getCurrentFiles()
    {
        return this.tableCurrentFiles;
    }

    protected final ErrorTableModel getTableModelErrorList()
    {
        return tableModelErrorList;
    }

    protected final JProgressBar getjProgressBarFiles()
    {
        return jProgressBarFiles;
    }

    protected final JProgressBar getjProgressBarOctets()
    {
        return jProgressBarOctets;
    }

    protected final JLabel getjLabelDuplicateSetsFoundValue()
    {
        return jLabelDuplicateSetsFoundValue;
    }

    protected final JLabel getjLabelDuplicateFilesFoundValue()
    {
        return jLabelDuplicateFilesFoundValue;
    }

    protected final void setCurrentFile( final int threadNumber, final File currentFile )
    {
        getCurrentFiles().setCurrentFile( threadNumber, currentFile );
    }

    protected final void setCurrentFileLabels( final String label )
    {
        getCurrentFiles().setCurrentFileLabels( label );
    }

    //    protected final void clearCurrentFiles( final int numberOfThreads )
    //    {
    //        panelCurrentFiles.clearCurrentFiles( numberOfThreads );
    //    }
    //
    //    protected final void clearCurrentFile( final int threadNumber )
    //    {
    //        panelCurrentFiles.clearCurrentFile( threadNumber );
    //    }
    //
    //    protected final void clearCurrentFileLabels()
    //    {
    //        panelCurrentFiles.clearCurrentFileLabels();
    //    }

    private final void setCurrentDirLabel( final String currentDir )
    {
        getCurrentFiles().setCurrentDirLabel( currentDir );
    }

    public final void clearFileErrors()
    {
        tableModelErrorList.clear();
    }

    public final void clearCurrentFiles()
    {
        getCurrentFiles().clear();
    }

    public void clear()
    {
        clearFileErrors();
        clearCurrentFiles();
    }
}
