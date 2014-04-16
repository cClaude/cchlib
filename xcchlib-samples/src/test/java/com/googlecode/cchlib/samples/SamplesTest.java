package com.googlecode.cchlib.samples;

import java.awt.Image;
import org.junit.Assert;
import org.junit.Test;

public class SamplesTest
{
    @Test
    public void testToolsIconImage()
    {
        Image res = Samples.getSampleIconImage();

        Assert.assertNotNull( res );
   }

}
