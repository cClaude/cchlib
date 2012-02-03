package com.googlecode.cchlib.resources;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.Icon;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 */
public class ResourcesLoaderTest
{
    @Test
    public void test_getResource()
    {
        URL url = ResourcesLoader.getResource( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( url );
    }

    @Test
    public void test_getResourceAsStream()
        throws ResourcesLoaderException, IOException
    {
        InputStream is = ResourcesLoader.getResourceAsStream( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_getImageIcon() throws ResourcesLoaderException
    {
        Icon icon = ResourcesLoader.getImageIcon( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( icon );
    }

    @Test
    public void test_getImage() throws ResourcesLoaderException
    {
        Image image = ResourcesLoader.getImage( ResourcesLoader.OK_ICON_32x32 );

        Assert.assertNotNull( image );
    }

}
