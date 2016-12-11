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
        try (InputStream is = IOTestHelper.createPNGInputStream()) {
            Assert.assertNotNull( is );
        }
    }

    @Test
    public void test_isEquals() throws IOException
    {
        try (InputStream is1 = IOTestHelper.createPNGInputStream()) {
            try (InputStream is2 = IOTestHelper.createPNGInputStream();) {

            final boolean     r   = IOHelper.isEquals( is1, is2 );
            Assert.assertTrue( r );
            }
        }
    }
}
