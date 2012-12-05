package com.googlecode.cchlib.io;

import java.io.File;

/**
 * Miscellaneous tools to create commons {@link File} objects
 * @since 4.1.6
 */
public class FileHelper
{
    // All static
    public FileHelper() {}

    /**
     * Returns File object for tmp directory
     * according to java.io.tmpdir Java property.
     *
     * @return File object for tmp directory
     * @since 4.1.6
     */
    public static File getTmpDirFile()
    {
        return new File( System.getProperty("java.io.tmpdir" ) );
    }

    /**
     * Returns File object for current user home directory
     * according to user.home Java property.
     *
     * @return File object for current user home directory
     * @since 4.1.6
     */
    public static File getUserHomeDirFile()
    {
        return new File( System.getProperty("user.home") );
    }

    /**
     * Returns File object relative to current user home
     * directory according to user.home Java property.
     *
     * @param relativePath Relative path to current user home
     *
     * @return File object relative to current user home directory
     * @since 4.1.6
     */
    public static File getUserHomeDirFile(
        final String relativePath
        )
    {
        return new File(
            getUserHomeDirFile(),
            relativePath
            );
    }

    /**
     * Returns File object for root system directory
     * @return File object for root system directory
     * @since 4.1.6
     */
    public static File getSystemRootFile()
    {
        return new File( "/" );
    }
}
