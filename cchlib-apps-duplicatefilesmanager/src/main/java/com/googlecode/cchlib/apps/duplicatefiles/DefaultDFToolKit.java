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
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.DefaultJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

public final class DefaultDFToolKit
    implements DFToolKit, I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( DefaultDFToolKit.class );
    private final Map<Component,JFileChooserInitializer> jFileChooserInitializerMap= new HashMap<>(); // parentComponent,jFileChooserInitializer;
    private final Preferences preferences;
    private DuplicateFilesFrame mainWindow;

    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";
    @I18nString private String txtOpenDesktopExceptionTitle     = "Can not open file";

    public DefaultDFToolKit(
        final Preferences preferences
        )
    {
        this.preferences = preferences;
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // DFToolKit
    public String getMessagesBundle()
    {
        return ResourcesLoader.class.getPackage().getName()
                + ".MessagesBundle";
    }

    public void setMainWindow( final DuplicateFilesFrame mainWindow )
    {
        this.mainWindow = mainWindow;
    }

    private DuplicateFilesFrame _getMainWindow() throws IllegalStateException
    {
        if( this.mainWindow == null ) {
            throw new IllegalStateException( "mainWindow not set" );
            }

        return this.mainWindow;
    }

//    @Deprecated
//    @Override // DFToolKit
//    public DuplicateFilesFrame getMainWindow() throws NullPointerException
//    {
//        return _getMainWindow();
//    }

    @Override // DFToolKit
    public Frame getMainFrame()
    {
        return this.mainWindow;
    }

    @Override
    public void initJFileChooser()
    {
        Window win = getMainFrame();
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
//    @Deprecated
//    @Override // DFToolKit
//    public JFileChooser getJFileChooser()
//    {
//        return getJFileChooserInitializer( getMainWindow(), getMainWindow() ).getJFileChooser();
//    }

    @Override
    public JFileChooserInitializer getJFileChooserInitializer(
        final Window    parentWindow,
        final Component refComponent
        )
//    public JFileChooserInitializer getJFileChooserInitializer(
//        final Window parentWindow
//        )
    {
        JFileChooserInitializer jFileChooserInitializer = this.jFileChooserInitializerMap.get( refComponent );

        if( jFileChooserInitializer == null ) {
            final DefaultJFCCustomizer configurator = new DefaultJFCCustomizer()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public void perfomeConfig( JFileChooser jfc )
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

            //jFileChooserInitializer = new JFileChooserInitializer( configurator );
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
    public void openDesktop( File file )
    {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            logger .info( "trying to open: " + file );
            desktop.open( file );
            }
        catch( IOException e ) {
            DialogHelper.showMessageExceptionDialog(
                    getMainFrame(),
                    txtOpenDesktopExceptionTitle,
                    e
                    );
            }
    }

    @Override // DFToolKit
    public void sleep(long ms)
    {
        try {
            Thread.sleep( ms );
            }
        catch( InterruptedException ignore ) { // $codepro.audit.disable emptyCatchClause, logExceptions
            }
    }

    @Override // DFToolKit
    public Preferences getPreferences()
    {
        return preferences;
    }

    @Override // DFToolKit
    public Locale getValidLocale()
    {
        Locale locale;

        try {
            locale = getMainFrame().getLocale();
            }
        catch( Exception e ) {
            locale = null;
            logger.warn( "Can not use main window to set Locale", e );
            }

        if( locale == null ) {
            return Locale.getDefault();
            }

        return locale;
    }


    @Override // DFToolKit
    public void setEnabledJButtonCancel( boolean b )
    {
        _getMainWindow().setJButtonCancelEnabled( b );
    }

    @Override // DFToolKit
    public boolean isEnabledJButtonCancel()
    {
        return _getMainWindow().isEnabledJButtonCancel();
    }

    @Override // DFToolKit
    public void initComponentsJPanelConfirm()
    {
        _getMainWindow().initComponentsJPanelConfirm();
    }

    @Override // DFToolKit
    public Resources getResources()
    {
        return ResourcesLoader.getResources();
    }

    @Override // DFToolKit
    public List<File> getRootDirectoriesList()
    {
        List<File> list = new ArrayList<>();

        if( this.mainWindow != null ) {
            for( File f : this.mainWindow.getDuplicateFilesMainPanel().getJPanel0Select().entriesToScans() ) {
                if( f.isDirectory() ) {
                    list.add( f );
                    }
                }
            }

        return list;
    }
}
