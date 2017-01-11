package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundlePopupMenu;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleTableModel;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FileObject;
import com.googlecode.cchlib.apps.editresourcesbundle.html.HTMLPreviewDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.load.LoadDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.multilineeditor.MultiLineEditorDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.PreferencesJPanel;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.PreferencesValues;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderHelper;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.lang.Threads;

/**
 * This small App create resources bundles files
 */
public class EditResourcesBundleAppI18nPrepApp implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( EditResourcesBundleAppI18nPrepApp.class );

    private volatile boolean                   done = false;
    private volatile I18nResourceBuilderResult doneResult;
    private volatile Exception                 doneCause;

    public boolean isDone()
    {
        return this.done;
    }

    public Exception getDoneCause()
    {
        return this.doneCause;
    }

    I18nResourceBuilderResult doResourceBuilder()
    {
        SwingUtilities.invokeLater( this );

        for( int i = 1; (i < 30) && ! this.done; i++ ) {
            LOGGER.info( "Launch EditResourcesBundleAppI18nPrep not yet ready (" + i + ")" );

            Threads.sleep( 1, TimeUnit.SECONDS );
        }

        LOGGER.info( "doResourceBuilder() result: " + this.done );
        Threads.sleep( 1, TimeUnit.SECONDS );

        return this.doneResult;
    }

    @Override
    @SuppressWarnings({"squid:S2142","squid:S00108"})
    public void run()
    {
        final Preferences                 prefs       = Preferences.createDefaultPreferences();
        final CompareResourcesBundleFrame mainFrame   = new CompareResourcesBundleFrame( prefs );
        final FilesConfig                 filesConfig = newFilesConfig( prefs );

        Threads.sleep( 1, TimeUnit.SECONDS );

        final I18nAutoUpdatable[] i18nConteners = {
            mainFrame,
            new LoadDialog( mainFrame, filesConfig ),
            new HTMLPreviewDialog( mainFrame, "<<fakeTitle>>", "**FakeContent**" ),
            newMultiLineEditorDialog( mainFrame ),
            new CompareResourcesBundleTableModel( filesConfig ),
            new CompareResourcesBundlePopupMenu( new JTable(), null, null ),
            new CompareResourcesBundleFrame(),
            new PreferencesJPanel( newPreferencesValues(), null ),
            };

        final Locale              defaultLocale = Locale.ENGLISH;
        final AutoI18n            autoI18n      = EditResourcesBundleApp.newAutoI18n( defaultLocale );
        final I18nResourceBuilder builder       = I18nResourceBuilderFactory.newI18nResourceBuilder(
                autoI18n,
                defaultLocale,
                AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS
                );

        try {
            for( final I18nAutoUpdatable i18nAutoUpdatable : i18nConteners ) {
                builder.append( i18nAutoUpdatable );
            }

            final File outputFile = I18nResourceBuilderHelper.newOutputFile(
                    EditResourcesBundleApp.getReferencePackage()
                    );
            builder.saveMissingResourceBundle( outputFile );

            LOGGER.info( "I18nResourceBuilder saveMissingResourceBundle to " + outputFile );

            this.doneResult = builder.getResult();
            this.doneCause  = null;
            this.done       = true;
        }
        catch( final Exception cause ) {
            LOGGER.error( "I18nResourceBuilder error", cause );

            this.done       = true;
            this.doneResult = null;
            this.doneCause  = cause;
        }

        LOGGER.info( "I18nResourceBuilder done = " + this.done );
    }

    private I18nAutoUpdatable newMultiLineEditorDialog( final CompareResourcesBundleFrame mainFrame )
    {
        return new MultiLineEditorDialog(
                mainFrame,
                text -> {/*fake*/} ,
                "<<fakeTitle>>",
                "**FakeContent**"
                );
    }

    private PreferencesValues newPreferencesValues()
    {
        return new PreferencesValues() {
            @Override
            public boolean isSaveWindowSize() { return false; }
            @Override
            public int getSelectedLanguageIndex() { return 0; }
            @Override
            public int getNumberOfFiles() { return 0; }
            @Override
            public String[] getLanguages() { return new String[] {"Fake"}; }
        };
    }

    private FilesConfig newFilesConfig( final Preferences prefs )
    {
        final FilesConfig fc = new FilesConfig( prefs ) {
            private static final long serialVersionUID = 1L;

            @Override
            public int getNumberOfFiles()
            {
                return 0;
            }
        };

        return fc.setFileObject( new FileObject( new File("pom.xml"), true ), 0 );
    }

    public static void main( final String[] args ) throws IOException
    {
        SwingUtilities.invokeLater( new EditResourcesBundleAppI18nPrepApp() );
    }
}
