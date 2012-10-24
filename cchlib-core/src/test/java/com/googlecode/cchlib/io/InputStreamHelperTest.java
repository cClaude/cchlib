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
        InputStream is = IO.createPNGInputStream();

        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_isEquals() throws IOException
    {
        InputStream is1 = IO.createPNGInputStream();
        InputStream is2 = IO.createPNGInputStream();
        boolean     r   = IOHelper.isEquals( is1, is2 );

        Assert.assertNotNull( r );

        is1.close();
        is2.close();
    }
}
