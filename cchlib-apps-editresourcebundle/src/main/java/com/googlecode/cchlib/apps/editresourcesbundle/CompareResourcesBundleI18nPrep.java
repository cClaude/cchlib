package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

/**
 * Create resources bundles files
 */
public class CompareResourcesBundleI18nPrep
{
    public static void main( String[] args ) throws IOException
    {
        CompareResourcesBundleFrame mainFrame0              = new CompareResourcesBundleFrame( Preferences.createDefaultPreferences() );
        I18nPrepAutoUpdatable       mainFrame               = mainFrame0;
        FilesConfig                 filesConfig             = new FilesConfig();
        I18nAutoUpdatable           loadFrame               = new LoadDialog( mainFrame0, filesConfig );
        I18nAutoUpdatable           htmlFrame               = new HTMLPreviewDialog(mainFrame0, "<<fakeTitle>>", "**FakeContent**" );
        Locale                      defaultLocale           = Locale.ENGLISH;
        MultiLineEditorDialog.StoreResult storeResult = new MultiLineEditorDialog.StoreResult(){@Override public void storeResult(String text) {}};
        I18nAutoUpdatable           mLineFrame              = new MultiLineEditorDialog( mainFrame0, storeResult , "<<fakeTitle>>", "**FakeContent**" );
        PrintStream                 usageStatPrintStream    = System.err;
        PrintStream                 notUsePrintStream       = System.out;

        I18nPrepHelper.defaultPrep(
            defaultLocale,
            usageStatPrintStream,
            notUsePrintStream,
            mainFrame,
            loadFrame,
            htmlFrame,
            mLineFrame
            );

        System.err.flush();
        System.out.flush();


        System.exit( 0 );
    }
}
