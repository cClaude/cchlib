package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;

public class PreferencesControlerFactoryTest
{
    private static final Logger LOGGER = Logger.getLogger( PreferencesControlerFactoryTest.class );

    @Test
    public void createDefaultPreferences()
    {
        final PreferencesControler pref = PreferencesControlerFactory.createDefaultPreferences();

        Assertions.assertThat( pref ).isNotNull();

        LOGGER.info( "createDefaultPreferences() : pref = " + pref );
    }

    @Test
    public void createPreferences_default_file() throws FileNotFoundException
    {
        final PreferencesControler pref = PreferencesControlerFactory.createPreferences( (File)null );

        Assertions.assertThat( pref ).isNotNull();

        LOGGER.info( "createPreferences_default_file() : pref = " + pref );
    }

    @Test(expected=FileNotFoundException.class)
    public void createPreferences_no_file() throws FileNotFoundException
    {
        final File file = getNotExistingFile();

        PreferencesControlerFactory.createPreferences( file );

        Assert.fail();
    }

    private File getNotExistingFile()
    {
        final File tmpDir = FileHelper.getTmpDirFile();
        final File file   = new File( tmpDir, "NotExistingFile" );

        Assertions.assertThat( file ).doesNotExist();

        return file;
    }
}
