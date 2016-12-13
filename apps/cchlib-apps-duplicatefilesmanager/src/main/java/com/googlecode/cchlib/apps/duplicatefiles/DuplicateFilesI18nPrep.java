package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Window;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.about.AboutDialog;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControlerFactory;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.swing.services.AutoI18nConfigService;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.lang.Threads;

/**
 * Create resources bundles files
 * <br>
 * This class is design to generate i18n files
 */
@SuppressWarnings("ucd") // Development configuration entry point
public class DuplicateFilesI18nPrep
{
    private DuplicateFilesI18nPrep() {} // All static

    @SuppressWarnings("squid:S106")
    public static void main( final String[] args ) throws Exception
    {
        // Default language !
        final PreferencesControler preferences = getPreferences();

        // Build frame
        final DuplicateFilesFrame duplicateFilesFrame  = new DuplicateFilesFrame( preferences );
        final AppToolKit          dfToolKit            = AppToolKitService.getInstance().createAppToolKit( preferences, duplicateFilesFrame );
        final PrintStream         usageStatPrintStream = System.err;
        final PrintStream         notUsePrintStream    = System.out;

        final I18nAutoCoreUpdatable[] i18nConteners = {
            duplicateFilesFrame,
            new AboutDialog(),
            new PreferencesDialogWB(),
            new RemoveEmptyFilesJPanel()
            };

        final Set<AutoI18nConfig>    config                 = AutoI18nConfigService.getInstance().getAutoI18nConfig();
        final I18nResourceBundleName i18nResourceBundleName = dfToolKit.getI18nResourceBundleName();

        final I18nPrep i18nPrep = I18nPrepHelper.createI18nPrep( config, i18nResourceBundleName, preferences.getLocale() );
        final Result   result   = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        for( final I18nAutoCoreUpdatable contener : i18nConteners ) {
            if( contener instanceof Window ) {
                ((Window)contener).dispose();
                }
            }

        Threads.sleep( 1, TimeUnit.SECONDS );
    }

    private static PreferencesControler getPreferences()
    {
        final PreferencesControler preferences = PreferencesControlerFactory.createDefaultPreferences();

        preferences.setLocale( Locale.ENGLISH );

        return preferences;
    }
}
