package com.googlecode.cchlib.tools.downloader;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.tools.downloader.comboconfig.GenericDownloaderAppComboBoxConfig;
import com.googlecode.cchlib.tools.downloader.common.DownloaderData;
import com.googlecode.cchlib.tools.downloader.common.DownloaderHandler;

public class GenericDownloaderUIPanel
    extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel pageScanCountJLabel;
    @I18nString private final String numberOfPicturesByPageTxt = "%d picture(s) by page";
    private JSpinner pageScanCountJSpinner;
    private SpinnerNumberModel pageScanCountSpinnerModel;
    private final List<GenericDownloaderUIPanelEntry> entryJPanelList = new ArrayList<GenericDownloaderUIPanelEntry>();

    //private final GenericDownloaderAppInterface config;
    private final DownloaderData    data;
    private final DownloaderHandler handler;

    /**
     * Create the panel.
     */
    @SuppressWarnings({"squid:S00117"})
    public GenericDownloaderUIPanel(
        final DownloaderData      data,
        final DownloaderHandler   handler
        )
    {
        this.data    = data;
        this.handler = handler;

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 50, 50, 0};
        gridBagLayout.rowHeights = new int[]{0, 14, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            final JLabel sitenameJLabel = new JLabel( this.data.getSiteName() );
            sitenameJLabel.setHorizontalAlignment(SwingConstants.CENTER);
            final GridBagConstraints gbc_sitenameJLabel = new GridBagConstraints();
            gbc_sitenameJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_sitenameJLabel.gridwidth = 3;
            gbc_sitenameJLabel.insets = new Insets(0, 0, 5, 0);
            gbc_sitenameJLabel.gridx = 0;
            gbc_sitenameJLabel.gridy = 0;
            add(sitenameJLabel, gbc_sitenameJLabel);
        }
        {
            this.pageScanCountJLabel = new JLabel( "Pages to scan :" );
            this.pageScanCountJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            final GridBagConstraints gbc_pageScanCountJLabel = new GridBagConstraints();
            gbc_pageScanCountJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_pageScanCountJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_pageScanCountJLabel.anchor = GridBagConstraints.NORTH;
            gbc_pageScanCountJLabel.gridx = 0;
            gbc_pageScanCountJLabel.gridy = 1;
            add(this.pageScanCountJLabel, gbc_pageScanCountJLabel);
        }
        {
            this.pageScanCountSpinnerModel
                = new SpinnerNumberModel(
                        this.data.getPageCount(),
                        1,
                        this.data.getMaxPageCount(),
                        1
                        );
            this.pageScanCountJSpinner = new JSpinner( this.pageScanCountSpinnerModel );
            this.pageScanCountJSpinner.addChangeListener(
                    event -> this.data.setPageCount(
                            this.pageScanCountSpinnerModel.getNumber().intValue()
                        )
                    );

            final GridBagConstraints gbc_pageScanCountJSpinner = new GridBagConstraints();
            gbc_pageScanCountJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_pageScanCountJSpinner.insets = new Insets(0, 0, 5, 5);
            gbc_pageScanCountJSpinner.gridx = 1;
            gbc_pageScanCountJSpinner.gridy = 1;
            add(this.pageScanCountJSpinner, gbc_pageScanCountJSpinner);
        }
        {
            final JLabel numberOfPicturesByPageLabel = new JLabel();
            numberOfPicturesByPageLabel.setText(
                    String.format(
                            this.numberOfPicturesByPageTxt,
                            Integer.valueOf( this.data.getNumberOfPicturesByPage() )
                            )
                    );
            final GridBagConstraints gbc_numberOfPicturesByPageLabel = new GridBagConstraints();
            gbc_numberOfPicturesByPageLabel.insets = new Insets(0, 0, 5, 0);
            gbc_numberOfPicturesByPageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfPicturesByPageLabel.gridx = 2;
            gbc_numberOfPicturesByPageLabel.gridy = 1;
            add(numberOfPicturesByPageLabel, gbc_numberOfPicturesByPageLabel);
        }

        int lineNumber = 0;
        // $hide>>$
        for( final GenericDownloaderAppComboBoxConfig entry : this.handler.getComboBoxConfigCollection() )
        // $hide<<$
        {
            final GenericDownloaderUIPanelEntry entryJPanel = new GenericDownloaderUIPanelEntry( e -> this.handler.doSelectedItems( getSelectedItems() ));
            final GridBagConstraints gbc_entryJPanel = new GridBagConstraints();
            gbc_entryJPanel.gridwidth = 3;
            //gbc_entryJPanel.insets = new Insets(0, 0, 0, 0);
            gbc_entryJPanel.fill = GridBagConstraints.BOTH;
            gbc_entryJPanel.gridx = 0;
            gbc_entryJPanel.gridy = 2 + lineNumber;
            add(entryJPanel, gbc_entryJPanel);

            entryJPanel.setDescription( entry.getDescription() );
            entryJPanel.setJComboBoxEntry( entry.getJComboBoxEntry() );
            this.entryJPanelList.add( entryJPanel );

            lineNumber++;
        } // for(...)

        /* / $hide>>$
        stringsJComboBoxList = new ArrayList<JComboBox<String>>();
        for( final GenericDownloaderAppInterface.ComboBoxConfig entry : config.getComboBoxConfigCollection() )
        // $hide<<$
        {
            {
                JLabel stringsJLabel = new JLabel( entry.getLabelString() );
                stringsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                GridBagConstraints gbc_stringsJLabel = new GridBagConstraints();
                gbc_stringsJLabel.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJLabel.insets = new Insets(0, 0, 5, 5);
                gbc_stringsJLabel.gridx = 0;
                gbc_stringsJLabel.gridy = 2 + lineNumber;
                add(stringsJLabel, gbc_stringsJLabel);
            }
            final JLabel stringsJLabels;
            {
                stringsJLabels = new JLabel( entry.getLabelString( 0 ) );
                GridBagConstraints gbc_stringsJLabels = new GridBagConstraints();
                gbc_stringsJLabels.insets = new Insets(0, 0, 5, 0);
                gbc_stringsJLabels.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJLabels.gridx = 2;
                gbc_stringsJLabels.gridy = 2 + lineNumber;
                add(stringsJLabels, gbc_stringsJLabels);
            }
            {
                final JComboBox<String> stringsJComboBox = new JComboBox<>();
                stringsJComboBoxList.add( stringsJComboBox );
                stringsJComboBox.addItemListener(new ItemListener() {
                    public void itemStateChanged( ItemEvent event )
                    {
                        final int index = stringsJComboBox.getSelectedIndex();

                        stringsJLabels.setText( entry.getLabelString( index ) );
                        entry.setSelectedIndex( index );
                     }
                });

                for( String s : entry.getComboBoxValues() ) {
                    stringsJComboBox.addItem( s );
                    }

                GridBagConstraints gbc_stringsJComboBox = new GridBagConstraints();
                gbc_stringsJComboBox.insets = new Insets(0, 0, 5, 5);
                gbc_stringsJComboBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJComboBox.gridx = 1;
                gbc_stringsJComboBox.gridy = 2 + lineNumber;
                add(stringsJComboBox, gbc_stringsJComboBox);

                lineNumber++;
            }
        } // for(...)*/

        // $hide>>$
        final GenericDownloaderAppButton button = this.handler.getButtonConfig();
        if( button != null )
        // $hide<<$
        {
            final JButton jButton = new JButton( button.getLabel() );
            jButton.addActionListener(event -> button.onClick());
            final GridBagConstraints gbc_jButton = new GridBagConstraints();
            gbc_jButton.insets = new Insets(0, 0, 0, 5);
            gbc_jButton.gridx = 1;
            gbc_jButton.gridy = 4;
            add(jButton, gbc_jButton);
        }
    }

//    public GenericDownloaderAppInterface getGenericDownloaderAppInterface()
//    {
//        return this.config;
//    }
    public DownloaderData getDownloaderData()
    {
        return this.data;
    }

    public DownloaderHandler getDownloaderHandler()
    {
        return this.handler;
    }

    public void setReadOnly( final boolean isReadOnly )
    {
        final boolean enabled = ! isReadOnly;

        this.pageScanCountJSpinner.setEnabled( enabled  );
    }

    public void setEnabledAllComponents( final boolean enabled )
    {
        this.pageScanCountJSpinner.setEnabled( enabled );

//        for( JComboBox<String> jcb : stringsJComboBoxList ) {
//            jcb.setEnabled( enabled );
//            }

        for( final GenericDownloaderUIPanelEntry entry : this.entryJPanelList ) {
            entry.setEnabledAllComponents( enabled );
            }
    }

    private List<GenericDownloaderUIPanelEntry.Item> getSelectedItems()
    {
        final List<GenericDownloaderUIPanelEntry.Item> selectedItems = new ArrayList<>();

        for( final GenericDownloaderUIPanelEntry p : this.entryJPanelList ) {
            selectedItems.add( p.getSelectedItem() );
            }

        return selectedItems;
    }

}

