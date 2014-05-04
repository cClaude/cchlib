package com.googlecode.cchlib.apps.duplicatefiles.common;

import javax.swing.JDialog;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 * Display about dialog
 */
public class AboutDialog
    extends JDialog
        implements I18nAutoCoreUpdatable, AboutPanelAction
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AboutDialog.class );
    private static final int ABOUT_FRAME_HEIGTH = 350;
    private static final int ABOUT_FRAME_WIDTH = 500;
    private final AboutPanel contentPanel;

    /**
     * Launch the application.
     */
    public static void open(
        final AutoI18nCore  autoI18n
        )
    {
        try {
            final AboutDialog dialog = new AboutDialog();

            dialog.performeI18n( autoI18n );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
            }
        catch( final Exception e ) {
            LOGGER.error( "showDialog", e );
            }
    }

    /**
     * Create the dialog.
     *
     * @param dfToolKit
     */
    public AboutDialog()
    {
        super( getAppToolKit().getMainFrame() );

        this.contentPanel = new AboutPanel( getAppToolKit().getResources(), this );

        super.setContentPane( contentPanel );
        super.setSize( ABOUT_FRAME_WIDTH, ABOUT_FRAME_HEIGTH );
    }

    private static AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( contentPanel, contentPanel.getClass() );
    }

    @Override
    public void buttonOKClicked()
    {
        AboutDialog.this.dispose();
    }
}

