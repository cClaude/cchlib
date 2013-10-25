package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import com.googlecode.cchlib.apps.duplicatefiles.Resources;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector.DuplicateData;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector.SelectorComboBox;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector.SelectorsJPanel;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.DividersLocation;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 */
@I18nName("JPanelResult")
public abstract class JPanelResultWB extends JPanel implements DuplicateData
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( JPanelResultWB.class );
    private Resources resources;

    private JTextField jTextFieldFileInfo;
    private JPanelResultListModel listModelDuplicatesFiles;
    private JSplitPane jSplitPaneResultMain;
    private JSplitPane jSplitPaneResultRight;
    private JList<KeyFileState> jListKeptIntact;
    private JList<KeyFileState> jListWillBeDeleted;
    private JList<KeyFiles> jListDuplicatesFiles;
    @I18nIgnore @I18nToolTipText private JButton jButtonPrevSet;
    @I18nIgnore @I18nToolTipText private JButton refreshButton;
    @I18nIgnore @I18nToolTipText private JButton jButtonNextSet;
    //private SelectorComboBox jComboBoxSelectMode;
    private SelectorsJPanel selectorsJPanel;

    /**
     * @param autoI18n
     * @param dfToolKit
     *
     */
    public JPanelResultWB( /*final AutoI18nBasicInterface autoI18n,*/ final DFToolKit dfToolKit )
    {
        this.resources = dfToolKit.getResources();

        setSize(488, 240);

        listModelDuplicatesFiles = new JPanelResultListModel();

        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{32, 0, 0};
            gridBagLayout.rowHeights = new int[]{25, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);
        }
        {
            GridBagConstraints gbc_jTextFieldFileInfo = new GridBagConstraints();
            gbc_jTextFieldFileInfo.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextFieldFileInfo.insets = new Insets(0, 0, 5, 0);
            gbc_jTextFieldFileInfo.gridx = 1;
            gbc_jTextFieldFileInfo.gridy = 0;

            jTextFieldFileInfo = new JTextField();
            jTextFieldFileInfo.setEditable( false );
            jTextFieldFileInfo.setHorizontalAlignment( JTextField.CENTER );
            add(jTextFieldFileInfo, gbc_jTextFieldFileInfo);
        }
        {
            GridBagConstraints gbc_jSplitPaneResultMain = new GridBagConstraints();
            gbc_jSplitPaneResultMain.gridwidth = 2;
            gbc_jSplitPaneResultMain.fill = GridBagConstraints.BOTH;
            gbc_jSplitPaneResultMain.insets = new Insets(0, 0, 5, 0);
            gbc_jSplitPaneResultMain.gridx = 0;
            gbc_jSplitPaneResultMain.gridy = 1;
            add(getJSplitPaneResultMain(), gbc_jSplitPaneResultMain);
        }
        {
            this.selectorsJPanel = new SelectorsJPanel(dfToolKit, this);
            GridBagConstraints gbc_selectorsJPanel = new GridBagConstraints();
            gbc_selectorsJPanel.fill = GridBagConstraints.BOTH;
            gbc_selectorsJPanel.gridx = 1;
            gbc_selectorsJPanel.gridy = 2;
            add(this.selectorsJPanel, gbc_selectorsJPanel);
        }
        {
            SelectorComboBox jComboBoxSelectMode = this.selectorsJPanel.getSelectorComboBox();
            GridBagConstraints gbc_jComboBoxSelectMode = new GridBagConstraints();
            gbc_jComboBoxSelectMode.insets = new Insets(0, 0, 0, 5);
            gbc_jComboBoxSelectMode.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxSelectMode.gridx = 0;
            gbc_jComboBoxSelectMode.gridy = 2;
            add(jComboBoxSelectMode, gbc_jComboBoxSelectMode);
        }
        {
            jListDuplicatesFiles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
            jListDuplicatesFiles.setModel( listModelDuplicatesFiles );
            jListDuplicatesFiles.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged( ListSelectionEvent event )
                    {
                        logger.info( "valueChanged: " + event );

                        if( ! event.getValueIsAdjusting() ) {
                            int i = jListDuplicatesFiles.getSelectedIndex();

                            updateDisplayKeptDelete( i );
                            }
//                        if( i >= 0 ) {
//                            //KeyFiles kf = (KeyFiles)listModelDuplicatesFiles.get( i );
//                            KeyFiles kf = listModelDuplicatesFiles.getElementAt( i );
//                            String   k = kf.getKey();
    //
//                            updateDisplayKeptDelete( k );
//                            }
                    }
                } );
        }
    }

    protected JTextField getJTextFieldFileInfo()
    {
        return jTextFieldFileInfo;
    }
