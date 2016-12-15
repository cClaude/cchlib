package com.googlecode.cchlib.awt;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
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

    @Test
    public void testDesktopHelper_is_this_one_ok_should_not_work()
        throws PlatformDesktopNotSupportedException, MalformedURLException
    {
        assumeTrue( SafeSwingUtilities.isHeadless() );

        final URL url = new URL( "https://code.google.com/p/cchlib/" );

        DesktopHelper.browse( url  );
    }
}
