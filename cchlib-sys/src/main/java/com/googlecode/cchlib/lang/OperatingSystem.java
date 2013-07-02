package com.googlecode.cchlib.lang;

import java.io.File;
import org.apache.commons.lang.SystemUtils;

/**
 * 
 * @since 4.1.8
 */
public class OperatingSystem
{
    private OperatingSystem() {}

    public static boolean isWindows()
    {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public static boolean isUnix()
    {
        return SystemUtils.IS_OS_UNIX;
    }
    
    public static File getUserHome()
    {
        return SystemUtils.getUserHome();
    }
}
