package com.googlecode.cchlib.tools;

import java.awt.Image;
import org.junit.Assert;
import org.junit.Test;

public class ToolsTest
{
    //private final static Logger logger = Logger.getLogger( ToolsTest.class );

    @Test
    public void testToolsIconImage()
    {
        Image res = Tools.getToolsIconImage();

        Assert.assertNotNull( res );
   }

}
