package com.googlecode.cchlib.tools.phone.recordsorter.conf.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;

public class GoogleReadConfigFactoryTest {

    private static final String FULL_PATH = "/com/googlecode/cchlib/xutil/google/googlecontact/";
    //private static final String RELATIVE_PATH = "googlecontact/";*

    private ConfigFactory instance;

    @Before
    public void setup()
    {
        instance = GoogleReadConfigFactory.getInstance();

        Assert.assertNotNull(instance);
    }

    @Test
    @Ignore // FIXME @Ignore Add sample in source code...
    public void test_load() throws FileNotFoundException, IOException
    {
        final File file = FileHelper.getUserHomeDirFile( "Dropbox/#CallRecorder/#Config/google-contacts.csv" );

        final Config config = instance.load( file );

        Assert.assertNotNull( config );
        Assert.assertEquals( 580, config.getContacts().size() );
    }

    @Test
    @Ignore // FIXME @Ignore /com/googlecode/cchlib/xutil/google/googlecontact/
    public void test_load_GoogleContact1bis() throws IOException
    {
        final String resourceName = FULL_PATH + "GoogleContact1bis.csv";
        try(final InputStream inStream = this.getClass().getResourceAsStream( resourceName )) {
            Assert.assertNotNull( "File not found : " + resourceName, inStream );

            final Config config = instance.load( inStream );

            Assert.assertNotNull( config );
            Assert.assertEquals( 1, config.getContacts().size() );
        }
    }
}
