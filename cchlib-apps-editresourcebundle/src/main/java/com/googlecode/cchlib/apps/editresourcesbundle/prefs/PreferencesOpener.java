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
    private static final Locale[] locales = {
        null, // System,
        Locale.ENGLISH,
        Locale.FRENCH
        };

    @I18nString private String msgStringDefaultLocale = "default system";
    @I18nString private String msgStringSavePrefsExceptionTitle = "Error while saving preferences";

    private final String[] languages = {
        null,
        locales[1].getDisplayLanguage(),
        locales[2].getDisplayLanguage() // $codepro.audit.disable numericLiterals
        };
    private JFrame rootFrame;
    private Preferences preferences;
    private PreferencesDefaultsParametersValues initParams;

    public PreferencesOpener( final JFrame rootFrame, final Preferences preferences )
    {
        this.rootFrame      = rootFrame;
        this.preferences    = preferences;
        this.languages[ 0 ] = msgStringDefaultLocale;

        final int selectedLanguageIndex;
        {
            final Locale pLocale  = preferences.getLocale();
            int    selected = 0;

            if( pLocale == null ) {
                selected = 0;
                }
            else {
                for( int i = 1; i<locales.length; i++ ) {
                    if( pLocale.equals( locales[ i ] ) ) {
                        selected = i;
                        break;
                        }
                    }
                }
            selectedLanguageIndex = selected;
        }

        this.initParams = newPreferencesDefaultsParametersValues(
                languages,
                selectedLanguageIndex
                );
    }

    public void open()
    {
        final AbstractPreferencesAction action = newPreferencesAction( locales );
        final PreferencesJDialog        dialog = new PreferencesJDialog( initParams, action );

        dialog.setVisible( true );
    }

    private AbstractPreferencesAction newPreferencesAction( final Locale[] locales )
    {
        final AbstractPreferencesAction action = new AbstractPreferencesAction()
        {
            @Override
            public void onSave( PreferencesCurentSaveParameters saveParams )
            {
                Locale locale = locales[ saveParams.getSelectedLanguageIndex() ];
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
        PreferencesDefaultsParametersValues initParams = new PreferencesDefaultsParametersValues()
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
        catch( IOException e ) {
            DialogHelper.showMessageExceptionDialog(
                this.rootFrame,
                msgStringSavePrefsExceptionTitle,
                e
                );
            }
    }

}
