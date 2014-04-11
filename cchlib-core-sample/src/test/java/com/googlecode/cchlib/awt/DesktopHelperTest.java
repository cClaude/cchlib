package com.googlecode.cchlib.awt;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class DesktopHelperTest
{
    @Test
    public void testDesktopHelper()
        throws PlatformDesktopNotSupportedException, MalformedURLException
    {
        URL url = new URL( "http://google.com/" );

        DesktopHelper.browse( url  );
    }
}
