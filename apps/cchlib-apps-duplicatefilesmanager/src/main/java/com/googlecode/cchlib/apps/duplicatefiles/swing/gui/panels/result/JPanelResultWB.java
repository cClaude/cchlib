package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFiles;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector.DuplicateData;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector.SelectorComboBox;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector.SelectorsJPanel;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.DividersLocation;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Resources;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.swing.JSplitPane.JSplitPanes;

@I18nName("JPanelResult")
@SuppressWarnings({ // Generated code
    "squid:S00117", // Naming conventions
    "squid:S1199" // Nested blocks
    })
public abstract class JPanelResultWB extends JPanel implements DuplicateData
{
    private abstract class ClickedOnFileListsMouseAdapter extends MouseAdapter
    {
        private final KeyFileStateListModel fromJListModel;

        private ClickedOnFileListsMouseAdapter(
            final KeyFileStateListModel fromJListModel
            )
        {
            this.fromJListModel = fromJListModel;
        }

        @Override
        public void mouseClicked( final MouseEvent event )
        {
            if( event.getClickCount() > 0 ) {
                getToJList().clearSelection();
                final int index = getFromJList().locationToIndex( event.getPoint() );

                if( index >= 0 ) {
                    final KeyFileState kf = this.fromJListModel.getElementAt( index );

                    displayFileInfo( kf );
                    }
                }
            if( event.getClickCount() == 2 ) { // Double-click
                final int index = getFromJList().locationToIndex( event.getPoint() );

                if( index >= 0 ) {
                    final KeyFileState kf = this.fromJListModel.remove( index );

                    doAction( kf, true );
                    }
                }
        }

