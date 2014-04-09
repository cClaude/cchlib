package com.googlecode.cchlib.apps.duplicatefiles.common;

import javax.swing.JDialog;
import org.apache.log4j.Logger;
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
//    private final class Panel extends AboutPanel
//    {
//        private static final long serialVersionUID = 1L;
//
//        private Panel( Resources resources )
//        {
//            super( resources );
//        }
//
//        @Override
//        protected void buttonOKClicked()
//        {
//            AboutDialog.this.dispose();
//        }
//    }
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AboutDialog.class );
    private /*final*/ AboutPanel contentPanel;
    //private /*final*/ DFToolKit dfToolKit;

    /**
     * Launch the application.
     */
    public static void open(
        final AutoI18nCore  autoI18n
        )
    {
        try {
            AboutDialog dialog = new AboutDialog();

            dialog.performeI18n( autoI18n );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
            }
        catch( Exception e ) {
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
        super( AppToolKitService.getInstance().getAppToolKit().getMainFrame() );

        //this.dfToolKit    = dfToolKit;
        this.contentPanel = new AboutPanel( AppToolKitService.getInstance().getAppToolKit().getResources(), this );

        super.setContentPane( contentPanel );
        super.setSize( 500, 350 ); // $codepro.audit.disable numericLiterals
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

