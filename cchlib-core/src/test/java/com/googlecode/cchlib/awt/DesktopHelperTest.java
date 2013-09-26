package com.googlecode.cchlib.awt;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Ignore;
import org.junit.Test;

public class DesktopHelperTest
{
    @Test
    @Ignore // result depend of jdk
    public void test_isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedException
    {
        DesktopHelper.isNativeDesktopSupported();
    }
    
    @Test
    @Ignore // could not work under jdk 1.5
    public void testDesktopHelper()
        throws PlateformeDesktopNotSupportedException, MalformedURLException
    {        
        URL url = new URL( "https://code.google.com/p/cchlib/" );
        
        DesktopHelper.browse( url  );
    }
}
