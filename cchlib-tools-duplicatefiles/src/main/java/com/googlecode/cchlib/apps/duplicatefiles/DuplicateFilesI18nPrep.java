package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.TooManyListenersException;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;
import cx.ath.choisnet.tools.duplicatefiles.gui.DuplicateFilesFrame;

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
        DefaultDFToolKit    defaultDFToolKit    = new DefaultDFToolKit( preferences );
        DuplicateFilesFrame duplicateFilesFrame = new DuplicateFilesFrame(defaultDFToolKit );
        defaultDFToolKit.setMainWindow( duplicateFilesFrame );
  
        PrintStream         usageStatPrintStream    = System.err;
        PrintStream         notUsePrintStream       = System.out;

        I18nPrepHelper.defaultPrep(
            locale,
            usageStatPrintStream,
            notUsePrintStream,
            duplicateFilesFrame/*,
            otherFrames*/
            );
    }

}
