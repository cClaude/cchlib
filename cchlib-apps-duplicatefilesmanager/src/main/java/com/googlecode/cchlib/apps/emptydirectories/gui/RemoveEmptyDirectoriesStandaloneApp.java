package com.googlecode.cchlib.apps.emptydirectories.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.DefaultDFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelperAutoUpdatable;

/**
 *
 *
 */
public class RemoveEmptyDirectoriesStandaloneApp
    extends JFrame
        implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 2L;
    private static final Logger logger = Logger.getLogger( RemoveEmptyDirectoriesStandaloneApp.class );
    private AutoI18n autoI18n;
    private DFToolKit dfToolKit;
    private RemoveEmptyDirectoriesPanel _contentPane;
    @I18nString private static String txtFrameTitle = "Delete Empty Directories";

    /**
     * 
     * @param dfToolKit
     * @param autoI18n Could be null.
     */
    private RemoveEmptyDirectoriesStandaloneApp( 
        final DFToolKit dfToolKit, 
        final AutoI18n  autoI18n 
        )
    {
        this.dfToolKit = dfToolKit;

        // Prepare i18n !
        if( autoI18n == null ) {
            this.autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle(
                    this.dfToolKit.getValidLocale(), 
                    new I18nPrepHelperAutoUpdatable() {
                        @Override
                        public void performeI18n( AutoI18n autoI18n )
                        {
                            RemoveEmptyDirectoriesStandaloneApp.this.performeI18n( autoI18n );
                        }
                        @Override
                        public String getMessagesBundleForI18nPrepHelper()
                        {
                            return getDFToolKit().getMessagesBundle();
                        }} 
                    ).getAutoI18n();
            }
        else {
            this.autoI18n = autoI18n;
            }

        // Build display
        {
            setSize( 800, 400 );

            _contentPane = new RemoveEmptyDirectoriesPanel( dfToolKit, this );
            _contentPane.setBorder( new CompoundBorder() );
            setContentPane( _contentPane );
        }

        setIconImage( getDFToolKit().getResources().getAppImage() );

        // Apply i18n !
        performeI18n( this.autoI18n );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
        autoI18n.performeI18n( _contentPane, _contentPane.getClass() );
    }
    
    private DFToolKit getDFToolKit()
    {
        return dfToolKit;
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        final Preferences   preferences = Preferences.createPreferences();
        final DFToolKit     dfToolKit   = new DefaultDFToolKit( preferences );
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                RemoveEmptyDirectoriesStandaloneApp frame = RemoveEmptyDirectoriesStandaloneApp.createRemoveEmptyDirectoriesFrame( dfToolKit, null );
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            }
        } );

        logger.fatal( "Running in a thread" );
    }

    /**
     * A WindowHandler should be add on frame.
     * @return Main Window
     */
    private static RemoveEmptyDirectoriesStandaloneApp createRemoveEmptyDirectoriesFrame( 
        final DFToolKit dfToolKit,
        final AutoI18n  autoI18n
        )
    {
        RemoveEmptyDirectoriesStandaloneApp frame = new RemoveEmptyDirectoriesStandaloneApp( dfToolKit, autoI18n );

        frame.setTitle( txtFrameTitle );
        //frame.init();
        frame.setVisible( true );

        return frame;
    }
}
