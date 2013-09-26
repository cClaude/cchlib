package com.googlecode.cchlib.awt;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Ignore;
import org.junit.Test;

public class DesktopHelperJ5Test
{
    @Test
    @Ignore // result depend of jdk
    public void test_isNativeDesktopSupported()
        throws PlateformeDesktopNotSupportedJ5Exception
    {
        DesktopHelperJ5.isNativeDesktopSupported();
    }
    
    @Test
    @Ignore // could not work under jdk 1.5
    public void testDesktopHelper()
        throws PlateformeDesktopNotSupportedJ5Exception, MalformedURLException
    {        
        URL url = new URL( "https://code.google.com/p/cchlib/" );
        
        DesktopHelperJ5.browse( url  );
    }
}
