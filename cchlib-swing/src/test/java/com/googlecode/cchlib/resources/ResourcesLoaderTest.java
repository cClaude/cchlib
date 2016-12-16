package com.googlecode.cchlib.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.Icon;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

@SuppressWarnings("resource")
public class ResourcesLoaderTest
{
    @Test
    public void test_getResource()
    {
        final URL actual = ResourcesLoader.getResource( ResourcesLoader.OK_ICON_32x32 );

        assertThat( actual )
            .as( "Can not find: " + ResourcesLoader.OK_ICON_32x32 )
            .isNotNull();
    }

    @Test
    public void test_getResourceAsStream()
        throws ResourcesLoaderException, IOException
    {
        try( final InputStream is = ResourcesLoader.getResourceAsStream( ResourcesLoader.OK_ICON_32x32 ) ) {
            assertThat( is )
                .as( "Can not find: " + ResourcesLoader.OK_ICON_32x32 )
                .isNotNull();
        };
    }

    @Test
    public void test_getImageIcon() throws ResourcesLoaderException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final Icon icon = ResourcesLoader.getImageIcon( ResourcesLoader.OK_ICON_32x32 );

        assertThat( icon )
            .as( "Can not find: " + ResourcesLoader.OK_ICON_32x32 )
            .isNotNull();
    }

    @Test
    public void test_getImage() throws ResourcesLoaderException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final Image image = ResourcesLoader.getImage( ResourcesLoader.OK_ICON_32x32 );

        assertThat( image )
            .as( "Can not find: " + ResourcesLoader.OK_ICON_32x32 )
            .isNotNull();
    }
}
