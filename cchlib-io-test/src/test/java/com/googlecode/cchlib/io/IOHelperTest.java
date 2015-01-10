package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class IOHelperTest
{
    @Test
    public void test_getPNGFile() throws IOException
    {
        try (InputStream is = IO.createPNGInputStream()) {
            Assert.assertNotNull( is );
        }
    }

    @Test
    public void test_isEquals() throws IOException
    {
        try (InputStream is1 = IO.createPNGInputStream()) {
            try (InputStream is2 = IO.createPNGInputStream()) {
                final boolean r = IOHelper.isEquals( is1, is2 );
                Assert.assertTrue( r );
            }
        }
    }
}