//    protected JToggleButton getJToggleButtonSelectByRegEx()
//    {
//        return jToggleButtonSelectByRegEx;
//    }
//    private XComboBoxPattern getXComboBoxPatternRegEx()
//    {
//        return xComboBoxPatternRegEx;
//    }
//    private JCheckBox getJCheckBoxKeepOne()
//    {
//        return jCheckBoxKeepOne;
//    }
//    private JButton getJButtonRegExDelete()
//    {
//        return jButtonRegExDelete;
//    }
//    private JButton getJButtonRegExKeep()
//    {
//        return jButtonRegExKeep;
//    }
    protected JList<KeyFileState> getJListKeptIntact()
    {
        return jListKeptIntact;
    }
    protected JList<KeyFileState> getJListWillBeDeleted()
    {
        return jListWillBeDeleted;
    }

    private JSplitPane getJSplitPaneResultMain()
    {
        if (jSplitPaneResultMain == null) {
            jSplitPaneResultMain = new JSplitPane();

            JPanel leftPanel = new JPanel();

            jSplitPaneResultMain.setLeftComponent( leftPanel );
            GridBagLayout gbl_leftPanel = new GridBagLayout();
            gbl_leftPanel.columnWidths = new int[]{32, 32, 32, 0};
            gbl_leftPanel.rowHeights = new int[]{24, 20, 0};
            gbl_leftPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
            gbl_leftPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            leftPanel.setLayout(gbl_leftPanel);
            {
                GridBagConstraints gbc_jButtonPrevSet = new GridBagConstraints();
                gbc_jButtonPrevSet.fill = GridBagConstraints.BOTH;
                gbc_jButtonPrevSet.insets = new Insets(0, 0, 5, 5);
                gbc_jButtonPrevSet.gridx = 0;
                gbc_jButtonPrevSet.gridy = 0;

                this.jButtonPrevSet = new JButton( resources.getPrevIcon() );
                this.jButtonPrevSet.setToolTipText( "jButtonPrevSet description" );
                this.jButtonPrevSet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onPrevSet();
                    }
                });
                leftPanel.add(this.jButtonPrevSet, gbc_jButtonPrevSet);
            }
            {
                this.refreshButton = new JButton( resources.getRefreshIcon() );
                this.refreshButton.setToolTipText( "Refresh file list (remove deleted entries from an other process)" );
                this.refreshButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onRefresh();
                    }
                });
                GridBagConstraints gbc_refreshButton = new GridBagConstraints();
                gbc_refreshButton.fill = GridBagConstraints.BOTH;
                gbc_refreshButton.insets = new Insets(0, 0, 5, 5);
                gbc_refreshButton.gridx = 1;
                gbc_refreshButton.gridy = 0;
                leftPanel.add(this.refreshButton, gbc_refreshButton);
            }
            {
                GridBagConstraints gbc_jButtonNextSet = new GridBagConstraints();
                gbc_jButtonNextSet.fill = GridBagConstraints.BOTH;
                gbc_jButtonNextSet.insets = new Insets(0, 0, 5, 5);
                gbc_jButtonNextSet.gridx = 2;
                gbc_jButtonNextSet.gridy = 0;

                this.jButtonNextSet = new JButton( resources.getNextIcon() );
                this.jButtonNextSet.setToolTipText( "jButtonNextSet description" );
                this.jButtonNextSet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onNextSet();
                    }
                });
                leftPanel.add(this.jButtonNextSet, gbc_jButtonNextSet);
            }
            {
                JScrollPane jScrollPaneDuplicatesFiles = new JScrollPane();
                jScrollPaneDuplicatesFiles.setViewportView(getJListDuplicatesFiles());
                GridBagConstraints gbc_panel = new GridBagConstraints();
                gbc_panel.fill = GridBagConstraints.BOTH;
                gbc_panel.gridwidth = 3;
                gbc_panel.gridx = 0;
                gbc_panel.gridy = 1;
                leftPanel.add(jScrollPaneDuplicatesFiles, gbc_panel);
            }
            jSplitPaneResultMain.setRightComponent(getJSplitPaneResultRight());
            }
        return jSplitPaneResultMain;
    }

    private JSplitPane getJSplitPaneResultRight()
    {
        if (jSplitPaneResultRight == null) {
            jSplitPaneResultRight = new JSplitPane();
            jSplitPaneResultRight.setDividerLocation( 0.5 ); // Proportional
            jSplitPaneResultRight.setOrientation(JSplitPane.VERTICAL_SPLIT);

            JScrollPane jScrollPaneKeptIntact = new JScrollPane();
            final KeyFileStateListModel jListKeptIntactListModel = listModelDuplicatesFiles.getKeptIntactListModel();
            jListKeptIntact = new JList<KeyFileState>();
            this.jListKeptIntact.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jListKeptIntact.setModel( jListKeptIntactListModel );
            jListKeptIntact.setCellRenderer( listModelDuplicatesFiles.getKeptIntactListCellRenderer() );

            jListKeptIntact.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent e )
                {
                    if( e.getClickCount() > 0 ) {
                        jListWillBeDeleted.clearSelection();
                        int index = jListKeptIntact.locationToIndex( e.getPoint() );

                        if( index >= 0 ) {
                            KeyFileState kf = jListKeptIntactListModel.getElementAt( index );

                            displayFileInfo( kf );
                            }
                        }
                    if( e.getClickCount() == 2 ) { // Double-click
                        int index = jListKeptIntact.locationToIndex( e.getPoint() );

                        if( index >= 0 ) {
                            KeyFileState kf = jListKeptIntactListModel.remove( index );

                            onDeleteThisFile( kf, true );
                            }
                        }
                }
            } );
            jScrollPaneKeptIntact.setViewportView( jListKeptIntact );
            jSplitPaneResultRight.setTopComponent( jScrollPaneKeptIntact );

            JScrollPane jScrollPaneWillBeDeleted = new JScrollPane();
            jListWillBeDeleted = new JList<KeyFileState>();
            this.jListWillBeDeleted.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            final KeyFileStateListModel jListWillBeDeletedListModel = listModelDuplicatesFiles.getWillBeDeletedListModel();
            jListWillBeDeleted.setModel( jListWillBeDeletedListModel );
            jListWillBeDeleted.setCellRenderer( listModelDuplicatesFiles.getKeptIntactListCellRenderer() );
            jListWillBeDeleted.addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent e )
                {
                    if( e.getClickCount() > 0 ) {
                        jListKeptIntact.clearSelection();
                        int index = jListWillBeDeleted.locationToIndex( e.getPoint() );

                        if( index >= 0 ) {
                            KeyFileState kf = jListWillBeDeletedListModel.getElementAt( index );

                            displayFileInfo( kf );
                            }
                        }
                    if( e.getClickCount() == 2 ) { // Double-click
                        int index = jListWillBeDeleted.locationToIndex( e.getPoint() );

                        if( index >= 0 ) {
                            KeyFileState kf = jListWillBeDeletedListModel.remove( index );

                            onKeepThisFile( kf, true );
                            }
                        }
                }
            });
            jScrollPaneWillBeDeleted.setViewportView( jListWillBeDeleted );
            jSplitPaneResultRight.setBottomComponent( jScrollPaneWillBeDeleted );
            }
        return jSplitPaneResultRight;
    }

    protected JList<KeyFiles> getJListDuplicatesFiles()
    {
        if (jListDuplicatesFiles == null) {
            jListDuplicatesFiles = new JList<KeyFiles>();
            jListDuplicatesFiles.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if( e.getClickCount() == 2 ) {
                        // TODO: move current file to other list !
                        }
                    }
                });
            }
        return jListDuplicatesFiles;
    }

    @Override
    public JPanelResultListModel getListModelDuplicatesFiles()
    {
        return listModelDuplicatesFiles;
    }

    protected void setDividersLocation(
        final DividersLocation dividersLocation
        )
    {
        Integer mainDividerLocation = dividersLocation.getMainDividerLocation();
        if( mainDividerLocation != null ) {
            this.jSplitPaneResultMain.setDividerLocation( mainDividerLocation.intValue() );
            }
        else {
            jSplitPaneResultMain.setDividerLocation( 0.10 ); // Proportional
            }

        Integer rightDividerLocation = dividersLocation.getRightDividerLocation();
        if( rightDividerLocation != null ) {
            this.jSplitPaneResultRight.setDividerLocation( rightDividerLocation.intValue() );
            }
        else {
            jSplitPaneResultRight.setDividerLocation( 0.50 ); // Proportional
            }
    }

//    protected SelectorComboBox _getSelectorComboBox()
//    {
//        return this.jComboBoxSelectMode;
//    }

    protected SelectorsJPanel getSelectorsJPanel()
    {
        return this.selectorsJPanel;
    }

    protected abstract void onNextSet();
    protected abstract void onPrevSet();
    protected abstract void onRefresh();

    protected abstract void updateDisplayKeptDelete(int index);
    protected abstract void onKeepThisFile(KeyFileState kf, boolean updateDisplay);
    protected abstract void onDeleteThisFile(KeyFileState kf, boolean updateDisplay) ;
    protected abstract void displayFileInfo(KeyFileState kf);
}
