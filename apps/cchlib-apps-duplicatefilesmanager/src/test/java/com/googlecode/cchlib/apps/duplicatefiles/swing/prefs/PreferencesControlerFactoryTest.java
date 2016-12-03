package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.lang.Threads;

public class PreferencesControlerFactoryTest
{
    private class TestRun implements Runnable
    {
        private PreferencesControler preferencesControler;
        private Exception            exception;
        private boolean              done = false;;

        @Override
        public void run()
        {
            try {
                this.preferencesControler = PreferencesControlerFactory
                        .createPreferences( (File)null );
                this.done = true;
            }
            catch( final Exception exception ) {
                this.exception = exception;
            }
        }

        public PreferencesControler getPreferencesControler()
        {
            return this.preferencesControler;
        }

        public Exception getException()
        {
            return this.exception;
        }

        public boolean isDone()
        {
            return this.done;
        }
    }

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
        final TestRun runner = new TestRun();

        new  Thread( runner ).start();

        // Wait max 10 seconds for result
        for( int i = 0; i<20; i++ ) {
            Threads.sleep( 500 );

            if( runner.isDone() ) {
                break;
            }
        }

        final PreferencesControler pref  = runner.getPreferencesControler();
        final Exception            error = runner.getException();
        final boolean              done  = runner.isDone();

        LOGGER.info( "createPreferences_default_file() : pref  = " + pref );
        LOGGER.info( "createPreferences_default_file() : error = " + error );
        LOGGER.info( "createPreferences_default_file() : done  = " + done );

        Assertions.assertThat( error ).isNull();
        Assertions.assertThat( pref ).isNotNull();
        Assertions.assertThat( done ).isTrue();
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
