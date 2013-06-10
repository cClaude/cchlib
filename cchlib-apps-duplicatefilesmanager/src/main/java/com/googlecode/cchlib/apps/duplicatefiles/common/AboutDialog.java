package com.googlecode.cchlib.apps.duplicatefiles.common;

import javax.swing.JDialog;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.Resources;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 * Display about dialog
 */
public class AboutDialog
    extends JDialog
        implements I18nAutoCoreUpdatable
{
    private final class Panel extends AboutPanel 
    {
        private static final long serialVersionUID = 1L;
        
        private Panel( Resources resources )
        {
            super( resources );
        }

        @Override
        protected void buttonOKClicked()
        {
            AboutDialog.this.dispose();
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( AboutDialog.class );
    private /*final*/ AboutPanel contentPanel;
    //private /*final*/ DFToolKit dfToolKit;

    /**
     * Launch the application.
     */
    public static void open( 
        final DFToolKit     dfToolKit, 
        final AutoI18nCore  autoI18n
        )
    {
        try {
            AboutDialog dialog = new AboutDialog( dfToolKit );
            
            dialog.performeI18n( autoI18n );
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
            }
        catch( Exception e ) {
            logger.error( "showDialog", e );
            }
    }
     
    /**
     * Create the dialog.
     * 
     * @param dfToolKit
     */
    public AboutDialog( 
        final DFToolKit dfToolKit
        )
    {
        super( dfToolKit.getMainFrame() );

        //this.dfToolKit    = dfToolKit;
        this.contentPanel = new Panel( dfToolKit.getResources() );
        
        super.setContentPane( contentPanel );
        super.setSize( 500, 350 );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( contentPanel, contentPanel.getClass() );
    }
}

