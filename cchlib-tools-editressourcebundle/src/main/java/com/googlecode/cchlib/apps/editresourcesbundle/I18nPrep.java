package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;

/**
 * Create resources bundles files
 */
public class I18nPrep
{
    public static void main( String[] args ) throws IOException
    {
        I18nPrepAutoUpdatable   mainFrame               = new CompareResourcesBundleFrame();
        JFileChooserInitializer jFileChooserInitializer = null;
        FilesConfig              filesConfig            = new FilesConfig();
        I18nAutoUpdatable       loadFrame               = new LoadDialog(null/*mainFrame*/, jFileChooserInitializer, filesConfig);
        Locale                  defaultLocale           = Locale.ENGLISH;
        PrintStream             usageStatPrintStream    = System.err;
        PrintStream             notUsePrintStream       = System.out;

        I18nPrepHelper.defaultPrep(
            defaultLocale,
            usageStatPrintStream,
            notUsePrintStream,
            mainFrame,
            loadFrame
            );

        System.err.flush();
        System.out.flush();

        System.exit( 0 );
    }
}
