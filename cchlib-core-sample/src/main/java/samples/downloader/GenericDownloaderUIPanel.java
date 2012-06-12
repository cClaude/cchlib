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

public class GenericDownloaderUIPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel pageScanCountJLabel;
    @I18nString private String numberOfPicturesByPageTxt = "%d picture(s) by page";
    private JSpinner pageScanCountJSpinner;
    private String siteName;

    /**
     * Create the panel.
     */
    public GenericDownloaderUIPanel(
        final String    siteName,
        final int       defaultPageCount,
        final int       maxPageCount,
        final int       numberOfPicturesByPage
        )
    {
        this.siteName = siteName;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 50, 50, 0};
        gridBagLayout.rowHeights = new int[]{0, 14, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JLabel sitenameJLabel = new JLabel( siteName );
        sitenameJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_sitenameJLabel = new GridBagConstraints();
        gbc_sitenameJLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_sitenameJLabel.gridwidth = 3;
        gbc_sitenameJLabel.insets = new Insets(0, 0, 5, 5);
        gbc_sitenameJLabel.gridx = 0;
        gbc_sitenameJLabel.gridy = 0;
        add(sitenameJLabel, gbc_sitenameJLabel);

        pageScanCountJLabel = new JLabel( "Pages to scan" );
        GridBagConstraints gbc_pageScanCountJLabel = new GridBagConstraints();
        gbc_pageScanCountJLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_pageScanCountJLabel.insets = new Insets(0, 0, 5, 5);
        gbc_pageScanCountJLabel.anchor = GridBagConstraints.NORTH;
        gbc_pageScanCountJLabel.gridx = 0;
        gbc_pageScanCountJLabel.gridy = 1;
        add(pageScanCountJLabel, gbc_pageScanCountJLabel);

        SpinnerNumberModel pageScanCountSpinnerModel = new SpinnerNumberModel( defaultPageCount, 1, maxPageCount, 1 );
        pageScanCountJSpinner = new JSpinner( pageScanCountSpinnerModel );

        GridBagConstraints gbc_pageScanCountJSpinner = new GridBagConstraints();
        gbc_pageScanCountJSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_pageScanCountJSpinner.insets = new Insets(0, 0, 5, 5);
        gbc_pageScanCountJSpinner.gridx = 1;
        gbc_pageScanCountJSpinner.gridy = 1;
        add(pageScanCountJSpinner, gbc_pageScanCountJSpinner);

        JLabel numberOfPicturesByPageLabel = new JLabel();
        numberOfPicturesByPageLabel.setText( String.format( numberOfPicturesByPageTxt, numberOfPicturesByPage ) );
        GridBagConstraints gbc_numberOfPicturesByPageLabel = new GridBagConstraints();
        gbc_numberOfPicturesByPageLabel.insets = new Insets(0, 0, 5, 0);
        gbc_numberOfPicturesByPageLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_numberOfPicturesByPageLabel.gridx = 2;
        gbc_numberOfPicturesByPageLabel.gridy = 1;
        add(numberOfPicturesByPageLabel, gbc_numberOfPicturesByPageLabel);
    }

    public String getSiteName()
    {
        return this.siteName;
    }

}
