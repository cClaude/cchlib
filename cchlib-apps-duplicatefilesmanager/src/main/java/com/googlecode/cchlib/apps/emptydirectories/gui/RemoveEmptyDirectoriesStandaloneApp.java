// $codepro.audit.disable
package com.googlecode.cchlib.apps.emptydirectories.gui;

import javax.swing.JFrame;
import javax.swing.border.CompoundBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 *
 *
 */
public class RemoveEmptyDirectoriesStandaloneApp
    extends JFrame
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( RemoveEmptyDirectoriesStandaloneApp.class );

    private AutoI18nCore autoI18n;
    private AppToolKit dfToolKit;
    private RemoveEmptyDirectoriesPanel _contentPane;
    @I18nString private static String txtFrameTitle = "Delete Empty Directories";

    /**
     *
     * @param dfToolKit
     * @param autoI18n Could be null.
     */
    private RemoveEmptyDirectoriesStandaloneApp(
        final AutoI18nCore  autoI18n
        )
    {
        this.dfToolKit = AppToolKitService.getInstance().getAppToolKit();

        // Prepare i18n !
        if( autoI18n == null ) {
//            this.autoI18n = com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory.createDefaultI18nBundle(
//                    this.dfToolKit.getValidLocale(),
//                    new com.googlecode.cchlib.i18n.config.I18nPrepHelperAutoUpdatable() {
//                        @Override
//                        public void performeI18n( AutoI18nCore autoI18n )
//                        {
//                            RemoveEmptyDirectoriesStandaloneApp.this.performeI18n( autoI18n );
//                        }
//                        @Override
//                        public String getMessagesBundleForI18nPrepHelper()
//                        {
//                            //return getDFToolKit().getMessagesBundle();
//                            //return getDFToolKit().getPackageMessageBundleBase().getName()
//                            //        + '.'
//                            //        + getDFToolKit().getMessageBundleBaseName();
//                            return getDFToolKit().getI18nResourceBundleName().getName();
//                        }}
//                    ).getAutoI18n();
            this.autoI18n = AutoI18nCoreFactory.createAutoI18nCore(
                    getDFToolKit().getAutoI18nConfig(),
                    getDFToolKit().getI18nResourceBundleName(),
                    getDFToolKit().getValidLocale() // locale == null ? Locale.getDefault() : locale
                    );
            }
        else {
            this.autoI18n = autoI18n;
            }

        // Build display
        {
            setSize( 800, 400 );

            _contentPane = new RemoveEmptyDirectoriesPanel( this );
            _contentPane.setBorder( new CompoundBorder() );
            setContentPane( _contentPane );
        }

        setIconImage( getDFToolKit().getResources().getAppImage() );

        // Apply i18n !
        performeI18n( this.autoI18n );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
        autoI18n.performeI18n( _contentPane, _contentPane.getClass() );
    }

    private AppToolKit getDFToolKit()
    {
        return dfToolKit;
    }

//    /**
//     * @param args
//     */
//    public static void main( String[] args )
//    {
//        final Preferences   preferences = Preferences.createPreferences();
//        final DFToolKit     dfToolKit   = new DefaultDFToolKit( preferences );
//
//        SwingUtilities.invokeLater( new Runnable() {
//            @Override
//            public void run()
//            {
//                RemoveEmptyDirectoriesStandaloneApp frame = RemoveEmptyDirectoriesStandaloneApp.createRemoveEmptyDirectoriesFrame( dfToolKit, null );
//                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//            }
//        } );
//
//        LOGGER.fatal( "Running in a thread" );
//    }

    /**
     * A WindowHandler should be add on frame.
     * @return Main Window
     */
    private static RemoveEmptyDirectoriesStandaloneApp createRemoveEmptyDirectoriesFrame(
        final AutoI18nCore  autoI18n
        )
    {
        RemoveEmptyDirectoriesStandaloneApp frame = new RemoveEmptyDirectoriesStandaloneApp( autoI18n );

        frame.setTitle( txtFrameTitle );
        //frame.init();
        frame.setVisible( true );

        return frame;
    }
}
