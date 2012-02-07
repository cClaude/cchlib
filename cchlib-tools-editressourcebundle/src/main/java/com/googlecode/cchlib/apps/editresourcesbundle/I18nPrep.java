package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

/**
 * Create resources bundles files
 */
public class I18nPrep
{
    public static void main( String[] args ) throws IOException
    {
        CompareResourcesBundleFrame mainFrame0              = new CompareResourcesBundleFrame();
        I18nPrepAutoUpdatable       mainFrame               = mainFrame0;
        FilesConfig                 filesConfig             = new FilesConfig();
        I18nAutoUpdatable           loadFrame               = new LoadDialog( mainFrame0, filesConfig );
        Locale                      defaultLocale           = Locale.ENGLISH;
        PrintStream                 usageStatPrintStream    = System.err;
        PrintStream                 notUsePrintStream       = System.out;

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
