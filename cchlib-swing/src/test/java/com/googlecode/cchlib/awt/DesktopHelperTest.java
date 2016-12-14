package com.googlecode.cchlib.awt;

import static org.junit.Assume.assumeFalse;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class DesktopHelperTest
{
    @Test
    public void testDesktopHelper()
        throws PlatformDesktopNotSupportedException, MalformedURLException
    {
        assumeFalse( SafeSwingUtilities.isHeadless() );

        final URL url = new URL( "https://code.google.com/p/cchlib/" );

        DesktopHelper.browse( url  );
    }
}
