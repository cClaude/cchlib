package com.googlecode.cchlib.tools.phone.recordsorter.conf.google;

import static org.junit.Assume.assumeTrue;
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

public class GoogleReadConfigFactoryTest
{
    private static final String FULL_PATH = "/com/googlecode/cchlib/xutil/google/googlecontact/";
    //private static final String RELATIVE_PATH = "googlecontact/";*

    private ConfigFactory instance;

    @Before
    public void setup()
    {
        this.instance = GoogleReadConfigFactory.getInstance();

        Assert.assertNotNull(this.instance);
    }

    @Test
    public void test_load() throws FileNotFoundException, IOException
    {
        // FIXME @Ignore Add sample in source code...
        final File file = FileHelper.getUserHomeDirectoryFile(
                "Dropbox/#CallRecorder/#Config/google-contacts.csv"
                );

        assumeTrue( file.exists() );

        final Config config = this.instance.load( file );

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

            final Config config = this.instance.load( inStream );

            Assert.assertNotNull( config );
            Assert.assertEquals( 1, config.getContacts().size() );
        }
    }
}
