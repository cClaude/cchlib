package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ressources.ResourcesPath;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.lang.Threads;

public class PreferencesPropertiesTest
{
    private static final Logger LOGGER = Logger.getLogger( PreferencesPropertiesTest.class );

    private static final String PREFERENCES_PROPERTIES
        = "com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.Preferences.properties";

    @Test
    @SuppressWarnings({ "squid:S1607", "deprecation" })
    @Ignore // save did not work any more (does not support collections)
    public void save_test() throws IOException
    {
        final File propertiesFile = IOTestHelper.createTempFile( PreferencesPropertiesTest.class, ".properties" );

        IOHelper.copy(
            ResourcesPath.class.getResourceAsStream( PREFERENCES_PROPERTIES ),
            propertiesFile
            );

        LOGGER.info( "Created file: " + propertiesFile );
        LOGGER.info( "File content : " + IOHelper.toString( propertiesFile ) );

        final PreferencesBean preferencesBean = new PreferencesBean();

        assertThat( preferencesBean.getDefaultSelectFirstMode() )
            .isEqualTo( SelectFirstMode.QUICK );

        final Preferences preferencesInterface = PreferencesProperties.loadPropertiesPreferences( propertiesFile );

        assertThat( preferencesInterface.getDefaultSelectFirstMode() )
            .isEqualTo( SelectFirstMode.QUICK );

        preferencesInterface.setDefaultSelectFirstMode(
                SelectFirstMode.FILEDEPTH_DESCENDING_ORDER
                );

        assertThat( preferencesInterface.getIncFilesFilterPatternRegExpList() )
            .isEmpty();

        assertThat( preferencesBean.getDefaultSelectFirstMode() )
            .isEqualTo( SelectFirstMode.FILEDEPTH_DESCENDING_ORDER );

        assertThat( preferencesBean.getIncFilesFilterPatternRegExpList() )
            .isEmpty();

        PreferencesProperties.saveProperties( preferencesInterface );

        LOGGER.info( "Save file: " + propertiesFile );
        LOGGER.info( "File content : " + IOHelper.toString( propertiesFile ) );

        final Preferences loadedPreferencesProperties
            = PreferencesProperties.loadPropertiesPreferences( propertiesFile );

        assertThat( loadedPreferencesProperties.getDefaultSelectFirstMode() )
            .isEqualTo( SelectFirstMode.FILEDEPTH_DESCENDING_ORDER );
    }

    @Test
    @SuppressWarnings("deprecation")
    public void load_test() throws IOException, InterruptedException, ExecutionException
    {
        final File propertiesFile = IOTestHelper.createTempFile( PreferencesPropertiesTest.class, ".properties" );

        IOHelper.copy(
            ResourcesPath.class.getResourceAsStream( PREFERENCES_PROPERTIES ),
            propertiesFile
            );

        LOGGER.info( "Created file: " + propertiesFile );
        LOGGER.info( "File content : " + IOHelper.toString( propertiesFile ) );

        final Preferences actual = Threads.startAndWait(
                () -> loadPropertiesPreferences( propertiesFile ),
                3,
                TimeUnit.SECONDS
                );

        assertThat( actual.getDefaultSelectFirstMode() )
            .isEqualTo( SelectFirstMode.QUICK );

        assertThat( actual.getIncFilesFilterPatternRegExpList() )
            .isEmpty();

        assertThat( actual.getDeleteSleepDisplay() ).isEqualTo( 42 );

        LOGGER.info( "Load file: " + propertiesFile );
        LOGGER.info( "File content : " + IOHelper.toString( propertiesFile ) );
    }

    @SuppressWarnings("deprecation")
    private Preferences loadPropertiesPreferences( final File propertiesFile )
    {
        return PreferencesProperties.loadPropertiesPreferences( propertiesFile );
    }
}
