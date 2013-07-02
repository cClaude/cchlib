package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Locale;
import java.util.TooManyListenersException;
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

/**
 * Create resources bundles files
 */
public class DuplicateFilesI18nPrep
{
    public static void main( String[] args ) throws IOException, HeadlessException, TooManyListenersException
    {
        // Default language !
        Preferences preferences = Preferences.createDefaultPreferences();
        Locale locale = Locale.ENGLISH;

        // Build frame
        DefaultDFToolKit    defaultDFToolKit_   = new DefaultDFToolKit( preferences );
        DFToolKit           dfToolKit           = defaultDFToolKit_;
        DuplicateFilesFrame duplicateFilesFrame = new DuplicateFilesFrame( dfToolKit );
        defaultDFToolKit_.setMainWindow( duplicateFilesFrame );
        PrintStream         usageStatPrintStream    = System.err;
        PrintStream         notUsePrintStream       = System.out;

        I18nAutoCoreUpdatable[] i18nConteners = {
            duplicateFilesFrame,
            new AboutDialog( dfToolKit ),
            new PreferencesDialogWB(),
            new RemoveEmptyFilesJPanel( dfToolKit )
            };

        EnumSet<AutoI18nConfig> config                 = dfToolKit.getAutoI18nConfig();
        I18nResourceBundleName  i18nResourceBundleName = dfToolKit.getI18nResourceBundleName();
        
        I18nPrep i18nPrep = I18nPrepHelper.createI18nPrep( config, i18nResourceBundleName, locale );

        //I18nPrepHelper.defaultPrep( i18nPrep, usageStatPrintStream, notUsePrintStream, i18nConteners );
        Result r = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );
        
        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, r.getUsageStatCollector() );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, r.getNotUseCollector() );
    }
}