        abstract JList<KeyFileState> getFromJList();
        abstract JList<KeyFileState> getToJList();
        abstract void doAction( KeyFileState kf, boolean b );
    }
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelResultWB.class );

    private transient Resources resources;

    private final JTextField jTextFieldFileInfo;
    private final JPanelResultListModelImpl listModelDuplicatesFiles;
    private JSplitPane jSplitPaneResultMain;
    private JSplitPane jSplitPaneResultRight;
    private JList<KeyFileState> jListKeptIntact;
    private JList<KeyFileState> jListWillBeDeleted;
    private JList<KeyFiles> jListDuplicatesFiles;
    @I18nIgnore @I18nToolTipText private JButton jButtonPrevSet;
    @I18nIgnore @I18nToolTipText private JButton refreshButton;
    @I18nIgnore @I18nToolTipText private JButton jButtonNextSet;
    private final SelectorsJPanel selectorsJPanel;

    public JPanelResultWB()
    {
        setSize(488, 240);

        this.listModelDuplicatesFiles = new JPanelResultListModelImpl();

        {
            final GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{32, 0, 0};
            gridBagLayout.rowHeights = new int[]{25, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            final GridBagConstraints gbc_jTextFieldFileInfo = new GridBagConstraints();
            gbc_jTextFieldFileInfo.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextFieldFileInfo.insets = new Insets(0, 0, 5, 0);
            gbc_jTextFieldFileInfo.gridx = 1;
            gbc_jTextFieldFileInfo.gridy = 0;

            this.jTextFieldFileInfo = new JTextField();
            this.jTextFieldFileInfo.setEditable( false );
            this.jTextFieldFileInfo.setHorizontalAlignment( SwingConstants.CENTER );
            add(this.jTextFieldFileInfo, gbc_jTextFieldFileInfo);
        }
        {
            final GridBagConstraints gbc_jSplitPaneResultMain = new GridBagConstraints();
            gbc_jSplitPaneResultMain.gridwidth = 2;
            gbc_jSplitPaneResultMain.fill = GridBagConstraints.BOTH;
            gbc_jSplitPaneResultMain.insets = new Insets(0, 0, 5, 0);
            gbc_jSplitPaneResultMain.gridx = 0;
            gbc_jSplitPaneResultMain.gridy = 1;
            add(getJSplitPaneResultMain(), gbc_jSplitPaneResultMain);
        }
        {
            this.selectorsJPanel = new SelectorsJPanel( this );
            final GridBagConstraints gbc_selectorsJPanel = new GridBagConstraints();
            gbc_selectorsJPanel.fill = GridBagConstraints.BOTH;
            gbc_selectorsJPanel.gridx = 1;
            gbc_selectorsJPanel.gridy = 2;
            add(this.selectorsJPanel, gbc_selectorsJPanel);
        }
        {
            final SelectorComboBox jComboBoxSelectMode = this.selectorsJPanel.getSelectorComboBox();
            final GridBagConstraints gbc_jComboBoxSelectMode = new GridBagConstraints();
            gbc_jComboBoxSelectMode.insets = new Insets(0, 0, 0, 5);
            gbc_jComboBoxSelectMode.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxSelectMode.gridx = 0;
            gbc_jComboBoxSelectMode.gridy = 2;
            add(jComboBoxSelectMode, gbc_jComboBoxSelectMode);
        }
        {
            this.jListDuplicatesFiles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
            this.jListDuplicatesFiles.setModel( this.listModelDuplicatesFiles );
            this.jListDuplicatesFiles.addListSelectionListener(
                (final ListSelectionEvent event) -> {
                    LOGGER.info( "valueChanged: " + event );

                    if( ! event.getValueIsAdjusting() ) {
                        final int i = this.jListDuplicatesFiles.getSelectedIndex();

                        updateDisplayKeptDelete( i );
                    }
            });
        }
    }

    private Resources getResources()
    {
        if( this.resources == null ) {
            this.resources = AppToolKitService.getInstance().getAppToolKit().getResources();
        }
        return this.resources;
    }

    protected JTextField getJTextFieldFileInfo()
    {
        return this.jTextFieldFileInfo;
    }

    protected JList<KeyFileState> getJListKeptIntact()
    {
        return this.jListKeptIntact;
    }
    protected JList<KeyFileState> getJListWillBeDeleted()
    {
        return this.jListWillBeDeleted;
    }

    private JSplitPane getJSplitPaneResultMain()
    {
        if( this.jSplitPaneResultMain == null ) {
            this.jSplitPaneResultMain = new JSplitPane();

            final JPanel leftPanel = new JPanel();

            this.jSplitPaneResultMain.setLeftComponent( leftPanel );
            final GridBagLayout gbl_leftPanel = new GridBagLayout();
            gbl_leftPanel.columnWidths = new int[]{64, 64, 64, 0};
            gbl_leftPanel.rowHeights = new int[]{24, 20, 0};
            gbl_leftPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
            gbl_leftPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            leftPanel.setLayout(gbl_leftPanel);
            {
                final GridBagConstraints gbc_jButtonPrevSet = new GridBagConstraints();
                gbc_jButtonPrevSet.fill = GridBagConstraints.BOTH;
                gbc_jButtonPrevSet.insets = new Insets(0, 0, 5, 5);
                gbc_jButtonPrevSet.gridx = 0;
                gbc_jButtonPrevSet.gridy = 0;

                this.jButtonPrevSet = new JButton( getResources().getPrevIcon() );
                this.jButtonPrevSet.setToolTipText( "jButtonPrevSet description" );
                this.jButtonPrevSet.addActionListener( e -> onPrevSet() );

                leftPanel.add(this.jButtonPrevSet, gbc_jButtonPrevSet);
            }
            {
                this.refreshButton = new JButton( getResources().getRefreshIcon() );
                this.refreshButton.setToolTipText(
                        "Refresh file list (remove deleted entries from an other process)"
                        );
                this.refreshButton.addActionListener( e -> onRefresh() );

                final GridBagConstraints gbc_refreshButton = new GridBagConstraints();
                gbc_refreshButton.fill = GridBagConstraints.BOTH;
                gbc_refreshButton.insets = new Insets(0, 0, 5, 5);
                gbc_refreshButton.gridx = 1;
                gbc_refreshButton.gridy = 0;
                leftPanel.add(this.refreshButton, gbc_refreshButton);
            }
            {
                final GridBagConstraints gbc_jButtonNextSet = new GridBagConstraints();
                gbc_jButtonNextSet.fill = GridBagConstraints.BOTH;
                gbc_jButtonNextSet.insets = new Insets(0, 0, 5, 5);
                gbc_jButtonNextSet.gridx = 2;
                gbc_jButtonNextSet.gridy = 0;

                this.jButtonNextSet = new JButton( getResources().getNextIcon() );
                this.jButtonNextSet.setToolTipText( "jButtonNextSet description" );
                this.jButtonNextSet.addActionListener( e -> onNextSet() );

                leftPanel.add(this.jButtonNextSet, gbc_jButtonNextSet);
            }
            {
                final JScrollPane jScrollPaneDuplicatesFiles = new JScrollPane();
                jScrollPaneDuplicatesFiles.setViewportView(getJListDuplicatesFiles());
                final GridBagConstraints gbc_panel = new GridBagConstraints();
                gbc_panel.fill = GridBagConstraints.BOTH;
                gbc_panel.gridwidth = 3;
                gbc_panel.gridx = 0;
                gbc_panel.gridy = 1;
                leftPanel.add(jScrollPaneDuplicatesFiles, gbc_panel);
            }
            this.jSplitPaneResultMain.setRightComponent(getJSplitPaneResultRight());
        }
        return this.jSplitPaneResultMain;
    }

    private JSplitPane getJSplitPaneResultRight()
    {
        if( this.jSplitPaneResultRight == null ) {
            this.jSplitPaneResultRight = new JSplitPane();
            this.jSplitPaneResultRight.setDividerLocation( 0.5 ); // Proportional
            this.jSplitPaneResultRight.setOrientation( JSplitPane.VERTICAL_SPLIT );

            final JScrollPane jScrollPaneKeptIntact = new JScrollPane();
            final KeyFileStateListModel jListKeptIntactListModel = this.listModelDuplicatesFiles.getKeptIntactListModel();
            this.jListKeptIntact = new JList<>();
            this.jListKeptIntact.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
            this.jListKeptIntact.setModel( jListKeptIntactListModel );
            this.jListKeptIntact.setCellRenderer( this.listModelDuplicatesFiles.getKeptIntactListCellRenderer() );

            this.jListKeptIntact.addMouseListener(
                new ClickedOnFileListsMouseAdapter( jListKeptIntactListModel ) {
                    @Override
                    void doAction( final KeyFileState kf, final boolean b )
                    {
                        onDeleteThisFile( kf, true );
                    }
                    @Override
                    JList<KeyFileState> getToJList()
                    {
                        return JPanelResultWB.this.jListWillBeDeleted;
                    }
                    @Override
                    JList<KeyFileState> getFromJList()
                    {
                        return JPanelResultWB.this.jListKeptIntact;
                    }} );
            jScrollPaneKeptIntact.setViewportView( this.jListKeptIntact );
            this.jSplitPaneResultRight.setTopComponent( jScrollPaneKeptIntact );

            final JScrollPane jScrollPaneWillBeDeleted = new JScrollPane();
            this.jListWillBeDeleted = new JList<>();
            this.jListWillBeDeleted.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            final KeyFileStateListModel jListWillBeDeletedListModel = this.listModelDuplicatesFiles.getWillBeDeletedListModel();
            this.jListWillBeDeleted.setModel( jListWillBeDeletedListModel );
            this.jListWillBeDeleted.setCellRenderer( this.listModelDuplicatesFiles.getKeptIntactListCellRenderer() );
            this.jListWillBeDeleted.addMouseListener(
                    new ClickedOnFileListsMouseAdapter( jListWillBeDeletedListModel ) {
                        @Override
                        void doAction( final KeyFileState kf, final boolean b )
                        {
                            onKeepThisFile( kf, true );
                        }
                        @Override
                        JList<KeyFileState> getToJList()
                        {
                            return JPanelResultWB.this.jListKeptIntact;
                        }
                        @Override
                        JList<KeyFileState> getFromJList()
                        {
                            return JPanelResultWB.this.jListWillBeDeleted;
                        }});
            jScrollPaneWillBeDeleted.setViewportView( this.jListWillBeDeleted );
            this.jSplitPaneResultRight.setBottomComponent( jScrollPaneWillBeDeleted );
            }
        return this.jSplitPaneResultRight;
    }

    protected JList<KeyFiles> getJListDuplicatesFiles()
    {
        if (this.jListDuplicatesFiles == null) {
            this.jListDuplicatesFiles = new JList<>();
            this.jListDuplicatesFiles.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if( e.getClickCount() == 2 ) {
                        // TODO: move current file to other list !
                        }
                    }
                });
            }
        return this.jListDuplicatesFiles;
    }

    @Override
    public JPanelResultListModel getListModelDuplicatesFiles()
    {
        return this.listModelDuplicatesFiles;
    }

    protected void setDividersLocation(
        final DividersLocation dividersLocation
        )
    {
        final Integer mainDividerLocation = dividersLocation.getMainDividerLocation();

        if( mainDividerLocation != null ) {
            this.jSplitPaneResultMain.setDividerLocation( mainDividerLocation.intValue() );
            }
        else {
            JSplitPanes.setJSplitPaneDividerLocation( this.jSplitPaneResultMain, 0.10 ); // Proportional
            }

        final Integer rightDividerLocation = dividersLocation.getRightDividerLocation();
        if( rightDividerLocation != null ) {
            this.jSplitPaneResultRight.setDividerLocation( rightDividerLocation.intValue() );
            }
        else {
            JSplitPanes.setJSplitPaneDividerLocation( this.jSplitPaneResultRight, 0.50 );// Proportional
            }
    }

    protected SelectorsJPanel getSelectorsJPanel()
    {
        return this.selectorsJPanel;
    }

    protected final void disableAllWidgets()
    {
        getSelectorsJPanel().disableAllWidgets();

        this.jButtonPrevSet.setEnabled( false );
        this.jButtonNextSet.setEnabled( false );
        this.refreshButton.setEnabled( false );

        this.jTextFieldFileInfo.setText( "****** REFRESH *****" ); // TODO Localization

        this.jListDuplicatesFiles.setEnabled( false );
        this.jListKeptIntact.setEnabled( false );
        this.jListWillBeDeleted.setEnabled( false );

        // TODO : disable all widgets
    }

    protected final void enableAllWidgets()
    {
        getSelectorsJPanel().enableAllWidgets();

        this.jButtonPrevSet.setEnabled( true );
        this.jButtonNextSet.setEnabled( true );
        this.refreshButton.setEnabled( true );

        this.jTextFieldFileInfo.setText( "-" );

        this.jListDuplicatesFiles.setEnabled( true );
        this.jListKeptIntact.setEnabled( true );
        this.jListWillBeDeleted.setEnabled( true );

        // TODO : enable all widgets
    }

    protected abstract void onNextSet();
    protected abstract void onPrevSet();
    protected abstract void onRefresh();

    protected abstract void updateDisplayKeptDelete(int index);
    protected abstract void onKeepThisFile(KeyFileState kf, boolean updateDisplay);
    protected abstract void onDeleteThisFile(KeyFileState kf, boolean updateDisplay) ;
    protected abstract void displayFileInfo(KeyFileState kf);
}
