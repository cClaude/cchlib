package com.googlecode.cchlib.apps.duplicatefiles;

import com.googlecode.cchlib.apps.duplicatefiles.common.AboutDialog;
import com.googlecode.cchlib.apps.duplicatefiles.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesDialogWB;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;
import java.util.TooManyListenersException;

/**
 * Create resources bundles files
 */
public class DuplicateFilesI18nPrep
{
    public static void main( final String[] args ) throws HeadlessException, TooManyListenersException, InterruptedException
    {
        // Default language !
        Preferences preferences = getPreferences();

        // Build frame
//        DefaultDFToolKit    defaultDFToolKit_   = new DefaultDFToolKit( preferences );
        DuplicateFilesFrame duplicateFilesFrame = new DuplicateFilesFrame( preferences );
        AppToolKit          dfToolKit           = AppToolKitService.getInstance().createAppToolKit( preferences, duplicateFilesFrame );
        //defaultDFToolKit_.setMainWindow( duplicateFilesFrame );
        PrintStream         usageStatPrintStream    = System.err;
        PrintStream         notUsePrintStream       = System.out;

        I18nAutoCoreUpdatable[] i18nConteners = {
            duplicateFilesFrame,
            new AboutDialog(),
            new PreferencesDialogWB(),
            new RemoveEmptyFilesJPanel()
            };

        Set<AutoI18nConfig>     config                 = dfToolKit.getAutoI18nConfig();
        I18nResourceBundleName  i18nResourceBundleName = dfToolKit.getI18nResourceBundleName();

        I18nPrep i18nPrep = I18nPrepHelper.createI18nPrep( config, i18nResourceBundleName, preferences.getLocale() );

        Result result = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        for( I18nAutoCoreUpdatable contener : i18nConteners ) {
            if( contener instanceof Window ) {
                ((Window)contener).dispose();
                }
            }
        System.gc();
        Thread.sleep( 1000 ); // $codepro.audit.disable numericLiterals
    }


    private static Preferences getPreferences()
    {
        final Preferences preferences = Preferences.createDefaultPreferences();
        preferences.setLocale( Locale.ENGLISH );
        return preferences;
    }
}
