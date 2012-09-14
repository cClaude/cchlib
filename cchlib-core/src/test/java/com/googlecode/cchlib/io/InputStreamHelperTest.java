package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.IOHelper;

public class InputStreamHelperTest
{
    @Test
    public void test_getPNGFile() throws IOException
    {
        InputStream is = IO.getPNGFile();

        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_isEquals() throws IOException
    {
        InputStream is1 = IO.getPNGFile();
        InputStream is2 = IO.getPNGFile();
        boolean     r   = IOHelper.isEquals( is1, is2 );

        Assert.assertNotNull( r );

        is1.close();
        is2.close();
    }
}
