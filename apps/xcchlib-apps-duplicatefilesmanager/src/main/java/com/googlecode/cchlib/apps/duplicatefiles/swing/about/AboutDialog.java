package com.googlecode.cchlib.apps.duplicatefiles.swing.about;

import javax.swing.JDialog;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AutoI18nService;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

/**
 * Display about dialog
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AboutDialog
    extends JDialog
        implements I18nAutoUpdatable, AboutPanelAction
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AboutDialog.class );
    private static final int ABOUT_FRAME_HEIGTH = 350;
    private static final int ABOUT_FRAME_WIDTH = 500;
    private final AboutPanel contentPanel;

    /**
     * Create the dialog.
     */
    public AboutDialog()
    {
        super( getAppToolKit().getMainFrame() );

        this.contentPanel = new AboutPanel( getAppToolKit().getResources(), this );

        super.setContentPane( this.contentPanel );
        super.setSize( ABOUT_FRAME_WIDTH, ABOUT_FRAME_HEIGTH );
    }

    /**
     * Launch the application.
     */
    public static void open()
    {
        final AutoI18n autoI18n = AutoI18nService.getInstance().getAutoI18n();

        try {
            final AboutDialog dialog = new AboutDialog();

            dialog.performeI18n( autoI18n );
            dialog.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
            }
        catch( final Exception e ) {
            LOGGER.error( "showDialog", e );
            }
    }

    private static AppToolKit getAppToolKit()
    {
        return AppToolKitService.getInstance().getAppToolKit();
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this.contentPanel, this.contentPanel.getClass() );
    }

    @Override
    public void buttonOKClicked()
    {
        AboutDialog.this.dispose();
    }
}

