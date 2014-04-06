package com.googlecode.cchlib.awt;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;

public class DesktopHelperTest
{
//    private static final Logger LOGGER = Logger.getLogger(DesktopHelperTest.class);

//    @Test
//    @Deprecated
//    public void test_isNativeDesktopSupported()
//    {
//        try {
//            DesktopHelper.isNativeDesktopSupported();
//        } catch (PlatformDesktopNotSupportedException e) {
//            LOGGER.info( e.getMessage() );
//        }
//    }

    @Test
    public void testDesktopHelper()
        throws PlatformDesktopNotSupportedException, MalformedURLException
    {
        URL url = new URL( "https://code.google.com/p/cchlib/" );

        DesktopHelper.browse( url  );
    }
}
