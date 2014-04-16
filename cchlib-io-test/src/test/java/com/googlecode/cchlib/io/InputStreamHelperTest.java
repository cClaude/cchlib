package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class InputStreamHelperTest
{
    @Test
    public void test_getPNGFile() throws IOException
    {
        final InputStream is = IO.createPNGInputStream();

        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_isEquals() throws IOException
    {
        final InputStream is1 = IO.createPNGInputStream();
        final InputStream is2 = IO.createPNGInputStream();
        final boolean     r   = IOHelper.isEquals( is1, is2 );

        Assert.assertTrue( r );

        is1.close();
        is2.close();
    }
}
