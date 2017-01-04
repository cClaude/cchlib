package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
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
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.lang.Threads;

/**
 * This small App create resources bundles files
 */
public class EditResourcesBundleAppI18nPrepApp implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( EditResourcesBundleAppI18nPrepApp.class );

    private boolean           done = false;
    private I18nPrepResult    doneResult;
    private I18nPrepException doneCause;

    public boolean isDone()
    {
        return this.done;
    }

    public I18nPrepException getDoneCause()
    {
        return this.doneCause;
    }

    I18nPrepResult runDoPrep() throws InvocationTargetException, InterruptedException
    {
        final EditResourcesBundleAppI18nPrepApp instance = new EditResourcesBundleAppI18nPrepApp();

        SwingUtilities.invokeLater( instance );

        for( int i = 1; (i < 10) && ! this.done; i++ ) {
            LOGGER.info( "Launch EditResourcesBundleAppI18nPrep not yet ready (" + i + ")" );

            Threads.sleep( 1, TimeUnit.SECONDS );
        }

        LOGGER.info( "Launch EditResourcesBundleAppI18nPrep result: " + this.done );

        return this.doneResult;
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

        final MultiLineEditorDialog.StoreResult storeResult = text -> { final int todo; };
        final MultiLineEditorDialog             mLineFrame  = new MultiLineEditorDialog( mainFrame, storeResult , "<<fakeTitle>>", "**FakeContent**" );

        final Locale defaultLocale = Locale.ENGLISH;

        Threads.sleep( 1, TimeUnit.SECONDS );

        final I18nAutoCoreUpdatable[] i18nConteners = {
            mainFrame,
            loadFrame,
            htmlFrame,
            mLineFrame
            };

        final I18nPrep i18nPrep = I18nPrepFactory.newI18nPrep(
                EditResourcesBundleApp.getConfig(),
                EditResourcesBundleApp.getI18nResourceBundleName(),
                defaultLocale
                );

        try {
            this.done       = true;
            this.doneResult = launchI18nPrep( i18nConteners, i18nPrep );
            this.doneCause  = null;
        }
        catch( final I18nPrepException cause ) {
            LOGGER.error( "I18n prep issue", cause );

            this.done       = true;
            this.doneResult = null;
            this.doneCause  = cause;
        }

        LOGGER.info( "done" );
    }

    @SuppressWarnings("squid:S106")
    private I18nPrepResult launchI18nPrep(
            final I18nAutoCoreUpdatable[] i18nConteners,
            final I18nPrep                i18nPrep
            ) throws I18nPrepException
    {
        final I18nPrepResult result = I18nPrepHelper.defaultPrep( i18nPrep, i18nConteners );

        final PrintStream usageStatPrintStream = System.err;
        final PrintStream notUsePrintStream    = System.out;

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        System.err.flush();
        System.out.flush();

        return result;
    }

    public static void main( final String[] args ) throws IOException
    {
        SwingUtilities.invokeLater( new EditResourcesBundleAppI18nPrepApp() );
    }
}
