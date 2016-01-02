package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.errors.ErrorTableModel;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;

//NOT public
abstract class JPanelSearchingLayout extends JPanelSearchingDisplayI18n implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingLayout.class );
    protected static final String ACTION_REMOVE_ERROR_ENTRY = "ACTION_REMOVE_ERROR_ENTRY";
    protected static final String REMOVE_ERROR_ENTRY_CLIENT_PROPERTY_KEY = "REMOVE_ERROR_ENTRY_CLIENT_PROPERTY_KEY";

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
    protected JMenuItem txtMenuRemoveError;

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
            jProgressBarFiles = new JProgressBar();
            final GridBagConstraints gbc_jProgressBarFiles = new GridBagConstraints();
            gbc_jProgressBarFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jProgressBarFiles.gridx = 1;
            gbc_jProgressBarFiles.gridy = 0;
            add(getjProgressBarFiles(), gbc_jProgressBarFiles);
        }

        {// line 1
            jLabelBytesReadFromDisk = new JLabel("Bytes reads :");
            jLabelBytesReadFromDisk.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_jLabelBytesReadFromDisk = new GridBagConstraints();
            gbc_jLabelBytesReadFromDisk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelBytesReadFromDisk.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelBytesReadFromDisk.gridx = 0;
            gbc_jLabelBytesReadFromDisk.gridy = 1;
            add(jLabelBytesReadFromDisk, gbc_jLabelBytesReadFromDisk);
        }
        {// line 1
            jProgressBarOctets = new JProgressBar();
            final GridBagConstraints gbc_jProgressBarOctets = new GridBagConstraints();
            gbc_jProgressBarOctets.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarOctets.insets = new Insets(0, 0, 5, 0);
            gbc_jProgressBarOctets.gridx = 1;
            gbc_jProgressBarOctets.gridy = 1;
            add(jProgressBarOctets, gbc_jProgressBarOctets);
        }

        {// line 2
            jLabelDuplicateSetsFound = new JLabel("Duplicate sets found :");
            jLabelDuplicateSetsFound.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_jLabelDuplicateSetsFound = new GridBagConstraints();
            gbc_jLabelDuplicateSetsFound.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateSetsFound.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDuplicateSetsFound.gridx = 0;
            gbc_jLabelDuplicateSetsFound.gridy = 2;
            add(jLabelDuplicateSetsFound, gbc_jLabelDuplicateSetsFound);
        }
        {// line 2
            jLabelDuplicateSetsFoundValue = new JLabel();
            final GridBagConstraints gbc_jLabelDuplicateSetsFoundValue = new GridBagConstraints();
            gbc_jLabelDuplicateSetsFoundValue.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateSetsFoundValue.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDuplicateSetsFoundValue.gridx = 1;
            gbc_jLabelDuplicateSetsFoundValue.gridy = 2;
            add(jLabelDuplicateSetsFoundValue, gbc_jLabelDuplicateSetsFoundValue);
        }

        {// line 3
            jLabelDuplicateFilesFound = new JLabel();
            jLabelDuplicateFilesFound.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabelDuplicateFilesFound.setText("DuplicateFilesFound :");
            final GridBagConstraints gbc_jLabelDuplicateFilesFound = new GridBagConstraints();
            gbc_jLabelDuplicateFilesFound.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDuplicateFilesFound.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDuplicateFilesFound.gridx = 0;
            gbc_jLabelDuplicateFilesFound.gridy = 3;
            add(jLabelDuplicateFilesFound, gbc_jLabelDuplicateFilesFound);
        }
        {// line 3
            jLabelDuplicateFilesFoundValue = new JLabel();
            jLabelDuplicateFilesFoundValue.setHorizontalAlignment(SwingConstants.LEFT);
            final GridBagConstraints gbc_jLabelDuplicateFilesFoundValue = new GridBagConstraints();
            gbc_jLabelDuplicateFilesFoundValue.fill = GridBagConstraints.BOTH;
            gbc_jLabelDuplicateFilesFoundValue.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDuplicateFilesFoundValue.gridx = 1;
            gbc_jLabelDuplicateFilesFoundValue.gridy = 3;
            add(jLabelDuplicateFilesFoundValue, gbc_jLabelDuplicateFilesFoundValue);
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

            jTableErrorList = new JTable();
            scrollPane.setViewportView(jTableErrorList);
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
        autoI18n.performeI18n( tableModelErrorList, ErrorTableModel.class );
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
        jTableErrorList.setModel( tableModelErrorList );

        final JPopupMenuForJTable popupMenu = newJPopupMenuForJTable();
        popupMenu.addMenu();

        getjProgressBarFiles().setStringPainted( true );
        jProgressBarOctets.setStringPainted( true );
    }

    private JPopupMenuForJTable newJPopupMenuForJTable()
    {
        final ActionListener removeErrorListener = actionEvent -> removeErrorEntry( actionEvent );

        return new JPopupMenuForJTable(jTableErrorList)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected JPopupMenu createContextMenu(
                    final int rowIndex,
                    final int columnIndex
                    )
            {
                final JPopupMenu contextMenu = new JPopupMenu();

                if( rowIndex == 0 ) {
                    addJMenuItem(
                            contextMenu,
                            txtMenuRemoveError,
                            removeErrorListener,
                            ACTION_REMOVE_ERROR_ENTRY,              // actionCommand,
                            REMOVE_ERROR_ENTRY_CLIENT_PROPERTY_KEY, // clientPropertyKey
                            Integer.valueOf( rowIndex )             // clientPropertyValue
                            );
                }

                return contextMenu;
            }
        };
    }

    private void removeErrorEntry( final ActionEvent actionEvent )
    {
        // TODO Auto-generated method stub

    }

    protected void prepareScan()
    {
        getjProgressBarFiles().setIndeterminate( true );
        jProgressBarOctets.setIndeterminate( true );

        clearCurrentFiles();
        setCurrentDir( getTxtCurrentDir() );

        getAppToolKit().setEnabledJButtonCancel( true );

        //tableModelErrorList.setRowCount( 0 );
        clearErrors();
    }

    protected void clearErrors()
    {
        tableModelErrorList.clear();
    }

    protected ErrorTableModel getTableModelErrorList()
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

//    public void clear()
//    {
//        while( tableModelErrorList.getRowCount() > 0 ) {
//            tableModelErrorList.removeRow( 0 );
//        }
//    }
}
