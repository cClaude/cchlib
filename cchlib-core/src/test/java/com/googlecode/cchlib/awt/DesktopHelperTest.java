package com.googlecode.cchlib.awt;

import org.junit.Test;

public class DesktopHelperTest
{
    @Test
    public void testDesktopHelper()
        throws PlateformeDesktopNotSupportedException
    {
        DesktopHelper.isNativeDesktopSupported();
    }

}
