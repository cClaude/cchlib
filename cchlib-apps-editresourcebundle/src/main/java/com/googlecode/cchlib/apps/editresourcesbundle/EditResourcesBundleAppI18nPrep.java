package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.apps.editresourcesbundle.html.HTMLPreviewDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.load.LoadDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.multilineeditor.MultiLineEditorDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;

/**
 * Create resources bundles files
 */
public class EditResourcesBundleAppI18nPrep implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( EditResourcesBundleAppI18nPrep.class );

    public static void main( final String[] args ) throws IOException
    {
        SwingUtilities.invokeLater( new EditResourcesBundleAppI18nPrep() );
    }

    @Override
    @SuppressWarnings({"squid:S2142","squid:S00108"})
    public void run()
    {
        final Preferences                 prefs     = Preferences.createDefaultPreferences();
        final CompareResourcesBundleFrame mainFrame = new CompareResourcesBundleFrame( prefs );

        final FilesConfig       filesConfig = new FilesConfig( prefs );
        final LoadDialog        loadFrame   = new LoadDialog( mainFrame, filesConfig );
        final HTMLPreviewDialog htmlFrame   = new HTMLPreviewDialog(mainFrame, "<<fakeTitle>>", "**FakeContent**" );

        final MultiLineEditorDialog.StoreResult storeResult = text -> {};
        final MultiLineEditorDialog             mLineFrame  = new MultiLineEditorDialog( mainFrame, storeResult , "<<fakeTitle>>", "**FakeContent**" );

        final Locale defaultLocale = Locale.ENGLISH;

        try { Thread.sleep( 1_000 ); } catch( final InterruptedException e ) { /* ignore */ }

        final I18nAutoCoreUpdatable[] i18nConteners = {
            mainFrame,
            loadFrame,
            htmlFrame,
            mLineFrame
            };

        final I18nPrep i18nPrep = I18nPrepHelper.createI18nPrep(
                EditResourcesBundleApp.getConfig(),
                EditResourcesBundleApp.getI18nResourceBundleName(),
                defaultLocale
                );

        try {
            launchI18nPrep( i18nConteners, i18nPrep );
        }
        catch( final I18nPrepException e ) {
            e.printStackTrace();
        }

        LOGGER.info( "done" );
    }

    private void launchI18nPrep(
            final I18nAutoCoreUpdatable[] i18nConteners,
            final I18nPrep i18nPrep
            ) throws I18nPrepException
    {
        final Result result = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );

        final PrintStream                 usageStatPrintStream    = System.err;
        final PrintStream                 notUsePrintStream       = System.out;

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        System.err.flush();
        System.out.flush();
    }
}
