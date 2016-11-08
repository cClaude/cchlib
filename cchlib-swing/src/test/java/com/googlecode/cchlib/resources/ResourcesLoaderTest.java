package com.googlecode.cchlib.resources;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.Icon;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("resource")
public class ResourcesLoaderTest
{
    @Test
    public void test_getResource()
    {
        final URL url = ResourcesLoader.getResource( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( url );
    }

    @Test
    public void test_getResourceAsStream()
        throws ResourcesLoaderException, IOException
    {
        final InputStream is = ResourcesLoader.getResourceAsStream( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_getImageIcon() throws ResourcesLoaderException
    {
        final Icon icon = ResourcesLoader.getImageIcon( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( icon );
    }

    @Test
    public void test_getImage() throws ResourcesLoaderException
    {
        final Image image = ResourcesLoader.getImage( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( image );
    }

}
