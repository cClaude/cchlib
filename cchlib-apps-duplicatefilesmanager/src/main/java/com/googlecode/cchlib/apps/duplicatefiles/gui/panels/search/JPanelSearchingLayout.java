package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;

//NOT public
abstract class JPanelSearchingLayout extends JPanelSearchingDisplayI18n
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingLayout.class );

    private final int nThreads;

    private DefaultTableModel tableModelErrorList;

    private final JTable jTableErrorList;
    private final JProgressBar jProgressBarFiles;
    private final JProgressBar jProgressBarOctets;
    private final JLabel jLabelBytesReadFromDisk;
    private final JLabel jLabelDuplicateSetsFound;
    private final JLabel jLabelDuplicateFilesFound;

    @I18nIgnore private final JLabel jLabelDuplicateSetsFoundValue;
    @I18nIgnore private final JLabel jLabelDuplicateFilesFoundValue;

    private final JPanelCurrentFiles panelCurrentFiles;

    /**
     * @wbp.parser.constructor
     */
    public JPanelSearchingLayout()
    {
        this( 2 );
    }

    public JPanelSearchingLayout( final int nThreads )
    {
        this.nThreads = nThreads;

        LOGGER.info( "nThreads = " +nThreads );

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{60, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 2.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.panelCurrentFiles = new JPanelCurrentFiles( nThreads );
        final GridBagConstraints gbc_panelCurrentFile = new GridBagConstraints();
        gbc_panelCurrentFile.gridwidth = 2;
        gbc_panelCurrentFile.insets = new Insets(0, 0, 5, 5);
        gbc_panelCurrentFile.fill = GridBagConstraints.BOTH;
        gbc_panelCurrentFile.gridx = 0;
        gbc_panelCurrentFile.gridy = 0;
        add(this.panelCurrentFiles, gbc_panelCurrentFile);

        final JLabel lblFilesReads = new JLabel("Files reads :");
        lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
        gbc_lblFilesReads.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
        gbc_lblFilesReads.gridx = 0;
        gbc_lblFilesReads.gridy = 1;
        add(lblFilesReads, gbc_lblFilesReads);

        jProgressBarFiles = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
        gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarFiles.gridx = 1;
        gbc_jProgressBarFiles.gridy = 1;
        add(getjProgressBarFiles(), gbc_jProgressBarFiles);

        jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
        jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
        gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelBytesReadFromDisk.gridx = 0;
        gbc_jLabelBytesReadFromDisk.gridy = 2;
        add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);

        jProgressBarOctets = new JProgressBar();
        final GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
        gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
        gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
        gbc_jProgressBarOctets.gridx = 1;
        gbc_jProgressBarOctets.gridy = 2;
        add(jProgressBarOctets, gbc_jProgressBarOctets);

        jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
        jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
        final GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateSetsFound.gridx = 0;
        gbc_jLabelDuplicateSetsFound.gridy = 3;
        add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);

        jLabelDuplicateSetsFoundValue = new JLabel("");
        final GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
        gbc_jLabelDuplicateSetsFoundValue.gridy = 3;
        add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);

        jLabelDuplicateFilesFound = new JLabel();
        jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
        final GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
        gbc_jLabelDuplicateFilesFound.gridx = 0;
        gbc_jLabelDuplicateFilesFound.gridy = 4;
        add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);

        jLabelDuplicateFilesFoundValue = new JLabel();
        jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
        final GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
        gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
        gbc_jLabelDuplicateFilesFoundValue.gridy = 4;
        add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 5;
        add(scrollPane, gbc_scrollPane);

        jTableErrorList = new JTable();
        scrollPane.setViewportView(jTableErrorList);
    }

    protected int getNumberOfThreads()
    {
        return this.nThreads;
    }

    protected AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    public void initFixComponents()
    {
         tableModelErrorList = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable( final int row, final int column )
            {
                return false;
            }
        };
        tableModelErrorList.addColumn( "Files" );
        tableModelErrorList.addColumn( "Errors" );
        jTableErrorList.setModel( tableModelErrorList );

        getjProgressBarFiles().setStringPainted( true );
        jProgressBarOctets.setStringPainted( true );
    }

    protected void prepareScan()
    {
        getjProgressBarFiles().setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );

        clearCurrentFiles();
        setCurrentDir( getTxtCurrentDir() );

        getAppToolKit().setEnabledJButtonCancel( true );
        tableModelErrorList.setRowCount( 0 );
    }

    protected DefaultTableModel getTableModelErrorList()
    {
        return tableModelErrorList;
    }

    protected JProgressBar getjProgressBarFiles()
    {
        return jProgressBarFiles;
    }

    protected JProgressBar getjProgressBarOctets()
    {
        return jProgressBarOctets;
    }

    protected JLabel getjLabelDuplicateSetsFoundValue()
    {
        return jLabelDuplicateSetsFoundValue;
    }

    protected JLabel getjLabelDuplicateFilesFoundValue()
    {
        return jLabelDuplicateFilesFoundValue;
    }

    protected void setCurrentFile( final String currentFileText, final int threadNumber )
    {
        panelCurrentFiles.setCurrentFile( currentFileText, threadNumber );
    }

    protected void setCurrentFileLabels( final String label )
    {
        panelCurrentFiles.setCurrentFileLabels( label );
    }

    protected void clearCurrentFiles()
    {
        panelCurrentFiles.clearCurrentFiles();
    }

    protected void clearCurrentFile( final int threadNumber )
    {
        panelCurrentFiles.clearCurrentFile( threadNumber );
    }

    protected void clearCurrentFileLabels()
    {
        panelCurrentFiles.clearCurrentFileLabels();
    }

    private void setCurrentDir( final String currentDir )
    {
        panelCurrentFiles.setCurrentDir( currentDir );
    }

    public void clear()
    {
        while( tableModelErrorList.getRowCount() > 0 ) {
            tableModelErrorList.removeRow( 0 );
        }
    }
}
