// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.errors.ErrorTableModel;
import com.googlecode.cchlib.apps.duplicatefiles.services.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

//NOT public
abstract class JPanelSearchingLayoutWB extends JPanelSearchingDisplayI18n implements I18nAutoCoreUpdatable // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingLayoutWB.class );

    private final int nThreads;

    private final ErrorTableModel tableModelErrorList = new ErrorTableModel();

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
     * Do not use - only for WindowBuilder Editor
     * @wbp.parser.constructor
     */
    public JPanelSearchingLayoutWB()
    {
        this( 2 );
    }

    public JPanelSearchingLayoutWB( final int nThreads )
    {
        this.nThreads = nThreads;

        LOGGER.info( "nThreads = " +nThreads );

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 30, 30, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 2.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {// line 0
            final JLabel lblFilesReads = new JLabel("Files reads :");
            lblFilesReads.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_lblFilesReads = new GridBagConstraints();
            gbc_lblFilesReads.fill = GridBagConstraints.HORIZONTAL;
            gbc_lblFilesReads.insets = new Insets(0, 0, 5, 5);
            gbc_lblFilesReads.gridx = 0;
            gbc_lblFilesReads.gridy = 0;
            add(lblFilesReads, gbc_lblFilesReads);
        }
        {// line 0
            this.jProgressBarFiles = new JProgressBar();
            final GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
            gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jProgressBarFiles.gridx = 1;
            gbc_jProgressBarFiles.gridy = 0;
            add(getjProgressBarFiles(), gbc_jProgressBarFiles);
        }

        {// line 1
            this.jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
            this.jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
            gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelBytesReadFromDisk.gridx = 0;
            gbc_jLabelBytesReadFromDisk.gridy = 1;
            add(this.jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);
        }
        {// line 1
            this.jProgressBarOctets = new JProgressBar();
            final GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
            gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
            gbc_jProgressBarOctets.gridx = 1;
            gbc_jProgressBarOctets.gridy = 1;
            add(this.jProgressBarOctets, gbc_jProgressBarOctets);
        }

        {// line 2
            this.jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
            this.jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
            gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDuplicateSetsFound.gridx = 0;
            gbc_jLabelDuplicateSetsFound.gridy = 2;
            add(this.jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);
        }
        {// line 2
            this.jLabelDuplicateSetsFoundValue = new JLabel();
            final GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
            gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
            gbc_jLabelDuplicateSetsFoundValue.gridy = 2;
            add(this.jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);
        }

        {// line 3
            this.jLabelDuplicateFilesFound = new JLabel();
            this.jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
            this.jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
            final GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
            gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDuplicateFilesFound.gridx = 0;
            gbc_jLabelDuplicateFilesFound.gridy = 3;
            add(this.jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);
        }
        {// line 3
            this.jLabelDuplicateFilesFoundValue = new JLabel();
            this.jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
            final GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
            gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.BOTH;
            gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
            gbc_jLabelDuplicateFilesFoundValue.gridy = 3;
            add(this.jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);
        }

        {// line 4
            this.panelCurrentFiles = new JPanelCurrentFiles( nThreads );
            final GridBagConstraints gbc_panelCurrentFile = new GridBagConstraints();
            gbc_panelCurrentFile.gridwidth = 2;
            gbc_panelCurrentFile.insets = new Insets(0, 0, 5, 0);
            gbc_panelCurrentFile.fill = GridBagConstraints.BOTH;
            gbc_panelCurrentFile.gridx = 0;
            gbc_panelCurrentFile.gridy = 4;
            add(this.panelCurrentFiles, gbc_panelCurrentFile);
        }

        {// line 5
            final JScrollPane scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 5;
            add(scrollPane, gbc_scrollPane);

            this.jTableErrorList = new JTable();
            scrollPane.setViewportView(this.jTableErrorList);
        }
    }

    protected int getNumberOfThreads()
    {
        return this.nThreads;
    }

    protected AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    @Override
    public final void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
        autoI18n.performeI18n( this.tableModelErrorList, ErrorTableModel.class );
    }

    public void initFixComponents()
    {
//         tableModelErrorList = new DefaultTableModel() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public boolean isCellEditable( final int row, final int column )
//            {
//                return false;
//            }
//        };
//        tableModelErrorList.addColumn( "Files" );
//        tableModelErrorList.addColumn( "Errors" );
        this.jTableErrorList.setModel( this.tableModelErrorList );

        getjProgressBarFiles().setStringPainted( true );
        this.jProgressBarOctets.setStringPainted( true );
    }

    protected void prepareScan()
    {
        getjProgressBarFiles().setIndeterminate( true );
        this.jProgressBarOctets.setIndeterminate( true );

        clearCurrentFiles();
        setCurrentDir( getTxtCurrentDir() );

        getAppToolKit().setEnabledJButtonCancel( true );

        //tableModelErrorList.setRowCount( 0 );
        clearErrors();
    }

    protected void clearErrors()
    {
        this.tableModelErrorList.clear();
    }

    protected ErrorTableModel getTableModelErrorList()
    {
        return this.tableModelErrorList;
    }

    protected JProgressBar getjProgressBarFiles()
    {
        return this.jProgressBarFiles;
    }

    protected JProgressBar getjProgressBarOctets()
    {
        return this.jProgressBarOctets;
    }

    protected JLabel getjLabelDuplicateSetsFoundValue()
    {
        return this.jLabelDuplicateSetsFoundValue;
    }

    protected JLabel getjLabelDuplicateFilesFoundValue()
    {
        return this.jLabelDuplicateFilesFoundValue;
    }

    protected void setCurrentFile( final String currentFileText, final int threadNumber )
    {
        this.panelCurrentFiles.setCurrentFile( currentFileText, threadNumber );
    }

    protected void setCurrentFileLabels( final String label )
    {
        this.panelCurrentFiles.setCurrentFileLabels( label );
    }

    protected void clearCurrentFiles()
    {
        this.panelCurrentFiles.clearCurrentFiles();
    }

    protected void clearCurrentFile( final int threadNumber )
    {
        this.panelCurrentFiles.clearCurrentFile( threadNumber );
    }

    protected void clearCurrentFileLabels()
    {
        this.panelCurrentFiles.clearCurrentFileLabels();
    }

    private void setCurrentDir( final String currentDir )
    {
        this.panelCurrentFiles.setCurrentDir( currentDir );
    }

//    public void clear()
//    {
//        while( tableModelErrorList.getRowCount() > 0 ) {
//            tableModelErrorList.removeRow( 0 );
//        }
//    }
}
