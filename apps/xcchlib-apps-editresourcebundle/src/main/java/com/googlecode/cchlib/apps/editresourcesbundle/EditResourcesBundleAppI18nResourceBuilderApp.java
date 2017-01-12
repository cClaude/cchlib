package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
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
import com.googlecode.cchlib.swing.RunnableSupplier;
import com.googlecode.cchlib.swing.RunnableSupplierHelper;

/**
 * This small App create resources bundles files
 */
public class EditResourcesBundleAppI18nResourceBuilderApp implements RunnableSupplier<I18nResourceBuilderResult>
{
    private static final Logger LOGGER = Logger.getLogger( EditResourcesBundleAppI18nResourceBuilderApp.class );

    private boolean                   done = false;
    private I18nResourceBuilderResult doneResult;
    private ExecutionException        doneCause;

    EditResourcesBundleAppI18nResourceBuilderApp()
    {
        // Empty
    }

    @Override
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
            this.doneCause  = new ExecutionException( cause );
        }

        LOGGER.info( "I18nResourceBuilder done = " + this.done );
    }

    @Override
    public I18nResourceBuilderResult getResult()
    {
        return this.doneResult;
    }

    @Override
    public boolean isDone()
    {
        return this.done;
    }

    @Override
    public ExecutionException getExecutionException()
    {
        return this.doneCause;
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

    I18nResourceBuilderResult doResourceBuilder()
        throws ExecutionException, InvocationTargetException, InterruptedException
    {
        return RunnableSupplierHelper.invokeAndWait( this );
    }

    public static void main( final String[] args ) throws IOException
    {
        SwingUtilities.invokeLater( new EditResourcesBundleAppI18nResourceBuilderApp() );
    }
}
