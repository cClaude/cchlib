package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.DefaultJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

//NOT public
@I18nName("DefaultAppToolKit")
final class DefaultAppToolKit
    implements AppToolKit, I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultAppToolKit.class );

    private final Map<Component,JFileChooserInitializer> jFileChooserInitializerMap= new HashMap<>(); // parentComponent,jFileChooserInitializer;
    private final PreferencesControler preferences;
    private DuplicateFilesFrame mainWindow;
    private Set<AutoI18nConfig> autoI18nConfig;

    @I18nString private String jFileChooserInitializerTitle;
    @I18nString private String jFileChooserInitializerMessage;
    @I18nString private String txtOpenDesktopExceptionTitle;

    //NOT public
    DefaultAppToolKit(
        final PreferencesControler preferences
        )
    {
        this.preferences = preferences;

        beSurNonFinal();
    }

    private void beSurNonFinal()
    {
        this.jFileChooserInitializerTitle     = "Waiting...";
        this.jFileChooserInitializerMessage   = "Analyze disk structure";
        this.txtOpenDesktopExceptionTitle     = "Can not open file";
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // DFToolKit
    public I18nResourceBundleName getI18nResourceBundleName()
    {
        return new DefaultI18nResourceBundleName(
                MyResourcesLoader.class,
                DefaultI18nResourceBundleName.DEFAULT_MESSAGE_BUNDLE_BASENAME
                );
    }

    @Override
    public Set<AutoI18nConfig> getAutoI18nConfig()
    {
        if( autoI18nConfig == null ) {
            autoI18nConfig = AutoI18nConfig.newAutoI18nConfig( AutoI18nConfig.DO_DEEP_SCAN );
            }
        return autoI18nConfig;
    }

    public void setMainWindow( final DuplicateFilesFrame mainWindow )
    {
        this.mainWindow = mainWindow;
    }

    private DuplicateFilesFrame private_getMainWindow() throws IllegalStateException
    {
        if( this.mainWindow == null ) {
            throw new IllegalStateException( "mainWindow not set" );
            }

        return this.mainWindow;
    }

    @Override // DFToolKit
    public Frame getMainFrame()
    {
        return this.mainWindow;
    }

    @Override
    public void initJFileChooser()
    {
        final Window win = getMainFrame();
        getJFileChooserInitializer( win , win );
    }

    @Override // DFToolKit
    public JFileChooser getJFileChooser(
        final Window    parentWindow,
        final Component refComponent
        )
    {
        return getJFileChooserInitializer( parentWindow, refComponent ).getJFileChooser();
    }

    @Override
    public JFileChooserInitializer getJFileChooserInitializer(
        final Window    parentWindow,
        final Component refComponent
        )
    {
        JFileChooserInitializer jFileChooserInitializer = this.jFileChooserInitializerMap.get( refComponent );

        if( jFileChooserInitializer == null ) {
            final DefaultJFCCustomizer configurator = new DefaultJFCCustomizer()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public void perfomeConfig( final JFileChooser jfc )
                {
                    super.perfomeConfig( jfc );

                    jfc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setAccessory( new TabbedAccessory()
                            .addTabbedAccessory( new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator() ) ) );
                }
            };

            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    parentWindow,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );
            this.jFileChooserInitializerMap.put( refComponent, jFileChooserInitializer );
        }

        return jFileChooserInitializer;
    }
    @Override // DFToolKit
    public void beep()
    {
        Toolkit.getDefaultToolkit().beep();
    }

    @Override // DFToolKit
    public void openDesktop( final File file )
    {
        final java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            LOGGER.info( "trying to open: " + file );
            desktop.open( file );
            }
        catch( final IOException e ) {
            DialogHelper.showMessageExceptionDialog(
                    getMainFrame(),
                    txtOpenDesktopExceptionTitle,
                    e
                    );
            }
    }

    @Override // DFToolKit
    public void sleep(final long ms)
    {
        try {
            Thread.sleep( ms );
            }
        catch( final InterruptedException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
            }
    }

    @Override // DFToolKit
    public PreferencesControler getPreferences()
    {
        return preferences;
    }

    @Override // DFToolKit
    public Locale getValidLocale()
    {
        // Get Locale define by prefs
        Locale locale = getPreferences().getLocale();

        if( locale == null ) {
            // Try to get Locale from main JFrame
            try {
                locale = getMainFrame().getLocale();
                }
            catch( final Exception e ) {
                locale = null;
                LOGGER.warn( "Can not use main window to set Locale", e );
                }

            if( locale == null ) {
                // Use Locale locale
                return Locale.getDefault();
                }
            }

        return locale;
    }


    @Override // DFToolKit
    public void setEnabledJButtonCancel( final boolean b )
    {
        private_getMainWindow().setJButtonCancelEnabled( b );
    }

    @Override // DFToolKit
    public boolean isEnabledJButtonCancel()
    {
        return private_getMainWindow().isEnabledJButtonCancel();
    }

    @Override // DFToolKit
    public void initComponentsJPanelConfirm()
    {
        private_getMainWindow().initComponentsJPanelConfirm();
    }

    @Override // DFToolKit
    public Resources getResources()
    {
        return MyResourcesLoader.getResources();
    }

    @Override // DFToolKit
    public List<File> getRootDirectoriesList()
    {
        final List<File> list = new ArrayList<>();

        if( this.mainWindow != null ) {
            for( final File f : this.mainWindow.getDuplicateFilesMainPanel().getJPanel0Select().entriesToScans() ) {
                if( f.isDirectory() ) {
                    list.add( f );
                    }
                }
            }

        return list;
    }
}
