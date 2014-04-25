package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.io.IOException;
import java.util.Locale;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;

@I18nName("PreferencesOpener")
public class PreferencesOpener
{
    private static final Logger LOGGER = Logger.getLogger( PreferencesOpener.class );
    private static final Locale[] LOCALES = {
        null, // System,
        Locale.ENGLISH,
        Locale.FRENCH
        };

    @I18nString protected String msgStringDefaultLocale = "default system";
    @I18nString protected String msgStringSavePrefsExceptionTitle = "Error while saving preferences";

    private final String[] languages = {
        null,
        LOCALES[1].getDisplayLanguage(),
        LOCALES[2].getDisplayLanguage() // $codepro.audit.disable numericLiterals
        };
    private final JFrame rootFrame;
    private final Preferences preferences;
    private final PreferencesDefaultsParametersValues initParams;

    public PreferencesOpener( final JFrame rootFrame, final Preferences preferences )
    {
        this.rootFrame      = rootFrame;
        this.preferences    = preferences;
        this.languages[ 0 ] = msgStringDefaultLocale;

        final int selectedLanguageIndex = getSelectedLanguageIndex( preferences );

        this.initParams = newPreferencesDefaultsParametersValues(
                languages,
                selectedLanguageIndex
                );
    }

    private int getSelectedLanguageIndex( final Preferences preferences )
    {
        final Locale pLocale               = preferences.getLocale();
        int          selectedLanguageIndex = 0;

        if( pLocale == null ) {
            selectedLanguageIndex = 0;
            }
        else {
            for( int i = 1; i<LOCALES.length; i++ ) {
                if( pLocale.equals( LOCALES[ i ] ) ) {
                    selectedLanguageIndex = i;
                    break;
                    }
                }
            }

        return selectedLanguageIndex;
    }

    public void open()
    {
        final AbstractPreferencesAction action = newPreferencesAction( LOCALES );
        final PreferencesJDialog        dialog = new PreferencesJDialog( initParams, action );

        dialog.setVisible( true );
    }

    private AbstractPreferencesAction newPreferencesAction( final Locale[] locales )
    {
        final AbstractPreferencesAction action = new AbstractPreferencesAction()
        {
            @Override
            public void onSave( final PreferencesCurentSaveParameters saveParams )
            {
                final Locale locale = locales[ saveParams.getSelectedLanguageIndex() ];
                preferences.setLocale( locale );

                if( saveParams.isSaveWindowSize() ) {
                    preferences.setWindowDimension( rootFrame.getSize() );
                    }

                preferences.setNumberOfFiles( saveParams.getNumberOfFiles() );

                if( saveParams.isSaveLookAndFeel() ) {
                    preferences.setLookAndFeelClassName();
                    }

                savePreferences();

                this.dispose();
            }
        };
        return action;
    }

    private PreferencesDefaultsParametersValues newPreferencesDefaultsParametersValues(
            final String[] languages,
            final int      selectedLanguageIndex
            )
    {
        final PreferencesDefaultsParametersValues initParams = new PreferencesDefaultsParametersValues()
        {
            @Override
            public int getNumberOfFiles()
            {
                return preferences.getNumberOfFiles();
            }
            @Override
            public String[] getLanguages()
            {
                return languages;
            }
            @Override
            public int getSelectedLanguageIndex()
            {
                return selectedLanguageIndex;
            }
            @Override
            public boolean isSaveWindowSize()
            {
                return false; // FIXME ??
            }
        };
        return initParams;
    }

    private void savePreferences()
    {
        LOGGER.info( "Save prefs: " + preferences );
        try {
            preferences.save();
            }
        catch( final IOException e ) {
            DialogHelper.showMessageExceptionDialog(
                this.rootFrame,
                msgStringSavePrefsExceptionTitle,
                e
                );
            }
    }

}
