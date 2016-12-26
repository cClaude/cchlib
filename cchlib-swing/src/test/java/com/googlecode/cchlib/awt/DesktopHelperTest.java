package com.googlecode.cchlib.awt;

import static org.junit.Assume.assumeTrue;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class DesktopHelperTest
{
    @Test
    @Ignore // Test disabled for CI
    @SuppressWarnings("squid:S1607")
    public void testDesktopHelper()
        throws PlatformDesktopNotSupportedException, MalformedURLException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final URL url = new URL( "https://code.google.com/p/cchlib/" );

        DesktopHelper.browse( url  );
    }
}
