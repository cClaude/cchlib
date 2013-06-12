package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.load.LoadDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;

/**
 * Create resources bundles files
 */
public class EditResourcesBundleAppI18nPrep implements Runnable
{
    private final static Logger logger = Logger.getLogger( EditResourcesBundleAppI18nPrep.class );

    public static void main( String[] args ) throws IOException
    {
        SwingUtilities.invokeLater( new EditResourcesBundleAppI18nPrep() );
    }

    @Override
    public void run()
    {
        final Preferences           prefs                  = Preferences.createDefaultPreferences();
        CompareResourcesBundleFrame mainFrame              = new CompareResourcesBundleFrame( prefs );

        FilesConfig                 filesConfig            = new FilesConfig( prefs );
        LoadDialog                  loadFrame              = new LoadDialog( mainFrame, filesConfig );
        HTMLPreviewDialog           htmlFrame              = new HTMLPreviewDialog(mainFrame, "<<fakeTitle>>", "**FakeContent**" );

        MultiLineEditorDialog.StoreResult storeResult       = new MultiLineEditorDialog.StoreResult(){@Override public void storeResult(String text) {}};
        MultiLineEditorDialog       mLineFrame              = new MultiLineEditorDialog( mainFrame, storeResult , "<<fakeTitle>>", "**FakeContent**" );

        Locale                      defaultLocale           = Locale.ENGLISH;

        try { Thread.sleep( 1_000 ); } catch( InterruptedException e ) {}

        I18nAutoCoreUpdatable[] i18nConteners = {
            mainFrame,
            loadFrame,
            htmlFrame,
            mLineFrame
            };

        I18nPrep i18nPrep = I18nPrepHelper.createI18nPrep(
                EditResourcesBundleApp.getConfig(),
                EditResourcesBundleApp.getI18nResourceBundleName(),
                defaultLocale
                );
        Result r = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );

        PrintStream                 usageStatPrintStream    = System.err;
        PrintStream                 notUsePrintStream       = System.out;

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, r.getUsageStatCollector() );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, r.getNotUseCollector() );

        System.err.flush();
        System.out.flush();

        logger.info( "done" );
    }
}
