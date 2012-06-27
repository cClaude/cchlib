package samples.downloader;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import com.googlecode.cchlib.i18n.I18nString;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GenericDownloaderUIPanel
    extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel pageScanCountJLabel;
    @I18nString private String numberOfPicturesByPageTxt = "%d picture(s) by page";
    private JSpinner pageScanCountJSpinner;
    private GenericDownloaderAppInterface config;
    private SpinnerNumberModel pageScanCountSpinnerModel;

    /**
     * Create the panel.
     */
    public GenericDownloaderUIPanel(
        final GenericDownloaderAppInterface config
        )
    {
        this.config = config;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 50, 50, 0};
        gridBagLayout.rowHeights = new int[]{0, 14, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            JLabel sitenameJLabel = new JLabel( config.getSiteName() );
            sitenameJLabel.setHorizontalAlignment(SwingConstants.CENTER);
            GridBagConstraints gbc_sitenameJLabel = new GridBagConstraints();
            gbc_sitenameJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_sitenameJLabel.gridwidth = 3;
            gbc_sitenameJLabel.insets = new Insets(0, 0, 5, 0);
            gbc_sitenameJLabel.gridx = 0;
            gbc_sitenameJLabel.gridy = 0;
            add(sitenameJLabel, gbc_sitenameJLabel);
        }
        {
            pageScanCountJLabel = new JLabel( "Pages to scan :" );
            pageScanCountJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            GridBagConstraints gbc_pageScanCountJLabel = new GridBagConstraints();
            gbc_pageScanCountJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_pageScanCountJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_pageScanCountJLabel.anchor = GridBagConstraints.NORTH;
            gbc_pageScanCountJLabel.gridx = 0;
            gbc_pageScanCountJLabel.gridy = 1;
            add(pageScanCountJLabel, gbc_pageScanCountJLabel);
        }
        {
            pageScanCountSpinnerModel
                = new SpinnerNumberModel(
                        config.getPageCount(),
                        1,
                        config.getMaxPageCount(),
                        1
                        );
            pageScanCountJSpinner = new JSpinner( pageScanCountSpinnerModel );
            pageScanCountJSpinner.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event)
                {
                    config.setPageCount( pageScanCountSpinnerModel.getNumber().intValue() );
                }
            });

            GridBagConstraints gbc_pageScanCountJSpinner = new GridBagConstraints();
            gbc_pageScanCountJSpinner.fill = GridBagConstraints.HORIZONTAL;
            gbc_pageScanCountJSpinner.insets = new Insets(0, 0, 5, 5);
            gbc_pageScanCountJSpinner.gridx = 1;
            gbc_pageScanCountJSpinner.gridy = 1;
            add(pageScanCountJSpinner, gbc_pageScanCountJSpinner);
        }
        {
            JLabel numberOfPicturesByPageLabel = new JLabel();
            numberOfPicturesByPageLabel.setText( String.format( numberOfPicturesByPageTxt, config.getNumberOfPicturesByPage() ) );
            GridBagConstraints gbc_numberOfPicturesByPageLabel = new GridBagConstraints();
            gbc_numberOfPicturesByPageLabel.insets = new Insets(0, 0, 5, 0);
            gbc_numberOfPicturesByPageLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfPicturesByPageLabel.gridx = 2;
            gbc_numberOfPicturesByPageLabel.gridy = 1;
            add(numberOfPicturesByPageLabel, gbc_numberOfPicturesByPageLabel);
        }

        int lineNumber = 0;
        // $hide>>$
        //if( config.isExtraStringValue() )
        for( final GenericDownloaderAppInterface.ComboBoxConfig entry : config.getComboBoxConfigCollection() )
        // $hide<<$
        {
            {
                JLabel stringsJLabel = new JLabel( entry.getLabelString() );
                stringsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                GridBagConstraints gbc_stringsJLabel = new GridBagConstraints();
                gbc_stringsJLabel.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJLabel.insets = new Insets(0, 0, 0, 5);
                gbc_stringsJLabel.gridx = 0;
                gbc_stringsJLabel.gridy = 2 + lineNumber;
                add(stringsJLabel, gbc_stringsJLabel);
            }
            final JLabel stringsJLabels;
            {
                stringsJLabels = new JLabel( entry.getLabelString( 0 ) );
                GridBagConstraints gbc_stringsJLabels = new GridBagConstraints();
                gbc_stringsJLabels.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJLabels.gridx = 2;
                gbc_stringsJLabels.gridy = 2 + lineNumber;
                add(stringsJLabels, gbc_stringsJLabels);
            }
            {
                final JComboBox<String> stringsJComboBox = new JComboBox<>();
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
                gbc_stringsJComboBox.insets = new Insets(0, 0, 0, 5);
                gbc_stringsJComboBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_stringsJComboBox.gridx = 1;
                gbc_stringsJComboBox.gridy = 2 + lineNumber;
                add(stringsJComboBox, gbc_stringsJComboBox);

                lineNumber++;
            }
        } // for(...)
    }

    public GenericDownloaderAppInterface getGenericDownloaderAppInterface()
    {
        return this.config;
    }

    public void setReadOnly( boolean isReadOnly )
    {
        final boolean enabled = ! isReadOnly;

        pageScanCountJSpinner.setEnabled( enabled  );
    }
}
