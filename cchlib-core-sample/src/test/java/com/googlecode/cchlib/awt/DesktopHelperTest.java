package com.googlecode.cchlib.awt;

import static org.junit.Assert.assertTrue;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

public class DesktopHelperTest
{
    @SuppressWarnings("deprecation")
    @Test
    public void test_isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedException
    {
        assertTrue( DesktopHelper.isNativeDesktopSupported() );
    }
    
    @Test
    public void testDesktopHelper()
        throws PlateformeDesktopNotSupportedException, MalformedURLException
    {        
        URL url = new URL( "http://google.com/" );
        
        DesktopHelper.browse( url  );
    }
}
